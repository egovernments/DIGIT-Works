package org.egov.works.measurement.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.measurement.config.Configuration;
import org.egov.works.measurement.kafka.Producer;
import org.egov.works.measurement.util.ContractUtil;
import org.egov.works.measurement.util.IdgenUtil;
import org.egov.works.measurement.util.MeasurementServiceUtil;
import org.egov.works.measurement.util.ResponseInfoFactory;
import org.egov.works.measurement.validator.MeasurementServiceValidator;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class MeasurementService {

    @Autowired
    private ContractUtil contractUtil;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private MeasurementServiceValidator measurementServiceValidator;
    @Autowired
    private ResponseInfoFactory responseInfoFactory;
    @Autowired
    private Producer producer;
    @Autowired
    private Configuration configuration;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MeasurementRegistry measurementRegistry;
    @Autowired
    private IdgenUtil idgenUtil;
    @Autowired
    private MeasurementServiceUtil measurementServiceUtil;

    /**
     * Handles create MeasurementRegistry
     *
     * @param body
     * @return
     */
    public MeasurementServiceResponse handleCreateMeasurementService(MeasurementServiceRequest body) {

        // Validate document IDs from the measurement service request
        measurementServiceValidator.validateDocumentIds(body.getMeasurements());
        // validate contracts
        measurementServiceValidator.validateContracts(body);

        // Convert to Measurement Registry
        List<Measurement> measurementList = measurementServiceUtil.convertToMeasurementList(body.getMeasurements());
        MeasurementRequest measurementRegistryRequest = MeasurementRequest.builder().requestInfo(body.getRequestInfo()).measurements(measurementList).build();
        ResponseEntity<MeasurementResponse> measurementResponse =  measurementRegistry.createMeasurement(measurementRegistryRequest);

        // Convert back into Measurement Service
        List<org.egov.works.measurement.web.models.MeasurementService> measurementServiceList = measurementServiceUtil.convertToMeasurementServiceList(body, Objects.requireNonNull(measurementResponse.getBody()).getMeasurements());
        body.setMeasurements(measurementServiceList);

        //  update WF
        List<String> wfStatusList = workflowService.updateWorkflowStatuses(body);
        measurementServiceUtil.enrichWf(body,wfStatusList);

        // Create response
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        MeasurementServiceResponse measurementServiceResponse = MeasurementServiceResponse.builder().responseInfo(responseInfo).measurements(body.getMeasurements()).build();

        // push to kafka
        producer.push(configuration.getMeasurementServiceCreateTopic(), body);
        return measurementServiceResponse;

    }

    /**
     * Handles update MeasurementRegistry
     *
     * @param measurementServiceRequest
     * @return
     */
    public MeasurementServiceResponse updateMeasurementService(MeasurementServiceRequest measurementServiceRequest) {

        // Validate existing data and set audit details
        measurementServiceValidator.validateExistingServiceDataAndEnrich(measurementServiceRequest);

        // Validate contracts for each measurement
        measurementServiceValidator.validateContracts(measurementServiceRequest);

        // Update measurements
        MeasurementResponse measurementResponse= measurementServiceUtil.updateMeasurementAndGetResponse(measurementServiceRequest);
        measurementServiceRequest.setMeasurements(measurementServiceUtil.convertToMeasurementServiceList(measurementServiceRequest,measurementResponse.getMeasurements()));

        // Update & enrich workflow statuses for each measurement service
        List<String> wfStatusList = workflowService.updateWorkflowStatuses(measurementServiceRequest);
        measurementServiceUtil.enrichMeasurementServiceUpdate(measurementServiceRequest,wfStatusList);

        // Create a MeasurementServiceResponse
        MeasurementServiceResponse response = measurementServiceUtil.makeUpdateResponseService(measurementServiceRequest);

        // Push the response to the service update topic
        producer.push(configuration.getServiceUpdateTopic(), response);

        // Return the response as a ResponseEntity with HTTP status NOT_IMPLEMENTED
        return response;
    }

}
