import React from "react";
import { Switch, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { PrivateRoute, AppContainer, BreadCrumb } from "@egovernments/digit-ui-react-components";

const MastersBreadCrumb = ({ location }) => {
  const { t } = useTranslation();
  const search = useLocation().search;
  const fromScreen = new URLSearchParams(search).get("from") || null;
  const crumbs = [
    {
        path: "/works-ui/employee",
        content: t("MASTERS_MASTERS"),
        show: true,
    },
    {
        path: `/${window.contextPath}/employee/masters/search-organization`,
        content: fromScreen ? `${t(fromScreen)} / ${t("MASTERS_MASTERS")}` : t("MASTERS_MASTERS"),
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
      path: `/${window.contextPath}/employee/masters/wage-seeker-registration`,
      content: fromScreen ? `${t(fromScreen)} / ${t("MASTERS_REGISTER_WAGESEEKER")}` : `${t("MASTERS_REGISTER_WAGESEEKER")}`,
      show: location.pathname.includes("/masters/wage-seeker-registration") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/masters/view-organization`,
      content: fromScreen ? `${t(fromScreen)} / ${t("MASTERS_VIEW_COMMUNITY_ORG")}` : `${t("MASTERS_VIEW_COMMUNITY_ORG")}`,
      show: location.pathname.includes("/masters/view-organization") ? true : false,
      isBack: fromScreen && true,
    },
  ];
  return <BreadCrumb crumbs={crumbs} spanStyle={{ maxWidth: "min-content" }} />;
};

const App = ({ path }) => {
  const SearchOrganization = Digit?.ComponentRegistryService?.getComponent("SearchOrganization");
  const CreateOrganization = Digit?.ComponentRegistryService?.getComponent("CreateOrganization");
  const RegisterWageSeekerComponent = Digit?.ComponentRegistryService?.getComponent("RegisterWageSeeker");
  const ViewOrganisationComponent = Digit?.ComponentRegistryService?.getComponent("ViewOrganisation");

  return (
    <Switch>
      <AppContainer className="ground-container">
        <React.Fragment>
          <MastersBreadCrumb location={location} />
        </React.Fragment>
        <PrivateRoute path={`${path}/search-organization`} component={() => <SearchOrganization parentRoute={path}/>} />
        <PrivateRoute path={`${path}/create-organization`} component={() => <CreateOrganization parentRoute={path}/>} />
        <PrivateRoute path={`${path}/wage-seeker-registration`} component={RegisterWageSeekerComponent} />
        <PrivateRoute path={`${path}/view-organization`} component={ViewOrganisationComponent} />
      </AppContainer>
    </Switch>
  );
};

export default App;
