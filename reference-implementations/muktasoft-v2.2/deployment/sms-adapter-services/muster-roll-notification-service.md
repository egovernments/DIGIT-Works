# Muster Roll Notification Service

* Package: org.egov.works.service
* Source File: MusterRollNotificationService.java

## Overview

The MusterRollNotificationService class is responsible for processing notifications related to muster rolls within the eGov Works module. It handles the generation and sending of notifications to CBO (Central Billing Office) personnel based on different actions performed on muster rolls.

## Attributes

1. producer: Kafka producer for sending notifications.
2. config: Configuration settings for notification services.
3. hrmsUtils: A utility class for interacting with the HRMS (Human Resource Management System).
4. projectServiceUtil: Utility class for project-related operations.
5. locationServiceUtil: Utility class for location-related operations.
6. mapper: Object mapper for JSON serialization/deserialization.
7. repository: Repository for service requests.
8. notificationUtil: Utility class for notification-related operations.
9. localizationUtil: Utility class for localization operations.

## Methods

* process(final String record, @Header(KafkaHeaders.RECEIVED\_TOPIC) String topic): Processes the incoming Kafka message containing muster roll details and delegates the notification sending process to the appropriate method.
* sendNotificationToCBO(MusterRollRequest musterRollRequest): Sends notifications to CBO personnel based on the action performed on the muster roll (e.g., send back for correction, approval).
* setAdditionalFields(MusterRollRequest request, String localizationCode,Map\<String, Object> additionalField ): Sets additional fields for the notification if required.
* checkAdditionalFieldAndPushONSmsTopic(String customizedMessage , Map\<String, Object> additionalField,Map\<String,String> smsDetails): Checks if additional fields are present and sends the SMS request accordingly.
* getMessage(MusterRollRequest musterRollRequest, String msgCode): Retrieves the localized message for a given code and locale.
* buildMessageReplaceVariables(String message, String musterRollName, String amount): Replaces variables in a message template with actual values.

## Usage

Instantiate and configure the MusterRollNotificationService class within the application context to handle notifications related to muster rolls. Ensure that dependencies such as Kafka producer, configuration settings, and utility classes are properly injected.

