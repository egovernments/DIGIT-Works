import { Loader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useRouteMatch } from "react-router-dom";
import EmployeeApp from "./pages/employee";
import { CustomisedHooks } from "./hooks";
import WorksCard from "./components/WorksCard";
import { UICustomizations } from "./configs/UICustomizations";
import HRMSCard from "./components/HRMSCard";
import Captcha from "./components/Captcha";
import DSSCard from "./components/DSSCard";
import WorkbenchCard from "./components/WorkbenchCard";
import Jurisdictions from "./components/jurisdiction";

const MuktaModule = ({ stateCode, userType, tenants }) => {
  const moduleCode = ["Mukta"];
  const { path, url } = useRouteMatch();
  const language = Digit.StoreData.getCurrentLanguage();
  const { isLoading, data: store } = Digit.Services.useStore({
    stateCode,
    moduleCode,
    language,
  });

  if (isLoading) {
    return <Loader />;
  }

  return <EmployeeApp path={path} stateCode={stateCode} />;
};

const componentsToRegister = {
  MuktaModule,
  WorksCard,
  Captcha,
  DSSCard:null,  // TO HIDE THE DSS CARD IN HOME SCREEN as per MUKTA
  AttendenceMgmtCard:null , // TO HIDE THE Attendance Mgmt CARD IN HOME SCREEN as per MUKTA
  HRMSCard, // Overridden the HRMS card as per MUKTA
  MeasurementCard:null,
  WorkbenchCard,
  Jurisdictions,
  DSSCard
};

const overrideHooks = () => {
  Object.keys(CustomisedHooks).map((ele) => {
    if (ele === "Hooks") {
      Object.keys(CustomisedHooks[ele]).map((hook) => {
        Object.keys(CustomisedHooks[ele][hook]).map((method) => {
          setupHooks(hook, method, CustomisedHooks[ele][hook][method]);
        });
      });
    } else {
      Object.keys(CustomisedHooks[ele]).map((method) => {
        setupLibraries(ele, method, CustomisedHooks[ele][method]);
      });
    }
  });
};

/* To Overide any existing hook we need to use similar method */
const setupHooks = (HookName, HookFunction, method) => {
  window.Digit = window.Digit || {};
  window.Digit["Hooks"] = window.Digit["Hooks"] || {};
  window.Digit["Hooks"][HookName] = window.Digit["Hooks"][HookName] || {};
  window.Digit["Hooks"][HookName][HookFunction] = method;
};
/* To Overide any existing libraries  we need to use similar method */
const setupLibraries = (Library, service, method) => {
  window.Digit = window.Digit || {};
  window.Digit[Library] = window.Digit[Library] || {};
  window.Digit[Library][service] = method;
};

/* To Overide any existing config/middlewares  we need to use similar method */
const updateCustomConfigs = () => {
  setupLibraries("Customizations", "commonUiConfig", { ...window?.Digit?.Customizations?.commonUiConfig, ...UICustomizations });
};

export const initMuktaCustomisations = () => {
  overrideHooks();
  updateCustomConfigs();
  Object.entries(componentsToRegister).forEach(([key, value]) => {
    Digit.ComponentRegistryService.setComponent(key, value);
  });
};
