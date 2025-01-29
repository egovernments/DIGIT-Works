<<<<<<<< HEAD:libraries/works-services-common/src/main/java/org/egov/works/services/common/models/expense/BillResponse.java
package org.egov.works.services.common.models.expense;
========
package org.egov.digit.expense.calculator.web.models;
>>>>>>>> 504a89d592593471db1fd567ee4faf870546941e:backend/health-expense-calculator/src/main/java/org/egov/digit/expense/calculator/web/models/BillResponse.java

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

<<<<<<<< HEAD:libraries/works-services-common/src/main/java/org/egov/works/services/common/models/expense/BillResponse.java

========
/**
 * BillResponse
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
>>>>>>>> 504a89d592593471db1fd567ee4faf870546941e:backend/health-expense-calculator/src/main/java/org/egov/digit/expense/calculator/web/models/BillResponse.java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillResponse {

	@JsonProperty("ResponseInfo")
	@Valid
	private ResponseInfo responseInfo;

	@JsonProperty("bills")
	@Valid
	private List<Bill> bills;

	@JsonProperty("pagination")
	@Valid
	private Pagination pagination;

	@JsonProperty("statusCode")
	private String statusCode;

	public BillResponse addBillItem(Bill billItem) {
		if (this.bills == null) {
			this.bills = new ArrayList<>();
		}
		this.bills.add(billItem);
		return this;
	}

}
