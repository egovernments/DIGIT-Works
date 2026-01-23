package org.egov.digit.expense.util;

import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.TransactionReport;
import org.egov.digit.expense.web.models.TransactionReportRequest;
import org.egov.digit.expense.web.models.TransactionReportRow;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
public class TransactionReportPdfUtil {

    public TransactionReportRequest buildReportRequest(
            RequestInfo requestInfo,
            String tenantId,
            Bill bill,
            List<TransactionReportRow> rows
    ) {
        BigDecimal totalAmount = rows.stream()
                .map(TransactionReportRow::getDebitAmount)
                .filter(Objects::nonNull)
                .map(BigDecimal::new)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String title = "Transactions Report - " + bill.getBillNumber();
        TransactionReport report =
                TransactionReport.builder()
                        .reportTitle(title)
                        .tenantId(tenantId)
                        .totalTransactions(rows.size())
                        .totalAmount(totalAmount)
                        .transactions(rows)
                        .build();

        return TransactionReportRequest.builder()
                .requestInfo(requestInfo)
                .report(report)
                .build();
    }
}