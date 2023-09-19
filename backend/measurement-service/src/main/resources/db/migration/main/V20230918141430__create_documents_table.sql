CREATE TABLE eg_mb_measurement_documents
(
    id VARCHAR(64) PRIMARY KEY,
    filestore VARCHAR(64),
    documenttype VARCHAR(128),
    documentuuid VARCHAR(128),
    measuresid VARCHAR(128),
    additionaldetails jsonb,
    createdby VARCHAR(64),
    lastmodifiedby VARCHAR(64),
    createdtime bigint,
    lastmodifiedtime bigint,
    FOREIGN KEY (measuresid) REFERENCES eg_mb_measurement_details (id)
);


ALTER TABLE eg_mb_measurement_measures
ADD COLUMN currentvalue NUMERIC;