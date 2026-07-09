-- RC-1: Replace full unique constraint with partial unique index so that
-- completed (DONE/FAILED) rows do not block re-insertion for the same
-- (tenant, type, reference) in a subsequent workflow phase.
-- IMPORTANT: Deploy immediately followed by a rolling-restart so that old-code
-- pods (using ON CONFLICT ON CONSTRAINT) are replaced before they need to insert.

ALTER TABLE eg_expense_scheduler_job
    DROP CONSTRAINT IF EXISTS uq_scheduler_job_active;

CREATE UNIQUE INDEX IF NOT EXISTS uq_scheduler_job_active
    ON eg_expense_scheduler_job (tenant_id, job_type, reference_id)
    WHERE scheduler_status IN ('PENDING', 'PROCESSING');
