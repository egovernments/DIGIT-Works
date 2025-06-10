ALTER TABLE jit_sanction_details ADD COLUMN programCode character varying(64);
ALTER TABLE jit_payment_inst_details ADD COLUMN programCode character varying(64);
ALTER TABLE jit_beneficiary_details ALTER COLUMN beneficiaryId DROP NOT NULL;
ALTER TABLE jit_beneficiary_details ALTER COLUMN beneficiaryType DROP NOT NULL;
ALTER TABLE jit_beneficiary_details ADD COLUMN bankAccountCode character varying(64);