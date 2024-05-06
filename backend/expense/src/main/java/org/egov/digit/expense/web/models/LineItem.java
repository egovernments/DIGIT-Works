package org.egov.digit.expense.web.models;

import java.math.BigDecimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.web.models.enums.LineItemType;
import org.egov.digit.expense.web.models.enums.PaymentStatus;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Line items are the amount breakups for net amounts
 */
@Schema(description = "Line items are the amount breakups for net amounts")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LineItem {

	@JsonProperty("id")
	@Valid
	private String id;

	@JsonProperty("billDetailId")
	@Valid
	private String billDetailId;

	@JsonProperty("tenantId")
	@NotNull
	@Size(min = 2, max = 64)
	private String tenantId;

	@JsonProperty("headCode")
	@NotNull
	@Size(min = 2, max = 64)
	private String headCode;

	@JsonProperty("amount")
	@NotNull
	@Valid
	private BigDecimal amount;

	@JsonProperty("type")
	@NotNull
	private LineItemType type;

	@JsonProperty("paidAmount")
	@Valid
	@Builder.Default
	private BigDecimal paidAmount = BigDecimal.ZERO;

	@JsonProperty("status")
	private Status status;
	
	@JsonProperty("paymentStatus")
	private PaymentStatus paymentStatus;
	
	@JsonProperty("additionalDetails")
	private Object additionalDetails;

	@JsonProperty("auditDetails")
	@Valid
	private AuditDetails auditDetails;

}
