-- ============================================================
-- HCM Payments V2: Add Billing Period Support to Muster Roll
-- Version: 2.0
-- Date: 2025-02-01
-- Description: Add billing_period_id column to support intermediate billing
-- Backward Compatible: YES (nullable column, NULL = V1 muster rolls)
-- ============================================================

-- Step 1: Add billing_period_id column (nullable for backward compatibility)
ALTER TABLE eg_wms_muster_roll
ADD COLUMN IF NOT EXISTS billing_period_id VARCHAR(64);

-- Step 2: Add comment for documentation
COMMENT ON COLUMN eg_wms_muster_roll.billing_period_id IS
'V2: Links muster roll to billing period (NULL for V1 muster rolls, populated for V2 intermediate billing)';

-- Step 3: Create index for period-based queries (performance optimization)
CREATE INDEX IF NOT EXISTS idx_muster_roll_period
ON eg_wms_muster_roll (billing_period_id);

-- Step 4: Create composite index for duplicate checking (register + period)
-- This enables fast duplicate prevention: "Does muster roll exist for this register in this period?"
CREATE INDEX IF NOT EXISTS idx_muster_roll_register_period
ON eg_wms_muster_roll (attendance_register_id, billing_period_id);

-- Step 5: Create index for filtering by billing period with tenant
CREATE INDEX IF NOT EXISTS idx_muster_roll_period_tenant
ON eg_wms_muster_roll (billing_period_id, tenant_id);

