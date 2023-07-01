CREATE TABLE IF NOT EXISTS jit_beneficiary_lineitems (
  id varchar(256) PRIMARY KEY,
  beneficiaryId varchar(256),
  lineItemId varchar(256),
  createdtime bigint,
  createdby varchar(256),
  lastmodifiedtime bigint,
  lastmodifiedby varchar(256)
);