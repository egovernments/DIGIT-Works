package org.egov.digit.expense.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.egov.common.utils.MultiStateInstanceUtil.SCHEMA_REPLACE_STRING;

public class Constants {
	public static final String REQUEST_INFO = "RequestInfo";
	public static final String TENANT_ID = "tenantId";
	public static final String CONTRACT_NUMBER = "contractNumber";
	public static final String ORG_ID_PATH = "$.contracts.*.orgId";
	public static final String ID = "id";

	public static final String EXCEPTION = "EXCEPTION";
	public static final String ERROR = "ERROR";

	public static final String SEARCH_CRITERIA = "SearchCriteria";
	public static final String INDIVIDUAL = "Individual";
	public static final String ORG_NAME_PATH = "$.organisations.*.name";
	public static final String INDIVIDUAL_GENDER_PATH = "$.Individual.*.gender";

	public static final String INDIVIDUAL_NAME_PATH = "$.Individual.*.name.givenName";
	public static final String INDIVIDUAL_USER_UUID_PATH = "$.Individual.*.userUuid";
	public static final String INDIVIDUAL_PHONE_NUMBER_PATH = "$.Individual.*.mobileNumber";
	public static final String INDIVIDUAL_EMAIL_PATH = "$.Individual.*.email";
	public static final String INDIVIDUAL_ROLE_PATH = "$.Individual.*.skills[0].type";

	// Worker Registry
	public static final String WORKER_SEARCH = "workerSearch";
	public static final String WORKERS_RESPONSE_PATH = "$.workers";
	public static final String INDIVIDUAL_ID = "individualId";
	public static final String PAYMENT_PROVIDER_MTN = "MTN";
	public static final String PAYMENT_PROVIDER_BANK = "BANK";
	public static final Set<String> VALID_PAYMENT_PROVIDERS = Set.of(PAYMENT_PROVIDER_MTN, PAYMENT_PROVIDER_BANK);

	// BankPaymentService error codes
	public static final String BILL_NOT_FOUND_ERR_CODE = "BILL_NOT_FOUND";
	public static final String MISSING_PAYEE_NAME_ERR_CODE           = "MISSING_PAYEE_NAME";
	public static final String MISSING_PAYEE_PHONE_NUMBER_ERR_CODE   = "MISSING_PAYEE_PHONE_NUMBER";
	public static final String MISSING_BANK_ACCOUNT_ERR_CODE         = "MISSING_BANK_ACCOUNT";
	public static final String INVALID_BANK_ACCOUNT_ERR_CODE         = "INVALID_BANK_ACCOUNT";
	public static final String INVALID_BANK_CODE_ERR_CODE            = "INVALID_BANK_CODE";
	public static final String MISSING_BENEFICIARY_CODE_ERR_CODE     = "MISSING_BENEFICIARY_CODE";
	public static final String INVALID_BENEFICIARY_CODE_ERR_CODE     = "INVALID_BENEFICIARY_CODE";
	public static final String BANK_TRANSFER_NOT_IMPLEMENTED_ERR_CODE = "BANK_TRANSFER_NOT_IMPLEMENTED";

	public static final String CONTACT_NAME_PATH = "$.organisations.*.contactDetails.*.contactName";
	public static final String CONTACT_MOBILE_NUMBER_PATH = "$.organisations.*.contactDetails.*.contactMobileNumber";
	public static final String ORG_NAME = "orgName";
	public static final String GENDER = "gender";
	public static final String CONTACT_NAME = "contactName";
	public static final String EXPENSE_CALCULATOR_LOCALIZATION_CODE_JSONPATH = "$.messages.*.code";
	public static final String EXPENSE_CALCULATOR_LOCALIZATION_MESSAGE_JSONPATH = "$.messages.*.message";
	public static final String HRMS_USER_USERNAME_CODE = "$.Employees.*.user.userName";
	public static final String HRMS_USER_MOBILE_NO = "$.Employees.*.user.mobileNumber";
	public static final String CONTACT_MOBILE_NUMBER = "contactMobileNumber";
	public static final String WORKFLOW_EMAIL_NOTIFICATION_MDMS_SCHEMA = "EXPENSE.billEmailNotification";
	public static final String APPROVE_CODE = "APPROVE";
	public static final String REJECT_CODE = "REJECT";
	public static final String MOBILE_NUMBER_CODE = "mobileNumber";
	public static final String PURCHASE_BILL_APPROVE_TO_VENDOR_LOCALIZATION_CODE = "PURCHASE_BILL_APPROVE_TO_VENDOR";
	public static final String PURCHASE_BILL_REJECT_TO_CREATOR_LOCALIZATION_CODE = "PURCHASE_BILL_REJECT_TO_CREATOR";
	public static final String SUPERVISION_BILL_APPROVE_ON_CREATE_TO_CBO_LOCALIZATION_CODE = "SUPERVISION_BILL_APPROVE_ON_CREATE_TO_CBO";

