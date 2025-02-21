package org.egov.works.services.common.models.expense.calculator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.ToString;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.services.common.models.expense.Bill;


@Builder
@ToString
public class BillCalculatorRequestInfoWrapper {
    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;


    @JsonProperty("bill")
    private Bill bill;

    @JsonProperty("workflow")
    private Workflow workflow = null;
}
