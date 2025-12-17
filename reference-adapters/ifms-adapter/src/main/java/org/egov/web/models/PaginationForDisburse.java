package org.egov.web.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationForDisburse {

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("total_count")
    private Integer totalCount;

    @JsonProperty("sort_by")
    private String sortBy;

    @JsonProperty("sort_order")
    private SortOrder sortOrder;

    public enum SortOrder {

        ASC, DESC;

    }
}
