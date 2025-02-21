package org.egov.wms.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.wms.service.WMSReportService;
import org.egov.wms.web.model.Job.ReportRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class WMSConsumer {
    private final ObjectMapper objectMapper;

    private final WMSReportService wmsReportService;

    @Autowired
    public WMSConsumer(ObjectMapper objectMapper, WMSReportService wmsReportService) {
        this.objectMapper = objectMapper;
        this.wmsReportService = wmsReportService;
    }


    @KafkaListener(topics = {"${wms.kafka.report.create.topic}"})
    public void listen(final String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            log.info("Consuming record from Kafka topic: " + topic);
            ReportRequest reportRequest = objectMapper.readValue(message, ReportRequest.class);
            if(reportRequest.getJobRequest() != null && reportRequest.getRequestInfo() != null){
                log.info("Consumed record: " + reportRequest.getJobRequest().getId());
                if(wmsReportService.validateJobScheduledRequest(reportRequest))
                    wmsReportService.processReportGenerationAfterConsumptionFromTopic(reportRequest);
            }
        }catch (Exception e){
            log.error("Exception in WMSConsumer: " + e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
