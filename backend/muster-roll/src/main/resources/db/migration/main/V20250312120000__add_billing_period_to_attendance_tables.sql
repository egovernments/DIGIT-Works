-- ============================================================
-- HCM Payments V2: Add Billing Period Support to Attendance Tables
-- Version: 2.0
-- Date: 2025-03-12
-- Description: Add billing_period_id to attendance_summary and attendance_entries
--              to enable period-aware attendance tracking and prevent duplicate records
-- Backward Compatible: YES (nullable column, NULL = V1 muster rolls)
-- ============================================================

-- ============================================================
-- PROBLEM BEING SOLVED:
--
-- Without billing_period_id in attendance_summary:
-- - Same individual cannot have separate attendance records for different periods
-- - Primary key (id) causes duplicates when Period 2 tries to reuse Period 1 IDs
-- - No way to query "attendance for individual X in period Y"
--
-- With billing_period_id in attendance_summary:
-- - Each period gets separate attendance records
-- - Same individual can have attendance in multiple periods
-- - Natural data model: attendance belongs to a period
-- - Unique constraint prevents true duplicates: (individual + muster + period)
-- ============================================================

-- Step 1: Add billing_period_id to eg_wms_attendance_summary
ALTER TABLE eg_wms_attendance_summary
ADD COLUMN IF NOT EXISTS billing_period_id VARCHAR(64);

-- Step 2: Add comment for documentation
COMMENT ON COLUMN eg_wms_attendance_summary.billing_period_id IS
'V2: Links attendance summary to billing period (NULL for V1 muster rolls, populated for V2 intermediate billing). Inherited from parent muster_roll.billing_period_id';

-- Step 3: Add billing_period_id to eg_wms_attendance_entries
ALTER TABLE eg_wms_attendance_entries
ADD COLUMN IF NOT EXISTS billing_period_id VARCHAR(64);

-- Step 4: Add comment for documentation
COMMENT ON COLUMN eg_wms_attendance_entries.billing_period_id IS
'V2: Links attendance entry to billing period (NULL for V1 muster rolls, populated for V2 intermediate billing). Inherited from parent muster_roll.billing_period_id';

-- Step 5: Create unique constraint to prevent duplicate attendance records
-- This ensures: One attendance summary per (individual + muster roll + billing period)
-- V1 musters (billing_period_id IS NULL) are not affected by this constraint
CREATE UNIQUE INDEX IF NOT EXISTS uk_attendance_summary_individual_muster_period
ON eg_wms_attendance_summary (individual_id, muster_roll_id, billing_period_id)
WHERE billing_period_id IS NOT NULL;

-- Step 6: Add comment for unique constraint
COMMENT ON INDEX uk_attendance_summary_individual_muster_period IS
'V2: Ensures one attendance summary per individual per muster roll per billing period (V1 musters with NULL billing_period_id are excluded)';

-- Step 7: Create index for period-based queries on attendance_summary
CREATE INDEX IF NOT EXISTS idx_attendance_summary_period
ON eg_wms_attendance_summary (billing_period_id)
WHERE billing_period_id IS NOT NULL;

-- Step 8: Create index for period-based queries on attendance_entries
CREATE INDEX IF NOT EXISTS idx_attendance_entries_period
ON eg_wms_attendance_entries (billing_period_id)
WHERE billing_period_id IS NOT NULL;

-- Step 9: Create composite index for efficient period + individual queries
CREATE INDEX IF NOT EXISTS idx_attendance_summary_period_individual
ON eg_wms_attendance_summary (billing_period_id, individual_id)
WHERE billing_period_id IS NOT NULL;

-- Step 10: Create composite index for efficient period + date queries on entries
CREATE INDEX IF NOT EXISTS idx_attendance_entries_period_date
ON eg_wms_attendance_entries (billing_period_id, date_of_attendance)
WHERE billing_period_id IS NOT NULL;

-- ============================================================
-- MIGRATION VALIDATION QUERIES
-- ============================================================
-- After running this migration, verify:
--
-- 1. Check columns were added:
--    SELECT column_name, data_type, is_nullable
--    FROM information_schema.columns
--    WHERE table_name IN ('eg_wms_attendance_summary', 'eg_wms_attendance_entries')
--    AND column_name = 'billing_period_id';
--
-- 2. Check unique constraint exists:
--    SELECT indexname, indexdef
--    FROM pg_indexes
--    WHERE tablename = 'eg_wms_attendance_summary'
--    AND indexname = 'uk_attendance_summary_individual_muster_period';
--
-- 3. Check V1 data is unaffected:
--    SELECT COUNT(*) FROM eg_wms_attendance_summary WHERE billing_period_id IS NULL;
--
-- 4. Test V2 duplicate prevention:
--    -- This should fail after a V2 record is inserted:
--    -- INSERT INTO eg_wms_attendance_summary (id, individual_id, muster_roll_id, billing_period_id, ...)
--    -- VALUES ('new-id', 'same-individual', 'same-muster', 'same-period', ...);
-- ============================================================
