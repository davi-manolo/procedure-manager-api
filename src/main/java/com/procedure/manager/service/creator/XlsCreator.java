package com.procedure.manager.service.creator;

import com.procedure.manager.domain.exception.WorkbookException;
import com.procedure.manager.domain.vo.DataContentForFileVo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

    private static final String RECEIVED_TOTAL = "Total a Receber:";
    private int lastRow = 0;

    public byte[] create(DataContentForFileVo dataContentForFileVo) {
        try(Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(PROCEDURE_NAME);
            lastRow = sheet.getLastRowNum();
            applyWorksheetSettings(sheet);
            createHeader(sheet);
            createContent(sheet, dataContentForFileVo);
            createFooter(sheet, dataContentForFileVo);
            return workbookToByteArray(workbook);
        } catch (IOException e) {
            throw new WorkbookException(INTERNAL_SERVER_ERROR, WORKBOOK_IO_ERROR);
        }
    }

    private void applyWorksheetSettings(Sheet sheet) {
        sheet.setDefaultColumnWidth(20);
        sheet.setDefaultRowHeight((short) 600);
    }

    private void createHeader(Sheet sheet) {

        Row rowTitle = createRow(sheet);
        createCell(rowTitle, 0, TITLE);

        Row rowSubtitles = createRow(sheet);
        createCell(rowSubtitles, 0, PROCEDURE_DATE);
        createCell(rowSubtitles, 1, CUSTOMER);
        createCell(rowSubtitles, 2, PROCEDURE_TYPE);
        createCell(rowSubtitles, 3, PROCEDURE_VALUE);
        createCell(rowSubtitles, 4, RECEIVED_VALUE);

    }

    private void createContent(Sheet sheet, DataContentForFileVo dataContentForFileVo) {

        dataContentForFileVo.getProcedureList().forEach(contentLine -> {

            Row rowContent = createRow(sheet);
            createCell(rowContent, 0, contentLine.getProcedureDate());
            createCell(rowContent, 1, contentLine.getProcedureCustomer());
            createCell(rowContent, 2, contentLine.getProcedureType());
            createCell(rowContent, 3, contentLine.getProcedureValue());
            createCell(rowContent, 4, contentLine.getValueReceived());

        });
    }

    private void createFooter(Sheet sheet, DataContentForFileVo dataContentForFileVo) {

        Row footerContent = createRow(sheet);
        createCell(footerContent, 3, RECEIVED_TOTAL);
        createCell(footerContent, 4, dataContentForFileVo.getTotalReceived());

    }

    private Row createRow(Sheet sheet) {
        return sheet.createRow(++lastRow);
    }

    private void createCell(Row row, int cellIndex, String cellContent) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(cellContent);
    }

    private byte[] workbookToByteArray(Workbook workbook) {
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            workbook.write(bos);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new WorkbookException(INTERNAL_SERVER_ERROR, WORKBOOK_CONVERT_TO_ARRAY_ERROR);
        }
    }

}
