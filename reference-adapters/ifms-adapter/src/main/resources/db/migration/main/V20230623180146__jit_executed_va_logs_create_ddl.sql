CREATE TABLE IF NOT EXISTS jit_executed_va_logs (
  id varchar(256),
  tenantId varchar NOT NULL,
  hoaCode varchar(64) NOT NULL,
  ddoCode varchar(64) NOT NULL,
  granteeCode varchar(64) NOT NULL,
  lastExecuted bigint,
  additionalDetails jsonb,
  createdtime bigint,
  createdby varchar(256),
  lastmodifiedtime bigint,
  lastmodifiedby varchar(256),
  CONSTRAINT jit_executed_va_logs_pkey PRIMARY KEY (id)
);
