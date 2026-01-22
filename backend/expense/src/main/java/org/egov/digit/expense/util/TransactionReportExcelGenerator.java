package org.egov.digit.expense.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egov.digit.expense.web.models.TransactionReportRow;
import org.egov.tracer.model.CustomException;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
public class TransactionReportExcelGenerator {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public ByteArrayResource generateExcel(List<TransactionReportRow> rows) throws Exception {
        log.info("inside gennn");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Congo B Transactions List"); //todo title and localisations

        /* ----------------------------
         * Styles
         * ---------------------------- */
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dateStyle = createDateStyle(workbook);
        CellStyle amountStyle = createAmountStyle(workbook);

        /* ----------------------------
         * Header Row
         * ---------------------------- */
        Row headerRow = sheet.createRow(0);

        String[] headers = {
                "Date",
                "Bill Number",
                "MTN Transaction ID",
                "Description",
                "Debit Amount"
        }; // todo add localizations for headers

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        /* ----------------------------
         * Data Rows
         * ---------------------------- */
        int rowIndex = 1;

        for (TransactionReportRow r : rows) {

            Row excelRow = sheet.createRow(rowIndex++);

            excelRow.createCell(0).setCellValue(
                    Instant.ofEpochMilli(r.getDate())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                            .toString()
            );
            excelRow.createCell(1).setCellValue(r.getBillNumber());
            excelRow.createCell(2).setCellValue(r.getMtnTransactionId());
            excelRow.createCell(3).setCellValue(r.getDescription());
            excelRow.createCell(4).setCellValue(r.getDebitAmount().doubleValue());
        }

        byte[] bytes;

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            workbook.write(bos);
            workbook.close();
            bytes = bos.toByteArray();
        } catch (IOException e) {
            throw new CustomException("EXCEL_GENERATION_FAILED", e.getMessage());
        }

        return new ByteArrayResource(bytes) {
            @Override
            public String getFilename() {
                return "CongoB_Transaction_Report.xlsx";
            }
        };
    }

    /* ----------------------------
     * Styles
     * ---------------------------- */

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle createDateStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        return style;
    }

    private CellStyle createAmountStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00"));
        return style;
    }
}

