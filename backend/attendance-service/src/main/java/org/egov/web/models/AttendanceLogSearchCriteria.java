package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceLogSearchCriteria {

    @JsonProperty("id")
    private List<String> ids;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("registerId")
    private String registerId;

    @JsonProperty("fromTime")
    private Long fromTime;

    @JsonProperty("toTime")
    private Double toTime;

    @JsonProperty("individualIds")
    private List<String> individualIds;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;

}
