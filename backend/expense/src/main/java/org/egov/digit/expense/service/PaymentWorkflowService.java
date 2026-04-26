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

    @Autowired
    public PaymentWorkflowService(WorkflowUtil workflowUtil,
                                   BillRepository billRepository,
                                   TaskRepository taskRepository,
                                   SchedulerJobRepository schedulerJobRepository,
                                   SchedulerJobRegistry schedulerJobRegistry,
                                   Configuration config,
                                   ExpenseProducer expenseProducer) {
        this.workflowUtil = workflowUtil;
        this.billRepository = billRepository;
        this.taskRepository = taskRepository;
        this.schedulerJobRepository = schedulerJobRepository;
        this.schedulerJobRegistry = schedulerJobRegistry;
        this.config = config;
        this.expenseProducer = expenseProducer;
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
     * Transitions the bill and eligible bill details to VERIFICATION_IN_PROGRESS,
     * then inserts a BILL_STATUS_POLL scheduler job for the VERIFICATION phase.
     *
     * <p>Pre-condition: Bill must be in PENDING_VERIFICATION or PARTIALLY_VERIFIED.
     * Detail-level transitions target details in PENDING_VERIFICATION or VERIFICATION_FAILED.
     */
    public void verifyBill(BillRequest billRequest) {
        Bill bill = billRequest.getBill();
        RequestInfo requestInfo = billRequest.getRequestInfo();

        validateBillInStates(bill, Status.PENDING_VERIFICATION, Status.PARTIALLY_VERIFIED);

        // Bill → VERIFICATION_IN_PROGRESS (1 synchronous WF call)
        transitionBill(bill, Actions.VERIFY, billRequest);
        pushBillUpdate(bill, requestInfo);

        // Kafka-first: push one Verify task per detail for near-realtime processing.
        // TASK_VERIFY_CHECK crash-recovery jobs are created inside createVerifyTask().
        createVerifyTask(bill, requestInfo);

        // Safety-net aggregator — fires after a short delay; dispatches scheduler retry jobs for any details still unsettled.
        insertBillStatusPollJob(bill, POLL_PHASE_VERIFICATION, requestInfo, config.getSchedulerSafetyNetDelayMs());
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
     * Transitions bill to PAYMENT_IN_PROGRESS and each REVIEWED detail to PAYMENT_IN_PROGRESS.
     *
     * <p>BILL_STATUS_POLL(PAYMENT) is inserted upfront as a safety-net to guarantee the bill
     * exits PAYMENT_IN_PROGRESS even if a pod crash occurs between this call and the
     * BillDetailWfUpdateHandler completing Transfer task creation.
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

        // Kafka-first: push one WfUpdate task per detail for near-realtime processing.
        createWfUpdateTask(bill, POLL_PHASE_PAYMENT_INITIATION, requestInfo,
                d -> d.getStatus() == Status.REVIEWED);

        // Safety-net aggregator for PAYMENT phase — fires after a long delay to handle the case
        // where a pod crash prevents Transfer tasks from being created or TASK_STATUS_CHECK from
        // completing the bill-level transition.
        insertBillStatusPollJob(bill, POLL_PHASE_PAYMENT, requestInfo, config.getSchedulerSafetyNetDelayMs());
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

        // Kafka-first: push one WfUpdate task per detail for near-realtime processing.
        createWfUpdateTask(bill, POLL_PHASE_PAYMENT_INITIATION, requestInfo,
                d -> d.getStatus() == Status.PAYMENT_FAILED);

        // Safety-net aggregator inserted upfront for both initial payment and retry.
        insertBillStatusPollJob(bill, POLL_PHASE_PAYMENT, requestInfo, config.getSchedulerSafetyNetDelayMs());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────

    /** Fetches a fresh bill (with details) from the DB by bill ID. Excludes soft-deleted (INACTIVE) bills. */
    public Bill fetchBillWithDetails(String billId, String tenantId, RequestInfo requestInfo) {
        BillSearchRequest searchRequest = BillSearchRequest.builder()
                .requestInfo(requestInfo)
                .billCriteria(BillCriteria.builder()
                        .tenantId(tenantId)
                        .ids(Collections.singleton(billId))
                        .statusesNot(Collections.singletonList(Status.INACTIVE.toString()))  // RC-13
                        .build())
                .build();
        List<Bill> bills = billRepository.search(searchRequest, false);
        return bills.isEmpty() ? null : bills.get(0);
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
        State wfState = workflowUtil.callWorkFlow(
                workflowUtil.prepareWorkflowRequestForBill(billRequest), billRequest);
        bill.setStatus(Status.fromValue(wfState.getApplicationStatus()));
        log.info("Bill {} transitioned via action={} → status={}",
                bill.getId(), action, bill.getStatus());
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
        State wfState = workflowUtil.callWorkFlow(
                workflowUtil.prepareWorkflowRequestForBill(billRequest), billRequest);
        bill.setStatus(Status.fromValue(wfState.getApplicationStatus()));
        log.info("Bill {} service-transitioned via action={} → status={}",
                bill.getId(), action, bill.getStatus());
    }

    /** Transitions a single bill detail workflow and updates the in-memory status. */
    public void transitionBillDetail(BillDetail detail, Actions action, RequestInfo requestInfo) {
        BillDetailRequest detailRequest = BillDetailRequest.builder()
                .billDetail(detail)
                .businessService(config.getBillDetailBusinessService())
                .workflow(Workflow.builder().action(action.toString()).build())
                .requestInfo(requestInfo)
                .build();
        State wfState = workflowUtil.callWorkFlow(
                workflowUtil.prepareWorkflowRequestForBillDetail(detailRequest), detailRequest);
        detail.setStatus(Status.fromValue(wfState.getApplicationStatus()));
        log.info("BillDetail {} transitioned via action={} → status={}",
                detail.getId(), action, detail.getStatus());
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
        BillRequest billRequest = BillRequest.builder()
                .requestInfo(requestInfo)
                .bill(bill)
                .build();
        expenseProducer.push(bill.getTenantId(), config.getBillUpdateTopic(), billRequest);
        log.info("Pushed bill update to Kafka for bill id={} status={}", bill.getId(), bill.getStatus());
    }

    /**
     * Pushes a Transfer task to the expense-bill-task Kafka topic so that TaskConsumer
     * triggers MTN disbursement for all PAYMENT_IN_PROGRESS details. Called by
     * BillDetailWfUpdateHandler after all PAYMENT_INITIATION transitions succeed.
     * Idempotent — deduplicates against any existing IN_PROGRESS Transfer task.
     */
    public void createTransferTask(Bill bill, RequestInfo requestInfo) {
        int pushed = 0;
        for (BillDetail detail : bill.getBillDetails()) {
            Task existing = taskRepository.searchTask(Task.builder()
                    .billId(bill.getId())
                    .billDetailId(detail.getId())
                    .tenantId(bill.getTenantId())
                    .type(Task.Type.Transfer)
                    .status(Status.IN_PROGRESS)
                    .build());
            if (existing != null) {
                long ageMs = System.currentTimeMillis() -
                        (existing.getAuditDetails() != null ? existing.getAuditDetails().getLastModifiedTime() : 0L);
                if (ageMs < config.getSchedulerStuckThresholdMs()) {
                    log.info("Transfer task already exists for bill={} detail={} — skipping", bill.getId(), detail.getId());
                    continue;
                }
                log.warn("Stale IN_PROGRESS transfer task {} for bill={} detail={} (age={}ms) — marking DONE and re-creating",
                        existing.getId(), bill.getId(), detail.getId(), ageMs);
                existing.setStatus(Status.DONE);
                expenseProducer.push(bill.getTenantId(), config.getTaskUpdateTopic(), existing);
            }
            Task task = Task.builder()
                    .id(UUID.randomUUID().toString())
                    .tenantId(bill.getTenantId())
                    .billId(bill.getId())
                    .billDetailId(detail.getId())
                    .type(Task.Type.Transfer)
                    .status(Status.IN_PROGRESS)
                    .auditDetails(bill.getAuditDetails())
                    .build();
            TaskRequest taskRequest = TaskRequest.builder()
                    .task(task)
                    .bill(buildSingleDetailBill(bill, detail))
                    .requestInfo(requestInfo)
                    .build();
            expenseProducer.push(bill.getTenantId(), config.getBillTaskTopic(), taskRequest);
            log.info("Pushed Transfer task to Kafka for bill={} detail={}", bill.getId(), detail.getId());
            pushed++;
        }
        // BILL_STATUS_POLL(PAYMENT) is now inserted upfront in initiatePayment/retryPayment,
        // so no deferred insertion is needed here. createTransferTask is called after all
        // details have been transitioned to PAYMENT_IN_PROGRESS.
    }

    /**
     * Pushes one Verify task per BillDetail to the expense-bill-task Kafka topic so that
     * TaskConsumer triggers MTN verification asynchronously per detail. Idempotent —
     * deduplicates against any existing IN_PROGRESS Verify task for the same (bill, detail).
     * Also inserts a TASK_VERIFY_CHECK crash-recovery job for each task so that a pod
     * crash after Kafka commit but before TaskConsumer processes the message is handled.
     */
    private void createVerifyTask(Bill bill, RequestInfo requestInfo) {
        for (BillDetail detail : bill.getBillDetails()) {
            Task existing = taskRepository.searchTask(Task.builder()
                    .billId(bill.getId())
                    .billDetailId(detail.getId())
                    .tenantId(bill.getTenantId())
                    .type(Task.Type.Verify)
                    .status(Status.IN_PROGRESS)
                    .build());
            if (existing != null) {
                long ageMs = System.currentTimeMillis() -
                        (existing.getAuditDetails() != null ? existing.getAuditDetails().getLastModifiedTime() : 0L);
                if (ageMs < config.getSchedulerStuckThresholdMs()) {
                    log.info("Verify task already exists for bill={} detail={} — skipping", bill.getId(), detail.getId());
                    continue;
                }
                log.warn("Stale IN_PROGRESS verify task {} for bill={} detail={} (age={}ms) — marking DONE and re-creating",
                        existing.getId(), bill.getId(), detail.getId(), ageMs);
                existing.setStatus(Status.DONE);
                expenseProducer.push(bill.getTenantId(), config.getTaskUpdateTopic(), existing);
            }
            String taskId = UUID.randomUUID().toString();
            Task task = Task.builder()
                    .id(taskId)
                    .tenantId(bill.getTenantId())
                    .billId(bill.getId())
                    .billDetailId(detail.getId())
                    .type(Task.Type.Verify)
                    .status(Status.IN_PROGRESS)
                    .auditDetails(bill.getAuditDetails())
                    .build();
            TaskRequest taskRequest = TaskRequest.builder()
                    .task(task)
                    .bill(buildSingleDetailBill(bill, detail))
                    .requestInfo(requestInfo)
                    .build();
            expenseProducer.push(bill.getTenantId(), config.getBillTaskTopic(), taskRequest);
            log.info("Pushed Verify task to Kafka for bill={} detail={}", bill.getId(), detail.getId());

            // TASK_VERIFY_CHECK: fires after a delay so that if the Kafka consumer crashes before
            // processing this task, the scheduler re-runs verification rather than leaving
            // the detail stuck in VERIFICATION_IN_PROGRESS.
            insertTaskVerifyCheckJob(taskId, bill.getTenantId(), requestInfo);
        }
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

    /** Inserts an immediate DETAIL_VERIFY scheduler retry job for a single detail. */
    public void insertDetailVerifyRetryJob(BillDetail detail, Bill bill, RequestInfo requestInfo) {
        long now = System.currentTimeMillis();
        DetailVerifyContext context = DetailVerifyContext.builder()
                .billId(bill.getId())
                .billDetailId(detail.getId())
                .requestInfo(requestInfo)
                .build();
        SchedulerJob job = SchedulerJob.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(bill.getTenantId())
                .jobType(SchedulerJobType.DETAIL_VERIFY)
                .referenceId(detail.getId())
                .schedulerStatus(SchedulerJobStatus.PENDING)
                .nextCheckAt(null)
                .attemptCount(0)
                .maxAttempts(config.getSchedulerMaxAttempts())
                .context(context)
                .createdAt(now)
                .updatedAt(now)
                .build();
        insertWithRetry(job, "DETAIL_VERIFY(retry) bill=" + bill.getId() + " detail=" + detail.getId());
        log.info("Inserted DETAIL_VERIFY retry job for bill={} detail={}", bill.getId(), detail.getId());
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

    /** Inserts a BILL_DETAIL_WF_UPDATE scheduler job for the given bill and phase. */
    private void insertBillDetailWfUpdateJob(Bill bill, String phase, RequestInfo requestInfo) {
        long now = System.currentTimeMillis();
        BillDetailWfUpdateContext context = BillDetailWfUpdateContext.builder()
                .phase(phase)
                .requestInfo(requestInfo)
                .build();

        SchedulerJob job = SchedulerJob.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(bill.getTenantId())
                .jobType(SchedulerJobType.BILL_DETAIL_WF_UPDATE)
                .referenceId(bill.getId())
                .schedulerStatus(SchedulerJobStatus.PENDING)
                .nextCheckAt(null)
                .attemptCount(0)
                .maxAttempts(config.getSchedulerMaxAttempts())
                .context(context)
                .createdAt(now)
                .updatedAt(now)
                .build();

        insertWithRetry(job, "BILL_DETAIL_WF_UPDATE bill=" + bill.getId() + " phase=" + phase);
        log.info("Inserted BILL_DETAIL_WF_UPDATE job for bill={} phase={}", bill.getId(), phase);
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

    /** Inserts a TASK_VERIFY_CHECK job for a Kafka-dispatched Verify task (legacy path). */
    private void insertTaskVerifyCheckJob(String taskId, String tenantId, RequestInfo requestInfo) {
        long now = System.currentTimeMillis();
        SchedulerJob job = SchedulerJob.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(tenantId)
                .jobType(SchedulerJobType.TASK_VERIFY_CHECK)
                .referenceId(taskId)
                .schedulerStatus(SchedulerJobStatus.PENDING)
                .nextCheckAt(now + config.getSchedulerSafetyNetDelayMs())
                .attemptCount(0)
                .maxAttempts(config.getSchedulerMaxAttempts())
                .context(requestInfo)
                .createdAt(now)
                .updatedAt(now)
                .build();

        insertWithRetry(job, "TASK_VERIFY_CHECK taskId=" + taskId);
        log.info("Inserted TASK_VERIFY_CHECK job for taskId={}", taskId);
    }

    /** Inserts one DETAIL_VERIFY scheduler job per BillDetail. */
    private void insertDetailVerifyJobsForAllDetails(Bill bill, RequestInfo requestInfo) {
        for (BillDetail detail : bill.getBillDetails()) {
            long now = System.currentTimeMillis();
            DetailVerifyContext context = DetailVerifyContext.builder()
                    .billId(bill.getId())
                    .billDetailId(detail.getId())
                    .requestInfo(requestInfo)
                    .build();

            SchedulerJob job = SchedulerJob.builder()
                    .id(UUID.randomUUID().toString())
                    .tenantId(bill.getTenantId())
                    .jobType(SchedulerJobType.DETAIL_VERIFY)
                    .referenceId(detail.getId())
                    .schedulerStatus(SchedulerJobStatus.PENDING)
                    .nextCheckAt(null)
                    .attemptCount(0)
                    .maxAttempts(config.getSchedulerMaxAttempts())
                    .context(context)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();

            insertWithRetry(job, "DETAIL_VERIFY bill=" + bill.getId() + " detail=" + detail.getId());
            log.info("Inserted DETAIL_VERIFY job for bill={} detail={}", bill.getId(), detail.getId());
        }
    }

    /**
     * Inserts one DETAIL_WF_UPDATE scheduler job per eligible BillDetail.
     *
     * @param eligibilityFilter predicate selecting which details to insert jobs for
     */
    private void insertDetailWfUpdateJobsForEligibleDetails(
            Bill bill, String phase, RequestInfo requestInfo,
            java.util.function.Predicate<org.egov.digit.expense.web.models.BillDetail> eligibilityFilter) {

        for (BillDetail detail : bill.getBillDetails()) {
            if (!eligibilityFilter.test(detail)) continue;
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

            insertWithRetry(job, "DETAIL_WF_UPDATE bill=" + bill.getId() + " detail=" + detail.getId() + " phase=" + phase);
            log.info("Inserted DETAIL_WF_UPDATE job for bill={} detail={} phase={}", bill.getId(), detail.getId(), phase);
        }
    }
}
