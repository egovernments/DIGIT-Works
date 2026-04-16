package org.egov.digit.expense.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.models.project.Project;
import org.egov.common.models.project.ProjectResponse;
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
import org.egov.digit.expense.web.models.WorkerDetails;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import static org.egov.digit.expense.config.Constants.GENDER;

@Component
public class EnrichmentUtil {

    private final Configuration config;

    private final IdgenUtil idgenUtil;

    private final GenderUtil genderUtil;
    private final ObjectMapper objectMapper;
    private final WorkerRegistryUtil workerRegistryUtil;

    @Autowired
    public EnrichmentUtil(Configuration config, IdgenUtil idgenUtil, GenderUtil genderUtil,
                          ObjectMapper objectMapper, WorkerRegistryUtil workerRegistryUtil) {
        this.config = config;
        this.idgenUtil = idgenUtil;
        this.genderUtil = genderUtil;
        this.objectMapper = objectMapper;
        this.workerRegistryUtil = workerRegistryUtil;
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

        // ── Worker Registry enrichment (health context only) ──
        // Batch-fetch worker payment details for all individual payees before the loop.
        Map<String, WorkerDetails> workersByIndividualId = Collections.emptyMap();
        if (config.isHealthContextEnabled()) {
            List<String> individualIds = bill.getBillDetails().stream()
                    .filter(bd -> bd.getPayee() != null && bd.getPayee().getIdentifier() != null)
                    .map(bd -> bd.getPayee().getIdentifier())
                    .distinct()
                    .collect(Collectors.toList());
            workersByIndividualId = workerRegistryUtil.fetchWorkersByIndividualIds(
                    billRequest.getRequestInfo(), bill.getTenantId(), individualIds);
        }

        for (BillDetail billDetail : bill.getBillDetails()) {

            billDetail.setId(UUID.randomUUID().toString());
            billDetail.setBillId(bill.getId());
            billDetail.setAuditDetails(audit);
            billDetail.setStatus(Status.ACTIVE);

            billDetail.getPayee().setId(UUID.randomUUID().toString());
            billDetail.getPayee().setParentId(billDetail.getBillId());
            billDetail.getPayee().setAuditDetails(audit);
            billDetail.getPayee().setStatus(Status.ACTIVE);

            // ── Enrich payment fields from Worker Registry ──
            if (config.isHealthContextEnabled() && billDetail.getPayee() != null) {
                String individualId = billDetail.getPayee().getIdentifier();
                WorkerDetails worker = workersByIndividualId.get(individualId);
                if (worker != null) {
                    billDetail.setWorkerId(worker.getWorkerId());
                    billDetail.getPayee().setPaymentProvider(worker.getPaymentProvider());
                    billDetail.getPayee().setPayeeName(worker.getPayeeName());
                    billDetail.getPayee().setPayeePhoneNumber(worker.getPayeePhoneNumber());
                    billDetail.getPayee().setBankAccount(worker.getBankAccount());
                    billDetail.getPayee().setBankCode(worker.getBankCode());
                    billDetail.getPayee().setBeneficiaryCode(worker.getBeneficiaryCode());
                }
            }

            if (!config.isHealthContextEnabled()) {
                String gender = genderUtil.getGenderDetails(billRequest.getRequestInfo(), billDetail.getPayee().getTenantId(), billDetail.getPayee().getIdentifier());
                Map<String, Object> map = objectMapper.convertValue(billDetail.getPayee().getAdditionalDetails(), new TypeReference<Map<String, Object>>() {
                });
                if (map == null) {
                    map = new HashMap<>();
                }
                map.put(GENDER, gender);
                billDetail.getPayee().setAdditionalDetails(objectMapper.convertValue(map, Object.class));
            }
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
        if (payer == null) {
            payer = billFromSearch.getPayer();
            bill.setPayer(payer);
        } else if (null == payer.getId()) {
            payer.setId(billFromSearch.getPayer().getId());
        }
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

        // ── Preserve payment fields from DB if not supplied in update request (partial update) ──
        if (detailFromSearch != null) {
            if (billDetail.getWorkerId() == null)
                billDetail.setWorkerId(detailFromSearch.getWorkerId());
            if (billDetail.getPayee() != null && detailFromSearch.getPayee() != null) {
                Party payeeFromSearch = detailFromSearch.getPayee();
                Party payee = billDetail.getPayee();
                if (payee.getPaymentProvider() == null)
                    payee.setPaymentProvider(payeeFromSearch.getPaymentProvider());
                if (payee.getPayeeName() == null)
                    payee.setPayeeName(payeeFromSearch.getPayeeName());
                if (payee.getPayeePhoneNumber() == null)
                    payee.setPayeePhoneNumber(payeeFromSearch.getPayeePhoneNumber());
                if (payee.getBankAccount() == null)
                    payee.setBankAccount(payeeFromSearch.getBankAccount());
                if (payee.getBankCode() == null)
                    payee.setBankCode(payeeFromSearch.getBankCode());
                if (payee.getBeneficiaryCode() == null)
                    payee.setBeneficiaryCode(payeeFromSearch.getBeneficiaryCode());
            }
        }

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

        if (pagination.getSortBy() == null)
            pagination.setSortBy("billdate");

        if (pagination.getOrder() == null)
            pagination.setOrder(Pagination.OrderEnum.ASC);
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

    /**
     * Merges each {@link org.egov.digit.expense.web.models.PartialBillDetail} in the request
     * with its corresponding DB snapshot, applying full null protection.
     * Immutable fields (id, tenantId, billId, referenceId) are always taken from DB.
     *
     * @return list of fully populated {@link BillDetail} objects ready for persistence
     */
    public List<BillDetail> enrichPartialBillDetails(
            org.egov.digit.expense.web.models.BillDetailUpdateRequest request,
            Bill billFromSearch,
            String updatedBy) {

        Map<String, BillDetail> dbDetailMap = billFromSearch.getBillDetails().stream()
                .collect(Collectors.toMap(BillDetail::getId, Function.identity()));

        List<BillDetail> mergedDetails = new ArrayList<>();
        long now = System.currentTimeMillis();

        for (org.egov.digit.expense.web.models.PartialBillDetail pd : request.getBillDetails()) {
            BillDetail db = dbDetailMap.get(pd.getId());

            AuditDetails updateAudit = AuditDetails.builder()
                    .createdBy(db.getAuditDetails().getCreatedBy())
                    .createdTime(db.getAuditDetails().getCreatedTime())
                    .lastModifiedBy(updatedBy)
                    .lastModifiedTime(now)
                    .build();

            BillDetail merged = BillDetail.builder()
                    // Immutable fields — always from DB
                    .id(db.getId())
                    .tenantId(db.getTenantId())
                    .billId(db.getBillId())
                    .referenceId(db.getReferenceId())
                    // Mutable fields — request value if non-null, else DB value
                    .totalAmount(pd.getTotalAmount()         != null ? pd.getTotalAmount()         : db.getTotalAmount())
                    .totalPaidAmount(pd.getTotalPaidAmount() != null ? pd.getTotalPaidAmount()     : db.getTotalPaidAmount())
                    .paymentStatus(pd.getPaymentStatus()     != null ? pd.getPaymentStatus()       : db.getPaymentStatus())
                    .status(pd.getStatus()                   != null ? pd.getStatus()              : db.getStatus())
                    .fromPeriod(pd.getFromPeriod()           != null ? pd.getFromPeriod()          : db.getFromPeriod())
                    .toPeriod(pd.getToPeriod()               != null ? pd.getToPeriod()            : db.getToPeriod())
                    .workerId(pd.getWorkerId()               != null ? pd.getWorkerId()            : db.getWorkerId())
                    .totalAttendance(pd.getTotalAttendance()  != null ? pd.getTotalAttendance()     : db.getTotalAttendance())
                    .additionalDetails(pd.getAdditionalDetails() != null ? pd.getAdditionalDetails() : db.getAdditionalDetails())
                    .payee(mergePayee(pd.getPayee(), db.getPayee(), updatedBy, now))
                    .lineItems(mergeLineItems(pd.getLineItems(), db.getLineItems(), updatedBy, now))
                    .payableLineItems(mergeLineItems(pd.getPayableLineItems(), db.getPayableLineItems(), updatedBy, now))
                    .auditDetails(updateAudit)
                    .build();

            mergedDetails.add(merged);
        }
        return mergedDetails;
    }

    /**
     * Merges the request payee with the DB payee field-by-field.
     * null request payee → DB payee used wholesale.
     * paymentProvider from PartialBillDetail is passed separately as it's used as a selector field.
     */
    private Party mergePayee(Party pdPayee, Party dbPayee, String updatedBy, long now) {

        AuditDetails payeeAudit = AuditDetails.builder()
                .createdBy(dbPayee.getAuditDetails().getCreatedBy())
                .createdTime(dbPayee.getAuditDetails().getCreatedTime())
                .lastModifiedBy(updatedBy)
                .lastModifiedTime(now)
                .build();

        return Party.builder()
                .id(dbPayee.getId())          // always DB id
                .parentId(dbPayee.getParentId()) // always DB parentId — linked to billDetail row
                .tenantId(pdPayee != null && pdPayee.getTenantId() != null ? pdPayee.getTenantId() : dbPayee.getTenantId())
                .type(pdPayee != null && pdPayee.getType() != null ? pdPayee.getType() : dbPayee.getType())
                .identifier(pdPayee != null && pdPayee.getIdentifier() != null ? pdPayee.getIdentifier() : dbPayee.getIdentifier())
                .status(pdPayee != null && pdPayee.getStatus() != null ? pdPayee.getStatus() : dbPayee.getStatus())
                .paymentProvider(pdPayee.getPaymentProvider() != null ? pdPayee.getPaymentProvider() : dbPayee.getPaymentProvider())
                .payeeName(pdPayee != null && pdPayee.getPayeeName() != null ? pdPayee.getPayeeName() : dbPayee.getPayeeName())
                .payeePhoneNumber(pdPayee != null && pdPayee.getPayeePhoneNumber() != null ? pdPayee.getPayeePhoneNumber() : dbPayee.getPayeePhoneNumber())
                .bankAccount(pdPayee != null && pdPayee.getBankAccount() != null ? pdPayee.getBankAccount() : dbPayee.getBankAccount())
                .bankCode(pdPayee != null && pdPayee.getBankCode() != null ? pdPayee.getBankCode() : dbPayee.getBankCode())
                .beneficiaryCode(pdPayee != null && pdPayee.getBeneficiaryCode() != null ? pdPayee.getBeneficiaryCode() : dbPayee.getBeneficiaryCode())
                .additionalDetails(pdPayee != null && pdPayee.getAdditionalDetails() != null
                        ? pdPayee.getAdditionalDetails() : dbPayee.getAdditionalDetails())
                .auditDetails(payeeAudit)
                .build();
    }

    /**
     * Merges request line items with DB line items.
     * null list → preserve all DB items.
     * empty list → no writes (persister skips empty arrays, DB rows untouched).
     * non-empty list → field-by-field merge per item ID; null ID = new item.
     */
    private List<LineItem> mergeLineItems(List<LineItem> pdItems, List<LineItem> dbItems,
                                          String updatedBy, long now) {
        if (pdItems == null) return dbItems;
        if (pdItems.isEmpty()) return pdItems;

        Map<String, LineItem> dbMap = dbItems.stream()
                .filter(li -> li.getId() != null)
                .collect(Collectors.toMap(LineItem::getId, Function.identity()));

        List<LineItem> merged = new ArrayList<>();
        for (LineItem li : pdItems) {
            if (li.getId() == null) {
                // New line item
                li.setId(UUID.randomUUID().toString());
                li.setAuditDetails(getAuditDetails(updatedBy, true));
            } else {
                LineItem db = dbMap.get(li.getId());
                if (db != null) {
                    if (li.getHeadCode()          == null) li.setHeadCode(db.getHeadCode());
                    if (li.getAmount()            == null) li.setAmount(db.getAmount());
                    if (li.getPaidAmount()        == null) li.setPaidAmount(db.getPaidAmount());
                    if (li.getType()              == null) li.setType(db.getType());
                    if (li.getStatus()            == null) li.setStatus(db.getStatus());
                    if (li.getPaymentStatus()     == null) li.setPaymentStatus(db.getPaymentStatus());
                    if (li.getTenantId()          == null) li.setTenantId(db.getTenantId());
                    if (li.getBillDetailId()      == null) li.setBillDetailId(db.getBillDetailId());
                    if (li.getAdditionalDetails() == null) li.setAdditionalDetails(db.getAdditionalDetails());
                    li.setAuditDetails(AuditDetails.builder()
                            .createdBy(db.getAuditDetails().getCreatedBy())
                            .createdTime(db.getAuditDetails().getCreatedTime())
                            .lastModifiedBy(updatedBy)
                            .lastModifiedTime(now)
                            .build());
                }
            }
            merged.add(li);
        }
        return merged;
    }
}
