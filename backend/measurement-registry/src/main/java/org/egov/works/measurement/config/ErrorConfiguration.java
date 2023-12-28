package org.egov.works.measurement.config;

public class ErrorConfiguration {
    public static final String MEASUREMENT_DATA_NOT_EXIST_CODE = "MEASUREMENT_DATA_NOT_EXIST";
    public static final String MEASUREMENT_DATA_NOT_EXIST_MSG = "Measurement ID not present in the database";

    public static final String MEASURES_DATA_NOT_EXIST_CODE = "MEASURES_DATA_NOT_EXIST";
    public static final String MEASURES_DATA_NOT_EXIST_MSG = "Measures data does not exist";

    public static final String CUMULATIVE_ENRICHMENT_ERROR_CODE = "CUMULATIVE_ENRICHMENT_ERROR";
    public static final String CUMULATIVE_ENRICHMENT_ERROR_MSG = "Error during Cumulative enrichment";

    public static final String INVALID_DOCUMENTS_CODE = "INVALID_DOCUMENTS";
    public static final String INVALID_DOCUMENTS_MSG = "Document IDs are invalid";

    public static final String TENANT_ID_MANDATORY_CODE = "TENANT_ID_MANDATORY";
    public static final String TENANT_ID_MANDATORY_MSG = "TenantId is mandatory.";

    public static final String SEARCH_CRITERIA_MANDATORY_CODE = "SEARCH_CRITERIA_MANDATORY";
    public static final String SEARCH_CRITERIA_MANDATORY_MSG = "Search Criteria is mandatory";

    public static final String API_REQUEST_FAIL_CODE = "API_REQUEST_FAIL";
    public static final String API_REQUEST_FAIL_MSG = "API request failed with status code: ";

    public static final String TENANT_ID_NOT_FOUND_CODE = "TENANT_ID_NOT_FOUND";
    public static final String TENANT_ID_NOT_FOUND_MSG = " Tenant Id is Not found.";
}
