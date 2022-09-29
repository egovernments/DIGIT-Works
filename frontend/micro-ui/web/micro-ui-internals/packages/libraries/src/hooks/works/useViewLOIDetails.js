import { WorksSearch } from "../../services/molecules/Works/Search";
import { useQuery } from "react-query";

const useViewLOIDetails = (t, tenantId, LOINumber,subEstiamteNumber, config = {}) => {
    return useQuery(
        ["LOI_WORKS_SEARCH", "LOI_SEARCH", tenantId, LOINumber, subEstiamteNumber],
        () => WorksSearch.viewLOIScreen(t, tenantId, LOINumber, subEstiamteNumber),
        config
    );
}

export default useViewLOIDetails