-- Create separate table for muster roll reports
CREATE TABLE eg_wms_muster_roll_report (
    id                          character varying(256) PRIMARY KEY,
    muster_roll_id              character varying(256) NOT NULL,
    tenant_id                   character varying(64) NOT NULL,
    report_type                 character varying(128) NOT NULL,
    report_format               character varying(128) NOT NULL,
    report_status               character varying(64) NOT NULL,
    file_store_id               character varying(256),
    generated_at                bigint NOT NULL,
    error_message               text,
    created_by                  character varying(256) NOT NULL,
    last_modified_by            character varying(256),
    created_time                bigint NOT NULL,
    last_modified_time          bigint,
    CONSTRAINT fk_muster_roll_report FOREIGN KEY (muster_roll_id) REFERENCES eg_wms_muster_roll (id),
    CONSTRAINT uk_muster_roll_report UNIQUE (muster_roll_id, report_type, report_format)
);

-- Create indexes for common queries
CREATE INDEX idx_muster_roll_report_muster_id ON eg_wms_muster_roll_report (muster_roll_id);
CREATE INDEX idx_muster_roll_report_tenant_id ON eg_wms_muster_roll_report (tenant_id);
CREATE INDEX idx_muster_roll_report_status ON eg_wms_muster_roll_report (report_status);
