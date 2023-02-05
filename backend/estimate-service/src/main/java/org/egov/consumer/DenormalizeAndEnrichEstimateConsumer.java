package org.egov.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.producer.Producer;
import org.egov.service.DenormalizeAndEnrichEstimateService;
import org.egov.web.models.EstimateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class DenormalizeAndEnrichEstimateConsumer {

    @Autowired
    private Producer producer;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private EstimateServiceConfiguration serviceConfiguration;

    @Autowired
    private DenormalizeAndEnrichEstimateService denormalizeAndEnrichEstimateService;

    @KafkaListener(topics = {"${estimate.kafka.create.topic}"})
    public void listen(final HashMap<String, Object> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("DenormalizeAndEnrichEstimateConsumer::listen");
        try {
            EstimateRequest estimateRequest = mapper.convertValue(record, EstimateRequest.class);
            if (estimateRequest != null && estimateRequest.getEstimate() != null && estimateRequest.getRequestInfo() != null) {
                EstimateRequest enrichedEstimateRequest = denormalizeAndEnrichEstimateService.denormalizeAndEnrich(estimateRequest);
                producer.push(serviceConfiguration.getEnrichEstimateTopic(), enrichedEstimateRequest);
            }
        } catch (Exception e) {
            log.error("Error occurred while processing the consumed save estimate record from topic : " + topic, e);
            throw new RuntimeException(e);
        }
    }
}
