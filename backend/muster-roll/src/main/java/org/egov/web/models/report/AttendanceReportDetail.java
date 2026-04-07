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

    @JsonProperty("registerName")
    private String registerName;

    @JsonProperty("registerNumber")
    private String registerNumber;

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

    @JsonProperty("loginId")
    private String loginId;

    @JsonProperty("enrollmentDate")
    private Long enrollmentDate;

    @JsonProperty("deEnrollmentDate")
    private Long deEnrollmentDate;

    @JsonProperty("attendanceMarker")
    private String attendanceMarker;

    @JsonProperty("presentDaysOriginal")
    private Double presentDaysOriginal;

    @JsonProperty("presentDaysModified")
    private Double presentDaysModified;

    @JsonProperty("dailyAttendance")
    private Map<String, String> dailyAttendance;

    @JsonProperty("totalPerformance")
    private Long totalPerformance;

    @JsonProperty("additionalDetails")
    private Map<String, Object> additionalDetails;

    // Key: dateStr (e.g. "25/02/2026"), Value: [morningFileStoreId, eveningFileStoreId] (null = NA)
    @JsonProperty("dailySignatureIds")
    private Map<String, String[]> dailySignatureIds;

    @JsonProperty("baseSignatureFileStoreId")
    private String baseSignatureFileStoreId;

    // Key: dateStr, Value: [morningStatus, eveningStatus] ("PRESENT"/"ABSENT")
    @JsonProperty("dailySessionAttendance")
    private Map<String, String[]> dailySessionAttendance;
}
