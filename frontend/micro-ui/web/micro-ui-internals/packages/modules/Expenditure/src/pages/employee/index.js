import React from "react";
import { useTranslation } from "react-i18next";
import { PrivateRoute, BreadCrumb, AppContainer } from "@egovernments/digit-ui-react-components";
import { Switch, useLocation } from "react-router-dom";
import CreateBill from "./CreateBill";
import ViewSupervisionbill from "./Bills/ViewSupervisionbill";

const ExpenditureBreadCrumbs = ({ location }) => {
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
        path: `/${window.contextPath}/employee/expenditure/billinbox`,
        content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_BILLING_MGMT")}` : t("WORKS_BILLING_MGMT"),
        show: location.pathname.includes("/expenditure/billinbox") ? true : false,
        isBack: fromScreen && true,
      },
      {
        path: `/${window.contextPath}/employee/expenditure/view-bills/menu`,
        content: fromScreen ? `${t(fromScreen)} / ${t("EXP_VIEW_BILLS_MENU")}` : t("EXP_VIEW_BILLS_MENU"),
        show: location.pathname.includes("/expenditure/view-bills/menu") ? true : false,
        isBack: fromScreen && true,
      },
      {
        path: `/${window.contextPath}/employee/expenditure/view-bills/bills`,
        content: fromScreen ? `${t(fromScreen)} / ${t("EXP_VIEW_BILL")}` : t("EXP_VIEW_BILL"),
        show: location.pathname.includes("/expenditure/view-bills/bills") ? true : false,
        isBack: fromScreen && true,
      },
      {
        path: `/${window.contextPath}/employee/expenditure/create-bill`,
        content: fromScreen ? `${t(fromScreen)} / ${t("EXP_CREATE_BILL")}` : t("EXP_CREATE_BILL"),
        show: location.pathname.includes("/expenditure/create-bill") ? true : false,
        isBack: fromScreen && true,
      },
      {
        path: `/${window.contextPath}/employee/expenditure/inbox`,
        content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_BILLING_MGMT")}` : t("WORKS_BILLING_MGMT"),
        show: location.pathname.includes("/expenditure/inbox") ? true : false,
        isBack: fromScreen && true,
      },
      {
        path: `/${window.contextPath}/employee/expenditure/search-bill`,
        content: fromScreen ? `${t(fromScreen)} / ${t("EXP_SEARCH_BILL")}` : t("EXP_SEARCH_BILL"),
        show: location.pathname.includes("/expenditure/search-bill") ? true : false,
        isBack: fromScreen && true,
      },
      {
        path: `/${window.contextPath}/employee/expenditure/view-bill`,
        content: fromScreen ? `${t(fromScreen)} / ${t("EXP_VIEW_BILL")}` : t("EXP_VIEW_BILL"),
        show: location.pathname.includes("/expenditure/view-bill") ? true : false,
        isBack: fromScreen && true,
      },
      {
        path: `/${window.contextPath}/employee/expenditure/supervision-bill-details`,
        content: fromScreen ? `${t(fromScreen)} / ${t("BILLS_SUPERVISION")}` : t("BILLS_SUPERVISION"),
        show: location.pathname.includes("/expenditure/supervision-bill-details") ? true : false,
        isBack: fromScreen && true,
      },
    ];
    return <BreadCrumb crumbs={crumbs} spanStyle={{ maxWidth: "min-content" }} />;
}

const App = ({ path }) => {
    
    const location = useLocation();
    const ViewBillsMenuComponent = Digit?.ComponentRegistryService?.getComponent("ViewBillsMenu");
    const ViewBillsComponent = Digit?.ComponentRegistryService?.getComponent("ViewBills");
    const BillInbox = Digit?.ComponentRegistryService?.getComponent("BillInbox");
    const SearchBill = Digit?.ComponentRegistryService?.getComponent("SearchBill");
    const ViewBill = Digit?.ComponentRegistryService?.getComponent("ViewBill");

    return (
      <Switch>
        <AppContainer className="ground-container">
          <React.Fragment>
            <ExpenditureBreadCrumbs location={location} />
          </React.Fragment>
          <PrivateRoute path={`${path}/create-application`} component={() => <div>Hi</div>} />
          <PrivateRoute path={`${path}/billinbox`} component={() => <BillInbox parentRoute={path} />} />
          <PrivateRoute path={`${path}/view-bills/bills`} component={ViewBillsComponent}></PrivateRoute>
          <PrivateRoute path={`${path}/view-bills/menu`} component={ViewBillsMenuComponent}></PrivateRoute>
          <PrivateRoute path={`${path}/create-bill`} component={() => <CreateBill parentRoute={path} />} />

          <PrivateRoute path={`${path}/inbox`} component={() => <BillInbox parentRoute={path} />} />
          <PrivateRoute path={`${path}/search-bill`} component={() => <SearchBill parentRoute={path} />} />
          <PrivateRoute path={`${path}/view-bill`} component={() => <ViewBill parentRoute={path} />} />
          <PrivateRoute path={`${path}/supervision-bill-details`} component={() => <ViewSupervisionbill parentRoute={path} />} />
        </AppContainer>
      </Switch>
    );
};

export default App;