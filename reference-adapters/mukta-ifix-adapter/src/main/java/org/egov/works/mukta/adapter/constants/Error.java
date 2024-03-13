package org.egov.works.mukta.adapter.constants;

public class Error {

    public static final String INVALID_REQUEST = "INVALID_REQUEST";
    public static final String INVALID_ADDRESS = "INVALID_ADDRESS";
    public static final String INVALID_EMAIL = "INVALID_EMAIL";
    public static final String INVALID_PHONE = "INVALID_PHONE";

    public static final String INVALID_NAME = "INVALID_NAME";
    public static final String INVALID_GENDER = "INVALID_GENDER";
    public static final String PARSING_ERROR = "PARSING_ERROR";
    public static final String PARSE_ERROR_MESSAGE = "The propertyAdditionalDetail json cannot be parsed";
    public static final String PAYMENT_NOT_FOUND = "PAYMENT_NOT_FOUND";
    public static final String PAYMENT_NOT_FOUND_MESSAGE = "Payment not found for the given disbursement request";
    public static final String BILLS_NOT_FOUND = "BILLS_NOT_FOUND";
    public static final String BILLS_NOT_FOUND_MESSAGE = "No bills found for the payment instruction";
    public static final String BENEFICIARIES_NOT_FOUND = "BENEFICIARIES_NOT_FOUND";
    public static final String BENEFICIARIES_NOT_FOUND_MESSAGE = "No beneficiaries found for the payment instruction";
    public static final String INVALID_BANK_ACCOUNT_SEARCH_CRITERIA = "INVALID_BANK_ACCOUNT_SEARCH_CRITERIA";
    public static final String INVALID_BANK_ACCOUNT_SEARCH_CRITERIA_MESSAGE = "Invalid bank account search criteria";
    public static final String INVALID_INDIVIDUAL_SEARCH_CRITERIA = "INVALID_INDIVIDUAL_SEARCH_CRITERIA";
    public static final String INVALID_INDIVIDUAL_SEARCH_CRITERIA_MESSAGE = "Invalid individual search criteria";
    public static final String ORG_CRITERIA_ERROR = "ORG_CRITERIA_ERROR";
    public static final String ORG_CRITERIA_ERROR_MESSAGE = "Request info, or ids are empty in organisation search";
    public static final String PAYMENT_ALREADY_PROCESSED = "PAYMENT_ALREADY_PROCESSED";
    public static final String PAYMENT_ALREADY_PROCESSED_MESSAGE = "Payment already processed";
    public static final String PAYMENT_REFERENCE_ID_NOT_FOUND_MESSAGE = "Payment reference id not found";
    public static final String SIGNATURE_NOT_FOUND_MESSAGE = "Signature not found in the request";
    public static final String HEADER_NOT_FOUND_MESSAGE = "Header not found in the request";
    public static final String MESSAGE_NOT_FOUND_MESSAGE = "Message not found in the request";
    public static final String DISBURSEMENT_NOT_FOUND = "DISBURSEMENT_NOT_FOUND";
    public static final String DISBURSEMENT_NOT_FOUND_IN_DB_MESSAGE = "Disbursement not found for the given disbursement request";
    public static final String TARGET_ID_NOT_MATCHED = "TARGET_ID_NOT_MATCHED";
    public static final String TARGET_ID_NOT_MATCHED_MESSAGE = "Target id not matched with the payment reference id";
    public static final String ALL_CHILDS_ARE_NOT_PRESENT = "ALL_CHILDS_ARE_NOT_PRESENT";
    public static final String ALL_CHILDS_ARE_NOT_PRESENT_MESSAGE = "All child disbursements are not present in the request";
    public static final String REFERENCE_ID_AND_PAYEMENT_NOT_FOUND = "Request should contain either reference id or payement number inside payment";
    public static final String REFERENCE_ID_AND_TENANT_ID_NOT_FOUND = "Request should contain both reference id or tenant id if payment is null";
    public static final String SENDER_ID_AND_RECEIVER_ID_SAME_MESSAGE = "Sender and receiver id cannot be same";
    public static final String DISBURSEMENTS_NOT_FOUND_MESSAGE = "Disbursements not found in the request";
    public static final String GROSS_AMOUNT_AND_NET_AMOUNT_NOT_MATCHED = "Sum of disbursement child item amounts should be equal to original disbursement amount.";
    public static final String ID_NOT_FOUND_MESSAGE = "Disbursement id not found in the request";
    public static final String DISBURSEMENT_STATUS_NOT_FOUND = "Disbursement status not found in the request";
    public static final String INDIVIDUAL_NOT_FOUND = "INDIVIDUAL_NOT_FOUND";
    public static final String ALLOCATION_IDS_NOT_FOUND_MESSAGE = "Allocation ids not found in the request";
    public static final String GROSS_AMOUNT_AND_NOT_FOUND_MESSAGE = "Gross amount not found in the request";
    public static final String NET_AMOUNT_NOT_FOUND_MESSAGE = "Net amount not found in the request";
    public static final String GROSS_AMOUNT_AND_NET_AMOUNT_NOT_MATCHED_WITH_ORIGINAL_DISBURSEMENT = "Gross amount and net amount not matched with the disbursement child items.";
    public static final String DISBURSEMENT_NOT_FOUND_MESSAGE = "Disbursement not found in the request";
    public static final String NO_BENEFICIARY_IDS_FOUND = "NO_BENEFICIARY_IDS_FOUND";
    public static final String NO_BENEFICIARY_IDS_FOUND_MESSAGE = "No beneficiary ids found in the request";
    public static final String DISBURSEMENT_ENRICHMENT_FAILED = "DISBURSEMENT_ENRICHMENT_FAILED";
    public static final String DISBURSEMENT_ENRICHMENT_FAILED_MESSAGE = "Disbursement enrichment failed for the given payment request";
    public static final String BANK_ACCOUNTS_NOT_FOUND = "BANK_ACCOUNTS_NOT_FOUND";
    public static final String BANK_ACCOUNTS_NOT_FOUND_MESSAGE = "Bank accounts not found for the given bank account search criteria";
    public static final String PROGRAM_SERVICE_ERROR = "PROGRAM_SERVICE_ERROR";
}
