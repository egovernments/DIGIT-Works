-- =============================================================================
-- Migration: Fix billing period constraints to allow config updates
-- Date     : 2025-02-13
-- Purpose  : Allow updating billing configurations by removing restrictive
--            unique constraints and adding config-specific constraints
-- =============================================================================

-- -----------------------------------------------------------------------------
-- Step 1: Drop overly restrictive unique constraints
-- -----------------------------------------------------------------------------

-- Drop the project_id based unique constraint (from V20250131120000)
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'uk_billing_period'
        AND conrelid = 'eg_wms_billing_period'::regclass
    ) THEN
        ALTER TABLE eg_wms_billing_period DROP CONSTRAINT uk_billing_period;
        RAISE NOTICE 'Dropped constraint: uk_billing_period';
    END IF;
END$$;

-- Drop the campaign_number based unique constraint (from V20250212160000)
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'uk_billing_period_campaign'
        AND conrelid = 'eg_wms_billing_period'::regclass
    ) THEN
        ALTER TABLE eg_wms_billing_period DROP CONSTRAINT uk_billing_period_campaign;
        RAISE NOTICE 'Dropped constraint: uk_billing_period_campaign';
    END IF;
END$$;

-- -----------------------------------------------------------------------------
-- Step 2: Add new constraint scoped to billing_config_id
-- -----------------------------------------------------------------------------

-- This allows multiple configs for same project (e.g., when updating config)
-- but ensures period numbers are unique within each configuration version
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'uk_billing_period_config_number'
        AND conrelid = 'eg_wms_billing_period'::regclass
    ) THEN
        ALTER TABLE eg_wms_billing_period
            ADD CONSTRAINT uk_billing_period_config_number
            UNIQUE (billing_config_id, period_number, tenant_id);
        RAISE NOTICE 'Added constraint: uk_billing_period_config_number';
    END IF;
END$$;

-- -----------------------------------------------------------------------------
-- Step 3: Add deprecation tracking field
-- -----------------------------------------------------------------------------

-- Add is_deprecated flag to track old periods when config is updated
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'eg_wms_billing_period'
        AND column_name = 'is_deprecated'
    ) THEN
        ALTER TABLE eg_wms_billing_period
            ADD COLUMN is_deprecated BOOLEAN DEFAULT FALSE NOT NULL;
        RAISE NOTICE 'Added column: is_deprecated';
    END IF;
END$$;

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
