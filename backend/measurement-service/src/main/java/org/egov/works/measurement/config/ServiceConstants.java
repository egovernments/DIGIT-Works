package org.egov.works.measurement.config;


import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


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
    public static final String BUSINESS_IDS = "&businessIds=";
    public static final String MEASUREMENT_SERVICE_LOCALIZATION_CODES_JSONPATH = "$.messages.*.code";
    public static final String MEASUREMENT_SERVICE_MSGS_JSONPATH = "$.messages.*.message";
    public static final String MB_LOCALIZATION_MODULE_CODE = "rainmaker-measurement-book";
    public static final String APPROVE_LOCALISATION_CODE = "MB_APPROVE";
    public static final String REJECT_LOCALISATION_CODE = "MB_REJECT";
    public static final String HRMS_USER_MOBILE_NO = "$.Employees.*.user.mobileNumber";
    public static final String PROJECT_NUMBER_CODE = "$.Project.*.projectNumber";
    public static final String MDMS_TENANTS_MASTER_NAME = "tenants";
    public static final String MDMS_TENANT_MODULE_NAME = "tenant";
    public static final String REJECTED_STATUS = "REJECTED";
    public static final String APPROVED_STATUS = "APPROVED";
    public static final String ACCEPTED_STATUS = "ACCEPTED";
    public static final String APPROVE_ACTION = "APPROVE";
    public static final String REJECT_ACTION = "REJECT";
    public static final String SAVE_AS_DRAFT_ACTION = "SAVE_AS_DRAFT";
    public static final String SUBMIT_ACTION = "SUBMIT";
    public static final String EDIT_RESUBMIT_ACTION = "EDIT/RE-SUBMIT";
    public static final Set<String> ACTION_SETS = new HashSet<>(Arrays.asList(SAVE_AS_DRAFT_ACTION, SUBMIT_ACTION, EDIT_RESUBMIT_ACTION));
    public static final String SOR_CODE = "SOR";
    public static final String NON_SOR_CODE = "NON-SOR";
    public static final String ACTIVE_STATUS = "ACTIVE";
    public static final String BUSINESS_SERVICE_TE_CONTRACT = "CONTRACT-REVISION";

    public static final String CONTRACT_SERVICE="CONTRACT";
    public static final String INWORKFLOW="INWORKFLOW";

    public static final String CONTRACT_REVISION_ESTIMATE="CONTRACT-REVISION-ESTIMATE";

    public static final String OVERHEAD="OVERHEAD";

    public  static final String SENT_BACK="SENT_BACK";
    public static final String SEND_BACK_TO_ORIGINATOR="SEND_BACK_TO_ORIGINATOR";


}