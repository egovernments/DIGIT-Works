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
    public static final String RATE_NOT_FOUND_KEY = "RATE_NOT_FOUND";
    public static final String RATE_NOT_FOUND_MSG = "No valid rate found for given time";
    public static final String RATES_NOT_FOUND_SOR_KEY = "RATES_NOT_FOUND_SOR";
    public static final String RATES_NOT_FOUND_SOR_MSG = "Rates not found for given SOR :: ";
    public static final String MDMS_PARSE_EXCEPTION_KEY = "MDMS_PARSE_EXCEPTION";
    public static final String MDMS_PARSE_EXCEPTION_MSG = "Error while parsing mdms response";
    public static final String MEASUREMENTS_NOT_FOUND_KEY = "MEASUREMENTS_NOT_FOUND";
    public static final String MEASUREMENTS_NOT_FOUND_MSG = "Measurements not found";
    public static final String CONTRACTS_NOT_FOUND_KEY = "CONTRACTS_NOT_FOUND";
    public static final String CONTRACTS_NOT_FOUND_MSG = "Contracts not found";
    public static final String ESTIMATES_NOT_FOUND_KEY = "ESTIMATES_NOT_FOUND";
    public static final String ESTIMATES_NOT_FOUND_MSG = "Estimates not found";
    public static final String NO_RATES_FOUND_FOR_BASIC_SOR_KEY = "NO_RATES_FOUND_FOR_BASIC_SOR";
    public static final String NO_RATES_FOUND_FOR_BASIC_SOR_MSG = "No rates found for basicSorId : ";
    public static final String NO_RATES_FOUND_KEY = "NO_RATES_FOUND";
    public static final String NO_RATES_FOUND_MSG = "No rates present ";
    public static final String SOR_COMPOSITION_NOT_FOUND_KEY = "SOR_COMPOSITION_NOT_FOUND";
    public static final String SOR_COMPOSITION_NOT_FOUND_MSG = "Sor composition with given effective time not found for SOR codes :: ";
    public static final String COMPOSITION_NOT_FOUND_IN_RATES_KEY = "COMPOSITION_NOT_FOUND_IN_RATES";
    public static final String COMPOSITION_NOT_FOUND_IN_RATES_MSG = "statements could not be generated because rate analysis for one or more SORs is missing.";


}
