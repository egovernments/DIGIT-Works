CREATE TABLE IF NOT EXISTS jit_sanction_details (
  id varchar(256),
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
  lastmodifiedby varchar(256),
  CONSTRAINT jit_sanction_details_pkey PRIMARY KEY (id)
);