	public static final String MTN_SUBSCRIPTION_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";
	public static final String MTN_AUTHORIZATION_HEADER_NAME = "Authorization";
	public static final String MTN_TARGET_ENVIRONMENT_HEADER_NAME = "X-Target-Environment";
	public static final String MTN_X_REFERENCE_HEADER_NAME = "X-Reference-Id";
	public static final String MTN_ACCESS_TOKEN_TYPE = "Bearer ";
	public static final String MTN_USER_GIVEN_NAME_FIELD = "given_name";
	public static final String MTN_USER_FAMILY_NAME_FIELD = "family_name";
	public static final String TASK_SEARCH_QUERY = "SELECT * FROM " + SCHEMA_REPLACE_STRING + ".eg_expense_task";
	public static final String TASK_ORDER_BY_QUERY = " ORDER BY last_modified_time DESC LIMIT 1";
	public static final String TASK_DETAILS_SEARCH_QUERY = "SELECT * FROM " + SCHEMA_REPLACE_STRING + ".eg_expense_task_details";
	public static final String TASK_DETAILS_BY_TASK_ID_SEARCH_QUERY = "SELECT * FROM " + SCHEMA_REPLACE_STRING + ".eg_expense_task_details WHERE task_id = ?";

	public static final String TENANT_MODULE_NAME = "tenant";

	public static final String TENANT_CODE_FILTER = "$.*.code";

	public static final String TENANT_MASTERNAME = "tenants";
	
	
	public static final String EXPENSE_MODULE_NAME = "expense";
	
	public static final String CODE_FILTER = "$.*.code";
	
	public static final String HEADCODE_MASTERNAME = "HeadCodes";
	
	public static final String BUSINESS_SERVICE_MASTERNAME = "BusinessService";
	
	public static final String BILL_ID_FORMAT_SUFFIX = ".bill.number";
	
	public static final String PAYMENT_ID_FORMAT_NAME = "expense.payment.number";

	// Scheduler poll / detail-wf phases
	public static final String POLL_PHASE_VERIFICATION      = "VERIFICATION";
	public static final String POLL_PHASE_IGNORE_ERRORS     = "IGNORE_ERRORS";
	public static final String POLL_PHASE_SEND_FOR_REVIEW   = "SEND_FOR_REVIEW";
	public static final String POLL_PHASE_SEND_FOR_APPROVAL = "SEND_FOR_APPROVAL";
	public static final String POLL_PHASE_REVIEW            = "REVIEW";
	public static final String POLL_PHASE_PAYMENT           = "PAYMENT";
	public static final String POLL_PHASE_PAYMENT_INITIATION = "PAYMENT_INITIATION";
	public static final String SYSTEM_SCHEDULER_ACTOR       = "system-scheduler";

	// BILL_STARTED_CHECK phase identifiers
	public static final String STARTED_CHECK_PHASE_VERIFY   = "VERIFY_STARTED";
	public static final String STARTED_CHECK_PHASE_PAYMENT  = "PAYMENT_STARTED";

	public static final String EFFECTIVE_FROM_FIELD_MDMS = "effectiveFrom";
	public static final String EFFECTIVE_TO_FIELD_MDMS = "effectiveTo";
	public static final String ACTIVE_FIELD_MDMS = "active";
	
