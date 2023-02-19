import React from "react";
import { useQuery } from "react-query";

const useViewProjectDetailsInEstimate = (t, tenantId, searchParams, filters, headerLocale, config = {cacheTime : 0}) => {
    return useQuery(
        ["SEARCH_PROJECTS", tenantId, searchParams?.projectNumber],
        () => window.Digit.Search.viewProjectDetailsScreen(t, tenantId, searchParams, filters, headerLocale),
        config
    );
}

export default useViewProjectDetailsInEstimate;