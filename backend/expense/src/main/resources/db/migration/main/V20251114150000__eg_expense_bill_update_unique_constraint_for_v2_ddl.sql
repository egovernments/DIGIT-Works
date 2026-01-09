-- Migration: Update unique constraint on eg_expense_bill to support Payments V2 period-based billing
-- Date: 2025-11-14
-- Purpose: Allow multiple bills for same referenceId when they have different billing periods
-- Impact: Enables v2 period-based billing while maintaining v1 backward compatibility

-- Drop the old unique constraint that prevents multiple bills per reference
DROP INDEX IF EXISTS index_unique_eg_expense_bill;

-- Create new unique constraint including period dates
-- This allows multiple bills for the same (referenceId, businessservice, tenantid)
-- as long as they have different (fromperiod, toperiod) combinations
CREATE UNIQUE INDEX index_unique_eg_expense_bill ON eg_expense_bill
(referenceId, businessservice, tenantid, fromperiod, toperiod)
WHERE status != 'INACTIVE';

-- Notes:
-- 1. V1 Compatibility: Existing v1 bills will continue to work as they have unique period dates
-- 2. V2 Support: Allows multiple bills per project/register with different billing periods
-- 3. Data Integrity: Prevents duplicate bills for the same period
-- 4. NULL handling: If fromperiod/toperiod are NULL, PostgreSQL treats each NULL as distinct,
--    so multiple NULL combinations are allowed (this is acceptable for v1 behavior)
