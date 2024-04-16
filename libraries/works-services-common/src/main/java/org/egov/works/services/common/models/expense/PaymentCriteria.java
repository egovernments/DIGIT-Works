package org.egov.works.services.common.models.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCriteria   {
	
  @JsonProperty("tenantId")
  private String tenantId;

  @JsonProperty("ids")
  @Valid
  private Set<String> ids;

  @JsonProperty("billIds")
  @Valid
  private Set<String> billIds;

  @JsonProperty("status")
  private String status;

  @JsonProperty("referenceStatus")
  private String referenceStatus;
  
  @JsonProperty("paymentNumbers")
  @Valid
  private Set<String> paymentNumbers;
  
}
