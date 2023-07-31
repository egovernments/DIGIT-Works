package org.egov.ifms.jit.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.ifms.jit.repository.PIRepository;
import org.egov.ifms.jit.utils.BillUtils;
import org.egov.ifms.jit.utils.HelperUtil;
import org.egov.ifms.jit.utils.PIUtils;
import org.egov.ifms.jit.web.models.enums.*;
import org.egov.ifms.jit.web.models.jit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.egov.ifms.jit.config.Constants.JIT_BILL_DATE_FORMAT;

@Service
@Slf4j
public class PAGService {

    @Autowired
    private PaymentInstructionService paymentInstructionService;
    @Autowired
    private IfmsService ifmsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PIRepository piRepository;

    @Autowired
    private HelperUtil helperUtil;

    @Autowired
    private PIUtils piUtils;

    @Autowired
    private BillUtils billUtils;

    public void updatePAG( RequestInfo requestInfo ){
        log.info("Start executing PAG update service.");
        List<PaymentInstruction> paymentInstructions = getApprovedPaymentInstructions();
        for(PaymentInstruction paymentInstruction : paymentInstructions){
            log.info("Started Processing PI for PAG : " + paymentInstruction.getJitBillNo());

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
                jitResponse = ifmsService.sendRequestToIFMS(jitRequest);
            }catch (Exception e){
                log.info("Exception occurred while fetching PAG from ifms." + e);

                /*
                TODO: commenting because this is invalid, check and remove this block
                List<Payment> payments = billUtils.fetchPaymentDetails(requestInfo,
                        Collections.singleton(paymentInstruction.getMuktaReferenceId()),
                        paymentInstruction.getTenantId());
                for (Payment payment : payments) {
                    PaymentRequest paymentRequest = PaymentRequest.builder()
                            .requestInfo(requestInfo).payment(payment).build();

                    billUtils.updatePaymentForStatus(paymentRequest, PaymentStatus.FAILED, ReferenceStatus.PAYMENT_SERVER_UNREACHABLE);
                }
                throw new CustomException("SERVER_UNREACHABLE","Server is currently unreachable");
                 */
            }

            if (jitResponse == null)
                continue;

            log.info("Response received from IFMS.");

            if(jitResponse.getErrorMsg() != null || jitResponse.getData().isEmpty()) {
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
                for (Beneficiary beneficiary: paymentInstruction.getBeneficiaryDetails()) {
                    beneficiary.setPaymentStatus(BeneficiaryPaymentStatus.IN_PROCESS);
                    beneficiary.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                    beneficiary.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
                }

                paymentInstruction.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                paymentInstruction.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
                paymentInstruction.setPiStatus(PIStatus.IN_PROCESS);
                log.info("Updating PI status and details for PAG : " + paymentInstruction.getJitBillNo());
                piRepository.update(Collections.singletonList(paymentInstruction),null);
                piUtils.updatePiForIndexer(requestInfo, paymentInstruction);
            }
            log.info("PAG status updated for PI : " + paymentInstruction.getJitBillNo());

        }
    }

    public List<PaymentInstruction> getApprovedPaymentInstructions() {
        PISearchRequest piSearchRequest = PISearchRequest.builder().requestInfo(RequestInfo.builder().build())
                .searchCriteria(PISearchCriteria.builder().piStatus(PIStatus.APPROVED).build()).build();
        List<PaymentInstruction> paymentInstructions = paymentInstructionService.searchPi(piSearchRequest);
        return paymentInstructions;

    }
}
