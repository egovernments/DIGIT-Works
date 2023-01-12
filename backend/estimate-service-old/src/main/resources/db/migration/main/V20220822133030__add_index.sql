CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_tenantId ON eg_wms_estimate (tenantId);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_id ON eg_wms_estimate (id);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_estimate_number ON eg_wms_estimate (estimate_number);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_admin_sanction_number ON eg_wms_estimate (admin_sanction_number);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_proposal_date ON eg_wms_estimate (proposal_date);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_estimate_status ON eg_wms_estimate (estimate_status);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_requirement_number ON eg_wms_estimate (requirement_number);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_location ON eg_wms_estimate (location);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_department ON eg_wms_estimate (department);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_createdtime ON eg_wms_estimate (createdtime);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_work_category ON eg_wms_estimate (work_category);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_beneficiary_type ON eg_wms_estimate (beneficiary_type);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_nature_of_work ON eg_wms_estimate (nature_of_work);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_type_of_work ON eg_wms_estimate (type_of_work);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_subtype_of_work ON eg_wms_estimate (subtype_of_work);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_entrustment_mode ON eg_wms_estimate (entrustment_mode);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_fund ON eg_wms_estimate (fund);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_function ON eg_wms_estimate (function);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_budget_head ON eg_wms_estimate (budget_head);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_scheme ON eg_wms_estimate (scheme);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_sub_scheme ON eg_wms_estimate (sub_scheme);


CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_detail_id ON eg_wms_estimate_detail (id);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_detail_tenantId ON eg_wms_estimate_detail (tenantId);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_detail_estimate_id ON eg_wms_estimate_detail (estimate_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_detail_estimate_detail_number ON eg_wms_estimate_detail (estimate_detail_number);
