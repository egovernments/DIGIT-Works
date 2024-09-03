package org.egov.works.services.common.models.bankaccounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
