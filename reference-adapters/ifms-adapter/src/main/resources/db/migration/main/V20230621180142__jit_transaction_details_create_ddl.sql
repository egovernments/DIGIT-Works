CREATE TABLE IF NOT EXISTS jit_transaction_details (
  id varchar(256),
  tenantId varchar(64) NOT NULL,
  sanctionId varchar(256) NOT NULL,
  paymentInstId varchar(256) NOT NULL,
  transactionAmount numeric(12,2),
  transactionDate bigint,
  transactionType varchar(256),
  additionalDetails jsonb,
  createdtime bigint,
  createdby varchar(256),
  lastmodifiedtime bigint,
  lastmodifiedby varchar(256),
  CONSTRAINT jit_transaction_details_pkey PRIMARY KEY (id),
  CONSTRAINT fk_jit_transaction_details_sanction_id FOREIGN KEY (sanctionId) REFERENCES jit_sanction_details (id),
  CONSTRAINT fk_jit_transaction_details_pi_id FOREIGN KEY (paymentInstId) REFERENCES jit_payment_inst_details (id)
);
