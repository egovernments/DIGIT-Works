package org.egov.works.services.common.models.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;


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
