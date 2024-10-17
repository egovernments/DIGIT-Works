package org.egov.works.services.common.models.expense.calculator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalculationResponse {
	@JsonProperty("responseInfo")

	@Valid
	private ResponseInfo responseInfo = null;

	@JsonProperty("calculation")

	@Valid
	private Calculation calculation = null;

}
