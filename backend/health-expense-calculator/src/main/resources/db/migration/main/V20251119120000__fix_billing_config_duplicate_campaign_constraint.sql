-- =============================================================================
-- Migration: Fix billing config constraint for duplicate campaign support
-- Date     : 2025-11-19
-- Purpose  : Remove old project_id constraint to allow same projects in
--            different campaigns (campaign duplication support)
-- =============================================================================
-- Issue    : When duplicating a campaign (C1 -> C2), the project hierarchy
--            remains the same (p1.p2.p3), but the campaign_number changes.
--            The old constraint uk_billing_config_project (project_id, tenant_id)
--            prevents creating billing config for the duplicated campaign
--            because it treats project_id as globally unique per tenant.
-- Solution : Drop the old project-based constraint and rely on the
--            campaign-based constraint uk_billing_config_campaign which
--            correctly allows same project_id across different campaigns.
-- =============================================================================

-- -----------------------------------------------------------------------------
-- Step 1: Drop the old project-based unique constraint
-- -----------------------------------------------------------------------------

DO $$
BEGIN
    -- Check if the old constraint exists
    IF EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'uk_billing_config_project'
        AND conrelid = 'eg_expense_billing_config'::regclass
    ) THEN
        -- Drop the constraint that prevents campaign duplication
        ALTER TABLE eg_expense_billing_config DROP CONSTRAINT uk_billing_config_project;
        RAISE NOTICE 'Dropped constraint: uk_billing_config_project';
    ELSE
        RAISE NOTICE 'Constraint uk_billing_config_project does not exist, skipping';
    END IF;
END$$;

-- -----------------------------------------------------------------------------
-- Step 2: Verify the campaign-based constraint exists
-- -----------------------------------------------------------------------------

DO $$
BEGIN
    -- Ensure the campaign-based constraint exists (should be from V20250212160000)
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'uk_billing_config_campaign'
        AND conrelid = 'eg_expense_billing_config'::regclass
    ) THEN
        -- If for some reason it doesn't exist, create it
        ALTER TABLE eg_expense_billing_config
            ADD CONSTRAINT uk_billing_config_campaign
            UNIQUE (tenant_id, campaign_number);
        RAISE NOTICE 'Created constraint: uk_billing_config_campaign';
    ELSE
        RAISE NOTICE 'Constraint uk_billing_config_campaign already exists';
    END IF;
END$$;

-- -----------------------------------------------------------------------------
-- Step 3: Drop the old project-based index (redundant after constraint drop)
-- -----------------------------------------------------------------------------

DO $$
BEGIN
    -- Drop the old index if it exists (created in V20250131120000)
    IF EXISTS (
        SELECT 1 FROM pg_indexes
        WHERE indexname = 'idx_billing_config_project'
        AND tablename = 'eg_expense_billing_config'
    ) THEN
        DROP INDEX IF EXISTS idx_billing_config_project;
        RAISE NOTICE 'Dropped index: idx_billing_config_project';
    ELSE
        RAISE NOTICE 'Index idx_billing_config_project does not exist, skipping';
    END IF;
END$$;

-- -----------------------------------------------------------------------------
-- Step 4: Add composite index for common query patterns
-- -----------------------------------------------------------------------------

-- Create a new composite index for queries filtering by project and campaign
-- This supports queries like "find config for project X in campaign Y"
CREATE INDEX IF NOT EXISTS idx_billing_config_project_campaign
    ON eg_expense_billing_config (project_id, campaign_number, tenant_id);

COMMENT ON INDEX idx_billing_config_project_campaign IS
    'Composite index for queries filtering by project and campaign together';

-- =============================================================================
-- Migration Summary:
-- 1. Dropped old constraint uk_billing_config_project (project_id, tenant_id)
--    - This constraint prevented duplicate campaigns with same projects
-- 2. Verified campaign-based constraint uk_billing_config_campaign exists
--    - This constraint allows same project_id in different campaigns
--    - Ensures campaign_number is unique per tenant
-- 3. Dropped old redundant index idx_billing_config_project
-- 4. Added new composite index for project+campaign queries
--
-- Result:
-- - Campaign duplication (C1 -> C2) with same project hierarchy now works
-- - Each campaign can have its own billing configuration
-- - Same projects (p1, p2, p3) can exist in multiple campaigns (C1, C2, C3)
-- - Billing configs remain unique per campaign per tenant
-- =============================================================================
