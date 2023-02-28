ALTER TABLE eg_pms_project ADD COLUMN nature_of_work character varying(64);
ALTER TABLE eg_pms_address RENAME COLUMN locality TO boundary;
ALTER TABLE eg_pms_address ADD COLUMN boundary_type character varying(64);