package org.egov.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
    @Autowired
    ObjectMapper objectMapper;

    public void updatePIIndex(RequestInfo requestInfo, PaymentInstruction paymentInstruction) {
        log.info("Executing PIUtils:updatePiForIndexer");
        try {
            PaymentInstruction pi = (PaymentInstruction) paymentInstruction;
//            if (paymentInstruction.getIsActive().equals(false))
//                return;
            pi.setPaDetails(null);
            for (Beneficiary beneficiary : pi.getBeneficiaryDetails()) {
                beneficiary.setBenefName(null);
                beneficiary.setBenfAcctNo(null);
                beneficiary.setBenfBankIfscCode(null);
                beneficiary.setBenfMobileNo(null);
                beneficiary.setBenfAddress(null);
                beneficiary.setBenfAccountType(null);
            }
            JsonNode node = objectMapper.valueToTree(pi);
            ObjectNode piObjectNode = (ObjectNode) node;
            if (pi.getParentPiNumber() == null || pi.getParentPiNumber().equals("")) {
                piObjectNode.put("parentPiNumber", "");
                piObjectNode.put("piType", "ORIGINAL");
            }
            else {
                piObjectNode.put("piType", "REVISED");
            }
            if (pi.getPiErrorResp() == null) {
                piObjectNode.put("piErrorResp", "");
            }

            Map<String, Object> indexerRequest = new HashMap<>();
            indexerRequest.put("RequestInfo", requestInfo);
            indexerRequest.put("paymentInstruction", piObjectNode);
            producer.push(adapterConfig.getIfmsPiEnrichmentTopic(), indexerRequest);
            log.info("PI pushed to indexer kafka topic.");
        } catch (Exception e) {
            log.error("Exception occurred in : PaymentInstructionService:updatePiForIndexer " + e);
        }
    }
}
