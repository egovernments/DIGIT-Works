<<<<<<<< HEAD:libraries/works-services-common/src/main/java/org/egov/works/services/common/models/expense/BillRequest.java
package org.egov.works.services.common.models.expense;
========
package org.egov.digit.expense.calculator.web.models;
>>>>>>>> 504a89d592593471db1fd567ee4faf870546941e:backend/health-expense-calculator/src/main/java/org/egov/digit/expense/calculator/web/models/BillMetaRecords.java

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
<<<<<<<< HEAD:libraries/works-services-common/src/main/java/org/egov/works/services/common/models/expense/BillRequest.java
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;

import jakarta.validation.Valid;

========

import java.util.List;
>>>>>>>> 504a89d592593471db1fd567ee4faf870546941e:backend/health-expense-calculator/src/main/java/org/egov/digit/expense/calculator/web/models/BillMetaRecords.java

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
<<<<<<<< HEAD:libraries/works-services-common/src/main/java/org/egov/works/services/common/models/expense/BillRequest.java
public class BillRequest {

	@JsonProperty("RequestInfo")
	@Valid
	private RequestInfo requestInfo;

	@JsonProperty("bill")
	@Valid
	private Bill bill;

	@JsonProperty("workflow")
	private Workflow workflow;

========
public class BillMetaRecords {

    @JsonProperty("BillMetaCalculation")
    private List<BillMetaCalculation> billMetaCalculation;
>>>>>>>> 504a89d592593471db1fd567ee4faf870546941e:backend/health-expense-calculator/src/main/java/org/egov/digit/expense/calculator/web/models/BillMetaRecords.java
}
