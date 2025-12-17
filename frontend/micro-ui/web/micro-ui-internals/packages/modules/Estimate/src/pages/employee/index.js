import React, { useEffect } from "react";
import { useTranslation } from "react-i18next";
import { Switch, useLocation } from "react-router-dom";
import { PrivateRoute, AppContainer } from "@egovernments/digit-ui-react-components";
import { BreadCrumb } from "@egovernments/digit-ui-components";
import CreateEstimate from "./Estimates/CreateEstimate/CreateEstimate";
import EstimateSearch from "./EstimateSearch";
import EstimateSearchPlain from "./EstimateSearchPlain";
import EstimateInbox from "./EstimateInbox";
import ViewEstimate from "./ViewEstimate";
import EstimateResponse from "./Estimates/CreateEstimate/EstimateResponse";
import CreateDetailedEstimate from "./Estimates/CreateDetailedEstimate/CreateEstimate";
import UpdateDetailedEstimate from "./Estimates/CreateDetailedEstimate/UpdateDetailedEstimate";
import CreateRevisionDetailedEstimate from "./Estimates/CreateDetailedEstimate/CreateRevisionDetailedEstimate";
import UpdateRevisionDetailedEstimate from "./Estimates/CreateDetailedEstimate/UpdateRevisionDetailedEstimate";
import ViewAnalysisStatementPage from "./ViewAnalysisStatementPage";

import ViewDetailedEstimate from "./ViewDetailedEstimate";

