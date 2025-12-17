package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;

import org.egov.works.config.NotificationServiceConfiguration;

import org.egov.works.config.MainConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Slf4j
public class NotificationConsumerService {


    @Autowired
    private ContractNotificationService contractNotificationService;

    @Autowired
    private EstimateNotificationService estimateNotificationService;

    @Autowired
    private NotificationServiceConfiguration notificationServiceConfiguration;

    @Autowired
    private ExpenseNotificationService expenseNotificationService;

    @Autowired
    private MusterRollNotificationService musterRollNotificationService;

    @Autowired
    private MeasurementNotificationService measurementNotificationService;

    @Autowired
    private OrganizationNotificationService organizationNotificationService;

    @Autowired
    private IndividualNotificationService individualNotificationService;


    public void fetchServiceBasedOnTopic(final String record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){



        if(topic.equalsIgnoreCase(notificationServiceConfiguration.getUpdateContractTopic())){
            contractNotificationService.process(record,topic);
        }
        if(topic.equalsIgnoreCase(notificationServiceConfiguration.getEstimateUpdateTopic())){
            estimateNotificationService.process(record,topic);
        }
        if(topic.equalsIgnoreCase(notificationServiceConfiguration.getBillCreateTopic())){
            expenseNotificationService.process(record,topic);
        }
        if(topic.equalsIgnoreCase(notificationServiceConfiguration.getBillUpdateTopic())){
            expenseNotificationService.process(record,topic);
        }
        if(topic.equalsIgnoreCase(notificationServiceConfiguration.getUpdateMusterRollTopic())){
            musterRollNotificationService.process(record,topic);
        }
        if(topic.equalsIgnoreCase(notificationServiceConfiguration.getUpdateMeasurementServiceKafkaTopic())){
            measurementNotificationService.process(record, topic);
        }
        if(topic.equalsIgnoreCase(notificationServiceConfiguration.getOrgKafkaCreateTopic())){
            organizationNotificationService.process(record, topic, true);
        }
        if(topic.equalsIgnoreCase(notificationServiceConfiguration.getOrgKafkaUpdateTopic())){
            organizationNotificationService.process(record, topic, false);
        }
        if(topic.equalsIgnoreCase(notificationServiceConfiguration.getSaveIndividualTopic())){
            individualNotificationService.process(record, topic, true);
        }
        if(topic.equalsIgnoreCase(notificationServiceConfiguration.getUpdateIndividualTopic())){
            individualNotificationService.process(record, topic, false);
        }


    }





}
