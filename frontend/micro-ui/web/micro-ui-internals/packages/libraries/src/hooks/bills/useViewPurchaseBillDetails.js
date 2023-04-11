import { useQuery } from "react-query";
import { Search } from "../../services/molecules/Expenditure/Bills/Search";

const useViewPurchaseBillDetails = (t, config = {}) => {
    return useQuery(
        ["VIEW_PURCHASE_BILL"], //TODO: Add other unique ID here while integrating APIs
        () => Search.viewPurchaseBillDetails(t),
        config
    );
}

export default useViewPurchaseBillDetails;