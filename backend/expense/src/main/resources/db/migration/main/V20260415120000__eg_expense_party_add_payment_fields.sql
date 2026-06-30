ALTER TABLE eg_expense_billdetail ADD COLUMN IF NOT EXISTS workerid character varying(64);
ALTER TABLE eg_expense_party ADD COLUMN IF NOT EXISTS paymentprovider character varying(16);
ALTER TABLE eg_expense_party ADD COLUMN IF NOT EXISTS payeename character varying(256);
ALTER TABLE eg_expense_party ADD COLUMN IF NOT EXISTS payeephonenumber character varying(64);
ALTER TABLE eg_expense_party ADD COLUMN IF NOT EXISTS bankaccount character varying(128);
ALTER TABLE eg_expense_party ADD COLUMN IF NOT EXISTS bankcode character varying(64);
ALTER TABLE eg_expense_party ADD COLUMN IF NOT EXISTS beneficiarycode character varying(128);
