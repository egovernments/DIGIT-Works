-- ============================================================
-- HCM Payments V2: Enhance Bill Generation Status Tracking
-- Version: 2.0
-- Date: 2025-02-01
-- Description: Add period-based tracking columns to bill gen status table
-- Backward Compatible: YES (nullable columns with defaults)
-- ============================================================

-- Step 1: Add billing_type column to distinguish V1 vs V2 bills
ALTER TABLE eg_expense_bill_gen_status
ADD COLUMN IF NOT EXISTS billing_type VARCHAR(32) DEFAULT 'REGULAR';

-- Step 2: Add period_id column to link bill status to billing period
ALTER TABLE eg_expense_bill_gen_status
ADD COLUMN IF NOT EXISTS period_id VARCHAR(64);

-- Step 3: Add period_number for easy ordering and validation
ALTER TABLE eg_expense_bill_gen_status
ADD COLUMN IF NOT EXISTS period_number INTEGER;

-- Step 4: Add register_count to track number of registers processed in bill
ALTER TABLE eg_expense_bill_gen_status
ADD COLUMN IF NOT EXISTS register_count INTEGER DEFAULT 0;

-- Step 5: Add processing timestamps for performance monitoring
ALTER TABLE eg_expense_bill_gen_status
ADD COLUMN IF NOT EXISTS processing_start_time BIGINT;

ALTER TABLE eg_expense_bill_gen_status
ADD COLUMN IF NOT EXISTS processing_end_time BIGINT;

-- Step 6: Add additional_details JSONB column for flexible metadata
ALTER TABLE eg_expense_bill_gen_status
ADD COLUMN IF NOT EXISTS additional_details JSONB;

-- Step 6b: Add foreign key constraint for period_id (nullable FK is valid - NULL values are allowed)
DO $$
DECLARE
  v_table_name CONSTANT TEXT := 'eg_expense_bill_gen_status';
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM pg_constraint c
    WHERE c.conname = 'fk_bill_status_period'
      AND c.conrelid = v_table_name::regclass
  ) THEN
    EXECUTE format('ALTER TABLE %I ADD CONSTRAINT fk_bill_status_period FOREIGN KEY (period_id) REFERENCES eg_wms_billing_period(id)', v_table_name);
  END IF;
END$$;

-- Step 7: Add comments for documentation
COMMENT ON COLUMN eg_expense_bill_gen_status.billing_type IS
'Type of bill: REGULAR (V1), INTERMEDIATE (V2 periodic), FINAL_AGGREGATE (V2 final)';

COMMENT ON COLUMN eg_expense_bill_gen_status.period_id IS
'V2: Foreign key to eg_wms_billing_period.id (NULL for V1 bills and aggregate bills)';

COMMENT ON COLUMN eg_expense_bill_gen_status.period_number IS
'V2: Sequential period number for easy sorting and validation';

COMMENT ON COLUMN eg_expense_bill_gen_status.register_count IS
'Number of attendance registers processed in this bill generation';

COMMENT ON COLUMN eg_expense_bill_gen_status.processing_start_time IS
'Timestamp when bill generation started (milliseconds)';

COMMENT ON COLUMN eg_expense_bill_gen_status.processing_end_time IS
'Timestamp when bill generation completed (milliseconds)';

-- Step 8: Create performance indices for V2 queries
CREATE INDEX IF NOT EXISTS idx_bill_status_billing_type
ON eg_expense_bill_gen_status (billing_type);

CREATE INDEX IF NOT EXISTS idx_bill_status_period
ON eg_expense_bill_gen_status (period_id);

CREATE INDEX IF NOT EXISTS idx_bill_status_ref_period
ON eg_expense_bill_gen_status (referenceid, period_number);

CREATE INDEX IF NOT EXISTS idx_bill_status_tenant_type
ON eg_expense_bill_gen_status (tenantid, billing_type);

CREATE INDEX IF NOT EXISTS idx_bill_status_tenant_status
ON eg_expense_bill_gen_status (tenantid, status);

-- Step 9: Create index for period status queries
CREATE INDEX IF NOT EXISTS idx_bill_status_period_status
ON eg_expense_bill_gen_status (period_id, status);

-- Step 11: Add check constraints for data validation
DO $$
DECLARE
  v_table_name CONSTANT TEXT := 'eg_expense_bill_gen_status';
BEGIN
  -- Check constraint for valid billing types
  IF NOT EXISTS (
    SELECT 1
    FROM pg_constraint c
    WHERE c.conname = 'chk_billing_type'
      AND c.conrelid = v_table_name::regclass
  ) THEN
    EXECUTE format('ALTER TABLE %I ADD CONSTRAINT chk_billing_type CHECK (billing_type IN (''REGULAR'', ''INTERMEDIATE'', ''FINAL_AGGREGATE''))', v_table_name);
  END IF;

  -- period_number > 0 (or NULL)
  IF NOT EXISTS (
    SELECT 1 FROM pg_constraint c
    WHERE c.conname = 'chk_period_number_positive'
      AND c.conrelid = v_table_name::regclass
  ) THEN
    EXECUTE format('ALTER TABLE %I ADD CONSTRAINT chk_period_number_positive CHECK (period_number IS NULL OR period_number > 0)', v_table_name);
  END IF;

  -- register_count >= 0
  IF NOT EXISTS (
    SELECT 1 FROM pg_constraint c
    WHERE c.conname = 'chk_register_count_non_negative'
      AND c.conrelid = v_table_name::regclass
  ) THEN
    EXECUTE format('ALTER TABLE %I ADD CONSTRAINT chk_register_count_non_negative CHECK (register_count >= 0)', v_table_name);
  END IF;

  -- processing_end_time >= processing_start_time (when both present)
  IF NOT EXISTS (
    SELECT 1 FROM pg_constraint c
    WHERE c.conname = 'chk_processing_time_order'
      AND c.conrelid = v_table_name::regclass
  ) THEN
    EXECUTE format('ALTER TABLE %I ADD CONSTRAINT chk_processing_time_order CHECK (processing_start_time IS NULL OR processing_end_time IS NULL OR processing_end_time >= processing_start_time)', v_table_name);
  END IF;
END$$;
