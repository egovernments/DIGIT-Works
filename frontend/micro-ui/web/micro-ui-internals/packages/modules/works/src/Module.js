import { Loader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useRouteMatch } from "react-router-dom";
import WorksCard from "./components/WorksCard";
import EmployeeApp from "./pages/employee";
import SearchEstimateApplication from "./components/SearchEstimate";
import SearchApprovedSubEs from "./components/SearchApprovedSubEstimate";
const WorksModule = ({ stateCode, userType, tenants }) => {
  const moduleCode = ["works"];
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
  WorksCard,
  WorksModule,
  SearchEstimateApplication,
  SearchApprovedSubEs
};

export const initWorksComponents = () => {
  Object.entries(componentsToRegister).forEach(([key, value]) => {
    Digit.ComponentRegistryService.setComponent(key, value);
  });
};
