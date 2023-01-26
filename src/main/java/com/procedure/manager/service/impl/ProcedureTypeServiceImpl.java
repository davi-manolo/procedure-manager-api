package com.procedure.manager.service.impl;

import com.procedure.manager.domain.exception.DatabaseException;
import com.procedure.manager.domain.mapper.ProcedureTypeMapper;
import com.procedure.manager.domain.model.ProcedureTypeModel;
import com.procedure.manager.domain.vo.ProcedureTypeCreationDataVo;
import com.procedure.manager.domain.vo.ProcedureTypeVo;
import com.procedure.manager.domain.vo.UserVo;
import com.procedure.manager.repository.ProcedureTypeRepository;
import com.procedure.manager.service.ProcedureTypeService;
import com.procedure.manager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.procedure.manager.domain.enumeration.ExceptionMessage.DATABASE_PROCEDURE_TYPE_ALREADY_EXISTS;
import static com.procedure.manager.domain.enumeration.ExceptionMessage.DATABASE_PROCEDURE_TYPE_DOES_NOT_EXIST;
import static com.procedure.manager.domain.enumeration.ExceptionMessage.DATABASE_PROCEDURE_TYPE_LIST_DOES_NOT_EXIST;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
@SuppressWarnings("unused")
public class ProcedureTypeServiceImpl implements ProcedureTypeService {

    @Autowired
    private ProcedureTypeRepository procedureTypeRepository;

    @Autowired
    private ProcedureTypeMapper procedureTypeMapper;

    @Autowired
    private UserService userService;

    @Override
    public void registerProcedureType(ProcedureTypeCreationDataVo procedureTypeCreationDataVo) {
        Optional<ProcedureTypeModel> optional = procedureTypeRepository.findByUser_userIdAndNameIgnoreCase(
                procedureTypeCreationDataVo.getUserId(), procedureTypeCreationDataVo.getName()
        );
        ProcedureTypeVo procedureTypeVo = new ProcedureTypeVo();
        if(optional.isPresent()) {
            ProcedureTypeModel procedureTypeModel = optional.get();
            if(Boolean.TRUE.equals(procedureTypeModel.getDisabled())) {
                procedureTypeVo.setProcedureTypeId(procedureTypeModel.getProcedureTypeId());
                procedureTypeVo.setDisabled(FALSE);
            } else {
                log.error("Tipo de procedimento já existe com o nome {}.", procedureTypeModel.getName());
                throw new DatabaseException(CONFLICT, DATABASE_PROCEDURE_TYPE_ALREADY_EXISTS);
            }
        }
        UserVo userVo = userService.getUser(procedureTypeCreationDataVo.getUserId());
        populateProcedureType(procedureTypeVo, procedureTypeCreationDataVo);
        procedureTypeVo.setUser(userVo);
        procedureTypeRepository.save(procedureTypeMapper.voToModel(procedureTypeVo));
        log.info("Tipo de procedimento foi registrado com o nome {}.", procedureTypeVo.getName());
    }

    @Override
    public ProcedureTypeVo getProcedureType(Long procedureTypeId) {
        Optional<ProcedureTypeModel> optional = procedureTypeRepository.findById(procedureTypeId);
        if(optional.isEmpty()) {
            log.error("Tipo de procedimento não existe com ID {}.", procedureTypeId);
            throw new DatabaseException(NOT_FOUND, DATABASE_PROCEDURE_TYPE_DOES_NOT_EXIST);
        }
        log.info("Tipo de procedimento retornado do banco de dados: {}.", optional.get());
        return procedureTypeMapper.modelToVo(optional.get());
    }

    @Override
    public List<ProcedureTypeVo> getProcedureTypeListByUser(Long userId) {
        Optional<List<ProcedureTypeModel>> optional = procedureTypeRepository.findByUser_userIdAndDisabledIsFalseOrderByNameAsc(userId);
        if(optional.isEmpty()) {
            log.error("Não houve retorno de tipos de procedimentos para o usuário com ID {}.", userId);
            throw new DatabaseException(NOT_FOUND, DATABASE_PROCEDURE_TYPE_LIST_DOES_NOT_EXIST);
        }
        log.info("Lista de tipos de procedimentos retornados para o usuário com ID {}", userId);
        return procedureTypeMapper.modelListToVoList(optional.get());
    }

    @Override
    public void editProcedureType(Long procedureTypeId, ProcedureTypeCreationDataVo procedureTypeCreationDataVo) {
        ProcedureTypeVo procedureTypeVoDatabase = getProcedureType(procedureTypeId);
        procedureTypeVoDatabase.setName(procedureTypeCreationDataVo.getName());
        procedureTypeVoDatabase.setPercentage(procedureTypeCreationDataVo.getPercentage());
        procedureTypeRepository.save(procedureTypeMapper.voToModel(procedureTypeVoDatabase));
    }

    @Override
    public void disableProcedureType(Long procedureTypeId) {
        ProcedureTypeVo procedureTypeVo = getProcedureType(procedureTypeId);
        procedureTypeVo.setDisabled(TRUE);
        procedureTypeRepository.save(procedureTypeMapper.voToModel(procedureTypeVo));
    }

    private void populateProcedureType(ProcedureTypeVo procedureTypeVo, ProcedureTypeCreationDataVo procedureTypeCreationDataVo) {
        procedureTypeVo.setName(procedureTypeCreationDataVo.getName());
        procedureTypeVo.setPercentage(procedureTypeCreationDataVo.getPercentage());
    }

}
