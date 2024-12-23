---
description: Rate Analysis frontend tech document
---

# Rate Analysis

## Overview

The Rate analysis Module helps in creating rate analysis for Works type of SOR, and helps in bifurcating the rate and revision of rates.\


The module has 5 associated screens :&#x20;

* Create Rate Analysis
* View Rate Analysis
* Edit Rate Analysis

## Configurations

### MDMS Configurations

| S.No. | Data      | MDMS Link                                                                                                                         |
| ----- | --------- | --------------------------------------------------------------------------------------------------------------------------------- |
| 1     | SOR Data  | [SOR MDMS Data](https://mukta-uat.digit.org/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS-SOR\&masterName=SOR)  |
| 2     | Overheads | [Overheads](https://mukta-uat.digit.org/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS-SOR\&masterName=Overhead) |

### Module Enablement Configuration&#x20;

Set module code as “RateAnalysis” in Module.js of Rate Analysis Module. Refer to the file [citymodule](https://github.com/egovernments/egov-mdms-data/blob/UNIFIED-QA/data/pg/tenant/citymodule.json)

```
{
        "module": "RateAnalysis",
        "code": "RateAnalysis",
        "active": true,
        "order": 15,
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

Enable Module in App.js. Update the object ‘enabledModules’. Use the code RateAnalysis. This code should match the code of the cityModule.json, mentioned above.\
\
Role-action Mapping

<table><thead><tr><th width="93">S.No.</th><th width="268">API</th><th width="110">Action ID</th><th>Roles</th></tr></thead><tbody><tr><td>1</td><td>/mdms-v2/v2/_create/WORKS-SOR.Composition</td><td>9</td><td><p>MDMS_STATE_ADMIN</p><p><br></p></td></tr><tr><td>2</td><td>/mdms-v2/v2/_update/WORKS-SOR.Composition</td><td>10</td><td>MDMS_STATE_ADMIN</td></tr><tr><td>3</td><td>/rate-analysis/v1/_calculate</td><td>11</td><td>ESTIMATE_CREATOR, MDMS_STATE_ADMIN</td></tr></tbody></table>

### Sidebar Configuration

<table><thead><tr><th width="93">S.No.</th><th width="118">Screen</th><th width="197">Navigation URL</th><th width="190">Left Icon Updates</th><th>Roles</th></tr></thead><tbody><tr><td>1</td><td>Rate Analysis</td><td><p>/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS-SOR&#x26;masterName=Composition</p><p><br></p></td><td><p>dynamic:=ContractIcon</p><p><br></p></td><td><p>MDMS_STATE_ADMIN, MDMS_CITY_VIEW_ADMIN</p><p><br></p></td></tr></tbody></table>

Sample object for a sidebar action defined in MDMS (containing navigationUrl )

```

   {
      "id": 192,
      "name": "MDMS",
      "url": "url",
      "displayName": "Manage Works SOR Composition",
      "orderNumber": 1,
      "parentModule": "",
      "enabled": true,
      "serviceCode": "MDMS",
      "code": "null",
      "navigationURL": "/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS-SOR&masterName=Composition",
      "path": "9MDMS.WORKS-SOR.Composition",
      "leftIcon": "dynamic:ContractIcon"
    }
```

### Screen Configuration&#x20;

<table><thead><tr><th width="93.66666666666666">S.No.</th><th width="245">Screen</th><th>Configuration</th></tr></thead><tbody><tr><td>1</td><td>Create Rate Analysis</td><td><a href="https://github.com/odisha-muktasoft/MUKTA_IMPL/blob/UAT/frontend/micro-ui/web/micro-ui-internals/packages/modules/RateAnalysis/src/configs/RateAnalysisCreateConfig.js">Create Rate Analysis</a></td></tr><tr><td>2</td><td>View Rate Analysis</td><td><a href="https://github.com/odisha-muktasoft/MUKTA_IMPL/blob/UAT/frontend/micro-ui/web/micro-ui-internals/packages/modules/RateAnalysis/src/configs/ViewRateAnalysisConfig.js">View Rate Analysis</a></td></tr></tbody></table>

### Localization Configuration & Modules

<table><thead><tr><th width="185">TenantID</th><th>Module</th></tr></thead><tbody><tr><td>od</td><td>rainmaker-common</td></tr><tr><td>od</td><td>rainmaker-rateanalysis</td></tr><tr><td>od</td><td>rainmaker-common-masters</td></tr></tbody></table>

### Workflow Configuration

There is no workflow associated with the Rate Analysis, Only create, view and update screens are present, once the Rate Analysis is created, Users can View and edit the composition.

## Customisation

&#x20;**Custom Components used in Rate Analysis:**

1. SORDetailsTemplate - This Component is used to search the SOR and add it in the template but mentioning the quantity of each basic sors.
2. ExtraCharges - This Component is used to add the additional charges which is not present in the form of basic sors.
3. TableWithOutHead - This Component is used to show the total value of data with total LC and per unit cost.
4. RateCardWithRightButton - This Component is used to show the composition data with its rate value
5. RateAmountGroup - This Component is used to show the Existing rate and new rate for the particular works sor.
6. ExtraChargesViewTable - This Component is used to show the additional charges added during the creation of composition.

**Create/ Edit Rate Analysis Screen:**&#x20;

1. Use the common Utility “useCustomAPIHook” to gather the data needed for validation and composition creation.
2. Use the common Utility “getDefaultValues” to gather the init state of the create form.
3. Pass the createState dependencies to the formComposer.
4. Refer to docs for Pre-Process config in Component comments.
5. Figures and additional details is captured in the screen.

**View Rate Analysis Screen:**&#x20;

1. Use the common Utility “useViewRateAnalysisDetails” to gather the data to show from the calculate API of rate analysis.
2. Call the "viewRateAnalysisdataconfig" to render the view config for the rate analysis according to the dynamic data recieved
3. Pass the config in the "ViewComposer" to show the segregation of the data according to different heads of Rates.
