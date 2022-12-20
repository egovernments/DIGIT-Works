package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.web.models.Status;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceRegisterSearchCriteria {

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("ids")
    private List<String> ids;

    @JsonProperty("registerNumber")
    private String registerNumber;

    @JsonProperty("name")
    private String name;

    @JsonProperty("fromDate")
    private Long fromDate;

    @JsonProperty("toDate")
    private Double toDate;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("attendeeId")
    private String attendeeId;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;

}
