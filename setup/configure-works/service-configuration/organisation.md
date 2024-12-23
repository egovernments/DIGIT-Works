---
description: Organisation registry to store vendors, contractors, CBOs and other org types.
---

# Organisation

## Overview

The organisation service is a generic registry to store all types of organisations. This includes vendors, contractors, and community-based organisations in the Works domain. This registry stores information about an organisation including their contact details, tax identifiers, work areas and other relevant information.

## Pre-requisites

The below services need to be running in the environment for the organisation registry to function as expected:

* Persister
* Indexer
* User
* Individual
* MDMS
* Access Control
* Notification
* IDGen
* Filestore

## Key Functionalities

Provides APIs to create, update and search an organisation's details. As part of organisation creation, it also creates a system user who can log into the DIGIT system and perform actions on behalf of the organisation. The contact person details are used to create the user with a CITIZEN role and an OTP-based login.

**Code**

[Organisation](https://github.com/egovernments/DIGIT-Works/tree/master/backend/organisation)

## Deployment Details

The Helm chart for this service is [configured here](https://github.com/egovernments/DIGIT-DevOps/tree/digit-works/deploy-as-code/helm/charts/digit-works/backend/organisation).&#x20;

### MDMS Configuration

Configure roles, actions and role-actions per the table below by following the steps [below](./#mdms-role-action-configuration).&#x20;

<table><thead><tr><th width="317">Roles</th><th>API Endpoints</th></tr></thead><tbody><tr><td><ul><li>WORK_ORDER_CREATOR</li><li>MUKTA_ADMIN</li></ul></td><td>/org-services/organisation/v1/_search</td></tr><tr><td><ul><li>MUKTA_ADMIN</li></ul></td><td>/org-services/organisation/v1/_create</td></tr><tr><td><ul><li>MUKTA_ADMIN</li></ul></td><td>/org-services/organisation/v1/_update</td></tr></tbody></table>

### Persister Configuration

Make sure [organisation-persister.yml ](https://github.com/egovernments/works-configs/blob/DEV/egov-persister/organisation-persister.yml)is present in the configs repo under the egov-persister directory.&#x20;

### Indexer Configuration

Make sure that the file [organisationservices-indexer.yml ](https://github.com/egovernments/works-configs/tree/DEV/egov-indexer)is present in the configs repo under the egov-indexer directory.

### ID Generation&#x20;

The following ID formats are configured for the Organisation service under the common-masters directory, IDFormat.json file. Make sure these are present in the file.

```json
   {
      "format": "ORG-[SEQ_ORG_NUM]",
      "idname": "org.number"
    },
    {
      "format": "SR/ORG/[cy:dd-MM-yyyy]/[SEQ_ORG_APP_NUM]",
      "idname": "org.application.number"
    },
    {
      "format": "SR/FUNC/[cy:dd-MM-yyyy]/[SEQ_FUNC_APP_NUM]",
      "idname": "function.application.number"
    }
```

### SMS Templates

SMS templates have to be configured with the service provider to send notifications to users. Current SMS template configurations are as follows:

```json
  {
           "code": "ORGANISATION_NOTIFICATION_ON_CREATE",
           "message": "Dear {individualName}, You have been registered as the contact person of {organisationName} on MuktaSoft, Organisation ID {ID}. To login please click on {cbo_portal_url}. Contact Mukta Coordinator for more details.",
           "module": "rainmaker-common-masters",
           "locale": "en_IN"
       },


       {
           "code": "ORGANISATION_NOTIFICATION_ON_UPDATE",
           "message": "Dear {contactpersonname}, The organization details has been updated on your request. Please contact the MUKTA Coordinator if you have not made this request. To login please click on {cbo_portal_url}.",
           "module": "rainmaker-common-masters",
           "locale": "en_IN"
       }

```

## Integration Details

Download the [Postman collection](https://github.com/egovernments/DIGIT-Works/blob/develop/backend/organisation/docs/Organisation%20Registry%20-%20Test%20Scripts.postman_collection.json) for this service and test the APIs against a DIGIT server.

[API spec](../../../platform/architecture/low-level-design/registries/organization.md#api-specification)
