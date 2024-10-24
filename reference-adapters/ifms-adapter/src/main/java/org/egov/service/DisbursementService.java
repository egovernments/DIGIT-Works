package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.IfmsAdapterConfig;
import org.egov.enrichment.PaymentInstructionEnrichment;
import org.egov.kafka.IfmsAdapterProducer;
import org.egov.repository.PIRepository;
import org.egov.repository.SanctionDetailsRepository;
import org.egov.tracer.model.CustomException;
import org.egov.utils.EncryptionDecryptionUtil;
import org.egov.utils.MdmsUtils;
import org.egov.utils.PIUtils;
import org.egov.validators.DisbursementValidator;
import org.egov.web.models.Disbursement;
import org.egov.web.models.DisbursementRequest;
import org.egov.web.models.DisbursementResponse;
import org.egov.web.models.ErrorRes;
import org.egov.web.models.enums.*;
import org.egov.web.models.jit.*;
import org.egov.works.services.common.models.expense.enums.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
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
    private final PaymentInstructionService paymentInstructionService;
    private final IfmsAdapterProducer ifmsAdapterProducer;


    @Autowired
    public DisbursementService(DisbursementValidator disbursementValidator, PIRepository piRepository, PaymentInstructionEnrichment paymentInstructionEnrichment, MdmsUtils mdmsUtils, SanctionDetailsRepository sanctionDetailsRepository, IfmsService ifmsService, PISService pisService, ObjectMapper objectMapper, PIUtils piUtils, EncryptionDecryptionUtil encryptionDecryptionUtil, IfmsAdapterConfig ifmsAdapterConfig, PaymentInstructionService paymentInstructionService, IfmsAdapterProducer ifmsAdapterProducer) {
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
        this.paymentInstructionService = paymentInstructionService;
        this.ifmsAdapterProducer = ifmsAdapterProducer;
    }

    /**
     * Process the disbursement request and create a payment instruction.
     *
     * @param disbursementRequest
     * @return
     */
    public DisbursementResponse processDisbursementRequest(DisbursementRequest disbursementRequest) {
        PaymentStatus paymentStatus;
        PaymentInstruction lastPI = null;
        PaymentInstruction lastOriginalPI = null;
        PaymentInstruction paymentInstructionFromDisbursement = null;
        RequestInfo requestInfo = RequestInfo.builder().build();
        Map<String, Map<String, JSONArray>> mdmsData = mdmsUtils.fetchHoaAndSSUDetails(requestInfo, disbursementRequest.getMessage().getLocationCode());
        disbursementValidator.validateDisbursementRequest(disbursementRequest, mdmsData);
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
        if (sanctionDetails.isEmpty()) {
            throw new CustomException("INVALID_SANCTION_ID", "Sanction Id is invalid for the disbursement Request.");
        }
        if (!paymentInstructions.isEmpty()) {
            for (PaymentInstruction paymentInstruction : paymentInstructions) {
                if (!paymentInstruction.getPiStatus().equals(PIStatus.FAILED)) {
                    if (lastOriginalPI == null && paymentInstruction.getParentPiNumber() == null) {
                        lastOriginalPI = paymentInstruction;
                    }

                    if (lastPI == null) {
                        lastPI = paymentInstruction;
                    }
                }
            }
        }

        boolean isValidForCORPIRequest = false;
        if (lastOriginalPI != null) {
            isValidForCORPIRequest = isValidForCORPIRequest(disbursementRequest.getMessage(), lastOriginalPI);
        }
        // Check if last PI is in PARTIAL status, if yes then process it for revised PI creation
        if (lastPI != null && lastPI.getPiStatus().equals(PIStatus.PARTIAL) && isValidForCORPIRequest) {
            log.info("Payment Instruction is in PARTIAL status, processing it for revised PI.");
            lastPI = encryptionDecryptionUtil.decryptObject(lastPI, ifmsAdapterConfig.getPaymentInstructionEncryptionKey(), PaymentInstruction.class, requestInfo);
            lastOriginalPI = encryptionDecryptionUtil.decryptObject(lastOriginalPI, ifmsAdapterConfig.getPaymentInstructionEncryptionKey(), PaymentInstruction.class, requestInfo);
            paymentInstructionFromDisbursement = paymentInstructionEnrichment.enrichPaymentIntsructionsFromDisbursementRequest(disbursementRequest, mdmsData, sanctionDetails.get(0), true, lastPI);
            paymentStatus = processDisbursementForRevisedPICreation(paymentInstructionFromDisbursement, requestInfo, lastPI, lastOriginalPI, sanctionDetails.get(0));
        } else {
            log.info("Payment Instruction is not in PARTIAL status, processing it for PI creation.");
            disbursementValidator.validatePI(paymentInstructions);
            paymentInstructionFromDisbursement = paymentInstructionEnrichment.enrichPaymentIntsructionsFromDisbursementRequest(disbursementRequest, mdmsData, sanctionDetails.get(0), false, lastPI);
            paymentStatus = processDisbursementForPICreation(disbursementRequest, paymentInstructionFromDisbursement, requestInfo, sanctionDetails);
            if (lastPI != null) {
                updatePIDateForRevisedPI(paymentInstructionFromDisbursement, lastPI, lastOriginalPI, paymentStatus, requestInfo);
            }
        }
        try {
            if (paymentStatus.equals(PaymentStatus.FAILED)) {
                for (Beneficiary beneficiary : paymentInstructionFromDisbursement.getBeneficiaryDetails()) {
                    beneficiary.setPaymentStatus(BeneficiaryPaymentStatus.FAILED);
                }
                if (paymentInstructionFromDisbursement.getParentPiNumber() == null) {
                    sanctionDetails.get(0).getFundsSummary().setAvailableAmount(sanctionDetails.get(0).getFundsSummary().getAvailableAmount().add(paymentInstructionFromDisbursement.getGrossAmount()));
                }
            }
            piRepository.update(Collections.singletonList(paymentInstructionFromDisbursement), sanctionDetails.get(0).getFundsSummary());

        } catch (Exception e) {
            log.info("Exception while saving PI data " + e);
            List<Object> errorObjects = new ArrayList<>();
            errorObjects.add(disbursementRequest);
            if (paymentInstructionFromDisbursement != null)
                errorObjects.add(paymentInstructionFromDisbursement);
            ErrorRes errorRes = ErrorRes.builder().message(e.getMessage()).objects(errorObjects).build();
            ifmsAdapterProducer.push(ifmsAdapterConfig.getIfixAdapterErrorQueueTopic(), errorRes);
        }

        piUtils.updatePIIndex(requestInfo, paymentInstructionFromDisbursement);
        return enrichDisbursementResponse(disbursementRequest, paymentInstructionFromDisbursement);
    }

    private void savePiInDatabase(PaymentInstruction paymentInstructionFromDisbursement, SanctionDetail sanctionDetail, PaymentStatus paymentStatus, RequestInfo requestInfo) {
        log.info("Saving PI data.");
        // Subtract amount from available amount in Sanction Details if PI is in INITIATED status and parent PI number is null
        if (paymentStatus.equals(PaymentStatus.INITIATED) && paymentInstructionFromDisbursement.getParentPiNumber() == null) {
            sanctionDetail.getFundsSummary().setAvailableAmount(sanctionDetail.getFundsSummary().getAvailableAmount().subtract(paymentInstructionFromDisbursement.getGrossAmount()));
            sanctionDetail.getFundsSummary().getAuditDetails().setLastModifiedTime(paymentInstructionFromDisbursement.getAuditDetails().getLastModifiedTime());
            sanctionDetail.getFundsSummary().getAuditDetails().setLastModifiedBy(paymentInstructionFromDisbursement.getAuditDetails().getLastModifiedBy());
        }
        // Encrypt PI and save it to DB
        try {
            paymentInstructionFromDisbursement = encryptionDecryptionUtil.encryptObject(paymentInstructionFromDisbursement, ifmsAdapterConfig.getStateLevelTenantId(), ifmsAdapterConfig.getPaymentInstructionEncryptionKey(), PaymentInstruction.class);
            piRepository.save(Collections.singletonList(paymentInstructionFromDisbursement), paymentStatus.equals(PaymentStatus.FAILED) ? null : sanctionDetail.getFundsSummary(), paymentStatus);
            piUtils.updatePIIndex(requestInfo, paymentInstructionFromDisbursement);
        } catch (Exception e) {
            log.info("Exception while encrypting PI data " + e);
            throw new CustomException("ENC_ERROR", "Exception while saving PI data " + e);
        }

    }

    /**
     * Process the revised PI creation for the disbursement request.
     *
     * @param paymentInstructionFromDisbursement
     * @return
     */
    private PaymentStatus processDisbursementForRevisedPICreation(PaymentInstruction paymentInstructionFromDisbursement, RequestInfo requestInfo, PaymentInstruction lastPI, PaymentInstruction originalPI, SanctionDetail sanctionDetail) {
        PaymentStatus paymentStatus = null;
        try {
            CORRequest corRequest = paymentInstructionEnrichment.getCorPaymentInstructionRequestForIFMS(paymentInstructionFromDisbursement, lastPI, originalPI);
            JITRequest jitRequest = JITRequest.builder()
                    .serviceId(JITServiceId.COR)
                    .params(corRequest)
                    .build();
            savePiInDatabase(paymentInstructionFromDisbursement, sanctionDetail, PaymentStatus.INITIATED, requestInfo);

            try {
                JITResponse jitResponse = ifmsService.sendRequest(originalPI.getTenantId(),jitRequest);
                if(jitResponse.getErrorMsg() == null && !jitResponse.getData().isEmpty()){
                    paymentStatus = PaymentStatus.INITIATED;
                    try {
                        Object piResponseNode = jitResponse.getData().get(0);
                        JsonNode node = objectMapper.valueToTree(piResponseNode);
                        String piSuccessCode = node.get("successCode").asText();
                        String piSucessDescrp = node.get("sucessDescrp").asText();
                        paymentInstructionFromDisbursement.setPiSuccessCode(piSuccessCode);
                        paymentInstructionFromDisbursement.setPiSuccessDesc(piSucessDescrp);
                    } catch (Exception e) {
                        log.info("Exception while parsing COR success response " + e);
                    }
                } else {
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
                    } catch (Exception e) {
                        log.info("Exception while parsing COR Error response " + e);
                    }
                }
            } catch (Exception e) {
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
            updatePIDateForRevisedPI(paymentInstructionFromDisbursement, lastPI, originalPI, paymentStatus, requestInfo);
        } catch (Exception e) {
            log.info("Exception " + e);
            throw new CustomException("ERROR_REVISED_PI", "Exception while generating revised PI" + e);
        }
        return paymentStatus;
    }

    /**
     * Update PI data for revised PI.
     *
     * @param paymentInstructionFromDisbursement
     * @param lastPI
     * @param originalPI
     * @param paymentStatus
     * @param requestInfo
     */
    private void updatePIDateForRevisedPI(PaymentInstruction paymentInstructionFromDisbursement, PaymentInstruction lastPI, PaymentInstruction originalPI, PaymentStatus paymentStatus, RequestInfo requestInfo) {
        log.info("Updating PI data for revised PI.");
        if (paymentStatus.equals(PaymentStatus.PARTIAL)) {
            paymentInstructionFromDisbursement.setPiStatus(PIStatus.FAILED);
            paymentInstructionFromDisbursement.setIsActive(false);

            PaymentInstruction lastPiForUpdate = lastPI == null ? originalPI : lastPI;
            Map<String, Beneficiary> beneficiaryMap = new HashMap<>();
            if (lastPiForUpdate != null && lastPiForUpdate.getBeneficiaryDetails() != null && !lastPiForUpdate.getBeneficiaryDetails().isEmpty()) {
                beneficiaryMap = lastPiForUpdate.getBeneficiaryDetails().stream()
                        .collect(Collectors.toMap(Beneficiary::getBeneficiaryNumber, Function.identity()));
            }

            for (Beneficiary beneficiary : paymentInstructionFromDisbursement.getBeneficiaryDetails()) {
                beneficiary.setPaymentStatus(BeneficiaryPaymentStatus.FAILED);
                if (beneficiaryMap.containsKey(beneficiary.getBeneficiaryNumber())) {
                    beneficiary.setPaymentStatusMessage(beneficiaryMap.get(beneficiary.getBeneficiaryNumber()).getPaymentStatusMessage());
                }
            }
        }

        if (paymentStatus.equals(PaymentStatus.INITIATED)) {
            paymentInstructionFromDisbursement.setIsActive(true);
            PaymentInstruction lastPiForUpdate = lastPI == null ? originalPI : lastPI;
            // Update last revised PI status to COMPLETED
            lastPiForUpdate.setPiStatus(PIStatus.COMPLETED);
            lastPiForUpdate.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
            lastPiForUpdate.getAuditDetails().setLastModifiedBy(paymentInstructionFromDisbursement.getAuditDetails().getLastModifiedBy());
            piRepository.update(Collections.singletonList(lastPiForUpdate), null);
            piUtils.updatePIIndex(requestInfo, lastPiForUpdate);
            paymentInstructionService.processPIForOnDisburse(lastPiForUpdate, requestInfo, true);
        }
    }

    /**
     * Process the disbursement request for PI creation.
     *
     * @param disbursementRequest
     * @return
     */
    private PaymentStatus processDisbursementForPICreation(DisbursementRequest disbursementRequest, PaymentInstruction paymentInstructionFromDisbursement, RequestInfo requestInfo, List<SanctionDetail> sanctionDetails) {
        PaymentStatus paymentStatus = null;
        if (sanctionDetails.get(0).getFundsSummary().getAvailableAmount().compareTo(disbursementRequest.getMessage().getGrossAmount()) < 0) {
            log.info("Fund not available processing payment instruction for FAILED.");
            // Get enriched PI request to store on DB
            throw new CustomException("FUND_NOT_AVAILABLE", "Fund not available for the disbursement Request.");
        }
        JITRequest jitRequest = paymentInstructionEnrichment.getJitPaymentInstructionRequestForIFMS(paymentInstructionFromDisbursement);
        savePiInDatabase(paymentInstructionFromDisbursement, sanctionDetails.get(0), PaymentStatus.INITIATED, requestInfo);
        try {
            log.info("Calling IFMS for JIT");
            JITResponse jitResponse = ifmsService.sendRequest(disbursementRequest.getMessage().getLocationCode(), jitRequest);
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
        } catch (Exception e) {
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
     *
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

        if (paymentInstructionFromDisbursement.getPiStatus().equals(PIStatus.INITIATED)) {
            statusMessage = paymentInstructionFromDisbursement.getPiSuccessDesc();
        }
        if (paymentInstructionFromDisbursement.getPiStatus().equals(PIStatus.FAILED)) {
            statusCode = StatusCode.FAILED;
            statusMessage = paymentInstructionFromDisbursement.getPiErrorResp();
        }

        disbursementResponse.getMessage().getStatus().setStatusCode(statusCode);
        disbursementResponse.getMessage().getStatus().setStatusMessage(statusMessage);
        disbursementResponse.getMessage().setTransactionId(paymentInstructionFromDisbursement.getJitBillNo());
        HashMap<String, Beneficiary> muktaRefIdToBenefPayementStatusMap = new HashMap<>();
        for (Beneficiary beneficiary : paymentInstructionFromDisbursement.getBeneficiaryDetails()) {
            for (BenfLineItems benfLineItems : beneficiary.getBenfLineItems()) {
                muktaRefIdToBenefPayementStatusMap.put(benfLineItems.getLineItemId(), beneficiary);
            }
        }
        for (Disbursement disbursement : disbursementResponse.getMessage().getDisbursements()) {
            Beneficiary beneficiary = muktaRefIdToBenefPayementStatusMap.get(disbursement.getTargetId());
            disbursement.setTransactionId(beneficiary.getBeneficiaryNumber());
            disbursement.getStatus().setStatusCode(statusCode);
            disbursement.getStatus().setStatusMessage(statusMessage);
        }

        return disbursementResponse;
    }

    /**
     * Method to convert epochDateTimeFormat to LocalDateTime
     *
     * @param epochTime The epoch time in milliseconds
     * @return The corresponding LocalDateTime object
     */
    private LocalDateTime convertEpochToLocalDateTime(long epochTime) {
        Instant instant = Instant.ofEpochMilli(epochTime);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * Method to get the financial year of the given date
     *
     * @param date The date for which the financial year is needed
     * @return The financial year in the format "YYYY-YYYY"
     */
    private String getFinancialYear(LocalDateTime date) {

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
     *
     * @param disbursement The payment instruction received from the disbursement
     * @param originalPI   The last payment instruction
     * @return The status of the payment
     */
    private boolean isValidForCORPIRequest(Disbursement disbursement, PaymentInstruction originalPI) {

        LocalDateTime originalPICreatedDate = convertEpochToLocalDateTime(originalPI.getAuditDetails().getCreatedTime());
        LocalDateTime corPICreatedDate = convertEpochToLocalDateTime(disbursement.getAuditDetails().getCreatedTime());
        LocalDateTime failureDatePlus90 = originalPICreatedDate.plusDays(ifmsAdapterConfig.getOriginalExpireDays());

        // Check if financial year of COR PI Request createdDate and OriginalPI createdDate is same
        if (getFinancialYear(originalPICreatedDate).equals(getFinancialYear(corPICreatedDate))) {

            // Check if corPICreatedDate <= (originalPIFailedDate + 90 days)
            if (!corPICreatedDate.isAfter(failureDatePlus90)) {
                // Normal Flow
                log.info("Payment Instruction is valid for Correction PI Request for same financial year.");
                return true;
            } else {
                // Create New PI
                log.info("New Payment Instruction is created due to 90 days scenario.");
                return false;
            }

        } else {  // If financial year is not same

            // Check if (corPICreatedDate <= (originalPIFailedDate + 90 days)) and corPICreatedDate <= 30th April 23:59:59
            LocalDateTime endOfApril = LocalDateTime.of(corPICreatedDate.getYear(), ifmsAdapterConfig.getOriginalExpireFinancialYearMonth(), ifmsAdapterConfig.getOriginalExpireFinancialYearDate(), 23, 59, 59);
            if (!corPICreatedDate.isAfter(endOfApril)) {
                // Normal flow
                log.info("Payment Instruction is valid for Correction PI Request.");
                return true;
            } else {
                // Create new PI
                log.info("New Payment Instruction is created due to 90 days or 30th April scenario");
                return false;
            }
        }
    }
}
