CREATE TABLE eg_wms_estimate(

id                           character varying(256),
tenantId                     character varying(64) NOT NULL,
estimate_number              character varying(128) NOT NULL,
admin_sanction_number        character varying(128),
proposal_date                bigint NOT NULL,
status                       character varying(64) NOT NULL,
estimate_status              character varying(64) NOT NULL,
subject                      character varying(140) NOT NULL,
requirement_number           character varying(140) NOT NULL,
description                  character varying(240) NOT NULL,
department                   character varying(64) NOT NULL,
location                     character varying(64),
work_category                character varying(64) NOT NULL,
beneficiary_type             character varying(64) NOT NULL,
nature_of_work               character varying(64) NOT NULL,
type_of_work                 character varying(64) NOT NULL,
subtype_of_work              character varying(64),
entrustment_mode             character varying(64) NOT NULL,
fund                         character varying(64) NOT NULL,
function                     character varying(64) NOT NULL,
budget_head                  character varying(64) NOT NULL,
scheme                       character varying(64),
sub_scheme                   character varying(64),
total_amount                 NUMERIC,
additionaldetails            JSONB,
createdby                    character varying(256)  NOT NULL,
lastmodifiedby               character varying(256),
createdtime                  bigint,
lastmodifiedtime             bigint,

CONSTRAINT uk_eg_wms_estimate UNIQUE (admin_sanction_number),
CONSTRAINT pk_eg_wms_estimate PRIMARY KEY (id)
);

CREATE TABLE eg_wms_estimate_detail(
id                           character varying(256),
tenantId                     character varying(64) NOT NULL,
estimate_id                  character varying(256) NOT NULL,
estimate_detail_number       character varying(128) NOT NULL,
name                         character varying(140) NOT NULL,
amount                       NUMERIC NOT NULL,
additionaldetails            JSONB,
CONSTRAINT pk_eg_wms_estimate_detail PRIMARY KEY (id),
CONSTRAINT fk_eg_wms_estimate_detail FOREIGN KEY (estimate_id) REFERENCES eg_wms_estimate (id)
);
