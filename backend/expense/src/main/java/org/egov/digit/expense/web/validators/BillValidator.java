package org.egov.digit.expense.web.validators;

import static org.egov.digit.expense.config.Constants.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.Role;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.config.Constants;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.util.MdmsUtil;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillCriteria;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.models.BulkBillStatusUpdateRequest;
import org.egov.digit.expense.web.models.BulkUpdateError;
import org.egov.digit.expense.web.models.LineItem;
import org.egov.digit.expense.web.models.Party;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.jayway.jsonpath.JsonPath;

@Service
public class BillValidator {

	public static final String BILL_DETAIL_ID_IS_INVALID_FOR_THE_GIVEN_IDS_OF_UPDATE_REQUEST = "bill detail id is Invalid for the given ids of update request : ";
	private final MdmsUtil mdmsUtil;

	private final Configuration configs;

	private final BillRepository billRepository;

	@Autowired
	public BillValidator(MdmsUtil mdmsUtil, Configuration configs, BillRepository billRepository) {
		this.mdmsUtil = mdmsUtil;
		this.configs = configs;
		this.billRepository = billRepository;
	}

	public void validateCreateRequest(BillRequest billRequest) {

		Map<String, String> errorMap = new HashMap<>();

		Bill bill = billRequest.getBill();

		List<Bill> billsFromSearch = getBillsForValidation(billRequest, true);
		if(!CollectionUtils.isEmpty(billsFromSearch)) {
			// V2 Periodic Billing: Allow multiple bills per register (one per billing period)
			// Both flags must be enabled: either health context OR v2 periodic billing + bill must be marked as V2
			boolean allowMultipleBillsForReference = (configs.isHealthContextEnabled() || configs.isV2PeriodicBillingEnabled()) && isV2Bill(bill);
			List<Bill> conflictingBills = billsFromSearch;

			if (allowMultipleBillsForReference) {
				conflictingBills = billsFromSearch.stream()
						.filter(existingBill -> hasV2Conflict(existingBill, bill))
						.collect(Collectors.toList());
			}

			if(!CollectionUtils.isEmpty(conflictingBills)) {
				throw new CustomException(ERR_DUPLICATE_BILL, buildDuplicateBillErrorMessage(bill));
			}
		}

		validateWorkflow(billRequest, errorMap);

		Map<String, Map<String, JSONArray>> mdmsData = getMasterDataForValidation(billRequest, bill);
		validateBillAmountAndDate(bill, errorMap);
		validateTenantId(billRequest,mdmsData);
		validateMasterData(billRequest, errorMap, mdmsData, true);

		if (!CollectionUtils.isEmpty(errorMap))
			throw new CustomException(errorMap);

	}

	public List<Bill> validateUpdateRequest(BillRequest billRequest) {

		Map<String, String> errorMap = new HashMap<>();
		Bill bill = billRequest.getBill();

		validateWorkflow(billRequest, errorMap);

		List<Bill> billsFromSearch = getBillsForValidation(billRequest, false);
		if(CollectionUtils.isEmpty(billsFromSearch))
			throw new CustomException(ERR_INVALID_BILL,"The bill does not exists for the given combination of "
					+ " id : " + bill.getId() + " and refernceId : " + bill.getTenantId());

		if (!configs.isHealthContextEnabled())
			validateFieldsForUpdate(bill, billsFromSearch.get(0), errorMap);

		validatePaymentFieldUpdate(bill, billsFromSearch.get(0));

		Map<String, Map<String, JSONArray>> mdmsData = getMasterDataForValidation(billRequest, bill);
		validateTenantId(billRequest,mdmsData);

		if (!configs.isHealthContextEnabled())
			validateMasterData(billRequest, errorMap, mdmsData, false);

		if (!CollectionUtils.isEmpty(errorMap))
			throw new CustomException(errorMap);

		return billsFromSearch;
	}

