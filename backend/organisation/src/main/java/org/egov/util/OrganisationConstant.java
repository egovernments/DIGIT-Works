package org.egov.util;

public class OrganisationConstant {

    //Modules
    public static final String MDMS_TENANT_MODULE_NAME = "tenant";
    public static final String MDMS_COMMON_MASTERS_MODULE_NAME = "common-masters";

    //Masters
    public static final String MASTER_TENANTS = "tenants";
    public static final String MASTER_ORG_TYPE = "OrgType";
    public static final String MASTER_ORG_FUNC_CATEGORY = "OrgFunctionCategory";
    public static final String MASTER_ORG_FUNC_CLASS = "OrgFunctionClass";
    public static final String MASTER_ORG_TAX_IDENTIFIER = "OrgTaxIdentifier";
    public static final String MASTER_ORG_TRANSFER_CODE = "OrgTransferCode";

    //Role
    public static final String ORG_CITIZEN_ROLE_CODE = "ORG_ADMIN";
    public static final String ORG_CITIZEN_ROLE_NAME = "Organization admin";
    public static final String ORG_CITIZEN_TYPE = "CITIZEN";


    public static final String PATTERN_NAME = "^[^\\\\$\\\"<>?\\\\\\\\~`!@#$%^()+={}\\\\[\\\\]*,:;“”‘’]*$";


    public static final String PATTERN_GENDER = "^[a-zA-Z ]*$";
    public static final String PATTERN_MOBILE = "(^$|[0-9]{10})";
    public static final String PATTERN_CITY = "^[a-zA-Z. ]*$";
    public static final String PATTERN_TENANT = "^[a-zA-Z. ]*$";
    public static final String PATTERN_PINCODE = "^[1-9][0-9]{5}$";
}
