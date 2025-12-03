-- =============================================================================
-- Migration: Add campaign_number columns to billing entities (non-destructive)
-- Date     : 2025-02-12
-- Purpose  : Introduce campaign-level metadata while retaining existing
--            project-based constraints for backward compatibility.
-- =============================================================================

-- -----------------------------------------------------------------------------
-- eg_expense_billing_config: add and populate campaign_number
-- -----------------------------------------------------------------------------
-- =============================================================================
-- Migration: Add campaign_number columns to billing entities (non-destructive)
-- Date     : 2025-02-12
-- =============================================================================

-- eg_expense_billing_config
ALTER TABLE eg_expense_billing_config
    ADD COLUMN IF NOT EXISTS campaign_number VARCHAR(128);

UPDATE eg_expense_billing_config
SET campaign_number = project_id
WHERE campaign_number IS NULL;

ALTER TABLE eg_expense_billing_config
    ALTER COLUMN campaign_number SET NOT NULL;

DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1
    FROM pg_constraint c
    WHERE c.conname = 'uk_billing_config_campaign'
      AND c.conrelid = 'eg_expense_billing_config'::regclass
  ) THEN
    ALTER TABLE eg_expense_billing_config
      ADD CONSTRAINT uk_billing_config_campaign
      UNIQUE (tenant_id, campaign_number);
  END IF;
END$$;

-- keep only if you need this column order; otherwise it’s redundant with the unique index
CREATE INDEX IF NOT EXISTS idx_billing_config_campaign
    ON eg_expense_billing_config (campaign_number, tenant_id);

COMMENT ON COLUMN eg_expense_billing_config.campaign_number IS
    'Campaign identifier for which the billing configuration applies';

-- eg_wms_billing_period
ALTER TABLE eg_wms_billing_period
    ADD COLUMN IF NOT EXISTS campaign_number VARCHAR(128);

UPDATE eg_wms_billing_period
SET campaign_number = project_id
WHERE campaign_number IS NULL;

ALTER TABLE eg_wms_billing_period
    ALTER COLUMN campaign_number SET NOT NULL;

DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1
    FROM pg_constraint c
    WHERE c.conname = 'uk_billing_period_campaign'
      AND c.conrelid = 'eg_wms_billing_period'::regclass
  ) THEN
    ALTER TABLE eg_wms_billing_period
      ADD CONSTRAINT uk_billing_period_campaign
      UNIQUE (tenant_id, campaign_number, period_number);
  END IF;
END$$;

-- keep only if you need this column order; otherwise potentially redundant
CREATE INDEX IF NOT EXISTS idx_billing_period_campaign
    ON eg_wms_billing_period (campaign_number, tenant_id);

COMMENT ON COLUMN eg_wms_billing_period.campaign_number IS
    'Campaign identifier this billing period belongs to';


-- =============================================================================
-- End of migration
-- =============================================================================
