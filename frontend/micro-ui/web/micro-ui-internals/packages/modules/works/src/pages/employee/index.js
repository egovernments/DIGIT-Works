import React, { useEffect } from "react";
import { useTranslation } from "react-i18next";
import { PrivateRoute, BreadCrumb } from "@egovernments/digit-ui-react-components";
import { Switch, useLocation } from "react-router-dom";
import CreateContractor from "./Contractor/CreateContractor";
import Search from "./search";
import SearchEstimate from "./SearchEstimate";
import CreateLOI from "./LOI/CreateLOI";
import CreateEstimate from "./Estimate/CreateEstimate";
import ViewEstimate from "./Estimate/ViewEstimate";
import ViewLOI from "./LOI/ViewLOI";
import SearchApprovedSubEstimate from "../employee/SearchApprovedSubEstimate";
import Response from "../../components/response";
import Inbox from "./Inbox";
import LOIInbox from "./LOIInbox";
import HandleDownloadPdf from "../../components/HandleDownloadPdf";
import ModifyEstimate from "../../components/ModifyEstimate";
import Checklist from "./Checklist/index";
import ChecklistInbox from './ChecklistInbox'
import ProjectClosure from "./Project Closure/ProjectClosure";
import ChecklistSampleForm from "./Checklist/SampleFormTest";
const BILLSBreadCrumbs = ({ location }) => {
  const { t } = useTranslation();

  const search = useLocation().search;
  const fromScreen = new URLSearchParams(search).get("from") || null;
  const crumbs = [
    {
      path: `/${window?.contextPath}/employee`,
      content: t("WORKS_MUKTA"),
      show: true,
    },
    {
      path: `/${window?.contextPath}/employee/works/create-contractor`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_CREATE_CONTRACTOR")}` : t("WORKS_CREATE_CONTRACTOR"),
      show: location.pathname.includes("/works/create-contractor") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window?.contextPath}/employee/works/search-Estimate-approved`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_SEARCH_ESTIMATES")}` : t("WORKS_SEARCH_ESTIMATES"),
      show: location.pathname.includes("/works/search-Estimate") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/works/create-estimate`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_CREATE_ESTIMATE")}` : t("WORKS_CREATE_ESTIMATE"),
      show: location.pathname.includes("/works/create-estimate") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/works/modify-estimate`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_MODIFY_ESTIMATE")}` : t("WORKS_MODIFY_ESTIMATE"),
      show: location.pathname.includes("/works/modify-estimate") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/works/create-loi`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_CREATE_LOI")}` : t("WORKS_CREATE_LOI"),
      show: location.pathname.includes("/works/create-loi") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/works/response`,
      content: fromScreen ? `${t(fromScreen)} / ${t("ACTION_TEST_RESPONSE")}` : t("ACTION_TEST_RESPONSE"),
      show: location.pathname.includes("/works/response") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/works/view-estimate`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_VIEW_ESTIMATE")}` : t("WORKS_VIEW_ESTIMATE"),
      show: location.pathname.includes("/works/view-estimate") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/works/view-loi`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_VIEW_LOI")}` : t("WORKS_VIEW_LOI"),
      show: location.pathname.includes("/works/view-loi") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/works/search-Estimate`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_SEARCH_ESTIMATES")}` : t("WORKS_SEARCH_ESTIMATES"),
      show: location.pathname.includes("/works/search-estimate") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/works/search-approved-estimate`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_SEARCH_APPROVED_ESTIMATE")}` : t("WORKS_SEARCH_APPROVED_ESTIMATE"),
      show: location.pathname.includes("/works/search-approved-estimate") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/works/inbox`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_ESTIMATE_INBOX")}` : t("WORKS_ESTIMATE_INBOX"),
      show: location.pathname.includes("/works/inbox") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/works/loiinbox`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_LOI_INBOX")}` : t("WORKS_LOI_INBOX"),
      show: location.pathname.includes("/works/loiinbox") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/works/checklistinbox`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_CHECKLIST_INBOX")}` : t("WORKS_CHECKLIST_INBOX"),
      show: location.pathname.includes("/works/checklistinbox") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/works/projectclosure`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_PROJECT_CLOSURE")}` : t("WORKS_PROJECT_CLOSURE"),
      show: location.pathname.includes("/works/projectclosure") ? true : false,
      isBack: fromScreen && true,
    }
  ];
  return <BreadCrumb crumbs={crumbs} spanStyle={{ maxWidth: "min-content" }} />;
};

