package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * AttendanceRegister
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-11-14T14:44:21.051+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceRegister {
    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("registerNumber")
    private String registerNumber = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("startDate")
    private Double startDate = null;

    @JsonProperty("endDate")
    private Double endDate = null;

    @JsonProperty("status")
    private Status status = null;

    @JsonProperty("staff")
    @Valid
    private List<StaffPermission> staff = null;

    @JsonProperty("attendees")
    @Valid
    private List<IndividualEntry> attendees = null;

    @JsonProperty("auditDetails")
    private Object auditDetails = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;


    public AttendanceRegister addStaffItem(StaffPermission staffItem) {
        if (this.staff == null) {
            this.staff = new ArrayList<>();
        }
        this.staff.add(staffItem);
        return this;
    }

    public AttendanceRegister addAttendeesItem(IndividualEntry attendeesItem) {
        if (this.attendees == null) {
            this.attendees = new ArrayList<>();
        }
        this.attendees.add(attendeesItem);
        return this;
    }

}

