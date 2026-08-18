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
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.web.models.report.ReportBill;
import org.egov.digit.expense.calculator.web.models.report.ReportBillDetail;
import org.egov.digit.expense.calculator.web.models.report.ReportSignature;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import org.egov.digit.expense.calculator.web.models.RateFieldConfig;

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

    /**
     * Generates an excel file from the given report bill object.
     *
     * @param requestInfo the request info
     * @param reportBill  the report bill object
     * @return the file store id of the generated excel file
     */
    public String generateExcel(RequestInfo requestInfo, ReportBill reportBill) {
        // Get local code from request info
        String localCode = localizationUtil.getLocalCode(requestInfo);
        Map<String, Map<String, String>> localizationMap = localizationUtil.getLocalisedMessages(requestInfo, config.getStateLevelTenantId(), localCode, config.getReportLocalizationModuleName());

        ByteArrayResource excelFile = generateExcelFromReportObject(reportBill, localizationMap.get(localCode + "|" + config.getStateLevelTenantId()));
        return fileStoreUtil.uploadFileAndGetFileStoreId(config.getStateLevelTenantId(), excelFile);
    }

    /**
     * Generate an excel file from the given report object
     * @param reportBill the report object using which the excel is generated
     * @param localizationMap the localization map which will replace header labels
     * @return the excel file binary
     */
    private ByteArrayResource generateExcelFromReportObject(ReportBill reportBill, Map<String, String> localizationMap) {
        byte[] excelBytes;

        // Use fieldConfigs from reportBill for ordered column header localization keys
        List<RateFieldConfig> fieldConfigs = reportBill.getFieldConfigs() != null
                ? reportBill.getFieldConfigs() : Collections.emptyList();

        // Build column headers: fixed prefix + per-day amounts + totalWages + days + total-per-field + totalAmount
        // Structure: [slNo, name, role, boundary, id, mobile] + [perDay_1..N] + [totalWages, days] + [total_1..N] + [totalAmountToPay]
        List<String> columnList = new ArrayList<>(Arrays.asList(
                "PDF_STATIC_LABEL_BILL_TABLE_SERIAL_NUMBER", "PDF_STATIC_LABEL_BILL_TABLE_INDIVIDUAL_NAME",
                "PDF_STATIC_LABEL_BILL_TABLE_ROLE", "PDF_STATIC_LABEL_BILL_TABLE_BOUNDARY_NAME",
                "PDF_STATIC_LABEL_BILL_TABLE_ID_NUMBER", "PDF_STATIC_LABEL_BILL_TABLE_MOBILE_NUMBER"
        ));
        fieldConfigs.forEach(cfg -> columnList.add(
                cfg.getColumnLabelKey() != null ? cfg.getColumnLabelKey() : cfg.getReportDetailKey()));
        columnList.add("PDF_STATIC_LABEL_BILL_TABLE_TOTAL_WAGE");
        columnList.add("PDF_STATIC_LABEL_BILL_TABLE_NUMBER_OF_DAYS");
        fieldConfigs.forEach(cfg -> columnList.add(
                cfg.getTotalColumnLabelKey() != null ? cfg.getTotalColumnLabelKey() : cfg.getReportDetailKey()));
        columnList.add("PDF_STATIC_LABEL_BILL_TABLE_TOTAL_AMOUNT_TO_PAY");

        // Ordered reportDetailKey list used for data rows and footer
        List<String> dynamicKeys = fieldConfigs.stream()
                .map(RateFieldConfig::getReportDetailKey)
                .collect(Collectors.toList());
        String[] columns = columnList.toArray(new String[0]);
        BigDecimal totalAmountToProcess = reportBill.getTotalAmount();
        String totalNumberOfWorkers = reportBill.getNumberOfIndividuals().toString();
        String campaignName = reportBill.getCampaignName();
        String billingPeriodLabel = reportBill.getBillingPeriodLabel() != null ? reportBill.getBillingPeriodLabel() : "";
        String billingPeriodRange = reportBill.getBillingPeriodDateRange() != null ? reportBill.getBillingPeriodDateRange() : "";

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(campaignName);

        // Create styles
        XSSFCellStyle otherHeaderLabelStyle = createOtherHeaderStyle(workbook, false, false);
        XSSFCellStyle otherHeaderTxtValueStyle = createOtherHeaderStyle(workbook, true, false);
        XSSFCellStyle otherHeaderNumValueStyle = createOtherHeaderStyle(workbook, false, true);
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
        campaignRow1.getCell(2).setCellStyle(otherHeaderTxtValueStyle);

        campaignRow1.createCell(3).setCellValue(localizationMap.getOrDefault(BILL_EXCEL_TOTAL_AMOUNT_TO_PROCESS_LABEL, BILL_EXCEL_TOTAL_AMOUNT_TO_PROCESS_LABEL));
        campaignRow1.getCell(3).setCellStyle(otherHeaderLabelStyle);
        campaignRow1.createCell(4).setCellValue(totalAmountToProcess.setScale(2, RoundingMode.HALF_UP).toPlainString());
        campaignRow1.getCell(4).setCellStyle(otherHeaderNumValueStyle);

        Row campaignRow2 = sheet.createRow(rowNum++);
        campaignRow2.createCell(0).setCellValue(localizationMap.getOrDefault(BILL_EXCEL_BILLING_PERIOD_LABEL, BILL_EXCEL_BILLING_PERIOD_LABEL));
        campaignRow2.getCell(0).setCellStyle(otherHeaderLabelStyle);
        campaignRow2.createCell(1).setCellValue(billingPeriodLabel);
        campaignRow2.getCell(1).setCellStyle(otherHeaderTxtValueStyle);
        campaignRow2.createCell(2).setCellValue(localizationMap.getOrDefault(BILL_EXCEL_BILLING_PERIOD_RANGE_LABEL, BILL_EXCEL_BILLING_PERIOD_RANGE_LABEL));
        campaignRow2.getCell(2).setCellStyle(otherHeaderLabelStyle);
        campaignRow2.createCell(3).setCellValue(billingPeriodRange);
        campaignRow2.getCell(3).setCellStyle(otherHeaderTxtValueStyle);
        campaignRow2.createCell(4).setCellValue(localizationMap.getOrDefault(BILL_EXCEL_TOTAL_NUMBER_OF_WORKERS_LABEL, BILL_EXCEL_TOTAL_NUMBER_OF_WORKERS_LABEL));
        campaignRow2.getCell(4).setCellStyle(otherHeaderLabelStyle);
        campaignRow2.createCell(5).setCellValue(totalNumberOfWorkers);
        campaignRow2.getCell(5).setCellStyle(otherHeaderNumValueStyle);

        // fill empty cells of header with green background (same as other header cells)
        // campaignRow1 has cells 0-4 created, campaignRow2 has cells 0-5 created
        // Fill remaining cells (5 onwards for row1, 6 onwards for row2) with green style
        for (int i = 5; i < columns.length; i++) {
            campaignRow1.createCell(i).setCellValue("");
            campaignRow1.getCell(i).setCellStyle(otherHeaderLabelStyle);
        }
        for (int i = 6; i < columns.length; i++) {
            campaignRow2.createCell(i).setCellValue("");
            campaignRow2.getCell(i).setCellStyle(otherHeaderLabelStyle);
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
            BigDecimal totalDays = BigDecimal.valueOf(detail.getTotalNumberOfDays() != null ? detail.getTotalNumberOfDays() : 0f);

            List<Object> data = new ArrayList<>(Arrays.asList(
                    detail.getSlNo(), detail.getIndividualName(), detail.getRole(), detail.getLocality(),
                    detail.getIdNumber(), detail.getMobileNumber()
            ));
            // Per-day amounts for each dynamic field
            for (String key : dynamicKeys) {
                data.add(detail.getPerDayBreakup().getOrDefault(key, BigDecimal.ZERO));
            }
            data.add(detail.getTotalWages());
            data.add(detail.getTotalNumberOfDays());
            // Total amounts per field: use stored bill total when available (PERCENTAGE fields),
            // otherwise fall back to perDayBreakup × days (FLAT fields).
            for (String key : dynamicKeys) {
                BigDecimal storedTotal = detail.getTotalAmountBreakup().get(key);
                if (storedTotal != null) {
                    data.add(storedTotal);
                } else {
                    data.add(detail.getPerDayBreakup().getOrDefault(key, BigDecimal.ZERO).multiply(totalDays));
                }
            }
            data.add(detail.getTotalAmount());

            for (int i = 0; i < data.size(); i++) {
                Cell cell = row.createCell(i);
                if (i == 0) {
                    cell.setCellStyle(slNoStyle);
                    cell.setCellValue(data.get(i) != null ? data.get(i).toString() : "");
                } else {
                    setCellValueWithAlignment(cell, data.get(i), textStyle, numberStyle);
                }
            }
        }

        // Write total row — dynamic number of amount columns
        Row billFooterTotalRow = sheet.createRow(rowNum++);
        // Label is placed before the dynamic total columns
        int footerLabelCol = columns.length - dynamicKeys.size() - 2;
        Cell totalLabelcell = billFooterTotalRow.createCell(footerLabelCol);
        setCellValueWithAlignment(totalLabelcell, localizationMap.getOrDefault(BILL_EXCEL_FOOTER_TOTAL_AMOUNT_LABEL, BILL_EXCEL_FOOTER_TOTAL_AMOUNT_LABEL), textStyle, numberStyle);
        // Dynamic total amount columns — use billAmountKey from fieldConfig for correct map lookup
        for (int i = 0; i < fieldConfigs.size(); i++) {
            String billAmountKey = fieldConfigs.get(i).getBillAmountKey();
            BigDecimal val = billAmountKey != null
                    ? reportBill.getAmountBreakup().getOrDefault(billAmountKey, BigDecimal.ZERO)
                    : BigDecimal.ZERO;
            setCellValueWithAlignment(billFooterTotalRow.createCell(footerLabelCol + 1 + i), val, textStyle, numberStyle);
        }
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

        // The three rows this report has always left blank under the sign-off labels are the
        // signing area. Where a signature was captured, the image and printed name now fill it;
        // where none was, they stay blank and the voucher prints for a pen signature as before.
        Row signatureImageRow = sheet.createRow(rowNum++);
        Row printedNameRow = sheet.createRow(rowNum++);
        sheet.createRow(rowNum++);

        writeSignOff(sheet, reportBill, signatureImageRow, printedNameRow, textStyle, numberStyle);

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

        // Freeze the first 4 rows
        sheet.createFreezePane(0, 4);

        // Adjust column widths
        for (int i = 0; i < BILL_EXCEL_COLUMN_WIDTH.length; i++) {
            sheet.setColumnWidth(i, BILL_EXCEL_COLUMN_WIDTH[i] * 256);
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

    /**
     * Creates a cell style with bold font.
     *
     * @param workbook the workbook where the style will be applied
     * @return the created XSSFCellStyle with bold font
     */
    private XSSFCellStyle createBoldStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(10);
        style.setFont(font);
        return style;
    }

    /**
     * Creates a cell style for the title with bold font and a light brown background.
     *
     * @param workbook the workbook where the style will be applied
     * @return the created XSSFCellStyle with bold font and light brown background
     */
    private XSSFCellStyle createTitleStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(10);
        style.setFont(font);
        style.setWrapText(true);
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

    /**
     * Creates a cell style with bold font, for headers.
     *
     * @param workbook the workbook where the style will be applied
     * @param isTxtValue true if the header value is a string, false if it is a number
     * @return the created XSSFCellStyle with bold font
     */
    private XSSFCellStyle createOtherHeaderStyle(XSSFWorkbook workbook, Boolean isTxtValue, Boolean isNumberValue) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(10);
        if (isNumberValue) {
            style.setAlignment(HorizontalAlignment.RIGHT);
        } else if (isTxtValue) {
            style.setAlignment(HorizontalAlignment.LEFT);
        } else {
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.LEFT);
        }
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setWrapText(true);
        XSSFColor customColor = new XSSFColor(new java.awt.Color(236, 255, 220), null);
        style.setFillForegroundColor(customColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    /**
     * Creates a header cell style with a specific background color, borders, and center alignment.
     *
     * @param workbook the workbook where the style will be applied
     * @return the created XSSFCellStyle for header cells
     */
    private XSSFCellStyle createHeaderStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFColor customColor = new XSSFColor(new java.awt.Color(201, 218, 248), null); // Light blue background
        style.setFillForegroundColor(customColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN); // Thin bottom border
        style.setBorderTop(BorderStyle.THIN);    // Thin top border
        style.setBorderLeft(BorderStyle.THIN);   // Thin left border
        style.setBorderRight(BorderStyle.THIN);  // Thin right border
        style.setAlignment(HorizontalAlignment.CENTER); // Center text alignment
        style.setWrapText(true); // Enable text wrapping
        style.setFont(createBoldStyle(workbook).getFont()); // Use bold font style
        return style;
    }

    /**
     * Creates a cell style with a light gray background and thin borders.
     * The alignment is set to left and the font is set to 8 points.
     * This style is used for the sl no column in the excel report.
     *
     * @param workbook the workbook where the style will be applied
     * @return the created XSSFCellStyle for the sl no column
     */
    private XSSFCellStyle createSlNoCellStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFColor customColor = new XSSFColor(new java.awt.Color(212, 212, 212), null);
        style.setFillForegroundColor(customColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font = workbook.createFont();
        font.setFontHeight(8);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    /**
     * Creates a cell style with a specified horizontal alignment, font size 8 and wrapping enabled.
     * The style also has thin borders on all sides and top alignment.
     *
     * @param workbook the workbook where the style will be applied
     * @param alignment the desired horizontal alignment
     * @return the created XSSFCellStyle
     */
    private XSSFCellStyle createCellStyle(XSSFWorkbook workbook, HorizontalAlignment alignment) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(8);
        style.setWrapText(true);
        style.setAlignment(alignment);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }


    /**
     * Sets the value of a cell with appropriate alignment and style based on the type of value.
     *
     * @param cell the cell where the value will be set
     * @param value the value to set in the cell
     * @param textStyle the style to apply for text values
     * @param numberStyle the style to apply for numeric values
     */
    /** Tall enough that an embedded signature stays legible once the voucher is printed. */
    private static final short SIGNATURE_ROW_HEIGHT_POINTS = 60;

    /**
     * Left-hand column of each sign-off slot, matching the label columns written above it.
     * Each signature is drawn across SIGNATURE_COL_SPAN columns from here.
     */
    private static final Map<String, Integer> SLOT_COLUMN = Map.of(
            ReportSignature.SLOT_PREPARED_BY, 1,
            ReportSignature.SLOT_VERIFIED_BY, 4,
            ReportSignature.SLOT_APPROVED_BY, 9);

    private static final int SIGNATURE_COL_SPAN = 3;

    /**
     * Fills the voucher's signing area with the printed name and signature image for each slot
     * that was signed. Slots with no signature are left untouched.
     */
    private void writeSignOff(Sheet sheet, ReportBill reportBill, Row imageRow, Row printedNameRow,
                              XSSFCellStyle textStyle, XSSFCellStyle numberStyle) {

        List<ReportSignature> signatures = reportBill.getSignatures();
        if (CollectionUtils.isEmpty(signatures)) {
            log.info("No signatures captured for this bill — leaving the voucher sign-off area blank");
            return;
        }

        Map<String, byte[]> signatureImages = downloadSignatureImages(reportBill);
        imageRow.setHeightInPoints(SIGNATURE_ROW_HEIGHT_POINTS);

        for (ReportSignature signature : signatures) {
            Integer column = SLOT_COLUMN.get(signature.getSlot());
            if (column == null) {
                log.warn("Signature for unknown voucher slot {} — skipping", signature.getSlot());
                continue;
            }

            if (signature.getPrintedName() != null)
                setCellValueWithAlignment(printedNameRow.createCell(column), signature.getPrintedName(),
                        textStyle, numberStyle);

            byte[] image = signature.getFileStoreId() == null ? null
                    : signatureImages.get(signature.getFileStoreId());
            if (image != null)
                embedSignature(sheet, image, imageRow.getRowNum(), column);
        }
    }

    /**
     * Fetches each distinct signature image once, so a voucher signed by the same person in more
     * than one slot costs a single filestore call. An image that cannot be fetched is left out
     * and the printed name alone records that slot's sign-off.
     */
    private Map<String, byte[]> downloadSignatureImages(ReportBill reportBill) {

        String tenantId = StringUtils.hasText(reportBill.getTenantId())
                ? reportBill.getTenantId()
                : config.getStateLevelTenantId();

        Map<String, byte[]> images = new HashMap<>();
        reportBill.getSignatures().stream()
                .map(ReportSignature::getFileStoreId)
                .filter(StringUtils::hasText)
                .distinct()
                .forEach(fileStoreId -> {
                    try {
                        images.put(fileStoreId, fileStoreUtil.downloadFile(tenantId, fileStoreId));
                    } catch (Exception e) {
                        log.warn("Could not fetch signature image {}: {}", fileStoreId, e.getMessage());
                    }
                });
        return images;
    }

    /**
     * Anchors a signature image into the signing area. An unusable image is logged and skipped —
     * the printed name still records the sign-off, and a bad image must never fail the voucher.
     */
    private void embedSignature(Sheet sheet, byte[] imageBytes, int rowIdx, int colIdx) {
        try {
            int pictureType = isJpeg(imageBytes) ? Workbook.PICTURE_TYPE_JPEG : Workbook.PICTURE_TYPE_PNG;
            int pictureIdx = sheet.getWorkbook().addPicture(imageBytes, pictureType);

            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = sheet.getWorkbook().getCreationHelper().createClientAnchor();
            anchor.setCol1(colIdx);
            anchor.setRow1(rowIdx);
            anchor.setCol2(colIdx + SIGNATURE_COL_SPAN);
            anchor.setRow2(rowIdx + 1);
            anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);

            drawing.createPicture(anchor, pictureIdx);
        } catch (Exception e) {
            log.warn("Skipping unusable signature image at row {} column {}: {}", rowIdx, colIdx, e.getMessage());
        }
    }

    /** Decides the picture type from the bytes themselves rather than trusting a file name. */
    private boolean isJpeg(byte[] bytes) {
        return bytes.length >= 3
                && bytes[0] == (byte) 0xFF && bytes[1] == (byte) 0xD8 && bytes[2] == (byte) 0xFF;
    }

    private void setCellValueWithAlignment(Cell cell, Object value, XSSFCellStyle textStyle, XSSFCellStyle numberStyle) {
        if (value instanceof BigDecimal) {
            // Set cell value for BigDecimal with two decimal places
            cell.setCellValue(((BigDecimal) value).setScale(2, RoundingMode.HALF_UP).toPlainString());
            cell.setCellStyle(numberStyle);
        } else if (value instanceof Number) {
            // Set cell value for other Number types
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
