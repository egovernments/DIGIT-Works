package org.egov.digit.expense.web.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PaymentResponse
 */
@Schema(description = "A Object which holds the info about the payment response")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse   {
	
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
