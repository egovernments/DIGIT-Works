package org.egov.digit.expense.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.service.BillAggregationService;
import org.egov.digit.expense.service.BillCacheService;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.egov.digit.expense.config.Constants.*;

/**
 * Handles {@link SchedulerJobType#DETAIL_WF_UPDATE} jobs (per-detail path).
 *
 * <p>Transitions a single BillDetail through a non-external WF action
 * (IGNORE_ERRORS, SEND_FOR_REVIEW, SEND_FOR_APPROVAL) and then calls
 * {@link BillAggregationService#checkAndAggregateBill} to fire the
 * bill-level WF transition when all sibling details have settled.
 *
 * <p>Each job targets exactly one BillDetail (referenceId = billDetailId).
 * Failures for one detail do not block other details.
 */
@Component
@Slf4j
public class DetailWfUpdateHandler implements SchedulerJobHandler {

    private final PaymentWorkflowService paymentWorkflowService;
    private final BillAggregationService billAggregationService;
    private final WorkflowUtil workflowUtil;
    private final BillCacheService billCacheService;
    private final ObjectMapper objectMapper;

    @Autowired
    public DetailWfUpdateHandler(PaymentWorkflowService paymentWorkflowService,
                                  BillAggregationService billAggregationService,
                                  WorkflowUtil workflowUtil,
                                  BillCacheService billCacheService,
                                  ObjectMapper objectMapper) {
        this.paymentWorkflowService = paymentWorkflowService;
        this.billAggregationService = billAggregationService;
        this.workflowUtil = workflowUtil;
        this.billCacheService = billCacheService;
        this.objectMapper = objectMapper;
    }

    @Override
    public SchedulerJobType getJobType() {
        return SchedulerJobType.DETAIL_WF_UPDATE;
    }

