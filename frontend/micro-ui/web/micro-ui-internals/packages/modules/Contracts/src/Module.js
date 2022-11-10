import { Loader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useRouteMatch } from "react-router-dom";
import contractInboxFilter from "./components/inbox/NewInboxFilter";
import EmployeeApp from "./pages/employee";

const ContractsModule = ({ stateCode, userType, tenants }) => {
    const moduleCode = ["Contracts"];
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
    ContractsModule,
    contractInboxFilter
};

export const initContractsComponents = () => {
    Object.entries(componentsToRegister).forEach(([key, value]) => {
        Digit.ComponentRegistryService.setComponent(key, value);
    });
};