DROP TABLE IF EXISTS eg_bank_branch_identifier;
DROP TABLE IF EXISTS eg_bank_accounts_doc;
DROP TABLE IF EXISTS eg_bank_account_detail;
DROP TABLE IF EXISTS eg_bank_account;


CREATE TABLE eg_bank_account(

id                           character varying(256),
tenant_id                    character varying(64) NOT NULL,
service_code                 character varying(128) NOT NULL,
reference_id                 character varying(256) NOT NULL,
additional_details           JSONB,
created_by                   character varying(256)  NOT NULL,
last_modified_by             character varying(256),
created_time                 bigint,
last_modified_time           bigint,

CONSTRAINT pk_eg_bank_account PRIMARY KEY (id)
);

CREATE TABLE eg_bank_account_detail(

id                           character varying(256),
tenant_id                    character varying(64) NOT NULL,
bank_account_id              character varying(256) NOT NULL,
account_holder_name          character varying(256),
account_number               character varying(256) NOT NULL,
account_type                 character varying(140) NOT NULL,
is_primary                   boolean,
is_active                    boolean,
additional_details           JSONB,
created_by                   character varying(256)  NOT NULL,
last_modified_by             character varying(256),
created_time                 bigint,
last_modified_time           bigint,

CONSTRAINT pk_eg_bank_account_detail PRIMARY KEY (id),
CONSTRAINT fk_eg_bank_account_detail FOREIGN KEY (bank_account_id) REFERENCES eg_bank_account (id)
);

CREATE TABLE eg_bank_accounts_doc(

id                           character varying(256),
bank_account_detail_id       character varying(64) NOT NULL,
document_type                character varying,
file_store                   character varying,
document_uid                 character varying(256),
additional_details           jsonb,

CONSTRAINT pk_eg_bank_accounts_doc PRIMARY KEY (id),
CONSTRAINT fk_eg_bank_accounts_doc FOREIGN KEY (bank_account_detail_id) REFERENCES eg_bank_account_detail (id)
);

CREATE TABLE eg_bank_branch_identifier(

id                           character varying(256),
bank_account_detail_id       character varying(64) NOT NULL,
type                         character varying(140),
code                         character varying(140),
additional_details           JSONB,

CONSTRAINT pk_eg_bank_branch_identifier PRIMARY KEY (id),
CONSTRAINT fk_eg_bank_branch_identifier FOREIGN KEY (bank_account_detail_id) REFERENCES eg_bank_account_detail (id)
);