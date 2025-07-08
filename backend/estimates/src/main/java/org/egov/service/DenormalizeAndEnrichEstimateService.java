package org.egov.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.workflow.ProcessInstanceResponse;
import org.egov.common.models.project.Project;
import org.egov.util.EstimateServiceConstant;
import org.egov.util.ProjectUtil;
import org.egov.web.models.Estimate;
import org.egov.web.models.EstimateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
@Slf4j
public class DenormalizeAndEnrichEstimateService {

    private final ProjectUtil projectUtil;

    private final WorkflowService workflowService;

    private final ObjectMapper objectMapper;

    @Autowired
    public DenormalizeAndEnrichEstimateService(ProjectUtil projectUtil, WorkflowService workflowService, ObjectMapper objectMapper) {
        this.projectUtil = projectUtil;
        this.workflowService = workflowService;
        this.objectMapper = objectMapper;
    }

    /**
     * Denormalize the project details by calling to project service and enrich the same
     * in estimate request
     * <p>
     * Enrich the workflow details with history as 'false' in estimate request by calling the
     * workflow service
     *
     * @param estimateRequest
     * @return
     */
    public EstimateRequest denormalizeAndEnrich(EstimateRequest estimateRequest) {
        log.info("DenormalizeAndEnrichEstimateService::denormalizeAndEnrich");
        Estimate estimate = estimateRequest.getEstimate();
        if (estimate != null && StringUtils.isNotBlank(estimate.getTenantId())) {
            //denormalize and enrich the project into estimate request
            if (StringUtils.isNotBlank(estimate.getProjectId())) {
                estimateRequest = denormalizeAndEnrichProject(estimateRequest);
            }
            //Enrich the process instance from workflow service to estimate request
            if (StringUtils.isNotBlank(estimate.getEstimateNumber())) {
                enrichProcessInstance(estimateRequest);
            }
        }
        return estimateRequest;
    }

    /**
     * @param estimateRequest
     */
    private EstimateRequest enrichProcessInstance(EstimateRequest estimateRequest) {
        log.info("DenormalizeAndEnrichEstimateService::enrichProcessInstance");
        ProcessInstanceResponse processInstanceResponse = workflowService.getProcessInstanceOfWorkFlowWithoutHistory(estimateRequest);
        if (processInstanceResponse != null
                && processInstanceResponse.getProcessInstances() != null
                && !processInstanceResponse.getProcessInstances().isEmpty()) {
            estimateRequest.getEstimate().setProcessInstances(processInstanceResponse.getProcessInstances().get(0));
        }
        return estimateRequest;
    }

    /**
     * Denormalize and Enrich the project in Estimate Request
     *
     * @param estimateRequest
     * @return
     */
    public EstimateRequest denormalizeAndEnrichProject(EstimateRequest estimateRequest) {
        log.info("DenormalizeAndEnrichEstimateService:: Enrich project details for estimate number %s", estimateRequest.getEstimate().getEstimateNumber());
        Object projectRes = projectUtil.getProjectDetails(estimateRequest);

        //If project payload changes, this key needs to be modified!
        List<Project> projects = objectMapper.convertValue(((LinkedHashMap) projectRes).get(EstimateServiceConstant.PROJECT_RESP_PAYLOAD_KEY), new TypeReference<List<Project>>() {
        })  ;

        if (projects != null && !projects.isEmpty()) {
            estimateRequest.getEstimate().setProject(projects.get(0));
        }
        else {
        	log.warn(String.format("Unable to enrich project details for estimate %s. Inbox and search will not function correctly!", estimateRequest.getEstimate().getEstimateNumber()));
        }

        return estimateRequest;
    }
}
