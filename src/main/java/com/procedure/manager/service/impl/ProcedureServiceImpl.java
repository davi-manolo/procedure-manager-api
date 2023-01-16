package com.procedure.manager.service.impl;

import com.procedure.manager.domain.exception.DatabaseException;
import com.procedure.manager.domain.mapper.ProcedureMapper;
import com.procedure.manager.domain.model.ProcedureModel;
import com.procedure.manager.domain.vo.ProcedureCreationDataVo;
import com.procedure.manager.domain.vo.ProcedureTypeVo;
import com.procedure.manager.domain.vo.ProcedureVo;
import com.procedure.manager.domain.vo.UserVo;
import com.procedure.manager.repository.ProcedureRepository;
import com.procedure.manager.service.ProcedureService;
import com.procedure.manager.service.ProcedureTypeService;
import com.procedure.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class ProcedureServiceImpl implements ProcedureService {

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private ProcedureMapper procedureMapper;

    @Autowired
    private ProcedureTypeService procedureTypeService;

    @Autowired
    private UserService userService;

    @Override
    public void registerProcedure(ProcedureCreationDataVo procedureCreationDataVo) {

        ProcedureTypeVo procedureTypeVo = procedureTypeService.getProcedureType(procedureCreationDataVo.getProcedureTypeId());
        UserVo userVo = userService.getUser(procedureCreationDataVo.getUserId());

        ProcedureVo procedureVo = createProcedure(procedureCreationDataVo, procedureTypeVo.getPercentage());

        procedureVo.setUser(userVo);
        procedureVo.setProcedureType(procedureTypeVo);

        procedureRepository.save(procedureMapper.voToModel(procedureVo));

    }

    @Override
    public ProcedureVo getProcedure(Long procedureId) {
        Optional<ProcedureModel> optional = procedureRepository.findById(procedureId);
        if(optional.isEmpty()) {
            throw new DatabaseException(NOT_FOUND, DATABASE_PROCEDURE_DOES_NOT_EXIST);
        }
        return procedureMapper.modelToVo(optional.get());
    }

    @Override
    public List<ProcedureVo> getProcedureListByPeriod(int month, int year, Long userId) {

        LocalDateTime startDate = getStartDate(month, year);
        LocalDateTime endDate = getEndDate(month, year);

        Optional<List<ProcedureModel>> optional = procedureRepository
                .findByRegistrationDateBetweenAndUser_userIdEqualsAndDisabledIsFalseOrderByRegistrationDateAsc(startDate, endDate, userId);
        if(optional.isEmpty()) {
            throw new DatabaseException(NOT_FOUND, DATABASE_PROCEDURE_LIST_DOES_NOT_EXIST);
        }
        return procedureMapper.modelToVoList(optional.get());

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

    private LocalDateTime getStartDate(int month, int year) {
        return getInitialLocalDateTime(month, year);
    }

    private LocalDateTime getEndDate(int month, int year) {
        return getFinalLocalDateTime(month, year);
    }

}
