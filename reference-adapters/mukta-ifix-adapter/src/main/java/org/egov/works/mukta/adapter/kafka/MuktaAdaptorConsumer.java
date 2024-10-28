package org.egov.works.mukta.adapter.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.config.Constants;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.enrichment.PaymentInstructionEnrichment;
import org.egov.works.mukta.adapter.repository.ServiceRequestRepository;
import org.egov.works.mukta.adapter.service.DisbursementService;
import org.egov.works.mukta.adapter.service.PaymentInstructionService;
import org.egov.works.mukta.adapter.service.PaymentService;
import org.egov.works.mukta.adapter.service.RedisService;
import org.egov.works.mukta.adapter.util.ProgramServiceUtil;
import org.egov.works.mukta.adapter.validators.DisbursementValidator;
import org.egov.works.mukta.adapter.web.models.*;
import org.egov.works.mukta.adapter.web.models.enums.*;
import org.egov.works.mukta.adapter.web.models.jit.PaymentInstruction;
import org.egov.works.services.common.models.attendance.RequestInfoWrapper;
import org.egov.works.services.common.models.estimate.EstimateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    private final ServiceRequestRepository restRepo;


    @Autowired
    public MuktaAdaptorConsumer(ObjectMapper objectMapper, PaymentInstructionService paymentInstructionService, ProgramServiceUtil programServiceUtil, MuktaAdaptorProducer muktaAdaptorProducer, MuktaAdaptorConfig muktaAdaptorConfig, PaymentService paymentService, DisbursementValidator disbursementValidator, PaymentInstructionEnrichment paymentInstructionEnrichment, RedisService redisService, ServiceRequestRepository restRepo) {
        this.objectMapper = objectMapper;
        this.paymentInstructionService = paymentInstructionService;
        this.programServiceUtil = programServiceUtil;
        this.muktaAdaptorProducer = muktaAdaptorProducer;
        this.muktaAdaptorConfig = muktaAdaptorConfig;
        this.paymentService = paymentService;
        this.disbursementValidator = disbursementValidator;
        this.paymentInstructionEnrichment = paymentInstructionEnrichment;
        this.redisService = redisService;
        this.restRepo = restRepo;
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

    @KafkaListener(topics = {"${ifms.pi.index.internal.topic}"})
    public void piListener(final String record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            log.info("Payment instruction index data received on.");
            Map<String, Object> indexerRequest = objectMapper.readValue(record, Map.class);
            RequestInfo requestInfo = objectMapper.convertValue(indexerRequest.get("RequestInfo"), RequestInfo.class);
            PaymentInstruction pi = objectMapper.convertValue(indexerRequest.get("paymentInstruction"), PaymentInstruction.class);
            Map<String, Object> additionalDetails = (Map<String, Object>) pi.getAdditionalDetails();

            // fetch estimate from work order
            String projectNumber = fetchProjectNumber(requestInfo,
                    ((Map<String, List<Object>>) pi.getAdditionalDetails()).get("referenceIds").get(0).toString(),
                    pi.getTenantId());

            additionalDetails.put("projectNumber", projectNumber);
            pi.setAdditionalDetails(additionalDetails);
            indexerRequest.replace("paymentInstruction", pi);
            muktaAdaptorProducer.push(muktaAdaptorConfig.getIfmsPiEnrichmentTopic(),indexerRequest);
            log.info("Payment instruction index data received on. " + pi);
        } catch (Exception e) {
            log.error("Error occurred while processing the consumed save estimate record from topic : " + topic, e);
        }
    }

    private String fetchProjectNumber(RequestInfo requestInfo, String referenceId, String tenantId) {
        StringBuilder urlWithParams = new StringBuilder(muktaAdaptorConfig.getEstimateHost()+muktaAdaptorConfig.getEstimateSearchEndpoint());
        urlWithParams.append("?tenantId=").append(tenantId).append("&referenceNumber=").append(referenceId);

        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        Object response = restRepo.fetchResult(urlWithParams, requestInfoWrapper);
        EstimateResponse estimateResponse = null;
        try {
            estimateResponse = objectMapper.convertValue(response, EstimateResponse.class);
        } catch (Exception e) {
            log.error("Error occurred while fetching project number from Estimate", e);
            throw new CustomException("Error occurred while fetching project number from Estimate", e.toString());
        }
        return estimateResponse.getEstimates().get(0).getReferenceNumber();
    }
}
