ALTER TABLE eg_wms_estimate_detail ADD COLUMN length NUMERIC DEFAULT NULL;

ALTER TABLE eg_wms_estimate_detail ADD COLUMN width NUMERIC DEFAULT NULL;

ALTER TABLE eg_wms_estimate_detail ADD COLUMN height NUMERIC DEFAULT NULL;

ALTER TABLE eg_wms_estimate_detail ADD COLUMN quantity NUMERIC DEFAULT NULL;

ALTER TABLE eg_wms_estimate_detail ADD COLUMN is_deduction boolean DEFAULT FALSE;