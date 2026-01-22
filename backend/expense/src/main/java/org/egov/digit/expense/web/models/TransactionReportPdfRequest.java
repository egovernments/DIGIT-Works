package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReportPdfRequest {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

    @JsonProperty("report")
    private TransactionReportPdf report;
}
