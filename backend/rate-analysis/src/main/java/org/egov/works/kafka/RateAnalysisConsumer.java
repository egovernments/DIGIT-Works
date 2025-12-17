package org.egov.works.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.config.Configuration;
import org.egov.works.service.RateAnalysisService;
import org.egov.works.service.SchedulerService;
import org.egov.works.validator.SchedulerValidator;
import org.egov.works.web.models.JobScheduledRequest;
import org.egov.works.web.models.MdmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class RateAnalysisConsumer {

    private final ObjectMapper objectMapper;
    private final SchedulerService schedulerService;
    private final Configuration configuration;
    private final RateAnalysisService rateAnalysisService;
    private final SchedulerValidator schedulerValidator;

    @Autowired
    public RateAnalysisConsumer(ObjectMapper objectMapper, SchedulerService schedulerService, Configuration configuration, RateAnalysisService rateAnalysisService, SchedulerValidator schedulerValidator) {
        this.objectMapper = objectMapper;
        this.schedulerService = schedulerService;
        this.configuration = configuration;
        this.rateAnalysisService = rateAnalysisService;
        this.schedulerValidator = schedulerValidator;
    }

    @KafkaListener(topics = {"${rate.analysis.job.create.topic}"})
    public void listen(final Map<String, Object> jobCreateRecord) {
        log.info("Received record for job creation: " + jobCreateRecord);
        try {
            JobScheduledRequest jobScheduledRequest = objectMapper.convertValue(jobCreateRecord, JobScheduledRequest.class);
            Boolean isValid = schedulerValidator.validateJobScheduledRequest(jobScheduledRequest);
            if (jobScheduledRequest.getRequestInfo() != null && jobScheduledRequest.getScheduledJobs() != null && Boolean.FALSE.equals(isValid)) {
                log.info("Processing job create request for record");
                schedulerService.createScheduledJobsFromConsumer(jobScheduledRequest);
            }
        } catch (Exception e) {
            log.error("Error while processing job create request for record: " + jobCreateRecord);
            log.error("Error: ", e);
        }
    }

    @KafkaListener(topics = {"${egov.mdms.data.save.topic}"})
    public void listenMdms(final Map<String, Object> mdmsRecord) {
        log.info("Received record for mdms save: " + mdmsRecord);
        try {
            MdmsRequest mdmsRequest = objectMapper.convertValue(mdmsRecord, MdmsRequest.class);
            if(mdmsRequest != null && mdmsRequest.getRequestInfo() != null && mdmsRequest.getMdms() != null && (mdmsRequest.getMdms().getSchemaCode().equals(configuration.getRatesSchemaCode()) ||
                    mdmsRequest.getMdms().getSchemaCode().equals(configuration.getCompositionSchemaCode()))&& configuration.getIsMdmsConsumerNeeded()) {
                log.info("Processing mdms save request for record");
                rateAnalysisService.updateMdmsDataForRatesAndComposition(mdmsRequest);
            }
        } catch (Exception e) {
            log.error("Error while processing mdms save request for record: " + mdmsRecord);
            log.error("Error: ", e);
        }
    }
}