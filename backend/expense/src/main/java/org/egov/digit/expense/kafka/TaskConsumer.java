package org.egov.digit.expense.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.service.BillAggregationService;
import org.egov.digit.expense.service.PaymentProviderService;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.Party;
import org.egov.digit.expense.web.models.Task;
import org.egov.digit.expense.web.models.TaskRequest;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.egov.digit.expense.config.Constants.*;

@Slf4j
@Component
public class TaskConsumer {

    private final ObjectMapper objectMapper;
    private final List<PaymentProviderService> paymentProviderServices;
    private final PaymentWorkflowService paymentWorkflowService;
    private final BillAggregationService billAggregationService;
    private final Configuration config;
    private final ExpenseProducer expenseProducer;

    @Autowired
    public TaskConsumer(ObjectMapper objectMapper,
                        List<PaymentProviderService> paymentProviderServices,
                        PaymentWorkflowService paymentWorkflowService,
                        BillAggregationService billAggregationService,
                        Configuration config,
                        ExpenseProducer expenseProducer) {
        this.objectMapper = objectMapper;
        this.paymentProviderServices = paymentProviderServices;
        this.paymentWorkflowService = paymentWorkflowService;
        this.billAggregationService = billAggregationService;
        this.config = config;
        this.expenseProducer = expenseProducer;
    }

    @KafkaListener(topicPattern = "(${expense.kafka.tenant.id.pattern})${expense.bill.task}")
    public void listen(final Map<String, Object> message) {
        log.info("Consuming message from kafka task topic");
        TaskRequest taskRequest = objectMapper.convertValue(message, TaskRequest.class);

        if (taskRequest.getTask() == null) {
            log.warn("Received task message with null task — skipping");
            return;
        }

        switch (taskRequest.getTask().getType()) {
            case WfUpdate             -> handleWfUpdateTask(taskRequest);
            case VerificationStart    -> handleVerificationStartTask(taskRequest);
            case VerificationVerify   -> handleVerificationVerifyTask(taskRequest);
            case PaymentInitiationStart -> handlePaymentInitiationStartTask(taskRequest);
            case PaymentInitiationPay   -> handlePaymentInitiationPayTask(taskRequest);
            default                   -> handleProviderTask(taskRequest);  // Verify / Transfer (legacy)
        }
    }

    private void handleProviderTask(TaskRequest taskRequest) {
        Set<String> providers = extractProviders(taskRequest.getBill());
        log.info("Task for bill={} providers={}", taskRequest.getTask().getBillId(), providers);

        paymentProviderServices.stream()
                .filter(s -> providers.stream().anyMatch(s::supports))
                .forEach(s -> {
                    try {
                        log.info("Dispatching task to {}", s.getClass().getSimpleName());
                        s.executeTask(taskRequest);
                    } catch (Exception e) {
                        log.error("executeTask failed for service={} taskId={} billId={} — skipping to prevent partition block",
                                s.getClass().getSimpleName(), taskRequest.getTask().getId(),
                                taskRequest.getTask().getBillId(), e);
                    }
                });
    }

    /**
     * Transitions bill detail → VERIFICATION_IN_PROGRESS; persists task record.
     * No external payment provider call — BILL_STARTED_CHECK handles re-check via scheduler.
     */
    private void handleVerificationStartTask(TaskRequest taskRequest) {
        Task task = taskRequest.getTask();
        BillDetail detail = taskRequest.getBill().getBillDetails().get(0);
        String billId = task.getBillId();
        try {
            // Skip if detail already advanced past PENDING_VERIFICATION
            if (detail.getStatus() != Status.PENDING_VERIFICATION
                    && detail.getStatus() != Status.VERIFICATION_FAILED) {
                log.info("VerificationStart: detail={} already in status={} — skipping", detail.getId(), detail.getStatus());
                markTaskDone(task);
                return;
            }
            paymentWorkflowService.transitionBillDetail(detail, Actions.VERIFY, taskRequest.getRequestInfo());
            paymentWorkflowService.pushBillUpdate(taskRequest.getBill(), taskRequest.getRequestInfo());
            log.info("VerificationStart: detail={} → VERIFICATION_IN_PROGRESS for bill={}", detail.getId(), billId);
        } catch (Exception e) {
            log.error("VerificationStart failed for bill={} detail={} — BILL_STARTED_CHECK will retry: {}",
                    billId, detail.getId(), e.getMessage());
        } finally {
            markTaskDone(task);
        }
    }

