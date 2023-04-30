package org.egov.digit.expense.web.validators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.egov.digit.expense.service.BillService;
import org.egov.digit.expense.service.PaymentService;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillCriteria;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.models.LineItem;
import org.egov.digit.expense.web.models.Pagination;
import org.egov.digit.expense.web.models.Payment;
import org.egov.digit.expense.web.models.PaymentBill;
import org.egov.digit.expense.web.models.PaymentBillDetail;
import org.egov.digit.expense.web.models.PaymentCriteria;
import org.egov.digit.expense.web.models.PaymentLineItem;
import org.egov.digit.expense.web.models.PaymentRequest;
import org.egov.digit.expense.web.models.PaymentSearchRequest;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class PaymentValidator {

	@Autowired
	private BillService billService;
	
	@Autowired
	private PaymentService paymentService;
	
	public List<Payment> validateUpdateRequest(PaymentRequest paymentRequest) {

		Payment payment = paymentRequest.getPayment();
		if (payment.getId() == null)
			throw new CustomException("EG_EXPENSE_PAYMENT_UPDATE_ERROR", "Payment id is mandatory for update");
		
		PaymentSearchRequest searchRequest = getPaymentSearchRequest(paymentRequest);

		List<Payment> payments = paymentService.search(searchRequest).getPayments();
		if(CollectionUtils.isEmpty(payments))
			throw new CustomException("EG_EXPENSE_PAYMENT_UPDATE_ERROR", "Payment id is invalid");
		
		return payments;
	}
	
	public void validateCreateRequest(PaymentRequest paymentRequest) {

		Payment payment = paymentRequest.getPayment();
		Map<String, String> errorMap = new HashMap<>();
		Set<String> billIds = payment.getBills().stream().map(PaymentBill::getBillId).collect(Collectors.toSet());
		
		if(payment.getBills().size() != billIds.size())
			throw new CustomException("EG_PAYMENT_DUPLICATE_BILLS_ERROR", "The same bills cannot be repeated for payments");
			
		BillSearchRequest billSearchRequest = prepareBillCriteriaFromPaymentRequest(paymentRequest, billIds);
		List<Bill> billsFromSearch = billService.search(billSearchRequest).getBills();
		
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
	
		if (payment.getBills().size() != billMap.size()) {
			billIds.removeAll(billsFromSearch.stream().map(Bill::getId).collect(Collectors.toList()));
			throw new CustomException("EG_PAYMENT_INVALID_BILLS_ERROR",
					"Invalid bill ids found in the payment request : " + billIds);
		}
			
		List<String> IdErrorList = new ArrayList<>();
		List<String> amountErrorList = new ArrayList<>();

		for (PaymentBill paymentBill : payment.getBills()) {

			Bill billFromSearch = billMap.get(paymentBill.getBillId());

			/*
			 * Paid amount of incoming bill of payment should be equal to the total Amount of the bill
			 * 
			 * since partial payment is not allowed
			 */
			if (paymentBill.getTotalPaidAmount().compareTo(billFromSearch.getTotalAmount()) != 0) {
				
				amountErrorList.add("The paid amount " + paymentBill.getTotalPaidAmount() 
						+ " for bill with id : " + paymentBill.getBillId() 
						+ " is not equal to the actual amount : " + billFromSearch.getTotalAmount());
			}

			for (PaymentBillDetail billDetail : paymentBill.getBillDetails()) {

				BillDetail billDetailFromSearch = billDetailMap.get(billDetail.getBillDetailId());

				/*
				 * invalid id of bill detail from payment
				 * 
				 * Skip amount validation if id is invalid
				 */
				if (null == billDetailFromSearch) {

					IdErrorList.add("The bill detail id is invalid : " + billDetail.getId());
				}
				/*
				 * Skip amount validation if id of bill detail is invalid
				 */
				else {

					/*
					 * if bill detail is valid
					 * 
					 * then
					 * 
					 * verify the amount of bill detail from search is equal 
					 * with the paid amount of bill detail from payment
					 */
					if (billDetail.getTotalPaidAmount().compareTo(billDetailFromSearch.getTotalAmount()) != 0) {

						amountErrorList.add("The paid amount " + billDetail.getTotalPaidAmount()
								+ " for billDetail with id : " + billDetail.getBillDetailId()
								+ " is not equal to the actual amount : " + billDetailFromSearch.getTotalAmount());
					}

					for (PaymentLineItem payableLineItem : billDetail.getPayableLineItems()) {

						LineItem lineItemFromSearch = payableLineItemMap.get(payableLineItem.getLineItemId());
						if (null == lineItemFromSearch) {
							IdErrorList.add("The payable line item is invalid : " + payableLineItem.getLineItemId());
						}
						/*
						 * Skip amount validation if id of bill detail is invalid
						 */
						else if (payableLineItem.getPaidAmount().compareTo(lineItemFromSearch.getAmount()) != 0) {

							amountErrorList.add("The paid line item amount " + payableLineItem.getPaidAmount()
									+ " for line item with id : " + payableLineItem.getLineItemId()
									+ "is not equal to the actual amount : " + lineItemFromSearch.getAmount());
						}
					}
				}
			}
		}
		
		if (!CollectionUtils.isEmpty(IdErrorList))
			errorMap.put("EG_EXPENSE_PAYMENT_INVALID_ITEMS", IdErrorList.toString());

		if (!CollectionUtils.isEmpty(amountErrorList))
			errorMap.put("EG_EXPENSE_PAYMENT_INVALID_AMOUNT", amountErrorList.toString());
		
		if (!CollectionUtils.isEmpty(errorMap))
			throw new CustomException(errorMap);
	}

	public BillSearchRequest prepareBillCriteriaFromPaymentRequest (PaymentRequest paymentRequest, Set<String> billIds) {
		
		Payment payment = paymentRequest.getPayment();
		BillCriteria billCriteria = BillCriteria.builder()
				.tenantId(payment.getTenantId())
				.ids(billIds)
				.build();
		Pagination pagination = Pagination.builder()
				.offSet(0)
				.limit(billIds.size())
				.build();
		
		BillSearchRequest billSearchRequest = BillSearchRequest.builder()
				.billCriteria(billCriteria)
				.pagination(pagination)
				.requestInfo(paymentRequest.getRequestInfo())
				.build();
		return billSearchRequest;
	}
	
	public PaymentSearchRequest getPaymentSearchRequest (PaymentRequest paymentRequest) {
		
		Payment payment = paymentRequest.getPayment();
		PaymentCriteria criteria = PaymentCriteria.builder()
				.ids(Stream.of(payment.getId()).collect(Collectors.toSet()))
				.tenantId(payment.getTenantId())
				.build();
		
		return PaymentSearchRequest.builder()
				.requestInfo(paymentRequest.getRequestInfo())
				.paymentCriteria(criteria)
				.build();
	}

}
