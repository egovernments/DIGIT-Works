package org.egov.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Constants;
import org.egov.repository.PIRepository;
import org.egov.utils.BillUtils;
import org.egov.utils.PIUtils;
import org.egov.web.models.bill.Payment;
import org.egov.web.models.bill.PaymentRequest;
import org.egov.web.models.enums.*;
import org.egov.web.models.jit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class PDService {

    @Autowired
    private PaymentInstructionService paymentInstructionService;

    @Autowired
    private IfmsService ifmsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PIRepository piRepository;

    @Autowired
    private BillUtils billUtils;

    @Autowired
    private PIUtils piUtils;

    /**
     * Call the JIT system for which payment status is in-process and update the status of payment advice based
     * on the response.
     * @param requestInfo
     */
    public void updatePDStatus(RequestInfo requestInfo) {
        log.info("Start executing PD update service.");
        // get in-process payment instructions
        List<PaymentInstruction> inProcessPaymentInstructions =  piRepository.searchPi(PISearchCriteria.builder().piStatus(PIStatus.IN_PROCESS).build());

        // Create JIT requests for in-process PI
        for (PaymentInstruction paymentInstruction : inProcessPaymentInstructions) {
            log.info("Started Processing PI for PD : " + paymentInstruction.getJitBillNo());
            JitRespStatusForPI jitRespStatusForPI = null;

            PDRequest pdRequest = PDRequest.builder().finYear(paymentInstruction.getPaDetails().get(0).getPaFinYear())
                    .extAppName(Constants.JIT_FD_EXT_APP_NAME)
                    .billRefNo(paymentInstruction.getPaDetails().get(0).getPaBillRefNumber())
                    .tokenNumber(paymentInstruction.getPaDetails().get(0).getPaTokenNumber())
                    .tokenDate(paymentInstruction.getPaDetails().get(0).getPaTokenDate())
                    .build();

            JITResponse jitResponse = null;
            log.info("Calling ifms service.");
            try {
                jitResponse = ifmsService.sendRequestToIFMS(JITRequest.builder()
                        .serviceId(JITServiceId.PD).params(pdRequest).build());
            }catch (Exception e){
                log.info("Exception occurred while fetching PD from ifms." + e);
                jitRespStatusForPI = JitRespStatusForPI.STATUS_LOG_PD_ERROR;
            }

            if (jitResponse == null) {
                // Create PI status log based on current existing PD request
                paymentInstructionService.createAndSavePIStatusLog(paymentInstruction, JITServiceId.PD, jitRespStatusForPI, requestInfo);
                continue;
            }

            if (jitResponse.getErrorMsg() != null) {
                log.info("Error Response received for PD from IFMS." + jitResponse.getErrorMsgs());
                jitRespStatusForPI = JitRespStatusForPI.STATUS_LOG_PD_ERROR;
                // Create PI status log based on current existing PD request
                paymentInstructionService.createAndSavePIStatusLog(paymentInstruction, JITServiceId.PD, jitRespStatusForPI, requestInfo);
                continue;
            } else if (jitResponse.getData().isEmpty()) {
                jitRespStatusForPI = JitRespStatusForPI.STATUS_LOG_PD_NO_RESPONSE;
                // Create PI status log based on current existing PD request
                paymentInstructionService.createAndSavePIStatusLog(paymentInstruction, JITServiceId.PD, jitRespStatusForPI, requestInfo);
                continue;
            }
            log.info("Processing PD response for bill : " + paymentInstruction.getJitBillNo());
            for (Object data : jitResponse.getData()) {
                Map<String, Object> dataObjMap = objectMapper.convertValue(data, Map.class);
                if (dataObjMap.get("billNumber") == null)
                    continue;
                String voucherNo = dataObjMap.get("voucherNo").toString();
                String voucherDate = dataObjMap.get("voucherDate").toString();
                String paymentStatusMessage = dataObjMap.get("paymentStatus").toString();
                JsonNode benfDtlsNode = objectMapper.valueToTree(dataObjMap.get("benfDtls"));

                log.info("Updating beneficiaries details for PD.");

                // Updating beneficiary details.
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
                // Updating PI status to successful
                paymentInstruction.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                paymentInstruction.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
                paymentInstruction.setPiStatus(PIStatus.SUCCESSFUL);

                log.info("Updating PI status and details for PD : " + paymentInstruction.getJitBillNo());
                // Update PI DB based on updated PI
                piRepository.update(Collections.singletonList(paymentInstruction),null);
                // Update PI indexer based on updated PI
                piUtils.updatePIIndex(requestInfo, paymentInstruction);
                //Calling On disburse for the PI
                paymentInstructionService.processPIForOnDisburse(paymentInstruction,requestInfo,false);
                // Set pi status response
                jitRespStatusForPI = JitRespStatusForPI.STATUS_LOG_PD_SUCCESS;
                // Create PI status log based on current existing PD request
                paymentInstructionService.createAndSavePIStatusLog(paymentInstruction, JITServiceId.PD, jitRespStatusForPI, requestInfo);
            }
        }

    }

}