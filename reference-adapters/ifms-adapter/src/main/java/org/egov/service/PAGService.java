package org.egov.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.repository.PIRepository;
import org.egov.utils.HelperUtil;
import org.egov.utils.PIUtils;
import org.egov.web.models.enums.JITServiceId;
import org.egov.web.models.enums.PIStatus;
import org.egov.web.models.jit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.egov.config.Constants.JIT_BILL_DATE_FORMAT;

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

    public void updatePAG( RequestInfo requestInfo ){
        List<PaymentInstruction> paymentInstructions = getApprovedPaymentInstructions();
        for(PaymentInstruction paymentInstruction : paymentInstructions){

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

            JITResponse jitResponse = ifmsService.sendRequestToIFMS(jitRequest);

            if(jitResponse.getData().isEmpty()) {
                continue;
            }
            for( Object data : jitResponse.getData() ){
                Map<String, Object> dataMap = objectMapper.convertValue(data, Map.class);
                String finYear = dataMap.get("finYear").toString();
                String adviceId = dataMap.get("adviceId").toString();
                String adviceDate = dataMap.get("adviceDate").toString();
                String onlineBillRefNo = dataMap.get("onlineBillRefNo").toString();
                String tokenNumber = dataMap.get("tokenNumber").toString();
                String tokenDate = dataMap.get("tokenDate").toString();

                for (PADetails paDetails : paymentInstruction.getPaDetails()) {
                    paDetails.setPaFinYear(finYear);
                    paDetails.setPaAdviceId(adviceId);
                    paDetails.setPaAdviceDate(adviceDate);
                    paDetails.setPaBillRefNumber(onlineBillRefNo);
                    paDetails.setPaTokenNumber(tokenNumber);
                    paDetails.setPaTokenDate(tokenDate);
                }

                paymentInstruction.setPiStatus(PIStatus.IN_PROCESS);
                piRepository.updatePaymentAdviceByPAG(Collections.singletonList(paymentInstruction));
                piUtils.updatePiForIndexer(requestInfo, paymentInstruction);
            }
        }
    }

    public List<PaymentInstruction> getApprovedPaymentInstructions() {
        PISearchRequest piSearchRequest = PISearchRequest.builder().requestInfo(RequestInfo.builder().build())
                .searchCriteria(PISearchCriteria.builder().piStatus(PIStatus.APPROVED).build()).build();
        List<PaymentInstruction> paymentInstructions = paymentInstructionService.searchPi(piSearchRequest);
        return paymentInstructions;

    }
}
