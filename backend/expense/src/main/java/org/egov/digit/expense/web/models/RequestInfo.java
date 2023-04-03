package org.egov.digit.expense.web.models;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RequestInfo should be used to carry meta information about the requests to
 * the server as described in the fields below. All eGov APIs will use
 * requestinfo as a part of the request body to carry this meta information.
 * Some of this information will be returned back from the server as part of the
 * ResponseInfo in the response body to ensure correlation.
 */
@Schema(description = "RequestInfo should be used to carry meta information about the requests to the server as described in the fields below. All eGov APIs will use requestinfo as a part of the request body to carry this meta information. Some of this information will be returned back from the server as part of the ResponseInfo in the response body to ensure correlation.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestInfo {
	
	@JsonProperty("apiId")
	@NotNull
	@Size(max = 128)
	private String apiId;

	@JsonProperty("ver")
	@NotNull
	@Size(max = 32)
	private String ver;

	@JsonProperty("ts")
	@NotNull
	private Long ts;

	@JsonProperty("action")
	@NotNull
	@Size(max = 32)
	private String action;

	@JsonProperty("did")
	@Size(max = 1024)
	private String did;

	@JsonProperty("key")
	@Size(max = 256)
	private String key;

	@JsonProperty("msgId")
	@NotNull
	@Size(max = 256)
	private String msgId;

	@JsonProperty("requesterId")
	@Size(max = 256)
	private String requesterId;

	@JsonProperty("authToken")
	private String authToken;

	@JsonProperty("userInfo")
	@Valid
	private User userInfo;

	@JsonProperty("correlationId")
	private String correlationId;

}
