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

