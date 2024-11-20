package org.egov.works.services.common.models.attendance;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import org.egov.common.contract.response.ResponseInfo;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendeeCreateResponse {
    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo = null;

    @JsonProperty("attendees")
    @Valid
    private List<IndividualEntry> attendees = null;


    public AttendeeCreateResponse addAttendeesItem(IndividualEntry attendeesItem) {
        if (this.attendees == null) {
            this.attendees = new ArrayList<>();
        }
        this.attendees.add(attendeesItem);
        return this;
    }

}

