package org.egov.producer;

import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.kafka.CustomKafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EstimateProducer {

    private final CustomKafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public EstimateProducer(CustomKafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void push(String topic, Object value) {
        kafkaTemplate.send(topic, value);
    }
}
