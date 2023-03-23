import React from "react";
import { useQuery } from "react-query";
import { WorksService } from "../../services/elements/Works";

//using this hook for searching only one estimate with estimateNumber
const useContractSearch = ({ tenantId, filters, config = {} }) => {
    
    return useQuery(
    ["CONTRACT_SEARCH", tenantId, filters],
    () => WorksService.contractSearch({ tenantId, filters }),
    {
        ...config,
        select: (data) => {
            return data?.contracts?.[0]
        }

    }
)
}
export default useContractSearch
