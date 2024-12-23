---
description: Organization Tech Document
---

# Organization

This module helps in creating an organization which are business functions that work with ULB to execute projects. These can be typical contractors, SHGs or material vendors. All of them are registered under the same Organization Master.&#x20;



This module has 4 associated screens :&#x20;

1. Create
2. Search
3. View
4. Modify

### MDMS Configurations

#### MDMS Configurations

<table data-header-hidden><thead><tr><th width="98.99999999999997"></th><th width="191"></th><th></th></tr></thead><tbody><tr><td>S.No.</td><td>Data</td><td>MDMS Link</td></tr><tr><td>1</td><td>OrgType</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/OrgType.json">https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/OrgType.json</a></td></tr><tr><td>2</td><td>OrgFunctionCategory</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/OrgFunctionCategory.json">https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/OrgFunctionCategory.json</a></td></tr><tr><td>3</td><td>OrgFunctionClass</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/OrgFunctionClass.json">https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/OrgFunctionClass.json</a></td></tr><tr><td>4</td><td>OrgTaxIdentifier</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/OrgTaxIdentifier.json">https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/OrgTaxIdentifier.json</a></td></tr><tr><td>5</td><td>BankAccType</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/BankAccType.json">https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/BankAccType.json</a></td></tr><tr><td>6</td><td>OrgTransferCode</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/OrgTransferCode.json">https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/OrgTransferCode.json</a></td></tr><tr><td>7</td><td>TenantBoundary</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/8285bc63aac7f056326165897ac18918520c9723/data/pg/citya/egov-location/boundary-data.json">https://github.com/egovernments/works-mdms-data/blob/8285bc63aac7f056326165897ac18918520c9723/data/pg/citya/egov-location/boundary-data.json</a></td></tr></tbody></table>

#### Module enablement configuration&#x20;

1. Set module code as “Masters” in Module.js for Masters (Organization) Module. Refer [CityModule.json](https://github.com/egovernments/works-mdms-data/blob/481752ba70aa29d235967d8ba6080685d897324a/data/pg/tenant/citymodule.json)

```
 {
            "module": "Masters",
            "code": "Masters",
            "active": true,
            "order": 6,
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

2. Enable Module in App.js. Update the object ‘enabledModules’. Use the code “Masters”. This code should match with the code of the cityModule.json, mentioned above.

#### Role-action mapping



<table data-header-hidden><thead><tr><th width="87"></th><th></th><th></th><th></th></tr></thead><tbody><tr><td>S.No</td><td>API</td><td><p>Action ID</p><p><br></p></td><td>Roles</td></tr><tr><td>1</td><td><p>/org-services/organisation/v1/_create</p><p><br></p></td><td>100</td><td>MUKTA_ADMIN</td></tr><tr><td>2</td><td>/org-services/organisation/v1/_search</td><td>91</td><td>MUKTA_ADMIN,<br>ORG_ADMIN, WORK_ORDER_CREATOR</td></tr><tr><td>3</td><td>/org-services/organisation/v1/_update</td><td>101</td><td>MUKTA_ADMIN</td></tr></tbody></table>

#### Sidebar configuration&#x20;



<table data-header-hidden><thead><tr><th width="96"></th><th></th><th></th><th></th><th></th></tr></thead><tbody><tr><td>S.No</td><td>Screen</td><td>Navigation URL</td><td><p>LeftIcon Updates</p><p><br></p></td><td>Roles</td></tr><tr><td>1</td><td>Create Organisation</td><td>/works-ui/employee/masters/create-organization</td><td>dynamic:OrganisationIcon</td><td>MUKTA_ADMIN</td></tr><tr><td>2</td><td>Search Organisation</td><td>/works-ui/employee/masters/search-organization</td><td>dynamic:OrganisationIcon</td><td>MUKTA_ADMIN</td></tr></tbody></table>

#### Screen configuration&#x20;

<table data-header-hidden><thead><tr><th width="129.99999999999997"></th><th></th><th></th></tr></thead><tbody><tr><td>S.No</td><td>Screen</td><td>Configuration</td></tr><tr><td>1</td><td>Create/Modify</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/commonMuktaUiConfig/CreateOrganizationConfig.json">Create Org Config</a></td></tr><tr><td>2</td><td>Search</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/commonMuktaUiConfig/SearchOrganisationConfig.json">Search Org Config</a></td></tr></tbody></table>

### Localization Configuration and Modules

<table data-header-hidden><thead><tr><th width="234"></th><th></th></tr></thead><tbody><tr><td>TenantID</td><td>Module</td></tr><tr><td>pg</td><td>rainmaker-masters</td></tr><tr><td>pg</td><td>rainmaker-common-masters</td></tr><tr><td>pg</td><td>rainmaker-common</td></tr><tr><td>pg.citya</td><td>rainmaker-pg.citya</td></tr></tbody></table>

### &#x20;Workflow Configuration

NA

### Customization

1. Custom Components used

&#x20;     TransferCodeTable -

* This component is a common component used to show Tax identifiers and     Transfer Codes input options in the ‘Financial Details’ section in Create screen
* Dropdown options for tax identifiers and transfer codes are populated by MDMS data
* Used can select type via dropdown and value needs to be entered for the corresponding type
* Users can add and delete rows for tax identifiers since there can be multiple identifiers. First row won't be deleted.
* Refer Component: [TransferCodeComponent](https://github.com/egovernments/DIGIT-Works/blob/master/frontend/micro-ui/web/micro-ui-internals/packages/modules/Masters/src/components/TransferCodeTable.js)\


#### 2. Create Organization Screen

* Please use the common Utility “PreProcessMDMSConfig”.&#x20;
* Pass the associated dependencies for the config.
* The utility will return a config that will run through FormComposer.
* Refer to docs for Pre-Process config in Component comments.
* Tax Identifiers and Transfer Codes are MDMS data.&#x20;

3\. Search Organization Screen

* Please use the common utility “preProcessMDMSConfigInboxSearch”.
* Pass the associated dependencies for the config.
* The utility will return a config that will run through RenderFormFields.
* Refer to docs for Pre-Process config in Component comments.
* All the Inbox and Search Screens throughout the app are rendered using this component [Inbox/Search Composer](https://github.com/egovernments/DIGIT-Works/blob/c2a234bb4b21f0e54ca9664ee3e99d72ce871168/frontend/micro-ui/web/micro-ui-internals/packages/react-components/src/hoc/InboxSearchComposer.js)
* Validations added
* All Form validations are being added in the Screen Configurations. Add the populators for respective validations and mention the JSON path for the Pre-Process to work.
* IFSC code has 2 validations as below

1. Pattern validation (given in config)
2. Third-party API is being used to get band and branch name based on entered valid IFSC code, This API throws 404 if the code is not valid, in that case the valid error message is displayed below the input field
3. Tax identifiers are not mandatory while creating Organisation. But as per API implementation, it's required in payload hence we send a dummy identifier value if the user has not entered anything in Tax identifier input. In the View Organisation screen if the PAN value is above the dummy value then ‘NA’ is displayed on the screen.

* Default value: Type => PAN, Value => XXXXX0123X
