CREATE TABLE IF NOT EXISTS eg_expense_scheduler_job (
    id               VARCHAR(64)  NOT NULL,
    tenant_id        VARCHAR(64)  NOT NULL,
    job_type         VARCHAR(64)  NOT NULL,
    reference_id     VARCHAR(64)  NOT NULL,
    scheduler_status VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    next_check_at    BIGINT       DEFAULT NULL,
    attempt_count    INT          NOT NULL DEFAULT 0,
    max_attempts     INT          NOT NULL DEFAULT 200,
    context          JSONB,
    created_at       BIGINT       NOT NULL,
    updated_at       BIGINT       NOT NULL,
    CONSTRAINT pk_scheduler_job PRIMARY KEY (id),
    CONSTRAINT uq_scheduler_job_active UNIQUE (tenant_id, job_type, reference_id)
);

CREATE INDEX IF NOT EXISTS idx_scheduler_job_poll
    ON eg_expense_scheduler_job (next_check_at)
    WHERE scheduler_status = 'PENDING';
