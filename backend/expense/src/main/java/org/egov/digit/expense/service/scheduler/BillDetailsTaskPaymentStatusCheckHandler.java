package org.egov.digit.expense.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.repository.TaskRepository;
import org.egov.digit.expense.service.BankPaymentService;
import org.egov.digit.expense.service.BillAggregationService;
import org.egov.digit.expense.service.MTNService;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.egov.digit.expense.config.Constants.POLL_PHASE_PAYMENT;

/**
 * Handles {@link SchedulerJobType#BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK} jobs (new design).
 *
 * <p>Polls-only handler — payment initiation is done by the {@code PaymentInitiationPay}
 * task consumer. This handler looks up the existing Transfer task for the detail and
 * delegates to MTN/Bank status-check logic.
 *
 * <p>Context JSONB: serialised {@link DetailVerifyContext} (billId + billDetailId + requestInfo).
 */
@Component
@Slf4j
public class BillDetailsTaskPaymentStatusCheckHandler implements SchedulerJobHandler {

    private final PaymentWorkflowService paymentWorkflowService;
    private final BillAggregationService billAggregationService;
    private final WorkflowUtil workflowUtil;
    private final MTNService mtnService;
    private final BankPaymentService bankPaymentService;
    private final TaskRepository taskRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public BillDetailsTaskPaymentStatusCheckHandler(PaymentWorkflowService paymentWorkflowService,
                                                     BillAggregationService billAggregationService,
                                                     WorkflowUtil workflowUtil,
                                                     MTNService mtnService,
                                                     BankPaymentService bankPaymentService,
                                                     TaskRepository taskRepository,
                                                     ObjectMapper objectMapper) {
        this.paymentWorkflowService = paymentWorkflowService;
        this.billAggregationService = billAggregationService;
        this.workflowUtil = workflowUtil;
        this.mtnService = mtnService;
        this.bankPaymentService = bankPaymentService;
        this.taskRepository = taskRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public SchedulerJobType getJobType() {
        return SchedulerJobType.BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK;
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
                log.warn("BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK: bill={} not found — marking FAILED", billId);
                return SchedulerJobResult.FAILED;
            }

            BillDetail detail = findDetail(bill, billDetailId);
            if (detail == null) {
                log.warn("BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK: detail={} not found in bill={} — marking FAILED",
                        billDetailId, billId);
                return SchedulerJobResult.FAILED;
            }

            // Idempotency: already settled
            if (isPaymentSettled(detail.getStatus())) {
                log.info("BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK: detail={} already settled ({}) — aggregating and done",
                        billDetailId, detail.getStatus());
                billAggregationService.checkAndAggregateBill(billId, tenantId, POLL_PHASE_PAYMENT, requestInfo);
                return SchedulerJobResult.DONE;
            }

            // Look up the Transfer task for this detail
            Task task = taskRepository.searchTask(Task.builder()
                    .billId(billId)
                    .billDetailId(billDetailId)
                    .tenantId(tenantId)
                    .type(Task.Type.Transfer)
                    .status(Status.IN_PROGRESS)
                    .build());

            if (task == null) {
                log.warn("BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK: no IN_PROGRESS Transfer task for detail={} bill={} — retrying",
                        billDetailId, billId);
                return SchedulerJobResult.RETRY;
            }

            TaskRequest taskRequest = TaskRequest.builder()
                    .task(task)
                    .bill(Bill.builder().id(billId).tenantId(tenantId).build())
                    .requestInfo(requestInfo)
                    .build();

            boolean mtnInProgress = mtnService.updatePaymentTaskStatusAndFinalize(taskRequest);
            boolean bankInProgress = bankPaymentService.updatePaymentTaskStatusAndFinalize(taskRequest);

            if (mtnInProgress || bankInProgress) {
                log.info("BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK: detail={} still PAYMENT_IN_PROGRESS — will retry",
                        billDetailId);
                return SchedulerJobResult.RETRY;
            }

            // Re-fetch to check settled status and trigger aggregation
            Bill refreshed = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo);
            if (refreshed != null) {
                BillDetail refreshedDetail = findDetail(refreshed, billDetailId);
                if (refreshedDetail != null && isPaymentSettled(refreshedDetail.getStatus())) {
                    billAggregationService.checkAndAggregateBill(billId, tenantId, POLL_PHASE_PAYMENT, requestInfo);
                    return SchedulerJobResult.DONE;
                }
            }

            return SchedulerJobResult.RETRY;

        } catch (Exception e) {
            log.error("BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK: error for detail={} tenant={}", billDetailId, tenantId, e);
            return SchedulerJobResult.RETRY;
        }
    }

    @Override
    public void onMaxAttemptsExceeded(SchedulerJob job) {
        String billDetailId = job.getReferenceId();
        String tenantId = job.getTenantId();
        log.error("BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK: max attempts exceeded for detail={} — forcing PAYMENT_FAILED",
                billDetailId);
        try {
            DetailVerifyContext ctx = objectMapper.convertValue(job.getContext(), DetailVerifyContext.class);
            String billId = ctx.getBillId();
            RequestInfo requestInfo = ctx.getRequestInfo();

            Bill bill = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo);
            if (bill == null) return;

            BillDetail detail = findDetail(bill, billDetailId);
            if (detail == null) return;

            if (!isPaymentSettled(detail.getStatus())) {
                try {
                    paymentWorkflowService.transitionBillDetail(detail, Actions.FAILED, requestInfo);
                    paymentWorkflowService.pushBillUpdate(bill, requestInfo);
                    log.warn("BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK: forced detail={} to PAYMENT_FAILED", billDetailId);
                } catch (Exception ex) {
                    if (workflowUtil.isRetryableWfError(ex)) {
                        log.info("BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK: detail={} already in terminal state (idempotent)",
                                billDetailId);
                    } else {
                        log.error("CRITICAL: BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK force-fail failed for detail={} bill={}",
                                billDetailId, billId, ex);
                    }
                }
            }

            billAggregationService.checkAndAggregateBill(billId, tenantId, POLL_PHASE_PAYMENT, requestInfo);

        } catch (Exception e) {
            log.error("BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK: onMaxAttemptsExceeded error for detail={}", billDetailId, e);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────

    private boolean isPaymentSettled(Status status) {
        return status == Status.PAID || status == Status.FULLY_PAID || status == Status.PAYMENT_FAILED;
    }

    private BillDetail findDetail(Bill bill, String billDetailId) {
        if (bill.getBillDetails() == null) return null;
        return bill.getBillDetails().stream()
                .filter(d -> billDetailId.equals(d.getId()))
                .findFirst().orElse(null);
    }
}
