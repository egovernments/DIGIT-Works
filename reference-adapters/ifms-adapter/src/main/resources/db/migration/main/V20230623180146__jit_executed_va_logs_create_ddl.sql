CREATE TABLE IF NOT EXISTS jit_executed_va_logs (
  id varchar(256) PRIMARY KEY,
  tenantId varchar NOT NULL,
  hoaCode varchar(64) NOT NULL,
  ddoCode varchar(64) NOT NULL,
  granteeCode varchar(64) NOT NULL,
  lastExecuted bigint,
  createdtime bigint,
  createdby varchar(256),
  lastmodifiedtime bigint,
  lastmodifiedby varchar(256)
);
