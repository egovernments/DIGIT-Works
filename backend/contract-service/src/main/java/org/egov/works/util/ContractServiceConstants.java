package org.egov.works.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ContractServiceConstants {
    public static final String MASTER_TENANTS = "tenants";
    public static final String MDMS_TENANT_MODULE_NAME = "tenant";
    public static final String MDMS_WORKS_MODULE_NAME = "works";

    public static final String OFFICER_IN_CHARGE_ID_CONSTANT = "officerInChargeId";
    public static final String MASTER_CONTRACT_TYPE = "ContractType";
    public static final String MASTER_CBO_ROLES = "CBORoles";
    public static final String MASTER_OVER_HEADS = "Overheads";
    //public static final String MASTER_DOCUMENT_TYPE = "DocumentType";
    public static final String MASTER_OIC_ROLES = "OICRoles";
    public static final String TENANT_FILTER_CODE = "$.*.code";
    public static final String COMMON_ACTIVE_FILTER = "$.*.[?(@.active==true)]";
    public static final String COMMON_CODE_FILTER = "code";
    public static final String COMMON_ACTIVE_WITH_CODE_FILTER = COMMON_ACTIVE_FILTER+ "." + COMMON_CODE_FILTER;
    public static final String COMMON_ACTIVE_WITH_WORK_ORDER_VALUE = "$.[?(@.active==true && @.isWorkOrderValue==true)]";
    public static final String MDMS_RESP_CONSTANT = "$.MdmsRes.";
    public static final String JSON_PATH_FOR_TENANTS_VERIFICATION = MDMS_RESP_CONSTANT + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";
    public static final String JSON_PATH_FOR_CBO_ROLES_VERIFICATION = MDMS_RESP_CONSTANT + MDMS_WORKS_MODULE_NAME + "." + MASTER_CBO_ROLES;
    public static final String JSON_PATH_FOR_CONTRACT_TYPE_VERIFICATION = MDMS_RESP_CONSTANT + MDMS_WORKS_MODULE_NAME + "." + MASTER_CONTRACT_TYPE + ".*";
    //public static final String JSON_PATH_FOR_DOCUMENT_TYPE_VERIFICATION = MDMS_RESP_CONSTANT + MDMS_WORKS_MODULE_NAME + "." + MASTER_DOCUMENT_TYPE + ".*";
    public static final String JSON_PATH_FOR_OIC_ROLES_VERIFICATION = MDMS_RESP_CONSTANT + MDMS_WORKS_MODULE_NAME + "." + MASTER_OIC_ROLES;
    public static final String JSON_PATH_FOR_OVER_HEADS_VERIFICATION = MDMS_RESP_CONSTANT + MDMS_WORKS_MODULE_NAME + "." + MASTER_OVER_HEADS+ ".*."+COMMON_CODE_FILTER;
    public static final String HRMS_USER_ROLES_CODE = "$.Employees.*.user.roles.*.code";

    //HRMS User Constants
    public static final String HRMS_USER_USERNAME_CODE = "$.Employees.*.user.userName";

    public static final String HRMS_USER_MOBILE_NO = "$.Employees.*.user.mobileNumber";

    public static final String HRMS_USER_DESIGNATION = "$.Employees.*.assignments.*.designation";


    //Localization CONSTANTS
    public static final String CONTRACTS_NOTIFICATION_ENG_LOCALE_CODE = "en_IN";

    public static final String CONTRACTS_MODULE_CODE = "rainmaker-contracts";

    public static final String CONTRACTS_REJECT_LOCALIZATION_CODE = "CONTRACTS_NOTIFICATION_WORKFLOW_REJECT_ACTION";

    public static final String CONTRACTS_APPROVE_CREATOR_LOCALIZATION_CODE = "CONTRACTS_NOTIFICATION_TO_CREATOR_WORKFLOW_APPROVE_ACTION";

    public static final String CONTRACTS_APPROVE_CBO_LOCALIZATION_CODE = "CONTRACTS_NOTIFICATION_TO_CBOADMIN_WORKFLOW_APPROVE_ACTION";


    public static final String CONTRACTS_LOCALIZATION_CODES_JSONPATH = "$.messages.*.code";
    public static final String CONTRACTS_LOCALIZATION_MSGS_JSONPATH = "$.messages.*.message";

    //Project service Constants
    public static final String PROJECT_NAME_CODE ="$.Projects.*.name";

    public static final String PROJECT_BOUNDARY_CODE ="$.Projects.*.address.boundary";

    public static final String PROJECT_BOUNDARY_TYPE_CODE ="$.Projects.*.address.boundaryType";

    //Location Service Constants
    public static final String LOCATION_BOUNDARY_NAME_CODE ="$.*.*.boundary.*.name";

    //Organisation Constants

    public static final String ORGANISATION_NAME_CODE ="$.organisations.*.name";
    public static final String ORGANISATION_PERSON_CODE ="$.organisations.*.contactDetails.*.contactName";
    public static final String ORGANISATION_MOBILE_NUMBER_CODE ="$.organisations.*.contactDetails.*.contactMobileNumber";




    public static final String HRMS_USER_ROLES_CODE = "$.Employees.*.user.roles.*code";
    public static final String REGISTER_ACTIVE_CODE_CONSTANT = "$.[?(@.active==true && @.code =='";
    public static final String CREATE_REGISTER_CONSTANT ="' && @.createRegister==true)]";
    public static final String PROJECT_NAME_CONSTANT = "projectName";
    public static final String WARD_CONSTANT = "ward";
    public static final String PROJECT_TYPE_CONSTANT = "projectType";
    public static final String PROJECT_ID_CONSTANT = "projectId";
    public static final String ORG_NAME_CONSTANT = "orgName";
}
