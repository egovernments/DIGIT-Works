CREATE TABLE IF NOT EXISTS eg_expense_payment
(
id character varying(256)   NOT NULL,
tenantid character varying(64)   NOT NULL,
netpayableamount numeric(12,2),
netpaidamount numeric(12,2),
status character varying(64) NOT NULL,
createdby character varying(64)   NOT NULL,
createdtime bigint NOT NULL,
lastmodifiedby character varying(64) NOT NULL,
lastmodifiedtime bigint NOT NULL,
additional_details jsonb,

CONSTRAINT pk_eg_expense_payment PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS eg_expense_payment_bill
(
id character varying(256)   NOT NULL,
paymentid character varying(256)   NOT NULL,
billid character varying(256)   NOT NULL,
tenantid character varying(64)  NOT NULL,
billAmount numeric(12,2) NOT NULL,
paidAmount numeric(12,2) NOT NULL,

CONSTRAINT pk_eg_expense_bill_payment PRIMARY KEY (id),
CONSTRAINT fk_eg_expense_bill_payment FOREIGN KEY (paymentid) REFERENCES eg_expense_payment (id)
);

CREATE TABLE IF NOT EXISTS eg_expense_payment_billdetail
(

id character varying(256)   NOT NULL,
billDetailId character varying(256)   NOT NULL,
paymentbillid character varying(256)   NOT NULL,
tenantid character varying(64)  NOT NULL,
netLineItemAmount numeric(12,2) NOT NULL

);

create table eg_expense_payment_lineitem
(

id character varying(128)   NOT NULL,
billdetailid character varying(128)   NOT NULL,
lineitemid character varying(128)   NOT NULL,	
tenantid character varying(250)   NOT NULL,
headCode character varying(250) NOT NULL,
amount	numeric(12,2) NOT NULL,
paidAmount numeric(12,2) NOT NULL
)
