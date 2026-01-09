package org.egov.digit.expense.calculator.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * BillingFrequency
 *
 * Enum representing supported billing frequencies for intermediate billing.
 * Defines how often billing periods are generated for a project.
 *
 * Supported frequencies:
 * - WEEKLY: Billing every 7 days
 * - BI_WEEKLY: Billing every 14 days
 * - MONTHLY: Billing approximately every 30 days
 * - CUSTOM: Billing at custom intervals (requires customFrequencyDays)
 * - END_OF_CAMPAIGN: Single billing at campaign completion
 *
 * @author DIGIT-Works
 */
public enum BillingFrequency {

    /**
     * Weekly billing frequency - periods every 7 days
     */
    WEEKLY("WEEKLY"),

    /**
     * Bi-weekly billing frequency - periods every 14 days
     */
    BI_WEEKLY("BI_WEEKLY"),

    /**
     * Monthly billing frequency - periods every 30 days
     */
    MONTHLY("MONTHLY"),

    /**
     * Custom billing frequency - periods at custom intervals
     * Requires customFrequencyDays to be specified (minimum 3 days)
     */
    CUSTOM("CUSTOM"),

    /**
     * End of campaign billing - single period for entire campaign duration
     * No intermediate billing periods are generated
     */
    END_OF_CAMPAIGN("END_OF_CAMPAIGN");

    private final String value;

    BillingFrequency(String value) {
        this.value = value;
    }

    /**
     * Gets the string value of the enum.
     * Used for JSON serialization.
     *
     * @return String value
     */
    @JsonValue
    public String getValue() {
        return value;
    }

    /**
     * Creates enum from string value.
     * Used for JSON deserialization.
     * Case-insensitive matching.
     *
     * @param value String value
     * @return BillingFrequency enum
     * @throws IllegalArgumentException if value doesn't match any enum constant
     */
    @JsonCreator
    public static BillingFrequency fromValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Billing frequency cannot be null");
        }

        for (BillingFrequency frequency : BillingFrequency.values()) {
            if (frequency.value.equalsIgnoreCase(value)) {
                return frequency;
            }
        }

        throw new IllegalArgumentException("Invalid billing frequency: " + value +
            ". Valid values are: WEEKLY, BI_WEEKLY, MONTHLY, CUSTOM, END_OF_CAMPAIGN");
    }

    /**
     * Checks if this frequency requires custom frequency days.
     *
     * @return true if frequency is CUSTOM
     */
    public boolean requiresCustomDays() {
        return this == CUSTOM;
    }

    /**
     * Gets the default duration in days for this frequency.
     * Returns null for CUSTOM (requires configuration).
     *
     * @return Default duration in days, or null for CUSTOM
     */
    public Integer getDefaultDurationDays() {
        switch (this) {
            case WEEKLY:
                return 7;
            case BI_WEEKLY:
                return 14;
            case MONTHLY:
                return 30;
            case END_OF_CAMPAIGN:
                return null; // Single period for entire campaign
            case CUSTOM:
                return null; // Requires explicit configuration
            default:
                return null;
        }
    }

    /**
     * Checks if this frequency generates multiple periods.
     *
     * @return true if multiple periods are generated
     */
    public boolean generatesMultiplePeriods() {
        return this != END_OF_CAMPAIGN;
    }

    @Override
    public String toString() {
        return value;
    }
}
