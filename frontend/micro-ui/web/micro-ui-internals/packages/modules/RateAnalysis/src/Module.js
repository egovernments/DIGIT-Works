import { Loader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useRouteMatch } from "react-router-dom";
import EmployeeApp from "./pages/employee";

import ViewRateAnalysis from "./pages/employee/ViewRateAnalysis";
import CreateRateAnalysis from "./pages/employee/CreateRateAnalysis";
import SORDetailsTemplate from "./components/SORDetailsTemplate";
import searchSor from "../../Estimate/src/pageComponents/searchSor";
import ExtraCharges from "./components/ExtraCharges";
import RateAmountGroup from "./components/rateAmountGroup";
import RateCardWithRightButton from "./components/ratecardbutton";
import TableWithOutHead from "./components/specficAmountTable";
import ViewTotalAmount from "./components/viewTotalAmount";
import ExtraChargesViewTable from "./components/extra_charges_view_table";
import RAResponseBanner from "./pages/employee/RAResponseBanner";
import ViewScheduledJobs from "./pages/employee/ViewScheduledJobs";
import SearchSOR from "./pages/employee/SearchSOR";

const RateAnalysisModule = ({ stateCode, userType, tenants }) => {
  const { path, url } = useRouteMatch();
  const language = Digit.StoreData.getCurrentLanguage();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const moduleCode = ["rateanalysis", "common-masters", "workflow", tenantId];
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
  ViewRateAnalysis,
  RateAnalysisModule,
  CreateRateAnalysis,
  SORDetailsTemplate,
  searchSor,
  ExtraCharges,
  RateAmountGroup,
  RateCardWithRightButton,
  TableWithOutHead,
  ViewTotalAmount,
 
  ExtraChargesViewTable,
  RAResponseBanner,

  ViewScheduledJobs,
  SearchSOR
};

export const initRateAnalysisComponents = () => {
  Object.entries(componentsToRegister).forEach(([key, value]) => {
    Digit.ComponentRegistryService.setComponent(key, value);
  });
};
