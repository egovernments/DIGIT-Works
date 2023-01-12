CREATE TABLE eg_pms_project(
id                           character varying(64),
tenant_id                    character varying(64) NOT NULL,
project_number               character varying(128) NOT NULL,
name                         character varying(128) NOT NULL,
project_type                 character varying(64) NOT NULL,
project_subtype              character varying(64),
department                   character varying(64),
description                  character varying(256),
reference_id                 character varying(100),
start_date                   bigint,
end_date                     bigint,
is_task_enabled              boolean,
parent                       character varying(64),
additional_details           JSONB,
is_deleted                   boolean,
row_version                  numeric,
created_by                   character varying(64)  NOT NULL,
last_modified_by             character varying(64),
created_time                 bigint,
last_modified_time           bigint,

CONSTRAINT pk_eg_pms_project PRIMARY KEY (id)
);

CREATE TABLE eg_pms_address(
id                           character varying(64),
tenant_id                    character varying(64) NOT NULL,
project_id                   character varying(64) NOT NULL,
door_no                      character varying(64),
latitude                     bigint,
longitude                    bigint,
location_accuracy            bigint,
type                         character varying(64),
address_line1                character varying(256),
address_line2                character varying(256),
landmark                     character varying(256),
city                         character varying(256),
pin_code                     character varying(64),
building_name                character varying(256),
street                       character varying(256),
locality                     character varying(128),
created_by                   character varying(64)  NOT NULL,
last_modified_by             character varying(64),
created_time                 bigint,
last_modified_time           bigint,

CONSTRAINT pk_eg_pms_address PRIMARY KEY (id),
CONSTRAINT fk_eg_pms_address FOREIGN KEY (project_id) REFERENCES eg_pms_project (id)
);

CREATE TABLE eg_pms_target(
id                           character varying(64),
project_id                   character varying(64) NOT NULL,
beneficiary_type             character varying(64),
total_no                     numeric,
target_no                    numeric,
is_deleted                   boolean,
created_by                   character varying(64)  NOT NULL,
last_modified_by             character varying(64),
created_time                 bigint,
last_modified_time           bigint,

CONSTRAINT pk_eg_pms_target PRIMARY KEY (id),
CONSTRAINT fk_eg_pms_target FOREIGN KEY (project_id) REFERENCES eg_pms_project (id)
);

CREATE TABLE eg_pms_document(
id                           character varying(64),
project_id                   character varying(64) NOT NULL,
document_type                character varying(256),
filestore_id                 character varying(256) NOT NULL,
document_uid                 character varying(64),
additional_details           JSONB,
status                       character varying(64),
created_by                   character varying(64)  NOT NULL,
last_modified_by             character varying(64),
created_time                 bigint,
last_modified_time           bigint,

CONSTRAINT pk_eg_pms_document PRIMARY KEY (id),
CONSTRAINT fk_eg_pms_document FOREIGN KEY (project_id) REFERENCES eg_pms_project (id)
);



CREATE INDEX IF NOT EXISTS index_eg_pms_project_tenant_id ON eg_pms_project (tenant_id);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_project_type ON eg_pms_project (project_type);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_project_subtype ON eg_pms_project (project_subtype);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_department ON eg_pms_project (department);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_reference_id ON eg_pms_project (reference_id);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_parent ON eg_pms_project (parent);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_is_deleted ON eg_pms_project (is_deleted);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_is_task_enabled ON eg_pms_project (is_task_enabled);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_start_date ON eg_pms_project (start_date);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_end_date ON eg_pms_project (end_date);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_created_time ON eg_pms_project (created_time);

CREATE INDEX IF NOT EXISTS index_eg_pms_target_beneficiary_type ON eg_pms_target (beneficiary_type);
CREATE INDEX IF NOT EXISTS index_eg_pms_target_total_no ON eg_pms_target (total_no);
CREATE INDEX IF NOT EXISTS index_eg_pms_target_target_no ON eg_pms_target (target_no);
CREATE INDEX IF NOT EXISTS index_eg_pms_target_is_deleted ON eg_pms_target (is_deleted);
CREATE INDEX IF NOT EXISTS index_eg_pms_target_created_time ON eg_pms_target (created_time);