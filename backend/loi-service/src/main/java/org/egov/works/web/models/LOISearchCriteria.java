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
public class LOISearchCriteria {

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("ids")
    private List<String> ids;

    @JsonProperty("letterOfIndentNumber")
    private String letterOfIndentNumber = null;

    @JsonProperty("workPackageNumber")
    private String workPackageNumber = null;

    @JsonProperty("letterStatus")
    private String letterStatus = null;

    @JsonProperty("contractorid")
    private String contractorid = null;

    @JsonProperty("fromAgreementDate")
    private BigDecimal fromAgreementDate = null;

    @JsonProperty("toAgreementDate")
    private BigDecimal toAgreementDate = null;

    @JsonProperty("sortBy")
    private SortBy sortBy;

    @JsonProperty("sortOrder")
    private SortOrder sortOrder;

    public enum SortOrder {
        ASC,
        DESC
    }

    public enum SortBy {
        defectLiabilityPeriod,
        contractPeriod,
        emdAmount,
        agreementDate,
        letterStatus,
        createdTime
    }

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;
}
