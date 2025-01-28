package org.egov.works.services.common.models.expense.calculator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillMapperSearchResponse {
	@JsonProperty("responseInfo")
	@Valid
	private ResponseInfo responseInfo = null;

	@JsonProperty("bills")
	@Valid
	private List<BillMapper> billMappers = null;

	@JsonProperty("pagination")
	@Valid
	private Pagination pagination;

}
