package org.egov.digit.expense.web.validators;

import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.repository.BillReportRepository;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillCriteria;
import org.egov.digit.expense.web.models.BillReport;
import org.egov.digit.expense.web.models.BillReportRequest;
import org.egov.digit.expense.web.models.BillReportSearchRequest;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.models.enums.ReportStatus;
import org.egov.digit.expense.web.models.enums.ReportType;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BillReportValidator {

    private final BillReportRepository repository;
    private final BillRepository billRepository;

    @Autowired
    public BillReportValidator(BillReportRepository repository, BillRepository billRepository) {
        this.repository = repository;
        this.billRepository = billRepository;
    }

    public void validateGenerateRequest(BillReportRequest request) {
        log.info("BillReportValidator::validateGenerateRequest");
        Map<String, String> errorMap = new HashMap<>();
        BillReport report = request.getBillReport();

        if (!StringUtils.hasText(report.getBillId())) {
            errorMap.put("EG_EXPENSE_REPORT_BILLID_REQUIRED", "Bill ID is mandatory for generating report");
        }

        if (!StringUtils.hasText(report.getTenantId())) {
            errorMap.put("EG_EXPENSE_REPORT_TENANTID_REQUIRED", "Tenant ID is mandatory for generating report");
        }

        if (report.getType() == null) {
            errorMap.put("EG_EXPENSE_REPORT_TYPE_REQUIRED", "Report type is mandatory (TRANSACTION_REPORT_EXCEL, TRANSACTION_REPORT_PDF, PAYMENT_ADVISORY_EXCEL)");
        }

        if (!errorMap.isEmpty()) {
            throw new CustomException(errorMap);
        }

        if (ReportType.PAYMENT_ADVISORY_EXCEL.equals(report.getType())) {
            validatePaymentAdvisoryAccess(request, report);
        }

        // Idempotency check — reject if an INITIATED report already exists for this bill + type
        BillReport latestReport = repository.getLatestReport(
                report.getBillId(), report.getTenantId(), report.getType().toString());

        if (latestReport != null && ReportStatus.INITIATED.equals(latestReport.getStatus())) {
            throw new CustomException("EG_EXPENSE_REPORT_ALREADY_INITIATED",
                    "Report generation is already in progress for this bill. Please wait for it to complete.");
        }
    }

    public void validateSearchRequest(BillReportSearchRequest request) {
        log.info("BillReportValidator::validateSearchRequest");
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

    private void validatePaymentAdvisoryAccess(BillReportRequest request, BillReport report) {
        boolean isApprover = request.getRequestInfo().getUserInfo().getRoles()
                .stream().anyMatch(r -> "PAYMENT_APPROVER".equals(r.getCode()));
        if (!isApprover) {
            throw new CustomException("EG_EXPENSE_REPORT_UNAUTHORIZED",
                    "Only PAYMENT_APPROVER role can generate Payment Advisory Report");
        }

        BillSearchRequest billSearchRequest = BillSearchRequest.builder()
                .requestInfo(request.getRequestInfo())
                .billCriteria(BillCriteria.builder()
                        .ids(new java.util.HashSet<>(java.util.Collections.singletonList(report.getBillId())))
                        .tenantId(report.getTenantId())
                        .build())
                .build();

        List<Bill> bills = billRepository.search(billSearchRequest, false);
        if (bills.isEmpty()) {
            throw new CustomException("EG_EXPENSE_BILL_NOT_FOUND",
                    "Bill not found: " + report.getBillId());
        }

        Bill bill = bills.get(0);
        if (!Status.REVIEWED.equals(bill.getStatus())) {
            throw new CustomException("EG_EXPENSE_REPORT_BILL_NOT_REVIEWED",
                    "Payment Advisory Report can only be generated for bills in REVIEWED status. Current status: " + bill.getStatus());
        }
    }
}
