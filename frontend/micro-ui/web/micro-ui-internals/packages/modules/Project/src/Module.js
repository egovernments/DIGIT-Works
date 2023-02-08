import { Loader } from "@egovernments/digit-ui-react-components";
import React, { useEffect } from "react";
import { useRouteMatch } from "react-router-dom";
import ProjectCard from "./components/ProjectCard";
import SubProjectDetailsTable from "./components/SubProjectDetailsTable";
import ViewFinancialDetails from "./pageComponents/ViewFinancialDetails";
import { default as EmployeeApp } from "./pages/employee";
import CreateProjectForm from "./pages/employee/CreateProject/CreateProjectForm";
import CreateProjectResponse from "./pages/employee/CreateProject/CreateProjectResponse";
import CreateProject from "./pages/employee/CreateProject/index";
import ProjectDetails from "./pages/employee/ProjectDetails";
import ProjectSearch from "./pages/employee/ProjectSearch";
import ProjectSearchAndInbox from "./pages/employee/ProjectSearchAndInbox";

export const ProjectModule = ({ stateCode, userType, tenants }) => {
  const moduleCode = ["Project"];
  const { path, url } = useRouteMatch();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const language = Digit.StoreData.getCurrentLanguage();
  const { isLoading, data: store } = Digit.Services.useStore({
    stateCode,
    moduleCode,
    language,
  });

  useEffect(() => {
    Digit.LocalizationService.getLocale({
      modules: [`rainmaker-${tenantId}`],
      locale: language,
      tenantId: stateCode,
    })
  }, [])

  if (isLoading) {
    return <Loader />;
  }
  return <EmployeeApp path={path} stateCode={stateCode} userType={userType} tenants={tenants} />;
};

const componentsToRegister = {
    ProjectModule,
    ProjectsCard :ProjectCard,
    CreateProject,
    ProjectDetails,
    ViewFinancialDetails,
    ProjectSearch,
    ProjectSearchAndInbox,
    SubProjectDetailsTable,
    CreateProjectResponse,
    CreateProjectForm
};

export const initProjectComponents = () => {
  Object.entries(componentsToRegister).forEach(([key, value]) => {
    Digit.ComponentRegistryService.setComponent(key, value);
  });
};
