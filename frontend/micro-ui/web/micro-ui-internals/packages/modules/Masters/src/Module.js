import { Loader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useRouteMatch } from "react-router-dom";
import MastersCard from "./components/MastersCard";
import ProjectCard from "./components/ProjectCard";
import { default as EmployeeApp } from "./pages/employee";
import SearchOrganization from "./pages/employee/Master/SearchOrganization";
import CreateOrganization from "./pages/employee/Master/CreateOrganization";
import RegisterWageSeeker from "./pages/employee/registerWageSeeker/index";

export const MastersModule = ({ stateCode, userType, tenants }) => {
  
  const moduleCode = ["Masters"];
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
  return <EmployeeApp path={path} stateCode={stateCode} userType={userType} tenants={tenants} />;
};

const componentsToRegister = {
  MastersModule,
  MastersCard,
  ProjectCard,
  SearchOrganization,
  CreateOrganization,
  RegisterWageSeeker,
};

export const initMastersComponents = () => {
  Object.entries(componentsToRegister).forEach(([key, value]) => {
    Digit.ComponentRegistryService.setComponent(key, value);
  });
};
