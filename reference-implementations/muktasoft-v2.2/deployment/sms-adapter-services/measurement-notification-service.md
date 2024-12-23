# Measurement Notification Service

* Package: org.egov.works.service
* Source File: MeasurementNotificationService.java

## Overview

The MeasurementNotificationService class is responsible for processing notifications related to measurements within the eGov Works module. It handles the generation and sending of notifications to relevant personnel based on different actions performed on measurements, such as approval or rejection.

## Attributes

1. mapper: ObjectMapper for JSON serialization/deserialization.
2. notificationServiceConfiguration: Configuration settings for notification services.
3. measurementServiceUtil: Utility class for measurement-related operations.
4. producer: Kafka producer for sending notifications.

## Methods

* process(final String record, @Header(KafkaHeaders.RECEIVED\_TOPIC) String topic): Processes the incoming Kafka message containing measurement details and delegates the notification sending process to the appropriate method.
* sendNotification(MeasurementServiceRequest request): Sends notifications to relevant personnel based on the action performed on the measurement (e.g., approval, rejection).
* setAdditionalFields(MeasurementServiceRequest request, String localizationCode, Map\<String, Object> additionalField): Sets additional fields for the notification if required.
* checkAdditionalFieldAndPushONSmsTopic(String customizedMessage, Map\<String, Object> additionalField, Map\<String, String> smsDetails): Checks if additional fields are present and sends the SMS request accordingly.
* getCustomMessage(String message, String measurementNumber, String projectNumber): Replaces variables in a message template with actual measurement and project numbers.
* getMessage(MeasurementServiceRequest request, String msgCode): Retrieves the localized message for a given code and locale.

## Usage

Instantiate and configure the MeasurementNotificationService class within the application context to handle notifications related to measurements. Ensure that dependencies such as ObjectMapper, configuration settings, and utility classes are properly injected.

