package org.egov.digit.expense.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.egov.digit.expense.config.Constants.*;

/**
 * Shared correctness core for per-detail scheduler job architecture.
 *
 * <p>Called by each per-detail handler ({@code DetailVerifyHandler},
 * {@code DetailWfUpdateHandler}) after a BillDetail settles. Checks whether
 * all sibling details have settled and, if so, drives the bill-level WF
 * transition <em>exactly once</em>.
 *
 * <p><b>Exactly-once guarantee</b>: the WF engine rejects a repeated transition
 * with {@code INVALID_ACTION}. {@link WorkflowUtil#isRetryableWfError} treats
 * that as idempotent success, so two concurrent detail handlers racing to
 * aggregate produce one bill transition and one silent no-op.
 *
 * <p>{@code BILL_STATUS_POLL} remains as a safety-net with a long delay —
 * it fires only if a pod crash occurs after the last detail settles but before
 * this service completes the bill transition.
 */
@Service
@Slf4j
public class BillAggregationService {

    private final PaymentWorkflowService paymentWorkflowService;
    private final WorkflowUtil workflowUtil;

    @Autowired
    public BillAggregationService(PaymentWorkflowService paymentWorkflowService,
                                   WorkflowUtil workflowUtil) {
        this.paymentWorkflowService = paymentWorkflowService;
        this.workflowUtil = workflowUtil;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Public API — called by per-detail handlers after each detail settles
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Checks whether all sibling details have settled for the given phase and,
     * if so, applies the derived bill-level WF action.
     *
     * <p>Returns silently if details have not all settled — the next completing
     * detail handler will call this method again.
     *
     * @param billId     bill to check
     * @param tenantId   tenant schema
     * @param phase      POLL_PHASE_* constant identifying the current phase
     * @param requestInfo actor context for the WF call
     */
    public void checkAndAggregateBill(String billId, String tenantId, String phase, RequestInfo requestInfo) {
        // Cache-first (bypassCache=false): detail cache holds settled statuses written before each
        // Kafka push, so inline aggregation sees current detail states without waiting for persister.
        Bill bill = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo, false);
        if (bill == null) {
            log.warn("BillAggregationService: bill={} not found for phase={} — skipping aggregation", billId, phase);
            return;
        }

        List<BillDetail> details = bill.getBillDetails();
        if (details == null || details.isEmpty()) {
            log.warn("BillAggregationService: bill={} has no details — skipping aggregation", billId);
            return;
        }

        // Skip if bill has already exited the expected intermediate state for this phase.
        // Prevents spurious INVALID_ACTION errors when a concurrent handler or BILL_STATUS_POLL
        // already applied the transition before this call.
        Status expectedIntermediate = resolveExpectedIntermediateStatus(phase);
        if (expectedIntermediate != null && bill.getStatus() != expectedIntermediate) {
            log.info("BillAggregationService: bill={} phase={} — already past {} (now={}) — skipping",
                    billId, phase, expectedIntermediate, bill.getStatus());
            return;
        }

        if (!allDetailsSettled(details, phase)) {
            log.debug("BillAggregationService: bill={} phase={} — details still pending, skipping", billId, phase);
            return;
        }

        Actions action = deriveAction(details, phase);
        if (action == null) {
            log.debug("BillAggregationService: bill={} phase={} — no bill-level action needed", billId, phase);
            return;
        }

        applyBillTransition(bill, action, requestInfo, phase);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Phase-specific settlement checks
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns true when no details remain in a non-terminal state for the given phase.
     * Each phase has its own "still pending" status set.
     */
    private boolean allDetailsSettled(List<BillDetail> details, String phase) {
        return switch (phase) {
            case POLL_PHASE_VERIFICATION ->
                    details.stream().noneMatch(d ->
                            d.getStatus() == Status.PENDING_VERIFICATION
                            || d.getStatus() == Status.VERIFICATION_IN_PROGRESS);

            case POLL_PHASE_IGNORE_ERRORS ->
                    details.stream().noneMatch(d -> d.getStatus() == Status.VERIFICATION_FAILED);

            case POLL_PHASE_SEND_FOR_REVIEW ->
                    details.stream().noneMatch(d -> d.getStatus() == Status.VERIFIED);

            case POLL_PHASE_SEND_FOR_APPROVAL ->
                    details.stream().noneMatch(d -> d.getStatus() == Status.UNDER_REVIEW);

            case POLL_PHASE_PAYMENT ->
                    details.stream().noneMatch(d -> d.getStatus() == Status.PAYMENT_IN_PROGRESS);

            default -> {
                log.warn("BillAggregationService: unknown phase '{}' — treating as settled", phase);
                yield true;
            }
        };
    }

    /**
     * Derives the bill-level WF action from settled detail states for the given phase.
     * Returns {@code null} for phases that don't produce a direct bill WF transition
     * (e.g., PAYMENT_INITIATION which triggers Transfer tasks instead).
     */
    private Actions deriveAction(List<BillDetail> details, String phase) {
        return switch (phase) {
            case POLL_PHASE_VERIFICATION -> {
                long verified = details.stream().filter(d -> d.getStatus() == Status.VERIFIED).count();
                long total = details.size();
                yield verified == total ? Actions.FULLY_VERIFY
                        : verified > 0  ? Actions.PARTIALLY_VERIFY
                        :                 Actions.FAILED;
            }

            case POLL_PHASE_IGNORE_ERRORS   -> Actions.COMPLETE;
            case POLL_PHASE_SEND_FOR_REVIEW -> Actions.COMPLETE;
            case POLL_PHASE_SEND_FOR_APPROVAL -> Actions.COMPLETE;

            case POLL_PHASE_PAYMENT -> {
                long paid = details.stream()
                        .filter(d -> d.getStatus() == Status.PAID || d.getStatus() == Status.FULLY_PAID)
                        .count();
                long total = details.size();
                yield paid == total ? Actions.FULLY_PAY
                        : paid > 0  ? Actions.PARTIALLY_PAY
                        :             Actions.FAILED;
            }

            default -> {
                log.warn("BillAggregationService: unknown phase '{}' — no action derived", phase);
                yield null;
            }
        };
    }

    private Status resolveExpectedIntermediateStatus(String phase) {
        return switch (phase) {
            case POLL_PHASE_VERIFICATION      -> Status.VERIFICATION_IN_PROGRESS;
            case POLL_PHASE_IGNORE_ERRORS     -> Status.IGNORING_ERRORS_IN_PROGRESS;
            case POLL_PHASE_SEND_FOR_REVIEW   -> Status.SENDING_FOR_REVIEW;
            case POLL_PHASE_SEND_FOR_APPROVAL -> Status.REVIEW_IN_PROGRESS;
            case POLL_PHASE_PAYMENT           -> Status.PAYMENT_IN_PROGRESS;
            default -> null;
        };
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Exactly-once bill transition
    // ─────────────────────────────────────────────────────────────────────────

    private void applyBillTransition(Bill bill, Actions action, RequestInfo requestInfo, String phase) {
        try {
            paymentWorkflowService.transitionBill(bill, action, requestInfo);
            paymentWorkflowService.pushBillUpdate(bill, requestInfo);
            log.info("BillAggregationService: bill={} phase={} → action={} applied (status={})",
                    bill.getId(), phase, action, bill.getStatus());
        } catch (Exception e) {
            if (workflowUtil.isRetryableWfError(e)) {
                // INVALID_ACTION: another concurrent detail handler already triggered the transition — idempotent.
                log.info("BillAggregationService: bill={} phase={} already transitioned (idempotent, action={})",
                        bill.getId(), phase, action);
            } else {
                // Transient failure (network, DB): log and let the BILL_STATUS_POLL safety-net retry.
                log.error("BillAggregationService: bill={} phase={} transition failed — " +
                        "BILL_STATUS_POLL safety-net will retry", bill.getId(), phase, e);
            }
        }
    }
}
