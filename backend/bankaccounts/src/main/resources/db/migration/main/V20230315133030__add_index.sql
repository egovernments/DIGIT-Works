CREATE INDEX IF NOT EXISTS index_eg_bank_account_id ON eg_bank_account (id);
CREATE INDEX IF NOT EXISTS index_eg_bank_account_service_code ON eg_bank_account (service_code);
CREATE INDEX IF NOT EXISTS index_eg_bank_account_reference_id ON eg_bank_account (reference_id);

CREATE INDEX IF NOT EXISTS index_eg_bank_account_detail_id ON eg_bank_account_detail (id);
CREATE INDEX IF NOT EXISTS index_eg_bank_account_detail_account_holder_name ON eg_bank_account_detail (account_holder_name);
CREATE INDEX IF NOT EXISTS index_eg_bank_account_detail_account_number ON eg_bank_account_detail (account_number);
CREATE INDEX IF NOT EXISTS index_eg_bank_account_detail_is_primary ON eg_bank_account_detail (is_primary);
CREATE INDEX IF NOT EXISTS index_eg_bank_account_detail_is_active ON eg_bank_account_detail (is_active);

CREATE INDEX IF NOT EXISTS index_eg_bank_accounts_doc_id ON eg_bank_accounts_doc (id);

CREATE INDEX IF NOT EXISTS index_eg_bank_branch_id ON eg_bank_branch_identifier (id);
CREATE INDEX IF NOT EXISTS index_eg_bank_branch_identifier_code ON eg_bank_branch_identifier (code);
CREATE INDEX IF NOT EXISTS index_eg_bank_branch_identifier_type ON eg_bank_branch_identifier (type);