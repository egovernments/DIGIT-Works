import { useQuery } from "react-query";
import { WorksService } from "../../services/elements/Works";

//using this hook for searching only one estimate with estimateNumber
const useSearchBill = ({ data, config = {} }) => {

    return useQuery(
        ["BILL_SEARCH",`${data.billCriteria.billNumbers[0]}`, data],
        () => WorksService.searchBill(data),
        {
            ...config,
            cacheTime:0
        }
    )
}

export default useSearchBill;
