import { fetchPOBillRecords } from "../../services/molecules/Expenditure/Bills/POBill"

const useViewPOBill = () => {
    return fetchPOBillRecords();
}

export default useViewPOBill;