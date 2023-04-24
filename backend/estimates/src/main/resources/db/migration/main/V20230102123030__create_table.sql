DROP TABLE IF EXISTS eg_wms_estimate_amount_detail;
DROP TABLE IF EXISTS eg_wms_estimate_detail;
DROP TABLE IF EXISTS eg_wms_estimate_address;
DROP TABLE IF EXISTS eg_wms_estimate;


CREATE TABLE eg_wms_estimate(

id                           character varying(256),
tenant_id                    character varying(64) NOT NULL,
estimate_number              character varying(128) NOT NULL,
project_id                   character varying(256) NOT NULL,
proposal_date                bigint NOT NULL,
status                       character varying(64) NOT NULL,
wf_status                    character varying(64) NOT NULL,
name                         character varying(140) NOT NULL,
reference_number             character varying(140) NOT NULL,
description                  character varying(240) NOT NULL,
executing_department         character varying(64) NOT NULL,
additional_details           JSONB,
created_by                   character varying(256)  NOT NULL,
last_modified_by             character varying(256),
created_time                 bigint,
last_modified_time           bigint,

CONSTRAINT uk_eg_wms_estimate UNIQUE (estimate_number),
CONSTRAINT pk_eg_wms_estimate PRIMARY KEY (id)
);

CREATE TABLE eg_wms_estimate_detail(
id                           character varying(256),
tenant_id                    character varying(64) NOT NULL,
estimate_id                  character varying(256) NOT NULL,
sor_id                       character varying(256),
category                     character varying(256) NOT NULL,
name                         character varying(140) NOT NULL,
description                  character varying(256) NOT NULL,
unit_rate                    NUMERIC,
no_of_unit                   NUMERIC,
uom                          character varying(256),
uom_value                    NUMERIC,
additional_details           JSONB,
CONSTRAINT pk_eg_wms_estimate_detail PRIMARY KEY (id),
CONSTRAINT fk_eg_wms_estimate_detail FOREIGN KEY (estimate_id) REFERENCES eg_wms_estimate (id)
);

CREATE TABLE eg_wms_estimate_address(
id                           character varying(256),
tenant_id                    character varying(64) NOT NULL,
estimate_id                  character varying(256) NOT NULL,
latitude                     NUMERIC NOT NULL,
longitude                    NUMERIC NOT NULL,
address_number               character varying NOT NULL,
address_line_1               character varying NOT NULL,
address_line_2               character varying,
landmark                     character varying,
city                         character varying(256) NOT NULL,
pin_code                     character varying(30) NOT NULL,
detail                       character varying(256),
CONSTRAINT pk_eg_wms_estimate_address PRIMARY KEY (id),
CONSTRAINT fk_eg_wms_estimate_address FOREIGN KEY (estimate_id) REFERENCES eg_wms_estimate (id)
);

CREATE TABLE eg_wms_estimate_amount_detail(
id                           character varying(256),
tenant_id                    character varying(64) NOT NULL,
estimate_detail_id           character varying(256) NOT NULL,
type                         character varying(140) NOT NULL,
amount                       NUMERIC NOT NULL,
additional_details           JSONB,
CONSTRAINT pk_eg_wms_estimate_amount_detail PRIMARY KEY (id),
CONSTRAINT fk_eg_wms_estimate_amount_detail FOREIGN KEY (estimate_detail_id) REFERENCES eg_wms_estimate_detail (id)
);