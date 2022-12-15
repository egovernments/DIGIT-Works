import { Loader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useRouteMatch } from "react-router-dom";
import EmployeeApp from "./pages/employee"
import billInboxFilter from "./components/BillInbox/NewInboxFilter"
import ExpenditureCard from "./components/ExpenditureCard";
import ViewBillsMenu from "./pages/employee/viewBills/viewBillsMenu";
import ViewPOBills from "./pages/employee/viewBills/viewPOBills";
import ViewSHGBills from "./pages/employee/viewBills/viewSHGBills";
import ViewWOBills from "./pages/employee/viewBills/viewWOBills";

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
    ExpenditureCard,
    ViewBillsMenu,
    ViewPOBills,
    ViewSHGBills,
    ViewWOBills
};

export const initExpenditureComponents = () => {
    Object.entries(componentsToRegister).forEach(([key, value]) => {
        Digit.ComponentRegistryService.setComponent(key, value);
    });
};