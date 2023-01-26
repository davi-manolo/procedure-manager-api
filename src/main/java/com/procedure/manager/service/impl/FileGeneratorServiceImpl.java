package com.procedure.manager.service.impl;

import com.procedure.manager.domain.enumeration.Extension;
import com.procedure.manager.domain.mapper.FileGeneratorMapper;
import com.procedure.manager.domain.vo.*;
import com.procedure.manager.service.FileGeneratorService;
import com.procedure.manager.service.ProcedureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@SuppressWarnings("unused")
public class FileGeneratorServiceImpl implements FileGeneratorService {

    @Autowired
    private ProcedureService procedureService;

    @Autowired
    private FileGeneratorMapper fileGeneratorMapper;

    @Override
    public FileVo generateFile(DataSearchProcedureMonthVo dataSearchProcedureMonthVo, Extension extension) {

        List<ProcedureVo> procedureVoList = procedureService.getProcedureListByPeriod(dataSearchProcedureMonthVo);
        BigDecimal totalReceived = procedureService.calculateAmountReceivableByMonth(dataSearchProcedureMonthVo);

        DataContentForFileVo dataContentForFileVo = prepareDataForPrinting(
                dataSearchProcedureMonthVo.getMonth(), totalReceived, procedureVoList
        );

        log.info("Arquivo gerado com a extensão {} foi retornado.", extension.name());

        return extension.getFile(dataContentForFileVo);

    }

    private DataContentForFileVo prepareDataForPrinting(int month, BigDecimal totalReceived, List<ProcedureVo> procedureVoList) {
        return fileGeneratorMapper.toDataContentForFileVo(month, totalReceived, procedureVoList);
    }

}
