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
import org.egov.works.measurement.repository.rowmapper.MeasurementRowMapper;
import org.egov.works.measurement.util.MdmsUtil;
import org.egov.works.measurement.validator.MeasurementServiceValidator;
import org.egov.works.measurement.web.models.Document;
import org.egov.works.measurement.web.models.Measure;
import org.egov.works.measurement.web.models.Measurement;
import org.egov.works.measurement.web.models.MeasurementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
    private MeasurementRowMapper rowMapper;

    @Autowired
    private MeasurementEnrichment enrichmentUtil;

    @Autowired
    private ContractUtil contractUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    public ResponseEntity<MeasurementResponse> updateMeasurement(MeasurementRequest measurementRegistrationRequest) {
        // Validate document IDs from the measurement request
        validator.validateDocumentIds(measurementRegistrationRequest);

        // Validate existing data and set audit details
        validator.validateExistingDataAndSetAuditDetails(measurementRegistrationRequest);

        // Create the MeasurementResponse object
        MeasurementResponse response = makeUpdateResponse(measurementRegistrationRequest);

        // Push the response to the producer
        producer.push(updateTopic, response);

        // Return the success response
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    private MeasurementResponse makeUpdateResponse(MeasurementRequest measurementRegistrationRequest) {
        MeasurementResponse response = new MeasurementResponse();
        response.setResponseInfo(ResponseInfo.builder()
                .apiId(measurementRegistrationRequest.getRequestInfo().getApiId())
                .msgId(measurementRegistrationRequest.getRequestInfo().getMsgId())
                .ts(measurementRegistrationRequest.getRequestInfo().getTs())
                .build());
        response.setMeasurements(measurementRegistrationRequest.getMeasurements());
        return response;
    }

    public ResponseEntity<MeasurementServiceResponse> updateMeasurementService(MeasurementServiceRequest measurementServiceRequest){
        MeasurementServiceResponse response=new MeasurementServiceResponse();
        return new ResponseEntity<>(response,HttpStatus.NOT_IMPLEMENTED);
    }


    private final ServiceRequestRepository serviceRequestRepository;

    @Autowired
    public MeasurementService(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    public List<Measurement> searchMeasurements(MeasurementCriteria searchCriteria) {
        // You can perform any necessary validation of the search criteria here.


        List<Measurement> measurements = serviceRequestRepository.getMeasurements(searchCriteria);
        // Call the repository to get the measurements based on the search criteria.
        return measurements;
    }
}
