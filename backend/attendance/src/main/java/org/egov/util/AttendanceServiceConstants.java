package org.egov.util;

public class AttendanceServiceConstants {
    public static final String MASTER_TENANTS = "tenants";
    public static final String MDMS_TENANT_MODULE_NAME = "tenant";
    public static final String LIMIT_OFFSET = "?limit=1000&offset=0";
    public static final String TWO_SESSIONS = "TWO_SESSIONS";
    public static final String TOTAL_COUNT = "totalCount";
    public static final String ATTENDANCE_REGISTER_APPROVED = "APPROVED";
    public static final String ATTENDANCE_REGISTER_PENDINGFORAPPROVAL = "PENDINGFORAPPROVAL";
    public static final String STAFF_NAME = "staffName";
    public static final String OWNER_NAME = "ownerName";

    // Error Keys
    public static final String ERROR_KEY_ID = "ID_MISSING";
    public static final String ERROR_KEY_TENANT_ID = "TENANT_ID_MISSING";
    public static final String ERROR_KEY_TAG = "TAG_MISSING";
    public static final String ERROR_KEY_ATTENDEE = "ATTENDEE_LIST_EMPTY";
    public static final String ERROR_KEY_MISSING_ATTENDEES = "ATTENDEES_NOT_FOUND";

    // Error Messages
    public static final String ERROR_MSG_EMPTY_ATTENDEE_LIST = "ATTENDEE Object is empty in attendee request";
    public static final String ERROR_MSG_ID_MANDATORY = "Attendee uuid is mandatory";
    public static final String ERROR_MSG_TENANT_ID_MANDATORY = "Tenant id is mandatory";
    public static final String ERROR_MSG_TAG_MANDATORY = "New tag value must be provided";
    public static final String ERROR_MSG_ATTENDEES_NOT_FOUND = "Attendees not found in database for IDs: ";
    public static final String ERROR_MSG_INCONSISTENT_TENANT_ID = "All Attendees to be enrolled or de enrolled must have the same tenant id. Please raise a new request for a different tenant id.";
}
