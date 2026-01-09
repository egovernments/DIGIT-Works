package org.egov.digit.expense.calculator.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.calculator.web.models.BillingPeriod;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * BillingPeriodUtil
 *
 * Utility class for billing period date calculations and intersection logic.
 * Provides methods for:
 * - Date intersection calculations
 * - Period overlap detection
 * - Date range validations
 * - Date formatting for logging
 *
 * @author DIGIT-Works
 */
@Component
@Slf4j
public class BillingPeriodUtil {

    private static final long ONE_DAY_MS = 24 * 60 * 60 * 1000L;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Calculates intersection between two date ranges.
     *
     * Algorithm:
     * - Intersection start = MAX(range1Start, range2Start)
     * - Intersection end = MIN(range1End, range2End)
     * - If start > end, no intersection exists
     *
     * Example:
     * Range 1: Jan 1 - Jan 30
     * Range 2: Jan 8 - Jan 14
     * Intersection: Jan 8 - Jan 14
     *
     * @param range1Start First range start date (epoch ms)
     * @param range1End First range end date (epoch ms)
     * @param range2Start Second range start date (epoch ms)
     * @param range2End Second range end date (epoch ms)
     * @return DateIntersection object with intersection details
     */
    public DateIntersection calculateIntersection(long range1Start, long range1End,
                                                 long range2Start, long range2End) {
        // Calculate intersection boundaries
        long intersectionStart = Math.max(range1Start, range2Start);
        long intersectionEnd = Math.min(range1End, range2End);

        // Check if intersection exists
        boolean hasIntersection = intersectionStart <= intersectionEnd;

        if (!hasIntersection) {
            log.debug("No intersection between ranges: [{} to {}] and [{} to {}]",
                formatDate(range1Start), formatDate(range1End),
                formatDate(range2Start), formatDate(range2End));

            return DateIntersection.builder()
                .hasIntersection(false)
                .startDate(0L)
                .endDate(0L)
                .durationMs(0L)
                .days(0)
                .build();
        }

        // Calculate intersection duration
        long durationMs = intersectionEnd - intersectionStart + 1;
        int days = (int) (durationMs / ONE_DAY_MS);

        log.debug("Intersection found: {} to {} ({} days)",
            formatDate(intersectionStart), formatDate(intersectionEnd), days);

        return DateIntersection.builder()
            .hasIntersection(true)
            .startDate(intersectionStart)
            .endDate(intersectionEnd)
            .durationMs(durationMs)
            .days(days)
            .build();
    }

    /**
     * Checks if two date ranges overlap.
     *
     * Overlap exists if:
     * - Range1 start is before Range2 end, AND
     * - Range1 end is after Range2 start
     *
     * @param range1Start First range start date (epoch ms)
     * @param range1End First range end date (epoch ms)
     * @param range2Start Second range start date (epoch ms)
     * @param range2End Second range end date (epoch ms)
     * @return true if ranges overlap
     */
    public boolean hasOverlap(long range1Start, long range1End,
                             long range2Start, long range2End) {
        // Standard interval overlap check
        return !(range1End < range2Start || range1Start > range2End);
    }

    /**
     * Checks if a register overlaps with a billing period.
     *
     * @param registerStart Register start date (epoch ms)
     * @param registerEnd Register end date (epoch ms)
     * @param period Billing period
     * @return true if register overlaps with period
     */
    public boolean isRegisterOverlappingWithPeriod(long registerStart, long registerEnd,
                                                  BillingPeriod period) {
        return hasOverlap(
            registerStart, registerEnd,
            period.getPeriodStartDate(), period.getPeriodEndDate()
        );
    }

    /**
     * Checks if a date falls within a date range.
     *
     * @param date Date to check (epoch ms)
     * @param rangeStart Range start date (epoch ms)
     * @param rangeEnd Range end date (epoch ms)
     * @return true if date is within range (inclusive)
     */
    public boolean isDateInRange(long date, long rangeStart, long rangeEnd) {
        return date >= rangeStart && date <= rangeEnd;
    }

