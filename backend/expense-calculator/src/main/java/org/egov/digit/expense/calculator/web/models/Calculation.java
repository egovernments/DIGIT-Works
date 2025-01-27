package org.egov.digit.expense.calculator.web.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.egov.common.contract.models.AuditDetails;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Calculation
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-11T13:19:59.852+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Calculation {
	@JsonProperty("tenantId")
	@NotNull

	private String tenantId = null;

	@JsonProperty("estimates")
	@Valid
	private List<CalcEstimate> estimates = null;

	@JsonProperty("totalAmount")

	@Valid
	private BigDecimal totalAmount = null;

	@JsonProperty("additionalDetails")

	private Object additionalDetails = null;

	@JsonProperty("auditDetails")

	@Valid
	private AuditDetails auditDetails = null;

	public Calculation addEstimatesItem(CalcEstimate estimatesItem) {
		if (this.estimates == null) {
			this.estimates = new ArrayList<>();
		}
		this.estimates.add(estimatesItem);
		return this;
	}

}
