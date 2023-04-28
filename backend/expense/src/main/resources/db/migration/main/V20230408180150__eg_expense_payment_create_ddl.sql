CREATE TABLE IF NOT EXISTS eg_expense_payment
(
id character varying(256)   NOT NULL,
tenant_id character varying(64)   NOT NULL,
net_payable_amount numeric(12,2),
net_paid_amount numeric(12,2),
status character varying(64) NOT NULL,
created_by character varying(64)   NOT NULL,
created_time bigint NOT NULL,
last_modified_by character varying(64) NOT NULL,
last_modified_time bigint NOT NULL,
additional_details jsonb,

CONSTRAINT pk_eg_expense_payment PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS eg_expense_bill_payment
(
id character varying(256)   NOT NULL,
payment_id character varying(256)   NOT NULL,
bill_id character varying(256)   NOT NULL,
tenant_id character varying(64)   NOT NULL,

CONSTRAINT pk_eg_expense_bill_payment PRIMARY KEY (id),
CONSTRAINT fk_eg_expense_bill_payment FOREIGN KEY (payment_id) REFERENCES eg_expense_payment (id)
);

