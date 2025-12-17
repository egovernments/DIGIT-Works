CREATE INDEX IF NOT EXISTS index_jit_sanction_details_tenantId ON jit_sanction_details (tenantId);
CREATE INDEX IF NOT EXISTS index_jit_sanction_details_hoaCode ON jit_sanction_details (hoaCode);
CREATE INDEX IF NOT EXISTS index_jit_sanction_details_masterAllotmentId ON jit_sanction_details (masterAllotmentId);
CREATE INDEX IF NOT EXISTS index_jit_sanction_details_createdtime ON jit_sanction_details (createdtime);

CREATE INDEX IF NOT EXISTS index_jit_allotment_details_sanctionId ON jit_allotment_details (sanctionId);
CREATE INDEX IF NOT EXISTS index_jit_funds_summary_sanctionId ON jit_funds_summary (sanctionId);

CREATE INDEX IF NOT EXISTS index_jit_payment_inst_details_tenantId ON jit_payment_inst_details (tenantId);
CREATE INDEX IF NOT EXISTS index_jit_payment_inst_details_piNumber ON jit_payment_inst_details (piNumber);
CREATE INDEX IF NOT EXISTS index_jit_payment_inst_details_parentPiNumber ON jit_payment_inst_details (parentPiNumber);
CREATE INDEX IF NOT EXISTS index_jit_payment_inst_details_muktaReferenceId ON jit_payment_inst_details (muktaReferenceId);
CREATE INDEX IF NOT EXISTS index_jit_payment_inst_details_piStatus ON jit_payment_inst_details (piStatus);
CREATE INDEX IF NOT EXISTS index_jit_payment_inst_details_createdtime ON jit_payment_inst_details (createdtime);
CREATE INDEX IF NOT EXISTS index_jit_payment_inst_details_isActive ON jit_payment_inst_details (isActive);

CREATE INDEX IF NOT EXISTS index_jit_payment_advice_details_piId ON jit_payment_advice_details (piId);
CREATE INDEX IF NOT EXISTS index_jit_beneficiary_details_piId ON jit_beneficiary_details (piId);
CREATE INDEX IF NOT EXISTS index_jit_beneficiary_lineitems_beneficiaryId ON jit_beneficiary_lineitems (beneficiaryId);

CREATE INDEX IF NOT EXISTS index_jit_transaction_details_sanctionId ON jit_transaction_details (sanctionId);
CREATE INDEX IF NOT EXISTS index_jit_transaction_details_paymentInstId ON jit_transaction_details (paymentInstId);

CREATE INDEX IF NOT EXISTS index_jit_pi_status_logs_piId ON jit_pi_status_logs (piId);



