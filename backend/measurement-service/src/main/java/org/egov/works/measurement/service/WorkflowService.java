package org.egov.works.measurement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.workflow.ProcessInstance;
import org.egov.works.measurement.config.Configuration;
import org.egov.works.measurement.util.WorkflowUtil;
import org.egov.works.measurement.web.models.MeasurementServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WorkflowService {

    @Autowired
    private WorkflowUtil workflowUtil;

    @Autowired
    private Configuration config;
    public List<String> updateWorkflowStatuses(MeasurementServiceRequest measurementServiceRequest) {
        List<String> wfStatusList = new ArrayList<>();
        for (org.egov.works.measurement.web.models.MeasurementSvcObject measurementService : measurementServiceRequest.getMeasurements()) {
            String currWfStatus = workflowUtil.updateWorkflowStatus(measurementServiceRequest.getRequestInfo(), measurementService.getTenantId(), measurementService.getMeasurementNumber(), config.getBussinessServiceCode(), measurementService.getWorkflow(), config.getWfModuleName());
            wfStatusList.add(currWfStatus);
        }
        return  wfStatusList;
    }
    public Map<String, Workflow> getCurrentWf(MeasurementServiceRequest measurementServiceRequest){
        List<ProcessInstance> processInstanceList = new ArrayList<>();
        for (org.egov.works.measurement.web.models.MeasurementSvcObject measurementService : measurementServiceRequest.getMeasurements()) {
            ProcessInstance currProcessInstance = workflowUtil.getProcessInstanceForWorkflow(measurementServiceRequest.getRequestInfo(), measurementService.getTenantId(), measurementService.getMeasurementNumber(), config.getBussinessServiceCode(), measurementService.getWorkflow(), config.getWfModuleName());
            processInstanceList.add(currProcessInstance);
        }
        return workflowUtil.getWorkflow(processInstanceList);
    }
}
