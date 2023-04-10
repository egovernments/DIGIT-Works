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
		Set<String> billIds = payment.getBills().stream().map(Bill::getId).collect(Collectors.toSet());
		
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
		
		for (Bill bill : payment.getBills()) {
			
			Bill billFromSearch = billMap.get(bill.getId());
			if (bill.getNetPaidAmount().compareTo(billFromSearch.getNetPayableAmount()) != 0) {
				errorMap.put("EG_PAYMENT_INVALID_LINEITEM_AMOUNT",
						"The paid bill amount " + bill.getNetPaidAmount()
					  + " is not equal to the actual amount : " + billFromSearch.getNetPayableAmount());
			}
			
			for (BillDetail billDetail : bill.getBillDetails()) {
				
				BillDetail billDetailFromSearch = billDetailMap.get(billDetail.getId());
				
				if (null == billDetailFromSearch) {
					errorMap.put("EG_PAYMENT_INVALID_BILLDETAIL",
							"The bill detail id is invalid : " + billDetail.getId());
				break;
				}
				
				for (LineItem payableLineItem : billDetail.getPayableLineItems()) {
					
					LineItem lineItemFromSearch = payableLineItemMap.get(payableLineItem.getId());
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
				.offSet(0l)
				.limit(Long.valueOf(billIds.size()))
				.build();
		
		BillSearchRequest billSearchRequest = BillSearchRequest.builder()
				.billcriteria(billCriteria)
				.pagination(pagination)
				.requestInfo(paymentRequest.getRequestInfo())
				.build();
		return billSearchRequest;
	}

}
