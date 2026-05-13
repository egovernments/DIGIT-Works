package org.egov.digit.expense.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.common.contract.workflow.ProcessInstance;
import org.egov.common.contract.workflow.ProcessInstanceResponse;
import org.egov.common.contract.workflow.State;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.util.CalculatorUtil;
import org.egov.digit.expense.util.EnrichmentUtil;
import org.egov.digit.expense.util.ResponseInfoFactory;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.digit.expense.web.validators.BillValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BillService {

	private final ExpenseProducer expenseProducer;
	private final Configuration config;
	private final BillValidator validator;
	private final WorkflowUtil workflowUtil;
	private final BillRepository billRepository;
	private final EnrichmentUtil enrichmentUtil;
	private final ResponseInfoFactory responseInfoFactory;
	private final NotificationService notificationService;
	private final CalculatorUtil calculatorUtil;
	private final PaymentWorkflowService paymentWorkflowService;
	private final ObjectMapper objectMapper;
	private final BillCacheService billCacheService;
	private final BillDetailService billDetailService;
	private final BillDetailCacheService billDetailCacheService;

	@Autowired
	public BillService(ExpenseProducer expenseProducer, Configuration config, BillValidator validator,
	                   WorkflowUtil workflowUtil, BillRepository billRepository, EnrichmentUtil enrichmentUtil,
	                   ResponseInfoFactory responseInfoFactory, NotificationService notificationService,
	                   CalculatorUtil calculatorUtil, PaymentWorkflowService paymentWorkflowService,
	                   ObjectMapper objectMapper, BillCacheService billCacheService,
	                   BillDetailService billDetailService, BillDetailCacheService billDetailCacheService) {
		this.expenseProducer = expenseProducer;
		this.config = config;
		this.validator = validator;
		this.workflowUtil = workflowUtil;
		this.billRepository = billRepository;
		this.enrichmentUtil = enrichmentUtil;
		this.responseInfoFactory = responseInfoFactory;
		this.notificationService = notificationService;
		this.calculatorUtil = calculatorUtil;
		this.paymentWorkflowService = paymentWorkflowService;
		this.objectMapper = objectMapper;
		this.billCacheService = billCacheService;
		this.billDetailService = billDetailService;
		this.billDetailCacheService = billDetailCacheService;
	}

	public BillResponse create(BillRequest billRequest) {
		Bill bill = billRequest.getBill();
		String tenantId = bill.getTenantId();
		RequestInfo requestInfo = billRequest.getRequestInfo();

		validator.validateCreateRequest(billRequest);
		enrichmentUtil.encrichBillForCreate(billRequest);

		if (validator.isWorkflowActiveForBusinessService(bill.getBusinessService())) {
			State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBill(billRequest), billRequest);
			bill.setStatus(Status.fromValue(wfState.getApplicationStatus()));

			if (config.getBillBusinessService().equalsIgnoreCase(bill.getBusinessService())) {
				paymentWorkflowService.createBillDetailProcessInstances(billRequest);
			}

			try {
				if (billRequest.getBill().getBusinessService().equalsIgnoreCase("EXPENSE.SUPERVISION"))
					notificationService.sendNotificationForSupervisionBill(billRequest);
			} catch (Exception e) {
				log.error("Exception while sending notification: " + e);
			}
		} else {
			bill.setStatus(Status.ACTIVE);
		}

		// Push bill (without details) to bill-create topic
		List<BillDetail> allDetails = bill.getBillDetails();
		bill.setBillDetails(null);
		expenseProducer.push(tenantId, config.getBillCreateTopic(), billRequest);
		bill.setBillDetails(allDetails);

		// Push each detail to bill-detail-create topic
		billDetailService.create(allDetails, tenantId);

		return BillResponse.builder()
				.bills(Arrays.asList(bill))
				.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, true))
				.build();
	}

	public BillResponse update(BillRequest billRequest) {
		Bill bill = billRequest.getBill();
		String tenantId = bill.getTenantId();
		RequestInfo requestInfo = billRequest.getRequestInfo();

		if ("PAYMENTS.BILL".equalsIgnoreCase(bill.getBusinessService()) && billRequest.getWorkflow() != null) {
			String action = billRequest.getWorkflow().getAction();
			boolean isNotificationAction = Actions.SEND_FOR_REVIEW.toString().equals(action)
					|| Actions.SEND_FOR_APPROVAL.toString().equals(action);
			String batchId = isNotificationAction ? UUID.randomUUID().toString() : null;
			updateBillStatus(bill, billRequest.getWorkflow(), requestInfo, batchId);
			if (isNotificationAction) {
				try {
					paymentWorkflowService.insertBillBatchEmailJob(
							tenantId, batchId, List.of(bill.getId()), action, requestInfo);
				} catch (Exception e) {
					log.error("Failed to insert BILL_BATCH_EMAIL for bill={} batchId={}: {}", bill.getId(), batchId, e.getMessage(), e);
				}
			}
		} else {
			List<Bill> billsFromSearch = validator.validateUpdateRequest(billRequest);
			enrichmentUtil.encrichBillWithUuidAndAuditForUpdate(billRequest, billsFromSearch);
			if (validator.isWorkflowActiveForBusinessService(bill.getBusinessService())) {
				State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBill(billRequest), billRequest);
				bill.setStatus(Status.fromValue(wfState.getApplicationStatus()));
			}
			billCacheService.put(bill);  // strips details automatically
			pushBillAndDetails(bill, tenantId, billRequest, config.getBillUpdateTopic());
		}

		try {
			if (bill.getBusinessService().equalsIgnoreCase("EXPENSE.PURCHASE"))
				notificationService.sendNotificationForPurchaseBill(billRequest);
		} catch (Exception e) {
			log.error("Exception while sending notification: " + e);
		}

		pushReportRegenerationTrigger(bill, requestInfo);

		return BillResponse.builder()
				.bills(Arrays.asList(bill))
				.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, true))
				.build();
	}

	public BillResponse search(BillSearchRequest billSearchRequest, boolean isWfEncrichRequired) {
		BillCriteria billCriteria = billSearchRequest.getBillCriteria();
		String tenantId = billCriteria.getTenantId();

		log.info("Validate billCriteria Parameters BillCriteria : " + billCriteria);
		validator.validateSearchRequest(billSearchRequest);

		log.info("Enrich billCriteria");
		enrichmentUtil.enrichSearchBillRequest(billSearchRequest);

		log.info("Search repository using billCriteria");
		// BillRepository.search() returns bills with details from DB (two-step SQL internally)
		List<Bill> bills = billRepository.search(billSearchRequest, false);
		Integer totalBills = billRepository.searchCount(billSearchRequest);
		billSearchRequest.getPagination().setTotalCount(totalBills);
		Map<String, Integer> statusCount = billRepository.searchStatusCount(billSearchRequest);

		if (bills != null && !bills.isEmpty()) {
			// Overlay bill cache for freshest bill-level status/amounts
			Set<String> billIds = bills.stream().map(Bill::getId).collect(Collectors.toSet());
			Map<String, Bill> cachedBills = billCacheService.getMultiple(tenantId, billIds);

			if (!cachedBills.isEmpty()) {
				bills = bills.stream()
						.map(dbBill -> {
							Bill cached = cachedBills.get(dbBill.getId());
							if (cached != null) {
								// Use cached bill fields but restore DB details
								List<BillDetail> dbDetails = dbBill.getBillDetails();
								cached.setBillDetails(dbDetails);
								return cached;
							}
							return dbBill;
						})
						.collect(Collectors.toList());
			}

			// Overlay detail cache for freshest detail-level status/data
			for (Bill bill : bills) {
				if (bill.getBillDetails() != null && !bill.getBillDetails().isEmpty()) {
					List<BillDetail> enriched = bill.getBillDetails().stream()
							.map(d -> billDetailCacheService.getDetail(bill.getId(), d.getId(), tenantId).orElse(d))
							.collect(Collectors.toList());
					bill.setBillDetails(enriched);

					// Restore amountBreakup from payableLineItems if not already populated
					if (bill.getAmountBreakup().isEmpty()) {
						for (BillDetail d : enriched) {
							if (d.getPayableLineItems() != null) {
								for (LineItem li : d.getPayableLineItems()) {
									if (li.getHeadCode() != null && li.getAmount() != null) {
										bill.getAmountBreakup().merge(li.getHeadCode(), li.getAmount(), BigDecimal::add);
									}
								}
							}
						}
					}
				}
			}
		}

		ResponseInfo responseInfo = responseInfoFactory
				.createResponseInfoFromRequestInfo(billSearchRequest.getRequestInfo(), true);

		if (!config.isHealthContextEnabled() && isWfEncrichRequired && bills != null && !bills.isEmpty())
			enrichWfstatusForBills(bills, tenantId, billSearchRequest.getRequestInfo());

		if (config.isHealthContextEnabled() && !StringUtils.isEmpty(billCriteria.getLocalityCode())) {
			bills.forEach(bill -> bill.setLocalityCode(billCriteria.getLocalityCode()));
		}

		return BillResponse.builder()
				.bills(bills)
				.pagination(billSearchRequest.getPagination())
				.statusCount(statusCount)
				.responseInfo(responseInfo)
				.build();
	}

	private void enrichWfstatusForBills(List<Bill> bills, String tenantId, RequestInfo requestInfo) {
		List<String> billNumbers = bills.stream().map(Bill::getBillNumber).collect(Collectors.toList());
		ProcessInstanceResponse response = workflowUtil.searchWorkflowForBusinessIds(billNumbers, tenantId, requestInfo);

		Map<String, String> busnessIdToWfStatus = response.getProcessInstances().stream().collect(Collectors
				.toMap(ProcessInstance::getBusinessId, processInstance -> processInstance.getState().getApplicationStatus()));
		for (Bill bill : bills) {
			bill.setWfStatus(busnessIdToWfStatus.get(bill.getBillNumber()));
		}
	}

	/**
	 * Pushes bill (without details) to bill topic and each detail to bill-detail topic.
	 */
	private void pushBillAndDetails(Bill bill, String tenantId, BillRequest billRequest, String billTopic) {
		List<BillDetail> allDetails = bill.getBillDetails();
		bill.setBillDetails(null);
		expenseProducer.push(tenantId, billTopic, billRequest);
		bill.setBillDetails(allDetails);
		if (allDetails != null && !allDetails.isEmpty()) {
			billDetailService.update(allDetails, tenantId);
		}
	}

	private void pushReportRegenerationTrigger(Bill bill, RequestInfo requestInfo) {
		int detailCount = (bill.getBillDetails() != null) ? bill.getBillDetails().size() : 0;
		ReportRegenerationTrigger trigger = ReportRegenerationTrigger.builder()
				.requestInfo(requestInfo)
				.billId(bill.getId())
				.tenantId(bill.getTenantId())
				.createdTime(System.currentTimeMillis())
				.numberOfBillDetails(detailCount)
				.forceRegenerate(true)
				.build();
		expenseProducer.push(bill.getTenantId(), config.getReportRegenerationTriggerTopic(), trigger);
		log.info("Pushed report regeneration trigger for billId: {}, billDetails: {}", bill.getId(), detailCount);
	}

	public BillResponse searchCalculatedBills(BillSearchRequest billSearchRequest, boolean isWfEncrichRequired) {
		BillResponse billResponse = search(billSearchRequest, isWfEncrichRequired);
		List<Bill> billsFromSearch = billResponse.getBills();
		if (billsFromSearch.isEmpty()) return billResponse;

		List<Bill> calculatedBills = new ArrayList<>();
		billsFromSearch.forEach(bill -> {
			boolean isBillBusinessServiceUpdated = bill.getBusinessService().equals(config.getBillBusinessService());
			if (isBillBusinessServiceUpdated) {
				calculatedBills.add(bill);
			} else {
				if (isBillCalculationComplete(bill, billSearchRequest.getRequestInfo())) {
					calculatedBills.add(bill);
				}
			}
		});

		billResponse.setBills(calculatedBills);
		return billResponse;
	}

	private Boolean isBillCalculationComplete(Bill bill, RequestInfo requestInfo) {
		String[] referenceIds = bill.getReferenceId().split("\\.");
		String projectReferenceId = referenceIds[referenceIds.length - 1];
		BillCalculationCriteria criteria = BillCalculationCriteria.builder()
				.tenantId(bill.getTenantId())
				.localityCode(bill.getLocalityCode())
				.referenceId(projectReferenceId)
				.build();
		BillCalculationRequest request = BillCalculationRequest.builder()
				.requestInfo(requestInfo)
				.criteria(criteria)
				.build();
		BillCalculationResponse response = calculatorUtil.getBills(request);
		return response.getStatusCode() == BillCalculationResponse.StatusCode.SUCCESSFUL;
	}

	public BulkBillStatusUpdateResponse bulkUpdateStatus(BulkBillStatusUpdateRequest bulkRequest) {
		RequestInfo requestInfo = bulkRequest.getRequestInfo();
		List<String> billIds = bulkRequest.getBillIds();
		String newStatus = bulkRequest.getStatus();
		org.egov.common.contract.models.Workflow workflow = bulkRequest.getWorkflow();

		List<Bill> successfulBills = new ArrayList<>();
		List<BulkUpdateError> errors = new ArrayList<>();

		List<BulkUpdateError> validationErrors = validator.validateBulkStatusUpdateRequest(bulkRequest);
		if (!validationErrors.isEmpty()) {
			return BulkBillStatusUpdateResponse.builder()
					.bills(new ArrayList<>())
					.errors(validationErrors)
					.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, false))
					.build();
		}

		List<Bill> billsFromSearch = validator.getBillsByIds(billIds, bulkRequest.getTenantId(), requestInfo);
		Map<String, Bill> billMap = billsFromSearch.stream()
				.collect(Collectors.toMap(Bill::getId, Function.identity()));

		String action = workflow.getAction();
		boolean isNotificationAction = Actions.SEND_FOR_REVIEW.toString().equals(action)
				|| Actions.SEND_FOR_APPROVAL.toString().equals(action);
		String batchId = isNotificationAction ? UUID.randomUUID().toString() : null;

		for (String billId : billIds) {
			try {
				Bill billFromSearch = billMap.get(billId);
				if (billFromSearch == null) {
					errors.add(BulkUpdateError.builder()
							.billId(billId)
							.code("EG_EXPENSE_INVALID_BILL")
							.message("Bill not found for id: " + billId)
							.build());
					continue;
				}

				Bill billToUpdate = Bill.builder()
						.id(billId)
						.tenantId(billFromSearch.getTenantId())
						.status(Status.fromValue(newStatus))
						.businessService(billFromSearch.getBusinessService())
						.billDetails(billFromSearch.getBillDetails())
						.billNumber(billFromSearch.getBillNumber())
						.billDate(billFromSearch.getBillDate())
						.dueDate(billFromSearch.getDueDate())
						.referenceId(billFromSearch.getReferenceId())
						.fromPeriod(billFromSearch.getFromPeriod())
						.toPeriod(billFromSearch.getToPeriod())
						.totalAmount(billFromSearch.getTotalAmount())
						.totalPaidAmount(billFromSearch.getTotalPaidAmount())
						.payer(billFromSearch.getPayer())
						.localityCode(billFromSearch.getLocalityCode())
						.paymentStatus(billFromSearch.getPaymentStatus())
						.additionalDetails(billFromSearch.getAdditionalDetails())
						.build();

				updateBillStatus(billToUpdate, workflow, requestInfo, batchId);
				successfulBills.add(billToUpdate);

			} catch (Exception e) {
				log.error("Error updating status for bill {}: {}", billId, e.getMessage());
				errors.add(BulkUpdateError.builder()
						.billId(billId)
						.code("EG_EXPENSE_BILL_STATUS_UPDATE_FAILED")
						.message(e.getMessage())
						.build());
			}
		}

		if (isNotificationAction && !successfulBills.isEmpty()) {
			List<String> successfulBillIds = successfulBills.stream().map(Bill::getId).collect(Collectors.toList());
			try {
				paymentWorkflowService.insertBillBatchEmailJob(
						bulkRequest.getTenantId(), batchId, successfulBillIds, action, requestInfo);
			} catch (Exception e) {
				log.error("Failed to insert BILL_BATCH_EMAIL coordinator for batchId={}: {}", batchId, e.getMessage(), e);
			}
		}

		boolean allFailed = !errors.isEmpty() && successfulBills.isEmpty();
		return BulkBillStatusUpdateResponse.builder()
				.bills(successfulBills)
				.errors(errors)
				.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, !allFailed))
				.build();
	}

	private void updateBillStatus(Bill bill, org.egov.common.contract.models.Workflow workflow, RequestInfo requestInfo) {
		updateBillStatus(bill, workflow, requestInfo, null);
	}

	private void updateBillStatus(Bill bill, org.egov.common.contract.models.Workflow workflow, RequestInfo requestInfo, String batchId) {
		String tenantId = bill.getTenantId();
		String action = workflow.getAction();

		BillRequest billRequest = BillRequest.builder()
				.requestInfo(requestInfo)
				.bill(bill)
				.workflow(workflow)
				.build();

		List<Bill> billsFromSearch = validator.validateUpdateRequest(billRequest);
		enrichmentUtil.encrichBillWithUuidAndAuditForUpdate(billRequest, billsFromSearch);
		bill.setStatus(billsFromSearch.get(0).getStatus());

		if (Actions.VERIFY.toString().equals(action)) {
			validator.validateNoBillDetailInVerificationInProgress(billsFromSearch.get(0));
			paymentWorkflowService.verifyBill(billRequest);
		} else if (Actions.IGNORE_ERRORS_AND_VERIFY.toString().equals(action)) {
			paymentWorkflowService.ignoreErrorsAndVerify(billRequest);
		} else if (Actions.SEND_FOR_REVIEW.toString().equals(action)) {
			paymentWorkflowService.sendForReview(billRequest, batchId);
		} else if (Actions.SEND_FOR_APPROVAL.toString().equals(action)) {
			paymentWorkflowService.sendForApproval(billRequest, batchId);
		} else if (Actions.PAYMENT_INITIATION.toString().equals(action)) {
			Status currentStatus = billsFromSearch.get(0).getStatus();
			if (currentStatus == Status.PAYMENT_FAILED || currentStatus == Status.PARTIALLY_PAID) {
				paymentWorkflowService.retryPayment(billRequest);
			} else {
				paymentWorkflowService.initiatePayment(billRequest);
			}
		} else {
			if (validator.isWorkflowActiveForBusinessService(bill.getBusinessService())) {
				State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBill(billRequest), billRequest);
				bill.setStatus(Status.fromValue(wfState.getApplicationStatus()));
			}
			billCacheService.put(bill);
			// Only bill status changed — no detail push needed
			List<BillDetail> details = bill.getBillDetails();
			bill.setBillDetails(null);
			expenseProducer.push(tenantId, config.getBillUpdateTopic(), billRequest);
			bill.setBillDetails(details);
		}
	}

	public BillDetailUpdateResponse partialUpdateBillDetails(BillDetailUpdateRequest request) {
		RequestInfo requestInfo = request.getRequestInfo();
		String tenantId = request.getTenantId();
		String updatedBy = requestInfo.getUserInfo().getUuid();

		BillValidator.BillDetailValidationResult validationResult = validator.validateBillDetailUpdateRequest(request);
		Bill billFromSearch = validationResult.bill;
		List<BillDetailUpdateError> warnings = validationResult.warnings;

		List<BillDetail> mergedDetails = enrichmentUtil.enrichPartialBillDetails(request, billFromSearch, updatedBy);

		Map<String, BillDetail> mergedMap = mergedDetails.stream()
				.collect(Collectors.toMap(BillDetail::getId, Function.identity()));

		List<BillDetail> allDetails = billFromSearch.getBillDetails().stream()
				.map(db -> mergedMap.getOrDefault(db.getId(), db))
				.collect(Collectors.toList());

		billFromSearch.setBillDetails(allDetails);

		BigDecimal recalculatedTotal = allDetails.stream()
				.filter(d -> d.getStatus() != Status.INACTIVE)
				.map(d -> d.getTotalAmount() != null ? d.getTotalAmount() : BigDecimal.ZERO)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		billFromSearch.setTotalAmount(recalculatedTotal);

		BillRequest billRequest = BillRequest.builder()
				.requestInfo(requestInfo)
				.bill(billFromSearch)
				.build();

		billCacheService.put(billFromSearch);  // strips details automatically
		// Push bill (no details) to bill-update
		List<BillDetail> savedDetails = billFromSearch.getBillDetails();
		billFromSearch.setBillDetails(null);
		expenseProducer.push(tenantId, config.getBillUpdateTopic(), billRequest);
		billFromSearch.setBillDetails(savedDetails);
		// Push merged details to bill-detail-update
		billDetailService.update(mergedDetails, tenantId);

		pushReportRegenerationTrigger(billFromSearch, requestInfo);

		return BillDetailUpdateResponse.builder()
				.billDetails(mergedDetails)
				.errors(Collections.emptyList())
				.warnings(warnings)
				.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, true))
				.build();
	}

	public BillResponse updateReportMeta(ReportMetaUpdateRequest request) {
		RequestInfo requestInfo = request.getRequestInfo();

		validator.validateReportMetaUpdateRequest(request);

		BillSearchRequest searchRequest = BillSearchRequest.builder()
				.requestInfo(requestInfo)
				.billCriteria(BillCriteria.builder()
						.ids(new HashSet<>(Collections.singletonList(request.getBillId())))
						.tenantId(request.getTenantId())
						.build())
				.pagination(Pagination.builder().build())
				.build();

		List<Bill> bills = billRepository.search(searchRequest, false);
		if (bills == null || bills.isEmpty())
			throw new org.egov.tracer.model.CustomException("EG_BILL_NOT_FOUND",
					"Bill not found for id: " + request.getBillId());

		Bill bill = bills.get(0);

		Map<String, Object> additionalDetailsMap;
		try {
			additionalDetailsMap = bill.getAdditionalDetails() != null
					? objectMapper.convertValue(bill.getAdditionalDetails(),
						objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class))
					: new HashMap<>();
		} catch (Exception e) {
			log.warn("Could not parse additionalDetails for bill {}, using empty map: {}", bill.getId(), e.getMessage());
			additionalDetailsMap = new HashMap<>();
		}

		additionalDetailsMap.put("reportDetails", request.getReportDetails());
		bill.setAdditionalDetails(additionalDetailsMap);

		long now = System.currentTimeMillis();
		if (bill.getAuditDetails() != null) {
			bill.getAuditDetails().setLastModifiedBy(
					requestInfo.getUserInfo() != null ? requestInfo.getUserInfo().getUuid() : "system");
			bill.getAuditDetails().setLastModifiedTime(now);
		}

		BillRequest billRequest = BillRequest.builder()
				.requestInfo(requestInfo)
				.bill(bill)
				.build();

		billCacheService.put(bill);
		// Push bill (no details) to bill-update
		List<BillDetail> savedDetails = bill.getBillDetails();
		bill.setBillDetails(null);
		expenseProducer.push(bill.getTenantId(), config.getBillUpdateTopic(), billRequest);
		bill.setBillDetails(savedDetails);

		log.info("Report metadata updated for billId: {}, status: {}",
				bill.getId(), request.getReportDetails().get("status"));

		return BillResponse.builder()
				.bills(Collections.singletonList(bill))
				.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, true))
				.build();
	}
}
