package com.procedure.manager.service;

import com.procedure.manager.domain.vo.ProcedureTypeVo;

import java.util.List;

public interface ProcedureTypeService {

    void registerProcedureType(ProcedureTypeVo procedureTypeVo);

    ProcedureTypeVo getProcedureType(Long procedureTypeId);

    List<ProcedureTypeVo> getProcedureTypeList();

    void editProcedureType(ProcedureTypeVo procedureTypeVo);

    void disableProcedureType(Long procedureTypeId);

}
