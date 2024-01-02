package org.egov.service;

import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.producer.EstimateProducer;
import org.egov.repository.EstimateRepository;
import org.egov.util.EstimateServiceConstant;
import org.egov.util.EstimateServiceUtil;
import org.egov.validator.EstimateServiceValidator;
import org.egov.web.models.Estimate;
import org.egov.web.models.EstimateRequest;
import org.egov.web.models.EstimateSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EstimateService {

    private final EstimateServiceConfiguration serviceConfiguration;

    private final EstimateProducer producer;

    private final EstimateServiceValidator serviceValidator;

    private final EnrichmentService enrichmentService;

    private final EstimateRepository estimateRepository;

    private final WorkflowService workflowService;

    private final NotificationService notificationService;
    private final EstimateServiceUtil estimateServiceUtil;

    @Autowired
    public EstimateService(EstimateServiceConfiguration serviceConfiguration, EstimateProducer producer, EstimateServiceValidator serviceValidator, EnrichmentService enrichmentService, EstimateRepository estimateRepository, WorkflowService workflowService, NotificationService notificationService, EstimateServiceUtil estimateServiceUtil) {
        this.serviceConfiguration = serviceConfiguration;
        this.producer = producer;
        this.serviceValidator = serviceValidator;
        this.enrichmentService = enrichmentService;
        this.estimateRepository = estimateRepository;
        this.workflowService = workflowService;
        this.notificationService = notificationService;
        this.estimateServiceUtil = estimateServiceUtil;
    }

    /**
     * Create Estimate by validating the details, enriched , update the workflow
     * and finally pushed to kafka to persist in postgres DB.
     *
     * @param estimateRequest
     * @return
     */
    public EstimateRequest createEstimate(EstimateRequest estimateRequest) {
        log.info("EstimateService::createEstimate");
        serviceValidator.validateEstimateOnCreate(estimateRequest);
        enrichmentService.enrichEstimateOnCreate(estimateRequest);
        workflowService.updateWorkflowStatus(estimateRequest);
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
        log.info("EstimateService::searchEstimate");
        serviceValidator.validateEstimateOnSearch(requestInfoWrapper, searchCriteria);
        enrichmentService.enrichEstimateOnSearch(searchCriteria);
        return estimateRepository.getEstimate(searchCriteria);
    }

    /**
     * @param searchCriteria - EstimateSearchCriteria contains search criteria on estimate
     * @return Count of List of matching estimate application
     */
    public Integer countAllEstimateApplications(EstimateSearchCriteria searchCriteria) {
        log.info("EstimateService::countAllEstimateApplications");
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
        log.info("EstimateService::updateEstimate");
        serviceValidator.validateEstimateOnUpdate(estimateRequest);
        enrichmentService.enrichEstimateOnUpdate(estimateRequest);
        workflowService.updateWorkflowStatus(estimateRequest);
        if(Boolean.TRUE.equals(estimateServiceUtil.isRevisionEstimate(estimateRequest))){
            updateWfStatusOfPreviousEstimate(estimateRequest);
        }
        producer.push(serviceConfiguration.getUpdateEstimateTopic(), estimateRequest);
        try{
            notificationService.sendNotification(estimateRequest);
        }catch (Exception e){
            log.error("Exception while sending notification: " + e);
        }
        return estimateRequest;
    }
    private void updateWfStatusOfPreviousEstimate(EstimateRequest estimateRequest){
        if(estimateRequest.getEstimate().getStatus() == Estimate.StatusEnum.ACTIVE){
           EstimateSearchCriteria estimateSearchCriteria = EstimateSearchCriteria.builder().tenantId(estimateRequest.getEstimate().getTenantId()).estimateNumber(estimateRequest.getEstimate().getEstimateNumber()).status(EstimateServiceConstant.ESTIMATE_ACTIVE_STATUS).sortOrder(EstimateSearchCriteria.SortOrder.DESC).sortBy(
                    EstimateSearchCriteria.SortBy.createdTime).build();
           List<Estimate> estimateList = estimateRepository.getEstimate(estimateSearchCriteria);
            if(!estimateList.isEmpty()){
                Estimate oldEstimate = estimateList.get(0);
                oldEstimate.setStatus(Estimate.StatusEnum.INACTIVE);
                EstimateRequest oldEstimateRequest = EstimateRequest.builder().requestInfo(estimateRequest.getRequestInfo()).estimate(oldEstimate).build();
                producer.push(serviceConfiguration.getUpdateEstimateTopic(), oldEstimateRequest);
            }
        }
    }
}
