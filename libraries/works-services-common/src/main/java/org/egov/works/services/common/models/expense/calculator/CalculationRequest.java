package org.egov.works.services.common.models.expense.calculator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalculationRequest {
	@JsonProperty("RequestInfo")
	@Valid
	private RequestInfo requestInfo = null;

	@JsonProperty("criteria")
	@Valid
	private Criteria criteria = null;

}
