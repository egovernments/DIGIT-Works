package org.egov.works.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.service.ContractService;
import org.egov.works.web.models.EstimateConsumerError;
import org.egov.works.web.models.EstimateRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.egov.works.util.ContractServiceConstants.APPROVE_ACTION;
import static org.egov.works.util.ContractServiceConstants.REVISION_ESTIMATE;

@Component
@Slf4j
public class ContractConsumer {


    private final ObjectMapper objectMapper;

    private final ContractService contractService;

    public ContractConsumer(ObjectMapper objectMapper,ContractService contractService) {
        this.objectMapper = objectMapper;
        this.contractService=contractService;
    }


    /*
     * Uncomment the below line to start consuming record from kafka.topics.consumer
     * Value of the variable kafka.topics.consumer should be overwritten in application.properties
     */
    //@KafkaListener(topics = {"kafka.topics.consumer"})
    @KafkaListener(topics = {"${estimate.kafka.update.topic}"})
    public void listen(final String consumerRecord, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("ContractConsumer:listen");
        EstimateRequest request = null;
        try {
            request = objectMapper.readValue(consumerRecord, EstimateRequest.class);
            if(request.getEstimate().getBusinessService().equals(REVISION_ESTIMATE)){
                if(!request.getWorkflow().getAction().equals(APPROVE_ACTION)){
                    log.info("Revised Estimate is not Approved");
                }else{
                    contractService.createAndPostRevisedContractRequest(request);
                }

            }else{
                log.info("Request is not for Revised Estimate");
            }

        } catch (Exception exception) {
            log.error("Error occurred while processing the consumed estimate request from the  topic : " + topic, exception);
            final EstimateConsumerError error = EstimateConsumerError.builder()
                    .estimateRequest(request)
                    .exception(exception)
                    .build();
        }
    }
}