	private void validateFieldsForUpdate(Bill bill, Bill billFromSearch, Map<String, String> errorMap) {

		List<String> invalidDetailIds = new ArrayList<>();
		List<String> invalidLineItemIds = new ArrayList<>();
		List<String> invalidPayableLineItemIds = new ArrayList<>();

		if(null == bill.getStatus()){
			bill.setStatus(billFromSearch.getStatus());
		}

		Party payer = bill.getPayer();
		Party payerFromSearch = billFromSearch.getPayer();

		if(null == payer) {
			bill.setPayer(payerFromSearch);
		}else if(null == payer.getId()){
			errorMap.put(ERR_BILL_UPDATE_NOTNULL_PAYER_ID, MSG_BILL_UPDATE_NOTNULL_PAYER_ID);
		}

		List<BillDetail> details = bill.getBillDetails();
		List<BillDetail> detailsFromSearch = billFromSearch.getBillDetails();
		Map<String, BillDetail> searchDetailsMap = detailsFromSearch.stream()
				.collect(Collectors.toMap(BillDetail::getId, Function.identity()));

		Map<String, LineItem> searchLineItemMap = detailsFromSearch.stream()
				.map(BillDetail::getLineItems)
				.flatMap(Collection::stream)
				.collect(Collectors.toMap(LineItem::getId, Function.identity()));

		Map<String, LineItem> searchPayableLineItemMap = detailsFromSearch.stream()
				.map(BillDetail::getPayableLineItems)
				.flatMap(Collection::stream)
				.collect(Collectors.toMap(LineItem::getId, Function.identity()));

		if(CollectionUtils.isEmpty(details)) {
			errorMap.put(ERR_BILL_UPDATE_NOTNULL_BILLDETAILS, MSG_BILL_UPDATE_NOTNULL_BILLDETAILS);
		}else {
			for (BillDetail currentDetail : details) {

				String currentDetailId = currentDetail.getId();
				BillDetail currentDetailFromSearch = searchDetailsMap.get(currentDetailId);

				if(null == currentDetailId) {
					currentDetail.setStatus(Status.ACTIVE);
				}
				if (null == currentDetailFromSearch) {
					invalidDetailIds.add(currentDetailId);
				}else {
					if(currentDetail.getStatus() == null)
						currentDetail.setStatus(currentDetailFromSearch.getStatus());

					List<LineItem> lineItems = currentDetail.getLineItems();
					List<LineItem> payableLineItems = currentDetail.getPayableLineItems();

					for (LineItem currentLineItem : lineItems) {

						String currentLineItemId = currentLineItem.getId();
						LineItem searchLineItem = searchLineItemMap.get(currentLineItemId);
						if(null == currentLineItemId) {
							currentLineItem.setStatus(Status.ACTIVE);
						}
						else if(null == searchLineItem) {
							invalidLineItemIds.add(currentLineItemId);
						}else {
							if(null == currentLineItem.getStatus())
								currentLineItem.setStatus(searchLineItem.getStatus());
						}
					}

					for (LineItem currentPayableLineItem : payableLineItems) {

						String currentPayableLineItemId = currentPayableLineItem.getId();
						LineItem searchPayableLineItem = searchPayableLineItemMap.get(currentPayableLineItemId);

						if(null == currentPayableLineItemId) {
							currentPayableLineItem.setStatus(Status.ACTIVE);
						}
						else if(null == searchPayableLineItem) {
							invalidLineItemIds.add(currentPayableLineItemId);
						}else {
							if(null == currentPayableLineItem.getStatus())
								currentPayableLineItem.setStatus(searchPayableLineItem.getStatus());
						}
					}
				}
			}

			if(!CollectionUtils.isEmpty(invalidDetailIds))
				errorMap.put(ERR_BILL_UPDATE_NOTNULL_BILLDETAIL_ID,
						BILL_DETAIL_ID_IS_INVALID_FOR_THE_GIVEN_IDS_OF_UPDATE_REQUEST + invalidDetailIds);

			if(!CollectionUtils.isEmpty(invalidLineItemIds))
				errorMap.put(ERR_BILL_UPDATE_NOTNULL_LINEITEM_ID,
						BILL_DETAIL_ID_IS_INVALID_FOR_THE_GIVEN_IDS_OF_UPDATE_REQUEST + invalidLineItemIds);

			if(!CollectionUtils.isEmpty(invalidPayableLineItemIds))
				errorMap.put(ERR_BILL_UPDATE_NOTNULL_PAYABLE_LINEITEM_ID,
						BILL_DETAIL_ID_IS_INVALID_FOR_THE_GIVEN_IDS_OF_UPDATE_REQUEST + invalidPayableLineItemIds);
		}

	}

	/**
	 * Validates that payment detail fields on BillDetail can only be updated when:
	 * - Bill status is PENDING_VERIFICATION or PARTIALLY_VERIFIED
	 * - BillDetail status is PENDING_VERIFICATION or VERIFICATION_FAILED
	 *
	 * Uses bill/detail `status` (stored in DB, set from workflow state) rather than
	 * `wfStatus` (transient, only enriched during external search — always null here).
	 */
	public void validatePaymentFieldUpdate(Bill bill, Bill billFromSearch) {
		Set<String> allowedBillStatuses = Set.of(STATUS_PENDING_VERIFICATION, STATUS_PARTIALLY_VERIFIED);
		Set<String> allowedBillDetailStatuses = Set.of(STATUS_PENDING_VERIFICATION, STATUS_VERIFICATION_FAILED);

		// Use status (stored in DB) as the reliable proxy for workflow state
		String billStatus = billFromSearch.getStatus() != null ? billFromSearch.getStatus().toString() : null;
		if (billStatus != null && !allowedBillStatuses.contains(billStatus)) {
			if (hasPaymentFieldChanges(bill, billFromSearch)) {
				throw new CustomException(ERR_PAYMENT_FIELD_UPDATE_NOT_ALLOWED,
						"Payment details can only be updated when bill status is PENDING_VERIFICATION or PARTIALLY_VERIFIED. Current status: " + billStatus);
			}
		}

		if (bill.getBillDetails() != null) {
			Map<String, BillDetail> searchDetailsMap = billFromSearch.getBillDetails().stream()
					.collect(Collectors.toMap(BillDetail::getId, Function.identity()));

			for (BillDetail detail : bill.getBillDetails()) {
				if (detail.getId() == null) continue;
				BillDetail detailFromSearch = searchDetailsMap.get(detail.getId());
				if (detailFromSearch == null) continue;

				if (hasPaymentFieldChangesOnDetail(detail, detailFromSearch)) {
					String detailStatus = detailFromSearch.getStatus() != null
							? detailFromSearch.getStatus().toString() : null;
					if (detailStatus != null && !allowedBillDetailStatuses.contains(detailStatus)) {
						throw new CustomException(ERR_PAYMENT_FIELD_UPDATE_NOT_ALLOWED,
								"Payment details on bill detail " + detail.getId()
								+ " can only be updated when bill detail status is PENDING_VERIFICATION or VERIFICATION_FAILED. Current status: " + detailStatus);
					}
				}
			}
		}

		validatePaymentProviderValues(bill);
		// TODO: Propagate payment detail changes back to Worker Registry
	}

	/**
	 * Detects if any payment field on any existing detail has actually changed from the stored value.
	 * Delegates to per-detail comparison to avoid false positives from non-null unchanged fields.
	 */
	private boolean hasPaymentFieldChanges(Bill bill, Bill billFromSearch) {
		if (bill.getBillDetails() == null) return false;
		Map<String, BillDetail> searchDetailsMap = billFromSearch.getBillDetails().stream()
				.collect(Collectors.toMap(BillDetail::getId, Function.identity()));
		return bill.getBillDetails().stream().anyMatch(detail -> {
			if (detail.getId() == null) return false;
			BillDetail detailFromSearch = searchDetailsMap.get(detail.getId());
			if (detailFromSearch == null) return false;
			return hasPaymentFieldChangesOnDetail(detail, detailFromSearch);
		});
	}

