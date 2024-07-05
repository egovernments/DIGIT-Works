package org.egov.works.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.StatementConfiguration;
import org.egov.works.service.AnalysisStatementService;
import org.egov.works.service.UtilizationService;
import org.egov.works.services.common.models.estimate.Estimate;
import org.egov.works.services.common.models.estimate.EstimateRequest;
import org.egov.works.services.common.models.measurement.Measurement;
import org.egov.works.services.common.models.measurement.MeasurementRequest;
import org.egov.works.util.EnrichmentUtil;
import org.egov.works.web.models.StatementCreateRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.egov.works.config.ErrorConfiguration.*;

@Slf4j
@Component
public class Consumer {

    private final ObjectMapper mapper;
    private final EnrichmentUtil enrichmentUtil;
    private final AnalysisStatementService analysisStatementService;
    private final Producer producer;
    private final StatementConfiguration configs;
    private final UtilizationService utilizationService;

    public Consumer(ObjectMapper mapper, EnrichmentUtil enrichmentUtil, AnalysisStatementService analysisStatementService, Producer producer, StatementConfiguration configs, UtilizationService utilizationService) {
        this.mapper = mapper;
        this.enrichmentUtil = enrichmentUtil;
        this.analysisStatementService = analysisStatementService;
        this.producer = producer;
        this.configs = configs;
        this.utilizationService = utilizationService;
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
        EstimateRequest estimateRequest;
        try {
            estimateRequest =mapper.readValue(message, EstimateRequest.class);
        }catch (Exception e) {
            log.info("Error while creating analysis statement for estimate :: {}", message, e);
            throw new CustomException(CONVERSION_ERROR_KEY, ANALYSIS_CONVERSION_ERROR_VALUE + message);
        }
        Estimate estimate=estimateRequest.getEstimate();
        if(estimate!=null){
            StatementCreateRequest statementCreateRequest = enrichmentUtil
                    .getAnalysisStatementCreateRequest(estimateRequest.getRequestInfo(), estimate.getId(),
                            estimate.getTenantId());
            try {
                log.info("Inside Analysis Statement Consumer for creating statement with request ::{}",statementCreateRequest);
                List<String> actionList= Arrays.asList("RE-SUBMIT","SUBMIT","DRAFT");

                if(actionList.contains(estimateRequest.getWorkflow().getAction()))
                {
                    analysisStatementService.createAnalysisStatement(statementCreateRequest, estimate);
                }else{
                    log.info("Estimate application is not in edit or submit state");
                }

            } catch (Exception e) {
                log.error("Error while creating analysis statement for estimate :: {}", estimate.getId(), e);
                producer.push(configs.getAnalysisStatementErrorTopic(), estimate);
            }
        }else{
            log.error("Estimate Not present in the request :: {}",estimateRequest);
        }
    }

    @KafkaListener(topics = {"${measurement.kafka.create.topic}","${measurement.kafka.update.topic}"})
    public void utilizationListener(final String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Creating/Updating Utilization statement");
        MeasurementRequest measurementRequest = new MeasurementRequest();
        try {
            measurementRequest = mapper.readValue(message, MeasurementRequest.class);
        }catch (Exception e) {
            log.info("Error while creating utilization statement for measurement :: " + message, e);
            throw new CustomException(CONVERSION_ERROR_KEY, CONVERSION_ERROR_VALUE + message);
        }
        for (Measurement measurement : measurementRequest.getMeasurements()) {
            StatementCreateRequest statementCreateRequest = enrichmentUtil
                    .getStatementCreateRequest(measurementRequest.getRequestInfo(), measurement.getId(),
                            measurement.getTenantId());
            try {
                utilizationService.utilizationCreate(statementCreateRequest, measurement);
                log.info("Successfully created utilization statement for measurement :: " + measurement.getId());
            } catch (Exception e) {
                log.error("Error while creating utilization statement for measurement :: " + measurement.getId(), e);
                producer.push(configs.getUtilizationErrorTopic(), measurement);
            }

        }

    }
}
