package org.egov.works.kafka;

import org.egov.works.service.NotificationConsumerService;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NotificationConsumer {

    @Autowired
    private NotificationConsumerService notificationService;



    /*
     * Uncomment the below line to start consuming record from kafka.topics.consumer
     * Value of the variable kafka.topics.consumer should be overwritten in application.properties
     */
    //@KafkaListener(topics = {"kafka.topics.consumer"})
    @KafkaListener(topics = {"${contract.kafka.update.topic}",
            "${estimate.kafka.update.topic}",
            "${expense.billing.bill.create}",
            "${expense.billing.bill.update}",
            "${musterroll.kafka.update.topic}",
            "${measurement-service.kafka.update.topic}",
            "${org.kafka.create.topic}",
            "${org.kafka.update.topic}",
            "${individual.producer.save.topic}",
            "${individual.producer.update.topic}"}
    )
    public void listen(final String record,@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        notificationService.fetchServiceBasedOnTopic(record,topic);


        //TODO

    }
}
