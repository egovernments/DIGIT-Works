package org.egov.works.mukta.adapter.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.config.Constants;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.enrichment.PaymentInstructionEnrichment;
import org.egov.works.mukta.adapter.service.DisbursementService;
import org.egov.works.mukta.adapter.service.PaymentInstructionService;
import org.egov.works.mukta.adapter.service.PaymentService;
import org.egov.works.mukta.adapter.service.RedisService;
import org.egov.works.mukta.adapter.util.ProgramServiceUtil;
import org.egov.works.mukta.adapter.validators.DisbursementValidator;
import org.egov.works.mukta.adapter.web.models.Disbursement;
import org.egov.works.mukta.adapter.web.models.DisbursementRequest;
import org.egov.works.mukta.adapter.web.models.ErrorRes;
import org.egov.works.mukta.adapter.web.models.MsgHeader;
import org.egov.works.mukta.adapter.web.models.bill.PaymentRequest;
import org.egov.works.mukta.adapter.web.models.enums.*;
import org.egov.works.mukta.adapter.web.models.jit.PaymentInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Collections;

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
    private final RedisService redisService;

    @Autowired
    public MuktaAdaptorConsumer(ObjectMapper objectMapper, PaymentInstructionService paymentInstructionService, ProgramServiceUtil programServiceUtil, MuktaAdaptorProducer muktaAdaptorProducer, MuktaAdaptorConfig muktaAdaptorConfig, PaymentService paymentService, DisbursementValidator disbursementValidator, PaymentInstructionEnrichment paymentInstructionEnrichment, RedisService redisService) {
        this.objectMapper = objectMapper;
        this.paymentInstructionService = paymentInstructionService;
        this.programServiceUtil = programServiceUtil;
        this.muktaAdaptorProducer = muktaAdaptorProducer;
        this.muktaAdaptorConfig = muktaAdaptorConfig;
        this.paymentService = paymentService;
        this.disbursementValidator = disbursementValidator;
        this.paymentInstructionEnrichment = paymentInstructionEnrichment;
        this.redisService = redisService;
    }
    /**
     * The function listens to the payment create topic and processes the payment request
     * @param record The payment request
     * @param topic The topic name
     */
    @KafkaListener(topics = {"${payment.create.topic}"})
    public void listen(final String record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        PaymentRequest paymentRequest = null;
        Disbursement encryptedDisbursement = null;
        PaymentInstruction pi = null;
        MsgHeader msgHeader = null;
        Boolean isRevised = false;
        try {
            log.info("Payment data received on.");
            paymentRequest = objectMapper.readValue(record, PaymentRequest.class);
            Boolean isDisbursementCreated = disbursementValidator.isDisbursementCreated(paymentRequest);
            if(Boolean.FALSE.equals(isDisbursementCreated))
            {
                log.info("Payment data is " + paymentRequest);
                isRevised = disbursementValidator.isValidForDisbursementCreate(paymentRequest);
                Disbursement disbursement = paymentInstructionService.processPaymentInstruction(paymentRequest,isRevised);
                encryptedDisbursement = paymentInstructionEnrichment.encriptDisbursement(disbursement);
                pi = paymentInstructionEnrichment.getPaymentInstructionFromDisbursement(encryptedDisbursement);
                msgHeader = programServiceUtil.getMessageCallbackHeader(paymentRequest.getRequestInfo(), muktaAdaptorConfig.getStateLevelTenantId());
                msgHeader.setAction(Action.CREATE);
                msgHeader.setMessageType(MessageType.DISBURSE);
                DisbursementRequest disbursementRequest = DisbursementRequest.builder().header(msgHeader).message(disbursement).build();
                DisbursementRequest encriptedDisbursementRequest = DisbursementRequest.builder().header(msgHeader).message(encryptedDisbursement).build();
                muktaAdaptorProducer.push(muktaAdaptorConfig.getDisburseCreateTopic(), encriptedDisbursementRequest);
                paymentInstructionService.updatePIIndex(paymentRequest.getRequestInfo(), pi,isRevised);
                redisService.setCacheForDisbursement(encryptedDisbursement);
                programServiceUtil.callProgramServiceDisbursement(disbursementRequest);
            }else{
                log.info("Payment data is already processed.");
            }
        } catch (Exception e) {
            log.error("Error occurred while processing the consumed save estimate record from topic : " + topic, e);
            log.info("Pushing data to error queue");
            ErrorRes errorRes = ErrorRes.builder().message(e.getMessage()).objects(Collections.singletonList(paymentRequest.getPayment())).build();
            muktaAdaptorProducer.push(muktaAdaptorConfig.getMuktaIfixAdapterErrorQueueTopic(), errorRes);
            // If disbursement is not null, enrich its status
            if (encryptedDisbursement != null) {
                paymentInstructionEnrichment.enrichDisbursementStatus(encryptedDisbursement, StatusCode.FAILED,e.getMessage());
                DisbursementRequest disbursementRequest = DisbursementRequest.builder().header(msgHeader).message(encryptedDisbursement).build();
                muktaAdaptorProducer.push(muktaAdaptorConfig.getDisburseUpdateTopic(), disbursementRequest);
                pi.setPiStatus(PIStatus.FAILED);
                pi.setPiErrorResp(e.getMessage());
                paymentInstructionService.updatePIIndex(paymentRequest.getRequestInfo(), pi,isRevised);
                redisService.setCacheForDisbursement(encryptedDisbursement);
            } else {
                // If disbursement is null, log the error and throw a custom exception
                log.error("Disbursement is null. Cannot enrich status.");
            }
            paymentService.updatePaymentStatusToFailed(paymentRequest);
            throw new CustomException("Error occurred while processing the consumed save estimate record from topic : " + topic, e.toString());
        }
    }
}
