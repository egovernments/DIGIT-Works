package org.egov.digit.expense.calculator.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.calculator.web.models.BillingConfig;
import org.egov.digit.expense.calculator.web.models.BillingPeriod;
import org.egov.digit.expense.calculator.web.models.enums.BillingFrequency;
import org.egov.tracer.model.CustomException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * PeriodGenerationService
 *
 * Generates billing periods from billing configuration based on frequency.
 * Handles all frequency types: WEEKLY, BI_WEEKLY, MONTHLY, CUSTOM, END_OF_CAMPAIGN.
 *
 * Algorithm:
 * 1. Calculate period duration from billing frequency
 * 2. Split project timeline into equal periods
 * 3. Handle last period (may be shorter than others)
 * 4. Create period objects with proper metadata
 *
 * Edge cases handled:
 * - Project duration shorter than frequency (creates single period)
 * - Last period adjustment to match project end date
 * - Leap year handling for monthly periods
 * - Timezone-independent calculations (uses epoch milliseconds)
 *
 * @author DIGIT-Works
 */
@Service
@Slf4j
public class PeriodGenerationService {

    // Time constants in milliseconds
    private static final long ONE_DAY_MS = 24 * 60 * 60 * 1000L;
    private static final long ONE_WEEK_MS = 7 * ONE_DAY_MS;
    private static final long TWO_WEEKS_MS = 14 * ONE_DAY_MS;
    private static final long THIRTY_DAYS_MS = 30 * ONE_DAY_MS;

    // Date formatter for logging (thread-safe)
    private static final DateTimeFormatter DATE_FORMAT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")
            .withZone(ZoneId.systemDefault());

    /**
     * Generates billing periods based on billing configuration.
     *
     * Main entry point for period generation. Routes to appropriate
     * frequency-specific generation method.
     *
     * @param config Billing configuration
     * @return List of billing periods ordered by period number
     * @throws CustomException if period generation fails
     */
    public List<BillingPeriod> generatePeriods(BillingConfig config) {
        return generatePeriods(config, 1);
    }

    public List<BillingPeriod> generatePeriods(BillingConfig config, int startingPeriodNumber) {
        log.info("Generating billing periods for campaign: {} with frequency: {} starting from period {}",
            config.getCampaignNumber(), config.getBillingFrequency(), startingPeriodNumber);

        validateConfig(config);

        BillingFrequency frequency = config.getBillingFrequency();
        List<BillingPeriod> periods;

        switch (frequency) {
            case WEEKLY:
                periods = generateWeeklyPeriods(config, startingPeriodNumber);
                break;

            case BI_WEEKLY:
                periods = generateBiWeeklyPeriods(config, startingPeriodNumber);
                break;

            case MONTHLY:
                periods = generateMonthlyPeriods(config, startingPeriodNumber);
                break;

            case CUSTOM:
                periods = generateCustomPeriods(config, startingPeriodNumber);
                break;

            case END_OF_CAMPAIGN:
                periods = generateEndOfCampaignPeriod(config, startingPeriodNumber);
                break;

            default:
                throw new CustomException("INVALID_BILLING_FREQUENCY",
                    "Unsupported billing frequency: " + frequency);
        }

        log.info("Generated {} billing periods for campaign: {}", periods.size(), config.getCampaignNumber());
        logPeriodsSummary(periods);

        return periods;
    }

    /**
     * Generates weekly billing periods (7-day intervals).
     *
     * @param config Billing configuration
     * @return List of weekly periods
     */
    private List<BillingPeriod> generateWeeklyPeriods(BillingConfig config, int startingPeriodNumber) {
        log.info("Generating weekly periods");
        return generateFixedDurationPeriods(config, ONE_WEEK_MS, BillingFrequency.WEEKLY, startingPeriodNumber);
    }

