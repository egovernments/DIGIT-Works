---
description: Common steps to configure Works services
---

# Configure Works

## Overview

On this page, you will find a set of standard configuration steps that should be applied consistently across all services. Make sure you adhere to the below steps within the context of each service, making necessary replacements only as instructed by the respective service's guidelines.

**Steps:**

<img src="../../.gitbook/assets/file.excalidraw (2).svg" alt="" class="gitbook-drawing">

## Deploy A Service

Deploying a service encompasses three key aspects:

1. **Service Image Deployment**: This entails deploying a published Docker image of the service within the DIGIT environment.
2. **Helm Charts Requirement**: Helm charts play a crucial role in service deployment as they configure environment variables tailored to the specific Kubernetes cluster. You can deploy a service either through CI/CD pipelines or directly by utilizing Helm commands from your system. All helm charts for MUKTA[^1] services are [available in this repository](https://github.com/egovernments/DIGIT-DevOps/tree/digit-works/deploy-as-code/helm/charts/digit-works/backend).&#x20;
3. **Service Configuration**: To ensure the service functions seamlessly, it is essential to configure it correctly. This includes setting up MDMS, IDGen, Workflow, and other masters as necessary, all of which can be done on GitHub.

In summary, deploying a service involves these three fundamental steps, each contributing to the successful deployment and operation of the service within the DIGIT environment.

## MDMS Role Action Configuration

[Click here](https://core.digit.org/platform/core-services/mdms-master-data-management-service) to find detailed information on MDMS configuration.

Each module offers specific actions (APIs), roles (actors), and role-action mappings (defining access permissions). Role-action mappings control the access and permissions for each role. Each service documentation contains the role-action table that specifies the actors authorized to access particular resources. Adhere to the structure below, substituting specific actions and roles as required for each module.

Actions, roles, and role-action mappings are organised within the master tenant and corresponding folders. These folders are conveniently named after the module, making it easy for users to identify.

Example:

![](<../../.gitbook/assets/Screenshot 2023-05-01 at 5.13.19 PM.png>)

In the image above, "pg" represents the state-level tenant. The highlighted orange folders contain the masters for actions, role actions, and roles.

{% hint style="info" %}
Folder structures are only for categorisation and easy navigation of master files. The MDMS service retrieves data only through module and master names. Make sure that these are correct.
{% endhint %}

## Configure Actions

* Add all the APIs exposed by the service (refer to service for actual APIs) to the <mark style="background-color:orange;">`actions.json`</mark> file in MDMS.&#x20;
* Keep appending new entries to the bottom of the file.&#x20;
* Make sure the `id` field is unique. The best practice is to increment the ID by one when adding a new entry. This id field will be used in the role-action mapping.

**Module name:** ACCESSCONTROL-ACTIONS-TEST

**Master name:** actions-test

{% hint style="info" %}
In case 403s are encountered despite configuration, double-check the actions.json file to make sure the API in question has a unique ID. In case of duplicate IDs, a 403 will be thrown by Zuul.
{% endhint %}

#### Example:

A sample entry is given below:

```json
{
      "id": {unique ID},
      "name": "Create Estimate",
      "url": "/estimate-service/estimate/v1/_create",
      "parentModule": "estimate-service",
      "displayName": "Create Estimate",
      "orderNumber": 0,
      "enabled": false,
      "serviceCode": "estimate-service",
      "code": "null",
      "path": ""
    },
    {
      "id": {unique ID},
      "name": "Search Estimate",
      "url": "/estimate-service/estimate/v1/_search",
      "parentModule": "estimate-service",
      "displayName": "Search Estimate",
      "orderNumber": 0,
      "enabled": false,
      "serviceCode": "estimate-service",
      "code": "null",
      "path": ""
    },
    {
      "id": {unique ID},
      "name": "Update Estimate",
      "url": "/estimate-service/estimate/v1/_update",
      "parentModule": "estimate-service",
      "displayName": "Update Estimate",
      "orderNumber": 0,
      "enabled": false,
      "serviceCode": "estimate-service",
      "code": "null",
      "path": ""
    },
```

## Configure Roles

Configure roles based on the details given in the roles column (refer to service documentation) in the roles.json file. Make sure the role does not exist already. Append new roles to the bottom of the file.&#x20;

**Module name:** ACCESSCONTROL-ROLES

**Master name:** roles

{% hint style="warning" %}
#### Assign EMPLOYEE\_COMMON to all actors in the Works platform.
{% endhint %}

#### Example:

A sample entry is given below:

```json
{
      "code": "ESTIMATE_CREATOR",
      "name": "ESTIMATE CREATOR",
      "description": "Estimate Creator"
    },
    {
      "code": "ESTIMATE_VIEWER",
      "name": "ESTIMATE VIEWER",
      "description": "Estimate VIEWER having search api access"
    },
```

## Configure Role Action

Role-action mapping should be configured as per the role-action table defined. Add new entries to the bottom of the roleactions.json file.&#x20;

Identify the action ID (from the actions.json file) and map roles to that ID. If multiple roles are mapped to an API, then each of them becomes a unique entry in the roleactions.json file.

**Module name:** ACCESSCONTROL-ROLEACTIONS

**Master name:** roleactions.json

**Example:** A sample set of role-action entries is shown in the code block below. Each of the `actionid` fields should match a corresponding API in the actions.json file.&#x20;

In the example below, the `ESTIMATE_CREATOR` is given access to API actionid 9. This maps to the estimate created API in our repository.&#x20;

{% hint style="warning" %}
**Note** that the `actionid` and `tenantId` might differ from implementation to implementation.&#x20;
{% endhint %}

```json
 {
      "rolecode": "ESTIMATE_CREATOR",
      "actionid": 9,
      "actioncode": "",
      "tenantId": "pg"
    },
    {
      "rolecode": "ESTIMATE_VERIFIER",
      "actionid": 11,
      "actioncode": "",
      "tenantId": "pg"
    },
    {
      "rolecode": "TECHNICAL_SANCTIONER",
      "actionid": 11,
      "actioncode": "",
      "tenantId": "pg"
    }
```

## Persister Configuration

Each service has a persister.yaml file which needs to be stored in the configs repository. The actual file will be mentioned in the service documentation.&#x20;

Add this YAML file to the configs repository if not present already.

{% hint style="warning" %}
Make sure to restart the persister service after adding the file in the above location.
{% endhint %}

## Indexer Configuration

Each service has a indexer.yaml file which needs to be stored in the configs repository. The actual file will be mentioned in the service documentation.&#x20;

Add this YAML file to the configs repository if not present already.

{% hint style="warning" %}
Make sure to restart the indexer service after adding the file in the above location.
{% endhint %}



[^1]: Mukhyamantri Karma Tatpara Abhiyan Yojana ( MUKTA Yojana) - a government  scheme to provide employment
