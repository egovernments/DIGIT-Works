package org.egov.works.measurement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.measurement.enrichment.MeasurementEnrichment;
import org.egov.works.measurement.kafka.Producer;
import org.egov.works.measurement.util.MdmsUtil;
import org.egov.works.measurement.validator.MeasurementServiceValidator;
import org.egov.works.measurement.web.models.Document;
import org.egov.works.measurement.web.models.Measure;
import org.egov.works.measurement.web.models.Measurement;
import org.egov.works.measurement.web.models.MeasurementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import org.egov.tracer.model.CustomException;
import org.egov.works.measurement.config.Configuration;

import org.egov.works.measurement.kafka.Producer;
import org.egov.works.measurement.util.ContractUtil;
import org.egov.works.measurement.util.IdgenUtil;
import org.egov.works.measurement.util.MdmsUtil;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.egov.works.measurement.repository.ServiceRequestRepository;
import org.egov.works.measurement.web.models.Measurement;
import org.egov.works.measurement.web.models.MeasurementCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MeasurementService {
    @Autowired
    private MdmsUtil mdmsUtil;
    @Autowired
    private IdgenUtil idgenUtil;
    @Autowired
    private Producer producer;

    @Autowired
    private Configuration configuration;

    @Autowired
    private MeasurementServiceValidator validator;

    @Autowired
    private MeasurementEnrichment enrichmentUtil;

    @Autowired
    private ContractUtil contractUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${egov.filestore.host}")
    private String baseFilestoreUrl;

    @Value("${egov.filestore.endpoint}")
    private String baseFilestoreEndpoint;

    @Value("${measurement.kafka.update.topic}")
    private String updateTopic;

    public ResponseEntity<MeasurementResponse> createMeasurement(MeasurementRequest request){
        System.out.println("Create Measurement service is called");
        String tenantId = request.getMeasurements().get(0).getTenantId(); // each measurement should have same tenantId otherwise this will fail
        String idName = "mb.reference.number";
        String idFormat = "MB/[fy:yyyy-yy]/[SEQ_MEASUREMENT_NUM]";

        MeasurementResponse response = new MeasurementResponse();
        List<Measurement> measurementList = new ArrayList<>();

        // TODO: Shift to Utils
        request.getMeasurements().forEach(measurement -> {

            // enrich UUID
            measurement.setId(UUID.randomUUID());

            // validate contracts
            Boolean isValidContract = contractUtil.validContract(measurement,request.getRequestInfo());

            if (!isValidContract) {
                // FIXME: handle the Error here ??
                throw new CustomException();
            }

            // TODO: check the docs for each measure in measurement
            for (Measure measure : measurement.getMeasures()) {

                // check all the docs;
                int isValidDocs = 1;
                for (Document document : measure.getDocuments()) {
                    if (!isValidDocuments(document.getFileStore())) {
                        isValidDocs = 0;
                        throw new Error("No Documents found with the given Ids");
                    } else {
                    }
                }
                if(isValidDocs == 1){
                    measure.setId(UUID.randomUUID());
                    measure.setReferenceId(measurement.getId().toString());
                }
            }

            // fetch ids from IdGen
            List<String> idList = idgenUtil.getIdList(request.getRequestInfo(), tenantId, idName, idFormat, 1);

            // enrich IdGen
            measurement.setMeasurementNumber(idList.get(0));

            // set audit details
            AuditDetails auditDetails = AuditDetails.builder().createdBy(request.getRequestInfo().getUserInfo().getUuid()).createdTime(System.currentTimeMillis()).lastModifiedTime(System.currentTimeMillis()).build();
            measurement.setAuditDetails(auditDetails);
            // add the measurement to measurementList
            measurementList.add(measurement);
        });
        response.setMeasurements(measurementList);

        // push to kafka topic
        producer.push(configuration.getCreateMeasurementTopic(),request);  // save  MeasurementResponse or request ?

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }


    public boolean isValidDocuments(String documentId){
        return true;
        // return !getDocuments(documentId).isEmpty(); // complete this method
    }
    public List<?> getDocuments(String documentId){
        return new ArrayList<>();
    }

    public ResponseEntity<MeasurementResponse> updateMeasurement(MeasurementRequest measurementRegistrationRequest) throws InvalidDocumentIdException {
        // Extract document IDs from the measurement request
        List<String> documentIds = extractDocumentIds(measurementRegistrationRequest);

        // Make an API request to retrieve file store IDs
        String responseJson = makeApiRequest(documentIds);

        // Parse the API response and check if all document IDs were found
        boolean allIdsMatched = validator.checkDocumentIdsMatch(documentIds, responseJson);

        // Perform the measurement update (if needed)
        List<String> idsToCheck=getAllIds(measurementRegistrationRequest);
        List<String> measurementExistingIds = validateMeasurementRequest(idsToCheck);
        MeasurementResponse response=new MeasurementResponse();
        response.setResponseInfo(ResponseInfo.builder().apiId(measurementRegistrationRequest.getRequestInfo().getApiId()).msgId(measurementRegistrationRequest.getRequestInfo().getMsgId()).ts(measurementRegistrationRequest.getRequestInfo().getTs()).build());

        // FIXME: validate the documents ids by allIdsMatched
        if (allIdsMatched) {
            if(measurementExistingIds.size()==measurementRegistrationRequest.getMeasurements().size()){
                response.setMeasurements(measurementRegistrationRequest.getMeasurements());
                AuditDetails auditDetails = AuditDetails.builder().createdBy(measurementRegistrationRequest.getRequestInfo().getUserInfo().getUuid()).createdTime(System.currentTimeMillis()).lastModifiedBy(measurementRegistrationRequest.getRequestInfo().getUserInfo().getUuid()).lastModifiedTime(System.currentTimeMillis()).build();
                for(Measurement measurement:measurementRegistrationRequest.getMeasurements()){
                    measurement.setAuditDetails(auditDetails);
                    for(Measure measure:measurement.getMeasures()){
                        measure.setAuditDetails(auditDetails);
                    }
                }
                producer.push(updateTopic,response);
            }
            else{
                throw new RuntimeException("Table data does not exist");
            }

        } else {
            // Handle the case where not all document IDs were found
            throw new RuntimeException("Files are invalid");

        }

        // Return the success response

        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }

    public ResponseEntity<MeasurementServiceResponse> updateMeasurementService(MeasurementServiceRequest measurementServiceRequest){
        MeasurementServiceResponse response=new MeasurementServiceResponse();
        AuditDetails auditDetails = AuditDetails.builder().createdBy(measurementServiceRequest.getRequestInfo().getUserInfo().getUuid()).createdTime(System.currentTimeMillis()).lastModifiedBy(measurementServiceRequest.getRequestInfo().getUserInfo().getUuid()).lastModifiedTime(System.currentTimeMillis()).build();
        for(Measurement measurement:measurementServiceRequest.getMeasurements()){
            measurement.setAuditDetails(auditDetails);
            for(Measure measure:measurement.getMeasures()){
                measure.setAuditDetails(auditDetails);
            }
        }
        response.setMeasurements(measurementServiceRequest.getMeasurements());
        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }

    public List<String> getAllIds(MeasurementRequest measurementRequest){
        List<String> ids=new ArrayList<>();
        for (Measurement measurement : measurementRequest.getMeasurements()) {
            String idAsString = measurement.getId().toString(); // Convert UUID to string
            ids.add(idAsString);
        }
        return ids;
    }

    public List<String> getAllIdsFromMbService(MeasurementServiceRequest measurementServiceRequest){
        List<String> ids=new ArrayList<>();
        for (Measurement measurement : measurementServiceRequest.getMeasurements()) {
            String idAsString = measurement.getId().toString(); // Convert UUID to string
            ids.add(idAsString);
        }
        return ids;
    }

    public List<String> validateMeasurementRequest(List<String> idsToCheck) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<String> measurementExistingIds = new ArrayList<>();

        for (String idToCheck : idsToCheck) {
            // Validate if a record with the specified ID exists in 'eg_mb_measurements' table
            String measurementExistingId = checkMeasurementExistsById(namedParameterJdbcTemplate, idToCheck);

            // If a record exists, add its ID to the list
            if (measurementExistingId != null) {
                measurementExistingIds.add(measurementExistingId);
            }
        }

        return measurementExistingIds;
    }


    private String checkMeasurementExistsById(NamedParameterJdbcTemplate namedParameterJdbcTemplate, String idToCheck) {
        // Define SQL query with named parameters
        String sql = "SELECT id FROM eg_mb_measurements WHERE id = :idToCheck";

        // Create parameter source with named parameter
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idToCheck", idToCheck);

        // Execute the query and return the ID if retrieved, or null if not found.
        List<String> ids = namedParameterJdbcTemplate.queryForList(sql, params, String.class);

        if (!ids.isEmpty()) {
            return ids.get(0); // Return the first retrieved ID
        } else {
            return null; // No matching record found
        }
    }


    public class InvalidDocumentIdException extends Exception {
        public InvalidDocumentIdException(String message) {
            super(message);
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
                throw new RuntimeException("API request failed with status code: " + response.getStatusLine().getStatusCode());
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

    private List<String> extractDocumentIds(MeasurementRequest measurementRegistrationRequest) {
        List<String> documentIds = new ArrayList<>();

        for (Measurement measurement : measurementRegistrationRequest.getMeasurements()) {
            for (Measure measure : measurement.getMeasures()) {
                for (Document document : measure.getDocuments()) {
                    documentIds.add(document.getFileStore());
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
        httpGet.addHeader("authority", "qa.digit.org");
        httpGet.addHeader("accept", "application/json, text/plain, */*");
        httpGet.addHeader("accept-language", "en-GB,en;q=0.9");
        httpGet.addHeader("cache-control", "no-cache");
        httpGet.addHeader("pragma", "no-cache");
        httpGet.addHeader("referer", "https://qa.digit.org/digit-ui/employee/dss/dashboard/fsm");
        httpGet.addHeader("sec-ch-ua", "\"Google Chrome\";v=\"107\", \"Chromium\";v=\"107\", \"Not=A?Brand\";v=\"24\"");
        httpGet.addHeader("sec-ch-ua-mobile", "?0");
        httpGet.addHeader("sec-ch-ua-platform", "\"Windows\"");
        httpGet.addHeader("sec-fetch-dest", "empty");
        httpGet.addHeader("sec-fetch-mode", "cors");
        httpGet.addHeader("sec-fetch-site", "same-origin");
        httpGet.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36");

        return httpGet;
    }

    private final ServiceRequestRepository serviceRequestRepository;

    @Autowired
    public MeasurementService(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    public List<Measurement> searchMeasurements(MeasurementCriteria searchCriteria) {

        List<Measurement> measurements = serviceRequestRepository.getMeasurements(searchCriteria);
        return measurements;
    }

}
