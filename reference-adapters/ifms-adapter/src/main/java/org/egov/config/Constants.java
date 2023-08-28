package org.egov.config;

public class Constants {
	public static final String REQUEST_INFO = "RequestInfo";
	public static final String TENANT_ID = "tenantId";

	public static final String MDMS_TENANT_MODULE_NAME = "tenant";
	public static final String MDMS_TENANTS_MASTER = "tenants";
	public static final String MDMS_IFMS_MODULE_NAME = "ifms";
	public static final String MDMS_HEAD_OF_ACCOUNT_MASTER = "HeadOfAccounts";
	public static final String MDMS_SCHEMA_DETAILS_MASTER = "SchemeDetails";
	public static final String MDMS_SSU_DETAILS_MASTER = "SSUDetails";
	public static final String PAYEE_TYPE_INDIVIDUAL = "INDIVIDUAL";

	public static final String VA_REQUEST_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String JIT_BILL_DATE_FORMAT = "yyyy-MM-dd";
	public static final String VA_TRANSACTION_TYPE_INITIAL_ALLOTMENT = "Initial Allotment";
	public static final String VA_TRANSACTION_TYPE_ADDITIONAL_ALLOTMENT = "Additional allotment";
	public static final String VA_TRANSACTION_TYPE_WITHDRAWAL = "Allotment withdrawal";
	public static final String DEDUCTION_BENEFICIARY_BY_HEADCODE = "Deduction_{tanentId}_{headcode}";
	public static final String MDMS_COMMON_ACTIVE_FILTER ="$.*.[?(@.active==true)]";
	public static final String MDMS_EXPENSE_MODULE_NAME = "expense";
	public static final String MDMS_HEAD_CODES_MASTER = "HeadCodes";
	public static final String HEAD_CODE_CATEGORY_KEY = "category";
	public static final String HEAD_CODE_DEDUCTION_CATEGORY = "deduction";
	public static final String JIT_UNAUTHORIZED_REQUEST_EXCEPTION="No valid session encryption key";

	public static final String JIT_FD_EXT_APP_NAME = "MUKTA";
	public static final String BANK_ACCOUNT_TABLE_NAME = "eg_bank_account_detail";
	public static final String BANK_IFSC_TABLE_NAME = "eg_bank_branch_identifier";
	public static final String BANK_ACCOUNT_DECRYPT_KEY = "BankAccountDecrypt";
	public static final String APPROVED_STATUS = "APPROVED";

	public static final String DOC_INSERT_PATH = "/_doc";
}
