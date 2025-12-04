-- V2 Intermediate Billing - Event-Driven Period Status Storage
-- This migration adds a JSONB field to store period-wise muster roll statuses
-- This eliminates the need for synchronous API calls during search operations

-- Add period_statuses column to store array of period status objects
-- Structure: [{"periodId": "uuid", "status": "APPROVED|PENDING|REJECTED|SENT_BACK|NOT_CREATED", "musterRollId": "uuid", "lastModifiedTime": epoch_millis}]
ALTER TABLE eg_wms_attendance_register ADD COLUMN IF NOT EXISTS period_statuses JSONB DEFAULT '[]'::jsonb;

-- Add comment for documentation
COMMENT ON COLUMN eg_wms_attendance_register.period_statuses IS 'V2 Intermediate Billing: Stores period-wise muster roll statuses as JSON array. Updated asynchronously via Kafka events when muster-roll status changes. Structure: [{"periodId": "string", "status": "string", "musterRollId": "string", "lastModifiedTime": number}]';

-- Create GIN index on period_statuses for fast JSONB containment queries
-- This enables efficient filtering when using @> operator: WHERE period_statuses @> '[{"periodId": "some-id"}]'::jsonb
CREATE INDEX IF NOT EXISTS idx_attendance_register_period_statuses ON eg_wms_attendance_register USING GIN (period_statuses);

-- Performance notes:
-- 1. GIN index enables O(log n) lookup for containment queries (@>) instead of full table scan
-- 2. JSONB is more efficient than JSON for querying and indexing
-- 3. Default empty array prevents NULL handling in application code
-- 4. Index size grows with number of periods per register (typically 5-20 periods per register)
-- 5. Single GIN index on the column is sufficient for all JSONB containment operations