import { Loader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useRouteMatch } from "react-router-dom";
import contractInboxFilter from "./components/inbox/NewInboxFilter";
import EmployeeApp from "./pages/employee";
import SearchContractApplication from "./components/SearchContract";
import ContractsCard from "./components/ContractsCard";
import CreateWorkOrder from "./pages/employee/CreateWorkOrder";
import WOTermsAndConditions from "./components/WOTermsAndConditions";
import ViewEstimateDocs from "./components/ViewEstimateDocs";

const ContractsModule = ({ stateCode, userType, tenants }) => {
    const { path, url } = useRouteMatch();
    const language = Digit.StoreData.getCurrentLanguage();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const moduleCode = ["Contracts","common-masters",tenantId];
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
    ContractsModule,
    contractInboxFilter,
    SearchContractApplication,
    ContractCard:ContractsCard,
    CreateWorkOrder,
    WOTermsAndConditions,
    ViewEstimateDocs
};

export const initContractsComponents = () => {
    Object.entries(componentsToRegister).forEach(([key, value]) => {
        Digit.ComponentRegistryService.setComponent(key, value);
    });
};