package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

/**
 * RegisterPeriodStatus
 *
 * Represents the muster roll status for a specific billing period.
 * This is stored as part of the period_statuses JSONB array in eg_wms_attendance_register.
 *
 * Updated asynchronously via Kafka events when muster-roll service changes status.
 * This eliminates the need for synchronous API calls during attendance search operations.
 *
 * Example JSON structure in DB:
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
     * Possible values:
     * - "NOT_CREATED": No muster roll exists for this register+period
     * - "PENDING": Muster roll exists but not yet approved
     * - "APPROVED": Muster roll approved and ready for billing
     * - "REJECTED": Muster roll rejected
     * - "SENT_BACK": Muster roll sent back for corrections
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

    /**
     * Status constants for validation and consistency
     */
    public static final String STATUS_NOT_CREATED = "NOT_CREATED";
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_APPROVED = "APPROVED";
    public static final String STATUS_REJECTED = "REJECTED";
    public static final String STATUS_SENT_BACK = "SENT_BACK";
}