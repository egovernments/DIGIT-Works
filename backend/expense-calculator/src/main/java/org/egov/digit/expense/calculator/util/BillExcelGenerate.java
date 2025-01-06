package org.egov.digit.expense.calculator.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.web.models.report.ReportBill;
import org.egov.digit.expense.calculator.web.models.report.ReportBillDetail;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import static org.egov.digit.expense.calculator.util.BillReportConstraints.*;

@Slf4j
@Service
public class BillExcelGenerate {

    private final LocalizationUtil localizationUtil;
    private final ExpenseCalculatorConfiguration config;
    private final FileStoreUtil fileStoreUtil;
    @Autowired
    public BillExcelGenerate(LocalizationUtil localizationUtil, ExpenseCalculatorConfiguration config, FileStoreUtil fileStoreUtil) {
        this.localizationUtil = localizationUtil;
        this.config = config;
        this.fileStoreUtil = fileStoreUtil;
    }
    public String generateExcel(RequestInfo requestInfo, ReportBill reportBill) {

        Map<String, Map<String, String>> localizationMap = localizationUtil.getLocalisedMessages(requestInfo, config.getStateLevelTenantId(), config.getReportLocalizationLocaleCode(), config.getReportLocalizationModuleName());

        ByteArrayResource excelFile = generateExcelFromReportObject(reportBill, localizationMap.get(config.getReportLocalizationLocaleCode() + "|" + config.getStateLevelTenantId()));
        return fileStoreUtil.uploadFileAndGetFileStoreId(config.getStateLevelTenantId(), excelFile);
    }


