package com.procedure.manager.service;

import com.procedure.manager.domain.mapper.FileGeneratorMapper;
import com.procedure.manager.domain.vo.DataContentForFileVo;
import com.procedure.manager.domain.vo.DataSearchProcedureMonthVo;
import com.procedure.manager.domain.vo.FileVo;
import com.procedure.manager.domain.vo.ProcedureVo;
import com.procedure.manager.service.impl.FileGeneratorServiceImpl;
import com.procedure.manager.service.impl.ProcedureServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.procedure.manager.domain.enumeration.Extension;

import java.math.BigDecimal;
import java.util.List;

import static com.procedure.manager.domain.mother.FileGeneratorMother.getDataContentForFileVo;
import static com.procedure.manager.domain.mother.ProcedureMother.getDataSearchProcedureMonthVo;
import static com.procedure.manager.domain.mother.ProcedureMother.getProcedureVoList;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class FileGeneratorServiceUnitTest {

    @InjectMocks
    private FileGeneratorServiceImpl fileGeneratorService;

    @Mock
    private ProcedureServiceImpl procedureService;

    @Mock
    private FileGeneratorMapper fileGeneratorMapper;

    private BigDecimal totalReceived;
    private Extension extension;
    private int month;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.totalReceived = BigDecimal.valueOf(480.0);
        this.extension = Extension.XLS;
        this.month = 1;
    }

    @Test
    void givenDataSearchProcedureMonthVoWhenCallGenerateFileThenReturnFileVo() {

        DataSearchProcedureMonthVo dataSearchProcedureMonthVo = getDataSearchProcedureMonthVo();
        List<ProcedureVo> procedureVoList = getProcedureVoList();
        DataContentForFileVo dataContentForFileVo = getDataContentForFileVo();

        when(procedureService.getProcedureListByPeriod(dataSearchProcedureMonthVo)).thenReturn(procedureVoList);
        when(procedureService.calculateAmountReceivableByMonth(dataSearchProcedureMonthVo)).thenReturn(totalReceived);
        when(fileGeneratorMapper.toDataContentForFileVo(month, totalReceived, procedureVoList)).thenReturn(dataContentForFileVo);

        FileVo result = fileGeneratorService.generateFile(dataSearchProcedureMonthVo, extension);

        assertNotNull(result.getTitle());
        assertNotNull(result.getContentBase64());

        verify(procedureService).getProcedureListByPeriod(dataSearchProcedureMonthVo);
        verify(procedureService).calculateAmountReceivableByMonth(dataSearchProcedureMonthVo);
        verify(fileGeneratorMapper).toDataContentForFileVo(month, totalReceived, procedureVoList);

    }

}
