package org.egov.works.mukta.adapter.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.constants.Error;
import org.egov.works.mukta.adapter.util.BillUtils;
import org.egov.works.mukta.adapter.web.models.Disbursement;
import org.egov.works.mukta.adapter.web.models.Status;
import org.egov.works.mukta.adapter.web.models.bill.*;
import org.egov.works.mukta.adapter.web.models.enums.PaymentStatus;
import org.egov.works.mukta.adapter.web.models.enums.ReferenceStatus;
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

    public void processDisbursement(Disbursement disbursementRequest) {
        log.info("Processing disbursement request");
        RequestInfo requestInfo = RequestInfo.builder().userInfo(User.builder().uuid("ee3379e9-7f25-4be8-9cc1-dc599e1668c9").build()).build();
        List<Payment> payments = billUtils.fetchPaymentDetails(requestInfo, disbursementRequest.getTargetId(), "pg.citya");
        if (payments == null || payments.isEmpty()) {
            throw new CustomException(Error.PAYMENT_NOT_FOUND, Error.PAYMENT_NOT_FOUND_MESSAGE);
        }
        log.info("Payments fetched for the disbursement request : " + payments);
        Payment payment = payments.get(0);
        log.info("Updating the payment status for the payments : " + payment);
        updatePaymentStatus(payment, disbursementRequest, requestInfo);
    }

    private void updatePaymentStatus(Payment payment, Disbursement disbursementRequest, RequestInfo requestInfo) {
        HashMap<String, Status> lineItemIdStatusMap = getLineItemIdStatusMap(disbursementRequest);
        payment.getBills().forEach(bill -> bill.getBillDetails().forEach(billDetail -> billDetail.getPayableLineItems().forEach(payableLineItem -> payableLineItem.setStatus(PaymentStatus.INITIATED))));
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

    private HashMap<String, Status> getLineItemIdStatusMap(Disbursement disbursementRequest) {
        HashMap<String, Status> lineItemIdStatusMap = new HashMap<>();
        disbursementRequest.getBills().forEach(disbursement -> lineItemIdStatusMap.put(disbursement.getTargetId(), disbursement.getStatus()));
        return lineItemIdStatusMap;
    }
}
