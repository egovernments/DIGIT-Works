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
import ViewEstimateComponent from "./components/ViewEstimateComponent";
import TotalEstAmount from "./pageComponents/TotalEstAmount";
import LabourAnalysis from "./pageComponents/LabourAnalysis";
import ViewTotalEstAmount from "./components/ViewTotalEstAmount";
import ViewLabourAnalysis from "./components/ViewLabourAnalysis"
import searchSor from "./pageComponents/searchSor";
import EstimateDropdown from "./pageComponents/EstimateDropdown";
import SearchBar from "./pageComponents/SearchBar";
import OverheadDetailsTable from "./components/OverheadDetailsTable";
import ViewAnalysisStatement from "./pageComponents/ViewAnalysisStatement";
import UpdateDetailedEstimate from "./pages/employee/Estimates/CreateDetailedEstimate/UpdateDetailedEstimate";
import CreateRevisionDetailedEstimate from "./pages/employee/Estimates/CreateDetailedEstimate/CreateRevisionDetailedEstimate";
import UpdateRevisionDetailedEstimate from "./pages/employee/Estimates/CreateDetailedEstimate/UpdateRevisionDetailedEstimate";
import EstimateMeasureTableWrapper from "./pageComponents/EstimateMeasureTableWrapper";
import ViewStatement from "./components/ViewStatement";
import searchTemplate from "./pageComponents/searchTemplate";

const EstimateModule = ({ stateCode, userType, tenants }) => {
    const { path, url } = useRouteMatch();
    const language = Digit.StoreData.getCurrentLanguage();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const moduleCode = ["Estimate","common-masters","workflow","mdms",tenantId];
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
    OverheadsTable,
    ViewEstimatePage: ViewEstimateComponent,
    TotalEstAmount,
    LabourAnalysis,
    ViewTotalEstAmount,
    ViewLabourAnalysis,
    searchSor,
    EstimateDropdown,
    SearchBar,
    OverheadDetailsTable,
    ViewAnalysisStatement,
    UpdateDetailedEstimate,
    CreateRevisionDetailedEstimate,
    UpdateRevisionDetailedEstimate,
    EstimateMeasureTableWrapper,
    ViewStatement,
    searchTemplate

};

export const initEstimateComponents = () => {
    Object.entries(componentsToRegister).forEach(([key, value]) => {
        Digit.ComponentRegistryService.setComponent(key, value);
    });
};