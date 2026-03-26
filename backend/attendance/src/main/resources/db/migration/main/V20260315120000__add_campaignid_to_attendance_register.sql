ALTER TABLE eg_wms_attendance_register ADD COLUMN IF NOT EXISTS campaignnumber character varying(256);
CREATE INDEX IF NOT EXISTS index_eg_wms_attendance_register_campaignnumber ON eg_wms_attendance_register (campaignnumber);

ALTER TABLE eg_wms_attendance_register ADD COLUMN IF NOT EXISTS isdeleted boolean DEFAULT false;
