-- =============================================================================
-- Migration: Add JSONB indexes for V2 billing performance optimization
-- Date     : 2025-12-04
-- Purpose  : Optimize V2 period-based billing queries on additionaldetails JSONB
-- Impact   : Performance improvement only - no schema or data changes
-- =============================================================================

-- -----------------------------------------------------------------------------
-- Index 1: billingPeriodId - High selectivity, frequently used in V2 queries
-- -----------------------------------------------------------------------------
-- Supports: criteria.getBillingPeriodIds() filter in BillQueryBuilder
-- Query pattern: bill.additionaldetails->>'billingPeriodId' IN (...)

CREATE INDEX IF NOT EXISTS idx_expense_bill_billing_period_id
ON eg_expense_bill ((additionaldetails->>'billingPeriodId'))
WHERE additionaldetails->>'billingPeriodId' IS NOT NULL;

-- -----------------------------------------------------------------------------
-- Index 2: billingType - Filter by PERIODIC vs AGGREGATE billing
-- -----------------------------------------------------------------------------
-- Supports: criteria.getBillingType() filter in BillQueryBuilder
-- Query pattern: bill.additionaldetails->>'billingType' = ?

CREATE INDEX IF NOT EXISTS idx_expense_bill_billing_type
ON eg_expense_bill ((additionaldetails->>'billingType'))
WHERE additionaldetails->>'billingType' IS NOT NULL;

-- -----------------------------------------------------------------------------
-- Index 3: isAggregate - Boolean filter for aggregate bills
-- -----------------------------------------------------------------------------
-- Supports: criteria.getIsAggregate() filter in BillQueryBuilder
-- Query pattern: bill.additionaldetails->>'isAggregate' = ?
-- Note: Low cardinality (true/false) but useful for filtering aggregate bills

CREATE INDEX IF NOT EXISTS idx_expense_bill_is_aggregate
ON eg_expense_bill ((additionaldetails->>'isAggregate'))
WHERE additionaldetails->>'isAggregate' IS NOT NULL;

-- -----------------------------------------------------------------------------
-- Index 4: reportStatus - Filter by report workflow status
-- -----------------------------------------------------------------------------
-- Supports: criteria.getReportStatus() filter in BillQueryBuilder
-- Query pattern: bill.additionaldetails->'reportDetails'->>'status' = ?

CREATE INDEX IF NOT EXISTS idx_expense_bill_report_status
ON eg_expense_bill ((additionaldetails->'reportDetails'->>'status'))
WHERE additionaldetails->'reportDetails'->>'status' IS NOT NULL;

-- =============================================================================
-- NOTES
-- =============================================================================
-- 1. All indexes are partial (WHERE ... IS NOT NULL) to minimize storage
-- 2. Indexes only affect V2 bills that have these fields populated
-- 3. V1 bills (without these fields) are unaffected
-- 4. Uses expression indexes on JSONB paths for optimal query performance
-- 5. CREATE INDEX IF NOT EXISTS ensures idempotent migration
-- =============================================================================
