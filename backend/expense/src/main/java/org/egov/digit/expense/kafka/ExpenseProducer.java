package org.egov.digit.expense.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.tracer.kafka.CustomKafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

// NOTE: If tracer is disabled change CustomKafkaTemplate to KafkaTemplate in autowiring

@Slf4j
@Service
public class ExpenseProducer {

    private final CustomKafkaTemplate<String, Object> kafkaTemplate;
    private final MultiStateInstanceUtil multiStateInstanceUtil;

    @Autowired
    public ExpenseProducer(CustomKafkaTemplate<String, Object> kafkaTemplate, MultiStateInstanceUtil multiStateInstanceUtil) {
        this.kafkaTemplate = kafkaTemplate;
        this.multiStateInstanceUtil = multiStateInstanceUtil;
    }

    /**
     * Publishes a message to a Kafka topic based on the tenant-specific topic name.
     *
     * @param tenantId the unique identifier of the tenant for which the message is being published
     * @param topic the base Kafka topic name to which the message needs to be pushed
     * @param value the message payload to be sent to the Kafka topic
     */
    public void push(String tenantId, String topic, Object value) {
        String updatedTopic = multiStateInstanceUtil.getStateSpecificTopicName(tenantId, topic);
        log.info("The Kafka topic for the tenantId : {} is : {}", tenantId, updatedTopic);
        this.kafkaTemplate.send(updatedTopic, value);
    }

    /**
     * Publishes a message to a tenant-specific Kafka topic with a processAfterTime header.
     * The consumer will delay processing until the specified epoch time is reached.
     *
     * @param tenantId         tenant identifier for topic resolution
     * @param topic            base topic name
     * @param value            message payload
     * @param processAfterTime epoch ms — consumer will nack until this time is reached
     */
    public void pushWithDelay(String tenantId, String topic, Object value, long processAfterTime) {
        String updatedTopic = multiStateInstanceUtil.getStateSpecificTopicName(tenantId, topic);
        log.info("The Kafka topic for the tenantId : {} is : {}, processAfterTime: {}", tenantId, updatedTopic, processAfterTime);
        ProducerRecord<String, Object> record = new ProducerRecord<>(updatedTopic, value);
        record.headers().add("processAfterTime",
                String.valueOf(processAfterTime).getBytes(StandardCharsets.UTF_8));
        this.kafkaTemplate.send(record);
    }
}
