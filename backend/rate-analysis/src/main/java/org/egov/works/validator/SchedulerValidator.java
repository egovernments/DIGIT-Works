package org.egov.works.validator;

import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.service.RedisService;
import org.egov.works.web.models.JobScheduledRequest;
import org.egov.works.web.models.JobScheduler;
import org.egov.works.web.models.JobSchedulerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@Slf4j
public class SchedulerValidator {

    private final RedisService redisService;

    @Autowired
    public SchedulerValidator(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * Validates the JobSchedulerRequest
     *
     * @param jobSchedulerRequest The JobSchedulerRequest
     */
    public void validateSchedulerRequest(JobSchedulerRequest jobSchedulerRequest) {
        log.info("SchedulerValidator: validateSchedulerRequest");
        validateJobScheduler(jobSchedulerRequest.getSchedule());
        log.info("JobSchedulerRequest validated successfully");
    }
    /**
     * Validates the JobScheduler
     * @param schedule The JobScheduler
     */
    private void validateJobScheduler(JobScheduler schedule) {
        log.info("SchedulerValidator: validateJobScheduler");
        //validating date range
        if (!validateEffectiveFromDateToCurrentDay(schedule.getEffectiveFrom())) {
            throw new CustomException("INVALID_SCHEDULER_REQUEST", "Effective from date should be of the current day");
        }
    }

    /**
     * Validates the effective from date to the current day
     *
     * @param effectiveFrom The effective from date
     * @return True if the effective from date is valid, false otherwise
     */
    public boolean validateEffectiveFromDateToCurrentDay(Long effectiveFrom) {
        log.info("SchedulerValidator: validateEffectiveFromDateToCurrentDay");
        // Get the start of the current day
        LocalDate currentDate = LocalDate.now();
        ZonedDateTime startOfDay = currentDate.atStartOfDay(ZoneId.systemDefault());
        Long startOfDayMillis = startOfDay.toInstant().toEpochMilli();

        // Check if the epoch time falls within the range of start and end of the current day
        return effectiveFrom.compareTo(startOfDayMillis) >= 0;
    }

    public Boolean validateJobScheduledRequest(JobScheduledRequest jobScheduledRequest) {
        log.info("SchedulerValidator: validateJobScheduledRequest");
        try {
            String jobId = jobScheduledRequest.getScheduledJobs().getJobId();
        if (Boolean.TRUE.equals(redisService.isJobPresentInCache(jobId))) {
            return true;
        }
        redisService.setCacheForJob(jobId);
        return false;
        }catch (Exception e) {
            log.error("Error while calling redis service", e);
            throw new CustomException("REDIS_ERROR", "Error while calling redis service");
        }
    }
}