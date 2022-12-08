package org.egov.works.service;

import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.config.EstimateServiceConfiguration;
import org.egov.works.producer.Producer;
import org.egov.works.repository.EstimateRepository;
import org.egov.works.validator.EstimateServiceValidator;
import org.egov.works.web.models.Estimate;
import org.egov.works.web.models.EstimateRequest;
import org.egov.works.web.models.EstimateSearchCriteria;
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


    /**
     * Create Estimate by validating the details, enriched , update the workflow
     * and finally pushed to kafka to persist in postgres DB.
     *
     * @param request
     * @return
     */
    public EstimateRequest createEstimate(EstimateRequest request) {
        serviceValidator.validateCreateEstimate(request);
        enrichmentService.enrichCreateEstimate(request);
        workflowService.updateWorkflowStatus(request);
        producer.push(serviceConfiguration.getSaveEstimateTopic(), request);
        producer.push(serviceConfiguration.getEstimateInboxTopic(), request);
        return request;
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
     * Except Date of Proposal, everything will be editable.
     *
     * @param request
     * @return
     */
    public EstimateRequest updateEstimate(EstimateRequest request) {
        serviceValidator.validateUpdateEstimate(request);
        enrichmentService.enrichUpdateEstimate(request);
        workflowService.updateWorkflowStatus(request);
        producer.push(serviceConfiguration.getUpdateEstimateTopic(), request);
        producer.push(serviceConfiguration.getEstimateInboxTopic(), request);
        return request;
    }
}
