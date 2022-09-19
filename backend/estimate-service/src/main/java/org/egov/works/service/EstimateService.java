package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
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


    public EstimateRequest createEstimate(EstimateRequest request) {
        serviceValidator.validateCreateEstimate(request);
        enrichmentService.enrichCreateEstimate(request);
        workflowService.updateWorkflowStatus(request);
        producer.push(serviceConfiguration.getSaveEstimateTopic(), request);
        return request;
    }

    public List<Estimate> searchEstimate(RequestInfo requestInfo, EstimateSearchCriteria searchCriteria) {
        serviceValidator.validateSearchEstimate(requestInfo, searchCriteria);
        enrichmentService.enrichSearchEstimate(requestInfo, searchCriteria);

        List<Estimate> estimateList = estimateRepository.getEstimate(searchCriteria);

        List<EstimateRequest> estimateRequestList = new LinkedList<>();
        for (Estimate estimate : estimateList) {
            EstimateRequest estimateRequest = EstimateRequest.builder().estimate(estimate).build();
            estimateRequestList.add(estimateRequest);
        }

        return estimateList;
    }
}
