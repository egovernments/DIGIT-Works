package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;


/**
 * A Object which holds the info about the expense details
 */
@Schema(description = "A Object which holds the info about the expense details")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillMapper {

	@JsonProperty("projectNumber")
	private String projectNumber;

	@JsonProperty("contractNumber")
	private String contractNumber;

	@JsonProperty("orgId")
	private String orgId;

	@JsonProperty("musterRollNumber")
	private String musterRollNumber;

	@JsonProperty("billId")
	private String billId;

	@JsonProperty("bill")
	private Bill bill;
}
