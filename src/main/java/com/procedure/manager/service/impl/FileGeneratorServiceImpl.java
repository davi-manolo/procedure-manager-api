package com.procedure.manager.service.impl;

import com.procedure.manager.domain.vo.DataContentProcedureVo;
import com.procedure.manager.service.FileGeneratorService;
import com.procedure.manager.service.creator.XlsCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileGeneratorServiceImpl implements FileGeneratorService {

    @Autowired
    private XlsCreator xlsCreator;

    @Override
    public String generateFile(List<DataContentProcedureVo> procedureVoList, String extension) {
        return null;
    }

}
