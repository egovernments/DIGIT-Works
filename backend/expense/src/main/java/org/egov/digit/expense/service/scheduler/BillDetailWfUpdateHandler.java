package org.egov.digit.expense.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.service.BillAggregationService;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillDetailWfUpdateContext;
import org.egov.digit.expense.web.models.SchedulerJob;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

import static org.egov.digit.expense.config.Constants.*;

/**
 * Handles {@link SchedulerJobType#BILL_DETAIL_WF_UPDATE} jobs.
 *
 * <p>Asynchronously transitions bill details through their workflow for phases that do not
 * involve external calls (IGNORE_ERRORS, SEND_FOR_REVIEW, SEND_FOR_APPROVAL, PAYMENT_INITIATION).
 * Each detail is transitioned independently — failures are logged and retried on the next cycle.
 *
 * <p>Context JSONB: serialised {@link BillDetailWfUpdateContext}.
 */
@Component
@Slf4j
public class BillDetailWfUpdateHandler implements SchedulerJobHandler {

    private final PaymentWorkflowService paymentWorkflowService;
    private final WorkflowUtil workflowUtil;
    private final ObjectMapper objectMapper;
    private final BillAggregationService billAggregationService;

    @Autowired
    public BillDetailWfUpdateHandler(PaymentWorkflowService paymentWorkflowService,
                                     WorkflowUtil workflowUtil,
                                     ObjectMapper objectMapper,
                                     BillAggregationService billAggregationService) {
        this.paymentWorkflowService = paymentWorkflowService;
        this.workflowUtil = workflowUtil;
        this.objectMapper = objectMapper;
        this.billAggregationService = billAggregationService;
    }

    @Override
    public SchedulerJobType getJobType() {
        return SchedulerJobType.BILL_DETAIL_WF_UPDATE;
    }

