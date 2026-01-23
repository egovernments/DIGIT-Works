package org.egov.digit.expense.web.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionReportRow {

    @Schema(description = "slNo")
    private Integer slNo;
    private Long date;
    private String billNumber;        // Bill.billNumber (HCM Identifier)
    private String mtnTransactionId;   // From TaskDetails.additionalDetails
    private String description;        // From BillDetail / LineItem
    private String debitAmount;

}
