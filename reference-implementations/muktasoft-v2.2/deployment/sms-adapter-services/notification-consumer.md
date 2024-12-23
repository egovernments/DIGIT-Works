# Notification Consumer

* Package: org.egov.works.kafka
* Source File: NotificationConsumer.java
* Dependencies: Spring Framework, Kafka, Lombok

## Overview

The NotificationConsumer class is responsible for consuming messages from Kafka topics and forwarding them to the NotificationConsumerService for further processing within the eGov Works module.

## Functionality

The primary functionality of the NotificationConsumer class includes:

**Consuming Kafka Messages**: Messages are consumed from specified Kafka topics using Kafka listeners.

**Forwarding Messages**: Consumed messages are forwarded to the NotificationConsumerService for processing based on the topic.

## Usage

The NotificationConsumer class acts as a bridge between Kafka topics and the NotificationConsumerService, ensuring seamless message consumption and processing within the eGov Works module.
