-- Create index on "id" column
CREATE INDEX IF NOT EXISTS idx_job_report_id ON job_report(id);

-- Create index on "report_number" column
CREATE INDEX IF NOT EXISTS idx_job_report_report_number ON job_report(report_number);
