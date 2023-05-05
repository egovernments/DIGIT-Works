import { useQuery } from "react-query";
import { WorksService } from "../../services/elements/Works";

//using this hook for searching only one estimate with estimateNumber
const useSearchBill = ({ data, config = {} }) => useQuery(
    ["BILL_SEARCH", data],
    () => WorksService.searchBill(data),
    {
        ...config,
        cacheTime:0
    }
)

export default useSearchBill;
