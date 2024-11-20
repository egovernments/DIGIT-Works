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
 * Criteria
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-11T13:19:59.852+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Criteria {
	@JsonProperty("tenantId")
	@NotNull

	private String tenantId = null;

	@JsonProperty("musterRollId")

	private List<String> musterRollId = null;

	@JsonProperty("fromPeriod")

	@Valid
	private BigDecimal fromPeriod = null;

	@JsonProperty("toPeriod")

	@Valid
	private BigDecimal toPeriod = null;

	@JsonProperty("contractId")

	private String contractId = null;

	@JsonProperty("auditDetails")

	@Valid
	private AuditDetails auditDetails = null;

	public Criteria addMusterRollIdItem(String musterRollIdItem) {
		if (this.musterRollId == null) {
			this.musterRollId = new ArrayList<>();
		}
		this.musterRollId.add(musterRollIdItem);
		return this;
	}

}
