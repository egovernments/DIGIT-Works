package org.egov.works.mukta.adapter.service;

import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.UserDetailResponse;
import digit.models.coremodels.UserSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.constants.Error;
import org.egov.works.mukta.adapter.enrichment.PaymentInstructionEnrichment;
import org.egov.works.mukta.adapter.kafka.MuktaAdaptorProducer;
import org.egov.works.mukta.adapter.util.BillUtils;
import org.egov.works.mukta.adapter.util.UserUtil;
import org.egov.works.mukta.adapter.validators.DisbursementValidator;
import org.egov.works.mukta.adapter.web.models.Disbursement;
import org.egov.works.mukta.adapter.web.models.DisbursementRequest;
import org.egov.works.mukta.adapter.web.models.DisbursementResponse;
import org.egov.works.mukta.adapter.web.models.Status;
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

    @Autowired
    public DisbursementService(BillUtils billUtils, MuktaAdaptorConfig muktaAdaptorConfig, MuktaAdaptorProducer muktaAdaptorProducer, DisbursementValidator disbursementValidator, UserUtil userUtil, PaymentInstructionEnrichment paymentInstructionEnrichment, PaymentInstructionService paymentInstructionService) {
        this.billUtils = billUtils;
        this.muktaAdaptorConfig = muktaAdaptorConfig;
        this.muktaAdaptorProducer = muktaAdaptorProducer;
        this.disbursementValidator = disbursementValidator;
        this.userUtil = userUtil;
        this.paymentInstructionEnrichment = paymentInstructionEnrichment;
        this.paymentInstructionService = paymentInstructionService;
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
        updatePaymentStatus(payment, disbursement, requestInfo);
        log.info("Updating the disbursement status for the payments : " + disbursementRequest.getMessage());
        // Get the disbursement response
        DisbursementResponse disbursementResponse = getDisbursementResponse(disbursementRequest);
        PaymentInstruction pi = paymentInstructionEnrichment.getPaymentInstructionFromDisbursement(disbursementResponse.getMessage());
        // Push the disbursement response to the disburse update topic
        muktaAdaptorProducer.push(muktaAdaptorConfig.getDisburseUpdateTopic(), disbursementResponse);
        paymentInstructionService.updatePIIndex(requestInfo, pi,false);
        return disbursementResponse;
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
        HashSet<StatusCode> statusCodes = new HashSet<>();
        for(Disbursement disbursement: disbursementResponse.getMessage().getDisbursements()){
            statusCodes.add(disbursement.getStatus().getStatusCode());
        }

        if(statusCodes.size() == 1){
            disbursementResponse.getMessage().setStatus(Status.builder().statusCode(statusCodes.iterator().next()).statusMessage(statusCodes.iterator().next().toString()).build());
        }else{
            disbursementResponse.getMessage().setStatus(Status.builder().statusCode(StatusCode.PARTIAL).statusMessage(StatusCode.PARTIAL.toString()).build());
        }
        return disbursementResponse;
    }

    /**
     * Updates the payment status based on the disbursement status
     * @param payment The payment
     * @param disbursement The disbursement
     * @param requestInfo The request info
     */
    private void updatePaymentStatus(Payment payment, Disbursement disbursement, RequestInfo requestInfo) {
        log.info("Updating payment status for the payment : " + payment);
        PaymentRequest paymentRequest = PaymentRequest.builder().requestInfo(requestInfo).payment(payment).build();
        EnumMap<StatusCode, PaymentStatus> lineItemIdStatusMap = getStatusCodeToPaymentStatusMap();
        HashMap<String, StatusCode> targetIdToStatusCodeMap = new HashMap<>();
        for(Disbursement disbursement1: disbursement.getDisbursements()){
            targetIdToStatusCodeMap.put(disbursement1.getTargetId(), disbursement1.getStatus().getStatusCode());
        }
        payment.getBills().forEach(bill ->
                bill.getBillDetails().forEach(billDetail ->
                        billDetail.getPayableLineItems().forEach(payableLineItem -> payableLineItem.setStatus(lineItemIdStatusMap.get(targetIdToStatusCodeMap.get(payableLineItem.getLineItemId()))))));
        if(disbursement.getStatus().getStatusCode().equals(StatusCode.PARTIAL)){
            updatePaymentStatusForPartial(payment, requestInfo);
        }else if(disbursement.getStatus().getStatusCode().equals(StatusCode.FAILED) || disbursement.getStatus().getStatusCode().equals(StatusCode.ERROR)){
            billUtils.updatePaymentStatus(paymentRequest,PaymentStatus.FAILED, ReferenceStatus.PAYMENT_FAILED);
        }else if(disbursement.getStatus().getStatusCode().equals(StatusCode.SUCCESSFUL)){
            billUtils.updatePaymentStatus(paymentRequest,PaymentStatus.SUCCESSFUL, ReferenceStatus.PAYMENT_SUCCESS);
        }
    }
    /**
     * Updates the payment status for partial
     * @param payment The payment
     * @param requestInfo The request info
     */
    private void updatePaymentStatusForPartial(Payment payment, RequestInfo requestInfo) {
        try {
            log.info("Updating payment status for partial.");
            boolean updatePaymentStatus = updatePaymentBills(payment);
            if (updatePaymentStatus) {
                payment.setStatus(PaymentStatus.PARTIAL);
                payment.setReferenceStatus(ReferenceStatus.PAYMENT_PARTIAL);
                PaymentRequest paymentRequest = PaymentRequest.builder().requestInfo(requestInfo).payment(payment).build();
                billUtils.callPaymentUpdate(paymentRequest);
            }
        } catch (Exception e) {
            log.error("Exception while updating the payment status FailureDetailsService:updatePaymentStatusForPartial : " + e);
        }
    }
    /**
     * Updates the payment status for partial
     * @param payment The payment
     * @return The boolean value
     */
    private boolean updatePaymentBills(Payment payment) {
        boolean updatePaymentStatus = false;
        for (PaymentBill bill : payment.getBills()) {
            boolean updateBillStatus = updateBillDetails(bill);
            if (updateBillStatus) {
                bill.setStatus(PaymentStatus.PARTIAL);
                updatePaymentStatus = true;
            }
        }
        return updatePaymentStatus;
    }
    /**
     * Updates the bill details
     * @param bill The payment bill
     * @return The boolean value
     */
    private boolean updateBillDetails(PaymentBill bill) {
        boolean updateBillStatus = false;
        for (PaymentBillDetail billDetail : bill.getBillDetails()) {
            boolean updateBillDetailsStatus = updateLineItems(billDetail);
            if (updateBillDetailsStatus) {
                billDetail.setStatus(PaymentStatus.PARTIAL);
                updateBillStatus = true;
            }
        }
        return updateBillStatus;
    }
    /**
     * Updates the line items
     * @param billDetail The payment bill detail
     * @return The boolean value
     */
    private boolean updateLineItems(PaymentBillDetail billDetail) {
        boolean updateBillDetailsStatus = false;
        for (PaymentLineItem lineItem : billDetail.getPayableLineItems()) {
            if (lineItem.getStatus().equals(PaymentStatus.FAILED)) {
                updateBillDetailsStatus = true;
                break;
            }
        }
        return updateBillDetailsStatus;
    }
    /**
     * Returns the status code to payment status map
     * @return The status code to payment status map
     */
    private EnumMap<StatusCode, PaymentStatus> getStatusCodeToPaymentStatusMap() {
        EnumMap<StatusCode,PaymentStatus> statusCodePaymentStatusHashMap = new EnumMap<>(StatusCode.class);
        statusCodePaymentStatusHashMap.put(StatusCode.INITIATED, PaymentStatus.INITIATED);
        statusCodePaymentStatusHashMap.put(StatusCode.INPROCESS, PaymentStatus.INITIATED);
        statusCodePaymentStatusHashMap.put(StatusCode.SUCCESSFUL,PaymentStatus.SUCCESSFUL);
        statusCodePaymentStatusHashMap.put(StatusCode.FAILED,PaymentStatus.FAILED);
        statusCodePaymentStatusHashMap.put(StatusCode.CANCELLED,PaymentStatus.CANCELLED);
        statusCodePaymentStatusHashMap.put(StatusCode.PARTIAL,PaymentStatus.PARTIAL);
        return statusCodePaymentStatusHashMap;
    }
}
