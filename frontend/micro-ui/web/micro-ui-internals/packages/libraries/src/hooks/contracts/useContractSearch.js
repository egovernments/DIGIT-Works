import React from "react";
import { useQuery } from "react-query";
import { WorksService } from "../../services/elements/Works";

//using this hook for searching only one WO with WO Number
const useContractSearch = ({ tenantId, filters, config = {} }) => {
    
    return useQuery(
    ["CONTRACT_SEARCH", tenantId, filters],
    () => WorksService.contractSearch({ tenantId, filters }),
    {
        ...config,
        select: (data) => {
            return filters?.estimateIds?.length > 0 ? data?.contracts : data?.contracts?.[0]
        }

    }
)
}
export default useContractSearch
