CREATE INDEX IF NOT EXISTS idx_job_report_tenant_id ON job_report(tenant_id);

CREATE INDEX IF NOT EXISTS idx_job_report_tenant_status
    ON job_report(tenant_id, status);

-- Create a composite index on tenant_id, status, and created_time columns
CREATE INDEX IF NOT EXISTS idx_job_report_tenant_status_time
    ON job_report(tenant_id, status, created_time);

