package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.digit.expense.web.models.enums.ReportStatus;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Schema(description = "Response object for generate bill report API")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillReportResponse {

    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo;

    @JsonProperty("status")
    private ReportStatus status;

    @JsonProperty("billReports")
    private List<BillReport> billReports;

    @JsonProperty("errors")
    private List<ErrorDetail> errors;
}
