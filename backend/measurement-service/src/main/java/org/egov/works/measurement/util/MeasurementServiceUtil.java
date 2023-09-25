package org.egov.works.measurement.util;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.measurement.config.Configuration;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class MeasurementServiceUtil {

    @Autowired
    private org.egov.works.measurement.service.MeasurementService measurementService;
    @Autowired
    private IdgenUtil idgenUtil;
    @Autowired
    private Configuration configuration;
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

    public List<MeasurementService> convertToMeasurementServiceList(MeasurementServiceRequest measurementServiceRequest, List<Measurement> measurements) {
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

    public void enrichMeasurementServiceUpdate(MeasurementServiceRequest body , List<String> wfStatusList){
        List<MeasurementService> measurementServiceList = body.getMeasurements();
        for(int i=0;i<measurementServiceList.size();i++){
            measurementServiceList.get(i).setWfStatus(wfStatusList.get(i));                              // enrich the workFlow Status
            measurementServiceList.get(i).setWorkflow(measurementServiceList.get(i).getWorkflow());      // enrich the Workflow
        }
    }

    public void enrichMeasures(MeasurementService measurementService, RequestInfo requestInfo) {
        List<Measure> measureList = measurementService.getMeasures();
        for (int i = 0; i < measureList.size(); i++) {
            measureList.get(i).setId(UUID.randomUUID());
            measureList.get(i).setReferenceId(measurementService.getId().toString()); // point to measurementId
            measureList.get(i).setAuditDetails(measurementService.getAuditDetails()); // enrich audit details
        }
    }
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

    public MeasurementResponse updateMeasurementAndGetResponse(MeasurementServiceRequest measurementServiceRequest) {
        // Convert MeasurementServiceRequest to MeasurementRequest
        MeasurementRequest measurementRequest = makeMeasurementUpdateRequest(measurementServiceRequest);

        // Call the updateMeasurement method to update measurements
        MeasurementResponse measurementResponse = measurementService.updateMeasurement(measurementRequest);

        return  measurementResponse;
    }

    public MeasurementRequest makeMeasurementUpdateRequest(MeasurementServiceRequest measurementServiceRequest) {
        MeasurementRequest measurementRequest = new MeasurementRequest();

        // Set the RequestInfo from MeasurementServiceRequest
        measurementRequest.setRequestInfo(measurementServiceRequest.getRequestInfo());

        // Set the Measurements from MeasurementServiceRequest
        measurementRequest.setMeasurements(convertToMeasurementList(measurementServiceRequest.getMeasurements()));

        return measurementRequest;
    }

    public MeasurementServiceResponse makeUpdateResponseService(MeasurementServiceRequest measurementServiceRequest) {
        MeasurementServiceResponse response = new MeasurementServiceResponse();

        response.setResponseInfo(ResponseInfo.builder()
                .apiId(measurementServiceRequest.getRequestInfo().getApiId())
                .msgId(measurementServiceRequest.getRequestInfo().getMsgId())
                .ts(measurementServiceRequest.getRequestInfo().getTs())
                .status("successful")
                .build());

        response.setMeasurements(measurementServiceRequest.getMeasurements());

        return response;
    }

    public void enrichWf(MeasurementServiceRequest measurementServiceRequest , List<String> wfStatusList){
        List<MeasurementService> measurementServiceList = measurementServiceRequest.getMeasurements();
        for(int i=0;i<measurementServiceList.size();i++){
            measurementServiceList.get(i).setWfStatus(wfStatusList.get(i));                              // enrich wf status
            measurementServiceList.get(i).setWorkflow(measurementServiceList.get(i).getWorkflow());      // enrich the Workflow
        }
    }

}
