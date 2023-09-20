package org.egov.works.measurement.service;

import digit.models.coremodels.ProcessInstance;
import digit.models.coremodels.Workflow;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.measurement.util.WorkflowUtil;
import org.egov.works.measurement.web.models.MeasurementServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WorkflowService {

    @Autowired
    private WorkflowUtil workflowUtil;
    List<String> updateWorkflowStatuses(MeasurementServiceRequest measurementServiceRequest) {
        List<String> wfStatusList = new ArrayList<>();
        for (org.egov.works.measurement.web.models.MeasurementService measurementService : measurementServiceRequest.getMeasurements()) {
//            measurementService.setWfStatus(workflowUtil.updateWorkflowStatus(measurementServiceRequest.getRequestInfo(), measurementService.getTenantId(), measurementService.getMeasurementNumber(), "MB", measurementService.getWorkflow(), "measurement-book-service"));
            // TODO: shift businessCode & moduleName to constants
            String currWfStatus = workflowUtil.updateWorkflowStatus(measurementServiceRequest.getRequestInfo(), measurementService.getTenantId(), measurementService.getMeasurementNumber(), "MB", measurementService.getWorkflow(), "measurement-book-service");
             wfStatusList.add(currWfStatus);
        }
        return  wfStatusList;
    }
    public Map<String, Workflow> getCurrentWf(MeasurementServiceRequest measurementServiceRequest){
        List<ProcessInstance> processInstanceList = new ArrayList<>();
        for (org.egov.works.measurement.web.models.MeasurementService measurementService : measurementServiceRequest.getMeasurements()) {
           ProcessInstance currProcessInstance = workflowUtil.getProcessInstanceForWorkflow(measurementServiceRequest.getRequestInfo(), measurementService.getTenantId(), measurementService.getMeasurementNumber(), "MB", measurementService.getWorkflow(), "measurement-book-service");
           processInstanceList.add(currProcessInstance);
        }
       return workflowUtil.getWorkflow(processInstanceList);
    }
}
