package org.egov.works.mukta.adapter.validators;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.constants.Error;
import org.egov.works.mukta.adapter.repository.DisbursementRepository;
import org.egov.works.mukta.adapter.web.models.*;
import org.egov.works.mukta.adapter.web.models.bill.PaymentRequest;
import org.egov.works.mukta.adapter.web.models.enums.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Component
@Slf4j
public class DisbursementValidator {
    private final DisbursementRepository disbursementRepository;

    @Autowired
    public DisbursementValidator(DisbursementRepository disbursementRepository) {
        this.disbursementRepository = disbursementRepository;
    }

    public void validateOnDisbursementRequest(DisbursementRequest disbursementRequest) {
        log.info("Validating on disbursement request");
        validateRequestBodyForOnDisbursement(disbursementRequest);
        validateDisbursement(disbursementRequest.getMessage());
    }

    public void isValidForDisbursementCreate(PaymentRequest paymentRequest){
        log.info("Validating request body for disbursement create");
        if(paymentRequest.getReferenceId() == null && paymentRequest.getPayment().getPaymentNumber() == null){
            throw new CustomException(Error.INVALID_REQUEST, Error.REFERENCE_ID_AND_PAYEMENT_NOT_FOUND);
        }
        if(paymentRequest.getReferenceId() != null && paymentRequest.getTenantId() == null){
            throw new CustomException(Error.INVALID_REQUEST, Error.REFERENCE_ID_AND_TENANT_ID_NOT_FOUND);
        }
        Pagination pagination = Pagination.builder().sortBy("createdtime").order(Pagination.OrderEnum.DESC).limit(1).build();
        DisbursementSearchRequest disbursementSearchRequest = DisbursementSearchRequest.builder()
                .requestInfo(paymentRequest.getRequestInfo())
                .criteria(DisbursementSearchCriteria.builder().paymentNumber(paymentRequest.getPayment() == null? paymentRequest.getReferenceId(): paymentRequest.getPayment().getPaymentNumber()).build())
                .pagination(pagination)
                .build();
        List<Disbursement> disbursements = disbursementRepository.searchDisbursement(disbursementSearchRequest);
        if(disbursements != null && !disbursements.isEmpty() && (disbursements.get(0).getStatus().getStatusCode().equals(StatusCode.INITIATED)
                || disbursements.get(0).getStatus().getStatusCode().equals(StatusCode.APPROVED)
                || disbursements.get(0).getStatus().getStatusCode().equals(StatusCode.IN_PROCESS) || disbursements.get(0).getStatus().getStatusCode().equals(StatusCode.SUCCESSFUL))){
            throw new CustomException(Error.PAYMENT_ALREADY_PROCESSED, Error.PAYMENT_ALREADY_PROCESSED_MESSAGE);
        }
        if(paymentRequest.getReferenceId() == null){
            paymentRequest.setReferenceId(paymentRequest.getPayment().getPaymentNumber());
        }
        log.info("No active disbursement found for the payment id : " + paymentRequest.getReferenceId());
    }
    private void validateRequestBodyForOnDisbursement(DisbursementRequest disbursementRequest) {
        log.info("Validating request body for on disbursement");
        if(disbursementRequest.getSignature() == null || disbursementRequest.getSignature().isEmpty()){
            throw new CustomException(Error.INVALID_REQUEST, Error.SIGNATURE_NOT_FOUND_MESSAGE);
        }
        if(disbursementRequest.getHeader() == null){
            throw new CustomException(Error.INVALID_REQUEST, Error.HEADER_NOT_FOUND_MESSAGE);
        }
        if(disbursementRequest.getMessage() == null) {
            throw new CustomException(Error.INVALID_REQUEST, Error.MESSAGE_NOT_FOUND_MESSAGE);
        }
    }

    public void validateDisbursement(Disbursement disbursement) {
        log.info("Validating disbursement");
        if(disbursement.getTargetId() == null || disbursement.getTargetId().isEmpty()){
            throw new CustomException(Error.INVALID_REQUEST, Error.PAYMENT_REFERENCE_ID_NOT_FOUND_MESSAGE);
        }
        if(disbursement.getStatus() == null || disbursement.getStatus().getStatusCode() == null){
            throw new CustomException(Error.INVALID_REQUEST, "Disbursement status not found in the request");
        }
        validateDisbursementFromDB(disbursement);
    }

    private void validateDisbursementFromDB(Disbursement disbursement) {
        log.info("Validating disbursement from db");
        DisbursementSearchCriteria disbursementSearchCriteria = DisbursementSearchCriteria.builder()
                .ids(Collections.singletonList(disbursement.getId()))
                .build();
        DisbursementSearchRequest disbursementSearchRequest = DisbursementSearchRequest.builder()
                .requestInfo(RequestInfo.builder().build())
                .criteria(disbursementSearchCriteria)
                .pagination(Pagination.builder().build())
                .build();
        List<Disbursement> disbursements = disbursementRepository.searchDisbursement(disbursementSearchRequest);
        if(disbursements == null || disbursements.isEmpty()){
            throw new CustomException(Error.DISBURSEMENT_NOT_FOUND, Error.DISBURSEMENT_NOT_FOUND_MESSAGE);
        }
        //Validating disbursement From DB
        Disbursement disbursementFromDB = disbursements.get(0);
        if(!disbursementFromDB.getTargetId().equals(disbursement.getTargetId())){
            throw new CustomException(Error.TARGET_ID_NOT_MATCHED, Error.TARGET_ID_NOT_MATCHED_MESSAGE);
        }
        if(disbursementFromDB.getDisbursements().size() != disbursement.getDisbursements().size()){
            throw new CustomException(Error.ALL_CHILDS_ARE_NOT_PRESENT, Error.ALL_CHILDS_ARE_NOT_PRESENT_MESSAGE);
        }
        HashSet<String> disbursementChildIds = new HashSet<>();
        for(Disbursement disbursement1: disbursementFromDB.getDisbursements()){
            disbursementChildIds.add(disbursement1.getId());
        }
        for(Disbursement disbursement1: disbursement.getDisbursements()){
            disbursementChildIds.remove(disbursement1.getId());
        }
        if(!disbursementChildIds.isEmpty()){
            throw new CustomException(Error.ALL_CHILDS_ARE_NOT_PRESENT, Error.ALL_CHILDS_ARE_NOT_PRESENT_MESSAGE);
        }
    }
}
