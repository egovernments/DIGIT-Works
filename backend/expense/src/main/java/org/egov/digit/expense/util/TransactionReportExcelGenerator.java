package org.egov.digit.expense.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.web.models.TransactionReport;
import org.egov.digit.expense.web.models.TransactionReportRequest;
import org.egov.digit.expense.web.models.TransactionReportRow;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class TransactionReportExcelGenerator {
    private final LocalizationUtil localizationUtil;
    private final Configuration config;


    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm z");

    @Autowired
    public TransactionReportExcelGenerator(LocalizationUtil localizationUtil, Configuration config) {
        this.localizationUtil = localizationUtil;
        this.config = config;
    }

    public ByteArrayResource generateExcel(TransactionReportRequest reportRequest) throws Exception {

        try {
            TransactionReport reportData = reportRequest.getReport();
            RequestInfo requestInfo = reportRequest.getRequestInfo();
            String tenantId = reportData.getTenantId();

            String locale = localizationUtil.getLocaleCode(requestInfo);
            Map<String, String> localizationMap =
                    localizationUtil.getLocalisedMessages(
                            requestInfo,
                            tenantId,
                            locale,
                            config.getTxnReportLocalisationModule()
                    ).get(locale + "|" + tenantId);

            List<TransactionReportRow> rows = reportData.getTransactions();
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(reportData.getReportTitle()); //todo title and localisations

            /* ----------------------------
             * Styles
             * ---------------------------- */
            XSSFCellStyle headerStyle = createHeaderStyle(workbook);
            XSSFCellStyle SlNoCellStyle = createSlNoCellStyle(workbook);
            XSSFCellStyle amountStyle = createAmountStyle(workbook);
            XSSFCellStyle textStyle = createTextStyle(workbook);

            /* ----------------------------
             * Header Row
             * ---------------------------- */
            Row headerRow = sheet.createRow(0);

            String[] headerKeys = {
                    "EXPENSE_TXN_SERIAL_NO",
                    "EXPENSE_TXN_DATE",
                    "EXPENSE_TXN_BILL_NO",
                    "EXPENSE_TXN_MTN_ID",
                    "EXPENSE_TXN_DESC",
                    "EXPENSE_TXN_DEBIT_AMT"
            }; // todo add localizations for headers

            for (int i = 0; i < headerKeys.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(localizationMap.getOrDefault(headerKeys[i], headerKeys[i]));
                cell.setCellStyle(headerStyle);
            }

            /* ----------------------------
             * Data Rows
             * ---------------------------- */
            int rowIndex = 1;

            for (TransactionReportRow r : rows) {
                Row excelRow = sheet.createRow(rowIndex++);

                excelRow.createCell(0).setCellValue(r.getSlNo());
                excelRow.getCell(0).setCellStyle(SlNoCellStyle);

                excelRow.createCell(1).setCellValue(getFormattedTime(r.getDate()));
                excelRow.getCell(1).setCellStyle(textStyle);

                excelRow.createCell(2).setCellValue(r.getBillNumber());
                excelRow.getCell(2).setCellStyle(textStyle);

                excelRow.createCell(3).setCellValue(r.getMtnTransactionId());
                excelRow.getCell(3).setCellStyle(textStyle);

                excelRow.createCell(4).setCellValue(r.getDescription());
                excelRow.getCell(4).setCellStyle(textStyle);

                excelRow.createCell(5).setCellValue(r.getDebitAmount());
                excelRow.getCell(5).setCellStyle(amountStyle);
            }

            /* ----------------------------
             * Footer Row (Generated Time)
             * ---------------------------- */
            Row footerRowGeneratedTime = sheet.createRow(rowIndex + 2);
            Cell keyCellGeneratedTime = footerRowGeneratedTime.createCell(1);
            Cell valueCellGeneratedTime = footerRowGeneratedTime.createCell(2);
            keyCellGeneratedTime.setCellValue(localizationMap.getOrDefault(
                    "EXPENSE_TXN_GENERATED_TIME",
                    "Generated Time"));
            valueCellGeneratedTime.setCellValue(getFormattedTime(reportData.getGeneratedTime()));
            keyCellGeneratedTime.setCellStyle(textStyle);
            valueCellGeneratedTime.setCellStyle(textStyle);

            sheet.setColumnWidth(0, 8 * 256);   // Sl No
            sheet.setColumnWidth(1, 15 * 256);  // Date
            sheet.setColumnWidth(2, 20 * 256);  // Bill No
            sheet.setColumnWidth(3, 25 * 256);  // MTN ID
            sheet.setColumnWidth(4, 35 * 256);  // Description
            sheet.setColumnWidth(5, 18 * 256);  // Amount

            sheet.createFreezePane(0, 1);

            byte[] bytes;

            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                workbook.write(bos);
                workbook.close();
                bytes = bos.toByteArray();

                return new ByteArrayResource(bytes) {
                    @Override
                    public String getFilename() {
                        return reportData.getReportTitle() + ".xlsx";
                    }
                };

            } catch (IOException e) {
                throw new CustomException("EXCEL_GENERATION_FAILED", e.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getFormattedTime(Long t) {
        return Instant.ofEpochMilli(t)
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm z"));
    }

    /* ----------------------------
     * Styles
     * ---------------------------- */

    private XSSFCellStyle createHeaderStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(10);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);

        XSSFColor customColor = new XSSFColor(new java.awt.Color(201, 218, 248), null);
        style.setFillForegroundColor(customColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    private XSSFCellStyle createSlNoCellStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFColor customColor = new XSSFColor(new java.awt.Color(212, 212, 212), null);
        style.setFillForegroundColor(customColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = workbook.createFont();
        font.setFontHeight(8);
        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private XSSFCellStyle createTextStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setWrapText(true);
        return style;
    }

    private XSSFCellStyle createAmountStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        return style;
    }
}

