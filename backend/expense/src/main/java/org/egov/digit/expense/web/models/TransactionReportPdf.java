package org.egov.digit.expense.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReportPdf {

    private String reportTitle;
    private String tenantId;

    /** Bills included in this report */
    private List<String> billIds;


    private Long generatedTime;
    private String generatedBy;

    /** Aggregated across ALL bills */
    private BigDecimal totalAmount;
    private Integer totalTransactions;

    /** Rows across all bills */
    private List<TransactionReportRow> transactions;
}
