import { useQuery } from "react-query";
import { WorksService } from "../../services/elements/Works";

const useSearchOrg = (payload) => {
    return useQuery(["ORG_SEARCH"], () => WorksService.searchOrg(payload))
}

export default useSearchOrg;