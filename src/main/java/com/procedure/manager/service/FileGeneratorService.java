package com.procedure.manager.service;

import com.procedure.manager.domain.enumeration.Extension;
import com.procedure.manager.domain.vo.DataSearchProcedureMonthVo;
import com.procedure.manager.domain.vo.FileVo;

public interface FileGeneratorService {

    FileVo generateFile(DataSearchProcedureMonthVo dataSearchProcedureMonthVo, Extension extension);

}
