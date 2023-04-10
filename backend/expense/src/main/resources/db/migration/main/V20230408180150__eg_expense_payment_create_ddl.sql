CREATE TABLE IF NOT EXISTS eg_expense_payment
(

id character varying(64)   NOT NULL,
tenantid character varying(250)   NOT NULL,
netPayableAmount numeric(12,2),
netPaidAmount numeric(12,2),
status character varying(64) NOT NULL,
createdby character varying(64)   NOT NULL,
createdtime bigint NOT NULL,
lastmodifiedby character varying(64) NOT NULL,
lastmodifiedtime bigint NOT NULL,
additionaldetails jsonb,

CONSTRAINT pk_eg_expense_payment PRIMARY KEY (id,tenantid)
);

CREATE TABLE IF NOT EXISTS eg_expense_bill_payment
(

paymentid character varying(64)   NOT NULL,
billid character varying(64)   NOT NULL,
tenantid character varying(250)   NOT NULL,

CONSTRAINT pk_eg_expense_payment PRIMARY KEY (paymentid,billid,tenantid)
);

