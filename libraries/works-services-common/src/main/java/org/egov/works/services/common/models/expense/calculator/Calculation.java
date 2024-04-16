package org.egov.works.services.common.models.expense.calculator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


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
