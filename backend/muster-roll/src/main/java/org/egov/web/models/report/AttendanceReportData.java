package org.egov.web.models.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceReportData {
    @JsonProperty("musterRollNumber")
    private String musterRollNumber;

    @JsonProperty("registerNumber")
    private String registerNumber;

    @JsonProperty("musterRollId")
    private String musterRollId;

    @JsonProperty("campaignName")
    private String campaignName;

    @JsonProperty("campaignCode")
    private String campaignCode;

    @JsonProperty("startDate")
    private Long startDate;

    @JsonProperty("endDate")
    private Long endDate;

    @JsonProperty("totalAttendees")
    private Integer totalAttendees;

    @JsonProperty("totalDays")
    private Integer totalDays;

    @JsonProperty("attendanceDetails")
    private List<AttendanceReportDetail> attendanceDetails;

    @JsonProperty("campaignDates")
    private List<Long> campaignDates;

    @JsonProperty("additionalMetadata")
    private Map<String, Object> additionalMetadata;

    // 1 = morning only, 2 = morning + evening
    @JsonProperty("sessions")
    private int sessions;
}
