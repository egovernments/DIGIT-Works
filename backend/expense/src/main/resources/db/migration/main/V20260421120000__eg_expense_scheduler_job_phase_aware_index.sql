-- Replace phase-unaware partial unique index with a phase-aware expression index.
-- Root cause: BILL_STATUS_POLL (and other multi-phase job types) share job_type + reference_id
-- across phases. The old index silently dropped a later-phase insert (DO NOTHING) when an
-- earlier-phase job was still PENDING/PROCESSING for the same bill, so the later phase job
-- was never created and its notification never fired.
-- Fix: include COALESCE(context->>'phase', '') in the index key so each phase is a distinct slot.
-- Strategy: create new index BEFORE dropping old to eliminate any constraint-free window.

CREATE UNIQUE INDEX IF NOT EXISTS uq_scheduler_job_active_v2
    ON eg_expense_scheduler_job (tenant_id, job_type, reference_id, (COALESCE(context->>'phase', '')))
    WHERE scheduler_status IN ('PENDING', 'PROCESSING');

DROP INDEX IF EXISTS uq_scheduler_job_active;

ALTER INDEX uq_scheduler_job_active_v2 RENAME TO uq_scheduler_job_active;
