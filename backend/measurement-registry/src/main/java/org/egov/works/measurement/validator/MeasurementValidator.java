package org.egov.works.measurement.validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.egov.common.contract.models.Document;
import org.egov.tracer.model.CustomException;
import org.egov.works.measurement.config.Configuration;
import org.egov.works.measurement.config.ErrorConfiguration;
import org.egov.works.measurement.service.MeasurementRegistry;
import org.egov.works.measurement.util.MdmsUtil;
import org.egov.works.measurement.web.models.Measure;
import org.egov.works.measurement.web.models.Measurement;
import org.egov.works.measurement.web.models.MeasurementCriteria;
import org.egov.works.measurement.web.models.MeasurementRequest;
import org.egov.works.measurement.web.models.MeasurementSearchRequest;
import org.egov.works.measurement.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;

@Component
@Slf4j
public class MeasurementValidator {

    @Autowired
    private MdmsUtil mdmsUtil;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ErrorConfiguration errorConfigs;
    @Autowired
    private Configuration configuration;
    @Autowired
    private MeasurementRegistry measurementRegistry;

    /**
     * Validate the measurement Req for valid tenantId
     * @param measurementRequest
     */
    public void validateTenantId(MeasurementRequest measurementRequest){
        Set<String> validTenantSet = new HashSet<>();
        List<String> masterList = Collections.singletonList(configuration.getMdmsMasterName());
        Map<String, Map<String, JSONArray>> response = mdmsUtil.fetchMdmsData(measurementRequest.getRequestInfo(),configuration.getMdmsTenantId(),configuration.getMdmsModuleName(),masterList);
        String node = response.get(configuration.getMdmsModuleName()).get(configuration.getMdmsMasterName()).toString();
        try {
                JsonNode currNode = objectMapper.readTree(node);
                for (JsonNode tenantNode : currNode) {
                    // Assuming each item in the array has a "code" field
                    String tenantId = tenantNode.get("code").asText();
                    validTenantSet.add(tenantId);
                }
            }
        catch (JsonProcessingException e) {
        	//TODO: Why are we catching and throwing again without any sort of value add?
            throw new RuntimeException(e);
            }
        List<Measurement> measurementList = measurementRequest.getMeasurements();
        for(int i=0;i<measurementList.size();i++){
            if(!validTenantSet.contains(measurementList.get(i).getTenantId())){
                 throw new CustomException("",measurementList.get(i).getTenantId()+" Tenant Id is Not found");
            }
        }
    }

    public void validateExistingDataAndEnrich(MeasurementRequest measurementRegistrationRequest) {
        List<Measurement> measurementExisting = new ArrayList<>();
        MeasurementCriteria criteria=new MeasurementCriteria();
        Pagination pagination=new Pagination();
        MeasurementSearchRequest searchRequest=MeasurementSearchRequest.builder().requestInfo(measurementRegistrationRequest.getRequestInfo()).criteria(criteria).pagination(pagination).build();

        for (Measurement measurement : measurementRegistrationRequest.getMeasurements()) {
            // Validate the measurement
            criteria.setIds(Collections.singletonList(measurement.getId().toString()));
            criteria.setTenantId(measurement.getTenantId());

            //Getting list every time because tenantId may vary
            List<Measurement> existingMeasurementList= measurementRegistry.searchMeasurements(criteria,searchRequest);
            if (existingMeasurementList.isEmpty()) {
                throw errorConfigs.measurementDataNotExist;
            }
            measurementExisting.add(existingMeasurementList.get(0));
            validateMeasureRequest(existingMeasurementList.get(0),measurement);
        }

        //setting totalValue
        for(Measurement measurement:measurementRegistrationRequest.getMeasurements() ){
            for(Measure measure:measurement.getMeasures()){
                measure.setCurrentValue(measure.getLength().multiply(measure.getHeight().multiply(measure.getBreadth().multiply(measure.getNumItems()))));
            }
        }

        // Perform the measurement update
        setAuditDetails(measurementExisting, measurementRegistrationRequest);
    }
    public void validateDocumentIds(List<Measurement> measurements) {
        List<String> documentIds = extractDocumentIds(measurements);

        if(!documentIds.isEmpty()){
            // Make an API request to validate document IDs
            String responseJson = makeApiRequest(documentIds);

            // Check if document IDs match the response
            boolean documentIdsMatch = checkDocumentIdsMatch(documentIds, responseJson);

            if (!documentIdsMatch) {
                throw errorConfigs.invalidDocuments;
            }
        }
    }
    public void validateMeasureRequest(Measurement existingMeasurement,Measurement measurement){
        Set<UUID> measuresIds=new HashSet<>();
        for(Measure measure:existingMeasurement.getMeasures()){
            measuresIds.add(measure.getId());
        }
        for(Measure measure:measurement.getMeasures()){
            if(!measuresIds.contains(measure.getId())){
                throw errorConfigs.measuresDataNotExist;
            }
        }
    }

