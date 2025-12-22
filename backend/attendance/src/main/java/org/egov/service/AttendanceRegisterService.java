package org.egov.service;

import ch.qos.logback.core.BasicStatusManager;
import com.fasterxml.jackson.databind.node.ObjectNode;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.common.models.project.Address;
import org.egov.common.models.project.Project;
import org.egov.common.utils.CommonUtils;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.enrichment.RegisterEnrichment;
import org.egov.enrichment.StaffEnrichmentService;
import org.egov.common.producer.Producer;
import org.egov.repository.AttendeeRepository;
import org.egov.repository.RegisterRepository;
import org.egov.repository.StaffRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.HRMSUtil;
import org.egov.util.IndividualServiceUtil;
import org.egov.util.MusterRollWorkflowUtil;
import org.egov.util.ProjectServiceUtil;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.AttendanceServiceValidator;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.util.AttendanceServiceConstants.*;

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

    private final ProjectServiceUtil projectServiceUtil;

    // V2 Intermediate Billing - Period-based enrichment
    private final RegisterPeriodEnrichmentService registerPeriodEnrichmentService;

    // V2 - Workflow utility for dynamic state lookup
    private final MusterRollWorkflowUtil musterRollWorkflowUtil;

    @Autowired
    public AttendanceRegisterService(AttendanceServiceValidator attendanceServiceValidator, ResponseInfoFactory responseInfoFactory, Producer producer, AttendanceServiceConfiguration attendanceServiceConfiguration, RegisterEnrichment registerEnrichment, StaffRepository staffRepository, RegisterRepository registerRepository, AttendeeRepository attendeeRepository, StaffEnrichmentService staffEnrichmentService, IndividualServiceUtil individualServiceUtil, ProjectServiceUtil projectServiceUtil, RegisterPeriodEnrichmentService registerPeriodEnrichmentService, MusterRollWorkflowUtil musterRollWorkflowUtil) {
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
        this.projectServiceUtil = projectServiceUtil;
        this.registerPeriodEnrichmentService = registerPeriodEnrichmentService;
        this.musterRollWorkflowUtil = musterRollWorkflowUtil;
    }

    /**
     * Create Attendance register
     *
     * @param request
     * @return
     */
    public AttendanceRegisterRequest createAttendanceRegister(AttendanceRegisterRequest request) {
        // Extract tenantId from the attendance register object for tenant-specific Kafka publishing
        String tenantId = CommonUtils.getTenantId(request.getAttendanceRegister());
        attendanceServiceValidator.validateCreateAttendanceRegister(request);
        registerEnrichment.enrichRegisterOnCreate(request);
        log.info("Enriched Register with Register number, Ids, first staff and audit details");
        // Push the enriched register create request to Kafka topic configured for the tenant
        producer.push(tenantId, attendanceServiceConfiguration.getSaveAttendanceRegisterTopic(), request);
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
    public AttendanceRegisterResponse searchAttendanceRegister(RequestInfoWrapper requestInfoWrapper, AttendanceRegisterSearchCriteria searchCriteria) {
        //Validate the requested parameters
        attendanceServiceValidator.validateSearchRegisterRequest(requestInfoWrapper, searchCriteria);

        //Enrich requested search criteria
        registerEnrichment.enrichSearchRegisterRequest(requestInfoWrapper.getRequestInfo(),searchCriteria);

        //Get the logged-in user roles
        Set<String> userRoles = HRMSUtil.getUserRoleCodes(requestInfoWrapper.getRequestInfo());

        //Get the roles enabled for open serach
        Set<String> openSearchEnabledRoles  = HRMSUtil.getRegisterOpenSearchEnabledRoles(attendanceServiceConfiguration.getRegisterOpenSearchEnabledRoles());

        AttendanceRegisterResponse attendanceRegisterResponse = new AttendanceRegisterResponse();

        if(HRMSUtil.isUserEnabledForOpenSearch(userRoles,openSearchEnabledRoles)){
            /*
               User having the role to perform open search on attendance register.
            */
            log.info("Searching registers for Superuser or Engineer");
            fetchAndFilterRegisters(requestInfoWrapper, searchCriteria, attendanceRegisterResponse);
        }else{
            /*
               Make sure response register list should contain only those register for which logged-in is associated.
            */
            Long userId = requestInfoWrapper.getRequestInfo().getUserInfo().getId();

            String individualId = individualServiceUtil.getIndividualDetailsFromUserId(userId,requestInfoWrapper.getRequestInfo(), searchCriteria.getTenantId()).get(0).getId();
            Set<String> registers = fetchRegistersAssociatedToLoggedInStaffUser(individualId, searchCriteria.getTenantId());
            updateSearchCriteriaAndFetchAndFilterRegisters(requestInfoWrapper, registers, searchCriteria, attendanceRegisterResponse);
        }

        return attendanceRegisterResponse;
    }

    /**
     * Update the search criteria with list of registerIds if it does not contain registerId
     * then fetch the all registers based on the search criteria
     * but keep only those registers at last which contains attendees or staff in given search criteria
     *
     * @param registers
     * @param searchCriteria
     * @param attendanceRegisterResponse
     */
    public void updateSearchCriteriaAndFetchAndFilterRegisters(RequestInfoWrapper requestInfoWrapper,Set<String> registers, AttendanceRegisterSearchCriteria searchCriteria, AttendanceRegisterResponse attendanceRegisterResponse) {
        // Initialize a map to store counts for different attendance register statuses
        Map<String, Long> counts = new HashMap<>();
        counts.put(TOTAL_COUNT,0L);

        // Populate the map with initial count values for each register status alias
        for (Map.Entry<String, String> entry : attendanceServiceConfiguration.getAttendanceRegisterStatusMap().entrySet()) {
            String alias = entry.getKey();
            counts.put(alias,0L);
        }

        // Check if the search criteria contain register IDs
        if (searchCriteria.getIds() == null) {
            log.info("Register search criteria does not contain any register ids");
            List<String> registerIds = new ArrayList<>();
            registerIds.addAll(registers);
            searchCriteria.setIds(registerIds);
        } else {
            log.info("Register search criteria does contains register ids");

            // Validate that the user is searching only for associated registers
            for (String id : searchCriteria.getIds()) {
                if (!registers.contains(id)) {
                    log.error( "User can search only associated registers");
                    throw new CustomException("INVALID_REGISTER_ID", "User can search only associated registers");
                }
            }
        }

        // Call the method to fetch and filter attendance registers based on the criteria
        fetchAndFilterRegisters(requestInfoWrapper, searchCriteria, attendanceRegisterResponse);
    }

    /**
     * Fetch the all registers based on the supplied search criteria
     * but keep only those registers which contains attendees or staff given in search criteria
     *
     * @param searchCriteria
     * @param attendanceRegisterResponse
     */
    private void fetchAndFilterRegisters(RequestInfoWrapper requestInfoWrapper,AttendanceRegisterSearchCriteria searchCriteria, AttendanceRegisterResponse attendanceRegisterResponse) {
        log.info("Fetching registers based on supplied search criteria");

        // SPECIAL CASE: billingPeriodId = "AGGREGATE"
        // When aggregate billing is requested, fall back to V1 search using reviewStatus
        // This allows filtering registers by their review status for aggregate bill generation
        boolean hasBillingPeriodId = StringUtils.isNotBlank(searchCriteria.getBillingPeriodId());
        boolean hasRegisterPeriodStatus = StringUtils.isNotBlank(searchCriteria.getRegisterPeriodStatus());

        if (hasBillingPeriodId && BILLING_PERIOD_AGGREGATE.equalsIgnoreCase(searchCriteria.getBillingPeriodId())) {
            log.info("{} billing period detected - falling back to V1 search", BILLING_PERIOD_AGGREGATE);

            // Map registerPeriodStatus to reviewStatus for V1 search
            if (hasRegisterPeriodStatus) {
                String periodStatus = searchCriteria.getRegisterPeriodStatus();
                RequestInfo requestInfo = requestInfoWrapper.getRequestInfo();
                String tenantId = searchCriteria.getTenantId();

                // Use workflow utility to check if status is terminal (billing-ready)
                // Terminal states (e.g., APPROVED) → map to APPROVED
                // Non-terminal states → map to PENDINGFORAPPROVAL
                if (musterRollWorkflowUtil.isTerminalState(periodStatus, tenantId, requestInfo)) {
                    searchCriteria.setReviewStatus(ATTENDANCE_REGISTER_APPROVED);
                    log.info("Converted {} request with registerPeriodStatus={} (terminal) to V1 search with reviewStatus={}",
                            BILLING_PERIOD_AGGREGATE, periodStatus, ATTENDANCE_REGISTER_APPROVED);
                } else {
                    // Non-terminal status (PENDING, PENDINGFORAPPROVAL, etc.) → map to PENDINGFORAPPROVAL
                    searchCriteria.setReviewStatus(ATTENDANCE_REGISTER_PENDINGFORAPPROVAL);
                    log.info("Converted {} request with registerPeriodStatus={} (non-terminal) to V1 search with reviewStatus={}",
                            BILLING_PERIOD_AGGREGATE, periodStatus, ATTENDANCE_REGISTER_PENDINGFORAPPROVAL);
                }
            } else {
                // If no registerPeriodStatus provided, default to APPROVED (original behavior)
                log.info("No registerPeriodStatus provided with {} - defaulting to reviewStatus={}",
                        BILLING_PERIOD_AGGREGATE, ATTENDANCE_REGISTER_APPROVED);
                searchCriteria.setReviewStatus(ATTENDANCE_REGISTER_APPROVED);
            }

            // Clear V2 parameters to ensure V1 flow
            searchCriteria.setBillingPeriodId(null);
            searchCriteria.setRegisterPeriodStatus(null);

            log.info("{} request converted to V1 search with reviewStatus={}",
                    BILLING_PERIOD_AGGREGATE, searchCriteria.getReviewStatus());
        }

        // IMPORTANT: reviewStatus takes priority over V2 logic
        // If reviewStatus is provided, ALWAYS use V1 flow regardless of billingPeriodId
        boolean hasReviewStatus = StringUtils.isNotBlank(searchCriteria.getReviewStatus());
        hasBillingPeriodId = StringUtils.isNotBlank(searchCriteria.getBillingPeriodId()); // Re-check after AGGREGATE handling

        if (hasReviewStatus) {
            // V1 Flow: reviewStatus takes priority - ignore V2 parameters
            log.info("V1 search detected (reviewStatus provided) - using database-level filtering and counting");
            if (hasBillingPeriodId) {
                log.warn("billingPeriodId and registerPeriodStatus are ignored when reviewStatus is provided");
            }
            fetchAndFilterRegistersV1(requestInfoWrapper, searchCriteria, attendanceRegisterResponse);
            return;
        }

        // V2 Flow: Only when billingPeriodId is provided AND reviewStatus is NOT provided
        if (hasBillingPeriodId) {
            log.info("V2 period-based search detected - using in-memory filtering with V1 status format");
            fetchAndFilterRegistersV2(requestInfoWrapper, searchCriteria, attendanceRegisterResponse);
            return;
        }

        // Default V1 Flow: No reviewStatus, no billingPeriodId
        log.info("V1 search (default) - using database-level filtering and counting");
        fetchAndFilterRegistersV1(requestInfoWrapper, searchCriteria, attendanceRegisterResponse);
    }

    /**
     * V1 Search Flow (Original Implementation)
     * - Uses database-level filtering by reviewStatus
     * - SQL-based counting and pagination
     * - Backward compatible with existing behavior
     */
    private void fetchAndFilterRegistersV1(RequestInfoWrapper requestInfoWrapper,
                                           AttendanceRegisterSearchCriteria searchCriteria,
                                           AttendanceRegisterResponse attendanceRegisterResponse) {
        log.info("Fetching registers using V1 flow (reviewStatus-based)");

        if(attendanceServiceConfiguration.getAttendanceRegisterProjectSearchEnabled()){
            if((StringUtils.isBlank(searchCriteria.getReferenceId()) && !StringUtils.isBlank(searchCriteria.getLocalityCode())) || (!StringUtils.isBlank(searchCriteria.getReferenceId()) && StringUtils.isBlank(searchCriteria.getLocalityCode())) ){
                throw new CustomException("ATTENDANCE_REGISTER_SEARCH_INVALID",
                    "Both referenceId AND localityCode are required together for project-based search. " +
                    "Received: referenceId=" + searchCriteria.getReferenceId() +
                    ", localityCode=" + searchCriteria.getLocalityCode());
            }

            if(!StringUtils.isBlank(searchCriteria.getReferenceId())){
                Project projectSearch = Project.builder()
                  .tenantId(searchCriteria.getTenantId())
                  .id(searchCriteria.getReferenceId())
                  .address(Address.builder().boundary(searchCriteria.getLocalityCode()).build())
                  .build();

                List<Project> projects = projectServiceUtil.getProject(
                  searchCriteria.getTenantId(), projectSearch, requestInfoWrapper.getRequestInfo(), searchCriteria.getIsChildrenRequired(), true
                );

                if(projects.isEmpty()){
                    throw new CustomException("ATTENDANCE_REGISTER_PROJECT_NOT_FOUND",
                        "Project not found for referenceId: " + searchCriteria.getReferenceId() +
                        ", tenantId: " + searchCriteria.getTenantId() +
                        ", localityCode: " + searchCriteria.getLocalityCode());
                }

                List<String> referenceId = new ArrayList<>();

                projects.forEach(project -> {
                    referenceId.add(project.getId());

                    if(project.getDescendants()!=null && !project.getDescendants().isEmpty()) {
                        project.getDescendants().forEach(child -> {
                            referenceId.add(child.getId());
                        });
                    }
                });
                searchCriteria.setReferenceIds(referenceId);
                searchCriteria.setReferenceId(null);
                searchCriteria.setLocalityCode(null);
            }
        }

        // Fetch the all registers based on the supplied search criteria
        List<AttendanceRegister> attendanceRegisters = registerRepository.getRegister(searchCriteria);

        // Retrieve the count of attendance registers based on the search criteria
        Map<String, Long> counts = registerRepository.getRegisterCounts(searchCriteria);

        // Initialize the final list that will hold the filtered attendance registers
        List<AttendanceRegister> resultAttendanceRegisters = new ArrayList<>();

        // Check if any registers were retrieved
        if(attendanceRegisters!=null && !attendanceRegisters.isEmpty()){
            // Create a map with key as registerId and corresponding register list as value
            Map<String, List<AttendanceRegister>> registerIdVsAttendanceRegisters = attendanceRegisters.stream().collect(Collectors.groupingBy(AttendanceRegister::getId));

            List<String> registerIdsToSearch = new ArrayList<>();
            registerIdsToSearch.addAll(registerIdVsAttendanceRegisters.keySet());

            // Fetch and filer staff members based on the supplied search criteria.
            log.info("Fetch all staff members based on the supplied search criteria");
            List<StaffPermission> staffMembers = fetchAllStaffMembersAssociatedToRegisterIds(registerIdsToSearch,searchCriteria);

            // Store the original staffId from searchCriteria before modifying it
            String staffId = searchCriteria.getStaffId();
            searchCriteria.setStaffId(null); // Temporarily remove staffId to fetch all related staff members

            // Fetch all staff members for the registers, regardless of the specific staff ID
            List<StaffPermission> allStaffMembers = fetchAllStaffMembersAssociatedToRegisterIds(registerIdsToSearch,searchCriteria);

            // Create a map with key as registerId and corresponding staff list as value
            Map<String, List<StaffPermission>> registerIdStaffMapping = staffMembers.stream().collect(Collectors.groupingBy(StaffPermission::getRegisterId));

            // Map all staff members (not just filtered ones) to their respective registers
            Map<String, List<StaffPermission>> registerIdAllStaffMapping = allStaffMembers.stream().collect(Collectors.groupingBy(StaffPermission::getRegisterId));

            // Enrich attendance registers with owner names based on staff mappings
            enrichOwnerNameOfAttendanceRegister(registerIdStaffMapping, registerIdAllStaffMapping);

            // Restore the original staffId in the search criteria
            searchCriteria.setStaffId(staffId);
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

        attendanceRegisterResponse.setAttendanceRegister(resultAttendanceRegisters);

        // Set the total count of registers in the response
        attendanceRegisterResponse.setTotalCount(counts.get(TOTAL_COUNT));
        counts.remove(TOTAL_COUNT);

        // If register review status is enabled, add the status count to the response
        if(attendanceServiceConfiguration.getAttendanceRegisterReviewStatusEnabled()) attendanceRegisterResponse.setStatusCount(counts);
    }

    /**
     * V2 Search Flow (Period-based with registerPeriodStatus)
     * - Fetches ALL registers without pagination
     * - Filters by billing period dates
     * - Enriches with registerPeriodStatus from muster-roll API
     * - Filters by registerPeriodStatus in-memory
     * - Counts by registerPeriodStatus in-memory
     * - Applies pagination in-memory
     */
    private void fetchAndFilterRegistersV2(RequestInfoWrapper requestInfoWrapper,
                                           AttendanceRegisterSearchCriteria searchCriteria,
                                           AttendanceRegisterResponse attendanceRegisterResponse) {
        log.info("Fetching registers using V2 flow (registerPeriodStatus-based)");

        // Save original pagination parameters
        Integer originalLimit = searchCriteria.getLimit();
        Integer originalOffset = searchCriteria.getOffset();

        // Set high limit to fetch all registers for V2 flow
        // We need all registers to properly filter and count by registerPeriodStatus
        // Using configurable max limit as safeguard against unbounded queries
        Integer v2MaxFetchLimit = attendanceServiceConfiguration.getAttendanceRegisterV2MaxFetchLimit();
        searchCriteria.setLimit(v2MaxFetchLimit);
        searchCriteria.setOffset(0);

        if(attendanceServiceConfiguration.getAttendanceRegisterProjectSearchEnabled()){
            if((StringUtils.isBlank(searchCriteria.getReferenceId()) && !StringUtils.isBlank(searchCriteria.getLocalityCode())) || (!StringUtils.isBlank(searchCriteria.getReferenceId()) && StringUtils.isBlank(searchCriteria.getLocalityCode())) ){
                throw new CustomException("ATTENDANCE_REGISTER_SEARCH_INVALID",
                    "Both referenceId AND localityCode are required together for project-based search. " +
                    "Received: referenceId=" + searchCriteria.getReferenceId() +
                    ", localityCode=" + searchCriteria.getLocalityCode());
            }

            if(!StringUtils.isBlank(searchCriteria.getReferenceId())){
                Project projectSearch = Project.builder()
                  .tenantId(searchCriteria.getTenantId())
                  .id(searchCriteria.getReferenceId())
                  .address(Address.builder().boundary(searchCriteria.getLocalityCode()).build())
                  .build();

                List<Project> projects = projectServiceUtil.getProject(
                  searchCriteria.getTenantId(), projectSearch, requestInfoWrapper.getRequestInfo(), searchCriteria.getIsChildrenRequired(), true
                );

                if(projects.isEmpty()){
                    throw new CustomException("ATTENDANCE_REGISTER_PROJECT_NOT_FOUND",
                        "Project not found for referenceId: " + searchCriteria.getReferenceId() +
                        ", tenantId: " + searchCriteria.getTenantId() +
                        ", localityCode: " + searchCriteria.getLocalityCode());
                }

                List<String> referenceId = new ArrayList<>();

                projects.forEach(project -> {
                    referenceId.add(project.getId());

                    if(project.getDescendants()!=null && !project.getDescendants().isEmpty()) {
                        project.getDescendants().forEach(child -> {
                            referenceId.add(child.getId());
                        });
                    }
                });
                searchCriteria.setReferenceIds(referenceId);
                searchCriteria.setReferenceId(null);
                searchCriteria.setLocalityCode(null);
            }
        }

        // Fetch ALL registers (without pagination)
        List<AttendanceRegister> attendanceRegisters = registerRepository.getRegister(searchCriteria);

        log.info("Fetched {} total registers before period filtering",
                attendanceRegisters != null ? attendanceRegisters.size() : 0);

        // Initialize the final list
        List<AttendanceRegister> resultAttendanceRegisters = new ArrayList<>();

        // Process registers if any were found
        if(attendanceRegisters!=null && !attendanceRegisters.isEmpty()){
            // Create a map with key as registerId and corresponding register list as value
            Map<String, List<AttendanceRegister>> registerIdVsAttendanceRegisters = attendanceRegisters.stream().collect(Collectors.groupingBy(AttendanceRegister::getId));

            List<String> registerIdsToSearch = new ArrayList<>();
            registerIdsToSearch.addAll(registerIdVsAttendanceRegisters.keySet());

            // Fetch and filter staff members
            log.info("Fetch all staff members based on the supplied search criteria");
            List<StaffPermission> staffMembers = fetchAllStaffMembersAssociatedToRegisterIds(registerIdsToSearch,searchCriteria);

            // Store the original staffId from searchCriteria before modifying it
            String staffId = searchCriteria.getStaffId();
            searchCriteria.setStaffId(null);

            // Fetch all staff members for the registers
            List<StaffPermission> allStaffMembers = fetchAllStaffMembersAssociatedToRegisterIds(registerIdsToSearch,searchCriteria);

            // Create mappings
            Map<String, List<StaffPermission>> registerIdStaffMapping = staffMembers.stream().collect(Collectors.groupingBy(StaffPermission::getRegisterId));
            Map<String, List<StaffPermission>> registerIdAllStaffMapping = allStaffMembers.stream().collect(Collectors.groupingBy(StaffPermission::getRegisterId));

            // Enrich attendance registers with owner names
            enrichOwnerNameOfAttendanceRegister(registerIdStaffMapping, registerIdAllStaffMapping);

            // Restore the original staffId
            searchCriteria.setStaffId(staffId);

            // Update registerIDToSearch if staffId present
            if (searchCriteria.getStaffId() != null){
                registerIdsToSearch.clear();
                registerIdsToSearch.addAll(registerIdStaffMapping.keySet());
            }

            // Fetch and filter attendees
            List<IndividualEntry> attendees = fetchAllAttendeesAssociatedToRegisterIds(registerIdsToSearch,searchCriteria);
            Map<String, List<IndividualEntry>> registerIdAttendeeMapping = attendees.stream().collect(Collectors.groupingBy(IndividualEntry::getRegisterId));

            // Update registerIDToSearch if attendeeId present
            if(searchCriteria.getAttendeeId() != null){
                List<String> registerIdsAssociatedToAttendees = new ArrayList<>();
                registerIdsAssociatedToAttendees.addAll(registerIdAttendeeMapping.keySet());
                registerIdsToSearch.clear();
                registerIdsToSearch.addAll(registerIdsAssociatedToAttendees);
            }

            // Populate registers with staff and attendees
            for(String registerId : registerIdsToSearch ){
                List<AttendanceRegister> registers = registerIdVsAttendanceRegisters.get(registerId);
                for(AttendanceRegister register : registers){
                    register.setStaff(registerIdStaffMapping.get(registerId));
                    register.setAttendees(registerIdAttendeeMapping.get(registerId));
                    resultAttendanceRegisters.add(register);
                }
            }
        }

        log.info("After staff/attendee filtering: {} registers remain", resultAttendanceRegisters.size());

        // V2 Period-based filtering and enrichment
        log.info("Applying period-based filtering and enrichment for period {}",
                searchCriteria.getBillingPeriodId());

        resultAttendanceRegisters = registerPeriodEnrichmentService.filterAndEnrichRegistersForPeriod(
                resultAttendanceRegisters,
                searchCriteria.getBillingPeriodId(),
                requestInfoWrapper.getRequestInfo(),
                searchCriteria.getTenantId()
        );

        log.info("After period filtering and enrichment: {} registers remain", resultAttendanceRegisters.size());

        // V2: Map registerPeriodStatus to V1 status format (APPROVED/PENDING)
        // This ensures consistent status format across V1 and V2 responses
        // Workflow states are fetched dynamically from workflow service
        RequestInfo requestInfo = requestInfoWrapper.getRequestInfo();
        String tenantId = searchCriteria.getTenantId();
        for (AttendanceRegister register : resultAttendanceRegisters) {
            String mappedStatus = mapRegisterPeriodStatusToV1Status(register.getRegisterPeriodStatus(), tenantId, requestInfo);
            // Store mapped status in additionalDetails for filtering and counting
            register.setReviewStatus(mappedStatus);
        }

        log.info("Mapped registerPeriodStatus to V1 status format");

        // V2 IMPORTANT: Calculate counts BEFORE filtering (just like V1)
        // This ensures statusCount shows ALL statuses for the period, not just the filtered one
        Map<String, Long> v1StatusCounts = calculateV1StatusCounts(resultAttendanceRegisters);

        log.info("V2 status counts (BEFORE filtering): {}", v1StatusCounts);

        // V2 Filter by registerPeriodStatus if provided
        // User sends either "APPROVED" or "PENDING" (or "PENDINGFORAPPROVAL")
        // - Terminal workflow state (e.g., APPROVED) → show only registers with billing-ready status
        // - Non-terminal → show registers with all non-terminal workflow statuses
        if (StringUtils.isNotBlank(searchCriteria.getRegisterPeriodStatus())) {
            log.info("Filtering by registerPeriodStatus: {}", searchCriteria.getRegisterPeriodStatus());

            String requestedStatus = searchCriteria.getRegisterPeriodStatus().toUpperCase();

            // Check if requested status is a terminal workflow state (dynamically from workflow service)
            if (musterRollWorkflowUtil.isTerminalState(requestedStatus, tenantId, requestInfo)) {
                // Filter only terminal (billing-ready) states
                resultAttendanceRegisters = resultAttendanceRegisters.stream()
                        .filter(register -> ATTENDANCE_REGISTER_APPROVED.equals(register.getReviewStatus()))
                        .collect(Collectors.toList());
                log.info("Filtered by terminal (APPROVED) status: {} registers remain", resultAttendanceRegisters.size());

            } else {
                // Any other value (PENDING, PENDINGFORAPPROVAL, etc.) → filter all non-terminal
                // This includes NOT_CREATED and all workflow states that are not terminal
                resultAttendanceRegisters = resultAttendanceRegisters.stream()
                        .filter(register -> ATTENDANCE_REGISTER_PENDINGFORAPPROVAL.equals(register.getReviewStatus()))
                        .collect(Collectors.toList());
                log.info("Filtered by non-terminal (PENDING) status: {} registers remain", resultAttendanceRegisters.size());
            }
        }

        // V2 Apply pagination in-memory
        int limit = originalLimit != null ? originalLimit : attendanceServiceConfiguration.getAttendanceRegisterDefaultLimit();
        int offset = originalOffset != null ? originalOffset : attendanceServiceConfiguration.getAttendanceRegisterDefaultOffset();

        // Ensure limit doesn't exceed max limit
        if (limit > attendanceServiceConfiguration.getAttendanceRegisterMaxLimit()) {
            limit = attendanceServiceConfiguration.getAttendanceRegisterMaxLimit();
        }

        // totalCount = count of FILTERED registers (after registerPeriodStatus filter)
        // statusCount = count of ALL registers for the period (calculated before filtering)
        // This matches V1 behavior where totalCount shows filtered count, statusCount shows all statuses
        long totalCount = resultAttendanceRegisters.size();

        // Apply pagination
        List<AttendanceRegister> paginatedRegisters = resultAttendanceRegisters.stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());

        log.info("After pagination (offset={}, limit={}): {} registers in page, {} total",
                offset, limit, paginatedRegisters.size(), totalCount);

        // Restore original pagination parameters
        searchCriteria.setLimit(originalLimit);
        searchCriteria.setOffset(originalOffset);

        // Set response
        attendanceRegisterResponse.setAttendanceRegister(paginatedRegisters);
        attendanceRegisterResponse.setTotalCount(totalCount);
        attendanceRegisterResponse.setStatusCount(v1StatusCounts);
    }

    /**
     * Map registerPeriodStatus values to V1 status format (APPROVED/PENDING)
     *
     * ================================================================================
     * WHY THIS MAPPING EXISTS - BACKWARD COMPATIBILITY
     * ================================================================================
     *
     * CONTEXT:
     * --------
     * V1 API Response has ONLY 2 statuses in statusCount:
     *   - APPROVED: Muster roll approved, ready for billing
     *   - PENDINGFORAPPROVAL: Not yet approved (waiting for action)
     *
     * V2 Muster Roll Workflow has GRANULAR statuses:
     *   - NOT_CREATED: No muster roll exists for this period
     *   - PENDING: Muster roll created, waiting for approval
     *   - SENT_BACK: Muster roll sent back for corrections
     *   - REJECTED: Muster roll rejected
     *   - APPROVED: Muster roll approved
     *
     * PROBLEM:
     * --------
     * Existing V1 clients (UI, reports, integrations) expect ONLY APPROVED/PENDING
     * in the statusCount response. If we return V2 statuses, these clients will break.
     *
     * SOLUTION:
     * ---------
     * Map V2 granular statuses to V1 format for API response:
     *   - APPROVED → APPROVED (muster approved, ready for billing)
     *   - Everything else → PENDINGFORAPPROVAL (needs action before billing)
     *
     * WHY NOT MDMS/WORKFLOW CONFIG:
     * -----------------------------
     * This is a COMPATIBILITY LAYER, not workflow configuration. The actual
     * workflow (status transitions, validations) is managed by muster-roll service's
     * workflow config. This mapping simply translates for backward compatibility
     * and will NOT change - it's a fixed 2-value output format.
     *
     * ================================================================================
     *
     * @param registerPeriodStatus The muster roll status from period_statuses
     * @param tenantId The tenant ID for workflow lookup
     * @param requestInfo Request info for workflow API call
     * @return V1 status (APPROVED or PENDINGFORAPPROVAL)
     */
    private String mapRegisterPeriodStatusToV1Status(String registerPeriodStatus, String tenantId, RequestInfo requestInfo) {
        if (StringUtils.isBlank(registerPeriodStatus)) {
            log.warn("registerPeriodStatus is null or blank, defaulting to PENDING");
            return ATTENDANCE_REGISTER_PENDINGFORAPPROVAL;
        }

        // Check if status is a terminal (billing-ready) workflow state
        // Terminal states are fetched dynamically from workflow service
        // Only NOT_CREATED is hardcoded (it's not a workflow status)
        if (musterRollWorkflowUtil.isTerminalState(registerPeriodStatus, tenantId, requestInfo)) {
            return ATTENDANCE_REGISTER_APPROVED;
        }

        // Any non-terminal status (including NOT_CREATED and all non-terminal workflow states) is pending
        return ATTENDANCE_REGISTER_PENDINGFORAPPROVAL;
    }

    /**
     * Calculate counts by V1 status format (APPROVED/PENDING)
     *
     * ================================================================================
     * WHY THIS METHOD EXISTS - CONSISTENT API RESPONSE FORMAT
     * ================================================================================
     *
     * CONTEXT:
     * --------
     * The attendance search API response includes a statusCount field:
     *   {
     *     "statusCount": {
     *       "APPROVED": 50,
     *       "PENDINGFORAPPROVAL": 25
     *     }
     *   }
     *
     * V1 clients expect BOTH keys to ALWAYS be present (even if count is 0).
     *
     * WHY INITIALIZE TO 0:
     * --------------------
     * If we only return keys that have non-zero counts, clients that access
     * statusCount["APPROVED"] would get null/undefined instead of 0,
     * causing potential NPE or display issues.
     *
     * WHY THESE SPECIFIC CONSTANTS:
     * -----------------------------
     * ATTENDANCE_REGISTER_APPROVED and ATTENDANCE_REGISTER_PENDINGFORAPPROVAL
     * are constants defined in AttendanceServiceConstants.java. They match
     * the V1 API contract that existing clients depend on.
     *
     * Making these MDMS-configurable would risk breaking clients if someone
     * accidentally changes the values. These are OUTPUT FORMAT constants,
     * not business-configurable values.
     *
     * ================================================================================
     *
     * @param registers List of registers (with reviewStatus set to mapped V1 status)
     * @return Map with APPROVED and PENDINGFORAPPROVAL counts (both always present)
     */
    private Map<String, Long> calculateV1StatusCounts(List<AttendanceRegister> registers) {
        Map<String, Long> counts = new HashMap<>();

        // Always initialize both counts to 0 - V1 clients expect both keys
        counts.put(ATTENDANCE_REGISTER_APPROVED, 0L);
        counts.put(ATTENDANCE_REGISTER_PENDINGFORAPPROVAL, 0L);

        if (CollectionUtils.isEmpty(registers)) {
            log.info("No registers to count, returning zero counts");
            return counts;
        }

        // Group by reviewStatus (which contains mapped V1 status) and count
        Map<String, Long> statusCounts = registers.stream()
                .filter(register -> StringUtils.isNotBlank(register.getReviewStatus()))
                .collect(Collectors.groupingBy(
                        AttendanceRegister::getReviewStatus,
                        Collectors.counting()
                ));

        // Update counts with actual values (keeping 0 for missing statuses)
        counts.putAll(statusCounts);

        log.info("Calculated V1 status counts - APPROVED: {}, PENDING: {}",
                counts.get(ATTENDANCE_REGISTER_APPROVED),
                counts.get(ATTENDANCE_REGISTER_PENDINGFORAPPROVAL));

        return counts;
    }

    private void enrichOwnerNameOfAttendanceRegister(Map<String, List<StaffPermission>> registerIdStaffMapping, Map<String, List<StaffPermission>> registerIdAllStaffMapping) {
        // Create a map to store the owner name for each register ID
        Map<String, String> registerIdToOwnerName = new HashMap<>();

        // Iterate over each entry in the registerIdAllStaffMapping map
        for (Map.Entry<String, List<StaffPermission>> entry : registerIdAllStaffMapping.entrySet()) {
            String registerId = entry.getKey();
            List<StaffPermission> staffPermissions = entry.getValue(); // Get the list of staff members for this register

            // Iterate through each StaffPermission in the list
            for (StaffPermission staffPermission : staffPermissions) {
                // Check if the staffType is OWNER
                if (staffPermission.getStaffType() == StaffType.OWNER) {
                    Object additionalDetails = staffPermission.getAdditionalDetails();

                    if (additionalDetails instanceof ObjectNode) {
                        // Cast additionalDetails to ObjectNode
                        ObjectNode detailsNode = (ObjectNode) additionalDetails;

                        // Get the staffName field as a String
                        if (detailsNode.has(STAFF_NAME)) {
                            registerIdToOwnerName.put(registerId, detailsNode.get(STAFF_NAME).asText());
                            break;
                        }
                    }
                }
            }
        }

        for (Map.Entry<String, List<StaffPermission>> entry : registerIdStaffMapping.entrySet()) {
            String registerId = entry.getKey();
            List<StaffPermission> staffPermissions = entry.getValue();

            for (StaffPermission staffPermission : staffPermissions) {
                Object additionalDetails = staffPermission.getAdditionalDetails();

                if (additionalDetails instanceof ObjectNode) {
                    // Cast additionalDetails to ObjectNode
                    ObjectNode detailsNode = (ObjectNode) additionalDetails;

                    // Add or update the ownerName field
                    detailsNode.put(OWNER_NAME, registerIdToOwnerName.get(registerId));

                    // Set the updated ObjectNode back
                    staffPermission.setAdditionalDetails(detailsNode);
                }
            }
        }
    }

    private List<IndividualEntry> fetchAllAttendeesAssociatedToRegisterIds(List<String> registerIdsToSearch, AttendanceRegisterSearchCriteria searchCriteria) {
        AttendeeSearchCriteria attendeeSearchCriteria = null;

        String tenantId = searchCriteria.getTenantId();
        String attendeeId = searchCriteria.getAttendeeId();
        List<String> fallbackTags = searchCriteria.getTags();
        boolean includeTagged = Boolean.TRUE.equals(searchCriteria.getIncludeTaggedAttendees());

        List<String> resolvedTags = null;

        // If includeTaggedAttendees is true and attendeeId is provided, try resolving tags
        if (attendeeId != null && includeTagged) {
            List<IndividualEntry> baseAttendee = Optional
                    .ofNullable(attendeeRepository.getAttendees(
                    tenantId ,
                    AttendeeSearchCriteria.builder()
                            .individualIds(Collections.singletonList(attendeeId))
                            .registerIds(registerIdsToSearch)
                            .tenantId(tenantId)
                            .build()
            )).orElse(Collections.emptyList());

            resolvedTags = baseAttendee.stream()
                    .map(IndividualEntry::getTag)
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());

            if (resolvedTags.isEmpty()) {
                log.warn("includeTaggedAttendees is true but no tags found for attendeeId {}", attendeeId);
            }
        }

        // Build the final search criteria based on the available information
        if (attendeeId != null && includeTagged && !resolvedTags.isEmpty()) {
            attendeeSearchCriteria = AttendeeSearchCriteria.builder()
                    .registerIds(registerIdsToSearch)
                    .tags(resolvedTags)
                    .tenantId(tenantId)
                    .build();
        } else if (attendeeId != null) {
            attendeeSearchCriteria = AttendeeSearchCriteria.builder()
                    .registerIds(registerIdsToSearch)
                    .individualIds(Collections.singletonList(attendeeId))
                    .tags((fallbackTags != null && !fallbackTags.isEmpty()) ? fallbackTags : null)
                    .tenantId(tenantId)
                    .build();
        } else {
            attendeeSearchCriteria = AttendeeSearchCriteria.builder()
                    .registerIds(registerIdsToSearch)
                    .tags((fallbackTags != null && !fallbackTags.isEmpty()) ? fallbackTags : null)
                    .tenantId(tenantId)
                    .build();
        }
        return Optional.ofNullable(attendeeRepository.getAttendees(tenantId,attendeeSearchCriteria))
                .orElse(Collections.emptyList());
    }

    /* Get all staff members associated for the register */
    private List<StaffPermission> fetchAllStaffMembersAssociatedToRegisterIds(List<String> registerIdsToSearch, AttendanceRegisterSearchCriteria searchCriteria) {
        StaffSearchCriteria staffSearchCriteria = null ;
        if(searchCriteria.getStaffId() != null){
            staffSearchCriteria = StaffSearchCriteria.builder().registerIds(registerIdsToSearch).individualIds(Collections.singletonList(searchCriteria.getStaffId())).tenantId(searchCriteria.getTenantId()).build();
        } else {
            staffSearchCriteria = StaffSearchCriteria.builder().registerIds(registerIdsToSearch).tenantId(searchCriteria.getTenantId()).build();
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
    private Set<String> fetchRegistersAssociatedToLoggedInStaffUser(String uuid, String tenantId ) {
        List<String> individualIds = new ArrayList<>();
        individualIds.add(uuid);
        // Construct staff search criteria with tenantId to fetch registers for the logged-in staff user
        StaffSearchCriteria staffSearchCriteria = StaffSearchCriteria.builder().individualIds(individualIds).tenantId(tenantId).build();
        List<StaffPermission> staffMembers = staffRepository.getAllStaff(staffSearchCriteria);
        return staffMembers.stream().map(e -> e.getRegisterId()).collect(Collectors.toSet());
    }

    /* Get all registers associated for the logged in attendee  */
    private Set<String> fetchRegistersAssociatedToLoggedInAttendeeUser(String tenantId, String uuid) {
        AttendeeSearchCriteria attendeeSearchCriteria = AttendeeSearchCriteria.builder().tenantId(tenantId).individualIds(Collections.singletonList(uuid)).build();
        List<IndividualEntry> attendees = attendeeRepository.getAttendees(tenantId, attendeeSearchCriteria);
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
        // Push the update request to Kafka using tenant-specific topic
        producer.push(tenantId, attendanceServiceConfiguration.getUpdateAttendanceRegisterTopic(), attendanceRegisterRequest);
        log.info("Pushed update attendance register request to kafka");

        return attendanceRegisterRequest;
    }

    public void updateAttendanceRegister(RequestInfoWrapper requestInfoWrapper, List<Project> projects) {
        if(!CollectionUtils.isEmpty(projects)) {
            // Extract tenantId from project list for downstream processing and Kafka publishing
            String tenantId = CommonUtils.getTenantId(projects);
            List<AttendanceRegister> updatedRegisters = new ArrayList<>();
            projects.forEach(project -> {
                BigDecimal projectStartDate = BigDecimal.valueOf(project.getStartDate());
                BigDecimal projectEndDate = BigDecimal.valueOf(project.getEndDate());
                log.info("Fetching register from db for project : " + project.getId());
                AttendanceRegisterResponse response = searchAttendanceRegister(
                        requestInfoWrapper,
                        AttendanceRegisterSearchCriteria.builder().referenceId(project.getId()).tenantId(project.getTenantId()).build()
                );
                List<AttendanceRegister> registers = response.getAttendanceRegister();
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
                    // Push the tenant-scoped update request to Kafka topic
                    producer.push(tenantId, attendanceServiceConfiguration.getUpdateAttendanceRegisterTopic(), attendanceRegisterRequest);
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

            attendanceRegisterList = searchAttendanceRegister(requestInfoWrapper, searchCriteria).getAttendanceRegister();
            log.info("Attendance register search successful");
        } catch (Exception e) {
            log.info("Error in searching attendance register", e);
            throw new CustomException("SEARCH_ATTENDANCE_REGISTER",
                    "Error in searching attendance register. registerIds: " + registerIds +
                    ", tenantId: " + tenantId + ". Error: " + e.getMessage());
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
                    throw new CustomException("END_DATE_NOT_EXTENDED",
                            "End date should not be earlier than previous end date. " +
                            "registerId: " + attendanceRegister.getId() +
                            ", previousEndDate: " + attendanceRegister.getEndDate() +
                            ", newEndDate: " + endDate);
                }

                attendanceRegister.setEndDate(endDate);
                AttendanceRegisterRequest attendanceRegisterRequest = AttendanceRegisterRequest.builder()
                        .attendanceRegister(Collections.singletonList(attendanceRegister)).
                        requestInfo(requestInfo).build();

                registerEnrichment.enrichRegisterOnUpdate(attendanceRegisterRequest, Collections.singletonList(attendanceRegister));
                // Push register update for revised contract end date to tenant-specific Kafka topic
                producer.push(tenantId, attendanceServiceConfiguration.getUpdateAttendanceRegisterTopic(), attendanceRegisterRequest);
            }
        }else {
            throw new CustomException("ATTENDANCE_REGISTER_NOT_FOUND",
                    "Attendance registers not found. referenceId: " + referenceId +
                    ", tenantId: " + tenantId);
        }

    }

}
