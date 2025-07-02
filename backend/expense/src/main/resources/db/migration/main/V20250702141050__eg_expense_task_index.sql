CREATE INDEX idx_expense_task_status_lastmod_type
ON eg_expense_task (status, last_modified_time, type)
where status = 'IN_PROGRESS'
AND type='Transfer';