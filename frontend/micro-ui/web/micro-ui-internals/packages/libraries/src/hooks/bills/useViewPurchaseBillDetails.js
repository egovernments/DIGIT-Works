import { Search } from "../../services/molecules/Expenditure/Bills/Search";

const useViewPurchaseBillDetails = ({config : {}}) => {
    return useQuery(
        ["VIEW_PURCHASE_BILL"], //TODO: Add other unique ID here while integrating APIs
        () => Search.viewPurchaseBillDetails(),
        config
    );
}

export default useViewPurchaseBillDetails;