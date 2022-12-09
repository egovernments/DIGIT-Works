package org.egov.service;

import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.IdResponse;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.repository.IdGenRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.MusterRollServiceUtil;
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

import static org.egov.util.MusterRollServiceConstants.EXIT_EVENT;

@Service
@Slf4j
public class EnrichmentService {

    @Autowired
    private MusterRollServiceUtil musterRollServiceUtil;

    @Autowired
    private MusterRollServiceConfiguration config;

    @Autowired
    private IdGenRepository idGenRepository;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * Enrich estimate muster roll
     * @param musterRollRequest
     *
     */
    public void enrichEstimateMusterRoll(MusterRollRequest musterRollRequest, AttendanceTime attendanceTime) {
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();

        //Audit details
        AuditDetails auditDetails = musterRollServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), musterRoll, true);
        musterRoll.setAuditDetails(auditDetails);

        //status
        musterRoll.setStatus(Status.ACTIVE);

        //calculate attendance
        calculateAttendance(musterRollRequest,auditDetails,attendanceTime);

    }

    /**
     * Enrich create muster roll
     * @param musterRollRequest
     *
     */
    public void enrichCreateMusterRoll(MusterRollRequest musterRollRequest,AttendanceTime attendanceTime) {
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();

        //Id
        musterRoll.setId(UUID.randomUUID());

        //Audit details
        AuditDetails auditDetails = musterRollServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), musterRoll, true);
        musterRoll.setAuditDetails(auditDetails);

        //musterRollNumber - Idgen
        String rootTenantId = musterRoll.getTenantId().split("\\.")[0];
        List<String> musterNumbers = getIdList(requestInfo, rootTenantId
                , config.getIdgenMusterRollNumberName(), config.getIdgenMusterRollNumberFormat(), 1);
        if (musterNumbers != null && !musterNumbers.isEmpty()) {
            String musterRollNumber = musterNumbers.get(0);
            musterRoll.setMusterRollNumber(musterRollNumber);
        }

        //status
        musterRoll.setStatus(Status.ACTIVE);

        //calculate attendance
        calculateAttendance(musterRollRequest,auditDetails,attendanceTime);

    }

    /**
     * Calculate the per day attendance and attendance aggregate for each individual
     * @param musterRollRequest
     *
     */
    private void calculateAttendance (MusterRollRequest musterRollRequest,AuditDetails auditDetails,AttendanceTime attendanceTime) {

        //fetch the attendance log
        List<AttendanceLog> attendanceLogList = fetchAttendanceLog(musterRollRequest.getMusterRoll(),musterRollRequest.getRequestInfo());

        //populate the map with key as individualId and value as the corresponding list of exit time
        Map<String,List<LocalDateTime>> individualAttendanceMap = attendanceLogList.stream()
                                                                    .filter(log -> log.getType().equalsIgnoreCase(EXIT_EVENT))
                                                                    .collect(Collectors.groupingBy(
                                                                         log ->log.getIndividualId(),
                                                                         LinkedHashMap::new,
                                                                         Collectors.mapping(log -> Instant.ofEpochMilli(log.getTime().longValue()).atZone(ZoneId.of("Asia/Kolkata")).toLocalDateTime(),Collectors.toList())
                                                                    ));


        //calculate attendance aggregate and per day per individual attendance
        int EXIT_HOUR_HALF_DAY  = attendanceTime.getExitHourHalfDay();
        int EXIT_HOUR_FULL_DAY  = attendanceTime.getExitHourFullDay();

        log.info("EnrichmentService::calculateAttendance::From MDMS::EXIT_HOUR_HALF_DAY::"+EXIT_HOUR_HALF_DAY+"::EXIT_HOUR_FULL_DAY::"+EXIT_HOUR_FULL_DAY);

        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        LocalDate startDate = Instant.ofEpochMilli(musterRoll.getStartDate()).atZone(ZoneId.of("Asia/Kolkata")).toLocalDate();
        LocalDate endDate = Instant.ofEpochMilli(musterRoll.getEndDate()).atZone(ZoneId.of("Asia/Kolkata")).toLocalDate();

        log.debug("EnrichmentService::calculateAttendance::startDate::"+startDate+"::endDate::"+endDate);

        List<IndividualEntry> individualEntries = new ArrayList<>();

        for (Map.Entry<String,List<LocalDateTime>> entry : individualAttendanceMap.entrySet()) {
            IndividualEntry individualEntry = new IndividualEntry();
            individualEntry.setIndividualId(entry.getKey());
            List<LocalDateTime> timeStampList = entry.getValue();
            List<AttendanceEntry> attendanceEntries = new ArrayList<>();
            LocalDate date = startDate;
            BigDecimal totalAttendance = BigDecimal.ZERO;
            while (date.isBefore(endDate) || date.isEqual(endDate)) {
                AttendanceEntry attendanceEntry = new AttendanceEntry();
                attendanceEntry.setTime(date.atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant().toEpochMilli());

                //check if the individual's attendance is logged between startDate and endDate
                LocalDateTime timeStamp = null;
                for (LocalDateTime dateTime : timeStampList) {
                    if (date.isEqual(dateTime.toLocalDate())) {
                        timeStamp = dateTime;
                        break;
                    }
                }

                //set attendance if present else set as 0 for all days from start to end date
                if (timeStamp != null) {
                    if (timeStamp.getHour() == EXIT_HOUR_HALF_DAY) {
                        attendanceEntry.setAttendance(new BigDecimal("0.5"));
                    } else if (timeStamp.getHour() == EXIT_HOUR_FULL_DAY) {
                        attendanceEntry.setAttendance(BigDecimal.ONE);
                    }
                } else {
                    attendanceEntry.setAttendance(BigDecimal.ZERO);
                }

                //calculate totalAttendance
                totalAttendance = totalAttendance.add(attendanceEntry.getAttendance());

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

    private List<AttendanceLog> fetchAttendanceLog(MusterRoll musterRoll, RequestInfo requestInfo){

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
     * Returns a list of numbers generated from idgen
     *
     * @param requestInfo RequestInfo from the request
     * @param tenantId    tenantId of the city
     * @param idKey       code of the field defined in application properties for which ids are generated for
     * @param idformat    format in which ids are to be generated
     * @param count       Number of ids to be generated
     * @return List of ids generated using idGen service
     */
    private List<String> getIdList(RequestInfo requestInfo, String tenantId, String idKey,
                                   String idformat, int count) {
        List<IdResponse> idResponses = idGenRepository.getId(requestInfo, tenantId, idKey, idformat, count).getIdResponses();

        if (CollectionUtils.isEmpty(idResponses))
            throw new CustomException("IDGEN ERROR", "No ids returned from idgen Service");

        return idResponses.stream()
                .map(IdResponse::getId).collect(Collectors.toList());
    }
}
