package org.egov.digit.expense.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.web.models.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.egov.digit.expense.config.Constants.*;

/**
 * Resolves the maximum attendance days a Payment Reviewer may record against a bill,
 * from the billing period stamped into bill.additionalDetails by health-expense-calculator.
 *
 * This is the single source of the attendance ceiling for the expense service — the Excel
 * template, the template parser and BillValidator all resolve it here so they cannot drift.
 *
 * The day count mirrors PeriodGenerationService.calculateDays in health-expense-calculator —
 * the service that generates these periods. Generation is pure epoch-millisecond stepping
 * (periodEndDate = periodStartDate + periodDurationMs - 1) with no timezone anchoring, so the
 * span must be measured the same way. Converting to calendar dates in a service timezone
 * over-counts by a day whenever the period boundaries are not midnight-aligned in that zone.
 *
 * Weekends and holidays are NOT deducted. Distinct from muster-roll's
 * MusterRollValidator.validateAndEnrichAttendance, which caps against the whole attendance
 * register; per billing cycle is tighter and is what a payment for that cycle must respect.
 */
@Component
@Slf4j
public class BillPeriodUtil {

    /** Mirrors PeriodGenerationService.ONE_DAY_MS — periods are stepped in whole days. */
    private static final long ONE_DAY_MS = 24 * 60 * 60 * 1000L;

    private final ObjectMapper objectMapper;

    @Autowired
    public BillPeriodUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Inclusive day count of the bill's billing period, or null when the period is absent or
     * unusable — callers must treat null as "no ceiling known" and skip the check rather than
     * reject, so legacy bills without period metadata stay uploadable.
     */
    public Integer resolveMaxAttendanceDays(Bill bill) {
        if (bill == null || bill.getAdditionalDetails() == null) return null;

        Long start;
        Long end;
        try {
            Map<String, Object> adMap = objectMapper.convertValue(bill.getAdditionalDetails(),
                    new TypeReference<Map<String, Object>>() {});
            start = toEpochMillis(adMap.get(PERIOD_START_DATE_KEY));
            end   = toEpochMillis(adMap.get(PERIOD_END_DATE_KEY));
        } catch (Exception e) {
            log.warn("Could not read billing period from bill.additionalDetails for billId={}: {}",
                    bill.getId(), e.getMessage());
            return null;
        }

        if (start == null || end == null) {
            log.info("No billing period on billId={} — attendance ceiling not enforced", bill.getId());
            return null;
        }
        if (end < start) {
            log.warn("Billing period ends before it starts on billId={} (start={} end={}) — "
                    + "attendance ceiling not enforced", bill.getId(), start, end);
            return null;
        }

        // Same formula as PeriodGenerationService.calculateDays, which produced these bounds
        int days = (int) ((end - start) / ONE_DAY_MS) + 1;
        log.debug("Max attendance days for billId={}: {} (start={} end={})",
                bill.getId(), days, start, end);
        return days;
    }

    /** Human-readable range for error messages and Excel error boxes. */
    public static String attendanceRangeText(Integer maxDays) {
        return maxDays != null ? "between 0 and " + maxDays : "0 or greater";
    }

    private Long toEpochMillis(Object value) {
        if (value == null) return null;
        if (value instanceof Number) return ((Number) value).longValue();
        try {
            String s = value.toString().trim();
            return s.isEmpty() ? null : Long.parseLong(s);
        } catch (NumberFormatException e) {
            log.warn("Billing period value is not an epoch timestamp: {}", value);
            return null;
        }
    }
}
