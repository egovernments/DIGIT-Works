package org.egov.works.measurement.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.measurement.config.ErrorConfiguration;
import org.egov.works.measurement.enrichment.MeasurementEnrichment;
import org.egov.works.measurement.kafka.Producer;
import org.egov.works.measurement.repository.rowmapper.MeasurementRowMapper;
import org.egov.works.measurement.util.*;
import org.egov.works.measurement.validator.MeasurementServiceValidator;
import org.egov.works.measurement.validator.MeasurementValidator;
import org.egov.works.measurement.web.models.Document;
import org.egov.works.measurement.web.models.Measure;
import org.egov.works.measurement.web.models.Measurement;
import org.egov.works.measurement.web.models.MeasurementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.egov.works.measurement.web.models.Pagination;

import java.math.BigDecimal;
import java.util.*;
import org.egov.tracer.model.CustomException;
import org.egov.works.measurement.config.Configuration;

import org.egov.works.measurement.util.MdmsUtil;
import org.egov.works.measurement.web.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.egov.works.measurement.repository.ServiceRequestRepository;
import org.egov.works.measurement.web.models.MeasurementCriteria;


@Service
@Slf4j
public class MeasurementService {
    @Autowired
    private Producer producer;
    @Autowired
    private Configuration configuration;
    @Autowired
    private MeasurementServiceValidator serviceValidator;
    @Autowired
    private ErrorConfiguration errorConfigs;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    @Autowired
    private ResponseInfoFactory responseInfoFactory;
    @Autowired
    private MeasurementValidator measurementValidator;
    @Autowired
    private MeasurementRegistryUtil measurementRegistryUtil;

    /**
     * Handles measurement create
     * @param request
     * @return
     */
    public ResponseEntity<MeasurementResponse> createMeasurement(MeasurementRequest request){

        //  validate tenant id
        measurementValidator.validateTenantId(request);
        // validate documents ids if present
        serviceValidator.validateDocumentIds(request.getMeasurements());
        // enrich measurements
        measurementRegistryUtil.enrichMeasurement(request);
        // create a response
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
    public MeasurementResponse updateMeasurement(MeasurementRequest measurementRegistrationRequest) {
        // Just validate tenant id
        measurementValidator.validateTenantId(measurementRegistrationRequest);

        //Validate document IDs from the measurement request
        serviceValidator.validateDocumentIds(measurementRegistrationRequest.getMeasurements());

        // Validate existing data and set audit details
        serviceValidator.validateExistingDataAndEnrich(measurementRegistrationRequest);

        //Updating Cumulative Value
        measurementRegistryUtil.handleCumulativeUpdate(measurementRegistrationRequest);

        // Create the MeasurementResponse object
        MeasurementResponse response = measurementRegistryUtil.makeUpdateResponse(measurementRegistrationRequest.getMeasurements(),measurementRegistrationRequest);

        // Push the response to the producer
        producer.push(configuration.getUpdateTopic(), response);

        // Return the success response
        return response;
    }


    /**
     * Handles search measurements
     */
     public List<Measurement> searchMeasurements(MeasurementCriteria searchCriteria, MeasurementSearchRequest measurementSearchRequest) {

         handleNullPagination(measurementSearchRequest);
        if (StringUtils.isEmpty(searchCriteria.getTenantId()) || searchCriteria == null) {
            throw errorConfigs.tenantIdRequired;
        }
        List<Measurement> measurements = serviceRequestRepository.getMeasurements(searchCriteria, measurementSearchRequest);
        return measurements;
    }


    /**
     converting all measurement registry to measurement-Service
     */
    public List<org.egov.works.measurement.web.models.MeasurementService> changeToMeasurementService(List<Measurement> measurements) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<String> mbNumbers = getMbNumbers(measurements);
        List<org.egov.works.measurement.web.models.MeasurementService> measurementServices = serviceRequestRepository.getMeasurementServicesFromMBSTable(namedParameterJdbcTemplate, mbNumbers);
        Map<String, org.egov.works.measurement.web.models.MeasurementService> mbNumberToServiceMap = getMbNumberToServiceMap(measurementServices);
        List<org.egov.works.measurement.web.models.MeasurementService> orderedExistingMeasurementService = serviceValidator.createOrderedMeasurementServiceList(mbNumbers, mbNumberToServiceMap);

        // Create measurement services for each measurement
        List<org.egov.works.measurement.web.models.MeasurementService> result = createMeasurementServices(measurements, orderedExistingMeasurementService);

        return result;
    }

    private List<org.egov.works.measurement.web.models.MeasurementService> createMeasurementServices(List<Measurement> measurements, List<org.egov.works.measurement.web.models.MeasurementService> orderedExistingMeasurementService) {
        List<org.egov.works.measurement.web.models.MeasurementService> measurementServices = new ArrayList<>();

        for (int i = 0; i < measurements.size(); i++) {
            Measurement measurement = measurements.get(i);
            org.egov.works.measurement.web.models.MeasurementService measurementService = new org.egov.works.measurement.web.models.MeasurementService();

            // Set properties of the measurement service based on the measurement object
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

            // If an existing measurement service exists, set its workflow status
            org.egov.works.measurement.web.models.MeasurementService existingMeasurementService = orderedExistingMeasurementService.get(i);
            if (existingMeasurementService != null) {
                measurementService.setWfStatus(existingMeasurementService.getWfStatus());
            }

            measurementServices.add(measurementService);
        }

        return measurementServices;
    }

    private void handleNullPagination(MeasurementSearchRequest body){
        if (body.getPagination() == null) {
            body.setPagination(new Pagination());
            body.getPagination().setLimit(null);
            body.getPagination().setOffSet(null);
            body.getPagination().setOrder(Pagination.OrderEnum.DESC);
        }
    }

    public MeasurementResponse createSearchResponse(MeasurementSearchRequest body){
        MeasurementResponse response = new MeasurementResponse();
        response.setResponseInfo(ResponseInfo.builder()
                .apiId(body.getRequestInfo().getApiId())
                .msgId(body.getRequestInfo().getMsgId())
                .ts(body.getRequestInfo().getTs())
                .status("successful")
                .build());
        return response;
    }

    public  List<String> getMbNumbers(List<Measurement> measurements){
        List<String> mbNumbers=new ArrayList<>();
        for(Measurement measurement:measurements){
            mbNumbers.add(measurement.getMeasurementNumber());
        }
        return mbNumbers;
    }

    public Map<String, org.egov.works.measurement.web.models.MeasurementService> getMbNumberToServiceMap(List<org.egov.works.measurement.web.models.MeasurementService> measurementServices){
        Map<String, org.egov.works.measurement.web.models.MeasurementService> mbNumberToServiceMap = new HashMap<>();
        for (org.egov.works.measurement.web.models.MeasurementService existingService : measurementServices) {
            mbNumberToServiceMap.put(existingService.getMeasurementNumber(), existingService);
        }
        return mbNumberToServiceMap;
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

}
