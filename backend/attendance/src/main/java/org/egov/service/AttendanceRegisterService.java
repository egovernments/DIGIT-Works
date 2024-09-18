package org.egov.service;

import ch.qos.logback.core.BasicStatusManager;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.common.models.project.Project;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.enrichment.RegisterEnrichment;
import org.egov.enrichment.StaffEnrichmentService;
import org.egov.common.producer.Producer;
import org.egov.repository.AttendeeRepository;
import org.egov.repository.RegisterRepository;
import org.egov.repository.StaffRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.IndividualServiceUtil;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.AttendanceServiceValidator;
import org.egov.web.models.*;
import org.egov.works.services.common.models.bankaccounts.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AttendanceRegisterService {
    private final AttendanceServiceValidator attendanceServiceValidator;

    private final ResponseInfoFactory responseInfoFactory;

    private final Producer producer;

    private final AttendanceServiceConfiguration attendanceServiceConfiguration;

    private final RegisterEnrichment registerEnrichment;


    private final StaffRepository staffRepository;

    private final RegisterRepository registerRepository;

    private final AttendeeRepository attendeeRepository;

    private final StaffEnrichmentService staffEnrichmentService;
    private final IndividualServiceUtil individualServiceUtil;

    @Autowired
    public AttendanceRegisterService(AttendanceServiceValidator attendanceServiceValidator, ResponseInfoFactory responseInfoFactory, Producer producer, AttendanceServiceConfiguration attendanceServiceConfiguration, RegisterEnrichment registerEnrichment, StaffRepository staffRepository, RegisterRepository registerRepository, AttendeeRepository attendeeRepository, StaffEnrichmentService staffEnrichmentService, IndividualServiceUtil individualServiceUtil) {
        this.attendanceServiceValidator = attendanceServiceValidator;
        this.responseInfoFactory = responseInfoFactory;
        this.producer = producer;
        this.attendanceServiceConfiguration = attendanceServiceConfiguration;
        this.registerEnrichment = registerEnrichment;
        this.staffRepository = staffRepository;
        this.registerRepository = registerRepository;
        this.attendeeRepository = attendeeRepository;
        this.staffEnrichmentService = staffEnrichmentService;
        this.individualServiceUtil = individualServiceUtil;
    }

    /**
     * Create Attendance register
     *
     * @param request
     * @return
     */
    public AttendanceRegisterRequest createAttendanceRegister(AttendanceRegisterRequest request) {
        attendanceServiceValidator.validateCreateAttendanceRegister(request);
        registerEnrichment.enrichRegisterOnCreate(request);
        log.info("Enriched Register with Register number, Ids, first staff and audit details");
        producer.push(attendanceServiceConfiguration.getSaveAttendanceRegisterTopic(), request);
        log.info("Pushed create attendance register request to kafka");
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
        //Validate the requested parameters
        attendanceServiceValidator.validateSearchRegisterRequest(requestInfoWrapper, searchCriteria);

        //Enrich requested search criteria
        registerEnrichment.enrichSearchRegisterRequest(requestInfoWrapper.getRequestInfo(),searchCriteria);

        //Get the logged-in user roles
        Set<String> userRoles = getUserRoleCodes(requestInfoWrapper.getRequestInfo());

        //Get the roles enabled for open serach
        Set<String> openSearchEnabledRoles  = getRegisterOpenSearchEnabledRoles();

        List<AttendanceRegister> resultAttendanceRegisters = new ArrayList<>();

        if(isUserEnabledForOpenSearch(userRoles,openSearchEnabledRoles)){
            /*
               User having the role to perform open search on attendance register.
            */
            log.info("Searching registers for Superuser or Engineer");
            fetchAndFilterRegisters(searchCriteria, resultAttendanceRegisters);
        }else{
            /*
               Make sure response register list should contain only those register for which logged-in is associated.
            */
            Long userId = requestInfoWrapper.getRequestInfo().getUserInfo().getId();

            String individualId = individualServiceUtil.getIndividualDetailsFromUserId(userId,requestInfoWrapper.getRequestInfo(), searchCriteria.getTenantId()).get(0).getId();
            Set<String> registers = fetchRegistersAssociatedToLoggedInStaffUser(individualId);
            updateSearchCriteriaAndFetchAndFilterRegisters(registers, searchCriteria, resultAttendanceRegisters);
        }
        return resultAttendanceRegisters;
    }

    private boolean isUserEnabledForOpenSearch(Set<String> userRoles, Set<String> openSearchEnabledRoles) {
        for(String userRole : userRoles){
            if(openSearchEnabledRoles.contains(userRole)){
                return true;
            }
        }
        return false;
    }

    private Set<String> getRegisterOpenSearchEnabledRoles() {
        Set<String> openSearchEnabledRoles = new HashSet<>();
        String registerOpenSearchEnabledRoles = attendanceServiceConfiguration.getRegisterOpenSearchEnabledRoles();
        if(!StringUtils.isBlank(registerOpenSearchEnabledRoles)){
            String[] roles = registerOpenSearchEnabledRoles.split(",");
            for(String role :roles){
                if(!StringUtils.isBlank(role)){
                    openSearchEnabledRoles.add(role);
                }
            }
        }
        return openSearchEnabledRoles;
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
        return staffRepository.getAllStaff(staffSearchCriteria);
    }

    /* Returns list of user roles */
    private Set<String> getUserRoleCodes(RequestInfo requestInfo) {
        Set<String> userRoles = new HashSet<>();
        List<Role> roles = requestInfo.getUserInfo().getRoles();
        if(roles == null)
            return userRoles;
        return roles.stream().map(e->e.getCode()).collect(Collectors.toSet());
    }

    /* Get all registers associated for the logged in staff  */
    private Set<String> fetchRegistersAssociatedToLoggedInStaffUser(String uuid) {
        List<String> individualIds = new ArrayList<>();
        individualIds.add(uuid);
        StaffSearchCriteria staffSearchCriteria = StaffSearchCriteria.builder().individualIds(individualIds).build();
        List<StaffPermission> staffMembers = staffRepository.getAllStaff(staffSearchCriteria);
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
        attendanceServiceValidator.validateUpdateAgainstDB(attendanceRegisterRequest, attendanceRegistersFromDB, attendanceServiceConfiguration.getRegisterFirstStaffInsertEnabled());

        registerEnrichment.enrichRegisterOnUpdate(attendanceRegisterRequest, attendanceRegistersFromDB);
        log.info("Enriched with register Number, Ids and AuditDetails");
        producer.push(attendanceServiceConfiguration.getUpdateAttendanceRegisterTopic(), attendanceRegisterRequest);
        log.info("Pushed update attendance register request to kafka");

        return attendanceRegisterRequest;
    }

    public void updateAttendanceRegister(RequestInfoWrapper requestInfoWrapper, List<Project> projects) {
        if(!CollectionUtils.isEmpty(projects)) {
            List<AttendanceRegister> updatedRegisters = new ArrayList<>();
            projects.forEach(project -> {
                BigDecimal projectStartDate = BigDecimal.valueOf(project.getStartDate());
                BigDecimal projectEndDate = BigDecimal.valueOf(project.getEndDate());
                log.info("Fetching register from db for project : " + project.getId());
                List<AttendanceRegister> registers = searchAttendanceRegister(
                        requestInfoWrapper,
                        AttendanceRegisterSearchCriteria.builder().referenceId(project.getId()).tenantId(project.getTenantId()).build()
                );
                if(CollectionUtils.isEmpty(registers)) return;

                registers.forEach(attendanceRegister -> {
                    Boolean isUpdated = false;
                    if(attendanceRegister.getEndDate().compareTo(projectEndDate) < 0) {
                        // update register end date to project end date
                        attendanceRegister.setEndDate(projectEndDate);
                        isUpdated = true;
                    }
                    if(isUpdated) updatedRegisters.add(attendanceRegister);
                });
                if(!updatedRegisters.isEmpty()) {
                    AttendanceRegisterRequest attendanceRegisterRequest = AttendanceRegisterRequest.builder()
                            .attendanceRegister(updatedRegisters)
                            .requestInfo(requestInfoWrapper.getRequestInfo())
                            .build();
                    registerEnrichment.enrichRegisterOnUpdate(attendanceRegisterRequest, updatedRegisters);
                    log.info("Pushing update attendance register request to kafka");
                    producer.push(attendanceServiceConfiguration.getUpdateAttendanceRegisterTopic(), attendanceRegisterRequest);
                    log.info("Pushed update attendance register request to kafka");
                }
            });
        }
    }

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

    /**
     * Validate and update the end date of Attendance register as per revised contract
     * @param requestInfo
     * @param tenantId
     * @param referenceId
     * @param endDate
     */
    public void updateEndDateForRevisedContract(RequestInfo requestInfo, String tenantId, String referenceId, BigDecimal endDate) {
        AttendanceRegisterSearchCriteria attendanceRegisterSearchCriteria = AttendanceRegisterSearchCriteria.builder()
                .tenantId(tenantId)
                .referenceId(referenceId)
                .limit(attendanceServiceConfiguration.getAttendanceRegisterDefaultLimit())
                .offset(attendanceServiceConfiguration.getAttendanceRegisterDefaultOffset()).build();


        List<AttendanceRegister> attendanceRegisters = registerRepository.getRegister(attendanceRegisterSearchCriteria);

        if (attendanceRegisters != null && !attendanceRegisters.isEmpty()) {
            for (AttendanceRegister attendanceRegister : attendanceRegisters) {
                int comparisonResult = endDate.compareTo(attendanceRegister.getEndDate());
                if (comparisonResult < 0) {
                    throw new CustomException("END_DATE_NOT_EXTENDED","End date should not be earlier than previous end date");
                }

                attendanceRegister.setEndDate(endDate);
                AttendanceRegisterRequest attendanceRegisterRequest = AttendanceRegisterRequest.builder()
                        .attendanceRegister(Collections.singletonList(attendanceRegister)).
                        requestInfo(requestInfo).build();

                registerEnrichment.enrichRegisterOnUpdate(attendanceRegisterRequest, Collections.singletonList(attendanceRegister));
                producer.push(attendanceServiceConfiguration.getUpdateAttendanceRegisterTopic(), attendanceRegisterRequest);
            }
        }else {
            throw new CustomException("ATTENDANCE_REGISTER_NOT_FOUND", "Attendance registers not found for the referenceId");
        }

    }

    public Pagination getPaginationObject(AttendanceRegisterSearchCriteria criteria){
        Integer totalCount = registerRepository.getRegisterCount(criteria);
        return Pagination.builder()
                .limit(criteria.getLimit())
                .offset(criteria.getOffset())
                .totalCount(totalCount)
                .sortBy(criteria.getSortBy().name())
                .order(criteria.getSortOrder().name())
                .build();
    }

}
