import { PrivateRoute } from "@egovernments/digit-ui-react-components";
import React from "react";
import { Switch } from "react-router-dom";

import CreateLOI from "./LOI/CreateLOI";
import CreateEstimate from "./Estimate/CreateEstimate";


const App = ({ path }) => {
  return (
    <Switch>
      <React.Fragment>
        <div className="ground-container">
          <PrivateRoute path={`${path}/create-loi`} component={() => <CreateLOI {...{ path }} />} />
          <PrivateRoute path={`${path}/create-estimate`} component={() => <CreateEstimate {...{path}}/>} />
        </div>
      </React.Fragment>
    </Switch>
  );
};

export default App;
