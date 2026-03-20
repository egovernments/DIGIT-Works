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

    // V2 Billing Constants
    public static final String BILLING_PERIOD_AGGREGATE = "AGGREGATE";

    // V2 Muster Roll Status Constants
    // Only NOT_CREATED is hardcoded - it's a custom status when no muster roll exists
    // All workflow statuses (APPROVED, PENDING, SENT_BACK, etc.) are fetched dynamically
    // from the workflow service via MusterRollWorkflowUtil
    public static final String MUSTER_ROLL_STATUS_NOT_CREATED = "NOT_CREATED";

    // Role Constants
    public static final String ROLE_CAMPAIGN_MANAGER = "CAMPAIGN_MANAGER";

    // Error Keys
    public static final String ERROR_KEY_ID = "ID_MISSING";
    public static final String ERROR_KEY_TENANT_ID = "TENANT_ID_MISSING";
    public static final String ERROR_KEY_TAG = "TAG_MISSING";
    public static final String ERROR_KEY_ATTENDEE = "ATTENDEE_LIST_EMPTY";
    public static final String ERROR_KEY_MISSING_ATTENDEES = "ATTENDEES_NOT_FOUND";
    public static final String ERROR_KEY_UNAUTHORIZED = "UNAUTHORIZED";
    public static final String ERROR_KEY_ENROLLMENT_DATE = "ENROLLMENT_DATE";
    public static final String ERROR_KEY_DENROLLMENT_DATE = "DENROLLMENT_DATE";
    public static final String ERROR_KEY_INVALID_ID = "INVALID_ID";

    // Error Messages
    public static final String ERROR_MSG_EMPTY_ATTENDEE_LIST = "ATTENDEE Object is empty in attendee request";
    public static final String ERROR_MSG_ID_MANDATORY = "Attendee uuid is mandatory";
    public static final String ERROR_MSG_TENANT_ID_MANDATORY = "Tenant id is mandatory";
    public static final String ERROR_MSG_TAG_MANDATORY = "New tag value must be provided";
    public static final String ERROR_MSG_ATTENDEES_NOT_FOUND = "Attendees not found in database for IDs: ";
    public static final String ERROR_MSG_INCONSISTENT_TENANT_ID = "All Attendees to be enrolled or de enrolled must have the same tenant id. Please raise a new request for a different tenant id.";
    public static final String ERROR_MSG_UNAUTHORIZED_UPDATE_ATTENDEE = "Only CAMPAIGN_MANAGER role is allowed to update attendees";
    public static final String ERROR_MSG_UNAUTHORIZED_UPDATE_STAFF = "Only CAMPAIGN_MANAGER role is allowed to update staff";
    public static final String ERROR_MSG_UNAUTHORIZED_FIRST_STAFF = "Only CAMPAIGN_MANAGER role is allowed to enroll the first staff in a register";
    public static final String ERROR_MSG_UNAUTHORIZED_FIRST_ATTENDEE_PREFIX = "Only CAMPAIGN_MANAGER role is allowed to enroll the first attendee in register: ";
    public static final String ERROR_MSG_ATTENDEE_NOT_FOUND_IN_DB_PREFIX = "Attendee not found in DB for id: ";
    public static final String ERROR_MSG_STAFF_NOT_FOUND_IN_DB_PREFIX = "Staff not found in DB for id: ";
    public static final String ERROR_MSG_ENROLLMENT_DATE_BEFORE_REGISTER_START = " must be >= register start date";
    public static final String ERROR_MSG_ENROLLMENT_DATE_EXCEEDS_DENROLLMENT = " must be <= existing de-enrollment date";
    public static final String ERROR_MSG_DENROLLMENT_DATE_BEFORE_ENROLLMENT = " must be >= enrollment date";
    public static final String ERROR_MSG_DENROLLMENT_DATE_AFTER_REGISTER_END = " must be <= register end date";
}
