package org.egov.digit.expense.util;

import digit.models.coremodels.AuditDetails;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

import static org.egov.digit.expense.config.Constants.*;

@Component
public class EnrichmentUtil {

    @Autowired
    private Configuration config;

    @Autowired
    private IdgenUtil idgenUtil;

    public BillRequest encrichBillForCreate(BillRequest billRequest) {

        Bill bill = billRequest.getBill();
        String createdBy = billRequest.getRequestInfo().getUserInfo().getUuid();
        AuditDetails audit = getAuditDetails(createdBy, billRequest.getBill().getAuditDetails(), true);

        bill.setId(UUID.randomUUID().toString());
        bill.setAuditDetails(audit);

        bill.getPayer().setId(UUID.randomUUID().toString());
        bill.getPayer().setAuditDetails(audit);
        bill.getPayer().setParentId(bill.getId());

        for (BillDetail billDetail : bill.getBillDetails()) {

            billDetail.setId(UUID.randomUUID().toString());
            billDetail.setBillId(bill.getId());
            billDetail.setAuditDetails(audit);

            billDetail.getPayee().setId(UUID.randomUUID().toString());
            billDetail.getPayee().setParentId(billDetail.getBillId());
            billDetail.getPayee().setAuditDetails(audit);

            for (LineItem lineItem : billDetail.getLineItems()) {
                lineItem.setId(UUID.randomUUID().toString());
                lineItem.setAuditDetails(audit);
                lineItem.setBillDetailId(billDetail.getId());
            }

            for (LineItem payablelineItem : billDetail.getPayableLineItems()) {
                payablelineItem.setId(UUID.randomUUID().toString());
                payablelineItem.setAuditDetails(audit);
                payablelineItem.setBillDetailId(billDetail.getId());

            }
        }

        //idGen to get the bill number
		enrichBillNumber(billRequest, bill);

		return billRequest;
    }

	private void enrichBillNumber(BillRequest billRequest, Bill bill) {
		if (StringUtils.isNotBlank(bill.getBusinessService())) {
			String tenantId = bill.getTenantId();
			String stateLevelTenantId = tenantId.split("\\.")[0];
			RequestInfo requestInfo = billRequest.getRequestInfo();
			if (BUSINESS_SERVICE_WAGE.equals(bill.getBusinessService())) {
				List<String> wageBillNumbers = idgenUtil.getIdList(requestInfo, stateLevelTenantId,
						config.getWageBillNumberName(), config.getWageBillNumberFormat(), 1);
				if (!CollectionUtils.isEmpty(wageBillNumbers)) {
					bill.setBillNumber(wageBillNumbers.get(0));
				}
			} else if (BUSINESS_SERVICE_PURCHASE.equals(bill.getBusinessService())) {
				List<String> purchaseBillNumbers = idgenUtil.getIdList(requestInfo, stateLevelTenantId,
						config.getPurchaseBillNumberName(), config.getPurchaseBillNumberFormat(), 1);
				if (!CollectionUtils.isEmpty(purchaseBillNumbers)) {
					bill.setBillNumber(purchaseBillNumbers.get(0));
				}
			} else if (BUSINESS_SERVICE_SUPERVISION.equals(bill.getBusinessService())) {
				List<String> supervisionBillNumbers = idgenUtil.getIdList(requestInfo, stateLevelTenantId,
						config.getSupervisionBillNumberName(), config.getSupervisionBillNumberFormat(), 1);
				if (!CollectionUtils.isEmpty(supervisionBillNumbers)) {
					bill.setBillNumber(supervisionBillNumbers.get(0));
				}
			}
		}
	}

	public BillRequest encrichBillWithUuidAndAuditForUpdate(BillRequest billRequest) {

        Bill bill = billRequest.getBill();
        String createdBy = billRequest.getRequestInfo().getUserInfo().getUuid();
        AuditDetails updateAudit = getAuditDetails(createdBy, billRequest.getBill().getAuditDetails(), false);
        AuditDetails createAudit = getAuditDetails(createdBy, billRequest.getBill().getAuditDetails(), true);

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
                    } else /* updating payable line item */
                        payablelineItem.setAuditDetails(updateAudit);
                }
            }
        }
        return billRequest;
    }


    public void enrichSearchBillRequest(BillCriteria billCriteria) {

        Pagination pagination = getPagination(billCriteria);

        if (pagination.getLimit() == null)
            pagination.setLimit(config.getDefaultLimit());

        if (pagination.getOffSet() == null)
            pagination.setOffSet(config.getDefaultOffset());

        if (pagination.getLimit() != null && pagination.getLimit().compareTo(config.getMaxSearchLimit()) > 0)
            pagination.setLimit(config.getMaxSearchLimit());
    }

    private Pagination getPagination(BillCriteria billCriteria) {
        Pagination pagination = billCriteria.getPagination();
        if (pagination == null) {
            pagination = Pagination.builder().build();
            billCriteria.setPagination(pagination);
        }
        return pagination;
    }

    public PaymentRequest encrichCreatePayment(PaymentRequest paymentRequest) {

        Payment payment = paymentRequest.getPayment();
        String createdBy = paymentRequest.getRequestInfo().getUserInfo().getUuid();
        payment.setId(UUID.randomUUID().toString());
        payment.setAuditDetails(getAuditDetails(createdBy, paymentRequest.getPayment().getAuditDetails(), true));
        return paymentRequest;
    }

    public PaymentRequest encrichUpdatePayment(PaymentRequest paymentRequest) {

        Payment payment = paymentRequest.getPayment();
        String createdBy = paymentRequest.getRequestInfo().getUserInfo().getUuid();
        payment.setAuditDetails(getAuditDetails(createdBy, paymentRequest.getPayment().getAuditDetails(), false));
        return paymentRequest;
    }


    /**
     * Method to return auditDetails for create/update flows
     *
     * @param by
     * @param isCreate
     * @return AuditDetails
     */
    public AuditDetails getAuditDetails(String by, AuditDetails auditDetails, Boolean isCreate) {

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
                    .createdBy(auditDetails.getCreatedBy())
                    .createdTime(auditDetails.getCreatedTime())
                    .lastModifiedBy(by)
                    .lastModifiedTime(time)
                    .build();
    }
}
