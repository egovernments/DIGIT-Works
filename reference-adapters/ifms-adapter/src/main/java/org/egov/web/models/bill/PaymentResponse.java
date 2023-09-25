package org.egov.web.models.bill;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.web.models.Pagination;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * PaymentResponse
 */
@Schema(description = "A Object which holds the info about the payment response")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
	
  @JsonProperty("ResponseInfo")
  private ResponseInfo responseInfo;

  @JsonProperty("payments")
  @Valid
  private List<Payment> payments;

  @JsonProperty("pagination")
  private Pagination pagination;
  
	public PaymentResponse addPaymentItem(Payment paymentItem) {
		if (this.payments == null) {
			this.payments = new ArrayList<>();
		}
		this.payments.add(paymentItem);
		return this;
	}
}
