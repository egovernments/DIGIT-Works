import { Loader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useRouteMatch } from "react-router-dom";
import EmployeeApp from "./pages/employee"
import EstimateCard from "./components/EstimateCard";
import ViewProject from "./pageComponents/ViewProject";
import SOR from "./pageComponents/SOR";
import NonSORTable from "./pageComponents/NonSORTable";
import EstimateTemplate from "./pageComponents/EstimateTemplate";
import OverheadsTable from "./pageComponents/OverheadsTable";

const EstimateModule = ({ stateCode, userType, tenants }) => {
    const moduleCode = ["Estimate","Works"];
    const { path, url } = useRouteMatch();
    const language = Digit.StoreData.getCurrentLanguage();
    const { isLoading, data: store } = Digit.Services.useStore({
        stateCode,
        moduleCode,
        language,
    });

    if (isLoading) {
        return <Loader />;
    }

    return <EmployeeApp path={path} stateCode={stateCode} />;
};

const componentsToRegister = {
    EstimateModule,
    EstimatesCard:EstimateCard,
    ViewProject,
    EstimateTemplate,
    SOR,
    NonSORTable,
    OverheadsTable
};

export const initEstimateComponents = () => {
    Object.entries(componentsToRegister).forEach(([key, value]) => {
        Digit.ComponentRegistryService.setComponent(key, value);
    });
};