    @Override
    public SchedulerJobResult handle(SchedulerJob job) {
        String billId = job.getReferenceId();
        String tenantId = job.getTenantId();

        try {
            BillDetailWfUpdateContext ctx = objectMapper.convertValue(job.getContext(), BillDetailWfUpdateContext.class);
            RequestInfo requestInfo = ctx.getRequestInfo();
            String phase = ctx.getPhase();

            Bill bill = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo);
            if (bill == null) {
                log.warn("Bill {} not found for tenant {} — marking FAILED", billId, tenantId);
                return SchedulerJobResult.FAILED;
            }

            return switch (phase) {
                case POLL_PHASE_IGNORE_ERRORS -> transitionDetails(bill, requestInfo,
                        d -> d.getStatus() == Status.VERIFICATION_FAILED,
                        Actions.IGNORE_ERRORS_AND_VERIFY, phase);
                case POLL_PHASE_SEND_FOR_REVIEW -> transitionDetails(bill, requestInfo,
                        d -> d.getStatus() == Status.VERIFIED,
                        Actions.SEND_FOR_REVIEW, phase);
                case POLL_PHASE_SEND_FOR_APPROVAL -> transitionDetails(bill, requestInfo,
                        d -> d.getStatus() == Status.UNDER_REVIEW,
                        Actions.SEND_FOR_APPROVAL, phase);
                case POLL_PHASE_PAYMENT_INITIATION -> transitionDetails(bill, requestInfo,
                        d -> d.getStatus() == Status.REVIEWED || d.getStatus() == Status.PAYMENT_FAILED,
                        Actions.PAYMENT_INITIATION, phase);
                default -> {
                    log.error("Unknown phase '{}' for BILL_DETAIL_WF_UPDATE bill={}", phase, billId);
                    yield SchedulerJobResult.FAILED;
                }
            };

        } catch (Exception e) {
            log.error("Error in BILL_DETAIL_WF_UPDATE for billId={}, tenantId={}", billId, tenantId, e);
            return SchedulerJobResult.RETRY;
        }
    }

    private SchedulerJobResult transitionDetails(Bill bill, RequestInfo requestInfo,
                                                  Predicate<BillDetail> filter,
                                                  Actions action, String phase) {
        boolean anyFailed = false;
        int transitioned = 0;

        for (BillDetail detail : bill.getBillDetails()) {
            if (!filter.test(detail)) continue;
            try {
                paymentWorkflowService.transitionBillDetail(detail, action, requestInfo);
                transitioned++;
            } catch (Exception e) {
                // RC-7: INVALID_ACTION means detail already transitioned — treat as idempotent success
                if (workflowUtil.isRetryableWfError(e)) {
                    log.info("BILL_DETAIL_WF_UPDATE phase={} — detail {} already in target state (idempotent)",
                            phase, detail.getId());
                    transitioned++;
                } else {
                    log.error("BILL_DETAIL_WF_UPDATE phase={} — failed to transition billDetail {} via {}: {}",
                            phase, detail.getId(), action, e.getMessage());
                    anyFailed = true;
                }
            }
        }

        if (transitioned > 0) {
            paymentWorkflowService.pushBillUpdate(bill, requestInfo);
        }

        if (anyFailed) {
            log.warn("BILL_DETAIL_WF_UPDATE phase={} bill={} — {} detail(s) transitioned, retrying failed ones",
                    phase, bill.getId(), transitioned);
            return SchedulerJobResult.RETRY;
        }

        log.info("BILL_DETAIL_WF_UPDATE phase={} bill={} — all {} eligible detail(s) transitioned",
                phase, bill.getId(), transitioned);

        // After all details reach PAYMENT_IN_PROGRESS, trigger the Transfer task so MTN
        // disbursement starts automatically without a separate frontend API call.
        if ("PAYMENT_INITIATION".equals(phase)) {
            paymentWorkflowService.createTransferTask(bill, requestInfo);
        }

        return SchedulerJobResult.DONE;
    }

    /**
     * Called when BILL_DETAIL_WF_UPDATE exhausts all scheduler retries.
     * Unlocks the bill by applying the phase-appropriate compensation action so the
     * bill is not left permanently in a transitional locked state.
     *
     * <pre>
     * IGNORE_ERRORS      → IGNORING_ERRORS_IN_PROGRESS → FAIL → PARTIALLY_VERIFIED
     * SEND_FOR_REVIEW    → SENDING_FOR_REVIEW           → FAIL → FULLY_VERIFIED
     * SEND_FOR_APPROVAL  → REVIEW_IN_PROGRESS           → FAIL → UNDER_REVIEW
     * PAYMENT_INITIATION → retry remaining REVIEWED details; BILL_STATUS_POLL handles bill-level
     * </pre>
     */
    @Override
    public void onMaxAttemptsExceeded(SchedulerJob job) {
        String billId = job.getReferenceId();
        String tenantId = job.getTenantId();
        log.error("BILL_DETAIL_WF_UPDATE max attempts exceeded for billId={} tenantId={} — applying compensation",
                billId, tenantId);
        try {
            BillDetailWfUpdateContext ctx = objectMapper.convertValue(job.getContext(), BillDetailWfUpdateContext.class);
            RequestInfo requestInfo = ctx.getRequestInfo();
            String phase = ctx.getPhase();

            Bill bill = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo);
            if (bill == null) {
                log.error("BILL_DETAIL_WF_UPDATE onMaxAttemptsExceeded — bill {} not found, cannot compensate", billId);
                return;
            }

            switch (phase) {
                case POLL_PHASE_IGNORE_ERRORS:
                case POLL_PHASE_SEND_FOR_REVIEW:
                case POLL_PHASE_SEND_FOR_APPROVAL:
                    applyBillFailCompensation(bill, requestInfo, phase);
                    break;

                case POLL_PHASE_PAYMENT_INITIATION:
                    // Force-transition any remaining REVIEWED/PAYMENT_FAILED details to PAYMENT_IN_PROGRESS.
                    // BILL_STATUS_POLL (PAYMENT phase) is already running and will drive bill-level outcome.
                    forcePaymentInitiationOnRemainingDetails(bill, requestInfo);
                    break;

                default:
                    log.error("BILL_DETAIL_WF_UPDATE onMaxAttemptsExceeded — unknown phase '{}' for bill={}", phase, billId);
            }
        } catch (Exception e) {
            log.error("CRITICAL: BILL_DETAIL_WF_UPDATE onMaxAttemptsExceeded compensation failed for bill={} — manual intervention required",
                    billId, e);
        }
    }

    private void applyBillFailCompensation(Bill bill, RequestInfo requestInfo, String phase) {
        try {
            paymentWorkflowService.transitionBill(bill, Actions.FAIL, requestInfo);
            paymentWorkflowService.pushBillUpdate(bill, requestInfo);
            log.warn("BILL_DETAIL_WF_UPDATE compensation applied FAIL for bill={} phase={}", bill.getId(), phase);
        } catch (Exception ex) {
            if (workflowUtil.isRetryableWfError(ex)) {
                log.info("Bill {} already exited phase {} (idempotent) — compensation no-op", bill.getId(), phase);
            } else {
                log.error("CRITICAL: compensation FAIL action failed for bill={} phase={} — " +
                        "stuck-job recovery will reset the scheduler job for retry", bill.getId(), phase, ex);
            }
        }
    }

    /**
     * Forces any remaining REVIEWED/PAYMENT_FAILED details to PAYMENT_IN_PROGRESS, then
     * triggers Transfer task creation so the payment is not left permanently stranded.
     * Called when this batch job exhausts all retries mid-PAYMENT_INITIATION phase.
     */
    private void forcePaymentInitiationOnRemainingDetails(Bill bill, RequestInfo requestInfo) {
        boolean anyForced = false;
        for (BillDetail detail : bill.getBillDetails()) {
            if (detail.getStatus() == Status.REVIEWED || detail.getStatus() == Status.PAYMENT_FAILED) {
                try {
                    paymentWorkflowService.transitionBillDetail(detail, Actions.PAYMENT_INITIATION, requestInfo);
                    anyForced = true;
                    log.info("Forced PAYMENT_INITIATION on detail {} for bill={}", detail.getId(), bill.getId());
                } catch (Exception e) {
                    if (workflowUtil.isRetryableWfError(e)) {
                        log.info("Detail {} already at PAYMENT_IN_PROGRESS (idempotent)", detail.getId());
                        anyForced = true;
                    } else {
                        log.error("CRITICAL: forced PAYMENT_INITIATION failed for detail {} bill={} — manual intervention required",
                                detail.getId(), bill.getId(), e);
                    }
                }
            }
        }
        if (anyForced) {
            paymentWorkflowService.pushBillUpdate(bill, requestInfo);
            // Ensure Transfer tasks are created and the BILL_STATUS_POLL(PAYMENT) safety-net is active
            // so the bill can exit PAYMENT_IN_PROGRESS even when the batch job exhausted its retries.
            billAggregationService.triggerTransferAfterPaymentInitiation(bill, requestInfo);
        }
    }
}
