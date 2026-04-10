package org.egov.digit.expense.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.common.contract.workflow.State;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.repository.SchedulerJobRepository;
import org.egov.digit.expense.repository.TaskRepository;
import org.egov.digit.expense.service.scheduler.SchedulerJobRegistry;
import org.egov.digit.expense.util.*;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.ResponseStatus;
import org.egov.digit.expense.web.models.enums.SchedulerJobStatus;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.digit.expense.web.validators.BillValidator;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.egov.digit.expense.config.Constants.ERROR;
import static org.egov.digit.expense.config.Constants.EXCEPTION;

@Service
@Slf4j
public class MTNService {

    private final ExpenseProducer expenseProducer;

    private final Configuration config;

    private final BillValidator validator;

    private final WorkflowUtil workflowUtil;

    private final BillRepository billRepository;

    private final EnrichmentUtil enrichmentUtil;

    private final ResponseInfoFactory responseInfoFactory;

    private final IndividualUtil individualUtil;

    private final TaskRepository taskRepository;

    private final MTNUtil mtnUtil;

    private final SchedulerJobRepository schedulerJobRepository;

    private final SchedulerJobRegistry schedulerJobRegistry;

    private final ObjectMapper objectMapper;

    @Autowired
    public MTNService(ExpenseProducer expenseProducer, Configuration config, BillValidator validator,
                      WorkflowUtil workflowUtil, BillRepository billRepository, EnrichmentUtil enrichmentUtil,
                      ResponseInfoFactory responseInfoFactory, IndividualUtil individualUtil,
                      TaskRepository taskRepository, MTNUtil mtnUtil,
                      SchedulerJobRepository schedulerJobRepository, SchedulerJobRegistry schedulerJobRegistry,
                      ObjectMapper objectMapper) {
        this.expenseProducer = expenseProducer;
        this.config = config;
        this.validator = validator;
        this.workflowUtil = workflowUtil;
        this.billRepository = billRepository;
        this.enrichmentUtil = enrichmentUtil;
        this.responseInfoFactory = responseInfoFactory;
        this.individualUtil = individualUtil;
        this.taskRepository = taskRepository;
        this.mtnUtil = mtnUtil;
        this.schedulerJobRepository = schedulerJobRepository;
        this.schedulerJobRegistry = schedulerJobRegistry;
        this.objectMapper = objectMapper;
    }

    private Task fetchOrCreateTask(BillTaskRequest billTaskRequest, Task.Type type) {
        Bill billFromRequest = billTaskRequest.getBill();
        Task task = Task
                .builder()
                .billId(billFromRequest.getId())
                .type(type)
                .status(Status.IN_PROGRESS)
                .build();
        Task taskDb = taskRepository.searchTask(task);
        if (taskDb != null) {
            return taskDb;
        }

        task.setId(UUID.randomUUID().toString());
        task.setTenantId(billFromRequest.getTenantId());

        task.setAuditDetails(billTaskRequest.getBill().getAuditDetails());

        TaskRequest taskRequest =
                TaskRequest.builder()
                        .task(task)
                        .bill(billFromRequest)
                        .requestInfo(billTaskRequest.getRequestInfo())
                        .build();

        expenseProducer.push(billFromRequest.getTenantId(), config.getBillTaskTopic(), taskRequest);

        return task;
    }

    private Bill getBillfromSearch(Bill billFromRequest, RequestInfo requestInfo) {
        BillRequest billRequest =
                BillRequest.builder()
                        .requestInfo(requestInfo)
                        .bill(billFromRequest)
                        .build();
        List<Bill> billsFromSearch = getBills(billRequest, false);
        Bill billFromSearch = billsFromSearch.get(0);
        if (null == billFromRequest.getPayer()) {
            billFromRequest.setPayer(billFromSearch.getPayer());
        }
        if (null == billFromRequest.getBillDetails()) {
            billFromRequest.setBillDetails(billFromSearch.getBillDetails());
        }
        enrichmentUtil.encrichBillWithUuidAndAuditForUpdate(billRequest, billsFromSearch);
        return billFromSearch;
    }

    public void executeTask(TaskRequest taskRequest) {
        Task task = taskRequest.getTask();
        if (task.getType() == Task.Type.Verify) {
            verify(taskRequest);
        } else if (task.getType() == Task.Type.Transfer) {
            transfer(taskRequest);
        }
    }

