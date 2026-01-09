package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * AttendanceRegister
 */
@Validated

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceRegister {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("registerNumber")
    private String registerNumber = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("referenceId")
    private String referenceId;

    @JsonProperty("serviceCode")
    private String serviceCode;

    @JsonProperty("startDate")
    private BigDecimal startDate = null;

    @JsonProperty("endDate")
    private BigDecimal endDate = null;

    @JsonProperty("status")
    private Status status = Status.ACTIVE;

    @JsonProperty("staff")
    @Valid
    private List<StaffPermission> staff = null;

    @JsonProperty("attendees")
    @Valid
    private List<IndividualEntry> attendees = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    @JsonProperty("localityCode")
    private String localityCode = null;

    @JsonProperty("reviewStatus")
    private String reviewStatus = null;

    /**
     * V2 Intermediate Billing - Register Period Status
     *
     * Indicates the muster roll status for this register within a specific billing period.
     * Only populated when billingPeriodId is provided in search criteria.
     *
     * Possible values:
     * - "NOT_CREATED": No muster roll exists for this register+period combination
     * - "PENDING": Muster roll created but awaiting approval
     * - "APPROVED": Muster roll approved, ready for billing
     * - "REJECTED": Muster roll rejected
     * - "SENT_BACK": Muster roll sent back for corrections
     *
     * This field helps UI determine which registers are ready for bill generation.
     */
    @JsonProperty("registerPeriodStatus")
    private String registerPeriodStatus = null;

    /**
     * V2 Intermediate Billing - Period Statuses (Event-Driven Storage)
     *
     * Stores muster roll statuses for all billing periods associated with this register.
     * Stored as JSONB array in database: eg_wms_attendance_register.period_statuses
     *
     * Updated asynchronously via Kafka events when muster-roll service changes status.
     * This eliminates synchronous API calls during search, improving scalability.
     *
     * Structure: List of RegisterPeriodStatus objects
     * Example: [
     *   {"periodId": "uuid-1", "status": "APPROVED", "musterRollId": "muster-1", "lastModifiedTime": 1709884800000},
     *   {"periodId": "uuid-2", "status": "PENDING", "musterRollId": "muster-2", "lastModifiedTime": 1709971200000}
     * ]
     *
     * This field is:
     * - Persisted to database as JSONB
     * - Indexed for fast queries (GIN index)
     * - Updated by MusterRollStatusUpdateConsumer
     * - Read during V2 search operations
     */
    @JsonProperty("periodStatuses")
    @Valid
    private List<RegisterPeriodStatus> periodStatuses = null;

    public AttendanceRegister addStaffItem(StaffPermission staffItem) {
        if (this.staff == null) {
            this.staff = new ArrayList<>();
        }
        this.staff.add(staffItem);
        return this;
    }

    public AttendanceRegister addAttendeesItem(IndividualEntry attendeesItem) {
        if (this.attendees == null) {
            this.attendees = new ArrayList<>();
        }
        this.attendees.add(attendeesItem);
        return this;
    }

}

