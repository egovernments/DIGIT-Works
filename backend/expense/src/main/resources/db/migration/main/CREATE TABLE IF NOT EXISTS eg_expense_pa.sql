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