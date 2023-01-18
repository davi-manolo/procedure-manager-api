package com.procedure.manager.service.creator;

import com.procedure.manager.domain.exception.WorkbookException;
import com.procedure.manager.domain.vo.DataContentProcedureVo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
    private static final String TITLE = "Relatórios de Procedimentos";
    private static final String PROCEDURE_DATE = "Data do Procedimento";
    private static final String CUSTOMER = "Cliente";
    private static final String PROCEDURE_TYPE = "Tipo de Procedimento";
    private static final String PROCEDURE_VALUE = "R$ Valor Procedimento";
    private static final String RECEIVED_VALUE = "R$ Valor Recebido";
    private int lastRow = 0;

    public byte[] create(List<DataContentProcedureVo> dataContentProcedureVoList) {
        try(Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(PROCEDURE_NAME);
            lastRow = sheet.getLastRowNum();
            applyWorksheetSettings(sheet);
            createHeader(sheet);
            createContent(sheet, dataContentProcedureVoList);
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

    private void createHeader(Sheet sheet) {
        Row rowTitle = createRow(sheet);
        Cell cellTitle = rowTitle.createCell(0);
        cellTitle.setCellValue(TITLE);

        Row rowSubtitles = createRow(sheet);

        Cell cellProcedureDate = rowSubtitles.createCell(0);
        cellProcedureDate.setCellValue(PROCEDURE_DATE);

        Cell cellCustomer = rowSubtitles.createCell(1);
        cellCustomer.setCellValue(CUSTOMER);

        Cell cellProcedureType = rowSubtitles.createCell(2);
        cellProcedureType.setCellValue(PROCEDURE_TYPE);

        Cell cellValue = rowSubtitles.createCell(3);
        cellValue.setCellValue(PROCEDURE_VALUE);

        Cell cellValueReceived = rowSubtitles.createCell(4);
        cellValueReceived.setCellValue(RECEIVED_VALUE);
    }

    private void createContent(Sheet sheet, List<DataContentProcedureVo> dataContentProcedureVoList) {

        dataContentProcedureVoList.forEach(contentLine -> {

            Row rowContent = createRow(sheet);

            Cell cellProcedureDate = rowContent.createCell(0);
            cellProcedureDate.setCellValue(contentLine.getProcedureDate());

            Cell cellCustomer = rowContent.createCell(1);
            cellCustomer.setCellValue(contentLine.getCustomer());

            Cell cellProcedureType = rowContent.createCell(2);
            cellProcedureType.setCellValue(contentLine.getProcedureTypeName());

            Cell cellValue = rowContent.createCell(3);
            cellValue.setCellValue(contentLine.getProcedureValue());

            Cell cellValueReceived = rowContent.createCell(4);
            cellValueReceived.setCellValue(contentLine.getValueReceived());

        });
    }

    private Row createRow(Sheet sheet) {
        return sheet.createRow(++lastRow);
    }

}
