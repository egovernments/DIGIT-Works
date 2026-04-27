package org.egov.digit.expense.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillStartedCheckContext;
import org.egov.digit.expense.web.models.SchedulerJob;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.egov.digit.expense.config.Constants.*;

/**
 * Handles {@link SchedulerJobType#BILL_STARTED_CHECK} jobs.
 *
 * <p>Coordinator that waits until all eligible bill details have entered the active phase
 * (VERIFICATION_IN_PROGRESS for verify; PAYMENT_IN_PROGRESS for payment), then:
 * <ol>
 *   <li>Creates a BILL_STATUS_POLL safety-net job.</li>
 *   <li>Pushes per-detail VerificationVerify or PaymentInitiationPay tasks to Kafka.</li>
 * </ol>
 */
@Component
@Slf4j
public class BillStartedCheckHandler implements SchedulerJobHandler {

    private final PaymentWorkflowService paymentWorkflowService;
    private final WorkflowUtil workflowUtil;
    private final ObjectMapper objectMapper;

    @Autowired
    public BillStartedCheckHandler(PaymentWorkflowService paymentWorkflowService,
                                    WorkflowUtil workflowUtil,
                                    ObjectMapper objectMapper) {
        this.paymentWorkflowService = paymentWorkflowService;
        this.workflowUtil = workflowUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    public SchedulerJobType getJobType() {
        return SchedulerJobType.BILL_STARTED_CHECK;
    }

    @Override
    public SchedulerJobResult handle(SchedulerJob job) {
        String billId = job.getReferenceId();
        String tenantId = job.getTenantId();

        try {
            BillStartedCheckContext ctx = objectMapper.convertValue(job.getContext(), BillStartedCheckContext.class);
            RequestInfo requestInfo = ctx.getRequestInfo();
            String phase = ctx.getPhase();

            Bill bill = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo);
            if (bill == null) {
                log.warn("BILL_STARTED_CHECK: bill={} not found for tenant={} — marking FAILED", billId, tenantId);
                return SchedulerJobResult.FAILED;
            }

            if (STARTED_CHECK_PHASE_VERIFY.equals(phase)) {
                return handleVerifyStartedCheck(bill, ctx, requestInfo);
            } else if (STARTED_CHECK_PHASE_PAYMENT.equals(phase)) {
                return handlePaymentStartedCheck(bill, ctx, requestInfo);
            } else {
                log.error("BILL_STARTED_CHECK: unknown phase '{}' for bill={} — marking FAILED", phase, billId);
                return SchedulerJobResult.FAILED;
            }

        } catch (Exception e) {
            log.error("BILL_STARTED_CHECK: error for bill={} tenant={}", billId, tenantId, e);
            return SchedulerJobResult.RETRY;
        }
    }

