package org.egov.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceStaffSearchCriteria {
    @JsonProperty("individualIds")
    private List<String> individualIds;

    @JsonProperty("registerIds")
    private List<String> registerIds;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;
}
