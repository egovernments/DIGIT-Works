package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.ToString;
import org.egov.common.contract.request.RequestInfo;

import java.util.List;

@Builder
@ToString
public class BillCalculatorRequestInfoWrapper {
    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

    @JsonProperty("bill")
    private List<Bill> bills;
}