	private boolean hasPaymentFieldChangesOnDetail(BillDetail detail, BillDetail detailFromSearch) {
		Party payee = detail.getPayee();
		Party payeeFromSearch = detailFromSearch.getPayee();
		if (payee == null || payeeFromSearch == null) return false;
		return (payee.getPaymentProvider() != null && !payee.getPaymentProvider().equals(payeeFromSearch.getPaymentProvider()))
				|| (payee.getPayeeName() != null && !payee.getPayeeName().equals(payeeFromSearch.getPayeeName()))
				|| (payee.getPayeePhoneNumber() != null && !payee.getPayeePhoneNumber().equals(payeeFromSearch.getPayeePhoneNumber()))
				|| (payee.getBankAccount() != null && !payee.getBankAccount().equals(payeeFromSearch.getBankAccount()))
				|| (payee.getBankCode() != null && !payee.getBankCode().equals(payeeFromSearch.getBankCode()))
				|| (payee.getBeneficiaryCode() != null && !payee.getBeneficiaryCode().equals(payeeFromSearch.getBeneficiaryCode()));
	}

	private void validatePaymentProviderValues(Bill bill) {
		if (bill.getBillDetails() == null) return;
		for (BillDetail detail : bill.getBillDetails()) {
			Party payee = detail.getPayee();
			if (payee != null && payee.getPaymentProvider() != null
					&& !Constants.VALID_PAYMENT_PROVIDERS.contains(payee.getPaymentProvider().toUpperCase())) {
				throw new CustomException(ERR_INVALID_PAYMENT_PROVIDER,
						"Invalid paymentProvider '" + payee.getPaymentProvider()
						+ "' on bill detail " + detail.getId()
						+ ". Allowed values: " + Constants.VALID_PAYMENT_PROVIDERS);
			}
		}
	}

	public void validateSearchRequest(BillSearchRequest billSearchRequest) {

		BillCriteria billCriteria = billSearchRequest.getBillCriteria();
		if (StringUtils.isEmpty(billCriteria.getBusinessService())
				&& CollectionUtils.isEmpty(billCriteria.getReferenceIds())
				&& CollectionUtils.isEmpty(billCriteria.getIds())
				&& CollectionUtils.isEmpty(billCriteria.getBillNumbers())
				&& billCriteria.getIsPaymentStatusNull() == null ) {
			if (configs.isHealthContextEnabled())
				throw new CustomException(ERR_BILL_SEARCH_ERROR,
						"One of ids OR referenceIds OR billNumbers should be provided for a bill search");
			throw new CustomException(ERR_BILL_SEARCH_ERROR,
					"One of ids OR (referenceIds & businessService) OR (billNumbers & businessService) should be provided for a bill search");
		}

		if (configs.isHealthContextEnabled()) {
			if (org.apache.commons.lang3.StringUtils.isNotBlank(billCriteria.getLocalityCode()) && CollectionUtils.isEmpty(billCriteria.getReferenceIds())) {
				throw new CustomException(ERR_BILL_SEARCH_ERROR, "referenceIds should be provided along with localityCode for a bill search");
			}
		} else {
			boolean isRefIdOrBillNoProvided = (!CollectionUtils.isEmpty(billCriteria.getReferenceIds())
					|| !CollectionUtils.isEmpty(billCriteria.getBillNumbers()));
			boolean isBusinessServiceProvided = !StringUtils.isEmpty(billCriteria.getBusinessService());

			if ((isRefIdOrBillNoProvided && !isBusinessServiceProvided)
					|| (isBusinessServiceProvided && !isRefIdOrBillNoProvided))
				throw new CustomException(ERR_BILL_SEARCH_ERROR,
						"The values of referenceIds or billNumbers should be provided along with businessService for a bill search");
		}
	}

	private void validateMasterData(BillRequest billRequest, Map<String, String> errorMap, Map<String, Map<String, JSONArray>> mdmsData, boolean isCreate) {

		Bill bill = billRequest.getBill();

		/* validating head code master data */
		List<String> businessCodeList = JsonPath.read(mdmsData.get(Constants.EXPENSE_MODULE_NAME).get(BUSINESS_SERVICE_MASTERNAME),CODE_FILTER);

		if (!businessCodeList.contains(bill.getBusinessService())) {
			errorMap.put(ERR_INVALID_BUSINESSSERVICE,
					"The business service value : " + bill.getBusinessService() + " is invalid");
		}

		List<String> headCodeList = JsonPath.read(mdmsData.get(Constants.EXPENSE_MODULE_NAME).get(HEADCODE_MASTERNAME),CODE_FILTER);

		Set<String> missingHeadCodes = new HashSet<>();
		BigDecimal billAmount = BigDecimal.ZERO;
		BigDecimal billPaidAmount = BigDecimal.ZERO;

		for (BillDetail billDetail : bill.getBillDetails()) {

			BigDecimal billDetailAmount = BigDecimal.ZERO;
			BigDecimal billDetailPaidAmount = BigDecimal.ZERO;

			for (LineItem item : billDetail.getLineItems()) {

				BigDecimal amount = item.getAmount();
				BigDecimal paidAmount = item.getPaidAmount() != null ? item.getPaidAmount() : BigDecimal.ZERO;

				if (!headCodeList.contains(item.getHeadCode()))
					missingHeadCodes.add(item.getHeadCode());

				if (amount.compareTo(paidAmount) < 0)
					errorMap.put(ERR_LINEITEM_INVALID_AMOUNT,
							"The tax amount : " + amount + " cannot be lesser than the paid amount : " + paidAmount);
				item.setPaidAmount(paidAmount);
			}

			for (LineItem payableLineItem : billDetail.getPayableLineItems()) {

				BigDecimal amount = payableLineItem.getAmount();
				BigDecimal paidAmount = payableLineItem.getPaidAmount() != null ? payableLineItem.getPaidAmount()
						: BigDecimal.ZERO;

				if (isCreate || (!isCreate && payableLineItem.getStatus().equals(Status.ACTIVE))) {

					billDetailAmount = billDetailAmount.add(amount);
					billDetailPaidAmount = billDetailPaidAmount.add(paidAmount);
				}

				if (!headCodeList.contains(payableLineItem.getHeadCode()))
					missingHeadCodes.add(payableLineItem.getHeadCode());

				if (amount.compareTo(paidAmount) < 0)
					errorMap.put(ERR_LINEITEM_INVALID_AMOUNT,
							"The tax amount : " + amount + " cannot be lesser than the paid amount : " + paidAmount);
				payableLineItem.setPaidAmount(paidAmount);
			}

			billDetail.setTotalAmount(billDetailAmount);
			billDetail.setTotalPaidAmount(billDetailPaidAmount);
			if (isCreate || (!isCreate && billDetail.getStatus().equals(Status.ACTIVE))) {

				billAmount = billAmount.add(billDetailAmount);
				billPaidAmount = billPaidAmount.add(billDetailPaidAmount);
			}
		}
		bill.setTotalAmount(billAmount);
		bill.setTotalPaidAmount(billPaidAmount);

		if (!CollectionUtils.isEmpty(missingHeadCodes))
			errorMap.put(ERR_INVALID_HEADCODES, "The following head codes are invalid : " + missingHeadCodes);
	}

