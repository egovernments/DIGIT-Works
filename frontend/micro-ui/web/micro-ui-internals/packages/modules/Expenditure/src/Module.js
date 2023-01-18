import { Loader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useRouteMatch } from "react-router-dom";
import EmployeeApp from "./pages/employee"
import billInboxFilter from "./components/BillInbox/NewInboxFilter"
import ExpenditureCard from "./components/ExpenditureCard";
import ViewBillsMenu from "./pages/employee/viewBills/viewBillsMenu";
import ViewBills from "./pages/employee/viewBills/viewBills";

const ExpenditureModule = ({ stateCode, userType, tenants }) => {
    const moduleCode = ["Expenditure"];
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
    ExpenditureModule,
    billInboxFilter,
    ExpCard:ExpenditureCard,
    ViewBillsMenu,
    ViewBills
};

export const initExpenditureComponents = () => {
    Object.entries(componentsToRegister).forEach(([key, value]) => {
        Digit.ComponentRegistryService.setComponent(key, value);
    });
};