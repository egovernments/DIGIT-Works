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
