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
    public static final String ORG_ADMIN_ROLE_CODE = "ORG_ADMIN";
    public static final String ORG_ADMIN_ROLE_NAME = "Organization admin";
    public static final String ORG_CITIZEN_TYPE = "CITIZEN";

    public static final String VIEW_ORG_UNMASKED_CODE = "VIEW_ORG_UNMASKED";
    public static final String VIEW_ORG_UNMASKED_NAME = "View ORG Unmasked";

    public static final String VIEW_DED_UNMASKED_CODE = "VIEW_DED_UNMASKED";
    public static final String VIEW_DED_UNMASKED_NAME = "View DED Unmasked";

    public static final String VIEW_WS_UNMASKED_CODE = "VIEW_WS_UNMASKED";
    public static final String VIEW_WS_UNMASKED_NAME = "View Ws Unmasked";


    public static final String PATTERN_NAME = "^[^\\\\$\\\"<>?\\\\\\\\~`!@#$%^()+={}\\\\[\\\\]*,:;“”‘’]*$";


    public static final String PATTERN_GENDER = "^[a-zA-Z ]*$";
    public static final String PATTERN_MOBILE = "(^$|[0-9]{10})";
    public static final String PATTERN_CITY = "^[a-zA-Z. ]*$";
    public static final String PATTERN_TENANT = "^[a-zA-Z. ]*$";
    public static final String PATTERN_PINCODE = "^[1-9][0-9]{5}$";

    // localization contants
    public static final String ORGANISATION_CREATE_LOCALIZATION_CODE="ORGANISATION_NOTIFICATION_ON_CREATE";
    public static final String ORGANISATION_UPDATE_LOCALIZATION_CODE="ORGANISATION_NOTIFICATION_ON_UPDATE";

    public static final String ORGANISATION_NOTIFICATION_ENG_LOCALE_CODE = "en_IN";

    public static final String ORGANISATION_MODULE_CODE = "rainmaker-common-masters";

    public static final String ORGANISATION_LOCALIZATION_CODES_JSONPATH = "$.messages.*.code";
    public static final String ORGANISATION_LOCALIZATION_MSGS_JSONPATH = "$.messages.*.message";

    //HRMS User Constants
    public static final String HRMS_USER_USERNAME_CODE = "$.Employees.*.user.userName";

    public static final String HRMS_USER_MOBILE_NO = "$.Employees.*.user.mobileNumber";

    public static final String ORGANISATION_ENCRYPT_KEY = "Organisation";


}
