package org.egov.works.mukta.adapter.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.works.mukta.adapter.web.models.enums.PIStatus;
import org.egov.works.mukta.adapter.web.models.enums.PIType;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PISearchCriteria {

    @JsonProperty("ids")
    private List<String> ids;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("piStatus")
    private PIStatus piStatus;

    @JsonProperty("muktaReferenceId")
    private String muktaReferenceId;

    @JsonProperty("piNumber")
    private String jitBillNo;

    @JsonProperty("parentPiNumber")
    private String parentPiNumber;

    @JsonProperty("isActive")
    private Boolean isActive;

    @JsonProperty("piType")
    private PIType piType;

    @JsonProperty("piNumbers")
    private Set<String> jitBillNumbers;

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
        createdTime
    }
}
