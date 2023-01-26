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
        content: t("WORKS_WMS"),
        show: true,
    },
    {
      path: `/${window.contextPath}/employee/project/create-project`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_PROJECT")}` : t("WORKS_PROJECT"),
      show: location.pathname.includes("/project/create-project") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/project/project-details`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_PROJECT_DETAILS")}` : t("WORKS_PROJECT_DETAILS"),
      show: location.pathname.includes("/project/project-details") ? true : false,
      isBack: fromScreen && true,
    },
  ];
  return <BreadCrumb crumbs={crumbs} spanStyle={{ maxWidth: "min-content" }} />;
};

const App = ({ path }) => {
  const CreateProjectComponent = Digit?.ComponentRegistryService?.getComponent("CreateProject");
  const ProjectDetailsComponent = Digit?.ComponentRegistryService?.getComponent("ProjectDetails");

  return (
    <Switch>
      <AppContainer className="ground-container">
        <React.Fragment>
          <ProjectBreadCrumb location={location} />
        </React.Fragment>
        <PrivateRoute path={`${path}/create-project`} component={() => <CreateProjectComponent parentRoute={path}/>} />
        <PrivateRoute path={`${path}/project-details`} component={() => <ProjectDetailsComponent parentRoute={path}/>} />
      </AppContainer>
    </Switch>
  );
};

export default App;
