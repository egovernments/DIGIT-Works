package org.egov.service;

import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.enrichment.AttendeeEnrichmentService;
import org.egov.common.producer.Producer;
import org.egov.repository.AttendeeRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.AttendanceServiceValidator;
import org.egov.validator.AttendeeServiceValidator;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AttendeeService {
    private final AttendeeServiceValidator attendeeServiceValidator;

    private final ResponseInfoFactory responseInfoFactory;

    private final AttendeeRepository attendeeRepository;

    private final AttendanceRegisterService attendanceRegisterService;

    private final AttendanceServiceValidator attendanceServiceValidator;

    private final AttendeeEnrichmentService attendeeEnrichmentService;

    private final AttendanceServiceConfiguration attendanceServiceConfiguration;

    private final Producer producer;

    @Autowired
    public AttendeeService(AttendeeServiceValidator attendeeServiceValidator, ResponseInfoFactory responseInfoFactory, AttendeeRepository attendeeRepository, AttendanceRegisterService attendanceRegisterService, AttendanceServiceValidator attendanceServiceValidator, AttendeeEnrichmentService attendeeEnrichmentService, AttendanceServiceConfiguration attendanceServiceConfiguration, Producer producer) {
        this.attendeeServiceValidator = attendeeServiceValidator;
        this.responseInfoFactory = responseInfoFactory;
        this.attendeeRepository = attendeeRepository;
        this.attendanceRegisterService = attendanceRegisterService;
        this.attendanceServiceValidator = attendanceServiceValidator;
        this.attendeeEnrichmentService = attendeeEnrichmentService;
        this.attendanceServiceConfiguration = attendanceServiceConfiguration;
        this.producer = producer;
    }

    /* * Update Attendee Tag
     *
     * @param request
     * @return
     */
    public AttendeeUpdateTagRequest updateAttendeeTag(AttendeeUpdateTagRequest attendeeUpdateTagRequest) {

        List<IndividualEntry> attendees = attendeeUpdateTagRequest.getAttendees();
        // Incoming request validation
        log.info("validating update attendee tag request parameters");
        attendeeServiceValidator.validateAttendeeUpdateTagRequestParameters(attendeeUpdateTagRequest);


        String tenantId = attendees.get(0).getTenantId();
        // Fetch current attendee records from DB
        List<String> ids = attendees.stream().map(IndividualEntry::getId).collect(Collectors.toList());
        List<IndividualEntry> attendeeListFromDB = getAttendeesByIds(ids, tenantId);

        // Validate that all requested attendees exist in the DB
        attendeeServiceValidator.validateAllAttendeesExist(attendees, attendeeListFromDB);

       //enrichment call by passing attendee request and data from db call
        attendeeEnrichmentService.enrichAttendeesForTagUpdate(attendeeUpdateTagRequest, attendeeListFromDB);

        //push to producer
        log.info("attendee objects pushed via producer");
        producer.push(tenantId, attendanceServiceConfiguration.getUpdateAttendeeTopic(), attendeeUpdateTagRequest);

        log.info("attendees present in update attendee tag request are updated with new tags");
        return attendeeUpdateTagRequest;
    }

    private  List<IndividualEntry> getAttendeesByIds(List<String> ids, String tenantId) {
        AttendeeSearchCriteria criteria = AttendeeSearchCriteria.builder()
                .ids(ids)
                .tenantId(tenantId)
                .build();
        return attendeeRepository.getAttendees(tenantId, criteria);
    }


    /**
     * Create Attendee
     *
     * @param attendeeCreateRequest
     * @return
     */
    public AttendeeCreateRequest createAttendee(AttendeeCreateRequest attendeeCreateRequest) {
        //incoming createRequest validation
        log.info("validating create attendee request parameters");
        attendeeServiceValidator.validateAttendeeCreateRequestParameters(attendeeCreateRequest);

        // Check if the configuration for "Register First Staff Insert" is disabled
        if (!attendanceServiceConfiguration.getRegisterFirstStaffInsertEnabled()) {
            // Validate whether the attendees are project staff and whether they have the correct reporting staff
            attendeeServiceValidator.validateAttendeeDetails(attendeeCreateRequest);

            // Check if the attendee list is empty after validation
            if (attendeeCreateRequest.getAttendees().isEmpty()) {
                // Throw a custom exception if no valid attendees are found in the request
                throw new CustomException("NO_VALID_ATTENDEES", "No valid attendees provided in this request.");
            }
        }

        //extract registerIds and attendee IndividualIds from client request
        String tenantId = attendeeCreateRequest.getAttendees().get(0).getTenantId();
        List<String> attendeeIds = extractAttendeeIdsFromCreateRequest(attendeeCreateRequest);
        List<String> registerIds = extractRegisterIdsFromCreateRequest(attendeeCreateRequest);

        //db call to get the attendeeList data
        List<IndividualEntry> attendeeListFromDB = getAttendees(tenantId, registerIds,attendeeIds);

        //db call to get registers from db
        List<AttendanceRegister> attendanceRegisterListFromDB = getAttendanceRegisters(attendeeCreateRequest,registerIds,tenantId);

        //validate registers from request with registers from DB
        log.info("validating register ids from request against DB");
        attendanceServiceValidator.validateRegisterAgainstDB(registerIds, attendanceRegisterListFromDB, tenantId);


        //validator call by passing attendee request and the data from db call
        log.info("attendeeServiceValidator called to validate Create attendee request");
        attendeeServiceValidator.validateAttendeeOnCreate(attendeeCreateRequest, attendeeListFromDB, attendanceRegisterListFromDB);

        //enrichment call by passing attendee request and data from db call
        log.info("attendeeServiceValidator called to enrich Create attendee request");
        attendeeEnrichmentService.enrichAttendeeOnCreate(attendeeCreateRequest);

        //push to producer
        log.info("attendee objects pushed via producer");
        // Push enriched create request to Kafka using tenant-specific topic
        producer.push(tenantId, attendanceServiceConfiguration.getSaveAttendeeTopic(), attendeeCreateRequest);
        log.info("attendees present in Create attendee request are enrolled to the registers");
        return attendeeCreateRequest;
    }

    public List<IndividualEntry> getAttendees(String tenantId, List<String> registerIds,List<String> attendeeIds){
        // Build search criteria using tenantId for schema-aware attendee fetch
        AttendeeSearchCriteria attendeeSearchCriteria = AttendeeSearchCriteria.builder().registerIds(registerIds).tenantId(tenantId).individualIds(attendeeIds).build();
        // Use tenant-aware repository method to retrieve matching attendees
        List<IndividualEntry> attendeeListFromDB = attendeeRepository.getAttendees(tenantId, attendeeSearchCriteria);
        log.info("attendee List received From DB : " + attendeeListFromDB.size());
        return attendeeListFromDB;
    }

    public List<AttendanceRegister> getAttendanceRegisters(AttendeeCreateRequest attendeeCreateRequest,List<String> registerIds,String tenantId){
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(attendeeCreateRequest.getRequestInfo()).build();
        List<AttendanceRegister> attendanceRegisterListFromDB = attendanceRegisterService.getAttendanceRegisters(requestInfoWrapper, registerIds, tenantId);
        log.info("attendance register List received From DB : " + attendanceRegisterListFromDB.size());
        return attendanceRegisterListFromDB;
    }

    public List<AttendanceRegister> getAttendanceRegisters(AttendeeDeleteRequest attendeeDeleteRequest,List<String> registerIds,String tenantId){
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(attendeeDeleteRequest.getRequestInfo()).build();
        List<AttendanceRegister> attendanceRegisterListFromDB = attendanceRegisterService.getAttendanceRegisters(requestInfoWrapper, registerIds, tenantId);
        log.info("attendance register List received From DB : " + attendanceRegisterListFromDB.size());
        return attendanceRegisterListFromDB;
    }

    /**
     * Update(Soft Delete) the given attendee
     *
     * @param
     * @return
     */
    public AttendeeDeleteRequest deleteAttendee(AttendeeDeleteRequest attendeeDeleteRequest) {
        //incoming deleteRequest validation
        log.info("validating delete attendee request parameters");
        Map<String, String> errorMap = new HashMap<>();
        attendeeServiceValidator.validateAttendeeDeleteRequestParameters(attendeeDeleteRequest, errorMap);

        //extract registerIds and attendee IndividualIds from client request
        String tenantId = attendeeDeleteRequest.getAttendees().get(0).getTenantId();
        List<String> attendeeIds = extractAttendeeIdsFromDeleteRequest(attendeeDeleteRequest);
        List<String> registerIds = extractRegisterIdsFromDeleteRequest(attendeeDeleteRequest);

        //db call to get the attendeeList data
        List<IndividualEntry> attendeeListFromDB = getAttendees(tenantId, registerIds,attendeeIds);

        //db call to get registers from db
        List<AttendanceRegister> attendanceRegisterListFromDB = getAttendanceRegisters(attendeeDeleteRequest,registerIds,tenantId);

        //validate request registers with registers from DB
        log.info("validating register ids from request against DB");
        attendanceServiceValidator.validateRegisterAgainstDB(registerIds, attendanceRegisterListFromDB, tenantId);


        //validator call by passing attendee request and the data from db call
        log.info("validating delete attendee request");
        attendeeServiceValidator.validateAttendeeOnDelete(attendeeDeleteRequest, attendeeListFromDB, attendanceRegisterListFromDB);

        //enrichment call by passing attendee request and data from db call
        log.info("enriching delete attendee request");
        attendeeEnrichmentService.enrichAttendeeOnDelete(attendeeDeleteRequest, attendeeListFromDB);

        //push to producer
        log.info("attendee objects updated via producer");
        // Push delete (de-enroll) request to Kafka with schema-aware tenant scoping
        producer.push(tenantId, attendanceServiceConfiguration.getUpdateAttendeeTopic(), attendeeDeleteRequest);
        log.info("attendees present in delete attendee request are deenrolled from the registers");
        return attendeeDeleteRequest;
    }


    private List<String> extractRegisterIdsFromCreateRequest(AttendeeCreateRequest attendeeCreateRequest) {
        List<IndividualEntry> attendeeListFromRequest = attendeeCreateRequest.getAttendees();
        List<String> registerIds = new ArrayList<>();
        for (IndividualEntry attendee : attendeeListFromRequest) {
            registerIds.add(attendee.getRegisterId());
        }
        return registerIds;
    }

    private List<String> extractAttendeeIdsFromCreateRequest(AttendeeCreateRequest attendeeCreateRequest) {
        List<IndividualEntry> attendeeListFromRequest = attendeeCreateRequest.getAttendees();
        List<String> attendeeIds = new ArrayList<>();
        for (IndividualEntry attendee : attendeeListFromRequest) {
            attendeeIds.add(attendee.getIndividualId());
        }
        return attendeeIds;
    }

    private List<String> extractRegisterIdsFromDeleteRequest(AttendeeDeleteRequest attendeeDeleteRequest) {
        List<IndividualEntry> attendeeListFromRequest = attendeeDeleteRequest.getAttendees();
        List<String> registerIds = new ArrayList<>();
        for (IndividualEntry attendee : attendeeListFromRequest) {
            registerIds.add(attendee.getRegisterId());
        }
        return registerIds;
    }

    private List<String> extractAttendeeIdsFromDeleteRequest(AttendeeDeleteRequest attendeeDeleteRequest) {
        List<IndividualEntry> attendeeListFromRequest = attendeeDeleteRequest.getAttendees();
        List<String> attendeeIds = new ArrayList<>();
        for (IndividualEntry attendee : attendeeListFromRequest) {
            attendeeIds.add(attendee.getIndividualId());
        }
        return attendeeIds;
    }
}
