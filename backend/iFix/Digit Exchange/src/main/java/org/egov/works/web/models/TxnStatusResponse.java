package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * txn status info on various service requests
 */
@Schema(description = "txn status info on various service requests")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-12-26T11:42:32.468+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TxnStatusResponse {
    @JsonProperty("transaction_id")
    @NotNull

    @Size(max = 99)
    private String transactionId = null;

    @JsonProperty("correlation_id")
    @NotNull

    @Size(max = 99)
    private String correlationId = null;

    @JsonProperty("txnstatus_response")
    @NotNull

    private Object txnstatusResponse = null;


}
