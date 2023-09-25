CREATE TABLE IF NOT EXISTS jit_pi_status_logs (
  id varchar(256),
  piId varchar(256) NOT NULL,
  serviceId varchar(64) NOT NULL,
  status varchar(256) NOT NULL,
  additionalDetails jsonb,
  createdtime bigint,
  createdby varchar(256),
  lastmodifiedtime bigint,
  lastmodifiedby varchar(256),
  CONSTRAINT jit_pi_status_logs_pkey PRIMARY KEY (id),
  CONSTRAINT fk_jit_pi_status_logs_pi_id FOREIGN KEY (piId) REFERENCES jit_payment_inst_details (id)
);
