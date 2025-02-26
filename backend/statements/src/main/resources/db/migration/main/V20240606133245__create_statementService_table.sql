
CREATE TABLE eg_statement (
  id character varying(256) ,
  tenantid character varying(64) NOT NULL,
  target_id character varying(256) NOT NULL,
  statement_type character varying(64),
  basic_sor_details JSONB,
  additional_details JSONB,
  createdtime BIGINT,
  createdby character varying(256),
  lastmodifiedtime BIGINT,
  lastmodifiedby character varying(256),

  CONSTRAINT pk_eg_statement PRIMARY KEY (id)
);

CREATE TABLE eg_statement_sor_details (
    id character varying(256),
    tenantid character varying(64) NOT NULL,
    statement_id character varying(256) NOT NULL,
    sorid character varying(256),
    basic_sor_details JSONB,
    additional_details JSONB,
    is_active boolean DEFAULT TRUE,
    createdtime BIGINT,
    createdby character varying(256),
    lastmodifiedtime BIGINT,
    lastmodifiedby character varying(256),
    CONSTRAINT pk_eg_statement_sor_details PRIMARY KEY (id),
    CONSTRAINT fk_eg_statement_sor_details FOREIGN KEY (statement_id) REFERENCES eg_statement (id)
);

CREATE TABLE eg_statement_sor_line_items (
    id character varying(256),
    tenantid character varying(64) NOT NULL,
    sorid character varying(256),
    sortype character varying(256),
    reference_id character varying(256),
    basic_sor_details JSONB,
    additional_details JSONB,
    createdtime BIGINT,
    createdby character varying(256),
    lastmodifiedtime BIGINT,
    lastmodifiedby character varying(256),
    CONSTRAINT pk_eg_statement_sor_line_items PRIMARY KEY (id),
    CONSTRAINT fk_eg_statement_sor_line_items FOREIGN KEY (reference_id) REFERENCES eg_statement_sor_details (id)
)



