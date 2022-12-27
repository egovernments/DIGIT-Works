import { fetchPOBillRecords } from "../../services/molecules/Expenditure/Bills/POBill";
import { fetchWOBillRecords } from "../../services/molecules/Expenditure/Bills/WOBill";
import { fetchSHGBillRecords } from "../../services/molecules/Expenditure/Bills/SHGBill";

const useViewBills = (billType) => {
    if(billType === "PO") {
        return fetchPOBillRecords();
    }else if(billType === "SHG") {
        return fetchSHGBillRecords();
    }
    return fetchWOBillRecords();
}

export default useViewBills;