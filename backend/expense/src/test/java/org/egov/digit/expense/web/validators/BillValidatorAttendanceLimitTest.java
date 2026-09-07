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

    @Test
    void fullBillUpdateWithNoDetailsIsANoOp() {
        assertTrue(invokeFullBill(Bill.builder().id("b").build(), billWithPeriod()).isEmpty());
        assertTrue(invokeFullBill(null, billWithPeriod()).isEmpty());
    }
}
