-- =============================================================================
-- Migration: Fix billing period constraints to allow config updates
-- Date     : 2025-02-13
-- Purpose  : Allow updating billing configurations by removing restrictive
--            unique constraints and adding config-specific constraints
-- =============================================================================

-- -----------------------------------------------------------------------------
-- Step 1: Drop overly restrictive unique constraints
-- -----------------------------------------------------------------------------

ALTER TABLE eg_wms_billing_period DROP CONSTRAINT IF EXISTS uk_billing_period;
ALTER TABLE eg_wms_billing_period DROP CONSTRAINT IF EXISTS uk_billing_period_campaign;

-- -----------------------------------------------------------------------------
-- Step 2: Add config-scoped unique constraint (periods unique within each config)
-- -----------------------------------------------------------------------------
-- DROP-then-ADD keeps this idempotent without a DO guard, since ADD CONSTRAINT has no IF NOT EXISTS
ALTER TABLE eg_wms_billing_period DROP CONSTRAINT IF EXISTS uk_billing_period_config_number;
ALTER TABLE eg_wms_billing_period
    ADD CONSTRAINT uk_billing_period_config_number UNIQUE (billing_config_id, period_number, tenant_id);

-- Native IF NOT EXISTS scopes to search_path; information_schema check would span all schemas and skip wrongly
ALTER TABLE eg_wms_billing_period
    ADD COLUMN IF NOT EXISTS is_deprecated BOOLEAN DEFAULT FALSE NOT NULL;

-- Create index for querying non-deprecated periods
CREATE INDEX IF NOT EXISTS idx_billing_period_deprecated
    ON eg_wms_billing_period (is_deprecated, billing_config_id);

COMMENT ON COLUMN eg_wms_billing_period.is_deprecated IS
    'Flag indicating if this period is deprecated due to billing config update';

-- -----------------------------------------------------------------------------
-- Step 4: Note on Billing Config Updates
-- -----------------------------------------------------------------------------
-- Billing configs are IDEMPOTENT - they should be updated in place
-- No versioning or deprecation at config level
-- Only periods are deprecated when frequency/dates change

-- =============================================================================
-- Migration Summary:
-- 1. Removed restrictive unique constraints that prevented config updates
-- 2. Added config-scoped unique constraint (periods unique within each config)
-- 3. Added deprecation tracking for periods only
--
-- This allows:
-- - Updating billing configurations in place (idempotent updates)
-- - Multiple sets of periods per project (old deprecated + new active)
-- - Periods are deprecated and regenerated only when frequency/dates change
-- - Config unique constraint remains on (project_id, tenant_id)
-- =============================================================================
