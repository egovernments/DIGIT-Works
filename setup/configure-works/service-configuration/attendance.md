# Attendance

## Overview

The attendance service provides generic attendance logging functionality for recording "in" and "out" timestamps for individuals. These timestamps are logged on a per-individual basis. The muster roll service takes on the responsibility of aggregating and calculating attendance based on these recorded timestamps. It processes and computes attendance data using these individual "in" and "out" timestamps to provide a comprehensive view of attendance records.

## Pre-requisites

A running DIGIT platform is needed to deploy the attendance service. Specifically, the following dependencies are needed:

* Individual
* MDMS&#x20;
* Idgen  &#x20;
* Persister
* Indexer

## Functionality

Provides APIs to:

* Create an attendance register
* Map staff to the register
* Map attendees to the register
* Log attendance
* Edit attendance registers, staff, attendees and attendance.

**Code**

[Module code](https://github.com/egovernments/DIGIT-Works/tree/master/backend/attendance)

Base URL: `/attendance/v1/`

## Deployment Details

Below are the variables that should be configured for the contract service in the Helm environment file before deployment. The below path is used to locate the Helm environment file.

`https://github.com/`<mark style="color:red;">`{{ORG}}`</mark>`/DIGIT-DevOps/deploy-as-code/helm/environments/`<mark style="color:red;">`{{EnvironmentFile}}`</mark>`.yaml`

Refer to the [sample here](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml).

* Add these ‘db-host’,’db-name’,’db-url’, ’domain’ and all the digit core platform services configurations (Idgen, workflow, user etc.) in respective environments YAML file.
* Add attendance-service related environment variables’ value like the way it's done in the[ ‘dev](https://github.com/egovernments/DIGIT-DevOps/blob/5a9eb4c6141e19bd747238889ceed9bc9fffdc6f/deploy-as-code/helm/environments/works-dev.yaml#L175)’ environment YAML file.
  * [https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L77](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L77)
  * [https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L189](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L189)
  * [https://github.com/egovernments/DIGIT-DevOps/tree/digit-works/deploy-as-code/helm/charts/digit-works/backend/attendance-service](https://github.com/egovernments/DIGIT-DevOps/tree/digit-works/deploy-as-code/helm/charts/digit-works/backend/attendance-service)&#x20;
* Add the ‘[egov-mdms-service](https://github.com/egovernments/DIGIT-DevOps/blob/5a9eb4c6141e19bd747238889ceed9bc9fffdc6f/deploy-as-code/helm/environments/works-dev.yaml#L190)’ related configuration to the respective environment YAML file. Make sure you change the gitsync.branch name.
* Check the attendance-service persister file is added in the egov-persister.perister-yml-path variable. If not, follow the steps outlined[ here](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L267).
* Make sure to add the DB(Postgres and flyway) username & password in the respective environment secret YAML file as per the steps given[ here](https://github.com/egovernments/DIGIT-DevOps/blob/e742a292f2966bb1affb3b03edd643a777917ba1/deploy-as-code/helm/environments/works-dev-secrets.yaml#L3).
* Make sure to add the DIGIT core services-related secrets that are configured in the respective environment secret file the way it's done[ here](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev-secrets.yaml).

{% hint style="info" %}
Restart egov-mdms-service, egov-persister, egov-indexer, inbox, egov-workflow-v2, egov-accesscontrol and ZUUL services after the above changes are performed.
{% endhint %}

## Configuration Details

### Configure Actions

Add all the APIs exposed by the attendance service (refer to the table below for actual APIs) to the actions.json file in MDMS

**Module name:** ACCESSCONTROL-ACTIONS-TEST

**Master name:** actions-test

### Configure Roles

Configure roles based on the roles column below in roles.json file.&#x20;

**Module name:** ACCESSCONTROL-ROLES

**Master name:** roles

### Configure Role-Action

Role-action mapping is configured in MDMS per the table below .&#x20;

**Module name:** ACCESSCONTROL-ROLEACTIONS

**Master name:** roleactions.json

<table><thead><tr><th width="357">Roles</th><th>APIs /Actions</th></tr></thead><tbody><tr><td><ul><li>ORG_ADMIN</li><li>JUNIOR_ENGINEER</li><li>MUNICIPAL_ENGINEER</li></ul></td><td>/attendance/v1/_create</td></tr><tr><td><ul><li>ORG_ADMIN</li><li>JUNIOR_ENGINEER</li><li>MUNICIPAL_ENGINEER</li></ul></td><td>/attendance/v1/_update</td></tr><tr><td><ul><li>ORG_ADMIN</li><li>JUNIOR_ENGINEER</li><li>MUNICIPAL_ENGINEER</li></ul></td><td>/attendance/v1/_search</td></tr><tr><td><ul><li>ORG_ADMIN</li><li>ORG_STAFF</li></ul></td><td>/attendance/staff/v1/_create</td></tr><tr><td><ul><li>ORG_ADMIN</li><li>ORG_STAFF</li></ul></td><td>/attendance/staff/v1/_delete</td></tr><tr><td><ul><li>ORG_ADMIN</li><li>ORG_STAFF</li></ul></td><td>/attendance/attendee/v1/_create</td></tr><tr><td><ul><li>ORG_ADMIN</li><li>ORG_STAFF</li></ul></td><td>/attendance/attendee/v1/_delete</td></tr><tr><td><ul><li>ORG_ADMIN</li><li>ORG_STAFF</li></ul></td><td>/attendance/log/v1/_create</td></tr><tr><td><ul><li>ORG_ADMIN</li><li>ORG_STAFF</li></ul></td><td>/attendance/log/v1/_search</td></tr><tr><td><ul><li>ORG_ADMIN</li><li>ORG_STAFF</li></ul></td><td>/attendance/log/v1/_update</td></tr></tbody></table>

### Idgen Configuration

Make sure the id format is configured in the [IdFormat.json](https://github.com/egovernments/works-mdms-data/commit/d2fb6946b2c1fd3fa4ee2742773794779de4a69a) file of the `common-masters` module in MDMS.

| IDGen format for attendance register number                                                                                                                  |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| <p>{</p><p>      "format": "WR/[fy:yyyy-yy]/[cy:MM]/[cy:dd]/[SEQ_ATTENDANCE_REGISTER_NUM]",</p><p>      "idname": "attendance.register.number"</p><p>  }</p> |

### Persister

Make sure that the file [attendance-service-persister.yml](https://github.com/egovernments/works-configs/tree/DEV/egov-persister) is present in the MDMS repository of the organisation: https://github.com/\{{ORG\}}/works-configs/tree/\<BRANCH>/egov-persister

## Integration Details

#### Base Path:&#x20;

/attendance/

[API spec](../../../platform/architecture/low-level-design/services/attendance.md#api-specifications)

Sample [postman collections ](https://raw.githubusercontent.com/egovernments/DIGIT-Works/master/backend/attendance/Attendace%20Service%20Postman%20Scripts.postman_collection.json)are here to demonstrate integration with the attendance service.
