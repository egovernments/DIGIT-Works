package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.config.EstimateServiceConfiguration;
import org.egov.works.producer.Producer;
import org.egov.works.web.models.EstimateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EstimateService {

    private EstimateServiceConfiguration serviceConfiguration;
    private Producer producer;

    @Autowired
    public EstimateService(EstimateServiceConfiguration serviceConfiguration, Producer producer) {
        this.producer = producer;
        this.serviceConfiguration = serviceConfiguration;
    }



    public EstimateRequest createEstimate(EstimateRequest request) {
        //TODO
        producer.push(serviceConfiguration.getSaveEstimateTopic(), request);
        return request;
    }
}
