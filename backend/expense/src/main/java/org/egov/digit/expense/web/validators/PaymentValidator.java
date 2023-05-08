package org.egov.digit.expense.web.validators;

import java.math.BigDecimal;
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
import org.egov.digit.expense.web.models.enums.Status;
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

		Map<String, String> errorMap = new HashMap<>();
		Payment payment = paymentRequest.getPayment();
		
		if (payment.getId() == null)
			throw new CustomException("EG_EXPENSE_PAYMENT_UPDATE_ERROR", "Payment id is mandatory for update");
		
		if(null == paymentRequest.getPayment().getStatus()) {
			throw new CustomException("EG_PAYMENT_UPDATE_STATUS_NOTNULL"," Payment status is mandatory in update request");
		}
		
		PaymentSearchRequest searchRequest = getPaymentSearchRequest(paymentRequest);
		List<Payment> payments = paymentService.search(searchRequest).getPayments();
		if(CollectionUtils.isEmpty(payments))
			throw new CustomException("EG_EXPENSE_PAYMENT_UPDATE_ERROR", "Payment id is invalid");
		
		validateBillForPayment(paymentRequest, errorMap, false);
		
		boolean isAnyStatusNull = false;
		for (PaymentBill paymentBill : payment.getBills()) {

			if(null == paymentBill.getStatus()) {
				isAnyStatusNull = true;
				break;
			}
			
			for (PaymentBillDetail billDetail : paymentBill.getBillDetails()) {
				
				if(null == billDetail.getStatus()) {
					isAnyStatusNull = true;
					break;
				}
				
				for (PaymentLineItem lineItem : billDetail.getPayableLineItems()) {
					
					if(null == lineItem.getStatus()) {
						isAnyStatusNull = true;
						break;
					}			
				}
			}
		}
		
		if(isAnyStatusNull)
			errorMap.put("EG_EXPENSE_PAYMENT_UPDATE_STATUS_NOTNULL",
					"Status is mandatory for the payment, bill, billdetails and lineitems in payment update request");
		
		if (!CollectionUtils.isEmpty(errorMap))
			throw new CustomException(errorMap);
		
		return payments;
	}
	
	public void validateCreateRequest(PaymentRequest paymentRequest) {

		Map<String, String> errorMap = new HashMap<>();
		
		validateBillForPayment(paymentRequest, errorMap, true);
		if (!CollectionUtils.isEmpty(errorMap))
			throw new CustomException(errorMap);
	}

	private void validateBillForPayment(PaymentRequest paymentRequest, Map<String, String> errorMap, boolean isCreate) {
		

		Payment payment = paymentRequest.getPayment();
		Set<String> billIds = payment.getBills().stream().map(PaymentBill::getBillId).collect(Collectors.toSet());
		if (payment.getBills().size() != billIds.size())
			throw new CustomException("EG_PAYMENT_DUPLICATE_BILLS_ERROR",
					"The same bills cannot be repeated in the payment request");

		BillSearchRequest billSearchRequest = prepareBillCriteriaFromPaymentRequest(paymentRequest, billIds);
		List<Bill> billsFromSearch = billService.search(billSearchRequest).getBills();

		Map<String, Bill> billMap = billsFromSearch.stream().filter(bill -> bill.getStatus().equals(Status.ACTIVE))
				.collect(Collectors.toMap(Bill::getId, Function.identity()));
		
		if (payment.getBills().size() != billMap.size()) {
			billIds.removeAll(billMap.keySet());
			throw new CustomException("EG_PAYMENT_INVALID_BILLS_ERROR",
					"The following bill ids are either Invalid or not ACTIVE in the system : " + billIds);
		}
		
		BigDecimal amountToBePaid = BigDecimal.ZERO;
		
		for (Bill bill : billsFromSearch) {
			amountToBePaid = amountToBePaid.add(bill.getTotalAmount().subtract(bill.getTotalPaidAmount()));
		}

		if (amountToBePaid.compareTo(payment.getNetPayableAmount()) != 0
				|| amountToBePaid.compareTo(payment.getNetPaidAmount()) != 0) {
			throw new CustomException("EG_PAYMENT_INVALID_PAYMENT_ERROR",
					"The netPayableAmount and netPaidAmount should be equal to pending amount : " + amountToBePaid
							+ " of the bills provided in the payment ");
		}
		
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
	
		int billIndex = 0;
		for (PaymentBill paymentBill : payment.getBills()) {

			Bill billFromSearch = billMap.get(paymentBill.getBillId());
			BigDecimal totalBillPayment = paymentBill.getTotalPaidAmount().add(billFromSearch.getTotalPaidAmount());

			/*
			 * Paid amount of incoming bill of payment should be equal to the total Amount of the bill
			 * 
			 * since partial payment is not allowed
			 */
			if (paymentBill.getTotalPaidAmount().compareTo(billFromSearch.getTotalAmount()) > 0) {
				
				errorMap.put("EG_EXPENSE_PAYMENT_INVALID_BILL[" + billIndex + "]",
						"The paid amount " + paymentBill.getTotalPaidAmount() + " for bill with id : "
								+ paymentBill.getBillId() + " is greater than the actual amount : "
								+ billFromSearch.getTotalAmount());
			}
			else if (totalBillPayment.compareTo(billFromSearch.getTotalAmount()) > 0) {

				errorMap.put("EG_EXPENSE_PAYMENT_BILL_INVALID_AMOUNT[" + billIndex + "]",
						"The total payment for bill with id : " + paymentBill.getBillId()
								+ " is greater than the actual amount : " + billFromSearch.getTotalAmount());
			}
			int billDetailIndex = 0;
			for (PaymentBillDetail paymentBillDetail : paymentBill.getBillDetails()) {

				validateBillDetail(billDetailMap, payableLineItemMap, paymentBillDetail, errorMap, billDetailIndex);
				billDetailIndex++;
			}
			billIndex++;
		}
	}

	private void validateBillDetail(Map<String, BillDetail> billDetailMap, Map<String, LineItem> payableLineItemMap,
			PaymentBillDetail paymentBillDetail, Map<String, String> errorMap, int billDetailIndex) {

		BillDetail billDetailFromSearch = billDetailMap.get(paymentBillDetail.getBillDetailId());

		/*
		 * invalid id of bill detail from payment
		 * 
		 * Skip amount validation if id is invalid
		 */
		if (null == billDetailFromSearch) {

			errorMap.put("EG_EXPENSE_PAYMENT_INVALID_BILL_DETAIL[" + billDetailIndex + "]","The bill detail id is invalid : " + paymentBillDetail.getBillDetailId());
		}
		/*
		 * Skip amount validation if id of bill detail is invalid
		 */
		else {
			BigDecimal totalBillDetailPayment = paymentBillDetail.getTotalPaidAmount().add(billDetailFromSearch.getTotalPaidAmount());
			/*
			 * if bill detail is valid
			 * 
			 * then
			 * 
			 * verify the amount of bill detail from search is equal 
			 * with the paid amount of bill detail from payment
			 */
			if (paymentBillDetail.getTotalPaidAmount().compareTo(billDetailFromSearch.getTotalAmount()) > 0) {

				errorMap.put("EG_EXPENSE_PAYMENT_BILLDETAIL_INVALID_AMOUNT[" + billDetailIndex + "]","The paid amount " + paymentBillDetail.getTotalPaidAmount()
						+ " for billDetail with id : " + paymentBillDetail.getBillDetailId()
						+ " is greater than the actual amount : " + billDetailFromSearch.getTotalAmount());
				
			} else if(totalBillDetailPayment.compareTo(billDetailFromSearch.getTotalAmount()) > 0) {
				
				errorMap.put("EG_EXPENSE_PAYMENT_BILLDETAIL_INVALID_AMOUNT[" + billDetailIndex + "]","The total payment for billDetail with id : " + paymentBillDetail.getBillDetailId()
				+ " is greater than the actual amount : " + billDetailFromSearch.getTotalAmount());
			}

			int lineItemIndex = 0;
			for (PaymentLineItem payableLineItem : paymentBillDetail.getPayableLineItems()) {

				validateLineItem(payableLineItemMap, payableLineItem, errorMap, lineItemIndex);
				lineItemIndex++;
			}
		}
	}

	private void validateLineItem(Map<String, LineItem> payableLineItemMap, PaymentLineItem payableLineItem,
			Map<String, String> errorMap, int lineItemIndex) {

		LineItem lineItemFromSearch = payableLineItemMap.get(payableLineItem.getLineItemId());
		if (null == lineItemFromSearch) {
			errorMap.put("EG_EXPENSE_PAYMENT_INVALID_LINEITEM[" + lineItemIndex + "]","The payable line item is invalid : " + payableLineItem.getLineItemId());
		}else {
			
			BigDecimal totalLineItemPayment = payableLineItem.getPaidAmount().add(lineItemFromSearch.getPaidAmount());
			/*
			 * Skip amount validation if id of bill detail is invalid
			 */
			if (payableLineItem.getPaidAmount().compareTo(lineItemFromSearch.getAmount()) > 0) {

				errorMap.put("EG_EXPENSE_PAYMENT_LINEITEM_INVALID_AMOUNT[" + lineItemIndex + "]",
						"The paid line item amount " + payableLineItem.getPaidAmount() + " for line item with id : "
								+ payableLineItem.getLineItemId() + "is greater than the actual amount : "
								+ lineItemFromSearch.getAmount());
			} else if (totalLineItemPayment.compareTo(lineItemFromSearch.getAmount()) > 0) {

				errorMap.put("EG_EXPENSE_PAYMENT_LINEITEM_INVALID_AMOUNT[" + lineItemIndex + "]",
						"The total payment for line item with id : " + payableLineItem.getLineItemId()
								+ " is greater than the actual amount : " + lineItemFromSearch.getAmount());
			}
		}
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
