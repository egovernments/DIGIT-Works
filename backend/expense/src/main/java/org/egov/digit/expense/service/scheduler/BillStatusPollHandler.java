package org.egov.digit.expense.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillPollContext;
import org.egov.digit.expense.web.models.SchedulerJob;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Handles {@link SchedulerJobType#BILL_STATUS_POLL} jobs.
 *
 * <p>Polls bill-detail states and aggregates them into a bill-level workflow transition
 * for phases: VERIFICATION, IGNORE_ERRORS, SEND_FOR_REVIEW, REVIEW.
 *
 * <p>Context JSONB: serialised {@link BillPollContext}.
 */
@Component
@Slf4j
public class BillStatusPollHandler implements SchedulerJobHandler {

    private final PaymentWorkflowService paymentWorkflowService;
    private final ObjectMapper objectMapper;

    @Autowired
    public BillStatusPollHandler(PaymentWorkflowService paymentWorkflowService,
                                  ObjectMapper objectMapper) {
        this.paymentWorkflowService = paymentWorkflowService;
        this.objectMapper = objectMapper;
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

            Bill bill = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo);
            if (bill == null) {
                log.warn("Bill {} not found for tenant {} — marking job FAILED", billId, tenantId);
                return SchedulerJobResult.FAILED;
            }

            return switch (phase) {
                case "VERIFICATION" -> handleVerificationPoll(bill, requestInfo);
                case "IGNORE_ERRORS" -> handleIgnoreErrorsPoll(bill, requestInfo);
                case "SEND_FOR_REVIEW" -> handleSendForReviewPoll(bill, requestInfo);
                case "REVIEW" -> handleReviewPoll(bill, requestInfo);
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

    // ─────────────────────────────────────────────────────────────────────────
    // Phase handlers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * VERIFICATION phase: waits for all details to leave VERIFICATION_IN_PROGRESS.
     *
     * <pre>
     * All VERIFIED             → FULLY_VERIFY  → FULLY_VERIFIED
     * Some VERIFIED, some FAILED → PARTIALLY_VERIFY → PARTIALLY_VERIFIED
     * All FAILED (or 0 verified) → FAILED       → PENDING_VERIFICATION
     * </pre>
     */
    private SchedulerJobResult handleVerificationPoll(Bill bill, RequestInfo requestInfo) {
        long inProgress = countDetailsInState(bill, Status.VERIFICATION_IN_PROGRESS);
        if (inProgress > 0) {
            log.debug("Bill {} VERIFICATION: {} detail(s) still in progress — retrying",
                    bill.getId(), inProgress);
            return SchedulerJobResult.RETRY;
        }

        long verified = countDetailsInState(bill, Status.VERIFIED);
        long total = bill.getBillDetails().size();

        if (verified == total) {
            paymentWorkflowService.transitionBill(bill, Actions.FULLY_VERIFY, requestInfo);
        } else if (verified > 0) {
            paymentWorkflowService.transitionBill(bill, Actions.PARTIALLY_VERIFY, requestInfo);
        } else {
            paymentWorkflowService.transitionBill(bill, Actions.FAILED, requestInfo);
        }
        // Persist updated bill status to DB via Kafka
        paymentWorkflowService.pushBillUpdate(bill, requestInfo);
        return SchedulerJobResult.DONE;
    }

    /**
     * IGNORE_ERRORS phase: waits until all details are VERIFIED.
     *
     * <pre>
     * All VERIFIED → COMPLETE → FULLY_VERIFIED
     * Otherwise    → RETRY
     * </pre>
     */
    private SchedulerJobResult handleIgnoreErrorsPoll(Bill bill, RequestInfo requestInfo) {
        boolean allVerified = bill.getBillDetails().stream()
                .allMatch(d -> d.getStatus() == Status.VERIFIED || d.getStatus() == Status.PAID);

        if (allVerified) {
            paymentWorkflowService.transitionBill(bill, Actions.COMPLETE, requestInfo);
            // Persist updated bill status to DB via Kafka
            paymentWorkflowService.pushBillUpdate(bill, requestInfo);
            return SchedulerJobResult.DONE;
        }
        log.debug("Bill {} IGNORE_ERRORS: not all details VERIFIED yet — retrying", bill.getId());
        return SchedulerJobResult.RETRY;
    }

    /**
     * SEND_FOR_REVIEW phase: waits until all details are UNDER_REVIEW.
     * On timeout (max attempts exhausted): bill transitions FAIL → FULLY_VERIFIED.
     * Already-moved details are NOT reverted.
     *
     * <pre>
     * All UNDER_REVIEW → COMPLETE → UNDER_REVIEW
     * Any still VERIFIED → RETRY
     * </pre>
     */
    private SchedulerJobResult handleSendForReviewPoll(Bill bill, RequestInfo requestInfo) {
        boolean allUnderReview = bill.getBillDetails().stream()
                .allMatch(d -> d.getStatus() == Status.UNDER_REVIEW
                            || d.getStatus() == Status.PAID
                            || d.getStatus() == Status.REVIEWED
                            || d.getStatus() == Status.PAYMENT_IN_PROGRESS
                            || d.getStatus() == Status.PAYMENT_FAILED
                            || d.getStatus() == Status.FULLY_PAID);

        if (allUnderReview) {
            paymentWorkflowService.transitionBill(bill, Actions.COMPLETE, requestInfo);
            // Persist updated bill status to DB via Kafka
            paymentWorkflowService.pushBillUpdate(bill, requestInfo);
            return SchedulerJobResult.DONE;
        }
        log.debug("Bill {} SEND_FOR_REVIEW: not all details UNDER_REVIEW yet — retrying", bill.getId());
        return SchedulerJobResult.RETRY;
    }

    /**
     * REVIEW phase: waits until all details are REVIEWED.
     *
     * <pre>
     * All REVIEWED → COMPLETE → REVIEWED
     * Otherwise    → RETRY
     * </pre>
     */
    private SchedulerJobResult handleReviewPoll(Bill bill, RequestInfo requestInfo) {
        boolean allReviewed = bill.getBillDetails().stream()
                .allMatch(d -> d.getStatus() == Status.REVIEWED
                            || d.getStatus() == Status.PAID
                            || d.getStatus() == Status.PAYMENT_IN_PROGRESS
                            || d.getStatus() == Status.PAYMENT_FAILED
                            || d.getStatus() == Status.FULLY_PAID);

        if (allReviewed) {
            paymentWorkflowService.transitionBill(bill, Actions.COMPLETE, requestInfo);
            // Persist updated bill status to DB via Kafka
            paymentWorkflowService.pushBillUpdate(bill, requestInfo);
            return SchedulerJobResult.DONE;
        }
        log.debug("Bill {} REVIEW: not all details REVIEWED yet — retrying", bill.getId());
        return SchedulerJobResult.RETRY;
    }

    // ─────────────────────────────────────────────────────────────────────────

    private long countDetailsInState(Bill bill, Status status) {
        return bill.getBillDetails().stream()
                .filter(d -> d.getStatus() == status)
                .count();
    }
}
