import { WorksSearch } from "../../services/molecules/Works/Search";
import { useQuery } from "react-query";

const useViewLOIDetails = (t, tenantId = "pb.amritsar", applicationNumber = "abcd", config = {}) => {
    return useQuery(
        ["LOI_WORKS_SEARCH", "LOI_SEARCH", tenantId, applicationNumber],
        () => WorksSearch.viewLOIScreen(t, tenantId, applicationNumber),
        config
    );
}

export default useViewLOIDetails