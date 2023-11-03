package org.egov.works.measurement.util;

import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.measurement.config.MBServiceConfiguration;
import org.egov.works.measurement.enrichment.MeasurementEnrichment;
import org.egov.works.measurement.service.MeasurementRegistry;
import org.egov.works.measurement.service.WorkflowService;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class MeasurementServiceUtil {

    @Autowired
    private MeasurementRegistry measurementRegistry;
    @Autowired
    private IdgenUtil idgenUtil;
    @Autowired
    private MBServiceConfiguration MBServiceConfiguration;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private MeasurementEnrichment measurementEnrichment;
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

    public void updateWorkflow(MeasurementServiceRequest measurementServiceRequest){
        List<String> wfStatusList = workflowService.updateWorkflowStatuses(measurementServiceRequest);
        measurementEnrichment.enrichMeasurementServiceUpdate(measurementServiceRequest,wfStatusList);
    }

    public void updateWorkflowDuringCreate(MeasurementServiceRequest body){
        List<String> wfStatusList = workflowService.updateWorkflowStatuses(body);
        measurementEnrichment.enrichWf(body,wfStatusList);
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

    public void validateDimensions(Measure measure) {
        if (measure.getLength().compareTo(BigDecimal.ZERO) == 0 && measure.getHeight().compareTo(BigDecimal.ZERO) == 0 &&
                measure.getBreadth().compareTo(BigDecimal.ZERO) == 0 && measure.getNumItems().compareTo(BigDecimal.ZERO) == 0)
            return;
        if (measure.getLength() == null || measure.getLength().compareTo(BigDecimal.ZERO) == 0) {
            measure.setLength(BigDecimal.ONE);
        }
        if (measure.getHeight() == null || measure.getHeight().compareTo(BigDecimal.ZERO) == 0) {
            measure.setHeight(BigDecimal.ONE);
        }
        if (measure.getBreadth() == null || measure.getBreadth().compareTo(BigDecimal.ZERO) == 0) {
            measure.setBreadth(BigDecimal.ONE);
        }
        if (measure.getNumItems() == null || measure.getNumItems().compareTo(BigDecimal.ZERO) == 0) {
            measure.setNumItems(BigDecimal.ONE);
        }
    }

}
