CREATE TABLE IF NOT EXISTS eg_works_calculation(
id                           character varying(256),
tenant_id                    character varying(64) NOT NULL,
business_service             character varying(128),
bill_id                      character varying(256) NOT NULL,
bill_number                  character varying(128),
bill_reference               character varying(128),
contract_number              character varying(128),
musterroll_number            character varying(128),
project_number               character varying(128),
org_id                       character varying(256),
is_active                    boolean,
additionaldetails            JSONB,
createdtime                  bigint,
createdby                    character varying(64),
lastmodifiedtime             bigint,
lastmodifiedby               character varying(64),
CONSTRAINT pk_eg_works_calculation PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS eg_works_calc_details (
id                           character varying(256),
tenant_id                    character varying(64) NOT NULL,
calculation_id               character varying(256),
payee_id                     character varying(256),
billingslab_code             character varying(64),
is_active                    boolean,
additionaldetails            JSONB,
createdtime                  bigint,
createdby                    character varying(64),
lastmodifiedtime             bigint,
lastmodifiedby               character varying(64),
CONSTRAINT pk_eg_works_calc_details PRIMARY KEY (id),
CONSTRAINT fk_eg_works_calc_details FOREIGN KEY (calculation_id) REFERENCES eg_works_calculation (id)
);
