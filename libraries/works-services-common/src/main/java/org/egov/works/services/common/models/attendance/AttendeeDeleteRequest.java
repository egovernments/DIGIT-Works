package org.egov.works.services.common.models.attendance;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import org.egov.common.contract.request.RequestInfo;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * AttendeeDeleteRequest
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendeeDeleteRequest {
    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo = null;

    @JsonProperty("attendees")
    @Valid
    private List<IndividualEntry> attendees = null;

    public AttendeeDeleteRequest addAttendeesItem(IndividualEntry attendeesItem) {
        if (this.attendees == null) {
            this.attendees = new ArrayList<>();
        }
        this.attendees.add(attendeesItem);
        return this;
    }

}

