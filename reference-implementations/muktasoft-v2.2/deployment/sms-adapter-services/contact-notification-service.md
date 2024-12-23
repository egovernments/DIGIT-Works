# Contact Notification Service

* Package: org.egov.works.service
* Source File: ContractNotificationService.java
* Dependencies: Spring Framework, Kafka, Lombok

## Overview

The ContractNotificationService class manages the notification workflow for contract-related actions within the eGov Works module. It processes contract-related Kafka messages and sends appropriate notifications to relevant stakeholders.

## Attributes

1. notificationServiceConfiguration: Configuration settings for notification services.
2. restTemplate: Template for making RESTful HTTP requests.
3. producer: Kafka producer for sending notifications.
4. mapper: Object mapper for JSON serialization and deserialization.
5. hrmsUtils: Utility class for HRMS (Human Resource Management System) operations.
6. estimateServiceUtil: Utility class for estimate-related operations.
7. projectServiceUtil: Utility class for project-related operations.
8. locationServiceUtil: Utility class for location-related operations.
9. organisationServiceUtil: Utility class for organization-related operations.
10. repository: Repository for service request operations.
11. localizationUtil: Utility class for localization operations.
12. contractsUtil: Utility class for contract-related operations.

## Methods

* **process**(final String record, @Header(KafkaHeaders.RECEIVED\_TOPIC) String topic): Processes the Kafka message for contract-related actions and sends notifications accordingly.
* **sendNotification**(ContractRequest request): Sends notifications based on the type of contract action.
* **pushNotificationForRevisedContract**(ContractRequest request): Sends notifications for revised contracts.
* **pushNotificationToOriginator**(ContractRequest request, String message, String cboMobileNumber): Sends notifications to the originator.
* **pushNotificationToCreatorForRejectAction**(ContractRequest request): Sends notifications to the creator for reject actions.
* **pushNotificationToCreatorForApproveAction**(ContractRequest request): Sends notifications to the creator for approve actions.
* **pushNotificationToCreatorForDeclineAction**(ContractRequest request): Sends notifications to the creator for decline actions.
* **pushNotificationToCreatorForAcceptAction**(ContractRequest request): Sends notifications to the creator for accept actions.
* **pushNotificationToCBOForApproveAction**(ContractRequest request): Sends notifications to the(CBO) for approve actions.
* **getDetailsForSMS**(ContractRequest request, String uuid): Retrieves details required for SMS notifications.
* **getProjectNumber**(ContractRequest request): Retrieves project details for SMS notifications.
* **getOrgDetailsForCBOAdmin**(ContractRequest request): Retrieves organization details for CBO administrators.
* **getMessage**(ContractRequest request, boolean isCBORole): Retrieves the message based on the contract action.
* **getMessage**(ContractRequest request, String msgCode): Retrieves the message from localization.
* **buildMessageForRevisedContract**(Map\<String, String> userDetailsForSMS, String message, Boolean isSendBack): Builds the message for revised contracts.
* **buildMessageForRejectAction**(Contract contract, Map\<String, String> userDetailsForSMS, String message): Builds the message for reject actions.
* **buildMessageForApproveActionWOCBO**(Contract contract, Map\<String, String> userDetailsForSMS, String message): Builds the message for approve actions by WO creator.
* **buildMessageForWOCreator**(Contract contract, Map\<String, String> userDetailsForSMS, String message): Builds the message for WO creator.
* **buildMessageForDeclineAction\_WOCreator**(Contract contract, Map\<String, String> userDetailsForSMS, String message): Builds the message for decline actions by WO creator.
* **buildMessageForAcceptAction\_WOCreator**(Contract contract, Map\<String, String> userDetailsForSMS, String message): Builds the message for accept actions by WO creator.
* **getLocalisedMessages**(RequestInfo requestInfo, String tenantId, String locale, String module): Retrieves localized messages.
* **getShortnerURL**(String actualURL): Retrieves the shortened URL.
* **setAdditionalFields**(ContractRequest request, String localizationCode,Map\<String, Object> additionalField ): Sets additional fields for notifications.
* **checkAdditionalFieldAndPushONSmsTopic**(String customizedMessage, Map\<String, Object> additionalField,Map\<String,String> smsDetails): Checks additional fields and pushes notifications onto the SMS topic.
