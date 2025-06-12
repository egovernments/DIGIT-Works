CREATE TABLE IF NOT EXISTS eg_expense_task (
    id VARCHAR(64) PRIMARY KEY,
    status VARCHAR(50),
    created_by VARCHAR(64),
    last_modified_by VARCHAR(64),
    created_time BIGINT,
    last_modified_time BIGINT,
    additional_details JSONB
);

CREATE TABLE IF NOT EXISTS eg_expense_task_details (
    id VARCHAR(64) PRIMARY KEY,
    tenant_id VARCHAR(64) NOT NULL,
    bill_id VARCHAR(64),
    bill_details_id VARCHAR(64),
    payee_id VARCHAR(64) NOT NULL,
    task_id VARCHAR(64),
    reference_id VARCHAR(64),
    response_message TEXT,
    status VARCHAR(50),
    reason_for_failure TEXT,
    created_by VARCHAR(64),
    last_modified_by VARCHAR(64),
    created_time BIGINT,
    last_modified_time BIGINT,
    additional_details JSONB
);