    /**
     * Generates bi-weekly billing periods (14-day intervals).
     *
     * @param config Billing configuration
     * @return List of bi-weekly periods
     */
    private List<BillingPeriod> generateBiWeeklyPeriods(BillingConfig config, int startingPeriodNumber) {
        log.info("Generating bi-weekly periods");
        return generateFixedDurationPeriods(config, TWO_WEEKS_MS, BillingFrequency.BI_WEEKLY, startingPeriodNumber);
    }

    /**
     * Generates monthly billing periods (30-day intervals).
     *
     * Note: Uses fixed 30-day months for consistency.
     * This is simpler than calendar months and works well for billing.
     *
     * @param config Billing configuration
     * @return List of monthly periods
     */
    private List<BillingPeriod> generateMonthlyPeriods(BillingConfig config, int startingPeriodNumber) {
        log.info("Generating monthly periods (30-day intervals)");
        return generateFixedDurationPeriods(config, THIRTY_DAYS_MS, BillingFrequency.MONTHLY, startingPeriodNumber);
    }

    /**
     * Generates custom frequency billing periods.
     *
     * @param config Billing configuration
     * @return List of custom frequency periods
     */
    private List<BillingPeriod> generateCustomPeriods(BillingConfig config, int startingPeriodNumber) {
        if (config.getCustomFrequencyDays() == null) {
            throw new CustomException("CUSTOM_FREQUENCY_DAYS_REQUIRED",
                "Custom frequency days is required for CUSTOM billing frequency. " +
                "campaignNumber: " + config.getCampaignNumber() +
                ", tenantId: " + config.getTenantId());
        }

        log.info("Generating custom periods with {} days frequency", config.getCustomFrequencyDays());
        long customDurationMs = config.getCustomFrequencyDays() * ONE_DAY_MS;
        return generateFixedDurationPeriods(config, customDurationMs, BillingFrequency.CUSTOM, startingPeriodNumber);
    }

    /**
     * Generates single period for end of campaign billing.
     *
     * Creates one period spanning the entire campaign duration.
     *
     * @param config Billing configuration
     * @return List containing single period
     */
    private List<BillingPeriod> generateEndOfCampaignPeriod(BillingConfig config, int startingPeriodNumber) {
        log.info("Generating end of campaign period (single period for entire campaign)");

        List<BillingPeriod> periods = new ArrayList<>();

        BillingPeriod period = BillingPeriod.builder()
            .id(UUID.randomUUID().toString())
            .tenantId(config.getTenantId())
            .projectId(config.getProjectId())
            .campaignNumber(config.getCampaignNumber())
            .billingConfigId(config.getId())
            .periodNumber(startingPeriodNumber)
            .periodStartDate(config.getProjectStartDate())
            .periodEndDate(config.getProjectEndDate())
            .billingFrequency(BillingFrequency.END_OF_CAMPAIGN)
            .periodType("INTERMEDIATE")
            .status("PENDING")
            .registerCount(0)
            .musterRollCount(0)
            .isDeprecated(false)
            .createdBy(config.getCreatedBy())
            .createdTime(System.currentTimeMillis())
            .build();

        periods.add(period);

        log.info("Generated single period: {} to {}",
            formatDate(period.getPeriodStartDate()),
            formatDate(period.getPeriodEndDate()));

        return periods;
    }