	public static final List<String> EXPENSE_MDMS_MASTER_NAMES = Collections
			.unmodifiableList(Arrays.asList(HEADCODE_MASTERNAME, BUSINESS_SERVICE_MASTERNAME));

	public static final List<String> TENANT_MDMS_MASTER_NAMES = Collections
			.unmodifiableList(Arrays.asList(TENANT_MASTERNAME));



	private static final String INNER_JOIN = "INNER JOIN";
	private static final String LEFT_JOIN = "LEFT JOIN";

	public static final String PAYMENT_QUERY = "SELECT "
			
			
			+ " payment.id as p_id, payment.tenantid as p_tenantid, netpayableamount, netpaidamount,"
			+ " payment.status as p_status, payment.referencestatus as p_referencestatus,"
			+ " payment.createdby as p_createdby, payment.createdtime as p_createdtime,"
			+ " payment.lastmodifiedby as p_lastmodifiedby, payment.lastmodifiedtime as p_lastmodifiedtime,"
			+ " payment.additionaldetails as p_additionaldetails, paymentnumber, "
			
			+ " paymentbill.id as b_id, paymentbill.tenantid as b_tenantid, paymentid, billid,"
			+ " paymentbill.totalamount as b_totalAmount, paymentbill.totalpaidamount as b_totalpaidAmount,"
			+ " paymentbill.status as b_status, paymentbill.createdby as b_createdby,"
			+ " paymentbill.createdtime as b_createdtime, paymentbill.lastmodifiedby as b_lastmodifiedby,"
			+ " paymentbill.lastmodifiedtime as b_lastmodifiedtime, "
			
			+ " paymentbd.id as bd_id, paymentbd.tenantid as bd_tenantid, billDetailId, paymentbillid,"
			+ " paymentbd.totalamount as bd_totalAmount, paymentbd.totalpaidamount as bd_totalpaidAmount,"
			+ " paymentbd.status as bd_status, paymentbd.createdby as bd_createdby,"
			+ " paymentbd.createdtime as bd_createdtime, paymentbd.lastmodifiedby as bd_lastmodifiedby,"
			+ " paymentbd.lastmodifiedtime as bd_lastmodifiedtime, "
			
			+ " li.id as li_id, li.tenantid as li_tenantid, paymentbilldetailid, lineitemid,"
			+ " paidAmount, li.status as li_status, li.createdby as li_createdby, li.createdtime as li_createdtime,"
			+ " li.lastmodifiedby as li_lastmodifiedby, li.lastmodifiedtime as li_lastmodifiedtime "

			
			+ " FROM " + SCHEMA_REPLACE_STRING + ".eg_expense_payment payment "
			
			+ INNER_JOIN + " " + SCHEMA_REPLACE_STRING + ".eg_expense_payment_bill paymentbill ON paymentbill.paymentid = payment.id"
					+ " AND paymentbill.tenantid = payment.tenantid "
			
			+ INNER_JOIN + " " + SCHEMA_REPLACE_STRING + ".eg_expense_payment_billdetail paymentbd ON paymentbd.paymentbillid = paymentbill.id"
					+ " AND paymentbd.tenantid = paymentbill.tenantid "
			
			+ INNER_JOIN + " " + SCHEMA_REPLACE_STRING + ".eg_expense_payment_lineitem li ON li.paymentbilldetailid = paymentbd.id"
					+ " AND li.tenantid=paymentbd.tenantid ";

	public static final String PAYMENT_COUNT_QUERY = "SELECT distinct(payment.id) " +
			"FROM " + SCHEMA_REPLACE_STRING + ".eg_expense_payment payment "

			+ INNER_JOIN + " " + SCHEMA_REPLACE_STRING + ".eg_expense_payment_bill paymentbill ON paymentbill.paymentid = payment.id"
					+ " AND paymentbill.tenantid = payment.tenantid ";
			
	
	public static final Set<String> SORTABLE_BILL_COLUMNS = Collections.unmodifiableSet(
			Stream.of("billdate", "duedate", "b_paymentstatus", "bill_status").collect(Collectors.toSet()));
	
	public static final String BILL_QUERY = "SELECT "
			
