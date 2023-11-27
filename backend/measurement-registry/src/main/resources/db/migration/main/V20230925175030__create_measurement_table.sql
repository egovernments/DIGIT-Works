-- Create eg_mb_measurements Table
CREATE TABLE eg_mb_measurements (
  id VARCHAR(128) PRIMARY KEY,
  tenantId VARCHAR(64) NOT NULL,
  mbNumber VARCHAR(128) NOT NULL,
  phyRefNumber VARCHAR(128),
  referenceId VARCHAR(128),
  entryDate BIGINT NOT NULL,
  isActive BOOLEAN,
  additionalDetails JSONB,
  createdtime BIGINT NOT NULL,
  createdby VARCHAR(128) NOT NULL,
  lastmodifiedtime BIGINT,
  lastmodifiedby VARCHAR(128)
);

-- Create eg_mb_measurement_details Table
CREATE TABLE eg_mb_measurement_details (
  id VARCHAR(128) PRIMARY KEY,
  referenceId VARCHAR(128) NOT NULL,
  targetId VARCHAR(128) NOT NULL,
  isActive BOOLEAN,
  description VARCHAR(256),
  additionalDetails JSONB,
  createdtime BIGINT,
  createdby VARCHAR(128),
  lastmodifiedtime BIGINT,
  lastmodifiedby VARCHAR(128),
  CONSTRAINT fk_measurement_details_referenceId
    FOREIGN KEY (referenceId) REFERENCES eg_mb_measurements(id)
);

-- Create eg_mb_measurement_measures Table
CREATE TABLE eg_mb_measurement_measures (
  id VARCHAR(128) PRIMARY KEY,
  length NUMERIC,
  breadth NUMERIC,
  height NUMERIC,
  numOfItems NUMERIC,
  currentValue NUMERIC,
  cumulative NUMERIC,
  createdtime BIGINT NOT NULL,
  createdby VARCHAR(128) NOT NULL,
  lastmodifiedtime BIGINT,
  lastmodifiedby VARCHAR(128),
  CONSTRAINT fk_measurement_measures_id
    FOREIGN KEY (id) REFERENCES eg_mb_measurement_details(id)
);

-- Create eg_mb_measurement_documents Table
CREATE TABLE eg_mb_measurement_documents (
  id VARCHAR(128) PRIMARY KEY,
  filestore VARCHAR(64),
  documentType VARCHAR(128),
  documentuuid VARCHAR(128),
  referenceId VARCHAR(128),
  additionaldetails JSONB,
  createdby VARCHAR(64) NOT NULL,
  lastmodifiedby VARCHAR(64),
  createdtime BIGINT NOT NULL,
  lastmodifiedtime BIGINT,
  CONSTRAINT fk_measurement_documents_referenceId
    FOREIGN KEY (referenceId) REFERENCES eg_mb_measurements(id)
);