	private void validateTenantId(BillRequest billRequest, Map<String, Map<String, JSONArray>> mdmsData) {

		Bill bill = billRequest.getBill();

		List<String> tenantIdList=null;
		try {
			/* validating head code master data */
			tenantIdList = JsonPath.read(mdmsData.get(Constants.TENANT_MODULE_NAME).get(TENANT_MASTERNAME), CODE_FILTER);
		} catch (Exception e) {
			throw new CustomException("INVALID_TENANT", "Invalid tenantId [" + bill.getTenantId() + "]");
		}

		if (!tenantIdList.contains(bill.getTenantId())){
			throw new CustomException("INVALID_TENANT", "Invalid tenantId [" + bill.getTenantId() + "]");
		}
	}

	private void validateBillAmountAndDate( Bill bill, Map<String, String> errorMap) {

		Long billDate = null != bill.getBillDate() ? bill.getBillDate() : 0l;
		Long dueDate = null != bill.getDueDate() ? bill.getDueDate() : Long.MAX_VALUE;

		if(dueDate.compareTo(billDate) < 0)
			errorMap.put(ERR_BILL_INVALID_DATE,
					"The due Date : " + billDate + " cannot be greater than the due Date : " + dueDate);
	}

	/**
	 * check whether the workflow is enabled for the given business type
	 *
	 * @param businessServiceName
	 * @return
	 */
	public boolean isWorkflowActiveForBusinessService(String businessServiceName) {
		Map<String, Boolean> workflowActiveMap = configs.getBusinessServiceWorkflowStatusMap();
		return null != workflowActiveMap.get(businessServiceName)
				&& workflowActiveMap.get(businessServiceName);
	}

	private List<Bill> getBillsForValidation(BillRequest billRequest, Boolean isCreate){

		Bill bill = billRequest.getBill();
		BillCriteria billCriteria = BillCriteria.builder()
				.statusesNot(Collections.singletonList(Status.INACTIVE.toString()))
				.tenantId(bill.getTenantId())
				.build();

		if (Boolean.TRUE.equals(isCreate)) {

			billCriteria.setReferenceIds(Stream.of(bill.getReferenceId()).collect(Collectors.toSet()));
			billCriteria.setBusinessService(bill.getBusinessService());
		} else {

			billCriteria.setIds(Stream.of(bill.getId()).collect(Collectors.toSet()));
		}



		BillSearchRequest billSearchRequest = BillSearchRequest.builder()
				.requestInfo(billRequest.getRequestInfo())
				.billCriteria(billCriteria)
				.build();

		return billRepository.search(billSearchRequest, true);
	}

	private void validateWorkflow(BillRequest billRequest, Map<String, String> errorMap) {

		Bill bill = billRequest.getBill();
		boolean isWorkflowActiveForBusinessService = isWorkflowActiveForBusinessService(bill.getBusinessService());

		if (isWorkflowActiveForBusinessService) {

			Workflow workflow = billRequest.getWorkflow();

			if (null == workflow)
				throw new CustomException(ERR_BILL_WF_ERROR, MSG_BILL_WF_ERROR);

			if (null == workflow.getAction())
				errorMap.put(ERR_BILL_WF_FIELDS_ERROR,
						MSG_BILL_WF_FIELDS_ERROR);
		}
	}

	private Map<String, Map<String, JSONArray>> getMasterDataForValidation(BillRequest billRequest, Bill bill) {

		Map<String, Map<String, JSONArray>> mdmsData = mdmsUtil.fetchMdmsData(billRequest.getRequestInfo(),
				bill.getTenantId(), billRequest);
		if(CollectionUtils.isEmpty(mdmsData)) {
			throw new CustomException(ERR_MDMS_ERROR, "MDMS Data not found for the tenantid : " + bill.getTenantId());
		}
		return mdmsData;
	}

	private boolean isV2Bill(Bill bill) {
		if (getBooleanAdditionalDetail(bill, "v2Enhanced").orElse(Boolean.FALSE)) {
			return true;
		}

		if (getStringAdditionalDetail(bill, "billingType")
				.map(value -> "INTERMEDIATE".equalsIgnoreCase(value))
				.orElse(false)) {
			return true;
		}

		return getBillingPeriodId(bill).isPresent();
	}

