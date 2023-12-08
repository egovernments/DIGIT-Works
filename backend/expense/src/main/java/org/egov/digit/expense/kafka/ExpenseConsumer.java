package org.egov.digit.expense.kafka;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExpenseConsumer {

    /*
    * Uncomment the below line to start consuming record from kafka.topics.consumer
    * Value of the variable kafka.topics.consumer should be overwritten in application.properties
    */
    //@KafkaListener(topics = {"kafka.topics.consumer"})
    public void listen(final Map<String, Object> message) {
        log.info("Consuming message from kafka topic: " + message);
    }
}
