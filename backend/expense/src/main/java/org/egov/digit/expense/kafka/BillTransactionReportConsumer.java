package org.egov.digit.expense.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.web.models.BillTransactionReport;
import org.egov.digit.expense.web.models.BillTransactionReportRequest;
import org.egov.digit.expense.web.models.enums.ReportStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class BillTransactionReportConsumer {

    private final ObjectMapper objectMapper;
    private final ExpenseProducer producer;
    private final Configuration config;

    @Autowired
    public BillTransactionReportConsumer(ObjectMapper objectMapper,
                                         ExpenseProducer producer,
                                         Configuration config) {
        this.objectMapper = objectMapper;
        this.producer = producer;
        this.config = config;
    }

    @KafkaListener(topicPattern = "(${expense.kafka.tenant.id.pattern}){0,1}${expense.bill.transaction.report.save}")
    public void listen(final Map<String, Object> message) {
        log.info("Consuming message from kafka topic: expense.bill.transaction.report.save");
        try {
            BillTransactionReportRequest request = objectMapper.convertValue(message, BillTransactionReportRequest.class);
            processReportGeneration(request);
        } catch (Exception e) {
            log.error("Error processing bill transaction report message", e);
        }
    }

    private void processReportGeneration(BillTransactionReportRequest request) {
        log.info("BillTransactionReportConsumer::processReportGeneration");
        BillTransactionReport report = request.getBillTransactionReport();

        try {
            // TODO: Implement report generation logic
            // 1. Fetch bill transaction data
            // 2. Generate report based on type (EXCEL/PDF)
            // 3. Upload to filestore
            // 4. Get fileStoreId

            String fileStoreId = generateReport(request);

            // Update report with success status
            report.setStatus(ReportStatus.GENERATED);
            report.setFileStoreId(fileStoreId);
            updateAuditDetails(report, request);

            // Push to update topic
            producer.push(request.getBillTransactionReport().getTenantId(), config.getBillTransactionReportUpdateTopic(), request);
            log.info("Report generated successfully for billId: {}", report.getBillId());

        } catch (Exception e) {
            log.error("Error generating report for billId: {}", report.getBillId(), e);

            // Update report with failed status
            report.setStatus(ReportStatus.FAILED);
            report.setErrorDetails(createErrorDetails(e.getMessage()));
            updateAuditDetails(report, request);

            // Push to update topic
            producer.push(request.getBillTransactionReport().getTenantId(), config.getBillTransactionReportUpdateTopic(), request);
        }
    }

    /**
     * TODO: Implement report generation logic
     * This method should:
     * 1. Fetch bill transaction data from database
     * 2. Generate report based on type (EXCEL/PDF)
     * 3. Upload to filestore service
     * 4. Return fileStoreId
     */
    private String generateReport(BillTransactionReportRequest request) {
        // TODO: Implement actual report generation
        // BillTransactionReport report = request.getBillTransactionReport();
        // ReportType type = report.getType();
        //
        // if (ReportType.EXCEL.equals(type)) {
        //     return generateExcelReport(request);
        // } else if (ReportType.PDF.equals(type)) {
        //     return generatePdfReport(request);
        // }

        throw new UnsupportedOperationException("Report generation not yet implemented");
    }

    private void updateAuditDetails(BillTransactionReport report, BillTransactionReportRequest request) {
        String modifiedBy = request.getRequestInfo().getUserInfo().getUuid();
        Long currentTime = System.currentTimeMillis();

        report.getAuditDetails().setLastModifiedBy(modifiedBy);
        report.getAuditDetails().setLastModifiedTime(currentTime);
    }

    private Object createErrorDetails(String errorMessage) {
        return Map.of(
                "code", "REPORT_GENERATION_FAILED",
                "message", errorMessage != null ? errorMessage : "Unknown error occurred during report generation"
        );
    }
}
