package org.egov.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.service.PaymentInstructionService;
import org.egov.web.models.bill.PaymentRequest;
import org.egov.web.models.jit.PaymentInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BillConsumer {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PaymentInstructionService piService;

    @KafkaListener(topics = {"${payment.create.topic}"})
    public void listen(final String record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            log.info("Payment data received on.");
            PaymentRequest paymentRequest = objectMapper.readValue(record, PaymentRequest.class);
            log.info("Payment data is " + paymentRequest);
            PaymentInstruction paymentInstruction = piService.processPaymentRequestForNewPI(paymentRequest);
        } catch (Exception e) {
            log.error("Error occurred while processing the consumed save estimate record from topic : " + topic, e);
            throw new RuntimeException(e);
        }
    }

}
