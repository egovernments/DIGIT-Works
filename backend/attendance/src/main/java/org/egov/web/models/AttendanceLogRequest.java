package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * AttendanceLogRequest
 */
@Validated

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceLogRequest {
    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo = null;

    @JsonProperty("attendance")
    @Valid
    private List<AttendanceLog> attendance = null;


    public AttendanceLogRequest addAttendanceItem(AttendanceLog attendanceItem) {
        if (this.attendance == null) {
            this.attendance = new ArrayList<>();
        }
        this.attendance.add(attendanceItem);
        return this;
    }

}

