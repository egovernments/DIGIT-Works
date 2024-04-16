package org.egov.works.services.common.models.expense.calculator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.works.services.common.models.bankaccounts.Order;

import javax.validation.constraints.DecimalMax;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagination {
	@JsonProperty("limit")

	@DecimalMax("100")
	private Integer limit = 10;

	@JsonProperty("offset")

	private Integer offSet = 0;

	@JsonProperty("totalCount")

	private Integer totalCount = null;

	@JsonProperty("sortBy")

	private String sortBy = null;

	@JsonProperty("order")

	private Order order = null;

}
