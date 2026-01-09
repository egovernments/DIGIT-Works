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
import org.springframework.beans.factory.annotation.Value;
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
 * ================================================================================
 * PURPOSE & BUSINESS CONTEXT
 * ================================================================================
 *
 * In V2 Intermediate Billing, we need to track muster roll status for EACH billing
 * period separately (unlike V1 where one register had one overall status).
 *
 * PROBLEM SOLVED:
 * ---------------
 * In V1 flow, when UI needed to show register status, it would make synchronous
 * API calls to muster-roll service for EACH register. With thousands of registers,
 * this caused:
 *   - Slow search responses (N API calls for N registers)
 *   - Timeout issues under load
 *   - Poor user experience
 *
 * SOLUTION - Event-Driven Denormalization:
 * ----------------------------------------
 * Instead of making API calls during search, we STORE the muster roll status
 * directly in the attendance register's `period_statuses` JSONB field.
 *
 * When muster roll workflow changes status (e.g., PENDING → APPROVED):
 *   1. Muster-roll service publishes event to Kafka topic
 *   2. This consumer receives the event
 *   3. Updates the attendance register's period_statuses field via persister
 *   4. Search API reads status from DB directly - NO API calls needed
 *
 * DATA FLOW:
 * ----------
 *   Muster Roll Workflow (APPROVED)
 *           ↓
 *   Kafka: muster-roll-status-update
 *           ↓
 *   This Consumer
 *           ↓
 *   Kafka: update-attendance (persister topic)
 *           ↓
 *   Database: eg_attendance_register.period_statuses
 *
 * IMPORTANT: This is NOT a workflow implementation. The actual workflow
 * (PENDING → APPROVED → etc.) is managed by muster-roll service. This consumer
 * simply MIRRORS that status into the attendance register for efficient searching.
 *
 * ================================================================================
 *
 * Topic consumed: muster-roll-status-update
 * Publisher: Muster-roll Service (on workflow status change)
 * Publishes to: update-attendance (persister updates period_statuses field)
 */
@Component
@Slf4j
public class MusterRollStatusUpdateConsumer {

    private final ObjectMapper objectMapper;
    private final RegisterRepository registerRepository;
    private final Producer producer;
    private final String updateAttendanceRegisterTopic;

    @Autowired
    public MusterRollStatusUpdateConsumer(
            ObjectMapper objectMapper,
            RegisterRepository registerRepository,
            Producer producer,
            @Value("${attendance.register.kafka.update.topic}") String updateAttendanceRegisterTopic) {
        this.objectMapper = objectMapper;
        this.registerRepository = registerRepository;
        this.producer = producer;
        this.updateAttendanceRegisterTopic = updateAttendanceRegisterTopic;
    }

    /**
     * Consumes muster-roll status update events and updates period_statuses via persister.
     *
     * ================================================================================
     * WHY THIS METHOD EXISTS
     * ================================================================================
     *
     * When a muster roll's workflow status changes (e.g., supervisor approves attendance),
     * the muster-roll service publishes an event. This method:
     *
     *   1. Receives that event
     *   2. Finds the corresponding attendance register
     *   3. Updates the register's period_statuses JSONB field with the new status
     *   4. Publishes to persister topic to save to database
     *
     * This enables the attendance search API to return muster roll status WITHOUT
     * making synchronous API calls to muster-roll service (major performance improvement).
     *
     * ================================================================================
     * V1 vs V2 EVENT HANDLING
     * ================================================================================
     *
     * V1 (Legacy): Events without billingPeriodId are from V1 flow where:
     *   - One register = one muster roll = one status
     *   - Status stored in register's reviewStatus field
     *   - No period_statuses needed
     *   → We SKIP V1 events because they don't need period-based tracking
     *
     * V2 (New): Events WITH billingPeriodId are from V2 intermediate billing where:
     *   - One register can have MULTIPLE muster rolls (one per billing period)
     *   - Each period has its own status in period_statuses array
     *   → We PROCESS V2 events to maintain period_statuses
     *
     * ================================================================================
     * ERROR HANDLING STRATEGY
     * ================================================================================
     *
     * - Deserialization errors: Log and skip (message format issue)
     * - Missing fields: Log and skip (incomplete event)
     * - Register not found: Log and skip (register may have been deleted)
     * - General errors: Log full context, don't crash consumer
     *
     * SELF-HEALING: If an event fails, the next valid event for the same
     * register+period will update the status correctly. Additionally, the
     * RegisterPeriodEnrichmentService has a fallback that calls muster-roll
     * API if period_statuses is missing (handles Kafka sync failures).
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

            // ============================================================
            // STEP 2: Skip V1 events - they don't need period tracking
            // ============================================================
            // WHY: V1 flow uses the register's single reviewStatus field.
            //      V2 flow uses period_statuses array (one status per period).
            //      Events without billingPeriodId are V1 events.
            //      Processing them here would corrupt period_statuses with null periodId.
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
            // ============================================================
            // Deserialization Error - Log and Skip
            // ============================================================
            // WHY THIS CAN HAPPEN:
            //   - Kafka message JSON doesn't match MusterRollStatusUpdateEvent structure
            //   - Schema mismatch between producer (muster-roll) and consumer (attendance)
            //   - Corrupted message in Kafka
            //
            // RECOVERY PLAN:
            //   - Log full context for debugging
            //   - Skip this message (don't block consumer)
            //   - SELF-HEALING: Next valid event for same register+period will update status
            //   - FALLBACK: RegisterPeriodEnrichmentService calls muster-roll API if status missing
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
     * Merges new period status into existing period_statuses list.
     *
     * ================================================================================
     * WHY MERGE IS NEEDED (UPSERT LOGIC)
     * ================================================================================
     *
     * A single attendance register can have multiple billing periods, each with
     * its own muster roll status. The period_statuses array looks like:
     *
     *   [
     *     { "periodId": "period-1", "status": "APPROVED", ... },
     *     { "periodId": "period-2", "status": "PENDING", ... },
     *     { "periodId": "period-3", "status": "NOT_CREATED", ... }
     *   ]
     *
     * When we receive a status update event for period-2:
     *   - If period-2 exists in array → UPDATE its status (replace)
     *   - If period-2 doesn't exist → APPEND new entry
     *
     * This is standard UPSERT (Update or Insert) logic to maintain the list.
     *
     * ================================================================================
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
                .anyMatch(s -> periodId.equals(s.getPeriodId()));

        if (periodExists) {
            // Update existing period status
            log.debug("mergePeriodStatus::Updating existing status for period: {}", periodId);
            return existing.stream()
                    .map(s -> periodId.equals(s.getPeriodId()) ? newStatus : s)
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
            producer.push(updateAttendanceRegisterTopic, request);

            log.debug("publishRegisterUpdate::Published to topic: {} for register: {}", updateAttendanceRegisterTopic, register.getId());

        } catch (Exception e) {
            log.error("publishRegisterUpdate::Failed to publish update for register: {} - Error: {}",
                    register.getId(), e.getMessage(), e);
            throw e;
        }
    }
}