    private ByteArrayResource generateExcelFromReportObject(ReportBill reportBill, Map<String, String> localizationMap) {
        byte[] excelBytes;

        String[] columns = BILL_EXCEL_COLUMNS; // Default column names
        BigDecimal totalAmountToProcess = reportBill.getTotalAmount();
        String totalNumberOfWorkers = reportBill.getNumberOfIndividuals().toString();
        String campaignName = reportBill.getCampaignName();

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(campaignName);

        // Create styles
        XSSFCellStyle otherHeaderLabelStyle = createOtherHeaderStyle(workbook, true);
        XSSFCellStyle otherHeaderValueStyle = createOtherHeaderStyle(workbook, false);
        XSSFCellStyle titleStyle = createTitleStyle(workbook);
        XSSFCellStyle boldStyle = createBoldStyle(workbook);
        XSSFCellStyle headerStyle = createHeaderStyle(workbook);
        XSSFCellStyle textStyle = createCellStyle(workbook, HorizontalAlignment.LEFT);
        XSSFCellStyle numberStyle = createCellStyle(workbook, HorizontalAlignment.RIGHT);
        XSSFCellStyle slNoStyle = createSlNoCellStyle(workbook);

        int rowNum = 0;

        // Write report header
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue(reportBill.getReportTitle());
        headerRow.getCell(0).setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.length - 1));

        // Write campaign details
        Row campaignRow1 = sheet.createRow(rowNum++);
        CellRangeAddress mergedRegion = new CellRangeAddress(1, 1, 0, 1); // Row 0, Columns 0 to 1
        sheet.addMergedRegion(mergedRegion);
        campaignRow1.createCell(0).setCellValue(localizationMap.getOrDefault(BILL_EXCEL_CAMPAIGN_NAME_LABEL, BILL_EXCEL_CAMPAIGN_NAME_LABEL));
        campaignRow1.getCell(0).setCellStyle(otherHeaderLabelStyle);
        campaignRow1.createCell(2).setCellValue(campaignName);
        campaignRow1.getCell(2).setCellStyle(otherHeaderValueStyle);

        campaignRow1.createCell(3).setCellValue(localizationMap.getOrDefault(BILL_EXCEL_TOTAL_AMOUNT_TO_PROCESS_LABEL, BILL_EXCEL_TOTAL_AMOUNT_TO_PROCESS_LABEL));
        campaignRow1.getCell(3).setCellStyle(otherHeaderLabelStyle);
        campaignRow1.createCell(4).setCellValue(totalAmountToProcess.toPlainString());
        campaignRow1.getCell(4).setCellStyle(otherHeaderValueStyle);

        Row campaignRow2 = sheet.createRow(rowNum++);
        campaignRow2.createCell(0).setCellValue("");
        campaignRow2.getCell(0).setCellStyle(otherHeaderValueStyle);
        campaignRow2.createCell(1).setCellValue("");
        campaignRow2.getCell(1).setCellStyle(otherHeaderValueStyle);
        campaignRow2.createCell(2).setCellValue("");
        campaignRow2.getCell(2).setCellStyle(otherHeaderValueStyle);
        campaignRow2.createCell(3).setCellValue(localizationMap.getOrDefault(BILL_EXCEL_TOTAL_NUMBER_OF_WORKERS_LABEL, BILL_EXCEL_TOTAL_NUMBER_OF_WORKERS_LABEL));
        campaignRow2.getCell(3).setCellStyle(otherHeaderLabelStyle);
        campaignRow2.createCell(4).setCellValue(totalNumberOfWorkers);
        campaignRow2.getCell(4).setCellStyle(otherHeaderValueStyle);
        // fill empty cells
        for (int i = 5; i < columns.length; i++) {
            campaignRow1.createCell(i).setCellValue("");
            campaignRow1.getCell(i).setCellStyle(headerStyle);
            campaignRow2.createCell(i).setCellValue("");
            campaignRow2.getCell(i).setCellStyle(headerStyle);
        }

        // Write column headers
        Row billDetailsHeaderRow = sheet.createRow(rowNum++);
        for (int i = 0; i < columns.length; i++) {
            String columnName = localizationMap.getOrDefault(columns[i], columns[i]);
            Cell cell = billDetailsHeaderRow.createCell(i);
            cell.setCellValue(columnName);
            cell.setCellStyle(headerStyle);
        }

        // Write data rows
        for (ReportBillDetail detail : reportBill.getReportBillDetails()) {
            Row row = sheet.createRow(rowNum++);
            Object[] data = {
                    detail.getSlNo(), detail.getIndividualName(), detail.getRole(), detail.getLocality(),
                    detail.getIdNumber(), detail.getMobileNumber(),
                    detail.getWageAmount(), detail.getFoodAmount(),
                    detail.getTransportAmount(), detail.getTotalWages(),
                    detail.getTotalNumberOfDays(), detail.getTotalAmount()
            };

            for (int i = 0; i < data.length; i++) {
                Cell cell = row.createCell(i);
                if (i == 0) {
                    cell.setCellStyle(slNoStyle);
                    cell.setCellValue(data[i].toString());
                } else {
                    setCellValueWithAlignment(cell, data[i], textStyle, numberStyle);
                }
            }
        }

        // Write total row
        Row billFooterTotalRow = sheet.createRow(rowNum++);
        Cell totalLabelcell = billFooterTotalRow.createCell(columns.length - 2);
        setCellValueWithAlignment(totalLabelcell, localizationMap.getOrDefault(BILL_EXCEL_FOOTER_TOTAL_AMOUNT_LABEL, BILL_EXCEL_FOOTER_TOTAL_AMOUNT_LABEL), textStyle, numberStyle);
        Cell totalAmountCell = billFooterTotalRow.createCell(columns.length - 1);
        setCellValueWithAlignment(totalAmountCell, reportBill.getTotalAmount(), textStyle, numberStyle);

        Row billFooterSignerRow = sheet.createRow(rowNum++);
        Cell signerCellPreparedBy = billFooterSignerRow.createCell(1);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 1, 3));
        setCellValueWithAlignment(signerCellPreparedBy, localizationMap.getOrDefault(BILL_EXCEL_FOOTER_PYMT_ADV_PREPARED_BY, BILL_EXCEL_FOOTER_PYMT_ADV_PREPARED_BY), textStyle, numberStyle);

        Cell signerCellVerifiedBy = billFooterSignerRow.createCell(4);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 4, 6));
        setCellValueWithAlignment(signerCellVerifiedBy, localizationMap.getOrDefault(BILL_EXCEL_FOOTER_PYMT_ADV_VERIFIED_BY, BILL_EXCEL_FOOTER_PYMT_ADV_VERIFIED_BY), textStyle, numberStyle);

        Cell signerCellApprovedBy = billFooterSignerRow.createCell(9);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 9, 11));
        setCellValueWithAlignment(signerCellApprovedBy, localizationMap.getOrDefault(BILL_EXCEL_FOOTER_PYMT_ADV_APPROVED_BY, BILL_EXCEL_FOOTER_PYMT_ADV_APPROVED_BY), textStyle, numberStyle);
        sheet.createRow(rowNum++);
        sheet.createRow(rowNum++);
        sheet.createRow(rowNum++);

        Row billFooterAuditDateTime = sheet.createRow(rowNum++);
        Cell auditCellDateAndTimeLabel = billFooterAuditDateTime.createCell(1);
        setCellValueWithAlignment(auditCellDateAndTimeLabel, localizationMap.getOrDefault(BILL_EXCEL_FOOTER_REPORT_RUN_DT_TIME, BILL_EXCEL_FOOTER_REPORT_RUN_DT_TIME), textStyle, numberStyle);

        Cell auditCellDateAndTimeValue = billFooterAuditDateTime.createCell(2);
        setCellValueWithAlignment(auditCellDateAndTimeValue, getFormatedDateTime(reportBill.getCreatedTime()), textStyle, numberStyle);

        Row billFooterAuditCreatedBy = sheet.createRow(rowNum++);
        Cell auditCellCreatedByLabel = billFooterAuditCreatedBy.createCell(1);
        setCellValueWithAlignment(auditCellCreatedByLabel, localizationMap.getOrDefault(BILL_EXCEL_FOOTER_REPORT_RUN_BY, BILL_EXCEL_FOOTER_REPORT_RUN_BY), textStyle, numberStyle);

        Cell auditCellCreatedByValue = billFooterAuditCreatedBy.createCell(2);
        setCellValueWithAlignment(auditCellCreatedByValue, reportBill.getCreatedBy(), textStyle, numberStyle);


        // Adjust column widths
        for (int i = 0; i < BILL_EXCEL_COLUMN_WIDTH.length; i++) {
            sheet.setColumnWidth(i, BILL_EXCEL_COLUMN_WIDTH[i]);
        }

        sheet.protectSheet(campaignName);
        /*
        // This code is for local testing, it Saves the file to the specified path
        try (FileOutputStream outputStream = new FileOutputStream("Sample.xlsx")) {
            workbook.write(outputStream);
            workbook.close();
            log.info("Excel file saved successfully at: " + "Sample.xlsx");
        } catch (IOException e) {
            log.error("Exception while saving Excel file: " + e);
            throw new CustomException("EXCEL_SAVE_ERROR", "Exception while saving Excel file to local system");
        }
        return null;

         */
        // Write to ByteArrayResource
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
            excelBytes = outputStream.toByteArray();
        } catch (IOException e) {
            log.error("Exception while writing workbook to output stream: " + e);
            throw new CustomException("EXCEL_GENERATION_ERROR", "Exception while writing workbook to output stream");
        }

        return new ByteArrayResource(excelBytes) {
            @Override
            public String getFilename() {
                return campaignName + ".xlsx";
            }
        };
    }

    private XSSFCellStyle createBoldStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(10);
        style.setFont(font);
        return style;
    }

    private XSSFCellStyle createTitleStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(10);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFColor customColor = new XSSFColor(new java.awt.Color(252, 229, 205), null);
        style.setFillForegroundColor(customColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private XSSFCellStyle createOtherHeaderStyle(XSSFWorkbook workbook, Boolean isValue) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(10);
        if (isValue) {
            style.setFont(font);
        }
        style.setAlignment(HorizontalAlignment.LEFT);
        XSSFColor customColor = new XSSFColor(new java.awt.Color(236, 255, 220), null);
        style.setFillForegroundColor(customColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private XSSFCellStyle createHeaderStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFColor customColor = new XSSFColor(new java.awt.Color(201, 218, 248), null);
        style.setFillForegroundColor(customColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(createBoldStyle(workbook).getFont());
        return style;
    }

    private XSSFCellStyle createSlNoCellStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFColor customColor = new XSSFColor(new java.awt.Color(212, 212, 212), null);
        style.setFillForegroundColor(customColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font = workbook.createFont();
        font.setFontHeight(8);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private XSSFCellStyle createCellStyle(XSSFWorkbook workbook, HorizontalAlignment alignment) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(8);
        style.setAlignment(alignment);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }


    private void setCellValueWithAlignment(Cell cell, Object value, XSSFCellStyle textStyle, XSSFCellStyle numberStyle) {
        if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).toPlainString());
            cell.setCellStyle(numberStyle);
        } else if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
            cell.setCellStyle(numberStyle);
        } else {
            cell.setCellValue(value != null ? value.toString() : "");
            cell.setCellStyle(textStyle);
        }
    }

    private String getFormatedDateTime(long timestamp) {
        // DateTimeFormatter for the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(config.getReportDateTimeFormat())
                .withZone(ZoneId.of(config.getReportDateTimeZone()));
        // Convert epoch to formatted date-time
        return formatter.format(Instant.ofEpochMilli(timestamp));
    }

}
