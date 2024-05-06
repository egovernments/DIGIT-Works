package org.egov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.repository.PIRepository;
import org.egov.repository.SanctionDetailsRepository;
import org.egov.utils.BillUtils;
import org.egov.utils.HelperUtil;
import org.egov.utils.PIUtils;
import org.egov.web.models.bill.Payment;
import org.egov.web.models.bill.PaymentRequest;
import org.egov.web.models.enums.*;
import org.egov.web.models.jit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

import static org.egov.config.Constants.JIT_BILL_DATE_FORMAT;


@Service
@Slf4j
public class PISService {

    @Autowired
    private PaymentInstructionService paymentInstructionService;
    @Autowired
    private IfmsService ifmsService;
    @Autowired
    private PIRepository piRepository;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private HelperUtil helperUtil;
    @Autowired
    private PIUtils piUtils;
    @Autowired
    private SanctionDetailsRepository sanctionDetailsRepository;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Call the JIT system for which payment status is initiated and update the status of payment advice based
     * on the response.
     * @param requestInfo
     */
    public void updatePIStatus(RequestInfo requestInfo){
        log.info("Start executing PIS update service.");
        // gets initiated payment instructions
        List<PaymentInstruction> paymentInstructions = getInitiatedPaymentInstructions();

        for(PaymentInstruction paymentInstruction : paymentInstructions){
            JitRespStatusForPI jitRespStatusForPI = null;
            log.info("Started Processing PI for PIS : " + paymentInstruction.getJitBillNo());
            JSONArray ssuIaDetails = ifmsService.getSSUDetails(RequestInfo.builder().build(), paymentInstruction.getTenantId());
            Map<String,String> ssuIaDetailsMap = (Map<String, String>) ssuIaDetails.get(0);
            String ssuId = ssuIaDetailsMap.get("ssuId");
            PISRequest pisRequest = PISRequest.builder()
                    .jitBillDate(helperUtil.getFormattedTimeFromTimestamp(paymentInstruction.getAuditDetails().getCreatedTime(), JIT_BILL_DATE_FORMAT))
                    .jitBillNo(paymentInstruction.getJitBillNo())
                    .ssuIaId(ssuId)
                    .build();
            JITRequest jitRequest = JITRequest.builder().serviceId(JITServiceId.PIS).params(pisRequest).build();
            JITResponse pisResponse = null;
            log.info("Calling ifms service.");
            try {
                pisResponse = ifmsService.sendRequestToIFMS(jitRequest);
            }catch (Exception e){
                log.info("Exception occurred while fetching PIS from ifms." + e);
                jitRespStatusForPI = JitRespStatusForPI.STATUS_LOG_PIS_ERROR;
            }

            if (pisResponse == null) {
                // Create PI status log based on current existing PIS request
                paymentInstructionService.createAndSavePIStatusLog(paymentInstruction, JITServiceId.PIS, jitRespStatusForPI, requestInfo);
                continue;
            }

            log.info("Response received from IFMS.");

            if (pisResponse.getErrorMsg() != null
                    && pisResponse.getErrorMsg().contains(paymentInstruction.getJitBillNo())
                    && pisResponse.getErrorMsg().contains("rejected")) {
                log.info("PI is rejected, updating the PI to failed. " + paymentInstruction.getJitBillNo());
                updateStatusToFailed(requestInfo, paymentInstruction);
                paymentInstruction.setPiErrorResp(pisResponse.getErrorMsg());
                // Add the previously deducted amount in funds summary for failure case
                updateFundsSummary(requestInfo, paymentInstruction);
                // Update PI indexer based on updated PI
                piUtils.updatePIIndex(requestInfo, paymentInstruction);
                log.info("PI updated to failed. " + paymentInstruction.getJitBillNo());
                jitRespStatusForPI = JitRespStatusForPI.STATUS_LOG_PIS_REJECTED;
                // Create PI status log based on current existing PIS request
                paymentInstructionService.createAndSavePIStatusLog(paymentInstruction, JITServiceId.PIS, jitRespStatusForPI, requestInfo);
                continue;
            }
            if(CollectionUtils.isEmpty(pisResponse.getData())){
                jitRespStatusForPI = JitRespStatusForPI.STATUS_LOG_PIS_NO_RESPONSE;
                // Create PI status log based on current existing PIS request
                paymentInstructionService.createAndSavePIStatusLog(paymentInstruction, JITServiceId.PIS, jitRespStatusForPI, requestInfo);
                continue;
            }
            try {
                log.info("Processing PIS response for : " + paymentInstruction.getJitBillNo());
                for(Object data : pisResponse.getData()){
                    Map<String, String> dataMap = mapper.convertValue(data, Map.class);
                    String piApprovedId = dataMap.get("pmtInstId");
                    String piApprovalDate = dataMap.get("payInstDate");

                    // Updated PI status to approved and sets the items as per response from JIT system
                    paymentInstruction.setPiApprovedId(piApprovedId);
                    paymentInstruction.setPiApprovalDate(piApprovalDate);
                    paymentInstruction.setPiStatus(PIStatus.APPROVED);
                    paymentInstruction.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                    paymentInstruction.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());

                    log.info("Updating PI status and details for PIS : " + paymentInstruction.getJitBillNo());
                    // Update PI DB based on updated PI
                    piRepository.update(Collections.singletonList(paymentInstruction),null);
                    // Update PI indexer based on updated PI
                    piUtils.updatePIIndex(requestInfo, paymentInstruction);
                    jitRespStatusForPI = JitRespStatusForPI.STATUS_LOG_PIS_SUCCESS;
                    // Create PI status log based on current existing PIS request
                    paymentInstructionService.createAndSavePIStatusLog(paymentInstruction, JITServiceId.PIS, jitRespStatusForPI, requestInfo);
                    paymentInstructionService.processPIForOnDisburse(paymentInstruction,requestInfo,false);
                }
            } catch (Exception e) {
                log.info("Exception occurred while processing PIS response." + e);
            }
        }
    }

    /**
     * Returns payment instructions for which payment status is initiated
     * @return
     */
    public List<PaymentInstruction> getInitiatedPaymentInstructions(){
        log.info("Executing PISService:getInitiatedPaymentInstructions");
        PISearchCriteria piSearchCriteria = PISearchCriteria.builder().piStatus(PIStatus.INITIATED).piType(PIType.ORIGINAL).build();
        return piRepository.searchPi(piSearchCriteria);
    }

    /**
     * Function sets the status PI to failed and also updates payment status to failed
     * @param requestInfo
     * @param paymentInstruction
     */
    private void updateStatusToFailed(RequestInfo requestInfo, PaymentInstruction paymentInstruction) {
        log.info("Executing PISService:updateStatusToFailed");
        for (Beneficiary beneficiary : paymentInstruction.getBeneficiaryDetails()) {
            beneficiary.setPaymentStatus(BeneficiaryPaymentStatus.FAILED);
        }
        paymentInstruction.setPiStatus(PIStatus.FAILED);
        paymentInstructionService.processPIForOnDisburse(paymentInstruction,requestInfo,false);
    }

    /**
     * Adds the previously deducted value in funds summary amount for failure cases.
     * @param requestInfo
     * @param paymentInstruction
     */
    public void updateFundsSummary(RequestInfo requestInfo, PaymentInstruction paymentInstruction) {
        log.info("Executing PISService:updateFundsSummary");
        SanctionDetailsSearchCriteria searchCriteria = SanctionDetailsSearchCriteria.builder()
                .ids(Collections.singletonList(paymentInstruction.getTransactionDetails().get(0).getSanctionId()))
                .build();
        List<SanctionDetail> sanctionDetails = sanctionDetailsRepository.getSanctionDetails(searchCriteria);
        SanctionDetail sanctionDetail = sanctionDetails.get(0);
        BigDecimal totalAmount = new BigDecimal(0);
        List<Beneficiary> beneficiaries = paymentInstruction.getBeneficiaryDetails();
        if (beneficiaries != null && !beneficiaries.isEmpty()) {
            for (Beneficiary piBeneficiary: beneficiaries) {
                totalAmount = totalAmount.add(piBeneficiary.getAmount());
            }
        }
        sanctionDetail.getFundsSummary().setAvailableAmount(sanctionDetail.getFundsSummary().getAvailableAmount().add(totalAmount));
        sanctionDetail.getFundsSummary().getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
        sanctionDetail.getFundsSummary().getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
        AuditDetails auditDetails = AuditDetails.builder()
                .createdBy(requestInfo.getUserInfo().getUuid()).createdTime(System.currentTimeMillis())
                .lastModifiedBy(requestInfo.getUserInfo().getUuid()).lastModifiedTime(System.currentTimeMillis())
                .build();
        TransactionDetails transactionDetails = TransactionDetails.builder()
                .tenantId(paymentInstruction.getTenantId())
                .sanctionId(sanctionDetail.getId())
                .paymentInstId(paymentInstruction.getId())
                .transactionAmount(totalAmount)
                .transactionDate(System.currentTimeMillis())
                .transactionType(TransactionType.REVERSAL)
                .additionalDetails(objectMapper.createObjectNode())
                .auditDetails(auditDetails)
                .build();
        paymentInstruction.getTransactionDetails().add(transactionDetails);
        log.info("Updating PI in PISService:updateFundsSummary");
        piRepository.update(Collections.singletonList(paymentInstruction), sanctionDetail.getFundsSummary());
        log.info("PI updated in PISService:updateFundsSummary");
    }

}
