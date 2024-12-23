---
description: Steps to configure the project service
---

# Project

## Overview

The project service provides APIs to create, update and manage a generic project. A project can have one or more of the following constructs: staff, tasks, beneficiaries and facilities. Currently, this service is shared across the Health and Works platforms. All Works projects start with a project construct. The Works platform uses only 3 primary APIs: project create, update and search.&#x20;

For a deeper understanding, refer to the following:

[Low-level design](https://works.digit.org/platform/specifications/technical-specifications/low-level-design/services/project)

[Functional specifications](https://works.digit.org/platform/specifications/functional-specifications/projects-service)

## Key Functionalities

* Creating, updating, and searching for a project
* Adding staff, tasks, resources and facilities to a project

**Code**

[Module code](https://github.com/egovernments/health-campaign-services/tree/master/health-services/project)

[Deployment in Works](https://github.com/egovernments/DIGIT-DevOps/tree/digit-works/deploy-as-code/helm/charts/digit-works/backend/project)

## Configuration Details

### MDMS Configuration

#### roles.json

```json
 {
      "code": "PROJECT_CREATOR",
      "name": "PROJECT CREATOR",
      "description": "Project Creator"
    },
    {
      "code": "PROJECT_VIEWER",
      "name": "PROJECT VIEWER",
      "description": "Project Viewer"
    },
```

{% hint style="info" %}
Define (if not present already) and assign the EMPLOYEE\_COMMON role to all project actors.&#x20;
{% endhint %}

#### actions.json

Below are the actions or APIs exposed by the Project service used by the Works platform. Note that the "id" in the attributes needs to be unique and may be different in the implementation environment. It need not be exactly the same as what is shown below.

```json
{
      "id": 51,
      "name": "Create Project",
      "url": "/pms/project/v1/_create",
      "parentModule": "project-management-system",
      "displayName": "Create Project",
      "orderNumber": 0,
      "enabled": false,
      "serviceCode": "project-management-system",
      "code": "null",
      "path": ""
    },
    {
      "id": 52,
      "name": "Search Project",
      "url": "/pms/project/v1/_search",
      "parentModule": "project-management-system",
      "displayName": "Search Project",
      "orderNumber": 0,
      "enabled": false,
      "serviceCode": "project-management-system",
      "code": "null",
      "path": ""
    },
    {
      "id": 53,
      "name": "Update Project",
      "url": "/pms/project/v1/_update",
      "parentModule": "project-management-system",
      "displayName": "Update Project",
      "orderNumber": 0,
      "enabled": false,
      "serviceCode": "project-management-system",
      "code": "null",
      "path": ""
    },
```

### roleactions.json

The following table shows the mapping between the APIs and the roles:

<table><thead><tr><th width="254">Role Code</th><th width="212">Description</th><th>API</th></tr></thead><tbody><tr><td>PROJECT_CREATOR</td><td>Project Creator</td><td>/project/v1/_create</td></tr><tr><td></td><td></td><td>/project/v1/_update</td></tr><tr><td></td><td></td><td>/project/v1/_search</td></tr><tr><td>PROJECT_VIEWER</td><td>Project Viewer</td><td>/project/v1/_search</td></tr><tr><td>EMPLOYEE_COMMON</td><td>Employee Common</td><td>/inbox/v2/_search</td></tr></tbody></table>

The following role-action mappings derived from the above table are configured for the Project service in the roleactions.json in MDMS. A sample is provided below. Make sure the action ID is correct and corresponds to actions.json.&#x20;

```json
{
      "id": 51,
      "name": "Create Project",
      "url": "/project/v1/_create",
      "parentModule": "project-management-system",
      "displayName": "Create Project",
      "orderNumber": 0,
      "enabled": false,
      "serviceCode": "project-management-system",
      "code": "null",
      "path": ""
    },
    {
      "id": 52,
      "name": "Search Project",
      "url": "/project/v1/_search",
      "parentModule": "project-management-system",
      "displayName": "Search Project",
      "orderNumber": 0,
      "enabled": false,
      "serviceCode": "project-management-system",
      "code": "null",
      "path": ""
    },
    {
      "id": 53,
      "name": "Update Project",
      "url": "/project/v1/_update",
      "parentModule": "project-management-system",
      "displayName": "Update Project",
      "orderNumber": 0,
      "enabled": false,
      "serviceCode": "project-management-system",
      "code": "null",
      "path": ""
    },
```

#### IdGen Format

Add Id Format as configured in the ‘IdFormat.json’ file of the ‘common-masters’ module [here](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/IdFormat.json#L25-L28). This format is used to generate the unique ID of the project.&#x20;

```json
{
    "format": "PJ/[fy:yyyy-yy]/[cy:MM]/[SEQ_PROJECT_NUM]",
    "idname": "project.number"
}
```

### Persister Configuration

Add persister file [`project-management-system-persister.yml`](https://github.com/egovernments/works-configs/blob/DEV/egov-persister/project-management-system-persister.yml) as defined [here](https://github.com/egovernments/works-configs/tree/DEV/egov-persister).

### Indexer Configuration

Add indexer file [projectmanagementsystem-indexer.yml](https://github.com/egovernments/works-configs/blob/DEV/egov-indexer/projectmanagementsystem-indexer.yml) as defined [here](https://github.com/egovernments/works-configs/tree/DEV/egov-indexer).

### Other Master Data Configuration

1\. [ProjectType ](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/ProjectType.json)&#x20;

2\. [Department](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/Department.json#L55-L59)

3\. [Boundary Data](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/citya/egov-location/boundary-data.json)

4\. [Nature of Work](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/NatureOfWork.json)

## Deployment Details

The image name of the service is available in the release charts in the DevOps repository. The service can be deployed using Helm commands.&#x20;

Environment variables to be configured in the Helm chart for the service are:

* Add the ‘db-host’,’db-name’,’db-url’,’domain’ and all the digit core platform services configurations (Idgen, workflow, user etc.) in respective environments yaml file.
* Add project-management-system related environment variables values. A sample from a[ ‘dev](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml)’ environment yaml file is provided below:
  1. [https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L80](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L80)
  2. [https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L223-L230](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L223-L230)
  3. [https://github.com/egovernments/DIGIT-DevOps/tree/digit-works/deploy-as-code/helm/charts/digit-works/backend/project-management-system](https://github.com/egovernments/DIGIT-DevOps/tree/digit-works/deploy-as-code/helm/charts/digit-works/backend/project-management-system)&#x20;
* Add the ‘[egov-mdms-service](https://github.com/egovernments/DIGIT-DevOps/blob/5a9eb4c6141e19bd747238889ceed9bc9fffdc6f/deploy-as-code/helm/environments/works-dev.yaml#L190)’ related configuration to the respective environment yaml file. Make sure you change the git-sync branch name to one that is appropriate for the environment.
* Check the project management system persister file is added in the egov-persister.persister-yml-path variable. If not, please add the way it's done [here](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L352).
* Check the project management system indexer file is added in the egov-indexer.egov-indexer-yaml-repo-path variable. If not, please add the way it's done [here](https://github.com/egovernments/DIGIT-DevOps/commit/8fa730b0a2586c553e597bd77a4a4b19caac99af#diff-9e3afadcf906194e9b3fbeede6ea73eaf1c1f8e93f145f9a37a6630121395de0R308).
* Check the project management system persister file is added in the audit-service.persist-yml-path variable. If not, please add the way it's done [here](https://github.com/egovernments/DIGIT-DevOps/commit/044aeda0d05d91fbc1267368d1154f2c7da3878c).
* Make sure to add the DB(Postgres and flyway) username & password in the respective environment secrets yaml file the way it's done[ here](https://github.com/egovernments/DIGIT-DevOps/blob/e742a292f2966bb1affb3b03edd643a777917ba1/deploy-as-code/helm/environments/works-dev-secrets.yaml#L3).
* Make sure to add the DIGIT core service-related secrets that are configured in the respective environment secret file the way it's done[ here](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev-secrets.yaml).

{% hint style="warning" %}
**NOTE:** Restart egov-mdms-service, egov-accesscontrol, egov-persister, audit-service, egov-indexer and zuul after the above changes are performed.
{% endhint %}

## Integration Details

### API Spec

**Base path**: /project/

Refer to the [API spec](https://works.digit.org/platform/specifications/technical-specifications/low-level-design/services/project) for a description of the APIs.&#x20;

Documentation for this service is available [here](https://health.digit.org/platform/low-level-design/services/project).&#x20;

### Postman Collection

Click [here](https://raw.githubusercontent.com/egovernments/DIGIT-Works/develop/backend/project-management-system/src/main/resources/Project%20Management%20System.postman_collection.json) to access the Postman collection used to test APIs. Import the link into Postman and follow the instructions to run the collection. Use these to understand the request payloads.&#x20;

