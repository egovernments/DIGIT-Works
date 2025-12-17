import React, { useEffect } from "react";
import { useTranslation } from "react-i18next";
import { PrivateRoute, AppContainer } from "@egovernments/digit-ui-react-components";
import { BreadCrumb } from "@egovernments/digit-ui-components";
import { Switch, useLocation } from "react-router-dom";
import CreateContract from "./CreateContract";
import Inbox from "./ContractsInbox/Inbox.js";
import SearchContractDetails from "./SearchContractDetails";
import ViewContractDetails from "./ViewContractDetails";

const ContractsBreadCrumbs = ({ location }) => {
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
      path: `/${window.contextPath}/employee/contracts/contract-details`,
      content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_CONTRACTS")}` : t("WORKS_CONTRACTS"),
      show: location.pathname.includes("/contracts/contract-details") ? true : false,
      isBack: fromScreen && true,
    },
    {
      path: `/${window.contextPath}/employee/contracts/create-time-extension-response`,
      content: fromScreen ? `${t(fromScreen)} / ${t("BREAD_TE_RESPONSE")}` : t("BREAD_TE_RESPONSE"),
      show: location.pathname.includes("/contracts/create-time-extension-response") ? true : false,
      isBack: fromScreen && true,
    },
  ];
  return <BreadCrumb crumbs={crumbs} />;
};

const App = ({ path }) => {
  const location = useLocation();
  const ContractSession = Digit.Hooks.useSessionStorage("CONTRACT_CREATE", {});
  const [sessionFormData, setSessionFormData, clearSessionFormData] = ContractSession;
  const locationCheck = window.location.href.includes("/employee/ws/new-application");
  const CreateWorkOrderComponent = Digit?.ComponentRegistryService?.getComponent("CreateWorkOrder");
  const CreateWOResponseComponent = Digit?.ComponentRegistryService?.getComponent("CreateWOResponse");
  const TimeExtensionResponse = Digit?.ComponentRegistryService?.getComponent("TimeExtensionResponse");

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
    if (!window.location.href.includes("create-contract") && sessionFormData && Object.keys(sessionFormData) != 0) {
      clearSessionFormData();
    }
  }, [location]);

  return (
    <Switch>
      <AppContainer>
        <React.Fragment>
          <ContractsBreadCrumbs location={location} />
        </React.Fragment>
        <PrivateRoute path={`${path}/search-contract`} component={() => <SearchContractDetails />} />
        <PrivateRoute path={`${path}/contract-details`} component={() => <ViewContractDetails />} />
        <PrivateRoute path={`${path}/create-contract`} component={() => <CreateWorkOrderComponent parentRoute={path} />} />
        <PrivateRoute path={`${path}/create-contract-response`} component={() => <CreateWOResponseComponent />} />
        <PrivateRoute path={`${path}/create-time-extension-response`} component={() => <TimeExtensionResponse />} />
        <PrivateRoute
          path={`${path}/inbox`}
          component={() => (
            <Inbox parentRoute={path} businessService="WORKS" filterComponent="contractInboxFilter" initialStates={{}} isInbox={true} />
          )}
        />
      </AppContainer>
    </Switch>
  );
};

export default App;