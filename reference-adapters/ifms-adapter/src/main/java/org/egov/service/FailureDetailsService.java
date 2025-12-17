package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Constants;
import org.egov.config.IfmsAdapterConfig;
import org.egov.repository.PIRepository;
import org.egov.repository.SanctionDetailsRepository;
import org.egov.utils.BillUtils;
import org.egov.utils.HelperUtil;
import org.egov.utils.PIUtils;
import org.egov.web.models.bill.*;
import org.egov.web.models.enums.*;
import org.egov.web.models.jit.*;
import org.egov.web.models.jit.PISearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FailureDetailsService {
    @Autowired
    private PaymentInstructionService paymentInstructionService;
    @Autowired
    private IfmsService ifmsService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HelperUtil helperUtil;
    @Autowired
    private BillUtils billUtils;
    @Autowired
    private PIRepository piRepository;
    @Autowired
    private PIUtils piUtils;
    @Autowired
    private IfmsAdapterConfig ifmsAdapterConfig;

    public void updateMockFailureDetails(RequestInfo requestInfo) {
        try {
            if (ifmsAdapterConfig.getIfmsJitMockEnabled() && ifmsAdapterConfig.getIfmsMockEnabledTenantsIds().length != 0) {
                log.info("FailureDetailsService::updateMockFailureDetails");
                JITRequest jitFDRequest = getFailedPayload();
                for (int idx = 0; idx < ifmsAdapterConfig.getIfmsMockEnabledTenantsIds().length; idx++) {
                    JITResponse fdResponse = ifmsService.sendRequest(ifmsAdapterConfig.getIfmsMockEnabledTenantsIds()[idx], jitFDRequest);
                    if (fdResponse != null && fdResponse.getErrorMsg() == null) {
                        for (Object failedPI: fdResponse.getData()) {
                            JsonNode node = objectMapper.valueToTree(failedPI);
                            JsonNode benef = node.get("benfDtls");
                            if (benef != null && benef.isArray() && !benef.isEmpty()) {
                                processPiForFailedTransaction(node, requestInfo, null, JITServiceId.FD);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("Exception in FailureDetailsService:updateMockFailureDetails : " + e.getMessage());
        }

    }
    public void updateFailureDetails(RequestInfo requestInfo) {
        try {
            JITRequest jitFDRequest = getFailedPayload();
            JITResponse fdResponse = ifmsService.sendRequestToIFMS(jitFDRequest);
            if (fdResponse != null && fdResponse.getErrorMsg() == null) {
                for (Object failedPI: fdResponse.getData()) {
                    JsonNode node = objectMapper.valueToTree(failedPI);
                    JsonNode benef = node.get("benfDtls");
                    if (benef != null && benef.isArray() && !benef.isEmpty()) {
                        processPiForFailedTransaction(node, requestInfo, null, JITServiceId.FD);
                    }
                }
            }
        } catch (Exception e) {
            log.info("Exception in FailureDetailsService:updateFailureDetails : " + e.getMessage());
        }

    }

    private void processPiForFailedTransaction(JsonNode failedPiResponse, RequestInfo requestInfo, PaymentInstruction pi, JITServiceId serviceId) {
        String piNumber = null;
        if (failedPiResponse.findValue("billNumber") != null) {
            piNumber = failedPiResponse.findValue("billNumber").asText();
        } else if (failedPiResponse.findValue("jitCorBillNo") != null) {
            piNumber = failedPiResponse.findValue("jitCorBillNo").asText();
        }

        if (pi == null) {
            pi = getCompletedPaymentInstructionByBill(piNumber);
        }

        if (pi != null) {
            Map<String, JsonNode> failedBeneficiariesMapById =  getFailedBeneficiaryMap(failedPiResponse);

            updatePiAndPaymentForFailedBenef(pi, failedBeneficiariesMapById);
            addReversalTransactionAndUpdatePIPa(pi, requestInfo);
            // Set success response based on service id
            JitRespStatusForPI jitRespStatusForPI =  serviceId == JITServiceId.FD ? JitRespStatusForPI.STATUS_LOG_FD_SUCCESS: JitRespStatusForPI.STATUS_LOG_FTFPS_SUCCESS;
            // Create PI status log based on current existing PIS request
            paymentInstructionService.createAndSavePIStatusLog(pi, serviceId, jitRespStatusForPI, requestInfo);
        }

    }

    private JITRequest getFailedPayload() {

        Map<String, String> failedRequestParams = new HashMap<>();
        Long subtractedTimeMillis = System.currentTimeMillis() - (120 * 60L * 60L * 1000L);
        String finYear = helperUtil.getFormattedTimeFromTimestamp(subtractedTimeMillis, "yyyy");
        String voucherDate = helperUtil.getFormattedTimeFromTimestamp(subtractedTimeMillis, "yyyy-MM-dd");
        failedRequestParams.put("finYear", finYear);
        failedRequestParams.put("voucherDate", voucherDate);
        failedRequestParams.put("extAppName", Constants.JIT_FD_EXT_APP_NAME);
        JITRequest jitRequest = JITRequest.builder()
                .serviceId(JITServiceId.FD)
                .params(failedRequestParams)
                .build();
        return jitRequest;
    }

    private PaymentInstruction getCompletedPaymentInstructionByBill(String billNo){
        PISearchCriteria piSearchCriteria = PISearchCriteria.builder().jitBillNo(billNo).piStatus(PIStatus.SUCCESSFUL).build();
        List<PaymentInstruction> paymentInstructions = piRepository.searchPi(piSearchCriteria);
        if (!paymentInstructions.isEmpty()) {
            return paymentInstructions.get(0);
        } else {
            return null;
        }
    }

    private Map<String, JsonNode> getFailedBeneficiaryMap(JsonNode failedPi) {
        Map<String, JsonNode> failedPiBenfMap = new HashMap<>();
        JsonNode benfDtlsNode = failedPi.get("benfDtls");
        if (benfDtlsNode.isArray() && !benfDtlsNode.isEmpty()) {
            for (JsonNode benf: benfDtlsNode) {
                String benfId = benf.get("benfId").asText();
                failedPiBenfMap.put(benfId, benf);
            }
        }
        return failedPiBenfMap;
    }

    private void updatePiAndPaymentForFailedBenef(PaymentInstruction pi, Map<String, JsonNode> failedBeneficiariesMapById) {
        for (Beneficiary beneficiary: pi.getBeneficiaryDetails()) {
            String benfNumber = beneficiary.getBeneficiaryNumber();
            if (failedBeneficiariesMapById.get(benfNumber) != null && !failedBeneficiariesMapById.get(benfNumber).isEmpty()) {
                JsonNode benfDtl = failedBeneficiariesMapById.get(benfNumber);
                beneficiary.setPaymentStatus(BeneficiaryPaymentStatus.FAILED);
                beneficiary.setPaymentStatusMessage(benfDtl.get("failedReason").asText());
                beneficiary.setChallanNumber(benfDtl.get("challanNumber").asText());
                beneficiary.setChallanDate(benfDtl.get("challanDate").asText());
            }
        }
    }

    private void addReversalTransactionAndUpdatePIPa(PaymentInstruction pi, RequestInfo requestInfo) {
        try {
            Long currentTime = System.currentTimeMillis();
            String userId = requestInfo.getUserInfo().getUuid();
            BigDecimal amount = new BigDecimal(0);
            pi.setPiStatus(PIStatus.PARTIAL);
            for (Beneficiary beneficiary: pi.getBeneficiaryDetails()) {
                if (beneficiary.getPaymentStatus().equals(BeneficiaryPaymentStatus.FAILED)) {
                    amount = amount.add(beneficiary.getAmount());
                    beneficiary.getAuditDetails().setLastModifiedBy(userId);
                    beneficiary.getAuditDetails().setLastModifiedTime(currentTime);
                }
            }
            piRepository.update(Collections.singletonList(pi), null);
            // Update PI indexer based on updated PI
            piUtils.updatePIIndex(requestInfo, pi);
            //Call On disburse for the PI
            paymentInstructionService.processPIForOnDisburse(pi, requestInfo, false);

        } catch (Exception e) {
            log.error("Failed in FailureDetailsService:addReversalTransactionAndUpdatePIPa " + e);
        }
    }

//    private void updatePaymentStatusForPartial(Payment payment, RequestInfo requestInfo) {
//        try {
//            log.info("Updating payment status for partial.");
//            boolean updatePaymentStatus = false;
//            for (PaymentBill bill: payment.getBills()) {
//                boolean updateBillStatus = false;
//                for (PaymentBillDetail billDetail: bill.getBillDetails()) {
//                    boolean updateBillDetailsStatus = false;
//                    for (PaymentLineItem lineItem : billDetail.getPayableLineItems()) {
//                        if (lineItem.getStatus().equals(PaymentStatus.FAILED)) {
//                            updateBillDetailsStatus = true;
//                        }
//                    }
//                    if (updateBillDetailsStatus) {
//                        billDetail.setStatus(PaymentStatus.PARTIAL);
//                        updateBillStatus = true;
//                    }
//                }
//                if (updateBillStatus) {
//                    bill.setStatus(PaymentStatus.PARTIAL);
//                    updatePaymentStatus = true;
//                }
//            }
//            if (updatePaymentStatus) {
//                payment.setStatus(PaymentStatus.PARTIAL);
//                payment.setReferenceStatus(ReferenceStatus.PAYMENT_PARTIAL);
//                PaymentRequest paymentRequest = PaymentRequest.builder().requestInfo(requestInfo).payment(payment).build();
//                billUtils.callPaymentUpdate(paymentRequest);
//            }
//        }catch (Exception e) {
//            log.error("Exception while updating the payment status FailureDetailsService:updatePaymentStatusForPartial : " + e);
//        }
//    }


    public void updateFTPSStatus(RequestInfo requestInfo) {
        log.info("Start executing FTPS update service.");

        // GET revised Payment instructions for FTPS where status is INITIATED
        PISearchCriteria revisedPiSearchCriteria = PISearchCriteria.builder().piType(PIType.REVISED).piStatus(PIStatus.INITIATED).build();
        List<PaymentInstruction> revisedPI =  piRepository.searchPi(revisedPiSearchCriteria);

        // Process all revised Payment instructions
        for (PaymentInstruction pi : revisedPI) {
            JitRespStatusForPI jitRespStatusForPI = null;
            PISearchCriteria originalPiSearchCriteria = PISearchCriteria.builder().jitBillNo(pi.getParentPiNumber()).build();
            PaymentInstruction originalPI =  piRepository.searchPi(originalPiSearchCriteria).get(0);

            FTPSRequest ftpsRequest = FTPSRequest.builder()
                    .jitCorBillNo(pi.getJitBillNo())
                    .jitCorBillDate(helperUtil.getFormattedTimeFromTimestamp(pi.getAuditDetails().getCreatedTime(), Constants.VA_REQUEST_TIME_FORMAT))
                    .jitCorBillDeptCode(Constants.JIT_FD_EXT_APP_NAME)
                    .jitOrgBillNo(originalPI.getJitBillNo())
                    .jitOrgBillRefNo(originalPI.getPaDetails().get(0).getPaBillRefNumber())
                    .jitOrgBillDate(helperUtil.getFormattedTimeFromTimestamp(originalPI.getAuditDetails().getCreatedTime(), Constants.VA_REQUEST_TIME_FORMAT))
                    .build();

            JITResponse jitResponse = null;
            try {
                jitResponse = ifmsService.sendRequest(pi.getTenantId(), JITRequest.builder().serviceId(JITServiceId.FTPS).params(ftpsRequest).build());
            } catch (Exception e) {
                log.info("Exception occurred while fetching FTPS from ifms." + e);
                jitRespStatusForPI = JitRespStatusForPI.STATUS_LOG_FTPS_ERROR;
            }

            // IF FTPS api is not available it will be null
            if (jitResponse == null) {
                // Create PI status log based on current existing PIS request
                paymentInstructionService.createAndSavePIStatusLog(pi, JITServiceId.FTPS, jitRespStatusForPI, requestInfo);
                continue;
            }

            // If We got an error or no response from FTPS API then set the error message according to that and create log
            if (jitResponse.getErrorMsg() != null || jitResponse.getData().isEmpty()) {
                jitRespStatusForPI = jitResponse.getErrorMsg() != null ? JitRespStatusForPI.STATUS_LOG_FTPS_ERROR : JitRespStatusForPI.STATUS_LOG_FTPS_NO_RESPONSE;
                paymentInstructionService.createAndSavePIStatusLog(pi, JITServiceId.FTPS, jitRespStatusForPI, requestInfo);
                continue;
            }
            // If FTPS api has rSUCCESSFULesponse then process the response
            for (Object data : jitResponse.getData()) {
                processFTPSResponse(data, pi, requestInfo);
            }
        }
    }

    private void processFTPSResponse(Object ftpsResponse, PaymentInstruction paymentInstruction, RequestInfo requestInfo) {
        try {
            Map<String, Object> dataObjMap = objectMapper.convertValue(ftpsResponse, Map.class);
            JitRespStatusForPI jitRespStatusForPI = null;

            if (dataObjMap.get("jitCorBillNo") != null) {

                String voucherNo = dataObjMap.get("voucherNo").toString();
                String voucherDate = dataObjMap.get("voucherDate").toString();
                String paymentStatusMessage = dataObjMap.get("paymentStatus").toString();
                String billRefNo = dataObjMap.get("billRefNo").toString();
                String tokenNumber = dataObjMap.get("tokenNumber").toString();
                String tokenDate = dataObjMap.get("tokenDate").toString();
                JsonNode benfDtlsNode = objectMapper.valueToTree(dataObjMap.get("benfDtls"));

                // Set pa  details
                paymentInstruction.getPaDetails().get(0).setPaBillRefNumber(billRefNo);
                paymentInstruction.getPaDetails().get(0).setPaTokenNumber(tokenNumber);
                paymentInstruction.getPaDetails().get(0).setPaTokenDate(tokenDate);
                paymentInstruction.getPaDetails().get(0).getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                paymentInstruction.getPaDetails().get(0).getAuditDetails().setLastModifiedTime(System.currentTimeMillis());

                // Set beneficiaries  details
                for (Beneficiary beneficiaryFromDB : paymentInstruction.getBeneficiaryDetails()) {
                    if (benfDtlsNode.isArray() && !benfDtlsNode.isEmpty()) {
                        for (JsonNode benf : benfDtlsNode) {
                            if (beneficiaryFromDB.getBeneficiaryNumber().equalsIgnoreCase(benf.get("benfId").asText())) {
                                String dateFormat = "yyyy-MM-dd";
                                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                                long voucherNumberTimestamp = 1;
                                try {
                                    Date date = sdf.parse(voucherDate);
                                    voucherNumberTimestamp = date.getTime();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                beneficiaryFromDB.setVoucherNumber(voucherNo);
                                beneficiaryFromDB.setVoucherDate(voucherNumberTimestamp);
                                beneficiaryFromDB.setPaymentStatus(BeneficiaryPaymentStatus.SUCCESS);
                                beneficiaryFromDB.setPaymentStatusMessage(paymentStatusMessage);
                                beneficiaryFromDB.setBenfAcctNo(benf.get("benfAcctNo").asText());
                                beneficiaryFromDB.setBenfBankIfscCode(benf.get("benfBankIfscCode").asText());
                                beneficiaryFromDB.setUtrNo(benf.get("utrNo").asText());
                                beneficiaryFromDB.setUtrDate(benf.get("utrDate").asText());
                                beneficiaryFromDB.setEndToEndId(benf.get("endToEndId").asText());
                                beneficiaryFromDB.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                                beneficiaryFromDB.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
                            }
                        }
                    }
                }
                paymentInstruction.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                paymentInstruction.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
                paymentInstruction.setPiStatus(PIStatus.SUCCESSFUL);
                piRepository.update(Collections.singletonList(paymentInstruction),null);
                piUtils.updatePIIndex(requestInfo, paymentInstruction);
                jitRespStatusForPI = JitRespStatusForPI.STATUS_LOG_FTPS_SUCCESS;
                paymentInstructionService.processPIForOnDisburse(paymentInstruction,requestInfo,false);
            } else {
                jitRespStatusForPI = JitRespStatusForPI.STATUS_LOG_FTPS_NO_RESPONSE;
            }
            // Create PI status log based on current existing PIS request
            paymentInstructionService.createAndSavePIStatusLog(paymentInstruction, JITServiceId.FTPS, jitRespStatusForPI, requestInfo);
        } catch (Exception e) {
            log.info("Exception in FailedPaymentUpdateService:processFTPSResponse " + e);
        }

    }

    public void updateFTFPSStatus(RequestInfo requestInfo) {
        log.info("Start executing FTFPS update service.");
        PISearchCriteria revisedPiSearchCriteria = PISearchCriteria.builder().piType(PIType.REVISED).piStatus(PIStatus.SUCCESSFUL).build();
        List<PaymentInstruction> revisedPIs =  piRepository.searchPi(revisedPiSearchCriteria);

        for (PaymentInstruction pi : revisedPIs) {
            JitRespStatusForPI jitRespStatusForPI = null;
            PISearchCriteria originalPiSearchCriteria = PISearchCriteria.builder().jitBillNo(pi.getParentPiNumber()).build();
            PaymentInstruction originalPI =  piRepository.searchPi(originalPiSearchCriteria).get(0);
            FTFPSRequest ftfpsRequest = FTFPSRequest.builder()
                    .jitCorBillNo(pi.getJitBillNo())
                    .jitCorBillDate(helperUtil.getFormattedTimeFromTimestamp(pi.getAuditDetails().getCreatedTime(), Constants.VA_REQUEST_TIME_FORMAT))
                    .billRefNo(originalPI.getPaDetails().get(0).getPaBillRefNumber())
                    .tokenNumber(pi.getPaDetails().get(0).getPaTokenNumber())
                    .tokenDate(pi.getPaDetails().get(0).getPaTokenDate())
                    .build();
            JITResponse jitResponse = null;
            try {
                jitResponse = ifmsService.sendRequest(pi.getTenantId(), JITRequest.builder()
                        .serviceId(JITServiceId.FTFPS).params(ftfpsRequest).build());
            } catch (Exception e) {
                log.info("Exception occurred while fetching FTFPS from ifms." + e);
                jitRespStatusForPI = JitRespStatusForPI.STATUS_LOG_FTFPS_ERROR;
            }

            if (jitResponse == null) {
                // Create PI status log based on current existing PIS request
                paymentInstructionService.createAndSavePIStatusLog(pi, JITServiceId.FTFPS, jitRespStatusForPI, requestInfo);
                continue;
            }

            if (jitResponse.getErrorMsg() != null || jitResponse.getData().isEmpty()) {
                jitRespStatusForPI = jitResponse.getErrorMsg() != null ? JitRespStatusForPI.STATUS_LOG_FTFPS_ERROR : JitRespStatusForPI.STATUS_LOG_FTFPS_NO_RESPONSE;
                // Create PI status log based on current existing FTFPS request
                paymentInstructionService.createAndSavePIStatusLog(pi, JITServiceId.FTFPS, jitRespStatusForPI, requestInfo);
                continue;
            }

            for (Object failedPI: jitResponse.getData()) {
                JsonNode node = objectMapper.valueToTree(failedPI);
                JsonNode benef = node.get("benfDtls");
                if (benef != null && benef.isArray() && !benef.isEmpty()) {
                    processPiForFailedTransaction(node, requestInfo, pi, JITServiceId.FTFPS);
                }
            }
        }
    }

}
