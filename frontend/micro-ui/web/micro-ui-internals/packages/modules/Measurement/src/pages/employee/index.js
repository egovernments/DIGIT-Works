import React,{useEffect} from "react";
import { useTranslation } from "react-i18next";
import { PrivateRoute, BreadCrumb } from "@egovernments/digit-ui-react-components";
import { Switch, useLocation } from "react-router-dom";

const MeasurementBreadCrumbs = ({ location }) => {
    const { t } = useTranslation();

    const search = useLocation().search;
    const fromScreen = new URLSearchParams(search).get("from") || null;
    
    const crumbs = [
        {
            path: `/${window?.contextPath}/employee`,
            content: t("WORKS_MUKTA"),
            show: true,
        },
    ];
    return <BreadCrumb crumbs={crumbs} spanStyle={{ maxWidth: "min-content" }} />;

}

const App = ({ path }) => {
    const location = useLocation();

    return (
        <Switch>
            <React.Fragment>
                <div>
                    <div>
                        <MeasurementBreadCrumbs location={location} />
                    </div>
                    <PrivateRoute path={`${path}/create-measurement`} component={() => <CreateMeasurement {...{ path }}  />} />
                </div>
            </React.Fragment>
        </Switch>
    );
};

export default App;