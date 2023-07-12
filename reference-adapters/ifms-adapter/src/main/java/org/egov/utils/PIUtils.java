package org.egov.utils;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.producer.Producer;
import org.egov.config.IfmsAdapterConfig;
import org.egov.web.models.jit.Beneficiary;
import org.egov.web.models.jit.PaymentInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class PIUtils {
    @Autowired
    Producer producer;
    @Autowired
    IfmsAdapterConfig adapterConfig;
    public void updatePiForIndexer(RequestInfo requestInfo, PaymentInstruction paymentInstruction) {
        try {
            PaymentInstruction pi = (PaymentInstruction) paymentInstruction;
            pi.setPaDetails(null);
            for (Beneficiary beneficiary : pi.getBeneficiaryDetails()) {
                beneficiary.setBenefName(null);
                beneficiary.setBenfAcctNo(null);
                beneficiary.setBenfBankIfscCode(null);
                beneficiary.setBenfMobileNo(null);
                beneficiary.setBenfAddress(null);
                beneficiary.setBenfAccountType(null);
            }
            Map<String, Object> indexerRequest = new HashMap<>();
            indexerRequest.put("RequestInfo", requestInfo);
            indexerRequest.put("paymentInstruction", pi);
            producer.push(adapterConfig.getIfmsPiEnrichmentTopic(), indexerRequest);

        } catch (Exception e) {
            log.error("Exception occurred in : PaymentInstructionService:updatePiForIndexer " + e);
        }
    }
}
