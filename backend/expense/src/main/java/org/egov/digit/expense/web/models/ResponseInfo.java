package org.egov.digit.expense.web.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.egov.digit.expense.web.models.enums.ResponseStatus;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ResponseInfo should be used to carry metadata information about the response
 * from the server. apiId, ver and msgId in ResponseInfo should always
 * correspond to the same values in respective request&#x27;s RequestInfo.
 */
@Schema(description = "ResponseInfo should be used to carry metadata information about the response from the server. apiId, ver and msgId in ResponseInfo should always correspond to the same values in respective request's RequestInfo.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseInfo {
	
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

	@JsonProperty("resMsgId")
	@Size(max = 256)
	private String resMsgId;

	@JsonProperty("msgId")
	@Size(max = 256)
	private String msgId;

	@JsonProperty("status")
	@NotNull
	private ResponseStatus status;

}
