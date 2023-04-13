CREATE TABLE IF NOT EXISTS eg_works_calculation(
id                           character varying(256),
tenant_id                    character varying(64) NOT NULL,
service_code                 character varying(128) NOT NULL,
contract_id                  character varying(256),
musterroll_id                character varying(256),
bill_type                    character varying(128),
bill_id                      character varying(256) NOT NULL,
is_active                    boolean,
additionaldetails            JSONB,
createdtime                  bigint,
createdby                    character varying(64),
lastmodifiedtime             bigint,
lastmodifiedby               character varying(64),
CONSTRAINT pk_eg_works_calculation PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS index_eg_works_calculation_tenantId ON eg_works_calculation (tenant_id);
CREATE INDEX IF NOT EXISTS index_eg_works_calculation_serviceCode ON eg_works_calculation (service_code);
CREATE INDEX IF NOT EXISTS index_eg_works_calculation_contractId ON eg_works_calculation (contract_id);
CREATE INDEX IF NOT EXISTS index_eg_works_calculation_musterrollId ON eg_works_calculation (musterroll_id);
CREATE INDEX IF NOT EXISTS index_eg_works_calculation_billId ON eg_works_calculation (bill_id);
CREATE INDEX IF NOT EXISTS index_eg_works_calculation_billType ON eg_works_calculation (bill_type);

