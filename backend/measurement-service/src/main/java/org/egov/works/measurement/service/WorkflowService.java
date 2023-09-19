package org.egov.works.measurement.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.measurement.util.WorkflowUtil;
import org.egov.works.measurement.web.models.MeasurementServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WorkflowService {

    @Autowired
    private WorkflowUtil workflowUtil;
    void updateWorkflowStatuses(MeasurementServiceRequest measurementServiceRequest) {
        for (org.egov.works.measurement.web.models.MeasurementService measurementService : measurementServiceRequest.getMeasurements()) {
            workflowUtil.updateWorkflowStatus(measurementServiceRequest.getRequestInfo(), measurementService.getTenantId(), measurementService.getMeasurementNumber(), "MB", measurementService.getWorkflow(), "measurement-book-service");
        }
    }
}
