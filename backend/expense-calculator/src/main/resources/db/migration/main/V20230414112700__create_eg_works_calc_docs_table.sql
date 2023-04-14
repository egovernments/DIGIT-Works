CREATE TABLE eg_works_calculation_documents (

  id                          character varying(256),

  filestore_id                character varying(64),

  document_type               character varying(64),

  document_uid                character varying(256),

  status                      character varying(64),

  calculation_id              character varying(256),

  additional_details          JSONB,

  created_by                  character varying(256)  NOT NULL,

  last_modified_by            character varying(256),

  created_time                bigint,

  last_modified_time          bigint,

  CONSTRAINT pk_eg_works_calculation_documents PRIMARY KEY (id),

  CONSTRAINT fk_eg_works_calculation_documents FOREIGN KEY (calculation_id) REFERENCES eg_works_calculation (id)

);