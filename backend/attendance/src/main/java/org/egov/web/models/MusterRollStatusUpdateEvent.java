package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

/**
 * MusterRollStatusUpdateEvent
 *
 * Event published by muster-roll service when status changes.
 * Consumed by attendance service to update period_statuses asynchronously.
 *
 * This event-driven architecture eliminates the need for synchronous API calls
 * during attendance register search operations, improving scalability.
 *
 * Published by: Muster-roll Service - MusterRollService
 * Consumed by: Attendance Service - MusterRollStatusUpdateConsumer
 * Topic: muster-roll-status-update
 */
@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusterRollStatusUpdateEvent {

    /**
     * Muster roll identifier
     */
    @JsonProperty("musterRollId")
    @NotNull
    private String musterRollId;

    /**
     * Attendance register identifier
     * Used to identify which register's period_statuses to update
     */
    @JsonProperty("registerId")
    @NotNull
    private String registerId;

    /**
     * Billing period identifier
     * NULL for V1 muster rolls (non-periodic)
     */
    @JsonProperty("billingPeriodId")
    private String billingPeriodId;

    /**
     * New muster roll status
     *
     * Possible values:
     * - "PENDING": Muster roll created but not approved
     * - "APPROVED": Muster roll approved
     * - "REJECTED": Muster roll rejected
     * - "SENT_BACK": Muster roll sent back for corrections
     */
    @JsonProperty("status")
    @NotNull
    private String status;

    /**
     * Tenant identifier for multi-tenancy support
     */
    @JsonProperty("tenantId")
    @NotNull
    private String tenantId;

    /**
     * Timestamp of status change (epoch milliseconds)
     */
    @JsonProperty("eventTime")
    @NotNull
    private Long eventTime;

    /**
     * Previous status (for audit/debugging)
     * NULL if this is the first status
     */
    @JsonProperty("previousStatus")
    private String previousStatus;

    /**
     * Reference ID (project/contract)
     * Used for logging and correlation
     */
    @JsonProperty("referenceId")
    private String referenceId;

    /**
     * Event metadata for troubleshooting
     */
    @JsonProperty("additionalDetails")
    private Object additionalDetails;
}