    public boolean checkDocumentIdsMatch(List<String> documentIds, String responseJson) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, String> fileStoreIds = objectMapper.readValue(responseJson, Map.class);

            for (String documentId : documentIds) {
                if (!fileStoreIds.containsKey(documentId)) {
                    return false; // At least one document ID was not found
                }
            }

            return true; // All document IDs were found
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Error occurred while parsing the response
        }
    }

    //TODO: We use Spring RestTemplate to make interservice calls, no need to construct 
    //HTTP client etc..Please check another service as a reference.
    private String makeApiRequest(List<String> documentIds) {
        // API URL with query parameters
        HttpGet httpGet = buildHttpGetRequest(documentIds);

        // Create an HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            // Execute the request
            HttpResponse response = httpClient.execute(httpGet);

            // Check the response status code
            if (response.getStatusLine().getStatusCode() == 200) {
                // Read and return the response content as a string
                return EntityUtils.toString(response.getEntity());
            } else {
                // Handle non-200 status codes (e.g., by throwing an exception)
                throw errorConfigs.apiRequestFailed(response);
            }
        } catch (IOException e) {
            // Handle exceptions (e.g., by logging or rethrowing)
            e.printStackTrace();
            throw errorConfigs.apiRequestFailedIOexception(e);
        } finally {
            try {
                // Close the HttpClient
                httpClient.close();
            } catch (IOException e) {
                // Handle closing exception (if needed)
                e.printStackTrace();
            }
        }
    }

    private HttpGet buildHttpGetRequest(List<String> documentIds) {
        String apiUrl = configuration.getBaseFilestoreUrl() + configuration.getBaseFilestoreEndpoint()+"?tenantId=pg.citya";

        for (String documentId : documentIds) {
            apiUrl += "&fileStoreIds=" + documentId;
        }

        HttpGet httpGet = new HttpGet(apiUrl);
        return httpGet;
    }

    private List<String> extractDocumentIds(List<Measurement> measurements) {
        List<String> documentIds = new ArrayList<>();
        for (Measurement measurement : measurements) {
            Measurement ms = measurement;
            if(ms.getDocuments()!=null){
                for(Document document:ms.getDocuments()){
                    documentIds.add(document.getFileStore());
                }
            }
        }
        return documentIds;
    }

    public void setAuditDetails(List<Measurement> measurementExisting,MeasurementRequest measurementRequest){
        List<Measurement> measurements=measurementRequest.getMeasurements();
        for(int i=0;i<measurements.size();i++){
            measurements.get(i).setAuditDetails(measurementExisting.get(i).getAuditDetails());
            measurements.get(i).getAuditDetails().setLastModifiedBy(measurementRequest.getRequestInfo().getUserInfo().getUuid());
            measurements.get(i).getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
            for(Measure measure:measurements.get(i).getMeasures()){
                measure.setAuditDetails(measurements.get(i).getAuditDetails());
            }
        }
        measurementRequest.setMeasurements(measurements);
    }
}
