package org.egov.works.measurement.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.measurement.config.MBServiceConfiguration;
import org.egov.works.measurement.kafka.MBServiceProducer;
import org.egov.works.measurement.util.MeasurementRegistryUtil;
import org.egov.works.measurement.util.MeasurementServiceUtil;
import org.egov.works.measurement.util.ResponseInfoFactory;
import org.egov.works.measurement.validator.MeasurementServiceValidator;
import org.egov.works.measurement.web.models.MeasurementResponse;
import org.egov.works.measurement.web.models.MeasurementSearchRequest;
import org.egov.works.measurement.web.models.MeasurementServiceRequest;
import org.egov.works.measurement.web.models.MeasurementServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class MeasurementService {

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MeasurementServiceValidator measurementServiceValidator;
    @Autowired
    private ResponseInfoFactory responseInfoFactory;
    @Autowired
    private MBServiceProducer MBServiceProducer;
    @Autowired
    private MBServiceConfiguration MBServiceConfiguration;
    @Autowired
    private MeasurementRegistry measurementRegistry;
    @Autowired
    private MeasurementServiceUtil measurementServiceUtil;
    @Autowired
    private MeasurementRegistryUtil measurementRegistryUtil;
    @Autowired
    private NotificationService notificationService;

    /**
     * Handles create MeasurementRegistry
     *
     * @param body
     * @return
     */
    public MeasurementServiceResponse handleCreateMeasurementService(MeasurementServiceRequest body) {
        // validate tenants
        measurementServiceValidator.validateTenantId(body);
        // validate contracts
        measurementServiceValidator.validateContracts(body);
        // validate workflow
        measurementServiceValidator.validateWorkflowForCreate(body);
        //Create Measurement via Measurement Registry create api
        ResponseEntity<MeasurementResponse> measurementResponse = measurementRegistryUtil.createMeasurements(body);

        // Convert back into Measurement Service
        List<org.egov.works.measurement.web.models.MeasurementService> measurementServiceList = measurementServiceUtil.convertToMeasurementServiceList(body, Objects.requireNonNull(measurementResponse.getBody()).getMeasurements());
        body.setMeasurements(measurementServiceList);

        //  update WF
        measurementServiceUtil.updateWorkflowDuringCreate(body);

        // Create response
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        MeasurementServiceResponse measurementServiceResponse = MeasurementServiceResponse.builder().responseInfo(responseInfo).measurements(body.getMeasurements()).build();

        // push to kafka
        MBServiceProducer.push(MBServiceConfiguration.getMeasurementServiceCreateTopic(), body);
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
        measurementServiceValidator.validateContractsOnUpdate(measurementServiceRequest);
        // validate workflow
        measurementServiceValidator.validateWorkflowForUpdate(measurementServiceRequest);
        //Update Measurement via Measurement Registry update api
        ResponseEntity<MeasurementResponse> measurementResponse = measurementRegistryUtil.updateMeasurements(measurementServiceRequest);

        // Convert back into Measurement Service
        measurementServiceRequest.setMeasurements(measurementServiceUtil.convertToMeasurementServiceList(measurementServiceRequest,measurementResponse.getBody().getMeasurements()));

        // Update & enrich workflow statuses for each measurement service
        measurementServiceUtil.updateWorkflow(measurementServiceRequest);

        //Send notification
        try {
            notificationService.sendNotification(measurementServiceRequest);
        }catch (Exception e) {
            log.error("Exception while sending notification: " + e);
        }

        // Create a MeasurementServiceResponse
        MeasurementServiceResponse response = measurementServiceUtil.makeUpdateResponseService(measurementServiceRequest);

        // Push the response to the service update topic
        MBServiceProducer.push(MBServiceConfiguration.getServiceUpdateTopic(), measurementServiceRequest);

        return response;
    }
    public MeasurementServiceResponse searchMeasurementService(MeasurementSearchRequest body) {
        // Perform the search for measurements via Measurement Registry search API
        ResponseEntity<MeasurementResponse> responseEntity = measurementRegistryUtil.searchMeasurements(body);
        MeasurementResponse measurementResponse = responseEntity.getBody();

        // Convert the MeasurementResponse into a MeasurementServiceResponse
        MeasurementServiceResponse measurementServiceResponse = measurementRegistry.makeSearchResponse(body);

        // Convert the measurements from Measurement objects to MeasurementService objects
        List<org.egov.works.measurement.web.models.MeasurementService> measurementServices = measurementRegistry.changeToMeasurementService(measurementResponse.getMeasurements());

        // Set the converted measurement services in the response
        measurementServiceResponse.setMeasurements(measurementServices);

        return measurementServiceResponse;
    }

}
