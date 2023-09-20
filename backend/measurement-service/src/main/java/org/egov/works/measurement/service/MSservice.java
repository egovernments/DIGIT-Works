package org.egov.works.measurement.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.measurement.config.Configuration;
import org.egov.works.measurement.kafka.Producer;
import org.egov.works.measurement.util.ContractUtil;
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
    public ResponseEntity<MeasurementServiceResponse> handleCreateMeasurementService(MeasurementServiceRequest body){
        measurementServiceValidator.validateContracts(body);                             // validate contracts
        List<String> wfStatusList = workflowService.updateWorkflowStatuses(body);        // update WF
        enrichMeasurementService(body,wfStatusList);                                     // enrich Measurement Service
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(),true);
        MeasurementServiceResponse measurementServiceResponse = MeasurementServiceResponse.builder().responseInfo(responseInfo).measurements(body.getMeasurements()).build();
        producer.push(configuration.getMeasurementServiceCreateTopic(),body);
        return new ResponseEntity<MeasurementServiceResponse>(measurementServiceResponse, HttpStatus.ACCEPTED);

    }
    public void enrichMeasurementService(MeasurementServiceRequest body , List<String> wfStatusList){
        List<MeasurementService> measurementServiceList = body.getMeasurements();
        RequestInfo requestInfo = body.getRequestInfo();
        for(int i=0;i<measurementServiceList.size();i++){
            // create an audit details
            AuditDetails auditDetails = (AuditDetails.builder().createdBy(requestInfo.getUserInfo().getUuid()).createdTime(System.currentTimeMillis()).lastModifiedBy(requestInfo.getUserInfo().getUuid()).lastModifiedTime(System.currentTimeMillis()).build());
            measurementServiceList.get(i).setId(UUID.randomUUID()); // set ID
            measurementServiceList.get(i).setAuditDetails(auditDetails);
            enrichMeasures(measurementServiceList.get(i), body.getRequestInfo());                        // enrich id & audit details in measures
            measurementServiceList.get(i).setWfStatus(wfStatusList.get(i));                              // enrich the workFlow Status
            measurementServiceList.get(i).setWorkflow(measurementServiceList.get(i).getWorkflow());      // enrich the Workflow
        }
    }

    public void enrichMeasures(MeasurementService measurementService,RequestInfo requestInfo){
        List<Measure> measureList = measurementService.getMeasures();
        for(int i=0;i<measureList.size();i++){
            measureList.get(i).setReferenceId(measurementService.getId().toString()); // point to measurementId
            measureList.get(i).setAuditDetails(measurementService.getAuditDetails()); // enrich audit details
        }
    }
}
