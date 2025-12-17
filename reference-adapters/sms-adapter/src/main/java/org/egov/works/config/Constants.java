package org.egov.works.config;

public class Constants {
    public static final String PURCHASE_BILL_APPROVE_TO_VENDOR_LOCALIZATION_CODE = "PURCHASE_BILL_APPROVE_TO_VENDOR";
    public static final String PURCHASE_BILL_REJECT_TO_CREATOR_LOCALIZATION_CODE = "PURCHASE_BILL_REJECT_TO_CREATOR";
    public static final String SUPERVISION_BILL_APPROVE_ON_CREATE_TO_CBO_LOCALIZATION_CODE = "SUPERVISION_BILL_APPROVE_ON_CREATE_TO_CBO";
    public static final String CONTACT_MOBILE_NUMBER = "contactMobileNumber";
    public static final String CONTRACT_NUMBER = "contractNumber";
    public static final String ORG_ID_PATH = "$.contracts.*.orgId";
    public static final String REQUEST_INFO = "RequestInfo";
    public static final String TENANT_ID = "tenantId";
    public static final String SEARCH_CRITERIA = "SearchCriteria";
    public static final String ID = "id";
    public static final String ORG_NAME_PATH = "$.organisations.*.name";
    public static final String CONTACT_NAME_PATH = "$.organisations.*.contactDetails.*.contactName";
    public static final String CONTACT_MOBILE_NUMBER_PATH = "$.organisations.*.contactDetails.*.contactMobileNumber";
    public static final String RAINMAKER_COMMON_MODULE_CODE = "rainmaker-common-masters";
    public static final String CONTACT_NAME = "contactName";
    public static final String ORG_NAME = "orgName";
    public static final String MASTER_TENANTS = "tenants";
    public static final String MDMS_TENANT_MODULE_NAME = "tenant";
    public static final String MDMS_WORKS_MODULE_NAME = "works";
    public static final String MASTER_CONTRACT_TYPE = "ContractType";
    public static final String MASTER_CBO_ROLES = "CBORoles";
    public static final String MASTER_OVER_HEADS = "Overheads";
    public static final String MASTER_OIC_ROLES = "OICRoles";
    public static final String TENANT_FILTER_CODE = "$.*.code";
    public static final String COMMON_ACTIVE_FILTER = "$.*.[?(@.active==true)]";
    public static final String COMMON_CODE_FILTER = "code";
    public static final String COMMON_ACTIVE_WITH_CODE_FILTER = COMMON_ACTIVE_FILTER+ "." + COMMON_CODE_FILTER;
    public static final String COMMON_ACTIVE_WITH_WORK_ORDER_VALUE = "$.[?(@.active==true && @.isWorkOrderValue==true)]";
    public static final String REGISTER_ACTIVE_CODE_CONSTANT = "$.[?(@.active==true && @.code =='";
    public static final String CREATE_REGISTER_CONSTANT ="' && @.createRegister==true)]";

    //HRMS User Constants
    public static final String HRMS_USER_USERNAME_CODE = "$.Employees.*.user.userName";
    public static final String HRMS_USER_MOBILE_NO = "$.Employees.*.user.mobileNumber";
    public static final String HRMS_USER_DESIGNATION = "$.Employees.*.assignments.*.designat" + "ion";
    public static final String HRMS_USER_ROLES_CODE = "$.Employees.*.user.roles.*.code";
    public static final String CONTRACTS_MODULE_CODE = "rainmaker-contracts";
    public static final String CONTRACTS_REJECT_LOCALIZATION_CODE = "CONTRACTS_NOTIFICATION_WORKFLOW_REJECT_ACTION";
    public static final String CONTRACTS_APPROVE_CREATOR_LOCALIZATION_CODE = "CONTRACTS_NOTIFICATION_TO_CREATOR_WORKFLOW_APPROVE_ACTION";
    public static final String CONTRACTS_APPROVE_CBO_LOCALIZATION_CODE = "CONTRACTS_NOTIFICATION_TO_CBOADMIN_WORKFLOW_APPROVE_ACTION";
    public static final String CONTRACTS_DECLINE_CREATOR_LOCALIZATION_CODE = "CONTRACTS_NOTIFICATION_TO_CREATOR_WORKFLOW_DECLINE_ACTION";
    public static final String CONTRACTS_ACCEPT_CREATOR_LOCALIZATION_CODE = "CONTRACTS_NOTIFICATION_TO_CREATOR_WORKFLOW_ACCEPT_ACTION";

    //Project service Constants
    public static final String PROJECT_ID_CODE = "$.Project.*.projectNumber";
    public static final String PROJECT_NAME_CODE ="$.Project.*.name";
    public static final String PROJECT_NAME = "projectName";
    public static final String PROJECT_NUMBER_CODE = "$.Project.*.projectNumber";
    public static final String PROJECT_BOUNDARY_CODE ="$.Project.*.address.boundary";
    public static final String PROJECT_BOUNDARY_TYPE_CODE ="$.Project.*.address.boundaryType";

