package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Size;
import org.egov.common.contract.models.AuditDetails;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import jakarta.validation.constraints.Min;

/**
 * IndividualEntry
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-11-14T19:58:09.415+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndividualEntry {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("individualId")
    private String individualId = null;

    @JsonProperty("musterRollId")
    private String musterRollId = null;

    @JsonProperty("enrollmentDate")
    private BigDecimal enrollmentDate = null;

    @JsonProperty("denrollmentDate")
    private BigDecimal denrollmentDate = null;

    @JsonProperty("actualTotalAttendance")
    @Min(0)
    private BigDecimal actualTotalAttendance = null;

    @JsonProperty("totalRegistrations")
    @Min(0)
    private Long totalRegistrations = null;

    @JsonProperty("totalInterventions")
    @Min(0)
    private Long totalInterventions = null;

    @JsonProperty("modifiedTotalAttendance")
    @Min(0)
    private BigDecimal modifiedTotalAttendance = null;

    @JsonProperty("attendanceEntries")
    @Valid
    private List<AttendanceEntry> attendanceEntries = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

    @Size(max=64)
    @JsonProperty("tag")
    private String tag = null; // represent teamcode to group attendees


    public IndividualEntry addAttendanceEntriesItem(AttendanceEntry attendanceEntriesItem) {
        if (this.attendanceEntries == null) {
            this.attendanceEntries = new ArrayList<>();
        }
        this.attendanceEntries.add(attendanceEntriesItem);
        return this;
    }

}

