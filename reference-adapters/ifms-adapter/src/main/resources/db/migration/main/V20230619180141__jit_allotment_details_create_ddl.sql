CREATE TABLE IF NOT EXISTS jit_allotment_details (
  id varchar(256),
  tenantId varchar(64),
  sanctionId varchar(256),
  allotmentSerialNo int,
  ssuAllotmentId varchar (64),
  allotmentAmount numeric(12,2),
  allotmentTransactionType varchar(64),
  sanctionBalance numeric(12,2),
  allotmentDate bigint,
  additionalDetails jsonb,
  createdtime bigint,
  createdby varchar(256),
  lastmodifiedtime bigint,
  lastmodifiedby varchar(256),
  CONSTRAINT fk_jit_allotment_details_sanction_id FOREIGN KEY (sanctionId) REFERENCES jit_sanction_details (id)
);
