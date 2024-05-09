package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.DecimalMax;

/**
 * Pagination details
 */
@Schema(description = "Pagination details")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-14T17:30:53.139+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagination {

    @JsonProperty("limit")
    @DecimalMax("100")
    private Double limit = 10d;

    @JsonProperty("offSet")
    private Double offSet = 0d;

    @JsonProperty("totalCount")
    private Double totalCount = null;

    @JsonProperty("sortBy")
    private SortBy sortBy;

    @JsonProperty("order")
    private Order order = null;

    public enum SortBy {
        createdTime,
        accountHolderName,
        serviceCode,
        accountNumber,
        bankBranchIdentifierCode
    }

}
