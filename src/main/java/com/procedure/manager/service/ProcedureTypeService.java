package com.procedure.manager.service;

import com.procedure.manager.domain.vo.ProcedureTypeCreationDataVo;
import com.procedure.manager.domain.vo.ProcedureTypeVo;

import java.util.List;

public interface ProcedureTypeService {

    void registerProcedureType(ProcedureTypeCreationDataVo procedureTypeCreationDataVo);

    ProcedureTypeVo getProcedureType(Long procedureTypeId);

    List<ProcedureTypeVo> getProcedureTypeListByUser(Long userId);

    void editProcedureType(Long procedureTypeId, ProcedureTypeCreationDataVo procedureTypeCreationDataVo);

    void disableProcedureType(Long procedureTypeId);

}
