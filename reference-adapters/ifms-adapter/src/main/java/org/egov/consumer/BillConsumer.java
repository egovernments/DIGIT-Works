package org.egov.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.service.PaymentInstruction;
import org.egov.web.models.bill.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class BillConsumer {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PaymentInstruction paymentInstruction;

    @KafkaListener(topics = {"${billing.payment.create}"})
    public void listen(HashMap<String, Object> record) {
        PaymentRequest paymentRequest = objectMapper.convertValue(record, PaymentRequest.class);
        try {
            paymentInstruction.getPaymentInstructionFromPayment(paymentRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
