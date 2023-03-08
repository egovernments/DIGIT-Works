import { Loader } from "@egovernments/digit-ui-react-components";
import React, { useEffect } from "react";
import { useRouteMatch } from "react-router-dom";
import MastersCard from "./components/MastersCard";
import ProjectCard from "./components/ProjectCard";
import { default as EmployeeApp } from "./pages/employee";
import SearchMasters from "./pages/employee/Master/SearchMasters";
import CreateOrganization from "./pages/employee/Master/CreateOrganization";
import RegisterWageSeeker from "./pages/employee/registerWageSeeker/index";
import SearchOrganisation from "./pages/employee/SearchOrganisation";
import SearchWageSeeker from "./pages/employee/SearchWageSeeker";
import SearchMastersApplication from "./components/SearchMasters";
import ViewOrganisation from "./pages/employee/Master/ViewOrganisation";
import ViewWageSeeker from "./pages/employee/ViewWageSeeker";

export const MastersModule = ({ stateCode, userType, tenants }) => {

  const { path, url } = useRouteMatch();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const moduleCode = ["Masters","common-masters",tenantId];
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
  MasterCard:MastersCard ,
  ProjectsCard:ProjectCard,
  SearchMasters,
  CreateOrganization,
  RegisterWageSeeker,
  SearchMastersApplication,
  SearchWageSeeker,
  ViewOrganisation,
  SearchOrganisation,
  ViewWageSeeker
};

export const initMastersComponents = () => {
  Object.entries(componentsToRegister).forEach(([key, value]) => {
    Digit.ComponentRegistryService.setComponent(key, value);
  });
};
