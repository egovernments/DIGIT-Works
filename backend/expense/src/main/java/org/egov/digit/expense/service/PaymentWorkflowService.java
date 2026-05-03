package org.egov.digit.expense.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.workflow.State;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.repository.SchedulerJobRepository;
import org.egov.digit.expense.repository.TaskRepository;
import org.egov.digit.expense.service.scheduler.SchedulerJobRegistry;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.BillBatchEmailContext;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.SchedulerJobStatus;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import static org.egov.digit.expense.config.Constants.*;

/**
 * Orchestrates all PAYMENTS workflow phases for bill and bill-detail lifecycle.
 *
 * <p>Covers:
 * <ul>
 *   <li>Phase 1 — Process instance creation (PAYMENTS.BILL + PAYMENTS.BILLDETAILS)</li>
 *   <li>Phase 2 — Verification (verify, re-verify)</li>
 *   <li>Phase 3 — Ignore Errors</li>
 *   <li>Phase 4 — Send for Review</li>
 *   <li>Phase 5 — Review (Send for Approval)</li>
 *   <li>Phase 6 — Payment Initiation</li>
 *   <li>Phase 7 — Payment Retry</li>
 * </ul>
 */
@Service
@Slf4j
public class PaymentWorkflowService {

    private final WorkflowUtil workflowUtil;
    private final BillRepository billRepository;
    private final TaskRepository taskRepository;
    private final SchedulerJobRepository schedulerJobRepository;
    private final SchedulerJobRegistry schedulerJobRegistry;
    private final Configuration config;
    private final ExpenseProducer expenseProducer;
    private final BillCacheService billCacheService;

