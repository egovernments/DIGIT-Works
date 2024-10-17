package org.egov.works.services.common.models.expense.calculator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.models.AuditDetails;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceEntry {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("time")
    private BigDecimal time = null;

    @JsonProperty("attendance")
    private BigDecimal attendance = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;
}

