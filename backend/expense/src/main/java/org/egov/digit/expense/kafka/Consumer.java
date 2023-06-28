package org.egov.digit.expense.kafka;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.service.PaymentService;
import org.egov.digit.expense.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    PaymentService paymentService;

    @KafkaListener(topics = {"${expense.billing.bill.create}","${expense.billing.bill.update}"})
    public void createPayment(String consumerRecord, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            BillRequest bill = objectMapper.readValue(consumerRecord, BillRequest.class);

            List<PaymentBillDetail> paymentBillDetails = new ArrayList<>();
            for(BillDetail billDetail : bill.getBill().getBillDetails()) {
                List<PaymentLineItem> payableLineItems = new ArrayList<>();
                for (LineItem lineItem : billDetail.getPayableLineItems()) {
                    PaymentLineItem payableLineItem = PaymentLineItem.builder().lineItemId(lineItem.getId())
                            .paidAmount(BigDecimal.ZERO).tenantId(lineItem.getTenantId()).build();
                    payableLineItems.add(payableLineItem);
                }
                PaymentBillDetail paymentBillDetail = PaymentBillDetail.builder()
                        .billDetailId(billDetail.getBillId())
                        .payableLineItems(payableLineItems)
                        .totalAmount(billDetail.getTotalAmount())
                        .totalPaidAmount(BigDecimal.ZERO).build();
                paymentBillDetails.add(paymentBillDetail);
            }

            List<PaymentBill> paymentBills = new ArrayList<>();
            PaymentBill paymentBill = PaymentBill.builder()
                    .billId(bill.getBill().getId())
                    .billDetails(paymentBillDetails)
                    .tenantId(bill.getBill().getTenantId())
                    .totalAmount(bill.getBill().getTotalAmount())
                    .totalPaidAmount(BigDecimal.ZERO).build();
            paymentBills.add(paymentBill);

            Payment payment = Payment.builder()
                    .bills(paymentBills)
                    .netPaidAmount(BigDecimal.ZERO)
                    .netPayableAmount(bill.getBill().getTotalAmount())
                    .tenantId(bill.getBill().getTenantId()).build();

            PaymentRequest paymentRequest = PaymentRequest.builder()
                    .requestInfo(bill.getRequestInfo())
                    .payment(payment).build();

            PaymentResponse paymentResponse = paymentService.create(paymentRequest);
            log.info(paymentResponse.toString());

        }catch (Exception e){
            log.error("Error while fetching bills", e);
        }

    }
}
