package org.egov.digit.expense.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    /** Cap on rows named in the error message, so a wholly wrong file doesn't produce a wall of text. */
    private static final int MAX_REPORTED_ROW_ERRORS = 20;

    private final LocalizationUtil localizationUtil;
    private final Configuration config;
    private final ObjectMapper objectMapper;
    private final BillDetailExcelGenerator generator;
    private final BillPeriodUtil billPeriodUtil;

    @Autowired
    public BillDetailExcelParser(LocalizationUtil localizationUtil, Configuration config,
                                  ObjectMapper objectMapper, BillDetailExcelGenerator generator,
                                  BillPeriodUtil billPeriodUtil) {
        this.localizationUtil = localizationUtil;
        this.config = config;
        this.objectMapper = objectMapper;
        this.generator = generator;
        this.billPeriodUtil = billPeriodUtil;
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

        BillDetailExcelGenerator.FieldConfigContext fcCtx =
                generator.buildFieldConfigContext(bill, requestInfo);

        List<String> headCodes = BillDetailExcelGenerator.resolveOrderedHeadCodes(bill, fcCtx);
        Map<String, BillDetail> workerToBillDetail = buildWorkerMap(bill);

        // Must match BillValidator's branch choice, else we validate fields it will strip
        BillUpdateMode mode = BillUpdateMode.resolve(userRoles, bill.getStatus());
        boolean isEditor   = mode == BillUpdateMode.EDITOR;
        boolean isReviewer = mode == BillUpdateMode.REVIEWER;

        Map<String, String> msgMap = resolveLocalization(bill, requestInfo);
        Integer maxAttendanceDays = billPeriodUtil.resolveMaxAttendanceDays(bill);

        List<PartialBillDetail> result = new ArrayList<>();
        List<RowError> rowErrors = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelBytes))) {
            Sheet sheet = workbook.getSheetAt(0);

            ColumnMap colMap = buildAndValidateColumnMap(sheet, headCodes, fcCtx, msgMap, isEditor, isReviewer);

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
                    rowErrors.add(new RowError(ERR_TEMPLATE_INVALID_ROW, rowIdx + 1, workerId,
                            "no matching worker in this bill"));
                    continue;
                }

                PartialBillDetail.PartialBillDetailBuilder builder = PartialBillDetail.builder()
                        .id(source.getId());

                if (isEditor) {
                    builder.payee(buildPayeeFromRow(row, source.getPayee(), colMap));
                }

                if (isReviewer) {
                    BigDecimal totalAttendance = readBigDecimal(row, colMap.totalAttendance);
                    // 0 is valid — absentees are billed with zero attendance by the calculator.
                    // Fractional is valid too — muster-roll emits 0.5 for half days.
                    if (totalAttendance == null || totalAttendance.compareTo(BigDecimal.ZERO) < 0
                            || (maxAttendanceDays != null
                                && totalAttendance.compareTo(BigDecimal.valueOf(maxAttendanceDays)) > 0)) {
                        rowErrors.add(new RowError(ERR_TEMPLATE_INVALID_ATTENDANCE, rowIdx + 1, workerId,
                                "Total Attendance must be "
                                + BillPeriodUtil.attendanceRangeText(maxAttendanceDays)
                                + " days, found " + (totalAttendance != null ? totalAttendance.toPlainString() : "blank")));
                        continue;
                    }
                    builder.totalAttendance(totalAttendance);

                    Map<String, BigDecimal> enteredRates = new LinkedHashMap<>();
                    List<LineItem> updatedLineItems = buildLineItems(row, headCodes, source, totalAttendance,
                            colMap, fcCtx, enteredRates);
                    builder.lineItems(updatedLineItems);
                    builder.additionalDetails(mergeRateSnapshot(source, enteredRates, totalAttendance));

                    List<LineItem> updatedPayableItems = updatedLineItems.stream()
                            .filter(li -> li.getType() == LineItemType.PAYABLE)
                            .collect(Collectors.toList());
                    builder.payableLineItems(updatedPayableItems);

                    builder.totalAmount(computeTotalAmount(updatedLineItems));
                }

                result.add(builder.build());
            }

            if (!rowErrors.isEmpty()) throw buildRowErrorException(rowErrors);
        } catch (CustomException e) {
            throw e;
        } catch (IOException e) {
            throw new CustomException(ERR_TEMPLATE_PARSE_ERROR, MSG_TEMPLATE_PARSE_ERROR_PREFIX + e.getMessage());
        } catch (Exception e) {
            throw new CustomException(ERR_TEMPLATE_PARSE_ERROR, MSG_TEMPLATE_PARSE_UNEXPECTED + e.getMessage());
        }

        return result;
    }

    /** One rejected row, so a reviewer sees every bad row at once instead of only the first. */
    private static class RowError {
        final String code;
        final int excelRow;
        final String workerId;
        final String reason;

        RowError(String code, int excelRow, String workerId, String reason) {
            this.code = code;
            this.excelRow = excelRow;
            this.workerId = workerId;
            this.reason = reason;
        }

        @Override
        public String toString() {
            return "Row " + excelRow + " (workerId=" + workerId + "): " + reason;
        }
    }

    private CustomException buildRowErrorException(List<RowError> rowErrors) {
        String detail = rowErrors.stream().limit(MAX_REPORTED_ROW_ERRORS)
                .map(RowError::toString)
                .collect(Collectors.joining("; "));
        if (rowErrors.size() > MAX_REPORTED_ROW_ERRORS)
            detail += "; and " + (rowErrors.size() - MAX_REPORTED_ROW_ERRORS) + " more row(s)";
        log.warn("Rejecting template upload: {} invalid row(s)", rowErrors.size());
        return new CustomException(rowErrors.get(0).code, MSG_TEMPLATE_ROW_ERRORS_PREFIX + detail);
    }

    /**
     * Builds a name→colIndex map from the header row, then resolves each required and optional
     * column by matching against: the raw column key, the default English label, and the
     * localized label from msgMap — all case-insensitively.
     */
    private ColumnMap buildAndValidateColumnMap(Sheet sheet, List<String> headCodes,
                                                BillDetailExcelGenerator.FieldConfigContext fcCtx,
                                                Map<String, String> msgMap,
                                                boolean isEditor, boolean isReviewer) {
        Row header = sheet.getRow(0);
        if (header == null || header.getLastCellNum() <= 0)
            throw new CustomException(ERR_TEMPLATE_INVALID_HEADER, MSG_TEMPLATE_MISSING_HEADER_ROW);

        Map<String, Integer> headerIndexMap = new LinkedHashMap<>();
        for (int c = 0; c < header.getLastCellNum(); c++) {
            String val = readString(header, c);
            if (val != null && !val.isBlank())
                headerIndexMap.put(val.trim().toLowerCase(), c);
        }

        ColumnMap colMap = new ColumnMap();

        colMap.workerId        = resolveColumn(headerIndexMap, COL_WORKER_ID,        msgMap.get("EXPENSE_TEMPLATE_COL_WORKER_ID"),        "Worker ID");
        colMap.payeeName       = resolveColumn(headerIndexMap, COL_PAYEE_NAME,       msgMap.get("EXPENSE_TEMPLATE_COL_PAYEE_NAME"),       "Payee Name");
        colMap.payeePhone      = resolveColumn(headerIndexMap, COL_PAYEE_PHONE,      msgMap.get("EXPENSE_TEMPLATE_COL_PAYEE_PHONE"),      "Payee Phone Number");
        colMap.bankAccount     = resolveColumn(headerIndexMap, COL_BANK_ACCOUNT,     msgMap.get("EXPENSE_TEMPLATE_COL_BANK_ACCOUNT"),     "Bank Account");
        colMap.bankCode        = resolveColumn(headerIndexMap, COL_BANK_CODE,        msgMap.get("EXPENSE_TEMPLATE_COL_BANK_CODE"),        "Bank Code");
        colMap.beneficiaryCode = resolveColumn(headerIndexMap, COL_BENEFICIARY_CODE, msgMap.get("EXPENSE_TEMPLATE_COL_BENEFICIARY_CODE"), "Beneficiary Code");
        colMap.totalAttendance = resolveColumn(headerIndexMap, COL_TOTAL_ATTENDANCE, msgMap.get("EXPENSE_TEMPLATE_COL_TOTAL_ATTENDANCE"), "Total Attendance");

        if (colMap.workerId == -1)
            throw new CustomException(ERR_TEMPLATE_INVALID_HEADER,
                    "Required column 'workerId' not found in uploaded template. "
                    + "Ensure the file was downloaded from this system.");

        if (isReviewer) {
            List<String> missingHeadCodes = new ArrayList<>();
            for (String headCode : headCodes) {
                String locKey = resolveHeadCodeLocKey(headCode, fcCtx);
                String defaultLabel = resolveHeadCodeDefault(headCode, fcCtx);
                int idx = resolveColumn(headerIndexMap, headCode,
                        msgMap.get(locKey), defaultLabel);
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

    private String resolveHeadCodeLocKey(String headCode, BillDetailExcelGenerator.FieldConfigContext fcCtx) {
        if (fcCtx.hasConfig()) {
            RateFieldConfig fc = fcCtx.byHeadCode.get(headCode);
            if (fc != null && fc.getColumnLabelKey() != null) return fc.getColumnLabelKey();
        }
        return "EXPENSE_TEMPLATE_COL_WAGE_" + headCode;
    }

    private String resolveHeadCodeDefault(String headCode, BillDetailExcelGenerator.FieldConfigContext fcCtx) {
        if (fcCtx.hasConfig()) {
            RateFieldConfig fc = fcCtx.byHeadCode.get(headCode);
            if (fc != null && fc.getColumnLabelKey() != null) return fc.getColumnLabelKey();
        }
        return headCode;
    }

    private int resolveColumn(Map<String, Integer> headerIndexMap, String key,
                               String localizedLabel, String defaultLabel) {
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

    /**
     * Builds updated line items from the uploaded row.
     *
     * When fieldConfig is present, processes fields in fieldConfig.order:
     *   PER_DAY      → newAmount = enteredRate * attendance
     *   ONE_TIME     → newAmount = enteredValue
     *   PER_PERIOD   → newAmount = enteredValue
     *   PERCENTAGE   → newAmount = enteredPct * Σ(component runningAmounts) / 100
     *
     * Falls back to (enteredValue * attendance) for all fields when no fieldConfig.
     */
    private List<LineItem> buildLineItems(Row row, List<String> headCodes,
                                          BillDetail source, BigDecimal totalAttendance,
                                          ColumnMap colMap,
                                          BillDetailExcelGenerator.FieldConfigContext fcCtx,
                                          Map<String, BigDecimal> enteredRatesOut) {
        Map<String, LineItem> existingByHeadCode = new HashMap<>();
        for (LineItem item : getPayableItems(source))
            existingByHeadCode.put(item.getHeadCode(), item);

        // Running map of headCode → computed amount (updated as we process each field in order)
        Map<String, BigDecimal> runningAmounts = new LinkedHashMap<>();

        List<LineItem> result = new ArrayList<>();

        if (!fcCtx.hasConfig()) {
            // Legacy: all fields treated as PER_DAY
            for (String headCode : headCodes) {
                Integer colIdx = colMap.headCodeCols.get(headCode);
                if (colIdx == null) continue;
                BigDecimal entered = readBigDecimal(row, colIdx);
                if (entered == null) entered = BigDecimal.ZERO;
                enteredRatesOut.put(snapshotKey(headCode, fcCtx), entered);
                BigDecimal newAmount = entered.multiply(totalAttendance).setScale(2, RoundingMode.HALF_UP);
                addOrUpdateLineItem(result, existingByHeadCode.get(headCode), source, headCode, newAmount, entered);
            }
        } else {
            // Process in fieldConfig.order — PERCENTAGE fields see already-computed component amounts
            for (RateFieldConfig fc : fcCtx.fieldConfigs) {
                if (!Boolean.TRUE.equals(fc.getIsPayable())) continue;
                String hc = fcCtx.resolveHeadCode(fc.getFieldKey());
                Integer colIdx = colMap.headCodeCols.get(hc);
                if (colIdx == null) continue;

                BigDecimal entered = readBigDecimal(row, colIdx);
                if (entered == null) entered = BigDecimal.ZERO;
                enteredRatesOut.put(BillDetailExcelGenerator.rateKey(fc), entered);

                BigDecimal newAmount;
                if (VALUE_TYPE_PERCENTAGE.equals(fc.getValueType())) {
                    BigDecimal componentSum = sumRunningComponents(fc, fcCtx, runningAmounts);
                    newAmount = componentSum.multiply(entered)
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                } else if (PAYMENT_TYPE_PER_DAY.equals(fc.getPaymentType())) {
                    newAmount = entered.multiply(totalAttendance).setScale(2, RoundingMode.HALF_UP);
                } else {
                    // ONE_TIME / PER_PERIOD — entered value IS the amount
                    newAmount = entered.setScale(2, RoundingMode.HALF_UP);
                }

                runningAmounts.put(hc, newAmount);
                addOrUpdateLineItem(result, existingByHeadCode.get(hc), source, hc, newAmount, entered);
            }

            // Safety net for head codes not in fieldConfig
            for (String hc : headCodes) {
                if (fcCtx.byHeadCode.containsKey(hc)) continue;
                Integer colIdx = colMap.headCodeCols.get(hc);
                if (colIdx == null) continue;
                BigDecimal entered = readBigDecimal(row, colIdx);
                if (entered == null) entered = BigDecimal.ZERO;
                enteredRatesOut.put(snapshotKey(hc, fcCtx), entered);
                BigDecimal newAmount = entered.multiply(totalAttendance).setScale(2, RoundingMode.HALF_UP);
                addOrUpdateLineItem(result, existingByHeadCode.get(hc), source, hc, newAmount, entered);
            }
        }

        // Preserve existing deduction line items unchanged
        if (source.getLineItems() != null) {
            source.getLineItems().stream()
                    .filter(li -> li.getType() == LineItemType.DEDUCTION)
                    .forEach(result::add);
        }

        return result;
    }

    /**
     * Rewrites rateBreakup and noOfDaysWorked on the detail's additionalDetails, preserving
     * every other key. EnrichmentUtil replaces additionalDetails wholesale rather than merging,
     * so anything omitted here (editInfo, individualId, attendance) would be dropped.
     */
    private Object mergeRateSnapshot(BillDetail source, Map<String, BigDecimal> enteredRates,
                                      BigDecimal totalAttendance) {
        Map<String, Object> merged = new LinkedHashMap<>();
        if (source.getAdditionalDetails() != null) {
            try {
                merged.putAll(objectMapper.convertValue(source.getAdditionalDetails(),
                        new TypeReference<Map<String, Object>>() {}));
            } catch (Exception e) {
                log.warn("Could not read additionalDetails for billDetail={}, rewriting rate snapshot only: {}",
                        source.getId(), e.getMessage());
            }
        }

        Map<String, Object> rates = new LinkedHashMap<>();
        Object existing = merged.get(BILL_DETAIL_RATE_BREAKUP_KEY);
        if (existing != null) {
            try {
                rates.putAll(objectMapper.convertValue(existing, new TypeReference<Map<String, Object>>() {}));
            } catch (Exception e) {
                log.warn("Discarding unreadable rateBreakup on billDetail={}: {}",
                        source.getId(), e.getMessage());
            }
        }
        rates.putAll(enteredRates);   // reviewer's values win; untouched heads keep their snapshot

        merged.put(BILL_DETAIL_RATE_BREAKUP_KEY, rates);
        merged.put(BILL_DETAIL_ATTENDANCE_KEY, totalAttendance);
        merged.put(BILL_DETAIL_DAYS_WORKED_KEY, totalAttendance);
        return merged;
    }

    private BigDecimal sumRunningComponents(RateFieldConfig fc,
                                             BillDetailExcelGenerator.FieldConfigContext fcCtx,
                                             Map<String, BigDecimal> runningAmounts) {
        if (fc.getComponents() == null || fc.getComponents().isEmpty()) return BigDecimal.ZERO;
        BigDecimal sum = BigDecimal.ZERO;
        for (String componentFieldKey : fc.getComponents()) {
            String componentHc = fcCtx.resolveHeadCode(componentFieldKey);
            sum = sum.add(runningAmounts.getOrDefault(componentHc, BigDecimal.ZERO));
        }
        return sum;
    }

    /**
     * The calculator omits zero-amount line items, so a worker with no attendance at bill
     * creation has no payable row to clone. Without creating one, restoring their days saves
     * nothing and underpays silently. Skipped at rate 0 so we don't litter zero rows.
     * A null id makes EnrichmentUtil.mergeLineItems assign the id and audit details.
     */
    private void addOrUpdateLineItem(List<LineItem> result, LineItem sourceItem, BillDetail detail,
                                     String headCode, BigDecimal newAmount, BigDecimal enteredRate) {
        if (sourceItem != null) {
            result.add(cloneWithAmount(sourceItem, newAmount));
            return;
        }
        if (enteredRate == null || enteredRate.compareTo(BigDecimal.ZERO) <= 0) return;

        log.info("Creating missing payable line item headCode={} amount={} for billDetail={}",
                headCode, newAmount, detail.getId());
        result.add(LineItem.builder()
                .billDetailId(detail.getId())
                .tenantId(detail.getTenantId())
                .headCode(headCode)
                .type(LineItemType.PAYABLE)
                .amount(newAmount)
                .paidAmount(BigDecimal.ZERO)
                .status(Status.ACTIVE)
                .build());
    }

    /** rateBreakup is keyed by fieldKey; translate where a headCode is all we have. */
    private static String snapshotKey(String headCode, BillDetailExcelGenerator.FieldConfigContext fcCtx) {
        return fcCtx.reverseMapping != null
                ? fcCtx.reverseMapping.getOrDefault(headCode, headCode)
                : headCode;
    }

    private LineItem cloneWithAmount(LineItem source, BigDecimal newAmount) {
        return LineItem.builder()
                .id(source.getId())
                .billDetailId(source.getBillDetailId())
                .tenantId(source.getTenantId())
                .headCode(source.getHeadCode())
                .type(source.getType())
                .amount(newAmount)
                .paidAmount(source.getPaidAmount())
                .status(source.getStatus())
                .paymentStatus(source.getPaymentStatus())
                .additionalDetails(source.getAdditionalDetails())
                .auditDetails(source.getAuditDetails())
                .build();
    }

    private List<LineItem> getPayableItems(BillDetail detail) {
        if (detail.getLineItems() != null && !detail.getLineItems().isEmpty()) {
            return detail.getLineItems().stream()
                    .filter(li -> li.getType() == LineItemType.PAYABLE)
                    .collect(Collectors.toList());
        }
        if (detail.getPayableLineItems() != null) return detail.getPayableLineItems();
        return Collections.emptyList();
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
