package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.models.AuditDetails;
import org.egov.works.services.common.models.expense.calculator.AttendanceEntry;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
public class IndividualEntry {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("individualId")
    private String individualId = null;

    @JsonProperty("actualTotalAttendance")
    private BigDecimal actualTotalAttendance = null;

    @JsonProperty("modifiedTotalAttendance")
    private BigDecimal modifiedTotalAttendance = null;

    @JsonProperty("attendanceEntries")
    @Valid
    private List<AttendanceEntry> attendanceEntries = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;


    public IndividualEntry addAttendanceEntriesItem(AttendanceEntry attendanceEntriesItem) {
        if (this.attendanceEntries == null) {
            this.attendanceEntries = new ArrayList<>();
        }
        this.attendanceEntries.add(attendanceEntriesItem);
        return this;
    }

}

