-- Create the 'eg_mb_measurements' table
CREATE TABLE eg_mb_measurements (
  id VARCHAR(128) PRIMARY KEY,
  tenantId VARCHAR(64) NOT NULL,
  mbNumber VARCHAR(128) NOT NULL,
  phyRefNumber VARCHAR(128),
  referenceId VARCHAR(128),
  entryDate BIGINT NOT NULL,
  isActive BOOLEAN,
  additionalDetails JSONB,
  createdtime BIGINT,
  createdby VARCHAR(128),
  lastmodifiedtime BIGINT,
  lastmodifiedby VARCHAR(128)
);

-- Create the 'eg_mb_measurement_details' table
CREATE TABLE eg_mb_measurement_details (
  id VARCHAR(128) PRIMARY KEY,
  referenceId VARCHAR(128) NOT NULL,
  targetId VARCHAR(128) NOT NULL,
  mbNumber VARCHAR(128) NOT NULL,
  isActive BOOLEAN,
  description VARCHAR(256),
  additionalDetails JSONB,
  createdtime BIGINT,
  createdby VARCHAR(128),
  lastmodifiedtime BIGINT,
  lastmodifiedby VARCHAR(128),
  FOREIGN KEY (referenceId) REFERENCES eg_mb_measurements (id)
);

-- Create the 'eg_mb_measurement_measures' table
CREATE TABLE eg_mb_measurement_measures (
  id VARCHAR(128) PRIMARY KEY,
  length NUMERIC,
  breadth NUMERIC,
  height NUMERIC,
  numOfItems NUMERIC,
  totalValue NUMERIC,
  createdtime BIGINT,
  createdby VARCHAR(128),
  lastmodifiedtime BIGINT,
  lastmodifiedby VARCHAR(128),
  FOREIGN KEY (id) REFERENCES eg_mb_measurement_details (id)
);