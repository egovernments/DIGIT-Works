CREATE TABLE IF NOT EXISTS jit_payment_advice_details (
  id varchar(256),
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
  lastmodifiedby varchar(256),
  CONSTRAINT jit_payment_advice_details_pkey PRIMARY KEY (id),
  CONSTRAINT fk_jit_payment_advice_details_pi_id FOREIGN KEY (piId) REFERENCES jit_payment_inst_details (id)
);
