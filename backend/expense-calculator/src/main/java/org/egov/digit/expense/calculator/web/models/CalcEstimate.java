package org.egov.digit.expense.calculator.web.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.egov.common.contract.models.AuditDetails;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Object which holds the info about the expense details.
 */
@Schema(description = "A Object which holds the info about the expense details.")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-11T13:19:59.852+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalcEstimate {
	@JsonProperty("id")

	@Valid
	private UUID id = null;

	@JsonProperty("tenantId")
	@NotNull

	@Size(min = 2, max = 64)
	private String tenantId = null;

	@JsonProperty("netPayableAmount")

	@Valid
	private BigDecimal netPayableAmount = null;

	@JsonProperty("businessService")
	@NotNull

	@Size(min = 2, max = 64)
	private String businessService = null;

	@JsonProperty("referenceId")

	@Size(min = 2, max = 64)
	private String referenceId = null;

	@JsonProperty("fromPeriod")

	@Valid
	private BigDecimal fromPeriod = null;

	@JsonProperty("toPeriod")

	@Valid
	private BigDecimal toPeriod = null;

	@JsonProperty("calcDetails")
	@Valid
	private List<CalcDetail> calcDetails = null;

	@JsonProperty("additionalFields")

	private Object additionalFields = null;

	@JsonProperty("auditDetails")

	@Valid
	private AuditDetails auditDetails = null;

	public CalcEstimate addCalcDetailsItem(CalcDetail calcDetailsItem) {
		if (this.calcDetails == null) {
			this.calcDetails = new ArrayList<>();
		}
		this.calcDetails.add(calcDetailsItem);
		return this;
	}

}
