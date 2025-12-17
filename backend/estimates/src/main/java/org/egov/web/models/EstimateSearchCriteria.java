package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstimateSearchCriteria {

    @JsonProperty("ids")
    @Size(max = 10)
    private List<String> ids;

    @JsonProperty("tenantId")
    @NotNull
    private String tenantId = null;

    @JsonProperty("estimateNumber")
    private String estimateNumber = null;

    @JsonProperty("revisionNumber")
    @Size(min = 2,max = 64)
    private String revisionNumber = null;

    @JsonProperty("businessService")
    @Size(min = 2,max = 64)
    private String businessService = null;

    @JsonProperty("versionNumber")
    private BigDecimal versionNumber = null;

    @JsonProperty("oldUuid")
    @Size(min = 2,max = 64)
    private String oldUuid = null;

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

    @JsonProperty("status")
    private String status = null;


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
