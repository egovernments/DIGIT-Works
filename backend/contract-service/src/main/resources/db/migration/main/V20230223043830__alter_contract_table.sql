ALTER TABLE eg_wms_contract ADD COLUMN issue_date bigint;
ALTER TABLE eg_wms_contract ADD COLUMN completion_period bigint;
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_issue_date ON eg_wms_contract (issue_date);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_agreement_date ON eg_wms_contract (agreement_date);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_executing_authority ON eg_wms_contract (executing_authority);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_completion_period ON eg_wms_contract (completion_period);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_contract_type ON eg_wms_contract (contract_type);