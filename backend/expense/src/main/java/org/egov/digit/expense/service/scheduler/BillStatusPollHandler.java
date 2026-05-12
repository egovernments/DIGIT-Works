package org.egov.digit.expense.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.service.WorkflowEmailNotificationService;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillPollContext;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.digit.expense.web.models.SchedulerJob;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.digit.expense.web.models.enums.WorkflowNotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.egov.digit.expense.config.Constants.*;

/**
 * Handles {@link SchedulerJobType#BILL_STATUS_POLL} jobs.
 *
 * <p>Pure aggregation poll — reads bill detail statuses from DB and drives bill-level
 * workflow transitions when all details have settled. Does NOT perform any detail WF
 * transitions itself (that is {@link BillDetailWfUpdateHandler}'s responsibility).
 *
 * <p>Phases: VERIFICATION, IGNORE_ERRORS, SEND_FOR_REVIEW, REVIEW.
 *
 * <p>Context JSONB: serialised {@link BillPollContext}.
 */
@Component
@Slf4j
public class BillStatusPollHandler implements SchedulerJobHandler {

    private final PaymentWorkflowService paymentWorkflowService;
    private final WorkflowUtil workflowUtil;
    private final ObjectMapper objectMapper;
    private final WorkflowEmailNotificationService workflowEmailNotificationService;

    @Autowired
    public BillStatusPollHandler(PaymentWorkflowService paymentWorkflowService,
                                  WorkflowUtil workflowUtil,
                                  ObjectMapper objectMapper,
                                  WorkflowEmailNotificationService workflowEmailNotificationService) {
        this.paymentWorkflowService = paymentWorkflowService;
        this.workflowUtil = workflowUtil;
        this.objectMapper = objectMapper;
        this.workflowEmailNotificationService = workflowEmailNotificationService;
    }

    @Override
    public SchedulerJobType getJobType() {
        return SchedulerJobType.BILL_STATUS_POLL;
    }

