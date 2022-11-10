import { Loader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useRouteMatch } from "react-router-dom";
import AttendenceMgmtCard from "./components/AttendenceMgmtCard"
import CitizenApp from "./pages/citizen";
import mobileInbox from "./components/markAttendenceInbox/mobileInbox";
import ViewRegister from "./pages/citizen/viewRegister/ViewRegister";
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

    return <CitizenApp path={path} stateCode={stateCode} />;
};

const componentsToRegister = {
    AttendenceMgmtCard,
    AttendenceMgmtModule,
    AttendenceMgmtInbox: mobileInbox,
    ViewRegister,
};

export const initAttendenceMgmtComponents = () => {
    Object.entries(componentsToRegister).forEach(([key, value]) => {
        Digit.ComponentRegistryService.setComponent(key, value);
    });
};
