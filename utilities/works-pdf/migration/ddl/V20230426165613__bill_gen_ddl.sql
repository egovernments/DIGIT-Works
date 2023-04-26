CREATE TABLE eg_bulk_bill_gen
(
  jobId character varying(100) NOT NULL,
  tenantId character varying(50) NOT NULL,
  uuid character varying(100) NOT NULL,
  status varying(50) NOT NULL,
  numberOfBills bigint,
  numberOfBeneficialy bigint,
  totalAmount bigint,
  filestoreId character varying(100),
  criteria json,
  createdtime bigint,
  lastmodifiedtime bigint,
  endtime bigint,
  CONSTRAINT egov_pdf_gen_pkey PRIMARY KEY (jobId)
)