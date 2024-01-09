CREATE TABLE eg_mukta_ifms_adapter (
    id VARCHAR(64) PRIMARY KEY,
    tenantId VARCHAR(64) NOT NULL,
    paymentId VARCHAR(64) NOT NULL,
    paymentStatus VARCHAR(64) NOT NULL,
    paymentType VARCHAR(64) NOT NULL,
    disburseData JSONB,
    additionalDetails JSONB,
    createdtime BIGINT,
    createdby VARCHAR(256),
    lastmodifiedtime BIGINT,
    lastmodifiedby VARCHAR(256)
);
