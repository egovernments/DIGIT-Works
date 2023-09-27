package org.egov.works.measurement.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.http.HttpResponse;
import org.egov.tracer.model.CustomException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;


@Component
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ErrorConfiguration {
    public  CustomException measurementDataNotExist=new CustomException("","Measurement ID not present in the database");
    public  CustomException measurementServiceDataNotExist=new CustomException("","MeasurementRegistry data does not exist");
    public  CustomException measuresDataNotExist=new CustomException("","Measures data does not exist");
    public  CustomException cumulativeEnrichmentError=new CustomException("","Error during Cumulative enrichment");

    public CustomException noActiveContractId=new CustomException(Collections.singletonMap("", "No active contract with the given contract id"));
    public CustomException duplicateTargetIds=new CustomException("","Duplicate Target Ids received, its should be unique");

    public CustomException incompleteMeasures=new CustomException("","Incomplete Measures, some active line items are missed for the given contract");

    public CustomException invalidDocuments=new CustomException("","Document IDs are invalid");
    public CustomException noValidEstimate=new CustomException("","No valid Estimate found");
    public CustomException idsAndMbNumberMismatch=new CustomException("","Id and Measurement Number is not matching");
    public CustomException invalidEstimateID=new CustomException("","Estimate Ids are invalid");

    public CustomException apiRequestFailed(HttpResponse response){
        return new CustomException("","API request failed with status code: " + response.getStatusLine().getStatusCode());
    }
    public CustomException apiRequestFailedIOexception(IOException e){
        return new CustomException("","API request failed: " + e.getMessage());
    }
    public IllegalArgumentException tenantIdRequired= new IllegalArgumentException("TenantId is required.");

}
