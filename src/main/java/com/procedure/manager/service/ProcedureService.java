package com.procedure.manager.service;

import com.procedure.manager.domain.vo.DataSearchProcedureMonthVo;
import com.procedure.manager.domain.vo.ProcedureCreationDataVo;
import com.procedure.manager.domain.vo.ProcedureVo;

import java.math.BigDecimal;
import java.util.List;

public interface ProcedureService {

    void registerProcedure(ProcedureCreationDataVo procedureCreationDataVo);

    ProcedureVo getProcedure(Long procedureId);

    List<ProcedureVo> getProcedureListByPeriod(DataSearchProcedureMonthVo dataSearchProcedureMonthVo);

    void editProcedure(Long procedureId, ProcedureCreationDataVo procedureCreationDataVo);

    void disableProcedure(Long procedureId);

    BigDecimal calculateAmountReceivableByMonth(DataSearchProcedureMonthVo dataSearchProcedureMonthVo);

}