    /**
     * Calls payment provider to initiate verification; creates BILL_DETAILS_TASK_VERIFY_CHECK job.
     */
    private void handleVerificationVerifyTask(TaskRequest taskRequest) {
        Task task = taskRequest.getTask();
        BillDetail detail = taskRequest.getBill().getBillDetails().get(0);
        String billId = task.getBillId();
        try {
            if (detail.getStatus() != Status.VERIFICATION_IN_PROGRESS) {
                log.info("VerificationVerify: detail={} not in VERIFICATION_IN_PROGRESS (status={}) — skipping",
                        detail.getId(), detail.getStatus());
                markTaskDone(task);
                return;
            }
            // Initiate verification via provider
            Set<String> providers = extractProviders(taskRequest.getBill());
            paymentProviderServices.stream()
                    .filter(s -> providers.stream().anyMatch(s::supports))
                    .forEach(s -> s.executeTask(taskRequest));

            // Create polling job for this detail
            paymentWorkflowService.insertBillDetailsTaskVerifyCheckJob(
                    taskRequest.getBill(), detail, taskRequest.getRequestInfo());
            log.info("VerificationVerify: initiated for bill={} detail={}", billId, detail.getId());
        } catch (Exception e) {
            log.error("VerificationVerify failed for bill={} detail={} — BILL_DETAILS_TASK_VERIFY_CHECK will poll: {}",
                    billId, detail.getId(), e.getMessage());
        } finally {
            markTaskDone(task);
        }
    }

    /**
     * Transitions bill detail → PAYMENT_IN_PROGRESS; persists task record.
     * No external payment provider call — BILL_STARTED_CHECK handles re-check via scheduler.
     */
    private void handlePaymentInitiationStartTask(TaskRequest taskRequest) {
        Task task = taskRequest.getTask();
        BillDetail detail = taskRequest.getBill().getBillDetails().get(0);
        String billId = task.getBillId();
        try {
            if (detail.getStatus() != Status.REVIEWED && detail.getStatus() != Status.PAYMENT_FAILED) {
                log.info("PaymentInitiationStart: detail={} not eligible (status={}) — skipping",
                        detail.getId(), detail.getStatus());
                markTaskDone(task);
                return;
            }
            paymentWorkflowService.transitionBillDetail(detail, Actions.PAYMENT_INITIATION, taskRequest.getRequestInfo());
            paymentWorkflowService.pushBillUpdate(taskRequest.getBill(), taskRequest.getRequestInfo());
            log.info("PaymentInitiationStart: detail={} → PAYMENT_IN_PROGRESS for bill={}", detail.getId(), billId);
        } catch (Exception e) {
            log.error("PaymentInitiationStart failed for bill={} detail={} — BILL_STARTED_CHECK will retry: {}",
                    billId, detail.getId(), e.getMessage());
        } finally {
            markTaskDone(task);
        }
    }

