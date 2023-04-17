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

    /**
     * It consumes the message from 'save-estimate' topic and do the
     * <p>
     * 1. denormalization & enrichment of project
     * 2. Enrichment of current process instance
     * <p>
     * in estimate And finally produce the enriched estimate to the 'enrich-estimate'
     * topic. And indexer consumes the same enriched estimate message from 'enrich-estimate'
     * topic and do the processing to elastic index.
     * <p>
     * This is introduced to get the search - criteria & result of project and status of workflow in
     * estimate-inbox
     *
     * @param record
     * @param topic
     */
    @KafkaListener(topics = {"${estimate.kafka.create.topic}","${estimate.kafka.update.topic}"})
    public void listen(final String record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("DenormalizeAndEnrichEstimateConsumer::listen");
        try {

            EstimateRequest estimateRequest = mapper.readValue(record, EstimateRequest.class);
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
