package com.procedure.manager.service.creator;

import com.procedure.manager.domain.exception.WorkbookException;
import com.procedure.manager.domain.vo.DataContentProcedureVo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static com.procedure.manager.domain.enumeration.ExceptionMessage.WORKBOOK_CONVERT_TO_ARRAY_ERROR;
import static com.procedure.manager.domain.enumeration.ExceptionMessage.WORKBOOK_IO_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
public class XlsCreator {

    private static final String PROCEDURE_NAME = "Procedimentos";

    public byte[] create(List<DataContentProcedureVo> dataContentProcedureVoList) {
        try(Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(PROCEDURE_NAME);
            applyWorksheetSettings(sheet);
            return workbookToByteArray(workbook);
        } catch (IOException e) {
            throw new WorkbookException(INTERNAL_SERVER_ERROR, WORKBOOK_IO_ERROR);
        }
    }

    private byte[] workbookToByteArray(Workbook workbook) {
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            workbook.write(bos);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new WorkbookException(INTERNAL_SERVER_ERROR, WORKBOOK_CONVERT_TO_ARRAY_ERROR);
        }
    }

    private void applyWorksheetSettings(Sheet sheet) {
        sheet.setDefaultColumnWidth(20);
        sheet.setDefaultRowHeight((short) 600);
    }
}
