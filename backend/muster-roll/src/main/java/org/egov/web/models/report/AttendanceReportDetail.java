package org.egov.web.models.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceReportDetail {
    @JsonProperty("serialNumber")
    private Integer serialNumber;

    @JsonProperty("individualId")
    private String individualId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("role")
    private String role;

    @JsonProperty("teamCode")
    private String teamCode;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("enrollmentDate")
    private Long enrollmentDate;

    @JsonProperty("deEnrollmentDate")
    private Long deEnrollmentDate;

    @JsonProperty("attendanceMarker")
    private String attendanceMarker;

    @JsonProperty("presentDaysOriginal")
    private Integer presentDaysOriginal;

    @JsonProperty("presentDaysModified")
    private Integer presentDaysModified;

    @JsonProperty("dailyAttendance")
    private Map<String, String> dailyAttendance;

    @JsonProperty("additionalDetails")
    private Map<String, Object> additionalDetails;
}