	private boolean hasV2Conflict(Bill existingBill, Bill newBill) {

		// FINAL_AGGREGATE bills should coexist with intermediate bills.
        // Only block if another FINAL_AGGREGATE already exists.
		boolean newIsFinalAggregate = isFinalAggregate(newBill);
		boolean existingIsFinalAggregate = isFinalAggregate(existingBill);

		if (newIsFinalAggregate) {
			return existingIsFinalAggregate;
		}

		// Existing FINAL_AGGREGATE should not block non-final bills
		if (existingIsFinalAggregate) {
			return false;
		}

		Optional<String> newBillingPeriod = getBillingPeriodId(newBill);
		Optional<String> existingBillingPeriod = getBillingPeriodId(existingBill);

		if (newBillingPeriod.isPresent() && existingBillingPeriod.isPresent()) {
			return Objects.equals(existingBillingPeriod.get(), newBillingPeriod.get());
		}

		if (!isV2Bill(existingBill)) {
			return false;
		}

		Long existingFrom = existingBill.getFromPeriod();
		Long existingTo = existingBill.getToPeriod();
		Long newFrom = newBill.getFromPeriod();
		Long newTo = newBill.getToPeriod();

		if (existingFrom == null || existingTo == null || newFrom == null || newTo == null) {
			return false;
		}

		return periodsOverlap(existingFrom, existingTo, newFrom, newTo);
	}

	private Optional<String> getBillingPeriodId(Bill bill) {
		return getStringAdditionalDetail(bill, "billingPeriodId")
				.filter(StringUtils::hasText);
	}

	private boolean isFinalAggregate(Bill bill) {
		return getStringAdditionalDetail(bill, "billingType")
				.map(value -> "FINAL_AGGREGATE".equalsIgnoreCase(value))
				.orElse(false);
	}

	private Optional<String> getStringAdditionalDetail(Bill bill, String key) {
		if (bill.getAdditionalDetails() instanceof Map) {
			Object value = ((Map<?, ?>) bill.getAdditionalDetails()).get(key);
			return value == null ? Optional.empty() : Optional.of(String.valueOf(value));
		} else if (bill.getAdditionalDetails() instanceof JsonNode) {
			JsonNode jsonNode = (JsonNode) bill.getAdditionalDetails();
			JsonNode valueNode = jsonNode.get(key);
			if (valueNode == null || valueNode.isNull()) {
				return Optional.empty();
			}
			return Optional.of(valueNode.asText());
		}
		return Optional.empty();
	}

	private Optional<Boolean> getBooleanAdditionalDetail(Bill bill, String key) {
		if (bill.getAdditionalDetails() instanceof Map) {
			Object value = ((Map<?, ?>) bill.getAdditionalDetails()).get(key);
			if (value == null) {
				return Optional.empty();
			}

			if (value instanceof Boolean) {
				return Optional.of((Boolean) value);
			}
			return Optional.of(Boolean.parseBoolean(String.valueOf(value)));
		} else if (bill.getAdditionalDetails() instanceof JsonNode) {
			JsonNode jsonNode = (JsonNode) bill.getAdditionalDetails();
			JsonNode valueNode = jsonNode.get(key);
			if (valueNode == null || valueNode.isNull()) {
				return Optional.empty();
			}
			return Optional.of(valueNode.asBoolean());
		}
		return Optional.empty();
	}

	private boolean periodsOverlap(Long existingFrom, Long existingTo, Long newFrom, Long newTo) {
		return !(existingTo < newFrom || existingFrom > newTo);
	}

	private String buildDuplicateBillErrorMessage(Bill bill) {
		// V2 Periodic Billing: Check if either health context or v2 periodic billing is enabled
		if ((configs.isHealthContextEnabled() || configs.isV2PeriodicBillingEnabled()) && isV2Bill(bill)) {
			String periodIdentifier = getBillingPeriodId(bill)
					.orElseGet(() -> {
						Long from = bill.getFromPeriod();
						Long to = bill.getToPeriod();
						return (from != null && to != null) ? from + "_" + to : "unknown";
					});
			return "Active bill exists for the given combination of businessService : "
					+ bill.getBusinessService()
					+ " , referenceId : " + bill.getReferenceId()
					+ " and billingPeriod : " + periodIdentifier
					+ ". V2 Periodic Billing is enabled - only one bill is allowed per register per billing period.";
		}

		return "Active bill exists for the given combination of "
				+ " businessService : " + bill.getBusinessService()
				+ " and referenceId : " + bill.getReferenceId()
				+ ". Only one bill is allowed per register (V1 mode).";
	}

	/**
	 * Rejects VERIFY action if any bill detail is already in VERIFICATION_IN_PROGRESS.
	 * Per design: a duplicate verify request on an in-flight bill must be rejected immediately.
	 */
	public void validateNoBillDetailInVerificationInProgress(Bill bill) {
		boolean anyInProgress = bill.getBillDetails().stream()
				.anyMatch(d -> d.getStatus() == Status.VERIFICATION_IN_PROGRESS);
		if (anyInProgress) {
			throw new CustomException("BILL_DETAIL_VERIFICATION_IN_PROGRESS",
					"Cannot VERIFY: one or more bill details are already in VERIFICATION_IN_PROGRESS. "
							+ "Wait for current verification to complete.");
		}
	}

	/**
	 * Rejects any API mutation when the bill is in a locked state (isStateUpdatable=false).
	 * Must be called before any update or workflow action on a PAYMENTS.BILL entity.
	 */
	public void validateBillStateUpdatable(Bill bill) {
		Set<Status> lockedStates = Set.of(
				Status.VERIFICATION_IN_PROGRESS,
				Status.IGNORING_ERRORS_IN_PROGRESS,
				Status.SENDING_FOR_REVIEW,
				Status.REVIEW_IN_PROGRESS,
				Status.PAYMENT_IN_PROGRESS,
				Status.FULLY_PAID
		);

		if (lockedStates.contains(bill.getStatus())) {
			throw new CustomException("BILL_STATE_LOCKED",
					"Bill " + bill.getId() + " is in locked state " + bill.getStatus()
							+ " — no API mutations allowed");
		}
	}

