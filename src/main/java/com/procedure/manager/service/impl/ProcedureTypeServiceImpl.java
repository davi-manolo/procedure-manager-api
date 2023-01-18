package com.procedure.manager.service.impl;

import com.procedure.manager.domain.exception.DatabaseException;
import com.procedure.manager.domain.mapper.ProcedureTypeMapper;
import com.procedure.manager.domain.model.ProcedureTypeModel;
import com.procedure.manager.domain.vo.ProcedureTypeVo;
import com.procedure.manager.repository.ProcedureTypeRepository;
import com.procedure.manager.service.ProcedureTypeService;
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

@Service
@SuppressWarnings("unused")
public class ProcedureTypeServiceImpl implements ProcedureTypeService {

    @Autowired
    private ProcedureTypeRepository procedureTypeRepository;

    @Autowired
    private ProcedureTypeMapper procedureTypeMapper;

    @Override
    public void registerProcedureType(ProcedureTypeVo procedureTypeVo) {
        Optional<ProcedureTypeModel> optional = procedureTypeRepository.findByNameIgnoreCase(procedureTypeVo.getName());
        if(optional.isPresent()) {
            ProcedureTypeModel procedureTypeModel = optional.get();
            if(Boolean.TRUE.equals(procedureTypeModel.getDisabled())) {
                procedureTypeVo.setProcedureTypeId(procedureTypeModel.getProcedureTypeId());
                procedureTypeVo.setDisabled(FALSE);
            } else {
                throw new DatabaseException(CONFLICT, DATABASE_PROCEDURE_TYPE_ALREADY_EXISTS);
            }
        }
        procedureTypeRepository.save(procedureTypeMapper.voToModel(procedureTypeVo));
    }

    @Override
    public ProcedureTypeVo getProcedureType(Long procedureTypeId) {
        Optional<ProcedureTypeModel> optional = procedureTypeRepository.findById(procedureTypeId);
        if(optional.isEmpty()) {
            throw new DatabaseException(NOT_FOUND, DATABASE_PROCEDURE_TYPE_DOES_NOT_EXIST);
        }
        return procedureTypeMapper.modelToVo(optional.get());
    }

    @Override
    public List<ProcedureTypeVo> getProcedureTypeList() {
        Optional<List<ProcedureTypeModel>> optional = procedureTypeRepository.findByDisabledIsFalseOrderByNameAsc();
        if(optional.isEmpty()) {
            throw new DatabaseException(NOT_FOUND, DATABASE_PROCEDURE_TYPE_LIST_DOES_NOT_EXIST);
        }
        return procedureTypeMapper.modelListToVoList(optional.get());
    }

    @Override
    public void editProcedureType(ProcedureTypeVo procedureTypeVo) {
        ProcedureTypeVo procedureTypeVoDatabase = getProcedureType(procedureTypeVo.getProcedureTypeId());
        procedureTypeVoDatabase.setName(procedureTypeVo.getName());
        procedureTypeVoDatabase.setPercentage(procedureTypeVo.getPercentage());
        procedureTypeRepository.save(procedureTypeMapper.voToModel(procedureTypeVoDatabase));
    }

    @Override
    public void disableProcedureType(Long procedureTypeId) {
        ProcedureTypeVo procedureTypeVo = getProcedureType(procedureTypeId);
        procedureTypeVo.setDisabled(TRUE);
        procedureTypeRepository.save(procedureTypeMapper.voToModel(procedureTypeVo));
    }

}
