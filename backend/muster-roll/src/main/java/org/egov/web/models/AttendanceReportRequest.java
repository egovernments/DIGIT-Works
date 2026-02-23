package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceReportRequest {
    @JsonProperty("requestInfo")
    @Valid
    @NotNull
    private RequestInfo requestInfo;

    @JsonProperty("musterRollId")
    @NotNull
    private String musterRollId;

    @JsonProperty("tenantId")
    @NotNull
    private String tenantId;
}
