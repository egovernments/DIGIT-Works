package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.services.common.models.expense.Payment;

import javax.validation.Valid;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
   @JsonProperty("RequestInfo")
  @jakarta.validation.Valid
  @NotNull
  private RequestInfo requestInfo;

  @JsonProperty("payment")
  @jakarta.validation.Valid
  private Payment payment;

    @JsonProperty("referenceId")
  @Valid
  private String referenceId;

  @JsonProperty("tenantId")
  @Valid
  private String tenantId;

  @JsonProperty("parentPI")
  @Valid
  private String parentPI;
}
