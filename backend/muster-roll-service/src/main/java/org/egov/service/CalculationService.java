package org.egov.service;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.tracer.model.CustomException;
import org.egov.util.MdmsUtil;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.util.MusterRollServiceConstants.*;

@Service
@Slf4j
public class CalculationService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MusterRollServiceConfiguration config;

    @Autowired
    private MdmsUtil mdmsUtils;

    private int entryHour;
    private int exitHourHalfDay;
    private int exitHourFullDay;

    /**
     * Calculate the per day attendance and attendance aggregate for each individual for create muster roll
     * @param musterRollRequest
     * @param isCreate
     *
     */
    public void createAttendance(MusterRollRequest musterRollRequest, boolean isCreate) {

        //fetch the log events for all individuals in a muster roll
        Map<String,List<LocalDateTime>> individualAttendanceMap = populateLogEvents(musterRollRequest);

        log.info("EnrichmentService::calculateAttendance::From MDMS::EXIT_HOUR_HALF_DAY::"+exitHourHalfDay+"::EXIT_HOUR_FULL_DAY::"+exitHourFullDay);
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        AuditDetails auditDetails = musterRoll.getAuditDetails();
        LocalDate startDate = Instant.ofEpochMilli(musterRoll.getStartDate()).atZone(ZoneId.of("Asia/Kolkata")).toLocalDate();
        LocalDate endDate = Instant.ofEpochMilli(musterRoll.getEndDate()).atZone(ZoneId.of("Asia/Kolkata")).toLocalDate();

        log.debug("EnrichmentService::calculateAttendance::startDate::"+startDate+"::endDate::"+endDate);

        //calculate attendance aggregate and per day per individual attendance
        List<IndividualEntry> individualEntries = new ArrayList<>();

        for (Map.Entry<String,List<LocalDateTime>> entry : individualAttendanceMap.entrySet()) {
            IndividualEntry individualEntry = new IndividualEntry();
            if (isCreate) {
                individualEntry.setId(UUID.randomUUID());
            }
            individualEntry.setIndividualId(entry.getKey());
            List<LocalDateTime> timeStampList = entry.getValue();
            List<AttendanceEntry> attendanceEntries = new ArrayList<>();
            LocalDate date = startDate;
            BigDecimal totalAttendance = BigDecimal.ZERO;
            while (date.isBefore(endDate) || date.isEqual(endDate)) {
                AttendanceEntry attendanceEntry = new AttendanceEntry();
                if (isCreate) {
                    attendanceEntry.setId(UUID.randomUUID());
                }
                attendanceEntry.setTime(date.atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant().toEpochMilli());

                //check if the individual's attendance is logged between startDate and endDate
                LocalDateTime timeStamp = null;
                for (LocalDateTime dateTime : timeStampList) {
                    if (date.isEqual(dateTime.toLocalDate())) {
                        timeStamp = dateTime;
                        break;
                    }
                }

                totalAttendance = getTotalAttendance(totalAttendance, attendanceEntry, timeStamp);

                date = date.plusDays(1);
                attendanceEntry.setAuditDetails(auditDetails);
                attendanceEntries.add(attendanceEntry);
            }

            individualEntry.setAttendanceEntries(attendanceEntries);
            log.debug("EnrichmentService::calculateAttendance::Attendance Entry::size::"+attendanceEntries.size());
            individualEntry.setAuditDetails(auditDetails);
            individualEntry.setTotalAttendance(totalAttendance);
            individualEntries.add(individualEntry);
        }
        musterRoll.setIndividualEntries(individualEntries);
        log.debug("EnrichmentService::calculateAttendance::Individuals::size::"+musterRoll.getIndividualEntries().size());

    }

    /**
     * Calculate the total attendance for the individual
     * @param totalAttendance
     * @param attendanceEntry
     * @param timeStamp
     *
     */
    private BigDecimal getTotalAttendance(BigDecimal totalAttendance, AttendanceEntry attendanceEntry, LocalDateTime timeStamp) {
        //set attendance if present else set as 0
        if (timeStamp != null) {
            if (timeStamp.getHour() == exitHourHalfDay) {
                attendanceEntry.setAttendance(new BigDecimal("0.5"));
            } else if (timeStamp.getHour() == exitHourFullDay) {
                attendanceEntry.setAttendance(BigDecimal.ONE);
            }
        } else {
            attendanceEntry.setAttendance(BigDecimal.ZERO);
        }

        //calculate totalAttendance
        totalAttendance = totalAttendance.add(attendanceEntry.getAttendance());
        return totalAttendance;
    }


    /**
     * Calculate the per day attendance and attendance aggregate for each individual - ACTION "RESUMBIT'
     * @param musterRollRequest
     *
     */
    public void updateAttendance(MusterRollRequest musterRollRequest) {

        //fetch the log events for all individuals in a muster roll
        Map<String,List<LocalDateTime>> individualAttendanceMap = populateLogEvents(musterRollRequest);

        log.info("EnrichmentService::calculateAttendance::From MDMS::EXIT_HOUR_HALF_DAY::"+exitHourHalfDay+"::EXIT_HOUR_FULL_DAY::"+exitHourFullDay);

        //calculate attendance aggregate and per day per individual attendance
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        AuditDetails auditDetails = musterRoll.getAuditDetails();
        List<IndividualEntry> individualEntries = musterRoll.getIndividualEntries();
        for (Map.Entry<String,List<LocalDateTime>> entry : individualAttendanceMap.entrySet()) {
            IndividualEntry individualEntry = individualEntries.stream()
                                                                .filter(individual -> individual.getIndividualId().equalsIgnoreCase(entry.getKey()))
                                                                .findFirst().get();
            List<LocalDateTime> timeStampList = entry.getValue();
            List<AttendanceEntry> attendanceEntries = individualEntry.getAttendanceEntries();
            BigDecimal totalAttendance = BigDecimal.ZERO;
            for (AttendanceEntry attendanceEntry : attendanceEntries) {
                LocalDate attendanceDate = Instant.ofEpochMilli(attendanceEntry.getTime()).atZone(ZoneId.of(/*serviceConfiguration.getTimeZone()*/"Asia/Kolkata")).toLocalDate();
                LocalDateTime timeStamp = timeStampList.stream()
                                                        .filter(dateTime -> dateTime.toLocalDate().isEqual(attendanceDate))
                                                        .findFirst().orElse(null);
                if (timeStamp != null) {
                    totalAttendance = getTotalAttendance(totalAttendance, attendanceEntry, timeStamp);
                    attendanceEntry.setAuditDetails(auditDetails);
                }
            }
            individualEntry.setAuditDetails(auditDetails);
            individualEntry.setTotalAttendance(totalAttendance);
        }

    }

    /**
     * Populate the attendance log events from attendance service and the attendance entry , exit time from MDMS
     * @param musterRollRequest
     * @return Map<String,List<LocalDateTime>>
     */
    private Map<String,List<LocalDateTime>> populateLogEvents(MusterRollRequest musterRollRequest) {

        //fetch the attendance log
        List<AttendanceLog> attendanceLogList = getAttendanceLogs(musterRollRequest.getMusterRoll(),musterRollRequest.getRequestInfo());

        //fetch the attendance time from MDMS
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        String rootTenantId = musterRoll.getTenantId().split("\\.")[0];
        Object mdmsData = mdmsUtils.mDMSCallMuster(musterRollRequest, rootTenantId);
        populateAttendanceTime(mdmsData);

        //populate the map with key as individualId and value as the corresponding list of exit time
        Map<String,List<LocalDateTime>> individualAttendanceMap = attendanceLogList.stream()
                .filter(log -> log.getType().equalsIgnoreCase(EXIT_EVENT))
                .collect(Collectors.groupingBy(
                        log -> log.getIndividualId().toString(),
                        LinkedHashMap::new,
                        Collectors.mapping(log -> Instant.ofEpochMilli(log.getTime().longValue()).atZone(ZoneId.of("Asia/Kolkata")).toLocalDateTime(),Collectors.toList())
                ));

        return individualAttendanceMap;
    }



    /**
     * Fetch the exit attendance log events for all individuals in an attendance register between the startDate and endDate
     * This is fetched from the AttendanceLog service
     * @param musterRoll
     * @param requestInfo
     *
     */
    private List<AttendanceLog> getAttendanceLogs(MusterRoll musterRoll, RequestInfo requestInfo){

        StringBuilder uri = new StringBuilder();
        uri.append(config.getAttendanceLogHost()).append(config.getAttendanceLogEndpoint());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .queryParam("tenantId",musterRoll.getTenantId())
                .queryParam("registerId",musterRoll.getRegisterId())
                .queryParam("fromTime",musterRoll.getStartDate())
                .queryParam("toTime",musterRoll.getEndDate().doubleValue())
                .queryParam("status",Status.ACTIVE);

        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

        AttendanceLogResponse attendanceLogResponse = restTemplate.postForObject(uriBuilder.toUriString(),requestInfoWrapper,AttendanceLogResponse.class);

        if (attendanceLogResponse == null || attendanceLogResponse.getAttendance() == null) {
            throw new CustomException("ATTENDANCE_LOG_EMPTY","No attendance log found for this register and date range");
        }

        return attendanceLogResponse.getAttendance();
    }

    /**
     * Fetch the entry , exit time from MDMS
     * @param mdmsData
     *
     */
    private void populateAttendanceTime(Object mdmsData) {

        final String jsonPathForWorksMuster = "$.MdmsRes." + MDMS_COMMON_MASTERS_MODULE_NAME + "." + MASTER_MUSTER_ROLL + ".*";
        List<LinkedHashMap<String,String>> musterRes = null;

        try {
            musterRes = JsonPath.read(mdmsData, jsonPathForWorksMuster);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }

        if (!CollectionUtils.isEmpty(musterRes)) {
            for (Object object : musterRes) {
                LinkedHashMap<String,String> codeValueMap = (LinkedHashMap<String, String>) object;
                String code = codeValueMap.get("code");
                String value = codeValueMap.get("value");
                switch (code) {
                    case ENTRY_HOUR :
                        entryHour = Integer.parseInt(value);
                        break;
                    case EXIT_HOUR_HALF_DAY :
                        exitHourHalfDay =Integer.parseInt(value);
                        break;
                    case EXIT_HOUR_FULL_DAY :
                        exitHourFullDay = Integer.parseInt(value);
                        break;
                }

            }
        }

    }

}
