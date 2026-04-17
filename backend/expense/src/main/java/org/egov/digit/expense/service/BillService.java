package org.egov.digit.expense.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.auth.In;
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

	@Autowired
	public BillService(ExpenseProducer expenseProducer, Configuration config, BillValidator validator, WorkflowUtil workflowUtil, BillRepository billRepository, EnrichmentUtil enrichmentUtil, ResponseInfoFactory responseInfoFactory, NotificationService notificationService, CalculatorUtil calculatorUtil, PaymentWorkflowService paymentWorkflowService, ObjectMapper objectMapper) {
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
    }

	/**
	 * Validates the Bill Request and sends to repository for create
	 * 
	 * @param billRequest
	 * @return
	 */
	public BillResponse create(BillRequest billRequest) {

		Bill bill = billRequest.getBill();
		String tenantId = bill.getTenantId();
		RequestInfo requestInfo = billRequest.getRequestInfo();
		BillResponse response = null;

		validator.validateCreateRequest(billRequest);
		enrichmentUtil.encrichBillForCreate(billRequest);
		
		if (validator.isWorkflowActiveForBusinessService(bill.getBusinessService())) {

			State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBill(billRequest), billRequest);
			bill.setStatus(Status.fromValue(wfState.getApplicationStatus()));

			// Create PAYMENTS.BILLDETAILS process instances for each detail when PAYMENTS.BILL is created
			if (config.getBillBusinessService().equalsIgnoreCase(bill.getBusinessService())) {
				paymentWorkflowService.createBillDetailProcessInstances(billRequest);
			}

			try {
				if (billRequest.getBill().getBusinessService().equalsIgnoreCase("EXPENSE.SUPERVISION"))
					notificationService.sendNotificationForSupervisionBill(billRequest);
			}catch (Exception e){
				log.error("Exception while sending notification: " + e);
			}
		} else {
			bill.setStatus(Status.ACTIVE);
		}
		if (config.isBillBreakdownEnabled() && bill.getBillDetails().size() > config.getBillBreakdownSize()) {
			/* For bills with high number of bill details, break down of billDetails into batches is done.
			* Every bill will have a batch of billDetails; it will not create a insert error because of
			* ON CONFLICT DO NOTHING change in persister config */
			// produce full bill to different topic if indexing is required
			produceBillsBatchWise(billRequest, config.getBillCreateTopic());
		} else {
			expenseProducer.push(tenantId, config.getBillCreateTopic(), billRequest);
		}
		
		response = BillResponse.builder()
				.bills(Arrays.asList(billRequest.getBill()))
				.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo,true))
				.build();
		
		return response;
	}
	
	/**
	 * 
	 * @param billRequest
	 * @return
	 */
	public BillResponse update(BillRequest billRequest) {

		Bill bill = billRequest.getBill();
		String tenantId = bill.getTenantId();
		RequestInfo requestInfo = billRequest.getRequestInfo();
		BillResponse response = null;

		List<Bill> billsFromSearch = validator.validateUpdateRequest(billRequest);
		enrichmentUtil.encrichBillWithUuidAndAuditForUpdate(billRequest, billsFromSearch);

		// Detect system update flag set by health-expense-calculator after report generation.
		// If present, remove it before persistence and skip the report regeneration trigger (cycle prevention).
		boolean isSystemUpdate = isSystemUpdate(bill);
		if (isSystemUpdate) {
			removeSystemUpdateFlag(bill);
			log.info("System update detected for billId: {} — skipping report regeneration trigger", bill.getId());
		}

		if (validator.isWorkflowActiveForBusinessService(bill.getBusinessService())) {

			State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBill(billRequest), billRequest);
			bill.setStatus(Status.fromValue(wfState.getApplicationStatus()));
		}
		try {
			if (billRequest.getBill().getBusinessService().equalsIgnoreCase("EXPENSE.PURCHASE"))
				notificationService.sendNotificationForPurchaseBill(billRequest);
		}catch (Exception e){
			log.error("Exception while sending notification: " + e);
		}

		if (config.isBillBreakdownEnabled() && bill.getBillDetails().size() > config.getBillBreakdownSize()) {
			/* For bills with high number of bill details, break down of billDetails into batches is done.
			 Every bill will have a batch of billDetails */
			produceBillsBatchWise(billRequest, config.getBillUpdateTopic());
		} else {
			expenseProducer.push(tenantId, config.getBillUpdateTopic(), billRequest);
		}

		if (!isSystemUpdate) {
			pushReportRegenerationTrigger(bill, requestInfo);
		}

		response = BillResponse.builder()
				.bills(Arrays.asList(billRequest.getBill()))
				.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo,true))
				.build();

		return response;
	}
	
	/**
	 * method to search bills from DB based on bill search criteria
	 * @param billSearchRequest
	 * @return
	 */
	public BillResponse search(BillSearchRequest billSearchRequest, boolean isWfEncrichRequired) {
		
		BillCriteria billCriteria=billSearchRequest.getBillCriteria();

		log.info("Validate billCriteria Parameters BillCriteria : "+billCriteria);
		validator.validateSearchRequest(billSearchRequest);

		log.info("Enrich billCriteria");
		enrichmentUtil.enrichSearchBillRequest(billSearchRequest);

		log.info("Search repository using billCriteria");
		List<Bill> bills = billRepository.search(billSearchRequest, false);
		Integer totalBills = billRepository.searchCount(billSearchRequest);
		billSearchRequest.getPagination().setTotalCount(totalBills);

		ResponseInfo responseInfo = responseInfoFactory.
		createResponseInfoFromRequestInfo(billSearchRequest.getRequestInfo(),true);

		if (!config.isHealthContextEnabled() && isWfEncrichRequired  && bills != null && !bills.isEmpty())
			enrichWfstatusForBills(bills, billCriteria.getTenantId(), billSearchRequest.getRequestInfo());

		if (config.isHealthContextEnabled() && !StringUtils.isEmpty(billCriteria.getLocalityCode())) {
			bills.stream().forEach(bill -> bill.setLocalityCode(billCriteria.getLocalityCode()));
		}

		return BillResponse.builder()
				.bills(bills)
				.pagination(billSearchRequest.getPagination())
				.responseInfo(responseInfo)
				.build();
	}

	private void enrichWfstatusForBills(List<Bill> bills, String tenantId, RequestInfo requestInfo) {

		List<String> billNumbers = bills.stream().map(Bill::getBillNumber).collect(Collectors.toList());
		ProcessInstanceResponse response = workflowUtil.searchWorkflowForBusinessIds(billNumbers, tenantId,
				requestInfo);

		Map<String, String> busnessIdToWfStatus = response.getProcessInstances().stream().collect(Collectors
				.toMap(ProcessInstance::getBusinessId, processInstance -> processInstance.getState().getApplicationStatus()));
		for (Bill bill : bills) {
			bill.setWfStatus(busnessIdToWfStatus.get(bill.getBillNumber()));
		}
	}
	/**
	 * Breakdown the billDetails into batches and push to kafka. This is needed
	 * to avoid large payload in kafka.
	 *
	 * @param billRequest The bill request object
	 */
	private void produceBillsBatchWise(BillRequest billRequest, String topic) {
		Bill bill = billRequest.getBill();
		String tenantId = bill.getTenantId();
		List<BillDetail> allBillDetails = new ArrayList<>(bill.getBillDetails());
		// Breakdown the billDetails into batches and push to kafka
		for (int i = 0; i < allBillDetails.size(); i += config.getBillBreakdownSize()) {
			// Breakdown bill details into batches and push to kafka topic
			List<BillDetail> currBatchBillDetails = allBillDetails.subList(i, Math.min(i + config.getBillBreakdownSize(), allBillDetails.size()));
			bill.setBillDetails(currBatchBillDetails);
			expenseProducer.push(tenantId, topic, billRequest);
		}
		bill.setBillDetails(allBillDetails);
		log.info("All bill details pushed to kafka");
	}

	/**
	 * Returns true if this bill update was made by the health-expense-calculator
	 * after report generation. The calculator sets _systemUpdate=true to signal
	 * that report regeneration must NOT be re-triggered (cycle prevention).
	 */
	private boolean isSystemUpdate(Bill bill) {
		if (bill.getAdditionalDetails() == null) return false;
		try {
			Map<?, ?> ad = objectMapper.convertValue(bill.getAdditionalDetails(), Map.class);
			return Boolean.TRUE.equals(ad.get("_systemUpdate"));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Removes the _systemUpdate flag from additionalDetails so it is never persisted to DB.
	 */
	private void removeSystemUpdateFlag(Bill bill) {
		if (bill.getAdditionalDetails() == null) return;
		try {
			Map<String, Object> ad = objectMapper.convertValue(
					bill.getAdditionalDetails(),
					objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class));
			ad.remove("_systemUpdate");
			bill.setAdditionalDetails(ad);
		} catch (Exception e) {
			log.warn("Failed to remove _systemUpdate flag from bill {}: {}", bill.getId(), e.getMessage());
		}
	}

	/**
	 * Publishes a report regeneration trigger to health-expense-calculator.
	 * forceRegenerate=true bypasses the calculator's Redis dedup cache so the report is refreshed.
	 */
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
		log.info("Pushed report regeneration trigger for billId: {}, billDetails: {}",
				bill.getId(), detailCount);
	}

	public BillResponse searchCalculatedBills(BillSearchRequest billSearchRequest, boolean isWfEncrichRequired) {
		BillResponse billResponse = search(billSearchRequest,isWfEncrichRequired);
		List<Bill> billsFromSearch = billResponse.getBills();
		if(billsFromSearch.isEmpty()){
			return billResponse;
		}
		List<Bill> calculatedBills = new ArrayList<>();
		billsFromSearch.stream().forEach(bill -> {
			boolean isBillBusinessServiceUpdated = bill.getBusinessService().equals(config.getBillBusinessService());

			if(isBillBusinessServiceUpdated){
				calculatedBills.add(bill);
			} else {
				boolean isBillCalculationComplete = isBillCalculationComplete(bill, billSearchRequest.getRequestInfo());
				if (isBillCalculationComplete){
					calculatedBills.add(bill);
				}
			}
		});

		billResponse.setBills(calculatedBills);
		return billResponse;
	}
	private Boolean isBillCalculationComplete(Bill bill, RequestInfo requestInfo){
		Boolean isBillCalculationComplete = false;
		String[] referenceIds = bill.getReferenceId().split("\\.");
		String projectReferenceId = referenceIds[referenceIds.length - 1];
		BillCalculationCriteria criteria =
				BillCalculationCriteria
						.builder()
						.tenantId(bill.getTenantId())
						.localityCode(bill.getLocalityCode())
						.referenceId(projectReferenceId)
						.build();

		BillCalculationRequest request =
				BillCalculationRequest
						.builder()
						.requestInfo(requestInfo)
						.criteria(criteria)
						.build();
		BillCalculationResponse response = calculatorUtil.getBills(request);
		if (response.getStatusCode() == BillCalculationResponse.StatusCode.SUCCESSFUL){
			isBillCalculationComplete = true;
		}
		return isBillCalculationComplete;
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
					.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, true))
					.build();
		}

		List<Bill> billsFromSearch = validator.getBillsByIds(billIds, bulkRequest.getTenantId(), requestInfo);
		Map<String, Bill> billMap = billsFromSearch.stream()
				.collect(Collectors.toMap(Bill::getId, Function.identity()));

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

				updateBillStatus(billToUpdate, workflow, requestInfo);

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

		return BulkBillStatusUpdateResponse.builder()
				.bills(successfulBills)
				.errors(errors)
				.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, true))
				.build();
	}

	private void updateBillStatus(Bill bill, org.egov.common.contract.models.Workflow workflow, RequestInfo requestInfo) {
		String tenantId = bill.getTenantId();
		String action = workflow.getAction();

		BillRequest billRequest = BillRequest.builder()
				.requestInfo(requestInfo)
				.bill(bill)
				.workflow(workflow)
				.build();

		List<Bill> billsFromSearch = validator.validateUpdateRequest(billRequest);
		enrichmentUtil.encrichBillWithUuidAndAuditForUpdate(billRequest, billsFromSearch);

		// Reset status to current DB state so PaymentWorkflowService state validation passes.
		// (bulkUpdateStatus sets bill.status = target newStatus from request, but PW service
		//  validates the *current* state before transitioning.)
		bill.setStatus(billsFromSearch.get(0).getStatus());

		if (Actions.VERIFY.toString().equals(action)) {
			paymentWorkflowService.verifyBill(billRequest);
		} else if (Actions.IGNORE_ERRORS_AND_VERIFY.toString().equals(action)) {
			paymentWorkflowService.ignoreErrorsAndVerify(billRequest);
		} else if (Actions.SEND_FOR_REVIEW.toString().equals(action)) {
			paymentWorkflowService.sendForReview(billRequest);
		} else if (Actions.SEND_FOR_APPROVAL.toString().equals(action)) {
			paymentWorkflowService.sendForApproval(billRequest);
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
			expenseProducer.push(tenantId, config.getBillUpdateTopic(), billRequest);
		}
	}

	/**
	 * Partially updates one or more bill details under a single bill.
	 * Only the fields provided in each {@link PartialBillDetail} are updated;
	 * null fields are preserved from the database.
	 * Immutable fields (id, tenantId, billId, referenceId) are always taken from DB.
	 */
	public BillDetailUpdateResponse partialUpdateBillDetails(BillDetailUpdateRequest request) {
		RequestInfo requestInfo = request.getRequestInfo();
		String tenantId = request.getTenantId();
		String updatedBy = requestInfo.getUserInfo().getUuid();

		// 1. Validate — strips blocked fields in-place, collects warnings
		BillValidator.BillDetailValidationResult validationResult = validator.validateBillDetailUpdateRequest(request);
		Bill billFromSearch = validationResult.bill;
		List<BillDetailUpdateError> warnings = validationResult.warnings;

		// 2. Enrich — applies full null protection, returns only the requested details (merged)
		List<BillDetail> mergedDetails = enrichmentUtil.enrichPartialBillDetails(request, billFromSearch, updatedBy);

		// 3. Rebuild full bill for Kafka: replace only the updated details in the DB snapshot;
		//    non-requested details are passed through unchanged to avoid clobbering the bill header.
		Map<String, BillDetail> mergedMap = mergedDetails.stream()
				.collect(Collectors.toMap(BillDetail::getId, Function.identity()));

		List<BillDetail> allDetails = billFromSearch.getBillDetails().stream()
				.map(db -> mergedMap.getOrDefault(db.getId(), db))
				.collect(Collectors.toList());

		billFromSearch.setBillDetails(allDetails);

		// 4. Push to the existing bill update topic — no persister changes required
		BillRequest billRequest = BillRequest.builder()
				.requestInfo(requestInfo)
				.bill(billFromSearch)
				.build();

		if (config.isBillBreakdownEnabled() && allDetails.size() > config.getBillBreakdownSize()) {
			produceBillsBatchWise(billRequest, config.getBillUpdateTopic());
		} else {
			expenseProducer.push(tenantId, config.getBillUpdateTopic(), billRequest);
		}

		// Always trigger report regeneration — health-expense-calculator never calls this endpoint
		pushReportRegenerationTrigger(billFromSearch, requestInfo);

		return BillDetailUpdateResponse.builder()
				.billDetails(mergedDetails)
				.errors(Collections.emptyList())
				.warnings(warnings)
				.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, true))
				.build();
	}
}
