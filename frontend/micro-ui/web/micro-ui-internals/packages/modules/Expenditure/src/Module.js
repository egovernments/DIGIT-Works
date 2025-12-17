import { Loader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useRouteMatch } from "react-router-dom";
import EmployeeApp from "./pages/employee"
import billInboxFilter from "./components/BillInbox/NewInboxFilter"
import ExpenditureCard from "./components/ExpenditureCard";
import ViewBillsMenu from "./pages/employee/viewBills/viewBillsMenu";
import DeductionsTable from "./pageComponents/DeductionsTable";
import ViewBills from "./pages/employee/viewBills/viewBills";
import BillInbox from "./pages/employee/Bills/BillInbox";
import SearchBill from "./pages/employee/Bills/SearchBill";
import PurchaseBill from "./pages/employee/CreateBills/PurchaseBill";
import CreatePurchaseBillResponse from "./pages/employee/CreateBills/CreatePurchaseBillResponse";
import ViewWageBill from "./pages/employee/Bills/ViewWageBill";
import TotalBillAmount from "./pageComponents/TotalBillAmount";
import PayableAmt from "./components/PayableAmt";
import DownloadBill from "./pages/employee/Bills/DownloadBill";
import ViewPurchaseBill from "./components/ViewBill/ViewPurchaseBill";
import TotalBillAmountView from "./pageComponents/ViewTotalBillAmount";
import ViewPaymentInstruction from "./pages/employee/Payments/viewPaymentInstruction";
import ViewPayment from "./pages/employee/Payments/ViewPayment";
import MBDetailes from "./components/CreateBill/MBDetailes";
import PaymentTrackerSearch from "./pages/employee/paymentTrackerSearch";
import PaymentTrackerView from "./pages/employee/paymentTrackerView";
import PaymentTrackerTable from "./components/PaymentTrackerTable";
import ViewTotalPaymentAmount from "./components/ViewTotalPaymentAmount";

const ExpenditureModule = ({ stateCode, userType, tenants }) => {
    
    const { path, url } = useRouteMatch();
    const language = Digit.StoreData.getCurrentLanguage();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const moduleCode = ["Expenditure","common-masters","workflow","ifms",tenantId];
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
    PurchaseBill,
    CreatePurchaseBillResponse,
    DeductionsTable,
    TotalBillAmount,
    //new
    ViewWageBill,
    ViewPurchaseBill,
    PayableAmt,
    DownloadBill,
    TotalBillAmountView,
    //new
    ViewPaymentInstruction,
    ViewPayment,
    MBDetailes,

    //new
    PaymentTrackerSearch,
    PaymentTrackerView,
    PaymentTrackerTable,
    ViewTotalPaymentAmount
};

export const initExpenditureComponents = () => {
    Object.entries(componentsToRegister).forEach(([key, value]) => {
        Digit.ComponentRegistryService.setComponent(key, value);
    });
};