    //Location Service Constants
    public static final String LOCATION_BOUNDARY_NAME_CODE ="$.*.*.boundary.*.name";

    //Organisation Constants
    public static final String ORGANISATION_NAME_CODE ="$.organisations.*.name";
    public static final String ORGANISATION_PERSON_CODE ="$.organisations.*.contactDetails.*.contactName";
    public static final String ORGANISATION_MOBILE_NUMBER_CODE ="$.organisations.*.contactDetails.*.contactMobileNumber";
    public static final String CONTRACT_TIME_EXTENSION_BUSINESS_SERVICE = "CONTRACT-REVISION";
    public static final String REJECT_ACTION = "REJECT";
    public static final String ACCEPT_ACTION = "ACCEPT";
    public static final String APPROVE_ACTION = "APPROVE";
    public static  final String DECLINE_ACTION = "DECLINE";
    public static final String SMS_NOT_FOUND = "SMS content has not been configured for this case";
    public static final String MOBILE_NUMBERS = "mobileNumbers";
    public static final String MOBILE_NUMBER = "mobileNumber";
    public static final String PROJECT_NUMBER = "projectNumber";
    public static final String PROJECT_BOUNDARY = "boundary";
    public static final String PROJECT_BOUNDARY_TYPE = "boundaryType";
    public static final String PROJECT_ID = "projectId";
    public static final String CONTRACT_REVISION_SEND_BACK_LOCALIZATION_CODE = "TIME_EXTENSION_SEND_BACK_TO_CBO";
    public static final String CONTRACT_REVISION_APPROVE_LOCALIZATION_CODE = "TIME_EXTENSION_APPROVE";
    public static final String CONTRACT_REVISION_REJECT_LOCALIZATION_CODE = "TIME_EXTENSION_REJECT";;
    public static final String PROJECT_ID_REPLACEMENT_STRING =  "{projectid}";
    public static final String WORK_ORDER_NO_REPLACEMENT_STRING = "{workorderno}";
    public static final String ESTIMATE_MODULE_CODE = "rainmaker-estimate";
    public static final String ESTIMATE_MODULE_NAME = "estimate";
    public static final String ESTIMATE_REJECT_LOCALIZATION_CODE = "ESTIMATE_NOTIFICATION_TO_CREATOR_WF_REJECT_ACTION";
    public static final String ESTIMATE_APPROVE_LOCALIZATION_CODE = "ESTIMATE_NOTIFICATION_TO_CREATOR_WF_APPROVE_ACTION";
    public static final String CONVERSION_ERROR_CODE = "CONVERSION_ERROR";
    public static final String CONVERSION_ERROR_MSG = "Cannot convert response";
    public static final String PARSING_ERROR_CODE = "PARSING_ERROR";
    public static final String PARSING_ERROR_MSG = "Failed to parse project response";
    public static final String LOCALIZATION_CODES_JSONPATH = "$.messages.*.code";
    public static final String MSGS_JSONPATH = "$.messages.*.message";
    public static final String MB_APPROVE_LOCALISATION_CODE = "MB_APPROVE";
    public static final String MB_REJECT_LOCALISATION_CODE = "MB_REJECT";
    public static final String MB_LOCALIZATION_MODULE_CODE = "rainmaker-measurement-book";
    public static final String WF_SEND_BACK_TO_CBO_CODE = "SENDBACKTOCBO";
    public static final String CBO_NOTIFICATION_FOR_CORRECTION_LOCALIZATION_CODE = "MUSTER_ROLL_CBO_NOTIFICATION_FOR_CORRECTION";
    public static final String CBO_NOTIFICATION_OF_APPROVAL_LOCALIZATION_CODE = "MUSTER_ROLL_CBO_NOTIFICATION_OF_APPROVAL";
    public static final String ORGANISATION_CREATE_LOCALIZATION_CODE="ORGANISATION_NOTIFICATION_ON_CREATE";
    public static final String ORGANISATION_UPDATE_LOCALIZATION_CODE="ORGANISATION_NOTIFICATION_ON_UPDATE";
    public static final String PERSON_NAMES = "personNames";
    public static final String INDIVIDUAL_CREATE_LOCALIZATION_CODE = "INDIVIDUAL_NOTIFICATION_ON_CREATE";
    public static final String INDIVIDUAL_UPDATE_LOCALIZATION_CODE = "INDIVIDUAL_NOTIFICATION_ON_UPDATE";
    public static final String INDIVIDUAL_MODULE_CODE = "rainmaker-masters";
    public static final String ORG_CITIZEN_ROLE_NAME = "Organization admin";
}
