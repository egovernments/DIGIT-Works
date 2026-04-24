package org.egov.digit.expense.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.LineItem;
import org.egov.digit.expense.web.models.Party;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class PaymentAdvisoryExcelGenerator {

    private final LocalizationUtil localizationUtil;
    private final Configuration config;

    // [title_key, title_default]
    private static final String[][] COLUMN_DEFS = {
        { "EXPENSE_PAYMENT_ADVISORY_COL_TRANSACTION_REF_NO_TITLE",   "TRANSACTION REFERENCE NUMBER" },
        { "EXPENSE_PAYMENT_ADVISORY_COL_BENEFICIARY_NAME_TITLE",     "BENEFICIARY NAME" },
        { "EXPENSE_PAYMENT_ADVISORY_COL_PAYMENT_AMOUNT_TITLE",       "PAYMENT AMOUNT" },
        { "EXPENSE_PAYMENT_ADVISORY_COL_PAYMENT_DUE_DATE_TITLE",     "PAYMENT DUE DATE" },
        { "EXPENSE_PAYMENT_ADVISORY_COL_BENEFICIARY_CODE_TITLE",     "BENEFICIARY CODE" },
        { "EXPENSE_PAYMENT_ADVISORY_COL_BENEFICIARY_ACCOUNT_NO_TITLE","BENEFICIARY ACCOUNT NUMBER" },
        { "EXPENSE_PAYMENT_ADVISORY_COL_BANK_SORT_CODE_TITLE",       "BENEFICIARY BANK SORT CODE" },
        { "EXPENSE_PAYMENT_ADVISORY_COL_DEBIT_ACCOUNT_NO_TITLE",     "DEBIT ACCOUNT NUMBER" }
    };

    @Autowired
    public PaymentAdvisoryExcelGenerator(LocalizationUtil localizationUtil, Configuration config) {
        this.localizationUtil = localizationUtil;
        this.config = config;
    }

    public byte[] generate(Bill bill, RequestInfo requestInfo) {
        log.info("PaymentAdvisoryExcelGenerator::generate billId={}", bill.getId());

        if (bill.getBillDetails() == null || bill.getBillDetails().isEmpty()) {
            throw new RuntimeException("No bill details found for billId=" + bill.getId());
        }

        Map<String, String> msgMap = resolveLocalization(bill, requestInfo);

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Payment Advisory");

            XSSFFont titleFont    = buildFont(workbook, true, IndexedColors.BLACK, (short) 11, false);

            XSSFCellStyle headerStyle = buildHeaderStyle(workbook, titleFont);
            XSSFCellStyle dataStyle   = buildDataStyle(workbook);
            XSSFCellStyle amountStyle = buildAmountStyle(workbook, dataStyle);

            writeHeader(sheet, headerStyle, titleFont, msgMap);
            writeRows(sheet, bill.getBillDetails(), dataStyle, amountStyle);
            setColumnWidths(sheet);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Payment Advisory Excel", e);
        }
    }

    private Map<String, String> resolveLocalization(Bill bill, RequestInfo requestInfo) {
        String locale = config.getLocalizationDefaultLocale();
        try {
            String msgId = requestInfo.getMsgId();
            if (msgId != null && msgId.contains("|")) {
                locale = msgId.split("\\|")[1];
            }
        } catch (Exception ignored) {}

        String rootTenantId = bill.getTenantId().contains(".")
                ? bill.getTenantId().split("\\.")[0]
                : bill.getTenantId();

        try {
            Map<String, Map<String, String>> locMsgs = localizationUtil.getLocalisedMessages(
                    requestInfo, rootTenantId, locale, config.getPaymentAdvisoryLocalizationModule());
            Map<String, String> msgMap = locMsgs.get(locale + "|" + rootTenantId);
            if (msgMap != null) return msgMap;
        } catch (Exception e) {
            log.warn("Localization fetch failed, using defaults. Error: {}", e.getMessage());
        }
        return Collections.emptyMap();
    }

    private void writeHeader(Sheet sheet, XSSFCellStyle headerStyle,
                             XSSFFont titleFont, Map<String, String> msgMap) {
        Row header = sheet.createRow(0);
        for (int i = 0; i < COLUMN_DEFS.length; i++) {
            Cell cell = header.createCell(i);
            String title = msgMap.getOrDefault(COLUMN_DEFS[i][0], COLUMN_DEFS[i][1]);
            XSSFRichTextString rich = new XSSFRichTextString();
            rich.append(title, titleFont);
            cell.setCellValue(rich);
            cell.setCellStyle(headerStyle);
        }
    }

    private void writeRows(Sheet sheet, List<BillDetail> billDetails,
                           XSSFCellStyle dataStyle, XSSFCellStyle amountStyle) {
        int rowNum = 1;
        for (BillDetail detail : billDetails) {
            Party payee = detail.getPayee();
            if (payee == null) {
                log.warn("Skipping bill detail id={} — payee is null", detail.getId());
                continue;
            }
            Row row = sheet.createRow(rowNum++);

            createDataCell(row, 0, "", dataStyle);                                       // TRANSACTION REFERENCE NUMBER — empty
            createDataCell(row, 1, safeStr(payee.getPayeeName()), dataStyle);            // BENEFICIARY NAME

            BigDecimal amount = sumPayableLineItems(detail.getPayableLineItems());
            Cell amountCell = row.createCell(2);
            amountCell.setCellValue(amount.setScale(2, RoundingMode.HALF_UP).doubleValue());
            amountCell.setCellStyle(amountStyle);                                        // PAYMENT AMOUNT

            createDataCell(row, 3, "", dataStyle);                                       // PAYMENT DUE DATE — empty
            createDataCell(row, 4, safeStr(payee.getBeneficiaryCode()), dataStyle);      // BENEFICIARY CODE
            createDataCell(row, 5, safeStr(payee.getBankAccount()), dataStyle);          // BENEFICIARY ACCOUNT NUMBER
            createDataCell(row, 6, safeStr(payee.getBankCode()), dataStyle);             // BENEFICIARY BANK SORT CODE
            createDataCell(row, 7, "", dataStyle);                                       // DEBIT ACCOUNT NUMBER — empty
        }
    }

    private void createDataCell(Row row, int col, String value, XSSFCellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private BigDecimal sumPayableLineItems(List<LineItem> items) {
        if (items == null || items.isEmpty()) return BigDecimal.ZERO;
        return items.stream()
                .map(LineItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private XSSFCellStyle buildHeaderStyle(XSSFWorkbook workbook, XSSFFont titleFont) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFColor green = new XSSFColor(new byte[]{(byte) 0xD9, (byte) 0xEA, (byte) 0xD3}, null);
        style.setFillForegroundColor(green);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(titleFont);
        style.setWrapText(true);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        applyThinBorders(style);
        return style;
    }

    private XSSFCellStyle buildDataStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        applyThinBorders(style);
        return style;
    }

    private XSSFCellStyle buildAmountStyle(XSSFWorkbook workbook, XSSFCellStyle dataStyle) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.cloneStyleFrom(dataStyle);
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("0.00"));
        return style;
    }

    private void applyThinBorders(XSSFCellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }

    private XSSFFont buildFont(XSSFWorkbook workbook, boolean bold, IndexedColors color, short size, boolean italic) {
        XSSFFont font = workbook.createFont();
        font.setBold(bold);
        font.setItalic(italic);
        font.setColor(color.getIndex());
        font.setFontHeightInPoints(size);
        return font;
    }

    private void setColumnWidths(Sheet sheet) {
        // Sized to fit the longest title ("TRANSACTION REFERENCE NUMBER" = 28 chars) + small padding
        int width = 32 * 256;
        for (int i = 0; i < COLUMN_DEFS.length; i++) {
            sheet.setColumnWidth(i, width);
        }
    }

    private String safeStr(String value) {
        return value != null ? value : "";
    }
}
