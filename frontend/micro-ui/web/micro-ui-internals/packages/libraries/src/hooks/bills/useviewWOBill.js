import { fetchWOBillRecords } from "../../services/molecules/Expenditure/Bills/WOBill"

const useviewWOBill = () => {
    return fetchWOBillRecords();
}

export default useviewWOBill;