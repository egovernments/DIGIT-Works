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
public class FaceAuthEventSearchCriteria {

    @JsonProperty("ids")
    private List<String> ids;

    @JsonProperty("clientReferenceId")
    private List<String> clientReferenceId;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("individualIds")
    private List<String> individualIds;

    @JsonProperty("eventType")
    private String eventType;

    @JsonProperty("outcome")
    private String outcome;

    @JsonProperty("projectId")
    private String projectId;

    @JsonProperty("fromTime")
    private BigDecimal fromTime;

    @JsonProperty("toTime")
    private BigDecimal toTime;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("sortBy")
    private SortBy sortBy;

    @JsonProperty("sortOrder")
    private SortOrder sortOrder;

    public enum SortOrder {
        ASC,
        DESC
    }

    public enum SortBy {
        lastModifiedTime,
        timestamp
    }
}
