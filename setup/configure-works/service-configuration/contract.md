---
description: Detailed description of configuring the contract service
---

# Contract

## Overview

The contract service captures work orders or purchase orders. It validates the work order against the estimate(s). Line items from one estimate can be put in a contract. Line items from multiple estimates can be aggregated into one work order as well. The contract service validates the line items from each estimate as part of Create and Update.

## Pre-requisites

A running DIGIT platform is needed to deploy the contract service. Specifically, the following dependencies are needed:

* Estimate
* Organisation
* User
* Workflow
* IDGen
* HRMS
* Notification
* Persister
* Indexer

## Key Functionalities

This service provides APIs to create, update and search for contracts.&#x20;

* Models a real-world work order/contract
* All line items of a single estimate can be put in a contract.
* Line items from multiple estimates can also be grouped into a contract.
* The service validates the estimate line items and ensures no duplication happens in including estimate line items in a contract.
* Terms and conditions, milestones and payment calendar are WIP

Refer to the [functional specifications here](../../../specifications/functional-specifications/contracts.md) for detailed scope and functionality. Low-level technical design is [available here.](../../../platform/architecture/low-level-design/services/contracts.md)

**Code** - [Contracts](https://github.com/egovernments/DIGIT-Works/tree/master/backend/contracts)

## Deployment

The variables below should be configured for the contract service in the Helm environment file before deployment. [Click here](https://github.com/egovernments/DIGIT-DevOps/tree/digit-works/deploy-as-code/helm/charts/digit-works/backend/contracts) to find the Helm environment file.

Refer to the [sample here](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml).

* Add configurations for db-host, db-name, db-url, domain, and core platform services (Idgen, workflow, user, etc.) in the YAML file.
* Search for "contract-service" in the environment YAML file. Add the contract-service environment variables similar to how it’s done in other entries.
* Add the ‘[egov-mdms-service](https://github.com/egovernments/DIGIT-DevOps/blob/5a9eb4c6141e19bd747238889ceed9bc9fffdc6f/deploy-as-code/helm/environments/works-dev.yaml#L190)’ related configuration to the respective environment YAML file. Make sure you change the gitsync.branch name.
* Check the contract-service persister file is added to the **`egov-persister.perister-yml-path`** variable. If not, update the values as given [here](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml#L310).
* Make sure to add the DB (Postgres and flyway) username & password in the respective environment secret YAML file. Refer to the details given [here](https://github.com/egovernments/DIGIT-DevOps/blob/e742a292f2966bb1affb3b03edd643a777917ba1/deploy-as-code/helm/environments/works-dev-secrets.yaml#L3).
* Make sure to add the DIGIT core services-related secrets configured in the respective environment secret file. Refer to the details given [here](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev-secrets.yaml).

{% hint style="info" %}
Restart egov-mdms-service, egov-persister, egov-indexer, inbox, egov-workflow-v2, egov-accesscontrol and zuul after the above changes are performed.
{% endhint %}

## Configuration

### MDMS Configuration

Configure actions, roles and role-action mappings from the table below. Refer to the details available [here](./).

<table><thead><tr><th width="361">Role</th><th>APIs</th></tr></thead><tbody><tr><td>WORK_ORDER_CREATOR</td><td>/contract/v1/_create</td></tr><tr><td></td><td>/contract/v1/_update</td></tr><tr><td></td><td>/contract/v1/_search</td></tr><tr><td></td><td>/wms/contract/_search</td></tr><tr><td>WORK_ORDER_VERIFIER</td><td>/contract/v1/_update</td></tr><tr><td></td><td>/contract/v1/_search</td></tr><tr><td></td><td>/wms/contract/_search</td></tr><tr><td>WORK_ORDER_APPROVER</td><td>/contract/v1/_update</td></tr><tr><td></td><td>/contract/v1/_search</td></tr><tr><td>WORK_ORDER_VIEWER</td><td>/contract/v1/_search</td></tr><tr><td></td><td>/wms/contract/_search</td></tr><tr><td>EMPLOYEE_COMMON</td><td>/inbox/v2/_search</td></tr></tbody></table>

These have to be translated into JSON in the role-action mapping module in MDMS.

Example - available [here](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/ACCESSCONTROL-ROLEACTIONS/roleactions.json).&#x20;

#### Other masters to be added:

Add the following masters as per the links below:

<table><thead><tr><th width="715">Master Data Configuration Links</th></tr></thead><tbody><tr><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/ContractCBORoles.json">CBO Roles</a></td></tr><tr><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/ContractOfficerIncharge.json">OCI Roles</a></td></tr><tr><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/ContractType.json">ContractType</a></td></tr><tr><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/Overheads.json">Overheads</a></td></tr></tbody></table>

## Idgen Configuration

Make sure the id format is configured in the ‘IdFormat.json’ file of the ‘common-masters’ module. The sample [is available here](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/IdFormat.json#L29-L32).&#x20;

| IDGen Format                                                                                                                                                                                                                                                  |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| <p>{</p><p>      "format": "WO/[fy:yyyy-yy]/[SEQ_CONTRACT_NUM]",</p><p>      "idname": "contract.number"</p><p> }<br>{</p><p>        "format": "RW/[fy:yyyy-yy]/[SEQ_CONT_SUPPLEMENT_NUM]",</p><p>        "idname": "contract.supplement.number" </p><p>}</p> |

### Workflow Configuration

#### Contract Workflow

The following workflow JSON needs to be put in the request body of the `/egov-workflow-v2/egov-wf/businessservice/_create` API.&#x20;

{% hint style="info" %}
For more information on configuring workflow, refer to the [Workflow Service](https://core.digit.org/platform/core-services/workflow) documentation.
{% endhint %}

{% code lineNumbers="true" %}
```json
"BusinessServices": [
    {
      "tenantId": "pg",
      "businessService": "contract-approval-mukta",
      "business": "contract-service",
      "businessServiceSla": 604800000,
      "states": [
        {
          "sla": null,
          "state": null,
          "applicationStatus": null,
          "docUploadRequired": false,
          "isStartState": true,
          "isTerminateState": false,
          "isStateUpdatable": true,
          "actions": [
            {
              "action": "CREATE",
              "nextState": "PENDING_FOR_VERIFICATION",
              "roles": [
                "WORK_ORDER_CREATOR"
              ]
            }
          ]
        },
        {
          "sla": 172800000,
          "state": "PENDING_FOR_VERIFICATION",
          "applicationStatus": "SUBMITTED",
          "docUploadRequired": false,
          "isStartState": false,
          "isTerminateState": false,
          "isStateUpdatable": true,
          "actions": [
            {
              "action": "VERIFY_AND_FORWARD",
              "nextState": "PENDING_FOR_APPROVAL",
              "roles": [
                "WORK_ORDER_VERIFIER"
              ]
            },
            {
              "action": "SEND_BACK",
              "nextState": "PENDING_FOR_CORRECTION",
              "roles": [
                "WORK_ORDER_VERIFIER"
              ]
            },
            {
              "action": "REJECT",
              "nextState": "REJECTED",
              "roles": [
                "WORK_ORDER_VERIFIER"
              ]
            }
          ]
        },
        {
          "sla": 86400000,
          "state": "PENDING_FOR_APPROVAL",
          "applicationStatus": "VERIFIED",
          "docUploadRequired": false,
          "isStartState": false,
          "isTerminateState": false,
          "isStateUpdatable": true,
          "actions": [
            {
              "action": "APPROVE",
              "nextState": "APPROVED",
              "roles": [
                "WORK_ORDER_APPROVER"
              ]
            },
            {
              "action": "SEND_BACK",
              "nextState": "PENDING_FOR_VERIFICATION",
              "roles": [
                "WORK_ORDER_APPROVER"
              ]
            },
            {
              "action": "REJECT",
              "nextState": "REJECTED",
              "roles": [
                "WORK_ORDER_APPROVER"
              ]
            },
            {
              "action": "SEND_BACK_TO_ORIGINATOR",
              "nextState": "PENDING_FOR_CORRECTION",
              "roles": [
                "WORK_ORDER_APPROVER"
              ]
            }
          ]
        },
        {
          "sla": 604800000,
          "state": "APPROVED",
          "applicationStatus": "APPROVED",
          "docUploadRequired": false,
          "isStartState": false,
          "isTerminateState": false,
          "isStateUpdatable": true,
          "actions": [
            {
              "action": "ACCEPT",
              "nextState": "ACCEPTED",
              "roles": [
                "ORG_ADMIN"
              ]
            },
            {
              "action": "DECLINE",
              "nextState": "PENDING_FOR_REASSIGNMENT",
              "roles": [
                "ORG_ADMIN"
              ]
            },
            {
              "action": "REJECT",
              "nextState": "REJECTED",
              "roles": [
                "ORG_ADMIN"
              ]
            }
          ]
        },
        {
          "sla": 86400000,
          "state": "PENDING_FOR_CORRECTION",
          "applicationStatus": "SENT_BACK",
          "docUploadRequired": false,
          "isStartState": true,
          "isTerminateState": false,
          "isStateUpdatable": true,
          "actions": [
            {
              "action": "EDIT",
              "nextState": "PENDING_FOR_VERIFICATION",
              "roles": [
                "WORK_ORDER_CREATOR"
              ]
            },
            {
              "action": "REJECT",
              "nextState": "REJECTED",
              "roles": [
                "WORK_ORDER_CREATOR"
              ]
            }
          ]
        },
        {
          "sla": 86400000,
          "state": "PENDING_FOR_REASSIGNMENT",
          "applicationStatus": "DECLINED",
          "docUploadRequired": false,
          "isStartState": true,
          "isTerminateState": false,
          "isStateUpdatable": true,
          "actions": [
            {
              "action": "EDIT",
              "nextState": "PENDING_FOR_VERIFICATION",
              "roles": [
                "WORK_ORDER_CREATOR"
              ]
            },
            {
              "action": "REJECT",
              "nextState": "REJECTED",
              "roles": [
                "WORK_ORDER_CREATOR"
              ]
            }
          ]
        },
        {
          "sla": null,
          "state": "ACCEPTED",
          "applicationStatus": "ACCEPTED",
          "docUploadRequired": false,
          "isStartState": false,
          "isTerminateState": true,
          "isStateUpdatable": false,
          "actions": [
          ]
        },
        {
          "sla": null,
          "state": "REJECTED",
          "applicationStatus": "REJECTED",
          "docUploadRequired": false,
          "isStartState": false,
          "isTerminateState": true,
          "isStateUpdatable": false,
          "actions": [
          ]
        }
      ]
    }
  ]
```
{% endcode %}

Sample CURL is also included below.&#x20;

{% hint style="warning" %}
Note that authToken and other requestInfo parameters should be updated as per your environment.
{% endhint %}

{% code lineNumbers="true" %}
```
curl --location 'http://localhost:8020/egov-workflow-v2/egov-wf/businessservice/_update' \
--header 'Content-Type: application/json' \
--data '{
    "RequestInfo": {
    "apiId": "estimate",
    "action": "",
    "did": 1,
    "key": "",
    "msgId": "20170310130900|en_IN",
    "requesterId": "",
    "ts": 1513579888683,
    "ver": "1.0.0",
    "authToken": "{{REPLACE THIS}}",
    "userInfo": {
      "uuid": "{{REPLACE WITH VALID USER ID}}"
    }
  },
  "BusinessServices": [
        {
            "tenantId": "pg",
            "uuid": "c798a157-b494-4b61-acb9-6c5923f2fc10",
            "businessService": "mukta-estimate",
            "business": "estimate-service",
            "businessServiceSla": 432000000,
            "states": [
                {
                    "auditDetails": {
                        "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                        "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                        "createdTime": 1678950370209,
                        "lastModifiedTime": 1678950370209
                    },
                    "uuid": "35fd0feb-319f-4af2-bfa1-faa770ec05cb",
                    "tenantId": "pg",
                    "businessServiceId": "c798a157-b494-4b61-acb9-6c5923f2fc10",
                    "sla": null,
                    "state": null,
                    "applicationStatus": null,
                    "docUploadRequired": false,
                    "isStartState": true,
                    "isTerminateState": false,
                    "isStateUpdatable": true,
                    "actions": [
                        {
                            "auditDetails": {
                                "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "createdTime": 1678950370209,
                                "lastModifiedTime": 1678950370209
                            },
                            "uuid": "a5689bee-5d02-4311-9ad4-62959bb50e8c",
                            "tenantId": "pg",
                            "currentState": "35fd0feb-319f-4af2-bfa1-faa770ec05cb",
                            "action": "SUBMIT",
                            "nextState": "6cc0eebd-e9f5-485b-94cd-cd28e7733974",
                            "roles": [
                                "ESTIMATE_CREATOR"
                            ],
                            "active": true
                        }
                    ]
                },
                {
                    "auditDetails": {
                        "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                        "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                        "createdTime": 1678950370209,
                        "lastModifiedTime": 1678950370209
                    },
                    "uuid": "6cc0eebd-e9f5-485b-94cd-cd28e7733974",
                    "tenantId": "pg",
                    "businessServiceId": "c798a157-b494-4b61-acb9-6c5923f2fc10",
                    "sla": 172800000,
                    "state": "PENDINGFORVERIFICATION",
                    "applicationStatus": "SUBMITTED",
                    "docUploadRequired": false,
                    "isStartState": true,
                    "isTerminateState": false,
                    "isStateUpdatable": true,
                    "actions": [
                        {
                            "auditDetails": {
                                "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "createdTime": 1678950370209,
                                "lastModifiedTime": 1678950370209
                            },
                            "uuid": "381cfe14-296f-4e03-8475-31495341a788",
                            "tenantId": "pg",
                            "currentState": "6cc0eebd-e9f5-485b-94cd-cd28e7733974",
                            "action": "VERIFYANDFORWARD",
                            "nextState": "98a0383d-5367-4f1e-a4f2-82a0b7ad6b9f",
                            "roles": [
                                "ESTIMATE_VERIFIER"
                            ],
                            "active": true
                        },
                        {
                            "auditDetails": {
                                "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "createdTime": 1678950370209,
                                "lastModifiedTime": 1678950370209
                            },
                            "uuid": "ca298290-42db-47c0-ae1d-f2e1249cc582",
                            "tenantId": "pg",
                            "currentState": "6cc0eebd-e9f5-485b-94cd-cd28e7733974",
                            "action": "SENDBACK",
                            "nextState": "06da8fe1-1d68-4ea8-9409-217182b58846",
                            "roles": [
                                "ESTIMATE_VERIFIER"
                            ],
                            "active": true
                        },
                        {
                            "auditDetails": {
                                "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "createdTime": 1678950370209,
                                "lastModifiedTime": 1678950370209
                            },
                            "uuid": "8dcacc97-36a4-42f1-8fab-8393cb3171c8",
                            "tenantId": "pg",
                            "currentState": "6cc0eebd-e9f5-485b-94cd-cd28e7733974",
                            "action": "REJECT",
                            "nextState": "37615e97-0eba-4022-be8b-3feaf0bf4441",
                            "roles": [
                                "ESTIMATE_VERIFIER"
                            ],
                            "active": true
                        }
                    ]
                },
                {
                    "auditDetails": {
                        "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                        "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                        "createdTime": 1678950370209,
                        "lastModifiedTime": 1678950370209
                    },
                    "uuid": "98a0383d-5367-4f1e-a4f2-82a0b7ad6b9f",
                    "tenantId": "pg",
                    "businessServiceId": "c798a157-b494-4b61-acb9-6c5923f2fc10",
                    "sla": 86400000,
                    "state": "PENDINGFORTECHNICALSANCTION",
                    "applicationStatus": "VERIFIED",
                    "docUploadRequired": false,
                    "isStartState": false,
                    "isTerminateState": false,
                    "isStateUpdatable": true,
                    "actions": [
                        {
                            "auditDetails": {
                                "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "createdTime": 1678950370209,
                                "lastModifiedTime": 1678950370209
                            },
                            "uuid": "bf5e5d56-9662-4077-b08b-fd3a678942d2",
                            "tenantId": "pg",
                            "currentState": "98a0383d-5367-4f1e-a4f2-82a0b7ad6b9f",
                            "action": "TECHNICALSANCTION",
                            "nextState": "c3249e25-9d3b-4b6f-9029-8a69b0b60415",
                            "roles": [
                                "TECHNICAL_SANCTIONER"
                            ],
                            "active": true
                        },
                        {
                            "auditDetails": {
                                "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "createdTime": 1678950370209,
                                "lastModifiedTime": 1678950370209
                            },
                            "uuid": "bb623a34-015b-4ce2-8167-d5333b5ffbce",
                            "tenantId": "pg",
                            "currentState": "98a0383d-5367-4f1e-a4f2-82a0b7ad6b9f",
                            "action": "SENDBACK",
                            "nextState": "6cc0eebd-e9f5-485b-94cd-cd28e7733974",
                            "roles": [
                                "TECHNICAL_SANCTIONER"
                            ],
                            "active": true
                        },
                        {
                            "auditDetails": {
                                "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "createdTime": 1678950370209,
                                "lastModifiedTime": 1678950370209
                            },
                            "uuid": "b08b8f1d-6fc6-49ab-98c2-887b90ff5d90",
                            "tenantId": "pg",
                            "currentState": "98a0383d-5367-4f1e-a4f2-82a0b7ad6b9f",
                            "action": "SENDBACKTOORIGINATOR",
                            "nextState": "06da8fe1-1d68-4ea8-9409-217182b58846",
                            "roles": [
                                "TECHNICAL_SANCTIONER"
                            ],
                            "active": true
                        },
                        {
                            "auditDetails": {
                                "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "createdTime": 1678950370209,
                                "lastModifiedTime": 1678950370209
                            },
                            "uuid": "0dacba16-22bf-44e3-bdc9-8dc54c215cbf",
                            "tenantId": "pg",
                            "currentState": "98a0383d-5367-4f1e-a4f2-82a0b7ad6b9f",
                            "action": "REJECT",
                            "nextState": "37615e97-0eba-4022-be8b-3feaf0bf4441",
                            "roles": [
                                "TECHNICAL_SANCTIONER"
                            ],
                            "active": true
                        }
                    ]
                },
                {
                    "auditDetails": {
                        "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                        "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                        "createdTime": 1678950370209,
                        "lastModifiedTime": 1678950370209
                    },
                    "uuid": "c3249e25-9d3b-4b6f-9029-8a69b0b60415",
                    "tenantId": "pg",
                    "businessServiceId": "c798a157-b494-4b61-acb9-6c5923f2fc10",
                    "sla": 86400000,
                    "state": "PENDINGFORAPPROVAL",
                    "applicationStatus": "TECHNICALLY SANCTIONED",
                    "docUploadRequired": false,
                    "isStartState": false,
                    "isTerminateState": false,
                    "isStateUpdatable": true,
                    "actions": [
                        {
                            "auditDetails": {
                                "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "createdTime": 1678950370209,
                                "lastModifiedTime": 1678950370209
                            },
                            "uuid": "bbb05598-8a74-42c4-8e04-b9499c571fda",
                            "tenantId": "pg",
                            "currentState": "c3249e25-9d3b-4b6f-9029-8a69b0b60415",
                            "action": "SENDBACK",
                            "nextState": "98a0383d-5367-4f1e-a4f2-82a0b7ad6b9f",
                            "roles": [
                                "ESTIMATE_APPROVER"
                            ],
                            "active": true
                        },
                        {
                            "auditDetails": {
                                "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "createdTime": 1678950370209,
                                "lastModifiedTime": 1678950370209
                            },
                            "uuid": "94d39cba-3271-4dae-b014-8eae07657bcc",
                            "tenantId": "pg",
                            "currentState": "c3249e25-9d3b-4b6f-9029-8a69b0b60415",
                            "action": "SENDBACKTOORIGINATOR",
                            "nextState": "06da8fe1-1d68-4ea8-9409-217182b58846",
                            "roles": [
                                "ESTIMATE_APPROVER"
                            ],
                            "active": true
                        },
                        {
                            "auditDetails": {
                                "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "createdTime": 1678950370209,
                                "lastModifiedTime": 1678950370209
                            },
                            "uuid": "97e550ff-20eb-4fd5-85ae-e395ba9dd358",
                            "tenantId": "pg",
                            "currentState": "c3249e25-9d3b-4b6f-9029-8a69b0b60415",
                            "action": "APPROVE",
                            "nextState": "fcd34bba-9f52-4de6-9a16-2a50315cfb91",
                            "roles": [
                                "ESTIMATE_APPROVER"
                            ],
                            "active": true
                        },
                        {
                            "auditDetails": {
                                "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "createdTime": 1678950370209,
                                "lastModifiedTime": 1678950370209
                            },
                            "uuid": "eaca07c5-d361-41a2-8d77-0063a075d2f3",
                            "tenantId": "pg",
                            "currentState": "c3249e25-9d3b-4b6f-9029-8a69b0b60415",
                            "action": "REJECT",
                            "nextState": "37615e97-0eba-4022-be8b-3feaf0bf4441",
                            "roles": [
                                "ESTIMATE_APPROVER"
                            ],
                            "active": true
                        }
                    ]
                },
                {
                    "auditDetails": {
                        "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                        "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                        "createdTime": 1678950370209,
                        "lastModifiedTime": 1678950370209
                    },
                    "uuid": "06da8fe1-1d68-4ea8-9409-217182b58846",
                    "tenantId": "pg",
                    "businessServiceId": "c798a157-b494-4b61-acb9-6c5923f2fc10",
                    "sla": 86400000,
                    "state": "PENDINGFORCORRECTION",
                    "applicationStatus": "SENT BACK",
                    "docUploadRequired": false,
                    "isStartState": false,
                    "isTerminateState": false,
                    "isStateUpdatable": true,
                    "actions": [
                        {
                            "auditDetails": {
                                "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "createdTime": 1678950370209,
                                "lastModifiedTime": 1678950370209
                            },
                            "uuid": "6228efd7-30c5-4bef-ae67-0c6517707b37",
                            "tenantId": "pg",
                            "currentState": "06da8fe1-1d68-4ea8-9409-217182b58846",
                            "action": "RE-SUBMITTED",
                            "nextState": "6cc0eebd-e9f5-485b-94cd-cd28e7733974",
                            "roles": [
                                "ESTIMATE_CREATOR"
                            ],
                            "active": true
                        },
                        {
                            "auditDetails": {
                                "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                                "createdTime": 1678950370209,
                                "lastModifiedTime": 1678950370209
                            },
                            "uuid": "fc711556-4849-4ca1-a47b-54f3facaac79",
                            "tenantId": "pg",
                            "currentState": "06da8fe1-1d68-4ea8-9409-217182b58846",
                            "action": "REJECT",
                            "nextState": "37615e97-0eba-4022-be8b-3feaf0bf4441",
                            "roles": [
                                "ESTIMATE_CREATOR"
                            ],
                            "active": true
                        }
                    ]
                },
                {
                    "auditDetails": {
                        "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                        "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                        "createdTime": 1678950370209,
                        "lastModifiedTime": 1678950370209
                    },
                    "uuid": "fcd34bba-9f52-4de6-9a16-2a50315cfb91",
                    "tenantId": "pg",
                    "businessServiceId": "c798a157-b494-4b61-acb9-6c5923f2fc10",
                    "sla": null,
                    "state": "APPROVED",
                    "applicationStatus": "APPROVED",
                    "docUploadRequired": false,
                    "isStartState": false,
                    "isTerminateState": true,
                    "isStateUpdatable": false,
                    "actions": null
                },
                {
                    "auditDetails": {
                        "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                        "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                        "createdTime": 1678950370209,
                        "lastModifiedTime": 1678950370209
                    },
                    "uuid": "37615e97-0eba-4022-be8b-3feaf0bf4441",
                    "tenantId": "pg",
                    "businessServiceId": "c798a157-b494-4b61-acb9-6c5923f2fc10",
                    "sla": null,
                    "state": "REJECTED",
                    "applicationStatus": "REJECTED",
                    "docUploadRequired": false,
                    "isStartState": false,
                    "isTerminateState": true,
                    "isStateUpdatable": false,
                    "actions": null
                }
            ],
            "auditDetails": {
                "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                "createdTime": 1678950370209,
                "lastModifiedTime": 1678950370209
            }
        }
    ]
}'l
```
{% endcode %}

### Revised Contract Workflow

{% code lineNumbers="true" %}
```json
"BusinessServices": [
  {
    "tenantId": "pg",
    "businessService": "CONTRACT-REVISION",
    "business": "contract-service",
    "businessServiceSla": 604800000,
    "states": [
      {
        "sla": null,
        "state": null,
        "applicationStatus": null,
        "docUploadRequired": false,
        "isStartState": true,
        "isTerminateState": false,
        "isStateUpdatable": true,
        "actions": [
          {
            "action": "CREATE",
            "nextState": "PENDING_FOR_VERIFICATION",
            "roles": [
              "WORK_ORDER_CREATOR", "ORG_ADMIN"
            ]
          }
        ]
      },
      {
        "sla": 172800000,
        "state": "PENDING_FOR_VERIFICATION",
        "applicationStatus": "INWORKFLOW",
        "docUploadRequired": false,
        "isStartState": false,
        "isTerminateState": false,
        "isStateUpdatable": true,
        "actions": [
          {
            "action": "VERIFY_AND_FORWARD",
            "nextState": "PENDING_FOR_APPROVAL",
            "roles": [
              "WORK_ORDER_VERIFIER"
            ]
          },
          {
            "action": "SEND_BACK",
            "nextState": "PENDING_FOR_CORRECTION",
            "roles": [
              "WORK_ORDER_VERIFIER"
            ]
          }
        ]
      },
      {
        "sla": 86400000,
        "state": "PENDING_FOR_APPROVAL",
        "applicationStatus": "INWORKFLOW",
        "docUploadRequired": false,
        "isStartState": false,
        "isTerminateState": false,
        "isStateUpdatable": true,
        "actions": [
          {
            "action": "APPROVE",
            "nextState": "APPROVED",
            "roles": [
              "WORK_ORDER_APPROVER"
            ]
          },
          {
            "action": "SEND_BACK",
            "nextState": "PENDING_FOR_VERIFICATION",
            "roles": [
              "WORK_ORDER_APPROVER"
            ]
          },
          {
            "action": "REJECT",
            "nextState": "REJECTED",
            "roles": [
              "WORK_ORDER_APPROVER"
            ]
          },
          {
            "action": "SEND_BACK_TO_ORIGINATOR",
            "nextState": "PENDING_FOR_CORRECTION",
            "roles": [
              "WORK_ORDER_APPROVER"
            ]
          }
        ]
      },

      {
        "sla": 86400000,
        "state": "PENDING_FOR_CORRECTION",
        "applicationStatus": "INWORKFLOW",
        "docUploadRequired": false,
        "isStartState": true,
        "isTerminateState": false,
        "isStateUpdatable": true,
        "actions": [
          {
            "action": "EDIT",
            "nextState": "PENDING_FOR_VERIFICATION",
            "roles": [
              "WORK_ORDER_CREATOR","ORG_ADMIN"
            ]
          },
          {
            "action": "REJECT",
            "nextState": "REJECTED",
            "roles": [
              "WORK_ORDER_CREATOR","ORG_ADMIN"
            ]
          }
        ]
      },
      {
        "sla": null,
        "state": "APPROVED",
        "applicationStatus": "ACTIVE",
        "docUploadRequired": false,
        "isStartState": false,
        "isTerminateState": true,
        "isStateUpdatable": false,
        "actions": [

        ]
      },
      {
        "sla": null,
        "state": "REJECTED",
        "applicationStatus": "INACTIVE",
        "docUploadRequired": false,
        "isStartState": false,
        "isTerminateState": true,
        "isStateUpdatable": false,
        "actions": [

        ]
      }
    ]
  }
]
```
{% endcode %}

Sample CURL is also included below.&#x20;

{% hint style="warning" %}
Note that authToken and other requestInfo parameters should be updated as per your environment.
{% endhint %}

{% code lineNumbers="true" %}
```
curl --location 'http://localhost:8020/egov-workflow-v2/egov-wf/businessservice/_update' \
--header 'Content-Type: application/json' \
--data '{
    "RequestInfo": {
    "apiId": "estimate",
    "action": "",
    "did": 1,
    "key": "",
    "msgId": "20170310130900|en_IN",
    "requesterId": "",
    "ts": 1513579888683,
    "ver": "1.0.0",
    "authToken": "{{REPLACE THIS}}",
    "userInfo": {
      "uuid": "{{REPLACE WITH VALID USER ID}}"
    }
  },
  "BusinessServices": [
    {
      "tenantId": "pg",
      "uuid": "96d0ce24-dab0-4533-a3aa-748156327b52",
      "businessService": "CONTRACT-REVISION",
      "business": "contract-service",
      "businessServiceSla": 604800000,
      "states": [
          {
              "auditDetails": {
                  "createdBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                  "lastModifiedBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                  "createdTime": 1691483807145,
                  "lastModifiedTime": 1691483807145
              },
              "uuid": "9c3aa3c0-05bf-4c1e-b0bb-0aec4f4a1f14",
              "tenantId": "pg",
              "businessServiceId": "96d0ce24-dab0-4533-a3aa-748156327b52",
              "sla": null,
              "state": null,
              "applicationStatus": null,
              "docUploadRequired": false,
              "isStartState": true,
              "isTerminateState": false,
              "isStateUpdatable": true,
              "actions": [
                  {
                      "auditDetails": {
                          "createdBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "lastModifiedBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "createdTime": 1691483807145,
                          "lastModifiedTime": 1691483807145
                      },
                      "uuid": "7bc558f7-ad3f-4d74-bbce-2b5b0123d9ee",
                      "tenantId": "pg",
                      "currentState": "9c3aa3c0-05bf-4c1e-b0bb-0aec4f4a1f14",
                      "action": "CREATE",
                      "nextState": "4858468d-b1a2-4c65-9db5-f6339da527e5",
                      "roles": [
                          "WORK_ORDER_CREATOR",
                          "ORG_ADMIN"
                      ],
                      "active": true
                  }
              ]
          },
          {
              "auditDetails": {
                  "createdBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                  "lastModifiedBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                  "createdTime": 1691483807145,
                  "lastModifiedTime": 1691483807145
              },
              "uuid": "4858468d-b1a2-4c65-9db5-f6339da527e5",
              "tenantId": "pg",
              "businessServiceId": "96d0ce24-dab0-4533-a3aa-748156327b52",
              "sla": 172800000,
              "state": "PENDING_FOR_VERIFICATION",
              "applicationStatus": "INWORKFLOW",
              "docUploadRequired": false,
              "isStartState": false,
              "isTerminateState": false,
              "isStateUpdatable": true,
              "actions": [
                  {
                      "auditDetails": {
                          "createdBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "lastModifiedBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "createdTime": 1691483807145,
                          "lastModifiedTime": 1691483807145
                      },
                      "uuid": "3edf6eb5-3639-4e08-9fdb-db759006dc80",
                      "tenantId": "pg",
                      "currentState": "4858468d-b1a2-4c65-9db5-f6339da527e5",
                      "action": "SEND_BACK",
                      "nextState": "4965e223-036d-4aa0-9f6b-461004fcae58",
                      "roles": [
                          "WORK_ORDER_VERIFIER"
                      ],
                      "active": true
                  },
                  {
                      "auditDetails": {
                          "createdBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "lastModifiedBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "createdTime": 1691483807145,
                          "lastModifiedTime": 1691483807145
                      },
                      "uuid": "a8ff8adf-d10e-46ba-ba44-5b8251e30f88",
                      "tenantId": "pg",
                      "currentState": "4858468d-b1a2-4c65-9db5-f6339da527e5",
                      "action": "VERIFY_AND_FORWARD",
                      "nextState": "a2240a1f-6c7c-4945-bdbc-a47fc1bd00ae",
                      "roles": [
                          "WORK_ORDER_VERIFIER"
                      ],
                      "active": true
                  }
              ]
          },
          {
              "auditDetails": {
                  "createdBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                  "lastModifiedBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                  "createdTime": 1691483807145,
                  "lastModifiedTime": 1691483807145
              },
              "uuid": "a2240a1f-6c7c-4945-bdbc-a47fc1bd00ae",
              "tenantId": "pg",
              "businessServiceId": "96d0ce24-dab0-4533-a3aa-748156327b52",
              "sla": 86400000,
              "state": "PENDING_FOR_APPROVAL",
              "applicationStatus": "INWORKFLOW",
              "docUploadRequired": false,
              "isStartState": false,
              "isTerminateState": false,
              "isStateUpdatable": true,
              "actions": [
                  {
                      "auditDetails": {
                          "createdBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "lastModifiedBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "createdTime": 1691483807145,
                          "lastModifiedTime": 1691483807145
                      },
                      "uuid": "82dfa853-d704-41e5-9b9c-b739fb198342",
                      "tenantId": "pg",
                      "currentState": "a2240a1f-6c7c-4945-bdbc-a47fc1bd00ae",
                      "action": "SEND_BACK_TO_ORIGINATOR",
                      "nextState": "4965e223-036d-4aa0-9f6b-461004fcae58",
                      "roles": [
                          "WORK_ORDER_APPROVER"
                      ],
                      "active": true
                  },
                  {
                      "auditDetails": {
                          "createdBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "lastModifiedBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "createdTime": 1691483807145,
                          "lastModifiedTime": 1691483807145
                      },
                      "uuid": "db24f24f-daa1-45d6-954b-5ad7cc463c2b",
                      "tenantId": "pg",
                      "currentState": "a2240a1f-6c7c-4945-bdbc-a47fc1bd00ae",
                      "action": "REJECT",
                      "nextState": "6c7d6892-ea91-4a0d-bfe1-fdb769de02fd",
                      "roles": [
                          "WORK_ORDER_APPROVER"
                      ],
                      "active": true
                  },
                  {
                      "auditDetails": {
                          "createdBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "lastModifiedBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "createdTime": 1691483807145,
                          "lastModifiedTime": 1691483807145
                      },
                      "uuid": "f80931ac-b9ef-40e0-b6bf-f73513546586",
                      "tenantId": "pg",
                      "currentState": "a2240a1f-6c7c-4945-bdbc-a47fc1bd00ae",
                      "action": "APPROVE",
                      "nextState": "14decd06-4369-4c3f-ae3f-28b633aec9e8",
                      "roles": [
                          "WORK_ORDER_APPROVER"
                      ],
                      "active": true
                  },
                  {
                      "auditDetails": {
                          "createdBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "lastModifiedBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "createdTime": 1691483807145,
                          "lastModifiedTime": 1691483807145
                      },
                      "uuid": "44b62d0d-78cb-4c5f-9fc0-3e7f3d7ad7f1",
                      "tenantId": "pg",
                      "currentState": "a2240a1f-6c7c-4945-bdbc-a47fc1bd00ae",
                      "action": "SEND_BACK",
                      "nextState": "4858468d-b1a2-4c65-9db5-f6339da527e5",
                      "roles": [
                          "WORK_ORDER_APPROVER"
                      ],
                      "active": true
                  }
              ]
          },
          {
              "auditDetails": {
                  "createdBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                  "lastModifiedBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                  "createdTime": 1691483807145,
                  "lastModifiedTime": 1691483807145
              },
              "uuid": "4965e223-036d-4aa0-9f6b-461004fcae58",
              "tenantId": "pg",
              "businessServiceId": "96d0ce24-dab0-4533-a3aa-748156327b52",
              "sla": 86400000,
              "state": "PENDING_FOR_CORRECTION",
              "applicationStatus": "INWORKFLOW",
              "docUploadRequired": false,
              "isStartState": true,
              "isTerminateState": false,
              "isStateUpdatable": true,
              "actions": [
                  {
                      "auditDetails": {
                          "createdBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "lastModifiedBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "createdTime": 1691483807145,
                          "lastModifiedTime": 1691483807145
                      },
                      "uuid": "35c4c66d-4792-4899-8644-f58beb31ada0",
                      "tenantId": "pg",
                      "currentState": "4965e223-036d-4aa0-9f6b-461004fcae58",
                      "action": "EDIT",
                      "nextState": "4858468d-b1a2-4c65-9db5-f6339da527e5",
                      "roles": [
                          "WORK_ORDER_CREATOR",
                          "ORG_ADMIN"
                      ],
                      "active": true
                  },
                  {
                      "auditDetails": {
                          "createdBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "lastModifiedBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                          "createdTime": 1691483807145,
                          "lastModifiedTime": 1691483807145
                      },
                      "uuid": "f1d4e7f5-4639-4c57-b2c9-fff028698702",
                      "tenantId": "pg",
                      "currentState": "4965e223-036d-4aa0-9f6b-461004fcae58",
                      "action": "REJECT",
                      "nextState": "6c7d6892-ea91-4a0d-bfe1-fdb769de02fd",
                      "roles": [
                          "WORK_ORDER_CREATOR",
                          "ORG_ADMIN"
                      ],
                      "active": true
                  }
              ]
          },
          {
              "auditDetails": {
                  "createdBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                  "lastModifiedBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                  "createdTime": 1691483807145,
                  "lastModifiedTime": 1691483807145
              },
              "uuid": "14decd06-4369-4c3f-ae3f-28b633aec9e8",
              "tenantId": "pg",
              "businessServiceId": "96d0ce24-dab0-4533-a3aa-748156327b52",
              "sla": null,
              "state": "APPROVED",
              "applicationStatus": "ACTIVE",
              "docUploadRequired": false,
              "isStartState": false,
              "isTerminateState": true,
              "isStateUpdatable": false,
              "actions": null
          },
          {
              "auditDetails": {
                  "createdBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                  "lastModifiedBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
                  "createdTime": 1691483807145,
                  "lastModifiedTime": 1691483807145
              },
              "uuid": "6c7d6892-ea91-4a0d-bfe1-fdb769de02fd",
              "tenantId": "pg",
              "businessServiceId": "96d0ce24-dab0-4533-a3aa-748156327b52",
              "sla": null,
              "state": "REJECTED",
              "applicationStatus": "INACTIVE",
              "docUploadRequired": false,
              "isStartState": false,
              "isTerminateState": true,
              "isStateUpdatable": false,
              "actions": null
          }
      ],
      "auditDetails": {
          "createdBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
          "lastModifiedBy": "d39db015-746a-40b7-95ab-6e8b36de40ff",
          "createdTime": 1691483807145,
          "lastModifiedTime": 1691483807145
      }
  }
  ]
}'
```
{% endcode %}

### Persister Configuration

Make sure that the file[ <mark style="color:purple;">contract-service-persister.yml</mark>](https://github.com/egovernments/works-configs/blob/DEV/egov-persister/contract-service-persister.yml) is present in the **`configs`** repository in the below location.&#x20;

[https://github.com/\<YOUR ORGANISATION>/works-configs/tree/DEV/egov-persister](https://github.com/egovernments/works-configs/tree/DEV/egov-persister)

In case it is not available, add the persister YML file.&#x20;

{% hint style="warning" %}
Make sure to restart MDMS and the persister service after adding the file at the above location.
{% endhint %}

### Indexer Configuration

Make sure that the [contract service indexer file](https://github.com/egovernments/works-configs/blob/DEV/egov-indexer/contractservices-indexer.yml) is present in the [**`configs`** repository here](https://github.com/egovernments/works-configs/tree/DEV/egov-indexer).&#x20;

### Inbox Configuration

In the MDMS repository, locate the[ inbox configuration file](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/inbox-v2/InboxConfiguration.json). Make sure the following JSON is added to the inbox configuration:

{% code lineNumbers="true" %}
```json
{
  "module": "contract-service",
  "index": "contract-inbox",
  "allowedSearchCriteria": [
    {
      "name": "tenantId",
      "path": "Data.tenantId.keyword",
      "isMandatory": true,
      "operator": "EQUAL"
    },
    {
      "name": "workOrderNumber",
      "path": "Data.contractNumber.keyword",
      "isMandatory": true,
      "operator": "EQUAL"
    },
    {
      "name": "status",
      "path": "Data.currentProcessInstance.state.uuid.keyword",
      "isMandatory": false
    },
    {
      "name": "projectId",
      "path": "Data.additionalDetails.projectId.keyword",
      "isMandatory": false,
      "operator": "EQUAL"
    },
    {
      "name": "projectType",
      "path": "Data.additionalDetails.projectType.keyword",
      "isMandatory": false,
      "operator": "EQUAL"
    },
    {
      "name": "ward",
      "path": "Data.additionalDetails.ward.keyword",
      "isMandatory": false,
      "operator": "EQUAL"
    },
    {
      "name": "locality",
      "path": "Data.additionalDetails.locality.keyword",
      "isMandatory": false,
      "operator": "EQUAL"
    },
    {
      "name": "wfStatus",
      "path": "Data.contractStatus.keyword",
      "isMandatory": false
    },
    {
      "name": "assignee",
      "path": "Data.currentProcessInstance.assignes.uuid.keyword",
      "isMandatory": false
    }
  ],
  "sortBy": {
    "path": "Data.auditDetails.createdTime",
    "defaultOrder": "DESC"
  },
  "sourceFilterPathList": [
    "Data.contractNumber",
    "Data.additionalDetails.projectName",
    "Data.additionalDetails.projectId",
    "Data.additionalDetails.orgName",
    "Data.currentProcessInstance",
    "Data.totalContractedAmount",
    "Data.auditDetails"
  ]
}
```
{% endcode %}

{% hint style="warning" %}
Restart the Inbox service after updating the above configuration
{% endhint %}

## Integration

The API specifications for this service are located [here](../../../platform/architecture/low-level-design/services/contracts.md#api-contract-link).&#x20;

[Click here](https://github.com/egovernments/DIGIT-Works/blob/master/backend/contracts/Contract_Service_Postman_Scripts.postman_collection.json) to access the Postman scripts to understand the request payloads.&#x20;

