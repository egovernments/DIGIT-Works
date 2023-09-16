package org.egov.works.measurement.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.egov.works.measurement.repository.rowmapper.MeasureUpdateRowMapper;
import org.egov.works.measurement.repository.rowmapper.MeasurementUpdateRowMapper;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class MeasurementServiceValidator {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Value("${egov.filestore.host}")
    private String baseFilestoreUrl;

    @Value("${egov.filestore.endpoint}")
    private String baseFilestoreEndpoint;

    public void validateDocumentIds(MeasurementRequest measurementRegistrationRequest) {
        // Extract document IDs from the measurement request
        List<String> documentIds = extractDocumentIds(measurementRegistrationRequest);

        // Make an API request to retrieve file store IDs
        String responseJson = makeApiRequest(documentIds);

        // Parse the API response and check if all document IDs were found
        boolean allIdsMatched = checkDocumentIdsMatch(documentIds, responseJson);
        if (!allIdsMatched) {
            throw new RuntimeException("Files are invalid");
        }
    }

    public void validateExistingDataAndSetAuditDetails(MeasurementRequest measurementRegistrationRequest) {
        Ids idsToCheck = getAllIds(measurementRegistrationRequest);
        List<String> measurementIds = idsToCheck.getMeasurementIds();
        List<String> measureIds = idsToCheck.getMeasureIds();
        List<Measurement> measurementExisting = validateMeasurementRequest(measurementIds);
        List<Measure> measureExisting = validateMeasureRequest(measureIds);

        // Perform the measurement update (if filestore validate successfully)
        if (measurementExisting.size() != measurementIds.size() || measureExisting.size() != measureIds.size()) {
            throw new RuntimeException("Data does not exist");
        }
        setAuditDetails(measurementExisting, measurementRegistrationRequest);
    }

    public void validateExistingServiceDataAndSetAuditDetails(MeasurementServiceRequest measurementServiceRequest) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        ServiceIds idsToCheck = getAllServiceIds(measurementServiceRequest);
        for (String id : idsToCheck.getIds()) {
            String mbNumber = idsToCheck.getMeasurementNumbers().get(idsToCheck.getIds().indexOf(id));

            // Check if measurements exist in eg_mb_measurements
            Measurement measurementInMBTable = getMeasurementFromMBTable(namedParameterJdbcTemplate, id, mbNumber);

            // Check if measurements exist in eg_mbs_measurements
            MeasurementService measurementServiceInMBSTable = getMeasurementServiceFromMBSTable(namedParameterJdbcTemplate, mbNumber);

            if (measurementInMBTable == null && measurementServiceInMBSTable == null) {
                throw new RuntimeException("Data does not exist");
            }
        }
    }

    public Measurement getMeasurementFromMBTable(NamedParameterJdbcTemplate jdbcTemplate, String id, String mbNumber) {
        String sql = "SELECT * FROM eg_mb_measurements WHERE id = :id AND mbNumber = :mbNumber";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("mbNumber", mbNumber);

        try {
            return jdbcTemplate.queryForObject(sql, params, new MeasurementUpdateRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null; // Measurement does not exist
        }
    }

    public MeasurementService getMeasurementServiceFromMBSTable(NamedParameterJdbcTemplate jdbcTemplate, String mbNumber) {
        String sql = "SELECT * FROM eg_mbs_measurements WHERE mbNumber = :mbNumber";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("mbNumber", mbNumber);

        try {
            return jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                MeasurementService measurementService = new MeasurementService();
                measurementService.setId(UUID.fromString(rs.getString("id")));
                measurementService.setTenantId(rs.getString("tenantId"));
                measurementService.setMeasurementNumber(rs.getString("mbNumber"));
                measurementService.setWfStatus(rs.getString("wfStatus"));
                // Set other properties as needed
                return measurementService;
            });
        } catch (EmptyResultDataAccessException e) {
            return null; // MeasurementService does not exist
        }
    }



    public void setAuditDetails(List<Measurement> measurementExisting,MeasurementRequest measurementRequest){
        List<Measurement> measurements=measurementRequest.getMeasurements();
        for(int i=0;i<measurements.size();i++){
            measurements.get(i).setAuditDetails(measurementExisting.get(0).getAuditDetails());
            measurements.get(i).getAuditDetails().setLastModifiedBy(measurementRequest.getRequestInfo().getUserInfo().getUuid());
            measurements.get(i).getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
        }
        measurementRequest.setMeasurements(measurements);
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
    public Ids getAllIds(MeasurementRequest measurementRequest) {
        Ids ids = new Ids();

        for (Measurement measurement : measurementRequest.getMeasurements()) {
            String measurementIdAsString = measurement.getId().toString(); // Convert UUID to string
            ids.getMeasurementIds().add(measurementIdAsString);

            for (Measure measure : measurement.getMeasures()) {
                String measureIdAsString = measure.getId().toString(); // Convert UUID to string
                ids.getMeasureIds().add(measureIdAsString);
            }
        }

        return ids;
    }

    public ServiceIds getAllServiceIds(MeasurementServiceRequest measurementServiceRequest) {
        ServiceIds serviceIds = new ServiceIds();
        List<String> ids = new ArrayList<>();
        List<String> measurementNumbers = new ArrayList<>();

        for (MeasurementService measurementService : measurementServiceRequest.getMeasurements()) {
            ids.add(measurementService.getId().toString());
            measurementNumbers.add(measurementService.getMeasurementNumber());
        }

        serviceIds.setIds(ids);
        serviceIds.setMeasurementNumbers(measurementNumbers);

        return serviceIds;
    }

    public List<Measurement> validateMeasurementRequest(List<String> idsToCheck) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<Measurement> existingMeasurements = new ArrayList<>();

        for (String idToCheck : idsToCheck) {
            // Fetch the Measurement object with the specified ID from the database
            Measurement existingMeasurement = getMeasurementById(namedParameterJdbcTemplate, idToCheck);

            // If a Measurement object exists, add it to the list
            if (existingMeasurement != null) {
                existingMeasurements.add(existingMeasurement);
            }
        }

        return existingMeasurements;
    }

    public List<Measure> validateMeasureRequest(List<String> idsToCheck) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<Measure> existingMeasures = new ArrayList<>();

        for (String idToCheck : idsToCheck) {
            // Fetch the Measurement object with the specified ID from the database
            Measure existingMeasure = getMeasureById(namedParameterJdbcTemplate, idToCheck);

            // If a Measurement object exists, add it to the list
            if (existingMeasure != null) {
                existingMeasures.add(existingMeasure);
            }
        }

        return existingMeasures;
    }


    public Measurement getMeasurementById(NamedParameterJdbcTemplate jdbcTemplate, String id) {
        String sql = "SELECT * FROM eg_mb_measurements WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id.toString());

        return jdbcTemplate.queryForObject(sql, params, new MeasurementUpdateRowMapper());
    }


    public Measure getMeasureById(NamedParameterJdbcTemplate jdbcTemplate, String id) {
        String sql = "SELECT * FROM eg_mb_measurement_measures WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return jdbcTemplate.queryForObject(sql, params, new MeasureUpdateRowMapper());
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

}
