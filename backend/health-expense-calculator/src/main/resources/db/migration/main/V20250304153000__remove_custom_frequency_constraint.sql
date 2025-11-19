-- =============================================================================
-- Migration: Remove custom frequency minimum day constraint
-- Date     : 2025-03-04
-- Purpose  : Allow CUSTOM billing frequency configurations with any duration
-- =============================================================================

DO $$
BEGIN
    -- Drop the constraint that enforced minimum 3 days for CUSTOM frequency
    IF EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'chk_custom_frequency'
        AND conrelid = 'eg_expense_billing_config'::regclass
    ) THEN
        ALTER TABLE eg_expense_billing_config
            DROP CONSTRAINT chk_custom_frequency;
        RAISE NOTICE 'Dropped constraint chk_custom_frequency from eg_expense_billing_config';
    ELSE
        RAISE NOTICE 'Constraint chk_custom_frequency not found on eg_expense_billing_config';
    END IF;
END;
$$;
