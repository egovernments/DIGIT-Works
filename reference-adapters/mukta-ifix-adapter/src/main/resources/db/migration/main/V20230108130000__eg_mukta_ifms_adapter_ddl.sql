-- Table eg_mukta_ifms_disburse
CREATE TABLE eg_mukta_ifms_disburse (
    id VARCHAR(64) PRIMARY KEY,
    program_code VARCHAR(64),
    target_id VARCHAR(64),
    parent_id varchar(64),
    transaction_id VARCHAR(255),
    account_code VARCHAR(256),
    status VARCHAR(64),
    status_message VARCHAR,
    individual JSONB,
    net_amount DOUBLE PRECISION,
    gross_amount DOUBLE PRECISION,
    created_time BIGINT,
    created_by VARCHAR(256),
    last_modified_time BIGINT,
    last_modified_by VARCHAR(256)
);

-- Table eg_mukta_ifms_message_codes
CREATE TABLE eg_mukta_ifms_message_codes (
     id VARCHAR(64) PRIMARY KEY,
     location_code VARCHAR(64) NOT NULL,
     type VARCHAR(64),
     parent_id VARCHAR(64) REFERENCES eg_mukta_ifms_disburse(id),
     function_code VARCHAR(128),
     administration_code VARCHAR(128),
     program_code VARCHAR(64),
     recipient_segment_code VARCHAR(128),
     economic_segment_code VARCHAR(128),
     source_of_fund_code VARCHAR(128),
     target_segment_code VARCHAR(128),
     additional_details JSONB,
     created_time BIGINT,
     created_by VARCHAR(256),
     last_modified_time BIGINT,
     last_modified_by VARCHAR(256),
     FOREIGN KEY (parent_id) REFERENCES eg_mukta_ifms_disburse(id)
);
