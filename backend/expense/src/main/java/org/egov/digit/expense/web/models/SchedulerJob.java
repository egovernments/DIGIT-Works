package org.egov.digit.expense.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.digit.expense.web.models.enums.SchedulerJobStatus;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;

/**
 * Represents a row in {@code eg_expense_scheduler_job}.
 * Generic scheduler entity — decoupled from any specific business domain table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchedulerJob {

    /** UUID primary key. */
    private String id;

    /** Tenant (schema) this job belongs to. */
    private String tenantId;

    /** Determines which {@code SchedulerJobHandler} processes this job. */
    private SchedulerJobType jobType;

    /** ID of the domain entity being tracked (e.g. task_id, bill_id). */
    private String referenceId;

    /** Scheduler lifecycle status. */
    private SchedulerJobStatus schedulerStatus;

    /**
     * Epoch-ms at which this job should next be polled.
     * {@code null} means "pick up immediately".
     */
    private Long nextCheckAt;

    /** Number of times this job has been processed by the scheduler. */
    private int attemptCount;

    /** Maximum allowed attempts before marking FAILED. */
    private int maxAttempts;

    /**
     * Job-specific payload stored as a free-form object (serialised to JSONB).
     * For TASK_STATUS_CHECK this holds the {@code RequestInfo} needed to call MTN.
     */
    private Object context;

    private long createdAt;
    private long updatedAt;
}
