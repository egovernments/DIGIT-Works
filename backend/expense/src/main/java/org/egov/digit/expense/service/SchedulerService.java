package org.egov.digit.expense.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.TaskRepository;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.Task;
import org.egov.digit.expense.web.models.TaskRequest;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SchedulerService {

    private final Configuration config;

    private MTNService mtnService;

    private TaskRepository taskRepository;

    private ExpenseProducer expenseProducer;

    private ObjectMapper mapper;

    @Autowired
    public SchedulerService(Configuration config, MTNService mtnService, TaskRepository taskRepository, ExpenseProducer expenseProducer,ObjectMapper mapper) {
        this.config = config;
        this.mtnService = mtnService;
        this.taskRepository = taskRepository;
        this.expenseProducer = expenseProducer;
        this.mapper = mapper;
    }

    @Scheduled(fixedRateString = "${bill.scheduled.task.fixed.rate.millisec}") // every 5 minutes
    public void UpdateInProgressPaymentStatus() {
        List<Task> inProgressPaymentTasks = taskRepository.getInProgressTasks(config.getScheduledTaskDelay().toString(),Task.Type.Transfer.toString());

        for (Task task : inProgressPaymentTasks) {
            try {
                log.info("Updating payment status for task: {}", task.getId());
                RequestInfo requestInfo = mapper.convertValue(task.getAdditionalDetails(), RequestInfo.class);
                TaskRequest taskRequest
                        = TaskRequest
                        .builder()
                            .requestInfo(requestInfo)
                            .task(task)
                            .bill(Bill.builder().id(task.getBillId()).tenantId(requestInfo.getUserInfo().getTenantId()).build())
                        .build();
                mtnService.updatePaymentTaskStatus(taskRequest);

            } catch (Exception e) {
                log.error("Error in Updating payment status for task {}", task.getId(),e);

            } finally {
                AuditDetails auditDetails = task.getAuditDetails();
                auditDetails.setLastModifiedTime(System.currentTimeMillis());
                task.setStatus(Status.DONE); // TODO : REMOVE FROM HERE AND MOVE TO SERVICE LOGIC
                expenseProducer.push(config.getTaskUpdateTopic(),task);
            }
        }
    }
}


