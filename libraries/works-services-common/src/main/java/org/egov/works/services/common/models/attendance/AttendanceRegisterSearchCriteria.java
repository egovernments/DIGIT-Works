package org.egov.works.services.common.models.attendance;

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

    @JsonProperty("referenceIds")
    private String referenceIds;

    @JsonProperty("serviceCode")
    private String serviceCode;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("sortBy")
    private SortBy sortBy;

    @JsonProperty("sortOrder")
    private SortOrder sortOrder;

    @JsonProperty("localityCodes")
    private List<String> localityCode;

    @JsonProperty("isChildrenRequired")
    private boolean isChildrenRequired;

    @JsonProperty("wfStatus")
    private WfStatus wfStatus;

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