	/**
	 * Rejects any API mutation when the bill detail is in a locked state (isStateUpdatable=false).
	 * Must be called before any update or workflow action on a PAYMENTS.BILLDETAILS entity.
	 * Pass the parent bill to enable additional bill-state-driven locks (EC-7).
	 */
	public void validateBillDetailStateUpdatable(BillDetail detail, Bill bill) {
		Set<Status> alwaysLocked = Set.of(
				Status.VERIFICATION_IN_PROGRESS,
				Status.PAYMENT_IN_PROGRESS,
				Status.PAID
		);

		if (alwaysLocked.contains(detail.getStatus())) {
			throw new CustomException("BILL_DETAIL_STATE_LOCKED",
					"Bill detail " + detail.getId() + " is in locked state " + detail.getStatus()
							+ " — no API mutations allowed");
		}

		// EC-7: additional locks driven by bill-level intermediate states
		if (bill != null) {
			if (bill.getStatus() == Status.SENDING_FOR_REVIEW && detail.getStatus() == Status.VERIFIED) {
				throw new CustomException(ERR_BILL_DETAIL_LOCKED_SENDING_FOR_REVIEW,
						MSG_BILL_DETAIL_LOCKED_SENDING_FOR_REVIEW);
			}
			if (bill.getStatus() == Status.REVIEW_IN_PROGRESS && detail.getStatus() == Status.UNDER_REVIEW) {
				throw new CustomException(ERR_BILL_DETAIL_LOCKED_REVIEW_IN_PROGRESS,
						MSG_BILL_DETAIL_LOCKED_REVIEW_IN_PROGRESS);
			}
		}
	}

	public static final int BULK_UPDATE_MAX_BILLS = 100;

	public List<BulkUpdateError> validateBulkStatusUpdateRequest(BulkBillStatusUpdateRequest bulkRequest) {
		List<BulkUpdateError> errors = new ArrayList<>();

		List<String> billIds = bulkRequest.getBillIds();

		if (CollectionUtils.isEmpty(billIds)) {
			errors.add(BulkUpdateError.builder()
					.code(ERR_BULK_STATUS_EMPTY)
					.message(MSG_BULK_STATUS_EMPTY)
					.build());
			return errors;
		}

		if (billIds.size() > BULK_UPDATE_MAX_BILLS) {
			errors.add(BulkUpdateError.builder()
					.code(ERR_BULK_STATUS_MAX_LIMIT)
					.message("Maximum " + BULK_UPDATE_MAX_BILLS + " bill IDs allowed per bulk status update request")
					.build());
			return errors;
		}

		Set<String> uniqueBillIds = new HashSet<>(billIds);
		if (uniqueBillIds.size() != billIds.size()) {
			errors.add(BulkUpdateError.builder()
					.code(ERR_BULK_STATUS_DUPLICATE_IDS)
					.message(MSG_BULK_STATUS_DUPLICATE_IDS)
					.build());
		}

		if (bulkRequest.getStatus() == null || bulkRequest.getStatus().isBlank()) {
			errors.add(BulkUpdateError.builder()
					.code(ERR_BULK_STATUS_INVALID)
					.message(MSG_BULK_STATUS_INVALID)
					.build());
		}

		if (bulkRequest.getTenantId() == null || bulkRequest.getTenantId().isBlank()) {
			errors.add(BulkUpdateError.builder()
					.code(ERR_BULK_STATUS_TENANT_REQUIRED)
					.message(MSG_BULK_STATUS_TENANT_REQUIRED)
					.build());
		}

		if (!errors.isEmpty()) {
			return errors;
		}

		for (String billId : billIds) {
			try {
				BillSearchRequest searchRequest = BillSearchRequest.builder()
						.requestInfo(bulkRequest.getRequestInfo())
						.billCriteria(BillCriteria.builder()
								.ids(Set.of(billId))
								.tenantId(bulkRequest.getTenantId())
								.statusesNot(Collections.singletonList(Status.INACTIVE.toString()))
								.build())
						.build();

				List<Bill> billsFromSearch = billRepository.search(searchRequest, true);
				if (CollectionUtils.isEmpty(billsFromSearch)) {
					throw new CustomException(ERR_INVALID_BILL,
							"Bill does not exist for id: " + billId);
				}

				validateBillStateUpdatable(billsFromSearch.get(0));

			} catch (CustomException e) {
				errors.add(BulkUpdateError.builder()
						.billId(billId)
						.code(e.getCode())
						.message(e.getMessage())
						.build());
			}
		}

		return errors;
	}

	public List<Bill> getBillsByIds(List<String> billIds, String tenantId, org.egov.common.contract.request.RequestInfo requestInfo) {
		BillSearchRequest searchRequest = BillSearchRequest.builder()
				.requestInfo(requestInfo)
				.billCriteria(BillCriteria.builder()
						.ids(new HashSet<>(billIds))
						.tenantId(tenantId)
						.statusesNot(Collections.singletonList(Status.INACTIVE.toString()))
						.build())
				.build();

		return billRepository.search(searchRequest, true);
	}

	/**
	 * Validates a partial bill detail update request.
	 * Checks: bill existence, all detail IDs belong to the bill, payment field guards.
	 *
	 * @return the bill fetched from DB (used downstream for enrichment)
	 */
	public static class BillDetailValidationResult {
		public final Bill bill;
		public final List<org.egov.digit.expense.web.models.BillDetailUpdateError> warnings;
		public BillDetailValidationResult(Bill bill, List<org.egov.digit.expense.web.models.BillDetailUpdateError> warnings) {
			this.bill = bill;
			this.warnings = warnings;
		}
	}