    @Autowired
    public PaymentWorkflowService(WorkflowUtil workflowUtil,
                                   BillRepository billRepository,
                                   TaskRepository taskRepository,
                                   SchedulerJobRepository schedulerJobRepository,
                                   SchedulerJobRegistry schedulerJobRegistry,
                                   Configuration config,
                                   ExpenseProducer expenseProducer,
                                   BillCacheService billCacheService) {
        this.workflowUtil = workflowUtil;
        this.billRepository = billRepository;
        this.taskRepository = taskRepository;
        this.schedulerJobRepository = schedulerJobRepository;
        this.schedulerJobRegistry = schedulerJobRegistry;
        this.config = config;
        this.expenseProducer = expenseProducer;
        this.billCacheService = billCacheService;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Phase 1 — Process Instance Creation
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Creates PAYMENTS.BILLDETAILS process instances for each bill detail
     * immediately after the PAYMENTS.BILL process instance is created.
     * Idempotent — if a process instance already exists, the error is logged and skipped.
     */
    public void createBillDetailProcessInstances(BillRequest billRequest) {
        Bill bill = billRequest.getBill();
        RequestInfo requestInfo = billRequest.getRequestInfo();

        for (BillDetail detail : bill.getBillDetails()) {
            try {
                BillDetailRequest detailRequest = BillDetailRequest.builder()
                        .billDetail(detail)
                        .businessService(config.getBillDetailBusinessService())
                        .workflow(Workflow.builder().action(Actions.CREATE.toString()).build())
                        .requestInfo(requestInfo)
                        .build();
                State wfState = workflowUtil.callWorkFlow(
                        workflowUtil.prepareWorkflowRequestForBillDetail(detailRequest), detailRequest);
                detail.setStatus(Status.fromValue(wfState.getApplicationStatus()));
            } catch (Exception e) {
                log.warn("Failed to create PAYMENTS.BILLDETAILS process instance for detail id={}: {}",
                        detail.getId(), e.getMessage());
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Phase 2 — Verification
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Transitions the bill to VERIFICATION_IN_PROGRESS, pushes VerificationStart tasks per
     * eligible detail, and inserts a BILL_STARTED_CHECK coordinator job.
     * BILL_STATUS_POLL is created later by BILL_STARTED_CHECK once all details confirm in-progress.
     *
     * <p>Pre-condition: Bill must be in PENDING_VERIFICATION or PARTIALLY_VERIFIED.
     */
    public void verifyBill(BillRequest billRequest) {
        Bill bill = billRequest.getBill();
        RequestInfo requestInfo = billRequest.getRequestInfo();

        validateBillInStates(bill, Status.PENDING_VERIFICATION, Status.PARTIALLY_VERIFIED);

        // Bill → VERIFICATION_IN_PROGRESS (1 synchronous WF call)
        transitionBill(bill, Actions.VERIFY, billRequest);
        pushBillUpdate(bill, requestInfo);

        // Push one VerificationStart task per eligible detail (transitions detail → VERIFICATION_IN_PROGRESS)
        createVerificationStartTasks(bill, requestInfo);

        // Coordinator: waits for all details to confirm in-progress before pushing VerificationVerify tasks
        insertBillStartedCheckJob(bill, STARTED_CHECK_PHASE_VERIFY, requestInfo);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Phase 3 — Ignore Errors
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Transitions VERIFICATION_FAILED details directly to VERIFIED (no external call),
     * moves the bill to IGNORING_ERRORS_IN_PROGRESS, then inserts a poll job.
     *
     * <p>Pre-condition: Bill in PARTIALLY_VERIFIED; no detail in PENDING_VERIFICATION.
     */
    public void ignoreErrorsAndVerify(BillRequest billRequest) {
        Bill bill = billRequest.getBill();
        RequestInfo requestInfo = billRequest.getRequestInfo();

        validateBillInStates(bill, Status.PARTIALLY_VERIFIED);

        boolean hasPending = bill.getBillDetails().stream()
                .anyMatch(d -> d.getStatus() == Status.PENDING_VERIFICATION);
        if (hasPending) {
            throw new CustomException("IGNORE_ERRORS_VALIDATION",
                    "This action cannot be performed as some bill details are still pending verification.");
        }

        // Bill → IGNORING_ERRORS_IN_PROGRESS (1 synchronous WF call)
        transitionBill(bill, Actions.IGNORE_ERRORS_AND_VERIFY, billRequest);
        pushBillUpdate(bill, requestInfo);

        // Kafka-first: push one WfUpdate task per detail for near-realtime processing.
        createWfUpdateTask(bill, POLL_PHASE_IGNORE_ERRORS, requestInfo,
                d -> d.getStatus() == Status.VERIFICATION_FAILED);
        insertBillStatusPollJob(bill, POLL_PHASE_IGNORE_ERRORS, requestInfo, config.getSchedulerSafetyNetDelayMs());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Phase 4 — Send for Review
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Transitions the bill to SENDING_FOR_REVIEW and each VERIFIED detail to UNDER_REVIEW,
     * then inserts a poll job.
     *
     * <p>Pre-condition: Bill in FULLY_VERIFIED.
     */
    public void sendForReview(BillRequest billRequest) {
        sendForReview(billRequest, null);
    }

    public void sendForReview(BillRequest billRequest, String batchId) {
        Bill bill = billRequest.getBill();
        RequestInfo requestInfo = billRequest.getRequestInfo();

        validateBillInStates(bill, Status.FULLY_VERIFIED);

        // Bill → SENDING_FOR_REVIEW (locked)
        transitionBill(bill, Actions.SEND_FOR_REVIEW, billRequest);
        pushBillUpdate(bill, requestInfo);

        // Kafka-first: push one WfUpdate task per detail for near-realtime processing.
        createWfUpdateTask(bill, POLL_PHASE_SEND_FOR_REVIEW, requestInfo,
                d -> d.getStatus() == Status.VERIFIED);
        insertBillStatusPollJob(bill, POLL_PHASE_SEND_FOR_REVIEW, requestInfo,
                config.getSchedulerSafetyNetDelayMs(), batchId);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Phase 5 — Review (Send for Approval)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Transitions bill to REVIEW_IN_PROGRESS and each UNDER_REVIEW detail to REVIEWED,
     * then inserts a poll job using the reviewer's RequestInfo.
     *
     * <p>Pre-condition: Bill in UNDER_REVIEW. Actor: PAYMENT_REVIEWER.
     */
    public void sendForApproval(BillRequest billRequest) {
        sendForApproval(billRequest, null);
    }

    public void sendForApproval(BillRequest billRequest, String batchId) {
        Bill bill = billRequest.getBill();
        RequestInfo requestInfo = billRequest.getRequestInfo();

        validateBillInStates(bill, Status.UNDER_REVIEW);

        // Bill → REVIEW_IN_PROGRESS (locked)
        transitionBill(bill, Actions.SEND_FOR_APPROVAL, billRequest);
        pushBillUpdate(bill, requestInfo);

        // Kafka-first: push one WfUpdate task per detail for near-realtime processing.
        createWfUpdateTask(bill, POLL_PHASE_SEND_FOR_APPROVAL, requestInfo,
                d -> d.getStatus() == Status.UNDER_REVIEW);
        insertBillStatusPollJob(bill, POLL_PHASE_REVIEW, requestInfo,
                config.getSchedulerSafetyNetDelayMs(), batchId);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Phase 6 — Payment Initiation
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Transitions bill to PAYMENT_IN_PROGRESS, pushes PaymentInitiationStart tasks per eligible
     * detail, and inserts a BILL_STARTED_CHECK coordinator job.
     * BILL_STATUS_POLL is created later by BILL_STARTED_CHECK once all details confirm in-progress.
     *
     * <p>Pre-condition: Bill in REVIEWED. Actor: PAYMENT_APPROVER.
     */
    public void initiatePayment(BillRequest billRequest) {
        Bill bill = billRequest.getBill();
        RequestInfo requestInfo = billRequest.getRequestInfo();

        validateBillInStates(bill, Status.REVIEWED);

        // Bill → PAYMENT_IN_PROGRESS (locked)
        transitionBill(bill, Actions.PAYMENT_INITIATION, billRequest);
        pushBillUpdate(bill, requestInfo);

        // Push one PaymentInitiationStart task per eligible detail (transitions detail → PAYMENT_IN_PROGRESS)
        createPaymentInitiationStartTasks(bill, requestInfo);

        // Coordinator: waits for all details to confirm in-progress before pushing PaymentInitiationPay tasks
        insertBillStartedCheckJob(bill, STARTED_CHECK_PHASE_PAYMENT, requestInfo);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Phase 7 — Payment Retry
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Retries payment for PAYMENT_FAILED details only (already-PAID details are skipped).
     *
     * <p>Pre-condition: Bill in PAYMENT_FAILED or PARTIALLY_PAID. Actor: PAYMENT_APPROVER.
     */
    public void retryPayment(BillRequest billRequest) {
        Bill bill = billRequest.getBill();
        RequestInfo requestInfo = billRequest.getRequestInfo();

        validateBillInStates(bill, Status.PAYMENT_FAILED, Status.PARTIALLY_PAID);

        // Bill → PAYMENT_IN_PROGRESS (locked)
        transitionBill(bill, Actions.PAYMENT_INITIATION, billRequest);
        pushBillUpdate(bill, requestInfo);

        // Push one PaymentInitiationStart task per eligible PAYMENT_FAILED detail
        createPaymentInitiationStartTasks(bill, requestInfo,
                d -> d.getStatus() == Status.PAYMENT_FAILED);

        // Coordinator: waits for all details to confirm in-progress before pushing PaymentInitiationPay tasks
        insertBillStartedCheckJob(bill, STARTED_CHECK_PHASE_PAYMENT, requestInfo);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Fetches bill with details — Redis-first, DB fallback.
     * Redis holds the most recent in-flight state (written before every Kafka push),
     * so this always returns the latest known state even during Kafka→persister lag.
     *
     * <p>Use {@code bypassCache=true} in aggregation contexts where counting ALL sibling
     * details is required for correctness — the cache may hold a partial single-detail bill
     * written during individual task processing.
     */
    public Bill fetchBillWithDetails(String billId, String tenantId, RequestInfo requestInfo) {
        return fetchBillWithDetails(billId, tenantId, requestInfo, false);
    }

    public Bill fetchBillWithDetails(String billId, String tenantId, RequestInfo requestInfo, boolean bypassCache) {
        BillSearchRequest searchRequest = BillSearchRequest.builder()
                .requestInfo(requestInfo)
                .billCriteria(BillCriteria.builder()
                        .tenantId(tenantId)
                        .ids(Collections.singleton(billId))
                        .statusesNot(Collections.singletonList(Status.INACTIVE.toString()))
                        .build())
                .build();
        List<Bill> bills = billRepository.search(searchRequest, false);
        if (bills.isEmpty()) return null;
        Bill dbBill = bills.get(0);
        if (bypassCache) return dbBill;
        return billCacheService.get(tenantId, billId).orElse(dbBill);
    }

    /**
     * Inserts a scheduler job with up to 3 rapid retries on transient failures.
     * No sleep between attempts — blocking the scheduler thread degrades all tenant jobs.
     * If all 3 attempts fail, logs CRITICAL; the BILL_STATUS_POLL safety-net and
     * stuck-job recovery (@Scheduled every 2 min) provide the backstop.
     */
    private void insertWithRetry(SchedulerJob job, String context) {
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                schedulerJobRepository.insert(job);
                schedulerJobRegistry.register(job.getTenantId());
                return;
            } catch (Exception e) {
                if (attempt == 3) {
                    log.error("CRITICAL: scheduler-job-insert failed after 3 attempts [{}] — " +
                            "BILL_STATUS_POLL safety-net will recover", context, e);
                } else {
                    log.warn("scheduler-job-insert attempt {}/3 failed [{}]: {}", attempt, context, e.getMessage());
                }
            }
        }
    }

    /** Transitions the bill workflow and updates the in-memory status. */
    public void transitionBill(Bill bill, Actions action, BillRequest billRequest) {
        billRequest.setWorkflow(Workflow.builder().action(action.toString()).build());
        try {
            State wfState = workflowUtil.callWorkFlow(
                    workflowUtil.prepareWorkflowRequestForBill(billRequest), billRequest);
            bill.setStatus(Status.fromValue(wfState.getApplicationStatus()));
            log.info("Bill {} transitioned via action={} → status={}", bill.getId(), action, bill.getStatus());
        } catch (Exception e) {
            if (workflowUtil.isRetryableWfError(e)) {
                reconcileBillFromWf(bill, billRequest.getRequestInfo());
            } else {
                throw e;
            }
        }
    }

    /**
     * Overload for service-triggered transitions that build a minimal BillRequest
     * from the scheduler context RequestInfo.
     */
    public void transitionBill(Bill bill, Actions action, RequestInfo requestInfo) {
        BillRequest billRequest = BillRequest.builder()
                .bill(bill)
                .requestInfo(requestInfo)
                .workflow(Workflow.builder().action(action.toString()).build())
                .build();
        try {
            State wfState = workflowUtil.callWorkFlow(
                    workflowUtil.prepareWorkflowRequestForBill(billRequest), billRequest);
            bill.setStatus(Status.fromValue(wfState.getApplicationStatus()));
            log.info("Bill {} service-transitioned via action={} → status={}", bill.getId(), action, bill.getStatus());
        } catch (Exception e) {
            if (workflowUtil.isRetryableWfError(e)) {
                reconcileBillFromWf(bill, requestInfo);
            } else {
                throw e;
            }
        }
    }

    /** Transitions a single bill detail workflow and updates the in-memory status. */
    public void transitionBillDetail(BillDetail detail, Actions action, RequestInfo requestInfo) {
        BillDetailRequest detailRequest = BillDetailRequest.builder()
                .billDetail(detail)
                .businessService(config.getBillDetailBusinessService())
                .workflow(Workflow.builder().action(action.toString()).build())
                .requestInfo(requestInfo)
                .build();
        try {
            State wfState = workflowUtil.callWorkFlow(
                    workflowUtil.prepareWorkflowRequestForBillDetail(detailRequest), detailRequest);
            detail.setStatus(Status.fromValue(wfState.getApplicationStatus()));
            log.info("BillDetail {} transitioned via action={} → status={}", detail.getId(), action, detail.getStatus());
        } catch (Exception e) {
            if (workflowUtil.isRetryableWfError(e)) {
                reconcileDetailFromWf(detail, requestInfo);
            } else {
                throw e;
            }
        }
    }

    /**
     * On INVALID_ACTION for a bill: searches the WF service for the actual current state
     * and updates the in-memory bill status to match.
     * Throws if the WF search itself fails — caller should treat as RETRY.
     */
    private void reconcileBillFromWf(Bill bill, RequestInfo requestInfo) {
        State actual = workflowUtil.searchCurrentWfState(bill.getBillNumber(), bill.getTenantId(), requestInfo);
        if (actual == null) {
            throw new CustomException(ERR_WF_STATE_SEARCH_FAILED, MSG_WF_STATE_SEARCH_FAILED + " bill=" + bill.getId());
        }
        bill.setStatus(Status.fromValue(actual.getApplicationStatus()));
        log.warn("Bill {} reconciled from WF after INVALID_ACTION — status={}", bill.getId(), bill.getStatus());
    }

    /**
     * On INVALID_ACTION for a bill detail: searches the WF service for the actual current state
     * and updates the in-memory detail status to match.
     * Throws if the WF search itself fails — caller should treat as RETRY.
     */
    private void reconcileDetailFromWf(BillDetail detail, RequestInfo requestInfo) {
        State actual = workflowUtil.searchCurrentWfState(detail.getId(), detail.getTenantId(), requestInfo);
        if (actual == null) {
            throw new CustomException(ERR_WF_STATE_SEARCH_FAILED, MSG_WF_STATE_SEARCH_FAILED + " detail=" + detail.getId());
        }
        detail.setStatus(Status.fromValue(actual.getApplicationStatus()));
        log.warn("BillDetail {} reconciled from WF after INVALID_ACTION — status={}", detail.getId(), detail.getStatus());
    }

    /** Validates that the bill's current status is one of the allowed states. */
    private void validateBillInStates(Bill bill, Status... allowedStates) {
        for (Status allowed : allowedStates) {
            if (bill.getStatus() == allowed) return;
        }
        throw new CustomException("INVALID_BILL_STATE",
                "Bill " + bill.getId() + " is not available for this action at its current stage.");
    }

    /**
     * Pushes the full bill (with updated in-memory statuses) to the Kafka bill-update topic
     * so the persister writes the new status values to eg_expense_bill / eg_expense_billdetail.
     */
    public void pushBillUpdate(Bill bill, RequestInfo requestInfo) {
        pushBillUpdate(bill, requestInfo, true);
    }

    public void pushBillUpdate(Bill bill, RequestInfo requestInfo, boolean cache) {
        BillRequest billRequest = BillRequest.builder()
                .requestInfo(requestInfo)
                .bill(bill)
                .build();
        if (cache) {
            billCacheService.put(bill);
        }
        expenseProducer.push(bill.getTenantId(), config.getBillUpdateTopic(), billRequest);
        log.info("Pushed bill update to Kafka for bill id={} status={}", bill.getId(), bill.getStatus());
    }

    /**
     * Pushes one WfUpdate task per eligible BillDetail to the expense-bill-task Kafka topic.
     * TaskConsumer processes these in near-realtime by calling the appropriate WF transition.
     * BILL_STATUS_POLL (inserted by the caller) fires after a short delay and dispatches
     * DETAIL_WF_UPDATE scheduler retry jobs for any details not yet settled.
     */
    private void createWfUpdateTask(Bill bill, String phase, RequestInfo requestInfo,
                                     java.util.function.Predicate<BillDetail> filter) {
        for (BillDetail detail : bill.getBillDetails()) {
            if (!filter.test(detail)) continue;
            Task task = Task.builder()
                    .id(UUID.randomUUID().toString())
                    .tenantId(bill.getTenantId())
                    .billId(bill.getId())
                    .billDetailId(detail.getId())
                    .type(Task.Type.WfUpdate)
                    .status(Status.IN_PROGRESS)
                    .additionalDetails(java.util.Map.of("phase", phase))
                    .auditDetails(bill.getAuditDetails())
                    .build();
            TaskRequest req = TaskRequest.builder()
                    .task(task)
                    .bill(buildSingleDetailBill(bill, detail))
                    .requestInfo(requestInfo)
                    .build();
            expenseProducer.push(bill.getTenantId(), config.getBillTaskTopic(), req);
            log.info("Pushed WfUpdate task to Kafka for bill={} detail={} phase={}", bill.getId(), detail.getId(), phase);
        }
    }

    /** Inserts an immediate DETAIL_WF_UPDATE scheduler retry job for a single detail. */
    public void insertDetailWfUpdateRetryJob(BillDetail detail, Bill bill, String phase, RequestInfo requestInfo) {
        long now = System.currentTimeMillis();
        DetailWfUpdateContext context = DetailWfUpdateContext.builder()
                .billId(bill.getId())
                .billDetailId(detail.getId())
                .phase(phase)
                .requestInfo(requestInfo)
                .build();
        SchedulerJob job = SchedulerJob.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(bill.getTenantId())
                .jobType(SchedulerJobType.DETAIL_WF_UPDATE)
                .referenceId(detail.getId())
                .schedulerStatus(SchedulerJobStatus.PENDING)
                .nextCheckAt(null)
                .attemptCount(0)
                .maxAttempts(config.getSchedulerMaxAttempts())
                .context(context)
                .createdAt(now)
                .updatedAt(now)
                .build();
        insertWithRetry(job, "DETAIL_WF_UPDATE(retry) bill=" + bill.getId() + " detail=" + detail.getId() + " phase=" + phase);
        log.info("Inserted DETAIL_WF_UPDATE retry job for bill={} detail={} phase={}", bill.getId(), detail.getId(), phase);
    }

    private Bill buildSingleDetailBill(Bill bill, BillDetail detail) {
        return Bill.builder()
                .id(bill.getId())
                .tenantId(bill.getTenantId())
                .localityCode(bill.getLocalityCode())
                .billDate(bill.getBillDate())
                .dueDate(bill.getDueDate())
                .totalAmount(bill.getTotalAmount())
                .totalWageAmount(bill.getTotalWageAmount())
                .totalFoodAmount(bill.getTotalFoodAmount())
                .totalTransportAmount(bill.getTotalTransportAmount())
                .totalPaidAmount(bill.getTotalPaidAmount())
                .businessService(bill.getBusinessService())
                .referenceId(bill.getReferenceId())
                .fromPeriod(bill.getFromPeriod())
                .toPeriod(bill.getToPeriod())
                .paymentStatus(bill.getPaymentStatus())
                .status(bill.getStatus())
                .billNumber(bill.getBillNumber())
                .payer(bill.getPayer())
                .additionalDetails(bill.getAdditionalDetails())
                .auditDetails(bill.getAuditDetails())
                .billDetails(Collections.singletonList(detail))
                .build();
    }



    /**
     * Inserts a BILL_STATUS_POLL scheduler job for the given bill and phase.
     *
     * @param delayMs millis from now before the job becomes eligible (0 = immediate).
     *                Use {@code config.getSchedulerSafetyNetDelayMs()} for safety-net jobs.
     */
    public void insertBillStatusPollJob(Bill bill, String phase, RequestInfo requestInfo, long delayMs) {
        insertBillStatusPollJob(bill, phase, requestInfo, delayMs, null);
    }

    public void insertBillStatusPollJob(Bill bill, String phase, RequestInfo requestInfo, long delayMs, String batchId) {
        long now = System.currentTimeMillis();
        BillPollContext context = BillPollContext.builder()
                .phase(phase)
                .requestInfo(requestInfo)
                .batchId(batchId)
                .build();

        SchedulerJob job = SchedulerJob.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(bill.getTenantId())
                .jobType(SchedulerJobType.BILL_STATUS_POLL)
                .referenceId(bill.getId())
                .schedulerStatus(SchedulerJobStatus.PENDING)
                .nextCheckAt(delayMs > 0 ? now + delayMs : null)
                .attemptCount(0)
                .maxAttempts(config.getSchedulerMaxAttempts())
                .context(context)
                .createdAt(now)
                .updatedAt(now)
                .build();

        insertWithRetry(job, "BILL_STATUS_POLL bill=" + bill.getId() + " phase=" + phase);
        log.info("Inserted BILL_STATUS_POLL job for bill={} phase={} batchId={} delayMs={}",
                bill.getId(), phase, batchId, delayMs);
    }

    /**
     * Creates a BILL_BATCH_EMAIL coordinator job for a bulk status-update request.
     * The coordinator fires ONE email after all sibling BILL_STATUS_POLL jobs (same batchId) settle.
     */
    public void insertBillBatchEmailJob(String tenantId, String batchId, List<String> billIds,
                                         String action, RequestInfo requestInfo) {
        String phase = Actions.SEND_FOR_REVIEW.toString().equals(action)
                ? POLL_PHASE_SEND_FOR_REVIEW : POLL_PHASE_SEND_FOR_APPROVAL;
        long now = System.currentTimeMillis();
        BillBatchEmailContext context = BillBatchEmailContext.builder()
                .batchId(batchId)
                .phase(phase)
                .billIds(billIds)
                .requestInfo(requestInfo)
                .build();
        SchedulerJob job = SchedulerJob.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(tenantId)
                .jobType(SchedulerJobType.BILL_BATCH_EMAIL)
                .referenceId(batchId)
                .schedulerStatus(SchedulerJobStatus.PENDING)
                .nextCheckAt(null)
                .attemptCount(0)
                .maxAttempts(config.getSchedulerMaxAttempts())
                .context(context)
                .createdAt(now)
                .updatedAt(now)
                .build();
        insertWithRetry(job, "BILL_BATCH_EMAIL batchId=" + batchId + " bills=" + billIds.size());
        log.info("Inserted BILL_BATCH_EMAIL coordinator job batchId={} billCount={}", batchId, billIds.size());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // New helpers for BILL_STARTED_CHECK design
    // ─────────────────────────────────────────────────────────────────────────

    /** Exposes safety-net delay for use in BillStartedCheckHandler. */
    public long getSafetyNetDelayMs() {
        return config.getSchedulerSafetyNetDelayMs();
    }

    /** Inserts a BILL_STARTED_CHECK coordinator job for the given bill and phase. */
    public void insertBillStartedCheckJob(Bill bill, String phase, RequestInfo requestInfo) {
        long now = System.currentTimeMillis();
        BillStartedCheckContext context = BillStartedCheckContext.builder()
                .billId(bill.getId())
                .tenantId(bill.getTenantId())
                .phase(phase)
                .requestInfo(requestInfo)
                .build();

        SchedulerJob job = SchedulerJob.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(bill.getTenantId())
                .jobType(SchedulerJobType.BILL_STARTED_CHECK)
                .referenceId(bill.getId())
                .schedulerStatus(SchedulerJobStatus.PENDING)
                .nextCheckAt(null)
                .attemptCount(0)
                .maxAttempts(config.getSchedulerMaxAttempts())
                .context(context)
                .createdAt(now)
                .updatedAt(now)
                .build();

        insertWithRetry(job, "BILL_STARTED_CHECK bill=" + bill.getId() + " phase=" + phase);
        log.info("Inserted BILL_STARTED_CHECK job for bill={} phase={}", bill.getId(), phase);
    }

    /**
     * Pushes one VerificationStart task per eligible detail (PENDING_VERIFICATION or VERIFICATION_FAILED).
     * The task consumer transitions the detail → VERIFICATION_IN_PROGRESS.
     */
    private void createVerificationStartTasks(Bill bill, RequestInfo requestInfo) {
        for (BillDetail detail : bill.getBillDetails()) {
            if (detail.getStatus() != Status.PENDING_VERIFICATION
                    && detail.getStatus() != Status.VERIFICATION_FAILED) continue;

            Task task = Task.builder()
                    .id(UUID.randomUUID().toString())
                    .tenantId(bill.getTenantId())
                    .billId(bill.getId())
                    .billDetailId(detail.getId())
                    .type(Task.Type.VerificationStart)
                    .status(Status.IN_PROGRESS)
                    .auditDetails(bill.getAuditDetails())
                    .build();
            TaskRequest req = TaskRequest.builder()
                    .task(task)
                    .bill(buildSingleDetailBill(bill, detail))
                    .requestInfo(requestInfo)
                    .build();
            expenseProducer.push(bill.getTenantId(), config.getBillTaskTopic(), req);
            log.info("Pushed VerificationStart task for bill={} detail={}", bill.getId(), detail.getId());
        }
    }

    /**
     * Pushes one PaymentInitiationStart task per eligible detail (REVIEWED or PAYMENT_FAILED).
     */
    private void createPaymentInitiationStartTasks(Bill bill, RequestInfo requestInfo) {
        createPaymentInitiationStartTasks(bill, requestInfo,
                d -> d.getStatus() == Status.REVIEWED || d.getStatus() == Status.PAYMENT_FAILED);
    }

    private void createPaymentInitiationStartTasks(Bill bill, RequestInfo requestInfo,
                                                    java.util.function.Predicate<BillDetail> filter) {
        for (BillDetail detail : bill.getBillDetails()) {
            if (!filter.test(detail)) continue;

            Task task = Task.builder()
                    .id(UUID.randomUUID().toString())
                    .tenantId(bill.getTenantId())
                    .billId(bill.getId())
                    .billDetailId(detail.getId())
                    .type(Task.Type.PaymentInitiationStart)
                    .status(Status.IN_PROGRESS)
                    .auditDetails(bill.getAuditDetails())
                    .build();
            TaskRequest req = TaskRequest.builder()
                    .task(task)
                    .bill(buildSingleDetailBill(bill, detail))
                    .requestInfo(requestInfo)
                    .build();
            expenseProducer.push(bill.getTenantId(), config.getBillTaskTopic(), req);
            log.info("Pushed PaymentInitiationStart task for bill={} detail={}", bill.getId(), detail.getId());
        }
    }

    /**
     * Pushes a VerificationVerify task for a single detail (called from BILL_STARTED_CHECK).
     * The task consumer calls the payment provider to initiate verification.
     */
    public void pushVerificationVerifyTask(Bill bill, BillDetail detail, RequestInfo requestInfo) {
        Task task = Task.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(bill.getTenantId())
                .billId(bill.getId())
                .billDetailId(detail.getId())
                .type(Task.Type.VerificationVerify)
                .status(Status.IN_PROGRESS)
                .auditDetails(bill.getAuditDetails())
                .build();
        TaskRequest req = TaskRequest.builder()
                .task(task)
                .bill(buildSingleDetailBill(bill, detail))
                .requestInfo(requestInfo)
                .build();
        expenseProducer.push(bill.getTenantId(), config.getBillTaskTopic(), req);
        log.info("Pushed VerificationVerify task for bill={} detail={}", bill.getId(), detail.getId());
    }

    /**
     * Pushes a PaymentInitiationPay task for a single detail (called from BILL_STARTED_CHECK).
     * The task consumer calls the payment provider to initiate payment.
     */
    public void pushPaymentInitiationPayTask(Bill bill, BillDetail detail, RequestInfo requestInfo) {
        Task task = Task.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(bill.getTenantId())
                .billId(bill.getId())
                .billDetailId(detail.getId())
                .type(Task.Type.PaymentInitiationPay)
                .status(Status.IN_PROGRESS)
                .auditDetails(bill.getAuditDetails())
                .build();
        TaskRequest req = TaskRequest.builder()
                .task(task)
                .bill(buildSingleDetailBill(bill, detail))
                .requestInfo(requestInfo)
                .build();
        expenseProducer.push(bill.getTenantId(), config.getBillTaskTopic(), req);
        log.info("Pushed PaymentInitiationPay task for bill={} detail={}", bill.getId(), detail.getId());
    }

    /**
     * Inserts a BILL_DETAILS_TASK_VERIFY_CHECK job for a single detail.
     * Called from TaskConsumer after VerificationVerify task consumer initiates verification.
     */
    public void insertBillDetailsTaskVerifyCheckJob(Bill bill, BillDetail detail, RequestInfo requestInfo) {
        long now = System.currentTimeMillis();
        DetailVerifyContext context = DetailVerifyContext.builder()
                .billId(bill.getId())
                .billDetailId(detail.getId())
                .requestInfo(requestInfo)
                .build();
        SchedulerJob job = SchedulerJob.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(bill.getTenantId())
                .jobType(SchedulerJobType.BILL_DETAILS_TASK_VERIFY_CHECK)
                .referenceId(detail.getId())
                .schedulerStatus(SchedulerJobStatus.PENDING)
                .nextCheckAt(null)
                .attemptCount(0)
                .maxAttempts(config.getSchedulerMaxAttempts())
                .context(context)
                .createdAt(now)
                .updatedAt(now)
                .build();
        insertWithRetry(job, "BILL_DETAILS_TASK_VERIFY_CHECK bill=" + bill.getId() + " detail=" + detail.getId());
        log.info("Inserted BILL_DETAILS_TASK_VERIFY_CHECK job for bill={} detail={}", bill.getId(), detail.getId());
    }

    /**
     * Inserts a BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK job for a single detail.
     * Called from TaskConsumer after PaymentInitiationPay task consumer initiates payment.
     */
    public void insertBillDetailsTaskPaymentStatusCheckJob(Bill bill, BillDetail detail, RequestInfo requestInfo) {
        long now = System.currentTimeMillis();
        DetailVerifyContext context = DetailVerifyContext.builder()
                .billId(bill.getId())
                .billDetailId(detail.getId())
                .requestInfo(requestInfo)
                .build();
        SchedulerJob job = SchedulerJob.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(bill.getTenantId())
                .jobType(SchedulerJobType.BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK)
                .referenceId(detail.getId())
                .schedulerStatus(SchedulerJobStatus.PENDING)
                .nextCheckAt(null)
                .attemptCount(0)
                .maxAttempts(config.getSchedulerMaxAttempts())
                .context(context)
                .createdAt(now)
                .updatedAt(now)
                .build();
        insertWithRetry(job, "BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK bill=" + bill.getId() + " detail=" + detail.getId());
        log.info("Inserted BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK job for bill={} detail={}", bill.getId(), detail.getId());
    }

}
