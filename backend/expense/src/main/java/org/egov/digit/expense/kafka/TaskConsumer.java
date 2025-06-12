package org.egov.digit.expense.kafka;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.service.MTNService;
import org.egov.digit.expense.web.models.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskConsumer {

    private final ObjectMapper objectMapper;

    private final MTNService mtnService;

    @Autowired
    public TaskConsumer(ObjectMapper objectMapper, MTNService mtnService) {
        this.objectMapper = objectMapper;
        this.mtnService = mtnService;
    }

    @KafkaListener(topics = {"${expense.bill.task}"})
    public void listen(final Map<String, Object> message) {
        log.info("Consuming message from kafka topic: " + message);
        TaskRequest taskRequest = objectMapper.convertValue(message, TaskRequest.class);
        mtnService.executeTask(taskRequest);
    }
}
