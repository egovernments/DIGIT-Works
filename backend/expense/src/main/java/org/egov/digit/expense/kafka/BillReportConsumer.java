package org.egov.digit.expense.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.util.FilestoreUtil;
import org.egov.digit.expense.util.PaymentAdvisoryExcelGenerator;
import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillCriteria;
import org.egov.digit.expense.web.models.BillReport;
import org.egov.digit.expense.web.models.BillReportRequest;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.models.enums.ReportStatus;
import org.egov.digit.expense.web.models.enums.ReportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class BillReportConsumer {

    private final ObjectMapper objectMapper;
    private final ExpenseProducer producer;
    private final Configuration config;
    private final BillRepository billRepository;
    private final PaymentAdvisoryExcelGenerator excelGenerator;
    private final FilestoreUtil filestoreUtil;

    @Autowired
    public BillReportConsumer(ObjectMapper objectMapper,
                              ExpenseProducer producer,
                              Configuration config,
                              BillRepository billRepository,
                              PaymentAdvisoryExcelGenerator excelGenerator,
                              FilestoreUtil filestoreUtil) {
        this.objectMapper = objectMapper;
        this.producer = producer;
        this.config = config;
        this.billRepository = billRepository;
        this.excelGenerator = excelGenerator;
        this.filestoreUtil = filestoreUtil;
    }

    @KafkaListener(topicPattern = "(${expense.kafka.tenant.id.pattern}){0,1}${expense.bill.report.save}")
    public void listen(final Map<String, Object> message) {
        log.info("BillReportConsumer::listen");
        try {
            BillReportRequest request = objectMapper.convertValue(message, BillReportRequest.class);
            processReportGeneration(request);
        } catch (Exception e) {
            log.error("Error processing bill report message", e);
        }
    }

    private void processReportGeneration(BillReportRequest request) {
        log.info("BillReportConsumer::processReportGeneration");
        BillReport report = request.getBillReport();

        try {
            String fileStoreId = generateReport(request);

            report.setStatus(ReportStatus.GENERATED);
            report.setFileStoreId(fileStoreId);
            updateAuditDetails(report, request);

            producer.push(report.getTenantId(), config.getBillReportUpdateTopic(), request);
            log.info("Report generated successfully for billId={} type={}", report.getBillId(), report.getType());

        } catch (Exception e) {
            log.error("Error generating report for billId={}", report.getBillId(), e);

            report.setStatus(ReportStatus.FAILED);
            report.setErrorDetails(Map.of(
                    "code", "REPORT_GENERATION_FAILED",
                    "message", e.getMessage() != null ? e.getMessage() : "Unknown error"
            ));
            updateAuditDetails(report, request);

            producer.push(report.getTenantId(), config.getBillReportUpdateTopic(), request);
        }
    }

    private String generateReport(BillReportRequest request) {
        BillReport report = request.getBillReport();

        if (ReportType.PAYMENT_ADVISORY_EXCEL.equals(report.getType())) {
            Bill bill = fetchBill(report.getBillId(), report.getTenantId(), request.getRequestInfo());
            byte[] excelBytes = excelGenerator.generate(bill, request.getRequestInfo());
            String fileName = "Bank Uplaod Template _Sample for Digit-" + bill.getBillNumber() + ".xlsx";
            return filestoreUtil.upload(excelBytes, report.getTenantId(), fileName);
        }

        throw new UnsupportedOperationException("Report type not supported: " + report.getType());
    }

    private Bill fetchBill(String billId, String tenantId, org.egov.common.contract.request.RequestInfo requestInfo) {
        BillSearchRequest searchRequest = BillSearchRequest.builder()
                .requestInfo(requestInfo)
                .billCriteria(BillCriteria.builder()
                        .ids(Collections.singleton(billId))
                        .tenantId(tenantId)
                        .build())
                .build();

        List<Bill> bills = billRepository.search(searchRequest, false);
        if (bills.isEmpty()) {
            throw new RuntimeException("Bill not found for billId=" + billId);
        }
        return bills.get(0);
    }

    private void updateAuditDetails(BillReport report, BillReportRequest request) {
        String modifiedBy = request.getRequestInfo().getUserInfo().getUuid();
        Long currentTime = System.currentTimeMillis();
        if (report.getAuditDetails() == null) {
            report.setAuditDetails(AuditDetails.builder()
                    .lastModifiedBy(modifiedBy).lastModifiedTime(currentTime).build());
        } else {
            report.getAuditDetails().setLastModifiedBy(modifiedBy);
            report.getAuditDetails().setLastModifiedTime(currentTime);
        }
    }
}