    @Override
    public void onMaxAttemptsExceeded(SchedulerJob job) {
        String billId = job.getReferenceId();
        String tenantId = job.getTenantId();
        log.error("BILL_STARTED_CHECK: max attempts exceeded for bill={} tenant={} — forcing stuck details to failed state",
                billId, tenantId);
        try {
            BillStartedCheckContext ctx = objectMapper.convertValue(job.getContext(), BillStartedCheckContext.class);
            RequestInfo requestInfo = ctx.getRequestInfo();
            String phase = ctx.getPhase();

            Bill bill = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo);
            if (bill == null) return;

            if (STARTED_CHECK_PHASE_VERIFY.equals(phase)) {
                forceFailVerifyDetails(bill, requestInfo);
            } else if (STARTED_CHECK_PHASE_PAYMENT.equals(phase)) {
                forceFailPaymentDetails(bill, requestInfo);
            }
        } catch (Exception e) {
            log.error("BILL_STARTED_CHECK: onMaxAttemptsExceeded error for bill={}", billId, e);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // VERIFY_STARTED phase
    // ─────────────────────────────────────────────────────────────────────────

    private SchedulerJobResult handleVerifyStartedCheck(Bill bill, BillStartedCheckContext ctx, RequestInfo requestInfo) {
        String billId = bill.getId();
        List<BillDetail> details = bill.getBillDetails();

        // Check if any eligible detail is still not yet in-progress
        boolean anyStillPending = details.stream()
                .anyMatch(d -> d.getStatus() == Status.PENDING_VERIFICATION
                            || d.getStatus() == Status.VERIFICATION_FAILED);

        if (anyStillPending) {
            log.debug("BILL_STARTED_CHECK(VERIFY): bill={} still has PENDING_VERIFICATION or VERIFICATION_FAILED details — retrying",
                    billId);
            return SchedulerJobResult.RETRY;
        }

        // All eligible details are now VERIFICATION_IN_PROGRESS or VERIFIED
        // Insert BILL_STATUS_POLL safety-net for the verification phase
        paymentWorkflowService.insertBillStatusPollJob(bill, POLL_PHASE_VERIFICATION, requestInfo,
                paymentWorkflowService.getSafetyNetDelayMs());

        // Push VerificationVerify tasks for each detail still in VERIFICATION_IN_PROGRESS
        List<BillDetail> inProgressDetails = details.stream()
                .filter(d -> d.getStatus() == Status.VERIFICATION_IN_PROGRESS)
                .toList();

        for (BillDetail detail : inProgressDetails) {
            paymentWorkflowService.pushVerificationVerifyTask(bill, detail, requestInfo);
        }

        log.info("BILL_STARTED_CHECK(VERIFY): bill={} all details in-progress — pushed {} VerificationVerify tasks",
                billId, inProgressDetails.size());
        return SchedulerJobResult.DONE;
    }

    private void forceFailVerifyDetails(Bill bill, RequestInfo requestInfo) {
        for (BillDetail detail : bill.getBillDetails()) {
            if (detail.getStatus() == Status.PENDING_VERIFICATION
                    || detail.getStatus() == Status.VERIFICATION_FAILED) {
                try {
                    paymentWorkflowService.transitionBillDetail(detail, Actions.FAILED, requestInfo);
                } catch (Exception ex) {
                    if (workflowUtil.isRetryableWfError(ex)) {
                        log.info("BILL_STARTED_CHECK: detail={} already in terminal state (idempotent)", detail.getId());
                    } else {
                        log.error("CRITICAL: BILL_STARTED_CHECK force-fail VERIFY detail={} bill={} failed",
                                detail.getId(), bill.getId(), ex);
                    }
                }
            }
        }
        paymentWorkflowService.pushBillUpdate(bill, requestInfo);
        log.warn("BILL_STARTED_CHECK: forced stuck PENDING_VERIFICATION/VERIFICATION_FAILED details to VERIFICATION_FAILED for bill={}",
                bill.getId());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PAYMENT_STARTED phase
    // ─────────────────────────────────────────────────────────────────────────

    private SchedulerJobResult handlePaymentStartedCheck(Bill bill, BillStartedCheckContext ctx, RequestInfo requestInfo) {
        String billId = bill.getId();
        List<BillDetail> details = bill.getBillDetails();

        // Check if any eligible detail is still not yet in-progress
        boolean anyStillPending = details.stream()
                .anyMatch(d -> d.getStatus() == Status.REVIEWED
                            || d.getStatus() == Status.PAYMENT_FAILED);

        if (anyStillPending) {
            log.debug("BILL_STARTED_CHECK(PAYMENT): bill={} still has REVIEWED or PAYMENT_FAILED details — retrying",
                    billId);
            return SchedulerJobResult.RETRY;
        }

        // All eligible details are now PAYMENT_IN_PROGRESS or PAID
        // Insert BILL_STATUS_POLL safety-net for the payment phase
        paymentWorkflowService.insertBillStatusPollJob(bill, POLL_PHASE_PAYMENT, requestInfo,
                paymentWorkflowService.getSafetyNetDelayMs());

        // Push PaymentInitiationPay tasks for each detail still in PAYMENT_IN_PROGRESS
        List<BillDetail> inProgressDetails = details.stream()
                .filter(d -> d.getStatus() == Status.PAYMENT_IN_PROGRESS)
                .toList();

        for (BillDetail detail : inProgressDetails) {
            paymentWorkflowService.pushPaymentInitiationPayTask(bill, detail, requestInfo);
        }

        log.info("BILL_STARTED_CHECK(PAYMENT): bill={} all details in-progress — pushed {} PaymentInitiationPay tasks",
                billId, inProgressDetails.size());
        return SchedulerJobResult.DONE;
    }

    private void forceFailPaymentDetails(Bill bill, RequestInfo requestInfo) {
        for (BillDetail detail : bill.getBillDetails()) {
            if (detail.getStatus() == Status.REVIEWED
                    || detail.getStatus() == Status.PAYMENT_FAILED) {
                try {
                    paymentWorkflowService.transitionBillDetail(detail, Actions.FAILED, requestInfo);
                } catch (Exception ex) {
                    if (workflowUtil.isRetryableWfError(ex)) {
                        log.info("BILL_STARTED_CHECK: detail={} already in terminal state (idempotent)", detail.getId());
                    } else {
                        log.error("CRITICAL: BILL_STARTED_CHECK force-fail PAYMENT detail={} bill={} failed",
                                detail.getId(), bill.getId(), ex);
                    }
                }
            }
        }
        paymentWorkflowService.pushBillUpdate(bill, requestInfo);
        log.warn("BILL_STARTED_CHECK: forced stuck REVIEWED/PAYMENT_FAILED details to PAYMENT_FAILED for bill={}",
                bill.getId());
    }
}
