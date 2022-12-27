CREATE TABLE eg_pms_project(
id                           character varying(64),
tenantid                     character varying(64) NOT NULL,
projecttype                  character varying(64),
projectsubtype               character varying(64),
department                   character varying(64),
description                  character varying(256),
reference_id                 character varying(100),
startdate                    bigint NOT NULL,
enddate                      bigint,
istaskenabled                boolean,
parent                       character varying(64),
additionaldetails            JSONB,
isdeleted                    boolean,
rowversion                   numeric,
createdby                    character varying(64)  NOT NULL,
lastmodifiedby               character varying(64),
createdtime                  bigint,
lastmodifiedtime             bigint,

CONSTRAINT pk_eg_pms_project PRIMARY KEY (id)
);

CREATE TABLE eg_pms_address(
id                           character varying(64),
tenantid                     character varying(64) NOT NULL,
project_id                   character varying(64) NOT NULL,
doorno                       character varying(64),
latitude                     bigint,
longitude                    bigint,
locationaccuracy             bigint,
type                         character varying(64),
addressline1                 character varying(256),
addressline2                 character varying(256),
landmark                     character varying(256),
city                         character varying(256),
pincode                      character varying(64),
buildingname                 character varying(256),
street                       character varying(256),
createdby                    character varying(64)  NOT NULL,
lastmodifiedby               character varying(64),
createdtime                  bigint,
lastmodifiedtime             bigint,

CONSTRAINT pk_eg_pms_address PRIMARY KEY (id),
CONSTRAINT fk_eg_pms_address FOREIGN KEY (project_id) REFERENCES eg_pms_project (id)
);

CREATE TABLE eg_pms_target(
id                           character varying(64),
project_id                   character varying(64) NOT NULL,
beneficiarytype              character varying(64),
totalno                      numeric,
targetno                     numeric,
isdeleted                    boolean,
createdby                    character varying(64)  NOT NULL,
lastmodifiedby               character varying(64),
createdtime                  bigint,
lastmodifiedtime             bigint,
CONSTRAINT pk_eg_pms_target PRIMARY KEY (id),
CONSTRAINT fk_eg_pms_target FOREIGN KEY (project_id) REFERENCES eg_pms_project (id)
);

CREATE TABLE eg_pms_document(
id                           character varying(64),
project_id                   character varying(64) NOT NULL,
documenttype                 character varying(256),
filestore_id                 character varying(256) NOT NULL,
documentuid                  character varying(64),
additionaldetails            JSONB,
status                       character varying(64),
createdby                    character varying(64)  NOT NULL,
lastmodifiedby               character varying(64),
createdtime                  bigint,
lastmodifiedtime             bigint,
CONSTRAINT pk_eg_pms_document PRIMARY KEY (id),
CONSTRAINT fk_eg_pms_document FOREIGN KEY (project_id) REFERENCES eg_pms_project (id)
);

CREATE TABLE eg_pms_locality(
id                           character varying(64),
code                         character varying(64),
address_id                   character varying(64),
name                         character varying(64),
latitude                     character varying(64),
longitude                    character varying(64),
materializedPath             character varying(256),

CONSTRAINT pk_eg_pms_locality PRIMARY KEY (id),
CONSTRAINT fk_eg_pms_locality FOREIGN KEY (address_id) REFERENCES eg_pms_address (id)
);

CREATE TABLE eg_pms_children(
id                           character varying(64),
locality_id                  character varying(64),

CONSTRAINT pk_eg_pms_children PRIMARY KEY (id),
CONSTRAINT fk_eg_pms_children FOREIGN KEY (locality_id) REFERENCES eg_pms_locality (id)
);



CREATE INDEX IF NOT EXISTS index_eg_pms_project_tenantId ON eg_pms_project (tenantId);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_projecttype ON eg_pms_project (projecttype);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_projectsubtype ON eg_pms_project (projectsubtype);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_department ON eg_pms_project (department);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_reference_id ON eg_pms_project (reference_id);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_parent ON eg_pms_project (parent);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_isdeleted ON eg_pms_project (isdeleted);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_istaskenabled ON eg_pms_project (istaskenabled);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_startdate ON eg_pms_project (startdate);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_enddate ON eg_pms_project (enddate);
CREATE INDEX IF NOT EXISTS index_eg_pms_project_createdtime ON eg_pms_project (createdtime);

CREATE INDEX IF NOT EXISTS index_eg_pms_target_beneficiarytype ON eg_pms_target (beneficiarytype);
CREATE INDEX IF NOT EXISTS index_eg_pms_target_totalno ON eg_pms_target (totalno);
CREATE INDEX IF NOT EXISTS index_eg_pms_target_targetno ON eg_pms_target (targetno);
CREATE INDEX IF NOT EXISTS index_eg_pms_target_isdeleted ON eg_pms_target (isdeleted);
CREATE INDEX IF NOT EXISTS index_eg_pms_target_createdtime ON eg_pms_target (createdtime);