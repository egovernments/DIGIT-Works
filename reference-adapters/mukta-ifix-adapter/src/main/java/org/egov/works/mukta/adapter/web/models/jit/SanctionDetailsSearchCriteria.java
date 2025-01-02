package org.egov.works.mukta.adapter.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SanctionDetailsSearchCriteria {
    @JsonProperty("ids")
    private List<String> ids;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("hoaCode")
    private String hoaCode;

    @JsonProperty("ddoCode")
    private String ddoCode;

    @JsonProperty("masterAllotmentId")
    private String masterAllotmentId;

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
        lastModifiedTime
    }
}
