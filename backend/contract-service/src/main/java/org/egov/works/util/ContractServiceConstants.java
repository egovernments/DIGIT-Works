package org.egov.works.util;

public class ContractServiceConstants {
    public static final String MASTER_TENANTS = "tenants";
    public static final String MDMS_TENANT_MODULE_NAME = "tenant";
    public static final String MDMS_WORKS_MODULE_NAME = "works";
    public static final String MASTER_CONTRACT_TYPE = "ContractType";
    public static final String MASTER_EXECUTING_AUTHORITY = "ExecutingAuthority";
    public static final String MASTER_DOCUMENT_TYPE = "DocumentType";
    public static final String TENANT_FILTER_CODE = "$.*.code";
    public static final String COMMON_ACTIVE_FILTER = "$.*.[?(@.active==true)].name";
    public static final String MDMS_RESP_CONSTANT = "$.MdmsRes.";
    public static final String JSON_PATH_FOR_TENANTS_VERIFICATION = MDMS_RESP_CONSTANT + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";
    public static final String JSON_PATH_FOR_EXECUTING_AUTHORITY_VERIFICATION = MDMS_RESP_CONSTANT + MDMS_WORKS_MODULE_NAME + "." + MASTER_EXECUTING_AUTHORITY ;
    public static final String JSON_PATH_FOR_CONTRACT_TYPE_VERIFICATION = MDMS_RESP_CONSTANT + MDMS_WORKS_MODULE_NAME + "." + MASTER_CONTRACT_TYPE + ".*";
    public static final String JSON_PATH_FOR_DOCUMENT_TYPE_VERIFICATION = MDMS_RESP_CONSTANT + MDMS_WORKS_MODULE_NAME + "." + MASTER_DOCUMENT_TYPE + ".*";
}
