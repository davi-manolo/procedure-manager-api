package com.procedure.manager.service;

import com.procedure.manager.domain.enumeration.Extension;
import com.procedure.manager.domain.vo.DataContentProcedureVo;

import java.util.List;

public interface FileGeneratorService {

    String generateFile(List<DataContentProcedureVo> dataContentProcedureVoList, Extension extension);

}
