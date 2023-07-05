CREATE TABLE IF NOT EXISTS jit_funds_summary (
  id varchar(256),
  tenantId varchar(64) NOT NULL,
  sanctionId varchar(256),
  allottedAmount numeric(12,2),
  availableAmount numeric(12,2),
  additionalDetails jsonb,
  createdtime bigint,
  createdby varchar(256),
  lastmodifiedtime bigint,
  lastmodifiedby varchar(256)
);
ALTER TABLE jit_funds_summary ADD CONSTRAINT jit_funds_summary_pkey PRIMARY KEY (id);