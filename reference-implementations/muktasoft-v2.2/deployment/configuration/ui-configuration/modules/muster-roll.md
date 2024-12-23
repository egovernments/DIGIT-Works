---
description: Muster Roll UI Tech Documentation
---

# Muster Roll

### Overview

This module helps in processing muster rolls through the workflow. Muster rolls created from SHG app end up in the web interface for workflow approval.\


This module has 5 associated screens :&#x20;

1. Inbox
2. Edit
3. View
4. Search

#### Module enablement configuration&#x20;

1. Set module code as "AttendenceMgmt" in Module.js of AttendenceMgmt  Module. Refer https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/tenant/citymodule.json

```json
{
            "module": "AttendenceMgmt",
            "code": "AttendenceMgmt",
            "active": true,
            "order": 4,
            "tenants": [
                {
                    "code": "statea.citytwo"
                },
                {
                    "code": "statea.citythree"
                },
                {
                    "code": "statea.cityone"
                }
            ]
}
```

2. Enable Module in App.js. Update the object ‘enabledModules’. Use the code “AttendenceMgmt”. This code should match with the code of the cityModule.json, mentioned above.

#### Role-action mapping

<table data-header-hidden><thead><tr><th width="91"></th><th width="260"></th><th width="110"></th><th></th></tr></thead><tbody><tr><td>S.No</td><td>API</td><td><p>Action ID</p><p><br></p></td><td>Roles</td></tr><tr><td>2</td><td>/muster-roll/v1/_search</td><td>37</td><td>MUSTER_ROLL_VERIFIER,MUSTER_ROLL_APPROVER</td></tr><tr><td>3</td><td>/muster-roll/v1/_update</td><td>38</td><td>MUSTER_ROLL_VERIFIER,MUSTER_ROLL_APPROVER</td></tr><tr><td>5</td><td>/egov-pdf/download/musterRoll/muster-roll</td><td>104</td><td>MUSTER_ROLL_APPROVER,MUSTER_ROLL_VERIFIER</td></tr><tr><td>6</td><td>/egov-hrms/employees/_search</td><td>4</td><td>EMPLOYEE_COMMON</td></tr><tr><td>7</td><td>/expense-calculator/v1/_estimate</td><td>108</td><td>MUSTER_ROLL_APPROVER,MUSTER_ROLL_VERIFIER</td></tr><tr><td>8</td><td>/inbox/v2/_search</td><td>65</td><td>EMPLOYEE_COMMON</td></tr></tbody></table>

#### Sidebar configuration&#x20;

<table data-header-hidden><thead><tr><th width="87"></th><th width="124"></th><th></th><th></th><th></th></tr></thead><tbody><tr><td>S.No</td><td>Screen</td><td>Navigation URL</td><td><p>LeftIcon Updates</p><p><br></p></td><td>Roles</td></tr><tr><td>1</td><td>Muster Roll Search</td><td>/works-ui/employee/attendencemgmt/search-attendance</td><td>dynamic:AttendanceIcon</td><td>MUSTER_ROLL_APPROVER,MUSTER_ROLL_VERIFIER</td></tr><tr><td>2</td><td>Muster Roll Inbox</td><td>/works-ui/employee/attendencemgmt/inbox</td><td>dynamic:AttendanceIcon</td><td>EMPLOYEE_COMMON</td></tr><tr><td></td><td></td><td></td><td></td><td></td></tr></tbody></table>

```json
{
      "id": 59,
      "name": "ATTENDENCEMGMT",
      "url": "url",
      "displayName": "ATTENDENCEMGMT Inbox",
      "orderNumber": 2,
      "parentModule": "",
      "enabled": true,
      "serviceCode": "ATTENDENCEMGMT",
      "code": "null",
      "navigationURL": "/works-ui/employee/attendencemgmt/inbox",
      "path": "4ATTENDENCEMGMT.Inbox",
      "leftIcon": "dynamic:AttendanceIcon"
}
```

#### Screen configuration&#x20;

<table data-header-hidden><thead><tr><th width="71.99999999999997"></th><th width="139"></th><th></th></tr></thead><tbody><tr><td>S.No</td><td>Screen</td><td>Configuration</td></tr><tr><td>1</td><td>Search</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/c4be764c33afd9fbffacb631fb5343cb0f8c28eb/data/statea/commonMuktaUiConfig/SearchAttendanceWMSConfig.json">https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/commonMuktaUiConfig/SearchContractConfig.json</a></td></tr><tr><td>2</td><td>Inbox</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/9871717c016cf8a6311d752c9390e7ea00692077/data/statea/commonMuktaUiConfig/InboxMusterConfig.json">https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/commonMuktaUiConfig/InboxConfigContracts.json</a></td></tr></tbody></table>

### Localization Configuration and Modules

<table data-header-hidden><thead><tr><th width="198"></th><th></th></tr></thead><tbody><tr><td>TenantID</td><td>Module</td></tr><tr><td>statea</td><td>rainmaker-attendencemgmt</td></tr><tr><td></td><td></td></tr></tbody></table>

### &#x20;Workflow Configuration

| UI Component Name | Business Service |
| ----------------- | ---------------- |
| WorkflowActions   | MR               |

### Customization

1\. API-based Data -&#x20;

* The amountTotal Wage Amount - Amount shown in the view muster roll screen is fetched from expense-calculator

&#x32;**.  Inbox / Search Screen :**&#x20;

1. Please use the common utility “preProcessMDMSConfigInboxSearch”.
2. Pass the associated dependencies for the config.
3. The utility will return a config that will run through RenderFormFields.
4. Refer to docs for Pre-Process config in Component comments.
