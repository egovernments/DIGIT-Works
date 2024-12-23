# Expense Notification Service

* Package: org.egov.works.service
* Source File: ExpenseNotificationService.java

## Overview

The ExpenseNotificationService class is responsible for processing notifications related to expenses within the eGov Works module. It handles the generation and sending of notifications for both purchase bills and supervision bills.

## Attributes

1. producer: Kafka producer for sending notifications.
2. config: Configuration settings for notification services.
3. hrmsUtils: Utility class for interacting with the HRMS (Human Resource Management System).
4. projectServiceUtil: Utility class for project-related operations.
5. locationServiceUtil: Utility class for location-related operations.
6. mapper: Object mapper for JSON serialization/deserialization.
7. repository: Repository for service requests.
8. notificationUtil: Utility class for notification-related operations.
9. localizationUtil: Utility class for localization operations.

## Methods

* process(final String record, @Header(KafkaHeaders.RECEIVED\_TOPIC) String topic): Processes the incoming Kafka message, determining whether it pertains to a purchase bill or a supervision bill, and sends the appropriate notification accordingly.
* sendNotificationForPurchaseBill(BillRequest billRequest): Sends notifications for purchase bills, handling approval and rejection actions.
* sendNotificationForSupervisionBill(BillRequest billRequest): Sends notifications for supervision bills.
* getMessage(RequestInfo requestInfo, String tenantId, String msgCode, Map\<String,Object> additionalFields): Retrieves the localized message for a given code and locale, optionally adding additional fields if required.
* buildMessageReplaceVariables(String message, String billNumber, String amount): Replaces variables in a message template with actual values.
* setAdditionalFields(RequestInfo requestInfo,String tenantId, String localizationCode, Map\<String,Object> additionalFields): Sets additional fields for the notification if required.
* checkAdditionalFieldAndPushONSmsTopic(String customizedMessage, Map\<String, Object> additionalFields, String mobileNumber): Checks if additional fields are present and sends the SMS request accordingly.

## Usage

Instantiate and configure the ExpenseNotificationService class within the application context to handle notifications related to expenses. Ensure that dependencies such as Kafka producer, configuration settings, and utility classes are properly injected.

