CREATE INDEX IF NOT EXISTS index_eg_org_id ON eg_org (id);
CREATE INDEX IF NOT EXISTS index_eg_org_tenant_id ON eg_org (tenant_id);
CREATE INDEX IF NOT EXISTS index_eg_org_application_number ON eg_org (application_number);
CREATE INDEX IF NOT EXISTS index_eg_org_org_number ON eg_org (org_number);
CREATE INDEX IF NOT EXISTS index_eg_org_application_status ON eg_org (application_status);
CREATE INDEX IF NOT EXISTS index_eg_org_external_ref_number ON eg_org (external_ref_number);
CREATE INDEX IF NOT EXISTS index_eg_org_is_active ON eg_org (is_active);

CREATE INDEX IF NOT EXISTS index_eg_org_address_tenant_id ON eg_org_address (tenant_id);
CREATE INDEX IF NOT EXISTS index_eg_org_address_org_id ON eg_org_address (org_id);
CREATE INDEX IF NOT EXISTS index_eg_org_address_city ON eg_org_address (city);
CREATE INDEX IF NOT EXISTS index_eg_org_address_pin_code ON eg_org_address (pin_code);
CREATE INDEX IF NOT EXISTS index_eg_org_address_district ON eg_org_address (district);
CREATE INDEX IF NOT EXISTS index_eg_org_address_region ON eg_org_address (region);
CREATE INDEX IF NOT EXISTS index_eg_org_address_state ON eg_org_address (state);
CREATE INDEX IF NOT EXISTS index_eg_org_address_street ON eg_org_address (street);
CREATE INDEX IF NOT EXISTS index_eg_org_address_country ON eg_org_address (country);

CREATE INDEX IF NOT EXISTS index_eg_org_address_geo_location_address_id ON eg_org_address_geo_location (address_id);
CREATE INDEX IF NOT EXISTS index_eg_org_address_geo_location_latitude ON eg_org_address_geo_location (latitude);
CREATE INDEX IF NOT EXISTS index_eg_org_address_geo_location_latitude ON eg_org_address_geo_location (latitude);

CREATE INDEX IF NOT EXISTS index_eg_org_contact_detail_tenant_id ON eg_org_contact_detail (tenant_id);
CREATE INDEX IF NOT EXISTS index_eg_org_contact_detail_contact_mobile_number ON eg_org_contact_detail (contact_mobile_number);
CREATE INDEX IF NOT EXISTS index_eg_org_contact_detail_org_id ON eg_org_contact_detail (org_id);
CREATE INDEX IF NOT EXISTS index_eg_org_contact_detail_contact_email ON eg_org_contact_detail (contact_email);

CREATE INDEX IF NOT EXISTS index_eg_tax_identifier_org_id ON eg_tax_identifier (org_id);
CREATE INDEX IF NOT EXISTS index_eg_tax_identifier_type ON eg_tax_identifier (type);

CREATE INDEX IF NOT EXISTS index_eg_org_jurisdiction_org_id ON eg_org_jurisdiction (org_id);
CREATE INDEX IF NOT EXISTS index_eg_org_jurisdiction_code ON eg_org_jurisdiction (code);

CREATE INDEX IF NOT EXISTS index_eg_org_document_org_id ON eg_org_document (org_id);
CREATE INDEX IF NOT EXISTS index_eg_org_document_document_type ON eg_org_document (document_type);
CREATE INDEX IF NOT EXISTS index_eg_org_document_document_uid ON eg_org_document (document_uid);

CREATE INDEX IF NOT EXISTS index_eg_org_function_org_id ON eg_org_function (org_id);
CREATE INDEX IF NOT EXISTS index_eg_org_function_application_number ON eg_org_function (application_number);
CREATE INDEX IF NOT EXISTS index_eg_org_function_is_active ON eg_org_function (is_active);
CREATE INDEX IF NOT EXISTS index_eg_org_function_type ON eg_org_function (type);
CREATE INDEX IF NOT EXISTS index_eg_org_function_category ON eg_org_function (category);
CREATE INDEX IF NOT EXISTS index_eg_org_function_wf_status ON eg_org_function (wf_status);
CREATE INDEX IF NOT EXISTS index_eg_org_function_class ON eg_org_function (class);