import { useQuery } from "react-query";
import { BillsSearch } from "../../services/molecules/Expenditure/Search";

const useViewPurchaseBillDetails = (t, billCriteria, config = {}) => {
    let pagination = {
            "limit": 10,
            "offSet": 0,
            "sortBy": "",
            "order": "ASC"
    }
    return useQuery(
        ["VIEW_PURCHASE_BILL"], //TODO: Add other unique ID here while integrating APIs
        () => BillsSearch.viewPurchaseBillDetails({t, billCriteria, pagination}),
        config
    );
}

export default useViewPurchaseBillDetails;