package org.egov.digit.expense.web.models;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.web.models.enums.PaymentStatus;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Line items are the amount breakups for net amounts
 */
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentLineItem   {
	
  @JsonProperty("id")
  private String id;

  @JsonProperty("lineItemId")
  private String lineItemId;

  @JsonProperty("tenantId")
  private String tenantId;

  @JsonProperty("paidAmount")
  @NotNull
  private BigDecimal paidAmount;

  @JsonProperty("status")
  private PaymentStatus status;

  @JsonProperty("auditDetails")
  private AuditDetails auditDetails;
}
