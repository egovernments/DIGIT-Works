package org.egov.works.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.service.SchedulerService;
import org.egov.works.web.models.JobScheduledRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class RateAnalysisConsumer {

    private final ObjectMapper objectMapper;
    private final SchedulerService schedulerService;

    @Autowired
    public RateAnalysisConsumer(ObjectMapper objectMapper, SchedulerService schedulerService) {
        this.objectMapper = objectMapper;
        this.schedulerService = schedulerService;
    }

    @KafkaListener(topics = {"${rate.analysis.job.create.topic}"})
    public void listen(final Map<String, Object> jobCreateRecord) {
        log.info("Received record for job creation: " + jobCreateRecord);
        try {
            JobScheduledRequest jobScheduledRequest = objectMapper.convertValue(jobCreateRecord, JobScheduledRequest.class);
            if (jobScheduledRequest != null && jobScheduledRequest.getRequestInfo() != null && jobScheduledRequest.getScheduledJobs() != null) {
                log.info("Processing job create request for record");
                schedulerService.createScheduledJobsFromConsumer(jobScheduledRequest);
            }
        } catch (Exception e) {
            log.error("Error while processing job create request for record: " + jobCreateRecord);
            log.error("Error: ", e);
        }
    }
}
