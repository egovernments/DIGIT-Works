CREATE TABLE IF NOT EXISTS jit_payment_advice_details (
  id varchar PRIMARY KEY,
  tenantId varchar NOT NULL,
  muktaReferenceId varchar,
  piId varchar,
  paBillRefNumber varchar,
  paFinYear varchar,
  paAdviceId varchar,
  paAdviceDate varchar,
  paTokenNumber varchar,
  paTokenDate varchar,
  paErrorMsg varchar,
  additionalDetails jsonb,
  createdtime bigint,
  createdby varchar(64),
  lastmodifiedtime bigint,
  lastmodifiedby varchar(64)
);
