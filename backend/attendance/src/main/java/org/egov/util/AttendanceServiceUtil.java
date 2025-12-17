package org.egov.util;

import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.repository.AttendanceLogRepository;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.egov.web.models.*;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@Slf4j
public class AttendanceServiceUtil {
    @Autowired
    private AttendanceLogRepository attendanceLogRepository;
    public AuditDetails getAuditDetails(String by, AuditDetails auditDetails, Boolean isCreate) {
        Long time = System.currentTimeMillis();
        if (isCreate)
            return AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
        else
            return AuditDetails.builder().createdBy(auditDetails.getCreatedBy()).lastModifiedBy(by)
                    .createdTime(auditDetails.getCreatedTime()).lastModifiedTime(time).build();
    }

    public void checkAttendanceLogsForIndividual(AttendeeDeleteRequest attendeeDeleteRequest) {

        List<IndividualEntry> individualEntryList = attendeeDeleteRequest.getAttendees();
        Set<String> individualIdSet = new HashSet<>();
        long deenrollementDate = 0;
        String registerId = "";
        for (IndividualEntry individualEntry : individualEntryList) {
            if (null != individualEntry.getDenrollmentDate()) {
                deenrollementDate = individualEntry.getDenrollmentDate().longValue();
                registerId = individualEntry.getRegisterId();
            }

            individualIdSet.add(individualEntry.getIndividualId());
        }


        if (0 != deenrollementDate && !registerId.isEmpty()) {
            fetchAttendanceLogs(deenrollementDate, registerId, individualIdSet);
        } else {
            log.info("REQUIRED_PARAMS_NOT_FOUND", "Either de-enrollementdate or registerId is empty");
        }


    }

    private void fetchAttendanceLogs(long deenrollementDate, String registerId, Set<String> individualIds) {
        // Convert epoch milliseconds to Instant
        Instant instant = Instant.ofEpochMilli(deenrollementDate);

        // Convert Instant to LocalDate to get the specific date
        LocalDate specificDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        // Find the start of the week (Monday) for the specific date
        LocalDate startOfWeek = specificDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // Find the end of the week (Sunday) for the specific date
        LocalDate endOfWeek = specificDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        // Convert LocalDate to epoch milliseconds (Unix timestamp)
        long startOfWeekEpoch = startOfWeek.atStartOfDay(ZoneOffset.UTC).toEpochSecond() * 1000;
        long endOfWeekEpoch = endOfWeek.atTime(LocalTime.MAX).atZone(ZoneOffset.UTC).toEpochSecond() * 1000;
        AttendanceLogSearchCriteria searchCriteria = AttendanceLogSearchCriteria.builder()
                .individualIds(new ArrayList<>(individualIds))
                .registerId(registerId)
                .fromTime(BigDecimal.valueOf(startOfWeekEpoch))
                .toTime(BigDecimal.valueOf(endOfWeekEpoch))
                .build();

        List<AttendanceLog> attendanceLogs = attendanceLogRepository.getAttendanceLogs(searchCriteria);


        if (null != attendanceLogs && !attendanceLogs.isEmpty()) {
            log.info("ATTENDANCE_LOGS_FETCHED","Attendance Logs fetched for the individuals");
            // Filter attendanceLogs based on individualIds and active status
            for (String individualId : individualIds) {
                List<AttendanceLog> filteredEntryLog = attendanceLogs.stream()
                        .filter(log -> individualId.equals(log.getIndividualId())
                                && log.getStatus().equals(Status.ACTIVE)
                                && log.getType().equals("ENTRY")
                                && isSameDay(log.getTime().longValue(), deenrollementDate)
                        )
                        .collect(Collectors.toList());
                List<AttendanceLog> filteredExitLog = attendanceLogs.stream()
                        .filter(log -> individualId.equals(log.getIndividualId())
                                && log.getStatus().equals(Status.ACTIVE)
                                && log.getType().equals("EXIT")
                                && isSameDay(log.getTime().longValue(), deenrollementDate)
                        )
                        .collect(Collectors.toList());

                BigDecimal entryTime = filteredEntryLog.get(0).getTime();
                BigDecimal exitTime = filteredExitLog.get(0).getTime();
                if(entryTime.longValue()==exitTime.longValue()){
                    continue;
                }

                if(isDateGreaterThanOrEqualTo(deenrollementDate, entryTime.longValue()) ||
                        isDateGreaterThanOrEqualTo(exitTime.longValue(), deenrollementDate)) {
                    throw new CustomException("ATTENDANCE_ALREADY_MARKED", "Today's attendance for the selected wage seeker:" + individualId + "is marked as present. " +
                            "Please update the attendance record before dis-engaging the wage seeker.");

                }
            }


        }

    }

    public boolean isSameDay(long date1Millis, long date2Millis) {
        LocalDate date1 = Instant.ofEpochMilli(date1Millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate date2 = Instant.ofEpochMilli(date2Millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return date1.equals(date2);
    }

    public boolean isDateGreaterThanOrEqualTo(long epochDate1, long epochDate2) {
        return epochDate1 >= epochDate2;
    }

}
