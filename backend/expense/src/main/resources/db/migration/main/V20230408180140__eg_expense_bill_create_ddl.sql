
CREATE TABLE IF NOT EXISTS eg_expense_bill
(

id character varying(64)   NOT NULL,
tenantid character varying(250)   NOT NULL,
billdate bigint NOT NULL,
duedate bigint NOT NULL,
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

CONSTRAINT pk_eg_expense_bill PRIMARY KEY (id,tenantid),
CONSTRAINT unique_eg_expense_bill UNIQUE (referenceId, businessservice, tenantid)
);

create table eg_expense_billdetail 
(
id character varying(64)   NOT NULL,	
tenantid character varying(250)   NOT NULL,
referenceId character varying(250), -- unique id of an external entity referred by the bill detail
billid character varying(64)   NOT NULL,
paymentStatus character varying(64),
fromperiod bigint,
toperiod bigint,
createdby character varying(64)   NOT NULL,
createdtime bigint NOT NULL,
lastmodifiedby character varying(64) NOT NULL,
lastmodifiedtime bigint NOT NULL,
additionaldetails jsonb,

CONSTRAINT pk_eg_expense_billdetail PRIMARY KEY (id,tenantid),
CONSTRAINT unique_eg_expense_billdetail UNIQUE (id, billid, tenantid)

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

CONSTRAINT pk_eg_expense_lineitem PRIMARY KEY (id,tenantid),
CONSTRAINT unique_eg_expense_lineitem UNIQUE (id, billdetailid, tenantid)
);

CREATE TABLE IF NOT EXISTS eg_expense_party
(
id character varying(64)   NOT NULL,
tenantid character varying(250)   NOT NULL,
type character varying(250)  NOT NULL,
status character varying(64) NOT NULL,
identifier character varying(250)  NOT NULL,
parentid character varying(250) NOT NULL, -- whether the bill or bill detail id for payer and payee respectively 
createdby character varying(64)   NOT NULL,
createdtime bigint NOT NULL,
lastmodifiedby character varying(64) NOT NULL,
lastmodifiedtime bigint NOT NULL,
additionaldetails jsonb,

CONSTRAINT pk_eg_expense_party PRIMARY KEY (id,tenantid),
CONSTRAINT unique_eg_expense_party UNIQUE (id, parentid, tenantid)
);

   