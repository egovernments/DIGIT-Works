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
        producer.push(attendanceServiceConfiguration.getSaveAttendanceRegisterTopic(), request);
        StaffPermissionRequest staffPermissionResponse = staffService.createFirstStaff(request.getRequestInfo(), request.getAttendanceRegister());
        registerEnrichment.enrichStaffInRegister(request.getAttendanceRegister(), staffPermissionResponse);
        return request;
    }

    /**
     * Search attendace register based on given search criteria
     *
     * @param requestInfoWrapper
     * @param searchCriteria
     * @return
     */
    public List<AttendanceRegister> searchAttendanceRegister(RequestInfoWrapper requestInfoWrapper, AttendanceRegisterSearchCriteria searchCriteria) {
        //Validate the requested parameters
        attendanceServiceValidator.validateSearchRegisterRequest(requestInfoWrapper, searchCriteria);

        //Enrich requested search criteria
        registerEnrichment.enrichSearchRegisterRequest(requestInfoWrapper.getRequestInfo(),searchCriteria);

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

            fetchAndFilterRegisters(searchCriteria, resultAttendanceRegisters);
        } else if (userRoles.contains(AttendanceServiceConstants.ORG_ADMIN_ROLE_CODE) ||
                    userRoles.contains(AttendanceServiceConstants.ORG_STAFF_ROLE_CODE)) {
            /*
                If the logged-in user is Org Admin or Org Staff then do the search based on the supplied criteria.
                But make sure response register list should contain only those register for which logged-in is associated.
            */
            String uuid = requestInfoWrapper.getRequestInfo().getUserInfo().getUuid();
            Set<String> registers = fetchRegistersAssociatedToLoggedInStaffUser(uuid);
            updateSearchCriteriaAndFetchAndFilterRegisters(registers, searchCriteria, resultAttendanceRegisters);

        } else if (userRoles.contains(AttendanceServiceConstants.CITIZEN_ROLE_CODE)) {
            /*
                If the logged-in user is Attendee then do the search based on the supplied criteria.
                But make sure response register list should contain only those register for which logged-in is associated.
            */
            String uuid = requestInfoWrapper.getRequestInfo().getUserInfo().getUuid();
            Set<String> registers = fetchRegistersAssociatedToLoggedInAttendeeUser(uuid);
            updateSearchCriteriaAndFetchAndFilterRegisters(registers, searchCriteria, resultAttendanceRegisters);
        } else {
            throw new CustomException("UNAUTHORIZED_USER", "User is not authorized");
        }
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

        if (registers == null || registers.isEmpty())
            return;
        if (searchCriteria.getIds() == null) {
            List<String> registerIds = new ArrayList<>();
            registerIds.addAll(registers);
            searchCriteria.setIds(registerIds);
        } else {
            for (String id : searchCriteria.getIds()) {
                if (!registers.contains(id)) {
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
        // Fetch the all registers based on the supplied search criteria
        List<AttendanceRegister> attendanceRegisters = registerRepository.getRegister(searchCriteria);
        // Create a map with key as registerId and corresponding register list as value
        Map<String, List<AttendanceRegister>> registerIdVsAttendanceRegisters = attendanceRegisters.stream().collect(Collectors.groupingBy(AttendanceRegister::getId));

        List<String> registerIdsToSearch = new ArrayList<>();
        registerIdsToSearch.addAll(registerIdVsAttendanceRegisters.keySet());

        // Fetch and filer staff members based on the supplied search criteria.
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

    private List<StaffPermission> fetchAllStaffMembersAssociatedToRegisterIds(List<String> registerIdsToSearch, AttendanceRegisterSearchCriteria searchCriteria) {
        StaffSearchCriteria staffSearchCriteria = null ;
        if(searchCriteria.getStaffId() != null){
            staffSearchCriteria = StaffSearchCriteria.builder().registerIds(registerIdsToSearch).individualIds(Collections.singletonList(searchCriteria.getStaffId())).build();
        } else {
            staffSearchCriteria = StaffSearchCriteria.builder().registerIds(registerIdsToSearch).build();
        }
        return staffService.getAllStaff(staffSearchCriteria);
    }

    private List<String> getUserRoleCodes(RequestInfo requestInfo) {
        List<Role> roles = requestInfo.getUserInfo().getRoles();
        return roles.stream().map(e->e.getCode()).collect(Collectors.toList());
    }

    private Set<String> fetchRegistersAssociatedToLoggedInStaffUser(String uuid) {
        List<String> individualIds = new ArrayList<>();
        individualIds.add(uuid);
        StaffSearchCriteria staffSearchCriteria = StaffSearchCriteria.builder().individualIds(individualIds).build();
        List<StaffPermission> staffMembers = staffService.getAllStaff(staffSearchCriteria);
        return staffMembers.stream().map(e -> e.getRegisterId()).collect(Collectors.toSet());
    }

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
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(attendanceRegisterRequest.getRequestInfo()).build();
        List<String> registerIds = getAttendanceRegisterIdList(attendanceRegisterRequest);
        String tenantId = attendanceRegisterRequest.getAttendanceRegister().get(0).getTenantId();
        List<AttendanceRegister> attendanceRegistersFromDB = getAttendanceRegisters(requestInfoWrapper, registerIds, tenantId);
        attendanceServiceValidator.validateUpdateAgainstDB(attendanceRegisterRequest, attendanceRegistersFromDB);
        registerEnrichment.enrichUpdateAttendanceRegister(attendanceRegisterRequest, attendanceRegistersFromDB);
        producer.push(attendanceServiceConfiguration.getUpdateAttendanceRegisterTopic(), attendanceRegisterRequest);

        return attendanceRegisterRequest;
    }

    public List<AttendanceRegister> getAttendanceRegisters(RequestInfoWrapper requestInfoWrapper, List<String> registerIds, String tenantId) {
        AttendanceRegisterSearchCriteria searchCriteria = AttendanceRegisterSearchCriteria.builder().ids(registerIds)
                .tenantId(tenantId).build();
        List<AttendanceRegister> attendanceRegisterList;
        try {
            attendanceRegisterList = searchAttendanceRegister(requestInfoWrapper, searchCriteria);
        } catch (Exception e) {
            throw new CustomException("SEARCH_ATTENDANCE_REGISTER", "Error in searching attendance register");
        }

        return attendanceRegisterList;
    }

    private List<String> getAttendanceRegisterIdList(AttendanceRegisterRequest request) {
        List<AttendanceRegister> attendanceRegisters = request.getAttendanceRegister();

        List<String> registerIds = new ArrayList<>();
        for (AttendanceRegister attendanceRegister : attendanceRegisters) {
            registerIds.add(String.valueOf(attendanceRegister.getId()));
        }
        return registerIds;
    }

}
