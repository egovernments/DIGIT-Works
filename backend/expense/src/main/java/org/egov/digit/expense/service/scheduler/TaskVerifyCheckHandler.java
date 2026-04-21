package org.egov.digit.expense.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.repository.TaskRepository;
import org.egov.digit.expense.service.MTNService;
import org.egov.digit.expense.service.PaymentProviderService;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.Party;
import org.egov.digit.expense.web.models.SchedulerJob;
import org.egov.digit.expense.web.models.Task;
import org.egov.digit.expense.web.models.TaskRequest;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles {@link SchedulerJobType#TASK_VERIFY_CHECK} jobs.
 *
 * <p>Purpose: crash/restart recovery for Verify tasks. If the service pod crashes after
 * the Kafka offset is committed but before verification completes, this handler re-runs
 * the verification for any bill details still in a non-terminal state.
 *
 * <p>Dispatches to all matching {@link PaymentProviderService} implementations based on
 * the bill detail's payment provider — mirrors {@code TaskConsumer} dispatch logic.
 *
 * <p>Context JSONB: serialised {@code RequestInfo}.
 */
@Component
@Slf4j
public class TaskVerifyCheckHandler implements SchedulerJobHandler {

    private final List<PaymentProviderService> paymentProviderServices;
    private final MTNService mtnService;
    private final TaskRepository taskRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public TaskVerifyCheckHandler(List<PaymentProviderService> paymentProviderServices,
                                   MTNService mtnService,
                                   TaskRepository taskRepository,
                                   ObjectMapper objectMapper) {
        this.paymentProviderServices = paymentProviderServices;
        this.mtnService = mtnService;
        this.taskRepository = taskRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public SchedulerJobType getJobType() {
        return SchedulerJobType.TASK_VERIFY_CHECK;
    }

    @Override
    public SchedulerJobResult handle(SchedulerJob job) {
        String taskId = job.getReferenceId();
        String tenantId = job.getTenantId();

        try {
            RequestInfo requestInfo = objectMapper.convertValue(job.getContext(), RequestInfo.class);

            Task task = taskRepository.searchTask(Task.builder().id(taskId).tenantId(tenantId).build());
            if (task == null) {
                log.warn("Verify task {} not found for tenant {} — marking job FAILED", taskId, tenantId);
                return SchedulerJobResult.FAILED;
            }

            if (task.getStatus() == Status.DONE) {
                log.info("Verify task {} already DONE — marking scheduler job DONE", taskId);
                return SchedulerJobResult.DONE;
            }

            TaskRequest taskRequest = TaskRequest.builder()
                    .task(task)
                    .bill(Bill.builder().id(task.getBillId()).tenantId(tenantId).build())
                    .requestInfo(requestInfo)
                    .build();

            // Dispatch to all matching payment provider services (same as TaskConsumer).
            // executeTask() internally handles verify and sets task DONE in its finally block.
            // We return DONE immediately after dispatch — no re-fetch needed since the verify
            // finally block guarantees task DONE is pushed to Kafka.
            Set<String> providers = extractProviders(taskRequest.getBill());
            paymentProviderServices.stream()
                    .filter(s -> providers.stream().anyMatch(s::supports))
                    .forEach(s -> {
                        try {
                            log.info("TASK_VERIFY_CHECK crash-recovery: dispatching to {} for taskId={}",
                                    s.getClass().getSimpleName(), taskId);
                            s.executeTask(taskRequest);
                        } catch (Exception e) {
                            log.error("TASK_VERIFY_CHECK dispatch failed for service={} taskId={}",
                                    s.getClass().getSimpleName(), taskId, e);
                        }
                    });

            log.info("Verify task {} crash-recovery dispatch complete — marking scheduler job DONE", taskId);
            return SchedulerJobResult.DONE;

        } catch (Exception e) {
            log.error("Error handling TASK_VERIFY_CHECK for taskId={}, tenantId={}", taskId, tenantId, e);
            return SchedulerJobResult.RETRY;
        }
    }

    @Override
    public void onMaxAttemptsExceeded(SchedulerJob job) {
        String taskId = job.getReferenceId();
        String tenantId = job.getTenantId();
        log.error("TASK_VERIFY_CHECK max attempts exceeded for taskId={} tenantId={} — forcing FAILED on stuck details",
                taskId, tenantId);
        try {
            RequestInfo requestInfo = objectMapper.convertValue(job.getContext(), RequestInfo.class);
            Task task = taskRepository.searchTask(Task.builder().id(taskId).tenantId(tenantId).build());
            if (task == null) return;

            TaskRequest taskRequest = TaskRequest.builder()
                    .task(task)
                    .bill(Bill.builder().id(task.getBillId()).tenantId(tenantId).build())
                    .requestInfo(requestInfo)
                    .build();

            mtnService.forceFailStuckVerifyDetails(taskRequest);
        } catch (Exception e) {
            log.error("CRITICAL: onMaxAttemptsExceeded failed for verify taskId={} — manual intervention required",
                    taskId, e);
        }
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
