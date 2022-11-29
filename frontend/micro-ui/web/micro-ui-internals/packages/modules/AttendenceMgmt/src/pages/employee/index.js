import React from "react";
import { Switch, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { PrivateRoute, AppContainer, BreadCrumb } from "@egovernments/digit-ui-react-components";

const AttendanceBreadCrumbs = ({ location }) => {
  const { t } = useTranslation();
  const search = useLocation().search;
  const fromScreen = new URLSearchParams(search).get("from") || null;
  const crumbs = [
    {
      path: "/works-ui/employee",
      content: "AMP",
      show: true,
    },
    {
      path: `${window.contextPath}/employee/attendencemgmt/inbox`,
      content: fromScreen ? `${t(fromScreen)} / AM` : "AM",
      show: location.pathname.includes("/attendencemgmt/inbox") ? true : false,
      isBack: fromScreen && true,
    }
  ];
  return <BreadCrumb crumbs={crumbs} spanStyle={{ maxWidth: "min-content" }} />;

}

const App = ({ path }) => {
  const location = useLocation();
  const Inbox = Digit.ComponentRegistryService.getComponent("AttendenceMgmtInbox");

  return (
      <span className={"pt-citizen"}>
          <Switch>
              <AppContainer>
                  <React.Fragment>
                      <AttendanceBreadCrumbs location={location} />
                  </React.Fragment>
                  <PrivateRoute 
                      path={`${path}/inbox`} 
                      component={ () => (
                          <Inbox 
                              isInbox 
                              parentRoute={path}
                              filterComponent="AttendenceInboxFilter" 
                              searchComponent="AttendenceInboxSearch"
                              initialStates={{}}
                          />
                      )} />
              </AppContainer>
          </Switch>
      </span>
  );
};

export default App;