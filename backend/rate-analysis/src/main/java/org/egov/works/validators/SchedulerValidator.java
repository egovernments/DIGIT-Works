package org.egov.works.validators;

import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.web.models.JobScheduler;
import org.egov.works.web.models.JobSchedulerRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@Slf4j
public class SchedulerValidator {

    public void validateSchedulerRequest(JobSchedulerRequest jobSchedulerRequest) {
        log.info("Validating JobSchedulerRequest");
        validateJobScheduler(jobSchedulerRequest.getSchedule());
        log.info("JobSchedulerRequest validated successfully");
    }

    private void validateJobScheduler(JobScheduler schedule) {
        log.info("Validating JobScheduler");
        if (!validateEffectiveFromDateToCurrentDay(schedule.getEffectiveFrom())) {
            throw new CustomException("INVALID_SCHEDULER_REQUEST", "Effective from date should be of the current day");
        }
    }

    private boolean validateEffectiveFromDateToCurrentDay(BigDecimal effectiveFrom) {
        // Get the start of the current day
        LocalDate currentDate = LocalDate.now();
        ZonedDateTime startOfDay = currentDate.atStartOfDay(ZoneId.systemDefault());
        BigDecimal startOfDayMillis = BigDecimal.valueOf(startOfDay.toInstant().toEpochMilli());

        // Get the end of the current day
        ZonedDateTime endOfDay = currentDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).minusNanos(1);
        BigDecimal endOfDayMillis = BigDecimal.valueOf(endOfDay.toInstant().toEpochMilli());

        // Check if the epoch time falls within the range of start and end of the current day
        return effectiveFrom.compareTo(startOfDayMillis) >= 0 && effectiveFrom.compareTo(endOfDayMillis) <= 0;
    }
}
