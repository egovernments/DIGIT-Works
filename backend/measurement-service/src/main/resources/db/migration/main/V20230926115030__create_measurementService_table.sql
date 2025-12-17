-- Create eg_mbs_measurements Table
CREATE TABLE eg_mbs_measurements (
  id VARCHAR(128) PRIMARY KEY,
  tenantId VARCHAR(64) NOT NULL,
  mbNumber VARCHAR(128) NOT NULL,
  wfStatus VARCHAR(128) NOT NULL,
  additionalDetails JSONB,
  createdtime BIGINT,
  createdby VARCHAR(128),
  lastmodifiedtime BIGINT,
  lastmodifiedby VARCHAR(128)
);