import { fetchSHGBillRecords } from "../../services/molecules/Expenditure/Bills/SHGBill"

const useviewSHGBill = () => {
    return fetchSHGBillRecords();
}

export default useviewSHGBill;