package org.egov.works.measurement.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.egov.common.contract.models.Document;
import org.egov.works.measurement.config.Configuration;
import org.egov.works.measurement.config.ErrorConfiguration;
import org.egov.works.measurement.repository.ServiceRequestRepository;
import org.egov.works.measurement.repository.rowmapper.MeasurementRowMapper;
import org.egov.works.measurement.service.MeasurementRegistry;
import org.egov.works.measurement.util.ContractUtil;
import org.egov.works.measurement.util.MeasurementRegistryUtil;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
    private ErrorConfiguration errorConfigs;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;


    @Autowired
    private MeasurementRegistry measurementRegistry;

    @Autowired
    private MeasurementRegistryUtil measurementRegistryUtil;


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
                throw errorConfigs.invalidDocuments;
            }
        }
    }


    public void validateContracts(MeasurementServiceRequest measurementServiceRequest) {
        measurementServiceRequest.getMeasurements().forEach(measurement -> {
            // validate contracts
            Boolean isValidContract = contractUtil.validContract(measurement, measurementServiceRequest.getRequestInfo());

            if (!isValidContract) {
                throw errorConfigs.invalidEstimateID;
            }
        });
    }

    public void validateExistingServiceDataAndEnrich(MeasurementServiceRequest measurementServiceRequest) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<String> mbNumbers = new ArrayList<>();
        Map<String, MeasurementSvcObject> mbNumberToServiceMap = new HashMap<>();

        for (MeasurementSvcObject measurementService : measurementServiceRequest.getMeasurements()) {
            mbNumbers.add(measurementService.getMeasurementNumber());
        }

        List<MeasurementSvcObject> existingMeasurementService = serviceRequestRepository.getMeasurementServicesFromMBSTable(namedParameterJdbcTemplate, mbNumbers);
        if(existingMeasurementService.size()!=measurementServiceRequest.getMeasurements().size()){
            throw errorConfigs.measurementServiceDataNotExist;
        }

        // if wfStatus is rejected then throw error
        for(MeasurementSvcObject measurementService:existingMeasurementService){
            if(measurementService.getWfStatus().equals("REJECTED")){
                 throw errorConfigs.rejectedError(measurementService.getMeasurementNumber());
            }
        }
        // Create a map to associate mbNumbers with corresponding MeasurementRegistry objects
        for (MeasurementSvcObject existingService : existingMeasurementService) {
            mbNumberToServiceMap.put(existingService.getMeasurementNumber(), existingService);
        }

        List<MeasurementSvcObject> orderedExistingMeasurementService = createOrderedMeasurementServiceList(mbNumbers, mbNumberToServiceMap); //ordering measurementServices
        matchIdsAndMbNumber(orderedExistingMeasurementService,measurementServiceRequest.getMeasurements()); // Match ids and measurement Numbers
        setServiceAuditDetails(orderedExistingMeasurementService, measurementServiceRequest); // Set audit details for orderedExistingMeasurementService
    }

    public void matchIdsAndMbNumber(List<MeasurementSvcObject> orderedExistingMeasurementService, List<MeasurementSvcObject> measurementServices) {
        List<UUID> existingIds = orderedExistingMeasurementService.stream()
                .map(MeasurementSvcObject::getId)
                .collect(Collectors.toList());

        List<String> existingMeasurementNumbers = orderedExistingMeasurementService.stream()
                .map(MeasurementSvcObject::getMeasurementNumber)
                .collect(Collectors.toList());

        List<UUID> newIds = measurementServices.stream()
                .map(MeasurementSvcObject::getId)
                .collect(Collectors.toList());

        List<String> newMeasurementNumbers = measurementServices.stream()
                .map(MeasurementSvcObject::getMeasurementNumber)
                .collect(Collectors.toList());

        if (!existingIds.equals(newIds) || !existingMeasurementNumbers.equals(newMeasurementNumbers)) {
            throw errorConfigs.idsAndMbNumberMismatch;
        }
    }


    public List<MeasurementSvcObject> createOrderedMeasurementServiceList(List<String> mbNumbers, Map<String, MeasurementSvcObject> mbNumberToServiceMap) {
        List<MeasurementSvcObject> orderedExistingMeasurementService = new ArrayList<>();

        // Populate orderedExistingMeasurementService to match the order of mbNumbers
        for (String mbNumber : mbNumbers) {
            MeasurementSvcObject existingService = mbNumberToServiceMap.get(mbNumber);
            orderedExistingMeasurementService.add(existingService);
        }

        return orderedExistingMeasurementService;
    }

    public void setServiceAuditDetails(List<MeasurementSvcObject> measurementServiceExisting,MeasurementServiceRequest measurementServiceRequest){
        List<MeasurementSvcObject> measurementServices=measurementServiceRequest.getMeasurements();
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

    private <T extends Measurement> List<String> extractDocumentIds(List<T> measurements) {
        List<String> documentIds = new ArrayList<>();
        for (T measurement : measurements) {
            if(measurement instanceof  MeasurementSvcObject){
                MeasurementSvcObject ms = (MeasurementSvcObject) measurement;
                if(ms.getDocuments()!=null){
                    for(Document document:ms.getDocuments()){
                        documentIds.add(document.getFileStore());
                    }
                }
                if (ms.getWorkflow() != null & ms.getWorkflow().getDocuments()!=null) {
                    for (Document document : ms.getWorkflow().getDocuments()) {
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