    /**
     * Core algorithm: Generates fixed-duration billing periods.
     *
     * Algorithm:
     * 1. Start from project start date
     * 2. Calculate period end = start + duration - 1ms
     * 3. If end exceeds project end, adjust to project end
     * 4. Create period object
     * 5. Move to next period (start = end + 1ms)
     * 6. Repeat until reaching project end
     *
     * Edge cases:
     * - Last period may be shorter than others
     * - If project duration < period duration, creates single period
     * - Handles millisecond-level precision
     *
     * @param config Billing configuration
     * @param periodDurationMs Period duration in milliseconds
     * @param frequencyType Frequency type enum
     * @return List of billing periods
     */
    private List<BillingPeriod> generateFixedDurationPeriods(BillingConfig config,
                                                            long periodDurationMs,
                                                            BillingFrequency frequencyType,
                                                            int startingPeriodNumber) {
        List<BillingPeriod> periods = new ArrayList<>();

        long projectStartMs = config.getProjectStartDate();
        long projectEndMs = config.getProjectEndDate();

        int periodNumber = startingPeriodNumber;
        long currentStart = projectStartMs;

        // Generate periods until we reach project end date
        while (currentStart < projectEndMs) {
            // Calculate period end date
            long currentEnd = currentStart + periodDurationMs - 1;

            // Last period: adjust end date to match project end
            if (currentEnd > projectEndMs) {
                currentEnd = projectEndMs;
            }

            // Create billing period
            BillingPeriod period = createBillingPeriod(
                config,
                periodNumber,
                currentStart,
                currentEnd,
                frequencyType
            );

            periods.add(period);

            log.debug("Generated period {}: {} to {} ({} days)",
                periodNumber,
                formatDate(currentStart),
                formatDate(currentEnd),
                calculateDays(currentStart, currentEnd));

            // Move to next period
            periodNumber++;
            currentStart = currentEnd + 1;

            // Safety check: prevent infinite loop
            if (periodNumber > 365) {
                log.error("Period generation exceeded maximum limit of 365 periods");
                throw new CustomException("PERIOD_GENERATION_ERROR",
                    "Period generation exceeded maximum limit. Check project dates and frequency.");
            }
        }

        // Validate that at least one period was generated
        if (periods.isEmpty()) {
            throw new CustomException("NO_PERIODS_GENERATED",
                "No billing periods could be generated. Check project dates.");
        }

        return periods;
    }

    /**
     * Creates a billing period object with proper metadata.
     *
     * @param config Billing configuration
     * @param periodNumber Sequential period number
     * @param startDate Period start date in epoch milliseconds
     * @param endDate Period end date in epoch milliseconds
     * @param frequencyType Frequency type enum
     * @return Billing period object
     */
    private BillingPeriod createBillingPeriod(BillingConfig config,
                                             int periodNumber,
                                             long startDate,
                                             long endDate,
                                             BillingFrequency frequencyType) {
        return BillingPeriod.builder()
            .id(UUID.randomUUID().toString())
            .tenantId(config.getTenantId())
            .projectId(config.getProjectId())
            .campaignNumber(config.getCampaignNumber())
            .billingConfigId(config.getId())
            .periodNumber(periodNumber)
            .periodStartDate(startDate)
            .periodEndDate(endDate)
            .billingFrequency(frequencyType)
            .periodType("INTERMEDIATE")
            .status("PENDING")
            .registerCount(0)
            .musterRollCount(0)
            .isDeprecated(false)
            .createdBy(config.getCreatedBy())
            .createdTime(System.currentTimeMillis())
            .build();
    }

    /**
     * Validates billing configuration before period generation.
     *
     * @param config Billing configuration
     * @throws CustomException if configuration is invalid
     */
    private void validateConfig(BillingConfig config) {
        if (config == null) {
            throw new CustomException("CONFIG_NULL", "Billing configuration is null");
        }

        String campaignInfo = "campaignNumber: " + config.getCampaignNumber() +
            ", tenantId: " + config.getTenantId();

        if (config.getProjectStartDate() == null) {
            throw new CustomException("START_DATE_NULL",
                "Project start date is null. " + campaignInfo);
        }

        if (config.getProjectEndDate() == null) {
            throw new CustomException("END_DATE_NULL",
                "Project end date is null. " + campaignInfo);
        }

        if (config.getProjectStartDate() >= config.getProjectEndDate()) {
            throw new CustomException("INVALID_DATE_RANGE",
                "Project start date must be before end date. " + campaignInfo +
                ", projectStartDate: " + config.getProjectStartDate() +
                ", projectEndDate: " + config.getProjectEndDate());
        }

        if (config.getBillingFrequency() == null) {
            throw new CustomException("FREQUENCY_NULL",
                "Billing frequency is null. " + campaignInfo);
        }
    }