			+ " bill.id as b_id, bill.tenantid as b_tenantid, bill.localitycode as b_localitycode, billdate, duedate, bill.totalamount as b_totalamount, bill.totalpaidamount as b_totalpaidamount, "
			+ " businessservice, bill.referenceid as b_referenceid, billnumber, bill.fromperiod as b_fromperiod, bill.toperiod as b_toperiod, bill.status as b_status, "
			+ " bill.paymentstatus as b_paymentstatus, bill.createdby as b_createdby, bill.createdtime as b_createdtime, bill.lastmodifiedby as b_lastmodifiedby,"
			+ " bill.lastmodifiedtime as b_lastmodifiedtime, bill.additionaldetails as b_additionaldetails,"
			
			+ " bd.id as bd_id, bd.tenantid as bd_tenantid, bd.totalamount as bd_totalamount, bd.totalpaidamount as bd_totalpaidamount, "
			+ " bd.referenceid as bd_referenceid, billid, bd.paymentstatus as bd_paymentstatus, bd.status as bd_status, bd.fromperiod as bd_fromperiod, "
			+ " bd.toperiod as bd_toperiod, bd.workerid as bd_workerid, "
			+ " bd.totalattendance as bd_totalattendance, "
			+ " bd.createdby as bd_createdby, bd.createdtime as bd_createdtime, bd.lastmodifiedby as bd_lastmodifiedby,"
			+ " bd.lastmodifiedtime as bd_lastmodifiedtime, bd.additionaldetails as bd_additionaldetails,"
			
			+ " li.id as line_id, li.billdetailid as line_billdetailid, li.tenantid as line_tenantid, li.paymentstatus as li_paymentstatus,"
			+ " headcode, amount, paidamount, li.type as line_type, li.status as line_status, islineitempayable, li.createdby as line_createdby, li.createdtime as line_createdtime,"
			+ " li.lastmodifiedby as line_lastmodifiedby, li.lastmodifiedtime as line_lastmodifiedtime, li.additionaldetails as line_additionaldetails, "
			
			+ " payee.id as payee_id, payee.tenantid as payee_tenantid, payee.type as payee_type, payee.identifier as payee_identifier, payee.parentid as payee_parentid, "
			+ " payee.paymentprovider as payee_paymentprovider, payee.payeename as payee_payeename, payee.payeephonenumber as payee_payeephonenumber, "
			+ " payee.bankaccount as payee_bankaccount, payee.bankcode as payee_bankcode, payee.beneficiarycode as payee_beneficiarycode, "
			+ " payee.createdby as payee_createdby, payee.createdtime as payee_createdtime, payee.lastmodifiedby as payee_lastmodifiedby,"
			+ " payee.lastmodifiedtime as payee_lastmodifiedtime, payee.additionaldetails as payee_additionaldetails, payee.status as payee_status, "
			
			+ " payer.id as payer_id, payer.tenantid as payer_tenantid, payer.type as payer_type, payer.identifier as payer_identifier, payer.parentid as payer_parentid, "
			+ " payer.createdby as payer_createdby, payer.createdtime as payer_createdtime, payer.lastmodifiedby as payer_lastmodifiedby, "
			+ "payer.lastmodifiedtime as payer_lastmodifiedtime, payer.additionaldetails as payer_additionaldetails, payer.status as payer_status "
			
			+ "FROM "+ SCHEMA_REPLACE_STRING + ".eg_expense_bill bill "
			
			+ LEFT_JOIN + " " + SCHEMA_REPLACE_STRING + ".EG_EXPENSE_PARTY PAYER ON bill.id = payer.parentid AND bill.tenantid = payer.tenantid "
			
			+ LEFT_JOIN + " " + SCHEMA_REPLACE_STRING + ".EG_EXPENSE_BILLDETAIL BD ON bill.id = bd.billid AND bd.tenantid = bill.tenantid "
			
			+ LEFT_JOIN + " " + SCHEMA_REPLACE_STRING + ".EG_EXPENSE_LINEITEM LI ON bd.id = li.billdetailid AND bd.tenantid = li.tenantid "
			
			+ LEFT_JOIN + " " + SCHEMA_REPLACE_STRING + ".EG_EXPENSE_PARTY PAYEE ON bd.id = payee.parentid AND bd.tenantid = payee.tenantid ";

