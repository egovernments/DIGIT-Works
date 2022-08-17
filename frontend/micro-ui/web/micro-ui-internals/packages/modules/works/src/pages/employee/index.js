import { PrivateRoute } from "@egovernments/digit-ui-react-components";
import React from "react";
import { Switch } from "react-router-dom";
import SearchEstimate from "./SearchEstimate";

import CreateLOI from "./LOI/CreateLOI";
import CreateEstimate from "./Estimate/CreateEstimate";
import ViewEstimate from "./Estimate/ViewEstimate";
import ViewLOI from "./LOI/ViewLOI";

const App = ({ path }) => {
  console.log(path)
  return (
    <Switch>
      <React.Fragment>
        <div className="ground-container">
          <PrivateRoute path={`${path}/search-application`} component={() => <SearchEstimate/>} />
          <PrivateRoute path={`${path}/create-loi`} component={() => <CreateLOI {...{ path }} />} />
          <PrivateRoute path={`${path}/create-estimate`} component={() => <CreateEstimate {...{path}}/>} />
          <PrivateRoute path={`${path}/view-estimate`} component={() => <ViewEstimate {...{ path }} />} />
          <PrivateRoute path={`${path}/view-loi`} component={() => <ViewLOI {...{ path }} />} />
        </div>
      </React.Fragment>
    </Switch>
  );
};

export default App;
