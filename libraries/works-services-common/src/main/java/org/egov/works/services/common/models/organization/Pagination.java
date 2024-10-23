package org.egov.works.services.common.models.organization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.DecimalMax;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pagination {

    @JsonProperty("limit")
    @DecimalMax("100")
    private Double limit = 10.0d;

    @JsonProperty("offset")
    private Double offset = 0.0d;

    @JsonProperty("totalCount")
    private Double totalCount = null;

    @JsonProperty("sortBy")
    private String sortBy = null;

    @JsonProperty("order")
    private Object order = null;

}
