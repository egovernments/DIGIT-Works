import React from "react";
import { Switch, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { PrivateRoute, AppContainer, BreadCrumb } from "@egovernments/digit-ui-react-components";

const ProjectBreadCrumb = ({ location }) => {
  const { t } = useTranslation();
  const search = useLocation().search;
  const fromScreen = new URLSearchParams(search).get("from") || null;
  const crumbs = [
    {
        path: "/works-ui/employee",
        content: t("PROJECT_PROJECT"),
        show: true,
    },
    // {
    //     path: `/${window.contextPath}/employee/masters/search-organization`,
    //     content: fromScreen ? `${t(fromScreen)} / ${t("MASTERS_SEARCH_MASTERS")}` : t("MASTERS_SEARCH_MASTERS"),
    //     show: location.pathname.includes("/masters/search-organization") ? true : false,
    //     isBack: fromScreen && true,
    // }
  ];
  return <BreadCrumb crumbs={crumbs} spanStyle={{ maxWidth: "min-content" }} />;
};

const App = ({ path }) => {
  const CreateProjectComponent = Digit?.ComponentRegistryService?.getComponent("CreateProject");

  return (
    <Switch>
      <AppContainer className="ground-container">
        <React.Fragment>
          <ProjectBreadCrumb location={location} />
        </React.Fragment>
        <PrivateRoute path={`${path}/create-project`} component={() => <CreateProjectComponent parentRoute={path}/>} />
      </AppContainer>
    </Switch>
  );
};

export default App;