const App = ({ path }) => {
  const location = useLocation();
  const EstimateSession = Digit.Hooks.useSessionStorage("ESTIMATE_CREATE", {});
  const [sessionFormData, setSessionFormData, clearSessionFormData] = EstimateSession;
  const locationCheck = window.location.href.includes("/employee/ws/new-application");
  const getBreadCrumbStyles = (screenType) => {
    // Defining 4 types for now -> create,view,inbox,search
    
    switch (true) {
      case screenType?.includes("/create"):
        return { marginLeft: "10px" };

      case screenType?.includes("/view"):
        return { marginLeft: "4px" };

      case screenType?.includes("/search"):
        return { marginLeft: "7px" };
      case screenType?.includes("/inbox") || screenType?.includes("/LOIInbox"):
        return { marginLeft: "5px" };

      default:
        return { marginLeft: "0px" };
    }
  };
  useEffect(() => {
    return () => {
      if (!window.location.href.includes("create-estimate") && Object.keys(sessionFormData) != 0) {
        clearSessionFormData();
      }
    };
  });
  return (
    <Switch>
      <React.Fragment>
        <div className="ground-container">
          <div style={getBreadCrumbStyles(window.location.href)}>
            <BILLSBreadCrumbs location={location} />
          </div>
          <PrivateRoute path={`${path}/create-application`} component={() => <div>Hi</div>} />
          <PrivateRoute path={`${path}/create-contractor`} component={() => <CreateContractor {...path} />} />
          <PrivateRoute path={`${path}/search-Estimate-approved`} component={(props) => <Search {...props} parentRoute={path} />} />
          <PrivateRoute
            path={`${path}/inbox`}
            component={() => (
              <Inbox parentRoute={path} businessService="WORKS" filterComponent="WORKS_INBOX_FILTER" initialStates={{}} isInbox={true} />
            )}
          />
          <PrivateRoute
            path={`${path}/checklistinbox`}
            component={() => (
              <ChecklistInbox parentRoute={path} businessService="WORKS" filterComponent="CHECKLIST_INBOX_FILTER" initialStates={{}} isInbox={true} />
            )}
          />
          
          <PrivateRoute
            path={`${path}/LOIInbox`}
            component={() => (
              <LOIInbox parentRoute={path} businessService="LOI" filterComponent="LOI_INBOX_FILTER" initialStates={{}} isInbox={true} />
            )}
          />
          <PrivateRoute
            path={`${path}/projectclosure`}
            component={() => <ProjectClosure />}
          />

          <PrivateRoute path={`${path}/search-estimate`} component={() => <SearchEstimate />} />
          <PrivateRoute path={`${path}/search-approved-estimate`} component={() => <SearchApprovedSubEstimate />} />
          <PrivateRoute path={`${path}/create-loi`} component={() => <CreateLOI {...{ path }} />} />
          <PrivateRoute path={`${path}/create-estimate`} component={() => <CreateEstimate {...{ path }} EstimateSession={EstimateSession} />} />
          <PrivateRoute path={`${path}/modify-estimate`} component={() => <ModifyEstimate {...{ path }} />} />
          <PrivateRoute path={`${path}/view-estimate`} component={() => <ViewEstimate {...{ path }} />} />
          <PrivateRoute path={`${path}/view-loi`} component={() => <ViewLOI {...{ path }} />} />
          <PrivateRoute path={`${path}/response`} component={() => <Response {...{ path }} />} />
          <PrivateRoute path={`${path}/checklist`} component={() => <Checklist {...{ path }} />} />
          <PrivateRoute path={`${path}/sampleForm`} component={() => <ChecklistSampleForm {...{ path }} />} />
          
          <PrivateRoute path={`${path}/download`} component={()=> <HandleDownloadPdf {...{path}}/>}/>
        </div>
      </React.Fragment>
    </Switch>
  );
};

export default App;
