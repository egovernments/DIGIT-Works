package org.egov.digit.expense.web.validators;

import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.repository.BillTransactionReportRepository;
import org.egov.digit.expense.web.models.BillTransactionReport;
import org.egov.digit.expense.web.models.BillTransactionReportRequest;
import org.egov.digit.expense.web.models.BillTransactionReportSearchRequest;
import org.egov.digit.expense.web.models.enums.ReportStatus;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class BillTransactionReportValidator {

    private final BillTransactionReportRepository repository;

    @Autowired
    public BillTransactionReportValidator(BillTransactionReportRepository repository) {
        this.repository = repository;
    }

    public void validateGenerateRequest(BillTransactionReportRequest request) {
        log.info("BillTransactionReportValidator::validateGenerateRequest");
        Map<String, String> errorMap = new HashMap<>();
        BillTransactionReport report = request.getBillTransactionReport();

        if (!StringUtils.hasText(report.getBillId())) {
            errorMap.put("EG_EXPENSE_REPORT_BILLID_REQUIRED", "Bill ID is mandatory for generating report");
        }

        if (!StringUtils.hasText(report.getTenantId())) {
            errorMap.put("EG_EXPENSE_REPORT_TENANTID_REQUIRED", "Tenant ID is mandatory for generating report");
        }

        if (report.getType() == null) {
            errorMap.put("EG_EXPENSE_REPORT_TYPE_REQUIRED", "Report type (EXCEL/PDF) is mandatory for generating report");
        }

        if (!errorMap.isEmpty()) {
            throw new CustomException(errorMap);
        }

        // Check if latest report for this billId is already INITIATED
        BillTransactionReport latestReport = repository.getLatestReport(
                report.getBillId(),
                report.getTenantId(),
                report.getType().toString()
        );

        if (latestReport != null && ReportStatus.INITIATED.equals(latestReport.getStatus())) {
            throw new CustomException("EG_EXPENSE_REPORT_ALREADY_INITIATED",
                    "Report generation is already in progress for this bill. Please wait for the current report to complete.");
        }
    }

    public void validateSearchRequest(BillTransactionReportSearchRequest request) {
        log.info("BillTransactionReportValidator::validateSearchRequest");
        Map<String, String> errorMap = new HashMap<>();

        if (request.getSearchCriteria() == null) {
            throw new CustomException("EG_EXPENSE_REPORT_SEARCH_CRITERIA_REQUIRED", "Search criteria is mandatory");
        }

        if (!StringUtils.hasText(request.getSearchCriteria().getBillId())) {
            errorMap.put("EG_EXPENSE_REPORT_BILLID_REQUIRED", "Bill ID is mandatory for searching reports");
        }

        if (!StringUtils.hasText(request.getSearchCriteria().getTenantId())) {
            errorMap.put("EG_EXPENSE_REPORT_TENANTID_REQUIRED", "Tenant ID is mandatory for searching reports");
        }

        if (!errorMap.isEmpty()) {
            throw new CustomException(errorMap);
        }
    }
}
