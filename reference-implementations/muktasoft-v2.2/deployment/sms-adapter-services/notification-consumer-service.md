# Notification Consumer Service

* Package: org.egov.works.service
* Source File: NotificationConsumerService.java
* Dependencies: Spring Framework, Kafka, Lombok

## Overview

The NotificationConsumerService class is responsible for routing Kafka messages to specific service components based on the topic they belong to within the eGov Works module.

## Attributes

1. contractNotificationService: Service for processing contract-related notifications.
2. estimateNotificationService: Service for processing estimate-related notifications.
3. notificationServiceConfiguration: Configuration settings for notification services.
4. expenseNotificationService: Service for processing expense-related notifications.
5. musterRollNotificationService: Service for processing muster roll related notifications.
6. measurementNotificationService: Service for processing measurement related notifications.
7. organizationNotificationService: Service for processing organization related notifications.
8. individualNotificationService: Service for processing individual related notifications.

## Methods

* **fetchServiceBasedOnTopic**(final String record, @Header(KafkaHeaders.RECEIVED\_TOPIC) String topic): Determines the appropriate service handler based on the topic of the received message and delegates the message processing to that specific service.

## Usage

Instantiate and configure the NotificationConsumer class within the application context to enable message consumption from Kafka topics. Ensure that the NotificationConsumerService class is properly injected into the NotificationConsumer class for seamless message processing.

