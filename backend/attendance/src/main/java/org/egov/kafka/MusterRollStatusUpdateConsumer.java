package org.egov.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.producer.Producer;
import org.egov.repository.RegisterRepository;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.AttendanceRegisterRequest;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.egov.web.models.MusterRollStatusUpdateEvent;
import org.egov.web.models.RegisterPeriodStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * MusterRollStatusUpdateConsumer
 *
 * V2 Intermediate Billing - Kafka consumer for muster-roll status update events.
 *
 * This consumer listens for muster-roll status changes and updates the attendance
 * register's period_statuses field by publishing to the existing update-attendance topic.
 *
 * Benefits:
 * - Eliminates synchronous API calls during attendance search
 * - Scales to millions of registers
 * - Event-driven, non-blocking architecture
 * - Uses persister pattern (no direct DB writes)
 * - Reuses existing update-attendance topic (simpler architecture)
 *
 * Topic consumed: muster-roll-status-update
 * Publisher: Muster-roll Service - MusterRollService
 * Publishes to: update-attendance (existing topic, period_statuses field added to persister config)
 */
@Component
@Slf4j
public class MusterRollStatusUpdateConsumer {

    private final ObjectMapper objectMapper;
    private final RegisterRepository registerRepository;
    private final Producer producer;

    @Autowired
    public MusterRollStatusUpdateConsumer(
            ObjectMapper objectMapper,
            RegisterRepository registerRepository,
            Producer producer) {
        this.objectMapper = objectMapper;
        this.registerRepository = registerRepository;
        this.producer = producer;
    }

    /**
     * Consumes muster-roll status update events and updates period_statuses via persister.
     *
     * Processing flow:
     * 1. Deserialize event from Kafka
     * 2. Validate event (must have billingPeriodId for V2)
     * 3. Fetch register from DB to get existing period_statuses
     * 4. Update or add the period status
     * 5. Publish to update-attendance topic for persister (reuses existing topic)
     *
     * Error handling:
     * - Invalid events are logged and skipped
     * - Register not found errors are logged but don't fail consumer
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

            // 4. Fetch existing register to get current period_statuses
            AttendanceRegister register = fetchRegister(event.getRegisterId(), event.getTenantId());
            if (register == null) {
                log.error("processMusterRollStatusUpdate::Register not found: {}", event.getRegisterId());
                return;
            }

            // 5. Build new period status
            RegisterPeriodStatus newPeriodStatus = RegisterPeriodStatus.builder()
                    .periodId(event.getBillingPeriodId())
                    .status(event.getStatus())
                    .musterRollId(event.getMusterRollId())
                    .lastModifiedTime(event.getEventTime())
                    .build();

            // 6. Merge with existing period_statuses
            List<RegisterPeriodStatus> updatedPeriodStatuses = mergePeriodStatus(
                    register.getPeriodStatuses(),
                    newPeriodStatus
            );

            // 7. Update register object
            register.setPeriodStatuses(updatedPeriodStatuses);

            // 8. Publish to persister topic (using same pattern as register updates)
            publishRegisterUpdate(register, event.getTenantId());

            log.info("processMusterRollStatusUpdate::Successfully published update for register: {} period: {} new status: {}",
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
        }
    }

    /**
     * Fetch register from repository
     */
    private AttendanceRegister fetchRegister(String registerId, String tenantId) {
        try {
            AttendanceRegisterSearchCriteria criteria = AttendanceRegisterSearchCriteria.builder()
                    .ids(Collections.singletonList(registerId))
                    .tenantId(tenantId)
                    .build();

            List<AttendanceRegister> registers = registerRepository.getRegister(criteria);

            if (registers == null || registers.isEmpty()) {
                log.warn("fetchRegister::Register not found for ID: {}", registerId);
                return null;
            }

            return registers.get(0);

        } catch (Exception e) {
            log.error("fetchRegister::Error fetching register: {} - Error: {}", registerId, e.getMessage(), e);
            return null;
        }
    }

    /**
     * Merges new period status into existing list.
     * If status for the period already exists, it's updated.
     * If not, it's appended to the list.
     */
    private List<RegisterPeriodStatus> mergePeriodStatus(
            List<RegisterPeriodStatus> existing,
            RegisterPeriodStatus newStatus) {

        String periodId = newStatus.getPeriodId();

        // Handle null existing list
        if (existing == null) {
            existing = new ArrayList<>();
        }

        // Check if status for this period already exists
        boolean periodExists = existing.stream()
                .anyMatch(s -> s.getPeriodId().equals(periodId));

        if (periodExists) {
            // Update existing period status
            log.debug("mergePeriodStatus::Updating existing status for period: {}", periodId);
            return existing.stream()
                    .map(s -> s.getPeriodId().equals(periodId) ? newStatus : s)
                    .collect(Collectors.toList());
        } else {
            // Add new period status
            log.debug("mergePeriodStatus::Adding new status for period: {}", periodId);
            List<RegisterPeriodStatus> updated = new ArrayList<>(existing);
            updated.add(newStatus);
            return updated;
        }
    }

    /**
     * Publish register update to persister topic
     * Uses the same pattern as AttendanceRegisterService
     * Reuses the existing update-attendance topic (period_statuses field added to persister config)
     */
    private void publishRegisterUpdate(AttendanceRegister register, String tenantId) {
        try {
            // Build request following same pattern as AttendanceRegisterService
            RequestInfo requestInfo = RequestInfo.builder()
                    .build();

            AttendanceRegisterRequest request = AttendanceRegisterRequest.builder()
                    .requestInfo(requestInfo)
                    .attendanceRegister(Collections.singletonList(register))
                    .build();

            // Publish to update-attendance topic (reusing existing topic)
            // Persister config already includes period_statuses field in the UPDATE query
            String topic = "update-attendance";
            producer.push(topic, request);

            log.debug("publishRegisterUpdate::Published to topic: {} for register: {}", topic, register.getId());

        } catch (Exception e) {
            log.error("publishRegisterUpdate::Failed to publish update for register: {} - Error: {}",
                    register.getId(), e.getMessage(), e);
            throw e;
        }
    }
}