	public BillDetailValidationResult validateBillDetailUpdateRequest(org.egov.digit.expense.web.models.BillDetailUpdateRequest request) {

		// 1. Fetch bill from DB
		Bill billFromSearch = getBillById(request.getBillId(), request.getTenantId(), request.getRequestInfo());
		if (billFromSearch == null)
			throw new CustomException(ERR_INVALID_BILL,
					"Bill not found: id=" + request.getBillId() + " tenantId=" + request.getTenantId());

		// 2. Validate all requested detail IDs exist under this bill
		Map<String, BillDetail> searchDetailMap = billFromSearch.getBillDetails().stream()
				.collect(Collectors.toMap(BillDetail::getId, Function.identity()));

		List<String> invalidIds = request.getBillDetails().stream()
				.map(org.egov.digit.expense.web.models.PartialBillDetail::getId)
				.filter(id -> !searchDetailMap.containsKey(id))
				.collect(Collectors.toList());

		if (!invalidIds.isEmpty())
			throw new CustomException(ERR_INVALID_BILL_DETAIL_IDS,
					"BillDetail ids not found under bill " + request.getBillId() + ": " + invalidIds);

		// 3. Role-based field access — strips blocked fields in-place, returns warnings
		List<org.egov.digit.expense.web.models.BillDetailUpdateError> warnings =
				stripAndWarnBlockedFields(request, billFromSearch, searchDetailMap);

		return new BillDetailValidationResult(billFromSearch, warnings);
	}

	/**
	 * Enforces role-based access for billdetails/_update by stripping blocked fields
	 * from each PartialBillDetail in-place and collecting warnings. Hard validations
	 * (role presence, bill/detail status) still throw.
	 *
	 * PAYMENT_EDITOR: may update payee fields only (payeeName, payeePhoneNumber, bankAccount,
	 *   bankCode, beneficiaryCode, paymentProvider). Strips amount/attendance/lineItem fields.
	 *
	 * PAYMENT_REVIEWER: may update amount/calculation fields only (totalAmount, totalPaidAmount,
	 *   totalAttendance, lineItems, payableLineItems). Strips payee and workerId fields.
	 */
	private List<org.egov.digit.expense.web.models.BillDetailUpdateError> stripAndWarnBlockedFields(
			org.egov.digit.expense.web.models.BillDetailUpdateRequest request,
			Bill billFromSearch,
			Map<String, BillDetail> searchDetailMap) {

		List<org.egov.common.contract.request.Role> rawRoles = request.getRequestInfo().getUserInfo().getRoles();
		Set<String> userRoles = (rawRoles != null ? rawRoles.stream() : java.util.stream.Stream.<org.egov.common.contract.request.Role>empty())
				.filter(r -> r != null && r.getCode() != null)
				.map(Role::getCode)
				.collect(Collectors.toSet());

		boolean hasPaymentEditor   = userRoles.contains(ROLE_PAYMENT_EDITOR);
		boolean hasPaymentReviewer = userRoles.contains(ROLE_PAYMENT_REVIEWER);

		if (!hasPaymentEditor && !hasPaymentReviewer)
			throw new CustomException(ERR_UNAUTHORIZED,
					MSG_UNAUTHORIZED_UPDATE);

		String billStatus = billFromSearch.getStatus() != null ? billFromSearch.getStatus().toString() : "";

		boolean editorStatusMatch   = STATUS_PENDING_VERIFICATION.equals(billStatus) || STATUS_PARTIALLY_VERIFIED.equals(billStatus);
		boolean reviewerStatusMatch = STATUS_UNDER_REVIEW.equals(billStatus);

		List<org.egov.digit.expense.web.models.BillDetailUpdateError> warnings = new ArrayList<>();

		if (hasPaymentEditor && editorStatusMatch) {
			Set<String> allowedDetailStatuses = Set.of(STATUS_PENDING_VERIFICATION, STATUS_VERIFICATION_FAILED);
			Iterator<org.egov.digit.expense.web.models.PartialBillDetail> editorIter = request.getBillDetails().iterator();
			while (editorIter.hasNext()) {
				org.egov.digit.expense.web.models.PartialBillDetail pd = editorIter.next();
				BillDetail db = searchDetailMap.get(pd.getId());
				String detailStatus = db.getStatus() != null ? db.getStatus().toString() : null;
				if (detailStatus != null && !allowedDetailStatuses.contains(detailStatus)) {
					warnings.add(org.egov.digit.expense.web.models.BillDetailUpdateError.builder()
							.code(ERR_DETAIL_STATUS_SKIPPED_EDITOR)
							.message("Bill detail " + pd.getId() + " cannot be updated at its current stage.")
							.build());
					editorIter.remove();
					continue;
				}
				stripAmountFields(pd, db, warnings);
			}
			return warnings;
		}

		if (hasPaymentReviewer && reviewerStatusMatch) {
			Iterator<org.egov.digit.expense.web.models.PartialBillDetail> reviewerIter = request.getBillDetails().iterator();
			while (reviewerIter.hasNext()) {
				org.egov.digit.expense.web.models.PartialBillDetail pd = reviewerIter.next();
				BillDetail db = searchDetailMap.get(pd.getId());
				String detailStatus = db.getStatus() != null ? db.getStatus().toString() : null;
				if (!STATUS_UNDER_REVIEW.equals(detailStatus)) {
					warnings.add(org.egov.digit.expense.web.models.BillDetailUpdateError.builder()
							.code(ERR_DETAIL_STATUS_SKIPPED_REVIEWER)
							.message("Bill detail " + pd.getId() + " is not available for review at its current stage.")
							.build());
					reviewerIter.remove();
					continue;
				}
				stripPayeeFields(pd, db, warnings);
			}
			return warnings;
		}

		throw new CustomException(ERR_ROLE_STATUS_MISMATCH,
				"The bill is not available for update at its current stage.");
	}

