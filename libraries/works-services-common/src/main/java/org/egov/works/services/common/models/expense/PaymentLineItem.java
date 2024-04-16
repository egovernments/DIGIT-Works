package org.egov.works.services.common.models.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;
import org.egov.works.services.common.models.expense.enums.PaymentStatus;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


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
