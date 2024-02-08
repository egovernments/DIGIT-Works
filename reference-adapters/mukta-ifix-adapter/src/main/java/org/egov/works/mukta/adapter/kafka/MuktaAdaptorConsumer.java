package org.egov.works.mukta.adapter.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.service.PaymentInstructionService;
import org.egov.works.mukta.adapter.service.PaymentService;
import org.egov.works.mukta.adapter.util.BillUtils;
import org.egov.works.mukta.adapter.util.ProgramServiceUtil;
import org.egov.works.mukta.adapter.validators.DisbursementValidator;
import org.egov.works.mukta.adapter.web.models.Disbursement;
import org.egov.works.mukta.adapter.web.models.DisbursementCreateRequest;
import org.egov.works.mukta.adapter.web.models.bill.PaymentRequest;
import org.egov.works.mukta.adapter.web.models.enums.PaymentStatus;
import org.egov.works.mukta.adapter.web.models.enums.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MuktaAdaptorConsumer {

    private final ObjectMapper objectMapper;
    private final PaymentInstructionService paymentInstructionService;
    private final ProgramServiceUtil programServiceUtil;
    private final MuktaAdaptorProducer muktaAdaptorProducer;
    private final MuktaAdaptorConfig muktaAdaptorConfig;
    private final PaymentService paymentService;
    private final DisbursementValidator disbursementValidator;

    @Autowired
    public MuktaAdaptorConsumer(ObjectMapper objectMapper, PaymentInstructionService paymentInstructionService, ProgramServiceUtil programServiceUtil, MuktaAdaptorProducer muktaAdaptorProducer, MuktaAdaptorConfig muktaAdaptorConfig, PaymentService paymentService, DisbursementValidator disbursementValidator) {
        this.objectMapper = objectMapper;
        this.paymentInstructionService = paymentInstructionService;
        this.programServiceUtil = programServiceUtil;
        this.muktaAdaptorProducer = muktaAdaptorProducer;
        this.muktaAdaptorConfig = muktaAdaptorConfig;
        this.paymentService = paymentService;
        this.disbursementValidator = disbursementValidator;
    }

    @KafkaListener(topics = {"${payment.create.topic}"})
    public void listen(final String record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        PaymentRequest paymentRequest = null;
        try {
            log.info("Payment data received on.");
            paymentRequest = objectMapper.readValue(record, PaymentRequest.class);
            log.info("Payment data is " + paymentRequest);
            disbursementValidator.isValidForDisbursementCreate(paymentRequest);
            Disbursement disbursement = paymentInstructionService.processPaymentInstruction(paymentRequest);
            if(disbursement.getStatus().getStatusCode().equals(StatusCode.FAILED)){
                paymentService.updatePaymentStatusToFailed(paymentRequest);
            }
            DisbursementCreateRequest disbursementRequest = DisbursementCreateRequest.builder().disbursement(disbursement).requestInfo(paymentRequest.getRequestInfo()).build();
//            programServiceUtil.callProgramServiceDisbursement(disbursementRequest);
            muktaAdaptorProducer.push(muktaAdaptorConfig.getDisburseCreateTopic(), disbursementRequest);
        } catch (Exception e) {
            paymentService.updatePaymentStatusToFailed(paymentRequest);
            log.error("Error occurred while processing the consumed save estimate record from topic : " + topic, e);
            throw new CustomException("Error occurred while processing the consumed save estimate record from topic : " + topic, e.toString());
        }
    }
}
