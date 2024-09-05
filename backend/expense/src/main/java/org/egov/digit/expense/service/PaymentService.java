package org.egov.digit.expense.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.PaymentRepository;
import org.egov.digit.expense.util.EnrichmentUtil;
import org.egov.digit.expense.util.ResponseInfoFactory;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.PaymentStatus;
import org.egov.digit.expense.web.validators.PaymentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentService {

    private final PaymentValidator validator;

    private final ExpenseProducer expenseProducer;

    private final Configuration config;

    private final BillService billService;

    private final EnrichmentUtil enrichmentUtil;

    private final ResponseInfoFactory responseInfoFactory;

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentValidator validator, ExpenseProducer expenseProducer, Configuration config, BillService billService, EnrichmentUtil enrichmentUtil, ResponseInfoFactory responseInfoFactory, PaymentRepository paymentRepository) {
        this.validator = validator;
        this.expenseProducer = expenseProducer;
        this.config = config;
        this.billService = billService;
        this.enrichmentUtil = enrichmentUtil;
        this.responseInfoFactory = responseInfoFactory;
        this.paymentRepository = paymentRepository;
    }

    public PaymentResponse create(@Valid PaymentRequest paymentRequest) {
    	
        log.info("PaymentService::create");
        Payment payment = paymentRequest.getPayment();
        validator.validateCreateRequest(paymentRequest);
        enrichmentUtil.encrichCreatePayment(paymentRequest);

        expenseProducer.push(config.getPaymentCreateTopic(), paymentRequest);
        backUpdateBillForPayment(paymentRequest);

        return PaymentResponse.builder()
                .payments(Arrays.asList(payment))
                .responseInfo(
                        responseInfoFactory.createResponseInfoFromRequestInfo(paymentRequest.getRequestInfo(), true))
                .build();
    }

    public PaymentResponse update(@Valid PaymentRequest paymentRequest) {
    	
        log.info("PaymentService::update");
        Payment payment = paymentRequest.getPayment();
        List<Payment> paymentsFromSearch = validator.validateUpdateRequest(paymentRequest);
        enrichmentUtil.encrichUpdatePayment(paymentRequest, paymentsFromSearch.get(0));
        paymentRequest.setPayment(paymentsFromSearch.get(0));
        backUpdateBillForPayment(paymentRequest);

        /* only status update should be allowed here */
        expenseProducer.push(config.getPaymentUpdateTopic(), paymentRequest);
        return PaymentResponse.builder()
                .payments(Arrays.asList(payment))
                .responseInfo(
                        responseInfoFactory.createResponseInfoFromRequestInfo(paymentRequest.getRequestInfo(), true))
                .build();
    }

    public PaymentResponse search(@Valid PaymentSearchRequest paymentSearchRequest) {
    	
        log.info("PaymentService::search");
        List<Payment> payments = paymentRepository.search(paymentSearchRequest);
        Integer count = paymentRepository.count(paymentSearchRequest);
        Pagination pagination = paymentSearchRequest.getPagination();
        pagination.setTotalCount(count);
        /*
         * TODO enrich bills if required, can be done from UI only when needed
         */
        return PaymentResponse.builder()
                .payments(payments)
                .pagination(pagination)
                .responseInfo(
                        responseInfoFactory.createResponseInfoFromRequestInfo(paymentSearchRequest.getRequestInfo(), true))
                .build();
    }

    private void backUpdateBillForPayment(@Valid PaymentRequest paymentRequest) {
    	
        log.info("PaymentService::backUpdateBillForPayment");
        RequestInfo requestInfo = paymentRequest.getRequestInfo();
        Payment payment = paymentRequest.getPayment();
        String createdBy = paymentRequest.getRequestInfo().getUserInfo().getUuid();
        AuditDetails auditDetails = enrichmentUtil.getAuditDetails(createdBy, true);
        

        Set<String> billIds = paymentRequest.getPayment().getBills()
                .stream().map(PaymentBill::getBillId)
                .collect(Collectors.toSet());

        BillSearchRequest billSearchRequest = validator.prepareBillCriteriaFromPaymentRequest(paymentRequest, billIds);
        List<Bill> billsFromSearch = billService.search(billSearchRequest, false).getBills();

        Map<String, Bill> billMap = billsFromSearch.stream()
                .collect(Collectors.toMap(Bill::getId, Function.identity()));

        Map<String, BillDetail> billDetailMap = billsFromSearch.stream()
                .map(Bill::getBillDetails)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(BillDetail::getId, Function.identity()));

        Map<String, LineItem> payableLineItemMap = billsFromSearch.stream()
                .map(Bill::getBillDetails)
                .flatMap(Collection::stream)
                .map(BillDetail::getPayableLineItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(LineItem::getId, Function.identity()));

		for (PaymentBill paymentBill : payment.getBills()) {

			Bill billFromSearch = billMap.get(paymentBill.getBillId());
			billFromSearch.setPaymentStatus(payment.getStatus());
			billFromSearch.setAuditDetails(auditDetails);
            // Define bill paid amount to ZERO, it will calculeted in bill details
            BigDecimal billPaidAmount = BigDecimal.ZERO;
            for (PaymentBillDetail paymentBillDetail : paymentBill.getBillDetails()) {

				BillDetail billDetailFromSearch = billDetailMap.get(paymentBillDetail.getBillDetailId());
				billDetailFromSearch.setPaymentStatus(paymentBillDetail.getStatus());
				billDetailFromSearch.setAuditDetails(auditDetails);
                // Define bill details paid amount to ZERO, it will calculeted in lineitem
                BigDecimal billDetailPaidAmount = BigDecimal.ZERO;
                for (PaymentLineItem payableLineItem : paymentBillDetail.getPayableLineItems()) {

					LineItem lineItemFromSearch = payableLineItemMap.get(payableLineItem.getLineItemId());
					lineItemFromSearch.setPaymentStatus(payableLineItem.getStatus());
                    /**
                     * Set paid amount based on payment status, because don't have support of partial payment
                     * todo: Remove this while implementing partial payment
                     */
                    lineItemFromSearch.setPaidAmount(getLineItemPaidAmountByStatus(lineItemFromSearch, payableLineItem.getStatus()));
                    billDetailPaidAmount = billDetailPaidAmount.add(lineItemFromSearch.getPaidAmount());
					lineItemFromSearch.setAuditDetails(auditDetails);
				}
                // Add lineitem paid amount to billdetails and
                billDetailFromSearch.setTotalPaidAmount(billDetailPaidAmount);
                billPaidAmount = billPaidAmount.add(billDetailPaidAmount);
			}
            billFromSearch.setTotalPaidAmount(billPaidAmount);
		}
		
        /*
         *  TODO create new bulk bill request for multiple bills persistence at once
         */
        for (Bill bill : billsFromSearch) {

            BillRequest billRequest = BillRequest.builder()
                    .bill(bill)
                    .requestInfo(requestInfo)
                    .build();
            expenseProducer.push(config.getBillUpdateTopic(), billRequest);
        }
    }


    /**
     * For lineitem paid amount will be either ZERO or same as Payable amount
     * @param lineItem
     * @param paymentStatus
     * @return
     */
    private BigDecimal getLineItemPaidAmountByStatus(LineItem lineItem, PaymentStatus paymentStatus) {
        BigDecimal paidAmount = BigDecimal.ZERO;
        if (lineItem != null && paymentStatus != null && (paymentStatus.equals(PaymentStatus.SUCCESSFUL)))
                {paidAmount = lineItem.getAmount();
        }
        return paidAmount;
    }


}
