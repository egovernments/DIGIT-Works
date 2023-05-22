ALTER TABLE eg_pms_project ADD COLUMN project_hierarchy character varying(1000);
ALTER TABLE eg_pms_project ALTER COLUMN name DROP NOT NULL;
ALTER TABLE eg_pms_project ALTER COLUMN project_type DROP NOT NULL;

DROP INDEX IF EXISTS index_eg_pms_project_department;
DROP INDEX IF EXISTS index_eg_pms_project_reference_id;
DROP INDEX IF EXISTS index_eg_pms_project_parent;
DROP INDEX IF EXISTS index_eg_pms_project_is_task_enabled;