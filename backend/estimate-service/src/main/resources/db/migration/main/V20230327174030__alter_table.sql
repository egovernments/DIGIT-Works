ALTER TABLE eg_wms_estimate_detail ADD COLUMN is_active boolean;

ALTER TABLE eg_wms_estimate_amount_detail ADD COLUMN is_active boolean;

CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_detail_is_active ON eg_wms_estimate_detail (is_active);
CREATE INDEX IF NOT EXISTS index_eg_wms_estimate_amount_detail_is_active ON eg_wms_estimate_amount_detail (is_active);