package org.egov.works.util;

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
    public static final String HRMS_USER_ROLES_CODE = "$.Employees.*.user.roles.*code";
}
