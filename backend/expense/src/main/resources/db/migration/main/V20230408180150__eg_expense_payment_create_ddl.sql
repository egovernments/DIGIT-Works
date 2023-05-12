CREATE TABLE IF NOT EXISTS eg_expense_payment
(
id character varying(256)   NOT NULL,
tenantid character varying(64)   NOT NULL,
netpayableamount numeric(12,2) NOT NULL,
netpaidamount numeric(12,2) NOT NULL,
paymentnumber character varying(128) NOT NULL,
status character varying(64) NOT NULL,
createdby character varying(64)   NOT NULL,
createdtime bigint NOT NULL,
lastmodifiedby character varying(64) NOT NULL,
lastmodifiedtime bigint NOT NULL,
additionaldetails jsonb,

CONSTRAINT pk_eg_expense_payment PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS eg_expense_payment_bill
(

id character varying(256)   NOT NULL,
tenantid character varying(64)  NOT NULL,
paymentid character varying(256)   NOT NULL,
billid character varying(256)   NOT NULL,
totalamount numeric(12,2) NOT NULL,
totalpaidamount numeric(12,2) NOT NULL,
status character varying(64) NOT NULL,
createdby character varying(64)   NOT NULL,
createdtime bigint NOT NULL,
lastmodifiedby character varying(64) NOT NULL,
lastmodifiedtime bigint NOT NULL,

CONSTRAINT pk_eg_expense_payment_bill PRIMARY KEY (id),
CONSTRAINT fk_eg_expense_payment_bill FOREIGN KEY (paymentid) REFERENCES eg_expense_payment (id)
);

CREATE TABLE IF NOT EXISTS eg_expense_payment_billdetail
(

id character varying(256)   NOT NULL,
tenantid character varying(64)  NOT NULL,
billDetailId character varying(256)   NOT NULL,
paymentbillid character varying(256)   NOT NULL,
totalamount numeric(12,2) NOT NULL,
totalpaidamount numeric(12,2) NOT NULL,
status character varying(64) NOT NULL,
createdby character varying(64)   NOT NULL,
createdtime bigint NOT NULL,
lastmodifiedby character varying(64) NOT NULL,
lastmodifiedtime bigint NOT NULL,


CONSTRAINT pk_eg_expense_payment_billdetail PRIMARY KEY (id),
CONSTRAINT fk_eg_expense_payment_billdetail FOREIGN KEY (paymentbillid) REFERENCES eg_expense_payment_bill (id)
);

CREATE TABLE IF NOT EXISTS eg_expense_payment_lineitem
(

id character varying(128)   NOT NULL,
tenantid character varying(250)   NOT NULL,
paymentbilldetailid character varying(128)   NOT NULL,
lineitemid character varying(128)   NOT NULL,	
paidAmount numeric(12,2) NOT NULL,
status character varying(64) NOT NULL,
createdby character varying(64)   NOT NULL,
createdtime bigint NOT NULL,
lastmodifiedby character varying(64) NOT NULL,
lastmodifiedtime bigint NOT NULL,

CONSTRAINT pk_eg_expense_payment_lineitem PRIMARY KEY (id),
CONSTRAINT fk_eg_expense_payment_lineitem FOREIGN KEY (paymentbilldetailid) REFERENCES eg_expense_payment_billdetail (id)
);
