package org.egov.digit.expense.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillReportRepository;
import org.egov.digit.expense.util.ResponseInfoFactory;
import org.egov.digit.expense.web.models.BillReport;
import org.egov.digit.expense.web.models.BillReportRequest;
import org.egov.digit.expense.web.models.BillReportResponse;
import org.egov.digit.expense.web.models.BillReportSearchRequest;
import org.egov.digit.expense.web.models.BillReportSearchResponse;
import org.egov.digit.expense.web.models.enums.ReportStatus;
import org.egov.digit.expense.web.validators.BillReportValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BillReportService {

    private final BillReportValidator validator;
    private final BillReportRepository repository;
    private final ExpenseProducer producer;
    private final Configuration config;
    private final ResponseInfoFactory responseInfoFactory;

    @Autowired
    public BillReportService(BillReportValidator validator,
                             BillReportRepository repository,
                             ExpenseProducer producer,
                             Configuration config,
                             ResponseInfoFactory responseInfoFactory) {
        this.validator = validator;
        this.repository = repository;
        this.producer = producer;
        this.config = config;
        this.responseInfoFactory = responseInfoFactory;
    }

    public BillReportResponse generate(BillReportRequest request) {
        log.info("BillReportService::generate");

        validator.validateGenerateRequest(request);
        enrichGenerateRequest(request);

        producer.push(request.getBillReport().getTenantId(), config.getBillReportSaveTopic(), request);

        return BillReportResponse.builder()
                .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), true))
                .status(ReportStatus.INITIATED)
                .build();
    }

    public BillReportSearchResponse search(BillReportSearchRequest request) {
        log.info("BillReportService::search");

        validator.validateSearchRequest(request);

        Integer count = repository.count(request.getSearchCriteria());
        List<BillReport> reports;
        if (count == 0) {
            reports = Collections.emptyList();
        } else {
            reports = repository.search(request.getSearchCriteria());
        }

        return BillReportSearchResponse.builder()
                .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), true))
                .totalCount(count)
                .billReports(reports)
                .build();
    }

    private void enrichGenerateRequest(BillReportRequest request) {
        BillReport report = request.getBillReport();
        String createdBy = request.getRequestInfo().getUserInfo().getUuid();
        Long currentTime = System.currentTimeMillis();

        report.setId(UUID.randomUUID().toString());
        report.setStatus(ReportStatus.INITIATED);

        AuditDetails auditDetails = AuditDetails.builder()
                .createdBy(createdBy)
                .createdTime(currentTime)
                .lastModifiedBy(createdBy)
                .lastModifiedTime(currentTime)
                .build();

        report.setAuditDetails(auditDetails);
    }
}
