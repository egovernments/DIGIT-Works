---
description: Workorder/Contract UI Tech Documentation
---

# Workorder

## Overview

This module helps in creating a work order for the Project based on the estimates designed.  This enables in deciding the Tendering, Quotation and Nomination. Based on the nomination, CBO is being mapped to the respective work order.

This module has 5 associated screens :&#x20;

1. Create
2. Search
3. Modify
4. View
5. Inbox

## MDMS Configurations

<table><thead><tr><th width="70.99999999999997">#</th><th width="183">Data</th><th>MDMS Link</th></tr></thead><tbody><tr><td>1</td><td>Role Of CBO</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/ContractCBORoles.json">https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/ContractCBORoles.json</a></td></tr><tr><td>2</td><td>Document Config</td><td>https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/DocumentConfig.json</td></tr></tbody></table>

### Module Enablement Configuration&#x20;

1. Set module code as “Contracts” in Module.js of Contracts Module. Refer https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/tenant/citymodule.json

```
 {
            "module": "Contracts",
            "code": "Contracts",
            "active": true,
            "order": 5,
            "tenants": [
                {
                    "code": "pg.cityb"
                },
                {
                    "code": "pg.cityc"
                },
                {
                    "code": "pg.citya"
                }
            ]
     }


```

2. Enable Module in App.js. Update the object ‘enabledModules’. Use the code “Contracts”. This code should match the code of the cityModule.json, mentioned above.

### Role-action Mapping

<table><thead><tr><th width="91">#</th><th width="260">API</th><th width="110">Action ID</th><th>Roles</th></tr></thead><tbody><tr><td>1</td><td><p>/contract-service/contract/v1/_create</p><p><br></p></td><td>66</td><td><p>WORK_ORDER_CREATOR</p><p><br></p></td></tr><tr><td>2</td><td>/contract-service/contract/v1/_search</td><td>68</td><td>WORK_ORDER_CREATOR, WORK_ORDER_APPROVER, WORK_ORDER_VERIFIER</td></tr><tr><td>3</td><td>/contract-service/contract/v1/_update</td><td>67</td><td>WORK_ORDER_CREATOR, WORK_ORDER_APPROVER, WORK_ORDER_VERIFIER</td></tr><tr><td>4</td><td>/estimate-service/estimate/v1/_search</td><td>10</td><td>ESTIMATE_VERIFIER</td></tr><tr><td>5</td><td>/pms/project/v1/_search</td><td>52</td><td>PROJECT_VIEWER</td></tr><tr><td>6</td><td>/egov-hrms/employees/_search</td><td>4</td><td>EMPLOYEE_COMMON</td></tr><tr><td>7</td><td>/org-services/organisation/v1/_search</td><td>91</td><td>ORG_ADMIN, WORK_ORDER_CREATOR, MUKTA_ADMIN</td></tr><tr><td>8</td><td>/wms/contract/_search</td><td>89</td><td>WORK_ORDER_CREATOR, WORK_ORDER_VERIFIER</td></tr></tbody></table>

### Sidebar Configuration&#x20;

<table><thead><tr><th width="87">#</th><th width="124">Screen</th><th>Navigation URL</th><th>LeftIcon Updates</th><th>Roles</th></tr></thead><tbody><tr><td>1</td><td>Contract Inbox</td><td><p>/works-ui/employee/contracts/inbox</p><p><br></p></td><td><p>dynamic:ContractIcon</p><p><br></p></td><td><p>WORK_ORDER_CREATOR, WORK_ORDER_VERIFIER, WORK_ORDER_APPROVER</p><p><br></p></td></tr><tr><td>2</td><td>Create Contract</td><td>/works-ui/employee/estimate/search-estimate</td><td>dynamic:ContractIcon</td><td>WORK_ORDER_CREATOR</td></tr></tbody></table>

{% code lineNumbers="true" %}
```
{
      "id": 58,
      "name": "CONTRACTS",
      "url": "url",
      "displayName": "Contracts Inbox",
      "orderNumber": 2,
      "parentModule": "",
      "enabled": true,
      "serviceCode": "CONTRACTS",
      "code": "null",
      "navigationURL": "/works-ui/employee/contracts/inbox",
      "path": "3CONTRACTS.Inbox",
      "leftIcon": "dynamic:ContractIcon"
   }

```
{% endcode %}

### Screen Configuration&#x20;

<table><thead><tr><th width="71.99999999999997">#</th><th width="139">Screen</th><th>Configuration</th></tr></thead><tbody><tr><td>1</td><td>Create </td><td>https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/commonMuktaUiConfig/CreateWorkOrderConfig.json</td></tr><tr><td>2</td><td>Search</td><td>https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/commonMuktaUiConfig/SearchContractConfig.json</td></tr><tr><td>3</td><td>Inbox</td><td>https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/commonMuktaUiConfig/InboxConfigContracts.json</td></tr></tbody></table>

### Localization Configuration & Modules

<table><thead><tr><th width="251">TenantID</th><th>Module</th></tr></thead><tbody><tr><td>pg</td><td>rainmaker-contracts</td></tr><tr><td>pg</td><td>rainmaker-common-masters</td></tr><tr><td>pg</td><td>rainmaker-common</td></tr><tr><td>pg</td><td>rainmaker-workflow</td></tr><tr><td>pg.citya</td><td>rainmaker-pg.citya</td></tr></tbody></table>

### &#x20;Workflow Configuration

| UI Component Name | Business Service        |
| ----------------- | ----------------------- |
| WorkflowActions   | contract-approval-mukta |

## Customization

1\. API-based Data -&#x20;

* Name of CBO - This field is being captured on the Create screen and Modify screen. Data for this field comes from the backend.
* CBO ID - This field is being set based on the value selected in the Name Of CBO.
* Name of Officer In Charge - This field is being populated based on the HRMS Search. Here the role is hardcoded to ‘OFFICER\_IN\_CHARGE’.
* Designation of Officer In Charge - This field is being set based on the value selected in the Name of Officer in charge.
* Work Order Amount - This field shows the calculated amount of Total Estimated Cost minus the Overheads which has ‘isWorkOrderValue’ as true. Please refer to [this](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/Overheads.json) OverHeads config for Amount calculation.
* The role of CBO will be shown based on the calculated Work Order Amount. If the calculated Work Order Amount is less than the ‘amount’ in RoleOfCBO Config, show Implementation Agency else show Implementation Partner. This logic will vary based on the updates in the [RoleOfCBO config](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/ContractCBORoles.json).

#### &#x20;  2.   Custom Components used in Projects :&#x20;

* Document Config - Based on the [document config](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/DocumentConfig.json), respective documents will be rendered on the Create Screen. Please mention the validations, file type limits and file size limits in this config.
* Work Order Terms and Conditions - This component will help input the description in the table. Users can add and delete the rows. The first row will not be deleted, clicking on the delete button will empty the first row. Refer Component - WOTermsAndConditions.

&#x20;**3.   Create Screen:**&#x20;

* Please use the common Utility “PreProcessMDMSConfig”.&#x20;
* Pass the associated dependencies for the config.
* The utility will return a config which will run through FormComposer.
* Refer to docs for Pre-Process config in Component comments.

&#x20; **4.  Inbox / Search Screen :**&#x20;

* Please use the common utility “preProcessMDMSConfigInboxSearch”.
* Pass the associated dependencies for the config.
* The utility will return a config which will run through RenderFormFields.
* Refer to docs for Pre-Process config in Component comments.
