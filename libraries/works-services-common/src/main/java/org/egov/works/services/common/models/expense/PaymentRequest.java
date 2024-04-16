package org.egov.works.services.common.models.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


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
