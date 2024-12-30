package org.egov.works.services.common.models.attendance;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import org.egov.common.contract.response.ResponseInfo;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @JsonProperty("totalCount")
    private long totalCount = 0;

    @JsonProperty("statusCount")
    private Map<String, Long> statusCount = new HashMap<>();


    public AttendanceRegisterResponse addAttendanceRegisterItem(AttendanceRegister attendanceRegisterItem) {
        if (this.attendanceRegister == null) {
            this.attendanceRegister = new ArrayList<>();
        }
        this.attendanceRegister.add(attendanceRegisterItem);
        return this;
    }

}

