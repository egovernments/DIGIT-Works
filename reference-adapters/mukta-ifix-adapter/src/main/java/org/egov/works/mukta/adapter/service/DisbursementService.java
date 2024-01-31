package org.egov.works.mukta.adapter.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.constants.Error;
import org.egov.works.mukta.adapter.util.BillUtils;
import org.egov.works.mukta.adapter.web.models.Disbursement;
import org.egov.works.mukta.adapter.web.models.DisbursementRequest;
import org.egov.works.mukta.adapter.web.models.Status;
import org.egov.works.mukta.adapter.web.models.bill.*;
import org.egov.works.mukta.adapter.web.models.enums.PaymentStatus;
import org.egov.works.mukta.adapter.web.models.enums.ReferenceStatus;
import org.egov.works.mukta.adapter.web.models.enums.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class DisbursementService {
    private final BillUtils billUtils;

    @Autowired
    public DisbursementService(BillUtils billUtils) {
        this.billUtils = billUtils;
    }

    public void processDisbursement(DisbursementRequest disbursementRequest) {
        log.info("Processing disbursement request");
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
    }

    private void updatePaymentStatus(Payment payment, Disbursement disbursement, RequestInfo requestInfo) {
        HashMap<StatusCode, PaymentStatus> lineItemIdStatusMap = getStatusCodeToPaymentStatusMap();
        HashMap<String, StatusCode> targetIdToStatusCodeMap = new HashMap<>();
        for(Disbursement disbursement1: disbursement.getDisbursements()){
            targetIdToStatusCodeMap.put(disbursement1.getTargetId(), disbursement1.getStatus().getStatusCode());
        }
        payment.getBills().forEach(bill ->
                bill.getBillDetails().forEach(billDetail ->
                        billDetail.getPayableLineItems().forEach(payableLineItem -> payableLineItem.setStatus(lineItemIdStatusMap.get(targetIdToStatusCodeMap.get(payableLineItem.getLineItemId()))))));
        updatePaymentStatusForPartial(payment, requestInfo);
    }

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

    private HashMap<StatusCode, PaymentStatus> getStatusCodeToPaymentStatusMap() {
        HashMap<StatusCode,PaymentStatus> statusCodePaymentStatusHashMap = new HashMap<>();
        statusCodePaymentStatusHashMap.put(StatusCode.INITIATED, PaymentStatus.INITIATED);
        statusCodePaymentStatusHashMap.put(StatusCode.SUCCESSFUL,PaymentStatus.SUCCESSFUL);
        statusCodePaymentStatusHashMap.put(StatusCode.FAILED,PaymentStatus.FAILED);
        statusCodePaymentStatusHashMap.put(StatusCode.CANCELLED,PaymentStatus.CANCELLED);
        statusCodePaymentStatusHashMap.put(StatusCode.PARTIAL,PaymentStatus.PARTIAL);
        return statusCodePaymentStatusHashMap;
    }
}