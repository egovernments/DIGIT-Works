package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusterRollSearchCriteria {

    @JsonProperty("id")
    private String id;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("musterRollNumber")
    private String musterRollNumber;

    @JsonProperty("registerId")
    private String registerId;

    @JsonProperty("fromDate")
    private Long fromDate;

    @JsonProperty("toDate")
    private Double toDate;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("musterRollStatus")
    private String musterRollStatus;


}
