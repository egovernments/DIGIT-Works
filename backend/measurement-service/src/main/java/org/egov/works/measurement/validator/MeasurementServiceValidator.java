package org.egov.works.measurement.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Criteria;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.egov.works.measurement.config.Configuration;
import org.egov.works.measurement.repository.ServiceRequestRepository;
import org.egov.works.measurement.repository.rowmapper.MeasurementServiceRowMapper;
import org.egov.works.measurement.repository.rowmapper.MeasurementRowMapper;
import org.egov.works.measurement.util.ContractUtil;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class MeasurementServiceValidator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ContractUtil contractUtil;

    @Autowired
    private Configuration configuration;

    @Autowired
    private MeasurementRowMapper rowMapper;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;


    @Autowired
    private org.egov.works.measurement.service.MeasurementService measurementService;


    @Value("${egov.filestore.host}")
    private String baseFilestoreUrl;

    @Value("${egov.filestore.endpoint}")
    private String baseFilestoreEndpoint;


    public <T extends Measurement> void validateDocumentIds(List<T> measurements) {
        List<String> documentIds = extractDocumentIds(measurements);

        if(!documentIds.isEmpty()){
            // Make an API request to validate document IDs
            String responseJson = makeApiRequest(documentIds);

            // Check if document IDs match the response
            boolean documentIdsMatch = checkDocumentIdsMatch(documentIds, responseJson);

            if (!documentIdsMatch) {
                throw new RuntimeException("Document IDs are invalid");
            }
        }
    }


    public void validateContracts(MeasurementServiceRequest measurementServiceRequest) {
        measurementServiceRequest.getMeasurements().forEach(measurement -> {
            // validate contracts
            Boolean isValidContract = contractUtil.validContract(measurement, measurementServiceRequest.getRequestInfo());

            if (!isValidContract) {
                throw new RuntimeException("Estimate Ids are invalid");
            }
        });
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
            List<Measurement> existingMeasurementList=measurementService.searchMeasurements(criteria,searchRequest);
            if (existingMeasurementList.isEmpty()) {
                throw new RuntimeException("MB Data does not exist");
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

    public void validateMeasureRequest(Measurement existingMeasurement,Measurement measurement){
        Set<UUID> measuresIds=new HashSet<>();
        for(Measure measure:existingMeasurement.getMeasures()){
            measuresIds.add(measure.getId());
        }
        for(Measure measure:measurement.getMeasures()){
            if(!measuresIds.contains(measure.getId())){
                throw new RuntimeException("Measures data does not exist");
            }
        }
    }


    public void validateExistingServiceDataAndEnrich(MeasurementServiceRequest measurementServiceRequest) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<MeasurementService> existingMeasurementService = new ArrayList<>();

        for (MeasurementService measurementService : measurementServiceRequest.getMeasurements()) {
            String id = measurementService.getId().toString();
            String mbNumber = measurementService.getMeasurementNumber();

            // Check if measurements exist in eg_mbs_measurements
            MeasurementService measurementServiceInMBSTable = serviceRequestRepository.getMeasurementServiceFromMBSTable(namedParameterJdbcTemplate, mbNumber);

            if (measurementServiceInMBSTable == null) {
                throw new RuntimeException("MBS Data does not exist");
            }

            existingMeasurementService.add(measurementServiceInMBSTable);
        }

        setServiceAuditDetails(existingMeasurementService, measurementServiceRequest);
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

    public void setServiceAuditDetails(List<MeasurementService> measurementServiceExisting,MeasurementServiceRequest measurementServiceRequest){
        List<MeasurementService> measurementServices=measurementServiceRequest.getMeasurements();
        for(int i=0;i<measurementServiceExisting.size();i++){
            measurementServices.get(i).setAuditDetails(measurementServiceExisting.get(i).getAuditDetails());
            measurementServices.get(i).getAuditDetails().setLastModifiedBy(measurementServiceRequest.getRequestInfo().getUserInfo().getUuid());
            measurementServices.get(i).getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
            for(Measure measure:measurementServices.get(i).getMeasures()){
                measure.setAuditDetails(measurementServiceExisting.get(i).getAuditDetails());
            }
        }
        measurementServiceRequest.setMeasurements(measurementServices);
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
                throw   new RuntimeException("API request failed with status code: " + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            // Handle exceptions (e.g., by logging or rethrowing)
            e.printStackTrace();
            throw new RuntimeException("API request failed: " + e.getMessage(), e);
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

    private <T extends Measurement> List<String> extractDocumentIds(List<T> measurements) {
        List<String> documentIds = new ArrayList<>();
        for (T measurement : measurements) {
            if(measurement instanceof  MeasurementService){
                MeasurementService ms = (MeasurementService) measurement;
                if(ms.getDocuments()!=null){
                    for(Document document:ms.getDocuments()){
                        documentIds.add(document.getFileStore());
                    }
                }
                if (ms.getWorkflow() != null) {
                    for (digit.models.coremodels.Document document : ms.getWorkflow().getVerificationDocuments()) {
                        documentIds.add(document.getFileStore());
                    }
                }
            }
            else{
                Measurement ms = measurement;
                if(ms.getDocuments()!=null){
                    for(Document document:ms.getDocuments()){
                        documentIds.add(document.getFileStore());
                    }
                }
            }
        }
        return documentIds;
    }

    private HttpGet buildHttpGetRequest(List<String> documentIds) {
        String apiUrl = baseFilestoreUrl + baseFilestoreEndpoint+"?tenantId=pg.citya";

        for (String documentId : documentIds) {
            apiUrl += "&fileStoreIds=" + documentId;
        }

        HttpGet httpGet = new HttpGet(apiUrl);
        return httpGet;
    }

}
