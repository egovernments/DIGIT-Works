package org.egov.web.models.report;

/**
 * Enum for report generation status
 */
public enum ReportStatus {
    INITIATED("INITIATED"),
    COMPLETED("COMPLETED"),
    FAILED("FAILED");

    private final String value;

    ReportStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static ReportStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ReportStatus status : ReportStatus.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid report status: " + value);
    }
}
