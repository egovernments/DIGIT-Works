package org.egov.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.service.PaymentInstructionService;
import org.egov.web.models.bill.PaymentRequest;
import org.egov.web.models.jit.PaymentInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BillConsumer {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PaymentInstructionService piService;

    @KafkaListener(topics = {"${payment.create.topic}"})
    public void listen(PaymentRequest paymentRequest) {
        try {
            PaymentInstruction paymentInstruction = piService.processPaymentRequestForPI(paymentRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
