package org.egov.works.measurement.config;

public class ErrorConfiguration {
    public static final String MEASUREMENT_SERVICE_DATA_NOT_EXIST_CODE = "MEASUREMENT_SERVICE_DATA_NOT_EXIST";
    public static final String MEASUREMENT_SERVICE_DATA_NOT_EXIST_MSG = "MeasurementRegistry data does not exist";

    public static final String DUPLICATE_TARGET_IDS_CODE = "DUPLICATE_TARGET_IDS";
    public static final String DUPLICATE_TARGET_IDS_MSG = "Duplicate Target Ids received, it should be unique";

    public static final String INCOMPLETE_MEASURES_CODE = "INCOMPLETE_MEASURES";
    public static final String INCOMPLETE_MEASURES_MSG = "Incomplete Measures, some active line items are missed for the given contract";

    public static final String NO_VALID_ESTIMATE_CODE = "NO_VALID_ESTIMATE";
    public static final String NO_VALID_ESTIMATE_MSG = "No valid Estimate found";

    public static final String IDS_AND_MB_NUMBER_MISMATCH_CODE = "IDS_AND_MB_NUMBER_MISMATCH";
    public static final String IDS_AND_MB_NUMBER_MISMATCH_MSG = "Id and Measurement Number do not match";

    public static final String INVALID_CONTRACT_CODE = "INVALID_CONTRACT";
    public static final String INVALID_CONTRACT_MSG = "Invalid Contract Number.";

    public static final String CONTRACT_NOT_ACCEPTED_CODE = "CONTRACT_NOT_ACCEPTED";
    public static final String CONTRACT_NOT_ACCEPTED_MSG = "Contract not in accepted state";

    public static final String REVISED_CONTRACT_NOT_APPROVED_CODE = "REVISED_CONTRACT_NOT_APPROVED";
    public static final String REVISED_CONTRACT_NOT_APPROVED_MSG = "Revised Contract not in approved state";

    public static final String NOT_VALID_REFERENCE_ID_CODE = "NOT_VALID_REFERENCE_ID";
    public static final String NOT_VALID_REFERENCE_ID_MSG = "Measurement data is already there in progress state for contract number :: ";

    public static final String REJECTED_ERROR_CODE = "REJECTED_ERROR";
    public static final String REJECTED_ERROR_MSG = "Measurement is rejected for measurementNumber :: ";

    public static final String LINE_ITEMS_NOT_PROVIDED_CODE = "LINE_ITEMS_NOT_PROVIDED";
    public static final String LINE_ITEMS_NOT_PROVIDED_MSG = "Mandatory value line item not provided :: ";

    public static final String INVALID_TARGET_ID_FOR_CONTRACT_CODE = "INVALID_TARGET_ID_FOR_CONTRACT";
    public static final String INVALID_TARGET_ID_FOR_CONTRACT_MSG = " is not a valid id for the given Contract ";

    public static final String PROCESS_INSTANCES_NOT_FOUND_CODE = "PROCESS_INSTANCES_NOT_FOUND";
    public static final String PROCESS_INSTANCES_NOT_FOUND_MSG = "Process instances not found for this businessId ::";

    public static final String CONVERSION_ERROR_CODE = "CONVERSION_ERROR";
    public static final String CONVERSION_ERROR_MSG = "Cannot convert response";

    public static final String PARSING_ERROR_CODE = "PARSING_ERROR";
    public static final String PARSING_ERROR_MSG = "Failed to parse project response";

    public static final String TENANT_ID_NOT_FOUND_CODE = "TENANT_ID_NOT_FOUND";
    public static final String TENANT_ID_NOT_FOUND_MSG = " Tenant Id is Not found.";

    public static final String ACTION_NOT_FOUND_CODE = "ACTION_NOT_FOUND";
    public static final String ACTION_NOT_FOUND_MSG1 = "Action ";
    public static final String ACTION_NOT_FOUND_MSG2 = " not found in config for the measurement number:: ";

    public static final String TOTAL_VALUE_GREATER_THAN_ESTIMATE_CODE = "TOTAL_VALUE_GREATER_THAN_ESTIMATE";
   // public static final String TOTAL_VALUE_GREATER_THAN_ESTIMATE_MSG = "For targetId : %s Total calculated value is greater than estimate quantity : %.2f";

    public static final String TOTAL_VALUE_GREATER_THAN_ESTIMATE_MSG = "For sorId: %s in estimate the total measurement quantity : %.2f is greater than the estimate quantity : %.2f ";

    public static final String INVALID_ENTRY_DATE_CODE = "INVALID_ENTRY_DATE";
    public static final String INVALID_ENTRY_DATE_MSG = "Entry date is not within contract start and end date";

    public static final String ESTIMATE_LINE_ITEM_ID_NOT_PRESENT_CODE = "ESTIMATE_LINE_ITEM_ID_NOT_PRESENT";
    public static final String ESTIMATE_LINE_ITEM_ID_NOT_PRESENT_MSG =  "Estimate lineItem id not present for targetId :: ";

    public static final String ESTIMATE_DETAILS_NOT_PRESENT_CODE = "ESTIMATE_DETAILS_NOT_PRESENT";
    public static final String ESTIMATE_DETAILS_NOT_PRESENT_MSG = "Estimate details not present for estimateLineItemid :: ";

    public static final String CONTRACT_NOT_APPROVED_CODE = "CONTRACT_NOT_APPROVED";

    public static final String CONTRACT_NOT_APPROVED_MSG = "Contract not in approved state";




}
