package org.egov.digit.expense.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.repository.TaskRepository;
import org.egov.digit.expense.service.BankPaymentService;
import org.egov.digit.expense.service.MTNService;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.SchedulerJob;
import org.egov.digit.expense.web.models.Task;
import org.egov.digit.expense.web.models.TaskRequest;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Handles {@link SchedulerJobType#TASK_STATUS_CHECK} jobs.
 *
 * <p>Context JSONB: serialised {@code RequestInfo}.
 *
 * <p>Per invocation:
 * <ol>
 *   <li>Loads the task (idempotent guard — returns DONE if already resolved).</li>
 *   <li>Calls {@code MTNService.updatePaymentTaskStatusAndFinalize}: polls MTN,
 *       updates task-detail + bill workflow, pushes {@code task.status=DONE}
 *       to Kafka when all details settle.</li>
 *   <li>Returns DONE / RETRY to the generic scheduler.</li>
 * </ol>
 */
@Component
@Slf4j
public class TaskStatusCheckHandler implements SchedulerJobHandler {

    private final MTNService mtnService;
    private final BankPaymentService bankPaymentService;
    private final TaskRepository taskRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public TaskStatusCheckHandler(MTNService mtnService,
                                   BankPaymentService bankPaymentService,
                                   TaskRepository taskRepository,
                                   ObjectMapper objectMapper) {
        this.mtnService = mtnService;
        this.bankPaymentService = bankPaymentService;
        this.taskRepository = taskRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public SchedulerJobType getJobType() {
        return SchedulerJobType.TASK_STATUS_CHECK;
    }

    @Override
    public SchedulerJobResult handle(SchedulerJob job) {
        String taskId = job.getReferenceId();
        String tenantId = job.getTenantId();

        try {
            RequestInfo requestInfo = objectMapper.convertValue(job.getContext(), RequestInfo.class);

            Task taskSearch = Task.builder().id(taskId).tenantId(tenantId).build();
            Task task = taskRepository.searchTask(taskSearch);

            if (task == null) {
                log.warn("Task {} not found for tenant {} — marking job FAILED", taskId, tenantId);
                return SchedulerJobResult.FAILED;
            }

            if (task.getStatus() == Status.DONE) {
                log.info("Task {} already DONE — marking scheduler job DONE", taskId);
                return SchedulerJobResult.DONE;
            }

            TaskRequest taskRequest = TaskRequest.builder()
                    .task(task)
                    .bill(Bill.builder().id(task.getBillId()).tenantId(tenantId).build())
                    .requestInfo(requestInfo)
                    .build();

            // Polls MTN and bank status; task is resolved only when both agree no details are in-progress.
            boolean mtnInProgress = mtnService.updatePaymentTaskStatusAndFinalize(taskRequest);
            boolean bankInProgress = bankPaymentService.updatePaymentTaskStatusAndFinalize(taskRequest);
            boolean anyInProgress = mtnInProgress || bankInProgress;

            if (!anyInProgress) {
                log.info("Task {} fully resolved — marking scheduler job DONE", taskId);
                return SchedulerJobResult.DONE;
            }

            log.info("Task {} still has IN_PROGRESS details — will retry", taskId);
            return SchedulerJobResult.RETRY;

        } catch (Exception e) {
            log.error("Error handling TASK_STATUS_CHECK for taskId={}, tenantId={}", taskId, tenantId, e);
            return SchedulerJobResult.RETRY;
        }
    }
}