	public static final String COUNT_WRAPPER = " SELECT COUNT(*) FROM ({INTERNAL_QUERY}) AS count ";

	public static final String BILL_COUNT_QUERY = "SELECT distinct(bill.id) " +
             "FROM " + SCHEMA_REPLACE_STRING + ".eg_expense_bill bill ";

	public static final String INVALID_TENANT_ID_ERR_CODE = "INVALID_TENANT_ID";

	// Roles
	public static final String ROLE_PAYMENT_EDITOR   = "PAYMENT_EDITOR";
	public static final String ROLE_PAYMENT_REVIEWER = "PAYMENT_REVIEWER";

	// Bill / BillDetail workflow statuses
	public static final String STATUS_PENDING_VERIFICATION = "PENDING_VERIFICATION";
	public static final String STATUS_PARTIALLY_VERIFIED   = "PARTIALLY_VERIFIED";
	public static final String STATUS_VERIFICATION_FAILED  = "VERIFICATION_FAILED";
	public static final String STATUS_UNDER_REVIEW         = "UNDER_REVIEW";

	// Error codes
	public static final String ERR_UNAUTHORIZED                    = "EG_EXPENSE_UNAUTHORIZED";
	public static final String ERR_INVALID_BILL                    = "EG_EXPENSE_INVALID_BILL";
	public static final String ERR_INVALID_BILL_STATUS             = "EG_EXPENSE_INVALID_BILL_STATUS";
	public static final String ERR_INVALID_BILL_DETAIL_IDS         = "EG_EXPENSE_INVALID_BILL_DETAIL_IDS";
	public static final String ERR_ROLE_STATUS_MISMATCH            = "EG_EXPENSE_ROLE_STATUS_MISMATCH";
	@Deprecated
	public static final String ERR_DETAIL_STATUS_SKIPPED           = "EG_EXPENSE_DETAIL_STATUS_SKIPPED";
	public static final String ERR_DETAIL_STATUS_SKIPPED_EDITOR    = "EG_EXPENSE_DETAIL_STATUS_SKIPPED_EDITOR";
	public static final String ERR_DETAIL_STATUS_SKIPPED_REVIEWER  = "EG_EXPENSE_DETAIL_STATUS_SKIPPED_REVIEWER";
	public static final String ERR_FIELD_STRIPPED_EDITOR           = "EG_EXPENSE_FIELD_STRIPPED_PAYMENT_EDITOR";
	public static final String ERR_FIELD_STRIPPED_REVIEWER         = "EG_EXPENSE_FIELD_STRIPPED_PAYMENT_REVIEWER";
	public static final String ERR_TEMPLATE_EMPTY                  = "EG_EXPENSE_TEMPLATE_EMPTY";
	public static final String ERR_TEMPLATE_GENERATE_ERROR         = "EG_EXPENSE_TEMPLATE_GENERATE_ERROR";
	public static final String ERR_TEMPLATE_INVALID_HEADER         = "EG_EXPENSE_TEMPLATE_INVALID_HEADER";
	public static final String ERR_TEMPLATE_INVALID_FORMAT         = "EG_EXPENSE_TEMPLATE_INVALID_FORMAT";
	public static final String ERR_TEMPLATE_INVALID_ROW            = "EG_EXPENSE_TEMPLATE_INVALID_ROW";
	public static final String ERR_TEMPLATE_INVALID_ATTENDANCE     = "EG_EXPENSE_TEMPLATE_INVALID_ATTENDANCE";
	public static final String ERR_TEMPLATE_PARSE_ERROR            = "EG_EXPENSE_TEMPLATE_PARSE_ERROR";
	public static final String ERR_DUPLICATE_BILL                  = "EG_EXPENSE_DUPLICATE_BILL";
	public static final String ERR_PAYMENT_FIELD_UPDATE_NOT_ALLOWED = "EG_EXPENSE_PAYMENT_FIELD_UPDATE_NOT_ALLOWED";
	public static final String ERR_INVALID_PAYMENT_PROVIDER        = "EG_EXPENSE_INVALID_PAYMENT_PROVIDER";
	public static final String ERR_BILL_SEARCH_ERROR               = "EG_EXPENSE_BILL_SEARCH_ERROR";
	public static final String ERR_MDMS_ERROR                      = "EG_EXPENSE_MDMS_ERROR";
	public static final String ERR_BULK_STATUS_EMPTY               = "EG_EXPENSE_BULK_STATUS_EMPTY";
	public static final String ERR_BULK_STATUS_MAX_LIMIT           = "EG_EXPENSE_BULK_STATUS_MAX_LIMIT";
	public static final String ERR_BULK_STATUS_DUPLICATE_IDS       = "EG_EXPENSE_BULK_STATUS_DUPLICATE_IDS";
	public static final String ERR_BULK_STATUS_INVALID             = "EG_EXPENSE_BULK_STATUS_INVALID";
	public static final String ERR_BULK_STATUS_TENANT_REQUIRED     = "EG_EXPENSE_BULK_STATUS_TENANT_REQUIRED";
	public static final String ERR_BILL_UPDATE_NOTNULL_PAYER_ID              = "EG_EXPENSE_BILL_UPDATE_NOTNULL_PAYER_ID";
	public static final String ERR_BILL_UPDATE_NOTNULL_BILLDETAILS           = "EG_EXPENSE_BILL_UPDATE_NOTNULL_BILLDETAILS";
	public static final String ERR_BILL_UPDATE_NOTNULL_BILLDETAIL_ID         = "EG_EXPENSE_BILL_UPDATE_NOTNULL_BillDETAIL_ID";
	public static final String ERR_BILL_UPDATE_NOTNULL_LINEITEM_ID           = "EG_EXPENSE_BILL_UPDATE_NOTNULL_LINEITEM_ID";
	public static final String ERR_BILL_UPDATE_NOTNULL_PAYABLE_LINEITEM_ID   = "EG_EXPENSE_BILL_UPDATE_NOTNULL_PAYABLE_LINEITEM_ID";
	public static final String ERR_INVALID_BUSINESSSERVICE                   = "EG_EXPENSE_INVALID_BUSINESSSERVICE";
	public static final String ERR_LINEITEM_INVALID_AMOUNT                   = "EG_EXPENSE_LINEITEM_INVALID_AMOUNT";
	public static final String ERR_INVALID_HEADCODES                         = "EG_EXPENSE_INVALID_HEADCODES";
	public static final String ERR_BILL_INVALID_DATE                         = "EG_EXPENSE_BILL_INVALID_DATE";
	public static final String ERR_BILL_WF_ERROR                             = "EG_BILL_WF_ERROR";
	public static final String ERR_BILL_WF_FIELDS_ERROR                     = "EG_BILL_WF_FIELDS_ERROR";
	public static final String ERR_BILL_META_UPDATE_ERROR                   = "EG_BILL_META_UPDATE_ERROR";

