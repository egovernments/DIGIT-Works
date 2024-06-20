package org.egov.works.mukta.adapter.config;

public class Constants {
    public static final String REQUEST_INFO = "RequestInfo";
    public static final String TENANT_ID = "tenantId";

    public static final String MDMS_TENANT_MODULE_NAME = "tenant";
    public static final String MDMS_TENANTS_MASTER = "tenants";
    public static final String MDMS_IFMS_MODULE_NAME = "ifms";
    public static final String MDMS_SEGMENT_CODES_MODULE = "segment-codes";

    public static final String MDMS_EXCHANGE_MODULE_NAME = "exchange";
    public static final String MDMS_EXCHANGE_SERVER_MASTER = "ExchangeServers";
    public static final String MDMS_SCHEMA_DETAILS_MASTER = "SchemeDetails";
    public static final String MDMS_SSU_DETAILS_MASTER = "SSUDetails";
    public static final String MDMS_ADMINISTRATIVE_CODES_MASTER = "AdministrativeCodes";
    public static final String MDMS_ECONOMIC_SEGMENT_CODES_MASTER ="EconomicSegmentCodes";
    public static final String MDMS_FUNCTION_CODES_MASTER = "FunctionCodes";
    public static final String MDMS_RECIPIENT_SEGMENT_CODES_MASTER = "RecipientSegmentCodes";
    public static final String MDMS_SOURCE_OF_FUNDS_CODE_MASTER = "SourceOfFundCodes";
    public static final String MDMS_TARGET_SEGMENT_CODES_MASTER = "TargetSegmentCodes";
    public static final String MDMS_GEOGRAPHIC_CODES_MASTER = "GeographicSegmentCodes";
    public static final String PAYEE_TYPE_INDIVIDUAL = "INDIVIDUAL";

    public static final String VA_REQUEST_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String JIT_BILL_DATE_FORMAT = "yyyy-MM-dd";
    public static final String VA_TRANSACTION_TYPE_INITIAL_ALLOTMENT = "Initial Allotment";
    public static final String VA_TRANSACTION_TYPE_ADDITIONAL_ALLOTMENT = "Additional allotment";
    public static final String VA_TRANSACTION_TYPE_WITHDRAWAL = "Allotment withdrawal";
    public static final String DEDUCTION_BENEFICIARY_BY_HEADCODE = "Deduction_{tanentId}_{headcode}";
    public static final String MDMS_COMMON_ACTIVE_FILTER = "$.*.[?(@.active==true)]";
    public static final String MDMS_EXPENSE_MODULE_NAME = "expense";
    public static final String MDMS_HEAD_CODES_MASTER = "HeadCodes";
    public static final String HEAD_CODE_CATEGORY_KEY = "category";
    public static final String HEAD_CODE_DEDUCTION_CATEGORY = "deduction";
    public static final String JIT_UNAUTHORIZED_REQUEST_EXCEPTION = "No valid session encryption key";

    public static final String JIT_FD_EXT_APP_NAME = "MUKTA";
    public static final String BANK_ACCOUNT_TABLE_NAME = "eg_bank_account_detail";
    public static final String BANK_IFSC_TABLE_NAME = "eg_bank_branch_identifier";
    public static final String BANK_ACCOUNT_DECRYPT_KEY = "BankAccountDecrypt";
    public static final String APPROVED_STATUS = "APPROVED";

    public static final String EXTERNAL_SERVICE_EXCEPTION = "External Service threw an Exception: ";
    public static final String SEARCHER_SERVICE_EXCEPTION = "Exception while fetching from searcher: ";
    public static final String ERROR_WHILE_FETCHING_FROM_MDMS = "Exception occurred while fetching data from mdms: ";
    public static final String IDGEN_ERROR = "IDGEN ERROR";
    public static final String NO_IDS_FOUND_ERROR = "No ids returned from idgen Service";
    public static final String RES_MSG_ID = "uief87324";
    public static final String SUCCESSFUL = "successful";
    public static final String FAILED = "failed";
    public static final String DOB_FORMAT_Y_M_D = "yyyy-MM-dd";
    public static final String DOB_FORMAT_D_M_Y = "dd/MM/yyyy";
    public static final String ILLEGAL_ARGUMENT_EXCEPTION_CODE = "IllegalArgumentException";
    public static final String OBJECTMAPPER_UNABLE_TO_CONVERT = "ObjectMapper not able to convertValue in userCall";
    public static final String DOB_FORMAT_D_M_Y_H_M_S = "dd-MM-yyyy HH:mm:ss";
    public static final String CREATED_DATE = "createdDate";
    public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
    public static final String DOB = "dob";
    public static final String PWD_EXPIRY_DATE = "pwdExpiryDate";
    public static final String INVALID_DATE_FORMAT_CODE = "INVALID_DATE_FORMAT";
    public static final String INVALID_DATE_FORMAT_MESSAGE = "Failed to parse date format in user";
    public static final String CITIZEN_UPPER = "CITIZEN";
    public static final String CITIZEN_LOWER = "Citizen";
    public static final String DISBURSEMENT_ENCRYPT = "DisbursementEncrypt";
    public static final String ACCOUNT_TYPE = "accountType";
    public static final String DISBURSE_REDIS_KEY = "DISBURSE_{uuid}";
    public static final String PAYMENT_REDIS_KEY = "PAYMENT_{uuid}";
}
