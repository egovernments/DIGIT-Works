package org.egov.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.individual.Individual;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.util.IndividualServiceUtil;
import org.egov.web.models.AttendeeSearchCriteria;
import org.egov.web.models.AttendanceLogSearchCriteria;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.egov.web.models.StaffSearchCriteria;
import org.egov.repository.AttendanceLogRepository;
import org.egov.repository.AttendeeRepository;
import org.egov.repository.RegisterRepository;
import org.egov.repository.StaffRepository;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AttendanceLogServiceValidator {

    private final StaffRepository attendanceStaffRepository;

    private final RegisterRepository attendanceRegisterRepository;

    private final AttendeeRepository attendanceAttendeeRepository;

    private final AttendanceLogRepository attendanceLogRepository;

    private final AttendanceServiceConfiguration config;

    private final IndividualServiceUtil individualServiceUtil;

    @Autowired
    public AttendanceLogServiceValidator(StaffRepository attendanceStaffRepository, RegisterRepository attendanceRegisterRepository, AttendeeRepository attendanceAttendeeRepository, AttendanceLogRepository attendanceLogRepository, AttendanceServiceConfiguration config, IndividualServiceUtil individualServiceUtil) {
        this.attendanceStaffRepository = attendanceStaffRepository;
        this.attendanceRegisterRepository = attendanceRegisterRepository;
        this.attendanceAttendeeRepository = attendanceAttendeeRepository;
        this.attendanceLogRepository = attendanceLogRepository;
        this.config = config;
        this.individualServiceUtil = individualServiceUtil;
    }

    public void validateCreateAttendanceLogRequest(AttendanceLogRequest attendanceLogRequest) {
        log.info("Validate attendance log create request");
        validateAttendanceLogRequest(attendanceLogRequest);

        // Verify all the attendance logs should below to same registerId
        validateMultipleRegisterIds(attendanceLogRequest);

        // Verify all the attendance logs should below to same tenantId
        validateMultipleTenantIds(attendanceLogRequest);

        // Verify the Logged-in user is associated to the given register.
        validateLoggedInUser(attendanceLogRequest);

        // Verify given attendance log against register params
        validateAttendanceLogsAgainstRegisterParams(attendanceLogRequest);

        // Check if the attendance of an individual is marked for a same day in different project
        checkAttendeeIsMappedToAnotherRegisterOrNot(attendanceLogRequest, Boolean.FALSE);

        // Verify if individuals are part of the given register and individuals were active during given attendance log time.
        validateAttendees(attendanceLogRequest);

        // Verify provided documentIds are valid.
        validateDocumentIds(attendanceLogRequest);
        log.info("Attendance log create request validation done");
    }

    private void validateMultipleTenantIds(AttendanceLogRequest attendanceLogRequest) {
        List<AttendanceLog> attendanceLogs = attendanceLogRequest.getAttendance();
        Set<String> tenantIds = new HashSet<>();
        for(AttendanceLog attendanceLog : attendanceLogs){
            String tenantId = attendanceLog.getTenantId();
            if(tenantIds.isEmpty()){
                tenantIds.add(tenantId);
            }else{
                if(!tenantIds.contains(tenantId)){
                    log.error("Attendance logs should below to same tenantId");
                    throw new CustomException("MULTIPLE_TENANTIDS","Attendance logs should belong to same tenantId");
                }
            }
        }
    }

    private void validateMultipleRegisterIds(AttendanceLogRequest attendanceLogRequest) {
        List<AttendanceLog> attendanceLogs = attendanceLogRequest.getAttendance();
        Set<String> registerIds = new HashSet<>();
        for(AttendanceLog attendanceLog : attendanceLogs){
            String registerId = attendanceLog.getRegisterId();
            if(registerIds.isEmpty()){
                registerIds.add(registerId);
            }else{
                if(!registerIds.contains(registerId)){
                    log.error("Attendance logs should below to same registerId");
                    throw new CustomException("MULTIPLE_REGISTERIDS","Attendance logs should belong to same registerId");
                }
            }
        }
    }

    public void validateUpdateAttendanceLogRequest(AttendanceLogRequest attendanceLogRequest) {
        log.info("Validate attendance log update request");
        validateAttendanceLogRequest(attendanceLogRequest);

        // Verify all the attendance logs should below to same registerId
        validateMultipleRegisterIds(attendanceLogRequest);

        // Verify all the attendance logs should below to same tenantId
        validateMultipleTenantIds(attendanceLogRequest);

        // Verify the Logged-in user is associated to the given register.
        validateLoggedInUser(attendanceLogRequest);

        // Verify provided log ids are present
        validateAttendanceLogIds(attendanceLogRequest);

        // Verify given attendance log against register params
        validateAttendanceLogsAgainstRegisterParams(attendanceLogRequest);

        // Check if the attendance of an individual is marked for a same day in different project
        checkAttendeeIsMappedToAnotherRegisterOrNot(attendanceLogRequest, Boolean.TRUE);


        // Verify if individuals are part of the given register and individuals were active during given attendance log time.
        validateAttendees(attendanceLogRequest);

        // Verify provided documentIds are valid.
        validateDocumentIds(attendanceLogRequest);

        log.info("Attendance log update request validation done");
    }

    private void validateAttendanceLogsAgainstRegisterParams(AttendanceLogRequest attendanceLogRequest){
        String registerId = attendanceLogRequest.getAttendance().get(0).getRegisterId();
        String tenantId = attendanceLogRequest.getAttendance().get(0).getTenantId();

        // Fetch register for given registerId
        List<AttendanceRegister> attendanceRegisters = fetchRegisterWithId(registerId);

        // Check existence of register
        checkRegisterExistence(attendanceRegisters,registerId);

        AttendanceRegister attendanceRegister = attendanceRegisters.get(0);

        // Check register is active ?
        checkRegisterStatus(attendanceRegister);

        // Check register association with tenantId
        validateTenantIdAssociationWithRegisterId(attendanceRegister,tenantId);

        // Check attendance log time against register start and end date
        validateAttendanceLogTimeWithRegisterStartEndDate(attendanceRegister,attendanceLogRequest);

        log.info("Attendance log verification against register params are done. RegisterId ["+registerId+"]");
    }

    private void checkRegisterStatus(AttendanceRegister attendanceRegister) {
        if(Status.INACTIVE.equals(attendanceRegister.getStatus())){
            String registerId = attendanceRegister.getId();
            log.error("Register ["+registerId+"] is inactive");
            throw new CustomException("INACTIVE_REGISTER", "Given RegisterId ["+registerId+"] is inactive");
        }
    }

    private void checkRegisterExistence(List<AttendanceRegister> attendanceRegisters,String registerId) {
        if (attendanceRegisters == null || attendanceRegisters.isEmpty()) {
            log.error("Register ["+registerId+"] does not exists");
            throw new CustomException("REGISTER_NOT_FOUND", "Given RegisterId ["+registerId+"] does not exists");
        }
    }

    private void validateTenantIdAssociationWithRegisterId(AttendanceRegister attendanceRegister,String tenantId) {
        if(!tenantId.equals(attendanceRegister.getTenantId())){
            log.error("TenantId ["+tenantId+"] is not associated with register ["+attendanceRegister.getId()+"]");
            throw new CustomException("INVALID_TENANTID", "TenantId ["+tenantId+"] is not associated with register ["+attendanceRegister.getId()+"]");
        }
    }

    private List<AttendanceRegister> fetchRegisterWithId(String registerId) {
        AttendanceRegisterSearchCriteria searchCriteria = AttendanceRegisterSearchCriteria
                .builder()
                .ids(Collections.singletonList(registerId))
                .build();
        return attendanceRegisterRepository.getRegister(searchCriteria);
    }

    private void validateAttendanceLogTimeWithRegisterStartEndDate(AttendanceRegister attendanceRegister,AttendanceLogRequest attendanceLogRequest) {
        Instant registerStartTime = Instant.ofEpochMilli(attendanceRegister.getStartDate().longValue());

        Instant registerEndTime = null;
        if(attendanceRegister.getEndDate() != null)
            registerEndTime = Instant.ofEpochMilli(attendanceRegister.getEndDate().longValue());

        List<AttendanceLog> attendanceLogs = attendanceLogRequest.getAttendance();

        if(registerEndTime != null){
            for(AttendanceLog attendanceLog : attendanceLogs){
                Instant instantAttendanceAttendeeLogTime = Instant.ofEpochMilli(attendanceLog.getTime().longValue());
                if(!(instantAttendanceAttendeeLogTime.compareTo(registerStartTime) >=0 && instantAttendanceAttendeeLogTime.compareTo(registerEndTime) <=0)){
                    log.error("Attendance time ["+instantAttendanceAttendeeLogTime+"] is invalid for register ["+attendanceRegister.getId()+"]");
                    throw new CustomException("INVALID_ATTENDANCE_TIME", "Attendance time ["+instantAttendanceAttendeeLogTime+"] is invalid for register ["+attendanceRegister.getId()+"]");
                }
            }
        }else{
            for(AttendanceLog attendanceLog : attendanceLogs){
                Instant instantAttendanceAttendeeLogTime = Instant.ofEpochMilli(attendanceLog.getTime().longValue());
                if(!(instantAttendanceAttendeeLogTime.compareTo(registerStartTime) >=0)){
                    log.error("Attendance time ["+instantAttendanceAttendeeLogTime+"] is invalid for register ["+attendanceRegister.getId()+"]");
                    throw new CustomException("INVALID_ATTENDANCE_TIME", "Attendance time ["+instantAttendanceAttendeeLogTime+"] is invalid for register ["+attendanceRegister.getId()+"]");
                }
            }
        }
    }
    private void validateDocumentIds(AttendanceLogRequest attendanceLogRequest) {
        if ("TRUE".equalsIgnoreCase(config.getDocumentIdVerificationRequired())) {
            //TODO
            // For now throwing exception. Later implementation will be done
            log.error("Document service not integrated yet");
            throw new CustomException("SERVICE_UNAVAILABLE", "Service not integrated yet");
        }
    }

    private void validateAttendanceLogIds(AttendanceLogRequest attendanceLogRequest) {
        String registerId = attendanceLogRequest.getAttendance().get(0).getRegisterId();
        List<AttendanceLog> attendance = attendanceLogRequest.getAttendance();
        List<String> providedAttendanceLogIds = attendance.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.toList());
        List<AttendanceLog> fetchedAttendanceLogList = fetchAttendanceLogsByIds(providedAttendanceLogIds);
        Set<String> fetchedAttendanceLogIds = fetchedAttendanceLogList.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.toSet());
        for (String providedAttendanceLogId : providedAttendanceLogIds) {
            if (!fetchedAttendanceLogIds.contains(providedAttendanceLogId)) {
                log.error("Provided attendance id ["+providedAttendanceLogId+"] is invalid for register ["+registerId+"]");
                throw new CustomException("ATTENDANCE_LOG", "Provided attendance id ["+providedAttendanceLogId+"] is invalid for register ["+registerId+"]");
            }
        }

        log.info("Attendance Log Ids are validated successfully for register ["+registerId+"]");
    }

    private List<AttendanceLog> fetchAttendanceLogsByIds(List<String> ids) {
        //AttendanceLogSearchCriteria searchCriteria = AttendanceLogSearchCriteria.builder().ids(ids).status(Status.ACTIVE).build();
        AttendanceLogSearchCriteria searchCriteria = AttendanceLogSearchCriteria.builder().ids(ids).build();
        return attendanceLogRepository.getAttendanceLogs(searchCriteria);
    }

    private void validateAttendees(AttendanceLogRequest attendanceLogRequest) {

        /*
            For now, we are validating attendees on below basis.
            1. Verify that each attendee is associated to given register.
            2. Make the entry in attendance log table only if attendee was active during the given attendance time.

            Future:
            Once Individual service will be available will integrate it for further validation.
         */


        if ("TRUE".equalsIgnoreCase(config.getIndividualServiceIntegrationRequired())) {
            //TODO
            // For now throwing exception. Since individual service is under discussion.
            log.error("Individual service integration is under development");
            throw new CustomException("INTEGRATION_UNDERDEVELOPMENT", "Individual service integration is under development");
        }

        // Fetch all attendees for given register_id.
        String registerId = attendanceLogRequest.getAttendance().get(0).getRegisterId();
        List<IndividualEntry> fetchAttendanceAttendeeLst = fetchAllAttendeesEnrolledInARegister(registerId);

        log.info("All attendees are fetched successfully for register ["+registerId+"]");

        // Convert the fetched Attendee List into a Map with individualId as key and corresponding Attendee list as value.
        Map<String, List<IndividualEntry>> attendanceAttendeeListMap = fetchAttendanceAttendeeLst
                .stream()
                .collect(Collectors.groupingBy(IndividualEntry::getIndividualId));

        // Identify unassociated(Attendees not associated with given register) and ineligible attendees
        identifyUnassociatedAndIneligibleAttendees(attendanceLogRequest, attendanceAttendeeListMap);


        log.info("Attendee validation is done for register [" + registerId + "]");
    }

    /**
     * This method is used to check if the same individual is mapped to
     * another register and his/her attendance is logged for the same day
     * or not
     *
     * @param attendanceLogRequest
     */
    private void checkAttendeeIsMappedToAnotherRegisterOrNot(AttendanceLogRequest attendanceLogRequest, Boolean isUpdate) {
        List<AttendanceLog> attendanceLogs = attendanceLogRequest.getAttendance();
        Set<String> uniqueIndividualIds = attendanceLogs.stream()
                .map(AttendanceLog::getIndividualId) // Extract individualId
                .collect(Collectors.toSet());
        List<String> uniqueIndividualIdsList = new ArrayList<>(uniqueIndividualIds);
        List<String> errorMessageList= new ArrayList<>();

        List<Individual> individualDetailList= individualServiceUtil.getIndividualDetails(uniqueIndividualIdsList,
                attendanceLogRequest.getRequestInfo(),attendanceLogs.get(0).getTenantId());

        if(individualDetailList.isEmpty()){
            throw new CustomException("INDIVIDUAL_SEARCH_RESPONSE_IS_EMPTY", "Individuals not found");
        }
        Map<String, Individual> individualDetailMap=individualDetailList.stream()
                .collect(Collectors.toMap(Individual::getId,individual -> individual));

        Map<String, List<AttendanceLog>> attendanceLogMap = attendanceLogs.stream().
                collect(Collectors.groupingBy(AttendanceLog::getIndividualId));

        Map<String, List<String>> requestAttendanceLogsByDay = createAttendanceMap(attendanceLogs);

        for (Map.Entry<String, List<String>> entry : requestAttendanceLogsByDay.entrySet()) {
            String[] keyParts = entry.getKey().split("_");
            String individualId = keyParts[0];
            Map<String, List<String>> fetchedAttendanceLogsByDay = new HashMap<>();
            boolean checkAttendeeMappedToAnotherRegister = false;
            if (!fetchAllTheAttendanceLogsForIndividual(individualId).isEmpty()) {
                checkAttendeeMappedToAnotherRegister = true;
                List<AttendanceLog> fetchAttendeeListMappedWithAnotherRegister = fetchAllTheAttendanceLogsForIndividual(individualId);

                fetchedAttendanceLogsByDay = createAttendanceMap(fetchAttendeeListMappedWithAnotherRegister);
            }


            if (checkAttendeeMappedToAnotherRegister) {
                identifyAttendeeMappedInAnotherRegisterForASameDay(entry, fetchedAttendanceLogsByDay, individualId, isUpdate,individualDetailMap,errorMessageList);
            }

        }
        if(!errorMessageList.isEmpty()){
            String concatenatedErrorMessage = errorMessageList.stream()
                    .collect(Collectors.joining("|| "));
            Map<String, String> errorMap= new HashMap<>();
            errorMap.put("SAME_DAY_ATTENDANCE_ERROR",concatenatedErrorMessage);
            throw new CustomException(errorMap);
        }

    }


    private Map<String, List<String>> createAttendanceMap(List<AttendanceLog> attendanceLogs) {
        // Create a map to store entry and exit times grouped by day
        Map<String, List<String>> attendanceByDay = new HashMap<>();
        List<AttendanceLog> attendanceList=attendanceLogs.stream()
                .filter(x -> x.getType().contains("EXIT"))
                .collect(Collectors.toList());
        for (AttendanceLog record : attendanceList) {
            String individualId = record.getIndividualId();
            long timeMillis = record.getTime().longValue();
            Date time = new Date(timeMillis);

            // Format the date to get the day
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String day = dateFormat.format(time);

            // Group entry and exit times by day
            attendanceByDay.computeIfAbsent(individualId + "_" + day, k -> new ArrayList<>())
                    .add(record.getType() + " at " + new SimpleDateFormat("HH:mm:ss").format(time) + " in register " + record.getRegisterId());
        }
        return attendanceByDay;
    }

    /**
     * @param individualId
     * @return List of all the Attendance Logs  entries for a particular individual
     */
    public List<AttendanceLog> fetchAllTheAttendanceLogsForIndividual(String individualId) {
        AttendanceLogSearchCriteria searchCriteria = AttendanceLogSearchCriteria
                .builder()
                .individualIds(Collections.singletonList(individualId))
                .status(Status.ACTIVE)
                .build();
        return attendanceLogRepository.getAttendanceLogsBasedOnIndividualId(searchCriteria);
    }


    /**
     * This method is used to check the attendance time passed in the attendance logs request of a particular individual
     * with all the attendance time which we get from the oepn search of attendance log on the individual id
     *
     * @param requestedAttendanceLogsByDay
     * @param fetchedLogsByDay
     */
    private void identifyAttendeeMappedInAnotherRegisterForASameDay(Map.Entry<String, List<String>> requestedAttendanceLogsByDay,
                                                                    Map<String, List<String>> fetchedLogsByDay, String individualId,
                                                                    Boolean isUpdate, Map<String, Individual> individualDetailMap, List<String> errorMessageList) {
        log.info("AttendanceLogServiceValidator:: identifyAttendeeMappedInAnotherRegisterForASameDay");
        Map<String, List<String>> mapFromEntry = new HashMap<>();
        mapFromEntry.put(requestedAttendanceLogsByDay.getKey(), requestedAttendanceLogsByDay.getValue());
        Map<String, String> entryAndExitTime = new HashMap<>();


        mapFromEntry.forEach((key, value) -> {
            List<String> value2 = fetchedLogsByDay.get(key);
            String[] keyparts = key.split("_");
            String day = keyparts[1];
            String requestRegisterId;

            log.info("Fetch Entry And Exit Time for requested attendance logs");
            String requestAttendanceRegisterID =  fetchEntryAndExitTimeAndRegisterId(mapFromEntry.get(key), entryAndExitTime);
            if (value2 != null) {
                List<Map<String, String>> listOfAttendanceMap = new ArrayList<>();
                Map<String, String> entryAndExitTimeForFetchedAttedance = new HashMap<>();
                if (!value2.isEmpty()) {
                    String registerId=null;
                    for (String entryInfo : value2) {
                        String exitTime = entryInfo.substring(entryInfo.indexOf("at") + 3);
                        String id=entryInfo.substring(entryInfo.indexOf("register") + "register".length()).trim();
                        if (!requestAttendanceRegisterID.equals(id)) {
                            registerId = id;
                            entryAndExitTimeForFetchedAttedance.put(registerId, exitTime);
                            listOfAttendanceMap.add(entryAndExitTimeForFetchedAttedance);
                        }

                    }
                    validateAttendanceWithExistingOne(entryAndExitTime, listOfAttendanceMap, individualId, day, isUpdate,registerId,requestAttendanceRegisterID,individualDetailMap,errorMessageList);
                } else {
                    log.info("No Existing Attendance Logs found");
                }

            } else {
                log.info("No Existing Attendance Logs found ");
            }

        });
    }

    private void validateAttendanceWithExistingOne(Map<String, String> entryAndExitTime, List<Map<String, String>> listOfAttendanceMap, String individualId,
                                                   String day, Boolean isUpdate,String registerId, String requestAttendanceRegisterID,
                                                   Map<String, Individual> individualDetailMap, List<String> errorMessageList) {
        if (!listOfAttendanceMap.isEmpty()) {
            for (Map<String, String> entryMap : listOfAttendanceMap) {
                if (!isUpdate || !requestAttendanceRegisterID.equals(registerId)) {
                    if (!entryMap.get(registerId).contains("09:00:00")) {
                        if (entryAndExitTime.get(requestAttendanceRegisterID).contains("09:00:00")) {
                            log.info("Logging Attendance for " + "[" + individualId + "] " +
                                    "on this day :" + day + "with this as exit time " + entryAndExitTime.get(requestAttendanceRegisterID));
                        } else {
                            Individual individual= individualDetailMap.get(individualId);
                            log.error("Attedance is already marked for " + "[" + individual.getIndividualId() + " ::" +individual.getName()+ "] " +
                                    "on this day : " + day + " with this as exit time" + entryMap.get(registerId));


                            errorMessageList.add("Attedance is already marked for " + "[" + individual.getIndividualId() + " ::" +individual.getName()+ "] " +
                                    "on this day : " + day + " with this as exit time" + entryMap.get(registerId));
                          /*  throw new CustomException("ATTENDANCE_FOR_SAME_DAY", "Attedance is already marked for " + "[" + individualId + "] " +
                                    "on this day :" + day + "with this as exit time" + entryMap.get(registerId));*/
                        }

                    } else {
                        log.info("Logging Attendance for individual" + "[" + individualId + "] " +
                                "on this day :" + day + "with this as exit time" + entryAndExitTime.get(requestAttendanceRegisterID));
                    }
                } else
                    log.info("Logging Attendance with Update Api call as no existing attendance logs with same register id is found " + "[" + individualId + "] " +
                            "on this day :" + day + "with this as exit time" + entryAndExitTime.get(requestAttendanceRegisterID));

            }


        }
        log.info("Logging Attendance as there is no existing attendance logs for this individual " + "[" + individualId + "] " +
                "on this day :" + day);
    }


    private String fetchEntryAndExitTimeAndRegisterId(List<String> attendanceByDay, Map<String, String> entryAndExitTime) {
        String exitTime = null;
        String requestRegisterId=null;
        if (!attendanceByDay.isEmpty()) {
            for (String entryInfo : attendanceByDay) {
                requestRegisterId = entryInfo.substring(entryInfo.indexOf("register") + "register".length()).trim();
                //entryAndExitTime.put("REGISTER_ID", registerId);
              /*  if (entryInfo.contains("ENTRY")) {
                    entryTime = entryInfo.substring(entryInfo.indexOf("at") + 3);
                    entryAndExitTime.put("ENTRY", entryTime);
                } else*/
                if (entryInfo.contains("EXIT")) {
                    exitTime = entryInfo.substring(entryInfo.indexOf("at") + 3);
                    entryAndExitTime.put(requestRegisterId, exitTime);
                }

            }
        } else {
            log.info("Attendance Map is empty");
        }
        return requestRegisterId;

    }

    private void identifyUnassociatedAndIneligibleAttendees(AttendanceLogRequest attendanceLogRequest, Map<String, List<IndividualEntry>> attendanceAttendeeListMap) {
        List<String> unassociatedAttendees = new ArrayList<>();
        Set<String> eligibleAttendanceAttendeeIdSet = new HashSet<>();

        List<AttendanceLog> attendanceLogs = attendanceLogRequest.getAttendance();
        for (AttendanceLog attendanceLog : attendanceLogs) {
            String givenIndividualId = attendanceLog.getIndividualId();
            if (attendanceAttendeeListMap.containsKey(givenIndividualId)) {
                List<IndividualEntry> lst = attendanceAttendeeListMap.get(givenIndividualId);
                for (IndividualEntry attendee : lst) {
                    Instant instantAttendanceAttendeeLogTime = Instant.ofEpochMilli(attendanceLog.getTime().longValue());
                    Instant instantEnrollmentDate = Instant.ofEpochMilli(attendee.getEnrollmentDate().longValue());
                    if (attendee.getDenrollmentDate()==null) {
                        if (instantAttendanceAttendeeLogTime.compareTo(instantEnrollmentDate) >= 0) {
                            eligibleAttendanceAttendeeIdSet.add(attendee.getIndividualId());
                        }
                    } else {
                        Instant instantDenrollmentDate = Instant.ofEpochMilli(attendee.getDenrollmentDate().longValue());
                        if (instantAttendanceAttendeeLogTime.compareTo(instantEnrollmentDate) >= 0 && instantAttendanceAttendeeLogTime.compareTo(instantDenrollmentDate) <= 0) {
                            eligibleAttendanceAttendeeIdSet.add(attendee.getIndividualId());
                        }
                    }
                }
            } else {
                unassociatedAttendees.add(givenIndividualId);
            }

        }

        String registerId = attendanceLogRequest.getAttendance().get(0).getRegisterId();

        if (!unassociatedAttendees.isEmpty()) {
            log.error("Attendees are not enrolled against register ["+registerId+"]");
            throw new CustomException("UNENROLLED_ATTENDEES", "Attendees are not enrolled against register ["+registerId+"]");
        }

        //find ineligible list
        Set<String> inEligibleAttendanceAttendeeIdSet = new HashSet<>();
        for (AttendanceLog attendanceLog : attendanceLogs) {
            if (!eligibleAttendanceAttendeeIdSet.contains(attendanceLog.getIndividualId())) {
                inEligibleAttendanceAttendeeIdSet.add(attendanceLog.getIndividualId());
            }
        }

        if (!inEligibleAttendanceAttendeeIdSet.isEmpty()) {
            log.error("Attendees are ineligible for given date range for register ["+registerId+"]");
            throw new CustomException("INELIGIBLE_ATTENDEES", "Attendees are ineligible for given date range for register ["+registerId+"]");
        }
    }

    private List<IndividualEntry> fetchAllAttendeesEnrolledInARegister(String registerId) {
        AttendeeSearchCriteria searchCriteria = AttendeeSearchCriteria
                .builder()
                .registerIds(Collections.singletonList(registerId))
                .build();

        return attendanceAttendeeRepository.getAttendees(searchCriteria);
    }

    private void validateLoggedInUser(AttendanceLogRequest attendanceLogRequest) {
        /*
            For now, we are validating logged-in user on below basis.
            1. Logged-in user should be active user for provided register_id. Query eg_wms_attendance_staff table for same.

            Future
            Once Staff service will be available will integrate it for further validation.
         */

        if ("TRUE".equalsIgnoreCase(config.getStaffServiceIntegrationRequired())) {
            //TODO
            // For now throwing exception. Since Staff service is under development.
            log.error("Staff service integration is under development");
            throw new CustomException("INTEGRATION_UNDERDEVELOPMENT", "Staff service integration is under development");
        }

        String userUUID = attendanceLogRequest.getRequestInfo().getUserInfo().getUuid();
        String registerId = attendanceLogRequest.getAttendance().get(0).getRegisterId();
        String individualId = individualServiceUtil.getIndividualDetailsFromUserId(attendanceLogRequest.getRequestInfo().getUserInfo().getId(), attendanceLogRequest.getRequestInfo(), attendanceLogRequest.getAttendance().get(0).getTenantId()).get(0).getId();
        validateLoggedInUser(individualId, registerId);
        log.info("User ["+userUUID+"] validation is done for register ["+registerId+"]");
    }

    public void validateSearchAttendanceLogRequest(RequestInfoWrapper requestInfoWrapper, AttendanceLogSearchCriteria searchCriteria) {

        log.info("Validate attendance log search request");

        // Verify given parameters
        validateSearchAttendanceLogParameters(requestInfoWrapper, searchCriteria);

        // Fetch register for given Id
        List<AttendanceRegister> attendanceRegisters = fetchRegisterWithId(searchCriteria.getRegisterId());

        if (attendanceRegisters == null || attendanceRegisters.isEmpty()) {
            throw new CustomException("INVALID_REGISTERID", "Register Not found ");
        }

        // Verify TenantId association with register
        validateTenantIdAssociationWithRegisterId(attendanceRegisters.get(0), searchCriteria.getTenantId());

        // Verify the Logged-in user is associated to the given register.
        String individualId = individualServiceUtil.getIndividualDetailsFromUserId(requestInfoWrapper.getRequestInfo().getUserInfo().getId(), requestInfoWrapper.getRequestInfo(), searchCriteria.getTenantId()).get(0).getId();
        validateLoggedInUser(individualId, searchCriteria.getRegisterId());

        log.info("Attendance log search request validated successfully");
    }

    private void validateSearchAttendanceLogParameters(RequestInfoWrapper requestInfoWrapper, AttendanceLogSearchCriteria searchCriteria) {
        if (searchCriteria == null || requestInfoWrapper == null) {
            log.error("Attendance log search criteria and request info is mandatory");
            throw new CustomException("ATTENDANCE_LOG_SEARCH_REQUEST", "Attendance log search criteria and request info is mandatory");
        }

        Map<String, String> errorMap = new HashMap<>();

        validateRequestInfo(requestInfoWrapper.getRequestInfo(), errorMap);

        if (StringUtils.isBlank(searchCriteria.getTenantId())) {
            log.error("Attendance log search, Tenant is mandatory");
            throw new CustomException("TENANT_ID", "Tenant is mandatory");
        }
        if (StringUtils.isBlank(searchCriteria.getRegisterId())) {
            log.error("Attendance log search, RegisterId is mandatory");
            throw new CustomException("REGISTER_ID", "RegisterId is mandatory");
        }

        // Throw exception if required parameters are missing
        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);

        if (searchCriteria.getIndividualIds() != null && !searchCriteria.getIndividualIds().isEmpty() && searchCriteria.getIndividualIds().size() > 10) {
            log.error("Attendance log search, only 10 IndividualIds are allowed to search");
            throw new CustomException("INDIVIDUALIDS", "only 10 IndividualIds are allowed to search");
        }
    }



    private void validateLoggedInUser(String userUUID, String registerId) {
        StaffSearchCriteria searchCriteria = StaffSearchCriteria
                .builder()
                .individualIds(Collections.singletonList(userUUID))
                .registerIds(Collections.singletonList(registerId))
                .build();
        List<StaffPermission> attendanceStaff = attendanceStaffRepository.getActiveStaff(searchCriteria);
        if (attendanceStaff == null || attendanceStaff.isEmpty()) {
            log.error("User ["+userUUID+"] is not authorised for register ["+registerId+"]");
            throw new CustomException("UNAUTHORISED_USER", "User ["+userUUID+"] is not authorised for register ["+registerId+"]");
        }
    }

    private void validateRequestInfo(RequestInfo requestInfo, Map<String, String> errorMap) {
        if (requestInfo == null) {
            log.error("Request info is mandatory");
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            log.error("UserInfo is mandatory");
            throw new CustomException("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            log.error("UUID is mandatory");
            throw new CustomException("USERINFO_UUID", "UUID is mandatory");
        }

        log.info("Request Info object validation done");
    }

    private void validateAttendanceLogRequest(AttendanceLogRequest attendanceLogRequest) {

        Map<String, String> errorMap = new HashMap<>();
        // Validate the Request Info object
        RequestInfo requestInfo = attendanceLogRequest.getRequestInfo();
        validateRequestInfo(requestInfo, errorMap);

        // Validate the Attendance Log parameters
        validateAttendanceLogParameters(attendanceLogRequest.getAttendance(), errorMap);

        // Throw exception if required parameters are missing
        if (!errorMap.isEmpty()){
            log.error("Attendance log request validation failed");
            throw new CustomException(errorMap);
        }
    }

    private void validateAttendanceLogParameters(List<AttendanceLog> attendance, Map<String, String> errorMap) {
        if (attendance == null || attendance.isEmpty()) {
            log.error("Attendance array is mandatory");
            throw new CustomException("ATTENDANCE", "Attendance array is mandatory");
        }

        for (AttendanceLog attendeeLog : attendance) {
            if (StringUtils.isBlank(attendeeLog.getTenantId())) {
                log.error("TenantId is mandatory");
                errorMap.put("ATTENDANCE.TENANTID", "TenantId is mandatory");
            }
            if (StringUtils.isBlank(attendeeLog.getRegisterId())) {
                log.error("Attendance registerid is mandatory");
                errorMap.put("ATTENDANCE.REGISTERID", "Attendance registerid is mandatory");
            }
            if (StringUtils.isBlank(attendeeLog.getIndividualId() )) {
                log.error("Attendance indidualid is mandatory");
                errorMap.put("ATTENDANCE.INDIVIDUALID", "Attendance indidualid is mandatory");
            }
            if (StringUtils.isBlank(attendeeLog.getType())) {
                log.error("Attendance type is mandatory");
                errorMap.put("ATTENDANCE.TYPE", "Attendance type is mandatory");
            }
            if (attendeeLog.getTime() == null) {
                log.error("Attendance time is mandatory");
                errorMap.put("ATTENDANCE.TIME", "Attendance time is mandatory");
            }
        }
    }
}
