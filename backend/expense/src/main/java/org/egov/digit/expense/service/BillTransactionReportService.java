package org.egov.digit.expense.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillTransactionReportRepository;
import org.egov.digit.expense.util.ResponseInfoFactory;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.ReportStatus;
import org.egov.digit.expense.web.validators.BillTransactionReportValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BillTransactionReportService {

    private final BillTransactionReportValidator validator;
    private final BillTransactionReportRepository repository;
    private final ExpenseProducer producer;
    private final Configuration config;
    private final ResponseInfoFactory responseInfoFactory;

    @Autowired
    public BillTransactionReportService(BillTransactionReportValidator validator,
                                        BillTransactionReportRepository repository,
                                        ExpenseProducer producer,
                                        Configuration config,
                                        ResponseInfoFactory responseInfoFactory) {
        this.validator = validator;
        this.repository = repository;
        this.producer = producer;
        this.config = config;
        this.responseInfoFactory = responseInfoFactory;
    }

    public BillTransactionReportResponse generate(BillTransactionReportRequest request) {
        log.info("BillTransactionReportService::generate");

        // Validate request
        validator.validateGenerateRequest(request);

        // Enrich request
        enrichGenerateRequest(request);

        // Push to Kafka save topic for async processing
        producer.push(request.getBillTransactionReport().getTenantId(), config.getBillTransactionReportSaveTopic(), request);

        // Build response
        return BillTransactionReportResponse.builder()
                .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), true))
                .status(ReportStatus.INITIATED)
                .build();
    }

    public BillTransactionReportSearchResponse search(BillTransactionReportSearchRequest request) {
        log.info("BillTransactionReportService::search");

        // Validate request
        validator.validateSearchRequest(request);

        // Search reports
        Integer count = repository.count(request.getSearchCriteria());
        List<BillTransactionReport> reports;
        if (count == 0) {
            reports = Collections.emptyList();
        } else {
            reports = repository.search(request.getSearchCriteria());
        }

        // Build response
        return BillTransactionReportSearchResponse.builder()
                .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), true))
                .totalCount(count)
                .billTransactionReports(reports)
                .build();
    }

    private void enrichGenerateRequest(BillTransactionReportRequest request) {
        BillTransactionReport report = request.getBillTransactionReport();
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
