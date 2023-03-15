import React, { useEffect } from "react";
import { useTranslation } from "react-i18next";
import { PrivateRoute, BreadCrumb } from "@egovernments/digit-ui-react-components";
import { Switch, useLocation } from "react-router-dom";
import CreateContract from "./CreateContract";
import Inbox from "./Inbox";
import SearchContracts from "./SearchContract";
import ViewContract from "./ViewContract";

const ContractsBreadCrumbs = ({ location }) => {
  const { t } = useTranslation();
  const search = useLocation().search;
  const fromScreen = new URLSearchParams(search).get("from") || null;
  const crumbs = [
    {
      path: `/${window?.contextPath}/employee`,
      content: t("WORKS_WMS"),
      show: true,
    },
    {
      path: `/${window.contextPath}/employee/contracts/`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_CONTRACTS")}` : t("WORKS_CONTRACTS"),
      show: location.pathname.includes("/contracts/create-contract") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/contracts/inbox`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_CONTRACTS")}` : t("WORKS_CONTRACTS"),
      show: location.pathname.includes("/contracts/inbox") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/contracts/search-contract`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_CONTRACTS")}` : t("WORKS_CONTRACTS"),
      show: location.pathname.includes("/contracts/search-contract") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/contracts/view-contract`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_CONTRACTS")}` : t("WORKS_CONTRACTS"),
      show: location.pathname.includes("/contracts/view-contract") ? true : false,
      isBack: fromScreen && true,
    },
  ];
  return <BreadCrumb crumbs={crumbs} spanStyle={{ maxWidth: "min-content" }} />;
};

const App = ({ path }) => {
  const location = useLocation();
  const ContractSession = Digit.Hooks.useSessionStorage("CONTRACT_CREATE", {});
  const [sessionFormData, setSessionFormData, clearSessionFormData] = ContractSession;
  const locationCheck = window.location.href.includes("/employee/ws/new-application");
  const CreateWorkOrderComponent = Digit?.ComponentRegistryService?.getComponent("CreateWorkOrder");

  const getBreadCrumbStyles = (screenType) => {
    // Defining 4 types for now -> create,view,inbox,search

    switch (true) {
      case screenType?.includes("/create"):
        return { marginLeft: "0px" };

      case screenType?.includes("/view"):
        return { marginLeft: "4px" };

      case screenType?.includes("/search"):
        return { marginLeft: "7px" };
      case screenType?.includes("/inbox") || screenType?.includes("/inbox"):
        return { marginLeft: "5px" };

      default:
        return { marginLeft: "8px" };
    }
  };

  useEffect(() => {
    return () => {
      if (!window.location.href.includes("create-contract") && sessionFormData && Object.keys(sessionFormData) != 0) {
        clearSessionFormData();
      }
    };
  }, [location]);

  return (
    <Switch>
      <React.Fragment>
        <div className="ground-container">
          <div style={getBreadCrumbStyles(window.location.href)}>
            <ContractsBreadCrumbs location={location} />
          </div>
          <PrivateRoute path={`${path}/create-contract`} component={() => <CreateWorkOrderComponent parentRoute={path}/>} />
          <PrivateRoute path={`${path}/search-contract`} component={() => <SearchContracts />} />
          <PrivateRoute path={`${path}/view-contract`} component={() => <ViewContract />} />
          <PrivateRoute
            path={`${path}/inbox`}
            component={() => (
              <Inbox parentRoute={path} businessService="WORKS" filterComponent="contractInboxFilter" initialStates={{}} isInbox={true} />
            )}
          />
        </div>
      </React.Fragment>
    </Switch>
  );
};

export default App;
