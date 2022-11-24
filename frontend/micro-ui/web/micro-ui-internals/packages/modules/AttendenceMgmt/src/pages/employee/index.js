import React, { useEffect } from "react";
import { PrivateRoute, BackButton, AppContainer, BreadCrumb } from "@egovernments/digit-ui-react-components";
import { Switch, useLocation } from "react-router-dom";
import AttendanceBreadCrumbs from "./viewAttendance/AttendanceBreadCrumbs";

const App = ({ path }) => {
  const location = useLocation();

  const ViewAttendanceApp = Digit?.ComponentRegistryService?.getComponent("ViewAttendance");

  return (
    <span>
      <Switch>
        <React.Fragment>
          <Switch>
            <AppContainer>
              <AttendanceBreadCrumbs location={location} />
              <PrivateRoute path={`${path}/view-attendance`} component={ViewAttendanceApp} />
            </AppContainer>
          </Switch>
        </React.Fragment>
      </Switch>
    </span>
  );
};

export default App;
