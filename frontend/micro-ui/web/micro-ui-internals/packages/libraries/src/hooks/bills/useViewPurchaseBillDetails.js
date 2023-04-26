import { useQuery } from "react-query";
import { BillsSearch } from "../../services/molecules/Expenditure/Search";

const useViewPurchaseBillDetails = (tenantId, t, billCriteria, headerLocale, config = {}, metaData = {}) => {
    let pagination = {
            "limit": 10,
            "offSet": 0,
            "sortBy": "",
            "order": "ASC"
    }
    return useQuery(
        ["VIEW_PURCHASE_BILL"], //TODO: Add other unique ID here while integrating APIs
        () => BillsSearch.viewPurchaseBillDetails({tenantId, t, billCriteria, pagination, headerLocale, metaData}),
        config
    );
}

export default useViewPurchaseBillDetails;