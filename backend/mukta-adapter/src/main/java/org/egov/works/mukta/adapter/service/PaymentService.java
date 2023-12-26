package org.egov.works.mukta.adapter.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.mukta.adapter.config.Constants;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.util.BillUtils;
import org.egov.works.mukta.adapter.util.MdmsUtil;
import org.egov.works.mukta.adapter.web.models.Pagination;
import org.egov.works.mukta.adapter.web.models.bill.*;
import org.egov.works.mukta.adapter.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@Service
public class PaymentService {

    private final BillUtils billUtils;
    private final MuktaAdaptorConfig config;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final MdmsUtil mdmsUtil;

    @Autowired
    public PaymentService(BillUtils billUtils, MuktaAdaptorConfig config, RestTemplate restTemplate, ObjectMapper mapper, MdmsUtil mdmsUtil) {
        this.billUtils = billUtils;
        this.config = config;
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.mdmsUtil = mdmsUtil;
    }

    /**
     * Creates payment automatically based by taking bills for which payment has not yet been created
     *
     * @param requestInfo
     */
    public void createPaymentFromBills(RequestInfo requestInfo) {
        // Fetches bill for which payment is not yet created
        List<Bill> bills = fetchBills(requestInfo);

        for (Bill bill : bills) {
            String wfStatus = bill.getWfStatus();
            if (bill.getPaymentStatus() == null && wfStatus != null && wfStatus.equalsIgnoreCase(Constants.APPROVED_STATUS)) {
                // Get payment request from bill
                PaymentRequest paymentRequest = getPaymentRequest(requestInfo, bill);
                // Payment create call
                List<Payment> payments = createPayment(paymentRequest);

                if (payments == null || payments.isEmpty()) {
                    log.error("Error creating Payment for bill number : " + bill.getBillNumber());
                }
            }

        }


    }

    public List<String> getTenants(RequestInfo requestInfo) {
        List<String> tenantIds = new ArrayList<>();
        List<String> tenantsMasters = new ArrayList<>();
        tenantsMasters.add(Constants.MDMS_TENANTS_MASTER);
        Map<String, Map<String, JSONArray>> tenantsResponse = mdmsUtil.fetchMdmsData(requestInfo, config.getStateLevelTenantId());
        JSONArray tenantValues = tenantsResponse.get(Constants.MDMS_TENANT_MODULE_NAME).get(Constants.MDMS_TENANTS_MASTER);
        for (Object tenant : tenantValues) {
            // Create ObjectMapper instance
            // Convert object to JsonNode
            JsonNode tenantNode = mapper.valueToTree(tenant);
            String tenantId = tenantNode.get("code").textValue();
            if (tenantId != null && !tenantId.equals(config.getStateLevelTenantId())) {
                tenantIds.add(tenantId);
            }
        }
        return tenantIds;
    }

    /**
     * fetches bills for which payment is not yet created by passing paymentStatus as null in search criteria.
     *
     * @param requestInfo
     * @return
     */
    private List<Bill> fetchBills(RequestInfo requestInfo) {
        List<Bill> bills = new ArrayList<>();
        // Gets the list of tenants from MDMS
//        List<String> tenantIds = getTenants(requestInfo);
//        log.info(tenantIds.toString());
        List<String> tenantIds = new ArrayList<>();
        tenantIds.add("pg.citya");
        //TODO: Remove this hardcoding
        Set<String> billNumbers = new HashSet<>();
        billNumbers.add("PB/2023-24/000523");
        // Fetch bills for which payment is not yet been created for every tenant
        for (String tenantId : tenantIds) {
            BillCriteria billCriteria = BillCriteria.builder()
                    .tenantId(tenantId)
                    .billNumbers(billNumbers)
                    .businessService("EXPENSE.PURCHASE")
                    .isPaymentStatusNull(true)
                    .build();

            Integer offset = 0;
            Integer limit = 100;
            List<Bill> currentBills;
            // loop for adding bills from every paged call to fetch bills
            do {
                BillSearchRequest billSearchRequest = BillSearchRequest.builder()
                        .requestInfo(requestInfo)
                        .billCriteria(billCriteria)
                        .pagination(Pagination.builder().limit(limit).offSet(offset).build())
                        .build();
                log.info(billSearchRequest.toString());
                currentBills = billUtils.fetchBillsData(billSearchRequest);
                if (currentBills == null) {
                    currentBills = new ArrayList<>();
                } else {
                    bills.addAll(currentBills);
                    offset += limit;
                }
            } while (currentBills.size() == limit);

        }
        return bills;
    }

    /**
     * Takes bill and creates payment create request based on the bill
     *
     * @param requestInfo
     * @param bill
     * @return
     */
    private PaymentRequest getPaymentRequest(RequestInfo requestInfo, Bill bill) {

        List<PaymentBillDetail> paymentBillDetails = new ArrayList<>();
        // loop to add list of bill details
        for (BillDetail billDetail : bill.getBillDetails()) {
            List<PaymentLineItem> payableLineItems = new ArrayList<>();
            // loop to add list of line items
            for (LineItem lineItem : billDetail.getPayableLineItems()) {
                if (lineItem.getStatus().equals(Status.ACTIVE)) {
                    PaymentLineItem payableLineItem = PaymentLineItem.builder().lineItemId(lineItem.getId())
                            .paidAmount(lineItem.getAmount()).tenantId(lineItem.getTenantId()).build();
                    payableLineItems.add(payableLineItem);
                }
            }
            // Create PaymentBillDetail for the above items
            PaymentBillDetail paymentBillDetail = PaymentBillDetail.builder()
                    .tenantId(billDetail.getTenantId())
                    .billDetailId(billDetail.getId())
                    .payableLineItems(payableLineItems)
                    .totalAmount(billDetail.getTotalAmount())
                    .totalPaidAmount(billDetail.getTotalAmount()).build();
            paymentBillDetails.add(paymentBillDetail);
        }
        // Create Payment bill from the above items
        List<PaymentBill> paymentBills = new ArrayList<>();
        PaymentBill paymentBill = PaymentBill.builder()
                .billId(bill.getId())
                .billDetails(paymentBillDetails)
                .tenantId(bill.getTenantId())
                .totalAmount(bill.getTotalAmount())
                .totalPaidAmount(bill.getTotalAmount()).build();
        paymentBills.add(paymentBill);

        // Create payment from the above items
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

    /**
     * funtion to send create payment request to payment service
     *
     * @param paymentRequest
     * @return
     */
    private @Valid List<Payment> createPayment(Object paymentRequest) {
        StringBuilder uri = new StringBuilder();
        uri.append(config.getBillHost()).append(config.getPaymentCreateEndpoint());
        Object response = new HashMap<>();
        PaymentResponse paymentResponse = new PaymentResponse();
        try {
            response = restTemplate.postForObject(uri.toString(), paymentRequest, Map.class);
            paymentResponse = mapper.convertValue(response, PaymentResponse.class);
        } catch (Exception e) {
            log.error("Exception while fetching posting create payment: ", e);
        }

        log.info(paymentResponse.toString());
        return paymentResponse.getPayments();
    }

}

