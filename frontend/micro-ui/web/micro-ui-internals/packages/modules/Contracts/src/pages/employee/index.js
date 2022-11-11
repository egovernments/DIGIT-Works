import React from "react";
import { useTranslation } from "react-i18next";
import { PrivateRoute, BreadCrumb } from "@egovernments/digit-ui-react-components";
import { Switch, useLocation } from "react-router-dom";
import CreateContract from "./CreateContract";
import Inbox from "./Inbox";

const ContractsBreadCrumbs = ({ location }) => {
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
            path: `/${window.contextPath}/employee/contracts/create-contract`,
            content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_CREATE_CONTRACT")}` : t("WORKS_CREATE_CONTRACT"),
            show: location.pathname.includes("/contracts/create-contract") ? true : false,
            isBack: fromScreen && true,
        },
        {
            path: `/${window.contextPath}/employee/contracts/inbox`,
            content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_CONTRACTS")}` : t("WORKS_CONTRACTS"),
            show: location.pathname.includes("/contracts/inbox") ? true : false,
            isBack: fromScreen && true,
        },
    ];
    return <BreadCrumb crumbs={crumbs} spanStyle={{ maxWidth: "min-content" }} />;

}

const App = ({ path }) => {
    const location = useLocation();
    const locationCheck =
        window.location.href.includes("/employee/ws/new-application");
    const getBreadCrumbStyles = (screenType) => {
        // Defining 4 types for now -> create,view,inbox,search

        switch (true) {
            case (screenType?.includes("/create")):
                return { marginLeft: "10px" }

            case (screenType?.includes("/view")):
                return { marginLeft: "4px" }

            case (screenType?.includes("/search")):
                return { marginLeft: "7px" }
            case (screenType?.includes("/inbox") || screenType?.includes("/LOIInbox")):
                return { marginLeft: "5px" }

            default:
                return { marginLeft: "8px" }
        }
    }
    return (
        <Switch>
            <React.Fragment>
                <div className="ground-container">
                    <div style={getBreadCrumbStyles(window.location.href)}>
                        <ContractsBreadCrumbs location={location} />
                    </div>
                    <PrivateRoute path={`${path}/create-application`} component={() => <div>Hi</div>} />
                    <PrivateRoute path={`${path}/create-contract`} component={() => <CreateContract/>}/>
                    <PrivateRoute path={`${path}/inbox`} component={() => <Inbox parentRoute={path} businessService="WORKS" filterComponent="contractInboxFilter" initialStates={{}} isInbox={true} />}/>
                </div>
            </React.Fragment>
        </Switch>
    );
};

export default App;