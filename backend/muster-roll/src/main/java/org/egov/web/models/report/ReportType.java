package org.egov.web.models.report;

/**
 * Enum for report types
 */
public enum ReportType {
    ATTENDANCE_REPORT("ATTENDANCE_REPORT"),
    SIGNATURE_REPORT("SIGNATURE_REPORT"),
    PHOTO_CAPTURE_REPORT("PHOTO_CAPTURE_REPORT");

    private final String value;

    ReportType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static ReportType fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ReportType type : ReportType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid report type: " + value);
    }
}
