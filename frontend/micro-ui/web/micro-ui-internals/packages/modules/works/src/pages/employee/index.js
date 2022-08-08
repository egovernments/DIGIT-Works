import { PrivateRoute } from "@egovernments/digit-ui-react-components";
import React from "react";
import { Switch } from "react-router-dom";
import Inbox from "./Inbox";
import LOIInbox from "./LOIInbox";

const App = ({ path }) => {
  const inboxInitialState = {
    searchParams: {
      uuid: { code: "ASSIGNED_TO_ALL", name: "ES_INBOX_ASSIGNED_TO_ALL" },
      services: ["PT.CREATE", "PT.MUTATION", "PT.UPDATE"],
      applicationStatus: [],
      locality: [],
    },
  };
  return (
    <Switch>
      <React.Fragment>
        <div className="ground-container">
          <PrivateRoute path={`${path}/create-application`} component={() => <div>Hi</div>} />
          <PrivateRoute path={`${path}/inbox`} component={() => <Inbox
                useNewInboxAPI={true}
                parentRoute={path}
                // businessService="PT"
                filterComponent="PT_INBOX_FILTER"
                initialStates={inboxInitialState}
                isInbox={true}
              />} />
          {/* <PrivateRoute path={`${path}/LOIInbox`} component={() => <LOIInbox
                useNewInboxAPI={true}
                parentRoute={path}
                businessService="WORKS"
                filterComponent="WORKS_LOI_INBOX_FILTER"
                initialStates={inboxInitialState}
                isInbox={true}
              />} /> */}
        </div>
      </React.Fragment>
    </Switch>
  );
};

export default App;
