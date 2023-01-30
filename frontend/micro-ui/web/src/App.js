import React from "react";

// import { initDSSComponents } from "@egovernments/digit-ui-module-dss";
// import { initEngagementComponents } from "@egovernments/digit-ui-module-engagement";
import { initWorksComponents } from "@egovernments/digit-ui-module-works";
import { initAttendenceMgmtComponents } from "@egovernments/digit-ui-module-attendencemgmt";
import { initExpenditureComponents } from "@egovernments/digit-ui-module-expenditure";
import { initContractsComponents } from "@egovernments/digit-ui-module-contracts";
import { initMastersComponents } from "@egovernments/digit-ui-module-masters";
import { initEstimateComponents } from "@egovernments/digit-ui-module-estimate";
import { DigitUI } from "@egovernments/digit-ui-module-core";
import { initLibraries } from "@egovernments/digit-ui-libraries";
import { initProjectComponents } from "@egovernments/digit-ui-module-project";
import { initHRMSComponents } from "@egovernments/digit-ui-module-hrms";


window.contextPath = window?.globalConfigs?.getConfig("CONTEXT_PATH");

initLibraries();

const enabledModules = [
  // "PGR",
  // "FSM",
  // "Payment",
  // "PT",
  // "QuickPayLinks",
  // "DSS",
  // "NDSS",
  // "MCollect",
  // "HRMS",
  // "TL",
  // "Receipts",
  // "OBPS",
  // "NOC",
  // "Engagement",
  // "CommonPT",
  // "WS",
  // "Reports",
  // "Bills",
  "HRMS",
  "Works",
  "AttendenceMgmt",
  "Contracts",
  "Expenditure",
  "Masters",
  "Estimate",
  "Project",
];
window.Digit.ComponentRegistryService.setupRegistry({});

// initDSSComponents();
// initEngagementComponents();
initWorksComponents();
initHRMSComponents();
initEstimateComponents();
initAttendenceMgmtComponents();
initContractsComponents();
initExpenditureComponents();
initMastersComponents();
initProjectComponents();

const moduleReducers = (initData) => ({
  initData,
});

function App() {
  window.contextPath = window?.globalConfigs?.getConfig("CONTEXT_PATH");
  const stateCode =
    window.globalConfigs?.getConfig("STATE_LEVEL_TENANT_ID") ||
    process.env.REACT_APP_STATE_LEVEL_TENANT_ID;
  if (!stateCode) {
    return <h1>stateCode is not defined</h1>;
  }
  return (
    <DigitUI
      stateCode={stateCode}
      enabledModules={enabledModules}
      moduleReducers={moduleReducers}
    />
  );
}

export default App;
