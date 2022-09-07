import { useQuery } from "react-query";
import { WORKSService } from "../../services/elements/WORKS";

const useSearchWORKS=({ tenantId, filters, config = {}, bussinessService, t, shortAddress = false })=>
    useQuery(["WORKS_ESTIMATE_SEARCH", tenantId, ...Object.keys(filters)?.map((e) => filters?.[e]), bussinessService],
    async()=>await WORKSService.WORKSEstimateSearch({tenantId,filters}),
    {
        ...config
    })

export default useSearchWORKS;