<!-- TODO: update this -->

# digit-ui-module-utilities

## Install

```bash
npm install --save digit-ui-module-utilities
```

## Limitation

```bash
This Package is more specific to DIGIT-UI's can be used across mission's
```

## Usage

After adding the dependency make sure you have this dependency in

```bash
frontend/micro-ui/web/package.json
```

```json
"@egovernments/digit-ui-module-utilities":"0.0.1",
```

then navigate to App.js

```bash
 frontend/micro-ui/web/src/App.js
```

```jsx
/** add this import **/

import { initUtilitiesComponents } from "@egovernments/digit-ui-module-utilities";

/** inside enabledModules add this new module key **/

const enabledModules = ["Utilities"];

/** inside init Function call this function **/

const initDigitUI = () => {
  initUtilitiesComponents();
};
```

In MDMS

_Add this configuration to enable this module [MDMS Enableing Utilities Module](https://github.com/egovernments/works-mdms-data/blob/48461ecaf944ea243e24e1c1f9a5e2179d8091ac/data/pg/tenant/citymodule.json#L193)_

## List of Screens available in this versions were as follows

1. Search or Inbox
   Example Routes as follows

   ```bash
   works-ui/employee/utilities/search/commonMuktaUiConfig/SearchEstimateConfig
   ```

2. Iframe

   ```bash
   works-ui/employee/utilities/iframe/shg/home
   ```

3. Workflow Test for any module

Sample URL

_Contract Module

```bash
  works-ui/employee/dss/workflow?tenantId=pg.citya&applicationNo=WO/2022-23/000375&businessService=contract-approval-mukta&moduleCode=contract
```

_Estimate Module

```bash
  works-ui/employee/dss/workflow?tenantId=pg.citya&applicationNo=ES/2022-23/000874&businessService=mukta-estimate&moduleCode=estimate
```

_Attendance Module

```bash
  works-ui/employee/dss/workflow?tenantId=pg.citya&applicationNo=MR/2022-23/03/19/000631&businessService=muster-roll-approval&moduleCode=attendence
```


## Coming Soon

1. Create Screen
2. View Screen


## License

## Published from Works PFM Mission

MIT © [jagankumar-egov](https://github.com/jagankumar-egov)
````
