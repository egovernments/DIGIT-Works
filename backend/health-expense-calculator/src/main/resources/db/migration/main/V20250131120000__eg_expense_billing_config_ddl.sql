-- =============================================================================
-- Billing Configuration V2 - Database Migration Script
-- =============================================================================
-- Purpose: Create tables for intermediate billing configuration and periods
-- Version: 1.0
-- Date: January 31, 2025
-- Author: DIGIT-Works
-- =============================================================================

-- =============================================================================
-- Table: eg_expense_billing_config
-- Purpose: Store billing configuration for projects enabling intermediate billing
-- =============================================================================

CREATE TABLE IF NOT EXISTS eg_expense_billing_config (
    -- Primary identification
    id                      VARCHAR(64) PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    project_id              VARCHAR(256) NOT NULL,

    -- Configuration details
    billing_frequency       VARCHAR(32) NOT NULL,  -- WEEKLY, BI_WEEKLY, MONTHLY, CUSTOM, END_OF_CAMPAIGN
    custom_frequency_days   INTEGER,               -- Required when frequency = CUSTOM (minimum 3)

    -- Project timeline
    project_start_date      BIGINT NOT NULL,       -- Campaign start timestamp
    project_end_date        BIGINT NOT NULL,       -- Campaign end timestamp

    -- Status tracking
    status                  VARCHAR(32) NOT NULL DEFAULT 'ACTIVE',  -- ACTIVE, INACTIVE, COMPLETED

    -- Audit fields
    created_by              VARCHAR(64) NOT NULL,
    created_time            BIGINT NOT NULL,
    last_modified_by        VARCHAR(64),
    last_modified_time      BIGINT,
    additional_details      JSONB,

    -- Constraints
    CONSTRAINT uk_billing_config_project UNIQUE(project_id, tenant_id),
    CONSTRAINT chk_billing_frequency CHECK (
        billing_frequency IN ('WEEKLY', 'BI_WEEKLY', 'MONTHLY', 'CUSTOM', 'END_OF_CAMPAIGN')
    ),
    CONSTRAINT chk_billing_status CHECK (
        status IN ('ACTIVE', 'INACTIVE', 'COMPLETED')
    ),
    CONSTRAINT chk_custom_frequency CHECK (
        (billing_frequency != 'CUSTOM') OR
        (billing_frequency = 'CUSTOM' AND custom_frequency_days >= 3)
    ),
    CONSTRAINT chk_project_dates CHECK (project_start_date < project_end_date)
);

-- Performance indices
CREATE INDEX IF NOT EXISTS idx_billing_config_project ON eg_expense_billing_config (project_id, tenant_id);
CREATE INDEX IF NOT EXISTS idx_billing_config_tenant ON eg_expense_billing_config (tenant_id);
CREATE INDEX IF NOT EXISTS idx_billing_config_status ON eg_expense_billing_config (status);
CREATE INDEX IF NOT EXISTS idx_billing_config_created_time ON eg_expense_billing_config (created_time);

-- Comments for documentation
COMMENT ON TABLE eg_expense_billing_config IS 'Stores billing configuration for projects enabling intermediate billing in HCM Payments V2';
COMMENT ON COLUMN eg_expense_billing_config.id IS 'Unique identifier for the billing configuration';
COMMENT ON COLUMN eg_expense_billing_config.tenant_id IS 'Tenant identifier for multi-tenancy support';
COMMENT ON COLUMN eg_expense_billing_config.project_id IS 'Project/campaign identifier this configuration applies to';
COMMENT ON COLUMN eg_expense_billing_config.billing_frequency IS 'Billing frequency: WEEKLY (7 days), BI_WEEKLY (14 days), MONTHLY (30 days), CUSTOM (variable), END_OF_CAMPAIGN (single period)';
COMMENT ON COLUMN eg_expense_billing_config.custom_frequency_days IS 'Number of days for custom frequency (required when billing_frequency = CUSTOM, minimum 3 days)';
COMMENT ON COLUMN eg_expense_billing_config.project_start_date IS 'Campaign start date in epoch milliseconds';
COMMENT ON COLUMN eg_expense_billing_config.project_end_date IS 'Campaign end date in epoch milliseconds';
COMMENT ON COLUMN eg_expense_billing_config.status IS 'Configuration status: ACTIVE, INACTIVE, COMPLETED';

