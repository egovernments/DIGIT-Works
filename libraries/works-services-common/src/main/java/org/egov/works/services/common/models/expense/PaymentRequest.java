package org.egov.works.services.common.models.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest   {
	
  @JsonProperty("RequestInfo")
  @Valid
  @NotNull
  private RequestInfo requestInfo;

  @JsonProperty("payment")
  @Valid
  @NotNull
  private Payment payment;

 
}
