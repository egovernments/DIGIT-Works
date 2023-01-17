ALTER TABLE eg_pms_project ADD COLUMN project_hierarchy character varying(256);
ALTER TABLE eg_pms_project ALTER COLUMN name DROP NOT NULL;
ALTER TABLE eg_pms_project ALTER COLUMN project_type DROP NOT NULL;