    @Override
    public SchedulerJobResult handle(SchedulerJob job) {
        String billId = job.getReferenceId();
        String tenantId = job.getTenantId();

        try {
            BillPollContext ctx = objectMapper.convertValue(job.getContext(), BillPollContext.class);
            RequestInfo requestInfo = ctx.getRequestInfo();
            String phase = ctx.getPhase();
            String batchId = ctx.getBatchId(); // null for single-bill; coordinator owns email when set

            // Cache-first (bypassCache=false): detail cache holds the post-WF status written before
            // each Kafka push, so it is always ahead of DB during persister lag.  Using bypassCache=true
            // here would see stale VERIFICATION_IN_PROGRESS entries and dispatch spurious
            // BILL_DETAILS_TASK_VERIFY_CHECK retry jobs for details the fast-path already settled.
            Bill bill = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo, false);
            if (bill == null) {
                log.warn("Bill {} not found for tenant {} — marking job FAILED", billId, tenantId);
                return SchedulerJobResult.FAILED;
            }

            return switch (phase) {
                case POLL_PHASE_VERIFICATION    -> handleVerificationPoll(billId, tenantId, bill, requestInfo);
                case POLL_PHASE_IGNORE_ERRORS   -> handleIgnoreErrorsPoll(billId, tenantId, bill, requestInfo);
                case POLL_PHASE_SEND_FOR_REVIEW -> handleSendForReviewPoll(billId, tenantId, bill, requestInfo, batchId);
                case POLL_PHASE_REVIEW          -> handleReviewPoll(billId, tenantId, bill, requestInfo, batchId);
                case POLL_PHASE_PAYMENT         -> handlePaymentPoll(billId, tenantId, bill, requestInfo);
                default -> {
                    log.error("Unknown poll phase '{}' for bill {} — marking FAILED", phase, billId);
                    yield SchedulerJobResult.FAILED;
                }
            };

        } catch (Exception e) {
            log.error("Error in BILL_STATUS_POLL for billId={}, tenantId={}", billId, tenantId, e);
            return SchedulerJobResult.RETRY;
        }
    }

    /**
     * Called when this job exceeds maxAttempts. Applies the appropriate FAIL action on the bill
     * so it is not left stranded in an in-progress locked state. Single attempt — no blocking
     * sleep on the scheduler thread. If the transition fails transiently, the scheduler's
     * stuck-job recovery will reset this job for another cycle.
     *
     * <pre>
     * IGNORING_ERRORS_IN_PROGRESS → FAIL → PARTIALLY_VERIFIED
     * SENDING_FOR_REVIEW          → FAIL → FULLY_VERIFIED
     * REVIEW_IN_PROGRESS          → FAIL → UNDER_REVIEW
     * </pre>
     */
    @Override
    public void onMaxAttemptsExceeded(SchedulerJob job) {
        String billId = job.getReferenceId();
        String tenantId = job.getTenantId();
        try {
            BillPollContext ctx = objectMapper.convertValue(job.getContext(), BillPollContext.class);
            RequestInfo requestInfo = ctx.getRequestInfo();
            String phase = ctx.getPhase();

            Bill bill = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo, true);
            if (bill == null) return;

            if (POLL_PHASE_PAYMENT.equals(phase)) {
                forceFailPaymentInProgressDetails(bill, billId, tenantId, requestInfo);
                return;
            }

            Actions failAction = switch (phase) {
                case POLL_PHASE_VERIFICATION    -> Actions.FAILED;
                case POLL_PHASE_IGNORE_ERRORS   -> Actions.FAIL;
                case POLL_PHASE_SEND_FOR_REVIEW -> Actions.FAIL;
                case POLL_PHASE_REVIEW          -> Actions.FAIL;
                default                         -> null;
            };

            if (failAction != null) {
                // EC-8: only compensate if bill is still in the expected intermediate state
                Status expectedIntermediate = resolveExpectedIntermediateStatus(phase);
                if (expectedIntermediate != null && bill.getStatus() != expectedIntermediate) {
                    log.info("BILL_STATUS_POLL: bill={} already exited intermediate {} (now={}) — skipping compensation",
                            billId, expectedIntermediate, bill.getStatus());
                    return;
                }
                try {
                    // transitionBill reconciles INVALID_ACTION internally; remaining exceptions are genuine
                    paymentWorkflowService.transitionBill(bill, failAction, requestInfo);
                    enrichAuditForSystemUpdate(bill, requestInfo);
                    paymentWorkflowService.pushBillUpdate(bill, requestInfo);
                    log.warn("BILL_STATUS_POLL max attempts exceeded — applied {} for bill={} phase={}",
                            failAction, billId, phase);
                } catch (Exception ex) {
                    log.error("CRITICAL: onMaxAttemptsExceeded FAIL action failed for bill={} phase={}", billId, phase, ex);
                }
            }
        } catch (Exception e) {
            log.error("Error in onMaxAttemptsExceeded for bill={} job={}", billId, job.getId(), e);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Phase poll handlers — pure status aggregation, no detail WF transitions
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * VERIFICATION: waits for TaskConsumer (MTN verify) to move all details out of
     * PENDING_VERIFICATION and VERIFICATION_IN_PROGRESS.
     *
     * <pre>
     * All VERIFIED             → FULLY_VERIFY   → FULLY_VERIFIED
     * Some VERIFIED, some FAILED → PARTIALLY_VERIFY → PARTIALLY_VERIFIED
     * All FAILED               → FAILED         → PENDING_VERIFICATION
     * </pre>
     */
    // RC-9: billId/tenantId now passed so we can re-fetch immediately before transition
    private SchedulerJobResult handleVerificationPoll(String billId, String tenantId, Bill bill, RequestInfo requestInfo) {
        // RC-5: VERIFICATION_FAILED is a settled terminal-of-phase — do NOT count it as pending
        long pending    = countDetailsInState(bill, Status.PENDING_VERIFICATION);
        long inProgress = countDetailsInState(bill, Status.VERIFICATION_IN_PROGRESS);

        if (pending > 0 || inProgress > 0) {
            // Re-attempt via Kafka first — the task consumer calls the provider, settles immediately
            // if synchronous (BANK), or creates BILL_DETAILS_TASK_VERIFY_CHECK only if still async-pending (MTN).
            bill.getBillDetails().stream()
                    .filter(d -> d.getStatus() == Status.PENDING_VERIFICATION
                              || d.getStatus() == Status.VERIFICATION_IN_PROGRESS)
                    .forEach(d -> paymentWorkflowService.pushVerificationVerifyTask(bill, d, requestInfo));
            log.debug("Bill {} VERIFICATION: {} pending, {} in-progress — re-pushed VerificationVerify tasks",
                    bill.getId(), pending, inProgress);
            return SchedulerJobResult.RETRY;
        }

        long verified = countDetailsInState(bill, Status.VERIFIED);
        long total    = bill.getBillDetails().size();

        Actions action = (verified == total) ? Actions.FULLY_VERIFY
                       : (verified > 0)      ? Actions.PARTIALLY_VERIFY
                       :                       Actions.FAILED;

        // RC-9: re-fetch just before commit to capture any concurrent detail updates
        Bill fresh = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo, true);
        if (fresh == null) { log.warn("Bill {} vanished before VERIFICATION transition — retrying", billId); return SchedulerJobResult.RETRY; }
        if (fresh.getStatus() == Status.VERIFICATION_IN_PROGRESS) {
            paymentWorkflowService.transitionBill(fresh, action, requestInfo);
            paymentWorkflowService.pushBillUpdate(fresh, requestInfo);
        }
        return SchedulerJobResult.DONE;
    }

    /**
     * IGNORE_ERRORS: waits for {@link BillDetailWfUpdateHandler} to move all
     * VERIFICATION_FAILED details to VERIFIED.
     *
     * <pre>
     * All VERIFIED/PAID → COMPLETE → FULLY_VERIFIED
     * Otherwise         → RETRY
     * </pre>
     */
    private SchedulerJobResult handleIgnoreErrorsPoll(String billId, String tenantId, Bill bill, RequestInfo requestInfo) {
        boolean anyFailed = bill.getBillDetails().stream()
                .anyMatch(d -> d.getStatus() == Status.VERIFICATION_FAILED);
        if (anyFailed) {
            bill.getBillDetails().stream()
                    .filter(d -> d.getStatus() == Status.VERIFICATION_FAILED)
                    .forEach(d -> paymentWorkflowService.insertDetailWfUpdateRetryJob(d, bill, POLL_PHASE_IGNORE_ERRORS, requestInfo));
            log.debug("Bill {} IGNORE_ERRORS: detail(s) still VERIFICATION_FAILED — dispatched retry jobs", bill.getId());
            return SchedulerJobResult.RETRY;
        }

        boolean allVerified = bill.getBillDetails().stream()
                .allMatch(d -> d.getStatus() == Status.VERIFIED || d.getStatus() == Status.PAID);
        if (allVerified) {
            // RC-9: re-fetch before commit
            Bill fresh = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo, true);
            if (fresh == null) { log.warn("Bill {} vanished before IGNORE_ERRORS transition — retrying", billId); return SchedulerJobResult.RETRY; }
            if (fresh.getStatus() == Status.IGNORING_ERRORS_IN_PROGRESS) {
                paymentWorkflowService.transitionBill(fresh, Actions.COMPLETE, requestInfo);
                paymentWorkflowService.pushBillUpdate(fresh, requestInfo);
            } else {
                log.info("Bill {} already past IGNORING_ERRORS_IN_PROGRESS (now={}) — skipping transition", billId, fresh.getStatus());
            }
            return SchedulerJobResult.DONE;
        }
        return SchedulerJobResult.RETRY;
    }

    /**
     * SEND_FOR_REVIEW: waits for {@link BillDetailWfUpdateHandler} to move all
     * VERIFIED details to UNDER_REVIEW.
     *
     * <pre>
     * All UNDER_REVIEW/advanced → COMPLETE → UNDER_REVIEW
     * Otherwise                 → RETRY
     * </pre>
     */
    private SchedulerJobResult handleSendForReviewPoll(String billId, String tenantId, Bill bill, RequestInfo requestInfo, String batchId) {
        boolean anyVerified = bill.getBillDetails().stream()
                .anyMatch(d -> d.getStatus() == Status.VERIFIED);
        if (anyVerified) {
            bill.getBillDetails().stream()
                    .filter(d -> d.getStatus() == Status.VERIFIED)
                    .forEach(d -> paymentWorkflowService.insertDetailWfUpdateRetryJob(d, bill, POLL_PHASE_SEND_FOR_REVIEW, requestInfo));
            log.debug("Bill {} SEND_FOR_REVIEW: detail(s) still VERIFIED — dispatched retry jobs", bill.getId());
            return SchedulerJobResult.RETRY;
        }

        boolean allUnderReview = bill.getBillDetails().stream()
                .allMatch(d -> isAtOrBeyondUnderReview(d.getStatus()));
        if (allUnderReview) {
            // RC-9: re-fetch before commit
            Bill fresh = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo, true);
            if (fresh == null) { log.warn("Bill {} vanished before SEND_FOR_REVIEW transition — retrying", billId); return SchedulerJobResult.RETRY; }
            if (fresh.getStatus() != Status.SENDING_FOR_REVIEW) {
                log.info("Bill {} already past SENDING_FOR_REVIEW (now={}) — skipping transition", billId, fresh.getStatus());
                return SchedulerJobResult.DONE;
            }
            paymentWorkflowService.transitionBill(fresh, Actions.COMPLETE, requestInfo);
            paymentWorkflowService.pushBillUpdate(fresh, requestInfo);
            if (batchId == null) {
                workflowEmailNotificationService.notify(
                        BillRequest.builder().bill(fresh).requestInfo(requestInfo).build(),
                        WorkflowNotificationType.REVIEW);
            } else {
                log.debug("Bill {} settled UNDER_REVIEW — batch email coordinator will handle notify (batchId={})", billId, batchId);
            }
            return SchedulerJobResult.DONE;
        }
        return SchedulerJobResult.RETRY;
    }

    /**
     * REVIEW: waits for {@link BillDetailWfUpdateHandler} to move all UNDER_REVIEW
     * details to REVIEWED.
     *
     * <pre>
     * All REVIEWED/advanced → COMPLETE → REVIEWED
     * Otherwise             → RETRY
     * </pre>
     */
    private SchedulerJobResult handleReviewPoll(String billId, String tenantId, Bill bill, RequestInfo requestInfo, String batchId) {
        boolean anyUnderReview = bill.getBillDetails().stream()
                .anyMatch(d -> d.getStatus() == Status.UNDER_REVIEW);
        if (anyUnderReview) {
            bill.getBillDetails().stream()
                    .filter(d -> d.getStatus() == Status.UNDER_REVIEW)
                    .forEach(d -> paymentWorkflowService.insertDetailWfUpdateRetryJob(d, bill, POLL_PHASE_SEND_FOR_APPROVAL, requestInfo));
            log.debug("Bill {} REVIEW: detail(s) still UNDER_REVIEW — dispatched retry jobs", bill.getId());
            return SchedulerJobResult.RETRY;
        }

        boolean allReviewed = bill.getBillDetails().stream()
                .allMatch(d -> isAtOrBeyondReviewed(d.getStatus()));
        if (allReviewed) {
            // RC-9: re-fetch before commit
            Bill fresh = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo, true);
            if (fresh == null) { log.warn("Bill {} vanished before REVIEW transition — retrying", billId); return SchedulerJobResult.RETRY; }
            if (fresh.getStatus() != Status.REVIEW_IN_PROGRESS) {
                log.info("Bill {} already past REVIEW_IN_PROGRESS (now={}) — skipping transition", billId, fresh.getStatus());
                return SchedulerJobResult.DONE;
            }
            paymentWorkflowService.transitionBill(fresh, Actions.COMPLETE, requestInfo);
            paymentWorkflowService.pushBillUpdate(fresh, requestInfo);
            if (batchId == null) {
                workflowEmailNotificationService.notify(
                        BillRequest.builder().bill(fresh).requestInfo(requestInfo).build(),
                        WorkflowNotificationType.APPROVAL);
            } else {
                log.debug("Bill {} settled REVIEWED — batch email coordinator will handle notify (batchId={})", billId, batchId);
            }
            return SchedulerJobResult.DONE;
        }
        return SchedulerJobResult.RETRY;
    }

    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Force-fails all PAYMENT_IN_PROGRESS details, then re-aggregates the bill to
     * determine FULLY_PAID / PARTIALLY_PAID / PAYMENT_FAILED.
     * Called from onMaxAttemptsExceeded for the PAYMENT phase.
     */
    private void forceFailPaymentInProgressDetails(Bill bill, String billId, String tenantId, RequestInfo requestInfo) {
        log.warn("BILL_STATUS_POLL PAYMENT timeout: force-failing PAYMENT_IN_PROGRESS details for bill={}", billId);

        // Force each stuck detail to PAYMENT_FAILED
        for (BillDetail detail : bill.getBillDetails()) {
            if (detail.getStatus() == Status.PAYMENT_IN_PROGRESS) {
                try {
                    paymentWorkflowService.transitionBillDetail(detail, Actions.FAILED, requestInfo);
                } catch (Exception ex) {
                    if (workflowUtil.isRetryableWfError(ex)) {
                        log.info("BILL_STATUS_POLL: detail={} already in terminal state (idempotent)", detail.getId());
                    } else {
                        log.error("CRITICAL: BILL_STATUS_POLL force-fail PAYMENT detail={} bill={} failed",
                                detail.getId(), billId, ex);
                    }
                }
            }
        }
        paymentWorkflowService.pushBillUpdate(bill, requestInfo);

        // Re-fetch and determine final bill action based on all detail statuses
        try {
            Bill fresh = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo, true);
            if (fresh == null) {
                log.warn("BILL_STATUS_POLL PAYMENT timeout: bill={} not found for re-aggregation", billId);
                return;
            }
            if (fresh.getStatus() != Status.PAYMENT_IN_PROGRESS) {
                log.info("BILL_STATUS_POLL PAYMENT timeout: bill={} already exited PAYMENT_IN_PROGRESS — done", billId);
                return;
            }

            long paid  = countDetailsInState(fresh, Status.PAID);
            long total = fresh.getBillDetails().size();
            Actions action = (paid == total) ? Actions.FULLY_PAY
                           : (paid > 0)      ? Actions.PARTIALLY_PAY
                           :                   Actions.FAILED;

            paymentWorkflowService.transitionBill(fresh, action, requestInfo);
            paymentWorkflowService.pushBillUpdate(fresh, requestInfo);
            log.warn("BILL_STATUS_POLL PAYMENT timeout: applied action={} for bill={}", action, billId);
        } catch (Exception ex) {
            if (workflowUtil.isRetryableWfError(ex)) {
                log.info("BILL_STATUS_POLL PAYMENT timeout: bill={} already exited PAYMENT_IN_PROGRESS (idempotent)", billId);
            } else {
                log.error("CRITICAL: BILL_STATUS_POLL PAYMENT timeout final transition failed for bill={}", billId, ex);
            }
        }
    }

    private boolean isAtOrBeyondUnderReview(Status s) {
        return s == Status.UNDER_REVIEW || s == Status.REVIEWED
                || s == Status.PAYMENT_IN_PROGRESS || s == Status.PAYMENT_FAILED
                || s == Status.PAID;
    }

    private boolean isAtOrBeyondReviewed(Status s) {
        return s == Status.REVIEWED || s == Status.PAYMENT_IN_PROGRESS
                || s == Status.PAYMENT_FAILED || s == Status.PAID;
    }

    /**
     * PAYMENT: waits for all PAYMENT_IN_PROGRESS details to settle (via per-detail Transfer tasks).
     * Acts as a guaranteed aggregator — the per-detail TaskStatusCheckHandler may miss the final
     * bill-level transition if the DB persister hasn't committed earlier tasks' updates in time.
     *
     * <pre>
     * All PAID              → FULLY_PAY    → FULLY_PAID
     * Some PAID, some FAILED → PARTIALLY_PAY → PARTIALLY_PAID
     * All PAYMENT_FAILED    → FAILED       → (terminal)
     * </pre>
     */
    private SchedulerJobResult handlePaymentPoll(String billId, String tenantId, Bill bill, RequestInfo requestInfo) {
        // If bill already exited PAYMENT_IN_PROGRESS (TaskStatusCheckHandler beat us), nothing to do
        if (bill.getStatus() != Status.PAYMENT_IN_PROGRESS) {
            log.info("Bill {} already exited PAYMENT_IN_PROGRESS (status={}) — PAYMENT poll done",
                    bill.getId(), bill.getStatus());
            return SchedulerJobResult.DONE;
        }

        long inProgress = countDetailsInState(bill, Status.PAYMENT_IN_PROGRESS);
        if (inProgress > 0) {
            log.debug("Bill {} PAYMENT: {} detail(s) still PAYMENT_IN_PROGRESS — retrying", bill.getId(), inProgress);
            return SchedulerJobResult.RETRY;
        }

        long paid  = countDetailsInState(bill, Status.PAID);
        long total = bill.getBillDetails().size();

        Actions action = (paid == total) ? Actions.FULLY_PAY
                       : (paid > 0)      ? Actions.PARTIALLY_PAY
                       :                   Actions.FAILED;

        // RC-9: re-fetch before commit
        Bill fresh = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo, true);
        if (fresh == null) { log.warn("Bill {} vanished before PAYMENT transition — retrying", billId); return SchedulerJobResult.RETRY; }
        if (fresh.getStatus() != Status.PAYMENT_IN_PROGRESS) {
            log.info("Bill {} exited PAYMENT_IN_PROGRESS concurrently (status={}) — done", billId, fresh.getStatus());
            return SchedulerJobResult.DONE;
        }
        paymentWorkflowService.transitionBill(fresh, action, requestInfo);
        paymentWorkflowService.pushBillUpdate(fresh, requestInfo);
        return SchedulerJobResult.DONE;
    }

    private long countDetailsInState(Bill bill, Status status) {
        return bill.getBillDetails().stream()
                .filter(d -> d.getStatus() == status)
                .count();
    }

    private Status resolveExpectedIntermediateStatus(String phase) {
        return switch (phase) {
            case POLL_PHASE_VERIFICATION    -> Status.VERIFICATION_IN_PROGRESS;
            case POLL_PHASE_IGNORE_ERRORS   -> Status.IGNORING_ERRORS_IN_PROGRESS;
            case POLL_PHASE_SEND_FOR_REVIEW -> Status.SENDING_FOR_REVIEW;
            case POLL_PHASE_REVIEW          -> Status.REVIEW_IN_PROGRESS;
            default -> null;
        };
    }

    private void enrichAuditForSystemUpdate(Bill bill, RequestInfo requestInfo) {
        long now = System.currentTimeMillis();
        String actor = (requestInfo != null && requestInfo.getUserInfo() != null)
                ? requestInfo.getUserInfo().getUuid()
                : SYSTEM_SCHEDULER_ACTOR;
        if (bill.getAuditDetails() == null) {
            bill.setAuditDetails(AuditDetails.builder()
                    .lastModifiedBy(actor).lastModifiedTime(now).build());
        } else {
            bill.getAuditDetails().setLastModifiedBy(actor);
            bill.getAuditDetails().setLastModifiedTime(now);
        }
    }
}
