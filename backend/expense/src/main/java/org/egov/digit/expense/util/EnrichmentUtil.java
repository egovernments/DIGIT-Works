package org.egov.digit.expense.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.config.Constants;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.models.LineItem;
import org.egov.digit.expense.web.models.Pagination;
import org.egov.digit.expense.web.models.Party;
import org.egov.digit.expense.web.models.Payment;
import org.egov.digit.expense.web.models.PaymentBill;
import org.egov.digit.expense.web.models.PaymentBillDetail;
import org.egov.digit.expense.web.models.PaymentLineItem;
import org.egov.digit.expense.web.models.PaymentRequest;
import org.egov.digit.expense.web.models.enums.PaymentStatus;
import org.egov.digit.expense.web.models.enums.ReferenceStatus;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import static org.egov.digit.expense.config.Constants.GENDER;

@Component
public class EnrichmentUtil {

    private final Configuration config;

    private final IdgenUtil idgenUtil;

    private final GenderUtil genderUtil;

    @Autowired
    public EnrichmentUtil(Configuration config, IdgenUtil idgenUtil, GenderUtil genderUtil) {
        this.config = config;
        this.idgenUtil = idgenUtil;
        this.genderUtil = genderUtil;
    }

    public void encrichBillForCreate(BillRequest billRequest) {

        Bill bill = billRequest.getBill();
        String createdBy = billRequest.getRequestInfo().getUserInfo().getUuid();
		AuditDetails audit = getAuditDetails(createdBy, true);
		String billNumberIdFormatName = bill.getBusinessService().toLowerCase().concat(Constants.BILL_ID_FORMAT_SUFFIX);
		String billNumber = idgenUtil
				.getIdList(billRequest.getRequestInfo(), bill.getTenantId(), billNumberIdFormatName, null, 1).get(0);

	    bill.setId(UUID.randomUUID().toString());
        bill.setAuditDetails(audit);
        bill.setBillNumber(billNumber);

        bill.getPayer().setId(UUID.randomUUID().toString());
        bill.getPayer().setAuditDetails(audit);
        bill.getPayer().setParentId(bill.getId());
        bill.getPayer().setStatus(Status.ACTIVE);
        
        for (BillDetail billDetail : bill.getBillDetails()) {

            billDetail.setId(UUID.randomUUID().toString());
            billDetail.setBillId(bill.getId());
            billDetail.setAuditDetails(audit);
            billDetail.setStatus(Status.ACTIVE);
            
            billDetail.getPayee().setId(UUID.randomUUID().toString());
            billDetail.getPayee().setParentId(billDetail.getBillId());
            billDetail.getPayee().setAuditDetails(audit);
            billDetail.getPayee().setStatus(Status.ACTIVE);

            String gender = genderUtil.getGenderDetails(billRequest.getRequestInfo(),billDetail.getPayee().getTenantId(),billDetail.getPayee().getIdentifier());
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.convertValue(billDetail.getPayee().getAdditionalDetails(), new TypeReference<Map<String, Object>>() {});
            if(map == null){
                map = new HashMap<>();
            }
            if(!gender.isEmpty()){
                map.put(GENDER,gender);
            }
            map.put(GENDER,gender);
            billDetail.getPayee().setAdditionalDetails(objectMapper.convertValue(map,Object.class));
            for (LineItem lineItem : billDetail.getLineItems()) {
                lineItem.setId(UUID.randomUUID().toString());
                lineItem.setAuditDetails(audit);
                lineItem.setBillDetailId(billDetail.getId());
                lineItem.setStatus(Status.ACTIVE);
            }

            for (LineItem payablelineItem : billDetail.getPayableLineItems()) {
                payablelineItem.setId(UUID.randomUUID().toString());
                payablelineItem.setAuditDetails(audit);
                payablelineItem.setBillDetailId(billDetail.getId());
                payablelineItem.setStatus(Status.ACTIVE);

            }
        }
    }
    public void encrichBillWithUuidAndAuditForUpdate(BillRequest billRequest, List<Bill> billsFromSearch) {

        Bill bill = billRequest.getBill();
        String createdBy = billRequest.getRequestInfo().getUserInfo().getUuid();
        AuditDetails updateAudit = getAuditDetails(createdBy, false);
        AuditDetails createAudit = getAuditDetails(createdBy, true);

        Bill billFromSearch = billsFromSearch.get(0);

        // Add createdBy and createdTime to updateAudit
        updateAudit.setCreatedBy(billFromSearch.getAuditDetails().getCreatedBy());
        updateAudit.setCreatedTime(billFromSearch.getAuditDetails().getCreatedTime());

        bill.setAuditDetails(updateAudit);

        Party payer = bill.getPayer();
        if (payer.getId() == null)
            payer.setId(billFromSearch.getPayer().getId());
        payer.setAuditDetails(updateAudit);

        Map<String, BillDetail> billDetailMap = billsFromSearch.stream()
                .map(Bill::getBillDetails)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(BillDetail::getId, Function.identity()));

