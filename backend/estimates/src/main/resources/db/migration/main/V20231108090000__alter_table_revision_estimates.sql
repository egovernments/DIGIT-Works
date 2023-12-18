ALTER TABLE eg_wms_estimate DROP CONSTRAINT uk_eg_wms_estimate;
ALTER TABLE eg_wms_estimate ADD COLUMN revision_number character varying(64);
ALTER TABLE eg_wms_estimate ADD COLUMN version_number bigint;
ALTER TABLE eg_wms_estimate ADD COLUMN old_uuid character varying(256);
ALTER TABLE eg_wms_estimate ADD COLUMN business_service character varying(64);
ALTER TABLE eg_wms_estimate_detail ADD COLUMN old_uuid character varying(256);