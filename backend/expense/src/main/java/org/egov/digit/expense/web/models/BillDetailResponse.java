package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * BillDetailResponse
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDetailResponse {

	@JsonProperty("RequestInfo")
	@Valid
	private ResponseInfo responseInfo;

	@JsonProperty("billDetails")
	@Valid
	private List<BillDetail> billDetails;

	@JsonProperty("pagination")
	@Valid
	private Pagination pagination;

}
