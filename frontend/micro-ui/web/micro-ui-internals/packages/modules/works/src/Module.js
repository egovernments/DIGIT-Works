import { Loader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useRouteMatch } from "react-router-dom";
import WorksCard from "./components/WorksCard";
import EmployeeApp from "./pages/employee";
import WORKS_INBOX_FILTER from './components/inbox/NewInboxFilter'
import LOI_INBOX_FILTER from "./components/LOIInbox/LOIInboxFilter";
import WORKSContractorDetails from "./pageComponents/WORKSContractorDetails";
import WORKSContractorTable from "./pageComponents/WORKSContractorTable";
import SearchEstimate from './components/SearchEstimate'
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
  LOI_INBOX_FILTER,
  WORKS_INBOX_FILTER,
  WORKSContractorTable,
  WORKSContractorDetails,
  SearchEstimate,
};

export const initWorksComponents = () => {
  Object.entries(componentsToRegister).forEach(([key, value]) => {
    Digit.ComponentRegistryService.setComponent(key, value);
  });
};
