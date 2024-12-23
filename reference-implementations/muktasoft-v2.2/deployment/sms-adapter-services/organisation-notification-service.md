# Organisation Notification Service

* Package: org.egov.works.service
* Source File: OrganizationNotificationService.java

## Overview

The OrganizationNotificationService class is responsible for handling notifications related to organization management within the eGov Works module. It facilitates the generation and sending of notifications to relevant parties when organizations are created or updated.

## Attributes

1. mapper: ObjectMapper for JSON serialization/deserialization.
2. notificationServiceConfiguration: Configuration settings for notification services.
3. producer: Kafka producer for sending notifications.
4. restTemplate: RestTemplate for making HTTP requests.
5. repository: ServiceRequestRepository for fetching data from external services.

## Methods

* process(final String record, @Header(KafkaHeaders.RECEIVED\_TOPIC) String topic, boolean isCreateOperation): Processes the incoming Kafka message containing organization details and delegates the notification sending process based on whether it's a create or update operation.
* sendNotification(OrgRequest request, boolean isCreateOperation): Sends notifications for organization creation or update based on the boolean flag.
* pushNotificationForCreate(OrgRequest request): Pushes notifications for organization creation to relevant personnel.
* pushNotificationForUpdate(OrgRequest request): Pushes notifications for organization updates to relevant personnel.
* getOrganisations(OrgSearchRequest orgSearchRequest): Fetches organization details based on search criteria.
* setAdditionalFields(OrgRequest request, String localizationCode, Map\<String, Object> additionalField): Sets additional fields for the notification if required.
* checkAdditionalFieldAndPushONSmsTopic(String customizedMessage, Map\<String, Object> additionalField, String mobileNumber): Checks if additional fields are present and pushes the SMS notification accordingly.
*   getDetailsForSMS(Organisation organisation): Retrieves details necessary for composing the SMS notification for organization creation.

    getShortnerURL(String actualURL): Shortens the provided URL using a URL
* getSMSDetailsForUpdate(OrgRequest request): Retrieves details necessary for composing the SMS notification for organization update.
* getMessage(OrgRequest request, boolean isCreateOperation): Retrieves the localized message for organization creation or update based on the operation type.
* getMessage(OrgRequest request, String msgCode): Retrieves the localized message for the specified code and locale.
* buildMessageForCreateAction(Map\<String, String> userDetailsForSMS, String message): Builds the message for organization creation based on the provided user details.
* buildMessageForUpdateAction(Map\<String, String> userDetailsForSMS, String message): Builds the message for organization update based on the provided user details.
* getLocalisedMessages(RequestInfo requestInfo, String rootTenantId, String locale, String module): Fetches localized messages from the localization service based on the specified parameters.
* getShortnerURL(String actualURL): Shortens the provided URL using a URL shortening service.

## Usage

Instantiate and configure the OrganizationNotificationService class within the application context to handle notifications related to organization management. Ensure that dependencies such as ObjectMapper, configuration settings, Kafka producer, and RestTemplate are properly injected. Additionally, ensure that the ServiceRequestRepository is configured to communicate with external services for fetching data.
