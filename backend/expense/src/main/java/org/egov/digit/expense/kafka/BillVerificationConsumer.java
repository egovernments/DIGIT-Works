package org.egov.digit.expense.kafka;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.service.BillVerificationService;
import org.egov.digit.expense.web.models.BillVerificationTask;
import org.egov.digit.expense.web.models.BillVerificationTaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BillVerificationConsumer {

    private final ObjectMapper objectMapper;

    private final BillVerificationService billVerificationService;

    @Autowired
    public BillVerificationConsumer(ObjectMapper objectMapper, BillVerificationService billVerificationService) {
        this.objectMapper = objectMapper;
        this.billVerificationService = billVerificationService;
    }

    @KafkaListener(topics = {"${expense.billing.bill.verify}"})
    public void listen(final Map<String, Object> message) {
        log.info("Consuming message from kafka topic: " + message);
        BillVerificationTaskRequest billVerificationTaskRequest = objectMapper.convertValue(message,BillVerificationTaskRequest.class);
        billVerificationService.verify(billVerificationTaskRequest);
    }
}
