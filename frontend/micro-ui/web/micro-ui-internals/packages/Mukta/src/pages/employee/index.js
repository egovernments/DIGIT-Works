import React, { useEffect } from "react";
import { useTranslation } from "react-i18next";
import { BreadCrumb, PrivateRoute } from "@egovernments/digit-ui-react-components";
import { Switch, useLocation } from "react-router-dom";

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
      path: `/${window.contextPath}/employee/project/view-projects`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_PROJECT")}` : t("WORKS_PROJECT"),
      show: location.pathname.includes("/project/view-projects") ? true : false,
      isBack: fromScreen && true,
    }
  ];
  return <BreadCrumb crumbs={crumbs} spanStyle={{ maxWidth: "min-content" }} />;
};

const App = ({ path }) => {
  const ViewProjectsComponent = Digit?.ComponentRegistryService?.getComponent("ViewProjects");

  return (
    <Switch>
      <React.Fragment>
      <ProjectBreadCrumb location={location} />
        <div>
          <PrivateRoute path={`${path}/sample`} component={() => <div>Sample Screen loaded</div>} />
          <PrivateRoute path={`${path}/project-details`} component={ViewProjectsComponent}/>
        </div>
      </React.Fragment>
    </Switch>
  );
};

export default App;