	/** Strips amount/attendance/lineItem fields blocked for PAYMENT_EDITOR. */
	private void stripAmountFields(
			org.egov.digit.expense.web.models.PartialBillDetail pd,
			BillDetail db,
			List<org.egov.digit.expense.web.models.BillDetailUpdateError> warnings) {

		List<String> stripped = new ArrayList<>();

		if (pd.getTotalAmount() != null) {
			BigDecimal dbAmount = db.getTotalAmount() != null ? db.getTotalAmount() : BigDecimal.ZERO;
			if (pd.getTotalAmount().compareTo(dbAmount) != 0) { pd.setTotalAmount(null); stripped.add("totalAmount"); }
		}
		if (pd.getTotalPaidAmount() != null) {
			BigDecimal dbPaid = db.getTotalPaidAmount() != null ? db.getTotalPaidAmount() : BigDecimal.ZERO;
			if (pd.getTotalPaidAmount().compareTo(dbPaid) != 0) { pd.setTotalPaidAmount(null); stripped.add("totalPaidAmount"); }
		}
		if (pd.getTotalAttendance() != null
				&& (db.getTotalAttendance() == null || pd.getTotalAttendance().compareTo(db.getTotalAttendance()) != 0)) {
			pd.setTotalAttendance(null); stripped.add("totalAttendance");
		}
		if (pd.getLineItems() != null && !pd.getLineItems().isEmpty()) {
			pd.setLineItems(null); stripped.add("lineItems");
		}
		if (pd.getPayableLineItems() != null && !pd.getPayableLineItems().isEmpty()) {
			pd.setPayableLineItems(null); stripped.add("payableLineItems");
		}
		if (pd.getWorkerId() != null && !pd.getWorkerId().equals(db.getWorkerId())) {
			pd.setWorkerId(null); stripped.add("workerId");
		}

		if (!stripped.isEmpty())
			warnings.add(org.egov.digit.expense.web.models.BillDetailUpdateError.builder()
					.billDetailId(pd.getId())
					.code(ERR_FIELD_STRIPPED_EDITOR)
					.message("Some fields on bill detail " + pd.getId() + " are not editable at this stage and have been ignored.")
					.build());
	}

	/** Strips payee/workerId fields blocked for PAYMENT_REVIEWER. */
	private void stripPayeeFields(
			org.egov.digit.expense.web.models.PartialBillDetail pd,
			BillDetail db,
			List<org.egov.digit.expense.web.models.BillDetailUpdateError> warnings) {

		List<String> stripped = new ArrayList<>();

		if (pd.getWorkerId() != null && !pd.getWorkerId().equals(db.getWorkerId())) {
			pd.setWorkerId(null); stripped.add("workerId");
		}

		Party pdPayee = pd.getPayee();
		Party dbPayee = db.getPayee();
		if (pdPayee != null && dbPayee != null) {
			if (pdPayee.getPayeeName() != null && !pdPayee.getPayeeName().equals(dbPayee.getPayeeName())) {
				pdPayee.setPayeeName(null); stripped.add("payee.payeeName");
			}
			if (pdPayee.getPayeePhoneNumber() != null && !pdPayee.getPayeePhoneNumber().equals(dbPayee.getPayeePhoneNumber())) {
				pdPayee.setPayeePhoneNumber(null); stripped.add("payee.payeePhoneNumber");
			}
			if (pdPayee.getBankAccount() != null && !pdPayee.getBankAccount().equals(dbPayee.getBankAccount())) {
				pdPayee.setBankAccount(null); stripped.add("payee.bankAccount");
			}
			if (pdPayee.getBankCode() != null && !pdPayee.getBankCode().equals(dbPayee.getBankCode())) {
				pdPayee.setBankCode(null); stripped.add("payee.bankCode");
			}
			if (pdPayee.getBeneficiaryCode() != null && !pdPayee.getBeneficiaryCode().equals(dbPayee.getBeneficiaryCode())) {
				pdPayee.setBeneficiaryCode(null); stripped.add("payee.beneficiaryCode");
			}
			if (pdPayee.getPaymentProvider() != null && !pdPayee.getPaymentProvider().equals(dbPayee.getPaymentProvider())) {
				pdPayee.setPaymentProvider(null); stripped.add("payee.paymentProvider");
			}
		}

		if (!stripped.isEmpty())
			warnings.add(org.egov.digit.expense.web.models.BillDetailUpdateError.builder()
					.billDetailId(pd.getId())
					.code(ERR_FIELD_STRIPPED_REVIEWER)
					.message("Some fields on bill detail " + pd.getId() + " cannot be changed at this stage and have been ignored.")
					.build());
	}

	private Bill getBillById(String billId, String tenantId, org.egov.common.contract.request.RequestInfo requestInfo) {
		BillSearchRequest searchRequest = BillSearchRequest.builder()
				.requestInfo(requestInfo)
				.billCriteria(BillCriteria.builder()
						.ids(new HashSet<>(List.of(billId)))
						.tenantId(tenantId)
						.build())
				.build();
		List<Bill> results = billRepository.search(searchRequest, true);
		return results.isEmpty() ? null : results.get(0);
	}

	public void validateReportMetaUpdateRequest(org.egov.digit.expense.web.models.ReportMetaUpdateRequest request) {
		if (request == null)
			throw new CustomException(ERR_BILL_META_UPDATE_ERROR, MSG_META_UPDATE_REQUEST_MANDATORY);
		if (!StringUtils.hasText(request.getBillId()))
			throw new CustomException(ERR_BILL_META_UPDATE_ERROR, MSG_META_UPDATE_BILL_ID_MANDATORY);
		if (!StringUtils.hasText(request.getTenantId()))
			throw new CustomException(ERR_BILL_META_UPDATE_ERROR, MSG_META_UPDATE_TENANT_ID_MANDATORY);
		if (request.getReportDetails() == null || request.getReportDetails().isEmpty())
			throw new CustomException(ERR_BILL_META_UPDATE_ERROR, MSG_META_UPDATE_REPORT_MANDATORY);
	}
}
