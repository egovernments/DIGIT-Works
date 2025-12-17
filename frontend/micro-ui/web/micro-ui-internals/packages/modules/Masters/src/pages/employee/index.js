import React, { useEffect } from "react";
import { Switch, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { PrivateRoute, AppContainer } from "@egovernments/digit-ui-react-components";
import { BreadCrumb } from "@egovernments/digit-ui-components";

const MastersBreadCrumb = ({ location }) => {
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
      path: `/${window.contextPath}/employee/masters/response-wage-seeker`,
      content: fromScreen ? `${t(fromScreen)} / ${t("ES_WAGE_SEEKER")}` : t("ES_WAGE_SEEKER"),
      show: location.pathname.includes("masters/response-wage-seeker") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/masters/response-org`,
      content: fromScreen ? `${t(fromScreen)} / ${t("MASTERS_ORG_1")}` : t("MASTERS_ORG_1"),
      show: location.pathname.includes("/masters/response-org") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/masters/search-organization`,
      content: fromScreen ? `${t(fromScreen)} / ${t("MASTERS_SEARCH_MASTERS")}` : t("MASTERS_SEARCH_MASTERS"),
      show: location.pathname.includes("/masters/search-organization") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/masters/create-organization`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_MASTERS")}` : t("WORKS_MASTERS"),
      show: location.pathname.includes("/masters/create-organization") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/masters/create-wageseeker`,
      content: fromScreen ? `${t(fromScreen)} / ${t("MASTERS_REGISTER_WAGESEEKER")}` : `${t("MASTERS_REGISTER_WAGESEEKER")}`,
      show: location.pathname.includes("/masters/create-wageseeker") ? true : false,
      isBack: fromScreen && true,
    },
    {
      // path: `/${window.contextPath}/employee/masters/view-organization`,
      content: fromScreen ? `${t(fromScreen)} / ${t("MASTERS_VIEW_ORG")}` : `${t("MASTERS_VIEW_ORG")}`,
      show: location.pathname.includes("/masters/view-organization") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/masters/search-wageseeker`,
      content: fromScreen ? `${t(fromScreen)} / ${t("ACTION_TEST_MASTERS_SEARCH_WAGESEEKER")}` : t("ACTION_TEST_MASTERS_SEARCH_WAGESEEKER"),
      show: location.pathname.includes("/masters/search-wageseeker") ? true : false,
      isBack: fromScreen && true,
    },
    {
      // path: `/${window.contextPath}/employee/masters/modify-wageseeker`,
      content: fromScreen ? `${t(fromScreen)} / ${t("MASTERS_MODIFY_WAGESEEKER")}` : `${t("MASTERS_MODIFY_WAGESEEKER")}`,
      show: location.pathname.includes("/masters/modify-wageseeker") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/masters/view-wageseeker`,
      content: fromScreen ? `${t(fromScreen)} / ${t("MASTERS_VIEW_WAGESEEKER")}` : `${t("MASTERS_VIEW_WAGESEEKER")}`,
      show: location.pathname.includes("/masters/view-wageseeker") ? true : false,
      isBack: fromScreen && true,
    },
  ];
  return <BreadCrumb crumbs={crumbs} />;
};

const App = ({ path }) => {
  const location = useLocation();
  const orgSession = Digit.Hooks.useSessionStorage("ORG_CREATE", {});
  const [sessionFormData, setSessionFormData, clearSessionFormData] = orgSession;

  const wageSeekerSession = Digit.Hooks.useSessionStorage("WAGE_SEEKER_CREATE", {});
  const [wsSesionFormData, setWsSessionFormData, clearWsSessionFormData] = wageSeekerSession;

  const SearchMasters = Digit?.ComponentRegistryService?.getComponent("SearchMasters");
  const CreateMasters = Digit?.ComponentRegistryService?.getComponent("CreateMasters");
  const ViewMasters = Digit?.ComponentRegistryService?.getComponent("ViewMasters");

  const CreateOrganisation = Digit?.ComponentRegistryService?.getComponent("CreateOrganisation");
  const SearchOrganisation = Digit?.ComponentRegistryService?.getComponent("SearchOrganisation");
  const ViewOrganisation = Digit?.ComponentRegistryService?.getComponent("ViewOrganisation");

  const RegisterWageSeeker = Digit?.ComponentRegistryService?.getComponent("RegisterWageSeeker");
  const SearchWageSeeker = Digit?.ComponentRegistryService?.getComponent("SearchWMSWageseeker");
  const ViewWageSeeker = Digit?.ComponentRegistryService?.getComponent("ViewWageSeeker");
  const ModifyWageSeeker = Digit?.ComponentRegistryService?.getComponent("ModifyWageSeeker");

  const MastersResponse = Digit?.ComponentRegistryService?.getComponent("MastersResponse");

  useEffect(() => {
    if (!window.location.href.includes("create-organization") && sessionFormData && Object.keys(sessionFormData) != 0) {
      clearSessionFormData();
    }
    if (!window.location.href.includes("modify-wageseeker") && wsSesionFormData && Object.keys(wsSesionFormData) != 0) {
      clearWsSessionFormData();
    }
  }, [location]);

  return (
    <Switch>
      <AppContainer>
        <React.Fragment>
          <MastersBreadCrumb location={location} />
        </React.Fragment>
        <PrivateRoute path={`${path}/search-masters`} component={() => <SearchMasters parentRoute={path} />} />
        <PrivateRoute path={`${path}/create-masters`} component={() => <CreateMasters parentRoute={path} />} />
        <PrivateRoute path={`${path}/view-masters`} component={() => <ViewMasters parentRoute={path} />} />

        {/* Organisation Masters  */}
        <PrivateRoute path={`${path}/create-organization`} component={() => <CreateOrganisation parentRoute={path} />} />
        <PrivateRoute path={`${path}/search-organization`} component={() => <SearchOrganisation parentRoute={path} />} />
        <PrivateRoute path={`${path}/view-organization`} component={() => <ViewOrganisation parentRoute={path} />} />

        {/* WageSeekers Masters*/}
        <PrivateRoute path={`${path}/search-wageseeker`} component={() => <SearchWageSeeker parentRoute={path} />} />
        <PrivateRoute path={`${path}/create-wageseeker`} component={() => <RegisterWageSeeker parentRoute={path} />} />
        <PrivateRoute path={`${path}/view-wageseeker`} component={() => <ViewWageSeeker parentRoute={path} />} />
        <PrivateRoute path={`${path}/modify-wageseeker`} component={() => <ModifyWageSeeker parentRoute={path} />} />
        <PrivateRoute path={`${path}/response-org`} component={() => <MastersResponse parentRoute={path} />} />
        <PrivateRoute path={`${path}/response-wage-seeker`} component={() => <MastersResponse parentRoute={path} />} />
      </AppContainer>
    </Switch>
  );
};

export default App;
