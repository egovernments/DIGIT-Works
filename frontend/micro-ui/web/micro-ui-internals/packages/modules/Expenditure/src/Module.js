import { Loader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useRouteMatch } from "react-router-dom";
import EmployeeApp from "./pages/employee"
import billInboxFilter from "./components/BillInbox/NewInboxFilter"
import ExpenditureCard from "./components/ExpenditureCard";
import ViewBillsMenu from "./pages/employee/viewBills/viewBillsMenu";
import DeductionsTable from "./pageComponents/DeductionsTable";
import TotalBillAmount from "./pageComponents/TotalBillAmount";
import ViewBills from "./pages/employee/viewBills/viewBills";
import BillInbox from "./pages/employee/Bills/BillInbox";
import SearchBill from "./pages/employee/Bills/SearchBill";
import ViewBill from "./pages/employee/Bills/ViewBill";
import PurchaseBill from "./pages/employee/CreateBills/PurchaseBill";

const ExpenditureModule = ({ stateCode, userType, tenants }) => {
    
    const { path, url } = useRouteMatch();
    const language = Digit.StoreData.getCurrentLanguage();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const moduleCode = ["Expenditure","common-masters",tenantId];
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
    ViewBills,
    //new
    BillInbox,
    SearchBill,
    ViewBill,
    PurchaseBill,
    DeductionsTable,
    TotalBillAmount,
};

export const initExpenditureComponents = () => {
    Object.entries(componentsToRegister).forEach(([key, value]) => {
        Digit.ComponentRegistryService.setComponent(key, value);
    });
};