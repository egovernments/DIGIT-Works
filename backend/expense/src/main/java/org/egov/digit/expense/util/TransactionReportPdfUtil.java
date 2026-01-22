package org.egov.digit.expense.util;

import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.web.models.TransactionReportPdf;
import org.egov.digit.expense.web.models.TransactionReportPdfRequest;
import org.egov.digit.expense.web.models.TransactionReportRow;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TransactionReportPdfUtil {

    public TransactionReportPdfRequest buildPdfRequest(
            RequestInfo requestInfo,
            String tenantId,
            List<TransactionReportRow> rows
    ) {

        BigDecimal totalAmount =
                rows.stream()
                        .map(TransactionReportRow::getDebitAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        TransactionReportPdf report =
                TransactionReportPdf.builder()
                        .reportTitle("Congo B – Transaction Report")
                        .tenantId(tenantId)
                        .generatedTime(System.currentTimeMillis())
                        .totalTransactions(rows.size())
                        .totalAmount(totalAmount)
                        .transactions(rows)
                        .build();

        return TransactionReportPdfRequest.builder()
                .requestInfo(requestInfo)
                .report(report)
                .build();
    }
}