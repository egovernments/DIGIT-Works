import React from "react";
import { useTranslation } from "react-i18next";
import { PrivateRoute, BreadCrumb } from "@egovernments/digit-ui-react-components";
import { Switch, useLocation } from "react-router-dom";

const AttendenceMgmtBreadCrumbs = ({ location }) => {
    const { t } = useTranslation();

    const search = useLocation().search;
    const fromScreen = new URLSearchParams(search).get("from") || null;
    const crumbs = [
        {
            path: "/works-ui/employee",
            content: t("WORKS_WMS"),
            show: true,
        },
        // {
        //     path: `/${window.contextPath}/employee/works/loiinbox`,
        //     content: fromScreen ? `${t(fromScreen)} / ${t("WORKS_LOI_INBOX")}` : t("WORKS_LOI_INBOX"),
        //     show: location.pathname.includes("/works/loiinbox") ? true : false,
        //     isBack: fromScreen && true,
        // },
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
                        <AttendenceMgmtBreadCrumbs location={location} />
                    </div>
                    <PrivateRoute path={`${path}/create-application`} component={() => <div>Hi</div>} />
                    {/* <PrivateRoute path={`${path}/create-contractor`} component={() => <NewApplication {...path} />} />
                    <PrivateRoute path={`${path}/search-Estimate-approved`} component={(props) => <Search {...props} parentRoute={path} />} />
                    <PrivateRoute path={`${path}/inbox`} component={() => (<Inbox parentRoute={path} businessService="WORKS" filterComponent="WORKS_INBOX_FILTER" initialStates={{}} isInbox={true} />)} />
                    <PrivateRoute path={`${path}/LOIInbox`} component={() => (<LOIInbox parentRoute={path} businessService="LOI" filterComponent="LOI_INBOX_FILTER" initialStates={{}} isInbox={true} />)} />

                    <PrivateRoute path={`${path}/search-estimate`} component={() => <SearchEstimate />} />
                    <PrivateRoute path={`${path}/search-approved-estimate`} component={() => <SearchApprovedSubEstimate />} />
                    <PrivateRoute path={`${path}/create-loi`} component={() => <CreateLOI {...{ path }} />} />
                    <PrivateRoute path={`${path}/create-estimate`} component={() => <CreateEstimate {...{ path }} />} />
                    <PrivateRoute path={`${path}/modify-estimate`} component={() => <ModifyEstimate {...{ path }} />} />
                    <PrivateRoute path={`${path}/view-estimate`} component={() => <ViewEstimate {...{ path }} />} />
                    <PrivateRoute path={`${path}/view-loi`} component={() => <ViewLOI {...{ path }} />} />
                    <PrivateRoute path={`${path}/response`} component={() => <Response {...{ path }} />} />
                    <PrivateRoute path={`${path}/download`} component={() => <HandleDownloadPdf {...{ path }} />} /> */}
                </div>
            </React.Fragment>
        </Switch>
    );
};

export default App;
