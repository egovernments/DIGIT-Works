ALTER TABLE eg_wms_attendance_attendee ADD COLUMN tag character varying(64);
CREATE INDEX IF NOT EXISTS index_eg_wms_attendance_attendee_tag ON eg_wms_attendance_attendee (tag);