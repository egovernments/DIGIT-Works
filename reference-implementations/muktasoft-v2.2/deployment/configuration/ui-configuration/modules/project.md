---
description: Project module frontend tech documentation
---

# Project

## Overview

The Project module enables the Junior Engineer to capture the project details, search, view and modify the existing projects. This module is the initial step in starting the work.

The module has 4 associated screens :

1. Create
2. Search
3. Modify
4. View

## MDMS Configurations

<table><thead><tr><th width="94.99999999999997">#</th><th width="185">Data</th><th>MDMS Link</th></tr></thead><tbody><tr><td>1</td><td>Project Type</td><td>https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/ProjectType.json</td></tr><tr><td>2</td><td>Target Demography</td><td>https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/TargetDemography.json</td></tr><tr><td>3</td><td>ULB, City, Locality</td><td>https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/citya/egov-location/boundary-data.json</td></tr><tr><td><br></td><td><br></td><td>https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/cityb/egov-location/boundary-data.json</td></tr><tr><td><br></td><td><br></td><td>https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/cityc/egov-location/boundary-data.json</td></tr><tr><td>4</td><td>Document Config</td><td>https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/DocumentConfig.json</td></tr></tbody></table>

## Module Enablement Configuration&#x20;

Set module code as “Project” in Module.js of Project Module. Refer https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/tenant/citymodule.json.

```
 {
            "module": "Project",
            "code": "Project",
            "active": true,
            "order": 8,
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
},

```

Enable module in App.js. Update the object ‘enabledModules’. Use the code “Project”. This code should match the code of cityModule.json, mentioned above.

## PDF Configuration

The project created can be downloaded in PDF format from the View Project screen in the project module. The PDF contains the respective project descriptions, work details, and location details.

**PDF URL** : https://works-dev.digit.org/egov-pdf/download/project/project-details?projectId=\<project-Id>\&tenantId=\<tenant-Id>

## Role-action Mapping

<table><thead><tr><th width="90">S.No.</th><th width="247">API</th><th width="111">Action ID</th><th>Roles</th></tr></thead><tbody><tr><td>1</td><td><p>/pms/project/v1/_create</p><p><br></p></td><td><p>51</p><p><br></p></td><td><p>PROJECT_CREATOR</p><p><br></p></td></tr><tr><td>2</td><td>/pms/project/v1/_search</td><td>52</td><td>PROJECT_VIEWER</td></tr><tr><td>3</td><td>/pms/project/v1/_update</td><td>52</td><td>PROJECT_CREATOR</td></tr></tbody></table>

## **Sidebar Configuration**&#x20;

<table><thead><tr><th width="89">S.No.</th><th width="104">Screen</th><th>Navigation URL</th><th width="125">Left-icon Updates</th><th>Roles</th></tr></thead><tbody><tr><td>1</td><td>Create Project</td><td><p>/works-ui/employee/project/create-project</p><p><br></p></td><td><p>dynamic:ProjectIcon</p><p><br></p></td><td><p>PROJECT_CREATOR</p><p><br></p></td></tr><tr><td>2</td><td>Search Project</td><td>/works-ui/employee/project/search-project</td><td><p>dynamic:ProjectIcon</p><p><br></p></td><td>PROJECT_VIEWER</td></tr></tbody></table>

### Sample Configuration

```
 {
     "id": 15,
     "name": "PROJECT",
     "url": "url",
     "displayName": "Create Project",
     "orderNumber": 1,
     "parentModule": "",
     "enabled": true,
     "serviceCode": "PROJECT",
     "code": "null",
     "navigationURL": "/works-ui/employee/project/create-project",
     "path": "1PROJECT.Create",
     "leftIcon": "dynamic:ProjectIcon"
  }

```

## Screen Configuration&#x20;

<table><thead><tr><th width="88.66666666666666">S.No.</th><th width="185">Screen</th><th>Configuration</th></tr></thead><tbody><tr><td>1</td><td>Create / Update</td><td>https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/commonMuktaUiConfig/CreateProjectConfig.json</td></tr><tr><td>2</td><td>Search</td><td>https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/commonMuktaUiConfig/SearchProjectConfig.json</td></tr></tbody></table>

## Localisation Configuration & Modules

<table><thead><tr><th width="205">TenantID</th><th>Module</th></tr></thead><tbody><tr><td>pg</td><td>rainmaker-project</td></tr><tr><td>pg</td><td>rainmaker-common-masters</td></tr><tr><td>pg</td><td>rainmaker-common</td></tr><tr><td>pg.citya</td><td>rainmaker-pg.citya</td></tr></tbody></table>

## &#x20;Workflow Configuration

&#x20; NA

## Customisation

**Create Screen:**&#x20;

1. Use the common Utility “PreProcessMDMSConfig”.&#x20;
2. Pass the associated dependencies for the config.
3. The utility returns a config that will run through the FormComposer.
4. Refer to docs for pre-process config in component comments.

&#x20; **Search Screen:**&#x20;

1. Use the common utility “preProcessMDMSConfigInboxSearch”.
2. Pass the associated dependencies for the config.
3. The utility returns a config that will run through the RenderFormFields.
4. Refer to docs for pre-process config in component comments.

&#x20;  **Custom Components Used in Projects:**&#x20;

1. Document Configuration - Based on the [document config](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/DocumentConfig.json), the required documents will be rendered on the Create Screen. Ensure to specify the validations, file type, and file size limits in this configuration.
2. &#x20;Form Validations: Verify that all necessary form validations are included in the screen configurations. Add populators for respective validations and specify the JSON path to enable the pre-process function.
