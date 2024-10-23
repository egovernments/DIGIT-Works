package org.egov.works.services.common.models.expense.calculator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.works.services.common.models.expense.Bill;


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