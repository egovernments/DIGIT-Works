package org.egov.digit.expense.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.service.TransactionReportGenerationService;
import org.egov.digit.expense.web.models.BillTransactionReport;
import org.egov.digit.expense.web.models.BillTransactionReportRequest;
import org.egov.digit.expense.web.models.enums.ReportStatus;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class BillTransactionReportConsumer {

    private final ObjectMapper objectMapper;
    private final ExpenseProducer producer;
    private final Configuration config;
    private final TransactionReportGenerationService transactionReportGenerationService;

    @Autowired
    public BillTransactionReportConsumer(ObjectMapper objectMapper,
                                         ExpenseProducer producer,
                                         Configuration config, TransactionReportGenerationService transactionReportGenerationService) {
        this.objectMapper = objectMapper;
        this.producer = producer;
        this.config = config;
        this.transactionReportGenerationService = transactionReportGenerationService;
    }

    @KafkaListener(topics = {"${expense.bill.transaction.report.save}"})
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
            String fileStoreId = transactionReportGenerationService.createReportAndUploadToFileStore(request);

            if (fileStoreId == null || fileStoreId.isBlank()) {
                throw new CustomException(
                        "FILESTORE_ID_NULL",
                        "fileStoreId is null or empty"
                );
            }

            // Update report with success status
            report.setFileStoreId(fileStoreId);
            updateAuditDetails(report, request);
            report.setStatus(ReportStatus.GENERATED);

            // Push to update topic
            producer.push(config.getBillTransactionReportUpdateTopic(), request);
            log.info("Report generated successfully for billId: {}", report.getBillId());

        } catch (Exception e) {
            log.error("Error generating report for billId: {}", report.getBillId(), e);

            // Update report with failed status
            report.setStatus(ReportStatus.FAILED);
            report.setErrorDetails(createErrorDetails(e.getMessage()));
            updateAuditDetails(report, request);

            // Push to update topic
            producer.push(config.getBillTransactionReportUpdateTopic(), request);
        }
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
