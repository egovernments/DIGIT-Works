ALTER TABLE jit_beneficiary_details ALTER COLUMN beneficiaryId DROP NOT NULL;
ALTER TABLE jit_beneficiary_details ALTER COLUMN beneficiaryType DROP NOT NULL;
ALTER TABLE jit_beneficiary_details RENAME COLUMN bankAccountId TO bankAccountCode;