    @Override
    public SchedulerJobResult handle(SchedulerJob job) {
        String billDetailId = job.getReferenceId();
        String tenantId = job.getTenantId();

        try {
            DetailWfUpdateContext ctx = objectMapper.convertValue(job.getContext(), DetailWfUpdateContext.class);
            String billId = ctx.getBillId();
            String phase = ctx.getPhase();
            RequestInfo requestInfo = ctx.getRequestInfo();

            Bill bill = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo, true);
            if (bill == null) {
                log.warn("DETAIL_WF_UPDATE: bill={} not found tenant={} — marking FAILED", billId, tenantId);
                return SchedulerJobResult.FAILED;
            }

            BillDetail detail = findDetail(bill, billDetailId);
            if (detail == null) {
                log.warn("DETAIL_WF_UPDATE: detail={} not found in bill={} — marking FAILED", billDetailId, billId);
                return SchedulerJobResult.FAILED;
            }

            Actions action = resolveAction(phase);
            if (action == null) {
                log.error("DETAIL_WF_UPDATE: unknown phase '{}' for detail={} — marking FAILED", phase, billDetailId);
                return SchedulerJobResult.FAILED;
            }

            // Idempotency: already in or past the target state
            if (isAlreadyTransitioned(detail.getStatus(), phase)) {
                log.info("DETAIL_WF_UPDATE: detail={} already past phase {} (status={}) — checking aggregation",
                        billDetailId, phase, detail.getStatus());
                aggregate(bill, billId, tenantId, phase, requestInfo);
                return SchedulerJobResult.DONE;
            }

            try {
                paymentWorkflowService.transitionBillDetail(detail, action, requestInfo);
                // Cache the full bill (with updated detail status) BEFORE Kafka push (EC-1 Fix B+C)
                billCacheService.put(bill);
                paymentWorkflowService.pushBillUpdate(buildSingleDetailBill(bill, detail), requestInfo, false);
                log.info("DETAIL_WF_UPDATE: detail={} transitioned via {} → {}", billDetailId, action, detail.getStatus());
            } catch (Exception e) {
                log.error("DETAIL_WF_UPDATE: WF transition failed for detail={} phase={}: {}", billDetailId, phase, e.getMessage());
                if (workflowUtil.isRetryableWfError(e)) {
                    // WF already applied this transition — treat as idempotent DONE
                    log.info("DETAIL_WF_UPDATE: retryable WF error for detail={} — treating as already transitioned", billDetailId);
                    billCacheService.put(bill);
                    aggregate(bill, billId, tenantId, phase, requestInfo);
                    return SchedulerJobResult.DONE;
                }
                return SchedulerJobResult.RETRY;
            }

            aggregate(bill, billId, tenantId, phase, requestInfo);
            return SchedulerJobResult.DONE;

        } catch (Exception e) {
            log.error("DETAIL_WF_UPDATE: error for detail={} tenant={}", billDetailId, tenantId, e);
            return SchedulerJobResult.RETRY;
        }
    }

    /**
     * Called when this job exhausts all retries. Forces the detail to a safe terminal state
     * and applies bill-level compensation so the bill doesn't get permanently locked.
     * Single attempt — no blocking sleep on the scheduler thread.
     */
    @Override
    public void onMaxAttemptsExceeded(SchedulerJob job) {
        String billDetailId = job.getReferenceId();
        String tenantId = job.getTenantId();
        log.error("DETAIL_WF_UPDATE: max attempts exceeded for detail={} tenant={}", billDetailId, tenantId);
        try {
            DetailWfUpdateContext ctx = objectMapper.convertValue(job.getContext(), DetailWfUpdateContext.class);
            String billId = ctx.getBillId();
            String phase = ctx.getPhase();
            RequestInfo requestInfo = ctx.getRequestInfo();

            Bill bill = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo, true);
            if (bill == null) return;

            BillDetail detail = findDetail(bill, billDetailId);
            if (detail == null) return;

            applyBillFailCompensation(bill, requestInfo, phase);

        } catch (Exception e) {
            log.error("DETAIL_WF_UPDATE: onMaxAttemptsExceeded error for detail={}", billDetailId, e);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────

    private void aggregate(Bill bill, String billId, String tenantId, String phase, RequestInfo requestInfo) {
        billAggregationService.checkAndAggregateBill(billId, tenantId, phase, requestInfo);
    }

    private void applyBillFailCompensation(Bill bill, RequestInfo requestInfo, String phase) {
        // EC-8: only compensate if bill is still in the expected intermediate state
        Status expected = resolveExpectedIntermediateStatus(phase);
        if (expected != null && bill.getStatus() != expected) {
            log.info("DETAIL_WF_UPDATE: bill={} already exited intermediate {} (now={}) — skipping compensation",
                    bill.getId(), expected, bill.getStatus());
            return;
        }
        try {
            paymentWorkflowService.transitionBill(bill, Actions.FAIL, requestInfo);
            paymentWorkflowService.pushBillUpdate(bill, requestInfo);
            log.warn("DETAIL_WF_UPDATE: compensation FAIL applied for bill={} phase={}", bill.getId(), phase);
        } catch (Exception ex) {
            log.error("CRITICAL: DETAIL_WF_UPDATE compensation FAIL failed for bill={} phase={}", bill.getId(), phase, ex);
        }
    }

    private Status resolveExpectedIntermediateStatus(String phase) {
        return switch (phase) {
            case POLL_PHASE_IGNORE_ERRORS     -> Status.IGNORING_ERRORS_IN_PROGRESS;
            case POLL_PHASE_SEND_FOR_REVIEW   -> Status.SENDING_FOR_REVIEW;
            case POLL_PHASE_SEND_FOR_APPROVAL -> Status.REVIEW_IN_PROGRESS;
            default -> null;
        };
    }

    private Actions resolveAction(String phase) {
        return switch (phase) {
            case POLL_PHASE_IGNORE_ERRORS      -> Actions.IGNORE_ERRORS_AND_VERIFY;
            case POLL_PHASE_SEND_FOR_REVIEW    -> Actions.SEND_FOR_REVIEW;
            case POLL_PHASE_SEND_FOR_APPROVAL  -> Actions.SEND_FOR_APPROVAL;
            default -> null;
        };
    }

    /** Returns true if the detail is already in or past the target state for the given phase. */
    private boolean isAlreadyTransitioned(Status status, String phase) {
        return switch (phase) {
            case POLL_PHASE_IGNORE_ERRORS      -> status == Status.VERIFIED || isAtOrBeyondVerified(status);
            case POLL_PHASE_SEND_FOR_REVIEW    -> status == Status.UNDER_REVIEW || isAtOrBeyondUnderReview(status);
            case POLL_PHASE_SEND_FOR_APPROVAL  -> status == Status.REVIEWED || isAtOrBeyondReviewed(status);
            default -> false;
        };
    }

    private boolean isAtOrBeyondVerified(Status s) {
        return s == Status.UNDER_REVIEW || s == Status.REVIEWED
                || s == Status.PAYMENT_IN_PROGRESS || s == Status.PAYMENT_FAILED
                || s == Status.PAID || s == Status.FULLY_PAID;
    }

    private boolean isAtOrBeyondUnderReview(Status s) {
        return s == Status.REVIEWED || s == Status.PAYMENT_IN_PROGRESS
                || s == Status.PAYMENT_FAILED || s == Status.PAID || s == Status.FULLY_PAID;
    }

    private boolean isAtOrBeyondReviewed(Status s) {
        return s == Status.PAYMENT_IN_PROGRESS || s == Status.PAYMENT_FAILED
                || s == Status.PAID || s == Status.FULLY_PAID;
    }

    private BillDetail findDetail(Bill bill, String billDetailId) {
        if (bill.getBillDetails() == null) return null;
        return bill.getBillDetails().stream()
                .filter(d -> billDetailId.equals(d.getId()))
                .findFirst().orElse(null);
    }

    private Bill buildSingleDetailBill(Bill bill, BillDetail detail) {
        return Bill.builder()
                .id(bill.getId())
                .tenantId(bill.getTenantId())
                .localityCode(bill.getLocalityCode())
                .billDate(bill.getBillDate())
                .dueDate(bill.getDueDate())
                .totalAmount(bill.getTotalAmount())
                .amountBreakup(new java.util.LinkedHashMap<>(bill.getAmountBreakup()))
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
                .billDetails(java.util.Collections.singletonList(detail))
                .build();
    }
}