	// Error messages (static — no dynamic parts)
	public static final String MSG_TEMPLATE_EMPTY                = "No valid rows found in the uploaded template";
	public static final String MSG_TEMPLATE_GENERATE_ERROR       = "Failed to generate bill detail template";
	public static final String MSG_TEMPLATE_INVALID_FORMAT       = "Uploaded file is not a valid Excel (.xlsx) format";
	public static final String MSG_TEMPLATE_MISSING_HEADER_ROW  = "Template header row is missing or empty";
	public static final String MSG_TEMPLATE_INVALID_WORKER_COL  = "Column 0 must be workerId; template file may be corrupt or wrong format";
	public static final String MSG_UNAUTHORIZED_TEMPLATE_PREFIX  = "You are not authorized to ";
	public static final String MSG_UNAUTHORIZED_UPDATE           = "You are not authorized to update bill details.";
	public static final String MSG_BULK_STATUS_EMPTY             = "At least one bill ID is required for bulk status update";
	public static final String MSG_BULK_STATUS_DUPLICATE_IDS     = "Duplicate bill IDs are not allowed in bulk status update request";
	public static final String MSG_BULK_STATUS_INVALID           = "Status is required for bulk status update";
	public static final String MSG_BULK_STATUS_TENANT_REQUIRED   = "tenantId is required for bulk status update";
	public static final String MSG_TEMPLATE_PARSE_ERROR_PREFIX   = "Failed to read Excel file: ";
	public static final String MSG_TEMPLATE_PARSE_UNEXPECTED     = "Unexpected error parsing template: ";
	public static final String MSG_BILL_UPDATE_NOTNULL_PAYER_ID     = "Payer id is mandaotry for update request";
	public static final String MSG_BILL_UPDATE_NOTNULL_BILLDETAILS  = "bill details cannot be empty for update request";
	public static final String MSG_BILL_WF_ERROR                    = "workflow is mandatory when worflow is active";
	public static final String MSG_BILL_WF_FIELDS_ERROR             = "workflow action is mandatory when worflow is active";
	public static final String MSG_META_UPDATE_REQUEST_MANDATORY    = "Request is mandatory";
	public static final String MSG_META_UPDATE_BILL_ID_MANDATORY    = "billId is mandatory";
	public static final String MSG_META_UPDATE_TENANT_ID_MANDATORY  = "tenantId is mandatory";
	public static final String MSG_META_UPDATE_REPORT_MANDATORY     = "reportDetails is mandatory and must not be empty";

