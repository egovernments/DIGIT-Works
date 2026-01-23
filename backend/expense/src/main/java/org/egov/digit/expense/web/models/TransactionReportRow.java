package org.egov.digit.expense.web.models;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionReportRow {

    private Long date;
    private String billNumber;        // Bill.billNumber (HCM Identifier)
    private String mtnTransactionId;   // From TaskDetails.additionalDetails
    private String description;        // From BillDetail / LineItem
    private String debitAmount;
}
