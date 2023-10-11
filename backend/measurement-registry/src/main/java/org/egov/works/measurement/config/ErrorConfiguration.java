package org.egov.works.measurement.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.egov.tracer.model.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;


@Component
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ErrorConfiguration {
    public CustomException measurementDataNotExist = new CustomException("MEASUREMENT_DATA_NOT_EXIST", "Measurement ID not present in the database");
    public CustomException measurementServiceDataNotExist = new CustomException("MEASUREMENT_SERVICE_DATA_NOT_EXIST", "MeasurementRegistry data does not exist");
    public CustomException measuresDataNotExist = new CustomException("MEASURES_DATA_NOT_EXIST", "Measures data does not exist");
    public CustomException cumulativeEnrichmentError = new CustomException("CUMULATIVE_ENRICHMENT_ERROR", "Error during Cumulative enrichment");

    public CustomException noActiveContractId = new CustomException(Collections.singletonMap("NO_ACTIVE_CONTRACT_ID", "No active contract with the given contract id"));
    public CustomException duplicateTargetIds = new CustomException("DUPLICATE_TARGET_IDS", "Duplicate Target Ids received, it should be unique");

    public CustomException incompleteMeasures = new CustomException("INCOMPLETE_MEASURES", "Incomplete Measures, some active line items are missed for the given contract");

    public CustomException invalidDocuments = new CustomException("INVALID_DOCUMENTS", "Document IDs are invalid");
    public CustomException noValidEstimate = new CustomException("NO_VALID_ESTIMATE", "No valid Estimate found");
    public CustomException idsAndMbNumberMismatch = new CustomException("IDS_AND_MB_NUMBER_MISMATCH", "Id and Measurement Number do not match");
    public CustomException invalidEstimateID = new CustomException("INVALID_ESTIMATE_ID", "Estimate Ids are invalid");
    public CustomException tenantIdMandatory = new CustomException("TENANT_ID_MANDATORY", "TenantId is mandatory.");
    public CustomException searchCriteriaMandatory = new CustomException("SEARCH_CRITERIA_MANDATORY", "Search Criteria is mandatory");

    public CustomException apiRequestFailed(ResponseEntity<String> response){
        return new CustomException("API_REQUEST_FAIL","API request failed with status code: " + response);
    }
    public CustomException apiRequestFailedIOexception(IOException e){
        return new CustomException("API_REQUEST_FAIL_IO_EXCEPTION","API request failed: " + e.getMessage());
    }

}
