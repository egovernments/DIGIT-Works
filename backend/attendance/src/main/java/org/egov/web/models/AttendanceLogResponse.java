package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * AttendanceLogResponse
 */
@Validated

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceLogResponse {
    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo = null;

    @JsonProperty("attendance")
    @Valid
    private List<AttendanceLog> attendance = null;


    public AttendanceLogResponse addAttendanceItem(AttendanceLog attendanceItem) {
        if (this.attendance == null) {
            this.attendance = new ArrayList<>();
        }
        this.attendance.add(attendanceItem);
        return this;
    }

}

