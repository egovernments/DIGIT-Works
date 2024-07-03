package org.egov.works.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.StatementConfiguration;
import org.egov.works.service.AnalysisStatementService;
import org.egov.works.services.common.models.estimate.Estimate;
import org.egov.works.services.common.models.estimate.EstimateRequest;
import org.egov.works.util.EnrichmentUtil;
import org.egov.works.web.models.StatementCreateRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import static org.egov.works.config.ErrorConfiguration.*;

@Slf4j
@Component
public class Consumer {

    private final ObjectMapper mapper;
    private final EnrichmentUtil enrichmentUtil;
    private final AnalysisStatementService analysisStatementService;
    private final Producer producer;
    private final StatementConfiguration configs;

    public Consumer(ObjectMapper mapper, EnrichmentUtil enrichmentUtil, AnalysisStatementService analysisStatementService, Producer producer, StatementConfiguration configs) {
        this.mapper = mapper;
        this.enrichmentUtil = enrichmentUtil;
        this.analysisStatementService = analysisStatementService;
        this.producer = producer;
        this.configs = configs;
    }


    /**
     * This method is will consume update and create topic of estimate and will create the
     * analysis statement
     * @param message
     * @param topic
     */
    @KafkaListener(topics = {"${estimate.kafka.create.topic}","${estimate.kafka.update.topic}"})
    public void listen(final String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Creating/Updating Analysis statement");
        EstimateRequest estimateRequest = new EstimateRequest();
        try {
            estimateRequest =mapper.convertValue(message, EstimateRequest.class);
        }catch (Exception e) {
            log.info("Error while creating utilization statement for measurement :: {}", message, e);
            throw new CustomException(CONVERSION_ERROR_KEY, ANALYSIS_CONVERSION_ERROR_VALUE + message);
        }
        Estimate estimate=estimateRequest.getEstimate();
        if(estimate!=null){
            StatementCreateRequest statementCreateRequest = enrichmentUtil
                    .getAnalysisStatementCreateRequest(estimateRequest.getRequestInfo(), estimate.getId(),
                            estimate.getTenantId());
            try {
                log.info("Inside Analysis Statement Consumer for creating statement with request ::{}",statementCreateRequest);
                analysisStatementService.createAnalysisStatement(statementCreateRequest, estimate);
            } catch (Exception e) {
                log.error("Error while creating analysis statement for estimate :: {}", estimate.getId(), e);
                producer.push(configs.getAnalysisStatementErrorTopic(), estimate);
            }
        }else{
            log.error("Estimate Not present in the request :: {}",estimateRequest);
        }





    }
}
