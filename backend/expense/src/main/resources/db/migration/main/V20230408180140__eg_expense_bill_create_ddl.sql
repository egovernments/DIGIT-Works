
CREATE TABLE IF NOT EXISTS eg_expense_bill
(

id character varying(64)   NOT NULL,
tenantid character varying(250) NOT NULL,
billdate bigint NOT NULL,
duedate bigint,
netPayableAmount numeric(12,2),
netPaidAmount numeric(12,2),
businessservice character varying(250)   NOT NULL,
referenceId character varying(250)   NOT NULL, -- id of the entity for which the bill is getting created 
fromperiod bigint,
toperiod bigint,
status character varying(64) NOT NULL,
paymentStatus character varying(64),
createdby character varying(64)   NOT NULL,
createdtime bigint NOT NULL,
lastmodifiedby character varying(64) NOT NULL,
lastmodifiedtime bigint NOT NULL,
additionaldetails jsonb,

CONSTRAINT pk_eg_expense_bill PRIMARY KEY (id),
CONSTRAINT unique_eg_expense_bill UNIQUE (referenceId, businessservice, tenantid)
);

create table eg_expense_billdetail 
(
id character varying(64)   NOT NULL,
tenantid character varying(250) NOT NULL,
referenceId character varying(250), -- unique id of an external entity referred by the bill detail
billid character varying(64)   NOT NULL,
paymentStatus character varying(64),
fromperiod bigint,
toperiod bigint,
netLineItemAmount numeric(12,2),
createdby character varying(64)   NOT NULL,
createdtime bigint NOT NULL,
lastmodifiedby character varying(64) NOT NULL,
lastmodifiedtime bigint NOT NULL,
additionaldetails jsonb,

CONSTRAINT pk_eg_expense_billdetail PRIMARY KEY (id),
CONSTRAINT fk_eg_expense_billdetail FOREIGN KEY (billid) REFERENCES eg_expense_bill (id)

);

create table eg_expense_lineitem
(

id character varying(64)   NOT NULL,
billdetailid character varying(64)   NOT NULL,	
tenantid character varying(250)   NOT NULL,
headCode character varying(250) NOT NULL,
amount	numeric(12,2) NOT NULL,
paidAmount numeric(12,2) NOT NULL,
type character varying(64)   NOT NULL,	
status character varying(64) NOT NULL,
islineitempayable boolean NOT NULL,
createdby character varying(64)   NOT NULL,
createdtime bigint NOT NULL,
lastmodifiedby character varying(64) NOT NULL,
lastmodifiedtime bigint NOT NULL,
additionaldetails jsonb,

CONSTRAINT pk_eg_expense_lineitem PRIMARY KEY (id),
CONSTRAINT fk_eg_expense_lineitem FOREIGN KEY (billdetailid) REFERENCES eg_expense_billdetail (id)

);

CREATE TABLE IF NOT EXISTS eg_expense_party_payer
(
id character varying(64)   NOT NULL,
tenantid character varying(250) NOT NULL,
type character varying(250)  NOT NULL,
status character varying(64) NOT NULL,
identifier character varying(250)  NOT NULL,
parentid character varying(250) NOT NULL, -- whether the bill or bill detail id for payer and payee respectively 
createdby character varying(64)   NOT NULL,
createdtime bigint NOT NULL,
lastmodifiedby character varying(64) NOT NULL,
lastmodifiedtime bigint NOT NULL,
additionaldetails jsonb,

CONSTRAINT pk_eg_expense_party_payer PRIMARY KEY (id),
CONSTRAINT fk_eg_expense_party_payer FOREIGN KEY (parentid) REFERENCES eg_expense_bill (id)
);

CREATE TABLE IF NOT EXISTS eg_expense_party_payee
(
id character varying(64)   NOT NULL,
tenantid character varying(250) NOT NULL,
type character varying(250)  NOT NULL,
status character varying(64) NOT NULL,
identifier character varying(250)  NOT NULL,
parentid character varying(250) NOT NULL, -- whether the bill or bill detail id for payer and payee respectively
createdby character varying(64)   NOT NULL,
createdtime bigint NOT NULL,
lastmodifiedby character varying(64) NOT NULL,
lastmodifiedtime bigint NOT NULL,
additionaldetails jsonb,

CONSTRAINT pk_eg_expense_party_payee PRIMARY KEY (id),
CONSTRAINT fk_eg_expense_party_payee FOREIGN KEY (parentid) REFERENCES eg_expense_billdetail (billid)
);

   