package org.egov.digit.expense.kafka;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class Consumer {

    /*
    * Uncomment the below line to start consuming record from kafka.topics.consumer
    * Value of the variable kafka.topics.consumer should be overwritten in application.properties
    */
    //@KafkaListener(topics = {"kafka.topics.consumer"})
    public void listen(final HashMap<String, Object> record) {


    }
}
