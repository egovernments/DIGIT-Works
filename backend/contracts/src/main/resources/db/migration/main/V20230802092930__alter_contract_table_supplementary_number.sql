ALTER TABLE eg_wms_contract ADD COLUMN supplement_number character varying(64);
ALTER TABLE eg_wms_contract ADD COLUMN version_number bigint;
ALTER TABLE eg_wms_contract ADD COLUMN old_uuid character varying(256);
ALTER TABLE eg_wms_contract ADD COLUMN business_service character varying(64);