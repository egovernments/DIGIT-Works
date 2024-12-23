# Measurement

### Overview

This module helps in taking the measurement, for an accepted contract\


This module has 5 associated screens :&#x20;

1. Create
2. Inbox
3. Edit
4. View
5. Search

#### Module enablement configuration&#x20;

1. Set module code as "Measurement" in Module.js of Measurement Module. Refer [https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/tenant/citymodule.json](https://github.com/egovernments/egov-mdms-data/commit/63b193bc9105ab3edba97e9f0b867fbec7e850a6)

```json
{
        "module": "Measurement",
        "code": "Measurement",
        "active": true,
        "order": 13,
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

2. Enable Module in App.js. Update the object ‘enabledModules’. Use the code “Measurement”. This code should match with the code of the cityModule.json, mentioned above.

#### Role-action mapping

<table data-header-hidden><thead><tr><th width="91"></th><th width="260"></th><th width="110"></th><th></th></tr></thead><tbody><tr><td>S.No</td><td>API</td><td><p>Action ID</p><p><br></p></td><td>Roles</td></tr><tr><td>2</td><td>to be updated</td><td>37</td><td>MB_CREATOR</td></tr><tr><td>3</td><td>to be updated</td><td>38</td><td>MB_VERIFIER</td></tr><tr><td>5</td><td>to be updated</td><td>104</td><td>MB_APPROVER</td></tr><tr><td>6</td><td>/egov-hrms/employees/_search</td><td>4</td><td>EMPLOYEE_COMMON</td></tr><tr><td>8</td><td>/inbox/v2/_search</td><td>65</td><td>EMPLOYEE_COMMON</td></tr></tbody></table>

#### Sidebar configuration&#x20;

<table data-header-hidden><thead><tr><th width="87"></th><th width="124"></th><th></th><th></th><th></th></tr></thead><tbody><tr><td>S.No</td><td>Screen</td><td>Navigation URL</td><td><p>LeftIcon Updates</p><p><br></p></td><td>Roles</td></tr><tr><td>2</td><td>to be updated</td><td></td><td></td><td></td></tr><tr><td></td><td></td><td></td><td></td><td></td></tr></tbody></table>

```json
{
      "id": 59,
      "name": "MEASUREMENT",
      "url": "url",
      "displayName": "MEASUREMENT Inbox",
      "orderNumber": 2,
      "parentModule": "",
      "enabled": true,
      "serviceCode": "ATTENDENCEMGMT",
      "code": "null",
      "navigationURL": "/works-ui/employee/measurement/inbox",
      "path": "4MEASUREMENT.Inbox",
      "leftIcon": "dynamic:AttendanceIcon"
}
```

#### Document Configuration

[https://github.com/egovernments/egov-mdms-data/commit/85761f2c82ced17a0259e077f6b82355e62ce35c](https://github.com/egovernments/egov-mdms-data/commit/85761f2c82ced17a0259e077f6b82355e62ce35c)

#### Screen configuration&#x20;

<table data-header-hidden><thead><tr><th width="71.99999999999997"></th><th width="139"></th><th></th></tr></thead><tbody><tr><td>S.No</td><td>Screen</td><td>Configuration</td></tr><tr><td>1</td><td>Search</td><td> to be updated</td></tr><tr><td>2</td><td>Inbox</td><td> to be updated</td></tr></tbody></table>

### Localization Configuration and Modules

<table data-header-hidden><thead><tr><th width="198"></th><th></th></tr></thead><tbody><tr><td>TenantID</td><td>Module</td></tr><tr><td>statea</td><td>rainmaker-measurement</td></tr><tr><td></td><td></td></tr></tbody></table>

### &#x20;Workflow Configuration

| UI Component Name | Business Service |
| ----------------- | ---------------- |
| WorkflowActions   | MB               |

### Custom Components

1. MeasureTable:&#x20;

&#x20;      This is the main component that handles the Logic for capturing the measurement for SOR / NONSOR based on the given processed Line items from the Estimate.

&#x20;       The same component has been used in the Detailed estimate which captures the detailed measurement information&#x20;

&#x20;      The same component has the capability to be used in both create and view screens.

2.  MeasureCard:

    &#x20;This component handles the logic of the inner table present inside the MeasureTable.