    public BillTaskResponse verify(BillTaskRequest billTaskRequest) {

        Task task = fetchOrCreateTask(billTaskRequest, Task.Type.Verify);

        ResponseInfo responseInfo = responseInfoFactory.
                createResponseInfoFromRequestInfo(billTaskRequest.getRequestInfo(), true);

        return BillTaskResponse.builder()
                .responseInfo(responseInfo)
                .taskId(task.getId())
                .build();
    }

    public void verify(TaskRequest taskRequest) {

        Task task = taskRequest.getTask();
        Bill billFromRequest = taskRequest.getBill();
        Bill billFromSearch = getBillfromSearch(billFromRequest, taskRequest.getRequestInfo());

        Map<String, BillDetail> billDetailsToBeUpdatedById = getBillDetailsToBeUpdated(billFromRequest, billFromSearch);


        for (BillDetail billDetail : billDetailsToBeUpdatedById.values()) {
            if (billDetail == null) {
                log.error("BillDetail from search is null for one of the requested IDs in bill number: {}. Skipping.", billFromSearch.getBillNumber());
                continue;
            }
            if (billDetail.getStatus() == Status.PENDING_VERIFICATION ||
                    billDetail.getStatus() == Status.VERIFICATION_FAILED) {
                IndividualDetails individualDetails = individualUtil.getIndividualDetails(taskRequest.getRequestInfo(), billFromSearch.getTenantId(), billDetail.getPayee().getIdentifier());
                TaskDetails taskDetails = TaskDetails.builder()
                        .id(UUID.randomUUID().toString())
                        .taskId(task.getId())
                        .billId(billFromSearch.getId())
                        .billDetailsId(billDetail.getId())
                        .payeeId(billDetail.getPayee().getId())
                        .status(Status.IN_PROGRESS)
                        .tenantId(billFromSearch.getTenantId())
                        .auditDetails(billFromSearch.getAuditDetails())
                        .referenceId(individualDetails.getPhoneNumber())
                        .build();
                boolean updateBillDetailWorkflow = true;

                // ── WF Step 1: VERIFY → VERIFICATION_IN_PROGRESS (unconditional) ──
                // setBillDetailStatus catches HttpClientErrorException internally; check status to detect failure.
                Workflow verifyWorkflow = Workflow.builder()
                        .action(Actions.VERIFY.toString())
                        .build();
                setBillDetailStatus(billDetail, verifyWorkflow, taskRequest.getRequestInfo(), updateBillDetailWorkflow);

                // Guard: Step 2 is only valid from VERIFICATION_IN_PROGRESS.
                // If Step 1 failed (exception swallowed internally), status is unchanged — skip to next BillDetail.
                if (billDetail.getStatus() != Status.VERIFICATION_IN_PROGRESS) {
                    log.error("WF Step 1 (VERIFY) did not reach VERIFICATION_IN_PROGRESS for billDetail Id: {}, bill number: {}, current status: {}. Skipping Step 2.",
                            billDetail.getId(), billFromSearch.getBillNumber(), billDetail.getStatus());
                    continue;
                }

                // ── Account check — sets failure reason on taskDetails BEFORE Kafka push ──
                boolean verificationSucceeded = false;
                try {
                    boolean isActive = mtnUtil.isMsisdnActive(individualDetails.getPhoneNumber());
                    if (isActive) {
                        verificationSucceeded = true;
                    } else {
                        taskDetails.setReasonForFailure("MTN_ACCOUNT_INACTIVE_" + EXCEPTION);
                        taskDetails.setResponseMessage("Account is not active");
                    }
                } catch (CustomException e) {
                    log.error("Exception while verifying MSISDN : {}", individualDetails.getPhoneNumber(), e);
                    taskDetails.setResponseMessage(e.getMessage());
                    taskDetails.setReasonForFailure(e.getCode());
                }

                // ── Kafka push — failure reason already set (unchanged from original intent) ──
                taskDetails.setStatus(Status.DONE);
                expenseProducer.push(billFromSearch.getTenantId(), config.getBillTaskDetailsTopic(), taskDetails);

                // ── additionalDetails enrichment (unchanged — ErrorDetails reads from taskDetails) ──
                Object additionalDetailsObj = billDetail.getAdditionalDetails();
                Map<String, Object> additionalDetails;

                try {
                    additionalDetails = new ObjectMapper().convertValue(
                            additionalDetailsObj,
                            new TypeReference<>() {
                            }
                    );
                } catch (IllegalArgumentException e) {
                    additionalDetails = new HashMap<>();
                }

                ErrorDetails errorDetails = ErrorDetails.builder()
                        .reasonForFailure(taskDetails.getReasonForFailure())
                        .responseMessage(taskDetails.getResponseMessage())
                        .response(taskDetails.getAdditionalDetails()).build();
                additionalDetails.put("errorDetails", errorDetails);
                billDetail.setAdditionalDetails(additionalDetails);

                // ── WF Step 2: VERIFICATION_SUCCESS or FAILED from VERIFICATION_IN_PROGRESS ──
                // setBillDetailStatus catches HttpClientErrorException internally.
                // If Step 2 fails, billDetail.getStatus() stays VERIFICATION_IN_PROGRESS — not retried by
                // automated flow; requires manual operator action (IGNORE_ERRORS_AND_VERIFY).
                if (verificationSucceeded) {
                    Workflow successWorkflow = Workflow.builder()
                            .action(Actions.VERIFICATION_SUCCESS.toString())
                            .build();
                    setBillDetailStatus(billDetail, successWorkflow, taskRequest.getRequestInfo(), updateBillDetailWorkflow);
                    if (billDetail.getStatus() == Status.VERIFICATION_IN_PROGRESS) {
                        log.error("WF Step 2 (VERIFICATION_SUCCESS) failed for billDetail Id: {}, bill number: {}. BillDetail stuck at VERIFICATION_IN_PROGRESS — manual intervention required.",
                                billDetail.getId(), billFromSearch.getBillNumber());
                    }
                } else {
                    Workflow failedWorkflow = Workflow.builder()
                            .action(Actions.FAILED.toString())
                            .build();
                    setBillDetailStatus(billDetail, failedWorkflow, taskRequest.getRequestInfo(), updateBillDetailWorkflow);
                    if (billDetail.getStatus() == Status.VERIFICATION_IN_PROGRESS) {
                        log.error("WF Step 2 (FAILED) failed for billDetail Id: {}, bill number: {}. BillDetail stuck at VERIFICATION_IN_PROGRESS — manual intervention required.",
                                billDetail.getId(), billFromSearch.getBillNumber());
                    }
                }
            }
        }
        if (billFromSearch.getStatus() == Status.PENDING_VERIFICATION
                || billFromSearch.getStatus() == Status.PARTIALLY_VERIFIED) {

            // Count verified BillDetails (unchanged filter logic)
            List<BillDetail> verifiedBillDetails = billFromSearch.getBillDetails()
                    .stream()
                    .filter(billDetail -> billDetail.getStatus() == Status.VERIFIED
                            || billDetail.getStatus() == Status.PAID)
                    .collect(Collectors.toList());

            if (!verifiedBillDetails.isEmpty()) {
                // Step 1: VERIFY → VERIFICATION_IN_PROGRESS
                // updateBillWfStatus catches HttpClientErrorException internally; check status to detect failure.
                Workflow verifyWorkflow = Workflow.builder()
                        .action(Actions.VERIFY.toString())
                        .build();
                BillRequest verifyBillRequest = BillRequest.builder()
                        .bill(billFromSearch)
                        .requestInfo(taskRequest.getRequestInfo())
                        .workflow(verifyWorkflow)
                        .build();
                updateBillWfStatus(verifyBillRequest, true);

                // Guard: Step 2 is only valid from VERIFICATION_IN_PROGRESS.
                // If Step 1 failed (exception swallowed internally), status is unchanged — skip Step 2.
                if (billFromSearch.getStatus() != Status.VERIFICATION_IN_PROGRESS) {
                    log.error("Bill WF Step 1 (VERIFY) did not reach VERIFICATION_IN_PROGRESS for bill number: {}, task id: {}. Current status: {}. Skipping Step 2.",
                            billFromSearch.getBillNumber(), task.getId(), billFromSearch.getStatus());
                } else {
                    // Step 2: FULLY_VERIFY or PARTIALLY_VERIFY from VERIFICATION_IN_PROGRESS
                    // verifiedBillDetails was computed post-BillDetail-loop — reflects current BillDetail statuses.
                    // billFromSearch.getStatus() is VERIFICATION_IN_PROGRESS — do NOT use it for branching.
                    Workflow aggregateWorkflow = Workflow.builder().build();
                    if (verifiedBillDetails.size() == billFromSearch.getBillDetails().size()) {
                        aggregateWorkflow.setAction(Actions.FULLY_VERIFY.toString());
                    } else {
                        aggregateWorkflow.setAction(Actions.PARTIALLY_VERIFY.toString());
                    }
                    BillRequest aggregateBillRequest = BillRequest.builder()
                            .bill(billFromSearch)
                            .requestInfo(taskRequest.getRequestInfo())
                            .workflow(aggregateWorkflow)
                            .build();
                    updateBillWfStatus(aggregateBillRequest, true);
                }
            } else {
                log.info("No verified BillDetails — skipping bill workflow change for bill number: {}, task id: {}",
                        billFromSearch.getBillNumber(), task.getId());
            }
        }
        task.setStatus(Status.DONE);
        expenseProducer.push(billFromSearch.getTenantId(), config.getTaskUpdateTopic(), task);
    }

