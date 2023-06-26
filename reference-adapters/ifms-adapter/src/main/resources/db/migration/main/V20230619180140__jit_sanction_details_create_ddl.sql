CREATE TABLE IF NOT EXISTS jit_sanction_details (
  id varchar(256) PRIMARY KEY,
  tenantId varchar(64) NOT NULL,
  hoaCode varchar(64) NOT NULL,
  ddoCode varchar(64) NOT NULL,
  masterAllotmentId varchar(64) NOT NULL,
  sanctionedAmount numeric(12,2),
  financialYear varchar(64),
  additionalDetails jsonb,
  createdtime bigint,
  createdby varchar(256),
  lastmodifiedtime bigint,
  lastmodifiedby varchar(256)
);