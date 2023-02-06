package org.egov.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.ProcessInstanceResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.util.ProjectUtil;
import org.egov.web.models.Estimate;
import org.egov.web.models.EstimateRequest;
import org.egov.web.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DenormalizeAndEnrichEstimateService {

    @Autowired
    private ProjectUtil projectUtil;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private ObjectMapper objectMapper;

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
            estimateRequest.getEstimate().setProcessInstances(processInstanceResponse.getProcessInstances());
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
        log.info("DenormalizeAndEnrichEstimateService::denormalizeAndEnrichProject");
        Object projectRes = projectUtil.getProjectDetails(estimateRequest);

        List<Project> projects = objectMapper.convertValue(projectRes, new TypeReference<List<Project>>() {
        });

        if (projects != null && !projects.isEmpty()) {
            estimateRequest.getEstimate().setProject(projects.get(0));
        }

        return estimateRequest;
    }
}
