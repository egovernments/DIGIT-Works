CREATE TABLE eg_rate_analysis_schedule
(
    id                VARCHAR(256) PRIMARY KEY NOT NULL,
    jobId             VARCHAR(128) UNIQUE,
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
    ratesJobId        VARCHAR(256),
    sorId             VARCHAR(256),
    status            VARCHAR(128),
    failureReason     VARCHAR(256),
    additionalDetails JSONB,
    FOREIGN KEY (ratesJobId) REFERENCES eg_rate_analysis_schedule (id)
);
