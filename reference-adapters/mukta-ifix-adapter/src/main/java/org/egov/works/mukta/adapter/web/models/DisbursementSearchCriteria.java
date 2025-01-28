package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisbursementSearchCriteria {
    @JsonProperty("ids")
    private List<String> ids;

    @JsonProperty("payment_number")
    private String paymentNumber;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("type")
    private String type;
}
