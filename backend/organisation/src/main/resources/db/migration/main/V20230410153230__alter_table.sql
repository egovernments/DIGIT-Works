ALTER TABLE eg_tax_identifier ADD COLUMN is_active boolean DEFAULT TRUE;

ALTER TABLE eg_org_document ADD COLUMN is_active boolean DEFAULT TRUE;

CREATE INDEX IF NOT EXISTS index_eg_tax_identifier_is_active ON eg_tax_identifier (is_active);
CREATE INDEX IF NOT EXISTS index_eg_org_document_is_active ON eg_org_document (is_active);