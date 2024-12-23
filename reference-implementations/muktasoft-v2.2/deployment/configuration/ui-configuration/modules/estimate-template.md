---
description: Estimate Template feature frontend tech documentation
---

# Estimate Template

## Overview

The Estimate Template feature helps in creating a template for particular work, so it can be directly taken refrence of or added by user in estimate creation. This feature has been incorporated in the estimate create screen where it can searched and added.

## Configurations

### MDMS Configurations

<table><thead><tr><th width="103">S.No.</th><th width="263">Data</th><th>MDMS Link</th></tr></thead><tbody><tr><td>1</td><td>Estimate Template</td><td><a href="https://mukta-uat.digit.org/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS&#x26;masterName=EstimateTemplate">Estimate Template</a></td></tr></tbody></table>

### Module Enablement Configuration

Estimate Template is a part of mdms-v2 data, so In order to see the changes in the UI, both Estimate module and Workbench module has to be enabled in the file CityModule.json

### Role-action Mapping

<table><thead><tr><th width="93">S.No.</th><th width="268">API</th><th width="110">Action ID</th><th>Roles</th></tr></thead><tbody><tr><td>1</td><td>/mdms-v2/v2/_create/WORKS.EstimateTemplate</td><td>9</td><td><p>MDMS_STATE_ADMIN</p><p><br></p></td></tr><tr><td>2</td><td>/mdms-v2/v2/_update/WORKS.EstimateTemplate</td><td>10</td><td>MDMS_STATE_ADMIN</td></tr></tbody></table>

### Sidebar Configuration&#x20;

<table><thead><tr><th width="93">S.No.</th><th width="118">Screen</th><th width="197">Navigation URL</th><th width="190">Left Icon Updates</th><th>Roles</th></tr></thead><tbody><tr><td>1</td><td>Estimate Template</td><td><p>/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS&#x26;masterName=EstimateTemplate</p><p><br></p></td><td><p>dynamic:ContractIcon</p><p><br></p></td><td><p>MDMS_STATE_ADMIN</p><p><br></p></td></tr></tbody></table>

Sample object for a sidebar action defined in MDMS (containing navigationUrl )

{% code lineNumbers="true" %}
```
{
      "id": 193,
      "name": "MDMS",
      "url": "url",
      "displayName": "Manage Works Estimate Template",
      "orderNumber": 1,
      "parentModule": "",
      "enabled": true,
      "serviceCode": "MDMS",
      "code": "null",
      "navigationURL": "/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS&masterName=EstimateTemplate",
      "path": "9MDMS.WORKS-SOR.EstimateTemplate",
      "leftIcon": "dynamic:ContractIcon"
}
```
{% endcode %}

### Localization Configuration & Modules

<table><thead><tr><th width="185">TenantID</th><th>Module</th></tr></thead><tbody><tr><td>od</td><td>rainmaker-common</td></tr><tr><td>od</td><td>rainmaker-estimate</td></tr><tr><td>od</td><td>rainmaker-workbench</td></tr></tbody></table>

## Customisation

&#x20;**Custom Components used for Estimate Template:**

searchTemplate

* This component is used to make a search for eatimate template already stored in the system and add the template for the creation of estimate
* It handles the validation corresponding to adding of SORs and Non SORs in the estimate create.
* Pass this component in the config of formcomp in order to render it in the create form&#x20;

