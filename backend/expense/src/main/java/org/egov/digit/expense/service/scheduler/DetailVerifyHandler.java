package org.egov.digit.expense.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.service.BillAggregationService;
import org.egov.digit.expense.service.PaymentProviderService;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.egov.digit.expense.config.Constants.POLL_PHASE_VERIFICATION;

/**
 * Handles {@link SchedulerJobType#DETAIL_VERIFY} jobs (per-detail path).
 *
 * <p>Responsibilities per invocation:
 * <ol>
 *   <li>Load the specific BillDetail from DB (idempotency guard: already settled → DONE).</li>
 *   <li>Build a single-detail {@link TaskRequest} and dispatch to matching
 *       {@link PaymentProviderService} implementations (same provider resolution
 *       as {@code TaskConsumer}).</li>
 *   <li>Re-fetch the detail to determine whether it settled (VERIFIED /
 *       VERIFICATION_FAILED) or is still in progress (MTN pending → RETRY).</li>
 *   <li>After settling, call {@link BillAggregationService#checkAndAggregateBill}
 *       so the bill-level WF transition fires exactly once when the last detail resolves.</li>
 * </ol>
 *
 * <p>Crash recovery: the scheduler's {@code recoverStuckJobs()} resets any
 * PROCESSING job whose {@code updated_at} exceeds the stuck threshold back to
 * PENDING — no separate crash-recovery handler needed.
 */
@Component
@Slf4j
public class DetailVerifyHandler implements SchedulerJobHandler {

    private final PaymentWorkflowService paymentWorkflowService;
    private final BillAggregationService billAggregationService;
    private final WorkflowUtil workflowUtil;
    private final List<PaymentProviderService> paymentProviderServices;
    private final ObjectMapper objectMapper;

    @Autowired
    public DetailVerifyHandler(PaymentWorkflowService paymentWorkflowService,
                                BillAggregationService billAggregationService,
                                WorkflowUtil workflowUtil,
                                List<PaymentProviderService> paymentProviderServices,
                                ObjectMapper objectMapper) {
        this.paymentWorkflowService = paymentWorkflowService;
        this.billAggregationService = billAggregationService;
        this.workflowUtil = workflowUtil;
        this.paymentProviderServices = paymentProviderServices;
        this.objectMapper = objectMapper;
    }

    @Override
    public SchedulerJobType getJobType() {
        return SchedulerJobType.DETAIL_VERIFY;
    }

    @Override
    public SchedulerJobResult handle(SchedulerJob job) {
        String billDetailId = job.getReferenceId();
        String tenantId = job.getTenantId();

        try {
            DetailVerifyContext ctx = objectMapper.convertValue(job.getContext(), DetailVerifyContext.class);
            String billId = ctx.getBillId();
            RequestInfo requestInfo = ctx.getRequestInfo();

            Bill bill = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo);
            if (bill == null) {
                log.warn("DETAIL_VERIFY: bill={} not found for tenant={} — marking FAILED", billId, tenantId);
                return SchedulerJobResult.FAILED;
            }

            BillDetail detail = findDetail(bill, billDetailId);
            if (detail == null) {
                log.warn("DETAIL_VERIFY: detail={} not found in bill={} — marking FAILED", billDetailId, billId);
                return SchedulerJobResult.FAILED;
            }

            // Idempotency: detail already settled in a previous run
            if (isVerifySettled(detail.getStatus())) {
                log.info("DETAIL_VERIFY: detail={} already in terminal state {} — checking aggregation and marking DONE",
                        billDetailId, detail.getStatus());
                billAggregationService.checkAndAggregateBill(billId, tenantId, POLL_PHASE_VERIFICATION, requestInfo);
                return SchedulerJobResult.DONE;
            }

            // Dispatch verify to the matching payment provider (same logic as TaskConsumer)
            Bill singleDetailBill = buildSingleDetailBill(bill, detail);
            Task task = Task.builder()
                    .id(job.getId())  // use scheduler job ID as task correlator (no Task table entry needed)
                    .billId(billId)
                    .billDetailId(billDetailId)
                    .tenantId(tenantId)
                    .type(Task.Type.Verify)
                    .status(Status.IN_PROGRESS)
                    .auditDetails(bill.getAuditDetails())
                    .build();
            TaskRequest taskRequest = TaskRequest.builder()
                    .task(task)
                    .bill(singleDetailBill)
                    .requestInfo(requestInfo)
                    .build();

            Set<String> providers = extractProviders(singleDetailBill);
            paymentProviderServices.stream()
                    .filter(s -> providers.stream().anyMatch(s::supports))
                    .forEach(s -> {
                        try {
                            s.executeTask(taskRequest);
                        } catch (Exception e) {
                            log.error("DETAIL_VERIFY: provider {} executeTask failed for detail={} bill={}",
                                    s.getClass().getSimpleName(), billDetailId, billId, e);
                            // Re-throw so the outer catch returns RETRY
                            throw new RuntimeException(e);
                        }
                    });

            // Re-fetch to see the persisted detail status (executeTask pushes Kafka updates)
            Bill refreshed = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo);
            if (refreshed != null) {
                BillDetail refreshedDetail = findDetail(refreshed, billDetailId);
                if (refreshedDetail != null && isVerifySettled(refreshedDetail.getStatus())) {
                    billAggregationService.checkAndAggregateBill(billId, tenantId, POLL_PHASE_VERIFICATION, requestInfo);
                    return SchedulerJobResult.DONE;
                }
            }

