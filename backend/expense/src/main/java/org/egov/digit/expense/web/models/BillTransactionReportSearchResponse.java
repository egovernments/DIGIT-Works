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

/**
 * Response object for search bill transaction reports API
 */
@Schema(description = "Response object for search bill transaction reports API")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillTransactionReportSearchResponse {

    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo;

    @JsonProperty("totalCount")
    private Integer totalCount;

    @JsonProperty("billTransactionReports")
    @Valid
    private List<BillTransactionReport> billTransactionReports;

    public BillTransactionReportSearchResponse addBillTransactionReportItem(BillTransactionReport item) {
        if (this.billTransactionReports == null) {
            this.billTransactionReports = new ArrayList<>();
        }
        this.billTransactionReports.add(item);
        return this;
    }
}
