package org.egov.digit.expense.web.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * All APIs will return ErrorRes in case of failure which will carry
 * ResponseInfo as metadata and Error object as actual representation of error.
 * In case of bulk apis, some apis may chose to return the array of Error
 * objects to indicate individual failure.
 */
@Schema(description = "All APIs will return ErrorRes in case of failure which will carry ResponseInfo as metadata and Error object as actual representation of error. In case of bulk apis, some apis may chose to return the array of Error objects to indicate individual failure.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorRes {

	@JsonProperty("ResponseInfo")
	@NotNull
	@Valid
	private ResponseInfo responseInfo;

	@JsonProperty("Errors")
	@Valid
	private List<Error> errors;

	public ErrorRes addErrorsItem(Error errorsItem) {
		if (this.errors == null) {
			this.errors = new ArrayList<>();
		}
		this.errors.add(errorsItem);
		return this;
	}

}
