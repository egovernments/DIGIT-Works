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
 * Captures details of a contact person
 */
@ApiModel(description = "Captures details of a contact person")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDetails {
	@JsonProperty("contactName")

	@Size(min = 2, max = 64)

	private String contactName = null;

	@JsonProperty("contactMobileNumber")

	@Size(max = 20)

	private String contactMobileNumber = null;

	@JsonProperty("contactEmail")

	@Size(min = 5, max = 200)

	private String contactEmail = null;

}
