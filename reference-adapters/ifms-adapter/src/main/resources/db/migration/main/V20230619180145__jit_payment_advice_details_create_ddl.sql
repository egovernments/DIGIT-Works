CREATE TABLE IF NOT EXISTS jit_payment_advice_details (
  id varchar(256) PRIMARY KEY,
  tenantId varchar(64) NOT NULL,
  muktaReferenceId varchar(256),
  piId varchar(256),
  paBillRefNumber varchar(256),
  paFinYear varchar(64),
  paAdviceId varchar(256),
  paAdviceDate varchar(64),
  paTokenNumber varchar(64),
  paTokenDate varchar(64),
  paErrorMsg varchar(256),
  additionalDetails jsonb,
  createdtime bigint,
  createdby varchar(256),
  lastmodifiedtime bigint,
  lastmodifiedby varchar(256)
);
