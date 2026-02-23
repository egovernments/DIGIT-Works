package org.egov.util;

public class AttendanceReportConstants {

    // Report Status Values
    public static final String REPORT_STATUS_INITIATED = "INITIATED";
    public static final String REPORT_STATUS_COMPLETED = "COMPLETED";
    public static final String REPORT_STATUS_FAILED = "FAILED";

    // Report Configuration
    public static final String REPORT_DATE_FORMAT = "dd/MM/yyyy";
    public static final String REPORT_TIMEZONE = "Asia/Kolkata";

    // Report Types
    public static final String REPORT_TYPE_ATTENDANCE = "ATTENDANCE_REPORT";
    public static final String REPORT_TYPE_SIGNATURE = "SIGNATURE_REPORT";
    public static final String REPORT_TYPE_PHOTO_CAPTURE = "PHOTO_CAPTURE_REPORT";

    // Report Formats
    public static final String REPORT_FORMAT_EXCEL = "EXCEL";
    public static final String REPORT_FORMAT_PDF = "PDF";

    // Default auto-generate report combinations (reportType, reportFormat)
    public static final String[][] DEFAULT_AUTO_GENERATE_REPORTS = {
            {REPORT_TYPE_ATTENDANCE, REPORT_FORMAT_EXCEL}
    };

    // Column Headers
    public static final String[] FIXED_COLUMN_HEADERS = {
            "Sl No",
            "Name",
            "Phone",
            "Role",
            "Team Code",
            "User ID",
            "Enrollment Date",
            "De-enrollment Date",
            "Attendance Marker",
            "Present Days (Original)",
            "Present Days (Modified)"
    };

    public static final int FIXED_COLUMNS_COUNT = 11;

    // FileStore Configuration
    public static final String FILESTORE_MODULE_NAME = "ATTENDANCE";

    // Error Messages
    public static final String MUSTER_NOT_FOUND = "MUSTER_NOT_FOUND";
    public static final String MUSTER_NOT_APPROVED = "MUSTER_NOT_APPROVED";
    public static final String ATTENDANCE_REGISTER_NOT_FOUND = "ATTENDANCE_REGISTER_NOT_FOUND";
    public static final String REPORT_GENERATION_FAILED = "REPORT_GENERATION_FAILED";
    public static final String FILESTORE_UPLOAD_FAILED = "FILESTORE_UPLOAD_FAILED";

    // Attendance Status Values
    public static final String ATTENDANCE_STATUS_PRESENT = "PRESENT";
    public static final String ATTENDANCE_STATUS_ABSENT = "ABSENT";

    // Kafka Topics
    public static final String GENERATE_ATTENDANCE_REPORT_TOPIC = "generate-attendance-report";
    public static final String ATTENDANCE_REPORT_UPDATE_TOPIC = "attendance-report-update";
}
