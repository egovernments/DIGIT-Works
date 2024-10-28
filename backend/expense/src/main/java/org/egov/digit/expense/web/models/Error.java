package org.egov.digit.expense.web.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error object will be returned as a part of reponse body in conjunction with
 * ResponseInfo as part of ErrorResponse whenever the request processing status
 * in the ResponseInfo is FAILED. HTTP return in this scenario will usually be
 * HTTP 400.
 */
@Schema(description = "Error object will be returned as a part of reponse body in conjunction with ResponseInfo as part of ErrorResponse whenever the request processing status in the ResponseInfo is FAILED. HTTP return in this scenario will usually be HTTP 400.")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Error {

	@JsonProperty("code")
	@NotNull
	private String code;

	@JsonProperty("message")
	@NotNull
	private String message;

	@JsonProperty("description")
	private String description;

	@JsonProperty("params")
	private List<String> params;

	public Error addParamsItem(String paramsItem) {
		if (this.params == null) {
			this.params = new ArrayList<>();
		}
		this.params.add(paramsItem);
		return this;
	}

}
