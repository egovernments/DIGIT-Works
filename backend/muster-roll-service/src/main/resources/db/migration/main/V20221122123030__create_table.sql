CREATE TABLE eg_wms_muster_roll(

id                           character varying(256),
tenantId                     character varying(64) NOT NULL,
musterRollNumber             character varying(128) NOT NULL,
attendanceRegisterId         character varying(128),
status                       character varying(64) NOT NULL,
musterRollStatus             character varying(64) NOT NULL,
additionaldetails            JSONB,
startDate                    bigint,
endDate                      bigint,
createdby                    character varying(256)  NOT NULL,
lastmodifiedby               character varying(256),
createdtime                  bigint,
lastmodifiedtime             bigint,

CONSTRAINT uk_eg_wms_muster_roll UNIQUE (musterRollNumber),
CONSTRAINT pk_eg_wms_muster_roll PRIMARY KEY (id)
);

CREATE TABLE eg_wms_attendance_summary(
id                           character varying(256),
musterRollNumber             character varying(128) NOT NULL,
individual_id                character varying(256) NOT NULL,
total_attendance             integer,
additionaldetails            JSONB,
createdby                    character varying(256)  NOT NULL,
lastmodifiedby               character varying(256),
createdtime                  bigint,
lastmodifiedtime             bigint,
CONSTRAINT pk_eg_wms_attendance_summary PRIMARY KEY (id)
);

CREATE TABLE eg_wms_attendance_entries(
id                           character varying(256),
musterRollNumber             character varying(128) NOT NULL,
individual_id                character varying(256) NOT NULL,
attendance_summary_id        character varying(256) NOT NULL,
attendance                   integer,
date_of_attendance           bigint,
additionaldetails            JSONB,
createdby                    character varying(256)  NOT NULL,
lastmodifiedby               character varying(256),
createdtime                  bigint,
lastmodifiedtime             bigint,
CONSTRAINT pk_eg_wms_attendance_entries PRIMARY KEY (id),
CONSTRAINT fk_eg_wms_attendance_entries FOREIGN KEY (attendance_summary_id) REFERENCES eg_wms_attendance_summary (id)
);