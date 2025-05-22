package org.egov.kafka;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.tracer.kafka.CustomKafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// NOTE: If tracer is disabled change CustomKafkaTemplate to KafkaTemplate in autowiring

@Service
@Slf4j
public class MusterRollProducer {

    private final CustomKafkaTemplate<String, Object> kafkaTemplate;
    private final MultiStateInstanceUtil multiStateInstanceUtil;

    @Autowired
    public MusterRollProducer(CustomKafkaTemplate<String, Object> kafkaTemplate, MultiStateInstanceUtil multiStateInstanceUtil) {
        this.kafkaTemplate = kafkaTemplate;
        this.multiStateInstanceUtil = multiStateInstanceUtil;
    }

    /**
     * Publishes a message to a Kafka topic specific to a tenant.
     *
     * @param tenantId The tenant ID to identify the specific tenant.
     * @param topic The base topic name for publishing the message.
     * @param value The value or payload to be sent to the Kafka topic.
     */
    public void push(String tenantId, String topic, Object value) {
        String updatedTopic = multiStateInstanceUtil.getStateSpecificTopicName(tenantId, topic);
        log.info("The Kafka topic for the tenantId : {} is : {}", tenantId, updatedTopic);
        this.kafkaTemplate.send(updatedTopic, value);
    }
}
