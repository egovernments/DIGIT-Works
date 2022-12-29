package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.AttendanceServiceValidator;
import org.egov.validator.AttendeeServiceValidator;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class AttendeeService {
    @Autowired
    private AttendeeServiceValidator attendeeServiceValidator;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    /**
     * Create Attendee
     *
     * @param attendeeCreateRequest
     * @return
     */
    public AttendeeCreateResponse createAttendee(AttendeeCreateRequest attendeeCreateRequest) {
        //TODO Returning Dummy Response
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(attendeeCreateRequest.getRequestInfo(), true);
        AttendeeCreateResponse attendeeCreateResponse = AttendeeCreateResponse.builder().responseInfo(responseInfo).attendees(attendeeCreateRequest.getAttendees()).build();
        return attendeeCreateResponse;
    }

    /**
     * Update(Soft Delete) the given attendee
     *
     * @param attendeeDeleteRequest
     * @return
     */
    public AttendeeDeleteResponse deleteAttendee(AttendeeDeleteRequest attendeeDeleteRequest) {
        //TODO Returning Dummy Response
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(attendeeDeleteRequest.getRequestInfo(), true);
        AttendeeDeleteResponse attendeeDeleteResponse = AttendeeDeleteResponse.builder().responseInfo(responseInfo).attendees(attendeeDeleteRequest.getAttendees()).build();
        return attendeeDeleteResponse;
    }
}