	// Cache key prefixes
	public static final String BILL_CACHE_KEY_PREFIX = "bill:";
	public static final String TASK_CACHE_KEY_PREFIX = "task:";

	// WF error detection strings
	public static final String WF_INVALID_ACTION_MSG_1 = "INVALID ACTION";
	public static final String WF_INVALID_ACTION_MSG_2 = "No valid action";
	public static final String WF_INVALID_ACTION_MSG_3 = "INVALID_ACTION";

	// Error codes — locking and WF state
	public static final String ERR_BILL_DETAIL_LOCKED_SENDING_FOR_REVIEW = "BILL_DETAIL_LOCKED_SENDING_FOR_REVIEW";
	public static final String ERR_BILL_DETAIL_LOCKED_REVIEW_IN_PROGRESS = "BILL_DETAIL_LOCKED_REVIEW_IN_PROGRESS";
	public static final String ERR_WF_STATE_SEARCH_FAILED                = "EG_WF_STATE_SEARCH_FAILED";

	// Error messages — locking and WF state
	public static final String MSG_BILL_DETAIL_LOCKED_SENDING_FOR_REVIEW = "Bill detail is locked while bill is being sent for review";
	public static final String MSG_BILL_DETAIL_LOCKED_REVIEW_IN_PROGRESS = "Bill detail is locked while bill review is in progress";
	public static final String MSG_WF_STATE_SEARCH_FAILED                = "Failed to fetch current workflow state after transition failure";

	// Rate field config — paymentType values (mirror of health-expense-calculator RateFieldConfigConstants)
	public static final String PAYMENT_TYPE_PER_DAY   = "PER_DAY";
	public static final String PAYMENT_TYPE_ONE_TIME   = "ONE_TIME";
	public static final String PAYMENT_TYPE_PER_PERIOD = "PER_PERIOD";

	// Rate field config — valueType values
	public static final String VALUE_TYPE_FLAT       = "FLAT";
	public static final String VALUE_TYPE_PERCENTAGE = "PERCENTAGE";

	// Keys used in bill.additionalDetails by health-expense-calculator
	public static final String RATE_FIELD_CONFIG_SNAPSHOT_KEY = "workerRatesSnapshot";
	public static final String HEAD_CODE_MAPPING_KEY           = "headCodeMapping";
	public static final String CAMPAIGN_NUMBER_KEY             = "campaignNumber";

	// MDMS constants for HCM worker rates lookup
	public static final String HCM_MODULE_NAME      = "HCM";
	public static final String WORKER_RATES_MASTER  = "WORKER_RATES";
	public static final String HCM_WORKER_RATES_JSON_PATH = "$.MdmsRes.HCM.WORKER_RATES";
	public static final String WORKER_RATES_CAMPAIGN_ID_FILTER_PREFIX = "[?(@.campaignId=='";
	public static final String WORKER_RATES_CAMPAIGN_ID_FILTER_SUFFIX = "')]";
}
