-- Add role column to eg_wms_attendance_summary
-- Stores the individual's skill type (e.g., REGISTRAR, DISTRIBUTOR) from the Individual service
-- Enriched during muster roll create/estimate from individual.getSkills().get(0).getType()
ALTER TABLE eg_wms_attendance_summary
ADD COLUMN IF NOT EXISTS role CHARACTER VARYING(128);