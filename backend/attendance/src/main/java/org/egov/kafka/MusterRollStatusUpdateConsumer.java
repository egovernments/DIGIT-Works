package org.egov.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.service.PeriodStatusUpdateService;
import org.egov.web.models.MusterRollStatusUpdateEvent;
import org.egov.web.models.RegisterPeriodStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * MusterRollStatusUpdateConsumer
 *
 * V2 Intermediate Billing - Kafka consumer for muster-roll status update events.
 *
 * This consumer listens for muster-roll status changes and updates the attendance
 * register's period_statuses field asynchronously.
 *
 * Benefits:
 * - Eliminates synchronous API calls during attendance search
 * - Scales to millions of registers
 * - Event-driven, non-blocking architecture
 * - Pre-computes status for fast queries
 *
 * Topic: muster-roll-status-update
 * Publisher: Muster-roll Service - MusterRollService
 */
@Component
@Slf4j
public class MusterRollStatusUpdateConsumer {

    private final ObjectMapper objectMapper;
    private final PeriodStatusUpdateService periodStatusUpdateService;

    @Autowired
    public MusterRollStatusUpdateConsumer(
            ObjectMapper objectMapper,
            PeriodStatusUpdateService periodStatusUpdateService) {
        this.objectMapper = objectMapper;
        this.periodStatusUpdateService = periodStatusUpdateService;
    }

    /**
     * Consumes muster-roll status update events and updates period_statuses.
     *
     * Processing flow:
     * 1. Deserialize event from Kafka
     * 2. Validate event (must have billingPeriodId for V2)
     * 3. Build RegisterPeriodStatus object
     * 4. Update attendance register's period_statuses JSONB field
     * 5. Log success/failure
     *
     * Error handling:
     * - Invalid events are logged and skipped (no DLQ for now)
     * - Database errors are logged but don't fail the consumer
     * - V1 events (no billingPeriodId) are skipped gracefully
     *
     * @param consumerRecord The Kafka message as Map
     * @param topic The Kafka topic name (for logging)
     */
    @KafkaListener(topics = "${attendance.register.kafka.muster.status.update.topic}")
    public void processMusterRollStatusUpdate(
            Map<String, Object> consumerRecord,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        MusterRollStatusUpdateEvent event = null;

        try {
            // 1. Deserialize event
            event = objectMapper.convertValue(consumerRecord, MusterRollStatusUpdateEvent.class);

            log.info("processMusterRollStatusUpdate::Received event - muster: {} register: {} period: {} status: {} topic: {}",
                    event.getMusterRollId(),
                    event.getRegisterId(),
                    event.getBillingPeriodId(),
                    event.getStatus(),
                    topic);

            // 2. Validate event - Skip V1 events (no billingPeriodId)
            if (StringUtils.isBlank(event.getBillingPeriodId())) {
                log.debug("processMusterRollStatusUpdate::Skipping V1 event (no billingPeriodId) for muster: {}",
                        event.getMusterRollId());
                return;
            }

            // 3. Validate required fields
            if (StringUtils.isBlank(event.getRegisterId()) || StringUtils.isBlank(event.getStatus())) {
                log.error("processMusterRollStatusUpdate::Invalid event - missing required fields: {}",
                        consumerRecord);
                return;
            }

            // 4. Build RegisterPeriodStatus object
            RegisterPeriodStatus periodStatus = RegisterPeriodStatus.builder()
                    .periodId(event.getBillingPeriodId())
                    .status(event.getStatus())
                    .musterRollId(event.getMusterRollId())
                    .lastModifiedTime(event.getEventTime())
                    .build();

            // 5. Update period_statuses in database
            periodStatusUpdateService.updatePeriodStatus(event.getRegisterId(), periodStatus);

            log.info("processMusterRollStatusUpdate::Successfully processed event for register: {} period: {} new status: {}",
                    event.getRegisterId(),
                    event.getBillingPeriodId(),
                    event.getStatus());

        } catch (IllegalArgumentException e) {
            // Deserialization error - log and skip
            log.error("processMusterRollStatusUpdate::Failed to deserialize event from topic: {} - Error: {} - Record: {}",
                    topic, e.getMessage(), consumerRecord);

        } catch (Exception e) {
            // General error - log with full context but don't fail consumer
            String registerId = event != null ? event.getRegisterId() : "unknown";
            String periodId = event != null ? event.getBillingPeriodId() : "unknown";

            log.error("processMusterRollStatusUpdate::Failed to process event for register: {} period: {} - Error: {}",
                    registerId, periodId, e.getMessage(), e);

            // Future enhancement: Push to Dead Letter Queue (DLQ) for manual retry
            // For now, just log the failure - the event will be retried on next muster update
        }
    }
}
