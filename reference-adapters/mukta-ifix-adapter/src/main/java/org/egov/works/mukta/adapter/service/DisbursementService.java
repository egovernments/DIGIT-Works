package org.egov.works.mukta.adapter.service;

import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.constants.Error;
import org.egov.works.mukta.adapter.kafka.MuktaAdaptorProducer;
import org.egov.works.mukta.adapter.util.BillUtils;
import org.egov.works.mukta.adapter.validators.DisbursementValidator;
import org.egov.works.mukta.adapter.web.models.Disbursement;
import org.egov.works.mukta.adapter.web.models.DisbursementRequest;
import org.egov.works.mukta.adapter.web.models.DisbursementResponse;
import org.egov.works.mukta.adapter.web.models.Status;
import org.egov.works.mukta.adapter.web.models.bill.*;
import org.egov.works.mukta.adapter.web.models.enums.PaymentStatus;
import org.egov.works.mukta.adapter.web.models.enums.ReferenceStatus;
import org.egov.works.mukta.adapter.web.models.enums.StatusCode;
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

    @Autowired
    public DisbursementService(BillUtils billUtils, MuktaAdaptorConfig muktaAdaptorConfig, MuktaAdaptorProducer muktaAdaptorProducer, DisbursementValidator disbursementValidator) {
        this.billUtils = billUtils;
        this.muktaAdaptorConfig = muktaAdaptorConfig;
        this.muktaAdaptorProducer = muktaAdaptorProducer;
        this.disbursementValidator = disbursementValidator;
    }
    /**
     * Processes the disbursement request and updates the payment status
     * @param disbursementRequest The disbursement request
     * @return The disbursement response
     */
    public DisbursementResponse processOnDisbursement(DisbursementRequest disbursementRequest) {
        log.info("Processing disbursement request");
        disbursementValidator.validateOnDisbursementRequest(disbursementRequest);
        Disbursement disbursement = disbursementRequest.getMessage();
        String tenantId = disbursement.getLocationCode();
        //TODO: FIX MANUAL UUID
        RequestInfo requestInfo = RequestInfo.builder().userInfo(User.builder().uuid("ee3379e9-7f25-4be8-9cc1-dc599e1668c9").build()).build();
        List<Payment> payments = billUtils.fetchPaymentDetails(requestInfo, disbursement.getTargetId(), tenantId);
        if (payments == null || payments.isEmpty()) {
            throw new CustomException(Error.PAYMENT_NOT_FOUND, Error.PAYMENT_NOT_FOUND_MESSAGE);
        }
        log.info("Payments fetched for the disbursement request : " + payments);
        Payment payment = payments.get(0);
        log.info("Updating the payment status for the payments : " + payment);
        updatePaymentStatus(payment, disbursement, requestInfo);
        log.info("Updating the disbursement status for the payments : " + disbursementRequest.getMessage());
        DisbursementResponse disbursementResponse = getDisbursementResponse(disbursementRequest);
        muktaAdaptorProducer.push(muktaAdaptorConfig.getDisburseUpdateTopic(), disbursementResponse);
        return disbursementResponse;
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
        EnumMap<StatusCode, PaymentStatus> lineItemIdStatusMap = getStatusCodeToPaymentStatusMap();
        HashMap<String, StatusCode> targetIdToStatusCodeMap = new HashMap<>();
        for(Disbursement disbursement1: disbursement.getDisbursements()){
            targetIdToStatusCodeMap.put(disbursement1.getTargetId(), disbursement1.getStatus().getStatusCode());
        }
        payment.getBills().forEach(bill ->
                bill.getBillDetails().forEach(billDetail ->
                        billDetail.getPayableLineItems().forEach(payableLineItem -> payableLineItem.setStatus(lineItemIdStatusMap.get(targetIdToStatusCodeMap.get(payableLineItem.getLineItemId()))))));
        updatePaymentStatusForPartial(payment, requestInfo);
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
        statusCodePaymentStatusHashMap.put(StatusCode.IN_PROCESS, PaymentStatus.INITIATED);
        statusCodePaymentStatusHashMap.put(StatusCode.SUCCESSFUL,PaymentStatus.SUCCESSFUL);
        statusCodePaymentStatusHashMap.put(StatusCode.FAILED,PaymentStatus.FAILED);
        statusCodePaymentStatusHashMap.put(StatusCode.CANCELLED,PaymentStatus.CANCELLED);
        statusCodePaymentStatusHashMap.put(StatusCode.PARTIAL,PaymentStatus.PARTIAL);
        return statusCodePaymentStatusHashMap;
    }
}
