package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.DecimalMax;

/**
 * Pagination details
 */
@ApiModel(description = "Pagination details")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

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
