import React from "react";
import { useTranslation } from "react-i18next";
import { PrivateRoute ,BreadCrumb} from "@egovernments/digit-ui-react-components";
import { Switch ,useLocation} from "react-router-dom";
import NewApplication from "./CreateContractor";
import Search from "./search";
import SearchEstimate from "./SearchEstimate";
import CreateLOI from "./LOI/CreateLOI";
import CreateEstimate from "./Estimate/CreateEstimate";
import ViewEstimate from "./Estimate/ViewEstimate";
import ViewLOI from "./LOI/ViewLOI";
import SearchApprovedSubEstimate from "../employee/SearchApprovedSubEstimate"
import Response from "../../components/response";
import Inbox from "./Inbox";
import LOIInbox from "./LOIInbox";

const BILLSBreadCrumbs = ({ location }) => {
  const { t } = useTranslation();

  const search = useLocation().search;
  const fromScreen = new URLSearchParams(search).get("from") || null;
  // const inboxInitialState = {
  //   searchParams: {
  //     uuid: { code: "ASSIGNED_TO_ALL", name: "ES_INBOX_ASSIGNED_TO_ALL" },
  //     services: ["PT.CREATE", "PT.MUTATION", "PT.UPDATE"],
  //     applicationStatus: [],
  //     locality: [],
  //   },
  // };
  const crumbs = [
    {
      path: "/digit-ui/employee",
      content: t("WORKS_COMMON_WMS"),
      show: true,
    },
    {
      path: "/digit-ui/employee/works/create-contractor",
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_CREATE_CONTRACTOR")}` : t("WORKS_CREATE_CONTRACTOR"),
      show: location.pathname.includes("/works/create-contractor") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: "/digit-ui/employee/works/search-Estimate-approved",
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
          <PrivateRoute path={`${path}/create-contractor`} component={NewApplication}/>
          <PrivateRoute path={`${path}/search-Estimate-approved`} component={(props)=><Search {...props} parentRoute={path}/>}/>
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