    /**
     * Checks if a date falls within a billing period.
     *
     * @param date Date to check (epoch ms)
     * @param period Billing period
     * @return true if date is within period
     */
    public boolean isDateInPeriod(long date, BillingPeriod period) {
        return isDateInRange(date, period.getPeriodStartDate(), period.getPeriodEndDate());
    }

    /**
     * Calculates number of days between two dates (inclusive).
     *
     * @param startDate Start date (epoch ms)
     * @param endDate End date (epoch ms)
     * @return Number of days
     */
    public int calculateDays(long startDate, long endDate) {
        if (endDate < startDate) {
            return 0;
        }
        return (int) ((endDate - startDate) / ONE_DAY_MS) + 1;
    }

    /**
     * Formats epoch milliseconds to readable date string.
     *
     * @param epochMs Epoch milliseconds
     * @return Formatted date string (yyyy-MM-dd)
     */
    public String formatDate(long epochMs) {
        return DATE_FORMAT.format(new Date(epochMs));
    }

    /**
     * Formats epoch milliseconds to detailed date-time string.
     *
     * @param epochMs Epoch milliseconds
     * @return Formatted date-time string
     */
    public String formatDateTime(long epochMs) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateTimeFormat.format(new Date(epochMs));
    }

    /**
     * Validates if date range is valid.
     *
     * @param startDate Start date (epoch ms)
     * @param endDate End date (epoch ms)
     * @return true if start date is before or equal to end date
     */
    public boolean isValidDateRange(long startDate, long endDate) {
        return startDate <= endDate;
    }

    /**
     * Calculates the percentage of a period that has elapsed.
     *
     * @param period Billing period
     * @param currentDate Current date (epoch ms)
     * @return Percentage (0-100) or -1 if current date is outside period
     */
    public double calculatePeriodElapsedPercentage(BillingPeriod period, long currentDate) {
        if (!isDateInPeriod(currentDate, period)) {
            return -1;
        }

        long periodDuration = period.getPeriodDuration();
        long elapsed = currentDate - period.getPeriodStartDate();

        return (elapsed * 100.0) / periodDuration;
    }

    /**
     * Gets the number of days remaining in a period.
     *
     * @param period Billing period
     * @param currentDate Current date (epoch ms)
     * @return Days remaining or -1 if current date is after period end
     */
    public int getDaysRemainingInPeriod(BillingPeriod period, long currentDate) {
        if (currentDate > period.getPeriodEndDate()) {
            return -1;
        }

        if (currentDate < period.getPeriodStartDate()) {
            return period.getPeriodDurationInDays();
        }

        return calculateDays(currentDate, period.getPeriodEndDate());
    }

    /**
     * Checks if a period has started.
     *
     * @param period Billing period
     * @param currentDate Current date (epoch ms)
     * @return true if current date is after or on period start date
     */
    public boolean hasPeriodStarted(BillingPeriod period, long currentDate) {
        return currentDate >= period.getPeriodStartDate();
    }

    /**
     * Checks if a period has ended.
     *
     * @param period Billing period
     * @param currentDate Current date (epoch ms)
     * @return true if current date is after period end date
     */
    public boolean hasPeriodEnded(BillingPeriod period, long currentDate) {
        return currentDate > period.getPeriodEndDate();
    }

    /**
     * Checks if a period is currently active.
     *
     * @param period Billing period
     * @param currentDate Current date (epoch ms)
     * @return true if current date is within period
     */
    public boolean isPeriodActive(BillingPeriod period, long currentDate) {
        return hasPeriodStarted(period, currentDate) && !hasPeriodEnded(period, currentDate);
    }

    /**
     * DateIntersection
     *
     * Inner class representing the intersection of two date ranges.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DateIntersection {
        private boolean hasIntersection;
        private long startDate;
        private long endDate;
        private long durationMs;
        private int days;

        /**
         * Gets intersection duration in days.
         *
         * @return Duration in days
         */
        public int getDays() {
            return days;
        }

        /**
         * Gets intersection duration in milliseconds.
         *
         * @return Duration in milliseconds
         */
        public long getDurationMs() {
            return durationMs;
        }

        /**
         * Checks if intersection exists.
         *
         * @return true if intersection exists
         */
        public boolean hasIntersection() {
            return hasIntersection;
        }
    }
}
