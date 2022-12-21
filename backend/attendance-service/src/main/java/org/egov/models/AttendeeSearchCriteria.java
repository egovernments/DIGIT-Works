package org.egov.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendeeSearchCriteria {

    @JsonProperty("id")
    private List<String> ids;

    @JsonProperty("individualId")
    private String individualId;

    @JsonProperty("registerId")
    private String registerId;

    @JsonProperty("enrollmentDate")
    private Double enrollmentDate = null;

    @JsonProperty("denrollmentDate")
    private Double denrollmentDate = null;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;
}
