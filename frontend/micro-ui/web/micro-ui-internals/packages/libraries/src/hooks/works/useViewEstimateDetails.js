import { WorksSearch } from "../../services/molecules/Works/Search";
import { useQuery } from "react-query";

const useViewEstimateDetails = (t, tenantId="pb.amritsar", applicationNumber="abcd", config = {}) => {
    return useQuery(
        ["ESTIMATE_WORKS_SEARCH", "ESTIMATE_SEARCH", tenantId, applicationNumber],
        () => WorksSearch.viewEstimateScreen(t, tenantId, applicationNumber),
        config
    );
}

export default useViewEstimateDetails