import { useQuery } from "react-query";
import { Search } from "../services/Search"

const useSearchEstimate = (tenantId, filters, config={cacheTime : 0} ) => {
    return useQuery(
        ["SEARCH_ESTIMATE", tenantId, filters?.projectId],
        ()=> Search.searchEstimate(tenantId, filters),
        {
            ...config,
            enabled : !!filters?.projectId
        }
    );
}

export default useSearchEstimate;