-- =============================================================================
-- Table: eg_wms_billing_period
-- Purpose: Store individual billing periods generated from billing configuration
-- =============================================================================

CREATE TABLE IF NOT EXISTS eg_wms_billing_period (
    -- Primary identification
    id                      VARCHAR(64) PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    project_id              VARCHAR(256) NOT NULL,
    billing_config_id       VARCHAR(64) NOT NULL,

    -- Period details
    period_number           INTEGER NOT NULL,          -- Sequential number (1, 2, 3, ...)
    period_start_date       BIGINT NOT NULL,
    period_end_date         BIGINT NOT NULL,
    billing_frequency       VARCHAR(32) NOT NULL,

    -- Period type
    period_type             VARCHAR(32) NOT NULL DEFAULT 'INTERMEDIATE',  -- INTERMEDIATE, FINAL_AGGREGATE

    -- Status tracking
    status                  VARCHAR(32) NOT NULL DEFAULT 'PENDING',  -- PENDING, PROCESSING, COMPLETED, BILLED
    bill_id                 VARCHAR(64),                             -- Links to generated bill

    -- Aggregate metrics
    total_amount            DECIMAL(12,2),
    beneficiary_count       INTEGER,
    register_count          INTEGER DEFAULT 0,
    muster_roll_count       INTEGER DEFAULT 0,

    -- Audit fields
    created_by              VARCHAR(64) NOT NULL,
    created_time            BIGINT NOT NULL,
    last_modified_by        VARCHAR(64),
    last_modified_time      BIGINT,
    additional_details      JSONB,

    -- Constraints
    CONSTRAINT uk_billing_period UNIQUE(project_id, period_number, tenant_id),
    CONSTRAINT chk_period_type CHECK (
        period_type IN ('INTERMEDIATE', 'FINAL_AGGREGATE')
    ),
    CONSTRAINT chk_period_status CHECK (
        status IN ('PENDING', 'PROCESSING', 'COMPLETED', 'BILLED')
    ),
    CONSTRAINT chk_period_dates CHECK (period_start_date < period_end_date),
    CONSTRAINT chk_period_number CHECK (period_number > 0),
    CONSTRAINT fk_billing_period_config FOREIGN KEY (billing_config_id)
        REFERENCES eg_expense_billing_config(id) ON DELETE CASCADE
);

-- Performance indices
CREATE INDEX IF NOT EXISTS idx_billing_period_project ON eg_wms_billing_period (project_id, tenant_id);
CREATE INDEX IF NOT EXISTS idx_billing_period_config ON eg_wms_billing_period (billing_config_id);
CREATE INDEX IF NOT EXISTS idx_billing_period_status ON eg_wms_billing_period (status, project_id);
CREATE INDEX IF NOT EXISTS idx_billing_period_dates ON eg_wms_billing_period (period_start_date, period_end_date);
CREATE INDEX IF NOT EXISTS idx_billing_period_number ON eg_wms_billing_period (project_id, period_number);
CREATE INDEX IF NOT EXISTS idx_billing_period_bill_id ON eg_wms_billing_period (bill_id);

-- Comments for documentation
COMMENT ON TABLE eg_wms_billing_period IS 'Stores individual billing periods generated from billing configuration';
COMMENT ON COLUMN eg_wms_billing_period.id IS 'Unique identifier for the billing period';
COMMENT ON COLUMN eg_wms_billing_period.billing_config_id IS 'References the parent billing configuration';
COMMENT ON COLUMN eg_wms_billing_period.period_number IS 'Sequential period number starting from 1';
COMMENT ON COLUMN eg_wms_billing_period.period_type IS 'Type of period: INTERMEDIATE (regular periods), FINAL_AGGREGATE (final settlement)';
COMMENT ON COLUMN eg_wms_billing_period.status IS 'Period status: PENDING (not started), PROCESSING (in progress), COMPLETED (done), BILLED (bill generated)';
COMMENT ON COLUMN eg_wms_billing_period.bill_id IS 'Reference to generated bill in expense service (populated after bill generation)';
COMMENT ON COLUMN eg_wms_billing_period.register_count IS 'Number of attendance registers processed for this period';
COMMENT ON COLUMN eg_wms_billing_period.muster_roll_count IS 'Number of muster rolls generated for this period';

-- =============================================================================
-- Migration complete
-- =============================================================================
