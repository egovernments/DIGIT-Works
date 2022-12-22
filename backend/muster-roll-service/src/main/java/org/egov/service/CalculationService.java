package org.egov.service;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
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

    private int halfDayNumHours;
    private int fullDayNumHours;
    private boolean isRoundOffHours;

    /**
     * Calculate the per day attendance and attendance aggregate for each individual for create muster roll
     * @param musterRollRequest
     * @param isCreate
     *
     */
    public void createAttendance(MusterRollRequest musterRollRequest, boolean isCreate) {

        //fetch the log events for all individuals in a muster roll
        List<AttendanceLog> attendanceLogList = fetchAttendanceLogsAndMDMSData(musterRollRequest);
        Map<String,List<LocalDateTime>> individualEntryAttendanceMap = populateAttendanceLogEvents(attendanceLogList,ENTRY_EVENT);
        Map<String,List<LocalDateTime>> individualExitAttendanceMap = populateAttendanceLogEvents(attendanceLogList,EXIT_EVENT);

        log.info("EnrichmentService::createAttendance::From MDMS::HALF_DAY_NUM_HOURS::"+halfDayNumHours+"::FULL_DAY_NUM_HOURS::"+fullDayNumHours);
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        AuditDetails auditDetails = musterRoll.getAuditDetails();
        LocalDate startDate = Instant.ofEpochMilli(musterRoll.getStartDate().longValue()).atZone(ZoneId.of(ZONE)).toLocalDate();
        LocalDate endDate = Instant.ofEpochMilli(musterRoll.getEndDate().longValue()).atZone(ZoneId.of(ZONE)).toLocalDate();

        log.debug("EnrichmentService::calculateAttendance::startDate::"+startDate+"::endDate::"+endDate);

        //calculate attendance aggregate and per day per individual attendance
        List<IndividualEntry> individualEntries = new ArrayList<>();

        for (Map.Entry<String,List<LocalDateTime>> entry : individualExitAttendanceMap.entrySet()) {
            IndividualEntry individualEntry = new IndividualEntry();
            if (isCreate) {
                individualEntry.setId(UUID.randomUUID().toString());
            }
            individualEntry.setIndividualId(entry.getKey());
            List<LocalDateTime> exitTimestampList = entry.getValue();
            List<LocalDateTime> entryTimestampList = individualEntryAttendanceMap.get(entry.getKey());
            List<AttendanceEntry> attendanceEntries = new ArrayList<>();
            LocalDate date = startDate;
            BigDecimal totalAttendance = new BigDecimal("0.0");
            while (date.isBefore(endDate) || date.isEqual(endDate)) {
                AttendanceEntry attendanceEntry = new AttendanceEntry();
                if (isCreate) {
                    attendanceEntry.setId(UUID.randomUUID().toString());
                }
                attendanceEntry.setTime(new BigDecimal(date.atStartOfDay(ZoneId.of(ZONE)).toInstant().toEpochMilli()));

                //check if the individual's entry attendance is logged between startDate and endDate
                LocalDateTime entryTimestamp = null;
                for (LocalDateTime dateTime : entryTimestampList) {
                    if (date.isEqual(dateTime.toLocalDate())) {
                        entryTimestamp = dateTime;
                        break;
                    }
                }

                //check if the individual's exit attendance is logged between startDate and endDate
                LocalDateTime exitTimestamp = null;
                for (LocalDateTime dateTime : exitTimestampList) {
                    if (date.isEqual(dateTime.toLocalDate())) {
                        exitTimestamp = dateTime;
                        break;
                    }
                }

                totalAttendance = getTotalAttendance(totalAttendance, attendanceEntry, entryTimestamp,exitTimestamp);

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
     * @param entryTimestamp
     * @param exitTimestamp
     *
     */
    private BigDecimal getTotalAttendance(BigDecimal totalAttendance, AttendanceEntry attendanceEntry, LocalDateTime entryTimestamp, LocalDateTime exitTimestamp) {
        //set attendance if present else set as 0
        if (entryTimestamp != null && exitTimestamp != null) {
            int workHours = exitTimestamp.getHour() - entryTimestamp.getHour();

            /** IF isRoundOffHours is true,
             * FullDayAttendance is given if the workHours is more than halfDayNumHours (even if it is less than fullDayNumHours).
             *  HalfDayAttendance is given if the workHours is less than halfDayNumHours. eg., 6hrs will be considered as fullDayAttendance, 2hrs as halfDayAttendance
             */
            attendanceEntry.setAttendance(new BigDecimal("0.5"));
            if (isRoundOffHours && workHours > halfDayNumHours) {
                attendanceEntry.setAttendance(new BigDecimal("1.0"));
            }

            if (!isRoundOffHours && workHours >= fullDayNumHours) {
                attendanceEntry.setAttendance(new BigDecimal("1.0"));
            }

        } else {
            attendanceEntry.setAttendance(new BigDecimal("0.0"));
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
        List<AttendanceLog> attendanceLogList = fetchAttendanceLogsAndMDMSData(musterRollRequest);
        Map<String,List<LocalDateTime>> individualEntryAttendanceMap = populateAttendanceLogEvents(attendanceLogList,ENTRY_EVENT);
        Map<String,List<LocalDateTime>> individualExitAttendanceMap = populateAttendanceLogEvents(attendanceLogList,EXIT_EVENT);

        log.info("EnrichmentService::updateAttendance::From MDMS::HALF_DAY_NUM_HOURS::"+halfDayNumHours+"::FULL_DAY_NUM_HOURS::"+fullDayNumHours);

        //calculate attendance aggregate and per day per individual attendance
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        AuditDetails auditDetails = musterRoll.getAuditDetails();
        List<IndividualEntry> individualEntries = musterRoll.getIndividualEntries();
        for (Map.Entry<String,List<LocalDateTime>> entry : individualExitAttendanceMap.entrySet()) {
            IndividualEntry individualEntry = individualEntries.stream()
                                                                .filter(individual -> individual.getIndividualId().equalsIgnoreCase(entry.getKey()))
                                                                .findFirst().get();

            List<LocalDateTime> exitTimestampList = entry.getValue();
            List<LocalDateTime> entryTimestampList = individualEntryAttendanceMap.get(entry.getKey());
            List<AttendanceEntry> attendanceEntries = individualEntry.getAttendanceEntries();
            BigDecimal totalAttendance = new BigDecimal("0.0");
            for (AttendanceEntry attendanceEntry : attendanceEntries) {
                LocalDate attendanceDate = Instant.ofEpochMilli(attendanceEntry.getTime().longValue()).atZone(ZoneId.of(ZONE)).toLocalDate();
                LocalDateTime entryTimestamp = entryTimestampList.stream()
                                                        .filter(dateTime -> dateTime.toLocalDate().isEqual(attendanceDate))
                                                        .findFirst().orElse(null);
                LocalDateTime exitTimestamp = exitTimestampList.stream()
                        .filter(dateTime -> dateTime.toLocalDate().isEqual(attendanceDate))
                        .findFirst().orElse(null);

                if (entryTimestamp != null && exitTimestamp != null) {
                    totalAttendance = getTotalAttendance(totalAttendance, attendanceEntry, entryTimestamp,exitTimestamp );
                    attendanceEntry.setAuditDetails(auditDetails);
                }
            }
            individualEntry.setAuditDetails(auditDetails);
            individualEntry.setTotalAttendance(totalAttendance);
        }

    }

    /**
     * Fetch the attendance logs and the halfDayNumHours, fullDayNumHours from MDMS
     * @param musterRollRequest
     * @return List<AttendanceLog>
     */
    private List<AttendanceLog> fetchAttendanceLogsAndMDMSData(MusterRollRequest musterRollRequest) {

        //fetch the attendance log
        List<AttendanceLog> attendanceLogList = getAttendanceLogs(musterRollRequest.getMusterRoll(),musterRollRequest.getRequestInfo());

        //fetch the attendance time from MDMS
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        String rootTenantId = musterRoll.getTenantId().split("\\.")[0];
        Object mdmsData = mdmsUtils.mDMSCallMuster(musterRollRequest, rootTenantId);
        populateAttendanceTime(mdmsData);

        return attendanceLogList;
    }

    /**
     * The method returns a map with key as individualId and value as list of entry or exit timestamp
     * @param attendanceLogList
     * @param event
     * @return
     */

    private Map<String,List<LocalDateTime>> populateAttendanceLogEvents(List<AttendanceLog> attendanceLogList, String event) {
        //populate the map with key as individualId and value as the corresponding list of exit time
        Map<String,List<LocalDateTime>> individualAttendanceMap = attendanceLogList.stream()
                .filter(attendanceLog -> attendanceLog.getType().equalsIgnoreCase(event))
                .collect(Collectors.groupingBy(
                        attendanceLog -> attendanceLog.getIndividualId(), //key
                        LinkedHashMap::new, // populate the map
                        Collectors.mapping(attendanceLog -> Instant.ofEpochMilli(attendanceLog.getTime().longValue()).atZone(ZoneId.of(ZONE)).toLocalDateTime(),Collectors.toList()) //value is the list of timestamp
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
                .queryParam("fromTime",musterRoll.getStartDate().longValue())
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
                    case HALF_DAY_NUM_HOURS :
                        halfDayNumHours = Integer.parseInt(value);
                        break;
                    case FULL_DAY_NUM_HOURS :
                        fullDayNumHours =Integer.parseInt(value);
                        break;
                    case ROUND_OFF_HOURS :
                        isRoundOffHours = BooleanUtils.toBoolean(value);
                        break;
                }

            }
        }

    }

}
