CREATE TABLE eg_rate_analysis_schedule
(
    id                VARCHAR(256) PRIMARY KEY NOT NULL,
    jobId             VARCHAR(128) UNIQUE,
    tenantId          VARCHAR(256),
    rateEffectiveFrom BIGINT,
    jobStatus         VARCHAR(64),
    createdTime       BIGINT,
    lastModifiedTime  BIGINT,
    lastModifiedBy    VARCHAR(256),
    createdBy         VARCHAR(256)
);

CREATE TABLE eg_rate_analysis_schedule_details
(
    id                VARCHAR(256) PRIMARY KEY NOT NULL,
    sorId             VARCHAR(256),
    ratesJobId        VARCHAR(256),
    sorCode           VARCHAR(256),
    status            VARCHAR(128),
    failureReason     VARCHAR(256),
    additionalDetails JSONB,
    FOREIGN KEY (ratesJobId) REFERENCES eg_rate_analysis_schedule (id)
);

CREATE INDEX index_eg_rate_analysis_schedule_job_id ON eg_rate_analysis_schedule (jobId);
CREATE INDEX index_eg_rate_analysis_schedule_job_status ON eg_rate_analysis_schedule (jobStatus);
CREATE INDEX index_eg_rate_analysis_schedule_created_time ON eg_rate_analysis_schedule (createdTime);
CREATE INDEX index_eg_rate_analysis_schedule_details_rates_job_id ON eg_rate_analysis_schedule_details (ratesJobId);
