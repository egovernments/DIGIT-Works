package org.egov.works.services.common.models.expense.calculator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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
