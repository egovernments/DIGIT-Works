import React, { useEffect } from "react";
import { useTranslation } from "react-i18next";
import { PrivateRoute, BreadCrumb } from "@egovernments/digit-ui-react-components";
import { Switch, useLocation } from "react-router-dom";
import ViewMeasurement from "./ViewMeasurement";
import SearchMeasurement from "./SearchMeasurement";
import InboxMeasurement from "./InboxMeasurement";
import CreateMeasurement from "./CreateMeasurement";

const MeasurementBreadCrumbs = ({ location }) => {
  const { t } = useTranslation();

  const search = useLocation().search;
  const fromScreen = new URLSearchParams(search).get("from") || null;

  const crumbs = [
    {
      path: `/${window?.contextPath}/employee`,
      content: t("WORKS_MUKTA"),
      show: true,
    },
  ];
  return <BreadCrumb crumbs={crumbs} spanStyle={{ maxWidth: "min-content" }} />;
};

const App = ({ path }) => {
  const location = useLocation();

  return (
    <Switch>
      <React.Fragment>
        <MeasurementBreadCrumbs location={location} />
        <PrivateRoute path={`${path}/create-measurement`} component={() => <CreateMeasurement {...{ path }} />} />
        <PrivateRoute path={`${path}/search`} component={() => <InboxMeasurement {...{ path }} />} />

        <PrivateRoute path={`${path}/inbox`} component={() => <SearchMeasurement {...{ path }} />} />
        <PrivateRoute path={`${path}/view`} component={() => <ViewMeasurement {...{ path }} />} />
      </React.Fragment>
    </Switch>
  );
};

export default App;
