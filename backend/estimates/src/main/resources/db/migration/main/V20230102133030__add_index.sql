CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_id ON eg_wms_estimate (id);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_tenant_id ON eg_wms_estimate (tenant_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_estimate_number ON eg_wms_estimate (estimate_number);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_project_id ON eg_wms_estimate (project_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_proposal_date ON eg_wms_estimate (proposal_date);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_status ON eg_wms_estimate (status);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_wf_status ON eg_wms_estimate (wf_status);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_reference_number ON eg_wms_estimate (reference_number);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_executing_department ON eg_wms_estimate (executing_department);

CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_detail_id ON eg_wms_estimate_detail (id);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_detail_tenant_id ON eg_wms_estimate_detail (tenant_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_detail_estimate_id ON eg_wms_estimate_detail (estimate_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_detail_category ON eg_wms_estimate_detail (category);

CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_amount_detail_id ON eg_wms_estimate_amount_detail (id);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_amount_detail_tenant_id ON eg_wms_estimate_amount_detail (tenant_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_amount_detail_estimate_detail_id ON eg_wms_estimate_amount_detail (estimate_detail_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_amount_detail_type ON eg_wms_estimate_amount_detail (type);

CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_address_id ON eg_wms_estimate_address (id);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_address_tenant_id ON eg_wms_estimate_address (tenant_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_address_estimate_id ON eg_wms_estimate_address (estimate_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_address_city ON eg_wms_estimate_address (city);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_address_pin_code ON eg_wms_estimate_address (pin_code);