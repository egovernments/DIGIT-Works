package org.egov.works.kafka;



import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.StatementConfiguration;
import org.egov.works.service.UtilizationService;
import org.egov.works.services.common.models.measurement.Measurement;
import org.egov.works.services.common.models.measurement.MeasurementRequest;
import org.egov.works.util.EnrichmentUtil;
import org.egov.works.web.models.StatementCreateRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static org.egov.works.config.ErrorConfiguration.CONVERSION_ERROR_KEY;
import static org.egov.works.config.ErrorConfiguration.CONVERSION_ERROR_VALUE;

@Component
@Slf4j
public class UtilizationConsumer {


    private final ObjectMapper mapper;
    private final EnrichmentUtil enrichmentUtil;
    private final UtilizationService utilizationService;
    private final Producer producer;
    private final StatementConfiguration configs;

    public UtilizationConsumer(ObjectMapper mapper, EnrichmentUtil enrichmentUtil, UtilizationService utilizationService, Producer producer, StatementConfiguration configs) {
        this.mapper = mapper;
        this.enrichmentUtil = enrichmentUtil;
        this.utilizationService = utilizationService;
        this.producer = producer;
        this.configs = configs;
    }

    /*
    * Uncomment the below line to start consuming record from kafka.topics.consumer
    * Value of the variable kafka.topics.consumer should be overwritten in application.properties
    */
    @KafkaListener(topics = {"${measurement.kafka.create.topic}","${measurement.kafka.update.topic}"})
    public void listen(final String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
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
            } catch (Exception e) {
                log.error("Error while creating utilization statement for measurement :: " + measurement.getId(), e);
                producer.push(configs.getUtilizationErrorTopic(), measurement);
            }

        }

    }
}
