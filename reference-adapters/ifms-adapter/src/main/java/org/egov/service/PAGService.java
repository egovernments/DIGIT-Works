package org.egov.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.repository.PIRepository;
import org.egov.utils.BillUtils;
import org.egov.utils.HelperUtil;
import org.egov.utils.PIUtils;
import org.egov.web.models.enums.*;
import org.egov.web.models.jit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.egov.config.Constants.JIT_BILL_DATE_FORMAT;

@Service
@Slf4j
public class PAGService {

    private final PaymentInstructionService paymentInstructionService;
    private final IfmsService ifmsService;

    private final ObjectMapper objectMapper;

    private final PIRepository piRepository;

    private final HelperUtil helperUtil;

    private final PIUtils piUtils;


    @Autowired
    public PAGService(PaymentInstructionService paymentInstructionService, IfmsService ifmsService, ObjectMapper objectMapper, PIRepository piRepository, HelperUtil helperUtil, PIUtils piUtils) {
        this.paymentInstructionService = paymentInstructionService;
        this.ifmsService = ifmsService;
        this.objectMapper = objectMapper;
        this.piRepository = piRepository;
        this.helperUtil = helperUtil;
        this.piUtils = piUtils;
    }

    /**
     * Call the JIT system for which payment status is approved and update the status of payment advice based
     * on the response.
     * @param requestInfo
     */
    public void updatePAG( RequestInfo requestInfo ){
        log.info("Start executing PAG update service.");
        // gets approved payment instructions
        List<PaymentInstruction> paymentInstructions = getApprovedPaymentInstructions();
        for(PaymentInstruction paymentInstruction : paymentInstructions){
            log.info("Started Processing PI for PAG : " + paymentInstruction.getJitBillNo());
            JitRespStatusForPI jitRespStatusForPI = null;

            JSONArray ssuIaDetails = ifmsService.getSSUDetails(RequestInfo.builder().build(), paymentInstruction.getTenantId());
            Map<String,String> ssuIaDetailsMap = (Map<String, String>) ssuIaDetails.get(0);
            String ssuId = ssuIaDetailsMap.get("ssuId");

            PAGRequest pagRequest = PAGRequest.builder()
                    .pmtInstId(paymentInstruction.getPiApprovedId())
                    .pmtInstDate(paymentInstruction.getPiApprovalDate())
                    .billNo(paymentInstruction.getJitBillNo())
                    .billDate(helperUtil.getFormattedTimeFromTimestamp(paymentInstruction.getAuditDetails().getCreatedTime(), JIT_BILL_DATE_FORMAT))
                    .ssuIaId(ssuId).build();

            JITRequest jitRequest = JITRequest.builder()
                    .serviceId(JITServiceId.PAG)
                    .params(pagRequest).build();
            log.info("Calling ifms service.");
            JITResponse jitResponse = null;
            try {
                jitResponse = ifmsService.sendRequest(paymentInstruction.getTenantId(), jitRequest);
            }catch (Exception e){
                log.info("Exception occurred while fetching PAG from ifms." + e);
                jitRespStatusForPI = JitRespStatusForPI.STATUS_LOG_PAG_ERROR;
            }

            if (jitResponse == null) {
                // Create PI status log based on current existing PIS request
                paymentInstructionService.createAndSavePIStatusLog(paymentInstruction, JITServiceId.PAG, jitRespStatusForPI, requestInfo);
                continue;
            }

            log.info("Response received from IFMS.");

            if(jitResponse.getErrorMsg() != null || jitResponse.getData().isEmpty()) {
                jitRespStatusForPI = jitResponse.getErrorMsg() != null ? JitRespStatusForPI.STATUS_LOG_PAG_ERROR : JitRespStatusForPI.STATUS_LOG_PAG_NO_RESPONSE;
                // Create PI status log based on current existing PIS request
                paymentInstructionService.createAndSavePIStatusLog(paymentInstruction, JITServiceId.PAG, jitRespStatusForPI, requestInfo);
                continue;
            }
            log.info("Processing PAG response for : " + paymentInstruction.getJitBillNo());
            for( Object data : jitResponse.getData() ){
                Map<String, Object> dataMap = objectMapper.convertValue(data, Map.class);
                String finYear = dataMap.get("finYear").toString();
                String adviceId = dataMap.get("adviceId").toString();
                String adviceDate = dataMap.get("adviceDate").toString();
                String onlineBillRefNo = dataMap.get("onlineBillRefNo").toString();
                String tokenNumber = dataMap.get("tokenNumber").toString();
                String tokenDate = dataMap.get("tokenDate").toString();
                log.info("Updating PAG details according to IFMS response.");
                // Updated PA details status to in-process and sets the items as per response from JIT system
                for (PADetails paDetails : paymentInstruction.getPaDetails()) {
                    paDetails.setPaFinYear(finYear);
                    paDetails.setPaAdviceId(adviceId);
                    paDetails.setPaAdviceDate(adviceDate);
                    paDetails.setPaBillRefNumber(onlineBillRefNo);
                    paDetails.setPaTokenNumber(tokenNumber);
                    paDetails.setPaTokenDate(tokenDate);
                    paDetails.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                    paDetails.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
                }
                // Updated Beneficiary details status to in-process and sets the items as per response from JIT system
                for (Beneficiary beneficiary: paymentInstruction.getBeneficiaryDetails()) {
                    beneficiary.setPaymentStatus(BeneficiaryPaymentStatus.IN_PROCESS);
                    beneficiary.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                    beneficiary.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
                }
                // Updated PI status to in-process and sets the items as per response from JIT system
                paymentInstruction.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                paymentInstruction.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
                paymentInstruction.setPiStatus(PIStatus.IN_PROCESS);
                log.info("Updating PI status and details for PAG : " + paymentInstruction.getJitBillNo());
                // Update PI DB based on updated PI
                piRepository.update(Collections.singletonList(paymentInstruction),null);
                // Update PI indexer based on updated PI
                piUtils.updatePIIndex(requestInfo, paymentInstruction);
                jitRespStatusForPI = JitRespStatusForPI.STATUS_LOG_PAG_SUCCESS;
                // Create PI status log based on current existing PIS request
                paymentInstructionService.createAndSavePIStatusLog(paymentInstruction, JITServiceId.PAG, jitRespStatusForPI, requestInfo);
                log.info("Convering PaymentInstruction to Disbursement And Calling OnDisburse");
                paymentInstructionService.processPIForOnDisburse(paymentInstruction,requestInfo,false);
            }
            log.info("PAG status updated for PI : " + paymentInstruction.getJitBillNo());
        }
    }

    /**
     * Returns payment instructions for which payment status is approved
     * @return
     */
    public List<PaymentInstruction> getApprovedPaymentInstructions() {
        PISearchCriteria piSearchCriteria = PISearchCriteria.builder().piStatus(PIStatus.APPROVED).build();
        return piRepository.searchPi(piSearchCriteria);
    }
}
