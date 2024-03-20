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
import org.egov.web.models.Individual;
import org.egov.web.models.MsgCallbackHeader;
import org.egov.web.models.enums.PIStatus;
import org.egov.web.models.enums.PaymentStatus;
import org.egov.web.models.jit.PaymentInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        validateHeader(disbursementRequest.getHeader());
        validateDisbursement(disbursement,mdmsData);
        validateChildDisbursements(disbursement.getDisbursements(),disbursementRequest);
    }

    private void validateHeader(MsgCallbackHeader header) {
        if(header.getSenderId().equals(header.getReceiverId())){
            throw new CustomException("INVALID_SENDER_RECEIVER_ID", "Sender Id and Receiver Id cannot be same for the disbursement Request.");
        }
    }

    private void validateDisbursement(Disbursement disbursement, Map<String, Map<String, JSONArray>> mdmsData) {

        JSONArray ssuDetails = mdmsData.get("ifms").get("SSUDetails");
        if(!ssuDetails.isEmpty()){
            JsonNode ssuDetail = objectMapper.valueToTree(ssuDetails.get(0));
            String programCode = ssuDetail.get("programCode").asText();
            if(!programCode.equals(disbursement.getProgramCode())){
                throw new CustomException("INVALID_PROGRAM_CODE", "Program Code is invalid for the tenantId and disbursement Request.");
            }
        }

        if(disbursement.getSanctionId() == null){
            throw new CustomException("INVALID_SANCTION_ID", "Sanction Id is mandatory for the disbursement Request.");
        }
        if(disbursement.getDisbursements() == null || disbursement.getDisbursements().isEmpty()){
            throw new CustomException("INVALID_CHILD_DISBURSEMENTS", "Child Disbursements are mandatory for the disbursement Request.");
        }
        validateChildDisbursement(disbursement);
    }

    private void validateChildDisbursements(List<Disbursement> disbursements, DisbursementRequest disbursementRequest) {
        log.info("DisbursementValidator.validateChildDisbursements()");
        BigDecimal netAmount = disbursementRequest.getMessage().getNetAmount();
        BigDecimal grossAmount = disbursementRequest.getMessage().getGrossAmount();

        if (disbursements != null && !disbursements.isEmpty()) {
            for (Disbursement disbursement : disbursements) {
                // Use the result of subtraction to update the variables
                validateChildDisbursement(disbursement);
                netAmount = netAmount.subtract(disbursement.getNetAmount());
                grossAmount = grossAmount.subtract(disbursement.getGrossAmount());
                if(disbursement.getAccountCode() == null){
                    throw new CustomException("INVALID_ACCOUNT_CODE", "Account Code is mandatory for the disbursement Request.");
                }
                validateIndividual(disbursement.getIndividual());
            }
        }

        // Use compareTo() for BigDecimal equality comparison
        if (netAmount.compareTo(BigDecimal.ZERO) != 0 || grossAmount.compareTo(BigDecimal.ZERO) != 0) {
            throw new CustomException("INVALID_AMOUNT", "Net Amount and Gross Amount are not matching with the child disbursements.");
        }
    }

    private void validateChildDisbursement(Disbursement disbursement) {
        if(disbursement.getId() == null){
            disbursement.setId(UUID.randomUUID().toString());
        }
        if(!disbursement.getNetAmount().equals(disbursement.getGrossAmount())){
            throw new CustomException("INVALID_AMOUNT", "Net Amount and Gross Amount are not matching for the disbursement Request.");
        }
        if(disbursement.getStatus() == null){
            throw new CustomException("INVALID_STATUS","Status is mandatory for the disbursement Request.");
        }
        if(disbursement.getStatus().getStatusCode() == null){
            throw new CustomException("INVALID_STATUS_CODE","Status Code is mandatory for the disbursement Request.");
        }
    }

    private void validateIndividual(Individual individual) {
        if(individual == null){
            throw new CustomException("INVALID_INDIVIDUAL", "Individual is mandatory for the disbursement Request.");
        }
        if(individual.getName() == null){
            throw new CustomException("INVALID_NAME", "Name is mandatory for the disbursement Request.");
        }
        if(individual.getPhone() == null){
            throw new CustomException("INVALID_PHONE", "Phone is mandatory for the disbursement Request.");
        }
        if(individual.getAddress() == null){
            throw new CustomException("INVALID_ADDRESS", "Address is mandatory for the disbursement Request.");
        }
    }

    public void validatePI(List<PaymentInstruction> paymentInstructions) {
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
