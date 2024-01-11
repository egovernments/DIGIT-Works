-- Table eg_mukta_ifms_exchange_message
CREATE TABLE eg_mukta_ifms_exchange_message (
    id VARCHAR(64) PRIMARY KEY,
    location_code VARCHAR(64) NOT NULL,
    parent_id VARCHAR(64),
    schema_version VARCHAR(50),
    action VARCHAR(50) NOT NULL,
    function_code VARCHAR(128),
    administration_code VARCHAR(128),
    program_code VARCHAR(64),
    status VARCHAR(64),
    recipient_segment_code VARCHAR(128),
    economic_segment_code VARCHAR(128),
    source_of_found_code VARCHAR(128),
    target_segment_code VARCHAR(128),
    createdtime BIGINT,
    createdby VARCHAR(256),
    lastmodifiedtime BIGINT,
    lastmodifiedby VARCHAR(256)
);

-- Table eg_mukta_ifms_request_header
CREATE TABLE eg_mukta_ifms_request_header (
    id VARCHAR(64) PRIMARY KEY,
    exchange_id VARCHAR(64),
    message_id VARCHAR(64),
    message_ts VARCHAR(64),
    action VARCHAR(20),
    sender_id VARCHAR(128),
    sender_uri VARCHAR(256),
    receiver_id VARCHAR(128),
    is_msg_encrypted BOOLEAN,
    createdtime BIGINT,
    createdby VARCHAR(256),
    lastmodifiedtime BIGINT,
    lastmodifiedby VARCHAR(256),
    FOREIGN KEY (exchange_id) REFERENCES eg_mukta_ifms_exchange_message(id)
);

-- Table eg_program_disbuse
CREATE TABLE eg_mukta_ifms_disburse (
    id VARCHAR(64) PRIMARY KEY,
    exchange_id VARCHAR(64),
    target_id VARCHAR(64),
    allocation_ids VARCHAR(64) ARRAY,
    account_code VARCHAR(50),
    net_amount DOUBLE PRECISION,
    gross_amount DOUBLE PRECISION,
    createdtime BIGINT,
    createdby VARCHAR(256),
    lastmodifiedtime BIGINT,
    lastmodifiedby VARCHAR(256),
    FOREIGN KEY (exchange_id) REFERENCES eg_mukta_ifms_exchange_message(id)
);
