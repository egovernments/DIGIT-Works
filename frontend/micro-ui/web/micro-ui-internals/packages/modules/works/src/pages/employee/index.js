import React from "react";
import { useTranslation } from "react-i18next";
import { PrivateRoute ,BreadCrumb} from "@egovernments/digit-ui-react-components";
import { Switch ,useLocation} from "react-router-dom";
import NewApplication from "./CreateContractor";
import Inbox from "./Inbox";
import LOIInbox from "./LOIInbox";

const BILLSBreadCrumbs = ({ location }) => {
  const { t } = useTranslation();

  const search = useLocation().search;
  const fromScreen = new URLSearchParams(search).get("from") || null;

  const crumbs = [
    {
      path: "/digit-ui/employee",
      content: t("ES_COMMON_HOME"),
      show: true,
    },
    {
      path: "/digit-ui/employee/works/inbox",
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_INBOX")}` : t("WORKS_INBOX"),
      show: location.pathname.includes("/works/inbox") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: "/digit-ui/employee/works/LOIInbox",
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_LOIInbox")}` : t("WORKS_LOIInbox"),
      show: location.pathname.includes("/works/LOIInbox") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: "/digit-ui/employee/works/create-contractor",
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_CREATE_CONTRACTOR")}` : t("WORKS_CREATE_CONTRACTOR"),
      show: location.pathname.includes("/works/create-contractor") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: "/digit-ui/employee/works/search-Estimate",
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_SEARCH_APPLICATIONS")}` : t("WORKS_SEARCH_APPLICATIONS"),
      show: location.pathname.includes("/works/search-Estimate") ? true : false,
      isBack: fromScreen && true,
    },
  ];
  return <BreadCrumb crumbs={crumbs} spanStyle={{ maxWidth: "min-content" }} />;

}

const App = ({ path }) => {
  const location = useLocation();
  const locationCheck = 
  window.location.href.includes("/employee/ws/new-application");
  return (
    <Switch>
      <React.Fragment>
        <div className="ground-container">
          <div style={{ marginLeft: "12px" }}>
            <BILLSBreadCrumbs location={location} />
          </div>
          <PrivateRoute path={`${path}/create-application`} component={() => <div>Hi</div>} />
          <PrivateRoute path={`${path}/inbox`} 
                component={() => (
                  <Inbox parentRoute={path} businessService="WORKS" filterComponent="WORKS_INBOX_FILTER" initialStates={{}} isInbox={true} />
                )} 
            />
          <PrivateRoute path={`${path}/LOIInbox`} 
                component={() => (
                  <LOIInbox parentRoute={path} businessService="LOI" filterComponent="LOI_INBOX_FILTER" initialStates={{}} isInbox={true} />
                )} 
            />
          <PrivateRoute path={`${path}/create-contractor`}
                component={NewApplication}
            />
          <PrivateRoute path={`${path}/search-Estimate`}
                component={()=><div>Search</div>}
            />
        </div>
      </React.Fragment>
    </Switch>
  );
};

export default App;
