import { Loader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useRouteMatch } from "react-router-dom";

import EmployeeApp from "./pages/employee";

const AttendenceMgmtModule = ({ stateCode, userType, tenants }) => {
    const moduleCode = ["AttendenceMgmt"];
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
    AttendenceMgmtCard,
    AttendenceMgmtModule,
};

export const initAttendenceMgmtComponents = () => {
    Object.entries(componentsToRegister).forEach(([key, value]) => {
        Digit.ComponentRegistryService.setComponent(key, value);
    });
};
