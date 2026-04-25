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

        // WfUpdate tasks: internal WF-only transitions — no external payment provider involved.
        if (taskRequest.getTask() != null && taskRequest.getTask().getType() == Task.Type.WfUpdate) {
            handleWfUpdateTask(taskRequest);
            return;
        }

        Set<String> providers = extractProviders(taskRequest.getBill());
        log.info("Task for bill={} providers={}", taskRequest.getTask().getBillId(), providers);

        // Call each service at most once — only if at least one provider in this bill is supported.
        // Each service then handles all its matching details internally in a single pass.
        // Wrapped per-service so one service throwing does not block offset commit or other services.
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
            case POLL_PHASE_PAYMENT_INITIATION -> Actions.PAYMENT_INITIATION;
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
