package org.egov.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.producer.Producer;
import org.egov.config.IfmsAdapterConfig;
import org.egov.service.PaymentInstructionService;
import org.egov.service.PaymentService;
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

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private Producer producer;

    @Autowired
    private IfmsAdapterConfig serviceConfiguration;

    @KafkaListener(topics = {"${billing.payment.create}"})
    public void listen(PaymentRequest paymentRequest) {
        try {
            PaymentInstruction paymentInstruction = piService.processPaymentRequestForPI(paymentRequest);
        } catch (Exception e) {
            log.error("Error occurred while processing the consumed create payment request : " + paymentRequest, e);
        }
        try {
            paymentService.fetchedBillDetails(paymentRequest);
            producer.push(serviceConfiguration.getPaymentEnrichTopic(), paymentRequest);
        } catch (Exception e) {
            log.error("Error occurred while processing the consumed create payment request : " + paymentRequest, e);
        }
    }

    @KafkaListener(topics = {"${billing.payment.update}"})
    public void listenUpdate(PaymentRequest paymentRequest) {
        try {
            paymentService.fetchedBillDetails(paymentRequest);
            producer.push(serviceConfiguration.getPaymentEnrichTopic(), paymentRequest);
        } catch (Exception e) {
            log.error("Error occurred while processing the consumed update payment request : " + paymentRequest, e);
        }
    }
}
