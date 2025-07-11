package org.egov.service;

import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.enrichment.StaffEnrichmentService;
import org.egov.common.producer.Producer;
import org.egov.repository.RegisterRepository;
import org.egov.repository.StaffRepository;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.AttendanceServiceValidator;
import org.egov.validator.StaffServiceValidator;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.StaffPermission;
import org.egov.web.models.StaffPermissionRequest;
import org.egov.web.models.StaffSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class StaffService {
    private final StaffServiceValidator staffServiceValidator;

    private final ResponseInfoFactory responseInfoFactory;

    private final StaffEnrichmentService staffEnrichmentService;

    private final StaffRepository staffRepository;

    private final RegisterRepository registerRepository;


    private final Producer producer;

    private final AttendanceServiceConfiguration serviceConfiguration;

    private final AttendanceRegisterService attendanceRegisterService;

    private final AttendanceServiceValidator attendanceServiceValidator;

    @Autowired
    public StaffService(StaffServiceValidator staffServiceValidator, ResponseInfoFactory responseInfoFactory, StaffEnrichmentService staffEnrichmentService, StaffRepository staffRepository, RegisterRepository registerRepository, Producer producer, AttendanceServiceConfiguration serviceConfiguration, AttendanceRegisterService attendanceRegisterService, AttendanceServiceValidator attendanceServiceValidator) {
        this.staffServiceValidator = staffServiceValidator;
        this.responseInfoFactory = responseInfoFactory;
        this.staffEnrichmentService = staffEnrichmentService;
        this.staffRepository = staffRepository;
        this.registerRepository = registerRepository;
        this.producer = producer;
        this.serviceConfiguration = serviceConfiguration;
        this.attendanceRegisterService = attendanceRegisterService;
        this.attendanceServiceValidator = attendanceServiceValidator;
    }


    /**
     * Create attendance staff
     *
     * @param staffPermissionRequest
     * @return
     */
    public StaffPermissionRequest createAttendanceStaff(StaffPermissionRequest staffPermissionRequest, Boolean cboMigrationReq) {
        //incoming createRequest validation
        log.info("Validating incoming staff request");
        staffServiceValidator.validateStaffPermissionRequestParameters(staffPermissionRequest);

        //extract registerIds and staffUserIds from client request
        String tenantId = staffPermissionRequest.getStaff().get(0).getTenantId();
        List<String> staffIds = extractStaffIdsFromRequest(staffPermissionRequest);
        List<String> registerIds = extractRegisterIdsFromRequest(staffPermissionRequest);

        //db call to get the staffList data whose de enrollment date is null
        List<StaffPermission> staffPermissionListFromDB = getActiveStaff(registerIds, staffIds, tenantId);

        //db call to get registers from db
        List<AttendanceRegister> attendanceRegisterListFromDB = getRegistersFromDB(staffPermissionRequest, registerIds, tenantId);

        //validate request registers with DB registers
        attendanceServiceValidator.validateRegisterAgainstDB(registerIds, attendanceRegisterListFromDB, tenantId);

        //validator call by passing staff request and the data from db call
        log.info("staffServiceValidator called to validate Create StaffPermission request");
        // When changing the CBO skip this validation
        if (!cboMigrationReq) {
            staffServiceValidator.validateStaffPermissionOnCreate(staffPermissionRequest, staffPermissionListFromDB, attendanceRegisterListFromDB);
        }

        //enrichment call by passing staff request and data from db call
        log.info("staffEnrichmentService called to enrich Create StaffPermission request");
        staffEnrichmentService.enrichStaffPermissionOnCreate(staffPermissionRequest);

        //push to producer
        log.info("staff objects pushed via producer");
        // Push the staff enrollment request to Kafka, scoped by tenant ID for multitenancy
        producer.push( tenantId, serviceConfiguration.getSaveStaffTopic(), staffPermissionRequest);
        log.info("staff present in Create StaffPermission request are enrolled to the register");
        return staffPermissionRequest;
    }

    public List<StaffPermission> getActiveStaff(List<String> registerIds, List<String> staffIds, String tenantId) {
        StaffSearchCriteria staffSearchCriteria = StaffSearchCriteria.builder().registerIds(registerIds).individualIds(staffIds).tenantId(tenantId).build();
        List<StaffPermission> staffPermissionListFromDB = staffRepository.getActiveStaff(staffSearchCriteria);
        log.info("size of active staffPermission List received From DB :" + staffPermissionListFromDB.size());
        return staffPermissionListFromDB;
    }

    public List<AttendanceRegister> getRegistersFromDB(StaffPermissionRequest staffPermissionRequest, List<String> registerIds, String tenantId) {
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(staffPermissionRequest.getRequestInfo()).build();
        List<AttendanceRegister> attendanceRegisterListFromDB = attendanceRegisterService.getAttendanceRegisters(requestInfoWrapper, registerIds, tenantId);
        log.info("size of Attendance Registers list received from DB : " + attendanceRegisterListFromDB.size());
        return attendanceRegisterListFromDB;
    }

    /**
     * Update(Soft Delete) the given attendance staff
     *
     * @param staffPermissionRequest
     * @return
     */
    public StaffPermissionRequest deleteAttendanceStaff(StaffPermissionRequest staffPermissionRequest) {
        //incoming deleteRequest validation
        log.info("Validating incoming staff request");
        staffServiceValidator.validateStaffPermissionRequestParameters(staffPermissionRequest);

        //extract registerIds and staffUserIds from client request
        String tenantId = staffPermissionRequest.getStaff().get(0).getTenantId();
        List<String> staffIds = extractStaffIdsFromRequest(staffPermissionRequest);
        List<String> registerIds = extractRegisterIdsFromRequest(staffPermissionRequest);

        //db call to get registers from db
        List<AttendanceRegister> attendanceRegisterListFromDB = getRegistersFromDB(staffPermissionRequest, registerIds, tenantId);


        //validate request registers against registers from DB
        log.info("Validating register ids from request against the DB");
        attendanceServiceValidator.validateRegisterAgainstDB(registerIds, attendanceRegisterListFromDB, tenantId);


        // db call to get staff data
        StaffSearchCriteria staffSearchCriteria = StaffSearchCriteria.builder().registerIds(registerIds).tenantId(tenantId).build();
        List<StaffPermission> staffPermissionListFromDB = staffRepository.getAllStaff(staffSearchCriteria);


        //validator call by passing staff request and the data from db call
        log.info("staffServiceValidator called to validate Delete StaffPermission request");
        staffServiceValidator.validateStaffPermissionOnDelete(staffPermissionRequest, staffPermissionListFromDB, attendanceRegisterListFromDB);

        log.info("staffEnrichmentService called to enrich Delete StaffPermission request");
        staffEnrichmentService.enrichStaffPermissionOnDelete(staffPermissionRequest, staffPermissionListFromDB);

        log.info("staff objects pushed via producer");
        // Push the staff de-enrollment (delete) request to the Kafka topic for the tenant
        producer.push(tenantId, serviceConfiguration.getUpdateStaffTopic(), staffPermissionRequest);
        log.info("staff present in Delete StaffPermission request are deenrolled from the register");
        return staffPermissionRequest;
    }

    private List<String> extractRegisterIdsFromRequest(StaffPermissionRequest staffPermissionRequest) {
        List<StaffPermission> staffPermissionListFromRequest = staffPermissionRequest.getStaff();
        List<String> registerIds = new ArrayList<>();
        for (StaffPermission staffPermission : staffPermissionListFromRequest) {
            registerIds.add(staffPermission.getRegisterId());
        }
        return registerIds;
    }

    private List<String> extractStaffIdsFromRequest(StaffPermissionRequest staffPermissionRequest) {
        List<StaffPermission> staffPermissionListFromRequest = staffPermissionRequest.getStaff();
        List<String> staffIds = new ArrayList<>();
        for (StaffPermission staffPermission : staffPermissionListFromRequest) {
            staffIds.add(staffPermission.getUserId());
        }
        return staffIds;
    }

    /**
     * Creates a map of attendance registerId and its first staff enrolled.
     *
     * @param tenantId
     * @param registerIds
     * @return
     */
    public Map<String, StaffPermission> fetchRegisterIdtoFirstStaffMap(String tenantId, List<String> registerIds) {
        Map<String, StaffPermission> registerIdToFirstStaffMap = new HashMap<>(); //mapping of registerId to the first StaffPermission for each unique registerId

        for ( String registerId  : registerIds) {
            if (!registerIdToFirstStaffMap.containsKey(registerId)) {
                StaffSearchCriteria staffSearchCriteria = StaffSearchCriteria.builder().tenantId(tenantId).registerIds(Collections.singletonList(registerId)).build();

                List<StaffPermission> staffPermissionList = staffRepository.getFirstStaff(staffSearchCriteria);

                if (!staffPermissionList.isEmpty()) {
                    registerIdToFirstStaffMap.put(registerId, staffPermissionList.get(0));
                }
            }
        }
        return registerIdToFirstStaffMap;
    }

    public Map<String, StaffPermission> fetchRegisterIdtoFirstOwnerStaffMap(String tenantId, List<String> registerIds) {
        Map<String, StaffPermission> registerIdToFirstStaffMap = new HashMap<>(); //mapping of registerId to the first StaffPermission for each unique registerId

        for ( String registerId  : registerIds) {
            if (!registerIdToFirstStaffMap.containsKey(registerId)) {
                StaffSearchCriteria staffSearchCriteria = StaffSearchCriteria.builder().tenantId(tenantId).registerIds(Collections.singletonList(registerId)).staffType("OWNER").build();

                List<StaffPermission> staffPermissionList = staffRepository.getFirstStaff(staffSearchCriteria);

                if (!staffPermissionList.isEmpty()) {
                    registerIdToFirstStaffMap.put(registerId, staffPermissionList.get(0));
                }
            }
        }
        return registerIdToFirstStaffMap;
    }

}
