package com.procedure.manager.service.impl;

import com.procedure.manager.domain.enumeration.Extension;
import com.procedure.manager.domain.mapper.FileGeneratorMapper;
import com.procedure.manager.domain.vo.DataContentForFileVo;
import com.procedure.manager.domain.vo.DataSearchProcedureMonthVo;
import com.procedure.manager.domain.vo.ProcedureVo;
import com.procedure.manager.service.FileGeneratorService;
import com.procedure.manager.service.ProcedureService;
import com.procedure.manager.service.creator.XlsCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.procedure.manager.util.DataEncoderUtils.encodeContent;

@Service
@SuppressWarnings("unused")
public class FileGeneratorServiceImpl implements FileGeneratorService {

    @Autowired
    private XlsCreator xlsCreator;

    @Autowired
    private ProcedureService procedureService;

    @Autowired
    private FileGeneratorMapper fileGeneratorMapper;

    @Override
    public String generateFile(DataSearchProcedureMonthVo dataSearchProcedureMonthVo, Extension extension) {

        List<ProcedureVo> procedureVoList = procedureService.getProcedureListByPeriod(dataSearchProcedureMonthVo);
        BigDecimal totalReceived = procedureService.calculateAmountReceivableByMonth(dataSearchProcedureMonthVo);

        DataContentForFileVo dataContentForFileVo = prepareDataForPrinting(procedureVoList, totalReceived);

        if(extension.equals(Extension.XLS)) {
            return generateXls(dataContentForFileVo);
        } else {
            return generatePdf(dataContentForFileVo);
        }
    }

    private String generateXls(DataContentForFileVo dataContentForFileVo) {
        byte[] content = xlsCreator.create(dataContentForFileVo);
        return encodeContent(content);
    }

    private String generatePdf(DataContentForFileVo dataContentForFileVo) {
        return null;
    }

    private DataContentForFileVo prepareDataForPrinting(List<ProcedureVo> procedureVoList, BigDecimal totalReceived) {
        return fileGeneratorMapper.toDataContentForFileVo(totalReceived, procedureVoList);
    }

}
