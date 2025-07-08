package org.egov.digit.expense.calculator.web.models.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * BillRequest
 */
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BillReportRequest {

    @JsonProperty("RequestInfo")
    @Valid
    private RequestInfo requestInfo;

    @JsonProperty("bill")
    @Valid
    private List<ReportBill> reportBill;


}
