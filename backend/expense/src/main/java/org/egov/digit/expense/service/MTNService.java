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
import org.egov.digit.expense.config.Constants;
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
import org.egov.tracer.model.ServiceCallException;
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
public class MTNService implements PaymentProviderService {

    private final ExpenseProducer expenseProducer;

    private final Configuration config;

    private final BillValidator validator;

    private final WorkflowUtil workflowUtil;

    private final BillRepository billRepository;

    private final EnrichmentUtil enrichmentUtil;

    private final ResponseInfoFactory responseInfoFactory;

    private final TaskRepository taskRepository;

    private final MTNUtil mtnUtil;

    private final SchedulerJobRepository schedulerJobRepository;

    private final SchedulerJobRegistry schedulerJobRegistry;

    private final ObjectMapper objectMapper;

    @Autowired
    public MTNService(ExpenseProducer expenseProducer, Configuration config, BillValidator validator,
                      WorkflowUtil workflowUtil, BillRepository billRepository, EnrichmentUtil enrichmentUtil,
                      ResponseInfoFactory responseInfoFactory,
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
        if (billsFromSearch == null || billsFromSearch.isEmpty()) {
            throw new CustomException("BILL_NOT_FOUND", "Bill not found for id: " + billFromRequest.getId());
        }
        Bill billFromSearch = billsFromSearch.get(0);
        if (null == billFromRequest.getPayer()) {
            billFromRequest.setPayer(billFromSearch.getPayer());
        }
        if (billFromRequest.getBillDetails() == null || billFromRequest.getBillDetails().isEmpty()) {
            billFromRequest.setBillDetails(billFromSearch.getBillDetails());
        }
        enrichmentUtil.encrichBillWithUuidAndAuditForUpdate(billRequest, billsFromSearch);
        return billFromSearch;
    }

    @Override
    public boolean supports(String paymentProvider) {
        // Handles MTN, null (no provider), and unrecognized providers — all result in FAILED or MTN verification.
        return paymentProvider == null
                || Constants.PAYMENT_PROVIDER_MTN.equalsIgnoreCase(paymentProvider)
                || !Constants.VALID_PAYMENT_PROVIDERS.contains(paymentProvider.toUpperCase());
    }

    @Override
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
        insertVerifySchedulerJob(task, billTaskRequest.getBill().getTenantId(), billTaskRequest.getRequestInfo());

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
        Bill billFromSearch = null;

