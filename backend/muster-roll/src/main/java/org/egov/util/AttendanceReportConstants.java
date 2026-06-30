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
    public static final String HEADER_LOGIN_ID = "ATTENDANCE_REPORT_HEADER_LOGIN_ID";
    public static final String HEADER_ENROLLMENT_DATE = "ATTENDANCE_REPORT_HEADER_ENROLLMENT_DATE";
    public static final String HEADER_DE_ENROLLMENT_DATE = "ATTENDANCE_REPORT_HEADER_DE_ENROLLMENT_DATE";
    public static final String HEADER_ATTENDANCE_MARKER = "ATTENDANCE_REPORT_HEADER_ATTENDANCE_MARKER";
    public static final String HEADER_PRESENT_DAYS_ORIGINAL = "ATTENDANCE_REPORT_HEADER_PRESENT_DAYS_ORIGINAL";
    public static final String HEADER_PRESENT_DAYS_MODIFIED = "ATTENDANCE_REPORT_HEADER_PRESENT_DAYS_MODIFIED";
    public static final String TOTAL_PERFORMANCE_HEADER = "ATTENDANCE_REPORT_TOTAL_PERFORMANCE_HEADER";

    // Base signature column localization key
    public static final String HEADER_BASE_SIGNATURE = "ATTENDANCE_REPORT_HEADER_BASE_SIGNATURE";

    // Ordered key array — used by excelGenerator (replaces old FIXED_COLUMN_HEADERS)
    public static final String[] FIXED_COLUMN_HEADER_KEYS = {
            HEADER_SL_NO, HEADER_NAME, HEADER_LOGIN_ID, HEADER_USER_ID, HEADER_TEAM_CODE, HEADER_ROLE,
            HEADER_PHONE, HEADER_ENROLLMENT_DATE,
            HEADER_DE_ENROLLMENT_DATE, HEADER_ATTENDANCE_MARKER,
            HEADER_PRESENT_DAYS_ORIGINAL, HEADER_PRESENT_DAYS_MODIFIED,
            TOTAL_PERFORMANCE_HEADER, HEADER_BASE_SIGNATURE
    };

    public static final int FIXED_COLUMNS_COUNT = 14;

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

    // Signature sub-column header localization keys
    public static final String HEADER_MORNING   = "ATTENDANCE_REPORT_HEADER_MORNING";
    public static final String HEADER_EVENING   = "ATTENDANCE_REPORT_HEADER_EVENING";
    public static final String HEADER_STATUS    = "ATTENDANCE_REPORT_HEADER_STATUS";
    public static final String HEADER_SIGNATURE = "ATTENDANCE_REPORT_HEADER_SIGNATURE";

    // Role value localization key prefix — append skill type code (e.g. ATTENDANCE_REPORT_ROLE_DISTRIBUTOR)
    public static final String ROLE_LOCALIZATION_KEY_PREFIX = "ATTENDANCE_REPORT_ROLE_";

    // Attendance status value localization key prefix — append status code (e.g. ATTENDANCE_REPORT_STATUS_PRESENT)
    public static final String STATUS_LOCALIZATION_KEY_PREFIX = "ATTENDANCE_REPORT_STATUS_";

    // Signature column structural constants
    public static final int SIGNATURE_COLS_PER_DATE = 4; // AM Status, AM Signature, PM Status, PM Signature
    public static final String SIGNATURE_NA = "NA";

    // Attendance log additionalDetails keys
    public static final String LOG_ADDITIONAL_DETAILS_SESSION_TYPE = "SESSION_TYPE";
    public static final String LOG_ADDITIONAL_DETAILS_SIGNATURE = "signature";

    // Synthetic key prefix for inline (non-filestore) signature images
    public static final String INLINE_SIGNATURE_KEY_PREFIX = "INLINE_SIG:";

    // Session type values (from attendance log additionalDetails.SESSION_TYPE)
    public static final String SESSION_TYPE_MORNING = "MORNING";
    public static final String SESSION_TYPE_EVENING = "EVENING";

}