        for (BillDetail billDetail : bill.getBillDetails()) {
            enrichBillDetail(billDetail, billDetailMap, createAudit);
        }
    }

    private void enrichBillDetail(BillDetail billDetail, Map<String, BillDetail> billDetailMap, AuditDetails createAudit) {
        if (null == billDetail.getId()) {
            enrichNewBillDetail(billDetail, createAudit);
        } else {
            enrichExistingBillDetail(billDetail, billDetailMap, createAudit);
        }
    }

    private void enrichNewBillDetail(BillDetail billDetail, AuditDetails createAudit) {
        billDetail.setId(UUID.randomUUID().toString());
        billDetail.setAuditDetails(createAudit);

        Party payee = billDetail.getPayee();
        payee.setId(UUID.randomUUID().toString());
        payee.setAuditDetails(createAudit);

        for (LineItem lineItem : billDetail.getLineItems()) {
            lineItem.setId(UUID.randomUUID().toString());
            lineItem.setAuditDetails(createAudit);
        }

        for (LineItem payablelineItem : billDetail.getPayableLineItems()) {
            payablelineItem.setId(UUID.randomUUID().toString());
            payablelineItem.setAuditDetails(createAudit);
        }
    }

    private void enrichExistingBillDetail(BillDetail billDetail, Map<String, BillDetail> billDetailMap, AuditDetails createAudit) {
        BillDetail detailFromSearch = billDetailMap.get(billDetail.getId());

        billDetail.setAuditDetails(createAudit);
        billDetail.getPayee().setId(detailFromSearch.getPayee().getId());
        billDetail.getPayee().setAuditDetails(createAudit);

        for (LineItem lineItem : billDetail.getLineItems()) {
            enrichLineItem(lineItem, createAudit);
        }

        for (LineItem payablelineItem : billDetail.getPayableLineItems()) {
            enrichLineItem(payablelineItem, createAudit);
        }
    }

    private void enrichLineItem(LineItem lineItem, AuditDetails createAudit) {
        if (null == lineItem.getId()) { /* new line item */
            lineItem.setId(UUID.randomUUID().toString());
            lineItem.setAuditDetails(createAudit);
        } else { /* updating line item */
            lineItem.setAuditDetails(createAudit);
        }
    }

    public void enrichSearchBillRequest(BillSearchRequest billSearchRequest) {

        Pagination pagination = getPagination(billSearchRequest);

        if (pagination.getLimit() == null)
            pagination.setLimit(config.getDefaultLimit());

        if (pagination.getOffSet() == null)
            pagination.setOffSet(config.getDefaultOffset());

        if (pagination.getLimit() != null && pagination.getLimit().compareTo(config.getMaxSearchLimit()) > 0)
            pagination.setLimit(config.getMaxSearchLimit());
    }

    private Pagination getPagination(BillSearchRequest billSearchRequest) {
        Pagination pagination = billSearchRequest.getPagination();
        if (pagination == null) {
            pagination = Pagination.builder().build();
            billSearchRequest.setPagination(pagination);
        }
        return pagination;
    }

    public PaymentRequest encrichCreatePayment(PaymentRequest paymentRequest) {

        Payment payment = paymentRequest.getPayment();
        String createdBy = paymentRequest.getRequestInfo().getUserInfo().getUuid();
        payment.setId(UUID.randomUUID().toString());
        /*
         * TODO needs to be removed when jit integration is implemented
         */
        PaymentStatus defaultStatus = PaymentStatus.fromValue(config.getDefaultPaymentStatus());
        ReferenceStatus defaultReferenceStatus = ReferenceStatus.fromValue(config.getDefaultReferenceStatus());
        payment.setStatus(defaultStatus);
        payment.setReferenceStatus(defaultReferenceStatus);
        
		String paymentNumber = idgenUtil.getIdList(paymentRequest.getRequestInfo(),
				payment.getTenantId(),
				Constants.PAYMENT_ID_FORMAT_NAME,
				null, // id-format is not needed, setting to null
				1).get(0);
		payment.setPaymentNumber(paymentNumber);		
        
		for (PaymentBill paymentBill : payment.getBills()) {

			paymentBill.setId(UUID.randomUUID().toString());
			paymentBill.setStatus(defaultStatus);
			
			for (PaymentBillDetail billDetail : paymentBill.getBillDetails()) {

				billDetail.setId(UUID.randomUUID().toString());
				billDetail.setStatus(defaultStatus);
				
				for (PaymentLineItem lineItem : billDetail.getPayableLineItems()) {
					
					lineItem.setId(UUID.randomUUID().toString());
					lineItem.setStatus(defaultStatus);				
				}
			}
		}
        payment.setAuditDetails(getAuditDetails(createdBy, true));
        return paymentRequest;
    }

    public PaymentRequest encrichUpdatePayment(PaymentRequest paymentRequest, Payment searchPayment) {

        Payment payment = paymentRequest.getPayment();
        String createdBy = paymentRequest.getRequestInfo().getUserInfo().getUuid();
        Long time = System.currentTimeMillis();

        // Update each payment status based on request status
        Map<String, PaymentBill> billMap = payment.getBills().stream()
                .collect(Collectors.toMap(PaymentBill::getId, Function.identity()));

        Map<String, PaymentBillDetail> billDetailMap = payment.getBills().stream()
                .map(PaymentBill::getBillDetails)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(PaymentBillDetail::getId, Function.identity()));

        Map<String, PaymentLineItem> payableLineItemMap = payment.getBills().stream()
                .map(PaymentBill::getBillDetails)
                .flatMap(Collection::stream)
                .map(PaymentBillDetail::getPayableLineItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(PaymentLineItem::getId, Function.identity()));

        searchPayment.setStatus(payment.getStatus());
        searchPayment.getAuditDetails().setLastModifiedBy(createdBy);
        searchPayment.getAuditDetails().setLastModifiedTime(time);

        for (PaymentBill bill: searchPayment.getBills()) {
            if (billMap.containsKey(bill.getId())) {
                bill.setStatus(billMap.get(bill.getId()).getStatus());
                bill.getAuditDetails().setLastModifiedBy(createdBy);
                bill.getAuditDetails().setLastModifiedTime(time);
            }
            for (PaymentBillDetail billDetail: bill.getBillDetails()) {
                if (billDetailMap.containsKey(billDetail.getId())) {
                    billDetail.setStatus(billDetailMap.get(billDetail.getId()).getStatus());
                    billDetail.getAuditDetails().setLastModifiedBy(createdBy);
                    billDetail.getAuditDetails().setLastModifiedTime(time);
                }
                for (PaymentLineItem payableLineItem: billDetail.getPayableLineItems()) {
                    if (payableLineItemMap.containsKey(payableLineItem.getId())) {
                        payableLineItem.setStatus(payableLineItemMap.get(payableLineItem.getId()).getStatus());
                        payableLineItem.getAuditDetails().setLastModifiedBy(createdBy);
                        payableLineItem.getAuditDetails().setLastModifiedTime(time);
                    }
                }
            }
        }

        return paymentRequest;
    }


    /**
     * Method to return auditDetails for create/update flows
     *
     * @param by
     * @param isCreate
     * @return AuditDetails
     */
    public AuditDetails getAuditDetails(String by, Boolean isCreate) {

        Long time = System.currentTimeMillis();

        if (Boolean.TRUE.equals(isCreate))
            return AuditDetails.builder()
                    .createdBy(by)
                    .createdTime(time)
                    .lastModifiedBy(by)
                    .lastModifiedTime(time)
                    .build();
        else
            return AuditDetails.builder()
                    .lastModifiedBy(by)
                    .lastModifiedTime(time)
                    .build();
    }
}