        try {
            billFromSearch = getBillfromSearch(billFromRequest, taskRequest.getRequestInfo());

            Map<String, BillDetail> billDetailsToBeUpdatedById = getBillDetailsToBeUpdated(billFromRequest, billFromSearch);

            for (BillDetail billDetail : billDetailsToBeUpdatedById.values()) {
                if (billDetail == null) {
                    log.error("BillDetail from search is null for one of the requested IDs in bill number: {}. Skipping.", billFromSearch.getBillNumber());
                    continue;
                }
                Party payee = billDetail.getPayee();
                String provider = payee != null ? payee.getPaymentProvider() : null;
                if (!Constants.PAYMENT_PROVIDER_MTN.equalsIgnoreCase(provider)) {
                    if (org.springframework.util.StringUtils.hasText(provider)
                            && Constants.VALID_PAYMENT_PROVIDERS.contains(provider.toUpperCase())) {
                        log.info("Skipping billDetail {} (provider={}) — handled by another service", billDetail.getId(), provider);
                        continue;
                    }
                    String reason = org.springframework.util.StringUtils.hasText(provider)
                            ? "Invalid payment provider configured: " + provider
                            : "No payment provider configured";
                    if (billDetail.getStatus() == Status.PENDING_VERIFICATION
                            || billDetail.getStatus() == Status.VERIFICATION_FAILED) {
                        log.warn("billDetail {} — {} — marking VERIFICATION_FAILED", billDetail.getId(), reason);
                        Workflow verifyWf = Workflow.builder().action(Actions.VERIFY.toString()).build();
                        setBillDetailStatus(billDetail, verifyWf, taskRequest.getRequestInfo(), true);
                        if (billDetail.getStatus() == Status.VERIFICATION_IN_PROGRESS) {
                            Workflow failWf = Workflow.builder().action(Actions.FAILED.toString()).build();
                            setBillDetailStatus(billDetail, failWf, taskRequest.getRequestInfo(), true);
                        }
                    } else {
                        log.info("Skipping billDetail {} (provider={}, status={}) — not actionable", billDetail.getId(), provider, billDetail.getStatus());
                    }
                    continue;
                }
                boolean alreadyInProgress = (billDetail.getStatus() == Status.VERIFICATION_IN_PROGRESS);
                if (billDetail.getStatus() == Status.PENDING_VERIFICATION ||
                        billDetail.getStatus() == Status.VERIFICATION_FAILED) {
                    Workflow verifyWorkflow = Workflow.builder()
                            .action(Actions.VERIFY.toString())
                            .build();
                    setBillDetailStatus(billDetail, verifyWorkflow, taskRequest.getRequestInfo(), true);

                    if (billDetail.getStatus() != Status.VERIFICATION_IN_PROGRESS) {
                        log.error("WF Step 1 (VERIFY) did not reach VERIFICATION_IN_PROGRESS for billDetail Id: {}, bill number: {}, current status: {}. Skipping Step 2.",
                                billDetail.getId(), billFromSearch.getBillNumber(), billDetail.getStatus());
                        continue;
                    }
                } else if (!alreadyInProgress) {
                    continue;
                }

                String payeePhoneNumber = payee.getPayeePhoneNumber();
                if (payeePhoneNumber == null || payeePhoneNumber.isBlank()) {
                    log.error("payeePhoneNumber is null/blank for MTN billDetail {}, bill {}. Skipping verification.",
                            billDetail.getId(), billFromSearch.getBillNumber());
                    continue;
                }
                TaskDetails taskDetails = TaskDetails.builder()
                        .id(UUID.randomUUID().toString())
                        .taskId(task.getId())
                        .billId(billFromSearch.getId())
                        .billDetailsId(billDetail.getId())
                        .payeeId(billDetail.getPayee().getId())
                        .status(Status.IN_PROGRESS)
                        .tenantId(billFromSearch.getTenantId())
                        .auditDetails(billFromSearch.getAuditDetails())
                        .referenceId(payeePhoneNumber)
                        .build();

                boolean verificationSucceeded = false;
                try {
                    boolean isActive = mtnUtil.isMsisdnActive(payeePhoneNumber);
                    if (isActive) {
                        verificationSucceeded = true;
                    } else {
                        taskDetails.setReasonForFailure("MTN_ACCOUNT_INACTIVE_" + EXCEPTION);
                        taskDetails.setResponseMessage("Account is not active");
                    }
                } catch (CustomException e) {
                    log.error("Exception while verifying MSISDN : {}", payeePhoneNumber, e);
                    taskDetails.setResponseMessage(e.getMessage());
                    taskDetails.setReasonForFailure(e.getCode());
                }

                taskDetails.setStatus(Status.DONE);
                expenseProducer.push(billFromSearch.getTenantId(), config.getBillTaskDetailsTopic(), taskDetails);

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

                if (verificationSucceeded) {
                    Workflow successWorkflow = Workflow.builder()
                            .action(Actions.VERIFICATION_SUCCESS.toString())
                            .build();
                    setBillDetailStatus(billDetail, successWorkflow, taskRequest.getRequestInfo(), true);
                    if (billDetail.getStatus() == Status.VERIFICATION_IN_PROGRESS) {
                        log.error("WF Step 2 (VERIFICATION_SUCCESS) failed for billDetail Id: {}, bill number: {}. BillDetail stuck at VERIFICATION_IN_PROGRESS — manual intervention required.",
                                billDetail.getId(), billFromSearch.getBillNumber());
                    }
                } else {
                    Workflow failedWorkflow = Workflow.builder()
                            .action(Actions.FAILED.toString())
                            .build();
                    setBillDetailStatus(billDetail, failedWorkflow, taskRequest.getRequestInfo(), true);
                    if (billDetail.getStatus() == Status.VERIFICATION_IN_PROGRESS) {
                        log.error("WF Step 2 (FAILED) failed for billDetail Id: {}, bill number: {}. BillDetail stuck at VERIFICATION_IN_PROGRESS — manual intervention required.",
                                billDetail.getId(), billFromSearch.getBillNumber());
                    }
                }
            }
            // Push ONLY the details processed by this task — not the full bill.
            // Each task handles a single detail; pushing all details with stale DB reads
            // from the other concurrent tasks would overwrite their VERIFIED statuses (lost-update).
            List<BillDetail> processedDetails = billDetailsToBeUpdatedById.values().stream()
                    .filter(java.util.Objects::nonNull)
                    .collect(java.util.stream.Collectors.toList());
            billFromSearch.setBillDetails(processedDetails);
            BillRequest billUpdateRequest = BillRequest.builder()
                    .bill(billFromSearch)
                    .requestInfo(taskRequest.getRequestInfo())
                    .build();
            updateBillWfStatus(billUpdateRequest, false);

            if (billFromSearch.getStatus() == Status.VERIFICATION_IN_PROGRESS) {
                log.info("Bill {} — pushed {} updated detail(s) to Kafka, bill number: {}",
                        billFromSearch.getId(), processedDetails.size(), billFromSearch.getBillNumber());
            } else {
                log.warn("Bill {} has unexpected status {} during verify task — detail(s) still persisted",
                        billFromSearch.getId(), billFromSearch.getStatus());
            }
        } finally {
            task.setStatus(Status.DONE);
            expenseProducer.push(billFromRequest.getTenantId(), config.getTaskUpdateTopic(), task);
        }
    }

    private List<Bill> getBills(BillRequest billRequest, Boolean isCreate) {

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

        // Set before try so scheduler can recover RequestInfo even if getBillfromSearch throws
        task.setAdditionalDetails(taskRequest.getRequestInfo());
        if (task.getAuditDetails() != null) task.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());

        try {
            Bill billFromSearch = getBillfromSearch(billFromRequest, taskRequest.getRequestInfo());
            Map<String, BillDetail> billDetailsToBeupdatedById = getBillDetailsToBeUpdated(billFromRequest, billFromSearch);

            for (BillDetail billDetail : billDetailsToBeupdatedById.values()) {
                if (billDetail == null) {
                    log.error("BillDetail from search is null for one of the requested IDs in bill number: {}. Skipping.", billFromSearch.getBillNumber());
                    continue;
                }
                Party payee = billDetail.getPayee();
                if (payee == null || !Constants.PAYMENT_PROVIDER_MTN.equalsIgnoreCase(payee.getPaymentProvider())) {
                    log.info("Skipping non-MTN billDetail {} (provider={}) in transfer()", billDetail.getId(), payee != null ? payee.getPaymentProvider() : "null");
                    continue;
                }
                if (billDetail.getStatus() == Status.PAYMENT_IN_PROGRESS) {
                    String payeePhoneNumber = payee.getPayeePhoneNumber();
                    if (payeePhoneNumber == null || payeePhoneNumber.isBlank()) {
                        log.error("payeePhoneNumber is null/blank for MTN billDetail {}, bill {}. Skipping transfer.",
                                billDetail.getId(), billFromSearch.getBillNumber());
                        continue;
                    }
                    TaskDetails taskDetails = TaskDetails.builder()
                            .id(UUID.randomUUID().toString())
                            .taskId(task.getId())
                            .referenceId(payeePhoneNumber)
                            .billId(billFromSearch.getId())
                            .billDetailsId(billDetail.getId())
                            .payeeId(billDetail.getPayee().getId())
                            .status(Status.IN_PROGRESS)
                            .tenantId(billFromSearch.getTenantId())
                            .auditDetails(billFromSearch.getAuditDetails())
                            .build();

                    if (billDetail.getTotalAmount().compareTo(BigDecimal.ZERO) == 0) {
                        taskDetails.setResponseMessage("Payment couldn't be processed as total amount is 0.");
                        taskDetails.setReasonForFailure("TOTAL_AMOUNT_ZERO_" + EXCEPTION);
                        expenseProducer.push(billFromSearch.getTenantId(), config.getBillTaskDetailsTopic(), taskDetails);
                        log.info("payment couldn't be processed for bill detail id {} as total amount is 0", billDetail.getId());
                    } else {
                        PaymentTransferRequest paymentTransferRequest = createPaymentTransferRequest(billDetail, payeePhoneNumber);
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
            expenseProducer.push(billFromSearch.getTenantId(), config.getTaskUpdateTopic(), task);
        } finally {
            // Always register scheduler job — even if bill search or loop fails, the task exists in DB
            // and needs status polling. insertSchedulerJob has its own try-catch so it never throws.
            insertSchedulerJob(task, billFromRequest.getTenantId(), taskRequest.getRequestInfo());
        }
    }

    private void forceBillDetailFailed(BillDetail billDetail, RequestInfo requestInfo) {
        try {
            Workflow failedWf = Workflow.builder().action(Actions.FAILED.toString()).build();
            BillDetailRequest billDetailRequest = BillDetailRequest.builder()
                    .billDetail(billDetail)
                    .businessService(config.getBillDetailBusinessService())
                    .workflow(failedWf)
                    .requestInfo(requestInfo)
                    .build();
            State wfState = workflowUtil.callWorkFlow(
                    workflowUtil.prepareWorkflowRequestForBillDetail(billDetailRequest), billDetailRequest);
            billDetail.setStatus(Status.fromValue(wfState.getApplicationStatus()));
            log.info("Forced billDetail {} to FAILED status via WF", billDetail.getId());
        } catch (Exception ex) {
            log.error("CRITICAL: failed to force billDetail {} to FAILED — manual intervention required",
                    billDetail.getId(), ex);
        }
    }

    private void insertVerifySchedulerJob(Task task, String tenantId, RequestInfo requestInfo) {
        try {
            long now = System.currentTimeMillis();
            SchedulerJob schedulerJob = SchedulerJob.builder()
                    .id(UUID.randomUUID().toString())
                    .tenantId(tenantId)
                    .jobType(SchedulerJobType.TASK_VERIFY_CHECK)
                    .referenceId(task.getId())
                    .schedulerStatus(SchedulerJobStatus.PENDING)
                    .nextCheckAt(now + 5000L)
                    .attemptCount(0)
                    .maxAttempts(config.getSchedulerMaxAttempts())
                    .context(requestInfo)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            schedulerJobRepository.insert(schedulerJob);
            schedulerJobRegistry.register(tenantId);
            log.info("Verify scheduler job created for task {} tenant {}", task.getId(), tenantId);
        } catch (Exception e) {
            log.error("Failed to insert verify scheduler job for task {} — crash recovery will not run", task.getId(), e);
        }
    }

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

    public void forceFailStuckVerifyDetails(TaskRequest taskRequest) {
        Task task = taskRequest.getTask();
        Bill billFromSearch = getBillfromSearch(taskRequest.getBill(), taskRequest.getRequestInfo());

        List<BillDetail> details = billFromSearch.getBillDetails();
        if (details == null || details.isEmpty()) {
            log.warn("No bill details found for bill {} in forceFailStuckVerifyDetails — skipping detail force-fail",
                    billFromSearch.getId());
            task.setStatus(Status.DONE);
            if (task.getAuditDetails() != null) task.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
            expenseProducer.push(billFromSearch.getTenantId(), config.getTaskUpdateTopic(), task);
            return;
        }

        for (BillDetail billDetail : details) {
            if (billDetail.getStatus() == Status.VERIFICATION_IN_PROGRESS
                    || billDetail.getStatus() == Status.PENDING_VERIFICATION) {
                log.warn("Force-failing stuck verify billDetail {} (status={})", billDetail.getId(), billDetail.getStatus());
                forceBillDetailFailed(billDetail, taskRequest.getRequestInfo());
            }
        }

        task.setStatus(Status.DONE);
        if (task.getAuditDetails() != null) {
            task.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
        }
        expenseProducer.push(billFromSearch.getTenantId(), config.getTaskUpdateTopic(), task);
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
        boolean wfSucceeded = false;
        if (isWorkflowChange && validator.isWorkflowActiveForBusinessService(bill.getBusinessService())) {
            try {
                State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBill(billRequest), billRequest);
                bill.setStatus(Status.fromValue(wfState.getApplicationStatus()));
                wfSucceeded = true;
            } catch (Exception e) {
                log.error("Bill WF transition failed for bill {} (status={}) — skipping bill push; BILL_STATUS_POLL will recover",
                        bill.getBillNumber(), bill.getStatus(), e);
            }
        }
        if (!isWorkflowChange || wfSucceeded) {
            expenseProducer.push(bill.getTenantId(), config.getBillUpdateTopic(), billRequest);
        }
    }

    private void setBillDetailStatus(BillDetail billDetail, Workflow workflow, RequestInfo requestInfo, boolean isWorkflowUpdate) {
        if (!validator.isWorkflowActiveForBusinessService(config.getBillDetailBusinessService()) || !isWorkflowUpdate) {
            return;
        }
        BillDetailRequest billDetailRequest = BillDetailRequest.builder()
                .billDetail(billDetail)
                .businessService(config.getBillDetailBusinessService())
                .workflow(workflow)
                .requestInfo(requestInfo)
                .build();
        int maxRetries = config.getWfTransitionRetryMaxAttempts();
        long delayMs = config.getWfTransitionRetryInitialDelayMs();
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBillDetail(billDetailRequest), billDetailRequest);
                billDetail.setStatus(Status.fromValue(wfState.getApplicationStatus()));
                return;
            } catch (Exception e) {
                // Retry all errors (INVALID_ACTION race AND transient 5xx/network) up to maxRetries.
                // Previously only INVALID_ACTION was retried — transient WF outages immediately
                // force-failed workers. Now we exhaust all attempts before forcing FAILED.
                if (attempt == maxRetries) {
                    log.error("WF retries exhausted ({}/{}) for billDetail {}, status={}, action={} — forcing FAILED",
                            attempt, maxRetries, billDetail.getId(), billDetail.getStatus(), workflow.getAction(), e);
                    forceBillDetailFailed(billDetail, requestInfo);
                    return;
                }
                log.warn("WF retry {}/{} for billDetail {}, action={}, retrying in {}ms: {}",
                        attempt, maxRetries, billDetail.getId(), workflow.getAction(), delayMs, e.getMessage());
                try {
                    Thread.sleep(delayMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return;
                }
                delayMs *= 2;
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
                            } catch (Exception e) {
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
        // For PAYMENTS.BILL CREATE: ensure bill status is PENDING_VERIFICATION even if WF call fails
        // (campaign supervisor may lack the role; status will be set correctly as fallback)
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
            Party payee = billDetail.getPayee();
            if (payee == null || !Constants.PAYMENT_PROVIDER_MTN.equalsIgnoreCase(payee.getPaymentProvider())) {
                log.info("Skipping non-MTN billDetail {} (provider={}) in updatePaymentTaskStatus()", billDetail.getId(), payee != null ? payee.getPaymentProvider() : "null");
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
        // Also check PARTIALLY_PAID: when concurrent task completions race past each other,
        // the bill may already be PARTIALLY_PAID by the time we evaluate. We must still drive
        // it to FULLY_PAY once all details settle.
        Status billStatus = billFromSearch.getStatus();
        if (billStatus == Status.PAYMENT_IN_PROGRESS || billStatus == Status.PARTIALLY_PAID) {

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

            Workflow workflow = Workflow.builder().build();
            boolean isWorkflowChange = true;
            int totalDetails = billFromSearch.getBillDetails().size();

            if (paidBillDetails.size() == totalDetails) {
                workflow.setAction(Actions.FULLY_PAY.toString());
            } else if (!paidBillDetails.isEmpty() && billStatus == Status.PAYMENT_IN_PROGRESS) {
                // PARTIALLY_PAY is only valid from PAYMENT_IN_PROGRESS; skip if already PARTIALLY_PAID
                workflow.setAction(Actions.PARTIALLY_PAY.toString());
            } else if (declinedBillDetails.size() == totalDetails && billStatus == Status.PAYMENT_IN_PROGRESS) {
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

        // Empty list means TaskDetails haven't been persisted yet (Kafka/persister lag after transfer fired).
        // Treat as still in-progress so the scheduler retries rather than prematurely marking task DONE.
        if (updatedTaskDetails.isEmpty()) {
            log.warn("Task {} has no task details in DB yet (persister lag?) — will retry on next scheduler run", task.getId());
            return true;
        }

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
