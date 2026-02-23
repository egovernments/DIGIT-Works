package org.egov.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.service.AttendanceReportGeneratorService;
import org.egov.web.models.report.ReportGenerationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class AttendanceReportConsumer {

    private final AttendanceReportGeneratorService reportGeneratorService;
    private final ObjectMapper objectMapper;
    private final MusterRollServiceConfiguration configuration;

    @Autowired
    public AttendanceReportConsumer(AttendanceReportGeneratorService reportGeneratorService,
                                    ObjectMapper objectMapper, MusterRollServiceConfiguration musterRollServiceConfiguration) {
        this.reportGeneratorService = reportGeneratorService;
        this.objectMapper = objectMapper;
        this.configuration = musterRollServiceConfiguration;
    }

    @KafkaListener(topics = { "${muster.roll.attendance.report.generate.topic}" })
    public void listen(Map<String, Object> consumerRecord, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            log.info("Received message to generate attendance report");

            // Deserialize into ReportGenerationRequest model
            ReportGenerationRequest request = objectMapper.convertValue(consumerRecord, ReportGenerationRequest.class);

            if (request.getMusterRollId() == null || request.getTenantId() == null) {
                log.error("Invalid report generation request - missing musterRollId or tenantId");
                return;
            }

            log.info("Processing report generation request: muster={}, type={}, format={}",
                    request.getMusterRollId(), request.getReportType(), request.getReportFormat());

            // Generate report with the new signature accepting ReportGenerationRequest
            reportGeneratorService.generateReport(request);

        } catch (Exception e) {
            log.error("Error processing attendance report generation message: {}", e.getMessage(), e);
        }
    }
}
