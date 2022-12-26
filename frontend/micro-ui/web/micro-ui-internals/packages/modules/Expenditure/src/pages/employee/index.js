import React from "react";
import { useTranslation } from "react-i18next";
import { PrivateRoute, BreadCrumb } from "@egovernments/digit-ui-react-components";
import { Switch, useLocation } from "react-router-dom";
import BILLInbox from "./billInbox";
import CreateBill from "./CreateBill";

const ExpenditureBreadCrumbs = ({ location }) => {
    const { t } = useTranslation();

    const search = useLocation().search;
    const fromScreen = new URLSearchParams(search).get("from") || null;
    const crumbs = [
        {
            path: "/works-ui/employee",
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
        }
    ];
    return <BreadCrumb crumbs={crumbs} spanStyle={{ maxWidth: "min-content" }} />;

}

const App = ({ path }) => {
    const location = useLocation();
    const locationCheck =
        window.location.href.includes("/employee/ws/new-application");
    const ViewBillsMenuComponent = Digit?.ComponentRegistryService?.getComponent("ViewBillsMenu");
    const ViewBillsComponent = Digit?.ComponentRegistryService?.getComponent("ViewBills");

    const getBreadCrumbStyles = (screenType) => {
        // Defining 4 types for now -> create,view,inbox,search

        switch (true) {
            case (screenType?.includes("/create")):
                return { marginLeft: "10px" }

            case (screenType?.includes("/view")):
                return { marginLeft: "4px" }

            case (screenType?.includes("/search")):
                return { marginLeft: "7px" }
            case (screenType?.includes("/inbox") || screenType?.includes("/inbox")):
                return { marginLeft: "5px" }

            default:
                return { marginLeft: "8px" }
        }
    }
    return (
        <Switch>
            <React.Fragment>
                <div>
                    <div style={getBreadCrumbStyles(window.location.href)}>
                        <ExpenditureBreadCrumbs location={location} />
                    </div>
                    <PrivateRoute path={`${path}/create-application`} component={() => <div>Hi</div>} />
                    <PrivateRoute path={`${path}/billinbox`} component={() => <BILLInbox parentRoute={path} businessService="WORKS" filterComponent="billInboxFilter" initialStates={{}} isInbox={true} />}/>
                    <PrivateRoute path={`${path}/view-bills/bills`} component={ViewBillsComponent}></PrivateRoute>
                    <PrivateRoute path={`${path}/view-bills/menu`} component={ViewBillsMenuComponent}></PrivateRoute>
                    <PrivateRoute path={`${path}/create-bill`} component={() => <CreateBill parentRoute={path} />}/>
                </div>
            </React.Fragment>
        </Switch>
    );
};

export default App;