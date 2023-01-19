package org.egov.service;

import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.enrichment.RegisterEnrichment;
import org.egov.kafka.Producer;
import org.egov.repository.AttendeeRepository;
import org.egov.repository.RegisterRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.AttendanceServiceConstants;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.AttendanceServiceValidator;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AttendanceRegisterService {
    @Autowired
    private AttendanceServiceValidator attendanceServiceValidator;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    private Producer producer;

    @Autowired
    private AttendanceServiceConfiguration attendanceServiceConfiguration;

    @Autowired
    private RegisterEnrichment registerEnrichment;


    @Autowired
    private StaffService staffService;

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private AttendeeRepository attendeeRepository;

    /**
     * Create Attendance register
     *
     * @param request
     * @return
     */
    public AttendanceRegisterRequest createAttendanceRegister(AttendanceRegisterRequest request) {
        attendanceServiceValidator.validateCreateAttendanceRegister(request);
        registerEnrichment.enrichCreateAttendanceRegister(request);
        log.info("Enriched Register with Register number, Ids and audit details");
        producer.push(attendanceServiceConfiguration.getSaveAttendanceRegisterTopic(), request);
        log.info("Pushed create attendance register request to kafka");

        // User who creates the register, by default gets enrolled as the first staff for that register.
        StaffPermissionRequest staffPermissionResponse = staffService.createFirstStaff(request.getRequestInfo(), request.getAttendanceRegister());
        log.info("The user " + request.getRequestInfo().getUserInfo().getUuid() + " is enrolled as staff for the created attendance registers");
        //Set created staff details in attendance register
        registerEnrichment.enrichStaffInRegister(request.getAttendanceRegister(), staffPermissionResponse);
        return request;
    }

    /**
     * Search attendance register based on given search criteria
     *
     * @param requestInfoWrapper
     * @param searchCriteria
     * @return
     */
    public List<AttendanceRegister> searchAttendanceRegister(RequestInfoWrapper requestInfoWrapper, AttendanceRegisterSearchCriteria searchCriteria) {
        log.info("Search for registers started");
        //Validate the requested parameters
        attendanceServiceValidator.validateSearchRegisterRequest(requestInfoWrapper, searchCriteria);
        log.info("Search registers request validations done");

        //Enrich requested search criteria
        registerEnrichment.enrichSearchRegisterRequest(requestInfoWrapper.getRequestInfo(),searchCriteria);
        log.info("Search registers enrichment done");

        //Get the logged-in user roles
        List<String> userRoles = getUserRoleCodes(requestInfoWrapper.getRequestInfo());

        List<AttendanceRegister> resultAttendanceRegisters = new ArrayList<>();

        if (userRoles.contains(AttendanceServiceConstants.SUPERUSER_ROLE_CODE) ||
                userRoles.contains(AttendanceServiceConstants.JUNIOR_ENGINEER_ROLE_CODE) ||
                userRoles.contains(AttendanceServiceConstants.MUNICIPAL_ENGINEER_ROLE_CODE)) {
            /*
                If logged-in user is Super User or Junior Engineer or Municipal Engineer then do the search based on the supplied criteria.
                Limit the response register count based on the configuration.
             */
            log.info("Searching registers for Superuser or Engineer");
            fetchAndFilterRegisters(searchCriteria, resultAttendanceRegisters);
        } else if (userRoles.contains(AttendanceServiceConstants.ORG_ADMIN_ROLE_CODE) ||
                    userRoles.contains(AttendanceServiceConstants.ORG_STAFF_ROLE_CODE)) {
            /*
                If the logged-in user is Org Admin or Org Staff then do the search based on the supplied criteria.
                But make sure response register list should contain only those register for which logged-in is associated.
            */
            log.info("Searching registers for Org Admin or Org Staff");
            String uuid = requestInfoWrapper.getRequestInfo().getUserInfo().getUuid();
            Set<String> registers = fetchRegistersAssociatedToLoggedInStaffUser(uuid);
            updateSearchCriteriaAndFetchAndFilterRegisters(registers, searchCriteria, resultAttendanceRegisters);

        } else if (userRoles.contains(AttendanceServiceConstants.CITIZEN_ROLE_CODE)) {
            /*
                If the logged-in user is Attendee then do the search based on the supplied criteria.
                But make sure response register list should contain only those register for which logged-in is associated.
            */
            log.info("Searching registers for Attendee");
            String uuid = requestInfoWrapper.getRequestInfo().getUserInfo().getUuid();
            Set<String> registers = fetchRegistersAssociatedToLoggedInAttendeeUser(uuid);
            updateSearchCriteriaAndFetchAndFilterRegisters(registers, searchCriteria, resultAttendanceRegisters);
        } else {
            log.error("User is not authorized");
            throw new CustomException("UNAUTHORIZED_USER", "User is not authorized");
        }
        log.info("Search registers completed");
        return resultAttendanceRegisters;
    }

    /**
     * Update the search criteria with list of registerIds if it does not contain registerId
     * then fetch the all registers based on the search criteria
     * but keep only those registers at last which contains attendees or staff in given search criteria
     *
     * @param registers
     * @param searchCriteria
     * @param resultAttendanceRegisters
     */
    public void updateSearchCriteriaAndFetchAndFilterRegisters(Set<String> registers, AttendanceRegisterSearchCriteria searchCriteria, List<AttendanceRegister> resultAttendanceRegisters) {

        if (registers == null || registers.isEmpty()) {
            log.info("Registers are empty or null");
            return;
        }
        if (searchCriteria.getIds() == null) {
            log.info("Register search criteria does not contain any register ids");
            List<String> registerIds = new ArrayList<>();
            registerIds.addAll(registers);
            searchCriteria.setIds(registerIds);
        } else {
            log.info("Register search criteria does contains register ids");
            for (String id : searchCriteria.getIds()) {
                if (!registers.contains(id)) {
                    log.error( "User can search only associated registers");
                    throw new CustomException("INVALID_REGISTER_ID", "User can search only associated registers");
                }
            }
        }
        fetchAndFilterRegisters(searchCriteria, resultAttendanceRegisters);
    }

    /**
     * Fetch the all registers based on the supplied search criteria
     * but keep only those registers which contains attendees or staff given in search criteria
     *
     * @param searchCriteria
     * @param resultAttendanceRegisters
     */
    private void fetchAndFilterRegisters(AttendanceRegisterSearchCriteria searchCriteria, List<AttendanceRegister> resultAttendanceRegisters) {
        log.info("Fetching registers based on supplied search criteria");
        // Fetch the all registers based on the supplied search criteria
        List<AttendanceRegister> attendanceRegisters = registerRepository.getRegister(searchCriteria);
        // Create a map with key as registerId and corresponding register list as value
        Map<String, List<AttendanceRegister>> registerIdVsAttendanceRegisters = attendanceRegisters.stream().collect(Collectors.groupingBy(AttendanceRegister::getId));

        List<String> registerIdsToSearch = new ArrayList<>();
        registerIdsToSearch.addAll(registerIdVsAttendanceRegisters.keySet());

        // Fetch and filer staff members based on the supplied search criteria.
        log.info("Fetch all staff members based on the supplied search criteria");
        List<StaffPermission> staffMembers = fetchAllStaffMembersAssociatedToRegisterIds(registerIdsToSearch,searchCriteria);
        // Create a map with key as registerId and corresponding staff list as value
        Map<String, List<StaffPermission>> registerIdStaffMapping = staffMembers.stream().collect(Collectors.groupingBy(StaffPermission::getRegisterId));

        // If staffId present in search criteria then update the registerIDToSearch list with new set of registerIds
        if (searchCriteria.getStaffId() != null){
            registerIdsToSearch.clear();
            registerIdsToSearch.addAll(registerIdStaffMapping.keySet());
        }

        // Fetch and filer attendees based on the supplied search criteria.
        List<IndividualEntry> attendees = fetchAllAttendeesAssociatedToRegisterIds(registerIdsToSearch,searchCriteria);
        // Create a map with key as registerId and corresponding attendee list as value
        Map<String, List<IndividualEntry>> registerIdAttendeeMapping = attendees.stream().collect(Collectors.groupingBy(IndividualEntry::getRegisterId));

        // If AttendeeId present in search criteria then update the registerIDToSearch list with new set of registerIds
        if(searchCriteria.getAttendeeId() != null){
            List<String> registerIdsAssociatedToAttendees = new ArrayList<>();
            registerIdsAssociatedToAttendees.addAll(registerIdAttendeeMapping.keySet());
            registerIdsToSearch.clear();
            registerIdsToSearch.addAll(registerIdsAssociatedToAttendees);
        }

        // Populate final list of registers to be return
        for(String registerId : registerIdsToSearch ){
            List<AttendanceRegister> registers = registerIdVsAttendanceRegisters.get(registerId);
            for(AttendanceRegister register : registers){
                register.setStaff(registerIdStaffMapping.get(registerId));
                register.setAttendees(registerIdAttendeeMapping.get(registerId));
                resultAttendanceRegisters.add(register);
            }
        }
    }

    private List<IndividualEntry> fetchAllAttendeesAssociatedToRegisterIds(List<String> registerIdsToSearch, AttendanceRegisterSearchCriteria searchCriteria) {
        AttendeeSearchCriteria attendeeSearchCriteria = null;
        if(searchCriteria.getAttendeeId() != null){
            attendeeSearchCriteria = AttendeeSearchCriteria.builder().registerIds(registerIdsToSearch).individualIds(Collections.singletonList(searchCriteria.getAttendeeId())).build();
        } else {
            attendeeSearchCriteria = AttendeeSearchCriteria.builder().registerIds(registerIdsToSearch).build();
        }
        return attendeeRepository.getAttendees(attendeeSearchCriteria);
    }

    /* Get all staff members associated for the register */
    private List<StaffPermission> fetchAllStaffMembersAssociatedToRegisterIds(List<String> registerIdsToSearch, AttendanceRegisterSearchCriteria searchCriteria) {
        StaffSearchCriteria staffSearchCriteria = null ;
        if(searchCriteria.getStaffId() != null){
            staffSearchCriteria = StaffSearchCriteria.builder().registerIds(registerIdsToSearch).individualIds(Collections.singletonList(searchCriteria.getStaffId())).build();
        } else {
            staffSearchCriteria = StaffSearchCriteria.builder().registerIds(registerIdsToSearch).build();
        }
        return staffService.getAllStaff(staffSearchCriteria);
    }

    /* Returns list of user roles */
    private List<String> getUserRoleCodes(RequestInfo requestInfo) {
        List<Role> roles = requestInfo.getUserInfo().getRoles();
        return roles.stream().map(e->e.getCode()).collect(Collectors.toList());
    }

    /* Get all registers associated for the logged in staff  */
    private Set<String> fetchRegistersAssociatedToLoggedInStaffUser(String uuid) {
        List<String> individualIds = new ArrayList<>();
        individualIds.add(uuid);
        StaffSearchCriteria staffSearchCriteria = StaffSearchCriteria.builder().individualIds(individualIds).build();
        List<StaffPermission> staffMembers = staffService.getAllStaff(staffSearchCriteria);
        return staffMembers.stream().map(e -> e.getRegisterId()).collect(Collectors.toSet());
    }

    /* Get all registers associated for the logged in attendee  */
    private Set<String> fetchRegistersAssociatedToLoggedInAttendeeUser(String uuid) {
        AttendeeSearchCriteria attendeeSearchCriteria = AttendeeSearchCriteria.builder().individualIds(Collections.singletonList(uuid)).build();
        List<IndividualEntry> attendees = attendeeRepository.getAttendees(attendeeSearchCriteria);
        return attendees.stream().map(e -> e.getRegisterId()).collect(Collectors.toSet());
    }

    /**
     * Update the given attendance register details
     *
     * @param attendanceRegisterRequest
     * @return
     */
    public AttendanceRegisterRequest updateAttendanceRegister(AttendanceRegisterRequest attendanceRegisterRequest) {
        attendanceServiceValidator.validateUpdateAttendanceRegisterRequest(attendanceRegisterRequest);
        log.info("Validated update attendance register request");

        //Create requestInfoWrapper from attendanceRegister request, collect ids in list for search attendance register request parameters
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(attendanceRegisterRequest.getRequestInfo()).build();
        List<String> registerIds = getAttendanceRegisterIdList(attendanceRegisterRequest);
        String tenantId = attendanceRegisterRequest.getAttendanceRegister().get(0).getTenantId();
        //Get Attendance registers from DB based on register ids, tenantId and requestInfo
        List<AttendanceRegister> attendanceRegistersFromDB = getAttendanceRegisters(requestInfoWrapper, registerIds, tenantId);
        log.info("Fetched attendance registers for update request");

        //Validate Update attendance register request against attendance registers fetched from database
        attendanceServiceValidator.validateUpdateAgainstDB(attendanceRegisterRequest, attendanceRegistersFromDB);

        registerEnrichment.enrichUpdateAttendanceRegister(attendanceRegisterRequest, attendanceRegistersFromDB);
        log.info("Enriched with register Number, Ids and AuditDetails");
        producer.push(attendanceServiceConfiguration.getUpdateAttendanceRegisterTopic(), attendanceRegisterRequest);
        log.info("Pushed update attendance register request to kafka");

        return attendanceRegisterRequest;
    }

    /* Get Attendance registers from DB based on register ids and tenant Id */
    public List<AttendanceRegister> getAttendanceRegisters(RequestInfoWrapper requestInfoWrapper, List<String> registerIds, String tenantId) {

        //Search criteria for attendance register search request
        AttendanceRegisterSearchCriteria searchCriteria = AttendanceRegisterSearchCriteria.builder().ids(registerIds)
                .tenantId(tenantId).build();
        List<AttendanceRegister> attendanceRegisterList;

        // Calls search attendance register with created request. If some error in searching attendance register, throws error
        try {
            attendanceRegisterList = searchAttendanceRegister(requestInfoWrapper, searchCriteria);
            log.info("Attendance register search successful");
        } catch (Exception e) {
            log.info("Error in searching attendance register", e);
            throw new CustomException("SEARCH_ATTENDANCE_REGISTER", "Error in searching attendance register");
        }

        return attendanceRegisterList;
    }

    /* Get attendance registers Id list from attendance register request */
    private List<String> getAttendanceRegisterIdList(AttendanceRegisterRequest request) {
        List<AttendanceRegister> attendanceRegisters = request.getAttendanceRegister();

        List<String> registerIds = new ArrayList<>();
        for (AttendanceRegister attendanceRegister : attendanceRegisters) {
            registerIds.add(String.valueOf(attendanceRegister.getId()));
        }
        return registerIds;
    }

}
