import React from "react";
import ReactDOM from "react-dom";

import { initLibraries } from "@egovernments/digit-ui-libraries";
// import { PGRReducers } from "@egovernments/digit-ui-module-pgr";
import { initDSSComponents } from "@egovernments/digit-ui-module-dss";

// import { initPGRComponents } from "@egovernments/digit-ui-module-pgr";
// import { initEngagementComponents } from "@egovernments/digit-ui-module-engagement";
// import { initWorksComponents } from "@egovernments/digit-ui-module-works";
import { initAttendenceMgmtComponents } from "@egovernments/digit-ui-module-attendencemgmt";
import { initExpenditureComponents } from "@egovernments/digit-ui-module-expenditure";
import { initEstimateComponents } from "@egovernments/digit-ui-module-estimate";
import { initContractsComponents } from "@egovernments/digit-ui-module-contracts";
import { DigitUI } from "@egovernments/digit-ui-module-core";
import { initHRMSComponents } from "@egovernments/digit-ui-module-hrms";
import { initMastersComponents } from "@egovernments/digit-ui-module-masters";
import { initProjectComponents } from  "@egovernments/digit-ui-module-project";
import "@egovernments/digit-ui-works-css/example/index.css";
import {initMuktaCustomisations} from "@egovernments/digit-ui-customisation-mukta";

// import * as comps from "@egovernments/digit-ui-react-components";

// import { subFormRegistry } from "@egovernments/digit-ui-libraries";

import { pgrCustomizations  } from "./pgr";
import { UICustomizations } from "./UICustomizations";
var Digit = window.Digit || {};

const enabledModules = [
  "Works",
  "HRMS",
  "AttendenceMgmt",
  "Contracts",
  "Expenditure",
  "Masters",
  "Estimate",
  "Project",
  "Mukta",
  "DSS"
  // "Engagement"
];

const initTokens = (stateCode) => {
  const userType = window.sessionStorage.getItem("userType") || process.env.REACT_APP_USER_TYPE || "CITIZEN";
  const token = window.localStorage.getItem("token") || process.env[`REACT_APP_${userType}_TOKEN`];

  const citizenInfo = window.localStorage.getItem("Citizen.user-info");

  const citizenTenantId = window.localStorage.getItem("Citizen.tenant-id") || stateCode;

  const employeeInfo = window.localStorage.getItem("Employee.user-info");
  const employeeTenantId = window.localStorage.getItem("Employee.tenant-id");

  const userTypeInfo = userType === "CITIZEN" || userType === "QACT" ? "citizen" : "employee";
  window.Digit.SessionStorage.set("user_type", userTypeInfo);
  window.Digit.SessionStorage.set("userType", userTypeInfo);

  if (userType !== "CITIZEN") {
    window.Digit.SessionStorage.set("User", { access_token: token, info: userType !== "CITIZEN" ? JSON.parse(employeeInfo) : citizenInfo });
  } else {
    // if (!window.Digit.SessionStorage.get("User")?.extraRoleInfo) window.Digit.SessionStorage.set("User", { access_token: token, info: citizenInfo });
  }

  window.Digit.SessionStorage.set("Citizen.tenantId", citizenTenantId);

  if (employeeTenantId && employeeTenantId.length) window.Digit.SessionStorage.set("Employee.tenantId", employeeTenantId);
};

const initDigitUI = () => {
  window.contextPath = window?.globalConfigs?.getConfig("CONTEXT_PATH");

  window?.Digit.ComponentRegistryService.setupRegistry({
    // ...pgrComponents,
  });

  // initPGRComponents();
  initDSSComponents();
  initEstimateComponents();
  // initEngagementComponents();
  // initWorksComponents();
  initAttendenceMgmtComponents();
  initHRMSComponents();
  initContractsComponents();
  initExpenditureComponents();
  initMastersComponents();
  initProjectComponents();

  const moduleReducers = (initData) => initData;

  window.Digit.Customizations = {
    PGR: pgrCustomizations,
    TL: {
      customiseCreateFormData: (formData, licenceObject) => licenceObject,
      customiseRenewalCreateFormData: (formData, licenceObject) => licenceObject,
      customiseSendbackFormData: (formData, licenceObject) => licenceObject,
    },
    commonUiConfig: UICustomizations
  };
  const registry = window?.Digit.ComponentRegistryService.getRegistry();

  const stateCode = window?.globalConfigs?.getConfig("STATE_LEVEL_TENANT_ID") || "pb";
  initTokens(stateCode);
  initMuktaCustomisations();
  ReactDOM.render(<DigitUI stateCode={stateCode} enabledModules={enabledModules} moduleReducers={moduleReducers} defaultLanding="employee" />, document.getElementById("root"));
};

initLibraries().then(() => {
  initDigitUI();
});
