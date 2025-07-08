<<<<<<<< HEAD:libraries/works-services-common/src/main/java/org/egov/works/services/common/models/contract/AmountBreakup.java
package org.egov.works.services.common.models.contract;
========
package org.egov.digit.expense.calculator.web.models;
>>>>>>>> 504a89d592593471db1fd567ee4faf870546941e:backend/health-expense-calculator/src/main/java/org/egov/digit/expense/calculator/web/models/AmountBreakup.java

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.egov.common.contract.models.AuditDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
<<<<<<<< HEAD:libraries/works-services-common/src/main/java/org/egov/works/services/common/models/contract/AmountBreakup.java
import org.egov.common.contract.models.AuditDetails;
========
import org.springframework.validation.annotation.Validated;
>>>>>>>> 504a89d592593471db1fd567ee4faf870546941e:backend/health-expense-calculator/src/main/java/org/egov/digit/expense/calculator/web/models/AmountBreakup.java

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

<<<<<<<< HEAD:libraries/works-services-common/src/main/java/org/egov/works/services/common/models/contract/AmountBreakup.java
========
/**
 * AmountBreakup
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-01T15:45:33.268+05:30")
>>>>>>>> 504a89d592593471db1fd567ee4faf870546941e:backend/health-expense-calculator/src/main/java/org/egov/digit/expense/calculator/web/models/AmountBreakup.java

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmountBreakup {
    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("estimateAmountBreakupId")
    @NotNull
    @Valid
    private String estimateAmountBreakupId = null;

    @JsonProperty("amount")
    @NotNull
    @Valid
    private Double amount = null;

    @JsonProperty("status")
    @Valid
    private Status status = null;

    @JsonIgnore
    private String lineItemId = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    @JsonIgnore
    private AuditDetails auditDetails;

}

