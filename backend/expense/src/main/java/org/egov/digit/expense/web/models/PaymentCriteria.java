package org.egov.digit.expense.web.models;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PaymentCriteria
 */
@Schema(description = "A Object which holds the info about the expense details")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
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
