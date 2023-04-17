package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusterRollSearchCriteria {

    @JsonProperty("ids")
    private List<String> ids;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("musterRollNumber")
    private String musterRollNumber;

    @JsonProperty("registerId")
    private String registerId;

    @JsonProperty("fromDate")
    private BigDecimal fromDate;

    @JsonProperty("toDate")
    private BigDecimal toDate;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("musterRollStatus")
    private String musterRollStatus;

    @JsonProperty("referenceId")
    private String referenceId = null;

    @JsonProperty("serviceCode")
    private String serviceCode = null;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;

}