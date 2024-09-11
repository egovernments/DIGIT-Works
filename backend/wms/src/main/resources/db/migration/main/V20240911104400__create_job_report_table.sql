CREATE TABLE IF Not Exists job_report (
    id                          character varying(256),       -- Corresponds to "id"
    tenant_id                   character varying(64) NOT NULL,  -- Corresponds to "tenantId"
    report_number               character varying(128),          -- Corresponds to "reportNumber"
    report_name                 character varying(140),          -- Corresponds to "reportName"
    status                      character varying(64),           -- Corresponds to "status" (enum stored as string)
    request_payload             JSONB,                           -- Corresponds to "requestPayload"
    additional_details          JSONB,                           -- Corresponds to "additionalDetails"
    file_store_id               character varying(256),          -- Corresponds to "fileStoreId"                          -- Corresponds to "auditDetails"
    created_by                  character varying(256) NOT NULL, -- The user who created the report
    last_modified_by            character varying(256),          -- The user who last modified the report
    created_time                bigint,                          -- Timestamp of report creation
    last_modified_time          bigint,                          -- Timestamp of the last modification

    CONSTRAINT uk_job_report UNIQUE (report_number),             -- Unique constraint on "reportNumber"
    CONSTRAINT pk_job_report PRIMARY KEY (id)                    -- Primary key constraint on "id"
);
