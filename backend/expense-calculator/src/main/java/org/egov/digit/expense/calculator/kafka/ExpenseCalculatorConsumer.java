package org.egov.digit.expense.calculator.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.calculator.service.CalculatorService;
import org.egov.digit.expense.calculator.web.models.MusterRollRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
public class ExpenseCalculatorConsumer {

	@Autowired
	private CalculatorService calculatorService;

	@Autowired
	private ObjectMapper objectMapper;


	@KafkaListener(topics = {"${expense.calculator.consume.topic}"})
	public void listen(final String consumerRecord, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("ExpenseCalculatorConsumer:listen");
		try {
			MusterRollRequest request = objectMapper.readValue(consumerRecord, MusterRollRequest.class);
			//calculatorService.estimate(request);
		} catch (Exception exception) {
			log.error("Error occurred while processing the consumed muster record from topic : " + topic, exception);
			throw new RuntimeException(exception);
		}
	}



}
