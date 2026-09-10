package org.egov.digit.expense.web.validators;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.common.contract.request.User;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.util.BillPeriodUtil;
import org.egov.digit.expense.util.MdmsUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.LineItemType;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.egov.digit.expense.config.Constants.ROLE_PAYMENT_EDITOR;
import static org.egov.digit.expense.config.Constants.ROLE_PAYMENT_REVIEWER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Attendance ceiling in BillValidator, covering both update paths.
 * The real demo period 07–09 Sep 2026 is used, so maxDays = 3.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BillValidatorAttendanceLimitTest {

    private static final long PERIOD_START = 1788719400000L;
    private static final long PERIOD_END   = 1788978599999L;

    @Mock private MdmsUtil mdmsUtil;
    @Mock private Configuration configs;
    @Mock private BillRepository billRepository;

    private BillValidator validator;

    @BeforeEach
    void setUp() {
        ObjectMapper mapper = new ObjectMapper();
        validator = new BillValidator(mdmsUtil, configs, billRepository, mapper,
                new BillPeriodUtil(mapper));
    }

    private Bill billWithPeriod() {
        Map<String, Object> ad = new HashMap<>();
        ad.put("periodStartDate", PERIOD_START);
        ad.put("periodEndDate", PERIOD_END);
        return Bill.builder().id("bill-1").tenantId("demo").additionalDetails(ad).build();
    }

    private Bill billWithoutPeriod() {
        return Bill.builder().id("bill-legacy").tenantId("demo").build();
    }

    @SuppressWarnings("unchecked")
    private List<BillDetailUpdateError> invokePartial(Bill existing, List<PartialBillDetail> partials) {
        try {
            Method m = BillValidator.class.getDeclaredMethod(
                    "validateAttendanceLimits", Bill.class, List.class);
            m.setAccessible(true);
            return (List<BillDetailUpdateError>) m.invoke(validator, existing, partials);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<BillDetailUpdateError> invokeFullBill(Bill updated, Bill existing) {
        try {
            Method m = BillValidator.class.getDeclaredMethod(
                    "validateAttendanceLimits", Bill.class, Bill.class);
            m.setAccessible(true);
            return (List<BillDetailUpdateError>) m.invoke(validator, updated, existing);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private PartialBillDetail partial(BigDecimal attendance) {
        return PartialBillDetail.builder().id("d1").totalAttendance(attendance).build();
    }

    private Bill updatedBillWith(BigDecimal attendance) {
        return Bill.builder().id("bill-1").tenantId("demo")
                .billDetails(List.of(BillDetail.builder().id("d1")
                        .totalAttendance(attendance).build()))
                .build();
    }

    // ── billdetails/_update ───────────────────────────────────────────────────

    @Test
    void partialUpdateRejectsAttendanceBeyondPeriod() {
        assertEquals(1, invokePartial(billWithPeriod(), List.of(partial(BigDecimal.valueOf(4)))).size());
    }

    @Test
    void partialUpdateAllowsAttendanceAtCeiling() {
        assertTrue(invokePartial(billWithPeriod(), List.of(partial(BigDecimal.valueOf(3)))).isEmpty());
    }

    @Test
    void partialUpdateAllowsZeroAndHalfDays() {
        assertTrue(invokePartial(billWithPeriod(), List.of(partial(BigDecimal.ZERO))).isEmpty());
        assertTrue(invokePartial(billWithPeriod(), List.of(partial(new BigDecimal("2.5")))).isEmpty());
    }

    @Test
    void partialUpdateIgnoresNullAttendance() {
        // An editor's attendance is stripped before this runs, leaving null
        assertTrue(invokePartial(billWithPeriod(), List.of(partial(null))).isEmpty());
    }

    // ── negatives must be caught even with no billing period ──────────────────

    @Test
    void negativeAttendanceIsRejectedEvenWithoutABillingPeriod() {
        List<BillDetailUpdateError> errors =
                invokePartial(billWithoutPeriod(), List.of(partial(BigDecimal.valueOf(-1))));
        assertEquals(1, errors.size());
        assertEquals("EG_EXPENSE_ATTENDANCE_LIMIT_EXCEEDED", errors.get(0).getCode());
    }

    @Test
    void legacyBillWithoutPeriodHasNoUpperBound() {
        assertTrue(invokePartial(billWithoutPeriod(), List.of(partial(BigDecimal.valueOf(9999)))).isEmpty());
    }

    // ── bill/_update (full bill) ──────────────────────────────────────────────

    @Test
    void fullBillUpdateRejectsAttendanceBeyondPeriod() {
        assertEquals(1, invokeFullBill(updatedBillWith(BigDecimal.valueOf(9999)), billWithPeriod()).size());
    }

    @Test
    void fullBillUpdateAllowsAttendanceWithinPeriod() {
        assertTrue(invokeFullBill(updatedBillWith(BigDecimal.valueOf(3)), billWithPeriod()).isEmpty());
    }

    @Test
    void fullBillUpdateRejectsNegativeAttendance() {
        assertEquals(1, invokeFullBill(updatedBillWith(BigDecimal.valueOf(-2)), billWithPeriod()).size());
    }

    // ── a total must have line items to account for it ───────────────────────

    @SuppressWarnings("unchecked")
    private List<BillDetailUpdateError> invokeBreakdown(PartialBillDetail pd, BillDetail db) {
        try {
            Method m = BillValidator.class.getDeclaredMethod(
                    "validateAmountHasLineItems", List.class, Map.class);
            m.setAccessible(true);
            Map<String, BillDetail> dbMap = db == null ? Map.of() : Map.of(db.getId(), db);
            return (List<BillDetailUpdateError>) m.invoke(validator, List.of(pd), dbMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private LineItem payable(String headCode, double amount) {
        return LineItem.builder().id("li-" + headCode).headCode(headCode)
                .type(LineItemType.PAYABLE).amount(BigDecimal.valueOf(amount))
                .status(Status.ACTIVE).build();
    }

    @Test
    void positiveTotalWithNoLineItemsAnywhereIsRejected() {
        // what the UI posts for a worker the calculator left without payable rows
        PartialBillDetail pd = PartialBillDetail.builder().id("d1")
                .totalAmount(BigDecimal.valueOf(50))
                .payableLineItems(List.of())
                .build();
        List<BillDetailUpdateError> errors = invokeBreakdown(pd, BillDetail.builder().id("d1").build());

        assertEquals(1, errors.size());
        assertEquals("EG_EXPENSE_AMOUNT_WITHOUT_LINE_ITEMS", errors.get(0).getCode());
    }

    @Test
    void positiveTotalWithSubmittedLineItemsIsFine() {
        PartialBillDetail pd = PartialBillDetail.builder().id("d1")
                .totalAmount(BigDecimal.valueOf(50))
                .payableLineItems(List.of(payable("PER_DAY", 50)))
                .build();
        assertTrue(invokeBreakdown(pd, BillDetail.builder().id("d1").build()).isEmpty());
    }

    @Test
    void totalOnlyUpdateLeansOnThePersistedLineItems() {
        // no line items submitted at all — the stored ones still account for the total
        PartialBillDetail pd = PartialBillDetail.builder().id("d1")
                .totalAmount(BigDecimal.valueOf(50)).build();
        BillDetail db = BillDetail.builder().id("d1")
                .payableLineItems(List.of(payable("PER_DAY", 50))).build();
        assertTrue(invokeBreakdown(pd, db).isEmpty());
    }

    @Test
    void submittingAnEmptyListIgnoresThePersistedOnes() {
        // an explicit empty list means "these are my line items", so the DB cannot vouch for it
        PartialBillDetail pd = PartialBillDetail.builder().id("d1")
                .totalAmount(BigDecimal.valueOf(50))
                .payableLineItems(List.of())
                .build();
        BillDetail db = BillDetail.builder().id("d1")
                .payableLineItems(List.of(payable("PER_DAY", 50))).build();
        assertEquals(1, invokeBreakdown(pd, db).size());
    }

    @Test
    void emptyLineItemsWithPayablesOmittedStillLeansOnTheDb() {
        // lineItems: [] does not speak for the payables — the stored ones still account for it
        PartialBillDetail pd = PartialBillDetail.builder().id("d1")
                .totalAmount(BigDecimal.valueOf(50))
                .lineItems(List.of())
                .build();
        BillDetail db = BillDetail.builder().id("d1")
                .payableLineItems(List.of(payable("PER_DAY", 50))).build();
        assertTrue(invokeBreakdown(pd, db).isEmpty());
    }

    @SuppressWarnings("unchecked")
    private List<String> invokeStripAmounts(PartialBillDetail pd, BillDetail db) {
        try {
            Method m = BillValidator.class.getDeclaredMethod(
                    "stripAmountFields", PartialBillDetail.class, BillDetail.class, List.class, Set.class);
            m.setAccessible(true);
            List<BillDetailUpdateError> warnings = new ArrayList<>();
            m.invoke(validator, pd, db, warnings, new HashSet<String>());
            return warnings.stream().map(BillDetailUpdateError::getMessage).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void editorEmptyPayablesAreNormalisedToNullWithoutAWarning() {
        // left as [] they would wipe the persisted rows and trip the breakdown check
        PartialBillDetail pd = PartialBillDetail.builder().id("d1")
                .totalAmount(BigDecimal.valueOf(50))
                .payableLineItems(new ArrayList<>())
                .lineItems(new ArrayList<>())
                .build();
        BillDetail db = BillDetail.builder().id("d1").totalAmount(BigDecimal.valueOf(50))
                .payableLineItems(List.of(payable("PER_DAY", 50))).build();

        List<String> warnings = invokeStripAmounts(pd, db);

        assertNull(pd.getPayableLineItems(), "empty list must become null");
        assertNull(pd.getLineItems());
        assertTrue(warnings.isEmpty(), "nothing was actually rejected, so no warning");
        assertTrue(invokeBreakdown(pd, db).isEmpty(), "and the breakdown check now passes");
    }

    @Test
    void zeroTotalNeedsNoLineItems() {
        PartialBillDetail pd = PartialBillDetail.builder().id("d1")
                .totalAmount(BigDecimal.ZERO).payableLineItems(List.of()).build();
        assertTrue(invokeBreakdown(pd, BillDetail.builder().id("d1").build()).isEmpty());
    }

    // ── the rate snapshot is capped regardless of attendance ─────────────────

    @SuppressWarnings("unchecked")
    private List<BillDetailUpdateError> invokeSnapshotLimits(String key, Object rate, Bill existingBill) {
        Map<String, Object> ad = new HashMap<>();
        ad.put("rateBreakup", Map.of(key, rate));
        PartialBillDetail pd = PartialBillDetail.builder().id("d1")
                .totalAttendance(BigDecimal.ZERO).additionalDetails(ad).build();
        try {
            Method m = BillValidator.class.getDeclaredMethod(
                    "validateRateSnapshotLimits", Bill.class, List.class, Map.class);
            m.setAccessible(true);
            return (List<BillDetailUpdateError>) m.invoke(validator, existingBill, List.of(pd),
                    Map.of("PER_DAY", BigDecimal.valueOf(150)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<BillDetailUpdateError> invokeSnapshotLimits(Object rate) {
        return invokeSnapshotLimits("PER_DAY", rate, billWithPeriod());
    }

    /** Bill whose fieldConfig declares a PERCENTAGE field keyed by FEE_PCT. */
    private Bill billWithPercentageConfig() {
        Map<String, Object> ad = new HashMap<>();
        ad.put("periodStartDate", PERIOD_START);
        ad.put("periodEndDate", PERIOD_END);
        ad.put("workerRatesSnapshot", List.of(Map.of(
                "fieldKey", "FEES", "isPayable", true, "valueType", "PERCENTAGE",
                "paymentType", "PER_DAY", "percentageKey", "FEE_PCT",
                "components", List.of("PER_DAY"))));
        return Bill.builder().id("bill-pct").tenantId("demo").additionalDetails(ad).build();
    }

    @SuppressWarnings("unchecked")
    private List<BillDetailUpdateError> invokeSnapshotLimitsNoMdms(Object rate) {
        Map<String, Object> ad = new HashMap<>();
        ad.put("rateBreakup", Map.of("PER_DAY", rate));
        PartialBillDetail pd = PartialBillDetail.builder().id("d1")
                .totalAttendance(BigDecimal.ZERO).additionalDetails(ad).build();
        try {
            Method m = BillValidator.class.getDeclaredMethod(
                    "validateRateSnapshotLimits", Bill.class, List.class, Map.class);
            m.setAccessible(true);
            return (List<BillDetailUpdateError>) m.invoke(validator, billWithPeriod(), List.of(pd), Map.of());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void nonNumericRateIsRejectedRatherThanSkipped() {
        assertEquals(1, invokeSnapshotLimits("PER_DAY", "abc", billWithPeriod()).size(),
                "a skipped value would still be persisted");
    }

    @Test
    void negativeRateIsRejectedEvenWithNoMdmsLimit() {
        assertEquals(1, invokeSnapshotLimitsNoMdms(-5).size());
        assertTrue(invokeSnapshotLimitsNoMdms(9999).isEmpty(),
                "with no MDMS limit there is no ceiling to apply for a flat field");
    }

    @Test
    void percentageRateDefaultsToACapOf100() {
        assertEquals(1, invokeSnapshotLimits("FEE_PCT", 150, billWithPercentageConfig()).size(),
                "no explicit MDMS limit for a percentage field means max 100");
        assertTrue(invokeSnapshotLimits("FEE_PCT", 100, billWithPercentageConfig()).isEmpty());
    }

    @Test
    void outOfRangeRateIsRejectedEvenAtZeroAttendance() {
        // the line-item check is skipped at 0 days, so this is the only thing guarding it
        assertEquals(1, invokeSnapshotLimits(9999).size());
        assertEquals(1, invokeSnapshotLimits(-1).size());
    }

    @Test
    void inRangeRateIsAccepted() {
        assertTrue(invokeSnapshotLimits(150).isEmpty());
        assertTrue(invokeSnapshotLimits(0).isEmpty());
    }

    // ── editors must not be able to forge the rate snapshot ───────────────────

    @SuppressWarnings("unchecked")
    private Map<String, Object> invokeRestore(PartialBillDetail pd, BillDetail db) {
        try {
            Method m = BillValidator.class.getDeclaredMethod(
                    "restoreCalculationMetadata", PartialBillDetail.class, BillDetail.class);
            m.setAccessible(true);
            m.invoke(validator, pd, db);
            return new ObjectMapper().convertValue(pd.getAdditionalDetails(), Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private BillDetail dbDetailWithRate(Object rate) {
        Map<String, Object> ad = new HashMap<>();
        ad.put("rateBreakup", Map.of("PER_DAY", rate));
        ad.put("attendance", 3);
        return BillDetail.builder().id("d1").additionalDetails(ad).build();
    }

    @Test
    void editorForgedRateIsReplacedByThePersistedOne() {
        Map<String, Object> forged = new HashMap<>();
        forged.put("rateBreakup", Map.of("PER_DAY", 9999));
        forged.put("editInfo", Map.of("payeeUpdatedAtEpochMs", 1L));
        PartialBillDetail pd = PartialBillDetail.builder().id("d1").additionalDetails(forged).build();

        Map<String, Object> result = invokeRestore(pd, dbDetailWithRate(10));

        assertEquals(Map.of("PER_DAY", 10), result.get("rateBreakup"), "the DB rate must win");
        assertNotNull(result.get("editInfo"), "the editor's own key must survive");
    }

    @Test
    void editorOmittingTheSnapshotDoesNotDeleteIt() {
        // EnrichmentUtil takes additionalDetails wholesale, so an omitted key would be lost
        Map<String, Object> submitted = new HashMap<>();
        submitted.put("editInfo", Map.of("payeeUpdatedAtEpochMs", 1L));
        PartialBillDetail pd = PartialBillDetail.builder().id("d1").additionalDetails(submitted).build();

        Map<String, Object> result = invokeRestore(pd, dbDetailWithRate(10));

        assertEquals(Map.of("PER_DAY", 10), result.get("rateBreakup"), "must be restored, not dropped");
        assertEquals(3, result.get("attendance"));
    }

    @Test
    void fullBillUpdateWithNoDetailsIsANoOp() {
        assertTrue(invokeFullBill(Bill.builder().id("b").build(), billWithPeriod()).isEmpty());
        assertTrue(invokeFullBill(null, billWithPeriod()).isEmpty());
    }

    // ── A restored snapshot must not be validated against the caller ──────────

    private RequestInfo requestInfoWithRole(String roleCode) {
        return RequestInfo.builder()
                .userInfo(User.builder()
                        .uuid("user-1")
                        .roles(List.of(Role.builder().code(roleCode).build()))
                        .build())
                .build();
    }

    /** Bill in the given status carrying one detail whose stored snapshot holds {@code rate}. */
    private Bill billWithStoredRate(Status status, Object rate) {
        Map<String, Object> detailAd = new HashMap<>();
        detailAd.put("rateBreakup", Map.of("PER_DAY", rate));
        BillDetail db = BillDetail.builder().id("d1").tenantId("demo")
                .status(status).additionalDetails(detailAd).build();

        Map<String, Object> billAd = new HashMap<>();
        billAd.put("periodStartDate", PERIOD_START);
        billAd.put("periodEndDate", PERIOD_END);
        return Bill.builder().id("bill-1").tenantId("demo").status(status)
                .additionalDetails(billAd).billDetails(List.of(db)).build();
    }

    private BillDetailUpdateRequest updateRequest(String roleCode, PartialBillDetail pd) {
        return BillDetailUpdateRequest.builder()
                .requestInfo(requestInfoWithRole(roleCode))
                .billId("bill-1").tenantId("demo")
                .billDetails(new ArrayList<>(List.of(pd)))
                .build();
    }

    @Test
    void editorIsNotLockedOutByANonCompliantStoredSnapshot() {
        // the UI echoes additionalDetails with only its own key, and step 3 injects the DB
        // rateBreakup — validating it would 400 on a value the editor cannot change
        when(billRepository.search(any(), eq(true)))
                .thenReturn(List.of(billWithStoredRate(Status.PENDING_VERIFICATION, -5)));

        Map<String, Object> submitted = new HashMap<>();
        submitted.put("editInfo", Map.of("payeeUpdatedAtEpochMs", 1L));
        PartialBillDetail pd = PartialBillDetail.builder().id("d1")
                .additionalDetails(submitted).build();

        assertDoesNotThrow(() ->
                validator.validateBillDetailUpdateRequest(updateRequest(ROLE_PAYMENT_EDITOR, pd)));
    }

    @Test
    void reviewerSubmittingANegativeRateIsStillRejected() {
        when(billRepository.search(any(), eq(true)))
                .thenReturn(List.of(billWithStoredRate(Status.UNDER_REVIEW, 10)));

        Map<String, Object> submitted = new HashMap<>();
        submitted.put("rateBreakup", Map.of("PER_DAY", -5));
        PartialBillDetail pd = PartialBillDetail.builder().id("d1")
                .additionalDetails(submitted).build();

        CustomException e = assertThrows(CustomException.class, () ->
                validator.validateBillDetailUpdateRequest(updateRequest(ROLE_PAYMENT_REVIEWER, pd)));
        assertTrue(e.getMessage().contains("cannot be negative"), e.getMessage());
    }
}
