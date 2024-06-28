package org.egov.works.config;


import org.springframework.stereotype.Component;


@Component
public class ServiceConstants {

    public static final String EXTERNAL_SERVICE_EXCEPTION = "External Service threw an Exception: ";
    public static final String SEARCHER_SERVICE_EXCEPTION = "Exception while fetching from searcher: ";

    public static final String IDGEN_ERROR = "IDGEN ERROR";
    public static final String NO_IDS_FOUND_ERROR = "No ids returned from idgen Service";

    public static final String ERROR_WHILE_FETCHING_FROM_MDMS = "Exception occurred while fetching category lists from mdms: ";

    public static final String RES_MSG_ID = "uief87324";
    public static final String SUCCESSFUL = "successful";
    public static final String FAILED = "failed";

    public static final String URL = "url";
    public static final String URL_SHORTENING_ERROR_CODE = "URL_SHORTENING_ERROR";
    public static final String URL_SHORTENING_ERROR_MESSAGE = "Unable to shorten url: ";

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
    public static final String USER = "user";

    public static final String PARSING_ERROR = "PARSING ERROR";
    public static final String FAILED_TO_PARSE_BUSINESS_SERVICE_SEARCH = "Failed to parse response of workflow business service search";
    public static final String BUSINESS_SERVICE_NOT_FOUND = "BUSINESSSERVICE_NOT_FOUND";
    public static final String THE_BUSINESS_SERVICE = "The businessService ";
    public static final String NOT_FOUND = " is not found";
    public static final String TENANTID = "?tenantId=";
    public static final String BUSINESS_SERVICES = "&businessServices=";

    public static final String FILTER_START = "[?(";
    public static final String FILTER_END = "')]";
    public static final String SOR_ID_FILTER = "@.sorId=='";
    public static final String ID_FILTER = "@.id=='";
    public static final String FILTER_OR_CONSTANT = "'||";

     public static final String WORKS_SOR_SCHEMA_CODE = "WORKS-SOR.SOR";
    public static final String SOR_CODE_JSON_KEY = "id";
    public static final String SOR_TYPE_JSON_KEY = "sorType";

    public static final String RATE_ANALYSIS_SCHEDULE_DETAILS_ID= "rateAnalysisScheduleDetailsId";

    public static final String WORKS_SOR_KEY = "WORKS-SOR";
    public static final String RATES_KEY = "Rates";
    public static final String SOR_KEY = "SOR";
    public static final String COMPOSITION_KEY = "Composition";

    public static final String MDMS_TENANTS_MASTER_NAME = "tenants";
    public static final String MDMS_TENANT_MODULE_NAME = "tenant";

    public static final String DEFINED_QUANTITY = "definedQuantity";
    public static final String QUANTITY = "quantity";
    public static final String RATE_KEY = "rate";
    public static final String VALID_TO_KEY = "validTo";
    public static final String RATES_SCHEMA_CODE = "WORKS-SOR.Rates";
    public static final String NAN_KEY = "NaN";
    public static final String SOR_SUB_TYPE = "sorSubType";
    public static final String SOR_VARIANT = "sorVariant";
    public static final String SOR_TYPE = "sorType";
    public static final String UOM = "uom";
    public static final String DESCRIPTION_KEY = "description";
    public static final String CODE_KEY = "code";


}