    /**
     * Calculates number of days between two dates.
     *
     * @param startMs Start date in epoch milliseconds
     * @param endMs End date in epoch milliseconds
     * @return Number of days (inclusive)
     */
    private int calculateDays(long startMs, long endMs) {
        return (int) ((endMs - startMs) / ONE_DAY_MS) + 1;
    }

    /**
     * Formats epoch milliseconds to readable date string.
     * Uses thread-safe DateTimeFormatter.
     *
     * @param epochMs Epoch milliseconds
     * @return Formatted date string
     */
    private String formatDate(long epochMs) {
        return DATE_FORMAT.format(Instant.ofEpochMilli(epochMs));
    }

    /**
     * Logs summary of generated periods for debugging.
     *
     * @param periods List of billing periods
     */
    private void logPeriodsSummary(List<BillingPeriod> periods) {
        if (periods == null || periods.isEmpty()) {
            log.warn("No periods to log");
            return;
        }

        log.info("===== Billing Periods Summary =====");
        log.info("Total periods: {}", periods.size());

        for (BillingPeriod period : periods) {
            log.info("Period {}: {} to {} ({} days)",
                period.getPeriodNumber(),
                formatDate(period.getPeriodStartDate()),
                formatDate(period.getPeriodEndDate()),
                period.getPeriodDurationInDays());
        }

        log.info("===================================");
    }

    /**
     * Recalculates periods for a billing configuration.
     *
     * Useful when configuration is updated and periods need regeneration.
     *
     * @param config Updated billing configuration
     * @return New list of billing periods
     */
    public List<BillingPeriod> regeneratePeriods(BillingConfig config) {
        log.info("Regenerating periods for billing config: {}", config.getId());
        return generatePeriods(config);
    }

    /**
     * Returns the billing frequency duration in milliseconds for the given config.
     * Returns 0 for END_OF_CAMPAIGN (no fixed interval).
     */
    public long getFrequencyDurationMs(BillingConfig config) {
        if (config.getBillingFrequency() == null) return 0;
        switch (config.getBillingFrequency()) {
            case WEEKLY:    return ONE_WEEK_MS;
            case BI_WEEKLY: return TWO_WEEKS_MS;
            case MONTHLY:   return THIRTY_DAYS_MS;
            case CUSTOM:
                return config.getCustomFrequencyDays() != null
                    ? config.getCustomFrequencyDays() * ONE_DAY_MS : 0;
            default:        return 0;
        }
    }

    /**
     * Validates if a date falls within a billing period.
     *
     * @param date Date to check (epoch milliseconds)
     * @param period Billing period
     * @return true if date is within period
     */
    public boolean isDateInPeriod(long date, BillingPeriod period) {
        return date >= period.getPeriodStartDate() && date <= period.getPeriodEndDate();
    }

    /**
     * Gets the period number for a given date within a project.
     *
     * @param date Date to check (epoch milliseconds)
     * @param periods List of billing periods
     * @return Period number or -1 if not found
     */
    public int getPeriodNumberForDate(long date, List<BillingPeriod> periods) {
        for (BillingPeriod period : periods) {
            if (isDateInPeriod(date, period)) {
                return period.getPeriodNumber();
            }
        }
        return -1;
    }

    /**
     * Calculates total project duration covered by all periods.
     *
     * @param periods List of billing periods
     * @return Total duration in days
     */
    public int calculateTotalDuration(List<BillingPeriod> periods) {
        if (periods == null || periods.isEmpty()) {
            return 0;
        }

        int totalDays = 0;
        for (BillingPeriod period : periods) {
            totalDays += period.getPeriodDurationInDays();
        }

        return totalDays;
    }
}
