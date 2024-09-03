package org.egov.works.services.common.models.expense.calculator;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseBillRequest   {
        @JsonProperty("RequestInfo")
        @Valid
        private RequestInfo requestInfo = null;

        @JsonProperty("bill")
        @Valid
        private PurchaseBill bill = null;
        
        @JsonProperty("workflow")
    	private Workflow workflow;

       
}
