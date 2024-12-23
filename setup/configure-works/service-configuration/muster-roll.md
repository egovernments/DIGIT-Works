---
description: >-
  Muster roll is a record of attendance and quantity of work done by wage
  seekers.
---

# Muster Roll

## Overview

A muster roll serves as a record of attendance, indicating the hours worked, wages owed, and the amount of work completed by labourers during a specified time frame.

The attendance service supplies raw attendance logs, while the muster roll service collects these logs and calculates attendance according to specific business rules. For instance, determining whether attendance is measured in hours or days and defining what constitutes a half-day or a full day of attendance are decisions that depend on the specific implementation. These configurations can be adjusted within the service, and attendance calculations will be carried out based on these settings.

## Pre-requisites

* Attendance
* Individual
* Persister
* MDMS
* Workflow
* Idgen
* Notification

## Functionality

The functionality includes the following APIs:

1. **Estimate Wages**: This API calculates the wages for a wage seeker based on their attendance logs.
2. **Create Muster Roll**: It allows you to create a muster roll, which is essentially a list of wage seekers.
3. **Update Muster Roll**: You can use this API to update a muster roll with modified aggregate attendance data.
4. **Search for Muster Roll**: This API enables you to search for a muster roll using specific parameters. For more details, you can refer to the Swagger contract.

**Code**

[Module code](https://github.com/egovernments/DIGIT-Works/tree/master/backend/muster-roll)

## Deployment Details

Here is a list of variables that needs to be configured in the Helm environment file before deploying the muster roll service. This file can typically be found under a specific directory or location as given below:

`https://github.com/`<mark style="color:red;">`{{ORG}}`</mark>`/DIGIT-DevOps/deploy-as-code/helm/environments/`<mark style="color:red;">`{{EnvironmentFile}}`</mark>`.yaml`

Refer to the [sample here](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml).

* Add these ‘db-host’,’db-name’,’db-url’, ’domain’ and all the digit core platform services configurations (Idgen, workflow, user etc.) in respective environments yaml file.
* Add muster-roll-service related environment variables’ value like the way it's done in the[ ‘dev](https://github.com/egovernments/DIGIT-DevOps/blob/5a9eb4c6141e19bd747238889ceed9bc9fffdc6f/deploy-as-code/helm/environments/works-dev.yaml#L175)’ environment yaml file.
  * [https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L79](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L79)&#x20;
  * [https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L203](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L203)&#x20;
  * [https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L264](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L264)&#x20;
  * [https://github.com/egovernments/DIGIT-DevOps/commit/684a75232e422357245eab5fefacde28f64ffc0e](https://github.com/egovernments/DIGIT-DevOps/commit/684a75232e422357245eab5fefacde28f64ffc0e)&#x20;
  * [https://github.com/egovernments/DIGIT-DevOps/commit/478e493bc245e6e3cdea9d4e9599e2b51b880bb0](https://github.com/egovernments/DIGIT-DevOps/commit/478e493bc245e6e3cdea9d4e9599e2b51b880bb0)&#x20;
  * [https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/charts/digit-works/backend/muster-roll-service/values.yaml](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/charts/digit-works/backend/muster-roll-service/values.yaml)&#x20;
* Add the [egov-mdms-service](https://github.com/egovernments/DIGIT-DevOps/blob/5a9eb4c6141e19bd747238889ceed9bc9fffdc6f/deploy-as-code/helm/environments/works-dev.yaml#L190) related configuration to the respective environment YAML file. Make sure you change the **`gitsync.branch`** name.
* Check the muster-roll-service persister file is added to the **`egov-persister.perister-yml-path`** variable. If not, follow the steps outlined[ here](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L263).
* Make sure to add the DB(Postgres and flyway) username & password in the respective environment secret YAML file as per the steps outlined[ here](https://github.com/egovernments/DIGIT-DevOps/blob/e742a292f2966bb1affb3b03edd643a777917ba1/deploy-as-code/helm/environments/works-dev-secrets.yaml#L3).
* Make sure to add the digit core services-related secrets that are configured in the respective environment secret file as per the steps outlined[ here](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev-secrets.yaml).

## Configuration Details

### Configure Actions

Add all the APIs exposed (refer to the table below for actual APIs) to the actions.json file in MDMS

**Module name:** ACCESSCONTROL-ACTIONS-TEST

**Master name:** actions-test

### Configure Roles

Configure roles based on the roles column below in roles.json file.&#x20;

**Module name:** ACCESSCONTROL-ROLES

**Master name:** roles

### Configure Role-Action

Role-action mapping is configured in MDMS per the table below.&#x20;

**Module name:** ACCESSCONTROL-ROLEACTIONS

**Master name:** roleactions.json

<table><thead><tr><th width="302">Roles</th><th>API Endpoints</th></tr></thead><tbody><tr><td><p>ORG_ADMIN</p><p>ORG_STAFF</p></td><td>/muster-roll/v1/_estimate</td></tr><tr><td><p>ORG_ADMIN</p><p>ORG_STAFF</p></td><td>/muster-roll/v1/_create</td></tr><tr><td><p></p><p>ORG_ADMIN</p><p>ORG_STAFF</p><p>MUSTER_ROLL_VERIFIER</p><p>MUSTER_ROLL_APPROVER</p><p><br></p></td><td>/muster-roll/v1/_update</td></tr><tr><td><p>ORG_ADMIN</p><p>ORG_STAFF</p><p>MUSTER_ROLL_VERIFIER</p><p>MUSTER_ROLL_APPROVER</p><p>BILL_CREATOR</p><p>BILL_VIEWER</p></td><td>/muster-roll/v1/_search</td></tr></tbody></table>

#### Other masters to be added

Other muster roll masters are configured in the **`common-masters`** folder:

[MusterRoll.json](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pb/common-masters/MusterRoll.json)

[WageSeekers.json](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pb/common-masters/WageSeekerSkills.json)

## Integration Details

[Environment file](https://github.com/egovernments/DIGIT-Works/blob/develop/backend/muster-roll-service/src/main/resources/Muster%20Environment.postman_environment.json)

[Postman collection](https://github.com/egovernments/DIGIT-Works/blob/develop/backend/muster-roll-service/src/main/resources/Muster%20Roll%20Service.postman_collection.json)

#### Steps to run the postman collection:

1\. Import the postman collection for muster roll service into the Postman application.

2\. Import the environment variables required for running the Postman collection. This will create an environment by the name of Muster Environment in Postman.

3\. MusterRoll requires the below services from the Attendance Service API to be run. So run the below services before running the muster roll postman collection.

&#x20;   a) Create Attendance register - `https://{hostname}/attendance/v1/_create`

&#x20;   b) Enroll attendees to the register - `https://{{hostname}}/attendance/attendee/v1/_create`

&#x20;   c) Attendance log create - `https://{{hostname}}/attendance/log/v1/_create`

4\. Update the current value of the variable ‘registerId’ in ‘Muster Environment’ with the id returned by the response in the create attendance register (in step 3 a).

5\. Run the ‘Muster Roll Service’ postman collection as ‘Run Collection’. It will run the /\_estimate, /\_create, /\_update and /\_search API’s success and validation error scenarios.

6\. Muster will be created for the attendees enrolled in the attendance register (in step 3 b) using the attendance logs created (in step 3 c).

&#x20;The current value of environment variables ‘musterRollId’ and ‘musterRollNumber’ will be set from the response of the /\_create muster roll which will be used by /\_update and /\_search APIs.

