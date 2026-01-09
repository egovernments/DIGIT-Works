package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

/**
 * RegisterPeriodStatus
 *
 * ================================================================================
 * PURPOSE
 * ================================================================================
 *
 * Represents the muster roll status for a SPECIFIC billing period within an
 * attendance register. Stored as part of the period_statuses JSONB array in
 * eg_wms_attendance_register table.
 *
 * WHY THIS EXISTS:
 * ----------------
 * In V1: One register → One muster roll → One status (reviewStatus field)
 * In V2: One register → MULTIPLE muster rolls → MULTIPLE statuses (period_statuses array)
 *
 * Each entry in period_statuses tracks:
 *   - Which billing period
 *   - What is the muster roll status for that period
 *   - Which muster roll ID (for reference)
 *   - When was it last updated
 *
 * HOW IT'S UPDATED:
 * -----------------
 * When muster roll workflow status changes in muster-roll service:
 *   1. Muster-roll service publishes Kafka event
 *   2. MusterRollStatusUpdateConsumer receives event
 *   3. Consumer updates this field via persister
 *
 * This is EVENT-DRIVEN denormalization for performance (no API calls during search).
 *
 * ================================================================================
 * EXAMPLE JSON IN DATABASE
 * ================================================================================
 *
 * [
 *   {
 *     "periodId": "period-uuid-1",
 *     "status": "APPROVED",
 *     "musterRollId": "muster-uuid-1",
 *     "lastModifiedTime": 1709884800000
 *   },
 *   {
 *     "periodId": "period-uuid-2",
 *     "status": "PENDING",
 *     "musterRollId": "muster-uuid-2",
 *     "lastModifiedTime": 1709971200000
 *   }
 * ]
 *
 * ================================================================================
 */
@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterPeriodStatus {

    /**
     * Billing period identifier
     * Links to eg_wms_billing_period.id
     */
    @JsonProperty("periodId")
    @NotNull
    private String periodId;

    /**
     * Muster roll status for this period
     *
     */
    @JsonProperty("status")
    @NotNull
    private String status;

    /**
     * Muster roll identifier
     * Links to eg_wms_muster_roll.id
     * NULL when status is "NOT_CREATED"
     */
    @JsonProperty("musterRollId")
    private String musterRollId;

    /**
     * Timestamp of last status update (epoch milliseconds)
     * Used for cache invalidation and audit purposes
     */
    @JsonProperty("lastModifiedTime")
    @NotNull
    private Long lastModifiedTime;
}