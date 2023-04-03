DROP TABLE IF EXISTS eg_org_address_geo_location;

DROP TABLE IF EXISTS eg_org_address;
DROP TABLE IF EXISTS eg_org_contact_detail;
DROP TABLE IF EXISTS eg_tax_identifier;
DROP TABLE IF EXISTS eg_org_jurisdiction;
DROP TABLE IF EXISTS eg_org_document;
DROP TABLE IF EXISTS eg_org_function;

DROP TABLE IF EXISTS eg_org;



CREATE TABLE eg_org (

  id                     character varying(256),
  tenant_id              character varying(64) NOT NULL,
  application_number     character varying(140) NOT NULL,
  name                   character varying(140) NOT NULL,
  org_number             character varying(140),
  external_ref_number    character varying(64),
  date_of_incorporation  bigint,
  application_status     character varying(256),
  is_active              boolean,
  additional_details     jsonb,
  created_by             character varying(64),
  last_modified_by       character varying(64),
  created_time           bigint,
  last_modified_time     bigint,

CONSTRAINT uk_eg_org UNIQUE (application_number),
CONSTRAINT pk_eg_org PRIMARY KEY (id)
);

CREATE TABLE eg_org_address (

  id                  character varying(256),
  tenant_id           character varying(64),
  org_id              character varying(256) NOT NULL,
  door_no             character varying,
  plot_no             character varying,
  landmark            character varying,
  city                character varying,
  pin_code            character varying,
  district            character varying,
  region              character varying,
  state               character varying,
  country             character varying,
  boundary_code       character varying,
  boundary_type       character varying,
  building_name       character varying(64),
  street              character varying(64),
  additional_details  jsonb,


  CONSTRAINT pk_eg_org_address PRIMARY KEY (id),
  CONSTRAINT fk_eg_org_address FOREIGN KEY (org_id) REFERENCES eg_org (id)
);

--CREATE TABLE eg_org_address_boundary (
--
--  id                character varying(256),
--  address_id        character varying(256) NOT NULL,
--  code              character varying,
--  name              character varying,
--  label             character varying,
--  latitude          numeric,
--  longitude         numeric,
--
-- CONSTRAINT pk_eg_org_address_boundary PRIMARY KEY (id),
-- CONSTRAINT fk_eg_org_address_boundary FOREIGN KEY (address_id) REFERENCES eg_org_address (id)
--);

CREATE TABLE eg_org_address_geo_location (
  id                   character varying(256),
  address_id           character varying(256) NOT NULL,
  latitude             numeric,
  longitude            numeric,
  additional_details   jsonb,

  CONSTRAINT pk_eg_org_address_geo_location PRIMARY KEY (id),
  CONSTRAINT fk_eg_org_address_geo_location FOREIGN KEY (address_id) REFERENCES eg_org_address (id)
);

CREATE TABLE eg_org_contact_detail (
  id                       character varying(256),
  tenant_id                character varying(64),
  org_id                   character varying(256) NOT NULL,
  contact_name             character varying(64),
  contact_mobile_number    character varying(15),
  contact_email            character varying(64),

  CONSTRAINT pk_eg_org_contact_detail PRIMARY KEY (id),
  CONSTRAINT fk_eg_org_contact_detail FOREIGN KEY (org_id) REFERENCES eg_org (id)
);

CREATE TABLE eg_tax_identifier (
  id                     character varying(256),
  org_id                 character varying(256) NOT NULL,
  type                   character varying(64),
  value                  character varying(64),
  additional_details     jsonb,

  CONSTRAINT pk_eg_tax_identifier PRIMARY KEY (id),
  CONSTRAINT fk_eg_tax_identifier FOREIGN KEY (org_id) REFERENCES eg_org (id)
);

CREATE TABLE eg_org_jurisdiction (
  id                   character varying(256),
  org_id               character varying(256) NOT NULL,
  code                 character varying(64) NOT NULL,
  additional_details   jsonb,

  CONSTRAINT pk_eg_org_jurisdiction PRIMARY KEY (id),
  CONSTRAINT fk_eg_org_jurisdiction FOREIGN KEY (org_id) REFERENCES eg_org (id)
);

CREATE TABLE eg_org_function (
  id                        character varying(256),
  org_id                    character varying(256) NOT NULL,
  application_number        character varying(140) NOT NULL,
  type                      character varying(256),
  category                  character varying(256),
  class                     character varying(256),
  valid_from                bigint,
  valid_to                  bigint,
  application_status        character varying(256),
  wf_status                 character varying(256),
  is_active                 boolean,
  additional_details        jsonb,
  created_by                character varying(64),
  last_modified_by          character varying(64),
  created_time              bigint,
  last_modified_time        bigint,

  CONSTRAINT pk_eg_org_function PRIMARY KEY (id),
  CONSTRAINT fk_eg_org_function FOREIGN KEY (org_id) REFERENCES eg_org (id)
);

CREATE TABLE eg_org_document (
  id                    character varying(256),
  org_id                character varying(256),
  org_func_id           character varying(256),
  document_type         character varying,
  file_store            character varying,
  document_uid          character varying(256),
  additional_details    jsonb,

  CONSTRAINT pk_eg_org_document PRIMARY KEY (id),
  CONSTRAINT fk_eg_org_document FOREIGN KEY (org_id) REFERENCES eg_org (id),
  CONSTRAINT fk_eg_org_document_func FOREIGN KEY (org_func_id) REFERENCES eg_org_function (id)
);

