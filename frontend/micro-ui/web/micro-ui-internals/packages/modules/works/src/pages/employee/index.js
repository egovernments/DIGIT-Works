import { PrivateRoute } from "@egovernments/digit-ui-react-components";
import React from "react";
import { Switch } from "react-router-dom";
import SearchEstimate from "./SearchEstimate";

const App = ({ path }) => {
  console.log(path)
  return (
    <Switch>
      <React.Fragment>
        <div className="ground-container">
          <PrivateRoute path={`${path}/create-application`} component={() => <div>Hi</div>} />
          <PrivateRoute path={`${path}/search-application`} component={() => <SearchEstimate/>} />
        </div>
      </React.Fragment>
    </Switch>
  );
};

export default App;
