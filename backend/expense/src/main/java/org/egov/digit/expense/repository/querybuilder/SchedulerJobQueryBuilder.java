package org.egov.digit.expense.repository.querybuilder;

import org.egov.common.exception.InvalidTenantIdException;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.egov.common.utils.MultiStateInstanceUtil.SCHEMA_REPLACE_STRING;

@Component
public class SchedulerJobQueryBuilder {

    private static final String TABLE = SCHEMA_REPLACE_STRING + ".eg_expense_scheduler_job";

    /** Insert a new scheduler job row. Duplicate jobs (same tenant+type+reference) are silently ignored. */
    static final String INSERT =
            "INSERT INTO " + TABLE +
            " (id, tenant_id, job_type, reference_id, scheduler_status, next_check_at," +
            "  attempt_count, max_attempts, context, created_at, updated_at)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
            " ON CONFLICT (tenant_id, job_type, reference_id)" +
            " WHERE scheduler_status IN ('PENDING', 'PROCESSING')" +
            " DO NOTHING";

    /**
     * Atomically claim a batch of PENDING jobs using a CTE.
     * Parameters: updatedAt (long), nowMs (long), batchSize (int)
     */
    static final String CLAIM_BATCH =
            "WITH claimed AS (" +
            "  UPDATE " + TABLE +
            "  SET scheduler_status = 'PROCESSING', updated_at = ?" +
            "  WHERE id IN (" +
            "    SELECT id FROM " + TABLE +
            "    WHERE scheduler_status = 'PENDING'" +
            "      AND (next_check_at IS NULL OR next_check_at <= ?)" +
            "    ORDER BY next_check_at ASC NULLS FIRST" +
            "    LIMIT ?" +
            "    FOR UPDATE SKIP LOCKED" +
            "  )" +
            "  RETURNING *" +
            ") SELECT * FROM claimed";

    /**
     * Update scheduler state fields for a single job after processing.
     * Only updates rows still in PROCESSING — prevents a late pod from overwriting
     * a status already set by recovery + a different pod (batch-tail race).
     * Parameters: schedulerStatus, nextCheckAt, attemptCount, updatedAt, id
     */
    static final String UPDATE_STATUS =
            "UPDATE " + TABLE +
            " SET scheduler_status = ?, next_check_at = ?, attempt_count = ?, updated_at = ?" +
            " WHERE id = ? AND scheduler_status = 'PROCESSING'";

    /**
     * Reset PROCESSING → PENDING for jobs stuck longer than the threshold (crash recovery).
     * Parameters: nextCheckAt (now + backoff), updatedAt (now), stuckCutoff (updatedAt threshold)
     */
    static final String RECOVER_STUCK =
            "UPDATE " + TABLE +
            " SET scheduler_status = 'PENDING', next_check_at = ?, updated_at = ?" +
            " WHERE scheduler_status = 'PROCESSING'" +
            "   AND updated_at < ?";

    /**
     * Delete completed jobs older than the cleanup threshold.
     * Parameters: cutoffMs
     */
    static final String CLEANUP =
            "DELETE FROM " + TABLE +
            " WHERE scheduler_status IN ('DONE', 'FAILED')" +
            "   AND updated_at < ?";

    /**
     * Find all BILL_STATUS_POLL jobs belonging to a batch (by batchId stored in JSONB context).
     * Used by BILL_BATCH_EMAIL coordinator to check settlement of all sibling jobs.
     * Parameters: batchId (String)
     */
    static final String FIND_BY_BATCH_ID =
            "SELECT * FROM " + TABLE +
            " WHERE context->>'batchId' = ? AND job_type = 'BILL_STATUS_POLL'";

    private final MultiStateInstanceUtil multiStateInstanceUtil;

    @Autowired
    public SchedulerJobQueryBuilder(MultiStateInstanceUtil multiStateInstanceUtil) {
        this.multiStateInstanceUtil = multiStateInstanceUtil;
    }

    public String insert(String tenantId) throws InvalidTenantIdException {
        return multiStateInstanceUtil.replaceSchemaPlaceholder(INSERT, tenantId);
    }

    public String claimBatch(String tenantId) throws InvalidTenantIdException {
        return multiStateInstanceUtil.replaceSchemaPlaceholder(CLAIM_BATCH, tenantId);
    }

    public String updateStatus(String tenantId) throws InvalidTenantIdException {
        return multiStateInstanceUtil.replaceSchemaPlaceholder(UPDATE_STATUS, tenantId);
    }

    public String recoverStuck(String tenantId) throws InvalidTenantIdException {
        return multiStateInstanceUtil.replaceSchemaPlaceholder(RECOVER_STUCK, tenantId);
    }

    public String cleanup(String tenantId) throws InvalidTenantIdException {
        return multiStateInstanceUtil.replaceSchemaPlaceholder(CLEANUP, tenantId);
    }

    public String findByBatchId(String tenantId) throws InvalidTenantIdException {
        return multiStateInstanceUtil.replaceSchemaPlaceholder(FIND_BY_BATCH_ID, tenantId);
    }
}
