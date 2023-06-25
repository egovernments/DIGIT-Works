CREATE TABLE IF NOT EXISTS jit_transaction_details (
  id varchar(256) PRIMARY KEY,
  tenantId varchar(64) NOT NULL,
  sanctionId varchar(256),
  paymentInstId varchar(256),
  transactionAmount numeric(12,2),
  transactionDate bigint,
  transactionType varchar(256),
  additionalDetails jsonb,
  createdtime bigint,
  createdby varchar(256),
  lastmodifiedtime bigint,
  lastmodifiedby varchar(256)
);