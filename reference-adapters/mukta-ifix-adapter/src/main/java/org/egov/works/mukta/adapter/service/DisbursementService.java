package org.egov.works.mukta.adapter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.UserDetailResponse;
import digit.models.coremodels.UserSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.config.Constants;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.constants.Error;
import org.egov.works.mukta.adapter.enrichment.PaymentInstructionEnrichment;
import org.egov.works.mukta.adapter.kafka.MuktaAdaptorProducer;
import org.egov.works.mukta.adapter.util.BillUtils;
import org.egov.works.mukta.adapter.util.UserUtil;
import org.egov.works.mukta.adapter.validators.DisbursementValidator;
import org.egov.works.mukta.adapter.web.models.*;
import org.egov.works.mukta.adapter.web.models.bill.*;
import org.egov.works.mukta.adapter.web.models.enums.PaymentStatus;
import org.egov.works.mukta.adapter.web.models.enums.ReferenceStatus;
import org.egov.works.mukta.adapter.web.models.enums.StatusCode;
import org.egov.works.mukta.adapter.web.models.jit.PaymentInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class DisbursementService {
    private final BillUtils billUtils;
    private final MuktaAdaptorConfig muktaAdaptorConfig;
    private final MuktaAdaptorProducer muktaAdaptorProducer;
    private final DisbursementValidator disbursementValidator;
    private final UserUtil userUtil;
    private final PaymentInstructionEnrichment paymentInstructionEnrichment;
    private final PaymentInstructionService paymentInstructionService;
    private final PaymentService paymentService;


    @Autowired
    public DisbursementService(BillUtils billUtils, MuktaAdaptorConfig muktaAdaptorConfig, MuktaAdaptorProducer muktaAdaptorProducer, DisbursementValidator disbursementValidator, UserUtil userUtil, PaymentInstructionEnrichment paymentInstructionEnrichment, PaymentInstructionService paymentInstructionService, PaymentService paymentService) {
        this.billUtils = billUtils;
        this.muktaAdaptorConfig = muktaAdaptorConfig;
        this.muktaAdaptorProducer = muktaAdaptorProducer;
        this.disbursementValidator = disbursementValidator;
        this.userUtil = userUtil;
        this.paymentInstructionEnrichment = paymentInstructionEnrichment;
        this.paymentInstructionService = paymentInstructionService;
        this.paymentService = paymentService;
    }
    /**
     * Processes the disbursement request and updates the payment status
     * @param disbursementRequest The disbursement request
     * @return The disbursement response
     */
    public DisbursementResponse processOnDisbursement(DisbursementRequest disbursementRequest) {
        log.info("Processing disbursement request");
        // Validate the disbursement request
        disbursementValidator.validateOnDisbursementRequest(disbursementRequest);
        // Extract the disbursement message from the request
        Disbursement disbursement = disbursementRequest.getMessage();
        // Extract the tenant ID from the disbursement
        String tenantId = disbursement.getLocationCode();
        // Build the request info with a hardcoded user UUID
        RequestInfo requestInfo = getRequestInfoForSystemUser();
        // Fetch payment details using the request info, target ID from disbursement, and tenant ID
        List<Payment> payments = billUtils.fetchPaymentDetails(requestInfo, disbursement.getTargetId(), tenantId);
        // If no payments are found, throw a custom exception
        if (payments == null || payments.isEmpty()) {
            throw new CustomException(Error.PAYMENT_NOT_FOUND, Error.PAYMENT_NOT_FOUND_MESSAGE);
        }
        log.info("Payments fetched for the disbursement request : " + payments);
        // Extract the first payment from the list of payments
        Payment payment = payments.get(0);
        log.info("Updating the payment status for the payments : " + payment);
        // Update the payment status
        boolean updatePaymentStatus = canUpdatePaymentStatus(disbursement);
        if (updatePaymentStatus)
            paymentService.updatePaymentStatus(payment, disbursement, requestInfo);
        log.info("Updating the disbursement status for the payments : " + disbursementRequest.getMessage());
        // Get the disbursement response
        DisbursementResponse disbursementResponse = getDisbursementResponse(disbursementRequest);
        PaymentInstruction pi = paymentInstructionEnrichment.getPaymentInstructionFromDisbursement(disbursementResponse.getMessage());
        // Push the disbursement response to the disburse update topic
        muktaAdaptorProducer.push(muktaAdaptorConfig.getDisburseUpdateTopic(), disbursementResponse);
        paymentInstructionService.updatePIIndex(requestInfo, pi,false);
        return disbursementResponse;
    }

    private boolean canUpdatePaymentStatus(Disbursement disbursement) {
        log.info("Checking if the payment status can be updated for the disbursement : " + disbursement.getId());
        boolean isRevised = false;
        DisbursementSearchCriteria disbursementSearchCriteria = DisbursementSearchCriteria.builder()
                .paymentNumber(disbursement.getTargetId())
                .build();
        DisbursementSearchRequest disbursementSearchRequest = DisbursementSearchRequest.builder()
                .criteria(disbursementSearchCriteria)
                .pagination(Pagination.builder().limit(50).build())
                .build();
        List<Disbursement> disbursements = paymentInstructionService.processDisbursementSearch(disbursementSearchRequest);

        for(Disbursement disbursement1: disbursements){
            if(disbursement1.getStatus().getStatusCode().equals(StatusCode.PARTIAL) || disbursement1.getStatus().getStatusCode().equals(StatusCode.COMPLETED)){
                isRevised = true;
            }
        }
        if(isRevised) {
            if (disbursement.getStatus().getStatusCode().equals(StatusCode.PARTIAL) || disbursement.getStatus().getStatusCode().equals(StatusCode.SUCCESSFUL)) {
                return true;
            }
            return false;
        }
        return true;
    }

    private RequestInfo getRequestInfoForSystemUser() {
        RequestInfo requestInfo = RequestInfo.builder().build();
        String username = muktaAdaptorConfig.getSystemUserUsername();
        UserSearchRequest userSearchRequest = new UserSearchRequest();
        userSearchRequest.setRequestInfo(requestInfo);
        userSearchRequest.setUserName(username);
        userSearchRequest.setTenantId(muktaAdaptorConfig.getStateLevelTenantId());

        StringBuilder uri = new StringBuilder();
        uri.append(muktaAdaptorConfig.getUserHost()).append(muktaAdaptorConfig.getUserSearchEndpoint());
        String uuid = userUtil.userCall(userSearchRequest, uri);
        requestInfo.setUserInfo(User.builder().uuid(uuid).build());
        return requestInfo;
    }

    /**
     * Processes the disbursement request and updates the payment status
     * @param disbursementRequest The disbursement request
     * @return The disbursement response
     */
    private DisbursementResponse getDisbursementResponse(DisbursementRequest disbursementRequest) {
        AuditDetails auditDetails = disbursementRequest.getMessage().getAuditDetails();
        AuditDetails updatedAuditDetails = AuditDetails.builder().createdBy(auditDetails.getCreatedBy()).createdTime(auditDetails.getCreatedTime()).lastModifiedBy(auditDetails.getLastModifiedBy()).lastModifiedTime(System.currentTimeMillis()).build();
        DisbursementResponse disbursementResponse = DisbursementResponse.builder().signature(disbursementRequest.getSignature()).header(disbursementRequest.getHeader()).message(disbursementRequest.getMessage()).build();
        disbursementResponse.getMessage().setAuditDetails(updatedAuditDetails);
        disbursementResponse.getMessage().getDisbursements().forEach(disbursement -> disbursement.setAuditDetails(updatedAuditDetails));
//        HashSet<StatusCode> statusCodes = new HashSet<>();
//        HashSet<String> statusMessages = new HashSet<>();
//        for(Disbursement disbursement: disbursementResponse.getMessage().getDisbursements()){
//            statusCodes.add(disbursement.getStatus().getStatusCode());
//            statusMessages.add(disbursement.getStatus().getStatusMessage());
//        }
//
//        if(statusCodes.size() == 1){
//            disbursementResponse.getMessage().setStatus(Status.builder().statusCode(statusCodes.iterator().next()).statusMessage(statusMessages.iterator().next()).build());
//        }else if(!disbursementRequest.getMessage().getStatus().getStatusCode().equals(StatusCode.COMPLETED)){
//            disbursementResponse.getMessage().setStatus(Status.builder().statusCode(StatusCode.PARTIAL).statusMessage(StatusCode.PARTIAL.toString()).build());
//        }
        return disbursementResponse;
    }


}
