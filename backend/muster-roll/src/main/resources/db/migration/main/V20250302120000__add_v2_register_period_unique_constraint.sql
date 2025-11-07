-- ============================================================
-- HCM Payments V2: Add Unique Constraint for Register-Period Combination
-- Version: 2.0
-- Date: 2025-03-02
-- Description: Prevent duplicate muster rolls for same register-period combination
-- Backward Compatible: YES (only applies when billing_period_id IS NOT NULL)
-- ============================================================

-- Add unique constraint to prevent duplicate muster rolls for same register in same period
-- This ensures: One register → One muster roll per billing period
-- V1 musters (billing_period_id IS NULL) are not affected by this constraint
CREATE UNIQUE INDEX IF NOT EXISTS uk_muster_register_period
ON eg_wms_muster_roll (attendanceregisterid, billing_period_id, tenantid)
WHERE billing_period_id IS NOT NULL;

-- Add comment for documentation
COMMENT ON INDEX uk_muster_register_period IS
'V2: Ensures one muster roll per register per billing period (V1 musters with NULL billing_period_id are excluded)';

-- Add index for efficient period-based queries
CREATE INDEX IF NOT EXISTS idx_muster_roll_period_status
ON eg_wms_muster_roll (billing_period_id, musterrollstatus, tenantid)
WHERE billing_period_id IS NOT NULL;

COMMENT ON INDEX idx_muster_roll_period_status IS
'V2: Optimizes queries for checking approved musters in a billing period';
