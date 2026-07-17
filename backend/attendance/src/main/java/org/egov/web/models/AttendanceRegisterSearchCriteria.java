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

    @JsonProperty("staffName")
    private String staffName;

    @JsonProperty("staffTypes")
    private List<String> staffTypes;

    @JsonProperty("referenceId")
    private String referenceId;

    @JsonProperty("serviceCode")
    private String serviceCode;

    @JsonProperty("isServiceCodeExact")
    private Boolean isServiceCodeExact = Boolean.TRUE;

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

    @JsonProperty("campaignNumber")
    private String campaignNumber;

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
     * When provided (and reviewStatus is NOT provided), the search will:
     * 1. Filter registers that overlap with the billing period dates
     * 2. Enrich each register with registerPeriodStatus (muster roll status for that period)
     * 3. Map registerPeriodStatus to V1 status format for consistent response
     *
     * IMPORTANT: If reviewStatus is provided, V1 logic takes priority and this parameter is ignored.
     */
    @JsonProperty("billingPeriodId")
    private String billingPeriodId;

    /**
     * V2 Intermediate Billing - Register Period Status Filter
     * When provided along with billingPeriodId (and reviewStatus is NOT provided), the search will:
     * 1. Filter registers by their muster roll status for the billing period
     * 2. Return statusCount with APPROVED and PENDING counts
     *
     * Valid Values (only 2 values accepted):
     * - "APPROVED": Show only registers with approved muster rolls
     * - "PENDING": Show registers with pending muster rolls
     *   (includes NOT_CREATED, PENDING, SENT_BACK, REJECTED muster roll statuses)
     *
     * Response will always contain statusCount with APPROVED and PENDING (0 if none found).
     *
     * Validation Rules:
     * - Must be either "APPROVED" or "PENDING" (throws error if invalid value)
     * - Requires billingPeriodId to be provided (throws error if missing)
     * - Ignored if reviewStatus is provided (V1 logic takes priority)
     *
     * Internal Mapping (how muster roll statuses map to this field):
     * - Muster roll status "APPROVED" → APPROVED
     * - Muster roll statuses "NOT_CREATED", "PENDING", "SENT_BACK", "REJECTED" → PENDING
     */
    @JsonProperty("registerPeriodStatus")
    private String registerPeriodStatus;

    @JsonProperty("includeStaff")
    private Boolean includeStaff = Boolean.TRUE;

    @JsonProperty("includeAttendee")
    private Boolean includeAttendee = Boolean.TRUE;

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
