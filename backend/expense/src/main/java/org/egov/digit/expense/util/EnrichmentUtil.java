package org.egov.digit.expense.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import digit.models.coremodels.AuditDetails;

@Component
public class EnrichmentUtil {

    @Autowired
    private Configuration config;

    @Autowired
    private IdgenUtil idgenUtil;

    public BillRequest encrichBillForCreate(BillRequest billRequest) {

        Bill bill = billRequest.getBill();
        String createdBy = billRequest.getRequestInfo().getUserInfo().getUuid();
		AuditDetails audit = getAuditDetails(createdBy, true);
		String billNumberIdFormatName = bill.getBusinessService().toLowerCase().concat(Constants.BILL_ID_FORMAT_SUFFIX);
		String billNumber = idgenUtil
				.getIdList(billRequest.getRequestInfo(), bill.getTenantId().split("\\.")[0], billNumberIdFormatName, null, 1).get(0);

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
		return billRequest;
    }

	public BillRequest encrichBillWithUuidAndAuditForUpdate(BillRequest billRequest, List<Bill> billsFromSearch) {

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

            /*
             * Enrich new bill detail
             */
            if (null == billDetail.getId()) {

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
            /*
             * Enrich update of bill detail
             */
            else {

            	BillDetail detailFromSearch = billDetailMap.get(billDetail.getId());
            	
                billDetail.setAuditDetails(createAudit);
                billDetail.getPayee().setId(detailFromSearch.getPayee().getId()); 
                billDetail.getPayee().setAuditDetails(createAudit);

                for (LineItem lineItem : billDetail.getLineItems()) {

                    if (null == lineItem.getId()) { /* new line item */

                        lineItem.setId(UUID.randomUUID().toString());
                        lineItem.setAuditDetails(createAudit);
                    } else { /* updating line item */
                        lineItem.setAuditDetails(createAudit);
                    }
                }

                for (LineItem payablelineItem : billDetail.getPayableLineItems()) {

                    if (null == payablelineItem.getId()) { /* new payable line item */
                        payablelineItem.setId(UUID.randomUUID().toString());
                        payablelineItem.setAuditDetails(createAudit);
                    } else /* updating payable line item */
                        payablelineItem.setAuditDetails(createAudit);
                }
            }
        }
        return billRequest;
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
        payment.setStatus(defaultStatus);
        
		String paymentNumber = idgenUtil.getIdList(paymentRequest.getRequestInfo(),
				payment.getTenantId().split("\\.")[0],
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

    public PaymentRequest encrichUpdatePayment(PaymentRequest paymentRequest) {

        Payment payment = paymentRequest.getPayment();
        String createdBy = paymentRequest.getRequestInfo().getUserInfo().getUuid();
        payment.setAuditDetails(getAuditDetails(createdBy, false));
        
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

        if (isCreate)
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
