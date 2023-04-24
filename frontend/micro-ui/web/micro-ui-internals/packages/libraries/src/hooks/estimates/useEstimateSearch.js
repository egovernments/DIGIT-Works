import React from "react";
import { useQuery } from "react-query";
import { WorksService } from "../../services/elements/Works";

//using this hook for searching only one estimate with estimateNumber
const useEstimateSearch = ({ tenantId, filters, config = {} }) => useQuery(
    ["ESTIMATE_SEARCH", tenantId,filters],
    () => WorksService.estimateSearch({ tenantId, filters }),
    {
        ...config,
        cacheTime:0,
        select:(data)=>{
            return data?.estimates?.[0]
        }
        
    }
)

export default useEstimateSearch
