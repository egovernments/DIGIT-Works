package org.egov.web.models.bill;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * PaymentRequest
 */
@Schema(description = "A Object which holds the info about the payment request")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PaymentRequest {
	
  @JsonProperty("RequestInfo")
  @Valid
  @NotNull
  private RequestInfo requestInfo;

  @JsonProperty("payment")
  @Valid
  @NotNull
  private Payment payment;

 
}
