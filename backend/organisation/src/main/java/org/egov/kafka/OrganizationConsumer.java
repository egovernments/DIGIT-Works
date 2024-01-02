package org.egov.kafka;

import org.springframework.stereotype.Component;

@Component
public class OrganizationConsumer {

	/*
	 * Uncomment the below line to start consuming record from kafka.topics.consumer
	 * Value of the variable kafka.topics.consumer should be overwritten in
	 * application.properties
	 */
	// @KafkaListener(topics = {"kafka.topics.consumer"})
	public void listen() {
		// document why this method is empty
	}
}
