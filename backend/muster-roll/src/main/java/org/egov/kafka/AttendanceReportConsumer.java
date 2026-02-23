package org.egov.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.service.AttendanceReportGeneratorService;
import org.egov.util.AttendanceReportConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class AttendanceReportConsumer {

    private final AttendanceReportGeneratorService reportGeneratorService;
    private final ObjectMapper objectMapper;

    @Autowired
    public AttendanceReportConsumer(AttendanceReportGeneratorService reportGeneratorService,
            ObjectMapper objectMapper) {
        this.reportGeneratorService = reportGeneratorService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = { "${muster.roll.attendance.report.generate.topic}" })
    public void listen(Map<String, Object> consumerRecord) {
        try {
            log.info("Received message to generate attendance report");

            String musterRollId = (String) consumerRecord.get("musterRollId");
            String tenantId = (String) consumerRecord.get("tenantId");
            RequestInfo requestInfo = null;

            // Extract RequestInfo if present
            if (consumerRecord.containsKey("requestInfo")) {
                Object requestInfoObj = consumerRecord.get("requestInfo");
                if (requestInfoObj instanceof Map) {
                    requestInfo = objectMapper.convertValue(requestInfoObj, RequestInfo.class);
                } else if (requestInfoObj instanceof RequestInfo) {
                    requestInfo = (RequestInfo) requestInfoObj;
                }
            }

            if (musterRollId == null || tenantId == null) {
                log.error("Invalid message - missing musterRollId or tenantId");
                return;
            }

            // Generate report
            reportGeneratorService.generateReport(musterRollId, tenantId, requestInfo);

        } catch (Exception e) {
            log.error("Error processing attendance report generation message: {}", e.getMessage(), e);
        }
    }
}
