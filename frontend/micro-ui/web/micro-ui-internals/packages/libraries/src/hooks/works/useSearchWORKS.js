import { useQuery } from "react-query";
import { WorksService } from "../../services/elements/Works";

const useSearchWORKS=({ tenantId, filters, config = {} })=>
    useQuery(["WORKS_ESTIMATE_SEARCH", tenantId, ...Object.keys(filters)?.map((e) => filters?.[e])],
    async()=>await WorksService.approvedEstimateSearch({tenantId,filters}),
    {
        ...config
    })

export default useSearchWORKS; 