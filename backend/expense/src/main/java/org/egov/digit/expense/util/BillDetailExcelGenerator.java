package org.egov.digit.expense.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTXf;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.LineItemType;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
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

    // Snapshot keys — imported from Constants
    static final String FIELD_CONFIG_KEY  = RATE_FIELD_CONFIG_SNAPSHOT_KEY;
    static final String HEAD_CODE_MAP_KEY = HEAD_CODE_MAPPING_KEY;

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
    private final ObjectMapper objectMapper;
    private final MdmsUtil mdmsUtil;

    @Autowired
    public BillDetailExcelGenerator(LocalizationUtil localizationUtil, IndividualUtil individualUtil,
                                     Configuration config, ObjectMapper objectMapper, MdmsUtil mdmsUtil) {
        this.localizationUtil = localizationUtil;
        this.individualUtil = individualUtil;
        this.config = config;
        this.objectMapper = objectMapper;
        this.mdmsUtil = mdmsUtil;
    }

    public byte[] generateTemplate(Bill bill, Set<String> userRoles, RequestInfo requestInfo) {
        return generateTemplate(bill, userRoles, requestInfo, Collections.emptyMap());
    }

    public byte[] generateTemplate(Bill bill, Set<String> userRoles, RequestInfo requestInfo,
                                    Map<String, BigDecimal> headCodeMaxLimits) {
        log.info("BillDetailExcelGenerator::generateTemplate billId={} roles={} limitsPresent={}",
                bill.getId(), userRoles, !headCodeMaxLimits.isEmpty());

        FieldConfigContext fcCtx = buildFieldConfigContext(bill, requestInfo);
        List<String> headCodes = resolveOrderedHeadCodes(bill, fcCtx);
        Map<String, String> msgMap = resolveLocalization(bill, requestInfo);

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            XSSFCellStyle lockedHeaderStyle = buildHeaderStyle(workbook, true);
            XSSFCellStyle lockedDataStyle   = buildDataStyle(workbook, true);
            XSSFCellStyle editableDataStyle = buildDataStyle(workbook, false);
            XSSFCellStyle lockedNumStyle    = buildNumericStyle(workbook, true);
            XSSFCellStyle editableNumStyle  = buildNumericStyle(workbook, false);

            XSSFSheet sheet = workbook.createSheet("Bill Details");

            writeHeaderRow(sheet, lockedHeaderStyle, headCodes, fcCtx, msgMap);
            sheet.createFreezePane(0, 1);

            writeDataRows(sheet, bill, headCodes, fcCtx, userRoles, requestInfo, msgMap,
                    lockedDataStyle, editableDataStyle, lockedNumStyle, editableNumStyle);

            if (!headCodeMaxLimits.isEmpty()) {
                addRateLimitValidations(sheet, headCodes, headCodeMaxLimits, fcCtx);
            }

            setColumnWidths(sheet, headCodes.size());
            sheet.protectSheet(config.getExcelSheetProtectPassword());
            workbook.setForceFormulaRecalculation(true);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new CustomException(ERR_TEMPLATE_GENERATE_ERROR, MSG_TEMPLATE_GENERATE_ERROR + ": " + e.getMessage());
        }
    }

    /**
     * Adds stop-error data validation to each editable headCode column.
     * fieldKeyMaxLimits keys are fieldKeys (e.g. PER_DAY, FOOD) — resolved to headCodes
     * via fcCtx.reverseMapping. For PERCENTAGE columns not in the limits map, a default
     * cap of 100 is applied. Other columns with no limit entry are left unconstrained.
     */
    private void addRateLimitValidations(XSSFSheet sheet, List<String> headCodes,
                                          Map<String, BigDecimal> fieldKeyMaxLimits,
                                          FieldConfigContext fcCtx) {
        int lastRow = sheet.getLastRowNum();
        if (lastRow < 1) return;

        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);

        for (int i = 0; i < headCodes.size(); i++) {
            String headCode = headCodes.get(i);

            // Resolve headCode → fieldKey → limit
            String fieldKey = fcCtx.reverseMapping.get(headCode);
            BigDecimal limit = fieldKey != null ? fieldKeyMaxLimits.get(fieldKey) : null;

            // PERCENTAGE fields default to 100 when no explicit limit is defined
            if (limit == null) {
                RateFieldConfig fc = fcCtx.byHeadCode.get(headCode);
                if (fc != null && VALUE_TYPE_PERCENTAGE.equals(fc.getValueType()))
                    limit = BigDecimal.valueOf(100);
            }
            if (limit == null) continue;

            int colIdx = STATIC_COL_COUNT + i;
            CellRangeAddressList range = new CellRangeAddressList(1, lastRow, colIdx, colIdx);

            DataValidationConstraint constraint = dvHelper.createNumericConstraint(
                    DataValidationConstraint.ValidationType.DECIMAL,
                    DataValidationConstraint.OperatorType.BETWEEN,
                    "0",
                    limit.stripTrailingZeros().toPlainString());

            DataValidation dv = dvHelper.createValidation(constraint, range);
            dv.setShowErrorBox(true);
            dv.setErrorStyle(DataValidation.ErrorStyle.STOP);
            dv.createErrorBox("Limit Exceeded",
                    "Maximum allowed value for this field is " + limit.stripTrailingZeros().toPlainString());
            sheet.addValidationData(dv);
            log.debug("Added rate limit validation: headCode={} fieldKey={} limit={} col={}",
                    headCode, fieldKey, limit, colIdx);
        }
    }

    // -------------------------------------------------------------------------
    // FieldConfig context helpers
    // -------------------------------------------------------------------------

    /**
     * Parsed fieldConfig from bill.additionalDetails. Null fieldConfigs means
     * no health-context config is present — fall back to legacy behaviour.
     */
    static class FieldConfigContext {
        List<RateFieldConfig> fieldConfigs;      // null when not present (legacy)
        Map<String, String> headCodeMapping;     // fieldKey → headCode (from WorkerMdms.headCodeMapping)
        Map<String, String> reverseMapping;      // headCode → fieldKey
        Map<String, RateFieldConfig> byHeadCode; // headCode → config
        Map<String, String> fieldKeyToHeadCode;  // fieldKey → resolved headCode (fc.headCode preferred)

        boolean hasConfig() { return fieldConfigs != null && !fieldConfigs.isEmpty(); }

        /** Resolves the headCode for a given fieldKey, preferring fc.headCode over headCodeMapping. */
        String resolveHeadCode(String fieldKey) {
            if (fieldKeyToHeadCode != null && fieldKeyToHeadCode.containsKey(fieldKey))
                return fieldKeyToHeadCode.get(fieldKey);
            return fieldKey;
        }
    }

    @SuppressWarnings("unchecked")
    FieldConfigContext buildFieldConfigContext(Bill bill, RequestInfo requestInfo) {
        FieldConfigContext ctx = new FieldConfigContext();
        ctx.headCodeMapping   = Collections.emptyMap();
        ctx.reverseMapping    = Collections.emptyMap();
        ctx.byHeadCode        = Collections.emptyMap();
        ctx.fieldKeyToHeadCode = Collections.emptyMap();

        Object additionalDetails = bill.getAdditionalDetails();

        if (additionalDetails != null) {
            try {
                Map<String, Object> adMap = objectMapper.convertValue(additionalDetails,
                        new TypeReference<Map<String, Object>>() {});

                Object fcRaw = adMap.get(FIELD_CONFIG_KEY);
                if (fcRaw != null) {
                    ctx.fieldConfigs = objectMapper.convertValue(fcRaw,
                            new TypeReference<List<RateFieldConfig>>() {});
                    if (ctx.fieldConfigs != null) {
                        ctx.fieldConfigs.sort(Comparator.comparingInt(
                                f -> Optional.ofNullable(f.getOrder()).orElse(99)));
                        log.info("Loaded {} fieldConfigs from bill.additionalDetails snapshot", ctx.fieldConfigs.size());
                    }
                }

                Object hcmRaw = adMap.get(HEAD_CODE_MAP_KEY);
                if (hcmRaw != null) {
                    ctx.headCodeMapping = objectMapper.convertValue(hcmRaw,
                            new TypeReference<Map<String, String>>() {});
                }
            } catch (Exception e) {
                log.warn("Could not parse fieldConfig from bill.additionalDetails: {}", e.getMessage());
                ctx.fieldConfigs = null;
            }
        }

        // No snapshot — fetch from MDMS using parent project ID derived from bill.referenceId
        // bill.referenceId = project.getProjectHierarchy() (e.g. "parentId.childId"), and
        // MDMS WorkerMdms.campaignId is keyed by the root parent project ID.
        if (ctx.fieldConfigs == null && config.isHealthContextEnabled()) {
            String billReferenceId = bill.getReferenceId();
            String campaignId = null;
            if (billReferenceId != null && !billReferenceId.isBlank()) {
                campaignId = billReferenceId.contains(".")
                        ? billReferenceId.split("\\.")[0]
                        : billReferenceId;
            }

            if (campaignId != null && !campaignId.isBlank()) {
                log.info("No workerRatesSnapshot — fetching fieldConfig from MDMS for campaignId={}", campaignId);
                List<RateFieldConfig> fetched = mdmsUtil.fetchWorkerRateFieldConfig(
                        requestInfo, bill.getTenantId(), campaignId);
                if (!fetched.isEmpty()) {
                    ctx.fieldConfigs = fetched;
                    log.info("Fetched {} fieldConfigs from MDMS for campaignId={}", fetched.size(), campaignId);
                }
            }

            if (ctx.fieldConfigs == null) {
                log.info("No MDMS fieldConfig available — using DEFAULT_FIELD_CONFIGS");
                ctx.fieldConfigs = new ArrayList<>(RateFieldConfig.DEFAULT_FIELD_CONFIGS);
            }
        }

        if (ctx.fieldConfigs != null) {
            ctx.reverseMapping     = new HashMap<>();
            ctx.byHeadCode         = new LinkedHashMap<>();
            ctx.fieldKeyToHeadCode = new HashMap<>();
            for (RateFieldConfig fc : ctx.fieldConfigs) {
                // Prefer headCode set directly on the config; fall back to headCodeMapping, then fieldKey
                String headCode = fc.getHeadCode() != null && !fc.getHeadCode().isBlank()
                        ? fc.getHeadCode()
                        : ctx.headCodeMapping.getOrDefault(fc.getFieldKey(), fc.getFieldKey());
                ctx.reverseMapping.put(headCode, fc.getFieldKey());
                ctx.byHeadCode.put(headCode, fc);
                ctx.fieldKeyToHeadCode.put(fc.getFieldKey(), headCode);
            }
            log.info("FieldConfigContext built: headCodes={}", ctx.byHeadCode.keySet());
        }

        return ctx;
    }

    /**
     * Returns ordered head codes for the template columns.
     * If fieldConfig is present, order follows fieldConfig.order (payable only, intersected
     * with head codes actually present in bill line items).
     * Falls back to insertion order from line items.
     */
    static List<String> resolveOrderedHeadCodes(Bill bill, FieldConfigContext fcCtx) {
        Set<String> billHeadCodes = new LinkedHashSet<>(collectPayableHeadCodes(bill));
        if (!fcCtx.hasConfig()) return new ArrayList<>(billHeadCodes);

        List<String> ordered = new ArrayList<>();
        for (RateFieldConfig fc : fcCtx.fieldConfigs) {
            if (!Boolean.TRUE.equals(fc.getIsPayable())) continue;
            String hc = fcCtx.resolveHeadCode(fc.getFieldKey());
            if (billHeadCodes.contains(hc)) ordered.add(hc);
        }
        // append any head codes present in the bill but not in fieldConfig (safety net)
        for (String hc : billHeadCodes) {
            if (!ordered.contains(hc)) ordered.add(hc);
        }
        return ordered;
    }

    // -------------------------------------------------------------------------
    // Header row
    // -------------------------------------------------------------------------

    private void writeHeaderRow(XSSFSheet sheet, XSSFCellStyle style,
                                 List<String> headCodes, FieldConfigContext fcCtx,
                                 Map<String, String> msgMap) {
        Row row = sheet.createRow(0);
        int col = 0;
        for (String[] def : STATIC_COLS) {
            createCell(row, col++, msgMap.getOrDefault(def[1], def[2]), style);
        }
        for (String headCode : headCodes) {
            String header = resolveHeadCodeHeader(headCode, fcCtx, msgMap);
            createCell(row, col++, header, style);
        }
        createCell(row, col++, msgMap.getOrDefault("EXPENSE_TEMPLATE_COL_TOTAL_ATTENDANCE", "Total Attendance"), style);
        createCell(row, col,   msgMap.getOrDefault("EXPENSE_TEMPLATE_COL_TOTAL_AMOUNT",     "Total Amount"),     style);
    }

    private String resolveHeadCodeHeader(String headCode, FieldConfigContext fcCtx, Map<String, String> msgMap) {
        if (fcCtx.hasConfig()) {
            RateFieldConfig fc = fcCtx.byHeadCode.get(headCode);
            if (fc != null && fc.getColumnLabelKey() != null) {
                // Try localization lookup; fall back to the columnLabelKey itself (descriptive key)
                String localized = msgMap.get(fc.getColumnLabelKey());
                return localized != null ? localized : fc.getColumnLabelKey();
            }
        }
        // Legacy: try EXPENSE_TEMPLATE_COL_WAGE_ key, then plain headCode
        String legacyKey = "EXPENSE_TEMPLATE_COL_WAGE_" + headCode;
        String legacyLoc = msgMap.get(legacyKey);
        return legacyLoc != null ? legacyLoc : headCode;
    }

    // -------------------------------------------------------------------------
    // Data rows
    // -------------------------------------------------------------------------

    private void writeDataRows(XSSFSheet sheet, Bill bill, List<String> headCodes,
                                FieldConfigContext fcCtx,
                                Set<String> userRoles, RequestInfo requestInfo, Map<String, String> msgMap,
                                XSSFCellStyle lockedStr, XSSFCellStyle editableStr,
                                XSSFCellStyle lockedNum, XSSFCellStyle editableNum) {
        boolean isEditor   = userRoles.contains(ROLE_PAYMENT_EDITOR);
        boolean isReviewer = userRoles.contains(ROLE_PAYMENT_REVIEWER);
        boolean canEditWages = isEditor || isReviewer;

        // headCode → column index (0-based from start of dynamic columns)
        Map<String, Integer> headCodeColIdx = new LinkedHashMap<>();
        for (int i = 0; i < headCodes.size(); i++) headCodeColIdx.put(headCodes.get(i), i);

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
            String rawRole = (individual != null && individual.getRoles() != null && !individual.getRoles().isEmpty())
                    ? individual.getRoles().get(0) : safeStr(detail.getWfStatus());
            String roleKey = "EXPENSE_TEMPLATE_ROLE_" + rawRole.toUpperCase().replace(" ", "_");
            createCell(row, col++, msgMap.getOrDefault(roleKey, rawRole), lockedStr);

            // Dynamic rate columns
            Map<String, BigDecimal> displayValues = computeDisplayValues(detail, headCodes, fcCtx);
            for (String headCode : headCodes) {
                BigDecimal val = displayValues.getOrDefault(headCode, BigDecimal.ZERO);
                createNumericCell(row, col++, val, canEditWages ? editableNum : lockedNum);
            }

            // totalAttendance
            BigDecimal attendance = detail.getTotalAttendance() != null ? detail.getTotalAttendance() : BigDecimal.ZERO;
            createNumericCell(row, col++, attendance, canEditWages ? editableNum : lockedNum);

            // totalAmount — locked formula
            int excelRow = row.getRowNum() + 1;
            int attendanceColIdx = STATIC_COL_COUNT + headCodes.size();
            String totalFormula = buildTotalFormula(headCodes, headCodeColIdx, fcCtx, attendanceColIdx, excelRow);
            createFormulaCell(row, col, totalFormula, lockedNum);
        }
    }

    // -------------------------------------------------------------------------
    // Display value computation (generator)
    // -------------------------------------------------------------------------

    /**
     * Computes the display value for each head code column.
     * Processes fieldConfigs in order so PERCENTAGE fields can use already-computed
     * stored amounts of their components.
     *
     * PER_DAY      → storedAmount / attendance
     * ONE_TIME      → storedAmount
     * PER_PERIOD   → storedAmount
     * PERCENTAGE   → storedAmount * 100 / Σ(component storedAmounts)
     *
     * Falls back to (amount / attendance) for all fields when no fieldConfig is present.
     */
    Map<String, BigDecimal> computeDisplayValues(BillDetail detail, List<String> headCodes,
                                                  FieldConfigContext fcCtx) {
        Map<String, BigDecimal> storedAmounts = buildStoredAmountMap(detail);
        BigDecimal attendance = detail.getTotalAttendance();

        Map<String, BigDecimal> displayValues = new LinkedHashMap<>();

        if (!fcCtx.hasConfig()) {
            // Legacy: divide by attendance for all columns
            for (String hc : headCodes) {
                BigDecimal amount = storedAmounts.getOrDefault(hc, BigDecimal.ZERO);
                displayValues.put(hc, divideByAttendance(amount, attendance));
            }
            return displayValues;
        }

        // Process in fieldConfig order to allow PERCENTAGE to reference earlier fields
        for (RateFieldConfig fc : fcCtx.fieldConfigs) {
            if (!Boolean.TRUE.equals(fc.getIsPayable())) continue;
            String hc = fcCtx.resolveHeadCode(fc.getFieldKey());
            if (!headCodes.contains(hc)) continue;

            BigDecimal stored = storedAmounts.getOrDefault(hc, BigDecimal.ZERO);
            BigDecimal displayVal;

            if (VALUE_TYPE_PERCENTAGE.equals(fc.getValueType())) {
                BigDecimal componentSum = sumComponentAmounts(fc, fcCtx, storedAmounts);
                if (componentSum.compareTo(BigDecimal.ZERO) == 0) {
                    displayVal = BigDecimal.ZERO;
                } else {
                    // Reverse: pct = storedAmount * 100 / Σ(component stored amounts)
                    displayVal = stored.multiply(BigDecimal.valueOf(100))
                            .divide(componentSum, 4, RoundingMode.HALF_UP);
                }
            } else if (PAYMENT_TYPE_PER_DAY.equals(fc.getPaymentType())) {
                displayVal = divideByAttendance(stored, attendance);
            } else {
                // ONE_TIME / PER_PERIOD — stored amount IS the rate
                displayVal = stored;
            }

            displayValues.put(hc, displayVal);
        }

        // Safety net: any head code not in fieldConfig
        for (String hc : headCodes) {
            if (!displayValues.containsKey(hc)) {
                BigDecimal amount = storedAmounts.getOrDefault(hc, BigDecimal.ZERO);
                displayValues.put(hc, divideByAttendance(amount, attendance));
            }
        }

        return displayValues;
    }

    private BigDecimal sumComponentAmounts(RateFieldConfig fc, FieldConfigContext fcCtx,
                                            Map<String, BigDecimal> storedAmounts) {
        if (fc.getComponents() == null || fc.getComponents().isEmpty()) return BigDecimal.ZERO;
        BigDecimal sum = BigDecimal.ZERO;
        for (String componentFieldKey : fc.getComponents()) {
            String componentHc = fcCtx.resolveHeadCode(componentFieldKey);
            sum = sum.add(storedAmounts.getOrDefault(componentHc, BigDecimal.ZERO));
        }
        return sum;
    }

    private BigDecimal divideByAttendance(BigDecimal amount, BigDecimal attendance) {
        if (attendance != null && attendance.compareTo(BigDecimal.ZERO) > 0) {
            return amount.divide(attendance, 4, RoundingMode.HALF_UP);
        }
        return amount;
    }

    /** headCode → stored line item amount from bill detail */
    private Map<String, BigDecimal> buildStoredAmountMap(BillDetail detail) {
        Map<String, BigDecimal> map = new HashMap<>();
        for (LineItem item : getPayableItems(detail)) {
            if (item.getHeadCode() != null) {
                map.put(item.getHeadCode(), item.getAmount() != null ? item.getAmount() : BigDecimal.ZERO);
            }
        }
        return map;
    }

    // -------------------------------------------------------------------------
    // Total amount Excel formula
    // -------------------------------------------------------------------------

    /**
     * Builds a formula for the total amount column.
     * If fieldConfig is present, each field contributes based on its paymentType:
     *   PER_DAY    → +colRef * attendanceRef
     *   ONE_TIME   → +colRef
     *   PER_PERIOD → +colRef
     *   PERCENTAGE (paymentType=PER_DAY)  → +(Σ componentColRefs) * attendanceRef * pctColRef / 100
     *   PERCENTAGE (paymentType=ONE_TIME) → +(Σ componentColRefs) * pctColRef / 100
     * Falls back to sum(all cols) * attendance when no fieldConfig.
     */
    String buildTotalFormula(List<String> headCodes, Map<String, Integer> headCodeColIdx,
                              FieldConfigContext fcCtx, int attendanceColIdx, int excelRow) {
        if (headCodes.isEmpty()) return "0";

        String attendanceRef = CellReference.convertNumToColString(attendanceColIdx) + excelRow;

        if (!fcCtx.hasConfig()) {
            // Legacy: sum(all wage cols) * attendance
            StringBuilder sb = new StringBuilder("(");
            for (int i = 0; i < headCodes.size(); i++) {
                if (i > 0) sb.append("+");
                sb.append(CellReference.convertNumToColString(STATIC_COL_COUNT + i)).append(excelRow);
            }
            sb.append(")*").append(attendanceRef);
            return sb.toString();
        }

        StringBuilder formula = new StringBuilder();

        for (RateFieldConfig fc : fcCtx.fieldConfigs) {
            if (!Boolean.TRUE.equals(fc.getIsPayable())) continue;
            String hc = fcCtx.resolveHeadCode(fc.getFieldKey());
            Integer colOffset = headCodeColIdx.get(hc);
            if (colOffset == null) continue;

            String colRef = CellReference.convertNumToColString(STATIC_COL_COUNT + colOffset) + excelRow;

            if (VALUE_TYPE_PERCENTAGE.equals(fc.getValueType())) {
                String componentSum = buildComponentSumRef(fc, fcCtx, headCodeColIdx, excelRow);
                if (componentSum.isEmpty()) continue;
                if (PAYMENT_TYPE_PER_DAY.equals(fc.getPaymentType())) {
                    formula.append("+").append("(").append(componentSum).append(")")
                           .append("*").append(attendanceRef)
                           .append("*").append(colRef).append("/100");
                } else {
                    formula.append("+").append("(").append(componentSum).append(")")
                           .append("*").append(colRef).append("/100");
                }
            } else if (PAYMENT_TYPE_PER_DAY.equals(fc.getPaymentType())) {
                formula.append("+").append(colRef).append("*").append(attendanceRef);
            } else {
                // ONE_TIME / PER_PERIOD
                formula.append("+").append(colRef);
            }
        }

        // Safety net for head codes not covered by fieldConfig
        for (String hc : headCodes) {
            if (fcCtx.byHeadCode.containsKey(hc)) continue;
            Integer colOffset = headCodeColIdx.get(hc);
            if (colOffset == null) continue;
            String colRef = CellReference.convertNumToColString(STATIC_COL_COUNT + colOffset) + excelRow;
            formula.append("+").append(colRef).append("*").append(attendanceRef);
        }

        String result = formula.toString();
        return result.startsWith("+") ? result.substring(1) : (result.isEmpty() ? "0" : result);
    }

    private String buildComponentSumRef(RateFieldConfig fc, FieldConfigContext fcCtx,
                                         Map<String, Integer> headCodeColIdx, int excelRow) {
        if (fc.getComponents() == null || fc.getComponents().isEmpty()) return "";
        List<String> refs = new ArrayList<>();
        for (String componentFieldKey : fc.getComponents()) {
            String hc = fcCtx.resolveHeadCode(componentFieldKey);
            Integer colOffset = headCodeColIdx.get(hc);
            if (colOffset != null) {
                refs.add(CellReference.convertNumToColString(STATIC_COL_COUNT + colOffset) + excelRow);
            }
        }
        return String.join("+", refs);
    }

    // -------------------------------------------------------------------------
    // Existing helpers (unchanged)
    // -------------------------------------------------------------------------

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

    public static List<LineItem> getPayableItems(BillDetail detail) {
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
        // autoSizeColumn() requires AWT/fontconfig which is absent in Alpine Docker
        // images. Use fixed widths instead (POI unit = 1/256 of a character width).
        int[] staticWidths = {
            6000,  // workerId
            5000,  // username
            7000,  // name
            7000,  // payeeName
            5000,  // mobileNumber
            5500,  // payeePhoneNumber
            6000,  // bankAccount
            4500,  // bankCode
            6500,  // beneficiaryCode
            7500,  // role
        };
        for (int i = 0; i < staticWidths.length; i++) {
            sheet.setColumnWidth(i, staticWidths[i]);
        }
        for (int i = 0; i < headCodeCount; i++) {
            sheet.setColumnWidth(STATIC_COL_COUNT + i, 6000);
        }
        sheet.setColumnWidth(STATIC_COL_COUNT + headCodeCount,     4500);
        sheet.setColumnWidth(STATIC_COL_COUNT + headCodeCount + 1, 5000);
    }

    private String safeStr(String value) {
        return value != null ? value : "";
    }
}
