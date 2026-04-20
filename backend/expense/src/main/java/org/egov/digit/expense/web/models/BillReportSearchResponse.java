package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Response object for search bill reports API")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillReportSearchResponse {

    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo;

    @JsonProperty("totalCount")
    private Integer totalCount;

    @JsonProperty("billReports")
    @Valid
    private List<BillReport> billReports;

    public BillReportSearchResponse addBillReportItem(BillReport item) {
        if (this.billReports == null) {
            this.billReports = new ArrayList<>();
        }
        this.billReports.add(item);
        return this;
    }
}
