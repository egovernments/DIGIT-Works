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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        // update WF
//        List<String> wfStatusList = workflowService.updateWorkflowStatuses(body);
        // enrich Measurement Service
        enrichMeasurementService(body);
        List<String> wfStatusList = workflowService.updateWorkflowStatuses(body);
        enrichWf(body,wfStatusList);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        MeasurementServiceResponse measurementServiceResponse = MeasurementServiceResponse.builder().responseInfo(responseInfo).measurements(body.getMeasurements()).build();

        producer.push(configuration.getMeasurementServiceCreateTopic(), body);
        return measurementServiceResponse;

    }
    public void enrichWf(MeasurementServiceRequest measurementServiceRequest , List<String> wfStatusList){
        List<MeasurementService> measurementServiceList = measurementServiceRequest.getMeasurements();
        for(int i=0;i<measurementServiceList.size();i++){
            measurementServiceList.get(i).setWfStatus(wfStatusList.get(i));
        }
    }
    /**
     * Handles update MeasurementService
     *
     * @param measurementServiceRequest
     * @return
     */
    public ResponseEntity<MeasurementServiceResponse> updateMeasurementService(MeasurementServiceRequest measurementServiceRequest) {
        // Validate document IDs from the measurement service request
        measurementServiceValidator.validateDocumentIds(measurementServiceRequest.getMeasurements());

        // Validate existing data and set audit details
        measurementServiceValidator.validateExistingServiceDataAndSetAuditDetails(measurementServiceRequest);

        // Validate contracts for each measurement
        measurementServiceValidator.validateContracts(measurementServiceRequest);

        // Update workflow statuses for each measurement service
        List<String> wfStatusList = workflowService.updateWorkflowStatuses(measurementServiceRequest);
        enrichMeasurementServiceUpdate(measurementServiceRequest,wfStatusList);

        // Create a MeasurementServiceResponse
        MeasurementServiceResponse response = makeUpdateResponseService(measurementServiceRequest);

        // Push the response to the service update topic
        producer.push(configuration.getServiceUpdateTopic(), response);

        // Return the response as a ResponseEntity with HTTP status NOT_IMPLEMENTED
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
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
                measure.setTotalValue(measure.getLength().multiply(measure.getHeight().multiply(measure.getBreadth().multiply(measure.getNumItems()))));
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
