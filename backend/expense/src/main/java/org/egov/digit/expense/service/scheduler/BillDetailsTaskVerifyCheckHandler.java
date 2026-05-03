package org.egov.digit.expense.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.repository.TaskRepository;
import org.egov.digit.expense.service.BillAggregationService;
import org.egov.digit.expense.service.PaymentProviderService;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.service.TaskCacheService;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.egov.digit.expense.config.Constants.POLL_PHASE_VERIFICATION;

/**
 * Handles {@link SchedulerJobType#BILL_DETAILS_TASK_VERIFY_CHECK} jobs (new design).
 *
 * <p>Polls-only handler — verification initiation is done by the {@code VerificationVerify}
 * task consumer. This handler checks the payment provider for the current verification status
 * and transitions the detail to VERIFIED or VERIFICATION_FAILED accordingly.
 *
 * <p>Context JSONB: serialised {@link DetailVerifyContext}.
 */
@Component
@Slf4j
public class BillDetailsTaskVerifyCheckHandler implements SchedulerJobHandler {

    private final PaymentWorkflowService paymentWorkflowService;
    private final BillAggregationService billAggregationService;
    private final WorkflowUtil workflowUtil;
    private final List<PaymentProviderService> paymentProviderServices;
    private final TaskCacheService taskCacheService;
    private final TaskRepository taskRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public BillDetailsTaskVerifyCheckHandler(PaymentWorkflowService paymentWorkflowService,
                                              BillAggregationService billAggregationService,
                                              WorkflowUtil workflowUtil,
                                              List<PaymentProviderService> paymentProviderServices,
                                              TaskCacheService taskCacheService,
                                              TaskRepository taskRepository,
                                              ObjectMapper objectMapper) {
        this.paymentWorkflowService = paymentWorkflowService;
        this.billAggregationService = billAggregationService;
        this.workflowUtil = workflowUtil;
        this.paymentProviderServices = paymentProviderServices;
        this.taskCacheService = taskCacheService;
        this.taskRepository = taskRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public SchedulerJobType getJobType() {
        return SchedulerJobType.BILL_DETAILS_TASK_VERIFY_CHECK;
    }

    @Override
    public SchedulerJobResult handle(SchedulerJob job) {
        String billDetailId = job.getReferenceId();
        String tenantId = job.getTenantId();

        try {
            DetailVerifyContext ctx = objectMapper.convertValue(job.getContext(), DetailVerifyContext.class);
            String billId = ctx.getBillId();
            RequestInfo requestInfo = ctx.getRequestInfo();

            Bill bill = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo, true);
            if (bill == null) {
                log.warn("BILL_DETAILS_TASK_VERIFY_CHECK: bill={} not found — marking FAILED", billId);
                return SchedulerJobResult.FAILED;
            }

            BillDetail detail = findDetail(bill, billDetailId);
            if (detail == null) {
                log.warn("BILL_DETAILS_TASK_VERIFY_CHECK: detail={} not found in bill={} — marking FAILED",
                        billDetailId, billId);
                return SchedulerJobResult.FAILED;
            }

            // Idempotency: already settled
            if (isVerifySettled(detail.getStatus())) {
                log.info("BILL_DETAILS_TASK_VERIFY_CHECK: detail={} already settled ({}) — aggregating and done",
                        billDetailId, detail.getStatus());
                billAggregationService.checkAndAggregateBill(billId, tenantId, POLL_PHASE_VERIFICATION, requestInfo);
                return SchedulerJobResult.DONE;
            }

            // Poll payment provider for verification status
            Bill singleDetailBill = buildSingleDetailBill(bill, detail);
            // EC-5: fetch persisted Verify task from cache first, then DB, then fall back to synthetic
            Task task = taskCacheService.get(tenantId, billDetailId, Task.Type.Verify)
                    .orElseGet(() -> {
                        Task probe = Task.builder().tenantId(tenantId).billDetailId(billDetailId)
                                .type(Task.Type.Verify).build();
                        Task dbTask = taskRepository.searchTask(probe);
                        return dbTask != null ? dbTask : Task.builder()
                                .id(job.getId())
                                .billId(billId)
                                .billDetailId(billDetailId)
                                .tenantId(tenantId)
                                .type(Task.Type.Verify)
                                .status(Status.IN_PROGRESS)
                                .auditDetails(bill.getAuditDetails())
                                .build();
                    });
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
                            log.error("BILL_DETAILS_TASK_VERIFY_CHECK: provider {} failed for detail={} bill={}",
                                    s.getClass().getSimpleName(), billDetailId, billId, e);
                            throw new RuntimeException(e);
                        }
                    });

            // Re-fetch from DB (bypass cache) to check if detail settled
            Bill refreshed = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo, true);
            if (refreshed != null) {
                BillDetail refreshedDetail = findDetail(refreshed, billDetailId);
                if (refreshedDetail != null && isVerifySettled(refreshedDetail.getStatus())) {
                    billAggregationService.checkAndAggregateBill(billId, tenantId, POLL_PHASE_VERIFICATION, requestInfo);
                    return SchedulerJobResult.DONE;
                }
            }

            log.info("BILL_DETAILS_TASK_VERIFY_CHECK: detail={} still VERIFICATION_IN_PROGRESS — will retry", billDetailId);
            return SchedulerJobResult.RETRY;

        } catch (Exception e) {
            log.error("BILL_DETAILS_TASK_VERIFY_CHECK: error for detail={} tenant={}", billDetailId, tenantId, e);
            return SchedulerJobResult.RETRY;
        }
    }

    @Override
    public void onMaxAttemptsExceeded(SchedulerJob job) {
        String billDetailId = job.getReferenceId();
        String tenantId = job.getTenantId();
        log.error("BILL_DETAILS_TASK_VERIFY_CHECK: max attempts exceeded for detail={} — forcing VERIFICATION_FAILED",
                billDetailId);
        try {
            DetailVerifyContext ctx = objectMapper.convertValue(job.getContext(), DetailVerifyContext.class);
            String billId = ctx.getBillId();
            RequestInfo requestInfo = ctx.getRequestInfo();

            Bill bill = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo, true);
            if (bill == null) return;

            BillDetail detail = findDetail(bill, billDetailId);
            if (detail == null) return;

            if (!isVerifySettled(detail.getStatus())) {
                try {
                    paymentWorkflowService.transitionBillDetail(detail, Actions.FAILED, requestInfo);
                    paymentWorkflowService.pushBillUpdate(bill, requestInfo);
                    log.warn("BILL_DETAILS_TASK_VERIFY_CHECK: forced detail={} to VERIFICATION_FAILED", billDetailId);
                } catch (Exception ex) {
                    if (workflowUtil.isRetryableWfError(ex)) {
                        log.info("BILL_DETAILS_TASK_VERIFY_CHECK: detail={} already in terminal state (idempotent)", billDetailId);
                    } else {
                        log.error("CRITICAL: BILL_DETAILS_TASK_VERIFY_CHECK force-fail failed for detail={} bill={}",
                                billDetailId, billId, ex);
                    }
                }
            }

            billAggregationService.checkAndAggregateBill(billId, tenantId, POLL_PHASE_VERIFICATION, requestInfo);

        } catch (Exception e) {
            log.error("BILL_DETAILS_TASK_VERIFY_CHECK: onMaxAttemptsExceeded error for detail={}", billDetailId, e);
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
                    return (payee != null && StringUtils.hasText(payee.getPaymentProvider()))
                            ? payee.getPaymentProvider().toUpperCase()
                            : null;
                })
                .collect(Collectors.toSet());
    }
}
