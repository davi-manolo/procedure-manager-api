package com.procedure.manager.service.impl;

import com.procedure.manager.domain.exception.DatabaseException;
import com.procedure.manager.domain.mapper.ProcedureMapper;
import com.procedure.manager.domain.model.ProcedureModel;
import com.procedure.manager.domain.vo.*;
import com.procedure.manager.repository.ProcedureRepository;
import com.procedure.manager.service.ProcedureService;
import com.procedure.manager.service.ProcedureTypeService;
import com.procedure.manager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.procedure.manager.domain.enumeration.ExceptionMessage.DATABASE_PROCEDURE_DOES_NOT_EXIST;
import static com.procedure.manager.domain.enumeration.ExceptionMessage.DATABASE_PROCEDURE_LIST_DOES_NOT_EXIST;
import static com.procedure.manager.util.DateUtils.getFinalLocalDateTime;
import static com.procedure.manager.util.DateUtils.getInitialLocalDateTime;
import static java.lang.Boolean.TRUE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
@SuppressWarnings("unused")
public class ProcedureServiceImpl implements ProcedureService {

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private ProcedureMapper procedureMapper;

    @Autowired
    private ProcedureTypeService procedureTypeService;

    @Autowired
    private UserService userService;

    @Value("${config.app.properties.delete_after_months}")
    private String deleteAfterMonths;

    @Override
    public void registerProcedure(ProcedureCreationDataVo procedureCreationDataVo) {

        ProcedureTypeVo procedureTypeVo = procedureTypeService.getProcedureType(procedureCreationDataVo.getProcedureTypeId());
        UserVo userVo = userService.getUser(procedureCreationDataVo.getUserId());

        ProcedureVo procedureVo = createProcedure(procedureCreationDataVo, procedureTypeVo.getPercentage());

        procedureVo.setUser(userVo);
        procedureVo.setProcedureType(procedureTypeVo);

        log.info("Procedimento criado com os dados {}.", procedureCreationDataVo);

        procedureRepository.save(procedureMapper.voToModel(procedureVo));

    }

    @Override
    public ProcedureVo getProcedure(Long procedureId) {
        Optional<ProcedureModel> optional = procedureRepository.findById(procedureId);
        if(optional.isEmpty()) {
            log.error("N??o houve retorno de procedimento com o ID {}.", procedureId);
            throw new DatabaseException(NOT_FOUND, DATABASE_PROCEDURE_DOES_NOT_EXIST);
        }
        log.info("Procedimento retornado: {}.", optional.get());
        return procedureMapper.modelToVo(optional.get());
    }

    @Override
    public List<ProcedureVo> getProcedureListByPeriod(DataSearchProcedureMonthVo dataSearchProcedureMonthVo) {

        LocalDateTime startDate = getInitialLocalDateTime(dataSearchProcedureMonthVo.getMonth(), dataSearchProcedureMonthVo.getYear());
        LocalDateTime endDate = getFinalLocalDateTime(dataSearchProcedureMonthVo.getMonth(), dataSearchProcedureMonthVo.getYear());

        Optional<List<ProcedureModel>> optional = procedureRepository
                .findByRegistrationDateBetweenAndUser_userIdEqualsAndDisabledIsFalseOrderByRegistrationDateAsc(
                        startDate, endDate, dataSearchProcedureMonthVo.getUserId()
                );
        if(optional.isEmpty()) {
            log.info("N??o houve retorno de procedimentos neste per??odo com os dados: {}.", dataSearchProcedureMonthVo);
            throw new DatabaseException(NOT_FOUND, DATABASE_PROCEDURE_LIST_DOES_NOT_EXIST);
        }
        return procedureMapper.modelListToVoList(optional.get());

    }

    @Override
    public void editProcedure(Long procedureId, ProcedureCreationDataVo procedureCreationDataVo) {

        ProcedureTypeVo procedureTypeVo = procedureTypeService.getProcedureType(procedureCreationDataVo.getProcedureTypeId());
        UserVo userVo = userService.getUser(procedureCreationDataVo.getUserId());

        ProcedureVo procedureVoDb = getProcedure(procedureId);
        ProcedureVo procedureVo = updateProcedure(procedureCreationDataVo, procedureTypeVo.getPercentage());

        procedureVo.setUser(userVo);
        procedureVo.setProcedureType(procedureTypeVo);

        procedureRepository.save(procedureMapper.voToModel(populateSameRegisteredProperties(procedureVoDb, procedureVo)));

    }

