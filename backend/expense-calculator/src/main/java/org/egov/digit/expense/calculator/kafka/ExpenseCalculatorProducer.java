package org.egov.digit.expense.calculator.kafka;

import org.egov.tracer.kafka.CustomKafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

// NOTE: If tracer is disabled change CustomKafkaTemplate to KafkaTemplate in autowiring

@Service
@Slf4j
public class ExpenseCalculatorProducer {

	private final CustomKafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	public ExpenseCalculatorProducer(CustomKafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void push(String topic, Object value) {
		kafkaTemplate.send(topic, value);
	}
}
