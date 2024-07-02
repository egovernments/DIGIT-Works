package org.egov.works.config;

public class ErrorConfiguration {

    public static final String MDMS_PARSE_EXCEPTION_KEY = "MDMS_PARSE_EXCEPTION";
    public static final String MDMS_PARSE_EXCEPTION_VALUE = "Error while parsing mdms response";
    public static final String SOR_COMPOSITION_NOT_FOUND_KEY = "SOR_COMPOSITION_NOT_FOUND";
    public static final String SOR_COMPOSITION_NOT_FOUND_VALUE = "Sor composition not found for SOR codes :: ";
    public static final String SOR_NOT_FOUND_KEY = "SOR_NOT_FOUND";
    public static final String SOR_NOT_FOUND_VALUE = "Sor not found for the following SOR ids :: ";

    public static final String TENANT_ID_NOT_FOUND_CODE = "TENANT_ID_NOT_FOUND";
    public static final String TENANT_ID_NOT_FOUND_MSG = " Tenant Id is Not found.";

    public static final String NO_RATES_FOUND_KEY = "NO_RATES_FOUND";
    public static final String NO_RATES_FOUND_MSG = "Rate not found for the sor id :: ";
    public static final String SOR_COMPOSITION_NOT_FOUND_TIME_KEY = "SOR_COMPOSITION_NOT_FOUND_TIME";
    public static final String SOR_COMPOSITION_NOT_FOUND_TIME_MSG = "Sor composition with given effective time not found for SOR codes :: ";
    public static final String EFFECTIVE_FROM_ERROR_KEY = "EFFECTIVE_FROM_ERROR";
    public static final String EFFECTIVE_FROM_ERROR_MSG ="Effective from date cannot be less than current date";
    public static final String EFFECTIVE_FROM_PREVIOUS_ERROR_KEY = "EFFECTIVE_FROM_PREVIOUS_ERROR";
    public static final String EFFECTIVE_FROM_PREVIOUS_ERROR_MSG = "Effective from date cannot be less than previous effective from date for sorId :: ";
    public static final String RATE_SAME_KEY = "RATE_SAME";
    public static final String RATE_SAME_MSG = "Previous rate same as new rate for sorId :: ";


}
