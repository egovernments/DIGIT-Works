package org.egov.works.mukta.adapter.constants;

public class Error {

    public static final String INVALID_REQUEST = "invalid_request";
    public static final String INVALID_ADDRESS = "invalid_address";
    public static final String INVALID_EMAIL = "invalid_email";
    public static final String INVALID_PHONE = "invalid_phone";

    public static final String INVALID_NAME = "invalid_name";
    public static final String INVALID_GENDER = "invalid_gender";
    public static final String PARSING_ERROR = "PARSING ERROR";
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
    public static final String DISBURSEMENT_NOT_FOUND_MESSAGE = "Disbursement not found for the given disbursement request";
    public static final String TARGET_ID_NOT_MATCHED = "TARGET_ID_NOT_MATCHED";
    public static final String TARGET_ID_NOT_MATCHED_MESSAGE = "Target id not matched with the payment reference id";
    public static final String ALL_CHILDS_ARE_NOT_PRESENT = "ALL_CHILDS_ARE_NOT_PRESENT";
    public static final String ALL_CHILDS_ARE_NOT_PRESENT_MESSAGE = "All child disbursements are not present in the request";
    public static final String TENANT_ID_NOT_FOUND = "TENANT_ID_NOT_FOUND";
    public static final String REFERENCE_ID_AND_PAYEMENT_NOT_FOUND = "Request should contain either reference id or payement number inside payment";
    public static final String REFERENCE_ID_AND_TENANT_ID_NOT_FOUND = "Request should contain both reference id or tenant id if payment is null";
}
