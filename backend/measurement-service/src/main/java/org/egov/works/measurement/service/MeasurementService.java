package org.egov.works.measurement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.Workflow;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.measurement.enrichment.MeasurementEnrichment;
import org.egov.works.measurement.kafka.Producer;
import org.egov.works.measurement.repository.rowmapper.MeasurementRowMapper;
import org.egov.works.measurement.repository.rowmapper.MeasurementServiceRowMapper;
import org.egov.works.measurement.util.*;
import org.egov.works.measurement.validator.MeasurementServiceValidator;
import org.egov.works.measurement.validator.MeasurementValidator;
import org.egov.works.measurement.web.models.Document;
import org.egov.works.measurement.web.models.Measure;
import org.egov.works.measurement.web.models.Measurement;
import org.egov.works.measurement.web.models.MeasurementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.egov.works.measurement.web.models.Pagination;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import org.egov.tracer.model.CustomException;
import org.egov.works.measurement.config.Configuration;

import org.egov.works.measurement.kafka.Producer;
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
    private WorkflowService workflowService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private MeasurementServiceValidator validator;
    @Autowired
    private MeasurementRowMapper rowMapper;

    @Autowired
    private MeasurementEnrichment enrichmentUtil;

    @Autowired
    private WorkflowUtil workflowUtil;

    @Autowired
    private ContractUtil contractUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    @Autowired
    private ResponseInfoFactory responseInfoFactory;
    @Autowired
    private MeasurementValidator measurementValidator;


    /**
     * Handles measurement create
     * @param request
     * @return
     */
    public ResponseEntity<MeasurementResponse> createMeasurement(MeasurementRequest request){
        // Just validate tenant id from idGen
        measurementValidator.validateTenantId(request);
        validator.validateDocumentIds(request.getMeasurements());
        // enrich measurements
        enrichMeasurement(request);
        MeasurementResponse response = MeasurementResponse.builder().responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(),true)).measurements(request.getMeasurements()).build();

        // push to kafka topic
        producer.push(configuration.getCreateMeasurementTopic(),request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    /**
     * Handles measurement update
     * @param measurementRegistrationRequest
     * @return
     */
    public ResponseEntity<MeasurementResponse> updateMeasurement(MeasurementRequest measurementRegistrationRequest) {
        // Just validate tenant id
        measurementValidator.validateTenantId(measurementRegistrationRequest);

        //Validate document IDs from the measurement request
        validator.validateDocumentIds(measurementRegistrationRequest.getMeasurements());

        // Validate existing data and set audit details
        validator.validateExistingDataAndEnrich(measurementRegistrationRequest);

        //Updating Cummulative Value
        handleCumulativeUpdate(measurementRegistrationRequest);


        // Create the MeasurementResponse object
        MeasurementResponse response = makeUpdateResponse(measurementRegistrationRequest.getMeasurements(),measurementRegistrationRequest);

        // Push the response to the producer
        producer.push(configuration.getUpdateTopic(), response);

        // Return the success response
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

     public List<Measurement> searchMeasurements(MeasurementCriteria searchCriteria, MeasurementSearchRequest measurementSearchRequest) {

        if (searchCriteria == null || StringUtils.isEmpty(searchCriteria.getTenantId())) {
            throw new IllegalArgumentException("TenantId is required.");
        }
        List<Measurement> measurements = serviceRequestRepository.getMeasurements(searchCriteria, measurementSearchRequest);
        return measurements;
    }

    public List<org.egov.works.measurement.web.models.MeasurementService> changeToMeasurementService(List<Measurement> measurements) {
        List<org.egov.works.measurement.web.models.MeasurementService> measurementServices = new ArrayList<>();

        for (Measurement measurement : measurements) {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(jdbcTemplate);
            org.egov.works.measurement.web.models.MeasurementService measurementService1=serviceRequestRepository.getMeasurementServiceFromMBSTable(namedParameterJdbcTemplate,measurement.getMeasurementNumber());
            org.egov.works.measurement.web.models.MeasurementService measurementService = new org.egov.works.measurement.web.models.MeasurementService();
            measurementService.setId(measurement.getId());
            measurementService.setTenantId(measurement.getTenantId());
            measurementService.setMeasurementNumber(measurement.getMeasurementNumber());
            measurementService.setPhysicalRefNumber(measurement.getPhysicalRefNumber());
            measurementService.setReferenceId(measurement.getReferenceId());
            measurementService.setEntryDate(measurement.getEntryDate());
            measurementService.setMeasures(measurement.getMeasures());
            measurementService.setDocuments(measurement.getDocuments());
            measurementService.setIsActive(measurement.getIsActive());
            measurementService.setAuditDetails(measurement.getAuditDetails());

            measurementService.setWfStatus(measurementService1.getWfStatus());

            measurementServices.add(measurementService);
        }
        return measurementServices;
    }

    public  void handleCumulativeUpdate(MeasurementRequest measurementRequest){
        for(Measurement measurement:measurementRequest.getMeasurements()){
            try {
                enrichCumulativeValueOnUpdate(measurement);
            }
            catch (Exception  e){
                throw new CustomException("","Error during Cumulative enrichment");
            }
        }
    }

    public MeasurementResponse makeUpdateResponse(List<Measurement> measurements,MeasurementRequest measurementRegistrationRequest) {
        MeasurementResponse response = new MeasurementResponse();
        response.setResponseInfo(ResponseInfo.builder()
                .apiId(measurementRegistrationRequest.getRequestInfo().getApiId())
                .msgId(measurementRegistrationRequest.getRequestInfo().getMsgId())
                .ts(measurementRegistrationRequest.getRequestInfo().getTs())
                .status("successful")
                .build());
        response.setMeasurements(measurements);
        return response;
    }

    public MeasurementServiceResponse makeSearchResponse(MeasurementSearchRequest measurementSearchRequest) {
        MeasurementServiceResponse response = new MeasurementServiceResponse();
        response.setResponseInfo(ResponseInfo.builder()
                .apiId(measurementSearchRequest.getRequestInfo().getApiId())
                .msgId(measurementSearchRequest.getRequestInfo().getMsgId())
                .ts(measurementSearchRequest.getRequestInfo().getTs())
                .status("successful")
                .build());
        return response;
    }


    /**
     * Helper function to enrich a measurement
     * @param request
     */
    public void enrichMeasurement(MeasurementRequest request){

        String tenantId = request.getMeasurements().get(0).getTenantId(); // each measurement should have same tenantId otherwise this will fail
        List<String> measurementNumberList = idgenUtil.getIdList(request.getRequestInfo(), tenantId, configuration.getIdName(), configuration.getIdFormat(), request.getMeasurements().size());
        List<Measurement> measurements = request.getMeasurements();
//        enrichCumulativeValue(request);
        for (int i = 0; i < measurements.size(); i++) {
            Measurement measurement = measurements.get(i);

            // enrich UUID
            measurement.setId(UUID.randomUUID());
            // enrich the Audit details
            measurement.setAuditDetails(AuditDetails.builder()
                    .createdBy(request.getRequestInfo().getUserInfo().getUuid())
                    .createdTime(System.currentTimeMillis())
                    .lastModifiedTime(System.currentTimeMillis())
                    .build());

            // enrich measures in a measurement
            enrichMeasures(measurement);
            // enrich IdGen
            measurement.setMeasurementNumber(measurementNumberList.get(i));
            // enrich Cumulative value
            try {
                enrichCumulativeValue(measurement);
            }
            catch (Exception e){
                throw new CustomException("","Error during Cumulative enrichment");
            }
            // measurement.setMeasurementNumber("DEMO_ID_TILL_MDMS_DOWN");  // for local-dev remove this
        }
    }
    public void enrichCumulativeValue(Measurement measurement){
        MeasurementCriteria measurementCriteria = MeasurementCriteria.builder()
                                                  .referenceId(Collections.singletonList(measurement.getReferenceId()))
                                                  .tenantId(measurement.getTenantId())
                                                  .build();
        Pagination pagination= Pagination.builder().offSet((double) 0).build();
        MeasurementSearchRequest measurementSearchRequest=MeasurementSearchRequest.builder().criteria(measurementCriteria).pagination(pagination).build();
        List<Measurement> measurementList = searchMeasurements(measurementCriteria,measurementSearchRequest);
        if(!measurementList.isEmpty()){
            Measurement latestMeasurement = measurementList.get(0);
            calculateCumulativeValue(latestMeasurement,measurement);
        }
        else{
            for(Measure measure : measurement.getMeasures()){
                measure.setCumulativeValue(measure.getCurrentValue());
            }
        }
    }

    public void enrichCumulativeValueOnUpdate(Measurement measurement){
        MeasurementCriteria measurementCriteria = MeasurementCriteria.builder()
                .referenceId(Collections.singletonList(measurement.getReferenceId()))
                .tenantId(measurement.getTenantId())
                .build();
        Pagination pagination= Pagination.builder().offSet((double) 0).build();
        MeasurementSearchRequest measurementSearchRequest=MeasurementSearchRequest.builder().criteria(measurementCriteria).pagination(pagination).build();
        List<Measurement> measurementList =searchMeasurements(measurementCriteria,measurementSearchRequest);
        if(!measurementList.isEmpty()){
            Measurement latestMeasurement = measurementList.get(0);
            calculateCumulativeValueOnUpdate(latestMeasurement,measurement);
        }
        else{
            for(Measure measure : measurement.getMeasures()){
                measure.setCumulativeValue(measure.getCurrentValue());
            }
        }
    }

    public void calculateCumulativeValue(Measurement latestMeasurement,Measurement currMeasurement){
        Map<String,BigDecimal> targetIdtoCumulativeMap = new HashMap<>();
        for(Measure measure:latestMeasurement.getMeasures()){
            targetIdtoCumulativeMap.put(measure.getTargetId(),measure.getCumulativeValue());
        }
        for(Measure measure:currMeasurement.getMeasures()){
            measure.setCumulativeValue( targetIdtoCumulativeMap.get(measure.getTargetId()).add(measure.getCurrentValue()));
        }
    }
    public void calculateCumulativeValueOnUpdate(Measurement latestMeasurement,Measurement currMeasurement){
        Map<String,BigDecimal> targetIdtoCumulativeMap = new HashMap<>();
        for(Measure measure:latestMeasurement.getMeasures()){
            targetIdtoCumulativeMap.put(measure.getTargetId(),measure.getCumulativeValue().subtract(measure.getCurrentValue()));
        }
        for(Measure measure:currMeasurement.getMeasures()){
            measure.setCumulativeValue( targetIdtoCumulativeMap.get(measure.getTargetId()).add(measure.getCurrentValue()));
        }
    }
    /**
     * Helper function to enriches a measure
     * @param measurement
     */
    public void enrichMeasures(Measurement measurement){
        List<Measure> measureList = measurement.getMeasures();
        if(measurement.getDocuments()!=null){
            for(Document document:measurement.getDocuments()){
                document.setId(UUID.randomUUID().toString());
            }
        }
        for (Measure measure : measureList) {
            measure.setId(UUID.randomUUID());
            measure.setReferenceId(measurement.getId().toString());
            measure.setAuditDetails(measurement.getAuditDetails());
            measure.setCurrentValue(measure.getLength().multiply(measure.getHeight().multiply(measure.getBreadth().multiply(measure.getNumItems()))));
        }
    }
}
