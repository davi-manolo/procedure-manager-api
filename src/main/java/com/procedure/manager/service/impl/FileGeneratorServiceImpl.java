package com.procedure.manager.service.impl;

import com.procedure.manager.domain.enumeration.Extension;
import com.procedure.manager.domain.mapper.FileGeneratorMapper;
import com.procedure.manager.domain.vo.DataContentForFileVo;
import com.procedure.manager.domain.vo.DataSearchProcedureMonthVo;
import com.procedure.manager.domain.vo.FileVo;
import com.procedure.manager.domain.vo.ProcedureVo;
import com.procedure.manager.service.FileGeneratorService;
import com.procedure.manager.service.ProcedureService;
import com.procedure.manager.service.creator.XlsCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.procedure.manager.util.DataEncoderUtils.encodeContent;
import static com.procedure.manager.util.DateUtils.getLocalDateTimeFormattedNow;

@Service
@SuppressWarnings("unused")
public class FileGeneratorServiceImpl implements FileGeneratorService {

    @Autowired
    private XlsCreator xlsCreator;

    @Autowired
    private ProcedureService procedureService;

    @Autowired
    private FileGeneratorMapper fileGeneratorMapper;

    private static final String PROCEDURE_REPORT = "Relatorio_Procedimentos";

    @Override
    public FileVo generateFile(DataSearchProcedureMonthVo dataSearchProcedureMonthVo, Extension extension) {

        List<ProcedureVo> procedureVoList = procedureService.getProcedureListByPeriod(dataSearchProcedureMonthVo);
        BigDecimal totalReceived = procedureService.calculateAmountReceivableByMonth(dataSearchProcedureMonthVo);

        DataContentForFileVo dataContentForFileVo = prepareDataForPrinting(
                dataSearchProcedureMonthVo.getMonth(), totalReceived, procedureVoList
        );

        if(extension.equals(Extension.XLS)) {
            return generateXls(dataContentForFileVo);
        } else {
            return generatePdf(dataContentForFileVo);
        }
    }

    private FileVo generateXls(DataContentForFileVo dataContentForFileVo) {
        byte[] content = xlsCreator.create(dataContentForFileVo);
        String contentEncoded = encodeContent(content);
        return fileBuilder(contentEncoded);
    }

    private FileVo generatePdf(DataContentForFileVo dataContentForFileVo) {
        return null;
    }

    private DataContentForFileVo prepareDataForPrinting(int month, BigDecimal totalReceived, List<ProcedureVo> procedureVoList) {
        return fileGeneratorMapper.toDataContentForFileVo(month, totalReceived, procedureVoList);
    }

    private FileVo fileBuilder(String contentEncoded) {
        return FileVo.builder()
                .title(String.format("%s_%s", PROCEDURE_REPORT, getLocalDateTimeFormattedNow()))
                .contentBase64(contentEncoded)
                .build();
    }

}
