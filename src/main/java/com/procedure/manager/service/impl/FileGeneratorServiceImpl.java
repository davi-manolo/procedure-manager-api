package com.procedure.manager.service.impl;

import com.procedure.manager.domain.enumeration.Extension;
import com.procedure.manager.domain.vo.DataContentProcedureVo;
import com.procedure.manager.service.FileGeneratorService;
import com.procedure.manager.service.creator.XlsCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.procedure.manager.util.DataEncoderUtils.encodeContent;

@Service
public class FileGeneratorServiceImpl implements FileGeneratorService {

    @Autowired
    private XlsCreator xlsCreator;

    @Override
    public String generateFile(List<DataContentProcedureVo> dataContentProcedureVoList, Extension extension) {
        if(extension.name().equals("XLS")) {
            return generateXls(dataContentProcedureVoList);
        } else {
            return generatePdf(dataContentProcedureVoList);
        }
    }

    private String generateXls(List<DataContentProcedureVo> dataContentProcedureVoList) {
        byte[] content = xlsCreator.create(dataContentProcedureVoList);
        return encodeContent(content);
    }

    private String generatePdf(List<DataContentProcedureVo> dataContentProcedureVoList) {
        return null;
    }

}
