package com.procedure.manager.service;

import com.procedure.manager.domain.vo.ProcedureCreationDataVo;
import com.procedure.manager.domain.vo.ProcedureVo;

import java.util.List;

public interface ProcedureService {

    void registerProcedure(ProcedureCreationDataVo procedureCreationDataVo);

    ProcedureVo getProcedure(Long procedureId);

    List<ProcedureVo> getProcedureListByPeriod(int month, int year, Long userId);

    void editProcedure(Long procedureId, ProcedureCreationDataVo procedureCreationDataVo);

    void disableProcedure(Long procedureId);

}
