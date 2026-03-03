package org.egov.util;

public class AttendanceReportConstants {

    // Report Status Values
    public static final String REPORT_STATUS_INITIATED = "INITIATED";
    public static final String REPORT_STATUS_COMPLETED = "COMPLETED";
    public static final String REPORT_STATUS_FAILED = "FAILED";

    // Report Configuration (moved to application.properties/MusterRollServiceConfiguration)
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

    // Localization keys — ALL_CAPS_UNDERSCORE
    public static final String REPORT_TITLE_KEY = "ATTENDANCE_REPORT_TITLE";
    public static final String REPORT_REGISTER_INFO_KEY = "ATTENDANCE_REPORT_REGISTER_INFO";
    public static final String REPORT_CAMPAIGN_INFO_KEY = "ATTENDANCE_REPORT_CAMPAIGN_INFO";

    // Column header localization keys (must match order in FIXED_COLUMN_HEADER_KEYS)
    public static final String HEADER_SL_NO = "ATTENDANCE_REPORT_HEADER_SL_NO";
    public static final String HEADER_NAME = "ATTENDANCE_REPORT_HEADER_NAME";
    public static final String HEADER_PHONE = "ATTENDANCE_REPORT_HEADER_PHONE";
    public static final String HEADER_ROLE = "ATTENDANCE_REPORT_HEADER_ROLE";
    public static final String HEADER_TEAM_CODE = "ATTENDANCE_REPORT_HEADER_TEAM_CODE";
    public static final String HEADER_USER_ID = "ATTENDANCE_REPORT_HEADER_USER_ID";
    public static final String HEADER_ENROLLMENT_DATE = "ATTENDANCE_REPORT_HEADER_ENROLLMENT_DATE";
    public static final String HEADER_DE_ENROLLMENT_DATE = "ATTENDANCE_REPORT_HEADER_DE_ENROLLMENT_DATE";
    public static final String HEADER_ATTENDANCE_MARKER = "ATTENDANCE_REPORT_HEADER_ATTENDANCE_MARKER";
    public static final String HEADER_PRESENT_DAYS_ORIGINAL = "ATTENDANCE_REPORT_HEADER_PRESENT_DAYS_ORIGINAL";
    public static final String HEADER_PRESENT_DAYS_MODIFIED = "ATTENDANCE_REPORT_HEADER_PRESENT_DAYS_MODIFIED";
    public static final String TOTAL_PERFORMANCE_HEADER = "ATTENDANCE_REPORT_TOTAL_PERFORMANCE_HEADER";

    // Ordered key array — used by excelGenerator (replaces old FIXED_COLUMN_HEADERS)
    public static final String[] FIXED_COLUMN_HEADER_KEYS = {
            HEADER_SL_NO, HEADER_NAME, HEADER_PHONE, HEADER_ROLE,
            HEADER_TEAM_CODE, HEADER_USER_ID, HEADER_ENROLLMENT_DATE,
            HEADER_DE_ENROLLMENT_DATE, HEADER_ATTENDANCE_MARKER,
            HEADER_PRESENT_DAYS_ORIGINAL, HEADER_PRESENT_DAYS_MODIFIED,
            TOTAL_PERFORMANCE_HEADER
    };

    public static final int FIXED_COLUMNS_COUNT = 12;

    // Localization fallback locale
    public static final String LOCALIZATION_DEFAULT_LOCALE = "en_IN";

    // FileStore Configuration (moved to application.properties/MusterRollServiceConfiguration)
    public static final String FILESTORE_MODULE_NAME = "ATTENDANCE";

    // Error Messages
    public static final String MUSTER_NOT_FOUND = "MUSTER_NOT_FOUND";
    public static final String MUSTER_NOT_APPROVED = "MUSTER_NOT_APPROVED";
    public static final String ATTENDANCE_REGISTER_NOT_FOUND = "ATTENDANCE_REGISTER_NOT_FOUND";

    // Attendance Status Values
    public static final String ATTENDANCE_STATUS_PRESENT = "PRESENT";
    public static final String ATTENDANCE_STATUS_ABSENT = "ABSENT";

}
