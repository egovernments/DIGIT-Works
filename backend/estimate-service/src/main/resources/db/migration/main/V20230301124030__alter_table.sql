ALTER TABLE eg_wms_estimate ALTER COLUMN reference_number DROP NOT NULL;
ALTER TABLE eg_wms_estimate ALTER COLUMN description DROP NOT NULL;

ALTER TABLE eg_wms_estimate_detail ALTER COLUMN name DROP NOT NULL;
ALTER TABLE eg_wms_estimate_detail ALTER COLUMN description DROP NOT NULL;
ALTER TABLE eg_wms_estimate_detail ALTER COLUMN Category DROP NOT NULL;

ALTER TABLE eg_wms_estimate_address ALTER COLUMN latitude DROP NOT NULL;
ALTER TABLE eg_wms_estimate_address ALTER COLUMN longitude DROP NOT NULL;
ALTER TABLE eg_wms_estimate_address ALTER COLUMN address_number DROP NOT NULL;
ALTER TABLE eg_wms_estimate_address ALTER COLUMN address_line_1 DROP NOT NULL;
ALTER TABLE eg_wms_estimate_address ALTER COLUMN city DROP NOT NULL;
ALTER TABLE eg_wms_estimate_address ALTER COLUMN pin_code DROP NOT NULL;
ALTER TABLE eg_wms_estimate_address ALTER COLUMN tenant_id DROP NOT NULL;



ALTER TABLE eg_wms_estimate_amount_detail ALTER COLUMN type DROP NOT NULL;

