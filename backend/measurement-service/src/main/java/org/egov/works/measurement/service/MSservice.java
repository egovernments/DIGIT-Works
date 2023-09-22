package org.egov.works.measurement.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.measurement.config.Configuration;
import org.egov.works.measurement.kafka.Producer;
import org.egov.works.measurement.util.ContractUtil;
import org.egov.works.measurement.util.IdgenUtil;
import org.egov.works.measurement.util.ResponseInfoFactory;
import org.egov.works.measurement.validator.MeasurementServiceValidator;
import org.egov.works.measurement.web.models.*;
import org.egov.works.measurement.web.models.MeasurementService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class MSservice {

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
    private org.egov.works.measurement.service.MeasurementService measurementService;
    @Autowired
    private IdgenUtil idgenUtil;

    /**
     * Handles create MeasurementService
     *
     * @param body
     * @return
     */
    public MeasurementServiceResponse handleCreateMeasurementService(MeasurementServiceRequest body) {

        // Validate document IDs from the measurement service request
        measurementServiceValidator.validateDocumentIds(body.getMeasurements());
        // validate contracts
        measurementServiceValidator.validateContracts(body);

        List<Measurement> measurementList = new ArrayList<>();
        for(MeasurementService measurementService:body.getMeasurements()){
            Measurement measurement = Measurement.builder().id(measurementService.getId())
                    .tenantId(measurementService.getTenantId())
                    .measurementNumber(measurementService.getMeasurementNumber())
                    .physicalRefNumber(measurementService.getPhysicalRefNumber())
                    .referenceId(measurementService.getReferenceId())
                    .entryDate(measurementService.getEntryDate())
                    .measures(measurementService.getMeasures())
                    .isActive(measurementService.getIsActive())
                    .documents(measurementService.getDocuments())
                    .auditDetails(measurementService.getAuditDetails())
                    .additionalDetails(measurementService.getAdditionalDetails())
                    .build();
            measurementList.add(measurement);
        }

        MeasurementRequest measurementRegistryRequest = MeasurementRequest.builder().requestInfo(body.getRequestInfo()).measurements(measurementList).build();
        ResponseEntity<MeasurementResponse> measurementResponse =  measurementService.createMeasurement(measurementRegistryRequest);

        List<MeasurementService> measurementServiceList = convertToMeasurementServiceList(body, Objects.requireNonNull(measurementResponse.getBody()).getMeasurements());
        body.setMeasurements(measurementServiceList);

        //  update WF
        List<String> wfStatusList = workflowService.updateWorkflowStatuses(body);
        enrichWf(body,wfStatusList);

        // create response
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        MeasurementServiceResponse measurementServiceResponse = MeasurementServiceResponse.builder().responseInfo(responseInfo).measurements(body.getMeasurements()).build();

        // push to kafka
        producer.push(configuration.getMeasurementServiceCreateTopic(), body);
        return measurementServiceResponse;

    }


    /**
     * Helper to covert measurementService to measurement
     * @param measurementServices
     */
    public void measurementServiceToMeasurementConverter(List<MeasurementService> measurementServices){
        for(MeasurementService measurementService:measurementServices){
            Measurement measurement = Measurement.builder().id(measurementService.getId())
                    .tenantId(measurementService.getTenantId())
                    .measurementNumber(measurementService.getMeasurementNumber())
                    .physicalRefNumber(measurementService.getPhysicalRefNumber())
                    .referenceId(measurementService.getReferenceId())
                    .entryDate(measurementService.getEntryDate())
                    .measures(measurementService.getMeasures())
                    .isActive(measurementService.getIsActive())
                    .documents(measurementService.getDocuments())
                    .auditDetails(measurementService.getAuditDetails())
                    .additionalDetails(measurementService.getAdditionalDetails())
                    .measurementNumber(measurementService.getMeasurementNumber())
                    .build();
        }
    }
    public void enrichWf(MeasurementServiceRequest measurementServiceRequest , List<String> wfStatusList){
        List<MeasurementService> measurementServiceList = measurementServiceRequest.getMeasurements();
        for(int i=0;i<measurementServiceList.size();i++){
            measurementServiceList.get(i).setWfStatus(wfStatusList.get(i));                              // enrich wf status
            measurementServiceList.get(i).setWorkflow(measurementServiceList.get(i).getWorkflow());      // enrich the Workflow
        }
    }
    /**
     * Handles update MeasurementService
     *
     * @param measurementServiceRequest
     * @return
     */
    public ResponseEntity<MeasurementServiceResponse> updateMeasurementService(MeasurementServiceRequest measurementServiceRequest) {

        // Validate existing data and set audit details
        measurementServiceValidator.validateExistingServiceDataAndSetAuditDetails(measurementServiceRequest);

        // Validate contracts for each measurement
        measurementServiceValidator.validateContracts(measurementServiceRequest);

        // Update measurements
        MeasurementResponse measurementResponse=updateMeasurementAndGetResponse(measurementServiceRequest);
        measurementServiceRequest.setMeasurements(convertToMeasurementServiceList(measurementServiceRequest,measurementResponse.getMeasurements()));

        // Update workflow statuses for each measurement service and enrich
        List<String> wfStatusList = workflowService.updateWorkflowStatuses(measurementServiceRequest);
        enrichMeasurementServiceUpdate(measurementServiceRequest,wfStatusList);

        // Create a MeasurementServiceResponse
        MeasurementServiceResponse response = makeUpdateResponseService(measurementServiceRequest);

        // Push the response to the service update topic
        producer.push(configuration.getServiceUpdateTopic(), response);

        // Return the response as a ResponseEntity with HTTP status NOT_IMPLEMENTED
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    public MeasurementResponse updateMeasurementAndGetResponse(MeasurementServiceRequest measurementServiceRequest) {
        // Validate existing data and set audit details
        measurementServiceValidator.validateExistingServiceDataAndSetAuditDetails(measurementServiceRequest);

        // Validate contracts for each measurement
        measurementServiceValidator.validateContracts(measurementServiceRequest);

        // Convert MeasurementServiceRequest to MeasurementRequest
        MeasurementRequest measurementRequest = makeMeasurementUpdateRequest(measurementServiceRequest);

        // Call the updateMeasurement method to update measurements
        ResponseEntity<MeasurementResponse> responseEntity = measurementService.updateMeasurement(measurementRequest);

        // Check if the response status is OK (2xx)
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("Error in update measurement");
        }
    }


    public MeasurementRequest makeMeasurementUpdateRequest(MeasurementServiceRequest measurementServiceRequest) {
        MeasurementRequest measurementRequest = new MeasurementRequest();

        // Set the RequestInfo from MeasurementServiceRequest
        measurementRequest.setRequestInfo(measurementServiceRequest.getRequestInfo());

        // Set the Measurements from MeasurementServiceRequest
        measurementRequest.setMeasurements(convertToMeasurementList(measurementServiceRequest.getMeasurements()));

        return measurementRequest;
    }

    public List<Measurement> convertToMeasurementList(List<MeasurementService> measurementServices) {
        List<Measurement> measurements = new ArrayList<>();

        for (MeasurementService measurementService : measurementServices) {
            Measurement measurement = new Measurement();

            // Set the common properties
            measurement.setId(measurementService.getId());
            measurement.setTenantId(measurementService.getTenantId());
            measurement.setMeasurementNumber(measurementService.getMeasurementNumber());
            measurement.setPhysicalRefNumber(measurementService.getPhysicalRefNumber());
            measurement.setReferenceId(measurementService.getReferenceId());
            measurement.setEntryDate(measurementService.getEntryDate());
            measurement.setIsActive(measurementService.getIsActive());
            measurement.setDocuments(measurementService.getDocuments());
            measurement.setAuditDetails(measurementService.getAuditDetails());
            measurement.setAdditionalDetails(measurementService.getAdditionalDetails());

            // Set measures if available
            if (measurementService.getMeasures() != null) {
                measurement.setMeasures(measurementService.getMeasures());
            }

            measurements.add(measurement);
        }

        return measurements;
    }

    private List<MeasurementService> convertToMeasurementServiceList(MeasurementServiceRequest measurementServiceRequest,List<Measurement> measurements) {
        List<MeasurementService> measurementServiceList = new ArrayList<>();

        for(int i=0;i<measurements.size();i++){
            MeasurementService measurementService = new MeasurementService();
            BeanUtils.copyProperties(measurements.get(i), measurementService); // Copy common properties

            // Set wfStatus and workflow to null
            measurementService.setWfStatus(null);
            measurementService.setWorkflow(measurementServiceRequest.getMeasurements().get(i).getWorkflow());

            measurementServiceList.add(measurementService);
        }

        return measurementServiceList;
    }

    /**
     * Helper function for MeasurementService Update
     *
     * @param measurementServiceRequest
     * @return
     */
    public MeasurementServiceResponse makeUpdateResponseService(MeasurementServiceRequest measurementServiceRequest) {
        MeasurementServiceResponse response = new MeasurementServiceResponse();

        //setting totalValue
        for (Measurement measurement : measurementServiceRequest.getMeasurements()) {
            for (Measure measure : measurement.getMeasures()) {
                measure.setCurrentValue(measure.getLength().multiply(measure.getHeight().multiply(measure.getBreadth().multiply(measure.getNumItems()))));
            }
        }

        response.setResponseInfo(ResponseInfo.builder()
                .apiId(measurementServiceRequest.getRequestInfo().getApiId())
                .msgId(measurementServiceRequest.getRequestInfo().getMsgId())
                .ts(measurementServiceRequest.getRequestInfo().getTs())
                .build());

        response.setMeasurements(measurementServiceRequest.getMeasurements());

        return response;
    }

    /**
     * Helper function to enrich measurementservice
     *
     * @param body
     */
    public void enrichMeasurementService(MeasurementServiceRequest body) {

        String tenantId = body.getMeasurements().get(0).getTenantId();
        List<String> measurementNumberList = idgenUtil.getIdList(body.getRequestInfo(), tenantId, configuration.getIdName(), configuration.getIdFormat(), body.getMeasurements().size());

        List<MeasurementService> measurementServiceList = body.getMeasurements();
        RequestInfo requestInfo = body.getRequestInfo();
        for (int i = 0; i < measurementServiceList.size(); i++) {
            // create an audit details
            AuditDetails auditDetails = (AuditDetails.builder().createdBy(requestInfo.getUserInfo().getUuid()).createdTime(System.currentTimeMillis()).lastModifiedBy(requestInfo.getUserInfo().getUuid()).lastModifiedTime(System.currentTimeMillis()).build());
            measurementServiceList.get(i).setId(UUID.randomUUID());
            measurementServiceList.get(i).setMeasurementNumber(measurementNumberList.get(i));            // enriches the measurement number
            measurementServiceList.get(i).setAuditDetails(auditDetails);                                 // enrich audit details
            enrichMeasures(measurementServiceList.get(i), body.getRequestInfo());                        // enrich id & audit details in measures
//            measurementServiceList.get(i).setWfStatus(wfStatusList.get(i));                              // enrich the workFlow Status
            measurementServiceList.get(i).setWorkflow(measurementServiceList.get(i).getWorkflow());      // enrich the Workflow
            for(Document document:measurementServiceList.get(i).getDocuments()){
                document.setId(UUID.randomUUID().toString());
            }
        }
    }
    public void enrichMeasurementServiceUpdate(MeasurementServiceRequest body , List<String> wfStatusList){
        List<MeasurementService> measurementServiceList = body.getMeasurements();
        for(int i=0;i<measurementServiceList.size();i++){
            measurementServiceList.get(i).setWfStatus(wfStatusList.get(i));                              // enrich the workFlow Status
            measurementServiceList.get(i).setWorkflow(measurementServiceList.get(i).getWorkflow());      // enrich the Workflow
        }
    }

    /**
     * Helper function to enrich measures
     *
     * @param measurementService
     * @param requestInfo
     */
    public void enrichMeasures(MeasurementService measurementService, RequestInfo requestInfo) {
        List<Measure> measureList = measurementService.getMeasures();
        for (int i = 0; i < measureList.size(); i++) {
            measureList.get(i).setId(UUID.randomUUID());
            measureList.get(i).setReferenceId(measurementService.getId().toString()); // point to measurementId
            measureList.get(i).setAuditDetails(measurementService.getAuditDetails()); // enrich audit details
        }
    }
}
