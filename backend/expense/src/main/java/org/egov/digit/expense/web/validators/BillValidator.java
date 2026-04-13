package org.egov.digit.expense.web.validators;

import static org.egov.digit.expense.config.Constants.BUSINESS_SERVICE_MASTERNAME;
import static org.egov.digit.expense.config.Constants.CODE_FILTER;
import static org.egov.digit.expense.config.Constants.HEADCODE_MASTERNAME;
import static org.egov.digit.expense.config.Constants.TENANT_MASTERNAME;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
import org.egov.digit.expense.web.models.BulkBillUpdateRequest;
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
				throw new CustomException("EG_EXPENSE_DUPLICATE_BILL", buildDuplicateBillErrorMessage(bill));
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
			throw new CustomException("EG_EXPENSE_INVALID_BILL","The bill does not exists for the given combination of "
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
			errorMap.put("EG_EXPENSE_BILL_UPDATE_NOTNULL_PAYER_ID", "Payer id is mandaotry for update request");
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
			errorMap.put("EG_EXPENSE_BILL_UPDATE_NOTNULL_BILLDETAILS", "bill details cannot be empty for update request");
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
				errorMap.put("EG_EXPENSE_BILL_UPDATE_NOTNULL_BillDETAIL_ID",
						BILL_DETAIL_ID_IS_INVALID_FOR_THE_GIVEN_IDS_OF_UPDATE_REQUEST + invalidDetailIds);

			if(!CollectionUtils.isEmpty(invalidLineItemIds))
				errorMap.put("EG_EXPENSE_BILL_UPDATE_NOTNULL_LINEITEM_ID",
						BILL_DETAIL_ID_IS_INVALID_FOR_THE_GIVEN_IDS_OF_UPDATE_REQUEST + invalidLineItemIds);

			if(!CollectionUtils.isEmpty(invalidPayableLineItemIds))
				errorMap.put("EG_EXPENSE_BILL_UPDATE_NOTNULL_PAYABLE_LINEITEM_ID",
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
		Set<String> allowedBillStatuses = Set.of("PENDING_VERIFICATION", "PARTIALLY_VERIFIED");
		Set<String> allowedBillDetailStatuses = Set.of("PENDING_VERIFICATION", "VERIFICATION_FAILED");

		// Use status (stored in DB) as the reliable proxy for workflow state
		String billStatus = billFromSearch.getStatus() != null ? billFromSearch.getStatus().toString() : null;
		if (billStatus != null && !allowedBillStatuses.contains(billStatus)) {
			if (hasPaymentFieldChanges(bill, billFromSearch)) {
				throw new CustomException("EG_EXPENSE_PAYMENT_FIELD_UPDATE_NOT_ALLOWED",
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
						throw new CustomException("EG_EXPENSE_PAYMENT_FIELD_UPDATE_NOT_ALLOWED",
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
		return (detail.getPaymentProvider() != null && !detail.getPaymentProvider().equals(detailFromSearch.getPaymentProvider()))
				|| (detail.getPayeeName() != null && !detail.getPayeeName().equals(detailFromSearch.getPayeeName()))
				|| (detail.getPayeePhoneNumber() != null && !detail.getPayeePhoneNumber().equals(detailFromSearch.getPayeePhoneNumber()))
				|| (detail.getBankAccount() != null && !detail.getBankAccount().equals(detailFromSearch.getBankAccount()))
				|| (detail.getBankCode() != null && !detail.getBankCode().equals(detailFromSearch.getBankCode()))
				|| (detail.getBeneficiaryCode() != null && !detail.getBeneficiaryCode().equals(detailFromSearch.getBeneficiaryCode()));
	}

	private void validatePaymentProviderValues(Bill bill) {
		if (bill.getBillDetails() == null) return;
		for (BillDetail detail : bill.getBillDetails()) {
			if (detail.getPaymentProvider() != null
					&& !Constants.VALID_PAYMENT_PROVIDERS.contains(detail.getPaymentProvider().toUpperCase())) {
				throw new CustomException("EG_EXPENSE_INVALID_PAYMENT_PROVIDER",
						"Invalid paymentProvider '" + detail.getPaymentProvider()
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
				throw new CustomException("EG_EXPENSE_BILL_SEARCH_ERROR",
						"One of ids OR referenceIds OR billNumbers should be provided for a bill search");
			throw new CustomException("EG_EXPENSE_BILL_SEARCH_ERROR",
					"One of ids OR (referenceIds & businessService) OR (billNumbers & businessService) should be provided for a bill search");
		}

		if (configs.isHealthContextEnabled()) {
			if (org.apache.commons.lang3.StringUtils.isNotBlank(billCriteria.getLocalityCode()) && CollectionUtils.isEmpty(billCriteria.getReferenceIds())) {
				throw new CustomException("EG_EXPENSE_BILL_SEARCH_ERROR", "referenceIds should be provided along with localityCode for a bill search");
			}
		} else {
			boolean isRefIdOrBillNoProvided = (!CollectionUtils.isEmpty(billCriteria.getReferenceIds())
					|| !CollectionUtils.isEmpty(billCriteria.getBillNumbers()));
			boolean isBusinessServiceProvided = !StringUtils.isEmpty(billCriteria.getBusinessService());

			if ((isRefIdOrBillNoProvided && !isBusinessServiceProvided)
					|| (isBusinessServiceProvided && !isRefIdOrBillNoProvided))
				throw new CustomException("EG_EXPENSE_BILL_SEARCH_ERROR",
						"The values of referenceIds or billNumbers should be provided along with businessService for a bill search");
		}
	}

	private void validateMasterData(BillRequest billRequest, Map<String, String> errorMap, Map<String, Map<String, JSONArray>> mdmsData, boolean isCreate) {

		Bill bill = billRequest.getBill();

		/* validating head code master data */
		List<String> businessCodeList = JsonPath.read(mdmsData.get(Constants.EXPENSE_MODULE_NAME).get(BUSINESS_SERVICE_MASTERNAME),CODE_FILTER);

		if (!businessCodeList.contains(bill.getBusinessService())) {
			errorMap.put("EG_EXPENSE_INVALID_BUSINESSSERVICE",
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
					errorMap.put("EG_EXPENSE_LINEITEM_INVALID_AMOUNT",
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
					errorMap.put("EG_EXPENSE_LINEITEM_INVALID_AMOUNT",
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
			errorMap.put("EG_EXPENSE_INVALID_HEADCODES", "The following head codes are invalid : " + missingHeadCodes);
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
			errorMap.put("EG_EXPENSE_BILL_INVALID_DATE",
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
				.statusNot(Status.INACTIVE.toString())
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
				throw new CustomException("EG_BILL_WF_ERROR", "workflow is mandatory when worflow is active");

			if (null == workflow.getAction())
				errorMap.put("EG_BILL_WF_FIELDS_ERROR",
						"workflow action is mandatory when worflow is active");
		}
	}

	private Map<String, Map<String, JSONArray>> getMasterDataForValidation(BillRequest billRequest, Bill bill) {

		Map<String, Map<String, JSONArray>> mdmsData = mdmsUtil.fetchMdmsData(billRequest.getRequestInfo(),
				bill.getTenantId(), billRequest);
		if(CollectionUtils.isEmpty(mdmsData)) {
			throw new CustomException("EG_EXPENSE_MDMS_ERROR", "MDMS Data not found for the tenantid : " + bill.getTenantId());
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
	 */
	public void validateBillDetailStateUpdatable(BillDetail detail) {
		Set<Status> lockedStates = Set.of(
				Status.VERIFICATION_IN_PROGRESS,
				Status.PAYMENT_IN_PROGRESS,
				Status.PAID
		);

		if (lockedStates.contains(detail.getStatus())) {
			throw new CustomException("BILL_DETAIL_STATE_LOCKED",
					"Bill detail " + detail.getId() + " is in locked state " + detail.getStatus()
							+ " — no API mutations allowed");
		}
	}

	public static final int BULK_UPDATE_MAX_BILLS = 100;

	public List<BulkUpdateError> validateBulkUpdateRequest(BulkBillUpdateRequest bulkRequest) {
		List<BulkUpdateError> errors = new ArrayList<>();

		List<Bill> bills = bulkRequest.getBills();

		if (CollectionUtils.isEmpty(bills)) {
			errors.add(BulkUpdateError.builder()
					.code("EG_EXPENSE_BULK_EMPTY")
					.message("At least one bill is required for bulk update")
					.build());
			return errors;
		}

		if (bills.size() > BULK_UPDATE_MAX_BILLS) {
			errors.add(BulkUpdateError.builder()
					.code("EG_EXPENSE_BULK_MAX_LIMIT")
					.message("Maximum " + BULK_UPDATE_MAX_BILLS + " bills allowed per bulk update request")
					.build());
			return errors;
		}

		String tenantId = bills.get(0).getTenantId();
		for (Bill bill : bills) {
			if (!tenantId.equals(bill.getTenantId())) {
				errors.add(BulkUpdateError.builder()
						.billId(bill.getId())
						.code("EG_EXPENSE_BULK_TENANT_MISMATCH")
						.message("All bills must have the same tenantId")
						.build());
			}
		}

		Set<String> billIds = bills.stream()
				.map(Bill::getId)
				.collect(Collectors.toSet());
		if (billIds.size() != bills.size()) {
			errors.add(BulkUpdateError.builder()
					.code("EG_EXPENSE_BULK_DUPLICATE_IDS")
					.message("Duplicate bill IDs are not allowed in bulk update request")
					.build());
		}

		if (!errors.isEmpty()) {
			return errors;
		}

		for (Bill bill : bills) {
			try {
				validateBillForBulkUpdate(bill, bulkRequest.getRequestInfo(), bulkRequest.getWorkflow());
			} catch (CustomException e) {
				errors.add(BulkUpdateError.builder()
						.billId(bill.getId())
						.code(e.getCode())
						.message(e.getMessage())
						.build());
			}
		}

		return errors;
	}

	private void validateBillForBulkUpdate(Bill bill, org.egov.common.contract.request.RequestInfo requestInfo, Workflow workflow) {
		Map<String, String> errorMap = new HashMap<>();

		if (isWorkflowActiveForBusinessService(bill.getBusinessService())) {
			if (workflow == null || workflow.getAction() == null) {
				throw new CustomException("EG_BILL_WF_ERROR", "workflow is mandatory when workflow is active");
			}
		}

		BillSearchRequest searchRequest = BillSearchRequest.builder()
				.requestInfo(requestInfo)
				.billCriteria(BillCriteria.builder()
						.ids(Set.of(bill.getId()))
						.tenantId(bill.getTenantId())
						.statusNot(Status.INACTIVE.toString())
						.build())
				.build();

		List<Bill> billsFromSearch = billRepository.search(searchRequest, true);
		if (CollectionUtils.isEmpty(billsFromSearch)) {
			throw new CustomException("EG_EXPENSE_INVALID_BILL",
					"The bill does not exist for the given combination of id: " + bill.getId() + " and tenantId: " + bill.getTenantId());
		}

		if (!configs.isHealthContextEnabled()) {
			validateFieldsForUpdate(bill, billsFromSearch.get(0), errorMap);
		}

		if (!CollectionUtils.isEmpty(errorMap)) {
			throw new CustomException(errorMap);
		}
	}

	public List<BulkUpdateError> validateBulkStatusUpdateRequest(BulkBillStatusUpdateRequest bulkRequest) {
		List<BulkUpdateError> errors = new ArrayList<>();

		List<String> billIds = bulkRequest.getBillIds();

		if (CollectionUtils.isEmpty(billIds)) {
			errors.add(BulkUpdateError.builder()
					.code("EG_EXPENSE_BULK_STATUS_EMPTY")
					.message("At least one bill ID is required for bulk status update")
					.build());
			return errors;
		}

		if (billIds.size() > BULK_UPDATE_MAX_BILLS) {
			errors.add(BulkUpdateError.builder()
					.code("EG_EXPENSE_BULK_STATUS_MAX_LIMIT")
					.message("Maximum " + BULK_UPDATE_MAX_BILLS + " bill IDs allowed per bulk status update request")
					.build());
			return errors;
		}

		Set<String> uniqueBillIds = new HashSet<>(billIds);
		if (uniqueBillIds.size() != billIds.size()) {
			errors.add(BulkUpdateError.builder()
					.code("EG_EXPENSE_BULK_STATUS_DUPLICATE_IDS")
					.message("Duplicate bill IDs are not allowed in bulk status update request")
					.build());
		}

		if (bulkRequest.getStatus() == null || bulkRequest.getStatus().isBlank()) {
			errors.add(BulkUpdateError.builder()
					.code("EG_EXPENSE_BULK_STATUS_INVALID")
					.message("Status is required for bulk status update")
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
								.tenantId(bulkRequest.getBillIds().get(0).contains(".") ? 
										bulkRequest.getBillIds().get(0).substring(0, bulkRequest.getBillIds().get(0).lastIndexOf('.')) : null)
								.statusNot(Status.INACTIVE.toString())
								.build())
						.build();

				List<Bill> billsFromSearch = billRepository.search(searchRequest, true);
				if (CollectionUtils.isEmpty(billsFromSearch)) {
					throw new CustomException("EG_EXPENSE_INVALID_BILL",
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
						.statusNot(Status.INACTIVE.toString())
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
	public Bill validateBillDetailUpdateRequest(org.egov.digit.expense.web.models.BillDetailUpdateRequest request) {

		// 1. Fetch bill from DB
		Bill billFromSearch = getBillById(request.getBillId(), request.getTenantId(), request.getRequestInfo());
		if (billFromSearch == null)
			throw new CustomException("EG_EXPENSE_INVALID_BILL",
					"Bill not found: id=" + request.getBillId() + " tenantId=" + request.getTenantId());

		// 2. Validate all requested detail IDs exist under this bill
		Map<String, BillDetail> searchDetailMap = billFromSearch.getBillDetails().stream()
				.collect(Collectors.toMap(BillDetail::getId, Function.identity()));

		List<String> invalidIds = request.getBillDetails().stream()
				.map(org.egov.digit.expense.web.models.PartialBillDetail::getId)
				.filter(id -> !searchDetailMap.containsKey(id))
				.collect(Collectors.toList());

		if (!invalidIds.isEmpty())
			throw new CustomException("EG_EXPENSE_INVALID_BILL_DETAIL_IDS",
					"BillDetail ids not found under bill " + request.getBillId() + ": " + invalidIds);

		// 3. Payment field guard — construct synthetic bill and reuse existing method
		List<BillDetail> syntheticDetails = request.getBillDetails().stream()
				.map(pd -> {
					BillDetail db = searchDetailMap.get(pd.getId());
					Status effectiveStatus = pd.getStatus() != null ? pd.getStatus() : db.getStatus();
					return BillDetail.builder()
							.id(pd.getId())
							.status(effectiveStatus)
							.workerId(pd.getWorkerId())
							.paymentProvider(pd.getPaymentProvider())
							.payeeName(pd.getPayeeName())
							.payeePhoneNumber(pd.getPayeePhoneNumber())
							.bankAccount(pd.getBankAccount())
							.bankCode(pd.getBankCode())
							.beneficiaryCode(pd.getBeneficiaryCode())
							.payee(db.getPayee())
							.payableLineItems(db.getPayableLineItems())
							.build();
				})
				.collect(Collectors.toList());

		Bill syntheticBill = Bill.builder()
				.id(billFromSearch.getId())
				.tenantId(billFromSearch.getTenantId())
				.status(billFromSearch.getStatus())
				.billDetails(syntheticDetails)
				.build();

		validatePaymentFieldUpdate(syntheticBill, billFromSearch);

		return billFromSearch;
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
}
