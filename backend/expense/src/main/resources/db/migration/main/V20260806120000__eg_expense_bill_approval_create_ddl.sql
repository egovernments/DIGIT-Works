-- Create table for bill approval signature records (one immutable row per approval event)
CREATE TABLE IF NOT EXISTS eg_expense_bill_approval
(
    id                       character varying(64) NOT NULL,
    tenant_id                character varying(64) NOT NULL,
    bill_id                  character varying(64) NOT NULL,
    bill_number              character varying(64),
    business_service         character varying(128),
    approver_uuid            character varying(64) NOT NULL,
    approver_name            character varying(256) NOT NULL,
    role                     character varying(64) NOT NULL,
    signature_method         character varying(32) NOT NULL,
    signature_file_store_id  character varying(256) NOT NULL,
    approval_status          character varying(32) NOT NULL,
    approved_time            bigint NOT NULL,
    additional_details       jsonb,
    created_by               character varying(64) NOT NULL,
    created_time             bigint NOT NULL,
    last_modified_by         character varying(64) NOT NULL,
    last_modified_time       bigint NOT NULL,

    CONSTRAINT pk_eg_expense_bill_approval PRIMARY KEY (id, tenant_id)
);

-- Index for searching/enriching bills with their approval history
CREATE INDEX IF NOT EXISTS idx_eg_expense_bill_approval_bill_tenant
    ON eg_expense_bill_approval (bill_id, tenant_id);

-- Index for ordering by approval time
CREATE INDEX IF NOT EXISTS idx_eg_expense_bill_approval_approved_time
    ON eg_expense_bill_approval (approved_time DESC);
