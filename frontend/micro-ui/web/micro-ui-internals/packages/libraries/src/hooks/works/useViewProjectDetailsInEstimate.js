import { WorksSearch } from "../../services/molecules/Works/Search";
import { useQuery } from "react-query";

const useViewProjectDetailsInEstimate = (t, tenantId="", estimateNumber="", config = { cacheTime: 0 }) => {
    return useQuery(
        ["ESTIMATE_WORKS_CREATE_NEW", "ESTIMATE_CREATE", tenantId, estimateNumber],
        () => WorksSearch.viewProjectDetailsScreenInCreateEstimate(t, tenantId, estimateNumber),
        config
    );
}

export default useViewProjectDetailsInEstimate