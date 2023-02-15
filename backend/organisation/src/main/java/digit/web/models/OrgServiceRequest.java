package digit.web.models;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrgServiceRequest
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrgServiceRequest {
	@JsonProperty("RequestInfo")

	private Object requestInfo = null;

	@JsonProperty("organisations")

	private List<Organisation> organisations = null;

	@JsonProperty("workflow")

	@Valid

	private Workflow workflow = null;

}
