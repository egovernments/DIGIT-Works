package org.egov.digit.expense.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTXf;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.LineItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.apache.poi.ss.util.CellReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.egov.digit.expense.config.Constants.*;

@Component
@Slf4j
public class BillDetailExcelGenerator {

    static final String COL_WORKER_ID         = "workerId";
    static final String COL_USERNAME          = "username";
    static final String COL_NAME              = "name";
    static final String COL_PAYEE_NAME        = "payeeName";
    static final String COL_MOBILE_NUMBER     = "mobileNumber";
    static final String COL_PAYEE_PHONE       = "payeePhoneNumber";
    static final String COL_BANK_ACCOUNT      = "bankAccount";
    static final String COL_BANK_CODE         = "bankCode";
    static final String COL_BENEFICIARY_CODE  = "beneficiaryCode";
    static final String COL_ROLE              = "role";
    static final String COL_TOTAL_ATTENDANCE  = "totalAttendance";
    static final String COL_TOTAL_AMOUNT      = "totalAmount";

    // [columnKey, localizationKey, defaultHeader]
    private static final String[][] STATIC_COLS = {
        {COL_WORKER_ID,        "EXPENSE_TEMPLATE_COL_WORKER_ID",        "Worker ID"},
        {COL_USERNAME,         "EXPENSE_TEMPLATE_COL_USERNAME",         "Username"},
        {COL_NAME,             "EXPENSE_TEMPLATE_COL_NAME",             "Name"},
        {COL_PAYEE_NAME,       "EXPENSE_TEMPLATE_COL_PAYEE_NAME",       "Payee Name"},
        {COL_MOBILE_NUMBER,    "EXPENSE_TEMPLATE_COL_MOBILE_NUMBER",    "Mobile Number"},
        {COL_PAYEE_PHONE,      "EXPENSE_TEMPLATE_COL_PAYEE_PHONE",      "Payee Phone Number"},
        {COL_BANK_ACCOUNT,     "EXPENSE_TEMPLATE_COL_BANK_ACCOUNT",     "Bank Account"},
        {COL_BANK_CODE,        "EXPENSE_TEMPLATE_COL_BANK_CODE",        "Bank Code"},
        {COL_BENEFICIARY_CODE, "EXPENSE_TEMPLATE_COL_BENEFICIARY_CODE", "Beneficiary Code"},
        {COL_ROLE,             "EXPENSE_TEMPLATE_COL_ROLE",             "Role"},
    };

    static final int STATIC_COL_COUNT = STATIC_COLS.length; // 10

    private final LocalizationUtil localizationUtil;
    private final IndividualUtil individualUtil;
    private final Configuration config;

    @Autowired
    public BillDetailExcelGenerator(LocalizationUtil localizationUtil, IndividualUtil individualUtil,
                                     Configuration config) {
        this.localizationUtil = localizationUtil;
        this.individualUtil = individualUtil;
        this.config = config;
    }

