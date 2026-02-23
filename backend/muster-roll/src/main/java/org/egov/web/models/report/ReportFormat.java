package org.egov.web.models.report;

/**
 * Enum for report formats
 */
public enum ReportFormat {
    EXCEL("EXCEL"),
    PDF("PDF");

    private final String value;

    ReportFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static ReportFormat fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ReportFormat format : ReportFormat.values()) {
            if (format.value.equals(value)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Invalid report format: " + value);
    }
}
