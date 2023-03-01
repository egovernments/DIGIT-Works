import React from "react";
import { useQuery } from "react-query";
import { Search } from "../services/Search";

const useViewProjectDetails = (t, tenantId, searchParams, filters, headerLocale, config = {cacheTime : 0}) => {
    return useQuery(
        ["SEARCH_PROJECTS", tenantId, searchParams?.projectNumber],
        () => Search.viewProjectDetailsScreen(t, tenantId, searchParams, filters, headerLocale),
        config
    );
}

export default useViewProjectDetails;