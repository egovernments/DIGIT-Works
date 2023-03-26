import React from "react";
import { useQuery } from "react-query";
import { WorksService } from "../../services/elements/Works";

//using this hook for searching only one project with projectNumber/id etc
const useProjectSearch = ({ tenantId, searchParams, filters = { limit: 10, offset: 0 }, config = {} }) => {
    console.log("Searched is being called");
    return useQuery(
        ["PROJECT_SEARCH", tenantId, filters],
        () => WorksService.searchProject(tenantId, searchParams, filters),
        {
            ...config,
            select: (data) => {
                return data?.Projects?.[0]
            }
    
        }
    )
}

export default useProjectSearch
