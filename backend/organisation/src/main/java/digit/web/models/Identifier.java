package digit.web.models;

import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Object to capture tax identifiers for a organisation
 */
@ApiModel(description = "Object to capture tax identifiers for a organisation")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Identifier {
	@JsonProperty("type")

	@Size(min = 2, max = 64)

	private String type = null;

	@JsonProperty("value")

	@Size(min = 2, max = 64)

	private String value = null;

	@JsonProperty("additionalDetails")

	private Object additionalDetails = null;

}
