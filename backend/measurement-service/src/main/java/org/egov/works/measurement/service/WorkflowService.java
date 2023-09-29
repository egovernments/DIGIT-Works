package org.egov.works.measurement.service;

import digit.models.coremodels.ProcessInstance;
import digit.models.coremodels.Workflow;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.measurement.config.MBServiceConfiguration;
import org.egov.works.measurement.util.WorkflowUtil;
import org.egov.works.measurement.web.models.MeasurementService;
import org.egov.works.measurement.web.models.MeasurementServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class WorkflowService {

    @Autowired
    private WorkflowUtil workflowUtil;

    @Autowired
    private MBServiceConfiguration config;
    public List<String> updateWorkflowStatuses(MeasurementServiceRequest measurementServiceRequest) {
        List<String> wfStatusList = new ArrayList<>();
        for (org.egov.works.measurement.web.models.MeasurementService measurementService : measurementServiceRequest.getMeasurements()) {
            String currWfStatus = workflowUtil.updateWorkflowStatus(measurementServiceRequest.getRequestInfo(), measurementService.getTenantId(), measurementService.getMeasurementNumber(), config.getBussinessServiceCode(), measurementService.getWorkflow(), config.getWfModuleName());
            wfStatusList.add(currWfStatus);
        }
        return  wfStatusList;
    }
    public Map<String, Workflow> getCurrentWf(MeasurementServiceRequest measurementServiceRequest){
        List<ProcessInstance> processInstanceList = new ArrayList<>();
        for (org.egov.works.measurement.web.models.MeasurementService measurementService : measurementServiceRequest.getMeasurements()) {
            ProcessInstance currProcessInstance = workflowUtil.getProcessInstanceForWorkflow(measurementServiceRequest.getRequestInfo(), measurementService.getTenantId(), measurementService.getMeasurementNumber(), config.getBussinessServiceCode(), measurementService.getWorkflow(), config.getWfModuleName());
            processInstanceList.add(currProcessInstance);
        }
        return workflowUtil.getWorkflow(processInstanceList);
    }

    public void changeDataAccordingToWfActions(MeasurementServiceRequest measurementServiceRequest,Map<String, org.egov.works.measurement.web.models.MeasurementService> mbNumberToServiceMap){
        Set<String> actionSets=config.actionSets;
        for(int i=0;i<measurementServiceRequest.getMeasurements().size();i++){
            if(!actionSets.contains(measurementServiceRequest.getMeasurements().get(i).getWorkflow().getAction())){
                MeasurementService existingCurrentMeasurementService = mbNumberToServiceMap.get(measurementServiceRequest.getMeasurements().get(i).getMeasurementNumber()); // Get existing MeasurementService
                Workflow workflow = measurementServiceRequest.getMeasurements().get(i).getWorkflow(); // Get workflow
                measurementServiceRequest.getMeasurements().set(i, existingCurrentMeasurementService); // Replace current measurement service
                measurementServiceRequest.getMeasurements().get(i).setWorkflow(workflow); // Set workflow

            }
            if(measurementServiceRequest.getMeasurements().get(i).getWorkflow().getAction().equals(config.rejectedStatus)){
                measurementServiceRequest.getMeasurements().get(i).setIsActive(false);
            }
        }
    }
}
