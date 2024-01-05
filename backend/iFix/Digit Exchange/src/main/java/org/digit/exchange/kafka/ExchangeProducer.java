package org.digit.exchange.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExchangeProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public ExchangeProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void push(String topic, Object value) {
        kafkaTemplate.send(topic, value);
    }

}
