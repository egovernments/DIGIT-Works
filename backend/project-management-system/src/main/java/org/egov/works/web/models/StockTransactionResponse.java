package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * StockTransactionResponse
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockTransactionResponse {
    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo = null;

    @JsonProperty("StockTransaction")
    @Valid
    private List<StockTransaction> stockTransaction = new ArrayList<>();


    public StockTransactionResponse addStockTransactionItem(StockTransaction stockTransactionItem) {
        this.stockTransaction.add(stockTransactionItem);
        return this;
    }

}