            // Detail still VERIFICATION_IN_PROGRESS — MTN is processing, retry later
            log.info("DETAIL_VERIFY: detail={} still VERIFICATION_IN_PROGRESS — will retry", billDetailId);
            return SchedulerJobResult.RETRY;

        } catch (Exception e) {
            log.error("DETAIL_VERIFY: error for detail={} tenant={}", billDetailId, tenantId, e);
            return SchedulerJobResult.RETRY;
        }
    }

    /**
     * Called when this job exhausts all retries. Forces the detail to VERIFICATION_FAILED
     * and triggers aggregation so the bill is not left permanently in VERIFICATION_IN_PROGRESS.
     * Single attempt — no blocking sleep on the scheduler thread.
     */
    @Override
    public void onMaxAttemptsExceeded(SchedulerJob job) {
        String billDetailId = job.getReferenceId();
        String tenantId = job.getTenantId();
        log.error("DETAIL_VERIFY: max attempts exceeded for detail={} tenant={} — forcing VERIFICATION_FAILED",
                billDetailId, tenantId);
        try {
            DetailVerifyContext ctx = objectMapper.convertValue(job.getContext(), DetailVerifyContext.class);
            String billId = ctx.getBillId();
            RequestInfo requestInfo = ctx.getRequestInfo();

            Bill bill = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo);
            if (bill == null) return;

            BillDetail detail = findDetail(bill, billDetailId);
            if (detail == null) return;

            if (!isVerifySettled(detail.getStatus())) {
                try {
                    // VERIFY transitions detail to VERIFICATION_IN_PROGRESS (if PENDING); then FAILED → VERIFICATION_FAILED
                    if (detail.getStatus() == Status.PENDING_VERIFICATION) {
                        paymentWorkflowService.transitionBillDetail(detail, Actions.VERIFY, requestInfo);
                    }
                    paymentWorkflowService.transitionBillDetail(detail, Actions.FAILED, requestInfo);
                    paymentWorkflowService.pushBillUpdate(bill, requestInfo);
                    log.warn("DETAIL_VERIFY: forced detail={} to VERIFICATION_FAILED", billDetailId);
                } catch (Exception ex) {
                    if (workflowUtil.isRetryableWfError(ex)) {
                        log.info("DETAIL_VERIFY: detail={} already in terminal state (idempotent)", billDetailId);
                    } else {
                        log.error("CRITICAL: DETAIL_VERIFY force-fail failed for detail={} bill={} — manual intervention required",
                                billDetailId, billId, ex);
                    }
                }
            }

            // Always attempt aggregation so the bill doesn't get stuck
            billAggregationService.checkAndAggregateBill(billId, tenantId, POLL_PHASE_VERIFICATION, requestInfo);

        } catch (Exception e) {
            log.error("DETAIL_VERIFY: onMaxAttemptsExceeded error for detail={}", billDetailId, e);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────

    private boolean isVerifySettled(Status status) {
        return status == Status.VERIFIED || status == Status.VERIFICATION_FAILED;
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

    private Set<String> extractProviders(Bill bill) {
        if (bill == null || CollectionUtils.isEmpty(bill.getBillDetails())) {
            return Collections.singleton(null);
        }
        return bill.getBillDetails().stream()
                .map(d -> {
                    Party payee = d.getPayee();
                    return (payee != null && org.springframework.util.StringUtils.hasText(payee.getPaymentProvider()))
                            ? payee.getPaymentProvider().toUpperCase()
                            : null;
                })
                .collect(Collectors.toSet());
    }
}