    private List<Bill> getBills(BillRequest billRequest, Boolean isCreate) {

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

    public StatusResponse getTaskStatus(StatusRequest statusRequest) {

        Task taskFromRequest = statusRequest.getTask();
        if (null == taskFromRequest.getId()
                && (null == taskFromRequest.getBillId()
                || null == taskFromRequest.getType())) {
            throw new CustomException("TASK_SEARCH_" + EXCEPTION, "either task id or bill id with type is required for search");
        }
        Task taskFromSearch = taskRepository.searchTask(taskFromRequest);


        ResponseInfo responseInfo = responseInfoFactory.
                createResponseInfoFromRequestInfo(statusRequest.getRequestInfo(), true);

        return StatusResponse
                .builder()
                .responseInfo(responseInfo)
                .task(taskFromSearch)
                .build();

    }

    public MtnBalanceResponse getMtnBalanceResponse(MtnBalanceRequest mtnBalanceRequest) {
        try {
            MtnBalance mtnBalance = mtnUtil.getTotalAmountBalance();
            ResponseInfo responseInfo = responseInfoFactory.
                    createResponseInfoFromRequestInfo(mtnBalanceRequest.getRequestInfo(), true);

            return MtnBalanceResponse
                    .builder()
                    .responseInfo(responseInfo)
                    .mtnBalance(mtnBalance)
                    .build();
        } catch (CustomException e) {
            log.error("Error while retrieving balance", e);
            throw new CustomException(e.getCode(), e.getMessage());
        } catch (HttpClientErrorException e) {
            log.error("Error from MTN service", e);
            throw new CustomException("MTN_SERVICE_" + EXCEPTION, e.getMessage());
        } catch (Exception e) {
            log.error("Error while retrieving balance", e);
            throw new CustomException("MTN_ACCOUNT_BALANCE_" + EXCEPTION, e.getMessage());
        }

    }

    public BillTaskResponse transfer(@Valid BillTaskRequest billTaskRequest) {
        Task task = fetchOrCreateTask(billTaskRequest, Task.Type.Transfer);

        ResponseInfo responseInfo = responseInfoFactory.
                createResponseInfoFromRequestInfo(billTaskRequest.getRequestInfo(), true);

        return BillTaskResponse.builder()
                .responseInfo(responseInfo)
                .taskId(task.getId())
                .build();
    }

    public void transfer(TaskRequest taskRequest) {

        Task task = taskRequest.getTask();
        Bill billFromRequest = taskRequest.getBill();
        Bill billFromSearch = getBillfromSearch(billFromRequest, taskRequest.getRequestInfo());

        Map<String, BillDetail> billDetailsToBeupdatedById = getBillDetailsToBeUpdated(billFromRequest, billFromSearch);


        for (BillDetail billDetail : billDetailsToBeupdatedById.values()) {
            if (billDetail == null) {
                log.error("BillDetail from search is null for one of the requested IDs in bill number: {}. Skipping.", billFromSearch.getBillNumber());
                continue;
            }
            if (billDetail.getStatus() == Status.PAYMENT_IN_PROGRESS) {
                IndividualDetails individualDetails = individualUtil.getIndividualDetails(taskRequest.getRequestInfo(), billFromSearch.getTenantId(), billDetail.getPayee().getIdentifier());
                TaskDetails taskDetails = TaskDetails.builder()
                        .id(UUID.randomUUID().toString())
                        .taskId(task.getId())
                        .referenceId(individualDetails.getPhoneNumber())
                        .billId(billFromSearch.getId())
                        .billDetailsId(billDetail.getId())
                        .payeeId(billDetail.getPayee().getId())
                        .status(Status.IN_PROGRESS)
                        .tenantId(billFromSearch.getTenantId())
                        .auditDetails(billFromSearch.getAuditDetails())
                        .build();

                //ZERO Amt check
                if (billDetail.getTotalAmount().compareTo(BigDecimal.ZERO) == 0) {
                    taskDetails.setResponseMessage("Payment couldn't be processed as total amount is 0.");
                    taskDetails.setReasonForFailure("TOTAL_AMOUNT_ZERO_" + EXCEPTION);
                    expenseProducer.push(billFromSearch.getTenantId(), config.getBillTaskDetailsTopic(), taskDetails);
                    log.info("payment couldn't be processed for bill detail id {} as total amount is 0", billDetail.getId());
                } else {
                    PaymentTransferRequest paymentTransferRequest = createPaymentTransferRequest(billDetail, individualDetails.getPhoneNumber());
                    try {
                        mtnUtil.transferIfAccountIsActive(paymentTransferRequest, taskDetails.getId());
                    } catch (CustomException e) {
                        taskDetails.setResponseMessage(e.getMessage());
                        taskDetails.setReasonForFailure(e.getCode());
                    }
                    expenseProducer.push(billFromSearch.getTenantId(), config.getBillTaskDetailsTopic(), taskDetails);
                }
            }
        }
        task.setAdditionalDetails(taskRequest.getRequestInfo());
        AuditDetails auditDetails = task.getAuditDetails();
        auditDetails.setLastModifiedTime(System.currentTimeMillis());
        expenseProducer.push(billFromSearch.getTenantId(), config.getTaskUpdateTopic(), task);

        // Register a scheduler job to poll MTN for transfer status (replaces Kafka delayed retry)
        insertSchedulerJob(task, billFromSearch.getTenantId(), taskRequest.getRequestInfo());
    }

    /**
     * Creates a {@code TASK_STATUS_CHECK} scheduler job for the given transfer task.
     * The generic scheduler will poll MTN transfer status using exponential backoff
     * until the task is resolved or max attempts are exceeded.
     */
    private void insertSchedulerJob(Task task, String tenantId, RequestInfo requestInfo) {
        try {
            long now = System.currentTimeMillis();
            SchedulerJob schedulerJob = SchedulerJob.builder()
                    .id(UUID.randomUUID().toString())
                    .tenantId(tenantId)
                    .jobType(SchedulerJobType.TASK_STATUS_CHECK)
                    .referenceId(task.getId())
                    .schedulerStatus(SchedulerJobStatus.PENDING)
                    .nextCheckAt(null)
                    .attemptCount(0)
                    .maxAttempts(config.getSchedulerMaxAttempts())
                    .context(requestInfo)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            schedulerJobRepository.insert(schedulerJob);
            schedulerJobRegistry.register(tenantId);
            log.info("Scheduler job created for task {} tenant {}", task.getId(), tenantId);
        } catch (Exception e) {
            log.error("Failed to insert scheduler job for task {} — status polling will not run", task.getId(), e);
        }
    }

    private PaymentTransferRequest createPaymentTransferRequest(BillDetail billDetail, String partyId) {

        BigDecimal totalAmount = billDetail.getTotalAmount();

        //additionalAmount
        BigDecimal additionalAmount = totalAmount
                .multiply(config.getAdditionalAmountPercent())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        //finalAmount
        BigDecimal finalAmount = totalAmount.add(additionalAmount);

        return PaymentTransferRequest.builder()
                .amount(finalAmount.setScale(0, RoundingMode.HALF_UP).toPlainString())
                .currency(config.getPaymentCurrency())
                .externalId(billDetail.getId())
                .payee(PaymentTransferRequest.Payee.builder()
                        .partyId(config.getPhoneCodePrefix() + partyId)
                        .partyIdType(config.getPartyIdType())
                        .build())
                .build();


    }

    public TaskDetailsResponse getTaskDetails(TaskDetailsRequest taskDetailsRequest) {
        String tenantId = taskDetailsRequest.getRequestInfo().getUserInfo().getTenantId();
        List<TaskDetails> taskDetails = new ArrayList<>();
        if (taskDetailsRequest.getBillDetailsId() != null) {
            taskDetails.add(taskRepository.searchTaskDetails(taskDetailsRequest));
        } else {
            taskDetails = taskRepository.searchTaskDetailsByTaskId(taskDetailsRequest.getTaskId(), tenantId);
        }
        ResponseInfo responseInfo = responseInfoFactory.
                createResponseInfoFromRequestInfo(taskDetailsRequest.getRequestInfo(), true);

        return TaskDetailsResponse.builder()
                .responseInfo(responseInfo)
                .taskDetails(taskDetails)
                .build();
    }

    private void updateBillWfStatus(BillRequest billRequest, boolean isWorkflowChange) {
        Bill bill = billRequest.getBill();
        try {
            if (isWorkflowChange &&
                    validator.isWorkflowActiveForBusinessService(bill.getBusinessService())) {
                State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBill(billRequest), billRequest);
                bill.setStatus(Status.fromValue(wfState.getApplicationStatus()));
            }
        } catch (HttpClientErrorException e) {
            log.error("Error in updating workflow state change for bill number : {} from status: {}", bill.getBillNumber(), bill.getStatus(), e);
        }
        expenseProducer.push(bill.getTenantId(), config.getBillUpdateTopic(), billRequest);
    }

