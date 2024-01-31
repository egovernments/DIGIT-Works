package org.egov.validators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.service.IfmsService;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.Disbursement;
import org.egov.web.models.DisbursementRequest;
import org.egov.web.models.enums.PIStatus;
import org.egov.web.models.enums.PaymentStatus;
import org.egov.web.models.jit.PaymentInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DisbursementValidator {
    private final ObjectMapper objectMapper;

    @Autowired
    public DisbursementValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void validateDisbursementRequest(DisbursementRequest disbursementRequest, Map<String, Map<String,JSONArray>> mdmsData) {
        log.info("DisbursementValidator.validateDisbursementRequest()");
        Disbursement disbursement = disbursementRequest.getMessage();
        JSONArray ssuDetails = mdmsData.get("ifms").get("SSUDetails");
        if(!ssuDetails.isEmpty()){
            JsonNode ssuDetail = objectMapper.valueToTree(ssuDetails.get(0));
            String programCode = ssuDetail.get("programCode").asText();
            if(!programCode.equals(disbursementRequest.getMessage().getProgramCode())){
                throw new CustomException("INVALID_PROGRAM_CODE", "Program Code is invalid for the tenantId and disbursement Request.");
            }
        }

        if(disbursement.getSanctionId() == null){
            throw new CustomException("INVALID_SANCTION_ID", "Sanction Id is mandatory for the disbursement Request.");
        }
        validateChildDisbursements(disbursement.getDisbursements(), mdmsData);
    }

    private void validateChildDisbursements(List<Disbursement> disbursements, Map<String, Map<String, JSONArray>> mdmsData) {
        log.info("DisbursementValidator.validateChildDisbursements()");
        if(disbursements != null && !disbursements.isEmpty()){
            for(Disbursement disbursement : disbursements){
                if(disbursement.getAccountCode() == null){
                    throw new CustomException("INVALID_ACCOUNT_CODE", "Account Code is mandatory for the disbursement Request.");
                }
            }
        }
    }

    public void validatePI(List<PaymentInstruction> paymentInstructions, DisbursementRequest disbursementRequest, Map<String, Map<String,JSONArray>> mdmsData) {
        log.info("DisbursementValidator.validatePI()");
        if(!paymentInstructions.isEmpty()){
            PIStatus piStatus = paymentInstructions.get(0).getPiStatus();
            if(piStatus.equals(PIStatus.INITIATED) || piStatus.equals(PIStatus.APPROVED) || piStatus.equals(PIStatus.IN_PROCESS) || piStatus.equals(PIStatus.SUCCESSFUL)){
                throw new CustomException("PI_ALREADY_EXISTS","Payment Instruction already exists");
            }
        }else{
            log.info("DisbursementValidator.validatePI() - Payment Instruction not found");
        }
    }
}
