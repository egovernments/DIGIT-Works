package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("totalCount")
    private Integer totalCount;

    @JsonProperty("order")
    private String order;

    @JsonProperty("sortBy")
    private String sortBy;
}
