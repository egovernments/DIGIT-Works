package org.egov.web.models;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttendanceLogSearchRequest {

    @JsonProperty("RequestInfo")
    @NotNull
    @Valid
    private RequestInfo requestInfo = null;

    @JsonProperty("attendance")
    @Valid
    private AttendanceLogSearchCriteria attendanceLogSearchCriteria = null;
}
