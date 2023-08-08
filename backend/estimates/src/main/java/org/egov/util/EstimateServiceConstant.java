package org.egov.util;

public class EstimateServiceConstant {

    //Modules
    public static final String MDMS_WORKS_MODULE_NAME = "works";
    public static final String MDMS_TENANT_MODULE_NAME = "tenant";
    public static final String MDMS_COMMON_MASTERS_MODULE_NAME = "common-masters";

    //Masters
    public static final String MASTER_DEPARTMENT = "Department";
    public static final String MASTER_TENANTS = "tenants";
    public static final String MASTER_SOR_ID = "SorId";
    public static final String MASTER_CATEGORY = "Category";
    public static final String MASTER_OVERHEAD = "Overheads";


    public static final String OVERHEAD_CODE = "OVERHEAD";
    //work flow
    public static final String ESTIMATE_MODULE_NAME = "estimate";

    //MDMS- location
    public static final String BOUNDARY_ADMIN_HIERARCHY_CODE = "ADMIN";
    public static final String MASTER_BOUNDARY_LOCATION = "TenantBoundary";
    public static final String MDMS_LOCATION_MODULE_NAME = "egov-location";


    //General
    public static final String SEMICOLON = ":";
    public static final String DOT = ".";
    public static final String EQUAL_TO = "=";

    //Estimate update roles - Add the roles using comma as separator
    public static final String ALLOW_EDITING_ROLES = "EST_CREATOR";
    public static final String ACTION_REJECT = "REJECT";
    public static final String ACTION_ADMIN_SANCTION = "ADMINSANCTION";
    public static final String ROLE_EST_CREATOR = "EST_CREATOR";

    //Json path
    public static final String JSONPATH_PROJECT = "";

    //Project service Constants
    
    public static final String PROJECT_RESP_PAYLOAD_KEY ="Project";

    public static final String PROJECT_NAME_CODE ="$.Project.*.name";

    public static final String PROJECT_ID_CODE = "$.Project.*.projectNumber";

    public static final String PROJECT_BOUNDARY_CODE ="$.Project.*.address.boundary";

    public static final String PROJECT_BOUNDARY_TYPE_CODE ="$.Project.*.address.boundaryType";

    //HRMS User Constants
    public static final String HRMS_USER_USERNAME_CODE = "$.Employees.*.user.userName";

    public static final String HRMS_USER_MOBILE_NO = "$.Employees.*.user.mobileNumber";

    public static final String HRMS_USER_DESIGNATION = "$.Employees.*.assignments.*.designation";

    //LOCALIZATION CONSTANTS
    public static final String ESTIMATE_REJECT_LOCALIZATION_CODE = "ESTIMATE_NOTIFICATION_TO_CREATOR_WF_REJECT_ACTION";

    public static final String ESTIMATE_APPROVE_LOCALIZATION_CODE = "ESTIMATE_NOTIFICATION_TO_CREATOR_WF_APPROVE_ACTION";

    public static final String ESTIMATE_NOTIFICATION_ENG_LOCALE_CODE = "en_IN";
    public static final String ESTIMATE_MODULE_CODE = "rainmaker-estimate";

    public static final String ESTIMATE_LOCALIZATION_CODES_JSONPATH = "$.messages.*.code";

    public static final String ESTIMATE_LOCALIZATION_MSGS_JSONPATH = "$.messages.*.message";

    //Location Service Constants
    public static final String LOCATION_BOUNDARY_NAME_CODE ="$.*.*.boundary.*.name";
}