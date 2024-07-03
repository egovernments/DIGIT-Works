package org.egov.works.config;

import org.springframework.stereotype.Component;

@Component
public class ErrorConfiguration {

    public static final String TENANT_ID_NOT_FOUND_CODE = "TENANT_ID_NOT_FOUND";
    public static final String TENANT_ID_NOT_FOUND_MSG = " Tenant Id is Not found.";
    public static final String INVALID_ESTIMATE_CODE = "INVALID_ESTIMATE";
    public static final String INVALID_ESTIMATE_MSG = "Invalid Estimate Number.";
    public static final String ESTIMATE_RESPONSE_NULL_EMPTY_CODE = "ESTIMATE_RESPONSE_NULL_EMPTY_CODE";
    public static final String ESTIMATE_RESPONSE_NULL_EMPTY_MSG = "Estimate Response is null or empty";
    public static final String STATEMENT_REQUEST_EMPTY_CODE="STATEMENT_REQUEST_EMPTY";
    public static final String STATEMENT_REQUEST_EMPTY_MSG="Statement Request is mandatory";
    public static final String ESTIMATE_ID_NOT_PASSED_CODE="ESTIMATE_ID_NOT_PASSED";
    public static final String ESTIMATE_ID_NOT_PASSED_MSG="Estimate Id is Mandatory";
    public static final String NO_SOR_PRESENT_CODE="NO_SOR_PRESENT";
    public static final String NO_SOR_PRESENT_MSG="Estimate does not contain any estimate details whose category is SOR";
    public static final String CONVERSION_ERROR_KEY = "CONVERSION_ERROR";
    public static final String CONVERSION_ERROR_VALUE = "Error while creating utilization statement for measurement :: ";
    public static final String ANALYSIS_CONVERSION_ERROR_VALUE = "Error while creating analysis statement for estimate :: ";


}
