-- Face Auth Event table for storing face verification events synced from the mobile app
CREATE TABLE IF NOT EXISTS eg_wms_face_auth_event (
    id                    VARCHAR(256) NOT NULL,
    clientreferenceid     VARCHAR(256),
    tenantid              VARCHAR(256) NOT NULL,
    individual_id         VARCHAR(256) NOT NULL,
    device_id             VARCHAR(256),
    event_type            VARCHAR(64) NOT NULL,
    outcome               VARCHAR(64) NOT NULL,
    confidence            NUMERIC(5,4),
    latitude              NUMERIC(10,7),
    longitude             NUMERIC(10,7),
    location_accuracy     NUMERIC(10,3),
    event_timestamp       NUMERIC(20,0),
    failed_attempt_count  INTEGER DEFAULT 0,
    popup_time            NUMERIC(20,0),
    response_time         NUMERIC(20,0),
    response_type         VARCHAR(64),
    face_image            TEXT,
    anomaly_flags         VARCHAR(512),
    project_id            VARCHAR(256),
    boundary_code         VARCHAR(256),
    additionaldetails     JSONB,
    createdby             VARCHAR(256) NOT NULL,
    lastmodifiedby        VARCHAR(256),
    createdtime           BIGINT NOT NULL,
    lastmodifiedtime      BIGINT,
    clientcreatedby       VARCHAR(256),
    clientlastmodifiedby  VARCHAR(256),
    clientcreatedtime     BIGINT,
    clientlastmodifiedtime BIGINT,
    CONSTRAINT pk_eg_wms_face_auth_event PRIMARY KEY (id)
);

-- Indexes for common query patterns
CREATE INDEX IF NOT EXISTS idx_face_auth_event_individual_id ON eg_wms_face_auth_event (individual_id);
CREATE INDEX IF NOT EXISTS idx_face_auth_event_tenantid ON eg_wms_face_auth_event (tenantid);
CREATE INDEX IF NOT EXISTS idx_face_auth_event_event_type ON eg_wms_face_auth_event (event_type);
CREATE INDEX IF NOT EXISTS idx_face_auth_event_outcome ON eg_wms_face_auth_event (outcome);
CREATE INDEX IF NOT EXISTS idx_face_auth_event_timestamp ON eg_wms_face_auth_event (event_timestamp);
CREATE INDEX IF NOT EXISTS idx_face_auth_event_clientrefid ON eg_wms_face_auth_event (clientreferenceid);
CREATE INDEX IF NOT EXISTS idx_face_auth_event_project_id ON eg_wms_face_auth_event (project_id);

COMMENT ON TABLE eg_wms_face_auth_event IS 'Stores face verification events synced from the mobile app including liveness checks, face matches, and PIN fallbacks';
COMMENT ON COLUMN eg_wms_face_auth_event.event_type IS 'Type of face auth event: LOGIN, CHECK_IN, RE_VERIFY, ENROLLMENT';
COMMENT ON COLUMN eg_wms_face_auth_event.outcome IS 'Verification outcome: FACE_SUCCESS, FACE_REJECTED, PIN_FALLBACK, HCM_FALLBACK, MISSED';
COMMENT ON COLUMN eg_wms_face_auth_event.confidence IS 'Cosine similarity score from face matching (0.0-1.0)';
COMMENT ON COLUMN eg_wms_face_auth_event.face_image IS 'Base64-encoded JPEG of the cropped face used for verification';
COMMENT ON COLUMN eg_wms_face_auth_event.response_type IS 'How the user responded: FACE, PIN, DISMISS, TIMEOUT';
