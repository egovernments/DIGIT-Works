package org.egov.digit.expense.web.models;

import javax.validation.constraints.DecimalMax;

import org.egov.digit.expense.web.models.enums.Order;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pagination details
 */
@Schema(description = "Pagination details")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagination {
	
	@JsonProperty("limit")
	@DecimalMax("100")
	@Default
	private Long limit = 10l;

	@JsonProperty("offSet")
	@Default
	private Long offSet = 0l;

	@JsonProperty("totalCount")
	private Long totalCount;

	@JsonProperty("sortBy")
	private String sortBy;

	@JsonProperty("order")
	private Order order;

}
