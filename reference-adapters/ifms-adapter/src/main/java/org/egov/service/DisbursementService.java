package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.IfmsAdapterConfig;
import org.egov.enrichment.PaymentInstructionEnrichment;
import org.egov.repository.PIRepository;
import org.egov.repository.SanctionDetailsRepository;
import org.egov.tracer.model.CustomException;
import org.egov.utils.EncryptionDecryptionUtil;
import org.egov.utils.MdmsUtils;
import org.egov.utils.PIUtils;
import org.egov.validators.DisbursementValidator;
import org.egov.web.models.*;
import org.egov.web.models.enums.*;
import org.egov.web.models.jit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DisbursementService {
    private final DisbursementValidator disbursementValidator;
    private final PIRepository piRepository;
    private final PaymentInstructionEnrichment paymentInstructionEnrichment;
    private final MdmsUtils mdmsUtils;
    private final SanctionDetailsRepository sanctionDetailsRepository;
    private final IfmsService ifmsService;
    private final ObjectMapper objectMapper;
    private final PIUtils piUtils;
    private final EncryptionDecryptionUtil encryptionDecryptionUtil;
    private final IfmsAdapterConfig ifmsAdapterConfig;


    @Autowired
    public DisbursementService(DisbursementValidator disbursementValidator, PIRepository piRepository, PaymentInstructionEnrichment paymentInstructionEnrichment, MdmsUtils mdmsUtils, SanctionDetailsRepository sanctionDetailsRepository, IfmsService ifmsService, PISService pisService, ObjectMapper objectMapper, PIUtils piUtils, EncryptionDecryptionUtil encryptionDecryptionUtil, IfmsAdapterConfig ifmsAdapterConfig) {
        this.disbursementValidator = disbursementValidator;
        this.piRepository = piRepository;
        this.paymentInstructionEnrichment = paymentInstructionEnrichment;
        this.mdmsUtils = mdmsUtils;
        this.sanctionDetailsRepository = sanctionDetailsRepository;
        this.ifmsService = ifmsService;
        this.objectMapper = objectMapper;
        this.piUtils = piUtils;
        this.encryptionDecryptionUtil = encryptionDecryptionUtil;
        this.ifmsAdapterConfig = ifmsAdapterConfig;
    }

    /**
     * Process the disbursement request and create a payment instruction.
     * @param disbursementRequest
     * @return
     */
    public DisbursementResponse processDisbursementRequest(DisbursementRequest disbursementRequest) {
        PaymentStatus paymentStatus;
        PaymentInstruction lastPI = null;
        PaymentInstruction originalPI = null;
        RequestInfo requestInfo = RequestInfo.builder().build();
        Map<String,Map<String,JSONArray>> mdmsData = mdmsUtils.fetchHoaAndSSUDetails(requestInfo,disbursementRequest.getMessage().getLocationCode());
        disbursementValidator.validateDisbursementRequest(disbursementRequest,mdmsData);
        PISearchCriteria piSearchCriteria = PISearchCriteria.builder()
                .muktaReferenceId(disbursementRequest.getMessage().getTargetId())
                .tenantId(disbursementRequest.getMessage().getLocationCode())
                .sortBy(PISearchCriteria.SortBy.createdTime)
                .sortOrder(PISearchCriteria.SortOrder.DESC)
                .isActive(true)
                .build();
        List<PaymentInstruction> paymentInstructions = piRepository.searchPi(piSearchCriteria);
        SanctionDetailsSearchCriteria sanctionDetailsSearchCriteria = SanctionDetailsSearchCriteria.builder()
                .ids(Collections.singletonList(disbursementRequest.getMessage().getSanctionId()))
                .tenantId(disbursementRequest.getMessage().getLocationCode())
                .build();
        List<SanctionDetail> sanctionDetails = sanctionDetailsRepository.getSanctionDetails(sanctionDetailsSearchCriteria);
        if(sanctionDetails.isEmpty()){
            throw new CustomException("INVALID_SANCTION_ID","Sanction Id is invalid for the disbursement Request.");
        }
        if(!paymentInstructions.isEmpty()){
            for(PaymentInstruction paymentInstruction:paymentInstructions){
                if(!paymentInstruction.getPiStatus().equals(PIStatus.FAILED) && lastPI == null){
                    lastPI = paymentInstruction;
                    originalPI = paymentInstruction;
                }else if(!paymentInstruction.getPiStatus().equals(PIStatus.FAILED)){
                    originalPI = paymentInstruction;
                }
            }
        }
        PaymentInstruction paymentInstructionFromDisbursement;
        // Check if last PI is in PARTIAL status, if yes then process it for revised PI creation
        if(lastPI != null && lastPI.getPiStatus().equals(PIStatus.PARTIAL)){
            log.info("Payment Instruction is in PARTIAL status, processing it for revised PI.");
            lastPI = encryptionDecryptionUtil.decryptObject(lastPI, ifmsAdapterConfig.getPaymentInstructionEncryptionKey(), PaymentInstruction.class, requestInfo);
            originalPI = encryptionDecryptionUtil.decryptObject(originalPI, ifmsAdapterConfig.getPaymentInstructionEncryptionKey(), PaymentInstruction.class, requestInfo);
            paymentInstructionFromDisbursement = paymentInstructionEnrichment.enrichPaymentIntsructionsFromDisbursementRequest(disbursementRequest,mdmsData,sanctionDetails.get(0),true,lastPI);

            // Check if COR PI Request is valid based on 90 Days and 30th April scenario
            if(isValidForCORPIRequest(paymentInstructionFromDisbursement, originalPI)){
                paymentStatus =  processDisbursementForRevisedPICreation(paymentInstructionFromDisbursement, requestInfo,lastPI,originalPI);
            }else{
                paymentStatus = processDisbursementForPICreation(disbursementRequest, paymentInstructionFromDisbursement, requestInfo, sanctionDetails);
            }

        }else{
            log.info("Payment Instruction is not in PARTIAL status, processing it for PI creation.");
            disbursementValidator.validatePI(paymentInstructions);
            paymentInstructionFromDisbursement = paymentInstructionEnrichment.enrichPaymentIntsructionsFromDisbursementRequest(disbursementRequest,mdmsData,sanctionDetails.get(0),false,lastPI);
            paymentStatus = processDisbursementForPICreation(disbursementRequest, paymentInstructionFromDisbursement, requestInfo, sanctionDetails);
        }
        log.info("Saving PI data.");
        // Subtract amount from available amount in Sanction Details if PI is in INITIATED status and parent PI number is null
        if (paymentStatus.equals(PaymentStatus.INITIATED) && paymentInstructionFromDisbursement.getParentPiNumber() == null) {
            sanctionDetails.get(0).getFundsSummary().setAvailableAmount(sanctionDetails.get(0).getFundsSummary().getAvailableAmount().subtract(paymentInstructionFromDisbursement.getGrossAmount()));
            sanctionDetails.get(0).getFundsSummary().getAuditDetails().setLastModifiedTime(paymentInstructionFromDisbursement.getAuditDetails().getLastModifiedTime());
            sanctionDetails.get(0).getFundsSummary().getAuditDetails().setLastModifiedBy(paymentInstructionFromDisbursement.getAuditDetails().getLastModifiedBy());
        }
        if(paymentStatus.equals(PaymentStatus.FAILED)){
            for(Beneficiary beneficiary: paymentInstructionFromDisbursement.getBeneficiaryDetails()) {
                beneficiary.setPaymentStatus(BeneficiaryPaymentStatus.FAILED);
            }
        }
        // Encrypt PI and save it to DB
        paymentInstructionFromDisbursement = encryptionDecryptionUtil.encryptObject(paymentInstructionFromDisbursement, ifmsAdapterConfig.getStateLevelTenantId(),ifmsAdapterConfig.getPaymentInstructionEncryptionKey(), PaymentInstruction.class);
        piRepository.save(Collections.singletonList(paymentInstructionFromDisbursement), paymentStatus.equals(PaymentStatus.FAILED) ? null:sanctionDetails.get(0).getFundsSummary(), paymentStatus);
        piUtils.updatePIIndex(requestInfo, paymentInstructionFromDisbursement);

        return enrichDisbursementResponse(disbursementRequest,paymentInstructionFromDisbursement);
    }

    /**
     * Process the revised PI creation for the disbursement request.
     * @param paymentInstructionFromDisbursement
     * @param requestInfo
     * @param lastPI
     * @param originalPI
     * @return
     */
    private PaymentStatus processDisbursementForRevisedPICreation(PaymentInstruction paymentInstructionFromDisbursement, RequestInfo requestInfo, PaymentInstruction lastPI, PaymentInstruction originalPI) {
        PaymentStatus paymentStatus = null;
        try {
            CORRequest corRequest = paymentInstructionEnrichment.getCorPaymentInstructionRequestForIFMS(paymentInstructionFromDisbursement,lastPI,originalPI);
            JITRequest jitRequest = JITRequest.builder()
                    .serviceId(JITServiceId.COR)
                    .params(corRequest)
                    .build();
            try {
                JITResponse jitResponse = ifmsService.sendRequestToIFMS(jitRequest);
                if(jitResponse.getErrorMsg() == null && !jitResponse.getData().isEmpty()){
                    paymentStatus = PaymentStatus.INITIATED;
                    try {
                        Object piResponseNode = jitResponse.getData().get(0);
                        JsonNode node = objectMapper.valueToTree(piResponseNode);
                        String piSuccessCode = node.get("successCode").asText();
                        String piSucessDescrp = node.get("sucessDescrp").asText();
                        paymentInstructionFromDisbursement.setPiSuccessCode(piSuccessCode);
                        paymentInstructionFromDisbursement.setPiSuccessDesc(piSucessDescrp);
                    }catch (Exception e){
                        log.info("Exception while parsing COR success response " + e);
                    }
                }else{
                    paymentStatus = PaymentStatus.PARTIAL;
                    try {
                        String piErrorDescrp = null;
                        // Set error response to PI
                        if (jitResponse.getErrorMsg() != null) {
                            piErrorDescrp = jitResponse.getErrorMsg();
                        } else if (jitResponse.getErrorMsgs() != null && !jitResponse.getErrorMsgs().isEmpty()) {
                            piErrorDescrp = jitResponse.getErrorMsgs().toString();
                        }
                        paymentInstructionFromDisbursement.setPiErrorResp(piErrorDescrp);
                    }catch (Exception e) {
                        log.info("Exception while parsing COR Error response " + e);
                    }
                }
            }catch (Exception e){
                paymentStatus = PaymentStatus.PARTIAL;
                String errorMessage;
                Throwable cause = e.getCause();
                if (cause instanceof HttpServerErrorException.InternalServerError) {
                    errorMessage = ((HttpServerErrorException.InternalServerError) cause).getResponseBodyAsString();
                } else {
                    errorMessage = e.getMessage();
                }
                log.error("Exception while calling request." + e);
                paymentInstructionFromDisbursement.setPiErrorResp(errorMessage);
            }
            updatePIDateForRevisedPI(paymentInstructionFromDisbursement,lastPI,originalPI,paymentStatus,requestInfo);
        }catch (Exception e){
            log.info("Exception " + e);
            throw new CustomException("ERROR_REVISED_PI","Exception while generating revised PI" + e);
        }
        return paymentStatus;
    }
    /**
     * Update PI data for revised PI.
     * @param paymentInstructionFromDisbursement
     * @param lastPI
     * @param originalPI
     * @param paymentStatus
     * @param requestInfo
     */
    private void updatePIDateForRevisedPI(PaymentInstruction paymentInstructionFromDisbursement, PaymentInstruction lastPI, PaymentInstruction originalPI, PaymentStatus paymentStatus,RequestInfo requestInfo) {
        log.info("Updating PI data for revised PI.");
        if(paymentStatus.equals(PaymentStatus.PARTIAL)){
            paymentInstructionFromDisbursement.setPiStatus(PIStatus.FAILED);
            paymentInstructionFromDisbursement.setIsActive(false);

            PaymentInstruction lastPiForUpdate = lastPI == null ? originalPI : lastPI;
            Map<String, Beneficiary> beneficiaryMap = new HashMap<>();
            if (lastPiForUpdate != null && lastPiForUpdate.getBeneficiaryDetails() != null && !lastPiForUpdate.getBeneficiaryDetails().isEmpty()) {
                beneficiaryMap = lastPiForUpdate.getBeneficiaryDetails().stream()
                        .collect(Collectors.toMap(Beneficiary::getBeneficiaryNumber, Function.identity()));
            }

            for(Beneficiary beneficiary: paymentInstructionFromDisbursement.getBeneficiaryDetails()) {
                beneficiary.setPaymentStatus(BeneficiaryPaymentStatus.FAILED);
                if (beneficiaryMap.containsKey(beneficiary.getBeneficiaryNumber())) {
                    beneficiary.setPaymentStatusMessage(beneficiaryMap.get(beneficiary.getBeneficiaryNumber()).getPaymentStatusMessage());
                }
            }
        }

        if(paymentStatus.equals(PaymentStatus.INITIATED)){
            paymentInstructionFromDisbursement.setIsActive(true);
            PaymentInstruction lastPiForUpdate = lastPI == null ? originalPI : lastPI;
            // Update last revised PI status to COMPLETED
            lastPiForUpdate.setPiStatus(PIStatus.COMPLETED);
            lastPiForUpdate.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
            lastPiForUpdate.getAuditDetails().setLastModifiedBy(paymentInstructionFromDisbursement.getAuditDetails().getLastModifiedBy());
            piRepository.update(Collections.singletonList(lastPiForUpdate), null);
            piUtils.updatePIIndex(requestInfo, lastPiForUpdate);
        }
    }
    /**
     * Process the disbursement request for PI creation.
     * @param disbursementRequest
     * @return
     */
    private PaymentStatus processDisbursementForPICreation(DisbursementRequest disbursementRequest, PaymentInstruction paymentInstructionFromDisbursement, RequestInfo requestInfo,List<SanctionDetail> sanctionDetails) {
        PaymentStatus paymentStatus = null;
        if(sanctionDetails.get(0).getFundsSummary().getAvailableAmount().compareTo(disbursementRequest.getMessage().getGrossAmount()) < 0){
            log.info("Fund not available processing payment instruction for FAILED.");
            // Get enriched PI request to store on DB
            paymentStatus = PaymentStatus.FAILED;
            paymentInstructionFromDisbursement.setPiStatus(PIStatus.FAILED);
            paymentInstructionFromDisbursement.setPiErrorResp(ReferenceStatus.PAYMENT_INSUFFICIENT_FUNDS.toString());

            for(Beneficiary beneficiary: paymentInstructionFromDisbursement.getBeneficiaryDetails()) {
                beneficiary.setPaymentStatus(BeneficiaryPaymentStatus.FAILED);
            }

            return paymentStatus;
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
            String errorMessage;
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

        return paymentStatus;
    }
    /**
     * Enrich the disbursement response with the status and transaction id.
     * @param disbursementRequest
     * @param paymentInstructionFromDisbursement
     * @return
     */
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
        disbursementResponse.getMessage().setTransactionId(paymentInstructionFromDisbursement.getJitBillNo());
        HashMap<String, Beneficiary> muktaRefIdToBenefPayementStatusMap = new HashMap<>();
        for(Beneficiary beneficiary:paymentInstructionFromDisbursement.getBeneficiaryDetails()){
            muktaRefIdToBenefPayementStatusMap.put(beneficiary.getMuktaReferenceId(),beneficiary);
        }
        for(Disbursement disbursement: disbursementResponse.getMessage().getDisbursements()){
            Beneficiary beneficiary = muktaRefIdToBenefPayementStatusMap.get(disbursement.getTargetId());
            disbursement.setTransactionId(beneficiary.getBeneficiaryNumber());
            disbursement.getStatus().setStatusCode(statusCode);
            disbursement.getStatus().setStatusMessage(statusMessage);
        }

        return disbursementResponse;
    }

    /**
     * Method to convert epochDateTimeFormat to LocalDateTime
     * @param epochTime The epoch time in milliseconds
     * @return The corresponding LocalDateTime object
    */
    private LocalDateTime convertEpochToLocalDateTime(long epochTime) {
        Instant instant = Instant.ofEpochMilli(epochTime);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * Method to get the financial year of the given date
     * @param date The date for which the financial year is needed
     * @return The financial year in the format "YYYY-YYYY"
     */
    private String getFinancialYear(LocalDateTime date){

        int year = date.getYear();
        int month = date.getMonthValue();

        // Check if the date falls in the current or next financial year
        int startYear, endYear;
        if (month < 4) { // Before April, so belongs to the previous financial year
            startYear = year - 1;
            endYear = year;
        } else {
            startYear = year;
            endYear = year + 1;
        }

        return startYear + "-" + endYear;
    }

    /**
     * This method validates the 90Days scenario and return true is COR PI request is valid else return false to create new PI
     * @param paymentInstructionFromDisbursement The payment instruction received from the disbursement
     * @param originalPI The last payment instruction
     * @return The status of the payment
     */
    private boolean isValidForCORPIRequest(PaymentInstruction paymentInstructionFromDisbursement, PaymentInstruction originalPI){

        LocalDateTime originalPICreatedDate = convertEpochToLocalDateTime(originalPI.getAuditDetails().getCreatedTime());
        LocalDateTime corPICreatedDate = convertEpochToLocalDateTime(paymentInstructionFromDisbursement.getAuditDetails().getCreatedTime());
        LocalDateTime failureDate = convertEpochToLocalDateTime(originalPI.getAuditDetails().getLastModifiedTime());  // lastModifiedDate of the originalPI
        LocalDateTime failureDatePlus90 = failureDate.plusDays(90);

        // Check if financial year of COR PI Request createdDate and OriginalPI createdDate is same
        if(getFinancialYear(originalPICreatedDate).equals(getFinancialYear(corPICreatedDate))){

            // Check if corPICreatedDate <= (originalPIFailedDate + 90 days)
            if(corPICreatedDate.isBefore(failureDatePlus90) || corPICreatedDate.isEqual(failureDatePlus90)){
                // Normal Flow
                log.info("Payment Instruction is valid for Correction PI Request for same financial year.");
                return true;
            }else{
                // Create New PI
                log.info("New Payment Instruction is created due to 90 days scenario.");
                return false;
            }

        }else{  // If financial year is not same

            // Check if (corPICreatedDate <= (originalPIFailedDate + 90 days)) and corPICreatedDate <= 30th April 23:59:59
            if((corPICreatedDate.isBefore(failureDatePlus90) || corPICreatedDate.isEqual(failureDatePlus90))
                    && corPICreatedDate.getMonth().equals(Month.APRIL) && corPICreatedDate.getDayOfMonth() <= 30){
                // Normal flow
                log.info("Payment Instruction is valid for Correction PI Request.");
                return true;
            }else{
                // Create new PI
                log.info("New Payment Instruction is created due to 90 days or 30th April scenario");
                return false;
            }

        }
    }
}
