package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstimateSearchCriteria {

    @JsonProperty("ids")
    private List<String> ids;

    @JsonProperty("tenantId")
    private String tenantId = null;//mand

    @JsonProperty("estimateNumber")
    private String estimateNumber = null;

    @JsonProperty("projectId")
    private String projectId = null;

    @JsonProperty("referenceNumber")
    private String referenceNumber = null;

    @JsonProperty("wfStatus")
    private String wfStatus = null;

    @JsonProperty("fromProposalDate")
    private BigDecimal fromProposalDate = null;

    @JsonProperty("toProposalDate")
    private BigDecimal toProposalDate = null;

    @JsonProperty("executingDepartment")
    private String executingDepartment = null;

    @JsonProperty("sortBy")
    private SortBy sortBy;

    @JsonProperty("sortOrder")
    private SortOrder sortOrder;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;

    @JsonIgnore
    private Boolean isCountNeeded = false;


    public enum SortOrder {
        ASC,
        DESC
    }

    public enum SortBy {
        createdTime,
        executingDepartment,
        proposalDate,
        wfStatus,
        totalAmount
    }
}
