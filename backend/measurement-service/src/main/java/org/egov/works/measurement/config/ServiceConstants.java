package org.egov.works.measurement.config;


import org.springframework.stereotype.Component;


@Component
public class ServiceConstants {

    public static final String EXTERNAL_SERVICE_EXCEPTION = "External Service threw an Exception: ";
    public static final String SEARCHER_SERVICE_EXCEPTION = "Exception while fetching from searcher: ";

    public static final String IDGEN_ERROR = "IDGEN_ERROR";
    public static final String NO_IDS_FOUND_ERROR = "No ids returned from idgen Service";

    public static final String ERROR_WHILE_FETCHING_FROM_MDMS = "Exception occurred while fetching category lists from mdms: ";

    public static final String RES_MSG_ID = "uief87324";
    public static final String SUCCESSFUL = "successful";
    public static final String FAILED = "failed";
    public static final String PWD_EXPIRY_DATE = "pwdExpiryDate";
    public static final String INVALID_DATE_FORMAT_CODE = "INVALID_DATE_FORMAT";
    public static final String INVALID_DATE_FORMAT_MESSAGE = "Failed to parse date format in user";
    public static final String CITIZEN_UPPER = "CITIZEN";
    public static final String CITIZEN_LOWER = "Citizen";
    public static final String USER = "user";

    public static final String PARSING_ERROR = "PARSING ERROR";
    public static final String FAILED_TO_PARSE_BUSINESS_SERVICE_SEARCH = "Failed to parse response of workflow business service search";
    public static final String BUSINESS_SERVICE_NOT_FOUND = "BUSINESSSERVICE_NOT_FOUND";
    public static final String THE_BUSINESS_SERVICE = "The businessService ";
    public static final String NOT_FOUND = " is not found";
    public static final String TENANTID = "?tenantId=";
    public static final String BUSINESS_SERVICES = "&businessServices=";
    public static final String MEASUREMENT_SERVICE_LOCALIZATION_CODES_JSONPATH = "$.messages.*.code";
    public static final String MEASUREMENT_SERVICE_MSGS_JSONPATH = "$.messages.*.message";
    public static final String MB_LOCALIZATION_MODULE_CODE = "rainmaker-measurement-book";
    public static final String APPROVE_LOCALISATION_CODE = "MB_APPROVE";
    public static final String REJECT_LOCALISATION_CODE = "MB_REJECT";
    public static final String HRMS_USER_MOBILE_NO = "$.Employees.*.user.mobileNumber";
    public static final String PROJECT_NUMBER_CODE = "$.Project.*.projectNumber";
    public static final String MDMS_TENANTS_MASTER_NAME = "tenants";
    public static final String MDMS_TENANT_MODULE_NAME = "tenant";

}