    /**
     * Calls payment provider to initiate payment; creates BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK job.
     */
    private void handlePaymentInitiationPayTask(TaskRequest taskRequest) {
        Task task = taskRequest.getTask();
        BillDetail detail = taskRequest.getBill().getBillDetails().get(0);
        String billId = task.getBillId();
        try {
            if (detail.getStatus() != Status.PAYMENT_IN_PROGRESS) {
                log.info("PaymentInitiationPay: detail={} not in PAYMENT_IN_PROGRESS (status={}) — skipping",
                        detail.getId(), detail.getStatus());
                markTaskDone(task);
                return;
            }
            // Initiate payment via provider (Transfer task type)
            Task transferTask = Task.builder()
                    .id(task.getId())
                    .tenantId(task.getTenantId())
                    .billId(task.getBillId())
                    .billDetailId(task.getBillDetailId())
                    .type(Task.Type.Transfer)
                    .status(Status.IN_PROGRESS)
                    .auditDetails(task.getAuditDetails())
                    .build();
            TaskRequest transferReq = TaskRequest.builder()
                    .task(transferTask)
                    .bill(taskRequest.getBill())
                    .requestInfo(taskRequest.getRequestInfo())
                    .build();
            Set<String> providers = extractProviders(taskRequest.getBill());
            paymentProviderServices.stream()
                    .filter(s -> providers.stream().anyMatch(s::supports))
                    .forEach(s -> s.executeTask(transferReq));

            // Create polling job for this detail
            paymentWorkflowService.insertBillDetailsTaskPaymentStatusCheckJob(
                    taskRequest.getBill(), detail, taskRequest.getRequestInfo());
            log.info("PaymentInitiationPay: initiated for bill={} detail={}", billId, detail.getId());
        } catch (Exception e) {
            log.error("PaymentInitiationPay failed for bill={} detail={} — BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK will poll: {}",
                    billId, detail.getId(), e.getMessage());
        } finally {
            markTaskDone(task);
        }
    }

    /**
     * Handles WfUpdate tasks — pure WF transitions with no external API call.
     * On success: transitions the detail, pushes a single-detail bill update, then calls
     * BillAggregationService to fire the bill-level transition when all details settle.
     * On failure: logs and marks task DONE so offset commits; BILL_STATUS_POLL will dispatch a
     * DETAIL_WF_UPDATE scheduler retry job for this detail.
     */
    @SuppressWarnings("unchecked")
    private void handleWfUpdateTask(TaskRequest taskRequest) {
        Task task = taskRequest.getTask();
        String billId = task.getBillId();
        String tenantId = task.getTenantId();
        try {
            Map<String, Object> addl = objectMapper.convertValue(task.getAdditionalDetails(), Map.class);
            String phase = (String) addl.get("phase");
            Actions action = resolveWfUpdateAction(phase);
            if (action == null) {
                log.error("WfUpdate task has unknown phase '{}' for bill={} — skipping", phase, billId);
                markTaskDone(task);
                return;
            }

            BillDetail detail = taskRequest.getBill().getBillDetails().get(0);
            log.info("WfUpdate task: bill={} detail={} phase={} action={}", billId, detail.getId(), phase, action);

            paymentWorkflowService.transitionBillDetail(detail, action, taskRequest.getRequestInfo());
            paymentWorkflowService.pushBillUpdate(taskRequest.getBill(), taskRequest.getRequestInfo());
            billAggregationService.checkAndAggregateBill(billId, tenantId, phase, taskRequest.getRequestInfo());

        } catch (Exception e) {
            log.error("WfUpdate task failed bill={} detail={} — BILL_STATUS_POLL will dispatch DETAIL_WF_UPDATE retry | error={}",
                    billId, task.getBillDetailId(), e.getMessage());
        } finally {
            markTaskDone(task);
        }
    }

    private void markTaskDone(Task task) {
        try {
            task.setStatus(Status.DONE);
            expenseProducer.push(task.getTenantId(), config.getTaskUpdateTopic(), task);
        } catch (Exception e) {
            log.warn("Failed to mark WfUpdate task DONE for taskId={}: {}", task.getId(), e.getMessage());
        }
    }

    private Actions resolveWfUpdateAction(String phase) {
        if (phase == null) return null;
        return switch (phase) {
            case POLL_PHASE_IGNORE_ERRORS      -> Actions.IGNORE_ERRORS_AND_VERIFY;
            case POLL_PHASE_SEND_FOR_REVIEW    -> Actions.SEND_FOR_REVIEW;
            case POLL_PHASE_SEND_FOR_APPROVAL  -> Actions.SEND_FOR_APPROVAL;
            default -> null;
        };
    }

    /**
     * Extracts the distinct set of paymentProvider values from the bill's details.
     * Null is included when any detail has no provider configured, so that
     * {@link org.egov.digit.expense.service.MTNService} (which handles null) can mark them FAILED.
     */
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
