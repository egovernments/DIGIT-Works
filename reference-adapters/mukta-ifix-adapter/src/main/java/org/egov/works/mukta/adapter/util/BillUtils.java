package org.egov.works.mukta.adapter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.web.models.Pagination;
import org.egov.works.mukta.adapter.web.models.bill.*;
import org.egov.works.mukta.adapter.web.models.enums.PaymentStatus;
import org.egov.works.mukta.adapter.web.models.enums.ReferenceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.*;

@Component
@Slf4j
public class BillUtils {

    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    private final MuktaAdaptorConfig config;

    @Autowired
    public BillUtils(RestTemplate restTemplate, ObjectMapper mapper, MuktaAdaptorConfig config) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.config = config;
    }

    public @Valid List<Bill> fetchBillsFromPayment(PaymentRequest paymentRequest) {
        log.info("Started executing fetchBillsFromPayment");
        RequestInfo requestInfo = paymentRequest.getRequestInfo();
        List<String> billIds = new ArrayList<>();
        if (requestInfo != null && paymentRequest.getPayment().getBills() != null && !paymentRequest.getPayment().getBills().isEmpty()) {
            for (PaymentBill bill : paymentRequest.getPayment().getBills()) {
                billIds.add(bill.getBillId());
            }
        }
        String tenantId = paymentRequest.getPayment().getTenantId();
        BillCriteria billCriteria = BillCriteria.builder()
                .ids(new HashSet<>(billIds))
                .tenantId(tenantId)
                .build();

        BillSearchRequest billSearchRequest = BillSearchRequest.builder().requestInfo(requestInfo).billCriteria(billCriteria).build();


        return fetchBillsData(billSearchRequest);
    }

    public @Valid List<Bill> fetchBillsData(Object billRequest) {
        log.info("Started executing fetchBillsData");
        StringBuilder uri = new StringBuilder();
        uri.append(config.getBillHost()).append(config.getBillSearchEndpoint());
        Object response = new HashMap<>();
        BillResponse billResponse = new BillResponse();
        try {
            response = restTemplate.postForObject(uri.toString(), billRequest, Map.class);
            billResponse = mapper.convertValue(response, BillResponse.class);
            log.info("Fetched bill list from bill service.");
        } catch (Exception e) {
            log.error("Exception occurred while fetching bill lists from bill service: ", e);
        }
        log.info("Bill fetched and sending back.");
        return billResponse.getBills();
    }

    public @Valid List<Payment> callPaymentUpdate(Object paymentRequest) {
        log.info("Updating payment using bill service");
        StringBuilder uri = new StringBuilder();
        uri.append(config.getBillHost()).append(config.getPaymentUpdateEndpoint());
        Object response = new HashMap<>();
        PaymentResponse paymentResponse = new PaymentResponse();
        try {
            response = restTemplate.postForObject(uri.toString(), paymentRequest, Map.class);
            paymentResponse = mapper.convertValue(response, PaymentResponse.class);
            log.info("Payment updated in bill service");
        } catch (Exception e) {
            log.error("Exception occurred while updating payment into bill service: ", e);
        }

        return paymentResponse.getPayments();
    }

    public @Valid Object fetchBillFromCalculator(PaymentRequest paymentRequest, List<String> billNumbers) {
        RequestInfo requestInfo = paymentRequest.getRequestInfo();
        String tenantId = paymentRequest.getPayment().getTenantId();
        Map<String, Object> searchCriteria = new HashMap<>();
        searchCriteria.put("tenantId", tenantId);
        searchCriteria.put("billNumbers", billNumbers);
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("RequestInfo", requestInfo);
        requestParams.put("searchCriteria", searchCriteria);
        Pagination pagination = Pagination.builder().limit(billNumbers.size()).offSet(0).build();
        requestParams.put("pagination", pagination);

        return fetchBillCalculatorData(requestParams);
    }

    public @Valid Object fetchBillCalculatorData(Object billRequest) {
        StringBuilder uri = new StringBuilder();
        uri.append(config.getBillCalculatorHost()).append(config.getBillCalculatorSearchEndpoint());
        Object response = new HashMap<>();
        Object billCalcResponse = new Object();
        try {
            response = restTemplate.postForObject(uri.toString(), billRequest, Map.class);
            billCalcResponse = mapper.convertValue(response, Object.class);
            log.info("Fetched data from bill calculator service.");
        } catch (Exception e) {
            log.error("BillUtils:fetchBillCalculatorData - Exception occurred while fetching bill lists from bill calculator service: ", e);
        }

        return billCalcResponse;
    }

    public @Valid List<Payment> fetchPaymentDetails(RequestInfo requestInfo, String paymentNumber, String tenantId) {
        log.info("Started executing fetchPaymentDetails");
        Map<String, Object> searchCriteria = new HashMap<>();
        searchCriteria.put("tenantId", tenantId);
        searchCriteria.put("paymentNumbers", Collections.singletonList(paymentNumber));
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("RequestInfo", requestInfo);
        requestParams.put("paymentCriteria", searchCriteria);
        Pagination pagination = Pagination.builder().limit(10).offSet(0).build();
        requestParams.put("pagination", pagination);
        StringBuilder uri = new StringBuilder();
        uri.append(config.getBillHost()).append(config.getPaymentSearchEndpoint());
        Object response = new HashMap<>();
        PaymentResponse paymentResponse = null;
        try {
            response = restTemplate.postForObject(uri.toString(), requestParams, Map.class);
            paymentResponse = mapper.convertValue(response, PaymentResponse.class);
        } catch (Exception e) {
            log.error("Exception occurred while fetching payment details from bill service: ", e);
        }
        log.info("Payment fetched, sending back.");
        return paymentResponse != null? paymentResponse.getPayments() : null;
    }

    public void updatePaymentStatus(PaymentRequest paymentRequest, PaymentStatus paymentStatus, ReferenceStatus referenceStatus) {
        log.info("Started executing updatePaymentForStatus");
        paymentRequest.getPayment().setStatus(paymentStatus);
        paymentRequest.getPayment().setReferenceStatus(referenceStatus);
        for (PaymentBill bill : paymentRequest.getPayment().getBills()) {
            bill.setStatus(paymentStatus);
            for (PaymentBillDetail billDetail : bill.getBillDetails()) {
                billDetail.setStatus(paymentStatus);
                for (PaymentLineItem lineItem : billDetail.getPayableLineItems()) {
                    lineItem.setStatus(paymentStatus);
                }
            }
        }
        callPaymentUpdate(paymentRequest);
    }

}
