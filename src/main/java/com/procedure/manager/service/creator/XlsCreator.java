package com.procedure.manager.service.creator;

import com.procedure.manager.domain.exception.WorkbookException;
import com.procedure.manager.domain.vo.DataContentForFileVo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.procedure.manager.domain.enumeration.ExceptionMessage.WORKBOOK_CONVERT_TO_ARRAY_ERROR;
import static com.procedure.manager.domain.enumeration.ExceptionMessage.WORKBOOK_IO_ERROR;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
public class XlsCreator {

    private static final String PROCEDURE_NAME = "Procedimentos";
    private static final String TITLE = "Relatório de Procedimentos";
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
            createHeader(sheet, dataContentForFileVo.getMonth());
            createContent(sheet, dataContentForFileVo);
            createFooter(sheet, dataContentForFileVo);
            return workbookToByteArray(workbook);
        } catch (IOException e) {
            throw new WorkbookException(INTERNAL_SERVER_ERROR, WORKBOOK_IO_ERROR);
        }
    }

    private void applyWorksheetSettings(Sheet sheet) {
        sheet.setColumnWidth(0, 25 * 256);
        sheet.setColumnWidth(1, 35 * 256);
        sheet.setColumnWidth(2, 25 * 256);
        sheet.setColumnWidth(3, 25 * 256);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setDefaultRowHeight((short) 600);
    }

    private void createHeader(Sheet sheet, String monthOfProcedure) {

        Row rowTitle = createRow(sheet);
        createCell(rowTitle, 0, format("%s - %s", TITLE, monthOfProcedure),
                titlesStyle(sheet.getWorkbook(), 14));
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));

        Row rowSubtitles = createRow(sheet);
        createCell(rowSubtitles, 0, PROCEDURE_DATE, titlesStyle(sheet.getWorkbook(), 12));
        createCell(rowSubtitles, 1, CUSTOMER, titlesStyle(sheet.getWorkbook(), 12));
        createCell(rowSubtitles, 2, PROCEDURE_TYPE, titlesStyle(sheet.getWorkbook(), 12));
        createCell(rowSubtitles, 3, PROCEDURE_VALUE, titlesStyle(sheet.getWorkbook(), 12));
        createCell(rowSubtitles, 4, RECEIVED_VALUE, titlesStyle(sheet.getWorkbook(), 12));

    }

    private void createContent(Sheet sheet, DataContentForFileVo dataContentForFileVo) {

        dataContentForFileVo.getProcedureList().forEach(contentLine -> {

            Row rowContent = createRow(sheet);
            createCell(rowContent, 0, contentLine.getProcedureDate(), contentStyle(sheet.getWorkbook()));
            createCell(rowContent, 1, contentLine.getProcedureCustomer(), contentStyle(sheet.getWorkbook()));
            createCell(rowContent, 2, contentLine.getProcedureType(), contentStyle(sheet.getWorkbook()));
            createCell(rowContent, 3, contentLine.getProcedureValue(), contentStyle(sheet.getWorkbook()));
            createCell(rowContent, 4, contentLine.getValueReceived(), contentStyle(sheet.getWorkbook()));

        });
    }

    private void createFooter(Sheet sheet, DataContentForFileVo dataContentForFileVo) {

        Row footerContent = createRow(sheet);
        createCell(footerContent, 3, RECEIVED_TOTAL, titlesStyle(sheet.getWorkbook(), 12));
        createCell(footerContent, 4, dataContentForFileVo.getTotalReceived(), titlesStyle(sheet.getWorkbook(), 12));

    }

    private Row createRow(Sheet sheet) {
        return sheet.createRow(++lastRow);
    }

    private Cell createCell(Row row, int cellIndex, String cellContent) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(cellContent);
        return cell;
    }

    private void createCell(Row row, int cellIndex, String cellContent, CellStyle style) {
        Cell cell = createCell(row, cellIndex, cellContent);
        cell.setCellStyle(style);
    }

    private byte[] workbookToByteArray(Workbook workbook) {
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            workbook.write(bos);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new WorkbookException(INTERNAL_SERVER_ERROR, WORKBOOK_CONVERT_TO_ARRAY_ERROR);
        }
    }

    private static CellStyle titlesStyle(Workbook workbook, int fontSize) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);

        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) fontSize);
        cellStyle.setFont(font);
        return cellStyle;
    }

    private static CellStyle contentStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        return cellStyle;
    }

}
