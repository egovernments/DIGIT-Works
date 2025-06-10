-- Step 2: Drop the existing staffType column
ALTER TABLE eg_wms_attendance_staff DROP COLUMN IF EXISTS staffType;

-- Step 3: Add the new staffType column with jsonb type
ALTER TABLE eg_wms_attendance_staff ADD COLUMN IF NOT EXISTS staffType JSONB;