package org.egov.digit.web.models;

import javax.validation.constraints.DecimalMax;

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
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-15T12:39:54.253+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagination {
	@JsonProperty("limit")

	@DecimalMax("100")
	private Double limit = 10d;

	@JsonProperty("offSet")

	private Double offSet = 0d;

	@JsonProperty("totalCount")

	private Double totalCount = null;

	@JsonProperty("sortBy")

	private String sortBy = null;

	@JsonProperty("order")

	private Order order = null;

}
