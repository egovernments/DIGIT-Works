import React from "react";
import { useQuery, useQueryClient } from "react-query";
import { WorksService } from "../../services/elements/Works";

//using this hook for searching only one estimate with estimateNumber
const useEstimateSearch = ({ tenantId, filters, config = {} }) => {
    const queryClient = useQueryClient();

    const { isLoading, error, ...rest } = useQuery(
        ["ESTIMATE_SEARCH", tenantId,filters],
        () => WorksService.estimateSearch({ tenantId, filters }),
        {
            ...config,
            cacheTime:0,
            select:(data)=>{
                //adding patch fix for getting latest approved contract in create wo, need to update
            return window.location.href.includes("/contracts/create-contract") ? data?.estimates?.filter((es) => es?.wfStatus?.includes("APPROVED"))?.[0] : data?.estimates?.[0];
        }
        
    }
    );
    // Function to invalidate the query
    const invalidateSearchQuery = () => {
        queryClient.invalidateQueries(["ESTIMATE_SEARCH", tenantId, filters]);
    };

    return {
        isLoading,
        error,
        invalidateSearchQuery,
        ...rest,
    };
};

export default useEstimateSearch
