package org.egov.digit.expense.web.validators;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.util.BillPeriodUtil;
import org.egov.digit.expense.util.MdmsUtil;
import org.egov.digit.expense.web.models.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
}
