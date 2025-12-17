import React, { useEffect } from "react";
import { useTranslation } from "react-i18next";
import { PrivateRoute, BreadCrumb } from "@egovernments/digit-ui-react-components";
import { Switch, useLocation } from "react-router-dom";
import ViewRateAnalysis from "./ViewRateAnalysis";
import CreateRateAnalysis from "./CreateRateAnalysis";
import RAResponseBanner from "./RAResponseBanner";
import ViewScheduledJobs from "./ViewScheduledJobs";
import SearchSOR from "./SearchSOR";

const RateAnalysisBreadCumbs = ({ location }) => {
  const { t } = useTranslation();
  const search = useLocation().search;
  const fromScreen = new URLSearchParams(search).get("from") || null;
  
  const crumbs = [
    {
      path: `/${window?.contextPath}/employee`,
      content: t("WORKS_MUKTA"),
      show: location.pathname.includes("response") ? false : true,
    },
    {
      path: `/${window.contextPath}/employee/rateanalysis/create-rate-analysis`,
      content: fromScreen ? `${t(fromScreen)} / ${t("RA_CREATE_RA")}` : t("RA_CREATE_RA"),
      show: location.pathname.includes("/rateanalysis/create-rate-analysis") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/rateAnalysis/view-rate-analysis`,
      content: fromScreen ? `${t(fromScreen)} / ${t("RA_VIEW_RATE_HEADER")}` : t("RA_VIEW_RATE_HEADER"),
      show: location.pathname.includes("/view-rate-analysis") ? true : false,
      isBack: true,
    },
    {
      path: `/${window.contextPath}/employee/rateAnalysis/update-rate-analysis`,
      content: fromScreen ? `${t(fromScreen)} / ${t("RA_UPDATE_RATE_HEADER")}` : t("RA_UPDATE_RATE_HEADER"),
      show: location.pathname.includes("/update-rate-analysis") ? true : false,
      isBack: true,
    },
    {
      path: `/${window.contextPath}/employee/rateAnalysis/search-sor`,
      content: t("RA_SEARCH_SOR"),
      show: location.pathname.includes("/search-sor") ? true : false,
      isBack: true,
    },
    {
      path: `/${window.contextPath}/employee/rateAnalysis/view-scheduled-jobs`,
      content: `${t("RA_SEARCH_SOR")} / ${t("RA_VIEW_JOBS")}`,
      show: location.pathname.includes("/view-scheduled-jobs") ? true : false,
      isBack: true,
    }
  ];
  return <BreadCrumb crumbs={crumbs} spanStyle={{ maxWidth: "min-content" }} />;
};

const App = ({ path }) => {
  const location = useLocation();
  const AnalysisSession = Digit.Hooks.useSessionStorage("RATE_REATE", {});
  const [sessionFormData, setSessionFormData, clearSessionFormData] = AnalysisSession;
  const locationCheck = window.location.href.includes("/employee/ws/new-application");

  const getBreadCrumbStyles = (screenType) => {
    // Defining 4 types for now -> create,view,inbox,search

    switch (true) {
      case screenType?.includes("/create"):
        return { marginLeft: "1rem" };

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

  useEffect(() => {
    if (!window.location.href.includes("create-contract") && sessionFormData && Object.keys(sessionFormData) != 0) {
      clearSessionFormData();
    }
  }, [location]);

  return (
    <Switch>
      <React.Fragment>
        <div className="ground-container">
          <div style={getBreadCrumbStyles(window.location.href)}>
            <RateAnalysisBreadCumbs location={location} />
          </div>

          <PrivateRoute path={`${path}/view-rate-analysis`} component={() => <ViewRateAnalysis />} />
          <PrivateRoute path={`${path}/create-rate-analysis`} component={() => <CreateRateAnalysis />} />
          <PrivateRoute path={`${path}/update-rate-analysis`} component={() => <CreateRateAnalysis isUpdate={true} />} />
          <PrivateRoute path={`${path}/response`} component={() => <RAResponseBanner />} />
          <PrivateRoute path={`${path}/view-scheduled-jobs`} component={() => <ViewScheduledJobs />} />
          <PrivateRoute path={`${path}/search-sor`} component={() => < SearchSOR/>} />
        </div>
      </React.Fragment>
    </Switch>
  );
};

export default App;
