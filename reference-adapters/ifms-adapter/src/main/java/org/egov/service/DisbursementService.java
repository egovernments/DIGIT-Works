package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.enrichment.PaymentInstructionEnrichment;
import org.egov.repository.PIRepository;
import org.egov.repository.SanctionDetailsRepository;
import org.egov.tracer.model.CustomException;
import org.egov.utils.MdmsUtils;
import org.egov.utils.PIUtils;
import org.egov.validators.DisbursementValidator;
import org.egov.web.models.Disbursement;
import org.egov.web.models.DisbursementRequest;
import org.egov.web.models.DisbursementResponse;
import org.egov.web.models.enums.*;
import org.egov.web.models.jit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DisbursementService {
    private final DisbursementValidator disbursementValidator;
    private final PIRepository piRepository;
    private final PaymentInstructionEnrichment paymentInstructionEnrichment;
    private final MdmsUtils mdmsUtils;
    private final SanctionDetailsRepository sanctionDetailsRepository;
    private final IfmsService ifmsService;
    private final PISService pisService;
    private final ObjectMapper objectMapper;
    private final PIUtils piUtils;


    @Autowired
    public DisbursementService(DisbursementValidator disbursementValidator, PIRepository piRepository, PaymentInstructionEnrichment paymentInstructionEnrichment, MdmsUtils mdmsUtils, SanctionDetailsRepository sanctionDetailsRepository, IfmsService ifmsService, PISService pisService, ObjectMapper objectMapper, PIUtils piUtils) {
        this.disbursementValidator = disbursementValidator;
        this.piRepository = piRepository;
        this.paymentInstructionEnrichment = paymentInstructionEnrichment;
        this.mdmsUtils = mdmsUtils;
        this.sanctionDetailsRepository = sanctionDetailsRepository;
        this.ifmsService = ifmsService;
        this.pisService = pisService;
        this.objectMapper = objectMapper;
        this.piUtils = piUtils;
    }
    public DisbursementResponse processDisbursementRequest(DisbursementRequest disbursementRequest) {
        PaymentStatus paymentStatus = null;
        disbursementValidator.validateDisbursementRequest(disbursementRequest);
        PISearchCriteria piSearchCriteria = PISearchCriteria.builder()
                .muktaReferenceId(disbursementRequest.getMessage().getTargetId())
                .tenantId(disbursementRequest.getMessage().getLocationCode())
                .build();

        List<PaymentInstruction> paymentInstructions = piRepository.searchPi(piSearchCriteria);
        RequestInfo requestInfo = RequestInfo.builder().userInfo(User.builder().uuid("ee3379e9-7f25-4be8-9cc1-dc599e1668c9").build()).build();
        Map<String,Map<String,JSONArray>> mdmsData = mdmsUtils.fetchHoaAndSSUDetails(requestInfo,disbursementRequest.getMessage().getLocationCode());
        SanctionDetailsSearchCriteria sanctionDetailsSearchCriteria = SanctionDetailsSearchCriteria.builder()
                .ids(Collections.singletonList(disbursementRequest.getMessage().getSanctionId()))
                .build();
        List<SanctionDetail> sanctionDetails = sanctionDetailsRepository.getSanctionDetails(sanctionDetailsSearchCriteria);

        disbursementValidator.validatePI(paymentInstructions,disbursementRequest,mdmsData);
        PaymentInstruction paymentInstructionFromDisbursement = paymentInstructionEnrichment.enrichPaymentIntsructionsFromDisbursementRequest(disbursementRequest,mdmsData,sanctionDetails.get(0));
        if(sanctionDetails.get(0).getFundsSummary().getAvailableAmount().compareTo(disbursementRequest.getMessage().getNetAmount()) < 0){
            log.info("Fund not available processing payment instruction for FAILED.");
            // Get enriched PI request to store on DB
            paymentStatus = PaymentStatus.FAILED;
            paymentInstructionFromDisbursement.setPiStatus(PIStatus.FAILED);
            paymentInstructionFromDisbursement.setPiErrorResp(ReferenceStatus.PAYMENT_INSUFFICIENT_FUNDS.toString());

            for(Beneficiary beneficiary: paymentInstructionFromDisbursement.getBeneficiaryDetails()) {
                beneficiary.setPaymentStatus(BeneficiaryPaymentStatus.FAILED);
            }
            log.info("Saving PI for failure, no funds available.");
            piRepository.save(Collections.singletonList(paymentInstructionFromDisbursement), null, paymentStatus);
            piUtils.updatePIIndex(requestInfo, paymentInstructionFromDisbursement);
            throw new CustomException("INSUFFICIENT_FUNDS","Insufficient funds in the system");
        }
        JITRequest jitRequest = paymentInstructionEnrichment.getJitPaymentInstructionRequestForIFMS(paymentInstructionFromDisbursement);
        try {
            log.info("Calling IFMS for JIT");
            JITResponse jitResponse = ifmsService.sendRequestToIFMS(jitRequest);
            log.info("IFMS response for JIT : " + jitResponse);
            if (jitResponse.getErrorMsg() == null && !jitResponse.getData().isEmpty()) {
                paymentStatus = PaymentStatus.INITIATED;
                Object piResponseNode = jitResponse.getData().get(0);
                JsonNode node = objectMapper.valueToTree(piResponseNode);
                String piSuccessCode = node.get("successCode").asText();
                String piSucessDescrp = node.get("sucessDescrp").asText();
                paymentInstructionFromDisbursement.setPiSuccessCode(piSuccessCode);
                paymentInstructionFromDisbursement.setPiSuccessDesc(piSucessDescrp);
            } else {
                paymentStatus = PaymentStatus.FAILED;
                paymentInstructionFromDisbursement.setPiStatus(PIStatus.FAILED);
                if (jitResponse.getErrorMsgs() != null && !jitResponse.getErrorMsgs().isEmpty())
                    paymentInstructionFromDisbursement.setPiErrorResp(jitResponse.getErrorMsgs().toString());
                else
                    paymentInstructionFromDisbursement.setPiErrorResp(jitResponse.getErrorMsg());
            }
        }catch (Exception e){
            log.info("Exception while creating a pi, setting payment status to FAILED.");
            paymentStatus = PaymentStatus.FAILED;
            String errorMessage = e.getMessage();
            Throwable cause = e.getCause();
            if (cause instanceof HttpServerErrorException.InternalServerError) {
                errorMessage = ((HttpServerErrorException.InternalServerError) cause).getResponseBodyAsString();
            } else {
                errorMessage = e.getMessage();
            }
            log.error("Exception while calling request." + e);
            paymentInstructionFromDisbursement.setPiStatus(PIStatus.FAILED);
            paymentInstructionFromDisbursement.setPiErrorResp(errorMessage);
        }
        if(paymentStatus.equals(PaymentStatus.FAILED)){
            for(Beneficiary beneficiary: paymentInstructionFromDisbursement.getBeneficiaryDetails()) {
                beneficiary.setPaymentStatus(BeneficiaryPaymentStatus.FAILED);
            }
        }
        log.info("Saving PI data.");
        piRepository.save(Collections.singletonList(paymentInstructionFromDisbursement), sanctionDetails.get(0).getFundsSummary(), paymentStatus);
        piUtils.updatePIIndex(requestInfo, paymentInstructionFromDisbursement);

        return enrichDisbursementResponse(disbursementRequest,paymentInstructionFromDisbursement);
    }

    private DisbursementResponse enrichDisbursementResponse(DisbursementRequest disbursementRequest, PaymentInstruction paymentInstructionFromDisbursement) {
        StatusCode statusCode = StatusCode.INITIATED;
        String statusMessage = null;
        DisbursementResponse disbursementResponse = DisbursementResponse.builder()
                .signature(disbursementRequest.getSignature())
                .header(disbursementRequest.getHeader())
                .message(disbursementRequest.getMessage())
                .build();

        if(paymentInstructionFromDisbursement.getPiStatus().equals(PIStatus.INITIATED)){
            statusMessage = paymentInstructionFromDisbursement.getPiSuccessDesc();
        }
        if(paymentInstructionFromDisbursement.getPiStatus().equals(PIStatus.FAILED)){
            statusCode = StatusCode.FAILED;
            statusMessage = paymentInstructionFromDisbursement.getPiErrorResp();
        }

        disbursementResponse.getMessage().getStatus().setStatusCode(statusCode);
        disbursementResponse.getMessage().getStatus().setStatusMessage(statusMessage);
        for(Disbursement disbursement: disbursementResponse.getMessage().getDisbursements()){
            disbursement.getStatus().setStatusCode(statusCode);
            disbursement.getStatus().setStatusMessage(statusMessage);
        }

        return disbursementResponse;
    }
}
