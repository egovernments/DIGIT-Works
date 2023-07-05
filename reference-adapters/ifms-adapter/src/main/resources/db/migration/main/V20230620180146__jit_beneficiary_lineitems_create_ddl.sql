CREATE TABLE IF NOT EXISTS jit_beneficiary_lineitems (
  id varchar(256),
  beneficiaryId varchar(256),
  lineItemId varchar(256),
  createdtime bigint,
  createdby varchar(256),
  lastmodifiedtime bigint,
  lastmodifiedby varchar(256),
  CONSTRAINT jit_beneficiary_lineitems_pkey PRIMARY KEY (id),
  CONSTRAINT fk_jit_beneficiary_lineitems_benef_id FOREIGN KEY (beneficiaryId) REFERENCES jit_beneficiary_details (id)
);
