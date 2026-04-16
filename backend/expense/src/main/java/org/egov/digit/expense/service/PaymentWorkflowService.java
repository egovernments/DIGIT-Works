package org.egov.digit.expense.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.workflow.State;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.repository.SchedulerJobRepository;
import org.egov.digit.expense.service.scheduler.SchedulerJobRegistry;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.WorkflowNotificationType;
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
    private final SchedulerJobRepository schedulerJobRepository;
    private final SchedulerJobRegistry schedulerJobRegistry;
    private final Configuration config;
    private final ExpenseProducer expenseProducer;
    private final WorkflowEmailNotificationService workflowEmailNotificationService;

    @Autowired
    public PaymentWorkflowService(WorkflowUtil workflowUtil,
                                   BillRepository billRepository,
                                   SchedulerJobRepository schedulerJobRepository,
                                   SchedulerJobRegistry schedulerJobRegistry,
                                   Configuration config,
                                   ExpenseProducer expenseProducer,
                                   WorkflowEmailNotificationService workflowEmailNotificationService) {
        this.workflowUtil = workflowUtil;
        this.billRepository = billRepository;
        this.schedulerJobRepository = schedulerJobRepository;
        this.schedulerJobRegistry = schedulerJobRegistry;
        this.config = config;
        this.expenseProducer = expenseProducer;
        this.workflowEmailNotificationService = workflowEmailNotificationService;
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

        // Bill → VERIFICATION_IN_PROGRESS (locked)
        transitionBill(bill, Actions.VERIFY, billRequest);

        // Eligible details → VERIFICATION_IN_PROGRESS
        for (BillDetail detail : bill.getBillDetails()) {
            if (detail.getStatus() == Status.PENDING_VERIFICATION
                    || detail.getStatus() == Status.VERIFICATION_FAILED) {
                transitionBillDetail(detail, Actions.VERIFY, requestInfo);
            }
        }

        // Persist updated bill + detail statuses to DB via Kafka
        pushBillUpdate(bill, requestInfo);

        insertBillStatusPollJob(bill, "VERIFICATION", requestInfo);
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
                    "Cannot ignore errors when bill details are still in PENDING_VERIFICATION");
        }

        // Bill → IGNORING_ERRORS_IN_PROGRESS (locked)
        transitionBill(bill, Actions.IGNORE_ERRORS_AND_VERIFY, billRequest);

        // VERIFICATION_FAILED details → VERIFIED (direct, no external call)
        for (BillDetail detail : bill.getBillDetails()) {
            if (detail.getStatus() == Status.VERIFICATION_FAILED) {
                transitionBillDetail(detail, Actions.IGNORE_ERRORS_AND_VERIFY, requestInfo);
            }
        }

        // Persist updated bill + detail statuses to DB via Kafka
        pushBillUpdate(bill, requestInfo);

        insertBillStatusPollJob(bill, "IGNORE_ERRORS", requestInfo);
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
        Bill bill = billRequest.getBill();
        RequestInfo requestInfo = billRequest.getRequestInfo();

        validateBillInStates(bill, Status.FULLY_VERIFIED);

        // Bill → SENDING_FOR_REVIEW (locked)
        transitionBill(bill, Actions.SEND_FOR_REVIEW, billRequest);

        // VERIFIED details → UNDER_REVIEW
        for (BillDetail detail : bill.getBillDetails()) {
            if (detail.getStatus() == Status.VERIFIED) {
                transitionBillDetail(detail, Actions.SEND_FOR_REVIEW, requestInfo);
            }
        }

        // Persist updated bill + detail statuses to DB via Kafka
        pushBillUpdate(bill, requestInfo);

        // Notify payment reviewers by email (fire-and-forget)
        workflowEmailNotificationService.notify(billRequest, WorkflowNotificationType.REVIEW);

        insertBillStatusPollJob(bill, "SEND_FOR_REVIEW", requestInfo);
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
        Bill bill = billRequest.getBill();
        RequestInfo requestInfo = billRequest.getRequestInfo();

        validateBillInStates(bill, Status.UNDER_REVIEW);

        // Bill → REVIEW_IN_PROGRESS (locked)
        transitionBill(bill, Actions.SEND_FOR_APPROVAL, billRequest);

        // UNDER_REVIEW details → REVIEWED
        for (BillDetail detail : bill.getBillDetails()) {
            if (detail.getStatus() == Status.UNDER_REVIEW) {
                transitionBillDetail(detail, Actions.SEND_FOR_APPROVAL, requestInfo);
            }
        }

        // Persist updated bill + detail statuses to DB via Kafka
        pushBillUpdate(bill, requestInfo);

        // Notify payment approvers by email (fire-and-forget)
        workflowEmailNotificationService.notify(billRequest, WorkflowNotificationType.APPROVAL);

        // Store reviewer's RequestInfo so the poll job uses the correct auth context
        insertBillStatusPollJob(bill, "REVIEW", requestInfo);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Phase 6 — Payment Initiation
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Transitions bill to PAYMENT_IN_PROGRESS and each REVIEWED detail to PAYMENT_IN_PROGRESS.
     * Does NOT insert a BILL_STATUS_POLL job — the existing TaskStatusCheckHandler
     * (via MTNService) handles bill-level payment outcome aggregation.
     *
     * <p>Pre-condition: Bill in REVIEWED. Actor: PAYMENT_APPROVER.
     */
    public void initiatePayment(BillRequest billRequest) {
        Bill bill = billRequest.getBill();
        RequestInfo requestInfo = billRequest.getRequestInfo();

        validateBillInStates(bill, Status.REVIEWED);

        // Bill → PAYMENT_IN_PROGRESS (locked)
        transitionBill(bill, Actions.PAYMENT_INITIATION, billRequest);

        // REVIEWED details → PAYMENT_IN_PROGRESS
        for (BillDetail detail : bill.getBillDetails()) {
            if (detail.getStatus() == Status.REVIEWED) {
                transitionBillDetail(detail, Actions.PAYMENT_INITIATION, requestInfo);
            }
        }

        // Persist updated bill + detail statuses to DB via Kafka
        pushBillUpdate(bill, requestInfo);
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

        // Only PAYMENT_FAILED details — do NOT re-process already-PAID ones
        for (BillDetail detail : bill.getBillDetails()) {
            if (detail.getStatus() == Status.PAYMENT_FAILED) {
                transitionBillDetail(detail, Actions.PAYMENT_INITIATION, requestInfo);
            }
        }

        // Persist updated bill + detail statuses to DB via Kafka
        pushBillUpdate(bill, requestInfo);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────

    /** Fetches a fresh bill (with details) from the DB by bill ID. */
    public Bill fetchBillWithDetails(String billId, String tenantId, RequestInfo requestInfo) {
        BillSearchRequest searchRequest = BillSearchRequest.builder()
                .requestInfo(requestInfo)
                .billCriteria(BillCriteria.builder()
                        .tenantId(tenantId)
                        .ids(Collections.singleton(billId))
                        .build())
                .build();
        List<Bill> bills = billRepository.search(searchRequest, false);
        return bills.isEmpty() ? null : bills.get(0);
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
                "Bill " + bill.getId() + " is in state " + bill.getStatus()
                        + " — expected one of: " + java.util.Arrays.toString(allowedStates));
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

    /** Inserts a BILL_STATUS_POLL scheduler job for the given bill and phase. */
    private void insertBillStatusPollJob(Bill bill, String phase, RequestInfo requestInfo) {
        long now = System.currentTimeMillis();
        BillPollContext context = BillPollContext.builder()
                .phase(phase)
                .requestInfo(requestInfo)
                .build();

        SchedulerJob job = SchedulerJob.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(bill.getTenantId())
                .jobType(SchedulerJobType.BILL_STATUS_POLL)
                .referenceId(bill.getId())
                .schedulerStatus(SchedulerJobStatus.PENDING)
                .nextCheckAt(null)
                .attemptCount(0)
                .maxAttempts(config.getSchedulerMaxAttempts())
                .context(context)
                .createdAt(now)
                .updatedAt(now)
                .build();

        schedulerJobRepository.insert(job);
        schedulerJobRegistry.register(bill.getTenantId());
        log.info("Inserted BILL_STATUS_POLL job for bill={} phase={}", bill.getId(), phase);
    }
}
