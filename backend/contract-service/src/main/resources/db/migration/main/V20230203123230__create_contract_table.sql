CREATE TABLE eg_wms_contract (
  id                            character varying(256),
  contract_number               character varying(64) NOT NULL,
  tenant_id                     character varying(64) NOT NULL,
  wf_status                     character varying(64),
  executing_authority           character varying(64) NOT NULL,
  contract_type                 character varying(64),
  total_contracted_amount       decimal,
  security_deposit              decimal,
  agreement_date                bigint NOT NULL,
  defect_liability_period       bigint,
  org_id                        character varying(256) NOT NULL,
  start_date                    bigint,
  end_date                      bigint,
  status                        character varying(64) NOT NULL,
  additional_details            JSONB,
  created_by                    character varying(256)  NOT NULL,
  last_modified_by              character varying(256),
  created_time                  bigint,
  last_modified_time            bigint,
  CONSTRAINT uk_eg_wms_contract UNIQUE (contract_number),
  CONSTRAINT pk_eg_wms_contract PRIMARY KEY (id)
);

CREATE TABLE eg_wms_contract_line_items (
  id                          character varying(256),
  estimate_id                 character varying(256) NOT NULL,
  estimate_line_item_id       character varying(256),
  contract_id                 character varying(256),
  tenant_id                   character varying(64) NOT NULL,
  unit_rate                   decimal,
  no_of_unit                  decimal,
  status                      character varying(64) NOT NULL,
  additional_details          JSONB,
  created_by                  character varying(256)  NOT NULL,
  last_modified_by            character varying(256),
  created_time                bigint,
  last_modified_time          bigint,
  CONSTRAINT pk_eg_wms_contract_line_items PRIMARY KEY (id),
  CONSTRAINT fk_eg_wms_contract_line_items FOREIGN KEY (contract_id) REFERENCES eg_wms_contract (id)
);

CREATE TABLE eg_wms_contract_amount_breakups (
  id                          character varying(256),
  estimate_amount_breakup_id  character varying(256) NOT NULL,
  line_item_id                character varying(256), 
  amount                      decimal NOT NULL,
  status                      character varying(64) NOT NULL,
  additional_details          JSONB,
  created_by                  character varying(256)  NOT NULL,
  last_modified_by            character varying(256),
  created_time                bigint,
  last_modified_time          bigint,
  CONSTRAINT pk_eg_wms_contract_amount_breakups PRIMARY KEY (id),
  CONSTRAINT fk_eg_wms_contract_amount_breakups FOREIGN KEY (line_item_id) REFERENCES eg_wms_contract_line_items (id)
);

CREATE TABLE eg_wms_contract_documents (
  id                          character varying(256),
  filestore_id                character varying(64),
  document_type               character varying(64),
  document_uid                character varying(256),
  status                      character varying(64),
  contract_id                 character varying(256),
  additional_details          JSONB,
  created_by                  character varying(256)  NOT NULL,
  last_modified_by            character varying(256),
  created_time                bigint,
  last_modified_time          bigint,
  CONSTRAINT pk_eg_wms_contract_documents PRIMARY KEY (id),
  CONSTRAINT fk_eg_wms_contract_documents FOREIGN KEY (contract_id) REFERENCES eg_wms_contract (id)
);

CREATE INDEX IF NOT EXISTS index_eg_wms_contract_id ON eg_wms_contract (id);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_tenantId ON eg_wms_contract (tenant_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_status ON eg_wms_contract (status);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_contractNumber ON eg_wms_contract (contract_number);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_orgId ON eg_wms_contract (org_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_startDate ON eg_wms_contract (start_date);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_endDate ON eg_wms_contract (end_date);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_createdTime ON eg_wms_contract (created_time);

CREATE INDEX IF NOT EXISTS index_eg_wms_contract_line_items_id ON eg_wms_contract_line_items (id);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_line_items_tenantId ON eg_wms_contract_line_items (tenant_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_line_items_status ON eg_wms_contract_line_items (status);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_line_items_estimateId ON eg_wms_contract_line_items (estimate_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_line_items_estimateLineItemTd ON eg_wms_contract_line_items (estimate_line_item_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_line_items_contractId ON eg_wms_contract_line_items (contract_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_line_items_createdTime ON eg_wms_contract_line_items (created_time);

CREATE INDEX IF NOT EXISTS index_eg_wms_contract_amount_breakups_id ON eg_wms_contract_amount_breakups (id);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_amount_breakups_estimateAmountBreakupId ON eg_wms_contract_amount_breakups (estimate_amount_breakup_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_amount_breakups_status ON eg_wms_contract_amount_breakups (status);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_amount_breakups_lineItemId ON eg_wms_contract_amount_breakups (line_item_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_amount_breakups_createdTime ON eg_wms_contract_amount_breakups (created_time);

CREATE INDEX IF NOT EXISTS index_eg_wms_contract_documents_id ON eg_wms_contract_documents (id);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_documents_filestoreId ON eg_wms_contract_documents (filestore_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_documents_contractId ON eg_wms_contract_documents (contract_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_documents_documentUid ON eg_wms_contract_documents (document_uid);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_documents_status ON eg_wms_contract_documents (status);
CREATE INDEX IF NOT EXISTS index_eg_wms_contract_documents_createdTime ON eg_wms_contract_documents (created_time);