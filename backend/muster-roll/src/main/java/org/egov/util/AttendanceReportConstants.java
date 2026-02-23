package org.egov.util;

public class AttendanceReportConstants {

    // Report Status Values
    public static final String REPORT_STATUS_INITIATED = "INITIATED";
    public static final String REPORT_STATUS_COMPLETED = "COMPLETED";
    public static final String REPORT_STATUS_FAILED = "FAILED";

    // Report Configuration
    public static final String REPORT_KEY = "attendanceReport";
    public static final String REPORT_DATE_FORMAT = "dd/MM/yyyy";
    public static final String REPORT_TIMEZONE = "Asia/Kolkata";

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
