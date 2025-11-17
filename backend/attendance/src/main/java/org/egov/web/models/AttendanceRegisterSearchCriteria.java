package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
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
    private BigDecimal fromDate;

    @JsonProperty("toDate")
    private BigDecimal toDate;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("attendeeId")
    private String attendeeId;

    @JsonProperty("staffId")
    private String staffId;

    @JsonProperty("referenceId")
    private String referenceId;

    @JsonProperty("serviceCode")
    private String serviceCode;

    @JsonProperty("referenceIds")
    private List<String> referenceIds;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("sortBy")
    private SortBy sortBy;

    @JsonProperty("sortOrder")
    private SortOrder sortOrder;

    @JsonProperty("localityCode")
    private String localityCode;

    @JsonProperty("reviewStatus")
    private String reviewStatus;

    @JsonProperty("isChildrenRequired")
    private Boolean isChildrenRequired;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("includeTaggedAttendees")
    private Boolean includeTaggedAttendees =  Boolean.FALSE;

    /**
     * V2 Intermediate Billing - Billing Period Filter
     * When provided, the search will:
     * 1. Filter registers that overlap with the billing period dates
     * 2. Enrich each register with registerPeriodStatus (muster roll status for that period)
     */
    @JsonProperty("billingPeriodId")
    private String billingPeriodId;

    /**
     * V2 Intermediate Billing - Register Period Status Filter
     * When provided along with billingPeriodId, the search will:
     * 1. Filter registers by their muster roll status for the billing period
     * 2. Count registers by registerPeriodStatus values
     *
     * Note: This filter only works when billingPeriodId is provided.
     * If used without billingPeriodId, validation error will be thrown.
     *
     * Possible values:
     * - "NOT_CREATED": No muster roll exists for register+period
     * - "PENDING": Muster roll created but awaiting approval
     * - "APPROVED": Muster roll approved
     * - "REJECTED": Muster roll rejected
     * - "SENT_BACK": Muster roll sent back for corrections
     */
    @JsonProperty("registerPeriodStatus")
    private String registerPeriodStatus;

    public enum SortOrder {
        ASC,
        DESC
    }

    public enum SortBy {
        lastModifiedTime,
        fromDate,
        toDate
    }

}