    private void setBillDetailStatus(BillDetail billDetail, Workflow workflow, RequestInfo requestInfo, boolean isWorkflowUpdate) {
        if (validator.isWorkflowActiveForBusinessService(config.getBillDetailBusinessService()) && isWorkflowUpdate) {
            BillDetailRequest billDetailRequest = BillDetailRequest.builder()
                    .billDetail(billDetail)
                    .businessService(config.getBillDetailBusinessService())
                    .workflow(workflow)
                    .requestInfo(requestInfo)
                    .build();
            try {
                State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBillDetail(billDetailRequest), billDetailRequest);
                billDetail.setStatus(Status.fromValue(wfState.getApplicationStatus()));
            } catch (HttpClientErrorException e) {
                log.error("Error in updating workflow state change for billDetail Id: {}, from status: {} to action: {}"
                        , billDetail.getId(), billDetail.getStatus(), workflow.getAction(), e);
            }
        }
    }

    public BillDetailResponse updateBillDetailStatus(BillRequest billRequest) {


        Bill billFromRequest = billRequest.getBill();

        Bill billFromSearch = getBillfromSearch(billFromRequest, billRequest.getRequestInfo());

        boolean isBillWorkflowChange = false;
        if (billRequest.getWorkflow().getAction().equals(Actions.CREATE.toString())) {
            billFromSearch.setBusinessService(config.getBillBusinessService());
            billFromSearch.setAdditionalDetails(billFromRequest.getAdditionalDetails());
            isBillWorkflowChange = true;
        }
        BillDetailRequest billDetailRequest
                = BillDetailRequest
                .builder()
                .requestInfo(billRequest.getRequestInfo())
                .businessService(config.getBillDetailBusinessService())
                .workflow(billRequest.getWorkflow())
                .build();

        List<BillDetail> updatedBillDetails = new ArrayList<>();
        getBillDetailsToBeUpdated(billFromRequest, billFromSearch)
                .values()
                .forEach(billDetail -> {
                            try {
                                billDetailRequest.setBillDetail(billDetail);
                                State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBillDetail(billDetailRequest), billDetailRequest);
                                billDetail.setStatus(Status.fromValue(wfState.getApplicationStatus()));
                                updatedBillDetails.add(billDetail);
                            } catch (HttpClientErrorException e) {
                                log.error("error updating workflow status for bill number: {}, billdetail id: {}, status: {}, action: {}",
                                        billFromSearch.getBillNumber(), billDetail.getId(), billDetail.getStatus(), billRequest.getWorkflow().getAction(), e);
                            }
                        }
                );
        BillRequest billUpdateRequest
                = BillRequest
                .builder()
                .requestInfo(billRequest.getRequestInfo())
                .bill(billFromSearch)
                .build();

        if (isBillWorkflowChange) {
            billUpdateRequest.setWorkflow(billRequest.getWorkflow());
        }
        updateBillWfStatus(billUpdateRequest, isBillWorkflowChange);

        ResponseInfo responseInfo = responseInfoFactory.
                createResponseInfoFromRequestInfo(billDetailRequest.getRequestInfo(), true);
        return BillDetailResponse
                .builder()
                .responseInfo(responseInfo)
                .billDetails(updatedBillDetails)
                .build();

    }

    private Map<String, BillDetail> getBillDetailsToBeUpdated(Bill billFromRequest, Bill billFromSearch) {
        Map<String, BillDetail> billDetailsFromRequestById
                = billFromRequest.getBillDetails().stream().collect(Collectors.toMap(BillDetail::getId, billDetail -> billDetail));

        Map<String, BillDetail> billDetailsFromSearchById
                = billFromSearch.getBillDetails().stream().collect(Collectors.toMap(BillDetail::getId, billDetail -> billDetail));

        Map<String, BillDetail> billDetailsToBeUpdatedById = new HashMap<>();
        for (String id : billDetailsFromRequestById.keySet()) {
            billDetailsToBeUpdatedById.put(id, billDetailsFromSearchById.get(id));
        }
        return billDetailsToBeUpdatedById;
    }

    public List<TaskDetails> updatePaymentTaskStatus(TaskRequest taskRequest) {

        Task task = taskRequest.getTask();
        Bill billFromRequest = taskRequest.getBill();
        Bill billFromSearch = getBillfromSearch(billFromRequest, taskRequest.getRequestInfo());
        log.info("updating payment status for task {}, bill number {}", task.getId(), billFromSearch.getBillNumber());

        Map<String, BillDetail> billDetailsById = billFromSearch.getBillDetails().stream()
                .collect(Collectors.toMap(BillDetail::getId, billDetail -> billDetail));

        List<TaskDetails> taskDetails = taskRepository.searchTaskDetailsByTaskId(task.getId(), billFromSearch.getTenantId());
        for (TaskDetails taskDetail : taskDetails) {
            boolean isUpdateWorkflow = true;
            BillDetail billDetail = billDetailsById.get(taskDetail.getBillDetailsId());
            if (billDetail == null) {
                log.error("BillDetail not found for taskDetail id: {}, billDetailsId: {}. Skipping.",
                        taskDetail.getId(), taskDetail.getBillDetailsId());
                continue;
            }
            if (taskDetail.getStatus() == Status.IN_PROGRESS && billDetail.getStatus() == Status.PAYMENT_IN_PROGRESS) {
                Workflow billDetailWorkflow = Workflow.builder().build();
                try {
                    if (billDetail.getTotalAmount().compareTo(BigDecimal.ZERO) == 0) {
                        billDetailWorkflow.setAction(Actions.FAILED.toString());
                        taskDetail.setStatus(Status.DONE);
                        log.info("Payment couldn't be processed for bill detail id {} as total amount is 0", billDetail.getId());
                    } else if (
                            (taskDetail.getReasonForFailure() != null && !taskDetail.getReasonForFailure().isBlank()) &&
                                    (taskDetail.getReasonForFailure().toLowerCase().contains(EXCEPTION.toLowerCase()) || taskDetail.getReasonForFailure().toLowerCase().contains(ERROR.toLowerCase()))
                    ) {
                        billDetailWorkflow.setAction(Actions.FAILED.toString());
                        taskDetail.setStatus(Status.DONE);
                        log.info("Payment couldn't be processed for bill detail id {} : {}", billDetail.getId(),
                                taskDetail.getReasonForFailure() + " " + taskDetail.getResponseMessage());
                    } else {
                        PaymentTransferResponse paymentTransferResponse = mtnUtil.getTransferStatus(taskDetail.getId());
                        if (paymentTransferResponse.getStatus().equalsIgnoreCase(ResponseStatus.SUCCESSFUL.toString())) {
                            billDetailWorkflow.setAction(Actions.PAY.toString());
                            taskDetail.setReasonForFailure(paymentTransferResponse.getReason());
                            taskDetail.setAdditionalDetails((Object) paymentTransferResponse);
                            taskDetail.setStatus(Status.DONE);
                        } else if (paymentTransferResponse.getStatus().equalsIgnoreCase(ResponseStatus.FAILED.toString())) {
                            billDetailWorkflow.setAction(Actions.FAILED.toString());
                            taskDetail.setReasonForFailure(paymentTransferResponse.getReason());
                            taskDetail.setAdditionalDetails((Object) paymentTransferResponse);
                            taskDetail.setStatus(Status.DONE);
                        } else {
                            // Unknown/pending status — do not finalize taskDetail; leave IN_PROGRESS for retry on next run.
                            log.warn("Unknown/pending MTN response status: {} for bill number: {}, task id: {}, task detail id: {}. Will retry on next run.",
                                    paymentTransferResponse.getStatus(), billFromSearch.getBillNumber(), task.getId(), taskDetail.getId());
                            taskDetail.setReasonForFailure(paymentTransferResponse.getReason());
                            taskDetail.setAdditionalDetails((Object) paymentTransferResponse);
                            isUpdateWorkflow = false;
                            // taskDetail.setStatus(Status.DONE) intentionally NOT set — stays IN_PROGRESS for retry
                        }
                    }
                    // Safety net (dead code after 4a/4b): PAYMENT_FAILED BillDetails can no longer enter this block
                    if (billDetail.getStatus() == Status.PAYMENT_FAILED && Objects.equals(billDetailWorkflow.getAction(), Actions.FAILED.toString()))
                        isUpdateWorkflow = false;
                } catch (CustomException e) {
                    log.error("error in fetching payment transfer status from mtn for bill number : {}, billDetail: {},task: {}, taskDetail: {}",
                            billFromSearch.getBillNumber(), billDetail.getId(), task.getId(), taskDetail.getId(), e);
                    taskDetail.setReasonForFailure(e.getCode());
                    taskDetail.setResponseMessage(e.getMessage());
                    isUpdateWorkflow = false;
                }
                expenseProducer.push(billFromSearch.getTenantId(), config.getTaskDetailsUpdateTopic(), taskDetail);

                Object additionalDetailsObj = billDetail.getAdditionalDetails();
                Map<String, Object> additionalDetails;

                try {
                    additionalDetails = new ObjectMapper().convertValue(
                            additionalDetailsObj,
                            new TypeReference<>() {
                            }
                    );
                } catch (IllegalArgumentException e) {
                    additionalDetails = new HashMap<>();
                }

                ErrorDetails errorDetails = ErrorDetails.builder()
                        .reasonForFailure(taskDetail.getReasonForFailure())
                        .responseMessage(taskDetail.getResponseMessage())
                        .response(taskDetail.getAdditionalDetails()).build();
                additionalDetails.put("errorDetails", errorDetails);
                billDetail.setAdditionalDetails(additionalDetails);
                // setBillDetailStatus catches HttpClientErrorException internally — no outer try-catch needed.
                setBillDetailStatus(billDetail, billDetailWorkflow, taskRequest.getRequestInfo(), isUpdateWorkflow);
            }
        }
        if (billFromSearch.getStatus() == Status.PAYMENT_IN_PROGRESS) {

            List<BillDetail> paidBillDetails = new ArrayList<>();
            List<BillDetail> declinedBillDetails = new ArrayList<>();
            billFromSearch
                    .getBillDetails().forEach(billDetail -> {
                        if (billDetail.getStatus() == Status.PAID) {
                            paidBillDetails.add(billDetail);
                        } else if (billDetail.getStatus() == Status.PAYMENT_FAILED) {
                            declinedBillDetails.add(billDetail);
                        }
                    });

            Workflow workflow = Workflow.builder()
                    .build();
            boolean isWorkflowChange = true;
            if (paidBillDetails.size() == billFromSearch.getBillDetails().size()) {
                workflow.setAction(Actions.FULLY_PAY.toString());
            } else if (!paidBillDetails.isEmpty()) {
                workflow.setAction(Actions.PARTIALLY_PAY.toString());
            } else if (declinedBillDetails.size() == billFromSearch.getBillDetails().size()) {
                workflow.setAction(Actions.FAILED.toString());
            } else {
                log.info("no workflow state change for bill number : {}, task id: {}", billFromSearch.getBillNumber(), task.getId());
                isWorkflowChange = false;
            }
            BillRequest billRequest = BillRequest
                    .builder()
                    .bill(billFromSearch)
                    .workflow(workflow)
                    .requestInfo(taskRequest.getRequestInfo())
                    .build();
            updateBillWfStatus(billRequest, isWorkflowChange);
        }

        log.info("finished updating payment status for task {}, bill number {}", task.getId(), billFromSearch.getBillNumber());
        return taskDetails;
    }

    /**
     * Called by the scheduler handler after the transfer is initiated.
     * Polls MTN status for all IN_PROGRESS task details, updates bill detail workflow,
     * and finalizes the task by pushing {@code status=DONE} to Kafka once all details are resolved.
     *
     * @return {@code true} if any task detail is still IN_PROGRESS (scheduler should retry),
     *         {@code false} if all task details are resolved
     */
    public boolean updatePaymentTaskStatusAndFinalize(TaskRequest taskRequest) {
        Task task = taskRequest.getTask();
        String tenantId = taskRequest.getBill().getTenantId();

        // Use in-memory updated taskDetails — avoids a stale DB re-read before the persister
        // has committed the Kafka events pushed by updatePaymentTaskStatus.
        List<TaskDetails> updatedTaskDetails = updatePaymentTaskStatus(taskRequest);
        boolean anyInProgress = updatedTaskDetails.stream()
                .anyMatch(td -> td.getStatus() == Status.IN_PROGRESS);

        if (!anyInProgress) {
            AuditDetails auditDetails = task.getAuditDetails();
            if (auditDetails != null) {
                auditDetails.setLastModifiedTime(System.currentTimeMillis());
            }
            task.setStatus(Status.DONE);
            log.info("Task {} fully resolved — pushing DONE status", task.getId());
            expenseProducer.push(tenantId, config.getTaskUpdateTopic(), task);
        }

        return anyInProgress;
    }

}
