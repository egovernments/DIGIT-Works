# Estimate Notification

* Package: org.egov.works.service
* Source File: EstimateNotificationService.java
* Dependencies: Spring Framework, Kafka, Jackson, Lombok, Apache Commons Lang3

## Overview

The EstimateNotificationService class is a part of the SMS adapter service in the eGov Works module. This service facilitates the sending of notifications related to estimate workflows via SMS.

## Attributes

1. producer: Kafka producer for sending notifications.
2. config: NotificationServiceConfiguration for accessing configuration settings.
3. hrmsUtils: HRMSUtils for fetching employee details.
4. projectServiceUtil: ProjectServiceUtil for fetching project details.
5. locationServiceUtil: LocationServiceUtil for fetching location details.
6. mapper: ObjectMapper for JSON serialization/deserialization.
7. repository: ServiceRequestRepository for fetching data from external services.
8. localizationUtil: LocalizationUtil for fetching localized messages.

## Methods

* **process**(final String record, @Header(KafkaHeaders.RECEIVED\_TOPIC) String topic): Processes Kafka messages containing EstimateRequest data.
* **sendNotification**(EstimateRequest request): Determines the action type and invokes corresponding notification methods.
* **pushNotificationToCreatorForRejectAction**(EstimateRequest request): Sends a notification for the rejection action in the estimate workflow.
* **pushNotificationToCreatorForApproveAction**(EstimateRequest request): Sends a notification for the approval action in the estimate workflow.
* **getDetailsForSMS**(EstimateRequest request, String uuid): Retrieves details necessary for constructing SMS notifications, such as project and user information.
* **getMessage**(EstimateRequest request): Retrieves the appropriate message template based on the workflow action.
* **buildMessageForRejectAction**(Estimate estimate, Map\<String, String> userDetailsForSMS, String message): Constructs the SMS content for the rejection action.
* **buildMessageForApproveActionCreator**(Estimate estimate, Map\<String, String> userDetailsForSMS, String message): Constructs the SMS content for the approval action by the estimate creator.
* **getLocalisedMessages**(RequestInfo requestInfo, String rootTenantId, String locale, String module): Retrieves localized messages based on the user's locale and module.
* **setAdditionalFields**(EstimateRequest request, String localizationCode, Map\<String, Object> additionalField): Sets additional fields required for customizing SMS content.
* **checkAdditionalFieldAndPushONSmsTopic**(String customizedMessage, Map\<String, Object> additionalField, Map\<String, String> smsDetails): Checks if additional fields are required and pushes the SMS message to the appropriate Kafka topic.

## Usage

The EstimateNotificationService class is utilized within the eGov Works module for sending SMS notifications related to estimate workflows. It integrates with Kafka for message processing and supports dynamic message construction based on predefined templates and user-specific data.
