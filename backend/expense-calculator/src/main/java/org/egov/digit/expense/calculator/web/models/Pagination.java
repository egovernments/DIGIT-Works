package org.egov.digit.expense.calculator.web.models;

import jakarta.validation.constraints.DecimalMax;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pagination details
 */
@Schema(description = "Pagination details")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-11T13:19:59.852+05:30[Asia/Kolkata]")
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
