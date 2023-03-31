import React, { useEffect } from "react";
import { Switch, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { PrivateRoute, AppContainer, BreadCrumb } from "@egovernments/digit-ui-react-components";

const MastersBreadCrumb = ({ location }) => {
  const { t } = useTranslation();
  const search = useLocation().search;
  const fromScreen = new URLSearchParams(search).get("from") || null;
  const crumbs = [
    {
        path: `/${window?.contextPath}/employee`,
        content: t("WORKS_WMS"),
        show: true,
    },
    {
        path: `/${window.contextPath}/employee/masters/search-organization`,
        content: fromScreen ? `${t(fromScreen)} / ${t("MASTERS_SEARCH_MASTERS")}` : t("MASTERS_SEARCH_MASTERS"),
        show: location.pathname.includes("/masters/search-organization") ? true : false,
        isBack: fromScreen && true,
    },
    {
        path: `/${window.contextPath}/employee/masters/create-organization`,
        content: fromScreen ? `${t(fromScreen)} / ${t("MASTERS_CREATE_ORGANISATION")}` : t("MASTERS_CREATE_ORGANISATION"),
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
      path: `/${window.contextPath}/employee/masters/view-organization`,
      content: fromScreen ? `${t(fromScreen)} / ${t("MASTERS_VIEW_COMMUNITY_ORG")}` : `${t("MASTERS_VIEW_COMMUNITY_ORG")}`,
      show: location.pathname.includes("/masters/view-organization") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/masters/view-wageseeker`,
      content: fromScreen ? `${t(fromScreen)} / ${t("MASTERS_VIEW_WAGESEEKER")}` : `${t("MASTERS_VIEW_WAGESEEKER")}`,
      show: location.pathname.includes("/masters/view-wageseeker") ? true : false,
      isBack: fromScreen && true,
    }
  ];
  return <BreadCrumb crumbs={crumbs} spanStyle={{ maxWidth: "min-content" }} />;
};

const App = ({ path }) => {
  const location = useLocation();
  const orgSession = Digit.Hooks.useSessionStorage("ORG_CREATE", {});
  const [sessionFormData, setSessionFormData, clearSessionFormData] = orgSession;

  const SearchMasters = Digit?.ComponentRegistryService?.getComponent("SearchMasters");
  const CreateMasters = Digit?.ComponentRegistryService?.getComponent("CreateMasters");
  const SearchWageSeeker = Digit?.ComponentRegistryService?.getComponent("SearchWageSeeker");
  const RegisterWageSeekerComponent = Digit?.ComponentRegistryService?.getComponent("RegisterWageSeeker");
  const ViewOrganisationComponent = Digit?.ComponentRegistryService?.getComponent("ViewOrganisation");
  const SearchOrganisation =  Digit?.ComponentRegistryService?.getComponent("SearchOrganisation");
  const ViewWageSeeker = Digit?.ComponentRegistryService?.getComponent("ViewWageSeeker");
  const ModifyWageSeeker = Digit?.ComponentRegistryService?.getComponent("ModifyWageSeeker");
  const CreateOrganization = Digit?.ComponentRegistryService?.getComponent("CreateOrganisation");
  useEffect(() => {
    return () => {
      if (!window.location.href.includes("create-organization") && Object.keys(sessionFormData) != 0) {
        clearSessionFormData();
      }
    };
  }, [location]);

  return (
    <Switch>
      <AppContainer className="ground-container">
        <React.Fragment>
          <MastersBreadCrumb location={location} />
        </React.Fragment>
        <PrivateRoute path={`${path}/search-masters`} component={() => <SearchMasters parentRoute={path}/>} />
        <PrivateRoute path={`${path}/create-masters`} component={() => <CreateMasters parentRoute={path}/>} />
        
         {/* Organisation Masters  */}
        <PrivateRoute path={`${path}/create-organization`} component={() => <CreateOrganization parentRoute={path}/>} />
        <PrivateRoute path={`${path}/search-organization`} component={() => <SearchOrganisation parentRoute={path}/>} />
        <PrivateRoute path={`${path}/view-organization`} component={ViewOrganisationComponent} />
       
        {/* WageSeekers Masters*/}
        <PrivateRoute path={`${path}/search-wageseeker`} component={() => <SearchWageSeeker parentRoute={path}/>} />
        <PrivateRoute path={`${path}/create-wageseeker`} component={RegisterWageSeekerComponent} />
        <PrivateRoute path={`${path}/view-wageseeker`} component={()=> <ViewWageSeeker parentRoute={path}/>} />
        <PrivateRoute path={`${path}/modify-wageseeker`} component={()=> <ModifyWageSeeker parentRoute={path}/>} />
      </AppContainer>
    </Switch>
  );
};

export default App;
