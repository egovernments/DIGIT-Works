ALTER TABLE eg_statement_sor_details ADD COLUMN additional_details JSONB IF NOT EXISTS;
ALTER TABLE eg_statement_sor_line_items ADD COLUMN additional_details JSONB IF NOT EXISTS;