const EstimateBreadCrumbs = ({ location }) => {
  const { t } = useTranslation();

  const search = useLocation().search;
  const fromScreen = new URLSearchParams(search).get("from") || null;

  const crumbs = [
    {
      path: `/${window?.contextPath}/employee`,
      content: t("WORKS_MUKTA"),
      show: true,
    },
    {
      path: `/${window.contextPath}/employee/expenditure/billinbox`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_BILLING_MGMT")}` : t("WORKS_BILLING_MGMT"),
      show: location.pathname.includes("/expenditure/billinbox") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/estimate/create-estimate`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_CREATE_ESTIMATE")}` : t("WORKS_CREATE_ESTIMATE"),
      show: location.pathname.includes("/estimate/create-estimate") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/estimate/create-detailed-estimate`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_CREATE_DETAILED_ESTIMATE")}` : t("WORKS_CREATE_DETAILED_ESTIMATE"),
      show: location.pathname.includes("/estimate/create-detailed-estimate") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/estimate/update-detailed-estimate`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_UPDATE_DETAILED_ESTIMATE")}` : t("WORKS_UPDATE_DETAILED_ESTIMATE"),
      show: location.pathname.includes("/estimate/update-detailed-estimate") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/estimate/create-revision-detailed-estimate`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_CREATE_REVISION_DETAILED_ESTIMATE")}` : t("WORKS_CREATE_REVISION_DETAILED_ESTIMATE"),
      show: location.pathname.includes("/estimate/create-revision-detailed-estimate") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/estimate/update-revision-detailed-estimate`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_UPDATE_REVISION_DETAILED_ESTIMATE")}` : t("WORKS_UPDATE_REVISION_DETAILED_ESTIMATE"),
      show: location.pathname.includes("/estimate/update-revision-detailed-estimate") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/estimate/search-estimate`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_SEARCH_ESTIMATES")}` : t("WORKS_SEARCH_ESTIMATES"),
      show: location.pathname.includes("/estimate/search-estimate") ? true : false,
      isBack: fromScreen && true,
    },
    {
      // path: `/${window.contextPath}/employee/estimate/response`,
      content: fromScreen ? `${t(fromScreen)} / ${t("ES_COMMON_RESPONSE")}` : t("ES_COMMON_RESPONSE"),
      show: location.pathname.includes("/estimate/response") ? true : false,
      isBack: fromScreen && true,
    },
    {
      // path: `/${window.contextPath}/employee/estimate/inbox`,
      content: fromScreen ? `${t(fromScreen)} / ${t("ESTIMATE_VIEW_ESTIMATE")}` : t("ESTIMATE_VIEW_ESTIMATE"),
      show: location.pathname.includes("/estimate/estimate-details") ? true : false,
      isBack: fromScreen && true,
    },
    {
      // path: `/${window.contextPath}/employee/estimate/inbox`,
      content: fromScreen ? `${t(fromScreen)} / ${t("ESTIMATE_VIEW_ESTIMATE")}` : t("ESTIMATE_VIEW_ESTIMATE"),
      show: location.pathname.includes("/estimate/view-estimate") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/estimate/inbox`,
      content: fromScreen ? `${t(fromScreen)} / ${t("ACTION_TEST_ESTIMATE_INBOX")}` : t("ACTION_TEST_ESTIMATE_INBOX"),
      show: location.pathname.includes("/estimate/inbox") ? true : false,
      isBack: fromScreen && true,
    },
    {
      // path: `/${window.contextPath}/employee/estimate/inbox`,
      content: fromScreen ? `${t(fromScreen)} / ${t("ESTIMATE_VIEW_ANALYSIS_STATEMENT")}` : t("ESTIMATE_VIEW_ANALYSIS_STATEMENT"),
      show: location.pathname.includes("/estimate/view-analysis-statement") ? true : false,
      isBack: fromScreen && true,
    },
  ];
  return (
    <BreadCrumb
      crumbs={crumbs}
      zerothStyle={
        location.pathname.includes("/estimate/inbox") || location.pathname.includes("/estimate/search-estimate") ? {} : {}
      }
    />
  );
};

const App = ({ path }) => {
  const EstimateSession = Digit.Hooks.useSessionStorage("NEW_ESTIMATE_CREATE", {});
  const [sessionFormData, clearSessionFormData] = EstimateSession;

  const location = useLocation();
  const locationCheck = window.location.href.includes("/employee/ws/new-application");
  const getBreadCrumbStyles = (screenType) => {
    // Defining 4 types for now -> create,view,inbox,search
    switch (true) {
      // case (screenType?.includes(`/${window?.contextPath}/employee/estimate/create-estimate`)):
      //     return { marginLeft: "0px" }
      case screenType?.includes("/create"):
        return { marginLeft: "10px" };

      case screenType?.includes("/view"):
        return { marginLeft: "4px" };

      case screenType?.includes("/search"):
        return { marginLeft: "7px" };
      case screenType?.includes("/inbox") || screenType?.includes("/inbox"):
        return { marginLeft: "5px" };

      default:
        return { marginLeft: "8px" };
    }
  };

  // remove session form data if user navigates away from the estimate create screen
  useEffect(() => {
    if (!window.location.href.includes("create-estimate") && sessionFormData && Object.keys(sessionFormData) != 0) {
      clearSessionFormData();
    }
  }, [location]);

  return (
    <Switch>
      <AppContainer>
        <React.Fragment>
          <EstimateBreadCrumbs location={location} />
        </React.Fragment>
        <PrivateRoute path={`${path}/create-estimate`} component={() => <CreateEstimate {...{ path }} />} />
        <PrivateRoute path={`${path}/create-detailed-estimate`} component={() => <CreateDetailedEstimate {...{ path }} />} />
        <PrivateRoute path={`${path}/search-estimate`} component={() => <EstimateSearch {...{ path }} />} />
        <PrivateRoute path={`${path}/search-estimate-plain`} component={() => <EstimateSearchPlain {...{ path }} />} />
        <PrivateRoute path={`${path}/inbox`} component={() => <EstimateInbox {...{ path }} />} />
        <PrivateRoute path={`${path}/view-estimate`} component={() => <ViewEstimate {...{ path }} />} />
        <PrivateRoute path={`${path}/estimate-details`} component={() => <ViewDetailedEstimate {...{ path }} />} />
        <PrivateRoute path={`${path}/update-detailed-estimate`} component={() => <UpdateDetailedEstimate {...{ path }} />} />
        <PrivateRoute path={`${path}/create-revision-detailed-estimate`} component={() => <CreateRevisionDetailedEstimate {...{ path }} />} />
        <PrivateRoute path={`${path}/update-revision-detailed-estimate`} component={() => <UpdateRevisionDetailedEstimate {...{ path }} />} />
        <PrivateRoute path={`${path}/view-analysis-statement`} component={() => <ViewAnalysisStatementPage {...{ path }} />} />
        <PrivateRoute path={`${path}/response`} component={() => <EstimateResponse {...{ path }} />} />
      </AppContainer>
    </Switch>
  );
};

export default App;