package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendeeSearchCriteria {

    @JsonProperty("ids")
    private List<String> ids;

    @JsonProperty("individualIds")
    private List<String> individualIds;

    @JsonProperty("registerIds")
    private List<String> registerIds;

    @JsonProperty("enrollmentDate")
    private BigDecimal enrollmentDate = null;

    @JsonProperty("denrollmentDate")
    private BigDecimal denrollmentDate = null;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("tags")
    private List<String> tags;

    @NotNull
    @JsonProperty("tenantId")
    private String tenantId;

}
