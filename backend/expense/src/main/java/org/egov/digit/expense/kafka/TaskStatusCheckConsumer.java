package org.egov.digit.expense.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.service.MTNService;
import org.egov.digit.expense.web.models.TaskStatusCheckRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class TaskStatusCheckConsumer {

    private final ObjectMapper objectMapper;
    private final MTNService mtnService;
    private final ExpenseProducer expenseProducer;
    private final Configuration config;

    @Autowired
    public TaskStatusCheckConsumer(ObjectMapper objectMapper,
                                   MTNService mtnService,
                                   ExpenseProducer expenseProducer,
                                   Configuration config) {
        this.objectMapper = objectMapper;
        this.mtnService = mtnService;
        this.expenseProducer = expenseProducer;
        this.config = config;
    }

    /**
     * Consumes task status check events. Uses nack(delay) to re-deliver the message
     * after the processAfterTime has been reached — no re-publish needed for the wait.
     *
     * Once due, checks MTN transfer status via MTNService:
     *   - All done    → acknowledge, done
     *   - Still pending → re-enqueue with new processAfterTime (retryCount++)
     *   - Max retries   → push to DLT for manual review
     */
    @KafkaListener(
            topicPattern = "(${expense.kafka.tenant.id.pattern}){0,1}${expense.task.status.check.topic}",
            containerFactory = "manualAckKafkaListenerContainerFactory"
    )
    public void checkTaskStatus(
            final Map<String, Object> message,
            final Acknowledgment ack,
            @Header(name = "processAfterTime", required = false) byte[] processAfterTimeHeader) {

        long now = System.currentTimeMillis();
        long processAfterTime = parseProcessAfterTime(processAfterTimeHeader);

        // Not yet time — nack causes Spring Kafka to seek back and pause partition
        if (processAfterTime > 0 && now < processAfterTime) {
            long delay = processAfterTime - now;
            log.debug("Task status check not yet due, pausing partition for {}ms", delay);
            ack.nack(delay);
            return;
        }

        try {
            TaskStatusCheckRequest request = objectMapper.convertValue(message, TaskStatusCheckRequest.class);
            log.info("Checking task status for taskId: {}, tenantId: {}, retryCount: {}/{}",
                    request.getTaskId(), request.getTenantId(),
                    request.getRetryCount(), request.getMaxRetries());

            boolean stillPending = mtnService.checkAndFinalizeTaskStatus(request);

            if (!stillPending) {
                log.info("Task {} fully resolved after {} retries", request.getTaskId(), request.getRetryCount());
                ack.acknowledge();
                return;
            }

            if (request.getRetryCount() >= request.getMaxRetries()) {
                log.error("Task {} exhausted all {} retries — pushing to DLT for manual review",
                        request.getTaskId(), request.getMaxRetries());
                expenseProducer.push(request.getTenantId(), config.getTaskStatusCheckDltTopic(), request);
                ack.acknowledge();
                return;
            }

            // Re-enqueue with incremented retry count and next processAfterTime
            request.setRetryCount(request.getRetryCount() + 1);
            long nextCheckTime = System.currentTimeMillis() + config.getTaskStatusCheckRetryDelayMs();
            log.info("Task {} still pending, re-enqueuing for retry {}/{} at epoch {}",
                    request.getTaskId(), request.getRetryCount(), request.getMaxRetries(), nextCheckTime);
            expenseProducer.pushWithDelay(
                    request.getTenantId(),
                    config.getTaskStatusCheckTopic(),
                    request,
                    nextCheckTime
            );
            ack.acknowledge();

        } catch (Exception e) {
            log.error("Unexpected error during task status check", e);
            ack.acknowledge(); // avoid blocking the topic on unexpected errors
        }
    }

    private long parseProcessAfterTime(byte[] headerBytes) {
        if (headerBytes == null || headerBytes.length == 0) return 0;
        try {
            return Long.parseLong(new String(headerBytes));
        } catch (NumberFormatException e) {
            log.warn("Invalid processAfterTime header value, treating as 0");
            return 0;
        }
    }
}
