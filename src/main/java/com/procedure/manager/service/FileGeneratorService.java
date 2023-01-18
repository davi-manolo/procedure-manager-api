package com.procedure.manager.service;

import com.procedure.manager.domain.enumeration.Extension;
import com.procedure.manager.domain.vo.DataSearchProcedureMonthVo;

import java.util.List;

public interface FileGeneratorService {

    String generateFile(DataSearchProcedureMonthVo dataSearchProcedureMonthVo, Extension extension);

}
