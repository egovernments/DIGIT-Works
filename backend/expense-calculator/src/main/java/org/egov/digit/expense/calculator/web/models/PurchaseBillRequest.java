package org.egov.digit.expense.calculator.web.models;


import jakarta.validation.Valid;

import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PurchaseBillRequest
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-17T16:59:23.221+05:30[Asia/Kolkata]")
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
