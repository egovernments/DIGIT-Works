package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * Pagination details
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagination {
    @JsonProperty("limit")

    @DecimalMax("100")
    private Integer limit = null;

    @JsonProperty("offSet")

    private Integer offSet = null;

    @JsonProperty("totalCount")

    private Integer totalCount = null;

    @JsonProperty("sortBy")

    private String sortBy = null;

    @JsonProperty("order")

    private Order order = null;


}
