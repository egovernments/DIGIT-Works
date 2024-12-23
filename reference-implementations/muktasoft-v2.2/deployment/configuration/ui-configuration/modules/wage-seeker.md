---
description: Wage seeker configuration details
---

# Wage Seeker

## Overview

This module helps in creating an individual (Wage Seeker), it is needed in Mukta to assign work, track attendance and process DBTs.

This module has 3  associated screens :&#x20;

1. Search
2. View
3. Modify

## MDMS Configurations

<table><thead><tr><th width="98.99999999999997">S.No.</th><th width="188">Data</th><th>MDMS Link</th></tr></thead><tbody><tr><td>1</td><td>Relationship</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/Relationship.json">https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/Relationship.json</a></td></tr><tr><td>2</td><td>GenderType</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/GenderType.json">https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/GenderType.json</a></td></tr><tr><td>3</td><td>SocialCategory</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/SocialCategory.json">https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/SocialCategory.json</a></td></tr><tr><td>4</td><td>WageSeekerSkills</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/WageSeekerSkills.json">https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/common-masters/WageSeekerSkills.json</a></td></tr><tr><td>5</td><td>TenantBoundary</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/8285bc63aac7f056326165897ac18918520c9723/data/pg/citya/egov-location/boundary-data.json">https://github.com/egovernments/works-mdms-data/blob/8285bc63aac7f056326165897ac18918520c9723/data/pg/citya/egov-location/boundary-data.json</a></td></tr></tbody></table>

### Module Enablement Configuration&#x20;

1. Set module code as “Masters” in Module.js for the Masters (Organization) Module. Refer [CityModule.json](https://github.com/egovernments/works-mdms-data/blob/481752ba70aa29d235967d8ba6080685d897324a/data/pg/tenant/citymodule.json)

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

2. Enable Module in App.js. Update the object ‘enabledModules’. Use the code “Masters”. This code should match the code of the cityModule.json, mentioned above.

### Role-action Mapping

<table><thead><tr><th width="82">S.No.</th><th>API</th><th width="147">Action ID</th><th>Roles</th></tr></thead><tbody><tr><td>1</td><td><p>/individual/v1/_search</p><p><br></p></td><td>71</td><td>MUKTA_ADMIN,<br>ORG_ADMIN</td></tr><tr><td>2</td><td>/individual/v1/_update</td><td>72</td><td>MUKTA_ADMIN,<br>ORG_ADMIN</td></tr><tr><td>3</td><td>/individual/v1/_delete</td><td>74</td><td>MUKTA_ADMIN,<br>ORG_ADMIN</td></tr></tbody></table>

### Sidebar Configuration&#x20;

<table><thead><tr><th width="83">S.No.</th><th>Screen</th><th>Navigation URL</th><th>Left Icon Updates</th><th>Roles</th></tr></thead><tbody><tr><td>1</td><td>Search Individual</td><td>/works-ui/employee/masters/search-wageseeker</td><td>dynamic:WageseekerIcon</td><td>MUKTA_ADMIN</td></tr></tbody></table>

Sample object for a sidebar action defined in MDMS (containing the navigation Url)

```
{
      "id": 78,
      "name": "WAGESEEKER",
      "url": "url",
      "displayName": "Masters Search Wageseeker",
      "orderNumber": 10,
      "parentModule": "",
      "enabled": true,
      "serviceCode": "WageSeeker",
      "code": "null",
      "navigationURL": "/works-ui/employee/masters/search-wageseeker",
      "path": "8WageSeeker.Search",
      "leftIcon": "dynamic:WageseekerIcon"
    }

```

### Screen Configuration&#x20;

<table><thead><tr><th width="97.99999999999997">S.No.</th><th>Screen</th><th>Configuration</th></tr></thead><tbody><tr><td>1</td><td>Create/Modify</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/commonMuktaUiConfig/CreateWageSeekerConfig.json">Modify Individual Config</a></td></tr><tr><td>2</td><td>Search</td><td><a href="https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/commonMuktaUiConfig/SearchIndividualConfig.json">Search Individual Config</a></td></tr></tbody></table>

### Localization Configuration & Modules

<table><thead><tr><th width="236">TenantID</th><th>Module</th></tr></thead><tbody><tr><td>pg</td><td>rainmaker-masters</td></tr><tr><td>pg</td><td>rainmaker-common-masters</td></tr><tr><td>pg</td><td>rainmaker-common</td></tr><tr><td>pg.citya</td><td>rainmaker-pg.citya</td></tr></tbody></table>

### &#x20;Workflow Configuration

&#x20;  NA

## Customization

1. Modify Individual Screen
   * Use the common Utility “PreProcessMDMSConfig”.&#x20;
   * Pass the associated dependencies for the config.
   * The utility will return a config which will run through FormComposer.
   * Refer to docs for Pre-Process configuration of Component comments.
2. Search Individual Screen
   * Use the common utility “preProcessMDMSConfigInboxSearch”.
   * Pass the associated dependencies for the config.
   * The utility will return a config which will run through RenderFormFields.
   * Refer to docs for Pre-Process configuration of Component comments.
   * All the Inbox and Search screens throughout the app are rendered using the component [Inbox/Search Composer](https://github.com/egovernments/DIGIT-Works/blob/c2a234bb4b21f0e54ca9664ee3e99d72ce871168/frontend/micro-ui/web/micro-ui-internals/packages/react-components/src/hoc/InboxSearchComposer.js)
3. Validations added&#x20;
   * Form validations are integrated within the Screen Configurations. To facilitate this, include the appropriate populators for the respective validations. Additionally, make sure to specify the JSON path necessary for the pre-processing to function correctly.
4. Delete API Usage
   * To modify existing skills, the "deleteIndividual" and "update Individual" APIs are utilized.
   * If skills are already present, they can be updated using the "update" API. If any skills need to be removed, the "delete" API is used. In the case of skill removal, the entire skill object should be sent with the 'isDeleted' flag set to true.
