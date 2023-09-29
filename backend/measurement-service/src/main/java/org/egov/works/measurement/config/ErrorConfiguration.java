package org.egov.works.measurement.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.http.HttpResponse;
import org.egov.tracer.model.CustomException;
import org.egov.works.measurement.web.models.Measurement;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.UUID;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ErrorConfiguration {
    public CustomException measurementDataNotExist = new CustomException("", Configuration.MEASUREMENT_DATA_NOT_EXIST);
    public CustomException measurementServiceDataNotExist = new CustomException("", Configuration.MEASUREMENT_SERVICE_DATA_NOT_EXIST);
    public CustomException measuresDataNotExist = new CustomException("", Configuration.MEASURES_DATA_NOT_EXIST);
    public CustomException cumulativeEnrichmentError = new CustomException("", Configuration.CUMULATIVE_ENRICHMENT_ERROR);
    public CustomException noActiveContractId = new CustomException(Collections.singletonMap("", Configuration.NO_ACTIVE_CONTRACT_ID));
    public CustomException duplicateTargetIds = new CustomException("", Configuration.DUPLICATE_TARGET_IDS);
    public CustomException incompleteMeasures = new CustomException("", Configuration.INCOMPLETE_MEASURES);
    public CustomException invalidDocuments = new CustomException("", Configuration.INVALID_DOCUMENTS);
    public CustomException noValidEstimate = new CustomException("", Configuration.NO_VALID_ESTIMATE);
    public CustomException idsAndMbNumberMismatch = new CustomException("", Configuration.IDS_AND_MB_NUMBER_MISMATCH);
    public CustomException invalidEstimateID = new CustomException("", Configuration.INVALID_ESTIMATE_ID);
    public CustomException invalidContract=new CustomException("",Configuration.INVALID_CONTRACT);

    public CustomException notValidReferenceId(String referenceId) {
        String errorMessage = MessageFormat.format(Configuration.NOT_VALID_REFERENCE_ID, referenceId);
        return new CustomException("", errorMessage);
    }

    public CustomException apiRequestFailed(HttpResponse response) {
        String errorMessage = MessageFormat.format(Configuration.API_REQUEST_FAILED, response.getStatusLine().getStatusCode());
        return new CustomException("", errorMessage);
    }

    public CustomException rejectedError(String measurementNumber) {
        String errorMessage = MessageFormat.format(Configuration.REJECTED_ERROR, measurementNumber);
        return new CustomException("", errorMessage);
    }

    public CustomException apiRequestFailedIOexception(IOException e) {
        String errorMessage = MessageFormat.format(Configuration.API_REQUEST_FAILED_IOEXCEPTION, e.getMessage());
        return new CustomException("", errorMessage);
    }

    public IllegalArgumentException tenantIdRequired = new IllegalArgumentException(Configuration.TENANT_ID_REQUIRED);
    public CustomException lineItemsNotProvided(String id) {
        String errorMessage = MessageFormat.format(Configuration.LINE_ITEMS_NOT_PROVIDED, id);
        return new CustomException("", errorMessage);
    }

    public CustomException invalidTargetIdForContract(String targetId, String referenceId) {
        String errorMessage = MessageFormat.format(Configuration.INVALID_TARGET_ID_FOR_CONTRACT, targetId, referenceId);
        return new CustomException("", errorMessage);
    }
}
