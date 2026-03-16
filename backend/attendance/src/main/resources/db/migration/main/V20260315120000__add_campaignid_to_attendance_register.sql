ALTER TABLE eg_wms_attendance_register ADD COLUMN IF NOT EXISTS campaignid character varying(256);
CREATE INDEX IF NOT EXISTS index_eg_wms_attendance_register_campaignid ON eg_wms_attendance_register (campaignid);

ALTER TABLE eg_wms_attendance_register ADD COLUMN IF NOT EXISTS isdeleted boolean DEFAULT false;
