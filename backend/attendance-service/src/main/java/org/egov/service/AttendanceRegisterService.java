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

        //Get the logged-in user roles code
        List<String> userRoleCodes = getUserRoleCodes(requestInfoWrapper.getRequestInfo());

        List<AttendanceRegister> resultAttendanceRegisters = new ArrayList<>();

        if (userRoleCodes.contains(AttendanceServiceConstants.JUNIOR_ENGINEER_ROLE_CODE) || userRoleCodes.contains(AttendanceServiceConstants.MUNICIPAL_ENGINEER_ROLE_CODE)) {
            /*
                If logged-in user is Junior Engineer or Municipal Engineer then do the search based on the supplied criteria.
                limit the response register count based on the configuration.
             */

            fetchAndFilterRegisters(searchCriteria,resultAttendanceRegisters);
        } else if (userRoleCodes.contains(AttendanceServiceConstants.ORG_ADMIN_ROLE_CODE) || userRoleCodes.contains(AttendanceServiceConstants.ORG_STAFF_ROLE_CODE)) {
            /*
                If the logged-in user is Org Admin or Org Staff then do the search based on the supplied criteria.
                But make sure response register list should contain only those register for which logged-in is associated.
            */
            String uuid = requestInfoWrapper.getRequestInfo().getUserInfo().getUuid();
            Set<String> registers = fetchRegistersAssociatedToLoggedInStaffUser(uuid);
            updateSearchCriteriaAndFetchAndFilterRegisters(registers,searchCriteria,resultAttendanceRegisters);

        } else {
            /*
                If the logged-in user is Attendee then do the search based on the supplied criteria.
                But make sure response register list should contain only those register for which logged-in is associated.
            */
            String uuid = requestInfoWrapper.getRequestInfo().getUserInfo().getUuid();
            Set<String> registers = fetchRegistersAssociatedToLoggedInAttendeeUser(uuid);
            updateSearchCriteriaAndFetchAndFilterRegisters(registers,searchCriteria,resultAttendanceRegisters);
        }

        updateRegisterWithAssociatedStaff(resultAttendanceRegisters);

        updateRegisterWithAssociatedAttendees(resultAttendanceRegisters);


        return resultAttendanceRegisters;
    }

    private void updateRegisterWithAssociatedAttendees(List<AttendanceRegister> resultAttendanceRegisters) {
        // Fetch all attendee associated with provided register
        List<String> registerIds = resultAttendanceRegisters.stream().map(e -> e.getId()).collect(Collectors.toList());
        AttendeeSearchCriteria searchCriteria = AttendeeSearchCriteria.builder().registerIds(registerIds).build();
        List<IndividualEntry> attendees = attendeeRepository.getAttendees(searchCriteria);
        Map<String, List<IndividualEntry>> registerIdAttendeeMapping = attendees.stream().collect(Collectors.groupingBy(IndividualEntry::getRegisterId));

        // Update register with associated staff
        for(AttendanceRegister register : resultAttendanceRegisters){
            List<IndividualEntry> staffPermissions = registerIdAttendeeMapping.get(register.getId());
            register.setAttendees(staffPermissions);
        }
    }

    private void updateRegisterWithAssociatedStaff(List<AttendanceRegister> resultAttendanceRegisters) {
        // Fetch all staff associated with provided register
        List<String> registerIds = resultAttendanceRegisters.stream().map(e -> e.getId()).collect(Collectors.toList());
        StaffSearchCriteria staffSearchCriteria = StaffSearchCriteria.builder().registerIds(registerIds).build();
        List<StaffPermission> staffMembers = staffService.getAllStaff(staffSearchCriteria);
        Map<String, List<StaffPermission>> registerIdStaffMapping = staffMembers.stream().collect(Collectors.groupingBy(StaffPermission::getRegisterId));

        // Update register with associated attendees
        for(AttendanceRegister register : resultAttendanceRegisters){
            List<StaffPermission> staffPermissions = registerIdStaffMapping.get(register.getId());
            register.setStaff(staffPermissions);
        }
    }

    /**
     * Update the search criteria with list of registerIds if it does not contain registerId
     * then fetch the all registers based on the search criteria
     * but keep only those registers at last which contains attendees or staff in given search criteria
     * @param registers
     * @param searchCriteria
     * @param resultAttendanceRegisters
     */
    public void updateSearchCriteriaAndFetchAndFilterRegisters(Set<String> registers,AttendanceRegisterSearchCriteria searchCriteria,List<AttendanceRegister> resultAttendanceRegisters){
      if(searchCriteria.getIds() == null){
           List<String> registerIds = new ArrayList<>();
           registerIds.addAll(registers);
           searchCriteria.setIds(registerIds);
      } else {
          for(String id: searchCriteria.getIds()){
              if(!registers.contains(id)){
                  throw new CustomException("INVALID_REGISTER_ID", "User can search only associated registers");
              }
          }
      }
        fetchAndFilterRegisters(searchCriteria,resultAttendanceRegisters);
    }

    /**
     *
     * Fetch the all registers based on the supplied search criteria
     * but keep only those registers which contains attendees or staff given in search criteria
     * @param searchCriteria
     * @param resultAttendanceRegisters
     */
    private void fetchAndFilterRegisters(AttendanceRegisterSearchCriteria searchCriteria, List<AttendanceRegister> resultAttendanceRegisters){
        List<AttendanceRegister> attendanceRegisters = registerRepository.getRegister(searchCriteria);

        // If search criteria contains AttdendeeId and StaffId
        if(searchCriteria.getAttendeeId() != null && searchCriteria.getStaffId() != null){
            for(AttendanceRegister register : attendanceRegisters){
                if(register.getAttendees().contains(searchCriteria.getAttendeeId()) && register.getStaff().contains(searchCriteria.getStaffId())){
                    resultAttendanceRegisters.add(register);
                }
            }

        }
        // If search criteria contains only AttdendeeId
        else if(searchCriteria.getAttendeeId() != null){
            for(AttendanceRegister register : attendanceRegisters){
                if(register.getAttendees().contains(searchCriteria.getAttendeeId())){
                    resultAttendanceRegisters.add(register);
                }
            }

        }
        // If search criteria contains only StaffId
        else if (searchCriteria.getStaffId() != null) {
            for (AttendanceRegister register : attendanceRegisters) {
                if (register.getStaff().contains(searchCriteria.getStaffId())) {
                    resultAttendanceRegisters.add(register);
                }
            }

        } else {
            // Since search criteria does not contain attendeeId or staffId
            resultAttendanceRegisters.addAll(attendanceRegisters);
        }
    }

    private List<String> getUserRoleCodes(RequestInfo requestInfo) {
        List<String> userRoleCodes = new ArrayList<>();
        List<Role> roles = requestInfo.getUserInfo().getRoles();
        for (Role role : roles) {
            userRoleCodes.add(role.getCode());
        }
        return userRoleCodes;
    }

    private Set<String> fetchRegistersAssociatedToLoggedInStaffUser(String uuid) {
        List<String> individualIds = new ArrayList<>();
        individualIds.add(uuid);
        StaffSearchCriteria staffSearchCriteria = StaffSearchCriteria.builder().individualIds(individualIds).build();
        List<StaffPermission> staffMembers = staffService.getAllStaff(staffSearchCriteria);
        return staffMembers.stream().map(e->e.getRegisterId()).collect(Collectors.toSet());
    }

    private Set<String> fetchRegistersAssociatedToLoggedInAttendeeUser(String uuid) {
        AttendeeSearchCriteria attendeeSearchCriteria = AttendeeSearchCriteria.builder().individualId(uuid).build();
        List<IndividualEntry> attendees = attendeeRepository.getAttendees(attendeeSearchCriteria);
        return attendees.stream().map(e->e.getRegisterId()).collect(Collectors.toSet());
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
