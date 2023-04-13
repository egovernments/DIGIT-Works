package org.egov.digit.expense.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.Producer;
import org.egov.digit.expense.repository.PaymentRepository;
import org.egov.digit.expense.util.EnrichmentUtil;
import org.egov.digit.expense.util.ResponseInfoFactory;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.models.LineItem;
import org.egov.digit.expense.web.models.Payment;
import org.egov.digit.expense.web.models.PaymentRequest;
import org.egov.digit.expense.web.models.PaymentResponse;
import org.egov.digit.expense.web.models.PaymentSearchRequest;
import org.egov.digit.expense.web.validators.PaymentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import digit.models.coremodels.AuditDetails;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentValidator validator;
	
	@Autowired
	private Producer producer;
	
	@Autowired
	private Configuration config;
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private EnrichmentUtil enrichmentUtil;
	
	@Autowired
	private ResponseInfoFactory responseInfoFactory;
	
	@Autowired
	private PaymentRepository paymentRepository;

	public PaymentResponse create(@Valid PaymentRequest paymentRequest) {

		Payment payment = paymentRequest.getPayment();
		validator.validateCreateRequest(paymentRequest);
		enrichmentUtil.encrichCreatePayment(paymentRequest);
		payment.setStatus("PAID");
		producer.push(config.getPaymentCreateTopic(), payment);
		backUpdateBillForPayment(paymentRequest);
		
		return PaymentResponse.builder()
				.payments(Arrays.asList(payment))
				.responseInfo(
						responseInfoFactory.createResponseInfoFromRequestInfo(paymentRequest.getRequestInfo(), true))
				.build();
	}

	public PaymentResponse update(@Valid PaymentRequest paymentRequest) {

		Payment payment = paymentRequest.getPayment();
		validator.validateUpdateRequest(paymentRequest);
		enrichmentUtil.encrichUpdatePayment(paymentRequest);
		
		/* only status update should be allowed here */
		producer.push(config.getPaymentCreateTopic(), payment);
		return PaymentResponse.builder()
				.payments(Arrays.asList(payment))
				.responseInfo(
						responseInfoFactory.createResponseInfoFromRequestInfo(paymentRequest.getRequestInfo(), true))
				.build();
	}

	public PaymentResponse search(@Valid PaymentSearchRequest paymentSearchRequest) {

		List<Payment> payments = paymentRepository.search(paymentSearchRequest);
		/*
		 * TODO enrich bills if required, can be done from UI only when needed
		 */
		return PaymentResponse.builder()
				.payments(payments)
				.responseInfo(
						responseInfoFactory.createResponseInfoFromRequestInfo(paymentSearchRequest.getRequestInfo(), true))
				.build();
	}
	
	private void backUpdateBillForPayment(@Valid PaymentRequest paymentRequest) {
		
		RequestInfo requestInfo = paymentRequest.getRequestInfo();
		Payment payment = paymentRequest.getPayment();
		String createdBy = paymentRequest.getRequestInfo().getUserInfo().getUuid();
		AuditDetails auditDetails = enrichmentUtil.getAuditDetails(createdBy, paymentRequest.getPayment().getAuditDetails(), false);
		
		Set<String> billIds = paymentRequest.getPayment().getBills()
				.stream().map(Bill::getId)
				.collect(Collectors.toSet());
		
		BillSearchRequest billSearchRequest = validator.prepareBillCriteriaFromPaymentRequest(paymentRequest, billIds);

		List<Bill> billsFromSearch =  billService.search(billSearchRequest).getBills();
		
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
		
		
		
		for (Bill bill : payment.getBills()) {
			
			Bill billFromSearch = billMap.get(bill.getId());
			
			billFromSearch.setNetPaidAmount(bill.getNetPaidAmount());
			billFromSearch.setPaymentStatus(payment.getStatus());
			billFromSearch.setAuditDetails(auditDetails);
			
			for (BillDetail billDetail : bill.getBillDetails()) {
				
				BillDetail billDetailFromSearch = billDetailMap.get(billDetail.getId());
				billDetailFromSearch.setPaymentStatus(billDetail.getPaymentStatus());
				billDetailFromSearch.setAuditDetails(auditDetails);

				for (LineItem payableLineItem : billDetail.getPayableLineItems()) {

					LineItem lineItemFromSearch = payableLineItemMap.get(payableLineItem.getId());
					lineItemFromSearch.setPaidAmount(payableLineItem.getPaidAmount());
					lineItemFromSearch.setAuditDetails(auditDetails);
				}
			}
		}
		/*
		 *  TODO create new bulk bill request for multiple bills persistence at once
		 */
		for (Bill bill : billsFromSearch) {
			
			BillRequest billRequest = BillRequest.builder()
					.bill(bill)
					.requestInfo(requestInfo)
					.build();
					billService.update(billRequest); 

		}
	}
}
