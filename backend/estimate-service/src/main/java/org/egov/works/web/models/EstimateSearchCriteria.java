package org.egov.works.web.models;

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

    @JsonProperty("estimateDetailNumber")
    private String estimateDetailNumber = null;

    @JsonProperty("adminSanctionNumber")
    private String adminSanctionNumber = null;

    @JsonProperty("estimateNumber")//called as 'applicationNumber'
    private String estimateNumber = null;

    @JsonProperty("estimateStatus")
    private String estimateStatus = null;

    @JsonProperty("fromProposalDate")
    private BigDecimal fromProposalDate = null;

    @JsonProperty("toProposalDate")
    private BigDecimal toProposalDate = null;

    @JsonProperty("department")
    private String department = null;

    @JsonProperty("typeOfWork")
    private String typeOfWork = null;

//    @JsonProperty("estimateType")
//    private String estimateType = null;//what is the estimate type

    @JsonProperty("sortBy")
    private SortBy sortBy;

    @JsonProperty("sortOrder")
    private SortOrder sortOrder;

    public enum SortOrder {
        ASC,
        DESC
    }

    public enum SortBy {
        totalAmount,
        typeOfWork,
        department,
        proposalDate,
        estimateStatus,
        createdTime
    }


    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;
}
