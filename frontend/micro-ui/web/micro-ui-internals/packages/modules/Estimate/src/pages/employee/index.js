import React from "react";
import { useTranslation } from "react-i18next";
import { PrivateRoute, BreadCrumb } from "@egovernments/digit-ui-react-components";
import { Switch, useLocation } from "react-router-dom";

const EstimateBreadCrumbs = ({ location }) => {
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
        }
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
                        <EstimateBreadCrumbs location={location} />
                    </div>
                    <PrivateRoute path={`${path}/create`} component={() => <div>Hi</div>} />
                    <PrivateRoute path={`${path}/search`} component={() => <div>Search</div>} />
                    <PrivateRoute path={`${path}/inbox`} component={() =><div>Inbox</div> }/>
                </div>
            </React.Fragment>
        </Switch>
    );
};

export default App;