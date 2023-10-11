package org.egov.works.measurement.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.http.HttpResponse;
import org.egov.tracer.model.CustomException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ErrorConfiguration {
    public CustomException measurementDataNotExist = new CustomException("MEASUREMENT_DATA_NOT_EXIST", MBServiceConfiguration.MEASUREMENT_DATA_NOT_EXIST);
    public CustomException measurementServiceDataNotExist = new CustomException("MEASUREMENT_SERVICE_DATA_NOT_EXIST", MBServiceConfiguration.MEASUREMENT_SERVICE_DATA_NOT_EXIST);
    public CustomException measuresDataNotExist = new CustomException("MEASURES_DATA_NOT_EXIST", MBServiceConfiguration.MEASURES_DATA_NOT_EXIST);
    public CustomException cumulativeEnrichmentError = new CustomException("CUMULATIVE_ENRICHMENT_ERROR", MBServiceConfiguration.CUMULATIVE_ENRICHMENT_ERROR);
    public CustomException noActiveContractId = new CustomException(Collections.singletonMap("NO_ACTIVE_CONTRACT_ID", MBServiceConfiguration.NO_ACTIVE_CONTRACT_ID));
    public CustomException duplicateTargetIds = new CustomException("DUPLICATE_TARGET_IDS", MBServiceConfiguration.DUPLICATE_TARGET_IDS);
    public CustomException incompleteMeasures = new CustomException("INCOMPLETE_MEASURES", MBServiceConfiguration.INCOMPLETE_MEASURES);
    public CustomException invalidDocuments = new CustomException("INVALID_DOCUMENTS", MBServiceConfiguration.INVALID_DOCUMENTS);
    public CustomException noValidEstimate = new CustomException("NO_VALID_ESTIMATE", MBServiceConfiguration.NO_VALID_ESTIMATE);
    public CustomException idsAndMbNumberMismatch = new CustomException("IDS_AND_MB_NUMBER_MISMATCH", MBServiceConfiguration.IDS_AND_MB_NUMBER_MISMATCH);
    public CustomException invalidEstimateID = new CustomException("INVALID_ESTIMATE_ID", MBServiceConfiguration.INVALID_ESTIMATE_ID);
    public CustomException invalidContract = new CustomException("INVALID_CONTRACT", MBServiceConfiguration.INVALID_CONTRACT);

    public CustomException notValidReferenceId(String referenceId) {
        String errorMessage = MessageFormat.format(MBServiceConfiguration.NOT_VALID_REFERENCE_ID, referenceId);
        return new CustomException("NOT_VALID_REFERENCE_ID", errorMessage);
    }

    public CustomException apiRequestFailed(HttpResponse response) {
        String errorMessage = MessageFormat.format(MBServiceConfiguration.API_REQUEST_FAILED, response.getStatusLine().getStatusCode());
        return new CustomException("API_REQUEST_FAILED", errorMessage);
    }

    public CustomException rejectedError(String measurementNumber) {
        String errorMessage = MessageFormat.format(MBServiceConfiguration.REJECTED_ERROR, measurementNumber);
        return new CustomException("REJECTED_ERROR", errorMessage);
    }

    public CustomException apiRequestFailedIOexception(IOException e) {
        String errorMessage = MessageFormat.format(MBServiceConfiguration.API_REQUEST_FAILED_IOEXCEPTION, e.getMessage());
        return new CustomException("API_REQUEST_FAILED_IOEXCEPTION", errorMessage);
    }

    public IllegalArgumentException tenantIdRequired = new IllegalArgumentException(MBServiceConfiguration.TENANT_ID_REQUIRED);

    public CustomException lineItemsNotProvided(String id) {
        String errorMessage = MessageFormat.format(MBServiceConfiguration.LINE_ITEMS_NOT_PROVIDED, id);
        return new CustomException("LINE_ITEMS_NOT_PROVIDED", errorMessage);
    }

    public CustomException invalidTargetIdForContract(String targetId, String referenceId) {
        String errorMessage = MessageFormat.format(MBServiceConfiguration.INVALID_TARGET_ID_FOR_CONTRACT, targetId, referenceId);
        return new CustomException("INVALID_TARGET_ID_FOR_CONTRACT", errorMessage);
    }
}
