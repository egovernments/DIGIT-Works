package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.config.EstimateServiceConfiguration;
import org.egov.works.producer.Producer;
import org.egov.works.util.MDMSUtils;
import org.egov.works.validator.EstimateServiceValidator;
import org.egov.works.web.models.EstimateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EstimateService {

    private EstimateServiceConfiguration serviceConfiguration;
    private Producer producer;
    private EstimateServiceValidator serviceValidator;
    private MDMSUtils mdmsUtils;
    private EnrichmentService enrichmentService;

    @Autowired
    public EstimateService(EstimateServiceConfiguration serviceConfiguration, Producer producer
            , EstimateServiceValidator serviceValidator, MDMSUtils mdmsUtils, EnrichmentService enrichmentService) {
        this.producer = producer;
        this.serviceConfiguration = serviceConfiguration;
        this.serviceValidator = serviceValidator;
        this.mdmsUtils = mdmsUtils;
        this.enrichmentService = enrichmentService;
    }

    //TODO
    public EstimateRequest createEstimate(EstimateRequest request) {

        Object mdmsData = mdmsUtils.mDMSCall(request);
        serviceValidator.validateCreateEstimate(request, mdmsData);
        enrichmentService.enrichCreateEstimate(request);
        producer.push(serviceConfiguration.getSaveEstimateTopic(), request);
        return request;
    }
}
