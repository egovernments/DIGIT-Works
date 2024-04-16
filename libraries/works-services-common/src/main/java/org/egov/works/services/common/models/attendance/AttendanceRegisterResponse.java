package org.egov.works.services.common.models.attendance;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import org.egov.common.contract.response.ResponseInfo;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceRegisterResponse {
    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo = null;

    @JsonProperty("attendanceRegister")
    @Valid
    private List<AttendanceRegister> attendanceRegister = null;


    public AttendanceRegisterResponse addAttendanceRegisterItem(AttendanceRegister attendanceRegisterItem) {
        if (this.attendanceRegister == null) {
            this.attendanceRegister = new ArrayList<>();
        }
        this.attendanceRegister.add(attendanceRegisterItem);
        return this;
    }

}

