package org.egov.works.measurement.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.models.Document;
import org.egov.tracer.model.CustomException;
import org.egov.works.measurement.config.MBRegistryConfiguration;
import org.egov.works.measurement.repository.ServiceRequestRepository;
import org.egov.works.measurement.service.MeasurementRegistry;
import org.egov.works.measurement.util.MdmsUtil;
import org.egov.works.measurement.util.MeasurementRegistryUtil;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;

import static org.egov.works.measurement.config.ErrorConfiguration.*;
import static org.egov.works.measurement.config.ServiceConstants.MDMS_TENANTS_MASTER_NAME;
import static org.egov.works.measurement.config.ServiceConstants.MDMS_TENANT_MODULE_NAME;

@Component
@Slf4j
public class MeasurementValidator {

    @Autowired
    private MdmsUtil mdmsUtil;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MBRegistryConfiguration MBRegistryConfiguration;
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    @Autowired
    private MeasurementRegistryUtil measurementRegistryUtil;

    /**
     * Validate the measurement Req for valid tenantId
     * @param measurementRequest
     */
    public void validateTenantId(MeasurementRequest measurementRequest){
        Set<String> validTenantSet = new HashSet<>();
        List<String> masterList = Collections.singletonList(MDMS_TENANTS_MASTER_NAME);
        Map<String, Map<String, JSONArray>> response = mdmsUtil.fetchMdmsData(measurementRequest.getRequestInfo(), MBRegistryConfiguration.getStateLevelTenantId(), MDMS_TENANT_MODULE_NAME,masterList);
        String node = response.get(MDMS_TENANT_MODULE_NAME).get(MDMS_TENANTS_MASTER_NAME).toString();
        try {
                JsonNode currNode = objectMapper.readTree(node);
                for (JsonNode tenantNode : currNode) {
                    // Assuming each item in the array has a "code" field
                    String tenantId = tenantNode.get("code").asText();
                    validTenantSet.add(tenantId);
                }
            }
        catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        List<Measurement> measurementList = measurementRequest.getMeasurements();
        for(int i=0;i<measurementList.size();i++){
            if(!validTenantSet.contains(measurementList.get(i).getTenantId())){
                 throw new CustomException(TENANT_ID_NOT_FOUND_CODE,measurementList.get(i).getTenantId() + TENANT_ID_NOT_FOUND_MSG);
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
            criteria.setIds(Collections.singletonList(measurement.getId()));
            criteria.setTenantId(measurement.getTenantId());

            //Getting list every time because tenantId may vary
            List<Measurement> existingMeasurementList= serviceRequestRepository.getMeasurements(criteria,searchRequest);
            if (existingMeasurementList.isEmpty()) {
                throw new CustomException(MEASUREMENT_DATA_NOT_EXIST_CODE, MEASUREMENT_DATA_NOT_EXIST_MSG);
            }
            measurementExisting.add(existingMeasurementList.get(0));
            validateMeasureRequest(existingMeasurementList.get(0),measurement);
        }

        //setting totalValue
        for(Measurement measurement:measurementRegistrationRequest.getMeasurements() ){
            for(Measure measure:measurement.getMeasures()){
                measurementRegistryUtil.validateDimensions(measure);
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
            String responseJson = makeApiRequest(documentIds, measurements.get(0).getTenantId());

            // Check if document IDs match the response
            boolean documentIdsMatch = checkDocumentIdsMatch(documentIds, responseJson);

            if (!documentIdsMatch) {
                throw new CustomException(INVALID_DOCUMENTS_CODE, INVALID_DOCUMENTS_MSG);
            }
        }
    }
    public void validateMeasureRequest(Measurement existingMeasurement,Measurement measurement){
        Set<String> measuresIds=new HashSet<>();
        for(Measure measure:existingMeasurement.getMeasures()){
            measuresIds.add(measure.getId());
        }
        for(Measure measure:measurement.getMeasures()){
            if(!measuresIds.contains(measure.getId())){
                throw new CustomException(MEASURES_DATA_NOT_EXIST_CODE, MEASURES_DATA_NOT_EXIST_MSG);
            }
        }
    }

    public boolean checkDocumentIdsMatch(List<String> documentIds, String responseJson) {

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

    private String makeApiRequest(List<String> documentIds, String tenantId) {
        String baseUrl = MBRegistryConfiguration.getBaseFilestoreUrl();
        String endpoint = MBRegistryConfiguration.getBaseFilestoreEndpoint();

        // Build the URL with query parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + endpoint)
                .queryParam("tenantId", tenantId);

        for (String documentId : documentIds) {
            builder.queryParam("fileStoreIds", documentId);
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                String.class
        );

        if (responseEntity.getStatusCodeValue() == 200) {
            // Read and return the response content as a string
            return responseEntity.getBody();
        } else {
            // Handle non-200 status codes (e.g., by throwing an exception)
            throw new CustomException(API_REQUEST_FAIL_CODE, API_REQUEST_FAIL_MSG + responseEntity);
        }
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
