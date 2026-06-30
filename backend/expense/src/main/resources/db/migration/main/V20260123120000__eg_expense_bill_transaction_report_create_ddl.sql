-- Create table for bill transaction reports
CREATE TABLE IF NOT EXISTS eg_expense_bill_transaction_report
(
    id character varying(64) NOT NULL,
    bill_id character varying(64) NOT NULL,
    tenant_id character varying(250) NOT NULL,
    type character varying(64) NOT NULL,
    status character varying(64) NOT NULL,
    file_store_id character varying(256),
    error_details jsonb,
    created_by character varying(64) NOT NULL,
    created_time bigint NOT NULL,
    last_modified_by character varying(64) NOT NULL,
    last_modified_time bigint NOT NULL,

    CONSTRAINT pk_eg_expense_bill_transaction_report PRIMARY KEY (id, tenant_id)
);

-- Index for searching by bill_id and tenant_id
CREATE INDEX IF NOT EXISTS idx_eg_expense_bill_txn_report_bill_tenant
    ON eg_expense_bill_transaction_report (bill_id, tenant_id);

-- Index for searching by status
CREATE INDEX IF NOT EXISTS idx_eg_expense_bill_txn_report_status
    ON eg_expense_bill_transaction_report (status);

-- Index for ordering by last_modified_time
CREATE INDEX IF NOT EXISTS idx_eg_expense_bill_txn_report_modified_time
    ON eg_expense_bill_transaction_report (last_modified_time DESC);
