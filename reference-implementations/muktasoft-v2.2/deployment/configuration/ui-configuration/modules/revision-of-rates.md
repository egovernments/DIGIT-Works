---
description: Revision of Rates feature frontend tech documentation
---

# Revision Of Rates

## Overview

The revision of Rates feature helps in revising the rates, if any changes in the SOR composition is done and the new works SOR rates has to be applied.

The feature has 2 associated screens :&#x20;

* Search SOR (Revision of Rates)
* View Jobs

## Configurations

### MDMS Configurations

<table><thead><tr><th width="103">S.No.</th><th width="275">Data</th><th>MDMS Link</th></tr></thead><tbody><tr><td>1</td><td>SOR Type</td><td><a href="https://mukta-uat.digit.org/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS-SOR&#x26;masterName=Type">SOR Type</a></td></tr><tr><td>2</td><td>SOR SubType</td><td><a href="https://mukta-uat.digit.org/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS-SOR&#x26;masterName=SubType">SOR SubType</a></td></tr><tr><td>3</td><td>SOR Varient</td><td><a href="https://mukta-uat.digit.org/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS-SOR&#x26;masterName=Variant">SOR Varient</a></td></tr></tbody></table>

### Module Enablement Configuration

This feature has been integrated with Rate analysis module, hence Please follow the document on Rate Analysis to understand this part.

### Role-action Mapping

<table><thead><tr><th width="93">S.No.</th><th width="268">API</th><th width="110">Action ID</th><th>Roles</th></tr></thead><tbody><tr><td>1</td><td>/rate-analysis/v1/scheduler/_create</td><td>464</td><td><p>MDMS_CITY_ADMIN</p><p><br></p></td></tr><tr><td>2</td><td>/rate-analysis/v1/scheduler/_search</td><td>465</td><td>MDMS_CITY_ADMIN</td></tr></tbody></table>

### Sidebar Configuration&#x20;

<table><thead><tr><th width="93">S.No.</th><th width="118">Screen</th><th width="197">Navigation URL</th><th width="190">Left Icon Updates</th><th>Roles</th></tr></thead><tbody><tr><td>1</td><td>Revision of Rates</td><td><p>/employee/rateAnalysis/search-sor</p><p><br></p></td><td><p>dynamic:ContractIcon</p><p><br></p></td><td><p>MDMS_CITY_ADMIN</p><p><br></p></td></tr></tbody></table>

Sample object for a sidebar action defined in MDMS (containing navigationUrl )

```
{
      "id": 194,
      "name": "MDMS",
      "url": "url",
      "displayName": "Manage Works Revision of Rates",
      "orderNumber": 1,
      "parentModule": "",
      "enabled": true,
      "serviceCode": "MDMS",
      "code": "null",
      "navigationURL": "/works-ui/employee/rateAnalysis/search-sor",
      "path": "9MDMS.WORKS-SOR.RevisionOfRates",
      "leftIcon": "dynamic:ContractIcon"
    }
```

### Screen Configuration&#x20;

<table><thead><tr><th width="93.66666666666666">S.No.</th><th width="245">Screen</th><th>Configuration</th></tr></thead><tbody><tr><td>1</td><td>Revision of Rates</td><td><a href="https://github.com/odisha-muktasoft/MUKTA_IMPL/blob/UAT/frontend/micro-ui/web/micro-ui-internals/packages/modules/RateAnalysis/src/configs/searchSORConfig.js">Search Sor</a></td></tr><tr><td>2</td><td>View Jobs</td><td><a href="https://github.com/odisha-muktasoft/MUKTA_IMPL/blob/UAT/frontend/micro-ui/web/micro-ui-internals/packages/modules/RateAnalysis/src/configs/ViewScheduledJobsConfig.js">View Jobs</a></td></tr></tbody></table>

### Localization Configuration & Modules

<table><thead><tr><th width="185">TenantID</th><th>Module</th></tr></thead><tbody><tr><td>pg</td><td>rainmaker-common</td></tr><tr><td>pg</td><td>rainmaker-rateanalysis</td></tr><tr><td>pg</td><td>rainmaker-common-masters</td></tr></tbody></table>

### Workflow Configuration

Revision of Rates do not have any workflow, here Users can search works type of SOR and proceed with two options

* Select few Works SORs and revise the rates for those by clicking on "**Revise Rate For Selected**"
* Revise rates for all Works type of SORs by clicking on "**Revise Rate For All**"

## Customisation

**Search SOR Screen:**&#x20;

* Use the common Utility “PreProcessMDMSConfig”.&#x20;
* Pass the associated dependencies for the config.
* The utility will return a config which will run through InboxSearchComposer.
* Refer to docs for Pre-Process config in Component comments.
* SOR Type, SOR SubType and SOR Vairent Loaded in the search screen are MDMS data.

**View Jobs Screen:**&#x20;

* Use the common Utility “PreProcessMDMSConfig”.&#x20;
* Pass the associated dependencies for the config.
* The utility will return a config which will run through InboxSearchComposer.
* Refer to docs for Pre-Process config in Component comments.

All the Inbox and Search screens throughout the app are rendered using this component [Inbox/Search Composer](https://github.com/egovernments/DIGIT-Works/blob/c2a234bb4b21f0e54ca9664ee3e99d72ce871168/frontend/micro-ui/web/micro-ui-internals/packages/react-components/src/hoc/InboxSearchComposer.js).
