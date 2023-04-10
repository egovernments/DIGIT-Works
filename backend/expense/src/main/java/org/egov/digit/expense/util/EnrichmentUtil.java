package org.egov.digit.expense.util;

import java.util.UUID;

import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.digit.expense.web.models.LineItem;
import org.egov.digit.expense.web.models.Payment;
import org.egov.digit.expense.web.models.PaymentRequest;
import org.springframework.stereotype.Component;

import digit.models.coremodels.AuditDetails;

@Component
public class EnrichmentUtil {
	
	public BillRequest encrichBillWithUuidAndAudit(BillRequest billRequest) {

		Bill bill = billRequest.getBill();
		String createdBy = billRequest.getRequestInfo().getUserInfo().getUuid();
		AuditDetails audit = getAuditDetails(createdBy, true);

		bill.setId(UUID.randomUUID().toString());
		bill.setAuditDetails(audit);

		for (BillDetail billDetail : bill.getBillDetails()) {

			billDetail.setId(UUID.randomUUID().toString());
			billDetail.setAuditDetails(audit);

			for (LineItem lineItem : billDetail.getLineItems()) {
				lineItem.setId(UUID.randomUUID().toString());
				lineItem.setAuditDetails(audit);
			}

			for (LineItem payablelineItem : billDetail.getPayableLineItems()) {
				payablelineItem.setId(UUID.randomUUID().toString());
				payablelineItem.setAuditDetails(audit);
			}
		}
		return billRequest;
	}

	public BillRequest encrichBillWithUuidAndAuditForUpdate(BillRequest billRequest) {

		Bill bill = billRequest.getBill();
		String createdBy = billRequest.getRequestInfo().getUserInfo().getUuid();
		AuditDetails updateAudit = getAuditDetails(createdBy, false);
		AuditDetails createAudit = getAuditDetails(createdBy, true);

		bill.setAuditDetails(updateAudit);

		for (BillDetail billDetail : bill.getBillDetails()) {

			/*
			 * Enrich new bill detail
			 */
			if (null == billDetail.getId()) {

				billDetail.setId(UUID.randomUUID().toString());
				billDetail.setAuditDetails(createAudit);

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

				billDetail.setAuditDetails(updateAudit);

				for (LineItem lineItem : billDetail.getLineItems()) {

					if (null == lineItem.getId()) { /* new line item */ 

						lineItem.setId(UUID.randomUUID().toString());
						lineItem.setAuditDetails(createAudit);
					} else { /* updating line item */
						lineItem.setAuditDetails(updateAudit);
					}
				}

				for (LineItem payablelineItem : billDetail.getPayableLineItems()) {

					if (null == payablelineItem.getId()) { /* new payable line item */
						payablelineItem.setId(UUID.randomUUID().toString());
						payablelineItem.setAuditDetails(createAudit);
					}else /* updating payable line item */
					payablelineItem.setAuditDetails(updateAudit);
				}
			}
		}
		return billRequest;
	}
	
	public PaymentRequest encrichCreatePayment(PaymentRequest paymentRequest) {

		Payment payment = paymentRequest.getPayment();
		String createdBy = paymentRequest.getRequestInfo().getUserInfo().getUuid();
		payment.setId(UUID.randomUUID().toString());
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
        
        if(isCreate)
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
