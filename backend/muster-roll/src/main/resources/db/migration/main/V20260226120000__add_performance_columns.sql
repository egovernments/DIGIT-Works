-- add column for total registrations
ALTER TABLE eg_wms_attendance_summary
ADD COLUMN IF NOT EXISTS total_registrations BIGINT DEFAULT 0;

-- add column for total interventions
ALTER TABLE eg_wms_attendance_summary
ADD COLUMN IF NOT EXISTS total_interventions BIGINT DEFAULT 0;
