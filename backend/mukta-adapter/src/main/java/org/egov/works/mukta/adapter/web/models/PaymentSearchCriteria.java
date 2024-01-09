package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSearchCriteria {
    @JsonProperty("ids")
    List<String> ids;

    @JsonProperty("tenantId")
    String tenantId;

    @JsonProperty("paymentId")
    String paymentId;

    @JsonProperty("paymentStatus")
    String paymentStatus;

    @JsonProperty("paymentType")
    String paymentType;
}
