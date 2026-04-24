package org.egov.digit.expense.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.LineItemType;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.digit.expense.util.BillDetailExcelGenerator.*;
import static org.egov.digit.expense.config.Constants.*;

@Component
@Slf4j
public class BillDetailExcelParser {

    private final LocalizationUtil localizationUtil;
    private final Configuration config;

    @Autowired
    public BillDetailExcelParser(LocalizationUtil localizationUtil, Configuration config) {
        this.localizationUtil = localizationUtil;
        this.config = config;
    }

    /**
     * Resolved column indices for the uploaded template.
     * Index = -1 means the column was not found (optional columns only).
     */
    private static class ColumnMap {
        int workerId        = -1;
        int payeeName       = -1;
        int payeePhone      = -1;
        int bankAccount     = -1;
        int bankCode        = -1;
        int beneficiaryCode = -1;
        int totalAttendance = -1;
        Map<String, Integer> headCodeCols = new HashMap<>(); // headCode → colIdx
    }

    /**
     * Parses an uploaded bill detail template Excel file and converts each data row
     * into a PartialBillDetail. Column resolution is name-based (key OR localized label),
     * so column order in the uploaded file does not matter.
     */
    public List<PartialBillDetail> parse(byte[] excelBytes, Bill bill, Set<String> userRoles, RequestInfo requestInfo) {
        log.info("BillDetailExcelParser::parse billId={}", bill.getId());

        if (excelBytes == null || excelBytes.length == 0)
            throw new CustomException(ERR_TEMPLATE_INVALID_FORMAT, MSG_TEMPLATE_INVALID_FORMAT);

        List<String> headCodes = collectPayableHeadCodes(bill);
        Map<String, BillDetail> workerToBillDetail = buildWorkerMap(bill);

        boolean isEditor   = userRoles.contains(ROLE_PAYMENT_EDITOR);
        boolean isReviewer = userRoles.contains(ROLE_PAYMENT_REVIEWER);

        Map<String, String> msgMap = resolveLocalization(bill, requestInfo);

        List<PartialBillDetail> result = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelBytes))) {
            Sheet sheet = workbook.getSheetAt(0);

            ColumnMap colMap = buildAndValidateColumnMap(sheet, headCodes, msgMap, isEditor, isReviewer);

            int lastRow = sheet.getLastRowNum();

            for (int rowIdx = 1; rowIdx <= lastRow; rowIdx++) {
                Row row = sheet.getRow(rowIdx);
                if (row == null) continue;

                String workerId = readString(row, colMap.workerId);
                if (workerId == null || workerId.isBlank()) {
                    log.warn("Row {} has no workerId — skipping", rowIdx);
                    continue;
                }

                BillDetail source = workerToBillDetail.get(workerId);
                if (source == null) {
                    throw new CustomException(ERR_TEMPLATE_INVALID_ROW,
                            "No BillDetail found for workerId=" + workerId + " in bill " + bill.getId());
                }

                PartialBillDetail.PartialBillDetailBuilder builder = PartialBillDetail.builder()
                        .id(source.getId());

                if (isEditor) {
                    builder.payee(buildPayeeFromRow(row, source.getPayee(), colMap));
                }

                if (isReviewer) {
                    BigDecimal totalAttendance = readBigDecimal(row, colMap.totalAttendance);
                    if (totalAttendance == null || totalAttendance.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new CustomException(ERR_TEMPLATE_INVALID_ATTENDANCE,
                                "totalAttendance must be > 0 for workerId=" + workerId);
                    }
                    builder.totalAttendance(totalAttendance);

                    List<LineItem> updatedLineItems = buildLineItems(row, headCodes, source, totalAttendance, colMap);
                    builder.lineItems(updatedLineItems);

                    List<LineItem> updatedPayableItems = updatedLineItems.stream()
                            .filter(li -> li.getType() == LineItemType.PAYABLE)
                            .collect(Collectors.toList());
                    builder.payableLineItems(updatedPayableItems);

                    builder.totalAmount(computeTotalAmount(updatedLineItems));
                }

                result.add(builder.build());
            }
        } catch (CustomException e) {
            throw e;
        } catch (IOException e) {
            throw new CustomException(ERR_TEMPLATE_PARSE_ERROR, MSG_TEMPLATE_PARSE_ERROR_PREFIX + e.getMessage());
        } catch (Exception e) {
            throw new CustomException(ERR_TEMPLATE_PARSE_ERROR, MSG_TEMPLATE_PARSE_UNEXPECTED + e.getMessage());
        }

        return result;
    }

    /**
     * Builds a name→colIndex map from the header row, then resolves each required and optional
     * column by matching against: the raw column key, the default English label, and the
     * localized label from msgMap — all case-insensitively.
     *
     * Throws ERR_TEMPLATE_INVALID_HEADER for:
     *   - missing/empty header row
     *   - workerId column not found (required for all roles)
     *   - any head code column not found (required for PAYMENT_REVIEWER)
     *   - totalAttendance column not found (required for PAYMENT_REVIEWER)
     */
    private ColumnMap buildAndValidateColumnMap(Sheet sheet, List<String> headCodes,
                                                Map<String, String> msgMap,
                                                boolean isEditor, boolean isReviewer) {
        Row header = sheet.getRow(0);
        if (header == null || header.getLastCellNum() <= 0)
            throw new CustomException(ERR_TEMPLATE_INVALID_HEADER, MSG_TEMPLATE_MISSING_HEADER_ROW);

        // Build headerValue(lowercase) → colIdx map from the uploaded file
        Map<String, Integer> headerIndexMap = new LinkedHashMap<>();
        for (int c = 0; c < header.getLastCellNum(); c++) {
            String val = readString(header, c);
            if (val != null && !val.isBlank())
                headerIndexMap.put(val.trim().toLowerCase(), c);
        }

        ColumnMap colMap = new ColumnMap();

        // Resolve each STATIC_COL by key + default label + localized label
        // STATIC_COLS order: workerId(0), username(1), name(2), payeeName(3), mobileNumber(4),
        //                    payeePhone(5), bankAccount(6), bankCode(7), beneficiaryCode(8), role(9)
        colMap.workerId        = resolveColumn(headerIndexMap, COL_WORKER_ID,        msgMap.get("EXPENSE_TEMPLATE_COL_WORKER_ID"),        "Worker ID");
        colMap.payeeName       = resolveColumn(headerIndexMap, COL_PAYEE_NAME,       msgMap.get("EXPENSE_TEMPLATE_COL_PAYEE_NAME"),       "Payee Name");
        colMap.payeePhone      = resolveColumn(headerIndexMap, COL_PAYEE_PHONE,      msgMap.get("EXPENSE_TEMPLATE_COL_PAYEE_PHONE"),      "Payee Phone Number");
        colMap.bankAccount     = resolveColumn(headerIndexMap, COL_BANK_ACCOUNT,     msgMap.get("EXPENSE_TEMPLATE_COL_BANK_ACCOUNT"),     "Bank Account");
        colMap.bankCode        = resolveColumn(headerIndexMap, COL_BANK_CODE,        msgMap.get("EXPENSE_TEMPLATE_COL_BANK_CODE"),        "Bank Code");
        colMap.beneficiaryCode = resolveColumn(headerIndexMap, COL_BENEFICIARY_CODE, msgMap.get("EXPENSE_TEMPLATE_COL_BENEFICIARY_CODE"), "Beneficiary Code");
        colMap.totalAttendance = resolveColumn(headerIndexMap, COL_TOTAL_ATTENDANCE, msgMap.get("EXPENSE_TEMPLATE_COL_TOTAL_ATTENDANCE"), "Total Attendance");

        // workerId is required for all roles
        if (colMap.workerId == -1)
            throw new CustomException(ERR_TEMPLATE_INVALID_HEADER,
                    "Required column 'workerId' not found in uploaded template. "
                    + "Ensure the file was downloaded from this system.");

        // Head code columns and totalAttendance are required for PAYMENT_REVIEWER
        if (isReviewer) {
            List<String> missingHeadCodes = new ArrayList<>();
            for (String headCode : headCodes) {
                String locKey = "EXPENSE_TEMPLATE_COL_WAGE_" + headCode;
                int idx = resolveColumn(headerIndexMap, headCode, msgMap.get(locKey), headCode + " Per Day Wage");
                if (idx == -1) {
                    missingHeadCodes.add(headCode);
                } else {
                    colMap.headCodeCols.put(headCode, idx);
                }
            }
            if (!missingHeadCodes.isEmpty())
                throw new CustomException(ERR_TEMPLATE_INVALID_HEADER,
                        "Head code column(s) not found in uploaded template: " + missingHeadCodes
                        + ". Ensure the file matches the current bill's head codes.");

            if (colMap.totalAttendance == -1)
                throw new CustomException(ERR_TEMPLATE_INVALID_HEADER,
                        "Required column 'totalAttendance' not found in uploaded template.");
        }

        log.info("BillDetailExcelParser: resolved column map workerId={} payeeName={} payeePhone={} "
                + "bankAccount={} bankCode={} beneficiaryCode={} totalAttendance={} headCodeCols={}",
                colMap.workerId, colMap.payeeName, colMap.payeePhone,
                colMap.bankAccount, colMap.bankCode, colMap.beneficiaryCode,
                colMap.totalAttendance, colMap.headCodeCols);

        return colMap;
    }

    /**
     * Resolves a column index by checking the headerIndexMap against any of:
     * the raw key, the localized label (from msgMap), and the default English label.
     * Returns -1 if none match.
     */
    private int resolveColumn(Map<String, Integer> headerIndexMap, String key, String localizedLabel, String defaultLabel) {
        Set<String> candidates = new LinkedHashSet<>();
        if (key != null)            candidates.add(key.trim().toLowerCase());
        if (localizedLabel != null) candidates.add(localizedLabel.trim().toLowerCase());
        if (defaultLabel != null)   candidates.add(defaultLabel.trim().toLowerCase());

        for (String candidate : candidates) {
            if (headerIndexMap.containsKey(candidate))
                return headerIndexMap.get(candidate);
        }
        return -1;
    }

    private Party buildPayeeFromRow(Row row, Party source, ColumnMap colMap) {
        Party.PartyBuilder builder = Party.builder();
        if (source != null) {
            builder.id(source.getId())
                   .parentId(source.getParentId())
                   .tenantId(source.getTenantId())
                   .type(source.getType())
                   .identifier(source.getIdentifier())
                   .paymentProvider(source.getPaymentProvider())
                   .status(source.getStatus())
                   .auditDetails(source.getAuditDetails())
                   .additionalDetails(source.getAdditionalDetails());
        }
        builder.payeeName(colMap.payeeName >= 0       ? readString(row, colMap.payeeName)       : null)
               .payeePhoneNumber(colMap.payeePhone >= 0 ? readString(row, colMap.payeePhone)     : null)
               .bankAccount(colMap.bankAccount >= 0   ? readString(row, colMap.bankAccount)      : null)
               .bankCode(colMap.bankCode >= 0         ? readString(row, colMap.bankCode)         : null)
               .beneficiaryCode(colMap.beneficiaryCode >= 0 ? readString(row, colMap.beneficiaryCode) : null);
        return builder.build();
    }

    private List<LineItem> buildLineItems(Row row, List<String> headCodes,
                                          BillDetail source, BigDecimal totalAttendance,
                                          ColumnMap colMap) {
        Map<String, LineItem> existingByHeadCode = new HashMap<>();
        for (LineItem item : getPayableItems(source))
            existingByHeadCode.put(item.getHeadCode(), item);

        List<LineItem> result = new ArrayList<>();

        for (String headCode : headCodes) {
            Integer colIdx = colMap.headCodeCols.get(headCode);
            if (colIdx == null) continue; // validated earlier — won't happen for reviewer

            BigDecimal perDayWage = readBigDecimal(row, colIdx);
            if (perDayWage == null) perDayWage = BigDecimal.ZERO;

            BigDecimal newAmount = perDayWage.multiply(totalAttendance).setScale(2, RoundingMode.HALF_UP);

            LineItem sourceItem = existingByHeadCode.get(headCode);
            if (sourceItem != null) {
                result.add(LineItem.builder()
                        .id(sourceItem.getId())
                        .billDetailId(sourceItem.getBillDetailId())
                        .tenantId(sourceItem.getTenantId())
                        .headCode(sourceItem.getHeadCode())
                        .type(sourceItem.getType())
                        .amount(newAmount)
                        .paidAmount(sourceItem.getPaidAmount())
                        .status(sourceItem.getStatus())
                        .paymentStatus(sourceItem.getPaymentStatus())
                        .additionalDetails(sourceItem.getAdditionalDetails())
                        .auditDetails(sourceItem.getAuditDetails())
                        .build());
            }
        }

        if (source.getLineItems() != null) {
            source.getLineItems().stream()
                    .filter(li -> li.getType() == LineItemType.DEDUCTION)
                    .forEach(result::add);
        }

        return result;
    }

    private BigDecimal computeTotalAmount(List<LineItem> lineItems) {
        BigDecimal payable = lineItems.stream()
                .filter(li -> li.getStatus() != Status.INACTIVE && li.getType() == LineItemType.PAYABLE)
                .map(LineItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal deduction = lineItems.stream()
                .filter(li -> li.getStatus() != Status.INACTIVE && li.getType() == LineItemType.DEDUCTION)
                .map(LineItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return payable.subtract(deduction);
    }

    private Map<String, BillDetail> buildWorkerMap(Bill bill) {
        if (bill.getBillDetails() == null) return Collections.emptyMap();
        return bill.getBillDetails().stream()
                .filter(d -> d.getWorkerId() != null)
                .collect(Collectors.toMap(BillDetail::getWorkerId, d -> d, (a, b) -> a));
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
            log.warn("Localization fetch failed in parser, matching by key/default only: {}", e.getMessage());
        }
        return Collections.emptyMap();
    }

    private String readString(Row row, int col) {
        Cell cell = row.getCell(col);
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING  -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default      -> null;
        };
    }

    private BigDecimal readBigDecimal(Row row, int col) {
        Cell cell = row.getCell(col);
        if (cell == null) return null;
        try {
            if (cell.getCellType() == CellType.NUMERIC)
                return BigDecimal.valueOf(cell.getNumericCellValue());
            if (cell.getCellType() == CellType.STRING) {
                String val = cell.getStringCellValue().trim();
                return val.isEmpty() ? null : new BigDecimal(val);
            }
        } catch (Exception e) {
            log.warn("Could not parse numeric value at row={} col={}: {}", row.getRowNum(), col, e.getMessage());
        }
        return null;
    }
}
