<<<<<<<< HEAD:libraries/works-services-common/src/main/java/org/egov/works/services/common/models/expense/Party.java
package org.egov.works.services.common.models.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
========
package org.egov.digit.expense.calculator.web.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.egov.common.contract.models.AuditDetails;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
>>>>>>>> 504a89d592593471db1fd567ee4faf870546941e:backend/health-expense-calculator/src/main/java/org/egov/digit/expense/calculator/web/models/Party.java
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
<<<<<<<< HEAD:libraries/works-services-common/src/main/java/org/egov/works/services/common/models/expense/Party.java
import org.egov.common.contract.models.AuditDetails;
import org.egov.works.services.common.models.expense.enums.Status;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


========

/**
 * Account details
 */
@Schema(description = "Account details")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-11T13:19:59.852+05:30[Asia/Kolkata]")
>>>>>>>> 504a89d592593471db1fd567ee4faf870546941e:backend/health-expense-calculator/src/main/java/org/egov/digit/expense/calculator/web/models/Party.java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Party {
	@JsonProperty("id")
	@Valid
	private String id;

	@JsonProperty("parentId")
	@Valid
	private String parentId;

	@JsonProperty("tenantId")
	@NotNull
	@Size(min = 2, max = 64)
	private String tenantId;

	@JsonProperty("type")
	@NotNull
	@Size(min = 2, max = 64)
	private String type;

	@JsonProperty("identifier")
	@NotNull
	@Size(min = 2, max = 64)
	private String identifier;

	@JsonProperty("status")
	@Size(min = 2, max = 64)
	private String status;

	@JsonProperty("additionalDetails")
	private Object additionalDetails;

	@JsonProperty("auditDetails")
	@Valid
	private AuditDetails auditDetails;

}
