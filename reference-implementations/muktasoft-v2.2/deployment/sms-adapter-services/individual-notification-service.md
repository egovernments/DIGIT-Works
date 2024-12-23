# Individual Notification Service

* Package: org.egov.works.service
* Source File: IndividualNotificationService.java

## Overview

The IndividualNotificationService class is responsible for managing notifications related to individual entities within the eGov Works module. It handles the generation and sending of notifications when individual entities are created or updated.

## Attributes

1. config: NotificationServiceConfiguration for accessing configuration settings.
2. producer: Kafka producer for sending notifications.
3. repository: ServiceRequestRepository for fetching data from external services.
4. restTemplate: RestTemplate for making HTTP requests.
5. mapper: ObjectMapper for JSON serialization/deserialization.

## Methods

* process(final String record, @Header(KafkaHeaders.RECEIVED\_TOPIC) String topic, boolean isCreateOperation): Processes the incoming Kafka message containing individual details and delegates the notification sending process based on whether it's a create or update operation.
* sendNotification(IndividualRequest request, boolean isCreateOperation): Sends notifications for individual creation or update based on the boolean flag.
* pushNotificationForCreate(IndividualRequest request): Pushes notifications for individual creation to relevant parties.
* pushNotificationForUpdate(IndividualRequest request): Pushes notifications for individual update to relevant parties.
* getDetailsForSMS(IndividualRequest request): Retrieves details necessary for composing the SMS notification for individual creation or update.
* getMessageForCreate(IndividualRequest request): Retrieves the localized message for individual creation.
* getMessageForUpdate(IndividualRequest request): Retrieves the localized message for individual update.
* getMessage(IndividualRequest request, String msgCode): Retrieves the localized message for the specified code and locale.
* buildMessageForCreate(Map\<String, String> userDetailsForSMS, String message): Builds the message for individual creation based on the provided user details.
* buildMessageForUpdate(Map\<String, String> userDetailsForSMS, String message): Builds the message for individual updates based on the provided user details.
* getLocalisedMessages(RequestInfo requestInfo, String rootTenantId, String locale, String module): Fetches localized messages from the localization service based on the specified parameters.
* setAdditionalFields(IndividualRequest request, String localizationCode, Map\<String, Object> additionalField): Sets additional fields for the notification if required.
* checkAdditionalFieldAndPushONSmsTopic(String customizedMessage, Map\<String, Object> additionalField, Map\<String, String> smsDetails): Checks if additional fields are present and push the SMS notification accordingly.

## Usage

Instantiate and configure the IndividualNotificationService class within the application context to handle notifications related to individual entities. Ensure that dependencies such as NotificationServiceConfiguration, Kafka producer, ServiceRequestRepository, RestTemplate, and ObjectMapper are properly injected. Additionally, configure the methods to handle individual creation and update notifications as per the application's requirements.
