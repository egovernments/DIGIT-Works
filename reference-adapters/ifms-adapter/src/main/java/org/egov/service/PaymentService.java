package org.egov.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.IfmsAdapterConfig;
import org.egov.utils.BillUtils;
import org.egov.web.models.Pagination;
import org.egov.web.models.bill.*;
import org.egov.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PaymentService {

    @Autowired
    private BillUtils billUtils;
    @Autowired
    private IfmsAdapterConfig config;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private VirtualAllotmentService virtualAllotmentService;


    public void createPaymentFromBills (RequestInfo requestInfo) {
        List<Bill> bills = fetchBills(requestInfo);

        for (Bill bill : bills) {
            String paymentStatus = String.valueOf(bill.getPaymentStatus());
            String wfStatus = bill.getWfStatus();
            if (paymentStatus != null && !paymentStatus.isEmpty() && (wfStatus == null ||
                    wfStatus.equalsIgnoreCase("APPROVED"))) {

                PaymentRequest paymentRequest = getPaymentRequest(requestInfo, bill);
                List<Payment> payments = createPayment(paymentRequest);

                if (payments == null || payments.isEmpty()) {
                    log.error("Error creating Payment for bill number : " + bill.getBillNumber());
                }
            }

        }


    }
    private List<Bill> fetchBills(RequestInfo requestInfo) {
        List<Bill> bills = new ArrayList<>();
        List<String> tenantIds = virtualAllotmentService.getTenants(requestInfo);
        for (String tenantId : tenantIds) {

            BillCriteria billCriteria = BillCriteria.builder()
                    .tenantId(tenantId)
                    .isPaymentStatusNull(true)
                    .build();

            Integer offset = 0;
            Integer limit = config.getBillSearchLimit();
            List<Bill> currentBills;
            do {
                BillSearchRequest billSearchRequest = BillSearchRequest.builder()
                        .requestInfo(requestInfo)
                        .billCriteria(billCriteria)
                        .pagination(Pagination.builder().limit(limit).offSet(offset).build())
                        .build();
                currentBills = billUtils.fetchBillsData(billSearchRequest);
                if (currentBills == null) {
                    currentBills = new ArrayList<>();
                }
                else {
                    bills.addAll(currentBills);
                    offset += limit;
                }
            } while (currentBills.size() == limit);

        }
        return bills;
    }
    private PaymentRequest getPaymentRequest (RequestInfo requestInfo, Bill bill) {

        List<PaymentBillDetail> paymentBillDetails = new ArrayList<>();
        for(BillDetail billDetail : bill.getBillDetails()) {
            List<PaymentLineItem> payableLineItems = new ArrayList<>();
            for (LineItem lineItem : billDetail.getPayableLineItems()) {
                if (lineItem.getStatus().equals(Status.ACTIVE)) {
                    PaymentLineItem payableLineItem = PaymentLineItem.builder().lineItemId(lineItem.getId())
                            .paidAmount(lineItem.getAmount()).tenantId(lineItem.getTenantId()).build();
                    payableLineItems.add(payableLineItem);
                }
            }
            PaymentBillDetail paymentBillDetail = PaymentBillDetail.builder()
                    .tenantId(billDetail.getTenantId())
                    .billDetailId(billDetail.getId())
                    .payableLineItems(payableLineItems)
                    .totalAmount(billDetail.getTotalAmount())
                    .totalPaidAmount(billDetail.getTotalAmount()).build();
            paymentBillDetails.add(paymentBillDetail);
        }

        List<PaymentBill> paymentBills = new ArrayList<>();
        PaymentBill paymentBill = PaymentBill.builder()

                .billId(bill.getId())
                .billDetails(paymentBillDetails)
                .tenantId(bill.getTenantId())
                .totalAmount(bill.getTotalAmount())
                .totalPaidAmount(bill.getTotalAmount()).build();
        paymentBills.add(paymentBill);

        Payment payment = Payment.builder()
                .bills(paymentBills)
                .netPaidAmount(bill.getTotalAmount())
                .netPayableAmount(bill.getTotalAmount())
                .tenantId(bill.getTenantId()).build();

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .requestInfo(requestInfo)
                .payment(payment).build();

        log.info(paymentRequest.toString());
        return paymentRequest;
    }
    private @Valid List<Payment> createPayment(Object paymentRequest) {
        StringBuilder uri = new StringBuilder();
        uri.append(config.getBillHost()).append(config.getPaymentCreateEndpoint());
        Object response = new HashMap<>();
        PaymentResponse paymentResponse = new PaymentResponse();
        try {
            response = restTemplate.postForObject(uri.toString(), paymentRequest, Map.class);
            paymentResponse = mapper.convertValue(response, PaymentResponse.class);
        }catch (Exception e){
            log.error("Exception while fetching posting create payment: ", e);
        }

        log.info(paymentResponse.toString());
        return paymentResponse.getPayments();
    }

}

