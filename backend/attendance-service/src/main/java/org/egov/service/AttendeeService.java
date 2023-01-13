package org.egov.service;

import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.enrichment.AttendeeEnrichmentService;
import org.egov.kafka.Producer;
import org.egov.web.models.AttendeeSearchCriteria;
import org.egov.repository.AttendeeRepository;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.AttendanceServiceValidator;
import org.egov.validator.AttendeeServiceValidator;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AttendeeService {
    @Autowired
    private AttendeeServiceValidator attendeeServiceValidator;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private AttendanceRegisterService attendanceRegisterService;

    @Autowired
    private AttendanceServiceValidator attendanceServiceValidator;

    @Autowired
    private AttendeeEnrichmentService attendeeEnrichmentService;

    @Autowired
    private AttendanceServiceConfiguration attendanceServiceConfiguration;

    @Autowired
    private Producer producer;


    /**
     * Create Attendee
     *
     * @param attendeeCreateRequest
     * @return
     */
    public AttendeeCreateRequest createAttendee(AttendeeCreateRequest attendeeCreateRequest) {
        //incoming createRequest validation
        attendeeServiceValidator.validateAttendeeCreateRequestParameters(attendeeCreateRequest);

        //extract registerIds and attendee IndividualIds from client request
        String tenantId = attendeeCreateRequest.getAttendees().get(0).getTenantId();
        List<String> attendeeIds = extractAttendeeIdsFromCreateRequest(attendeeCreateRequest);
        List<String> registerIds = extractRegisterIdsFromCreateRequest(attendeeCreateRequest);


        //db call to get the attendeeList data
        AttendeeSearchCriteria attendeeSearchCriteria = AttendeeSearchCriteria.builder().registerIds(registerIds).individualIds(attendeeIds).build();
        List<IndividualEntry> attendeeListFromDB = attendeeRepository.getAttendees(attendeeSearchCriteria);

        //db call to get registers from db and use them to validate request registers
        digit.models.coremodels.RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(attendeeCreateRequest.getRequestInfo()).build();
        List<AttendanceRegister> attendanceRegisterListFromDB = attendanceRegisterService.getAttendanceRegisters(requestInfoWrapper, registerIds, tenantId);
        attendanceServiceValidator.validateRegisterAgainstDB(registerIds, attendanceRegisterListFromDB, tenantId);


        //validator call by passing attendee request and the data from db call
        attendeeServiceValidator.validateCreateAttendee(attendeeCreateRequest, attendeeListFromDB, attendanceRegisterListFromDB);

        //enrichment call by passing attendee request and data from db call
        attendeeEnrichmentService.enrichAttendeeOnCreate(attendeeCreateRequest);

        //push to producer
        producer.push(attendanceServiceConfiguration.getSaveAttendeeTopic(), attendeeCreateRequest);
        return attendeeCreateRequest;
    }

    /**
     * Update(Soft Delete) the given attendee
     *
     * @param
     * @return
     */
    public AttendeeDeleteRequest deleteAttendee(AttendeeDeleteRequest attendeeDeleteRequest) {
        //incoming deleteRequest validation
        attendeeServiceValidator.validateAttendeeDeleteRequestParameters(attendeeDeleteRequest);

        //extract registerIds and attendee IndividualIds from client request
        String tenantId = attendeeDeleteRequest.getAttendees().get(0).getTenantId();
        List<String> attendeeIds = extractAttendeeIdsFromDeleteRequest(attendeeDeleteRequest);
        List<String> registerIds = extractRegisterIdsFromDeleteRequest(attendeeDeleteRequest);


        //db call to get the attendeeList data
        AttendeeSearchCriteria attendeeSearchCriteria = AttendeeSearchCriteria.builder().registerIds(registerIds).individualIds(attendeeIds).build();
        List<IndividualEntry> attendeeListFromDB = attendeeRepository.getAttendees(attendeeSearchCriteria);

        //db call to get registers from db and use them to validate request registers
        digit.models.coremodels.RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(attendeeDeleteRequest.getRequestInfo()).build();
        List<AttendanceRegister> attendanceRegisterListFromDB = attendanceRegisterService.getAttendanceRegisters(requestInfoWrapper, registerIds, tenantId);
        attendanceServiceValidator.validateRegisterAgainstDB(registerIds, attendanceRegisterListFromDB, tenantId);


        //validator call by passing attendee request and the data from db call
        attendeeServiceValidator.validateDeleteAttendee(attendeeDeleteRequest, attendeeListFromDB, attendanceRegisterListFromDB);

        //enrichment call by passing attendee request and data from db call
        attendeeEnrichmentService.enrichAttendeeOnDelete(attendeeDeleteRequest,attendeeListFromDB);

        //push to producer
        producer.push(attendanceServiceConfiguration.getUpdateAttendeeTopic(), attendeeDeleteRequest);
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
