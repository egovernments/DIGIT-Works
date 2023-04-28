package org.egov.digit.expense.web.validators;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.egov.digit.expense.service.BillService;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillCriteria;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.models.LineItem;
import org.egov.digit.expense.web.models.Pagination;
import org.egov.digit.expense.web.models.Payment;
import org.egov.digit.expense.web.models.PaymentBill;
import org.egov.digit.expense.web.models.PaymentBillDetail;
import org.egov.digit.expense.web.models.PaymentLineItem;
import org.egov.digit.expense.web.models.PaymentRequest;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class PaymentValidator {

	@Autowired
	private BillService billService;
	
	public void validateUpdateRequest(PaymentRequest paymentRequest) {
		
		validateCreateRequest(paymentRequest);
	}
	
	public void validateCreateRequest(PaymentRequest paymentRequest) {

		Payment payment = paymentRequest.getPayment();
		Map<String, String> errorMap = new HashMap<>();
		Set<String> billIds = payment.getBills().stream().map(PaymentBill::getBillId).collect(Collectors.toSet());
		
		if(payment.getBills().size() != billIds.size())
			errorMap.put("EG_PAYMENT_DUPLICATE_BILLS_ERROR", "The same bills cannot be repeated for payments");
			
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
	
		if(payment.getBills().size() != billMap.size())
			errorMap.put("EG_PAYMENT_INVALID_BILLS_ERROR", "Invalid bill ids found in the payment request");
			
		if(!CollectionUtils.isEmpty(errorMap))
			throw new CustomException(errorMap);
		
		for (PaymentBill paymentBill : payment.getBills()) {
			
			Bill billFromSearch = billMap.get(paymentBill.getBillId());
			if (paymentBill.getPaidAmount().compareTo(billFromSearch.getTotalAmount()) != 0) {
				errorMap.put("EG_PAYMENT_INVALID_LINEITEM_AMOUNT",
						"The paid bill amount " + paymentBill.getPaidAmount()
					  + " is not equal to the actual amount : " + billFromSearch.getTotalAmount());

			}
			
			for (PaymentBillDetail billDetail : paymentBill.getBillDetails()) {
				
				BillDetail billDetailFromSearch = billDetailMap.get(billDetail.getBillDetailId());
				
				if (null == billDetailFromSearch) {
					errorMap.put("EG_PAYMENT_INVALID_BILLDETAIL",
							"The bill detail id is invalid : " + billDetail.getId());
				break;
				}
				
				for (PaymentLineItem payableLineItem : billDetail.getPayableLineItems()) {
					
					LineItem lineItemFromSearch = payableLineItemMap.get(payableLineItem.getLineItemid());
					if (null == lineItemFromSearch) {
						errorMap.put("EG_PAYMENT_INVALID_LINEITEM",
								"The payable line item id is invalid : " + payableLineItem.getId());
					}
					
					if (payableLineItem.getPaidAmount().compareTo(lineItemFromSearch.getAmount()) != 0) {
						errorMap.put("EG_PAYMENT_INVALID_LINEITEM_AMOUNT",
								"The paid line item amount " + payableLineItem.getPaidAmount()
							  + " is not equal to the actual amount : " + lineItemFromSearch.getAmount());
					}
				}
			}
		}
		
		if(!CollectionUtils.isEmpty(errorMap))
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

}
