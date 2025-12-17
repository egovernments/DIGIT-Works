import { Loader } from "@egovernments/digit-ui-react-components";
import React, { useEffect } from "react";
import { useRouteMatch } from "react-router-dom";
import MastersCard from "./components/MastersCard";
import ProjectCard from "./components/ProjectCard";
import { default as EmployeeApp } from "./pages/employee";
import CreateMasters from "./pages/employee/Master/CreateMasters";
import SearchMasters from "./pages/employee/Master/SearchMasters";
import ViewMasters from "./pages/employee/Master/ViewMasters";
import SearchMastersApplication from "./components/SearchMasters";

import RegisterWageSeeker from "./pages/employee/WageSeeker/RegisterWageSeeker";
import SearchWageSeeker from "./pages/employee/WageSeeker/SearchWageSeeker";
import ViewWageSeeker from "./pages/employee/WageSeeker/ViewWageSeeker";
import ModifyWageSeeker from "./pages/employee/WageSeeker/ModifyWageSeeker/index";
import SearchWMSWageseeker from "./pages/employee/WageSeeker/SearchWMSWageseeker";

import CreateOrganisation from  "./pages/employee/Organisation/CreateOrganization/index";
import SearchOrganisation from "./pages/employee/Organisation/SearchOrganisation";
import ViewOrganisation from "./pages/employee/Organisation/ViewOrganisation";

import TransferCodeTable from "./components/TransferCodeTable";
import MastersResponse from "./components/MastersResponse";

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
  CreateMasters,
  SearchMasters,
  ViewMasters,
  SearchMastersApplication,
  
  RegisterWageSeeker,
  SearchWageSeeker,
  ViewWageSeeker,
  ModifyWageSeeker,
  SearchWMSWageseeker,

  CreateOrganisation,
  SearchOrganisation,
  ViewOrganisation,
  TransferCodeTable,
  MastersResponse
};

export const initMastersComponents = () => {
  Object.entries(componentsToRegister).forEach(([key, value]) => {
    Digit.ComponentRegistryService.setComponent(key, value);
  });
};
