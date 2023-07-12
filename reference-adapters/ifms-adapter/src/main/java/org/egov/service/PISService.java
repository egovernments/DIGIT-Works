package org.egov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.IfmsAdapterConfig;
import org.egov.repository.PIRepository;
import org.egov.utils.HelperUtil;
import org.egov.utils.MdmsUtils;
import org.egov.utils.PIUtils;
import org.egov.web.models.enums.JITServiceId;
import org.egov.web.models.enums.PIStatus;
import org.egov.web.models.jit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    public void updatePIStatus(RequestInfo requestInfo){
        List<PaymentInstruction> paymentInstructions = getInitiatedPaymentInstructions();

        for(PaymentInstruction paymentInstruction : paymentInstructions){
            JSONArray ssuIaDetails = ifmsService.getSSUDetails(RequestInfo.builder().build(), paymentInstruction.getTenantId());
            Map<String,String> ssuIaDetailsMap = (Map<String, String>) ssuIaDetails.get(0);
            String ssuId = ssuIaDetailsMap.get("ssuId");
            PISRequest pisRequest = PISRequest.builder()
                    .jitBillDate(helperUtil.getFormattedTimeFromTimestamp(paymentInstruction.getAuditDetails().getCreatedTime(), JIT_BILL_DATE_FORMAT))
                    .jitBillNo(paymentInstruction.getJitBillNo())
                    .ssuIaId(ssuId)
                    .build();
            JITRequest jitRequest = JITRequest.builder().serviceId(JITServiceId.PIS).params(pisRequest).build();

            JITResponse pisResponse = ifmsService.sendRequestToIFMS(jitRequest);

            if(CollectionUtils.isEmpty(pisResponse.getData())){
                continue;
            }
            for(Object data : pisResponse.getData()){
                Map<String, String> dataMap = mapper.convertValue(data, Map.class);
                String piApprovedId = dataMap.get("pmtInstId");
                String piApprovalDate = dataMap.get("payInstDate");

                paymentInstruction.setPiApprovedId(piApprovedId);
                paymentInstruction.setPiApprovalDate(piApprovalDate);
                paymentInstruction.setPiStatus(PIStatus.APPROVED);
                paymentInstruction.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                paymentInstruction.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());

                piRepository.update(Collections.singletonList(paymentInstruction),null);
                piUtils.updatePiForIndexer(requestInfo, paymentInstruction);
            }

        }
    }

    public List<PaymentInstruction> getInitiatedPaymentInstructions(){
        PISearchRequest piSearchRequest = PISearchRequest.builder().requestInfo(RequestInfo.builder().build())
                .searchCriteria(PISearchCriteria.builder().piStatus(PIStatus.INITIATED).build()).build();
        List<PaymentInstruction> paymentInstructions = paymentInstructionService.searchPi(piSearchRequest);
        return paymentInstructions;
    }




}