    @Override
    public void disableProcedure(Long procedureId) {
        ProcedureVo procedureVo = getProcedure(procedureId);
        procedureVo.setDisabled(TRUE);
        procedureRepository.save(procedureMapper.voToModel(procedureVo));
    }

    @Override
    @Transactional
    public void deleteProcedureWithThreeMonths() {
        LocalDateTime ldtNow = LocalDateTime.now();
        LocalDateTime ldtMinus = ldtNow.minusMonths(Long.parseLong(deleteAfterMonths));
        List<ProcedureModel> procedureModelList = procedureRepository.removeByRegistrationDateLessThan(ldtMinus);
        if (procedureModelList.isEmpty()) {
            log.info("N??o existe procedimentos de {} meses atr??s.", deleteAfterMonths);
        } else {
            StringBuilder sb = new StringBuilder();
            procedureModelList.forEach(procedure -> {
                sb.append("ID: ").append(procedure.getProcedureId()).append("\n");
                sb.append("Registro no DB: ").append(procedure.getRegistrationDate()).append("\n");
                sb.append("Registro do procedimento: ").append(procedure.getProcedureDate()).append("\n");
                sb.append("Cliente: ").append(procedure.getCustomer()).append("\n");
                sb.append("Tipo de procedimento: ").append(procedure.getProcedureType().getName()).append("\n");
                sb.append("Valor procedimento: ").append(procedure.getValue()).append("\n");
                sb.append("Valor recebido: ").append(procedure.getValueReceived()).append("\n");
                sb.append("Realizado por: ").append(procedure.getUser().getName()).append("\n");
                sb.append("\n");
            });
            log.info("\n" + "Lista de procedimentos exclu??dos:" + "\n\n" + "{}.", sb);
        }
    }

    @Override
    public BigDecimal calculateAmountReceivableByMonth(DataSearchProcedureMonthVo dataSearchProcedureMonthVo) {
        List<ProcedureVo> procedureVoList = getProcedureListByPeriod(dataSearchProcedureMonthVo);
        return procedureVoList.stream()
                .map(ProcedureVo::getValueReceived)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private ProcedureVo createProcedure(ProcedureCreationDataVo procedureCreationDataVo, Double percentage) {
        ProcedureVo procedureVo = populateProcedure(procedureCreationDataVo, percentage);
        procedureVo.setRegistrationDate(LocalDateTime.now());
        return procedureVo;
    }

    private ProcedureVo updateProcedure(ProcedureCreationDataVo procedureCreationDataVo, Double percentage) {
        return populateProcedure(procedureCreationDataVo, percentage);
    }

    private ProcedureVo populateProcedure(ProcedureCreationDataVo procedureCreationDataVo, Double percentage) {
        ProcedureVo procedureVo = new ProcedureVo();
        procedureVo.setProcedureDate(procedureCreationDataVo.getProcedureDate());
        procedureVo.setCustomer(procedureCreationDataVo.getCustomer());
        procedureVo.setValue(procedureCreationDataVo.getValue());
        procedureVo.setValueReceived(calculateAmountReceivable(procedureCreationDataVo.getValue(), percentage));
        return procedureVo;
    }

    private ProcedureVo populateSameRegisteredProperties(ProcedureVo procedureDb, ProcedureVo procedureTarget) {
        procedureTarget.setProcedureId(procedureDb.getProcedureId());
        procedureTarget.setRegistrationDate(procedureDb.getRegistrationDate());
        procedureTarget.setDisabled(procedureDb.getDisabled());
        return procedureTarget;
    }

    private BigDecimal calculateAmountReceivable(BigDecimal procedureValue, Double percentage) {
        return procedureValue.divide(BigDecimal.valueOf(100.00)).multiply(BigDecimal.valueOf(percentage));
    }

}
