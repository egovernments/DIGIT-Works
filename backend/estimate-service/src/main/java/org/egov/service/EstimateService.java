package org.egov.service;

import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.producer.Producer;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.repository.EstimateRepository;
import org.egov.validator.EstimateServiceValidator;
import org.egov.web.models.Estimate;
import org.egov.web.models.EstimateRequest;
import org.egov.web.models.EstimateSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class EstimateService {

    @Autowired
    private EstimateServiceConfiguration serviceConfiguration;

    @Autowired
    private Producer producer;

    @Autowired
    private EstimateServiceValidator serviceValidator;

    @Autowired
    private EnrichmentService enrichmentService;

    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private CalculationService calculationService;

    /**
     * Create Estimate by validating the details, enriched , update the workflow
     * and finally pushed to kafka to persist in postgres DB.
     *
     * @param estimateRequest
     * @return
     */
    public EstimateRequest createEstimate(EstimateRequest estimateRequest) {
        serviceValidator.validateCreateEstimate(estimateRequest);
        enrichmentService.enrichCreateEstimate(estimateRequest);
        workflowService.updateWorkflowStatus(estimateRequest);
        calculationService.calculateEstimate(estimateRequest);
        producer.push(serviceConfiguration.getSaveEstimateTopic(), estimateRequest);
        return estimateRequest;
    }

    /**
     * Search Estimate based on given search criteria
     *
     * @param requestInfoWrapper
     * @param searchCriteria
     * @return
     */
    public List<Estimate> searchEstimate(RequestInfoWrapper requestInfoWrapper, EstimateSearchCriteria searchCriteria) {
        serviceValidator.validateSearchEstimate(requestInfoWrapper, searchCriteria);
        enrichmentService.enrichSearchEstimate(requestInfoWrapper.getRequestInfo(), searchCriteria);

        List<Estimate> estimateList = estimateRepository.getEstimate(searchCriteria);

        List<EstimateRequest> estimateRequestList = new LinkedList<>();
        for (Estimate estimate : estimateList) {
            EstimateRequest estimateRequest = EstimateRequest.builder().estimate(estimate).build();
            estimateRequestList.add(estimateRequest);
        }

        return estimateList;
    }

    /**
     * @param searchCriteria - EstimateSearchCriteria contains search criteria on estimate
     * @return Count of List of matching estimate application
     */
    public Integer countAllEstimateApplications(EstimateSearchCriteria searchCriteria) {
        searchCriteria.setIsCountNeeded(Boolean.TRUE);
        return estimateRepository.getEstimateCount(searchCriteria);
    }

    /**
     * Except Date of Proposal, everything will be editable.
     *
     * @param estimateRequest
     * @return
     */
    public EstimateRequest updateEstimate(EstimateRequest estimateRequest) {
        serviceValidator.validateUpdateEstimate(estimateRequest);
        enrichmentService.enrichUpdateEstimate(estimateRequest);
        workflowService.updateWorkflowStatus(estimateRequest);
        calculationService.calculateEstimate(estimateRequest);
        producer.push(serviceConfiguration.getUpdateEstimateTopic(), estimateRequest);
        return estimateRequest;
    }
}
