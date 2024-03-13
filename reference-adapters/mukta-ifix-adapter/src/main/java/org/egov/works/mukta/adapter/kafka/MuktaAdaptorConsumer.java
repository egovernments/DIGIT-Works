package org.egov.works.mukta.adapter.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.constants.Error;
import org.egov.works.mukta.adapter.enrichment.PaymentInstructionEnrichment;
import org.egov.works.mukta.adapter.service.PaymentInstructionService;
import org.egov.works.mukta.adapter.service.PaymentService;
import org.egov.works.mukta.adapter.util.BillUtils;
import org.egov.works.mukta.adapter.util.ProgramServiceUtil;
import org.egov.works.mukta.adapter.validators.DisbursementValidator;
import org.egov.works.mukta.adapter.web.models.Disbursement;
import org.egov.works.mukta.adapter.web.models.DisbursementCreateRequest;
import org.egov.works.mukta.adapter.web.models.DisbursementRequest;
import org.egov.works.mukta.adapter.web.models.MsgHeader;
import org.egov.works.mukta.adapter.web.models.bill.PaymentRequest;
import org.egov.works.mukta.adapter.web.models.enums.*;
import org.egov.works.mukta.adapter.web.models.jit.PaymentInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    private final PaymentInstructionEnrichment paymentInstructionEnrichment;

    @Autowired
    public MuktaAdaptorConsumer(ObjectMapper objectMapper, PaymentInstructionService paymentInstructionService, ProgramServiceUtil programServiceUtil, MuktaAdaptorProducer muktaAdaptorProducer, MuktaAdaptorConfig muktaAdaptorConfig, PaymentService paymentService, DisbursementValidator disbursementValidator, PaymentInstructionEnrichment paymentInstructionEnrichment) {
        this.objectMapper = objectMapper;
        this.paymentInstructionService = paymentInstructionService;
        this.programServiceUtil = programServiceUtil;
        this.muktaAdaptorProducer = muktaAdaptorProducer;
        this.muktaAdaptorConfig = muktaAdaptorConfig;
        this.paymentService = paymentService;
        this.disbursementValidator = disbursementValidator;
        this.paymentInstructionEnrichment = paymentInstructionEnrichment;
    }
    /**
     * The function listens to the payment create topic and processes the payment request
     * @param record The payment request
     * @param topic The topic name
     */
    @KafkaListener(topics = {"${payment.create.topic}"})
    public void listen(final String record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        PaymentRequest paymentRequest = null;
        Disbursement disbursement = null;
        PaymentInstruction pi = null;
        String signature = "Signature:  namespace=\\\"g2p\\\", kidId=\\\"{sender_id}|{unique_key_id}|{algorithm}\\\", algorithm=\\\"ed25519\\\", created=\\\"1606970629\\\", expires=\\\"1607030629\\\", headers=\\\"(created) (expires) digest\\\", signature=\\\"Base64(signing content)";
        MsgHeader msgHeader = null;
        Boolean isRevised = false;
        try {
            log.info("Payment data received on.");
            paymentRequest = objectMapper.readValue(record, PaymentRequest.class);
            log.info("Payment data is " + paymentRequest);
            isRevised = disbursementValidator.isValidForDisbursementCreate(paymentRequest);
            disbursement = paymentInstructionService.processPaymentInstruction(paymentRequest,isRevised);
            pi = paymentInstructionEnrichment.getPaymentInstructionFromDisbursement(disbursement);
            msgHeader = programServiceUtil.getMessageCallbackHeader(paymentRequest.getRequestInfo(), muktaAdaptorConfig.getStateLevelTenantId());
            msgHeader.setAction(Action.CREATE);
            msgHeader.setMessageType(MessageType.DISBURSE);
            DisbursementRequest disbursementRequest = DisbursementRequest.builder().header(msgHeader).message(disbursement).signature(signature).build();
            muktaAdaptorProducer.push(muktaAdaptorConfig.getDisburseCreateTopic(), disbursementRequest);
            paymentInstructionService.updatePIIndex(paymentRequest.getRequestInfo(), pi,isRevised);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            programServiceUtil.callProgramServiceDisbursement(disbursementRequest);
        } catch (Exception e) {
            paymentService.updatePaymentStatusToFailed(paymentRequest);
            // If disbursement is not null, enrich its status
            if (disbursement != null) {
                paymentInstructionEnrichment.enrichDisbursementStatus(disbursement, StatusCode.FAILED,e.getMessage());
                DisbursementRequest disbursementRequest = DisbursementRequest.builder().header(msgHeader).message(disbursement).signature(signature).build();
                muktaAdaptorProducer.push(muktaAdaptorConfig.getDisburseUpdateTopic(), disbursementRequest);
                pi.setPiStatus(PIStatus.FAILED);
                pi.setPiErrorResp(e.getMessage());
                paymentInstructionService.updatePIIndex(paymentRequest.getRequestInfo(), pi,isRevised);
            } else {
                // If disbursement is null, log the error and throw a custom exception
                log.error("Disbursement is null. Cannot enrich status.");
                throw new CustomException(Error.DISBURSEMENT_ENRICHMENT_FAILED, e.getMessage());
            }
            log.error("Error occurred while processing the consumed save estimate record from topic : " + topic, e);
            throw new CustomException("Error occurred while processing the consumed save estimate record from topic : " + topic, e.toString());
        }
    }
}
