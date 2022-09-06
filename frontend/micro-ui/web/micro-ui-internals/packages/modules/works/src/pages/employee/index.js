import { PrivateRoute } from "@egovernments/digit-ui-react-components";
import React from "react";
import { Switch } from "react-router-dom";
import SearchEstimate from "./SearchEstimate";
import CreateLOI from "./LOI/CreateLOI";
import CreateEstimate from "./Estimate/CreateEstimate";
import ViewEstimate from "./Estimate/ViewEstimate";
import ViewLOI from "./LOI/ViewLOI";
import SearchApprovedSubEstimate from "../employee/SearchApprovedSubEstimate"
import Response from "../../components/response";
import Inbox from "./Inbox";
import LOIInbox from "./LOIInbox";

const App = ({ path }) => {
  // const inboxInitialState = {
  //   searchParams: {
  //     uuid: { code: "ASSIGNED_TO_ALL", name: "ES_INBOX_ASSIGNED_TO_ALL" },
  //     services: ["PT.CREATE", "PT.MUTATION", "PT.UPDATE"],
  //     applicationStatus: [],
  //     locality: [],
  //   },
  // };

  return (
    <Switch>
      <React.Fragment>
        <div className="ground-container">
          <PrivateRoute path={`${path}/inbox`} component={() => (<Inbox parentRoute={path} businessService="WORKS" filterComponent="WORKS_INBOX_FILTER" initialStates={{}} isInbox={true} />)}/>
          <PrivateRoute path={`${path}/LOIInbox`} component={() => (<LOIInbox parentRoute={path} businessService="LOI" filterComponent="LOI_INBOX_FILTER" initialStates={{}} isInbox={true} />)}/>
          <PrivateRoute path={`${path}/search-estimate`} component={() => <SearchEstimate/>} />
          <PrivateRoute path={`${path}/search-approved-estimate`} component={() => <SearchApprovedSubEstimate />} />
          <PrivateRoute path={`${path}/create-loi`} component={() => <CreateLOI {...{ path }} />} />
          <PrivateRoute path={`${path}/create-estimate`} component={() => <CreateEstimate {...{path}}/>} />
          <PrivateRoute path={`${path}/view-estimate`} component={() => <ViewEstimate {...{ path }} />} />
          <PrivateRoute path={`${path}/view-loi`} component={() => <ViewLOI {...{ path }} />} />
          <PrivateRoute path={`${path}/response`} component={() => <Response {...{ path }} />} />
        </div>
      </React.Fragment>
    </Switch>
  );
};

export default App;
