
CREATE TABLE IF NOT EXISTS eg_expense_bill_gen_status
(

id character varying(64)   NOT NULL,
tenantid character varying(64) NOT NULL,
referenceid character varying(256) NOT NULL,
status character varying(64) NOT NULL,
error text

)
   