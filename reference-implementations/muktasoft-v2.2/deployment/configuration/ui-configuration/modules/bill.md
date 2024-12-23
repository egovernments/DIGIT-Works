# Bill

### Overview

This module is used to handle all billing-related actions such as create, update and view Bills.

This module has 5  associated screens :&#x20;

1. Create (Purchase Bill)
2. Search
3. View (Wage, Purchase, Supervision)
4. Modify (Purchase Bill)
5. Download Bills&#x20;

### MDMS Configurations

<table data-header-hidden><thead><tr><th width="119.99999999999997"></th><th></th><th></th></tr></thead><tbody><tr><td>S.No.</td><td>Data</td><td>MDMS Link</td></tr><tr><td>1</td><td>BillType</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/expense/BusinessService.json">https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/expense/BusinessService.json</a></td></tr><tr><td>2</td><td>TenantBoundary</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/8285bc63aac7f056326165897ac18918520c9723/data/pg/citya/egov-location/boundary-data.json">https://github.com/egovernments/works-mdms-data/blob/8285bc63aac7f056326165897ac18918520c9723/data/pg/citya/egov-location/boundary-data.json</a></td></tr></tbody></table>

#### Module enablement configuration&#x20;

1. Set module code as “Expenditure” in Module.js for Billing Module. Refer [CityModule.json](https://github.com/egovernments/works-mdms-data/blob/481752ba70aa29d235967d8ba6080685d897324a/data/pg/tenant/citymodule.json)

```
{
            "module": "Expenditure",
            "code": "Expenditure",
            "active": true,
            "order": 7,
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

2. Enable Module in App.js. Update the object ‘enabledModules’. Use the code “Expenditure”. This code should match with the code of the cityModule.json, mentioned above.

#### Role-action mapping



<table data-header-hidden><thead><tr><th width="105"></th><th></th><th></th><th></th></tr></thead><tbody><tr><td>S.No</td><td>API</td><td><p>Action ID</p><p><br></p></td><td>Roles</td></tr><tr><td>1</td><td>/expense-calculator/purchase/v1/_createbill</td><td>112</td><td>BILL_CREATOR</td></tr><tr><td>2</td><td>/expense-calculator/v1/_search</td><td>113</td><td>BILL_CREATOR<br>BILL_VERIFIER<br>BILL_VIEWER<br>BILL_ACCOUNTANT</td></tr><tr><td>3</td><td>/expense/bill/v1/_search</td><td>106</td><td>BILL_CREATOR<br>BILL_VERIFIER<br>BILL_APPROVER<br>BILL_ACCOUNTANT</td></tr><tr><td>4</td><td>/wms/expense/_search</td><td>110</td><td>BILL_CREATOR<br>BILL_VERIFIER<br>BILL_APPROVER<br>BILL_ACCOUNTANT</td></tr><tr><td>5</td><td>/expense/payment/v1/_create</td><td>116</td><td>BILL_ACCOUNTANT</td></tr><tr><td>6</td><td>/egov-pdf/bill/_search</td><td>114</td><td>BILL_CREATOR</td></tr><tr><td>7</td><td>/expense/payment/v1/_search</td><td>122</td><td>BILL_ACCOUNTANT</td></tr><tr><td>8</td><td>/expense/payment/v1/_update</td><td>125</td><td>BILL_ACCOUNTANT</td></tr><tr><td></td><td></td><td></td><td></td></tr></tbody></table>

#### Sidebar configuration&#x20;



<table data-header-hidden><thead><tr><th width="105"></th><th width="121"></th><th></th><th></th><th></th></tr></thead><tbody><tr><td>S.No</td><td>Screen</td><td>Navigation URL</td><td><p>LeftIcon Updates</p><p><br></p></td><td>Roles</td></tr><tr><td>1</td><td>Search Bills</td><td>/works-ui/employee/expenditure/search-bill</td><td>dynamic:ExpenditureIcon</td><td><p></p><p>BILL_VIEWER</p></td></tr></tbody></table>

Sample object for a sidebar action define in MDMS (containing navigationUrl)

```
{
      "id": 56,
      "name": "BILLS",
      "url": "url",
      "displayName": "Bill Search",
      "orderNumber": 2,
      "parentModule": "",
      "enabled": true,
      "serviceCode": "BILLS",
      "code": "null",
      "navigationURL": "/works-ui/employee/expenditure/search-bill",
      "path": "BILLS.BillSearch",
      "leftIcon": "dynamic:ExpenditureIcon"
    }

```



#### Screen configuration&#x20;

| S.No | Screen                      | Configuration                                                                                                                            |
| ---- | --------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- |
| 1    | Create/Modify Purchase bill | [Create Bill Config](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/commonMuktaUiConfig/CreatePurchaseBillConfig.json) |

### Localization Configuration and Modules

| TenantID | Module                   |
| -------- | ------------------------ |
| pg       | rainmaker-expenditure    |
| pg       | rainmaker-common         |
| pg       | rainmaker-common-masters |
| pg       | rainmaker-workflow       |
| pg.citya | rainmaker-pg.citya       |

### &#x20;Workflow Configuration

\<details>

### Customization

1. Create Purchase Bill Screen

* Please use the common Utility “PreProcessMDMSConfig”.&#x20;
* Pass the associated dependencies for the config.
* The utility will return a config which will run through FormComposer.
* Refer docs for Pre-Process config in Component comments.

2. Search Bill Screen

* Please use the common utility “preProcessMDMSConfigInboxSearch”.
* Pass the associated dependencies for the config.
* The utility will return a config which will run through RenderFormFields.
* Refer docs for Pre-Process config in Component comments.
* All the Inbox and Search Screens throughout the app are rendered using this component [Inbox/Search Composer](https://github.com/egovernments/DIGIT-Works/blob/c2a234bb4b21f0e54ca9664ee3e99d72ce871168/frontend/micro-ui/web/micro-ui-internals/packages/react-components/src/hoc/InboxSearchComposer.js)
* Checkbox selection is added to results table displayed in search screen based on search config passed via MDMS

3. Download Bill Screen

DownloadBillConfig is used to render table to show bills where download link is provided to download respective bills\
