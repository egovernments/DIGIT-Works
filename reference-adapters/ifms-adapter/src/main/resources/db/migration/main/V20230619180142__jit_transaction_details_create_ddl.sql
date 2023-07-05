CREATE TABLE IF NOT EXISTS jit_transaction_details (
  id varchar(256),
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
ALTER TABLE jit_allotment_details ADD CONSTRAINT jit_transaction_details_pkey PRIMARY KEY (id);