    public byte[] generateTemplate(Bill bill, Set<String> userRoles, RequestInfo requestInfo) {
        log.info("BillDetailExcelGenerator::generateTemplate billId={} roles={}", bill.getId(), userRoles);

        List<String> payableHeadCodes = collectPayableHeadCodes(bill);
        Map<String, String> msgMap = resolveLocalization(bill, requestInfo);

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            XSSFCellStyle lockedHeaderStyle = buildHeaderStyle(workbook, true);
            XSSFCellStyle lockedDataStyle   = buildDataStyle(workbook, true);
            XSSFCellStyle editableDataStyle = buildDataStyle(workbook, false);
            XSSFCellStyle lockedNumStyle    = buildNumericStyle(workbook, true);
            XSSFCellStyle editableNumStyle  = buildNumericStyle(workbook, false);

            XSSFSheet sheet = workbook.createSheet("Bill Details");

            writeHeaderRow(sheet, lockedHeaderStyle, payableHeadCodes, msgMap);
            sheet.createFreezePane(0, 1);

            writeDataRows(sheet, bill, payableHeadCodes, userRoles, requestInfo, msgMap,
                    lockedDataStyle, editableDataStyle, lockedNumStyle, editableNumStyle);

            setColumnWidths(sheet, payableHeadCodes.size());
            sheet.protectSheet(config.getExcelSheetProtectPassword());
            workbook.setForceFormulaRecalculation(true);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate bill detail template", e);
        }
    }

    private void writeHeaderRow(XSSFSheet sheet, XSSFCellStyle style,
                                 List<String> headCodes, Map<String, String> msgMap) {
        Row row = sheet.createRow(0);
        int col = 0;
        for (String[] def : STATIC_COLS) {
            createCell(row, col++, msgMap.getOrDefault(def[1], def[2]), style);
        }
        for (String headCode : headCodes) {
            String key = "EXPENSE_TEMPLATE_COL_WAGE_" + headCode;
            String def = headCode + " Per Day Wage";
            createCell(row, col++, msgMap.getOrDefault(key, def), style);
        }
        createCell(row, col++, msgMap.getOrDefault("EXPENSE_TEMPLATE_COL_TOTAL_ATTENDANCE", "Total Attendance"), style);
        createCell(row, col,   msgMap.getOrDefault("EXPENSE_TEMPLATE_COL_TOTAL_AMOUNT",     "Total Amount"),     style);
    }

    private void writeDataRows(XSSFSheet sheet, Bill bill, List<String> headCodes,
                                Set<String> userRoles, RequestInfo requestInfo, Map<String, String> msgMap,
                                XSSFCellStyle lockedStr, XSSFCellStyle editableStr,
                                XSSFCellStyle lockedNum, XSSFCellStyle editableNum) {
        boolean isEditor   = userRoles.contains(ROLE_PAYMENT_EDITOR);
        boolean isReviewer = userRoles.contains(ROLE_PAYMENT_REVIEWER);
        boolean canEditWages = isEditor || isReviewer;

        int rowNum = 1;
        List<BillDetail> sortedDetails = bill.getBillDetails().stream()
                .sorted(Comparator.comparing(
                        d -> d.getPayee() != null && d.getPayee().getPayeeName() != null
                                ? d.getPayee().getPayeeName() : "",
                        String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        for (BillDetail detail : sortedDetails) {
            Row row = sheet.createRow(rowNum++);
            Party payee = detail.getPayee() != null ? detail.getPayee() : new Party();

            String identifier = payee.getIdentifier();
            IndividualDetails individual = fetchIndividual(identifier, bill.getTenantId(), requestInfo);

            int col = 0;

            createCell(row, col++, safeStr(detail.getWorkerId()), lockedStr);
            createCell(row, col++, individual != null ? safeStr(individual.getName()) : "", lockedStr);
            createCell(row, col++, individual != null ? safeStr(individual.getName()) : "", lockedStr);
            createCell(row, col++, safeStr(payee.getPayeeName()), isEditor ? editableStr : lockedStr);
            createCell(row, col++, individual != null ? safeStr(individual.getPhoneNumber()) : "", lockedStr);
            createCell(row, col++, safeStr(payee.getPayeePhoneNumber()), isEditor ? editableStr : lockedStr);
            createCell(row, col++, safeStr(payee.getBankAccount()),     isEditor ? editableStr : lockedStr);
            createCell(row, col++, safeStr(payee.getBankCode()),        isEditor ? editableStr : lockedStr);
            createCell(row, col++, safeStr(payee.getBeneficiaryCode()), isEditor ? editableStr : lockedStr);

            // Col 9: role — localized
            String rawRole = (individual != null && individual.getRole() != null && !individual.getRole().isBlank())
                    ? individual.getRole() : safeStr(detail.getWfStatus());
            String roleKey = "EXPENSE_TEMPLATE_ROLE_" + rawRole.toUpperCase().replace(" ", "_");
            createCell(row, col++, msgMap.getOrDefault(roleKey, rawRole), lockedStr);

            // Dynamic per-day wage columns — editable for EDITOR or REVIEWER
            Map<String, BigDecimal> perDayWages = computePerDayWages(detail, headCodes);
            for (String headCode : headCodes) {
                BigDecimal wage = perDayWages.getOrDefault(headCode, BigDecimal.ZERO);
                createNumericCell(row, col++, wage, canEditWages ? editableNum : lockedNum);
            }

            // totalAttendance — editable for EDITOR or REVIEWER
            BigDecimal attendance = detail.getTotalAttendance() != null ? detail.getTotalAttendance() : BigDecimal.ZERO;
            createNumericCell(row, col++, attendance, canEditWages ? editableNum : lockedNum);

            // totalAmount — locked, formula: sum(wage_cols) * attendance_col
            int excelRow = row.getRowNum() + 1;
            int attendanceColIdx = STATIC_COL_COUNT + headCodes.size();
            String attendanceRef = CellReference.convertNumToColString(attendanceColIdx) + excelRow;
            String totalAmountFormula;
            if (!headCodes.isEmpty()) {
                StringBuilder sb = new StringBuilder("(");
                for (int i = 0; i < headCodes.size(); i++) {
                    if (i > 0) sb.append("+");
                    sb.append(CellReference.convertNumToColString(STATIC_COL_COUNT + i)).append(excelRow);
                }
                sb.append(")*").append(attendanceRef);
                totalAmountFormula = sb.toString();
            } else {
                totalAmountFormula = "0";
            }
            createFormulaCell(row, col, totalAmountFormula, lockedNum);
        }
    }

    private Map<String, BigDecimal> computePerDayWages(BillDetail detail, List<String> headCodes) {
        Map<String, BigDecimal> result = new HashMap<>();
        List<LineItem> payableItems = getPayableItems(detail);
        if (payableItems.isEmpty()) return result;

        BigDecimal attendance = detail.getTotalAttendance();

        for (LineItem item : payableItems) {
            if (!headCodes.contains(item.getHeadCode())) continue;
            BigDecimal amount = item.getAmount() != null ? item.getAmount() : BigDecimal.ZERO;
            if (attendance != null && attendance.compareTo(BigDecimal.ZERO) > 0) {
                result.put(item.getHeadCode(), amount.divide(attendance, 4, RoundingMode.HALF_UP));
            } else {
                // attendance unknown — store full amount as per-day so user can see and correct
                result.put(item.getHeadCode(), amount);
            }
        }
        return result;
    }

    static List<String> collectPayableHeadCodes(Bill bill) {
        LinkedHashSet<String> codes = new LinkedHashSet<>();
        if (bill.getBillDetails() == null) return new ArrayList<>();
        for (BillDetail detail : bill.getBillDetails()) {
            for (LineItem item : getPayableItems(detail)) {
                if (item.getHeadCode() != null) codes.add(item.getHeadCode());
            }
        }
        return new ArrayList<>(codes);
    }

    // Check lineItems first (PAYABLE filter), fall back to payableLineItems
    static List<LineItem> getPayableItems(BillDetail detail) {
        if (detail.getLineItems() != null && !detail.getLineItems().isEmpty()) {
            return detail.getLineItems().stream()
                    .filter(li -> li.getType() == LineItemType.PAYABLE)
                    .collect(Collectors.toList());
        }
        if (detail.getPayableLineItems() != null) return detail.getPayableLineItems();
        return Collections.emptyList();
    }

    private IndividualDetails fetchIndividual(String identifier, String tenantId, RequestInfo requestInfo) {
        if (identifier == null || identifier.isBlank()) return null;
        try {
            return individualUtil.getIndividualDetails(requestInfo, tenantId, identifier);
        } catch (Exception e) {
            log.warn("Failed to fetch individual for identifier={}: {}", identifier, e.getMessage());
            return null;
        }
    }

    private Map<String, String> resolveLocalization(Bill bill, RequestInfo requestInfo) {
        String locale = config.getLocalizationDefaultLocale();
        try {
            String msgId = requestInfo.getMsgId();
            if (msgId != null && msgId.contains("|")) locale = msgId.split("\\|")[1];
        } catch (Exception ignored) {}

        String rootTenantId = bill.getTenantId().contains(".")
                ? bill.getTenantId().split("\\.")[0] : bill.getTenantId();
        try {
            Map<String, Map<String, String>> locMsgs = localizationUtil.getLocalisedMessages(
                    requestInfo, rootTenantId, locale, config.getTemplateLocalizationModule());
            Map<String, String> msgMap = locMsgs.get(locale + "|" + rootTenantId);
            if (msgMap != null) return msgMap;
        } catch (Exception e) {
            log.warn("Localization fetch failed, using defaults: {}", e.getMessage());
        }
        return Collections.emptyMap();
    }

    private XSSFCellStyle buildHeaderStyle(XSSFWorkbook workbook, boolean locked) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFColor blue = new XSSFColor(new byte[]{(byte) 0xBD, (byte) 0xD7, (byte) 0xEE}, null);
        style.setFillForegroundColor(blue);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        style.setWrapText(false);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        applyLock(style, locked);
        applyBorders(style);
        return style;
    }

    private XSSFCellStyle buildDataStyle(XSSFWorkbook workbook, boolean locked) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        applyLock(style, locked);
        applyBorders(style);
        if (!locked) {
            XSSFColor yellow = new XSSFColor(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xCC}, null);
            style.setFillForegroundColor(yellow);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        return style;
    }

    private XSSFCellStyle buildNumericStyle(XSSFWorkbook workbook, boolean locked) {
        XSSFCellStyle style = buildDataStyle(workbook, locked);
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("0.00"));
        return style;
    }

    private void applyBorders(XSSFCellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }

    /**
     * Sets cell protection reliably across POI versions.
     * Calling only setLocked() may not set applyProtection=true on the underlying XF element,
     * which Excel requires to honor per-cell locked/unlocked state under sheet protection.
     */
    private void applyLock(XSSFCellStyle style, boolean locked) {
        CTXf xf = style.getCoreXf();
        xf.setApplyProtection(true);
        if (!xf.isSetProtection()) {
            xf.addNewProtection();
        }
        xf.getProtection().setLocked(locked);
    }

    private void createCell(Row row, int col, String value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value != null ? value : "");
        cell.setCellStyle(style);
    }

    private void createNumericCell(Row row, int col, BigDecimal value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value != null ? value.setScale(4, RoundingMode.HALF_UP).doubleValue() : 0.0);
        cell.setCellStyle(style);
    }

    private void createFormulaCell(Row row, int col, String formula, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellFormula(formula);
        cell.setCellStyle(style);
    }

    private void setColumnWidths(Sheet sheet, int headCodeCount) {
        int totalCols = STATIC_COL_COUNT + headCodeCount + 2;
        for (int i = 0; i < totalCols; i++) {
            sheet.autoSizeColumn(i);
            // Add a small padding so text isn't clipped at the edge
            int paddedWidth = sheet.getColumnWidth(i) + 1024;
            sheet.setColumnWidth(i, paddedWidth);
        }
    }

    private String safeStr(String value) {
        return value != null ? value : "";
    }
}
