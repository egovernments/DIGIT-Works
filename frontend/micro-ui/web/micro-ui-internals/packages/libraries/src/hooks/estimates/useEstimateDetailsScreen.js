import { WorksSearch } from "../../services/molecules/Works/Search";
import { useQuery } from "react-query";

const useEstimateDetailsScreen = (t, tenantId, estimateNumber, config ) => {
    return useQuery(
        ["ESTIMATE_WORKS_SEARCH", "ESTIMATE_SEARCH", tenantId, estimateNumber],
        () => WorksSearch.viewEstimateScreen(t, tenantId, estimateNumber),
        config
    );
}

export default useEstimateDetailsScreen